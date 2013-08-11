package com.java.examples.generic;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-8-11
 * Time: 上午8:34
 * To change this template use File | Settings | File Templates.
 */
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
