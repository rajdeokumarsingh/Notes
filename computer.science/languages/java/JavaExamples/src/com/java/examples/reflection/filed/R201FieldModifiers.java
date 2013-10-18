package com.java.examples.reflection.filed;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class R201FieldModifiers {
	volatile int share;
	private int instance;

	class Inner {
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> c = Class
				.forName("com.java.examples.reflection.filed.R201FieldModifiers");
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			System.out.println(f.toGenericString());
			System.out.println(Modifier.toString(f.getModifiers()));
		}
	}

	private static int modifierFromString(String s) {
		int m = 0x0;
		if ("public".equals(s))
			m |= Modifier.PUBLIC;
		else if ("protected".equals(s))
			m |= Modifier.PROTECTED;
		else if ("private".equals(s))
			m |= Modifier.PRIVATE;
		else if ("static".equals(s))
			m |= Modifier.STATIC;
		else if ("final".equals(s))
			m |= Modifier.FINAL;
		else if ("transient".equals(s))
			m |= Modifier.TRANSIENT;
		else if ("volatile".equals(s))
			m |= Modifier.VOLATILE;
		return m;
	}
}
