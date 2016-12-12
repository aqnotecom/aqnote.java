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
 * 类AbstractTaskV2.java的实现描述：生产者消费者模型v2版本
 * 
 * @author madding.lip Dec 22, 2012 1:59:01 PM
 */
public abstract class AbstractTaskV2 extends AbstractTask {

    protected static final Logger  logger  = LoggerFactory.getLogger(AbstractTaskV2.class);

    protected static final PCModel pcModel = new PCModel();

    public AbstractTaskV2(File basedir, String logfileName){
        super(basedir, logfileName);
    }

    /**
     * 借助LinkedBlockingQueue方法实现并发控制
     */
    protected static class PCModel {

        private LinkedBlockingQueue<String> lbqueue = new LinkedBlockingQueue<String>();

        public void produce(String line) {
            try {
                lbqueue.put(line);
            } catch (InterruptedException e) {
                logger.error("false:_data:invoke error", e);
            }
        }

        public String consume() {
            try {
                return lbqueue.take();
            } catch (InterruptedException e) {
                logger.error("false:_data:invoke error", e);
            }
            return null;
        }

        public boolean isEmpty() {
            return lbqueue.isEmpty();
        }

        public int getLength() {
            return lbqueue.size();
        }
    }
}
