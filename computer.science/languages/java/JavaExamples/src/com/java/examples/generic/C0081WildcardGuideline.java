package com.java.examples.generic;

import java.util.ArrayList;
import java.util.List;

/*
Wildcard Guidelines:
    An "in" variable is defined with an upper bounded wildcard, using the extends keyword.
    An "out" variable is defined with a lower bounded wildcard, using the super keyword.
    In the case where the "in" variable can be accessed using methods defined in the Object class, use an unbounded wildcard.
    In the case where the code needs to access the variable as both an "in" and an "out" variable, do not use a wildcard.
 */
public class C0081WildcardGuideline {
    public static void main(String[] args) {
        List<EvenNumber> le = new ArrayList<EvenNumber>();
        List<? extends NaturalNumber> ln = le;  // upper bounder is read-only,
                                                // could be used for input argument
        // ln.add(new NaturalNumber(35));  // compile-time error
        // ln.add(new EvenNumber(34));  // compile-time error

        List<NaturalNumber> naturalNumbers = new ArrayList<NaturalNumber>();
        List<? super EvenNumber> evenNumbers = naturalNumbers;
        evenNumbers.add(new EvenNumber(4));
        // evenNumbers.add(new NaturalNumber(3)); // compile-time error
    }
}


class NaturalNumber {

    private int i;

    public NaturalNumber(int i) { this.i = i; }
    // ...
}

class EvenNumber extends NaturalNumber {

    public EvenNumber(int i) { super(i); }
    // ...
}
