package com.example.algorithm.snippet;


/**
 * swap two number without third variable
 */
public class SwapTwoNumber {
    public static final void swap(int a, int b) {
        a = a + b;
        b = a - b;
        a = a - b;

        System.out.println("after swap: " + a + ", " + b);
    }

    public static void main(String[] args) {
        swap(20, 30);
    }
}
