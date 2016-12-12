/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.UnknownHostException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class TestSSLSocket {

    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {

    }

    public void test() throws UnknownHostException, IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 1234);
        printSocketInfo(socket);
        socket.startHandshake();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = System.out;
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String m = null;
        while ((m = r.readLine()) != null) {
            out.println(m);
            m = in.readLine();
            w.write(m, 0, m.length());
            w.newLine();
            w.flush();
        }
        w.close();
        r.close();
        socket.close();
    }

    public void test2() throws Exception {
        SSLContext context = SSLContext.getInstance("SUN", "SUNJSSE");
        context.init(null, null, null);
        SSLSocketFactory factory = (SSLSocketFactory) context.getSocketFactory();
        SSLSocket socket = (SSLSocket) factory.createSocket("localhost", 1234);
        printSocketInfo(socket);
        socket.startHandshake();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = System.out;
        BufferedWriter w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String m = null;
        while ((m = r.readLine()) != null) {
            out.println(m);
            m = in.readLine();
            w.write(m, 0, m.length());
            w.newLine();
            w.flush();
        }
        w.close();
        r.close();
        socket.close();

    }

    private static void printSocketInfo(SSLSocket s) {
        System.out.println("Socket class: " + s.getClass());
        System.out.println("   Remote address = " + s.getInetAddress().toString());
        System.out.println("   Remote port = " + s.getPort());
        System.out.println("   Local socket address = " + s.getLocalSocketAddress().toString());
        System.out.println("   Local address = " + s.getLocalAddress().toString());
        System.out.println("   Local port = " + s.getLocalPort());
        System.out.println("   Need client authentication = " + s.getNeedClientAuth());
        SSLSession ss = s.getSession();
        System.out.println("   Cipher suite = " + ss.getCipherSuite());
        System.out.println("   Protocol = " + ss.getProtocol());
    }
}
