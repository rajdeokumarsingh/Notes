package com.java.examples.basic.date;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 Correct Calendar format:
     YEAR + MONTH + DAY_OF_MONTH
     YEAR + MONTH + WEEK_OF_MONTH + DAY_OF_WEEK
     YEAR + MONTH + DAY_OF_WEEK_IN_MONTH + DAY_OF_WEEK
     YEAR + DAY_OF_YEAR
     YEAR + DAY_OF_WEEK + WEEK_OF_YEAR

 Correct time format:
     HOUR_OF_DAY
     AM_PM + HOUR
*/

/* XXX:
    The Month Trap
        The MONTH field of the Calendar class does not go from 1 to 12 like they do
        when we write dates otherwise. Instead the months run from 0 to 11,
        where 0 is January and 11 is December. This can cause a bit of errors and
        subsequent debugging if you are not aware of this.

    The Day of Week Trap
        The day of week runs from 1 to 7 as you might expect, but sunday,
        not monday is the first day of the week. That means that
        1 = sunday, 2 = monday, ..., 7 = saturday.
        This has also caused me minor annoyances from time to time.
 */

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-10
 * Time: 上午10:37
 * To change this template use File | Settings | File Templates.
 */
public class CalendarExample {
    public static void main(String[] args) {
//        displayCalendarInfo(new GregorianCalendar());
//        displayCalendarInfo(myBirthDay());
//        System.out.println("last day of 2013-1: " + lastDayOfMonth(2013, 1));
//        System.out.println("last day of 2013-2: " + lastDayOfMonth(2013, 2));
//        System.out.println("last day of 2013-6: " + lastDayOfMonth(2013, 6));

        System.out.println(isLastDayOfMonth(genDate(2013,6,30)));
        System.out.println(!isLastDayOfMonth(genDate(2013,6,5)));
        System.out.println(isLastDayOfMonth(genDate(2013,1,31)));

        System.out.println(!isWeekend(genDate(2013,6,10)));
        System.out.println(!isWeekend(genDate(2013,6,9)));
        System.out.println(isWeekend(genDate(2013,6,8)));
    }

    private static boolean isLastDayOfMonth(Calendar c) {
        Calendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.set(Calendar.YEAR, c.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, c.get(Calendar.MONTH));

        return (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) ==
                c.get(Calendar.DAY_OF_MONTH));
    }

    private static boolean isWeekend(Calendar c) {
        return (c.get(Calendar.DAY_OF_WEEK) ==
                c.getActualMaximum(Calendar.DAY_OF_WEEK));
    }

    static Calendar genDate(int year, int month, int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month + Calendar.JANUARY - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar;
    }

    static int lastDayOfMonth(int year, int month) {
        Calendar calendar = new GregorianCalendar();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month + Calendar.JANUARY - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    static void displayCalendarInfo(Calendar calendar) {

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
}
