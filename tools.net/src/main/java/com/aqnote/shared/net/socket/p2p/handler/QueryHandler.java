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
import com.aqnote.shared.net.socket.p2p.sharer.QueryEngine;
import com.aqnote.shared.net.socket.p2p.sharer.SharerMessage;
import com.aqnote.shared.net.socket.p2p.sharer.SharerNode;

public class QueryHandler extends Handler {

	public QueryHandler(Node node) {
		super(node);
	}

	@Override
	public void handleMessage(Connection conn, Message message) {
		Node peer = this.getNode();
		String data[] =  message.getMsgData().split("\\s");
		if(data.length != 3){
			conn.sendData(new Message(SharerMessage.ERROR,"QUERY: INCORRECT ARGUMENTS"));
			return;
		}
		
		String ret_id = data[0];
		String key = data[1];
		int ttl = Integer.parseInt(data[2]);
		// SEND ACK AND CLOSE CONNECTION.  AFTER THAT A NEW THREAD WILL TAKE CARE OF THE QUERY
		conn.sendData(new Message(SharerMessage.REPLY, "QUERY: ACK"));
		
		QueryEngine query = new QueryEngine((SharerNode)peer, ret_id, key, ttl);
		query.start();
		
	}

}
