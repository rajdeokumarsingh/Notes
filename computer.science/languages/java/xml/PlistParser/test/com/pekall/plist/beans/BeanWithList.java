package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/6/13
 * Time: 5:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanWithList {
    private List<BeanBasicType> list = new ArrayList<BeanBasicType>();

    public BeanWithList() {
    }

    public BeanWithList(List<BeanBasicType> list) {
        this.list = list;
    }

    public List<BeanBasicType> getList() {
        return list;
    }

    public void setList(List<BeanBasicType> list) {
        this.list = list;
    }

    public void addListItem(BeanBasicType item) {
        this.list.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BeanWithList)) return false;

        BeanWithList that = (BeanWithList) o;

        if (this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (BeanBasicType beanBasicType : list) {
            result += beanBasicType.hashCode();
        }
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
        return "BeanWithList{" +
                "list=" + list +
                '}';
    }
}
