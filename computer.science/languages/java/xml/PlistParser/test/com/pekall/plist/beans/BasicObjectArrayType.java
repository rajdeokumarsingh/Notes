package com.pekall.plist.beans;

import java.util.Arrays;

/**
 * Test bean class for PlistBeanConverter
 * Only includes basic types
 */
public class BasicObjectArrayType {
    Boolean[] booleans;
    Byte[] bytes;
    Character[] chars;
    Short[] shorts;
    Integer[] integers;
    Long[] longs;
    Float[] floats;
    Double[] doubles;
    BasicType[] objects;

    public Boolean[] getBooleans() {
        return booleans;
    }

    public void setBooleans(Boolean[] booleans) {
        this.booleans = booleans;
    }

    public Byte[] getBytes() {
        return bytes;
    }

    public void setBytes(Byte[] bytes) {
        this.bytes = bytes;
    }

    public Character[] getChars() {
        return chars;
    }

    public void setChars(Character[] chars) {
        this.chars = chars;
    }

    public Short[] getShorts() {
        return shorts;
    }

    public void setShorts(Short[] shorts) {
        this.shorts = shorts;
    }

    public Integer[] getIntegers() {
        return integers;
    }

    public void setIntegers(Integer[] integers) {
        this.integers = integers;
    }

    public Long[] getLongs() {
        return longs;
    }

    public void setLongs(Long[] longs) {
        this.longs = longs;
    }

    public Float[] getFloats() {
        return floats;
    }

    public void setFloats(Float[] floats) {
        this.floats = floats;
    }

    public Double[] getDoubles() {
        return doubles;
    }

    public void setDoubles(Double[] doubles) {
        this.doubles = doubles;
    }

    public BasicType[] getObjects() {
        return objects;
    }

    public void setObjects(BasicType[] objects) {
        this.objects = objects;
    }


    @Override
    public String toString() {
        return "BasicObjectArrayType{" +
                "booleans=" + Arrays.toString(booleans) +
                ", bytes=" + Arrays.toString(bytes) +
                ", chars=" + Arrays.toString(chars) +
                ", shorts=" + Arrays.toString(shorts) +
                ", integers=" + Arrays.toString(integers) +
                ", longs=" + Arrays.toString(longs) +
                ", floats=" + Arrays.toString(floats) +
                ", doubles=" + Arrays.toString(doubles) +
                ", objects=" + Arrays.toString(objects) +
                '}';
    }
}
