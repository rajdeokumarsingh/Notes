package com.design.pattern.proxy;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午3:21
 * To change this template use File | Settings | File Templates.
 */
public class Customer {
    /** customer id */
    private String cid;
    private String name;
    private String address;

    private ArrayList<OrderImpl> orders = new ArrayList<OrderImpl>();

    public Customer(String cid, String name, String address) {
        this.cid = cid;
        this.name = name;
        this.address = address;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addOrder(OrderImpl o) {
        orders.add(o);
    }

    public OrderImpl removeOrder(int index) {
        return orders.remove(index);
    }
}
