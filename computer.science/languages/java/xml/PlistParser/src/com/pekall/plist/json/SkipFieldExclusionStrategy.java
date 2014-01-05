package com.pekall.plist.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Skip strategy for Xml Parsing
 */
public class SkipFieldExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        SkipFiled skipFiled = f.getAnnotation(SkipFiled.class);
        return skipFiled != null && skipFiled.skip();
    }
}
