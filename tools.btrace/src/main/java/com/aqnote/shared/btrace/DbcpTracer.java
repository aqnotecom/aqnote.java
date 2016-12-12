/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.btrace;

import java.lang.reflect.Field;
import java.util.Map;

import static com.sun.btrace.BTraceUtils.*;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Self;

/**
 * 类DbcpTracer.java的实现描述：
 * 项目中使用了dbcp做为数据库连接池，但对于连接池大小是否够用没有很直观的数据可以提供，所以写了个脚本提取一下数据
 * 主要的数据内容：
 * maxActive(最大连接池大小)，numActive(目前处于使用中),numIdle(处于空闲状态的连接数)
 * maxTotal(开启ps的最大值)，totalActive(目前处于使用ps的总数)，
 * keyActive(当前sql的ps使用数),keyIdle(当前sql的ps空闲数) 
 *  针对开启了ps cache
 * Java代码:
 * <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">    
 *  ....     
 *      <property name="poolPreparedStatements" value="true" />    
 *      <property name="maxOpenPreparedStatements" value="10" />    
 *  ....    
 * </bean>     
 * @author madding.lip Jul 3, 2012 10:45:32 AM
 */
@BTrace
public class DbcpTracer {  
  
    @OnMethod(clazz = "org.apache.commons.pool.impl.GenericObjectPool", method = "/.*Object/", location = @Location(value = Kind.ENTRY))  
    public static void poolMonitor(@Self Object self) {  
        Field maxActiveField = field("org.apache.commons.pool.impl.GenericObjectPool", "_maxActive");  
        Field numActiveField = field("org.apache.commons.pool.impl.GenericObjectPool", "_numActive");  
        Field poolField = field("org.apache.commons.pool.impl.GenericObjectPool", "_pool");  
        Field sizeField = field("org.apache.commons.pool.impl.CursorableLinkedList", "_size");  
        int maxActive = (Integer) get(maxActiveField, self);  
        int numActive = (Integer) get(numActiveField, self);  
        int numIdle = (Integer) get(sizeField, get(poolField, self));  
  
        println(strcat(strcat(strcat(strcat(strcat("maxActive : ", str(maxActive)), " numActive : "), str(numActive)),  
                              " numIdle : "), str(numIdle)));  
    }
  
    @SuppressWarnings("unchecked")
    @OnMethod(clazz = "org.apache.commons.pool.impl.GenericKeyedObjectPool", method = "/.*Object/", location = @Location(value = Kind.ENTRY))  
    public static void psMonitor(@Self Object self, Object key) {  
        Field maxTotalField = field("org.apache.commons.pool.impl.GenericKeyedObjectPool", "_maxTotal"); // connectio的maxActive  
        Field totalActiveField = field("org.apache.commons.pool.impl.GenericKeyedObjectPool", "_totalActive"); // connectio的active  
        Field poolMapField = field("org.apache.commons.pool.impl.GenericKeyedObjectPool", "_poolMap"); // connectio的active  
  
        Field keyActiveField = field("org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue", "activeCount"); // key的active  
        Field keyIdleField = field("org.apache.commons.pool.impl.GenericKeyedObjectPool$ObjectQueue", "queue"); // key的idle  
        Field keyIdleSizeField = field("org.apache.commons.pool.impl.CursorableLinkedList", "_size");  
  
        Field sqlField = field("org.apache.commons.dbcp.PoolingConnection$PStmtKey", "_sql");  
  
        int maxTotal = (Integer) get(maxTotalField, self);  
        int totalActive = (Integer) get(totalActiveField, self);  
        Map<Object, Object> poolMap = (Map<Object, Object>) get(poolMapField, self);  
        int keyActive = 0, keyIdle = 0;  
        if (poolMap != null) {  
            Object queue = get(poolMap, key);  
            if (queue != null) { // ObjectQueue  
                keyActive = (Integer) get(keyActiveField, queue);  
                keyIdle = (Integer) get(keyIdleSizeField, get(keyIdleField, queue));  
            }  
        }  
        println(strcat(strcat(strcat(strcat(strcat(strcat(strcat("maxTotal : ", str(maxTotal)), " totalActive : "),  
                                                   str(totalActive)), " keyActive : "), str(keyActive)), " keyIdle "),  
                       str(keyIdle)));  
  
        println(strcat("Ps Key: ", str(get(sqlField, key))));  
    }
  
}  