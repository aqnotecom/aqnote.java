/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.https;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.security.Security;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

public class HTTPSServer {

    public static void main(String[] args) throws Exception {

//        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        System.setProperty("javax.net.ssl.keyStore", "/home/madding/certs/test.keystore");
        System.setProperty("javax.net.ssl.keyStorePassword", "123456");

        // SSLContext context = SSLContext.getInstance("SSL", "SunJSSE");
        // context.init(null, null, null);
        // SSLServerSocketFactory ssf = (SSLServerSocketFactory) context.getServerSocketFactory();
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        SSLServerSocket ss = (SSLServerSocket) ssf.createServerSocket(8080);
        // ss.setNeedClientAuth(true);

        while (true) {
            try {
                Socket s = ss.accept();
                OutputStream out = s.getOutputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                String line = null;
                while (((line = in.readLine()) != null) && (!("".equals(line)))) {
                    System.out.println(line);
                }
                StringBuffer buffer = new StringBuffer();
                buffer.append("<HTML><HEAD><TITLE>HTTPS Server</TITLE></HEAD>\n");
                buffer.append("<BODY>\n<H1>Success!</H1></BODY></HTML>\n");

                String string = buffer.toString();
                byte[] data = string.getBytes();
                out.write("HTTP/1.0 200 OK\n".getBytes());
                out.write(new String("Content-Length: " + data.length + "\n").getBytes());
                out.write("Content-Type: text/html\n\n".getBytes());
                out.write(data);
                out.flush();

                out.close();
                in.close();
                s.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
