/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.btrace.business;

import static com.sun.btrace.BTraceUtils.currentThread;
import static com.sun.btrace.BTraceUtils.print;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;
import static com.sun.btrace.BTraceUtils.threadId;
import static com.sun.btrace.BTraceUtils.timeMillis;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeMethodName;
import com.sun.btrace.annotations.TLS;

@BTrace
public class LaputaTracer {

    @TLS
    public static long msgTime;
    @OnMethod(clazz="com.alibaba.radiance.site.RadianceFilter", method="doFilter", location=@Location(Kind.ENTRY))
    public static void onSendOneEnter() { // all calls to the methods with signature "()"
        msgTime=timeMillis();
    }
    @OnMethod(clazz="com.alibaba.radiance.site.RadianceFilter", method="doFilter", location=@Location(Kind.RETURN))
    public static void onSendOneExit(@ProbeMethodName String pmn) { // all calls to the methods with signature "()"
        long cost = timeMillis()-msgTime;
        print(strcat(str(threadId(currentThread())),"----"));
        print(pmn);
        println(strcat(" cost in ms: ",str(cost)));
    }
    
    
    @TLS
    public static long time;
    @OnMethod(clazz="com.alibaba.plouto.service.OfferDetailModelServiceImpl", method="getOfferDetailModel", location=@Location(Kind.ENTRY))
    public static void onSendOneEnter1() { // all calls to the methods with signature "()"
        time=timeMillis();
    }
    @OnMethod(clazz="com.alibaba.plouto.service.OfferDetailModelServiceImpl", method="getOfferDetailModel", location=@Location(Kind.RETURN))
    public static void onSendOneExit1(@ProbeMethodName String pmn) { // all calls to the methods with signature "()"
        long cost = timeMillis()-time;
        print(strcat(str(threadId(currentThread())),"----"));
        print(pmn);
        println(strcat(" cost in ms: ",str(cost)));
    }
    
}
