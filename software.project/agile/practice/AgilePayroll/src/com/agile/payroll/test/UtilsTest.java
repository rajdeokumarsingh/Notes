package com.agile.payroll.test;

import com.agile.payroll.DateUtils;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-13
 * Time: 上午9:24
 * To change this template use File | Settings | File Templates.
 */
public class UtilsTest extends TestCase {

    public void testIsLastDayOfMonth() throws Exception {
        Date d1 = new Date(113, 5, 30);
        assertTrue(DateUtils.isLastDayOfMonth(d1));

        Date d2 = new Date(113, 0, 31);
        assertTrue(DateUtils.isLastDayOfMonth(d2));

        Date d3 = new Date(113, 11, 31);
        assertTrue(DateUtils.isLastDayOfMonth(d3));

        Date d4 = new Date(113, 5, 29);
        assertTrue(!DateUtils.isLastDayOfMonth(d4));

        Date d5 = new Date(113, 9, 30);
        assertTrue(!DateUtils.isLastDayOfMonth(d5));

    }

    public void testIsWeekend() throws Exception {
        Date d1 = new Date(113, 5,15);
        assertTrue(DateUtils.isWeekend(d1));

        Date d2 = new Date(113, 5,1);
        assertTrue(DateUtils.isWeekend(d2));

        Date d3 = new Date(113, 5,12);
        assertTrue(!DateUtils.isWeekend(d3));
    }

    public void testIsBiWeekend() throws Exception {
        Date d1 = new Date(113, 5,1);
        assertTrue(!DateUtils.isBiWeekend(d1));

        Date d2 = new Date(113, 5,8);
        assertTrue(DateUtils.isBiWeekend(d2));

        Date d3 = new Date(113, 5,12);
        assertTrue(!DateUtils.isBiWeekend(d3));
     }

    public void testDayBegin() {
        Date d1 = new Date();
        long d2 = DateUtils.dayBeginDate(d1.getTime());
        Date date2 = new Date(d2);
        assertTrue(date2.getHours() == 0);
        assertTrue(date2.getMinutes() == 0);
        assertTrue(date2.getSeconds() == 0);
    }

    public void testWeekBegin() {
        Date d1 = new Date(113, 5,13);
        long d2 = DateUtils.weekBeginDate(d1.getTime());
        Date date2 = new Date(d2);
        assertTrue(date2.getDate() == 8); // last saturday
        assertTrue(date2.getHours() == 0);
        assertTrue(date2.getMinutes() == 0);
        assertTrue(date2.getSeconds() == 0);
    }

    public void testMonthBegin() {
        Date d1 = new Date(113, 5,13);
        long d2 = DateUtils.monthBeginDate(d1.getTime());
        Date date2 = new Date(d2);
        assertTrue(date2.getDate() == 1);
        assertTrue(date2.getHours() == 0);
        assertTrue(date2.getMinutes() == 0);
        assertTrue(date2.getSeconds() == 0);
    }

    public void testWorkingDay() {
        Date sunday = new Date(113, 5, 9);
        Date monday = new Date(113, 5, 10);
        Date tuesday = new Date(113, 5, 11);
        Date wednesday = new Date(113, 5, 12);
        Date thursday = new Date(113, 5, 13);
        Date friday = new Date(113, 5, 14);
        Date saturday = new Date(113, 5, 15);

        assertFalse(DateUtils.isWorkingDay(sunday.getTime()));
        assertFalse(DateUtils.isWorkingDay(saturday.getTime()));
        assertTrue(DateUtils.isWorkingDay(monday.getTime()));
        assertTrue(DateUtils.isWorkingDay(tuesday.getTime()));
        assertTrue(DateUtils.isWorkingDay(wednesday.getTime()));
        assertTrue(DateUtils.isWorkingDay(thursday.getTime()));
        assertTrue(DateUtils.isWorkingDay(friday.getTime()));
    }
}
