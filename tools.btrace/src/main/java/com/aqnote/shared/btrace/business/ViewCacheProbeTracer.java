/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.btrace.business;

import java.util.Map;

import com.sun.btrace.BTraceUtils;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnEvent;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.OnTimer;
import com.sun.btrace.annotations.Property;

/**
 * @author madding.lip
 */
@BTrace
public class ViewCacheProbeTracer {

    @Property
    private static Map<String, String> map = BTraceUtils.newHashMap();

    @OnMethod(
        clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", 
        method = "/getAllPostAttributes|getAllPostLocators|getCategoryTree|getFullOfferNameList|getFullPostNameList|getOfferCategoryTree|getPostCategoryTree|getSubPostCategory|retrieveCategory|retrievePostCategory/"
    )
    public static void visitPostCategory() {
        if (!BTraceUtils.containsKey(map, "postCategory")) {
            BTraceUtils.put(map, "postCategory", "yes");
        }
    }

    @OnMethod(
              clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", 
              method = "/getAreaMap|getAreaTree/"
                  )
    public static void visitArea() {
        if (!BTraceUtils.containsKey(map, "area")) {
            BTraceUtils.put(map, "area", "yes");
        }
    }

    @OnMethod(clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", method = "getCategoryIndustryMap")
    public static void visitCategoryIndustry() {
        if (!BTraceUtils.containsKey(map, "categoryIndustry")) {
            BTraceUtils.put(map, "categoryIndustry", "yes");
        }
    }

    @OnMethod(
        clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", 
        method = "/getCommodityUnitMap|retrieveCommodityUnit/"
    )
    public static void visitCommodityUnit() {
        if (!BTraceUtils.containsKey(map, "commodityUnit")) {
            BTraceUtils.put(map, "commodityUnit", "yes");
        }
    }
    
    @OnMethod(
        clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", 
        method = "/getCompanyCategoryTree|getFullCompanyNameList|getSubCompanyCategory|retrieveCompanyCategory/"
    )
    public static void visitCompanyCategory() {
        if (!BTraceUtils.containsKey(map, "companyCategory")) {
            BTraceUtils.put(map, "companyCategory", "yes");
        }
    }
    
    @OnMethod(
        clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", 
        method = "/getDisplayCategoryTree|getSubDisplayCategory|retrieveDisplayCategory/"
    )
    public static void visitDisplayCategory() {
        if (!BTraceUtils.containsKey(map, "displayCategory")) {
            BTraceUtils.put(map, "displayCategory", "yes");
        }
    }
    
    @OnMethod(clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", method = "getFeatureMap")
    public static void visitFeature() {
        if (!BTraceUtils.containsKey(map, "feature")) {
            BTraceUtils.put(map, "feature", "yes");
        }
    }
    
    @OnMethod(clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", method = "getFeatureValueMap")
    public static void visitFeatureValue() {
        if (!BTraceUtils.containsKey(map, "featureValue")) {
            BTraceUtils.put(map, "featureValue", "yes");
        }
    }
    
    @OnMethod(clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", method = "getNewsCategoryTree")
    public static void visitNewsCategory() {
        if (!BTraceUtils.containsKey(map, "newsCategory")) {
            BTraceUtils.put(map, "newsCategory", "yes");
        }
    }
    
    @OnMethod(clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", method = "getReourcesMap")
    public static void visitResources() {
        if (!BTraceUtils.containsKey(map, "resources")) {
            BTraceUtils.put(map, "resources", "yes");
        }
    }
    
    @OnMethod(
        clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", 
        method = "/getSqForumFieldList|getSqForumFieldMap/"
    )
    public static void visitSqForumFiled() {
        if (!BTraceUtils.containsKey(map, "sqForumField")) {
            BTraceUtils.put(map, "sqForumField", "yes");
        }
    }
    
    @OnMethod(clazz = "com.alibaba.china.biz.viewcache.model.ViewCache", method = "getSqForumMap")
    public static void visitSqForum() {
        if (!BTraceUtils.containsKey(map, "sqForum")) {
            BTraceUtils.put(map, "sqForum", "yes");
        }
    }
    
    @OnEvent
    public static void onEvent() {
        BTraceUtils.println("BTrace probe will print the used original data:");
        BTraceUtils.printMap(map);
        BTraceUtils.println("BTrace program exits!");
    } 
    
    @OnTimer(1000*60*5)
    public static void ontimer() {
        BTraceUtils.println("--------------------------------------:");
        BTraceUtils.printMap(map);
    }
    
}
