package com.example.algorithm.lec1;

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

        int[] array1 = {};
        int[] array2 = {};
        MergeSorter.sort(array1, 0, array1.length);
        assertTrue(Arrays.equals(array1, array2));

        int[] array3 = {0};
        int[] array4 = {0};
        MergeSorter.sort(array3, 0, array3.length);
        assertTrue(Arrays.equals(array3, array4));

        int[] array5 = {1, 0};
        int[] array6 = {0, 1};
        MergeSorter.sort(array5, 0, array5.length);
        assertTrue(Arrays.equals(array5, array6));

        int[] array7 = {1, 0, 5};
        int[] array8 = {0, 1, 5};
        MergeSorter.sort(array7, 0, array7.length);
        assertTrue(Arrays.equals(array8, array8));
    }

    public void testNormalSort() throws Exception {
        int[] array1 = {8, 2, 4, 9, 3, 6};
        int[] array2 = {8, 2, 4, 9, 3, 6};
        MergeSorter.sort(array1, 0, array1.length);
        Arrays.sort(array2);
        assertTrue(Arrays.equals(array1, array2));

        int[] array3 = {8, 2, 4, 2, 3, 6};
        int[] array4 = {8, 2, 4, 2, 3, 6};
        MergeSorter.sort(array3, 0, array3.length);
        Arrays.sort(array4);
        assertTrue(Arrays.equals(array3, array4));

        int[] array5 = {8, 8, 4, 2, 3, 6};
        int[] array6 = {8, 8, 4, 2, 3, 6};
        MergeSorter.sort(array5, 0, array5.length);
        Arrays.sort(array6);
        assertTrue(Arrays.equals(array5, array6));
    }

    public void testRandomTest() throws Exception {
        for (int i = 0; i < 100000; i++) {
            int[] array = Utility.randomIntegerArray();
            int[] array1 = Arrays.copyOf(array, array.length);
            assertTrue(Arrays.equals(array, array1));

            MergeSorter.sort(array, 0, array.length);
            Arrays.sort(array1);

            assertTrue(Arrays.equals(array, array1));
        }
    }
}
