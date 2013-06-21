package com.design.pattern.proxy;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午5:13
 * To change this template use File | Settings | File Templates.
 */
public class ProductProxy implements Product {
    private String pid;
    public ProductProxy(String pid) {
       this.pid = pid;
    }
    @Override
    public String getPid() {
        return pid;
    }

    @Override
    public String getName() {
        ProductData p = MemoryDb.getProductData(pid);
        if(p == null) return null;

        return p.getName();
    }

    @Override
    public double getPrice() {
        ProductData p = MemoryDb.getProductData(pid);
        if(p == null) return -1.0;

        return p.getPrice();
    }
}
