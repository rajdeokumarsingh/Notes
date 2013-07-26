package com.java.examples.reflection.method;

import java.io.Console;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;

public class ConsoleCharset {
    public static void main(String... args) {
        Constructor[] ctors = Console.class.getDeclaredConstructors();
        Constructor ctor = null;
//        for(Constructor c : ctors) {
//            if(c.getGenericParameterTypes().length == 0) {
//                ctor = c;
//                break;
//            }
//        }
        
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
            break;
        }
        
        try {
//            ctor = Console.class.getDeclaredConstructor(null);
            
            ctor.setAccessible(true);
            Console con = (Console) ctor.newInstance();
            Field f = con.getClass().getDeclaredField("cs");
            f.setAccessible(true);
            
            System.out.format("Console charset         :  %s%n", f.get(con));
            System.out.format("Charset.defaultCharset():  %s%n",
                   Charset.defaultCharset());

            
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
        }
    }
}
