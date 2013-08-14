package com.example.algorithm;

import java.util.Random;

public class Utility {
    public static double[] randomDoubleArray() {
        Random random = new Random(System.currentTimeMillis());

        int len = random.nextInt(100);
        double[] ret = new double[len];

        for (int i = 0; i < ret.length; i++) {
            ret[i] = random.nextDouble() * 900;
        }
        return ret;
    }
}
