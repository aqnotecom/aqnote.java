package com.madding.shared.bugs.hashmapdeadloop;

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
