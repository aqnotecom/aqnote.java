/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.protocol.ssl.two_way_authentication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.util.Date;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class Client {

    private static final String DEFAULT_HOST                    = "127.0.0.1";
    private static final int    DEFAULT_PORT                    = 10240;

    private static final String CLIENT_KEY_STORE_PASSWORD       = "client_pwd";
    private static final String CLIENT_TRUST_KEY_STORE_PASSWORD = "server_pwd";

    private SSLSocket           sslSocket;

    public static void main(String[] args) {
        Client client = new Client();
        client.init();
        client.process();
    }

    public void process() {
        if (sslSocket == null) {
            System.out.println("ERROR");
            return;
        }
        try {
            InputStream input = sslSocket.getInputStream();
            OutputStream output = sslSocket.getOutputStream();

            BufferedInputStream bis = new BufferedInputStream(input);
            BufferedOutputStream bos = new BufferedOutputStream(output);

            send(bos, new Date().toString());

            receive(bis);

            sslSocket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void init() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");

            ks.load(new FileInputStream("src/main/resources/two_way_authentication/kclient.keystore"),
                    CLIENT_KEY_STORE_PASSWORD.toCharArray());
            tks.load(new FileInputStream("src/main/resources/two_way_authentication/tclient.keystore"),
                     CLIENT_TRUST_KEY_STORE_PASSWORD.toCharArray());

            kmf.init(ks, CLIENT_KEY_STORE_PASSWORD.toCharArray());
            tmf.init(tks);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            sslSocket = (SSLSocket) ctx.getSocketFactory().createSocket(DEFAULT_HOST, DEFAULT_PORT);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void send(BufferedOutputStream bos, String data) throws IOException {
        System.out.println("client send> " + data);
        bos.write(data.getBytes());
        bos.flush();
    }
    
    public void receive(BufferedInputStream bis) throws IOException {
        byte[] buffer = new byte[1024];
        bis.read(buffer);
        System.out.println("client receive> " + new String(buffer));
    }

}
