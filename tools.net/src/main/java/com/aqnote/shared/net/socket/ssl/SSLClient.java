/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.ssl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocketFactory;

import com.aqnote.shared.lang.StreamUtil;

/**
 * SSLClient.java desc：模拟ssl客户端
 * 
 * @author madding.lip Sep 10, 2014 11:23:39 AM
 */
public class SSLClient {

    private Socket             socket;

    private ObjectOutputStream oos;

    private ObjectInputStream  ois;

    private BufferedReader     br;
    private InputStreamReader  isr;

    private String             message;

    public SSLClient(){
        System.setProperty("javax.net.ssl.keyStore", "/home/madding/output/client.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");
        System.setProperty("javax.net.ssl.trustStore", "/home/madding/output/server.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "123456");

        isr = new InputStreamReader(System.in);
        br = new BufferedReader(isr);
    }

    void run() {
        try {
            // 1. creating a socket to connect to the server
            socket = SSLSocketFactory.getDefault().createSocket("localhost", 1443);
            // requestSocket.setSoTimeout(timeout);
            // requestSocket.setKeepAlive(true);
            System.out.println("Connected to localhost in port 1443");
            // 2. get Input and Output streams
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();

            ois = new ObjectInputStream(socket.getInputStream());
            // 3: Communicating with the server
            do {
                displayMessage();

                inputMessage();

                sendMessage();
            } while (!message.equals("bye"));
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(br);
            StreamUtil.close(isr);

            StreamUtil.close(oos);

            StreamUtil.close(ois);

            StreamUtil.close(socket);
        }
    }

    void displayMessage() {
        try {
            message = (String) ois.readObject();
            System.out.println("sslServer>" + message);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void inputMessage() {
        System.out.print("sslClient>");
        try {
            message = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage() {
        try {
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] argv) throws Exception {
        SSLClient client = new SSLClient();
        client.run();
    }
}
