package com.design.pattern.proxy.test;

import com.design.pattern.proxy.OrderImpl;
import com.design.pattern.proxy.ProductData;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午4:21
 * To change this template use File | Settings | File Templates.
 */
public class OrderTest extends TestCase {
    public void testTotalPrice() throws Exception {
        ProductData p1 = new ProductData("p1", "s899t", 1000.0);
        ProductData p2 = new ProductData("p2", "s868t", 1100.0);

        OrderImpl order = new OrderImpl("o001", 111, 111, "c001");
        order.addItem(p1, 2);
        assertEquals(2000.0, order.totalPrice());

        order.addItem(p2, 3);
        assertEquals(5300.0, order.totalPrice());
    }
}
