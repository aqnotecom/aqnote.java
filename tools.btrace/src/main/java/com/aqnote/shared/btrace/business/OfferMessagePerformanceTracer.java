/*
 * Copyright 2008-2010 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */
package com.aqnote.shared.btrace.business;

import com.sun.btrace.annotations.*;
import com.sun.btrace.*;
import static com.sun.btrace.BTraceUtils.*;

/**
 * This script demonstrates the possibility to intercept
 * method calls that are abou to be executed from the body of
 * a certain method. This is achieved by using the {@linkplain Kind#CALL}
 * location value.
 */
@BTrace public class OfferMessagePerformanceTracer {
   
    @TLS
    public static long msgTime;
    @OnMethod(clazz="com.alibaba.exodus2.biz.offer.bo.OfferMessageBO", method="/.*/", location=@Location(Kind.ENTRY))
    public static void onSendOneEnter() { // all calls to the methods with signature "()"
        msgTime=timeMillis();
    }
    @OnMethod(clazz="com.alibaba.exodus2.biz.offer.bo.OfferMessageBO", method="/.*/", location=@Location(Kind.RETURN))
    public static void onSendOneExit(@ProbeMethodName String pmn) { // all calls to the methods with signature "()"
        long cost = timeMillis()-msgTime;
        print(strcat(str(threadId(currentThread())),"----"));
        print(pmn);
        println(strcat(" cost in ms: ",str(cost)));
    }
 

    @TLS public static long jmsTime;
    @OnMethod(clazz="com.alibaba.napoli.client.async.impl.DefaultAsyncSender", method="send", location=@Location(Kind.ENTRY))
    public static void onSendJmsEnter() { // all calls to the methods with signature "()"
        jmsTime=timeMillis();
    }
    @OnMethod(clazz="com.alibaba.napoli.client.async.impl.DefaultAsyncSender", method="send", location=@Location(Kind.RETURN))
    public static void onSendJmsExit() { // all calls to the methods with signature "()"
        long cost = timeMillis()-jmsTime;
        print(strcat(str(threadId(currentThread())),"----"));
        println(strcat("napoli sendObject cost in ms: ",str(cost)));
    }
    
    @TLS
    public static long allTime;
    @OnMethod(clazz="com.alibaba.exodus2.biz.offer.manager.impl.DefaultOfferControlManager", method="repostOffer", location=@Location(Kind.ENTRY))
    public static void onCreateOfferEnter() { // all calls to the methods with signature "()"
        allTime=timeMillis();
    }
    @OnMethod(clazz="com.alibaba.exodus2.biz.offer.manager.impl.DefaultOfferControlManager", method="repostOffer", location=@Location(Kind.RETURN))
    public static void onSendExit(@ProbeMethodName String pmn,AnyType[] args) { // all calls to the methods with signature "()"
        long cost = timeMillis()-allTime;
        //println("\n------------------------------------------------------------------------------");
        print(strcat(str(threadId(currentThread())),"----"));
        print(strcat(pmn,str(args[0])));
        println(strcat(" cost all in ms: ",str(cost)));
        println("------------------------------------------------------------------------------");
    }
}
