package com.java.language.basic;

public class Array {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		declareAndPrintArray();
        
        copyArray();
	}

	private static void declareAndPrintArray() {
		int[] anArray;
		
		// allocates memory for 10 integers
        anArray = new int[10];
           
        // initialize first element
        anArray[0] = 100;
        // initialize second element
        anArray[1] = 200;
        // etc.
        anArray[2] = 300;
        anArray[3] = 400;
        anArray[4] = 500;
        anArray[5] = 600;
        anArray[6] = 700;
        anArray[7] = 800;
        anArray[8] = 900;
        anArray[9] = 1000;

        for (int a: anArray) {
        	System.out.println("Array element: " + a);
        }
	}

	private static void copyArray() {
		char[] copyFrom = { 'd', 'e', 'c', 'a', 'f', 'f', 'e',
			    'i', 'n', 'a', 't', 'e', 'd' };
        char[] copyTo = new char[copyFrom.length];

        System.arraycopy(copyFrom, 0, copyTo, 0, copyFrom.length);
        System.out.println(new String(copyTo));
	}
}
