package com.aqnote.shared.lang.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.Date;

public class MethodHandlePerf {

    private String address;
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public static void testSet(Long times){
		MethodHandlePerf po = new MethodHandlePerf();
		long startTime = System.currentTimeMillis();
		for(int i = 0;i<times;i++)
			po.setAddress("xxxx");
		System.out.println(System.currentTimeMillis() - startTime);
	}

	public static void testMethodHandle(Long times) throws Throwable{
		MethodHandlePerf po = new MethodHandlePerf();
		MethodHandle handle = MethodHandles.lookup().findVirtual(MethodHandlePerf.class,"setAddress", MethodType.methodType(void.class,String.class));
		long startTime = System.currentTimeMillis();
		for(int i = 0;i<times;i++)
			handle.invoke(po, "xxxx");
		System.out.println(System.currentTimeMillis() - startTime);
	}

	public static void testReflect(Long times) throws Exception{
		MethodHandlePerf po = new MethodHandlePerf();
		Method m = po.getClass().getMethod("setAddress", String.class);
		long startTime = System.currentTimeMillis();
		for(int i = 0;i<times;i++)
			m.invoke(po, "xxxx");
		System.out.println(System.currentTimeMillis() - startTime);
	}

	public static void main(String[] args) throws Throwable{
		Long times =1 * 1000 * 1000l;
									// 编译模式          解释模式				混合模式
		testSet(times);   			// -Xcomp:   4 ms  	-Xint: 175 ms		-Xmixed:  8 ms
		testReflect(times);   		// -Xcomp: 342 ms 	-Xint: 918 ms		-Xmixed: 32 ms
		testMethodHandle(times);  	// -Xcomp: 224 ms   -Xint: 801 ms		-Xmixed: 16 ms

		times =10 * 1000 * 1000l;
		testSet(times);   			// -Xcomp:  17 ms  	-Xint: 1897 ms		-Xmixed: 14 ms
		testReflect(times);   		// -Xcomp: 373 ms 	-Xint: 8005 ms		-Xmixed: 50 ms
		testMethodHandle(times);  	// -Xcomp: 367 ms   -Xint: 7383 ms		-Xmixed: 66 ms

        times =100 * 1000 * 1000l;
        testSet(times);   			// -Xcomp:   ? ms  	-Xint: ? ms		-Xmixed:  39 ms
        testReflect(times);   		// -Xcomp:   ? ms 	-Xint: ? ms		-Xmixed: 203 ms
        testMethodHandle(times);  	// -Xcomp:   ? ms   -Xint: ? ms		-Xmixed: 444 ms

        times =1 * 1000 * 1000 * 1000l;
        testSet(times);   			// -Xcomp:   ? ms  	-Xint: ? ms		-Xmixed:  345 ms
        testReflect(times);   		// -Xcomp:   ? ms 	-Xint: ? ms		-Xmixed: 2008 ms
        testMethodHandle(times);  	// -Xcomp:   ? ms   -Xint: ? ms		-Xmixed: 4443 ms
	}
}