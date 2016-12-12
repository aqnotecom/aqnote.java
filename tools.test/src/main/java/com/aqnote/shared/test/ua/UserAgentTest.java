/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.ua;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

/**
 * 
 * 
 * @author madding.lip
 */
public class UserAgentTest {

    public static void main(String[] args) throws Exception {
        String userAgentString = "Mozilla/5.0 (Android 4.x) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/30.0.1599.114 Safari/537.36";
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        
        System.out.println("=======================================");
        Browser browser = userAgent.getBrowser();
        System.out.println("browser.getName(): " + browser.getName());
        System.out.println("browser.name(): " + browser.name());
        System.out.println("browser.name(): " + browser.ordinal());
        System.out.println("browser.getBrowserType(): " + browser.getBrowserType());
        System.out.println("browser.getGroup(): " + browser.getGroup());
        System.out.println("browser.getId(): " + browser.getId());
        System.out.println("browser.getManufacturer(): " + browser.getManufacturer());
        System.out.println("browser.getRenderingEngine(): " + browser.getRenderingEngine());
        System.out.println("=======================================");
        System.out.println("userAgent.getBrowserVersion(): " + userAgent.getBrowserVersion());
        System.out.println("=======================================");
        OperatingSystem os = userAgent.getOperatingSystem();
        System.out.println("os.getName(): " + os.getName());
        System.out.println(os.isMobileDevice());
        System.out.println(os.name());
        System.out.println(os.ordinal());
        System.out.println(os.getDeviceType());
        System.out.println(os.getGroup());
        System.out.println(os.getId());
        System.out.println(os.getManufacturer());
        System.out.println("=======================================");
    };
}
