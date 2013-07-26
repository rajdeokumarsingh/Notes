package com.java.language.basic;

public class Operator {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        testInstanceOf();
	}

	private static void testInstanceOf() {
		Parent objP = new Parent();
        Parent objC = new Child();

        System.out.println("objP instanceof Parent: "
            + (objP instanceof Parent));
        System.out.println("objP instanceof Child: "
            + (objP instanceof Child));
        System.out.println("objP instanceof MyInterface: "
            + (objP instanceof MyInterface));
        System.out.println("objC instanceof Parent: "
            + (objC instanceof Parent));
        System.out.println("objC instanceof Child: "
            + (objC instanceof Child));
        System.out.println("objC instanceof MyInterface: "
            + (objC instanceof MyInterface));
	}
}

class Parent {}
class Child extends Parent implements MyInterface {}
interface MyInterface {}
