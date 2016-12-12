/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.download.batch;

import java.io.IOException;

import com.aqnote.shared.net.download.Download;

/**
 * infoxa内容批量下载代码
 * 
 * @author madding.lip
 */
public class InfoxaDownload {
    
    private final static String url = "http://www.infoxa.com/asp/book/xzcs_nn1.asp?id=";
    
    private Download instance = new Download();

    public static void main(String args[]) {
        
        if(args.length < 2) {
            System.err.println("USAGE: class begin end");
            System.exit(-1);
        }
        
        int begin = Integer.valueOf(args[0]);
        int end = Integer.valueOf(args[1]);
        
        // 23 22239
        InfoxaDownload idown = new InfoxaDownload();
        
        for (int id =begin; id < end; id++) {
            try {
                idown.instance.saveToFile(url + id, id + "");
            } catch (Exception err) {
                System.out.println(err.getMessage());
            }
        }
    }
    
    class InfoxaRunnable implements Runnable {
        
        private String url;
        private String id;
        private Download down = new Download();
        
        
        public InfoxaRunnable(String url, String id, Download down) {
            this.url = url;
            this.id = id;
            this.down = down;
        }

        @Override
        public void run() {
            try {
                down.saveToFile(url, id);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        
    }
}


