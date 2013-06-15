package com.agile.payroll.test;

import com.agile.payroll.classifications.SalariedClassification;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-13
 * Time: 上午11:08
 * To change this template use File | Settings | File Templates.
 */
public class SalariedClassificationTest extends TestCase {
    public void testCalculatePay() throws Exception {
        SalariedClassification sc = new SalariedClassification(1000.0);
        assertEquals(1000.0, sc.calculatePay(new Date(113,5,30).getTime()));
    }
}
