package com.madding.shared.test.hash.collision;

import com.madding.shared.test.hash.type.JavaAndOracleHashAlgorithm;

/**
 * 罗列一批的hash的冲突
 * 
 * @author madding.lip
 */
public class ExtCollision {

    public static void main(String[] args) {
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "AaAaAaAa"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "BBBBBBBB"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "AaBBBBBB"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "BBAaBBBB"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "BBBBAaBB"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "BBBBBBAa"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "AaAaBBBB"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "AaBBAaBB"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "AaBBBBAa"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "BBAaAaBB"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "BBAaBBAa"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "BBBBAaAa"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "AaAaAaBB"));
        System.out.println(JavaAndOracleHashAlgorithm.sameHashcode("AaAaAaAa", "BBAaAaAa"));
        
    }
    
}



