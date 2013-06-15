package com.agile.payroll.test;

import com.agile.payroll.schedules.MonthlySchedule;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-8
 * Time: 下午8:19
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleTest extends TestCase {
    public void testIsPayDay() {
        Date date = new Date(113, 5, 30);
        Date date1 = new Date(113, 6, 31);
        Date date2 = new Date(113, 5, 29);
        Date date3 = new Date(113, 0, 2);

        MonthlySchedule ms = new MonthlySchedule();
        assertTrue(ms.isPayDay(date.getTime()));
        assertTrue(ms.isPayDay(date1.getTime()));
        assertFalse(ms.isPayDay(date2.getTime()));
        assertFalse(ms.isPayDay(date3.getTime()));
    }
}
