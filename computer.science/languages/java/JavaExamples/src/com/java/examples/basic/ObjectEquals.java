package com.java.examples.basic;

public class ObjectEquals {
    int a;

    public ObjectEquals(int a) {
        this.a = a;
    }

    public static void main(String[] args) {

        ObjectEquals o1 = new ObjectEquals(1);
        ObjectEquals o2 = new ObjectEquals(1);

        System.out.println("o1 equals o2: " + o1.equals(o2));
    }
}
