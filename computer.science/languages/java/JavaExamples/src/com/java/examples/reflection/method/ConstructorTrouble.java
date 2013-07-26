package com.java.examples.reflection.method;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/*
 * Troubleshooting
 The following problems are sometimes encountered by developers when 
 trying to invoke constructors via reflection.
 InstantiationException Due to Missing Zero-Argument Constructor

 The ConstructorTrouble example illustrates what happens when code attempts to 
 create a new instance of a class using Class.newInstance() and there is
 no accessible zero-argument constructor:
 */
public class ConstructorTrouble {

    private ConstructorTrouble(int i) {
    }

    // private ConstructorTrouble() {
    // }

    public static void main(String... args) {
        try {
            Class<?> c = Class
                    .forName("com.java.examples.reflection.method.ConstructorTrouble");
            // Class c = ConstructorTrouble.class;
            Constructor ctor = c.getDeclaredConstructor(int.class);
            ctor.setAccessible(true);

            // Object o = c.newInstance(); // InstantiationException
            Object o = ctor.newInstance(50);

            // production code should handle these exceptions more gracefully
            // } catch (ClassNotFoundException x) {
            // x.printStackTrace();
        } catch (InstantiationException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // } catch (InvocationTargetException e) {
            // // TODO Auto-generated catch block
            // e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
