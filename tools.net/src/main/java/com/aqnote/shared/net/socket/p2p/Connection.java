/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p;

import java.io.IOException;
import java.net.UnknownHostException;

import com.aqnote.shared.net.socket.p2p.socket.P2PSocketFactory;
import com.aqnote.shared.net.socket.p2p.util.LoggerUtil;

/**
 * Encapsulates a socket connection to a peer node, providing simple, reliable send and receive functionality All data
 * sent to a peer through this class must be formatted as a P2PMessage object
 * 
 * @author axvelazq
 */
public class Connection {

    private Peer peerInfo;
    private ISocket  socket;

    public Connection(Peer info) throws IOException, UnknownHostException{
        this.peerInfo = info;
        this.socket = P2PSocketFactory.getSocketFactory().makeSocket(info.getHost(), info.getPort());
    }

    public Connection(Peer info, ISocket socket){
        this.peerInfo = info;
        this.socket = socket;
    }

    public void sendData(Message message) {
        try {
            socket.write(message.toBytes());
        } catch (IOException ex) {
            LoggerUtil.getLogger().warning("Error sending data: " + ex.getMessage());
        }
    }

    public Message recvData() {
        try {
            Message message = new Message(socket);
            return message;
        } catch (IOException ex) {
            LoggerUtil.getLogger().warning("Error to recv Data: " + ex.getMessage());
            return null;
        }
    }

    /**
     * Close the peer Connection
     */
    public void close() {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
                LoggerUtil.getLogger().warning("Error Closing: " + ex.getMessage());
            }
            socket = null;
        }
    }

    public Peer getPeerInfo() {
        return this.peerInfo;
    }

    public String toString() {
        return "PeerConnection[" + this.peerInfo + "]";
    }

}
