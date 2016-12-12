/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.bugs.hashmapdeadloop;

import java.util.HashMap;

public class HashMapLoop {

    private HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();

    public HashMapLoop(){
        Thread t1 = new Thread() {

            public void run() {
                for (int i = 0; i < 50000; i++) {
                    hash.put(new Integer(i), i);
                }

                System.out.println("t1 over");
            }
        };

        Thread t2 = new Thread() {

            public void run() {
                for (int i = 0; i < 50000; i++) {
                    hash.put(new Integer(i), i);
                }

                System.out.println("t2 over");
            }
        };

        t1.start();
        t2.start();

    }

    public static void main(String[] args) {
        new HashMapLoop();
    }
}
