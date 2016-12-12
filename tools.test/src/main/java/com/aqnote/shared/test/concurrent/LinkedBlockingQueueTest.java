/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.concurrent;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * 类LinkedBlockingQueueTest.java的实现描述：TODO 类实现描述 
 * 测试case： 
 * 1.生产者快，消费者慢，是否池子的状况 
 * 2.生产者慢，消费者快，消费是否正常
 * 3.多个消费者之间的竞争问题
 * 
 * @author madding.lip Jul 31, 2013 3:53:23 PM
 */
public class LinkedBlockingQueueTest {

    public static final LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(16);

    public static void main(String[] args) {
        LinkedBlockingQueueTest test = new LinkedBlockingQueueTest();

//        LinkedBlockingQueueTest.Producer p = test.new Producer();
//        LinkedBlockingQueueTest.Consumer c = test.new Consumer();
//        LinkedBlockingQueueTest.Consumer c1 = test.new Consumer();
//        new Thread(p).start();
//        new Thread(c).start();
//        new Thread(c1).start();

         LinkedBlockingQueueTest.ConcurrentProducer cp = test.new ConcurrentProducer();
         LinkedBlockingQueueTest.ConcurrentConsumer cc = test.new ConcurrentConsumer();
         LinkedBlockingQueueTest.ConcurrentConsumer cc1 = test.new ConcurrentConsumer();
         new Thread(cp).start();
         new Thread(cc).start();
         new Thread(cc1).start();
    }

    class ConcurrentProducer implements Runnable {

        @Override
        public void run() {
            int i = 0;
            while (true) {
                try {
                    queue.put(++i);
                    System.out.println("producer: " + i);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Producer implements Runnable {

        @Override
        public void run() {
            int i = 0;
            while (true) {
                queue.offer(++i);
                System.out.println("producer: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ConcurrentConsumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("consumer(" + Thread.currentThread().getId() + "): " + queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Consumer implements Runnable {

        @Override
        public void run() {
            while (true) {
                if (!queue.isEmpty()) {
                    System.out.println("consumer(" + Thread.currentThread().getId() + "): " + queue.poll());
                }
            }
        }
    }

}
