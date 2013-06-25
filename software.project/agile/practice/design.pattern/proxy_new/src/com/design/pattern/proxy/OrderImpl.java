package com.design.pattern.proxy;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午3:23
 * To change this template use File | Settings | File Templates.
 */
public class OrderImpl implements Order {
    /** order id */
    private String oid;
    private long date;
    private int status;

    private ArrayList<Item> items = new ArrayList<Item>();

    /** customer id */
    private String cid;

    public OrderImpl(String oid, long date, int status, String cid) {
        this.oid = oid;
        this.date = date;
        this.status = status;
        this.cid = cid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCid() {
        return cid;
    }

    @Override
    public void addItem(Product p, int quantity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void addItem(ProductData product, int quantity) {
        Item item = new Item(product, quantity);
        items.add(item);
    }

    public Item removeItem(int index) {
        return items.remove(index);
    }

    public double totalPrice() {
        double total = 0;
        for(Item item: items) {
            if(item == null || item.getProduct() == null) continue;
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }
}
