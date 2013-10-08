package com.example.algorithm.sort;

import com.example.algorithm.Debug;
import com.example.algorithm.RandomUtility;
import junit.framework.TestCase;

import java.util.Arrays;

public class RadixSortTest extends TestCase {

    public void testGetNthByte() throws Exception {
        assertEquals(0, RadixSort.getNthByte(0, 1));
        assertEquals(0, RadixSort.getNthByte(0, 2));

        assertEquals(1, RadixSort.getNthByte(1, 1));
        assertEquals(0, RadixSort.getNthByte(1, 2));
        assertEquals(0, RadixSort.getNthByte(1, 3));

        assertEquals(255, RadixSort.getNthByte(255, 1));
        assertEquals(0, RadixSort.getNthByte(255, 2));
        assertEquals(0, RadixSort.getNthByte(255, 3));

        assertEquals(0, RadixSort.getNthByte(256, 1));
        assertEquals(1, RadixSort.getNthByte(256, 2));
        assertEquals(0, RadixSort.getNthByte(256, 3));

        assertEquals(255, RadixSort.getNthByte(511, 1));
        assertEquals(1, RadixSort.getNthByte(511, 2));
        assertEquals(0, RadixSort.getNthByte(511, 3));

        assertEquals(0, RadixSort.getNthByte(512, 1));
        assertEquals(2, RadixSort.getNthByte(512, 2));
        assertEquals(0, RadixSort.getNthByte(512, 3));

        assertEquals(255, RadixSort.getNthByte(65535, 1));
        assertEquals(255, RadixSort.getNthByte(65535, 2));
        assertEquals(0, RadixSort.getNthByte(65535, 3));

        assertEquals(0, RadixSort.getNthByte(65536, 1));
        assertEquals(0, RadixSort.getNthByte(65536, 2));
        assertEquals(1, RadixSort.getNthByte(65536, 3));

        assertEquals(0, RadixSort.getNthByte(1, 4));
    }

    public void testBoundary() throws Exception {
        RadixSort.sort(null);

        int[] array1 = {};
        int[] array2 = {};
        RadixSort.sort(array1);
        assertTrue(Arrays.equals(array1, array2));

        int[] array3 = {0};
        int[] array4 = {0};
        RadixSort.sort(array3);
        assertTrue(Arrays.equals(array3, array4));
    }

    public void testNormalSort() throws Exception {
        Debug.setVerboseDebugLog(true);
        int[] array1 = {4,1,3,4,3};
        int[] array2 = {4,1,3,4,3};
        Arrays.sort(array2);
        assertTrue(Arrays.equals(RadixSort.sort(array1), array2));

        int[] array3 = {8, 2, 4, 2, 3, 6};
        int[] array4 = {8, 2, 4, 2, 3, 6};
        RadixSort.sort(array3);
        Arrays.sort(array4);
        assertTrue(Arrays.equals(RadixSort.sort(array3), array4));

        int[] array5 = {8, 8, 4, 2, 3, 6};
        int[] array6 = {8, 8, 4, 2, 3, 6};
        Arrays.sort(array6);
        assertTrue(Arrays.equals(RadixSort.sort(array5), array6));
    }

    public void testNormalSort2th() throws Exception {
        int[] array1 = {264,261,263,264,263};
        int[] array2 = {264,261,263,264,263};
        Arrays.sort(array2);
        assertTrue(Arrays.equals(RadixSort.sort(array1), array2));

        Debug.setVerboseDebugLog(true);
        int[] array3 = {278, 272, 274, 272, 273, 6};
        int[] array4 = {278, 272, 274, 272, 273, 6};
        RadixSort.sort(array3);
        Arrays.sort(array4);
        assertTrue(Arrays.equals(RadixSort.sort(array3), array4));

        int[] array5 = {528, 268, 135, 522, 523, 6};
        int[] array6 = {528, 268, 135, 522, 523, 6};
        Arrays.sort(array6);
        assertTrue(Arrays.equals(RadixSort.sort(array5), array6));

        int[] array8 = {528, 268, 65536, 522, 523, 6};
        int[] array9 = {528, 268, 65536, 522, 523, 6};
        Arrays.sort(array8);
        assertTrue(Arrays.equals(RadixSort.sort(array9), array8));

    }

    public void testNegativeNumber() throws Exception {
        int[] array1 = {8, 2, -1, 4, -200, 2, -100, 3, 6};
        int[] array2 = {8, 2, -1, 4, -200, 2, -100, 3, 6};

        Debug.logTest(Arrays.toString(RadixSort.sort(array1)));

        Arrays.sort(array2);
        Debug.logTest(Arrays.toString(array2));

        // todo: support negative integer
        assertTrue(Arrays.equals(RadixSort.sort(array1), array2));
    }

    public void testRandomTest() throws Exception {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            int[] array = RandomUtility.randomIntegerArray();
            int[] array1 = Arrays.copyOf(array, array.length);
            // Debug.logTest("input: " + Arrays.toString(array));
            array = RadixSort.sort(array);
            Arrays.sort(array1);
            // Debug.logTest("std sort: " + Arrays.toString(array1));
            // Debug.logTest("radix sort: " + Arrays.toString(array));
            assertTrue(Arrays.equals(array, array1));
        }
        Debug.logTest("Radix, Random Test: " + (System.currentTimeMillis() - begin));
    }

    public void testPerformance() throws Exception {
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            int[] array = RandomUtility.randomIntegerArray();
            RadixSort.sort(array);
        }
        Debug.logTest("Radix, Perf Test: " + (System.currentTimeMillis() - begin));
    }
}
