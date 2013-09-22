package com.example.algorithm.recursion;

import com.example.algorithm.Debug;

import java.util.Arrays;

/**
 * Lecture 6, Introduction to Algorithms
 */
public class FindNthNumber {
    /**
     * Find nth smallest number of array
     * @param array
     * @param n, the nth number, from 0 to array.length-1
     * @return the value of the number
     */
    public static final int find(int array[], int n) {
        if (array == null)
            throw new IllegalArgumentException("array should not be empty!");
        if(n < 0 || n >= array.length)
            throw new IllegalArgumentException("n is not in array range!");

        return findInternal(array, 0, array.length - 1, n);
    }

    /**
     * Find nth smallest of array
     * @param array
     * @param start
     * @param end
     * @param nth nth smallest of array[start, end]
     * @return
     */
    private static int findInternal(int[] array, int start, int end, int nth) {
        if(start < 0 || start > end)
            throw new IllegalStateException();

        if(start == end)
            return array[start];

        int r = partition(array, start, end);
        int k = r - start;
        Debug.logVerbose("after partition, r: " + r + ", k: " + k +
                ", array: " + Arrays.toString(array));
        if(nth == k) {
            return array[r];
        }
        if(nth < k) {
            Debug.logVerbose("low, start: " + start + ", end: " + (r - 1) +
                    ", nth" + nth + ", array: " + Arrays.toString(array));
            return findInternal(array, start, r - 1, nth);
        } else {
            Debug.logVerbose("high, start: " + (r + 1) + ", end: " + end +
                    ", nth: " + (nth - k) + ", array: " + Arrays.toString(array));
            return findInternal(array, r + 1, end, nth - k - 1);
        }
    }

    private static int partition(int[] array, int first, int last) {
        int i = first;
        int compare = array[first];
        for (int j = i + 1; j <= last; j++) {
            if (array[j] > compare) {
                continue;
            } else {
                // swap array[++i] with array[j]
                int tmp = array[j];
                array[j] = array[++i];
                array[i] = tmp;
            }
        }
        // swap array[firstElement] with array[i]
        array[first] = array[i];
        array[i] = compare;
        return i;
    }
}
