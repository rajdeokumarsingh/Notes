package com.pekall.push.test;

public class Util {

    /**
     * Sleep for a few seconds
     * @param seconds
     */
    static final void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
