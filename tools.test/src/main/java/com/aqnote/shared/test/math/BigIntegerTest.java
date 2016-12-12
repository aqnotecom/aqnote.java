/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.test.math;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class BigIntegerTest {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        test_probablePrime();
        test_keypair();
    }
    
    public static void test_probablePrime() {
        System.out.println(BigInteger.probablePrime(8, new Random(0)));
    }
    
    public static void test_keypair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024); 
        KeyPair keyPair = keyPairGen.generateKeyPair();
        System.out.println(keyPair.getPublic());
        System.out.println(keyPair.getPrivate());
    }
}
