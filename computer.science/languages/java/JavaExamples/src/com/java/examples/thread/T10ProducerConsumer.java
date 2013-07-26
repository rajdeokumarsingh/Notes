package com.java.examples.thread;

import java.util.Random;

class Producer extends Thread {
	T10ProducerConsumer mCup;

	public Producer(T10ProducerConsumer cup) {
		mCup = cup;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			mCup.put("coffee: " + i);
			T10ProducerConsumer.threadMessage("put coffee: " + i);
			Random random = new Random();
			try {
				Thread.sleep(random.nextInt(5000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer extends Thread {
	T10ProducerConsumer mCup;

	public Consumer(T10ProducerConsumer cup) {
		mCup = cup;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			T10ProducerConsumer.threadMessage("got: " + mCup.take());
			Random random = new Random();
			try {
				Thread.sleep(random.nextInt(5000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class T10ProducerConsumer {

	private String message;
	private boolean empty = true;

	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}

	public synchronized String take() {
		while (empty) {
			try {
				wait();
//				wait(1000);
//				threadMessage("take(), waiting...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		empty = true;
		notifyAll();
		return message;
	}

	public synchronized void put(String msg) {
		while (!empty) {
			try {
				wait();
//				wait(1000);
//				threadMessage("put(), waiting...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		empty = false;
		message = msg;
		notifyAll();
	}

	public static void main(String[] args) {
		T10ProducerConsumer cup = new T10ProducerConsumer();
		Consumer take = new Consumer(cup);
		Producer put = new Producer(cup);

		take.start();
		put.start();

		while (take.isAlive() || put.isAlive()) {
			try {
				take.join(1000);
				put.join(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			threadMessage("wait children exit ...");
		}

		threadMessage("children exit.");
	}
}
