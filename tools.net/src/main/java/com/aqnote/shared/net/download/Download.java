/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.download;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Vector;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

/**
 * 下载网络资源,能定位到location重新下载具体的内容，支持location的http、ftp
 * 
 * @author madding.lip
 **/
public class Download {

    public final static boolean DEBUG       = true;                // 调试用
    private static int          BUFFER_SIZE = 8096;                // 缓冲区大小
    private Vector<String>      vDownLoad   = new Vector<String>(); // URL列表
    private Vector<String>      vFileList   = new Vector<String>(); // 下载后的保存文件名列表

    public Download(){
    }

    public void resetList() {
        vDownLoad.clear();
        vFileList.clear();
    }

    public void addItem(String url, String filename) {
        vDownLoad.add(url);
        vFileList.add(filename);
    }

    public void downLoadByList() {
        String url = null;
        String filename = null;
        // 按列表顺序保存资源
        for (int i = 0; i < vDownLoad.size(); i++) {
            url = (String) vDownLoad.get(i);
            filename = (String) vFileList.get(i);

            try {
                saveToFile(url, filename);
            } catch (IOException err) {
                if (DEBUG) {
                    System.out.println("资源[" + url + "]下载失败!!!");
                }
            }
        }

        if (DEBUG) {
            System.out.println("下载完成!!!");
        }
    }

    /**
     * 将HTTP资源另存为文件
     * 
     * @param destUrl String
     * @param fileName String
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    public void saveToFile(String destUrl, String fileName) throws IOException {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        URL url = null;
        byte[] buf = new byte[BUFFER_SIZE];
        int size = 0;
        // 建立链接
        url = new URL(destUrl);
        httpUrl = (HttpURLConnection) url.openConnection();
        // 连接指定的资源
        httpUrl.connect();
        // 获取网络输入流
        String location = httpUrl.getHeaderField("Location");

        if (location != null) {
            url = new URL(location);
            if ("http".equalsIgnoreCase(url.getProtocol())) {
                fileName = url.getFile();
                httpUrl = (HttpURLConnection) url.openConnection();
                // 连接指定的资源
                httpUrl.connect();
            } else if ("ftp".equalsIgnoreCase(url.getProtocol())) {
                fileName = url.getFile();
                FTPClient ftp = new FTPClient();
                try {

                    int reply;
                    if (url.getPort() == -1) {
                        ftp.connect(url.getHost());
                    } else {
                        ftp.connect(url.getHost(), url.getPort());
                    }
                    // 下面三行代码必须要，而且不能改变编码格式
                    ftp.setControlEncoding("GBK");
                    FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
                    conf.setServerLanguageCode("zh");

                    // 如果采用默认端口，可以使用ftp.connect(url) 的方式直接连接FTP服务器
                    String userInfo = url.getUserInfo();
                    if (userInfo != null) {
                        String[] userArray = userInfo.split(":");
                        ftp.login(userArray[0], userArray[1]);// 登录
                    }

                    ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                    reply = ftp.getReplyCode();

                    if (!FTPReply.isPositiveCompletion(reply)) {
                        ftp.disconnect();
                        System.out.println("登陆失败.....");
                        return;
                    }

                    fileName = URLDecoder.decode(fileName, "GBK");
                    fos = new FileOutputStream("/home/madding" + fileName);
                    if (this.DEBUG) {
                        System.out.println("start 获取链接[" + destUrl + "]的内容...将其保存为文件[" + fileName + "]");
                    }
                    ftp.retrieveFile(new String(fileName.getBytes("GBK"), "ISO-8859-1"), fos);
                    if (this.DEBUG) {
                        System.out.println("end.............");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException ioe) {
                        }
                    }
                    if (ftp.isConnected()) {
                        try {
                            ftp.disconnect();
                        } catch (IOException ioe) {
                        }
                    }
                }
                return;
            }
        }
        bis = new BufferedInputStream(httpUrl.getInputStream());
        // 建立文件
        fos = new FileOutputStream(fileName);
        if (this.DEBUG) {
            System.out.println("正在获取链接[" + destUrl + "]的内容...\n将其保存为文件[" + fileName + "]");
        }
        // 保存文件
        while ((size = bis.read(buf)) != -1) {
            fos.write(buf, 0, size);
        }

        fos.close();
        bis.close();
        httpUrl.disconnect();
    }
}
