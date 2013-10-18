package com.java.examples.basic.classs;

import java.util.Calendar;
import java.util.Locale;

import static java.lang.System.out;

public class NumberTest {

    public static void main(String[] args) {
//        basicTest(); 
//        testFormat();
//        basicMath();
//        exponentialTest();
//        trigonometricTest();
//        randomTest();

//        Integer i = 65;
//        out.format("%d, hex %s%n", i, i.toHexString(i));
//        out.format("230, int %s%n", i, i.valueOf("230", 5));
//        out.format("%b", Integer.valueOf(1).equals(Long.valueOf(1)));

//      testMax();
        testMin();
    }

    private static void testMin() {
        // integers
        byte smallestByte = Byte.MIN_VALUE;
        short smallestShort = Short.MIN_VALUE;
        int smallestInteger = Integer.MIN_VALUE;
        long smallestLong = Long.MIN_VALUE;

        // real numbers
        float smallestFloat = Float.MIN_VALUE;
        double smallestDouble = Double.MIN_VALUE;

        // display them all
        System.out.println("The smallest byte value is " + smallestByte);
        System.out.println("The smallest short value is " + smallestShort);
        System.out.println("The smallest integer value is " + smallestInteger);
        System.out.println("The smallest long value is " + smallestLong);

        System.out.println("The smallest float value is " + smallestFloat);
        System.out.println("The smallest double value is " + smallestDouble);
    }

    private static void testMax() {
        //integers
        byte largestByte = Byte.MAX_VALUE;
        short largestShort = Short.MAX_VALUE;
        int largestInteger = Integer.MAX_VALUE;
        long largestLong = Long.MAX_VALUE;

        //real numbers
        float largestFloat = Float.MAX_VALUE;
        double largestDouble = Double.MAX_VALUE;

        //other primitive types
        char aChar = 'S';
        boolean aBoolean = true;

        //Display them all.
        System.out.println("The largest byte value is "
                + largestByte + ".");
        System.out.println("The largest short value is "
                + largestShort + ".");
        System.out.println("The largest integer value is "
                + largestInteger + ".");
        System.out.println("The largest long value is "
                + largestLong + ".");

        System.out.println("The largest float value is "
                + largestFloat + ".");
        System.out.println("The largest double value is "
                + largestDouble + ".");

        if (Character.isUpperCase(aChar)) {
            System.out.println("The character " + aChar
                    + " is uppercase.");
        } else {
            System.out.println("The character " + aChar
                    + " is lowercase.");
        }
        System.out.println("The value of aBoolean is "
                + aBoolean + ".");
    }

    private static void basicTest() {
        out.println(Integer.decode("100"));
        out.println(Integer.parseInt("100"));

        float floatVar = 1.0f;
        int intVar = 5000;
        String stringVar = "test";
        out.format("The value of " + "the float variable is " +
                "%f%nwhile the value of the " + "integer variable is %d%n" +
                "and the string is %s%n", floatVar, intVar, stringVar);
    }

    private static void randomTest() {
        // 0.0 <= Math.random() < 1.0
        int number = (int) (Math.random() * 10);
        System.out.format("random number: %d%n", number);
    }

    private static void trigonometricTest() {
        double degrees = 45.0;
        double radians = Math.toRadians(degrees);

        System.out.format("The value of pi " +
                "is %.4f%n",
                Math.PI);

        System.out.format("The sine of %.1f " +
                "degrees is %.4f%n",
                degrees,
                Math.sin(radians));

        System.out.format("The cosine of %.1f " +
                "degrees is %.4f%n",
                degrees,
                Math.cos(radians));

        System.out.format("The tangent of %.1f " +
                "degrees is %.4f%n",
                degrees,
                Math.tan(radians));

        System.out.format("The arcsine of %.4f " +
                "is %.4f degrees %n",
                Math.sin(radians),
                Math.toDegrees(Math.asin(Math.sin(radians))));

        System.out.format("The arccosine of %.4f " +
                "is %.4f degrees %n",
                Math.cos(radians),
                Math.toDegrees(Math.acos(Math.cos(radians))));

        System.out.format("The arctangent of %.4f " +
                "is %.4f degrees %n",
                Math.tan(radians),
                Math.toDegrees(Math.atan(Math.tan(radians))));
    }

    private static void exponentialTest() {
        double x = 11.635;
        double y = 2.76;

        System.out.printf("The value of " +
                "e is %.4f%n",
                Math.E);

        System.out.printf("exp(%.3f) " +
                "is %.3f%n",
                x, Math.exp(x));

        System.out.printf("log(%.3f) is " +
                "%.3f%n",
                x, Math.log(x));

        System.out.printf("pow(%.3f, %.3f) " +
                "is %.3f%n",
                x, y, Math.pow(x, y));

        System.out.printf("sqrt(%.3f) is " +
                "%.3f%n",
                x, Math.sqrt(x));
    }

    private static void basicMath() {
        double a = -191.635;
        double b = 43.74;
        int c = 16, d = 45;

        System.out.printf("The absolute value " +
                "of %.3f is %.3f%n",
                a, Math.abs(a));

        System.out.printf("The ceiling of " +
                "%.2f is %.0f%n",
                b, Math.ceil(b));

        System.out.printf("The floor of " +
                "%.2f is %.0f%n",
                b, Math.floor(b));

        System.out.printf("The rint of %.2f " +
                "is %.0f%n",
                b, Math.rint(b));

        System.out.printf("The max of %d and " +
                "%d is %d%n",
                c, d, Math.max(c, d));

        System.out.printf("The min of of %d " +
                "and %d is %d%n",
                c, d, Math.min(c, d));
    }

    private static void testFormat() {
        long n = 461012;
        System.out.format("%d%n", n);      //  -->  "461012"
        System.out.format("%08d%n", n);    //  -->  "00461012"
        System.out.format("%+8d%n", n);    //  -->  " +461012"
        System.out.format("%,8d%n", n);    // -->  " 461,012"
        System.out.format("%+,8d%n%n", n); //  -->  "+461,012"

        double pi = Math.PI;

        System.out.format("%f%n", pi);       // -->  "3.141593"
        System.out.format("%.3f%n", pi);     // -->  "3.142"
        System.out.format("%10.3f%n", pi);   // -->  "     3.142"
        System.out.format("%-10.3f%n", pi);  // -->  "3.142"
        System.out.format(Locale.FRANCE,
                "%-10.4f%n%n", pi); // -->  "3,1416"

        Calendar c = Calendar.getInstance();
        System.out.format("%tB %te, %tY%n", c, c, c); // -->  "May 29, 2006"
        System.out.format("%tl:%tM %tp%n", c, c, c);  // -->  "2:34 am"
        System.out.format("%tD%n", c);    // -->  "05/29/06"
    }
}
