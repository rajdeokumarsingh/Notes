package com.java.examples.basic;

public class EndianTest {
    public static void main(String[] args) {
        System.out.print(isLittleEndian());
    }

    static boolean isLittleEndian() {
        if ((byte) 0x1234 == 0x34) {
            return true;
        }
        return false;
    }
}
