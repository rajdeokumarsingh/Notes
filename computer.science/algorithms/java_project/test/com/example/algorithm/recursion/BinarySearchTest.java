package com.example.algorithm.recursion;

import com.example.algorithm.RandomUtility;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Random;

public class BinarySearchTest extends TestCase {
    public void testBoundary() throws Exception {
        assertEquals(-1, BinarySearch.search(0, null));
        assertEquals(-1, BinarySearch.search(0, new double[0]));

        assertEquals(-1, BinarySearch.search(0, new double[] {9.0}));
        assertEquals(0, BinarySearch.search(9.0, new double[] {9.0}));
    }

    public void testNormal() throws Exception {
        assertEquals(0, BinarySearch.search(9.0, new double[] {9.0, 18.5}));
        assertEquals(1, BinarySearch.search(18.5, new double[] {9.0, 18.5}));

        assertEquals(4, BinarySearch.search(4, new double[] {0, 1, 2, 3, 4, 5, 6}));
    }

    public void testRandom() throws Exception {
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 10000; i++) {
            double[] array = RandomUtility.randomDoubleArray();
            Arrays.sort(array);

            int randomPos = random.nextInt(array.length);
            assertEquals(randomPos, BinarySearch.search(array[randomPos], array));
        }
    }
}
