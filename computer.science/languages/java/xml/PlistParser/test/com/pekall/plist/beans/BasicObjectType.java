package com.pekall.plist.beans;

/**
 * Test bean class for PlistBeanConverter
 * Only includes basic types
 */
public class BasicObjectType {
    private Boolean aBoolean;
    private Byte aByte;
    private Character aChar;
    private Short aShort;
    private Integer aInt;
    private Long aLong;
    private Float aFloat;
    private Double aDouble;
    private String aString;

    public Boolean getBoolean() {
        return aBoolean;
    }

    public void setBoolean(Boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public Byte getByte() {
        return aByte;
    }

    public void setByte(Byte aByte) {
        this.aByte = aByte;
    }

    public Character getChar() {
        return aChar;
    }

    public void setChar(Character aChar) {
        this.aChar = aChar;
    }

    public Short getShort() {
        return aShort;
    }

    public void setShort(Short aShort) {
        this.aShort = aShort;
    }

    public Integer getInt() {
        return aInt;
    }

    public void setInt(Integer aInt) {
        this.aInt = aInt;
    }

    public Long getLong() {
        return aLong;
    }

    public void setLong(Long aLong) {
        this.aLong = aLong;
    }

    public Float getFloat() {
        return aFloat;
    }

    public void setFloat(Float aFloat) {
        this.aFloat = aFloat;
    }

    public Double getDouble() {
        return aDouble;
    }

    public void setDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    public String getString() {
        return aString;
    }

    public void setString(String aString) {
        this.aString = aString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BasicObjectType)) return false;

        BasicObjectType that = (BasicObjectType) o;

        if (aBoolean != null ? !aBoolean.equals(that.aBoolean) : that.aBoolean != null) return false;
        if (aByte != null ? !aByte.equals(that.aByte) : that.aByte != null) return false;
        if (aChar != null ? !aChar.equals(that.aChar) : that.aChar != null) return false;
        if (aDouble != null ? !aDouble.equals(that.aDouble) : that.aDouble != null) return false;
        if (aFloat != null ? !aFloat.equals(that.aFloat) : that.aFloat != null) return false;
        if (aInt != null ? !aInt.equals(that.aInt) : that.aInt != null) return false;
        if (aLong != null ? !aLong.equals(that.aLong) : that.aLong != null) return false;
        if (aShort != null ? !aShort.equals(that.aShort) : that.aShort != null) return false;
        if (aString != null ? !aString.equals(that.aString) : that.aString != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = aBoolean != null ? aBoolean.hashCode() : 0;
        result = 31 * result + (aByte != null ? aByte.hashCode() : 0);
        result = 31 * result + (aChar != null ? aChar.hashCode() : 0);
        result = 31 * result + (aShort != null ? aShort.hashCode() : 0);
        result = 31 * result + (aInt != null ? aInt.hashCode() : 0);
        result = 31 * result + (aLong != null ? aLong.hashCode() : 0);
        result = 31 * result + (aFloat != null ? aFloat.hashCode() : 0);
        result = 31 * result + (aDouble != null ? aDouble.hashCode() : 0);
        result = 31 * result + (aString != null ? aString.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BasicObjectType{" +
                "aBoolean=" + aBoolean +
                ", aByte=" + aByte +
                ", aChar=" + aChar +
                ", aShort=" + aShort +
                ", aInt=" + aInt +
                ", aLong=" + aLong +
                ", aFloat=" + aFloat +
                ", aDouble=" + aDouble +
                ", aString='" + aString + '\'' +
                '}';
    }
}
