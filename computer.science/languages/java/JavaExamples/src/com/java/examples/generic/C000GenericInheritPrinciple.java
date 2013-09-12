package com.java.examples.generic;

/**
 *  Inheritance relationship:
 *  Collection<String> <|--List<String> <|-- ArrayList<String>
 *
 *  Box<Number> is NOT base class for Box<Integer>
 *
 *  In generic programing, anything in the diamond <>, could not be inherit directly
 *
 *  泛型编程中， 继承关系一般体现在diamond之外。 diamond之内的类型必须一一对应
 *
 *  diamond之内的继承关系需要通过通过通配符 ? extends | super 来表示
 *      List<?> <|-- List<Number>
 *      List<?> <|-- List<Integer>
 *      List<?> <|-- List<? extends Number> <|-- List<? extends Integer> <\-- List<Integer>
 *      List<?> <|-- List<? super Integer> <|-- List<? super Number> <\-- List<Number>
 *
 *
 */
public class C000GenericInheritPrinciple {
}
