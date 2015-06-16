package com.madding.shared.thread;

import java.util.Timer;
import java.util.TimerTask;

public class TimerThreadManager {

	
	static interface Callback {
		public void run();
	}
	
	static class XRunnable implements  Runnable {
		private Callback callback;
		
		public XRunnable(Callback callback) {
			this.callback = callback;
		}

		public void run() {
			try {
				int i = 0;

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
				while (true) {
					System.out.println(i++);
					Thread.sleep(1000);
					t2.start();
				}
			} catch (InterruptedException e) {
				callback.run();
				e.printStackTrace();
			} catch(IllegalThreadStateException e) {
				callback.run();
				e.printStackTrace();
			}
		}
	}

	public void execute(XRunnable runnable) {
		final Thread t = new Thread(runnable);
		t.setName("A");
		t.start();

		TimerTask task = new TimerTask() {
			public void run() {
				t.interrupt();

			}
		};
		Timer timer = new Timer("thread-monitor", true);
		timer.schedule(task, 3000);
	}

	public static void main(String[] args) {
		TimerThreadManager manager = new TimerThreadManager();
		manager.execute(new XRunnable(new Callback() {
			public void run() {
				System.out.println("my logic");
			}
		}));
	}
}
