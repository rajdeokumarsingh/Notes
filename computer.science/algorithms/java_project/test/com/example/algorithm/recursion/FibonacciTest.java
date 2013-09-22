package com.example.algorithm.recursion;

import com.example.algorithm.Debug;
import junit.framework.TestCase;

import java.util.Arrays;

public class FibonacciTest extends TestCase {
    public void testPrintFibonacci() throws Exception {
        // Fibonacci.printFibonacci(20);

        int[] fibonacci = Fibonacci.generateFibonacci(30);
        Debug.logTest(Arrays.toString(fibonacci));
    }
}
