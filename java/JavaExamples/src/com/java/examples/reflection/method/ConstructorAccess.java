package com.java.examples.reflection.method;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

public class ConstructorAccess {
    public static void main(String... args) {
        try {
            Class cls = Class.forName("java.io.File");
            Constructor[] ctors = cls.getConstructors();
            for(Constructor ctor : ctors) {
                System.out.println("constructor:" + ctor.toGenericString());
                System.out.format("  [ synthetic=%-5b var_args=%-5b ]%n",
                        ctor.isSynthetic(), ctor.isVarArgs());
                System.out.println(Modifier.toString(ctor.getModifiers()));
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
