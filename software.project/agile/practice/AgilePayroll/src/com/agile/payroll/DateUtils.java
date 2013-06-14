package com.agile.payroll;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-13
 * Time: 上午9:14
 * To change this template use File | Settings | File Templates.
 */
public class DateUtils {

    public static boolean isLastDayOfMonth(Calendar c) {
        Calendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.set(Calendar.YEAR, c.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, c.get(Calendar.MONTH));

        return (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) ==
                c.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Check the date is weekend
     * @param c
     * @return
     */
    public static boolean isWeekend(Calendar c) {
        return (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
        /* return (c.get(Calendar.DAY_OF_WEEK) ==
                c.getActualMaximum(Calendar.DAY_OF_WEEK)); */
    }

    public static boolean isWeekend(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return isWeekend(c);
    }

    public static boolean isFriday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return isFriday(c);
    }

    public static boolean isFriday(Calendar c) {
        return (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY);
    }

    public static  boolean isBiWeekFriday(Calendar c) {
        return (isFriday(c) &&
                (c.get(Calendar.WEEK_OF_MONTH) % 2 == 0));
    }

    public static  boolean isBiWeekFriday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return isBiWeekFriday(c);
    }


    public static boolean isWorkingDay(long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        return isWorkingDay(c);
    }

    public static boolean isWorkingDay(Calendar c) {
        return !(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }

    public static  boolean isBiWeekend(Calendar c) {
        return (isWeekend(c) &&
                (c.get(Calendar.WEEK_OF_MONTH) % 2 == 0));
    }

    public static boolean isLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return isLastDayOfMonth(c);
    }

   public static  boolean isBiWeekend(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return isBiWeekend(c);
    }

    public static void displayCalendarDate(Calendar calendar) {
        System.out.println("year: " + calendar.get(Calendar.YEAR));
        System.out.println("month: " + calendar.get(Calendar.MONTH));
        System.out.println("day of month: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("day of week: " + calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println("week of year: " + calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println("week of month: " + calendar.get(Calendar.WEEK_OF_MONTH));
    }

    public static long weekBeginDate(long date) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date));
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        // last saturday is our begin date to calculate payment.
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH)-1);
        return dayBeginDate(c.getTime().getTime());
    }

    public static long monthBeginDate(long date) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date));
        c.set(Calendar.DAY_OF_MONTH, 1);
        return dayBeginDate(c.getTime().getTime());
    }

    public static long dayBeginDate(long date) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(date));

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.clear(Calendar.MINUTE);
        c.clear(Calendar.SECOND);
        c.clear(Calendar.MILLISECOND);
        return c.getTime().getTime();
    }

    public static void displayCalendarInfo(Calendar calendar) {

        System.out.println("year: " + calendar.get(Calendar.YEAR));
        System.out.println("month: " + calendar.get(Calendar.MONTH));
        System.out.println("day of month: " + calendar.get(Calendar.DAY_OF_MONTH));
        System.out.println("day of week: " + calendar.get(Calendar.DAY_OF_WEEK));
        System.out.println("week of year: " + calendar.get(Calendar.WEEK_OF_YEAR));
        System.out.println("week of month: " + calendar.get(Calendar.WEEK_OF_MONTH));

        System.out.println("hour: " + calendar.get(Calendar.HOUR));
        System.out.println("hour of day: " + calendar.get(Calendar.HOUR_OF_DAY));
        System.out.println("minute: " + calendar.get(Calendar.MINUTE));
        System.out.println("second: " + calendar.get(Calendar.SECOND));
        System.out.println("millisecond: " + calendar.get(Calendar.MILLISECOND));
    }

    public static void displayCalendarInfo(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        displayCalendarInfo(c);
    }

    public static void displayCalendarInfo(long date) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date);
        displayCalendarInfo(c);
    }


}
