package com.example.algorithm.recursion;

import com.example.algorithm.recursion.FindNthNumber;
import junit.framework.TestCase;

import java.util.Arrays;

public class FindNthNumberTest extends TestCase {
    public void testBoundary() throws Exception {
        try {
            FindNthNumber.find(null, 0);
            fail();
        } catch (IllegalArgumentException e) {
        }

        int[] array1 = {6, 10, 13, 5, 8, 3, 2, 11};
        try {
            FindNthNumber.find(array1, -1);
            fail();
        } catch (IllegalArgumentException e) {
        }

        try {
            FindNthNumber.find(array1, array1.length);
            fail();
        } catch (IllegalArgumentException e) {
        }
    }

    public void testNormalSort() throws Exception {
        int[] array1 = {6, 10, 13, 5, 8, 3, 2, 11};
        int[] array2 = {6, 10, 13, 5, 8, 3, 2, 11};
        Arrays.sort(array2);
        assertEquals(array2[0], FindNthNumber.find(array1, 0));
        assertEquals(array2[1], FindNthNumber.find(array1, 1));
        assertEquals(array2[2], FindNthNumber.find(array1, 2));
        assertEquals(array2[3], FindNthNumber.find(array1, 3));
        assertEquals(array2[4], FindNthNumber.find(array1, 4));
        assertEquals(array2[5], FindNthNumber.find(array1, 5));
        assertEquals(array2[6], FindNthNumber.find(array1, 6));
        assertEquals(array2[7], FindNthNumber.find(array1, 7));
    }


    public void testFind() throws Exception {

    }
}
