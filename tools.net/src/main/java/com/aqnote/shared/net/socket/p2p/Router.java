/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p;

/**
 * Router
 * @author axvelazq
 *
 */
public class Router implements IRouter {
	private Node node;
	public Router(Node node){
		this.node = node;
	}
	@Override
	public Peer route(String peerId) {
		if(node.getPeerKeys().contains(peerId)){
			return node.getPeer(peerId);
		}else{
			return null;
		}
		
	}

}
