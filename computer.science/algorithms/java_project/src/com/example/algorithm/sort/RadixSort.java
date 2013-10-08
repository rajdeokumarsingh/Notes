package com.example.algorithm.sort;

import com.example.algorithm.Debug;

import java.util.Arrays;

/**
 * Lecture 5, Introduction to Algorithms
 */
public class RadixSort {
    /**
     * sort array
     *  todo: negative number
     * @param array, array[i] should be greater than or equal to 0
     * @return sorted array
     */
    public static int[] sort(int[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }

        final int BYTE_MAX = 255;  // an 32-bit integer contains 4 bytes
        int[] countingArray = new int[BYTE_MAX + 1];
        int[] auxiliary = new int[array.length];

        for (int n = 1; n <= 4; n++) {
            for (int i = 0; i <= BYTE_MAX; i++) {
                countingArray[i] = 0;
            }
            for (int i = 0; i < array.length; i++) {
                // c[i] = |{key = i}|
                // c[array[i]] = c[array[i]] + 1;
                int nth = getNthByte(array[i], n);
                countingArray[nth] = countingArray[nth] + 1;
            }
            for (int i = 1; i <= BYTE_MAX; i++) {
                // c[i] = |{key <= i}|
                countingArray[i] += countingArray[i - 1];
            }
            // System.out.println("c : " + Arrays.toString(c));
            for (int i = array.length - 1; i >= 0; i--) {
                int nth = getNthByte(array[i], n);
                // b[c[array[i]] - 1] = array[i];
                // c[array[i]]--;
                auxiliary[countingArray[nth] - 1] = array[i];
                countingArray[nth]--;
            }

            array = auxiliary;
            auxiliary = new int[array.length];
            // Debug.logVerbose("sort, nth: " + n + ", " + Arrays.toString(array));
        }
        // Debug.logTest(Arrays.toString(array));
        return array;
    }

    static int getNthByte(int number, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        int n1 = number >>> ((n - 1) << 3) << ((n - 1) << 3);
        int n0 = number >>> (n << 3) << (n << 3);
        if(n == 4) n0 = 0;
        return (n1 - n0) >>> (n - 1) * 8;
    }
}
