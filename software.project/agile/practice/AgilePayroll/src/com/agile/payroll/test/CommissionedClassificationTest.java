package com.agile.payroll.test;

import com.agile.payroll.classifications.CommissionedClassification;
import com.agile.payroll.classifications.SalesReceipt;
import junit.framework.TestCase;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-13
 * Time: 上午11:11
 * To change this template use File | Settings | File Templates.
 */
public class CommissionedClassificationTest extends TestCase {
    public void testCalculatePay() throws Exception {
        CommissionedClassification cc = new CommissionedClassification(1000, 0.1);

        // month begin
        cc.addSaleReceipt(new SalesReceipt(new Date(113, 5, 1).getTime(), 10));
        assertEquals(1001.0, cc.calculatePay(new Date(113,5,30).getTime()));

        // month end
        cc.addSaleReceipt(new SalesReceipt(new Date(113, 5, 30).getTime(), 10));
        assertEquals(1002.0, cc.calculatePay(new Date(113, 5, 30).getTime()));

        // day in month
        cc.addSaleReceipt(new SalesReceipt(new Date(113, 5, 20).getTime(), 10));
        assertEquals(1003.0, cc.calculatePay(new Date(113, 5, 30).getTime()));

        // day before month begin
        cc.addSaleReceipt(new SalesReceipt(new Date(113, 4, 31).getTime(), 10));
        assertEquals(1003.0, cc.calculatePay(new Date(113,5,30).getTime()));

        // day after month end
        cc.addSaleReceipt(new SalesReceipt(new Date(113, 6, 1).getTime(), 10));
        assertEquals(1003.0, cc.calculatePay(new Date(113, 5, 30).getTime()));


    }
}
