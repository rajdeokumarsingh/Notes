package com.pekall.plist.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * Created with IntelliJ IDEA.
 * User: wjl
 * Date: 13-10-21
 * Time: 下午3:55
 */
public class SkipFieldExclusionStrategy implements ExclusionStrategy {
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        return false;
    }

    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        SkipFiled skipFiled = f.getAnnotation(SkipFiled.class);
        if(skipFiled != null && skipFiled.skip()){
            return true;
        }
        return false;
    }
}
