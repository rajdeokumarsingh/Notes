package com.java.examples.basic.classs;


class MyBase {
    public MyBase() {
        test(); // will invoke test() of subclass
    }
    
    void test() {
        System.out.println("MyBase test");
    }
}

public class OverridePrimary extends MyBase {
    public OverridePrimary() {
    }
    
    @Override
    void test() {
        System.out.println("OverridePrimary test");
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        new OverridePrimary();
    }
}
