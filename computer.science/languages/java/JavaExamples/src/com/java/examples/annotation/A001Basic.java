package com.java.examples.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class A001Basic {

    public static void main(String[] args) {
        for (Field field : TestAnnotation.class.getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println("field name: " + field.getName());

            if(field.isAnnotationPresent(PlistControl.class)) {
                PlistControl annotation = field.getAnnotation(PlistControl.class);
                System.out.println("Plist control: " + annotation.toPlistXml());

                /*
                Annotation[] annotations = field.getDeclaredAnnotations();
                System.out.println("field annotations length: " + annotations.length);

                for (Annotation an : annotations) {
                    if(PlistControl.class.equals(an.annotationType())) {
                        System.out.println("Plist control: " + an.toString());
                    }
                }
                */
            }
        }
    }

}
