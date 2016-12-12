/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.misc;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;

public class URLTest {
    
    private static final URLTest test = new URLTest();

    public static void main(String[] args) {

        test.test();

    }
    
    /**
     * 
     * Object的equals依赖hashcode，而URL的hashcode依赖URLStreamHandler，并且只计算一次缓存下来；
     * URLStreamHandler会获取网络中hostAddress做比对；一般服务器都有做dns的负载均衡；   
     *  - InetAddress addr = getHostAddress(u);
     * 所以超过1d基本上拿到的结果都不一样
     * 
     * 如果要比较url，可考虑用URI或者String
     */
    public void test() {
        try {
            HashSet<URL> set = new HashSet<URL>();
            set.add(new URL("http://google.com"));
            set.contains(new URL("http://google.com"));   // true
            Thread.sleep(60000);
            set.contains(new URL("http://google.com"));  // connect to network: false; else true;
        } catch (MalformedURLException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
