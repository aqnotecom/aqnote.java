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
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManagerFactory;

public class Server implements Runnable {

    private static final int    DEFAULT_PORT                    = 10240;

    private static final String SERVER_KEY_STORE_PASSWORD       = "server_pwd";
    private static final String SERVER_TRUST_KEY_STORE_PASSWORD = "client_pwd";

    private SSLServerSocket     serverSocket;

    public static void main(String[] args) {
        Server server = new Server();
        server.init();
        Thread thread = new Thread(server);
        thread.start();
    }

    public synchronized void start() {
        if (serverSocket == null) {
            System.out.println("ERROR");
            return;
        }
        while (true) {
            try {
                Socket s = serverSocket.accept();
                InputStream input = s.getInputStream();
                OutputStream output = s.getOutputStream();

                BufferedInputStream bis = new BufferedInputStream(input);
                BufferedOutputStream bos = new BufferedOutputStream(output);

                receive(bis);
                
                send(bos, "I recevied.");
                
                s.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void init() {
        try {
            SSLContext ctx = SSLContext.getInstance("SSL");

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("JKS");
            KeyStore tks = KeyStore.getInstance("JKS");

            ks.load(new FileInputStream("src/main/resources/two_way_authentication/kserver.keystore"), SERVER_KEY_STORE_PASSWORD.toCharArray());
            tks.load(new FileInputStream("src/main/resources/two_way_authentication/tserver.keystore"), SERVER_TRUST_KEY_STORE_PASSWORD.toCharArray());

            kmf.init(ks, SERVER_KEY_STORE_PASSWORD.toCharArray());
            tmf.init(tks);

            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            serverSocket = (SSLServerSocket) ctx.getServerSocketFactory().createServerSocket(DEFAULT_PORT);
            serverSocket.setNeedClientAuth(true);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        start();
    }
    
    public void send(BufferedOutputStream bos, String data) throws IOException {
        System.out.println("server send> " + data);
        bos.write(data.getBytes());
        bos.flush();
    }
    
    public void receive(BufferedInputStream bis) throws IOException {
        byte[] buffer = new byte[1024];
        bis.read(buffer);
        System.out.println("server receive> " + new String(buffer));
    }
}
