package com.java.examples.reflection.filed;

import java.lang.reflect.Field;
import java.util.List;
import static java.lang.System.out;

public class ObtainFieldType<T> {
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
		// TODO Auto-generated method stub
		Class<?> c = Class.forName("com.java.examples.reflection.filed.ObtainFieldType");
		Field f = c.getField("val");
		out.println(f.getType());
		out.println(f.getGenericType());
		
		c = new ObtainFieldType<String>().getClass();
		f = c.getField("val");
		out.println(f.getType());
		out.println(f.getGenericType());

	}

}
