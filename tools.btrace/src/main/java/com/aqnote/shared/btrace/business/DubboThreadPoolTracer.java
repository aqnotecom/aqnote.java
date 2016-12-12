/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.btrace.business;

import static com.sun.btrace.BTraceUtils.field;
import static com.sun.btrace.BTraceUtils.get;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;

import java.lang.reflect.Field;

import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Self;

/**
 * 类DubboThreadPoolTracer.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Aug 6, 2012 9:33:26 AM
 */
@BTrace
public class DubboThreadPoolTracer {

    private static final String A_M_C_CLASS = "com.alibaba.dubbo.config.AbstractMethodConfig";
//    private static final String P_C_CLASS   = "com.alibaba.dubbo.config.ProviderConfig";

    @OnMethod(clazz = A_M_C_CLASS, method = "/.*Object/", location = @Location(value = Kind.ENTRY))
    public static void amcMonitor(@Self Object self) {
        Field activeField = field(A_M_C_CLASS, "actives");
        Field retriesField = field(A_M_C_CLASS, "retries");
        int active = (Integer) get(activeField, self);
        int retries = (Integer) get(retriesField, self);

        println(strcat(strcat(strcat("active : ", str(active)), " retries : "), str(retries)));
    }
    
    

}
