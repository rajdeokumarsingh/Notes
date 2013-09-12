package com.java.examples.generic;

public class C004GenericMethod {

    public static <K, V> boolean compare(
            C003Pair<K, V> p1, C003Pair<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) &&
                p1.getValue().equals(p2.getValue());
    }

    public static void main(String[] args) {
        C003Pair<String, Integer> p1 = new C003OrderedPair<String, Integer>("key", 20);
        C003Pair<String, Integer> p2 = new C003OrderedPair<String, Integer>("key", 20);
        C003Pair<String, Integer> p3 = new C003OrderedPair<String, Integer>("key", 2320);

        System.out.println("compare p1, p2: " + C004GenericMethod.compare(p1, p2));
        System.out.println("compare p2, p3: " + C004GenericMethod.compare(p2, p3));

    }

}
