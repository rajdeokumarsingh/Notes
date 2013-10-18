package com.java.examples.reflection.method;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import static java.lang.System.out;

public class R301MethodModifierSpy {

    private static int count;

    private static synchronized void inc() {
        count++;
    }

    private static synchronized int cnt() {
        return count;
    }

    public static void main(String... args) {
        try {
            // Class<?> c = Class.forName(args[0]);
            Class<?> c = Class.forName("java.lang.Object");
            Method[] allMethods = c.getDeclaredMethods();
            for (Method m : allMethods) {
                /* if (!m.getName().equals(args[1])) {
                    continue;
                } */
                out.format("%s%n", m.toGenericString());
                out.format("  Modifiers:  %s%n",
                        Modifier.toString(m.getModifiers()));
                out.format("  [ synthetic=%-5b var_args=%-5b bridge=%-5b ]%n",
                        m.isSynthetic(), m.isVarArgs(), m.isBridge());
                inc();
            }
            out.format("%d matching overload%s found%n", cnt(),
                    (cnt() == 1 ? "" : "s"));

            // production code should handle this exception more gracefully
        } catch (ClassNotFoundException x) {
            x.printStackTrace();
        }
    }
}
