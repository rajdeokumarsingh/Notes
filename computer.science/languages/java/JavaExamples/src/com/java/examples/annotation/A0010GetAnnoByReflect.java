package com.java.examples.annotation;

import java.lang.reflect.Field;

/**
 * Get field annotation from A0011AnnotationClass by reflection
 */
public class A0010GetAnnoByReflect {

    public static void main(String[] args) {
        for (Field field : A0011AnnotationClass.class.getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println("field name: " + field.getName());

            if(field.isAnnotationPresent(PlistControl.class)) {
                PlistControl annotation = field.getAnnotation(PlistControl.class);
                System.out.println("Plist control: " + annotation.toPlistXml());
            }
        }
    }
}
