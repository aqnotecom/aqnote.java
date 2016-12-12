/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p;

import java.io.IOException;

/**
 * The socket interface fot the P2P system.
 * 
 * @author axvelazq
 */
public interface ISocket {

    public void close() throws IOException;

    public int read() throws IOException;

    public int read(byte array[]) throws IOException;

    public void write(byte array[]) throws IOException;
}
