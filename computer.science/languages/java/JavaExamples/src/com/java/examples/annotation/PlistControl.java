package com.java.examples.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Definition of annotation
 * Must add @RetentionPolicy and @Target, otherwise, you can not get it by reflection
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PlistControl {
    boolean toPlistXml() default true;
}
