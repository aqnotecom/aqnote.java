/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p.socket;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.net.UnknownHostException;

import com.aqnote.shared.net.socket.p2p.ISocket;

/**
 * PeerSocket Encapsulates the standard Socket object of the Java Library to fit the Socket Interface of the P2P system.
 * 
 * @author axvelazq
 */
public class P2PSocket implements ISocket {

    private Socket       socket;
    private InputStream  input;
    private OutputStream output;
    
    public P2PSocket(Socket socket) throws IOException{
        this.socket = socket;
        this.input = socket.getInputStream();
        this.output = socket.getOutputStream();
    }

    public P2PSocket(String host, int port) throws IOException, UnknownHostException{
        this(new Socket(host, port));
    }
    
    @Override
    public void close() throws IOException {
        this.socket.close();
        this.input.close();
        this.output.close();
    }
    
    @Override
    public int read() throws IOException {
        return this.input.read();
    }
    
    @Override
    public int read(byte[] array) throws IOException {
        return this.input.read(array);
    }
    
    @Override
    public void write(byte[] array) throws IOException {
        this.output.write(array);
        this.output.flush();
    }

}
