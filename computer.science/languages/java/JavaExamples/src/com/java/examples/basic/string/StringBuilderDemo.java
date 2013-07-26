package com.java.examples.basic.string;

import static java.lang.System.out;

public class StringBuilderDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        out.println("sb len:" + sb.length() + ", sb capacity: " + sb.capacity());
        sb.append("Greetings");
        out.println("sb len:" + sb.length() + ", sb capacity: " + sb.capacity());
    }

}
