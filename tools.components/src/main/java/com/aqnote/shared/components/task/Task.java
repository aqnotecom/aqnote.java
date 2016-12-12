/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.task;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 类Task.java的实现描述：task包含的基础功能抽取
 * 
 * @author madding.lip Dec 22, 2012 1:59:01 PM
 */
public abstract class Task {

    protected static final Logger logger       = LoggerFactory.getLogger(AbstractTask.class);

    protected int                 threadNumber = 10;
    protected static final String DAEMON_FILE  = "classpath:spring-daemon-root.xml";
    protected File                file         = new File("/home/admin/inputfile.txt");
    protected File                fileLock     = new File("/home/admin/inputfile.txt.lock");

    protected ApplicationContext  context      = null;

    public Task(File basedir, String logfileName){
        System.setProperty("application.codeset", "UTF-8");
        System.setProperty("database.codeset", "UTF-8");

        if (basedir == null) {
            throw new IllegalArgumentException("Basedir is not specified");
        }

        basedir.mkdirs();

        if (!basedir.isDirectory() || !basedir.exists()) {
            throw new IllegalArgumentException("Base directory does not exist: " + basedir);
        }

        logger.info("Set basedir to: " + basedir);

        context = new ClassPathXmlApplicationContext(DAEMON_FILE);
    }

    public Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    public void initThreadNumber(String threadNumberStr) {
        try {
            this.threadNumber = Integer.parseInt(threadNumberStr);
        } catch (NumberFormatException nfe) {
            this.threadNumber = 1;
        }
    }

    public void initInputFile(String fileName) {
        if (fileName != null) {
            this.file = new File(fileName);
            this.fileLock = new File(fileName + ".lock");
        }
    }

    public void waitForProduce(int millis) {
        if (millis < 1) {
            millis = 1000;
        }

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            logger.error("false:_data:sleep error", e);
        }
    }

    abstract public void produce();

    abstract public void cusume();

    public void execute(int waitMillis) {
        produce();
        waitForProduce(waitMillis);
        cusume();
    }
}
