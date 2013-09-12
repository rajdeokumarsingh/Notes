package com.java.examples.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C0071UnboundedWildcard {
    // only output List<Object>
    public static void printObjectList(List<Object> list) {
        list.add(1);
        list.add(2.5);
        list.add("test");

        System.out.println("printObjectList: ");
        for (Object elem : list) {
            System.out.print(elem + ", ");
        }
        System.out.println();
    }

    // output any list
    public static void printGenericList(List<?> list) {
        // FIXME: compile error, since List<?> may be a List<Integer>, List<Double>,
        // or List<Anything>, so we can not add any object to it
        // list.add(1);
        // list.add(2.5);
        // list.add("test");

        System.out.println("printGenericList: ");
        for (Object elem : list) {
            System.out.print(elem + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<Object> objects = Arrays.asList((Object)new Integer(3), (Object)Integer.valueOf(4));
        printObjectList(objects);

        // FIXME: runtime error, java.lang.UnsupportedOperationException
        // objects.add(3);
        // objects.add(4.5);
        // objects.add("test");
        // objects.add((Object) new String("test"));

        // FIXME: compile error
        // printObjectList(integers);
        // printGenericList(doubles);

        List<Integer> integers = Arrays.asList(1, 2, 3);
        List<Double> doubles = Arrays.asList(4.5, 5.4, 9.0);
        printGenericList(integers);
        printGenericList(doubles);

        List<?> unknowns = Arrays.asList(1, 2.0, "test");
        // FIXME: compile error
        // unknowns.add((Object) new String("test"));
        printGenericList(unknowns);
    }
}
