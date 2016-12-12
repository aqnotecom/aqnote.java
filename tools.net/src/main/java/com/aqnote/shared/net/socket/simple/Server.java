/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.socket.simple;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aqnote.shared.lang.StreamUtil;

/**
 * 模拟socket服务器返回信息
 * 
 * @author madding.lip
 */
public class Server {

    private static final SimpleDateFormat sdf        = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ServerSocket                  ssocket;
    private Socket                        connection = null;

    private ObjectOutputStream            out;
    private ObjectInputStream             in;

    private String                        control;
    private String                        inmessage;
    private String                        outmessage;

    Server(){
        try {
            // 1. creating a server socket
            ssocket = new ServerSocket(1080, 10);
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
            connection = ssocket.accept();
            System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            // 3. get Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());

            control = "start";
            sendMessage();
            // 4. The two parts communicate via the input and output streams
            do {
                try {
                    displayMessage();

                    setControl();

                    sendMessage();
                } catch (ClassNotFoundException classnot) {
                    System.err.println("Data received in unknown format");
                }
            } while (!control.equals("bye") && !connection.isClosed());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            StreamUtil.close(out);

            StreamUtil.close(in);

            StreamUtil.close(connection);
        }
    }

    void setControl() {
        control = "";
        if (inmessage.equals("bye")) {
            control = "bye";
        } else if (inmessage.equals("date")) {
            control = "date";
        } else if (inmessage.equals("help")) {
            control = "help";
        }
    }

    void displayMessage() throws IOException, ClassNotFoundException {
        inmessage = (String) in.readObject();
        System.out.println("client>" + inmessage);
    }

    void sendMessage() throws IOException {
        if (control.equals("start")) {
            outmessage = "Connection successful";
        } else if (control.equals("date")) {
            outmessage = sdf.format(new Date());
        } else if (control.equals("help")) {
            outmessage = "help will support letter.";
        } else {
            outmessage = "error input.";
        }

        try {
            if (!"bye".equalsIgnoreCase(control)) {
                out.writeObject(outmessage);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outmessage = "";
        }
    }

    public static void main(String args[]) {
        Server server = new Server();
        while (true) {
            server.run();
        }
    }
}
