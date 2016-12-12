/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.io;

import static java.lang.String.format;
import static java.lang.System.out;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class ByteBufferTest {

    private static final String FILE_PATH = "/Users/madding/logs/bytebuffer/tmpfile";
    private static WritableByteChannel target;
    private static ReadableByteChannel source;
    private static ByteBuffer          buffer;

    public static void main(String[] args) throws IOException, InterruptedException {
        out.println("start");
//        createfile();
        
         test_perf();
         out.println("stop");
    }

    protected static void createfile() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream os = new FileOutputStream(file, true);
        byte[] b = new byte[1024 * 1024];
        for (int i = 0; i < 512; i++) {
            for (int j = 0; j < 1024 * 1024; j++) {
                b[j] = (byte) ((Math.random() * 128) % 128);
            }
            os.write(b, 0, 1024 * 1024);
            os.flush();
            System.out.printf("%d\n", i);
        }
        os.close();
    }
    
/**
 * start
 * 1048576, 167, 94
 * 2097152, 176, 95
 * 4194304, 195, 96
 * 8388608, 215, 99
 * 16777216, 227, 99
 * 33554432, 229, 100
 * 67108864, 275, 104
 * stop
 * 说明：
 *  - HeapByteBuffer 的数据处理流程如下：io -> 临时DirectByteBufer -> 应用HeapDirectByteBuffer -> 临时DirectByteBufer -> io，最大消耗在new natvie memory
 *  - DirectByteBufer的数据处理流程如下：io -> 应用DirectByteBufer -> io，
 */
    private static void test_perf() throws IOException, InterruptedException {
        long directTime;
        long normalTime;
       
        for (int i = 1024 * 1024; i <= 1024 * 1024 * 64; i *= 2) {
        buffer = ByteBuffer.allocateDirect(i);
        directTime = copyShortest();
       
        buffer = ByteBuffer.allocate(i);
        normalTime = copyShortest();
       
        out.println(format("%d, %d, %d", i, normalTime, directTime));
        }
    }

    private static long copyShortest() throws IOException, InterruptedException {
        int result = 0;
        for (int i = 0; i < 100; i++) {
            int single = copyOnce();
            result = (i == 0) ? single : Math.min(result, single);
        }
        return result;
    }

    private static int copyOnce() throws IOException, InterruptedException {
        initialize();

        long start = System.currentTimeMillis();

        while (source.read(buffer) != -1) {
            buffer.flip();
            target.write(buffer);
            buffer.clear(); // pos = 0, limit = capacity
        }

        long time = System.currentTimeMillis() - start;

        rest();

        return (int) time;
    }

    private static void initialize() throws UnknownHostException, IOException {
        InputStream is = new FileInputStream(new File(FILE_PATH));// 1024 MB file
        OutputStream os = new FileOutputStream(new File("/dev/null"));

        target = Channels.newChannel(os);
        source = Channels.newChannel(is);
    }

    private static void rest() throws InterruptedException {
        System.gc();
        Thread.sleep(200);
    }
}
