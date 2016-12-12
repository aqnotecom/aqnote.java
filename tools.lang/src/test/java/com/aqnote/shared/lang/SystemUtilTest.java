/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.lang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.aqnote.shared.lang.StringEscapeUtil;
import com.aqnote.shared.lang.StringUtil;
import com.aqnote.shared.lang.SystemUtil;

/**
 * SystemUtil测试类
 * 
 * @author madding.lip
 */
public class SystemUtilTest {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static void main(String[] args) {
        SystemUtil.dumpSystemInfo();

        List list = new ArrayList(System.getProperties().keySet());

        Collections.sort(list);

        for (Iterator i = list.iterator(); i.hasNext();) {
            String key   = (String) i.next();
            String value = System.getProperty(key);

            System.out.println(key + " = "
                + StringUtil.defaultIfNull(StringEscapeUtil.escapeJava(value), "[n/a]"));
        }
    }
}
