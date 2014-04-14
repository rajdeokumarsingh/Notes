package com.example.algorithm;

public class NumberUtil {

//    public static T <? extends Number> getNthNumber(T number, int nth) {
//
//        return T;
//    }

    /**
     * Get nth number from left
     * @param num
     * @param nth
     * @return
     */
    public static int getNthNumber(double num, int nth) {
        if (nth <= 0) {
            throw new IllegalArgumentException("nth should be greater than zero");
        }
        if (num < 0) num = - num;

        int tmp = (int) (num / (Math.pow(10, (nth - 1))));
        return tmp - (tmp / 10) * 10;
    }
}
