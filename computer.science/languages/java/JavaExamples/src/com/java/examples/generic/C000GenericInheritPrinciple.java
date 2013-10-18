package com.java.examples.generic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *  Inheritance relationship:
 *  1. 泛型编程中， 继承关系一般体现在diamond之外。 diamond之内的类型必须一一对应
 *  Collection<String> <|--List<String> <|-- ArrayList<String>
 *  Box<Number> is NOT base class for Box<Integer>
 *
 *  diamond之内的继承关系需要通过通过通配符 ? extends | ? super 来表示
 *      List<?> <|-- List<Number>
 *      List<?> <|-- List<Integer>
 *      List<?> <|-- List<? extends Number> <|-- List<? extends Integer> <\-- List<Integer>
 *      List<?> <|-- List<? super Integer> <|-- List<? super Number> <\-- List<Number>
 *
 *
 */
public class C000GenericInheritPrinciple {
    public static void main(String[] args) {
        // OK
        ArrayList<String> arrayList = new ArrayList<String>();
        Collection<String> collection = arrayList;

        // compile error
        // ArrayList<Integer> integers = new ArrayList<Integer>();
        // ArrayList<Number> numbers = integers;

        // OK
        List<? extends Integer> integers = new ArrayList<Integer>();
        List<? extends Number> numbers = integers;

        // OK
        List<? super Number> suNumber = new ArrayList<Number>();
        List<? super Integer> suInteger = suNumber;

    }
}
