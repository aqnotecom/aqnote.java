/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.p2p;

/**
 * Contains info related to the location of a peer node in the p2p network along with the peers id
 * 
 * @author axvelazq
 */
public class Peer {

    private String id;
    private String host;
    private int    port;

    public Peer(String id, String host, int port){
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public Peer(String host, int port){
        this(host + ":" + port, host, port);
    }

    public Peer(int port){
        this(null, port);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String toString() {
        return id + "(" + host + ":" + port + ")";
    }

}
