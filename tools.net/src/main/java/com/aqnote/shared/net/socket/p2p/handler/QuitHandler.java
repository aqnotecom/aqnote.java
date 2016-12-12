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
import com.aqnote.shared.net.socket.p2p.sharer.SharerMessage;

/**
 * HANDLER FOR QUIT MESSAGES
 * QUIT pid: Indicate to a peer that the node identified by pid wishes to be unregistered from the P2P system.
 * @author axvelazq
 *
 */
public class QuitHandler extends Handler{

	public QuitHandler(Node node) {
		super(node);
	}

	@Override
	public void handleMessage(Connection conn, Message message) {
		Node peer = this.getNode();
		String pid = message.getMsgData();
		
		if(peer.getPeer(pid) == null){
			conn.sendData(new Message(SharerMessage.ERROR,"QUIT: PEER NOT FOUND"));
		}else{
			peer.removePeer(pid);
			conn.sendData(new Message(SharerMessage.REPLY,"QUIT: PEER REMOVED: " + pid));
		}
	}

}
