/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.task;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类AbstractTask.java的实现描述：生产者消费者模型原始实现，用来分析用
 * 
 * @author madding.lip Dec 22, 2012 1:59:01 PM
 */
public abstract class AbstractTask extends Task {

    protected static final Logger  logger  = LoggerFactory.getLogger(AbstractTask.class);

    protected static final PCModel pcModel = new PCModel();

    public AbstractTask(File basedir, String logfileName){
        super(basedir, logfileName);
    }

    /**
     * 该实现性能差些，可参考{@link AbstractTaskV2.PCModel}
     */
    protected static class PCModel {

        private LinkedBlockingQueue<String> lbqueue = new LinkedBlockingQueue<String>();

        public synchronized void produce(String line) {
            lbqueue.add(line);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            this.notifyAll();
        }

        public synchronized String consume() {
            if (lbqueue.isEmpty()) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    logger.error("", e);
                }
            }
            return lbqueue.poll();
        }

        public boolean isEmpty() {
            return lbqueue.isEmpty();
        }

        public int getLength() {
            return lbqueue.size();
        }

    }
}
