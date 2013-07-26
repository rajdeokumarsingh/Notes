package com.design.pattern.proxy.test;

import com.design.pattern.proxy.MemoryDb;
import com.design.pattern.proxy.ProductData;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午4:54
 * To change this template use File | Settings | File Templates.
 */
public class MemoryDBTest extends TestCase {
    @Override
    protected void setUp() throws Exception {
        ProductData p1 = new ProductData("p1", "s899t", 1000.0);
        ProductData p2 = new ProductData("p2", "s868t", 1100.0);
        ProductData p3 = new ProductData("p3", "a658t", 900.0);
        MemoryDb.storeProductData(p1);
        MemoryDb.storeProductData(p2);
        MemoryDb.storeProductData(p3);
    }

    @Override
    protected void tearDown() throws Exception {
        MemoryDb.clear();
    }

    public void testStoreProductData() throws Exception {
        assertEquals(3, MemoryDb.getProductNumber());

        ProductData p1 = MemoryDb.getProductData("p1");
        assertEquals("s899t", p1.getName());
        assertEquals(1000.0, p1.getPrice());

        ProductData p2 = MemoryDb.getProductData("p2");
        assertEquals("s868t", p2.getName());
        assertEquals(1100.0, p2.getPrice());

        ProductData p3 = MemoryDb.getProductData("p3");
        assertEquals("a658t", p3.getName());
        assertEquals(900.0, p3.getPrice());
    }

    public void testDeleteProductData() throws Exception {
        MemoryDb.deleteProductData("p2");

        assertEquals(2, MemoryDb.getProductNumber());
        assertNull(MemoryDb.getProductData("p2"));
    }

}
