/*
 * Copyright (C) 2013-2016 Peng Li<aqnote@qq.com>.
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.lang.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 类OutputEngine.java的实现描述： 增量地将数据源写入到指定输出流的引擎, 本代码移植自IBM developer works精彩文章, 参见文档.
 * 
 * @author "Peng Li"<aqnote@qq.com> May 7, 2012 4:54:12 PM
 */
public interface OutputEngine {

    /** 默认的输出流工厂, 直接返回指定的输出流. */
    OutputStreamFactory DEFAULT_OUTPUT_STREAM_FACTORY = new OutputStreamFactory() {
      public OutputStream getOutputStream(OutputStream out) {
          return out;
      }};

    /**
     * 初始化输出引擎, 通常<code>OutputEngine</code>的实现会将一个<code>FilterOutputStream</code>连接到指定的输出流中.
     * 
     * @param out 输出到指定的输出流
     * @throws IOException 输入输出异常
     */
    void open(OutputStream out) throws IOException;

    /**
     * 执行一次输出引擎. 此操作在<code>OutputEngine</code>的生命期中会被执行多次, 每次都将少量数据写入到初始化时指定的输出流.
     * 
     * @throws IOException 输入输出异常
     */
    void execute() throws IOException;

    /**
     * 扫尾工作. 当所有的输出都完成以后, 此方法被调用.
     * 
     * @throws IOException 输入输出异常
     */
    void close() throws IOException;

    /**
     * 创建输出流的工厂.
     */
    interface OutputStreamFactory {

        /**
         * 创建输出流, 通常返回一个<code>FilterOutputStream</code>连接到指定的输出流中.
         * 
         * @param out 输出到指定的输出流
         * @return 输出流
         * @throws IOException 输入输出异常
         */
        OutputStream getOutputStream(OutputStream out) throws IOException;
    }
}
