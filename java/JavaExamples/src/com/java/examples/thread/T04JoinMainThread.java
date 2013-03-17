package com.java.examples.thread;

public class T04JoinMainThread extends Thread {
	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			System.out.println("child thread running: " + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("main thread 1");
		Thread t = new T04JoinMainThread();
		t.start();
		System.out.println("main thread 2, child thread created");

		// wait for exit of child thread 
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("main thread 3, child thread joined");
	}

}
