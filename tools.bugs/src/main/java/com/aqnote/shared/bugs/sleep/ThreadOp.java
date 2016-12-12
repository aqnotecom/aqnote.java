/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.bugs.sleep;

/**
 * 类Main.java的实现描述：线程常见操作理解
 * 
 * @author madding.lip Jun 21, 2012 2:38:46 PM
 */
public class ThreadOp {

    public static void main(String[] args) throws Exception {
//        thread_sleep();
//        thread_normal();
        thread_join();
    }

    // result maybe not 10.
    public static void thread_sleep() throws InterruptedException {

        long start = System.currentTimeMillis();
        Thread.sleep(10);
        System.out.println(System.currentTimeMillis() - start);

    }
    
    
    /**
     * 
     * Thread a: 0
     * Wait for the child threads to finish.
     * Exit from Main Thread.
     * Thread b: 0
     * .....
     * Thread b: 4
     * Exit from thread: Thread a
     * Exit from thread: Thread b
     */
    public static void thread_normal() {
        DemoAlive da = new DemoAlive("Thread a");
        DemoAlive db = new DemoAlive("Thread b");
        try {
            System.out.println("Wait for the child threads to finish.");
            
            if (!da.isAlive()) {
                System.out.println("Thread A not alive.");
            }
            
            if (!db.isAlive()) {
                System.out.println("Thread B not alive.");
            }

        } catch (Exception e) {
        }
        System.out.println("Exit from Main Thread.");
    }
    
    /**
     *  Wait for the child threads to finish.
     *  Thread b: 0
     *  ...
     *  Thread a: 4
     *  Exit from thread: Thread b
     *  Exit from thread: Thread a
     *  Thread A not alive.
     *  Thread B not alive.
     *  Exit from Main Thread.
     */
    public static void thread_join() {
        DemoAlive da = new DemoAlive("Thread a");
        DemoAlive db = new DemoAlive("Thread b");
        try {
            System.out.println("Wait for the child threads to finish.");
            // da线程没结束代码就不完下走
            da.join();
            if (!da.isAlive()) {
                System.out.println("Thread A not alive.");
            }

            // db线程没结束代码就不完下走
            db.join();
            if (!db.isAlive()) {
                System.out.println("Thread B not alive.");
            }

        } catch (Exception e) {
        }
        System.out.println("Exit from Main Thread.");
    }
}

class DemoAlive extends Thread {

    int value;

    public DemoAlive(String str){
        super(str);
        value = 0;
        start();
    }

    public void run() {
        try {
            while (value < 5) {
                System.out.println(getName() + ": " + (value++));
                Thread.sleep(250);
            }
        } catch (Exception e) {
        }
        System.out.println("Exit from thread: " + getName());
    }
}
