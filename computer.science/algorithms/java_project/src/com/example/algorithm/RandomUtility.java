package com.example.algorithm;

import java.util.Random;

public class RandomUtility {
    public static double[] randomDoubleArray() {
        Random random = new Random(System.currentTimeMillis());

        int len = random.nextInt(1000) + 1;
        double[] ret = new double[len];

        for (int i = 0; i < ret.length; i++) {
            ret[i] = random.nextDouble() * 900;
        }
        return ret;
    }

    public static int[] randomIntegerArray() {
        Random random = new Random(System.currentTimeMillis());

        int len = random.nextInt(1000);
        int[] ret = new int[len];

        for (int i = 0; i < ret.length; i++) {
            ret[i] = random.nextInt(500000) + 1;
        }
        return ret;
    }
}
