/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.btrace.business;

import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;
import java.util.concurrent.atomic.AtomicInteger;
import  java.util.concurrent.atomic.AtomicLong;

/**
 * This script demonstrates the possibility to intercept
 * method calls that are abou to be executed from the body of
 * a certain method. This is achieved by using the {@linkplain Kind#CALL}
 * location value.
 */
@BTrace 
public class MQAVGTracer {
    
    @Property
    private static AtomicInteger  count= newAtomicInteger(0);

   @Property
    private static AtomicLong  time = newAtomicLong(0);

    @TLS
    public static long allTime;
    
    @OnMethod(clazz="com.alibaba.napoli.client.inner.messagesender.impl.MessageSenderImpl", method="send", location=@Location(Kind.ENTRY))
    public static void onSendEnter(@Self Object self) { // all calls to the methods with signature "()"
        allTime=timeMillis();
    }
    @OnMethod(clazz="com.alibaba.napoli.client.inner.messagesender.impl.MessageSenderImpl", method="send", location=@Location(Kind.RETURN))
    public static void onSendExit(@Self Object self , @Return boolean success) { // all calls to the methods with signature "()"
        incrementAndGet(count);
        long cost = timeMillis()-allTime;
        println(str( success));
        addAndGet(time,cost);
        if(cost>50){
            println(strcat( strcat(str(cost),"ms to  send ,result:" ), str( success) ));
            //printFields(self);
        }
     }
        
     @OnTimer(4000)
    public static void print() {
    	if(get(count)!=0){ 
    	    println(strcat("count:",str(get(count))));
    	    println(strcat("avg:",str(get(time)/get(count))));
    	}
    }
 
     @TLS
     public static long jmsTime;
    
    @OnMethod(clazz="org.apache.activemq.transport.ResponseCorrelator", method="request", location=@Location(Kind.ENTRY))
    public static void onSendJmsEnter() { // all calls to the methods with signature "()"
        jmsTime=timeMillis();
    }
    @OnMethod(clazz="org.apache.activemq.transport.ResponseCorrelator", method="request", location=@Location(Kind.RETURN))
    public static void onSendJmsExit() { // all calls to the methods with signature "()"
        long cost = timeMillis()-jmsTime;
        if(cost>50){
             println(strcat("               JMS sendObject cost in ms: ",str(cost)));
	         jstack(10);        
        }
    }
 }
