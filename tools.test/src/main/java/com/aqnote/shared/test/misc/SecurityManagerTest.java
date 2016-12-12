/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.misc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 限制文件读写和网络连接
 * 
 * @author madding.lip
 */
class MySecurityManager extends SecurityManager {

    @Override
    public void checkRead(FileDescriptor fd) {
        throw new SecurityException("File reading is not allowed");
    }

    @Override
    public void checkWrite(FileDescriptor fd) {
        throw new SecurityException("File writing is not allowed");
    }

    @Override
    public void checkConnect(String host, int port) {
        throw new SecurityException("Socket connections are not allowed");
    }
}

public class SecurityManagerTest {

    public static void main(String[] args) throws IOException {

        System.setSecurityManager(new MySecurityManager());

        File file = new File("/Users/madding/logs/tmp/tmpfile");
        InputStream is = new FileInputStream(file);

        // for (int count = 0; (count = is.read()) != -1;) {
        // System.out.println(count + " []");
        // System.out.println(Character.toChars(count));
        // }

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1];
        for (int length = 0; (length = is.read(buffer)) != -1;) {
            result.write(buffer, 0, length);
        }
        System.out.println(result);

        is.close();
    }
}
