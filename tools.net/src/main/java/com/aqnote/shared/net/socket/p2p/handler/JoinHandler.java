/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p.handler;

import com.aqnote.shared.net.socket.p2p.Connection;
import com.aqnote.shared.net.socket.p2p.Handler;
import com.aqnote.shared.net.socket.p2p.Message;
import com.aqnote.shared.net.socket.p2p.Node;
import com.aqnote.shared.net.socket.p2p.Peer;
import com.aqnote.shared.net.socket.p2p.sharer.SharerMessage;
import com.aqnote.shared.net.socket.p2p.util.LoggerUtil;

/**
 * Handler for JOIN Messages JOIN pid host port: Requests a peer to add the supplied host/port combination, associated
 * with the node identified by pid, to its list of known peers.
 * 
 * @author axvelazq
 */
public class JoinHandler extends Handler {

    public JoinHandler(Node node){
        super(node);
    }

    @Override
    public void handleMessage(Connection conn, Message message) {
        Node peer = this.getNode();

        if (peer.maxPeerReached()) {
            LoggerUtil.getLogger().fine("MAX PEERS REACHED: " + peer.getMaxPeers());
            conn.sendData(new Message(SharerMessage.ERROR, "JOIN ERROR: TOO MANY ARGUMENTS"));
        }

        String data[] = message.getMsgData().split("\\s");

        if (data.length != 3) {
            conn.sendData(new Message(SharerMessage.ERROR, "JOIN ERROR : INCORRECT ARGUMENTS"));
        }

        Peer info = new Peer(data[0], data[1], Integer.parseInt(data[2]));

        if (peer.getPeer(info.getId()) != null) {
            conn.sendData(new Message(SharerMessage.ERROR, "JOIN ERROR: PEER ALREADY INSERTED"));
        } else if (peer.getId().equals(info.getId())) {
            conn.sendData(new Message(SharerMessage.ERROR, "JOIN ERROR: ATTEMPT TO INSERT SELF"));
        } else {
            peer.addPeer(info);
            conn.sendData(new Message(SharerMessage.REPLY, "JOIN: PEER ADDED: " + info.getId()));
        }
    }

}
