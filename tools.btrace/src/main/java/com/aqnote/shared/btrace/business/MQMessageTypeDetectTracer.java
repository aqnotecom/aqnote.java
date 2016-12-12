/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.btrace.business;


import static com.sun.btrace.BTraceUtils.classOf;
import static com.sun.btrace.BTraceUtils.field;
import static com.sun.btrace.BTraceUtils.getInt;
import static com.sun.btrace.BTraceUtils.println;
import static com.sun.btrace.BTraceUtils.str;
import static com.sun.btrace.BTraceUtils.strcat;

import com.sun.btrace.AnyType;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;

@BTrace
public class MQMessageTypeDetectTracer {

    @OnMethod(clazz = "com.alibaba.china.biz.esb.impl.SimpleJmsTransport", method = "send", location = @Location(Kind.RETURN))
    public static void onSetType(AnyType[] esbs) {
            int type = getInt(field(classOf(esbs[0]),"destType",false),esbs[0]);
            println(str(type));
            if(type == 1001 || type == 1002){
                println(strcat("find message: ",str(type)));
        }
    }

}
