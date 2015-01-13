package com.madding.shared.test.hash.type;


/**
 * java和oracle中的hash算法 F
 * <p>
 * 案例数据
 * </p>
 * <ul>
 * <li>"Aa", "BB"</li>
 * </ul>
 * 
 * @author madding.lip
 */
public class JavaAndOracleHashAlgorithm {

    public static boolean sameHashcode(String a, String b) {
        return hash(a) == hash(b);
    }

    public static int hash(String str) {
        return hash(str.hashCode());
    }

    private static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
}
