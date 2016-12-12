/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.completionservice;

/**
 * CallServiceModel类描述：调用模型
 * 
 * @author madding.lip
 */
public class CallServiceModel {

    private String   serviceName;
    private String   methodName;
    private Object[] methodParam;

    public CallServiceModel(String serviceName, String methodName, Object... methodParam){
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.methodParam = methodParam;
    }

    public Class<?>[] getMethodPattern() {
        Class<?>[] clazz = new Class[methodParam.length];
        for (int i = 0; i < methodParam.length; i++) {
            clazz[i] = methodParam[i].getClass();
        }
        return clazz;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getMethodParam() {
        return methodParam;
    }

    public void setMethodParam(Object[] methodParam) {
        this.methodParam = methodParam;
    }
}
