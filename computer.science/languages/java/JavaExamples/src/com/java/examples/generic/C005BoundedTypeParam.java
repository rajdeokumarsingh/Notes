package com.java.examples.generic;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-8-10
 * Time: 下午4:17
 * To change this template use File | Settings | File Templates.
 */
public class C005BoundedTypeParam<U extends Number>{

    private U number;

    public C005BoundedTypeParam(U number) {
        this.number = number;
    }

    public U getNumber() {
        return number;
    }

    public void setNumber(U number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "C005BoundedTypeParam{" +
                "number=" + number +
                '}';
    }

    public static void main(String[] args) {
        C005BoundedTypeParam<Integer> p = new C005BoundedTypeParam<Integer>(50);
        System.out.println("p is: " + p.toString());

        // compiling error
        // C005BoundedTypeParam<String> p2 = new C005BoundedTypeParam<String>("test");
    }
}
