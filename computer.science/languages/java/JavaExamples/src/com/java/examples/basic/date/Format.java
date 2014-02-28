package com.java.examples.basic.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
    y   = year   (yy or yyyy)
    M   = month  (MM)
    d   = day in month (dd)
    h   = hour (0-12)  (hh)
    H   = hour (0-23)  (HH)
    m   = minute in hour (mm)
    s   = seconds (ss)
    S   = milliseconds (SSS)
    z   = time zone  text        (e.g. Pacific Standard Time...)
    Z   = time zone, time offset (e.g. -0800)

    yyyy-MM-dd           (2009-12-31)
    dd-MM-YYYY           (31-12-2009)
    yyyy-MM-dd HH:mm:ss  (2009-12-31 23:59:59)
    HH:mm:ss.SSS         (23:59.59.999)
    yyyy-MM-dd HH:mm:ss.SSS   (2009-12-31 23:59:59.999)
    yyyy-MM-dd HH:mm:ss.SSS Z   (2009-12-31 23:59:59.999 +0100)
*/

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-10
 * Time: 上午9:58
 * To change this template use File | Settings | File Templates.
 */
public class Format {
    public static void main(String[] args) {
        simpleFormat();
    }

    private static void simpleFormat() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        System.out.println(df.format(new Date()));

        DateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(df1.format(new Date()));

        DateFormat df2 = new SimpleDateFormat("yyyy/mm/dd");
        System.out.println(df2.format(new Date()));

        SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df3.format(new Date()));

        System.out.println(Math.abs(1 - 2));

    }
}
