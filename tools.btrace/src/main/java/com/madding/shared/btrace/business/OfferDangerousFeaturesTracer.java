/*
 * Copyright madding.me.
 */
package com.madding.shared.btrace.business;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.TLS;

/**
 * @author madding.lip May 16, 2012 9:31:22 AM
 */
@BTrace
public class OfferDangerousFeaturesTracer {
    
    public final static String className = "com.alibaba.bops.biz.offer.util.OfferDangerousFieldsUtil"; 
    public final static String classMethod = "setOfferDangerousFeatures";

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
//        BTraceUtils.jstack();
    }
}
