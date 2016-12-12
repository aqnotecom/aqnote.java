/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.resultcode;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;

import com.aqnote.shared.components.resultcode.factory.PropertiesFactory;
import com.aqnote.shared.lang.MessageUtil;

/**
 * resultCode内部功能封装类
 * 
 * @author madding.lip
 */
public class ResultCodeComp {

    private final IResultCode resultCode;
    private final Locale      locale;

    public ResultCodeComp(IResultCode resultCode, Locale locale){
        if (resultCode == null) {
            throw new IllegalArgumentException("ResultCode should not be null: " + getClass().getName());
        }

        if (!resultCode.getClass().isEnum() || !(resultCode instanceof IResultCode)) {
            throw new IllegalArgumentException("ResultCode should be java.lang.Enum: " + getClass().getName());
        }

        this.resultCode = resultCode;
        this.locale = locale;
    }

    public ResultCodeComp(IResultCode resultCode){
        this(resultCode, null);
    }

    public String getName() {
        return Enum.class.cast(resultCode).name();
    }

    public String getMessage() {
        return getMessage(null);
    }

    public String getMessage(Object[] param) {
        final Properties props = getResource();
        return MessageUtil.getMessage(props, resultCode.getName(), param);
    }

    @SuppressWarnings("rawtypes")
    public String getRichMessage(Map<String, Object> param) {

        final Properties props = getResource();
        String instring = MessageUtil.getMessage(props, resultCode.getName(), param);

        VelocityContext context = new VelocityContext();
        if (param != null) {
            Iterator iter = param.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                context.put(key, value);
            }
        }

        StringWriter writer = new StringWriter();
        Velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, new NullLogChute());
        try {
            Velocity.evaluate(context, writer, null, instring);
        } catch (ParseErrorException e) {
            return null;
        } catch (MethodInvocationException e) {
            return null;
        } catch (ResourceNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }

        return writer.toString();
    }

    protected final synchronized Properties getResource() {

        Properties props = ResultCodeCache.get(getCacheKey());

        if (props == null) {
            String enumClassName = resultCode.getClass().getName();
            props = PropertiesFactory.getProperties(enumClassName, locale);
            if (props != null) {
                fixedProperites(props);
            } else {
                setDefaultProperties(props);
            }
            ResultCodeCache.put(getCacheKey(), props);
        }

        return props;
    }

    // 将配置文件中未配置的枚举采用name显示
    protected final synchronized void fixedProperites(Properties props) {
        IResultCode[] elements = resultCode.getClass().getEnumConstants();
        for (IResultCode element : elements) {
            String value = (String) props.get(element.getName());
            if (StringUtils.isBlank(value)) {
                props.setProperty(element.getName(), element.getName());
            }
        }
    }

    // 使用枚举的name当作默认值
    protected final synchronized void setDefaultProperties(Properties props) {
        props = new Properties();
        IResultCode[] elements = resultCode.getClass().getEnumConstants();
        for (IResultCode element : elements) {
            props.setProperty(element.getName(), element.getName());
        }
    }

    private String getCacheKey() {
        String enumClassName = resultCode.getClass().getName();
        return enumClassName + "_" + locale.toString();
    }

}
