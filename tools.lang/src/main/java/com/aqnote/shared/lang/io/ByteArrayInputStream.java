/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.lang.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类ByteArrayInputStream.java的实现描述： 非同步的<code>ByteArrayInputStream</code>替换方案, 本代码移植自IBM developer works精彩文章,
 * 参见文档.
 * 
 * @author madding.lip May 7, 2012 4:47:32 PM
 */
public class ByteArrayInputStream extends InputStream {

    private byte[]  buffer;
    private int     index;
    private int     limit;
    private int     mark;

    // is the stream closed?
    private boolean closed;

    public ByteArrayInputStream(byte[] data){
        this(data, 0, data.length);
    }

    public ByteArrayInputStream(byte[] data, int offset, int length){
        if (data == null) {
            throw new NullPointerException();
        } else if ((offset < 0) || ((offset + length) > data.length) || (length < 0)) {
            throw new IndexOutOfBoundsException();
        } else {
            buffer = data;
            index = offset;
            limit = offset + length;
            mark = offset;
        }
    }

    public int read() throws IOException {
        if (closed) {
            throw new IOException("Stream closed");
        } else if (index >= limit) {
            return -1; // EOF
        } else {
            return buffer[index++] & 0xff;
        }
    }

    public int read(byte[] data, int offset, int length) throws IOException {
        if (data == null) {
            throw new NullPointerException();
        } else if ((offset < 0) || ((offset + length) > data.length) || (length < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (closed) {
            throw new IOException("Stream closed");
        } else if (index >= limit) {
            return -1; // EOF
        } else {
            // restrict length to available data
            if (length > (limit - index)) {
                length = limit - index;
            }

            // copy out the subarray
            System.arraycopy(buffer, index, data, offset, length);
            index += length;
            return length;
        }
    }

    public long skip(long amount) throws IOException {
        if (closed) {
            throw new IOException("Stream closed");
        } else if (amount <= 0) {
            return 0;
        } else {
            // restrict amount to available data
            if (amount > (limit - index)) {
                amount = limit - index;
            }

            index += (int) amount;
            return amount;
        }
    }

    public int available() throws IOException {
        if (closed) {
            throw new IOException("Stream closed");
        } else {
            return limit - index;
        }
    }

    public void close() {
        closed = true;
    }

    public void mark(int readLimit) {
        mark = index;
    }

    public void reset() throws IOException {
        if (closed) {
            throw new IOException("Stream closed");
        } else {
            // reset index
            index = mark;
        }
    }

    public boolean markSupported() {
        return true;
    }
}
