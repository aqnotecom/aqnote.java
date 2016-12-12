/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p;

/**
 * Handles a new incomming connection from a peer.
 * 
 * @author axvelazq
 */
public interface IHandler {

    public void handleMessage(Connection conn, Message message);
}
