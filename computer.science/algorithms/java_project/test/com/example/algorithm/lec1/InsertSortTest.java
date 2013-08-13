package com.example.algorithm.lec1;

import junit.framework.TestCase;

import java.util.Arrays;

public class InsertSortTest extends TestCase {
    public void testSort() throws Exception {
        double[] array1 = {8, 2, 4, 9, 3, 6};
        double[] array2 = {8, 2, 4, 9, 3, 6};
        InsertSort.sort(array1);
        Arrays.sort(array2);

        System.out.println("array 1 : " + Arrays.toString(array1));
        System.out.println("array 2 : " + Arrays.toString(array2));
        assertTrue(Arrays.equals(array1, array2));

        // TODO: more test
    }
}
