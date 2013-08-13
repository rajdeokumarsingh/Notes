package com.pekall.plist.beans;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Test bean class for PlistBeanConverter
 * Only includes basic types
 */
public class BeanBasicObjectType {
    private Boolean boolean_value;
    private Integer int_number;
    private Long long_number;
    private Float float_number;
    private Double double_number;

    public BeanBasicObjectType() {
    }

    public BeanBasicObjectType(Float float_number, Double double_number,
                               Integer int_number, Long long_number, Boolean boolean_value) {
        this.float_number = float_number;
        this.double_number = double_number;
        this.int_number = int_number;
        this.long_number = long_number;
        this.boolean_value = boolean_value;
    }

    public Float getFloat_number() {
        return float_number;
    }

    public void setFloat_number(Float float_number) {
        this.float_number = float_number;
    }

    public Double getDouble_number() {
        return double_number;
    }

    public void setDouble_number(Double double_number) {
        this.double_number = double_number;
    }

    public Integer getInt_number() {
        return int_number;
    }

    public void setInt_number(Integer int_number) {
        this.int_number = int_number;
    }

    public Long getLong_number() {
        return long_number;
    }

    public void setLong_number(Long long_number) {
        this.long_number = long_number;
    }

    public Boolean getBoolean_value() {
        return boolean_value;
    }

    public void setBoolean_value(Boolean boolean_value) {
        this.boolean_value = boolean_value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeanBasicObjectType)) return false;

        BeanBasicObjectType that = (BeanBasicObjectType) o;

        if (boolean_value != null ? !boolean_value.equals(that.boolean_value) : that.boolean_value != null)
            return false;
        if (double_number != null ? !double_number.equals(that.double_number) : that.double_number != null)
            return false;
        if (float_number != null ? !float_number.equals(that.float_number) : that.float_number != null) return false;
        if (int_number != null ? !int_number.equals(that.int_number) : that.int_number != null) return false;
        if (long_number != null ? !long_number.equals(that.long_number) : that.long_number != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = float_number != null ? float_number.hashCode() : 0;
        result = 31 * result + (double_number != null ? double_number.hashCode() : 0);
        result = 31 * result + (int_number != null ? int_number.hashCode() : 0);
        result = 31 * result + (long_number != null ? long_number.hashCode() : 0);
        result = 31 * result + (boolean_value != null ? boolean_value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BeanBasicObjectType{" +
                "float_number=" + float_number +
                ", double_number=" + double_number +
                ", int_number=" + int_number +
                ", long_number=" + long_number +
                ", boolean_value=" + boolean_value +
                '}';
    }
}
