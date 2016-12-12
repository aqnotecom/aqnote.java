/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.test.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.aqnote.shared.components.task.AbstractTaskV2;
import com.aqnote.shared.components.test.task.sub.MemberService;

/**
 * 类AutoSignLocalTask.java的实现描述：生产者消费者模型
 * 
 * @author madding.lip Dec 22, 2012 2:19:55 PM
 */
public class AbstractTaskV2Test extends AbstractTaskV2 {

    private static final Date FIRST_TIME;
    protected MemberService   memberService;

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR, 1);
        FIRST_TIME = calendar.getTime();
    }

    public AbstractTaskV2Test(){
        super(new File(getBaseDir()), "classpath:log4j.xml");
    }

    public void init(String[] args) {
        initInputFile(args[0]);
        initThreadNumber(args[1]);
        memberService = (MemberService) context.getBean("memberService");
    }

    private static String getBaseDir() {
        String basedir = System.getProperty("project.home");

        if (basedir == null) {
            System.setProperty("project.home", System.getProperty("user.dir", ""));
        }
        basedir = System.getProperty("project.home");
        if (!basedir.endsWith(File.separator)) {
            basedir += File.separator;
        }
        return basedir;
    }

    public void produce() {
        Thread thread = new Thread(new AutoProduceRunnable());
        thread.setName("produce");
        thread.start();
    }

    public void produceTimer() {
        Timer timer = new Timer("Timer - produce", true);
        timer.schedule(new AutoProduceRunnable(), FIRST_TIME);

        return;
    }

    public void cusume() {
        for (int i = 0; i < threadNumber; i++) {
            Thread thread = new Thread(new AutoConsumeRunnable());
            thread.setName("cusumer" + i);
            thread.start();
        }
    }

    class AutoProduceRunnable extends TimerTask {

        public void run() {
            if (file.exists()) {
                if (fileLock.exists()) {
                    try {
                        Thread.sleep(60 * 1000);
                    } catch (InterruptedException e) {
                        logger.error("Thread.sleep error.", e);
                    }
                }

                try {
                    InputStream in = new FileInputStream(file);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String lineStr = br.readLine();
                    while (lineStr != null) {
                        lineStr = lineStr.trim();
                        pcModel.produce(lineStr);
                        System.out.println("produce " + lineStr);
                        lineStr = br.readLine();
                    }
                    br.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            } else {
                logger.error("error:the file not exist!");
            }

        }
    }

    class AutoConsumeRunnable implements Runnable {

        public void run() {
            while (!pcModel.isEmpty()) {
                String _data = pcModel.consume();
                // TODO _data
                System.out.println("consume " + _data);
            }
        }
    }

    public static void main(String args[]) {
        args = new String[3];
        args[0] = "/home/madding/output/file.key";
        args[1] = "1";
        args[2] = "";
        if (args.length != 3) {
            System.out.println("paremeter count is error.");
            System.exit(0);
        }

        AbstractTaskV2Test task = new AbstractTaskV2Test();
        task.init(args);
        task.execute(1000);
    }
}
