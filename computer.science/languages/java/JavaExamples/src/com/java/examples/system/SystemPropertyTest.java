package com.java.examples.system;

import java.security.KeyStore;
import java.security.Security;

public class SystemPropertyTest {
    public static void main(String[] args) {
//        System.getProperties().list(System.out);
        System.out.println("keystore.type:" + Security.getProperty("keystore.type"));
        System.out.println("keystore.type:" + KeyStore.getDefaultType());
    }
}
