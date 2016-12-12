/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.lang.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 类InputStreamOutputEngine.java的实现描述： 将数据从任意<code>InputStream</code>复制到<code>FilterOutputStream</code>的输出引擎. 本代码移植自IBM
 * developer works精彩文章, 参见文档.
 * 
 * @author madding.lip May 7, 2012 4:56:33 PM
 */
public class InputStreamOutputEngine implements OutputEngine {

    private static final int    DEFAULT_BUFFER_SIZE = 8192;
    private InputStream         in;
    private OutputStreamFactory factory;
    private byte[]              buffer;
    private OutputStream        out;

    public InputStreamOutputEngine(InputStream in, OutputStreamFactory factory){
        this(in, factory, DEFAULT_BUFFER_SIZE);
    }

    public InputStreamOutputEngine(InputStream in, OutputStreamFactory factory, int bufferSize){
        this.in = in;
        this.factory = (factory == null) ? DEFAULT_OUTPUT_STREAM_FACTORY : factory;
        buffer = new byte[bufferSize];
    }

    public void open(OutputStream out) throws IOException {
        if (this.out != null) {
            throw new IOException("Already initialized");
        } else {
            this.out = factory.getOutputStream(out);
        }
    }

    public void execute() throws IOException {
        if (out == null) {
            throw new IOException("Not yet initialized");
        } else {
            int amount = in.read(buffer);

            if (amount < 0) {
                out.close();
            } else {
                out.write(buffer, 0, amount);
            }
        }
    }

    public void close() throws IOException {
        in.close();
    }
}
