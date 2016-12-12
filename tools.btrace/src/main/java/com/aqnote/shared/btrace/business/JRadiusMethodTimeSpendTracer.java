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
 * 类MethodTimeUsage.java的实现描述： 追踪运行时某个方法具体消耗的时间
 * 
 * @author madding.lip May 16, 2012 9:31:22 AM
 */
@BTrace
public class JRadiusMethodTimeSpendTracer {
    
    public final static String cn_frp = "net.jradius.freeradius.FreeRadiusProcessor";
    public final static String cm_pr = "processRequest";
    
    @TLS
    static long frp_pr_time;
    
    @OnMethod(clazz = cn_frp, method = cm_pr)
    public static void beginTrace_frp_pr() {
        frp_pr_time = BTraceUtils.timeMillis();
    }

    @OnMethod(clazz = cn_frp, method = cm_pr, location = @Location(Kind.RETURN))
    public static void endTrace_frp_pr() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(
          BTraceUtils.strcat(cn_frp , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_pr ,": "))),
                    BTraceUtils.str(BTraceUtils.timeMillis() - frp_pr_time)),
                        "ms"));
    }
    
    
    public final static String cn_handle     = "com.alibaba.device.app.jradius.handler.NewNACHandler";
    public final static String cm_handle     = "handle";
    public final static String cm_processeap = "processEAP";
    public final static String cm_processtls = "processEAP_TLS";
    public final static String cm_processpeap = "genetateMsChapv2AuthResult";
    public final static String cm_processpeap_auth = "processMsChapv2Auth";
    

    public final static String cn_httpclientutil     = "com.alibaba.device.app.jradius.utils.HttpClientUtil";
    public final static String cm_postasutf8     = "postAsUTF8";
    
    public final static String cn_httpclient     = "org.apache.http.impl.client.CloseableHttpClient";
    public final static String cm_execute     = "execute";

    @TLS
    static long beginTime;

    /******************handle*******************/
    @OnMethod(clazz = cn_handle, method = cm_handle)
    public static void beginTrace() {
        beginTime = BTraceUtils.timeMillis();
    }

    /******************eap*******************/
    @OnMethod(clazz = cn_handle, method = cm_processeap)
    public static void beginTrace_eap() {
        BTraceUtils.println(
                            BTraceUtils.strcat(
                                BTraceUtils.strcat(
                          BTraceUtils.strcat(BTraceUtils.strcat("in ", cn_handle) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_processeap ,": "))),
                                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                                        "ms"));
    }
    
    /******************tls*******************/
    @OnMethod(clazz = cn_handle, method = cm_processtls)
    public static void beginTrace_tls() {
        BTraceUtils.println(
                            BTraceUtils.strcat(
                                BTraceUtils.strcat(
                          BTraceUtils.strcat(BTraceUtils.strcat("in ", cn_handle) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_processtls ,": "))),
                                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                                        "ms"));
    }
    
    @OnMethod(clazz = cn_handle, method = cm_processtls, location = @Location(Kind.RETURN))
    public static void endTrace_tls() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(
          BTraceUtils.strcat(BTraceUtils.strcat("out ", cn_handle) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_processtls ,": "))),
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                        "ms"));
    }
    
    /******************peap*******************/
    @OnMethod(clazz = cn_handle, method = cm_processpeap)
    public static void beginTrace_peap() {
        BTraceUtils.println(
                            BTraceUtils.strcat(
                                BTraceUtils.strcat(
                          BTraceUtils.strcat(BTraceUtils.strcat("in ", cn_handle) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_processpeap ,": "))),
                                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                                        "ms"));
    }
    
    /******************peap auth*******************/
    @OnMethod(clazz = cn_handle, method = cm_processpeap_auth)
    public static void beginTrace_peap_auth() {
        BTraceUtils.println(
                            BTraceUtils.strcat(
                                BTraceUtils.strcat(
                          BTraceUtils.strcat(BTraceUtils.strcat("in ", cn_handle) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_processpeap_auth ,": "))),
                                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                                        "ms"));
    }
    
    /******************httpclient*******************/
    @OnMethod(clazz = cn_httpclientutil, method = cm_postasutf8)
    public static void beginTrace_httpclientutil() {
        BTraceUtils.println(
                            BTraceUtils.strcat(
                                BTraceUtils.strcat(
                          BTraceUtils.strcat(BTraceUtils.strcat("in ", cn_httpclientutil) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_postasutf8 ,": "))),
                                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                                        "ms"));
    }
    
    @OnMethod(clazz = cn_httpclient, method = cm_execute)
    public static void beginTrace_httpclient() {
        BTraceUtils.println(
                            BTraceUtils.strcat(
                                BTraceUtils.strcat(
                          BTraceUtils.strcat(BTraceUtils.strcat("in ", cn_httpclient) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_execute ,": "))),
                                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                                        "ms"));
    }
    @OnMethod(clazz = cn_httpclient, method = cm_execute, location = @Location(Kind.RETURN))
    public static void endTrace_httpclient() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(
          BTraceUtils.strcat(BTraceUtils.strcat("out ", cn_httpclient) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_execute, ": "))),
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                        "ms"));
    }
    
    @OnMethod(clazz = cn_httpclientutil, method = cm_postasutf8, location = @Location(Kind.RETURN))
    public static void endTrace_httpclientutil() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(
          BTraceUtils.strcat(BTraceUtils.strcat("out ", cn_httpclientutil) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_postasutf8, ": "))),
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                        "ms"));
    }
    
    @OnMethod(clazz = cn_handle, method = cm_processpeap_auth, location = @Location(Kind.RETURN))
    public static void endTrace_peap_auth() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(
          BTraceUtils.strcat(BTraceUtils.strcat("out ", cn_handle) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_processpeap_auth, ": "))),
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                        "ms"));
    }
    
    
    @OnMethod(clazz = cn_handle, method = cm_processpeap, location = @Location(Kind.RETURN))
    public static void endTrace_peap() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(
          BTraceUtils.strcat(BTraceUtils.strcat("out ", cn_handle) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_processpeap ,": "))),
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                        "ms"));
    }
    ////////////////////////////////////////////////
    @OnMethod(clazz = cn_handle, method = cm_processeap, location = @Location(Kind.RETURN))
    public static void endTrace_eap() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(
          BTraceUtils.strcat(BTraceUtils.strcat("out ", cn_handle) , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_processeap ,": "))),
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                        "ms"));
    }

    @OnMethod(clazz = cn_handle, method = cm_handle, location = @Location(Kind.RETURN))
    public static void endTrace() {
        BTraceUtils.println(
            BTraceUtils.strcat(
                BTraceUtils.strcat(
          BTraceUtils.strcat(cn_handle , BTraceUtils.strcat(".", BTraceUtils.strcat(cm_handle ,": "))),
                    BTraceUtils.str(BTraceUtils.timeMillis() - beginTime)),
                        "ms"));
    }
}
