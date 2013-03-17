package com.java.examples.thread;

public class T03Sleep {
	public static void main(String args[]) {
		String importantInfo[] = { "Mares eat oats", "Does eat oats",
				"Little lambs eat ivy", "A kid will eat ivy too" };
		
		for (int i = 0; i < importantInfo.length; i++) {
		    // Pause for 4 seconds
		    try {
		        Thread.sleep(4000);
		    } catch (InterruptedException e) {
		        // We've been interrupted: no more messages.
		        return;
		    }
		    // Print a message
		    System.out.println(importantInfo[i]);
		}
		
		/* XXX: A thread goes a long time without invoking a method 
		 * that throws InterruptedException
		 * Then it must periodically invoke Thread.interrupted, which returns true 
		 * if an interrupt has been received.
		 */
//		for (int i = 0; i < inputs.length; i++) {
//		    heavyCrunch(inputs[i]);
//		    if (Thread.interrupted()) {
//		        // We've been interrupted: no more crunching.
//		    	throw new InterruptedException();
//		    }
//		}
	}
}
