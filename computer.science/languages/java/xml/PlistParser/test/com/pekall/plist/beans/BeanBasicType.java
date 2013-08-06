package com.pekall.plist.beans;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Test bean class for PlistBeanConverter
 * Only includes basic types
 */
public class BeanBasicType {

    String string = "";
    float float_number;
    double double_number;
    int int_number;
    long long_number;
    boolean boolean_value = true;
    Date date;
    byte[] byte_array;

    public BeanBasicType() {
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 1, 13, 9, 28, 49);
        date = cal.getTime();

        byte_array = new byte[50];
        for (int i = 0; i < byte_array.length; i++) {
            byte_array[i] = (byte) i;
        }
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public float getFloatNumber() {
        return float_number;
    }

    public void setFloatNumber(float float_number) {
        this.float_number = float_number;
    }

    public double getDoubleNumber() {
        return double_number;
    }

    public void setDoubleNumber(double double_number) {
        this.double_number = double_number;
    }

    public int getIntNumber() {
        return int_number;
    }

    public void setIntNumber(int int_number) {
        this.int_number = int_number;
    }

    public long getLongNumber() {
        return long_number;
    }

    public void setLongNumber(long long_number) {
        this.long_number = long_number;
    }

    public boolean isBooleanValue() {
        return boolean_value;
    }

    public void setBooleanValue(boolean boolean_value) {
        this.boolean_value = boolean_value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte[] getByteArray() {
        return byte_array;
    }

    public void setByteArray(byte[] byte_array) {
        this.byte_array = byte_array;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeanBasicType)) return false;

        BeanBasicType that = (BeanBasicType) o;

        if (boolean_value != that.boolean_value) return false;
        if (Double.compare(that.double_number, double_number) != 0) return false;
        if (Float.compare(that.float_number, float_number) != 0) return false;
        if (int_number != that.int_number) return false;
        if (long_number != that.long_number) return false;
        if (!Arrays.equals(byte_array, that.byte_array)) return false;
        if (!string.equals(that.string)) return false;

        // String equality is enough
        if (!date.toString().equals(that.date.toString())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = string.hashCode();
        result = 31 * result + (float_number != +0.0f ? Float.floatToIntBits(float_number) : 0);
        temp = Double.doubleToLongBits(double_number);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + int_number;
        result = 31 * result + (int) (long_number ^ (long_number >>> 32));
        result = 31 * result + (boolean_value ? 1 : 0);
        result = 31 * result + Arrays.hashCode(byte_array);
        // String equality is enough
        result = 31 * result + date.toString().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BeanBasicType{" +
                "string='" + string + '\'' +
                ", float_number=" + float_number +
                ", double_number=" + double_number +
                ", int_number=" + int_number +
                ", long_number=" + long_number +
                ", boolean_value=" + boolean_value +
                ", date=" + date +
                ", byte_array=" + Arrays.toString(byte_array) +
                '}';
    }
}
