/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.btrace;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.TLS;

/**
 * 类MethodTimeUsage.java的实现描述：
 * 追踪运行时某个方法具体消耗的时间,用来排查性能
 * 
 * @author madding.lip May 16, 2012 9:31:22 AM
 */
@BTrace
public class MethodTimeSpendTracer {
    
    public final static String className = "com.aqnote.shared.btrace.TraceMethodTimeSpendTest";
    public final static String classMethod = "execute";

    @TLS
    static long beginTime;

    @OnMethod(clazz = className, method = classMethod)
    public static void beginTrace() {
        beginTime = BTraceUtils.timeMillis();
    }

    @OnMethod(clazz = className, method = classMethod, location = @Location(Kind.RETURN))
    public static void endTrace() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(className + "." + classMethod +": ",
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                        "ms"));
//        BTraceUtils.jstack();
    }
}
