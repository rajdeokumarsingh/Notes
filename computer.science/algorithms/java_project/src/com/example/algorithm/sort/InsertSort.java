package com.example.algorithm.sort;

/**
 * Lecture 1, Introduction to Algorithms
 */
public class InsertSort {
    public static void sort(double[] array) {
        if (array == null) {
            throw new IllegalArgumentException("input is null");
        }

        /* Assumption: elements before i are sorted
           Procedure:
           compare a[i] with a[j:j<i]
           if (a[i] <= a[j])
                insert a[i] before a[j]
         */
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if(array[i] > array[j]) continue;

                double tmp = array[i];
                for (int k = i; k > j; k--) {
                    array[k] = array[k-1];
                }
                array[j] = tmp;
                break;
            }
        }
    }
}
