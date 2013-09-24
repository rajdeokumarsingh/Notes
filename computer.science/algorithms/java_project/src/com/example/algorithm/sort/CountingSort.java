package com.example.algorithm.sort;

import java.util.Arrays;

/**
 * Lecture 5, Introduction to Algorithms
 */
public class CountingSort {
    public static int[] sort(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }

        int max = internalMax(array);
        int[] c = new int[max + 1];
        int[] b = new int[array.length];

        for (int i = 0; i <= max; i++) {
            c[i] = 0;
        }
        for (int i = 0; i < array.length; i++) {
            // c[i] = |{key = i}|
            c[array[i]] = c[array[i]] + 1;
        }
        /*
        for (int i = 1; i <= max; i++) {
            // c[i] = |{key <= i}|
            c[i] += c[i-1];
        }
        for (int i = array.length - 1; i >= 0; i--) {
            b[c[array[i]]] = array[i];
            c[array[i]]--;
        } */
        int k = 0;
        for (int i = 0; i < array.length; i++) {
            while (c[k] == 0) {
                k++;
            }
            b[i] = k;
            c[k]--;
        }
        return b;
    }

    public static int[] sort2(int[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int MAX = internalMax(array);
        int[] c = new int[MAX + 1];
        int[] b = new int[array.length];

        for (int i = 0; i <= MAX; i++) {
            c[i] = 0;
        }
        for (int i = 0; i < array.length; i++) {
            // c[i] = |{key = i}|
            c[array[i]] = c[array[i]] + 1;
        }
        for (int i = 1; i <= MAX; i++) {
            // c[i] = |{key <= i}|
            c[i] += c[i-1];
        }
        // System.out.println("a : " + Arrays.toString(array));
        // System.out.println("c : " + Arrays.toString(c));
        // good: for (int i = 0; i < array.length; i++) {
        // good: for (int i = array.length - 1; i >= 0; i--) {
        for (int i = 0; i < array.length; i++) {
            b[c[array[i]] - 1] = array[i];
            c[array[i]]--;
        }
        return b;
    }

    public static int internalMax(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("array should not be empty");
        }
        int ret = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > ret) {
                ret = array[i];
            }
        }
        return ret;
    }
}
