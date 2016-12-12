/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.simple;

import java.io.*;
import java.net.*;

import com.aqnote.shared.lang.StreamUtil;

/**
 * 模拟socket客户端发送信息
 * 
 * @author madding.lip
 */
public class Client {

    private Socket             socket;

    private ObjectOutputStream out;
    private ObjectInputStream  in;
    private BufferedReader     br;
    private InputStreamReader  isr;

    private String             message;

    public Client(){
        isr = new InputStreamReader(System.in);
        br = new BufferedReader(isr);
    }

    void run() {
        try {
            // 1. creating a socket to connect to the server
            socket = new Socket("localhost", 1080);
            // requestSocket.setSoTimeout(timeout);
            // requestSocket.setKeepAlive(true);
            System.out.println("Connected to localhost in port 1080");
            // 2. get Input and Output streams
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            // 3: Communicating with the server
            do {
                try {
                    displayMessage();

                    inputMessage();

                    sendMessage();
                } catch (ClassNotFoundException classNot) {
                    System.err.println("data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (UnknownHostException e) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(br);
            StreamUtil.close(isr);
            
            StreamUtil.close(in);
            
            StreamUtil.close(out);
            
            StreamUtil.close(socket);
        }
    }

    void displayMessage() throws IOException, ClassNotFoundException {
        message = (String) in.readObject();
        System.out.println("server>" + message);
    }

    void inputMessage() throws IOException {
        System.out.print("client>");
        message = br.readLine();
    }

    void sendMessage() {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Client client = new Client();
        client.run();
    }
}
