package com.java.examples.generic;

import java.util.ArrayList;
import java.util.List;

public class C006BoxList {
    public static <U> void addBox(U u, List<Box<U>> boxes) {
        Box<U> box = new Box<U>(u);
        boxes.add(box);
    }

    public static <U> void outputBoxes(List<Box<U>> boxes) {
        for (Box<U> box : boxes) {
            System.out.println("box: " + box.toString());
        }
    }

    public static void main(String[] args) {
        ArrayList<Box<Integer>> boxArrayList = new ArrayList<Box<Integer>>();
        C006BoxList.addBox(20, boxArrayList);
        C006BoxList.addBox(30, boxArrayList);
        C006BoxList.addBox(40, boxArrayList);
        C006BoxList.outputBoxes(boxArrayList);
    }
}
