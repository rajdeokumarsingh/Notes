package com.design.pattern.proxy;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
public class ProductData {
    /** product id */
    private String pid;
    private String name;
    private double price;

    public ProductData(String pid, String name, double price) {
        this.pid = pid;
        this.name = name;
        this.price = price;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ProductData)) return false;

        ProductData p = (ProductData) obj;
        return this.pid.equals(p.getPid());
    }
}
