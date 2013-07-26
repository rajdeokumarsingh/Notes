package com.java.examples.reflection.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodTroubleReturns {
	private void drinkMe(int liters) {
		if (liters < 0)
			throw new IllegalArgumentException(
					"I can't drink a negative amount of liquid");
	}

	public static void main(String... args) {
		try {
			MethodTroubleReturns mtr = new MethodTroubleReturns();
			Class<?> c = mtr.getClass();
			Method m = c.getDeclaredMethod("drinkMe", int.class);
			m.invoke(mtr, -1);

			// production code should handle these exceptions more gracefully
		} catch (InvocationTargetException x) {
			Throwable cause = x.getCause();
			System.err.format("drinkMe() failed: %s%n", cause.getMessage());
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
