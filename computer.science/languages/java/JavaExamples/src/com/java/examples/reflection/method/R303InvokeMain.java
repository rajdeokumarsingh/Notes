package com.java.examples.reflection.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class R303InvokeMain {
	public static void main(String[] args) {
		args = new String[] { "R302InvokeMethodDeet", "xx", "XX", "XX" };
		try {
//			Class<?> c = Class.forName(args[0]);
			Class<?> c = R302InvokeMethodDeet.class;
			Class[] argTypes = new Class[] { String[].class };
//			Method main = c.getDeclaredMethod("main", argTypes);
			Method main = c.getDeclaredMethod("main", String[].class);
			String[] mainArgs = Arrays.copyOfRange(args, 1, args.length);
			System.out.format("invoking %s.main()%n", c.getName());
			main.invoke(null, (Object) mainArgs);
		} catch (NoSuchMethodException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (InvocationTargetException x) {
			x.printStackTrace();
		}
	}
}
