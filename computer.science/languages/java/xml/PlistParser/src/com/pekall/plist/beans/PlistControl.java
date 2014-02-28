package com.pekall.plist.beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SuppressWarnings({"UnusedDeclaration", "BooleanMethodIsAlwaysInverted"})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PlistControl {
    boolean toPlistXml() default true;
}
