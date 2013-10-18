package com.java.examples.reflection.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Locale;

import static java.lang.System.out;
import static java.lang.System.err;

public class R302InvokeMethodDeet<T> {
	private boolean testDeet(Locale l) {
		// getISO3Language() may throw a MissingResourceException
		out.format("Locale = %s, ISO Language Code = %s%n", l.getDisplayName(),
				l.getISO3Language());
		return true;
	}

	private int testFoo(Locale l) {
		return 0;
	}

	private boolean testBar() {
		return true;
	}

	private void testBar(int t) {
		out.format("testBar invoked: %d.%n", t);
	}
	
	public static void main(String... args) {
		// if (args.length != 4) {
		// err.format("Usage: java R302InvokeMethodDeet <classname> <langauge> <country> <variant>%n");
		// return;
		// }
//		args = new String[] { "com.java.examples.reflection.method", "ja", "JP", "JP" };
		args = new String[] { "R302InvokeMethodDeet", "ja", "JP", "JP" };
//		args = new String[] { "R302InvokeMethodDeet", "xx", "XX", "XX" };
		try {
//			Class<?> c = Class.forName(args[0]);
			Class<?> c = R302InvokeMethodDeet.class;
			Object t = c.newInstance();

			Method methodTest = c.getDeclaredMethod("testBar", int.class);
			methodTest.setAccessible(true);
			methodTest.invoke(t, 25);
			Method[] allMethods = c.getDeclaredMethods();
			
			for (Method m : allMethods) {
				String mname = m.getName();
				if (!mname.startsWith("test")
						|| (m.getGenericReturnType() != boolean.class)) {
					continue;
				}
				Type[] pType = m.getGenericParameterTypes();
				if ((pType.length != 1)
						|| Locale.class.isAssignableFrom(pType[0].getClass())) {
					continue;
				}

				out.format("invoking %s()%n", mname);
				try {
					m.setAccessible(true);
					Object o = m.invoke(t,
							new Locale(args[1], args[2], args[3]));
					out.format("%s() returned %b%n", mname, (Boolean) o);

					// Handle any exceptions thrown by method to be invoked.
				} catch (InvocationTargetException x) {
					Throwable cause = x.getCause();
					err.format("invocation of %s failed: %s%n", mname,
							cause.getMessage());
				}
			}

			// production code should handle these exceptions more gracefully
//		} catch (ClassNotFoundException x) {
//			x.printStackTrace();
		} catch (InstantiationException x) {
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			x.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}