/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.bugs.contextswitch;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 模拟上下文切换，即vmstat 1 100中的cs=20000
 */
public final class ContextSwitch {

    static final int               RUNS     = 3;
    static final int               ITERATES = 1000000;
    static AtomicReference<Thread> turn     = new AtomicReference<Thread>();

    static final class WorkerThread extends Thread {

        volatile Thread other;
        volatile int    nparks;

        public void run() {
            final AtomicReference<Thread> t = turn;
            final Thread other = this.other;
            if (turn == null || other == null) throw new NullPointerException();
            int p = 0;
            for (int i = 0; i < ITERATES; ++i) {
                while (!t.compareAndSet(other, this)) {
                    LockSupport.park();
                    ++p;
                }
                LockSupport.unpark(other);
            }
            LockSupport.unpark(other);
            nparks = p;
            System.out.println("parks: " + p);

        }
    }

    static void test() throws Exception {
        WorkerThread a = new WorkerThread();
        WorkerThread b = new WorkerThread();
        a.other = b;
        b.other = a;
        turn.set(a);
        long startTime = System.nanoTime();
        a.start();
        b.start();
        a.join();
        b.join();
        long endTime = System.nanoTime();
        int parkNum = a.nparks + b.nparks;
        System.out.println("Average time: " + ((endTime - startTime) / parkNum) + "ns");
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < RUNS; i++) {
            test();
        }
    }
}
