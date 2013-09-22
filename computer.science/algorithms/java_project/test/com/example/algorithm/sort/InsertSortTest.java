package com.example.algorithm.sort;

import com.example.algorithm.Debug;
import com.example.algorithm.RandomUtility;
import junit.framework.TestCase;

import java.util.Arrays;

public class InsertSortTest extends TestCase {
    public void testBoundary() throws Exception {
        InsertSort.sort(null);

        double[] array1 = {};
        double[] array2 = {};
        InsertSort.sort(array1);
        assertTrue(Arrays.equals(array1, array2));

        double[] array3 = {0};
        double[] array4 = {0};
        InsertSort.sort(array3);
        assertTrue(Arrays.equals(array3, array4));
    }

    public void testNormalSort() throws Exception {
        double[] array1 = {8, 2, 4, 9, 3, 6};
        double[] array2 = {8, 2, 4, 9, 3, 6};
        InsertSort.sort(array1);
        Arrays.sort(array2);
        assertTrue(Arrays.equals(array1, array2));

        double[] array3 = {8, 2, 4, 2, 3, 6};
        double[] array4 = {8, 2, 4, 2, 3, 6};
        InsertSort.sort(array3);
        Arrays.sort(array4);
        assertTrue(Arrays.equals(array3, array4));

        double[] array5 = {8, 8, 4, 2, 3, 6};
        double[] array6 = {8, 8, 4, 2, 3, 6};
        InsertSort.sort(array5);
        Arrays.sort(array6);
        assertTrue(Arrays.equals(array5, array6));
    }

    public void testRandomTest() throws Exception {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            double[] array = RandomUtility.randomDoubleArray();
            double[] array1 = Arrays.copyOf(array, array.length);
            assertTrue(Arrays.equals(array, array1));

            InsertSort.sort(array);
            Arrays.sort(array1);

            assertTrue(Arrays.equals(array, array1));
        }
        Debug.logTest("Insert, random test:" + (System.currentTimeMillis() - begin));
    }

    public void testPerformance() throws Exception {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            double[] array = RandomUtility.randomDoubleArray();
            InsertSort.sort(array);
        }
        Debug.logTest("Insert, perf test: " + (System.currentTimeMillis() - begin));
    }
}
