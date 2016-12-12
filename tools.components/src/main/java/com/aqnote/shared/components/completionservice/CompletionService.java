/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.completionservice;

import java.lang.reflect.Method;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 多并发调用服务封装
 * 
 * @author madding.lip
 */
public class CompletionService {

    private static final Logger       logger            = LoggerFactory.getLogger(CompletionService.class);

    private ExecutorService           fixedPool         = Executors.newFixedThreadPool(20);
    private java.util.concurrent.CompletionService<Object> completionService = new ExecutorCompletionService<Object>(fixedPool);

    private Vector<CallServiceModel>  models;

    private static class CallService implements Callable<Object> {

        private CallServiceModel model;

        public void setCallServiceModel(CallServiceModel model) {
            this.model = model;
        }

        @Override
        public Object call() throws Exception {
            Object classObj = SpringConfig.context.getBean(model.getServiceName());
            Class<? extends Object> clazz = classObj.getClass();
            Method method = clazz.getMethod(model.getMethodName(), model.getMethodPattern());
            Object resultObject = method.invoke(classObj, model.getMethodParam());
            CallReturnModelPool.put(model, resultObject);
            return resultObject;
        }
    }

    public void init(Vector<CallServiceModel> models) {
        this.models = models;
    }

    /** 提交所有请求--开始运行 */
    public void submitService() {
        for (CallServiceModel model : models) {
            CallService callService = new CallService();
            callService.setCallServiceModel(model);
            completionService.submit(callService);
        }
    }

    /** 确认所有请求都已经OK */
    public void waitService() {
        int correntSerivceLength = models.size();
        for (int i = 0; i < correntSerivceLength; i++) {
            try {
                Future<Object> future = completionService.take();
                if (logger.isDebugEnabled()) {
                    logger.debug("true:" + future.get() + ":future.get()");
                }
            } catch (InterruptedException e) {
                logger.error("false:_data:completionSerivce.take() error", e);
            } catch (ExecutionException e) {
                logger.error("false:_data:future.get()", e);
            }
        }
        fixedPool.shutdown();
    }

}
