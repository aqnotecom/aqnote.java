/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.resultcode.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqnote.shared.lang.ClassLoaderUtil;
import com.aqnote.shared.lang.StringUtil;

/**
 * 创建<code>ResourceBundle</code>的实例的工厂.
 */
public class PropertiesFactory {

    private static final Logger logger         = LoggerFactory.getLogger(PropertiesFactory.class);

    private static final String FILE_TYPE      = ".properties";
    private static final String SEPARATOR_SIGN = "_";

    public static Properties getProperties(String enumClassName, Locale locale) {

        String filename = getFilename(enumClassName, locale);
        System.out.println(filename);
        ClassLoader classLoader = ClassLoaderUtil.getClassLoader();
        InputStream is = classLoader.getResourceAsStream(filename);
        if (is == null) {
            logger.error("false:load file error1:" + classLoader.getClass().getName() + ":" + filename);
            return null;
        }
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            logger.error("false:load file error2:" + classLoader.getClass().getName() + ":" + filename, e);
            return null;
        }
        return props;
    }

    protected static String getFilename(String enumClassName, Locale locale) {

        if (StringUtil.isBlank(enumClassName) || locale == null) {
            return null;
        }
        String path = enumClassName.replace('.', '/');
        return path + SEPARATOR_SIGN + locale.toString() + FILE_TYPE;
    }
}
