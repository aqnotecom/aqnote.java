/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.mutlithreadvariable;

class VisibilityThread extends Thread {

    // private volatile boolean stop;
    private boolean stop;

    public void run() {
        int i = 0;
        System.out.println("start loop.");
        while (!getStop()) {
            i++;
        }
        System.out.println("java loop,i=" + i);
    }

    public void stopIt() {
        stop = true;
    }

    public boolean getStop() {
        return stop;
    }
}

public class MultiThreadVariable {

    public static void main(String[] args) throws Exception {
        VisibilityThread v = new VisibilityThread();
        v.start();

        Thread.sleep(1000); // 停顿1秒等待新启线程执行
        System.out.println("即将置stop值为true");
        v.stopIt();
        Thread.sleep(1000);
        System.out.println("finish main");
        System.out.println("main中通过getStop获取的stop值:" + v.getStop());
    }
}
