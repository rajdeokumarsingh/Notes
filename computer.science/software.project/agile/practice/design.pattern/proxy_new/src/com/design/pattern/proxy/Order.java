package com.design.pattern.proxy;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午5:58
 * To change this template use File | Settings | File Templates.
 */
public interface Order {
    public String getCid();
    public void addItem(Product p, int quantity);
    public double totalPrice();
}
