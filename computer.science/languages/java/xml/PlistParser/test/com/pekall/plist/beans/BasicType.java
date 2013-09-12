package com.pekall.plist.beans;

/**
 * Test bean class for PlistBeanConverter
 * Only includes basic types
 */
public class BasicType {
    boolean aBoolean;
    byte aByte;
    char aChar;
    short aShort;
    int anInt;
    long aLong;
    float aFloat;
    double aDouble;

    public boolean isBoolean() {
        return aBoolean;
    }

    public void setBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public byte getByte() {
        return aByte;
    }

    public void setByte(byte aByte) {
        this.aByte = aByte;
    }

    public char getChar() {
        return aChar;
    }

    public void setChar(char aChar) {
        this.aChar = aChar;
    }

    public short getShort() {
        return aShort;
    }

    public void setShort(short aShort) {
        this.aShort = aShort;
    }

    public int getInt() {
        return anInt;
    }

    public void setInt(int anInt) {
        this.anInt = anInt;
    }

    public long getLong() {
        return aLong;
    }

    public void setLong(long aLong) {
        this.aLong = aLong;
    }

    public float getFloat() {
        return aFloat;
    }

    public void setFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    public double getDouble() {
        return aDouble;
    }

    public void setDouble(double aDouble) {
        this.aDouble = aDouble;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicType basicType = (BasicType) o;

        if (aBoolean != basicType.aBoolean) return false;
        if (aByte != basicType.aByte) return false;
        if (aChar != basicType.aChar) return false;
        if (Double.compare(basicType.aDouble, aDouble) != 0) return false;
        if (Float.compare(basicType.aFloat, aFloat) != 0) return false;
        if (aLong != basicType.aLong) return false;
        if (aShort != basicType.aShort) return false;
        if (anInt != basicType.anInt) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (aBoolean ? 1 : 0);
        result = 31 * result + (int) aByte;
        result = 31 * result + (int) aChar;
        result = 31 * result + (int) aShort;
        result = 31 * result + anInt;
        result = 31 * result + (int) (aLong ^ (aLong >>> 32));
        result = 31 * result + (aFloat != +0.0f ? Float.floatToIntBits(aFloat) : 0);
        temp = Double.doubleToLongBits(aDouble);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "BasicType{" +
                "aBoolean=" + aBoolean +
                ", aByte=" + aByte +
                ", aChar=" + aChar +
                ", aShort=" + aShort +
                ", anInt=" + anInt +
                ", aLong=" + aLong +
                ", aFloat=" + aFloat +
                ", aDouble=" + aDouble +
                '}';
    }
}
