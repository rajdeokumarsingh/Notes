package com.java.examples.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Illustrate difference between
 *  List<Number> and
 *  List<? extends Number>
 */
public class C0070UpperBoundedWildcard {
    public static void outputNumberList(List<Number> list) {
        // The list must be a List<Number>, and Integer and Double are Number,
        // so, they could be added into the list
        list.add(Integer.valueOf(20));
        list.add(Double.valueOf(30.5));

        for (Number n : list) {
            System.out.println(n.toString());
        }
    }

    public static void outputNumberWildcardList(List<? extends Number> list) {
        // The list may be a List<Integer>, List<Float>, List<Double>
        // since its type is unknown , we can not add an Integer or Double to it

        // Compile error
        // list.add(Integer.valueOf(20));
        // list.add(Double.valueOf(30.5));
        // list.add((Object) Integer.valueOf(20));

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

        C0070UpperBoundedWildcard.outputNumberList(numbers);
        // Compile error:
        // C0070UpperBoundedWildcard.outputNumberList(integers);
        C0070UpperBoundedWildcard.outputNumberWildcardList(numbers);
        C0070UpperBoundedWildcard.outputNumberWildcardList(integers);

        List<Integer> integerList = Arrays.asList(1, 2, 3);
        System.out.println("sum of integer list is: " +
            C0070UpperBoundedWildcard.sumOfList(integerList));
    }
}
