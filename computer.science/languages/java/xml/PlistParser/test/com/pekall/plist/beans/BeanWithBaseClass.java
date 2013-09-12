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

    private List<BeanBasicType> list;

    private BeanBasicType foo;
    private BeanBasicType bar;

    public BeanWithBaseClass() {
        super();
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

    public BeanBasicType getFoo() {
        return foo;
    }

    public BeanBasicType getBar() {
        return bar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeanWithBaseClass)) return false;
        if (!super.equals(o)) return false;

        BeanWithBaseClass that = (BeanWithBaseClass) o;

        if (bar != null ? !bar.equals(that.bar) : that.bar != null) return false;
        if (foo != null ? !foo.equals(that.foo) : that.foo != null) return false;
        // if (list != null ? !list.equals(that.list) : that.list != null) return false;
        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        if (list != null) {
            for (BeanBasicType basicType : list) {
                result += basicType.hashCode();
            }
        }
        // result = 31 * result + (list != null ? list.hashCode() : 0);
        result = 31 * result + (foo != null ? foo.hashCode() : 0);
        result = 31 * result + (bar != null ? bar.hashCode() : 0);
        return result;
    }
}
