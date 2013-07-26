package com.java.examples.reflection.filed;

import java.lang.reflect.Field;

class BaseClass {
	String baseString = "base string";

	void printFields() {
		for (Field f : this.getClass().getDeclaredFields()) {
			System.out.println("field name: " + f.getName() + ", type: "
					+ f.getType());
			try {
				if (boolean.class.equals(f.getType())) {
					System.out.println(f.getBoolean(this));
					continue;
				}
				if (byte.class.equals(f.getType())) {
					System.out.println(f.getByte(this));
					continue;
				}
				if (char.class.equals(f.getType())) {
					System.out.println(f.getChar(this));
					continue;
				}
				if (short.class.equals(f.getType())) {
					System.out.println(f.getShort(this));
					continue;
				}
				if (int.class.equals(f.getType())) {
					System.out.println(f.getInt(this));
					continue;
				}
				if (long.class.equals(f.getType())) {
					System.out.println(f.getLong(this));
					continue;
				}
				if (float.class.equals(f.getType())) {
					System.out.println(f.getFloat(this));
					continue;
				}
				if (double.class.equals(f.getType())) {
					System.out.println(f.getDouble(this));
					continue;
				}

				if (Object.class.isAssignableFrom(f.getType())) {
					System.out.println(f.get(this));
					continue;
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public class GetFieldValue extends BaseClass {
	boolean bf = true;
	char cf = 'q';
	byte btf = 'b';
	short sf = 20;
	int iif = 1;
	long lf = 1000l;
	float ff = 334.9f;
	double df = 343.2d;

	String str1 = "test";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BaseClass gf = new GetFieldValue();
		gf.printFields();
//		for (Field f : gf.getClass().getDeclaredFields()) {
//			System.out.println("field name: " + f.getName() + ", type: "
//					+ f.getType());
//			try {
//				if (boolean.class.equals(f.getType())) {
//					System.out.println(f.getBoolean(gf));
//					continue;
//				}
//				if (byte.class.equals(f.getType())) {
//					System.out.println(f.getByte(gf));
//					continue;
//				}
//				if (char.class.equals(f.getType())) {
//					System.out.println(f.getChar(gf));
//					continue;
//				}
//				if (short.class.equals(f.getType())) {
//					System.out.println(f.getShort(gf));
//					continue;
//				}
//				if (int.class.equals(f.getType())) {
//					System.out.println(f.getInt(gf));
//					continue;
//				}
//				if (long.class.equals(f.getType())) {
//					System.out.println(f.getLong(gf));
//					continue;
//				}
//				if (float.class.equals(f.getType())) {
//					System.out.println(f.getFloat(gf));
//					continue;
//				}
//				if (double.class.equals(f.getType())) {
//					System.out.println(f.getDouble(gf));
//					continue;
//				}
//
//				if (Object.class.isAssignableFrom(f.getType())) {
//					System.out.println(f.get(gf));
//					continue;
//				}
//			} catch (IllegalArgumentException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
}
