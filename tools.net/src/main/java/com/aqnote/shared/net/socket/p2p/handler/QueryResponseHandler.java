/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p.handler;

import java.util.Hashtable;

import com.aqnote.shared.net.socket.p2p.Connection;
import com.aqnote.shared.net.socket.p2p.Handler;
import com.aqnote.shared.net.socket.p2p.Message;
import com.aqnote.shared.net.socket.p2p.Node;
import com.aqnote.shared.net.socket.p2p.sharer.SharerMessage;
import com.aqnote.shared.net.socket.p2p.sharer.SharerNode;
/**
 * Handler for the RESP
 * RESP file-name pid: Notifies a peer that the node specified by pid has a file with the given name.
 * @author axvelazq
 *
 */
public class QueryResponseHandler extends Handler{

	public QueryResponseHandler(Node node) {
		super(node);
	}

	@Override
	public void handleMessage(Connection conn, Message message) {
		SharerNode peer = (SharerNode)this.getNode();
		Hashtable<String,String> files = peer.getTableFiles();
		
		String data [] = message.getMsgData().split("\\s");
		if(data.length != 2){
			conn.sendData(new Message(SharerMessage.ERROR,"RESP: INCORRECT ARGUMENTS"));
			return;
		}
		String fileName = data[0];
		String pid = data[1];
		
		if(files.containsKey(fileName)){
			conn.sendData(new Message(SharerMessage.ERROR,"RESP: CANNOT ADD DUPLICATE FILE " + fileName));
			return;
		}
		files.put(fileName, pid);
		conn.sendData(new Message(SharerMessage.REPLY, "RESP: FILE INFO ADDED" + fileName));
		
		
		
		
	}

}
