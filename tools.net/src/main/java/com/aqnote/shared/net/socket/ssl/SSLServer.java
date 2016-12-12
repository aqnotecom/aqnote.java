/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.ssl;

import static com.aqnote.shared.net.socket.ssl.command.SSLCommand.BYE;
import static com.aqnote.shared.net.socket.ssl.command.SSLCommand.DATE;
import static com.aqnote.shared.net.socket.ssl.command.SSLCommand.HELLO;
import static com.aqnote.shared.net.socket.ssl.command.SSLCommand.HELP;
import static com.aqnote.shared.net.socket.ssl.command.SSLCommand.command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocketFactory;

import com.aqnote.shared.lang.StreamUtil;
import com.aqnote.shared.net.socket.ssl.command.SSLCommand;

/**
 * SSLServer.java desc：模拟ssl服务端
 * 
 * @author madding.lip Sep 10, 2014 11:23:39 AM
 */
public class SSLServer {

    public static final SimpleDateFormat sdf    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final ExecutorService pool   = Executors.newCachedThreadPool();

    private ServerSocket                 ssocket;
    private Socket                       socket = null;

    public SSLServer(){
        System.setProperty("javax.net.ssl.keyStore", "/home/madding/output/server.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        try {
            // 1. creating a server socket
            ssocket = SSLServerSocketFactory.getDefault().createServerSocket(1443, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void destory() {
        // 4: Closing connection
        StreamUtil.close(ssocket);
    }

    void run() {
        if (ssocket == null) {
            System.exit(1);
        }

        try {
            // 2. Wait for connection
            System.out.println("Waiting for connection");
            socket = ssocket.accept();
            pool.execute(new Handler(socket));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] argv) throws Exception {
        SSLServer server = new SSLServer();
        while (true) {
            server.run();
        }
    }
}

class Handler implements Runnable {

    private Socket             socket;

    private ObjectOutputStream oos;

    private ObjectInputStream  ois;

    private String             control;
    private String             inmessage;
    private String             outmessage;

    Handler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Connection received from " + socket.getInetAddress().getHostName());
            // 3. get Input and Output streams
            oos = new ObjectOutputStream(socket.getOutputStream());

            ois = new ObjectInputStream(socket.getInputStream());

            control = HELLO;
            socketOMessage();
            // 4. The two parts communicate via the input and output streams
            while (!control.equals(SSLCommand.BYE) && !socket.isClosed()) {

                resetMessage();
                
                socketIMessage();

                displayIMessage();

                dealIMessage();

                socketOMessage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            StreamUtil.close(oos);

            StreamUtil.close(ois);

            StreamUtil.close(socket);
        }
    }

    private void socketIMessage() {
        try {
            inmessage = (String) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dealIMessage() {
        if (command.contains(inmessage)) {
            control = inmessage;
        } else {
            control = SSLCommand.UNKNOWN;
        }
        
        if (control.equals(HELLO)) {
            outmessage = "Connection successful";
        } else if (control.equals(DATE)) {
            outmessage = SSLServer.sdf.format(new Date());
        } else if (control.equals(HELP)) {
            outmessage = "help will support letter.";
        } else {
            outmessage = "error input.";
        }
    }

    private void displayIMessage() {
        System.out.println("sslClient>" + inmessage);
    }

    private void socketOMessage() {
        try {
            if (!BYE.equalsIgnoreCase(control)) {
                oos.writeObject(outmessage);
                oos.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void resetMessage() {
        inmessage = "";
        outmessage = "";
        control = "";
    }

}
