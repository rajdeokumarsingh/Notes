package com.memcached.test;

import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;

public class TestMemcached {

    public static void main(String[] args) throws IOException {
        MemcachedClient c= new MemcachedClient(new InetSocketAddress("localhost", 11212));

        // Store a value (async) for one hour
        c.set("testKey", 1, "test");


        // Retrieve a value (synchronously).
        Object myObject=c.get("testKey");

        System.out.println("got value:" + (String) myObject);

        try {
            System.out.println("sleep 2 seconds");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("got value: " + (String) myObject);
        System.out.println("got value: " + (String) c.get("unknown"));

    }

}
