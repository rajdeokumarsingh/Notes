package com.pekall.plist.beans;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/6/13
 * Time: 5:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanComboType {
    private String string;
    private float float_number;
    private double double_number;
    private int int_number;
    private long long_number;
    private boolean boolean_value;
    private Date date;
    private byte[] byte_array;

    private List<BeanBasicType> list;

    private BeanBasicType foo;
    private BeanBasicType bar;

    public BeanComboType() {
        /*
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 1, 13, 9, 28, 49);
        date = cal.getTime();

        byte_array = new byte[50];
        for (int i = 0; i < byte_array.length; i++) {
            byte_array[i] = (byte) i;
        }
        */
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

    public void setFoo(BeanBasicType foo) {
        this.foo = foo;
    }

    public void setBar(BeanBasicType bar) {
        this.bar = bar;
    }

    public void addListItem(BeanBasicType item) {
        if (list == null) {
            list = new ArrayList<BeanBasicType>();
        }
        this.list.add(item);
    }

    public List<BeanBasicType> getList() {
        return list;
    }

    public void setList(List<BeanBasicType> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeanComboType)) return false;

        BeanComboType comboType = (BeanComboType) o;

        if (boolean_value != comboType.boolean_value) return false;
        if (Double.compare(comboType.double_number, double_number) != 0) return false;
        if (Float.compare(comboType.float_number, float_number) != 0) return false;
        if (int_number != comboType.int_number) return false;
        if (long_number != comboType.long_number) return false;
        if (bar != null ? !bar.equals(comboType.bar) : comboType.bar != null) return false;
        if (!Arrays.equals(byte_array, comboType.byte_array)) return false;
        if (date != null ? !date.equals(comboType.date) : comboType.date != null) return false;
        if (foo != null ? !foo.equals(comboType.foo) : comboType.foo != null) return false;
        if (list != null ? !list.equals(comboType.list) : comboType.list != null) return false;
        if (string != null ? !string.equals(comboType.string) : comboType.string != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = string != null ? string.hashCode() : 0;
        result = 31 * result + (float_number != +0.0f ? Float.floatToIntBits(float_number) : 0);
        temp = Double.doubleToLongBits(double_number);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + int_number;
        result = 31 * result + (int) (long_number ^ (long_number >>> 32));
        result = 31 * result + (boolean_value ? 1 : 0);
        // String equality is enough
        result = 31 * result + (date != null ? date.toString().hashCode() : 0);
        result = 31 * result + (byte_array != null ? Arrays.hashCode(byte_array) : 0);
        // result = 31 * result + (list != null ? list.hashCode() : 0);
        if (list != null) {
            for (BeanBasicType beanBasicType : list) {
                result += beanBasicType.hashCode();
            }
        }
        result = 31 * result + (foo != null ? foo.hashCode() : 0);
        result = 31 * result + (bar != null ? bar.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (BeanBasicType beanBasicType : list) {
            sb.append(";" + beanBasicType.toString());
        }
        sb.append("}");

        return "BeanComboType{" +
                "string='" + string + '\'' +
                ", float_number=" + float_number +
                ", double_number=" + double_number +
                ", int_number=" + int_number +
                ", long_number=" + long_number +
                ", boolean_value=" + boolean_value +
                ", date=" + date +
                ", byte_array=" + Arrays.toString(byte_array) +
                ", list=" + sb.toString() +
                ", foo=" + foo +
                ", bar=" + bar +
                '}';
    }
}
