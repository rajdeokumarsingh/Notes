package com.java.examples.reflection.filed;

import java.lang.reflect.Field;
import java.util.List;
import static java.lang.System.out;

public class R200ObtainFieldType<T> {
	public boolean[][] b = { { false, false }, { true, true } };
	public String name = "Alice";
	public List<Integer> list;
	public T val;

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SecurityException, NoSuchFieldException {

		Class<?> c = Class.forName("com.java.examples.reflection.filed.R200ObtainFieldType");

        for (Field f : c.getDeclaredFields()) {
            System.out.format("Name: %s%n", f.getName());
            System.out.format("Type: %s%n", f.getType());
            System.out.format("GenericType: %s%n", f.getGenericType());
        }
	}
}
