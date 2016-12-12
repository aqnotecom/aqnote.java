/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.thread;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ThreadPoolManager {

    private static ThreadPoolManager instance     = null;
    private List<Runnable>           taskQueue    = Collections.synchronizedList(new LinkedList<Runnable>()); // 任务队列
    private WorkThread[]             workQueue;                                                              // 工作线程（真正执行任务的线程）
    private static int               worker_num   = 5;                                                       // 工作线程数量（默认工作线程数量是5）
    @SuppressWarnings("unused")
    private static int               worker_count = 0;

    private ThreadPoolManager(){
        this(5);
    }

    private ThreadPoolManager(int num){
        worker_num = num;
        workQueue = new WorkThread[worker_num];
        for (int i = 0; i < worker_num; i++) {
            workQueue[i] = new WorkThread(i);
        }
    }

    public static synchronized ThreadPoolManager getInstance() {
        if (instance == null) instance = new ThreadPoolManager();
        return instance;
    }

    public void addTask(Runnable task) {
        // 对任务队列的操作要上锁
        synchronized (taskQueue) {
            if (task != null) {
                taskQueue.add(task);
                taskQueue.notifyAll();
                System.out.println("task id " + task.toString() + " submit!");
            }
        }
    }

    public void BatchAddTask(Runnable[] tasks) {
        // 对任务队列的修改操作要上锁
        synchronized (taskQueue) {
            for (Runnable e : tasks) {
                if (e != null) {
                    taskQueue.add(e);
                    taskQueue.notifyAll();
                    System.out.println("task id " + e.toString() + " submit!");
                }
            }
        }
    }

    public void destory() {
        System.out.println("pool begins to destory ...");
        for (int i = 0; i < worker_num; i++) {
            workQueue[i].stopThread();
            workQueue[i] = null;
        }
        // 对任务队列的操作要上锁
        synchronized (taskQueue) {
            taskQueue.clear();
        }

        System.out.println("pool ends to destory ...");
    }

    private class WorkThread extends Thread {

        private int     taksId;
        private boolean isRuning  = true;
        @SuppressWarnings("unused")
        private boolean isWaiting = false;

        public WorkThread(int taskId){
            this.taksId = taskId;
            this.start();
        }

        // 如果任务进行中时，不能立刻终止线程，需要等待任务完成之后检测到isRuning为false的时候，退出run()方法
        public void stopThread() {
            isRuning = false;
        }

        @Override
        public void run() {
            while (isRuning) {
                Runnable task = null;
                // 对任务队列的操作要上锁
                synchronized (taskQueue) {
                    // 等待
                    loop();
                    if (isRuning) {
                        task = taskQueue.remove(0);
                    }
                }
                // 当等待新任务加入时候，终止线程(调用stopThread函数)造成 temp ＝ null
                if (task != null) {
                    System.out.println("task info: " + taksId + " is begining");
                    isWaiting = false;
                    task.run();
                    isWaiting = true;
                    System.out.println("task info: " + taksId + " is finished");
                }
            }
        }
        
        private void loop() {
            // 任务队列为空，等待新的任务加入
            while (isRuning && taskQueue.isEmpty()) {
                try {
                    taskQueue.wait(20);
                } catch (InterruptedException e) {
                    System.out.println("InterruptedException occre...");
                    e.printStackTrace();
                }
            }
        }
    }
}
