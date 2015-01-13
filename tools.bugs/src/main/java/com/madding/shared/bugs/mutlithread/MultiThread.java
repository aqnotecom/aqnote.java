/*
 * Copyright madding.me.
 */
package com.madding.shared.bugs.mutlithread;

import java.util.ArrayList;
import java.util.List;

/**
 * 类MultiThread.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Jun 13, 2012 2:30:31 PM
 */
public class MultiThread {

    public static void main(String[] args) {
        for (int i = 0; i < 300; i++) {
            new Thread(new Fragment()).start();
        }
    }
}

class Fragment implements Runnable {

    List<?> list = new ArrayList<Object>();

    public Fragment(){
    }

    public void run() {
        for (int i = 0; true; i++) {
            list.remove(MultiThread.class.getName() + i);
            // try {
            // Thread.sleep(1);
            // } catch (InterruptedException e) {
            // }
        }
    }
}
