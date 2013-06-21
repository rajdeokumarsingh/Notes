package com.design.pattern.proxy;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午5:11
 * To change this template use File | Settings | File Templates.
 */
public class ProductImpl implements Product {
    /** product id */
    private String pid;
    private String name;
    private double price;

    public ProductImpl(String pid, String name, double price) {
        this.pid = pid;
        this.name = name;
        this.price = price;
    }

    @Override
    public String getPid() {
        return pid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }
}
