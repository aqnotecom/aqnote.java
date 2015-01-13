package com.madding.shared.components.completionservice;

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
