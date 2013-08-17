package com.example.algorithm.lec1;

public class MergeSorter {

    /**
     * Merge sort
     * @param array input array
     * @param start start point of the array
     * @param size number of element to sort
     */
    public static void sort(int[] array, int start, int size) {
        if(size <= 1) return;

        sort(array, start, size / 2);
        sort(array, start + size / 2, size - size / 2);

        // merge two list
        int i = 0;
        int[] merge = new int[size];
        int pos1 = start, pos1End = start + size / 2 - 1;
        int pos2 = start + size / 2, pos2End = start + size - 1;
        while (pos1 <= pos1End && pos2 <= pos2End) {
             if(array[pos1] < array[pos2]) {
                merge[i] = array[pos1];
                pos1++;
            } else {
                merge[i] = array[pos2];
                pos2++;
            }
            i++;
        }
        if(pos1 <= pos1End) {
            for (;i<merge.length;i++) {
                merge[i] = array[pos1];
                pos1++;
            }
        } else {
            for (;i<merge.length;i++) {
                merge[i] = array[pos2];
                pos2++;
            }
         }
        for (int j = 0; j < merge.length; j++) {
            array[start + j] = merge[j];
        }
    }
}
