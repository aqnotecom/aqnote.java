package com.madding.shared.test.hash.collision;

import com.madding.shared.test.hash.type.JavaAndOracleHashAlgorithm;

/**
 * 罗列一致的hash的种子冲突
 * 
 * @author madding.lip
 */
public class SeedCollision {

    public static void main(String[] args) {
        JavaAndOracleHashAlgorithm.sameHashcode("Aa", "BB");
    }

}
