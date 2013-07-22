package com.java.examples.xml.xstream.simple;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-7-20
 * Time: 下午5:58
 * To change this template use File | Settings | File Templates.
 */
public class PhoneNumber {
    private int code;
    private String number;

    public PhoneNumber() {
        this(0, "");
    }

    public PhoneNumber(int code, String number) {
        this.code = code;
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "code=" + code +
                ", number='" + number + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        if (code != that.code) return false;
        if (!number.equals(that.number)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + number.hashCode();
        return result;
    }
}
