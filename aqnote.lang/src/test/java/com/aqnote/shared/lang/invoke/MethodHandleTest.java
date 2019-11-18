package com.aqnote.shared.lang.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aqnote on 6/26/17.
 */
public class MethodHandleTest {

    public static void main(String[] args) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        try {
            MethodHandle handle =  lookup.findStatic(MethodHandleTest.class, "doubleVal", MethodType.methodType(int.class, int.class));
            Integer three = null;
            try {
                three = (Integer)handle.invoke(3);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            System.out.printf("data=%d\n", three);

            List<Integer> dataList = Arrays.asList(1, 2, 3, 4);
            try {
                MethodHandleTest.transform(dataList, handle);

                for(Integer data : dataList) {
                    System.out.printf("%d ", data);
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static int doubleVal(int val) {
        return val * 2;
    }

    public static List<Integer> transform(List<Integer> dataList, MethodHandle handle) throws Throwable {
       for(int i=0; i < dataList.size(); i++) {
           dataList.set(i, (Integer) handle.invoke(dataList.get(i)));
        }
        return dataList;
    }
}
