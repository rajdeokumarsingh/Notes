package com.java.examples.basic.classs;

/**
 * Super class of anonymous nested class.
 */
class Class1 {
    void test() {
        System.out.println("Class1 test");
    }
}

/**
 * Examples of nested classes
 */
public class OuterClass {
    static class StaticNestedClass {
        static void test() {
            System.out.println("StaticNestedClass test");
            dumpClassInfo(StaticNestedClass.class);
        }
    }

    class InnerMemberClass {
        void test() {
            System.out.println("InnerMemberClass test");
            dumpClassInfo(InnerMemberClass.class);
        }
    }

    void testInnerLocalClass() {
        class InnerLocalClass {
            void test() {
                System.out.println("testLocalInnerClass test");
                dumpClassInfo(InnerLocalClass.class);
            }
        }

        InnerLocalClass lic = new InnerLocalClass();
        lic.test();
    }

    void testInnerAnonymousClass() {
        Class1 c = new Class1() {
            @Override
            void test() {
                System.out.println("testAnonymousInnerClass test");
                dumpClassInfo(this.getClass());
            }
        };
        c.test();
    }

    // FIXME: Can NOT declare a static local class
    void testLocalStaticClass() {
        // static class LocalStaticClass {
        // }
    }

    static {
        class InnerNonMemberClass {
            void test() {
                System.out.println("InnerNonMemberClass test");
                dumpClassInfo(this.getClass());
            }
        }
        new InnerNonMemberClass().test();
    }

    public static void main(String[] args) {
        OuterClass.StaticNestedClass.test();

        OuterClass outerObj = new OuterClass();
        OuterClass.InnerMemberClass innerObj = outerObj.new InnerMemberClass();
        innerObj.test();

        outerObj.testInnerLocalClass();
        outerObj.testInnerAnonymousClass();
    }

    static void dumpClassInfo(Class c) {
        System.out.println("class: " + c.toString());
        System.out.println("getEnclosingClass: " + c.getEnclosingClass());
        System.out.println("getDeclaringClass: " + c.getDeclaringClass());
        System.out.println("getEnclosingConstructor: " + c.getEnclosingConstructor());
        System.out.println("getEnclosingMethod: " + c.getEnclosingMethod());
        System.out.println("isAnonymousClass: " + c.isAnonymousClass());
        System.out.println("isLocalClass: " + c.isLocalClass());
        System.out.println("isMemberClass: " + c.isMemberClass());
        System.out.format("%n%n");
    }
}
