package com.java.examples.reflection.method;

import java.lang.reflect.Method;

public class MethodTroubleToo {
	public void ping() {
		System.out.format("PONG!%n");
	}

	public static void main(String... args) {
		try {
			int option = 4;

			MethodTroubleToo mtt = new MethodTroubleToo();
			Method m = MethodTroubleToo.class.getMethod("ping");

			// switch (Integer.parseInt(args[0])) {
			switch (option) {
			case 0:
				m.invoke(mtt); // works
				break;
			case 1:
				m.invoke(mtt, null); // works (expect compiler warning)
				break;
			case 2:
				Object arg2 = null;
				m.invoke(mtt, arg2); // IllegalArgumentException
				break;
			case 3:
				m.invoke(mtt, new Object[0]); // works
				break;
			case 4:
				Object arg4 = new Object[0];
				m.invoke(mtt, arg4); // IllegalArgumentException
				break;
			default:
				System.out.format("Test not found%n");
			}

			// production code should handle these exceptions more gracefully
		} catch (Exception x) {
			x.printStackTrace();
		}
	}
}
