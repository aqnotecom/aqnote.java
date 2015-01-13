package com.madding.shared.lang.classloader;

import java.lang.reflect.Method;

public class MetaClassLoaderTest {

    static public void main(String args[]) throws Exception {
        MetaClassLoader classloader = new MetaClassLoader();
        Class<?> c = classloader.loadClass(args[0]);
        Class<?> argsClass[] = { "1".getClass() };
        Method m = c.getMethod("main", argsClass);
        String[] my1 = { "arg1 passed", "arg2 passed" };
        Object myarg1[] = { my1 };
        m.invoke(null, myarg1);

    }
}
