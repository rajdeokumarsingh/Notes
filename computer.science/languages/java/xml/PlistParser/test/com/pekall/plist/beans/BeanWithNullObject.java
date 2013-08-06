package com.pekall.plist.beans;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/6/13
 * Time: 7:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanWithNullObject {
    private BeanBasicType foo;
    private BeanBasicType bar;

    public BeanWithNullObject() {
        foo = new BeanBasicType();
    }

    public BeanBasicType getFoo() {
        return foo;
    }

    public void setFoo(BeanBasicType foo) {
        this.foo = foo;
    }

    public BeanBasicType getBar() {
        return bar;
    }

    public void setBar(BeanBasicType bar) {
        this.bar = bar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeanWithNullObject)) return false;

        BeanWithNullObject that = (BeanWithNullObject) o;

        if (bar != null && !bar.equals(that.bar)) return false;
        if (foo != null && !foo.equals(that.foo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        if (foo != null) {
            result = 31 * result + foo.hashCode();
        }
        if (bar != null) {
            result = 31 * result + bar.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        return "BeanWithNullObject{" +
                "foo=" + (foo != null ? foo.toString() : "") +
                ", bar=" + (bar != null ? bar.toString() : "") +
                '}';
    }
}
