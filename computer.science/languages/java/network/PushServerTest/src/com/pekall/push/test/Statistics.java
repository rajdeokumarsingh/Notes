package com.pekall.push.test;

public class Statistics {

    private static Statistics statistics;

    private long creCount = 0;
    private long sucCount = 0;
    private long failCount = 0;
    private long pingCnt = 0;
    private long pongCnt = 0;
    private long errorCnt = 0;
    private long msgCnt = 0;

    private Statistics() {
    }

    public static Statistics getInstance() {
        if (statistics == null) {
            synchronized (Statistics.class) {
                if (statistics == null) {
                    statistics = new Statistics();
                }
            }
        }
        return statistics;
    }

    public synchronized void create() {
        // System.out.println(creCount);
        creCount++;
    }

    public synchronized void success() {
        sucCount++;
    }

    public synchronized void fail() {
        failCount++;
    }

    public synchronized String getStatistics() {
        return "Thread total: " + creCount
                + " , connect OK: " + sucCount
                + ", ping: " + pingCnt + ", pong: " + pongCnt
                + ", message: " + msgCnt
                + " , connect fail : " + failCount
                + ", network error: " + errorCnt;
    }

    public synchronized void ping() {
        pingCnt++;
    }

    public synchronized void pong() {
        pongCnt++;
    }

    public synchronized void error() {
        errorCnt++;
    }

    public synchronized void message() {
        msgCnt++;
    }
}
