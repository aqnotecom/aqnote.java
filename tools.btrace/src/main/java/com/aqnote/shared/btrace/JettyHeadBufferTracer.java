/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.btrace;

import java.lang.reflect.Field;

import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Self;

/**
 * 类JettyHeadBufferTracer.java的实现描述：
 * Jetty监控request/response buffer，有项目在使用中发现出现http 413错误
 * (Request entity too large) , http://www.checkupdown.com/status/E413_cn.html
 * 初步怀疑是和buffer参数有关，原先使用jboss的参数为maxHttpHeadSize=8196，
 * 所以写了脚本提取了下线上的jetty参数，后面就修改了jetty参数为8k，解决了问题
 * 
 * @author madding.lip Jul 3, 2012 10:40:10 AM
 */
@BTrace
public class JettyHeadBufferTracer {

    @OnMethod(clazz = "org.eclipse.jetty.http.HttpBuffers", method = "/.*get.*Buffers/", location = @Location(value = Kind.ENTRY))
    public static void bufferMonitor(@Self Object self) {
        Field requestBuffersField = field("org.eclipse.jetty.http.HttpBuffers", "_requestBuffers");
        Field responseBuffersField = field("org.eclipse.jetty.http.HttpBuffers", "_responseBuffers");

        Field bufferSizeField = field("org.eclipse.jetty.io.ThreadLocalBuffers", "_bufferSize");
        Field headerSizeField = field("org.eclipse.jetty.io.ThreadLocalBuffers", "_headerSize");
        Object requestBuffers = get(requestBuffersField, self);
        int requestBufferSize = (Integer) get(bufferSizeField, requestBuffers);
        int requestHeaderSize = (Integer) get(headerSizeField, requestBuffers);
        Object responseBuffers = get(responseBuffersField, self);
        int responseBufferSize = (Integer) get(bufferSizeField, responseBuffers);
        int responseHeaderSize = (Integer) get(headerSizeField, responseBuffers);

        println(strcat(strcat(strcat("requestBufferSize : ", str(requestBufferSize)), " requestHeaderSize : "),
                       str(requestHeaderSize)));
        println(strcat(strcat(strcat("responseBufferSize : ", str(responseBufferSize)), " responseHeaderSize : "),
                       str(responseHeaderSize)));
    }
}
