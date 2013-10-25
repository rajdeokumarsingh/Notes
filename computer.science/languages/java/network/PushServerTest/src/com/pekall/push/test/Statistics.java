package com.pekall.push.test;

public class Statistics {

    private static Statistics statistics;

    private int creCount = 0;
    private int sucCount = 0;
    private static int failCount = 0;

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
        System.out.println(creCount);
        creCount++;
    }

    public synchronized void success() {
        sucCount++;
    }

    public synchronized void fail() {
        failCount++;
    }

    public synchronized String getStatistics() {
        return "Connection : " + creCount + " , success : " + sucCount + " , fail : " + failCount;
    }

}
