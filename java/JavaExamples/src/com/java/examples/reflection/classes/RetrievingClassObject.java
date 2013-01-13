package com.java.examples.reflection.classes;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class RetrievingClassObject {

	enum E { A, B };
	
	static Object o = new Object() {
		public void m() {}
	};
	
	public class A {};
	class B {};
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 */
	public static void main(String[] args) throws ClassNotFoundException, SecurityException, NoSuchFieldException {
//		testGetClass();
//		testDotClass();
//		testClassForName();
//		testSuperClass();
//		testDeclaringClass();
//		System.out.println(o.getClass().getEnclosingClass());
		testGetClasses();
		
		System.out.println("end");
	}

	private static void testGetClasses() {
		Class<?>[] cs = new RetrievingClassObject().getClass().getDeclaredClasses();
		for(Class<?> ci : cs) {
			System.out.println(ci);
		}
		System.out.println("========================================");
		cs = new RetrievingClassObject().getClass().getClasses();
		for(Class<?> ci : cs) {
			System.out.println(ci);
		}
	}

	private static void testDeclaringClass() throws NoSuchFieldException {
		Field f = System.class.getField("out");
		System.out.println(f.getDeclaringClass());
	}

	private static void testSuperClass() {
		System.out.println(java.lang.String.class.getSuperclass());
		Class<?>[] cs = java.lang.String.class.getClasses();
		for(Class<?> ci : cs) {
			System.out.println(ci);
		}
	}

	private static void testClassForName() throws ClassNotFoundException {
		Class<?> c4 = Class.forName("com.java.examples.reflection.classes.RetrievingClassObject");
		System.out.println(c4);
	}

	private static void testDotClass() {
		System.out.println(String.class);
		System.out.println(boolean.class);
		System.out.println(java.io.PrintStream.class);
		System.out.println(int [][][].class);
	}

	private static void testGetClass() {
		Class<?> c = "foo".getClass();
		System.out.println(c.toString());
		
//		Class c2 = System.console().getClass();
		
		Class<?> c1 = E.A.getClass();
		System.out.println(c1.toString());
		
		byte[] bytes = new byte[1024];
		Class<?> c2 = bytes.getClass();
		System.out.println(c2.toString());
		
		Set<String> s = new HashSet<String>();
		Class<?> c3 = s.getClass();
		System.out.println(c3.toString());
	}

}
