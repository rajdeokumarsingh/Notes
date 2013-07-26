package com.agile.payroll.test;

import com.agile.payroll.classifications.HourlyClassification;
import com.agile.payroll.classifications.TimeCard;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-13
 * Time: 上午10:45
 * To change this template use File | Settings | File Templates.
 */
public class HourlyClassificationTest extends TestCase {
    public void testCalculatePay() throws Exception {
        HourlyClassification hc = new HourlyClassification();
        hc.setRate(10);

        // day in week
        hc.addTimeCard(new TimeCard(new Date(113,5,13).getTime(), 8, ""));
        assertEquals(80.0, hc.calculatePay(new Date(113,5,14).getTime()));

        // first day of week
        hc.addTimeCard(new TimeCard(new Date(113,5,10).getTime(), 8, ""));
        assertEquals(160.0, hc.calculatePay(new Date(113,5,14).getTime()));

        hc.addTimeCard(new TimeCard(new Date(113,5,14).getTime(), 8, ""));
        assertEquals(240.0, hc.calculatePay(new Date(113,5,14).getTime()));

        // last day of week
        hc.addTimeCard(new TimeCard(new Date(113,5,8).getTime(), 8, ""));
        assertEquals(360.0, hc.calculatePay(new Date(113,5,14).getTime()));

        // day out of range
        hc.addTimeCard(new TimeCard(new Date(113,5,15).getTime(), 8, ""));
        assertEquals(360.0, hc.calculatePay(new Date(113,5,14).getTime()));
    }
}
