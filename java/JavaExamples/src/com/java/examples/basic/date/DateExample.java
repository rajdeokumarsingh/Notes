package com.java.examples.basic.date;

import java.util.Date;

/* XXX: Note
    Date date = new Date(int year, int month, int day);
        year: from 1900, 2013 -> 113
        month: from 0,   1 -> 0
        day: is day
*/

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-10
 * Time: 上午10:21
 * To change this template use File | Settings | File Templates.
 */
public class DateExample {

    public static void main(String[] args) {
        displayDateInfo();
        compareDate();
    }

    private static void displayDateInfo() {
        Date d = new Date();
        System.out.println("get time: " + d.getTime());
        System.out.println("locale string: " + d.toLocaleString());
        System.out.println("GMT string: " + d.toGMTString());
        System.out.println("string: " + d.toString());
        System.out.println("get date: " + d.getDate());
        System.out.println("year: " + d.getYear());
        System.out.println("month: " + d.getMonth());
        System.out.println("day: " + d.getDay());
        System.out.println("hours:" + d.getHours());
        System.out.println("minutes: " + d.getMinutes());
        System.out.println("seconds: " + d.getSeconds());
    }

    private static void compareDate() {
        Date d1 = new Date();
        Date d2 = new Date();
        System.out.println(d1.compareTo(d2));
    }
}
