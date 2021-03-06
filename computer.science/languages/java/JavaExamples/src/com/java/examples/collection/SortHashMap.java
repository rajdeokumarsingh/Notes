package com.java.examples.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class SortHashMap {
    /**
     * @param args
     */
    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(20, "test");
        map.put(6, "test1");
        map.put(3, "test2");
        map.put(210, "test3");
        map.put(120, "test4");
        map.put(67, "test5");

        // sort hash map by its keys
        Set<Integer> key = map.keySet();
        ArrayList<Integer> list = new ArrayList<Integer>(key);
        Collections.sort(list);

        for(Integer i:list){
            System.out.println("key: " + i);
            System.out.println("value: " + map.get(i));
        }
    }

}
