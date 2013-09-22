package com.example.algorithm.sort;

import com.example.algorithm.Debug;

import java.util.Arrays;

/**
 * Lecture 4, Introduction to Algorithms
 */
public class QuickSort {
    public static void sort(double[] array) {
        if (array == null || array.length <= 1) return;

        sortInternal(array, 0, array.length - 1);
    }

    private static boolean isValidRange(int index, double[] array) {
        if(index >= 0 && index < array.length)
            return true;
        return false;
    }

    private static void sortInternal(double[] array, int firstElement, int lastElement) {
        Debug.logVerbose("\narray length: " + array.length
                + ", first: " + firstElement + ", second: " + lastElement);
        if (!isValidRange(firstElement, array) || !isValidRange(lastElement, array)
                || firstElement >= lastElement) {
            return;
        }
        int i = partition(array, firstElement, lastElement);

        sortInternal(array, firstElement, i - 1);
        sortInternal(array, i + 1, lastElement);
    }

    private static int partition(double[] array, int firstElement, int lastElement) {
        int i = firstElement;
        double compare = array[firstElement];
        for (int j = i + 1; j <= lastElement; j++) {
            if (array[j] > compare) {
                continue;
            } else {
                // swap array[++i] with array[j]
                double tmp = array[j];
                array[j] = array[++i];
                array[i] = tmp;
            }
        }
        // swap array[firstElement] with array[i]
        array[firstElement] = array[i];
        array[i] = compare;
        return i;
    }
}
