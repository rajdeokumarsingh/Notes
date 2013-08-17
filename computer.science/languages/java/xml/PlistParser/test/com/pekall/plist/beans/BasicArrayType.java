package com.pekall.plist.beans;

import java.util.Arrays;

/**
 * Test bean class for PlistBeanConverter
 * Only includes basic types
 */
public class BasicArrayType {
    boolean[] booleans;
    byte[] bytes;
    char[] chars;
    short[] shorts;
    int[] integers;
    long[] longs;
    float[] floats;
    double[] doubles;

    public boolean[] getBooleans() {
        return booleans;
    }

    public void setBooleans(boolean[] booleans) {
        this.booleans = booleans;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public char[] getChars() {
        return chars;
    }

    public void setChars(char[] chars) {
        this.chars = chars;
    }

    public short[] getShorts() {
        return shorts;
    }

    public void setShorts(short[] shorts) {
        this.shorts = shorts;
    }

    public int[] getIntegers() {
        return integers;
    }

    public void setIntegers(int[] integers) {
        this.integers = integers;
    }

    public long[] getLongs() {
        return longs;
    }

    public void setLongs(long[] longs) {
        this.longs = longs;
    }

    public float[] getFloats() {
        return floats;
    }

    public void setFloats(float[] floats) {
        this.floats = floats;
    }

    public double[] getDoubles() {
        return doubles;
    }

    public void setDoubles(double[] doubles) {
        this.doubles = doubles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicArrayType that = (BasicArrayType) o;

        if (!Arrays.equals(booleans, that.booleans)) return false;
        if (!Arrays.equals(bytes, that.bytes)) return false;
        if (!Arrays.equals(chars, that.chars)) return false;
        if (!Arrays.equals(doubles, that.doubles)) return false;
        if (!Arrays.equals(floats, that.floats)) return false;
        if (!Arrays.equals(integers, that.integers)) return false;
        if (!Arrays.equals(longs, that.longs)) return false;
        if (!Arrays.equals(shorts, that.shorts)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = booleans != null ? Arrays.hashCode(booleans) : 0;
        result = 31 * result + (bytes != null ? Arrays.hashCode(bytes) : 0);
        result = 31 * result + (chars != null ? Arrays.hashCode(chars) : 0);
        result = 31 * result + (shorts != null ? Arrays.hashCode(shorts) : 0);
        result = 31 * result + (integers != null ? Arrays.hashCode(integers) : 0);
        result = 31 * result + (longs != null ? Arrays.hashCode(longs) : 0);
        result = 31 * result + (floats != null ? Arrays.hashCode(floats) : 0);
        result = 31 * result + (doubles != null ? Arrays.hashCode(doubles) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BasicArrayType{" +
                "booleans=" + Arrays.toString(booleans) +
                ", bytes=" + Arrays.toString(bytes) +
                ", chars=" + Arrays.toString(chars) +
                ", shorts=" + Arrays.toString(shorts) +
                ", integers=" + Arrays.toString(integers) +
                ", longs=" + Arrays.toString(longs) +
                ", floats=" + Arrays.toString(floats) +
                ", doubles=" + Arrays.toString(doubles) +
                '}';
    }
}
