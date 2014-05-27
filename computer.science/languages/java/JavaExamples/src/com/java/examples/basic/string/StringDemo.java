package com.java.examples.basic.string;

public class StringDemo {

    public static void main(String[] args) {
//        palinrome();
//        convertValue("10.23", "20.38");
        toStringDemo();

        System.out.println("t".substring(0, "t".length()-1));
    }

    private static void toStringDemo() {
        double d = 858.48;
        String s = Double.toString(d);
        
        int dot = s.indexOf('.');
        
        System.out.println(dot + " digits " +
            "before decimal point.");
        System.out.println( (s.length() - dot - 1) +
            " digits after decimal point.");
    }

    private static void convertValue(String... args) {
        // convert strings to numbers
        float a = (Float.valueOf(args[0])).floatValue();
        float b = (Float.valueOf(args[1])).floatValue();

        // do some arithmetic
        System.out.println("a + b = " + (a + b));
        System.out.println("a - b = " + (a - b));
        System.out.println("a * b = " + (a * b));
        System.out.println("a / b = " + (a / b));
        System.out.println("a % b = " + (a % b));
    }

    private static void palinrome() {
        String palindrome = "Dot saw I was Tod";
        int len = palindrome.length();
        char[] tempCharArray = new char[len];
        char[] charArray = new char[len];

        // put original string in an
        // array of chars
        for (int i = 0; i < len; i++) {
            tempCharArray[i] = palindrome.charAt(i);
        }

        // reverse array of chars
        for (int j = 0; j < len; j++) {
            charArray[j] = tempCharArray[len - 1 - j];
        }

        String reversePalindrome = new String(charArray);
        System.out.println(reversePalindrome);
    }

}
