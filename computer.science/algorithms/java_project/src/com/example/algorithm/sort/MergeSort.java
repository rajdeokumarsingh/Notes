package com.example.algorithm.sort;

import com.example.algorithm.Debug;

/**
 * Lecture 1, Introduction to Algorithms
 */
public class MergeSort {

    /**
     * Merge sort
     * @param array input array
     * @param start start position of the array
     * @param size number of element to sort
     */
    public static void sort(double[] array, int start, int size) {
        if(size <= 1) return;

        sort(array, start, size / 2);
        sort(array, start + size / 2, size - size / 2);

        // merge two list
        double[] merge = new double[size];
        int pos1 = start, pos1End = start + size / 2 - 1;
        int pos2 = start + size / 2, pos2End = start + size - 1;
        for (int i = 0; i < merge.length; i++) {
            Debug.logVerbose("i: " + i);
            Debug.logVerbose("pos1: " + pos1 + ", pos1End: " + pos1End);
            Debug.logVerbose("pos2: " + pos2 + ", pos2End: " + pos2End);

            if (pos1 > pos1End) {
                Debug.logVerbose("take pos2");
                merge[i] = array[pos2++];
                continue;
            }
            if (pos2 > pos2End) {
                Debug.logVerbose("take pos1");
                merge[i] = array[pos1++];
                continue;
            }
            if (array[pos1] < array[pos2]) {
                Debug.logVerbose("take pos1");
                merge[i] = array[pos1++];
                continue;
            }
            if (array[pos1] >= array[pos2]) {
                Debug.logVerbose("take pos2");
                merge[i] = array[pos2++];
                continue;
            }
        }
        for (int j = 0; j < merge.length; j++) {
            array[start + j] = merge[j];
        }
    }
}
