package com.java.examples.reflection.method;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static java.lang.System.out;

public class MethodTrouble<T> {
	public void lookup(T t) {
		out.println("lookup T!");
	}
	
	// FIXME: Compiling error, Method lookup(Object) has the same erasure 
	// lookup(Object) as another method in type MethodTrouble<T>
//	public void lookup(Object t) {
//	}
	
	public void lookup(Integer t) {
		out.println("lookup Integer!");
	}

	public void find(Integer i) {
	}

	public static void main(String... args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		try {

			String mName = "lookup";
			// String cname = "java.lang.Integer"; // got
			// java.lang.NoSuchMethodException
			String coname = "java.lang.Object";
			Class cArg = Class.forName(coname);

			// When a method is declared with a generic parameter type,
			// the compiler will replace the generic type with its "upper bound"
			// in this case, the upper bound of T is Object
			MethodTrouble<Integer> mt = new MethodTrouble<Integer>();
			Class<?> c = mt.getClass();
			Method m = c.getMethod(mName, cArg);
//			System.out.format("Found:%n  %s%n", m.toGenericString());
			m.invoke(mt, new Integer(20));
			
			cArg = Class.forName("java.lang.Integer");
			m = c.getMethod(mName, cArg);
			m.invoke(mt, new Integer(25));
			

			// production code should handle these exceptions more gracefully
		} catch (NoSuchMethodException x) {
			x.printStackTrace();
		} catch (ClassNotFoundException x) {
			x.printStackTrace();
		}
	}
}
