package com.example.algorithm.recursion;


/**
 * Lecture 3, Introduction to Algorithms
 */
public class BinarySearch {
    /**
     * Search a number in an sorted array
     * @param target the number to search
     * @param array the sorted array
     * @param begin begin position of the array to search
     * @param end end position of the array to search
     * @return position of the number or -1 if not found
     */
    private static int searchInternal(double target, double array[], int begin, int end) {
        if (array == null)
            return -1;
        if (begin > end || begin < 0 || end > array.length - 1)
            return -1; // not found

        int middle = (begin + end) / 2;
        if (target == array[middle]) {
            return middle;  // found
        } else if (target < array[middle]) {
            // search low partition
            return searchInternal(target, array, begin, middle - 1);
        } else {
            // search high partition
            return searchInternal(target, array, middle + 1, end);
        }
    }

    public static int search(double target, double[] array) {
        if(array == null) return -1;

        return searchInternal(target, array, 0, array.length - 1);
    }
}
