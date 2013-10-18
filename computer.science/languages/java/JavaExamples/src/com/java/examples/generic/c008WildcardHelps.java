package com.java.examples.generic;

import java.util.List;

public class c008WildcardHelps {

    void foo(List<?> i) {
        // compile error
        // i.add(i.get(0))
        // i.set(0, i.get(0));
        fooHelper(i);
    }

    // Helper method created so that the wildcard can be captured
    // through type inference.
    private <T> void fooHelper(List<T> l) {
        l.set(0, l.get(0));
    }



    /*
    void swapFirst(List<? extends Number> l1, List<? extends Number> l2) {
        Number temp = l1.get(0);
        l1.set(0, l2.get(0)); // expected a CAP#1 extends Number,
                                // got a CAP#2 extends Number;
                                // same bound, but different types
        l2.set(0, temp);	    // expected a CAP#1 extends Number,
                                // got a Number
    }
    */
}
