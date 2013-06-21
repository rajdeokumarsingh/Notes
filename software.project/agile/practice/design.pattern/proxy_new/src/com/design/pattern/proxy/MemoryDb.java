package com.design.pattern.proxy;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
public class MemoryDb {
    static private HashMap<String, ProductData> products = new HashMap<String, ProductData>();
    static private HashMap<String, OrderData> orders = new HashMap<String, OrderData>();

    static private int oidBase;

    public static void storeProductData(ProductData p) {
        products.put(p.getPid(), p);
    }

    public static void deleteProductData(String pid) {
        products.remove(pid);
    }

    public static ProductData getProductData(String pid) {
       return products.get(pid);
    }

    public static void clear() {
        products.clear();
    }

    public static int getProductNumber() {
        return products.size();
    }

    public static OrderData newOrder(String cid) {
        OrderData od = new OrderData(cid, generateOid());
        orders.put(od.getOid(), od);

        return od;
    }

    private static String generateOid() {
        return "oid_" + oidBase++;
    }
}
