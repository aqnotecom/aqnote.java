/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.btrace.business;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.TLS;

/**
 * 追踪radiance次数
 * 
 * @author madding.lip
 */
@BTrace
public class RadianceTracer {
    
    //############################################################################################
    public final static String className = "com.alibaba.radiance.core.app.spi.DefaultAppContainer"; 
    public final static String classMethod = "renderApp";
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
    }

    //############################################################################################
    public final static String className_0 = "com.alibaba.radiance.site.spi.SitePageRestSPI"; 
    public final static String classMethod_0 = "renderApp";
    @TLS
    static long beginTime_0;

    @OnMethod(clazz = className_0, method = classMethod_0)
    public static void beginTrace0() {
        beginTime_0 = BTraceUtils.timeMillis();
    }

    @OnMethod(clazz = className_0, method = classMethod_0, location = @Location(Kind.RETURN))
    public static void endTrace0() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(className_0 + "." + classMethod_0 +": ",
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime_0)),
                        "ms"));
    }

    //############################################################################################    
    public final static String className_1 = "com.alibaba.radiance.site.spi.SitePageRestSPI"; 
    public final static String classMethod_1 = "buildPage";
    @TLS
    static long beginTime_1;

    @OnMethod(clazz = className_1, method = classMethod_1)
    public static void beginTrace1() {
        beginTime_1 = BTraceUtils.timeMillis();
    }

    @OnMethod(clazz = className_1, method = classMethod_1, location = @Location(Kind.RETURN))
    public static void endTrace1() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(className_1 + "." + classMethod_1 +": ",
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime_1)),
                        "ms"));
    }

    //############################################################################################    
    public final static String className_2 = "com.alibaba.radiance.site.spi.SitePageRestSPI"; 
    public final static String classMethod_2 = "renderSegments";
    @TLS
    static long beginTime_2;

    @OnMethod(clazz = className_2, method = classMethod_2)
    public static void beginTrace2() {
        beginTime_2 = BTraceUtils.timeMillis();
    }

    @OnMethod(clazz = className_2, method = classMethod_2, location = @Location(Kind.RETURN))
    public static void endTrace2() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(className_2 + "." + classMethod_2 +": ",
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime_2)),
                        "ms"));
    }

    
    //############################################################################################
    public final static String className_3 = "com.alibaba.radiance.site.RadianceFilter"; 
    public final static String classMethod_3 = "doFilter";
    @TLS
    static long beginTime_3;

    @OnMethod(clazz = className_3, method = classMethod_3)
    public static void beginTrace3() {
        beginTime_3 = BTraceUtils.timeMillis();
    }

    @OnMethod(clazz = className_3, method = classMethod_3, location = @Location(Kind.RETURN))
    public static void endTrace3() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(className_3 + "." + classMethod_3 +": ",
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime_3)),
                        "ms"));
    }
    
    
    //############################################################################################    
    public final static String className_4 = "com.alibaba.radiance.site.spi.SiteAppRestSPI"; 
    public final static String classMethod_4 = "view";
    @TLS
    static long beginTime_4;

    @OnMethod(clazz = className_4, method = classMethod_4)
    public static void beginTrace4() {
        beginTime_4 = BTraceUtils.timeMillis();
    }

    @OnMethod(clazz = className_4, method = classMethod_4, location = @Location(Kind.RETURN))
    public static void endTrace4() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(className_4 + "." + classMethod_4 +": ",
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime_4)),
                        "ms"));
    }
    
    
    //############################################################################################    
    public final static String className_5 = "com.alibaba.radiance.site.spi.SitePageRestSPI"; 
    public final static String classMethod_5 = "view";
    @TLS
    static long beginTime_5;

    @OnMethod(clazz = className_5, method = classMethod_5)
    public static void beginTrace5() {
        beginTime_5 = BTraceUtils.timeMillis();
    }

    @OnMethod(clazz = className_5, method = classMethod_5, location = @Location(Kind.RETURN))
    public static void endTrace5() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(className_5 + "." + classMethod_5 +": ",
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime_5)),
                        "ms"));
    }

    
    //############################################################################################    
    public final static String className_6 = "com.alibaba.radiance.render.impl.VelocityRenderImpl"; 
    public final static String classMethod_6 = "render";
    @TLS
    static long beginTime_6;

    @OnMethod(clazz = className_6, method = classMethod_6)
    public static void beginTrace6() {
        beginTime_6 = BTraceUtils.timeMillis();
    }

    @OnMethod(clazz = className_6, method = classMethod_6, location = @Location(Kind.RETURN))
    public static void endTrace6() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(className_6 + "." + classMethod_6 +": ",
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime_6)),
                        "ms"));
    }

    
    //############################################################################################    
    public final static String className_7 = "org.apache.velocity.Template"; 
    public final static String classMethod_7 = "merge";
    @TLS
    static long beginTime_7;

    @OnMethod(clazz = className_7, method = classMethod_7)
    public static void beginTrace7() {
        beginTime_7 = BTraceUtils.timeMillis();
    }

    @OnMethod(clazz = className_7, method = classMethod_7, location = @Location(Kind.RETURN))
    public static void endTrace7() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(className_7 + "." + classMethod_7 +": ",
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime_7)),
                        "ms"));
    }
}