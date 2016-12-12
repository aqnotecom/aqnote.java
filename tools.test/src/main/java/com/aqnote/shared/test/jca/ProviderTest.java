/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.jca;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Set;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.jcajce.provider.BouncyCastlePQCProvider;

public class ProviderTest {

    static Provider[] providers = null;
    static {
        providers = new Provider[] { new BouncyCastleProvider(), new BouncyCastlePQCProvider() };
        providers = Security.getProviders();
    }

    public static void main(String[] args) {
        ProviderTest test = new ProviderTest();
        // test.test_list_jce_provider();
        test.test_list_jce_service();
    }

    public void test_list_jce_provider() {

        for (int i = 0; i < providers.length; i++) {
            Provider provider = providers[i];
            String className = provider.getClass().getName();
            System.out.println("==" + provider.getName() + "," + provider.getVersion() + "," + className
                               + "========================");
            for (Object obj : provider.keySet()) {
                System.out.println(" " + obj + "=" + provider.get(obj));
            }
        }
    }

    public void test_list_jce_service() {
        for (int i = 0; i < providers.length; i++) {
            Provider provider = providers[i];
            Set<Service> services = provider.getServices();
            String className = provider.getClass().getName();
            System.out.println("#####" + provider.getName() + "," + provider.getVersion() + "," + className + "#####");
            for (Service service : services) {
                System.out.println(" " + service.getType() + "*" + service.getAlgorithm() + "*"
                                   + service.getClassName());
            }
        }
    }
}
