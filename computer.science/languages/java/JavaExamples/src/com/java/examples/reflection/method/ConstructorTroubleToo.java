package com.java.examples.reflection.method;

import java.lang.reflect.InvocationTargetException;
import static java.lang.System.err;

public class ConstructorTroubleToo {
    public ConstructorTroubleToo() {
        throw new RuntimeException("exception in constructor");
    }

    public static void main(String... args) {
        try {
//            Class<?> c = Class.forName("ConstructorTroubleToo");
            Class<?> c = ConstructorTroubleToo.class;
            // Method propagetes any exception thrown by the constructor
            // (including checked exceptions).
//            if (args.length > 0 && args[0].equals("class")) {
            if(true) {
                Object o = c.newInstance(); // can not be caught
            } else {
                Object o = c.getConstructor().newInstance(); // can be caught
            }

            // production code should handle these exceptions more gracefully
        } catch (InstantiationException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        } catch (NoSuchMethodException x) {
            x.printStackTrace();
        } catch (InvocationTargetException x) {
            x.printStackTrace();
            err.format("%n%nCaught exception: %s%n", x.getCause());
        }
    }
}