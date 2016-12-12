/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.components.completionservice;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 初始化spring容器
 * 
 * @author madding.lip
 */
public class SpringConfig {

    private static final String            DAEMON_FILE = "classpath:spring-daemon-root.xml";
    public static final ApplicationContext context     = new ClassPathXmlApplicationContext(DAEMON_FILE);
}
