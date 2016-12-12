/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.bugs.waitcpu;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟等待cpu执行，即vmstat 1 100中的r=5
 * 
 * @author madding.lip
 */
public class Waitcpu {

    static AtomicInteger ai = new AtomicInteger();
    static AtomicInteger ai1 = new AtomicInteger();

    public static void main(String[] args) {
        // 40个线程处理
        for (int i = 0; i < 40; i++) {
            new Thread(new MyTask(String.valueOf(ai.addAndGet(1)))).start();
        }
    }
    
    static class MyTask implements Runnable {

        private String name;

        public MyTask(String name){
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                // 代表处理时间 ,测试环境处理时间大概24ms
                System.out.println(name + " begin");
                long start = System.currentTimeMillis();
                long sum = 1;
                for (int i = 0; i < 50000000; i++) {
                    sum+=i;
                }
                long end = System.currentTimeMillis();
                System.out.println(name + " " + ai1.addAndGet(1) + " end. sum " + sum + " " + (end - start) + " ms");
                
                try {
                    Thread.sleep(500);  // 每秒80个请求
                } catch (InterruptedException e) {
                }
            }
        }

    }

}

