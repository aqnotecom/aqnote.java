package com.madding.shared.lang;

import java.io.Serializable;

import org.junit.Test;

/**
 * 根据传入的类型作类型转换
 * 
 * @author madding.lip
 */
public class ClassUtilTest {
    
    @Test
    public void testCase() {
        System.out.println(ClassUtil.converObj(getObj(12345), int.class));
        System.out.println(ClassUtil.converObj(getObj(true), boolean.class));
        System.out.println(ClassUtil.converObj(getObj(12345), Integer.class));
    }

    private Object getObj(Serializable serial) {
        return serial;
    }
}
