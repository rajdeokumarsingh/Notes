package com.java.examples.generic;

import java.util.ArrayList;
import java.util.List;

public class C0072LowerBoundedWildcard {
    /**
     * The term List<Integer> is more restrictive than List<? super Integer>
     *     because the former matches a list of type Integer only,
     *     whereas the latter matches a list of any type that is a supertype of Integer.
     */
    public static void addInteger(List<Integer> list) {
        list.add(1);
        list.add(2);

        // FIXME: compile error
        // list.add(2.5);
    }

    public static void addIntegerSupers(List<? super Integer> list) {
        list.add(1);
        list.add(Integer.valueOf(50));

        // FIXME: compile error
        // list.add(3.5);
        // list.add(Double.valueOf(40.5));
    }

    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<Integer>();
        addInteger(integers);
        addIntegerSupers(integers);

        List<Number> numbers = new ArrayList<Number>();
        numbers.add(1);
        numbers.add(2.5);
        // FIXME: compile error
        // addInteger(numbers);
        addIntegerSupers(numbers);

        List<Object> objects = new ArrayList<Object>();
        objects.add(1);
        objects.add(3.5);
        objects.add("test");
        addIntegerSupers(objects);
    }
}
