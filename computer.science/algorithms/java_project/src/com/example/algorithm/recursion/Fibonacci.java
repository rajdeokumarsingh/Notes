package com.example.algorithm.recursion;

import com.example.algorithm.Debug;

/**
 * Lecture 3, Introduction to Algorithms
 */
public class Fibonacci {
    /**
     * Print Fibonacci
     * @param n number to print
     */
    public static void printFibonacci(int n) {
        printFibonacciInternal(n);
    }

    private static void printFibonacciInternal(int n) {
        StringBuilder sb = new StringBuilder("Fibonacci: ");
        int x = 0;
        int y = 1;

        sb.append(x + ", ");
        sb.append(y + ", ");

        for (int i = 2; i < n; i++) {
            int tmp = y;
            y = x + y;
            x = tmp;
            sb.append(y + ", ");
        }

        Debug.logTest(sb.toString());
    }

    public static int[] generateFibonacci(int n) {
        if (n <= 0) {
            return new int[0];
        }
        if (n == 1) {
            return new int[]{0};
        }
        if (n == 2) {
            return new int[]{0, 1};
        }

        int[] result = new int[n];
        result[0] = 0;
        result[1] = 1;
        for (int i = 2; i < n; i++) {
            result[i] = result[i - 1] + result[i - 2];
        }
        return result;
    }
}
