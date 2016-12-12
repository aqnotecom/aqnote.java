/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.btrace.business;

import java.io.UnsupportedEncodingException;

//import com.alibaba.china.fasttext.decorator.DecoratorText;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.Return;
import com.sun.btrace.annotations.Self;
import com.sun.btrace.annotations.TLS;


/**
 * @author madding.lip May 16, 2012 9:31:22 AM
 */
@SuppressWarnings("unused")
@BTrace
public class DecoratorText_HighlightText_Param_ReturnTracer {
    
    public final static String className = "com.alibaba.china.fasttext.decorator.DecoratorText"; 
    public final static String classMethod = "highlightText";

    @TLS
    static long beginTime;

//    @OnMethod(clazz = className, method = classMethod, location= @Location(Kind.RETURN))
//    public static void onHighlightText(@Self DecoratorText text, String _context, boolean  _ignoreCase, @Return String hightlightDetails) throws UnsupportedEncodingException {
//        BTraceUtils.println(new String(_context.getBytes(),"UTF-8"));
//        BTraceUtils.println(hightlightDetails);  
//        BTraceUtils.println("=====================================");
//    }
}
