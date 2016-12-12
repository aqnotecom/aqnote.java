/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FixTimeThreadManager {
	private ExecutorService executor = Executors.newFixedThreadPool(1);
	
	static interface Callback {
		public void run();
	}
	
	public void execute(Runnable runnable, Callback callback) {
		
	    Future<?> future = executor.submit(runnable);

	    // reject all further submissions
//	    executor.shutdown();

	    try {
	    	// wait 8 seconds to finish
	        future.get(8, TimeUnit.SECONDS);
	    } catch (InterruptedException e) {
	        System.out.println("job was interrupted");
	    } catch (ExecutionException e) {
	        System.out.println("caught exception: " + e.getCause());
	    } catch (TimeoutException e) {
	    	// interrupt the job
	        future.cancel(true);
	        callback.run();
	        System.out.println("timeout");
	    }
	    
	    try {
	    	// wait all unfinished tasks for 2 sec
			if(!executor.awaitTermination(0, TimeUnit.SECONDS)){
			    // force them to quit by interrupting
			    executor.shutdownNow();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String [] args) {
		FixTimeThreadManager manager = new FixTimeThreadManager();
		manager.execute(new Runnable() {
			public void run() {
				
				Thread t2 = new Thread(new Runnable() {
					public void run() {
						while(true) {
							System.out.println("sub thread");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				});
				t2.setName("B");
				
				int i=0;
				while(true) {
					System.out.println(i++);
					try {
						Thread.sleep(1000);
						t2.start();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}, new Callback() {
			public void run() {
				System.out.println("my logic");
			}
		});
	}
}
