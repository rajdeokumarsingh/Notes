
package com.java.examples.basic.string;

public class StringSplit {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String sentence = ";long1;long2;long3;long4;";
        String[] tokens = sentence.split(";");
        for (String str : tokens) {
            System.out.println("Token: " + str);
        }
    }
}
