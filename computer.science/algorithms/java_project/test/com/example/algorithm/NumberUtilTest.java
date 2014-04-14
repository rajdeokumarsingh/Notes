package com.example.algorithm;

import junit.framework.TestCase;

public class NumberUtilTest extends TestCase {


    public void testNthNumber() throws Exception {
        double num = 987654321;
        for (int i = 1; i <= 10; i++) {
            System.out.println(NumberUtil.getNthNumber(num, i));
        }

        try {
            NumberUtil.getNthNumber(num, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
