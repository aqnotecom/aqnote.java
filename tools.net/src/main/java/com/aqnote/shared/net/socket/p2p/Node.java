/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.aqnote.shared.net.socket.p2p.socket.P2PSocketFactory;
import com.aqnote.shared.net.socket.p2p.util.LoggerUtil;

/**
 * Main class for the P2P System. It holds the node information (id, host, port), a list of known peers list of messages
 * handlers and a handler for routing data through the p2p netwoek.
 * 
 * @author axvelazq
 */

public class Node {

    private Peer                        peer;
    private static final int            SOCKET_TIMEOUT = 2000; // 2 seconds
    private int                         maxPeers;
    private Hashtable<String, IHandler> handlers;
    private Hashtable<String, Peer>     peerInfos;
    private IRouter                     router;
    private boolean                     shutdown;

    public Node(int maxPeers, Peer peer){
        if (peer.getHost() == null) {
            peer.setHost(getHostname());
        }
        if (peer.getId() == null) {
            peer.setId(peer.getHost() + ":" + peer.getPort());
        }
        this.peer = peer;
        this.maxPeers = maxPeers;
        this.peerInfos = new Hashtable<String, Peer>();
        this.handlers = new Hashtable<String, IHandler>();
        this.router = null;
        this.shutdown = false;
    }

    public Node(int port){
        this(0, new Peer(port));
    }

    /*
     * Attempt to determine the name or IP address of the machine this node is running on.
     */
    private String getHostname() {
        String host = "";
        Socket s = null;
        try {
            s = new Socket("www.google.com", 80);
            host = s.getLocalAddress().getHostAddress();
        } catch (UnknownHostException e) {
            LoggerUtil.getLogger().warning("Could not determine host: " + e);
        } catch (IOException e) {
            LoggerUtil.getLogger().warning(e.toString());
        } finally {
            if (s != null) {
                if (!s.isClosed()) {
                    try {
                        s.close();
                    } catch (IOException ex) {
                        LoggerUtil.getLogger().severe(ex.getMessage());
                    }
                }
            }
        }

        LoggerUtil.getLogger().config("Determined host: " + host);
        return host;
    }

    public ServerSocket makeServerSocket(int port) throws IOException {
        return makeServerSocket(port, 5);
    }

    public ServerSocket makeServerSocket(int port, int backlog) throws IOException {
        ServerSocket server = new ServerSocket(port, backlog);
        server.setReuseAddress(true);
        return server;
    }

    /**
     * Attempts to route and send a message to the specified peer This method using the Node's routing function to
     * decide the next immediate peer to actually send the message to, based on the peer id of the final destination. if
     * no router func ( obj ) has been registered, it will not work.
     * 
     * @param peerId the destination peer
     * @param type of message
     * @param data message
     * @param waitReply whether to wait for replies
     * @return
     */
    public List<Message> sendToPeer(String peerId, String type, String data, boolean waitReply) {
        Peer info = null;
        if (this.router != null) {
            info = this.router.route(peerId);

        }
        if (info == null) {
            LoggerUtil.getLogger().severe(String.format("Unable to route %s to %s ", type, peerId));
            return new ArrayList<Message>();
        }
        return connectAndSend(info, type, data, waitReply);
    }

    /**
     * Connects to the specified peer node to send a message, optionally waiting and returning all the replies
     * 
     * @param info the peer info
     * @param type of the message being sent
     * @param data the message data
     * @param waitReply whether to wait for reply(ies)
     * @return the list of replies, empty if something went wrong
     */
    public List<Message> connectAndSend(Peer info, String type, String data, boolean waitReply) {
        List<Message> msgreply = new ArrayList<Message>();
        try {
            Connection connection = new Connection(info);
            Message message = new Message(type, data);
            connection.sendData(message);
            LoggerUtil.getLogger().info("Sent to: " + message + "/" + connection);
            if (waitReply) {
                Message oneReply = connection.recvData();
                while (oneReply != null) {
                    msgreply.add(oneReply);
                    LoggerUtil.getLogger().info("Got Reply: " + oneReply);
                    oneReply = connection.recvData();
                }

            }
            connection.close();
        } catch (IOException ex) {
            LoggerUtil.getLogger().severe("Error: " + ex.getMessage());
        }
        return msgreply;
    }

