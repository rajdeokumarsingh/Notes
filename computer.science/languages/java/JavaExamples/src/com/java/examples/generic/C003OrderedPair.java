package com.java.examples.generic;

public class C003OrderedPair<K, V> implements C003Pair<K, V> {
    private K key;
    private V value;

    public C003OrderedPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "C003OrderedPair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }

    public static void main(String[] args) {
        C003OrderedPair<String, Integer> p1 =
                new C003OrderedPair<String, Integer>("key1", 20);
        C003OrderedPair<String, Integer> p2 =
                new C003OrderedPair<String, Integer>("key2", 50);
        C003OrderedPair<String, C002GenericBox<String, Integer>> p3 =
                new C003OrderedPair<String, C002GenericBox<String, Integer>>(
                        "key3", new C002GenericBox<String, Integer>("box1", 789)
                );

        System.out.println("p1: " + p1.toString());
        System.out.println("p2: " + p2.toString());
        System.out.println("p3: " + p3.toString());
    }
}
