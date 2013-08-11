package com.java.examples.generic;

class A { /* ... */ }
interface B { /* ... */ }
interface C { /* ... */ }

// If one of the bounds is a class, it must be specified first.
class D <T extends A & B & C> { /* ... */ }

// compile-time error
// class E <T extends B & A & C> { /* ... */ }  // compile-time error

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-8-11
 * Time: 上午8:21
 * To change this template use File | Settings | File Templates.
 */
public class C005MultipleBoundedTypeParam {

}
