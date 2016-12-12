/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketPerf {

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
        int i = 0;
        while (true) {
            i++;
            Socket socket = new Socket("localhost", 80);
            socket.setKeepAlive(true);
            System.out.println(i + " " + socket.toString());

            Thread.sleep(1);
        }
        // OutputStream ostream = socket.getOutputStream();
        //
        // String message = "HTTP 1.1";
        // ostream.write(message.getBytes());
        //
        // InputStream istream = socket.getInputStream();
        // DataInputStream distream = new DataInputStream(istream);
        // System.out.println(distream.readLine());
        //
        // if (istream != null) {
        // ostream.close();
        // }
    }
}
