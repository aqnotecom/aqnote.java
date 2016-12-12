/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p;

/**
 * Used to determine the next hoop for a message to be routed to in the p2p network
 * 
 * @author axvelazq
 */
public interface IRouter {

    public Peer route(String peerId);
}
