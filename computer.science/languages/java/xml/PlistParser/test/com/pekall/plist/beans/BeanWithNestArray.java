package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/6/13
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
@SuppressWarnings("ALL")
public class BeanWithNestArray {
    private List<Integer> integers = new ArrayList<Integer>();

    List<ArrayList<BeanBasicType>> lists = new ArrayList<ArrayList<BeanBasicType>>();

    public BeanWithNestArray() {
        integers.add(1);
        integers.add(2);
        integers.add(3);

        List<BeanBasicType> list = new ArrayList<BeanBasicType>();
        list.add(new BeanBasicType());
        list.add(new BeanBasicType());

        List<BeanBasicType> list1 = new ArrayList<BeanBasicType>();
        list1.add(new BeanBasicType());
        list1.add(new BeanBasicType());
    }
}
