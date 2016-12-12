/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    static final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 50, 60 * 60, TimeUnit.SECONDS,
                                                                      new ArrayBlockingQueue<Runnable>(10000));

    public static void main(String[] args) {
        ThreadPoolTest test = new ThreadPoolTest();
//         test.test01();

        test.test02();

        System.out.println(executor.getActiveCount());
    }

    public static void test01() {
        int x=0;
        while (x++<1) {
            for (int i = 0; i < 100; i++) {
                try {
                    executor.execute(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Thread.sleep(40);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    });
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void test02() {
        int x=0;
        while (x++<1000) {
            for (int i = 0; i < 260; i++) {
                try {
                    executor.submit(new MyCallable());
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static long max=0;
    class MyCallable implements Callable<String> {

        long ts;

        public MyCallable(){
            ts = System.currentTimeMillis();
        }

        @Override
        public String call() throws Exception {
//            long start = System.currentTimeMillis();
            Thread.sleep(40);
            long end = System.currentTimeMillis();
//            long cost = end - start;
            long total = end - ts;
//            System.out.println("all=" + total + " ,cost=" + cost);
            if(max < total) {
                max = total;
                System.out.println("max=" + max);
            }
            return null;
        }

    }

}
