package com.madding.shared.components.completionservice;

import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 调用结果缓存
 * 
 * @author madding.lip
 */
public class CallReturnModelPool {

    private static Map<CallServiceModel, Object> resultPool = new ConcurrentHashMap<CallServiceModel, Object>();

    public static Map<CallServiceModel, Object> getCallReturnPool() {
        return resultPool;
    }

    public static void put(CallServiceModel model, Object result) {
        resultPool.put(model, result);
    }

    public static void remove(CallServiceModel model) {
        resultPool.remove(model);
    }
    
    public static void clean() {
        resultPool.clear();
    }
    
    public static void clean(Vector<CallServiceModel> models) {
        if(models == null) {
            return;
        }
        for(CallServiceModel model : models) {
            remove(model);
        }
    }
    
}
