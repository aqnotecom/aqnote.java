/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.thread;

import com.aqnote.shared.thread.ThreadPoolManager;

public class ThreadPoolManagerTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Runnable[] tasks = createBatchTask(7);
        ThreadPoolManager pool = ThreadPoolManager.getInstance();
        pool.BatchAddTask(tasks);
        pool.destory();
    }

    private static Runnable[] createBatchTask(int n) {
        Runnable[] tasks = new TaskUpload[n];
        for (int i = 0; i < n; i++) {
            tasks[i] = new TaskUpload("task id is " + i);
        }
        return tasks;
    }
}


class TaskUpload implements Runnable {
    
    protected String info;

    public TaskUpload(String info){
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        System.out.println(info + "sleep begin ....");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(info + "sleep end ....");
    }
}