    /**
     * Starts the main loop which is the primary operation of the Peer Node The main loop opens a server Socket, listens
     * for incoming connections and dispatches then to register handlers appropriately
     */
    public void mainLoop() {
        try {
            ServerSocket socket = makeServerSocket(peer.getPort());
            socket.setSoTimeout(SOCKET_TIMEOUT);
            while (!this.shutdown) {
                LoggerUtil.getLogger().fine("Listening...");
                try {
                    Socket client = socket.accept();
                    client.setSoTimeout(0);

                    PeerHandler handler = new PeerHandler(client);
                    handler.start();
                } catch (SocketTimeoutException te) {
                    LoggerUtil.getLogger().fine("" + te);
                    continue;
                }
            }
            socket.close();
        } catch (SocketException se) {
            LoggerUtil.getLogger().severe("Stopping main loop (SocketExc): " + se);
        } catch (IOException ioe) {
            LoggerUtil.getLogger().severe("Stopping main loop (IOExc): " + ioe);
        }
        this.shutdown = true;

    }

    public void startStabilizer(IStabilizer st, int delay) {
        StabilizerRunner stabilizer = new StabilizerRunner(st, delay);
        stabilizer.start();
    }

    public void addHandler(String type, IHandler handler) {
        handlers.put(type, handler);
    }

    public void addRouter(IRouter router) {
        this.router = router;
    }

    public boolean addPeer(Peer pi) {
        return addPeer(pi.getId(), pi);
    }

    public boolean addPeer(String key, Peer peerInfo) {
        if ((maxPeers == 0 || peerInfos.size() < maxPeers) && !peerInfos.containsKey(key)) {
            peerInfos.put(key, peerInfo);
            return true;
        }
        return false;
    }

    public Peer getPeer(String key) {
        return peerInfos.get(key);
    }

    public Peer removePeer(String key) {
        return peerInfos.remove(key);
    }

    public Set<String> getPeerKeys() {
        return peerInfos.keySet();
    }

    public int getNumberPeers() {
        return peerInfos.size();
    }

    public int getMaxPeers() {
        return this.maxPeers;
    }

    public boolean maxPeerReached() {
        return maxPeers > 0 && peerInfos.size() == maxPeers;
    }

    public String getInfo() {
        return peer.getId();
    }

    public String getHost() {
        return peer.getHost();
    }

    public int getPort() {
        return peer.getPort();
    }

    public String getId() {
        return peer.getId();
    }

    private class PeerHandler extends Thread {

        private ISocket socket;

        public PeerHandler(Socket s) throws IOException{
            socket = P2PSocketFactory.getSocketFactory().makeSocket(s);
        }

        public void run() {
            LoggerUtil.getLogger().fine("New Peer Handler");
            Connection connection = new Connection(null, socket);
            Message msg = connection.recvData();
            if (!handlers.containsKey(msg.getMsgType())) {
                LoggerUtil.getLogger().fine("Not handled: " + msg);
            } else {
                LoggerUtil.getLogger().finer("Handling: " + msg);
                handlers.get(msg.getMsgType()).handleMessage(connection, msg);
            }
            LoggerUtil.getLogger().fine("Disconnecting incoming: " + connection);
            connection.close();

        }
    }

    private class StabilizerRunner extends Thread {

        private IStabilizer stabilizer;
        private int         delay;

        public StabilizerRunner(IStabilizer st, int delay){
            this.stabilizer = st;
            this.delay = delay;
        }

        public void run() {
            while (true) {
                this.stabilizer.stabilize();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    LoggerUtil.getLogger().fine("" + e);
                }
            }
        }
    }
}
