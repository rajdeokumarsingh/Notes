package com.java.examples.reflection.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static java.lang.System.out;

public class ConstructorTroubleAgain {
    public ConstructorTroubleAgain() {
    }

    public ConstructorTroubleAgain(Integer i) {
    }

    public ConstructorTroubleAgain(Object o) {
        out.format("Constructor passed Object%n");
    }

    public ConstructorTroubleAgain(String s) {
        out.format("Constructor passed String%n");
    }

    public static void main(String... args) {
        String argType = (args.length == 0 ? "" : args[0]);
        try {
//            Class<?> c = Class.forName("ConstructorTroubleAgain");
            Class<?> c = ConstructorTroubleAgain.class;
            argType = "";
            
            if ("".equals(argType)) {
                // IllegalArgumentException: wrong number of arguments
                Object o = c.getConstructor().newInstance("foo");
            } else if ("int".equals(argType)) {
                // NoSuchMethodException - looking for int, have Integer
                Object o = c.getConstructor(int.class);
            } else if ("Object".equals(argType)) {
                // newInstance() does not perform method resolution
                Object o = c.getConstructor(Object.class).newInstance("foo");
            } else {
                assert false;
            }

            // production code should handle these exceptions more gracefully
        } catch (NoSuchMethodException x) {
            x.printStackTrace();
        } catch (InvocationTargetException x) {
            x.printStackTrace();
        } catch (InstantiationException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        }
    }
}
