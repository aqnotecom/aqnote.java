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

import com.sun.btrace.*;
import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

/**
 * This script demonstrates the possibility to intercept
 * method calls that are abou to be executed from the body of
 * a certain method. This is achieved by using the {@linkplain Kind#CALL}
 * location value.
 */
@BTrace public class MQPerformaceTracer {
    @TLS 
    public static long allTime;
    
    @TLS
    public static long jmsTime;
    
    @OnMethod(clazz="com.alibaba.napoli.client.inner.messagesender.impl.MessageSenderImpl", method="send", location=@Location(Kind.ENTRY))
    public static void onSendEnter(@Self Object self) { // all calls to the methods with signature "()"
        allTime=timeMillis();
    }
    @OnMethod(clazz="com.alibaba.napoli.client.inner.messagesender.impl.MessageSenderImpl", method="send", location=@Location(Kind.RETURN))
    public static void onSendExit(@Self Object self,AnyType[] args) { // all calls to the methods with signature "()"
        long cost = timeMillis()-allTime;
    	Object queue = get(field(classOf(self),"queueName"),self);
    	if(cost>50){
    		println(str(queue));
    		println(strcat("MessageSenderImpl send cost in ms: ",str(cost)));
    	//	printFields(self);
    	//	printArray(args);
    	}
    }
    
    @OnMethod(clazz="org.apache.activemq.transport.ResponseCorrelator", method="request", location=@Location(Kind.ENTRY))
    public static void onSendJmsEnter() { // all calls to the methods with signature "()"
        jmsTime=timeMillis();
    }
    @OnMethod(clazz="org.apache.activemq.transport.ResponseCorrelator", method="request", location=@Location(Kind.RETURN))
    public static void onSendJmsExit() { // all calls to the methods with signature "()"
        long cost = timeMillis()-jmsTime;
    	if(cost>50){
           		 println(strcat("		JMS sendObject cost in ms: ",str(cost)));
    	}
    }

}
