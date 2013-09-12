package com.java.examples.generic;

interface Comparable<T> {
    public int compareTo(T o);
}

public class C006MethodBoundedTypeParam {
    public static <T extends Comparable> int greaterThan(T[] array, T element) {
        int count = 0;
        for (T e : array) {
            if (e.compareTo(element) > 0) {
                count++;
            }
        }
        return count;
    }
}
