package com.java.examples.basic.classs;

import java.util.Random;

public class RandomBasic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random r = new Random(20);
		for(int i=0;i<10;i++) {
			System.out.println(r.nextInt(100));
		}
		
		System.out.println(Long.MIN_VALUE + "~" + Long.MAX_VALUE); 
		
	}
}
