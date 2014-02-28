package com.java.examples.basic.string;

public class EmptyStringMatch {
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("whether".contains(""));
        System.out.println("whether".startsWith(""));
        System.out.println("whether".endsWith(""));

        String contactsId = null;
        StringBuilder sb = new StringBuilder();
        sb.append("contactsId: " + contactsId);

        System.out.println(sb.toString());
    }

}
