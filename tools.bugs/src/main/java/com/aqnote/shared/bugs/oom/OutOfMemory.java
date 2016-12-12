/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.bugs.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * 类OutOfMemory.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Jun 13, 2012 1:20:36 PM
 */
public class OutOfMemory {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        new Thread(new AddRunnable(list)).start();
        new Thread(new RemoveRunable(list)).start();
    }
};;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

class AddRunnable implements Runnable {

    List<String> list = null;

    public AddRunnable(List<String> list){
        this.list = list;
    }

    public void run() {
        for (int i = 0; true; i++) {
            list.add(OutOfMemory.class.getName() + i);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }

    }
};;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

class RemoveRunable implements Runnable {

    List<String> list = null;

    public RemoveRunable(List<String> list){
        this.list = list;
    }

    public void run() {
        for (int i = 0; true; i++) {
            list.remove(OutOfMemory.class.getName() + i);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
        }
    }
}
