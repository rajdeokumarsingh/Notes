package com.java.examples.thread;

public class T02HelloThread extends Thread {

	@Override
	public void run() {
		for (int i = 0; i< 5; i++) {
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
		(new T02HelloThread()).start();
		System.out.println("main thread 2, child thread created");
	}

}
