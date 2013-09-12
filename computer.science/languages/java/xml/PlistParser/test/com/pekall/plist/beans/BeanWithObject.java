package com.pekall.plist.beans;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/6/13
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanWithObject {
    private BeanBasicType foo;
    private BeanBasicType bar;

    public BeanWithObject() {
        this(new BeanBasicType(), new BeanBasicType());
    }

    public BeanWithObject(BeanBasicType foo, BeanBasicType bar) {
        this.foo = foo;
        this.bar = bar;
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
        if (!(o instanceof BeanWithObject)) return false;

        BeanWithObject that = (BeanWithObject) o;

        if (!bar.equals(that.bar)) return false;
        if (!foo.equals(that.foo)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = foo.hashCode();
        result = 31 * result + bar.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "BeanWithObject{" +
                "foo=" + foo.toString() +
                ", bar=" + bar.toString() +
                '}';
    }
}
