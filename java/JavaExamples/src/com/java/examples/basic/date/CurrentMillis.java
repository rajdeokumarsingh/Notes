package com.java.examples.basic.date;

import java.util.Date;

/* Reference:
    http://tutorials.jenkov.com/java-date-time/index.html
 */

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-10
 * Time: 上午9:16
 * To change this template use File | Settings | File Templates.
 */
public class CurrentMillis {

    public static void main(String[] args) {
        System.out.println("main");

//        showTimeMillis();
        timeMillis2Date();
    }

    static void showTimeMillis() {

        for (int i = 0; i<10;i++) {
            System.out.println(System.currentTimeMillis());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void timeMillis2Date() {
        long millis = System.currentTimeMillis();
        Date d = new Date(millis);
        System.out.println("current millis: " + millis);
        System.out.println(d.toString());
        System.out.println(d.toGMTString());
        System.out.println(d.toLocaleString());
        System.out.println(d.getTimezoneOffset());
    }
}
