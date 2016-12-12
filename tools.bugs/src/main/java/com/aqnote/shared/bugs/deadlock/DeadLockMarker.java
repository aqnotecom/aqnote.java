/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.bugs.deadlock;

/**
 * ClassName:DeadLockMarker
 * 
 * @author madding.lip
 * @version
 * @since Ver 1.0
 * @Date 2009-5-12 下午05:47:28
 * @see
 */
public class DeadLockMarker {

    public static void main(String[] args) throws Exception {
        // 资源
        final Object lock1 = new Object();
        final Object lock2 = new Object();
        // 和run2并发执行
        Runnable run1 = new Runnable() {

            public void run() {
                // 锁定lock1资源
                synchronized (lock1) {
                    print("lock1");
                    sleep();
                    // 锁定lock2资源
                    synchronized (lock2) {
                        print("lock2");
                    }
                }
            }
        };
        // 和run1并发执行
        Runnable run2 = new Runnable() {

            public void run() {
                // 锁定lock2资源
                synchronized (lock2) {
                    print("lcok2");
                    sleep();
                    // 锁定lock1资源
                    synchronized (lock1) {
                        print("lock1");
                    }
                }
            }
        };
        Thread t1 = new Thread(run1, "thread-1");
        Thread t2 = new Thread(run2, "thread-2");
        // 启动线程
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void print(String lock) {
        System.out.printf("%s locks %s/n", Thread.currentThread().getName(), lock);
    }

}
