package com.example.algorithm.lec1;

import java.util.Arrays;

public class InsertSort {
    public static void sort(double[] array) {
        if (array == null)  return;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if(array[i] > array[j]) continue;

                double tmp = array[i];
                for (int k = i; k > j; k--) {
                    array[k] = array[k-1];
                }
                array[j] = tmp;
                break;
            }
        }
    }

    public static void main(String[] args) {

    }
}
