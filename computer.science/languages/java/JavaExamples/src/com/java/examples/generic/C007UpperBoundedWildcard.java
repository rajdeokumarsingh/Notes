package com.java.examples.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Illustrate difference between
 *  List<Number> and
 *  List<? extends Number>
 */
public class C007UpperBoundedWildcard {
    public static void outputNumberList(List<Number> list) {
        for (Number n : list) {
            System.out.println(n.toString());
        }
    }

    public static void outputNumberWildcardList(List<? extends Number> list) {
        for (Number n : list) {
            System.out.println(n.toString());
        }
    }

    public static double sumOfList(List<? extends Number> list) {
        double sum = 0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        return sum;
    }

    public static void main(String[] args) {
        List<Number> numbers = new ArrayList<Number>();
        numbers.add(23);
        numbers.add(34.4);
        numbers.add(3.1415926);

        List<Integer> integers = new ArrayList<Integer>();
        integers.add(20);
        integers.add(30);
        integers.add(40);

        C007UpperBoundedWildcard.outputNumberList(numbers);
        // Compile error:
        // C007UpperBoundedWildcard.outputNumberList(integers);
        C007UpperBoundedWildcard.outputNumberWildcardList(numbers);
        C007UpperBoundedWildcard.outputNumberWildcardList(integers);

        List<Integer> integerList = Arrays.asList(1, 2, 3);
        System.out.println("sum of integer list is: " +
            C007UpperBoundedWildcard.sumOfList(integerList));
    }
}
