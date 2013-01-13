package com.java.examples.basic.classs;

class FinalSuper {
    public FinalSuper() {
        test(); // will invoke test() of subclass
    }
    
    void test() {
        System.out.println("FinalSuper test");
    }
}

public class FinalTest extends FinalSuper {
    public FinalTest() {
    }
    
    @Override
    void test() {
        System.out.println("FinalTest test");
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        new FinalTest();
    }
}
