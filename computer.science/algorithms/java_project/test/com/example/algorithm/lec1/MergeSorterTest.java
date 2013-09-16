package com.example.algorithm.lec1;

import com.example.algorithm.Debug;
import com.example.algorithm.Utility;
import junit.framework.TestCase;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-8-17
 * Time: 上午9:06
 * To change this template use File | Settings | File Templates.
 */
public class MergeSorterTest extends TestCase {
    public void testBoundary() throws Exception {
        MergeSorter.sort(null, 0, 0);

        double[] array1 = {};
        double[] array2 = {};
        MergeSorter.sort(array1, 0, array1.length);
        assertTrue(Arrays.equals(array1, array2));

        double[] array3 = {0};
        double[] array4 = {0};
        MergeSorter.sort(array3, 0, array3.length);
        assertTrue(Arrays.equals(array3, array4));

        double[] array5 = {1, 0};
        double[] array6 = {0, 1};
        MergeSorter.sort(array5, 0, array5.length);
        assertTrue(Arrays.equals(array5, array6));

        double[] array7 = {1, 0, 5};
        double[] array8 = {0, 1, 5};
        MergeSorter.sort(array7, 0, array7.length);
        assertTrue(Arrays.equals(array8, array8));
    }

    public void testNormalSort() throws Exception {
        double[] array1 = {8, 2, 4, 9, 3, 6};
        double[] array2 = {8, 2, 4, 9, 3, 6};
        MergeSorter.sort(array1, 0, array1.length);
        Arrays.sort(array2);
        assertTrue(Arrays.equals(array1, array2));

        double[] array3 = {8, 2, 4, 2, 3, 6};
        double[] array4 = {8, 2, 4, 2, 3, 6};
        MergeSorter.sort(array3, 0, array3.length);
        Arrays.sort(array4);
        assertTrue(Arrays.equals(array3, array4));

        double[] array5 = {8, 8, 4, 2, 3, 6};
        double[] array6 = {8, 8, 4, 2, 3, 6};
        MergeSorter.sort(array5, 0, array5.length);
        Arrays.sort(array6);
        assertTrue(Arrays.equals(array5, array6));
    }

    public void testRandomTest() throws Exception {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            double[] array = Utility.randomDoubleArray();
            double[] array1 = Arrays.copyOf(array, array.length);
            assertTrue(Arrays.equals(array, array1));

            MergeSorter.sort(array, 0, array.length);
            Arrays.sort(array1);

            assertTrue(Arrays.equals(array, array1));
        }
        Debug.logTest("Merge, random test: " + (System.currentTimeMillis() - begin));
    }

    public void testPerformance() throws Exception {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            double[] array = Utility.randomDoubleArray();
            MergeSorter.sort(array, 0, array.length);
        }
        Debug.logTest("Merge, perf test: " + (System.currentTimeMillis() - begin));
    }
}
