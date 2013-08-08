package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/6/13
 * Time: 6:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanWithBaseClass extends BeanBasicType {

    private List<BeanBasicType> list = new ArrayList<BeanBasicType>();

    private BeanBasicType foo;
    private BeanBasicType bar;

    public BeanWithBaseClass() {
        super();
        foo = new BeanBasicType();
        bar = new BeanBasicType();
    }

    public void setFoo(BeanBasicType foo) {
        this.foo = foo;
    }

    public void setBar(BeanBasicType bar) {
        this.bar = bar;
    }

    public void addListItem(BeanBasicType item) {
        this.list.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeanWithBaseClass)) return false;

        BeanWithBaseClass that = (BeanWithBaseClass) o;

        if (boolean_value != that.boolean_value) return false;
        if (Double.compare(that.double_number, double_number) != 0) return false;
        if (Float.compare(that.float_number, float_number) != 0) return false;
        if (int_number != that.int_number) return false;
        if (long_number != that.long_number) return false;
        if (!Arrays.equals(byte_array, that.byte_array)) return false;
        if (!string.equals(that.string)) return false;
        // String equality is enough
        if (!date.toString().equals(that.date.toString())) return false;
        if (!foo.equals(that.foo)) return false;
        if (!bar.equals(that.bar)) return false;

        if (this.hashCode() != that.hashCode()) return false;

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
        for (BeanBasicType beanBasicType : list) {
            result += beanBasicType.hashCode();
        }
        result = 31 * result + foo.hashCode();
        result = 31 * result + bar.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for (BeanBasicType beanBasicType : list) {
            sb.append(";" + beanBasicType.toString());
        }
        sb.append("{");

        return "BeanWithBaseClass{" +
                "string='" + string + '\'' +
                ", float_number=" + float_number +
                ", double_number=" + double_number +
                ", int_number=" + int_number +
                ", long_number=" + long_number +
                ", boolean_value=" + boolean_value +
                ", date=" + date +
                ", byte_array=" + Arrays.toString(byte_array) +
                ", list=" + sb.toString() +
                ", foo=" + foo.toString() +
                ", bar=" + bar.toString() +
                '}';
    }
}
