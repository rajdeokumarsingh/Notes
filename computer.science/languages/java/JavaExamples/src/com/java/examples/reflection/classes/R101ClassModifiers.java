package com.java.examples.reflection.classes;

import static java.lang.System.out;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class R101ClassModifiers {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        // spyClass("java.util.concurrent.ConcurrentNavigableMap");
        // spyClass("java.security.Identity");
        // List<String> stringList = new ArrayList<String>();
        // spyClass(stringList.getClass());
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        spyClass(map.getClass());
    }

	static void spyClass(String className) {
        Class<?> c = null;
        try {
            c = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        spyClass(c);
    }

    private static void spyClass(Class<?> c) {
        out.format("Class:%n  %s%n%n", c.getCanonicalName());
        out.format("Modifiers:%n  %s%n%n",
                Modifier.toString(c.getModifiers()));

        out.format("Type Parameters:%n");
        TypeVariable[] tv = c.getTypeParameters();
        if (tv.length != 0) {
            out.format("  ");
            for (TypeVariable t : tv) {
                out.format("%s ", t.getName());
                out.format("%s ", t.getGenericDeclaration());
                out.format("%s ", Arrays.toString(t.getBounds()));
            }
            out.format("%n%n");
        } else {
            out.format("  -- No Type Parameters --%n%n");
        }

        out.format("Implemented Interfaces:%n");
        Type[] intfs = c.getGenericInterfaces();
        if (intfs.length != 0) {
            for (Type intf : intfs)
                out.format("  %s%n", intf.toString());
            out.format("%n");
        } else {
            out.format("  -- No Implemented Interfaces --%n%n");
        }

        out.format("Inheritance Path:%n");
        List<Class> l = new ArrayList<Class>();
        printAncestor(c, l);
        if (l.size() != 0) {
            for (Class<?> cl : l)
                out.format("  %s%n", cl.getCanonicalName());
            out.format("%n");
        } else {
            out.format("  -- No Super Classes --%n%n");
        }

        out.format("AnnotationGrammar:%n");
        Annotation[] ann = c.getAnnotations();
        if (ann.length != 0) {
            for (Annotation a : ann)
                out.format("  %s%n", a.toString());
            out.format("%n");
        } else {
            out.format("  -- No AnnotationGrammar --%n%n");
        }
    }

    private static void printAncestor(Class<?> c, List<Class> l) {
	Class<?> ancestor = c.getSuperclass();
 	if (ancestor != null) {
	    l.add(ancestor);
	    printAncestor(ancestor, l);
 	}
    }

}
