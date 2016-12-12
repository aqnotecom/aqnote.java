/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.synch;

import java.util.concurrent.locks.ReentrantLock;

public class SynchPerf {

    transient private final ReentrantLock lock = new ReentrantLock();

    long                                  sum  = 0;

    public void addSumByLock() {
        sum = 0;
        ReentrantLock lock = this.lock;
        lock.lock();
        try {
            for (int i = 0; i < 10000000; i++) {
                sum += i;
            }
        } finally {
            lock.unlock();
        }
    }

    synchronized public void addSumBySynch() {
        sum = 0;
        for (int i = 0; i < 10000000; i++) {
            sum += i;
        }
    }

    public void addSum() {
        sum = 0;
        for (int i = 0; i < 10000000; i++) {
            sum += i;
        }
    }

    public static void main(String[] args) {
        SynchPerf perf = new SynchPerf();
        long start = System.currentTimeMillis();
        perf.addSum();
        long end = System.currentTimeMillis();
        System.out.println("addSum(): " + (end - start));

        start = System.currentTimeMillis();
        perf.addSumByLock();
        end = System.currentTimeMillis();
        System.out.println("addSumByLock(): " + (end - start));

        start = System.currentTimeMillis();
        perf.addSumBySynch();
        end = System.currentTimeMillis();
        System.out.println("addSumBySynch(): " + (end - start));
    }
}
