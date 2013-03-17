package com.java.examples.thread;

public class T08SyncObject {
    private long c1 = 0;
    private long c2 = 0;
    
    // use two lock, the operation of c1 would not impact c2.
    private Object lock1 = new Object();
    private Object lock2 = new Object();

    public void inc1() {
        synchronized(lock1) {
            c1++;
        }
    }

    public void inc2() {
        synchronized(lock2) {
            c2++;
        }
    }
}