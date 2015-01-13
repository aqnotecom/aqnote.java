/*
 * Copyright madding.me.
 */
package com.madding.shared.hadoop.offer.join;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * 类E.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Jul 27, 2012 3:09:28 PM
 */
public class JoinComparator extends WritableComparator {

    public JoinComparator(){
        super(TextPair.class, true);
    }

    @SuppressWarnings("rawtypes")
    public int compare(WritableComparable a, WritableComparable b) {
        TextPair t1 = (TextPair) a;
        TextPair t2 = (TextPair) b;
        return t1.getFirst().compareTo(t2.getFirst());
    }
}
