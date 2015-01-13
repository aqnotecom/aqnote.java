/*
 * Copyright madding.me.
 */
package com.madding.shared.btrace;

/**
 * 类TraceMethodTimeSpendTest.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip May 16, 2012 10:17:13 AM
 */
public class TraceMethodTimeSpendTest {

    public void execute() {
        int i = (int) (Math.random() * 1000);
        System.out.println(i);
        try {
            Thread.sleep(Math.abs(i));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void loopExcute() {
        while (true) {
            execute();
        }
    }

    public static void main(String[] args) {
        TraceMethodTimeSpendTest m = new TraceMethodTimeSpendTest();
        m.loopExcute();
    }
}
