package com.java.examples.reflection.method;

import static java.lang.System.out;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class R300MethodSpy {
	private static final String fmt = "%24s: %s%n";

	// for the morbidly curious
	<E extends RuntimeException> void genericThrow() throws E {
	}

	public static void main(String... args) {
		final String className = "java.lang.String";
		final String methodName = "compareTo";
		try {
			Class<?> c = Class.forName(className);
			Method[] allMethods = c.getDeclaredMethods();
			for (Method m : allMethods) {
				if (!m.getName().equals(methodName)) {
					continue;
				}
				out.format("%s%n", m.toGenericString());
				out.format(fmt, "ReturnType", m.getReturnType());
				out.format(fmt, "GenericReturnType", m.getGenericReturnType());

				Class<?>[] pType = m.getParameterTypes();
				Type[] gpType = m.getGenericParameterTypes();
				for (int i = 0; i < pType.length; i++) {
					out.format(fmt, "ParameterType", pType[i]);
					out.format(fmt, "GenericParameterType", gpType[i]);
				}

				Class<?>[] xType = m.getExceptionTypes();
				Type[] gxType = m.getGenericExceptionTypes();
				for (int i = 0; i < xType.length; i++) {
					out.format(fmt, "ExceptionType", xType[i]);
					out.format(fmt, "GenericExceptionType", gxType[i]);
				}

				out.format("  Modifiers:  %s%n",
						Modifier.toString(m.getModifiers()));
				out.format("  [ synthetic=%-5b var_args=%-5b bridge=%-5b ]%n",
						m.isSynthetic(), m.isVarArgs(), m.isBridge());
			}

			// production code should handle these exceptions more gracefully
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		}
	}
}
