package com.design.pattern.proxy;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public class Item {
    private int quantity;
    private ProductData product;

    public Item(ProductData product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductData getProduct() {
        return product;
    }

    public void setProduct(ProductData product) {
        this.product = product;
    }
}
