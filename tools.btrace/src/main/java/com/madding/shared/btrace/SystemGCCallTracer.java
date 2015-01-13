/*
 * Copyright madding.me.
 */
package com.madding.shared.btrace;

import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

/**
 * 类TraceSystemGCCall.java的实现描述：
 * 查看显示gc调用trace类，用来判断显示调用gc引起的频繁fullgc
 * 
 * @author madding.lip May 16, 2012 9:32:50 AM
 */
@BTrace
public class SystemGCCallTracer {

    @OnMethod(
              clazz = "/.*/", 
              method = "/.*/", 
              location = @Location(value = Kind.CALL, clazz = "java.lang.System", method = "gc"))
    public static void onSystemGC() {
        jstack();
    }
}
