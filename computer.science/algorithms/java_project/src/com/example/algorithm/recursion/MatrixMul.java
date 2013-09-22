package com.example.algorithm.recursion;

/**
 * Lecture 3, Introduction to Algorithms
 */
public class MatrixMul {

    public static double[][] mul(double a[][], double b[][]) {
        if (a.length == 0 || b.length == 0 ||
                a[0].length != b.length) {
            throw new IllegalArgumentException();
        }

        double result[][] = new double[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                result[i][j] = 0;
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] += a[i][k]* b[k][j];
                }
            }
        }

        return result;
    }
}
