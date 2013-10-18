package com.java.examples.generic;

/* The most commonly used type parameter names are:
    E - Element (used extensively by the Java Collections Framework)
    K - Key
    N - Number
    T - Type
    V - Value
    S,U,V etc. - 2nd, 3rd, 4th types
 */

/**
 * Generic version of the Box class.
 * @param <T> the first type of the value being boxed
 * @param <S> the second type of the value being boxed
 */
public class C002GenericBox<T, S> {
    private T t;
    private S s;

    public C002GenericBox(T t, S s) {
        this.t = t;
        this.s = s;
    }
    public S getS() {
        return s;
    }
    public void setS(S s) {
        this.s = s;
    }
    public T getT() {
        return t;
    }
    public void setT(T t) {
        this.t = t;
    }

    @Override
    public String toString() {
        return "C002GenericBox{" +
                "t=" + t +
                ", s=" + s +
                '}';
    }

    public static void main(String[] args) {
        C002GenericBox<String, Integer> box = new C002GenericBox<String, Integer>("test", 20);

        System.out.println("T: " + box.getT());
        System.out.println("S: " + box.getS());

        // Here T is Object
        C002GenericBox box1 = new C002GenericBox("raw box", 893);
    }
}
