/*
 * Copyright (C) 2013-2016 Peng Li<aqnote@qq.com>.
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.resultcode;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.aqnote.shared.lang.collections.SoftConcurrentHashMap;

/**
 * 类ResultCodeCache.java的实现描述：resultCode的信息缓存，保存解析后的properties内容和locale的对应关系
 * 
 * @author "Peng Li"<aqnote@qq.com> Sep 18, 2013 10:44:56 AM
 */
@SuppressWarnings("unchecked")
public class ResultCodeCache {

    private static transient Map<String, Properties> cache;

    static {
        if (cache == null) {
            cache = new SoftConcurrentHashMap();
        }
    }

    public static Properties get(String classLocale) {
        return cache.get(classLocale);
    }

    public static Properties put(String classLocale, Properties props) {
        return cache.put(classLocale, props);
    }

    public static Properties remove(Locale locale) {
        return cache.remove(locale);
    }

    public static String dump() {
        Set<String> classLocaleSet = cache.keySet();
        if (classLocaleSet == null || classLocaleSet.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder("dump[");
        for (String classLocale : classLocaleSet) {
            sb.append(classLocale).append(":");
            sb.append(cache.get(classLocale)).append(";");
        }
        sb.append("]");
        return sb.toString();
    }

}
