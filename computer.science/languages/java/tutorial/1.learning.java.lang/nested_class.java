// example:
// com.java.examples.basic.classs.OuterClass

Nested Classes
    two categories: (Terminology)
        1. static nested class
            all static nested classes are member class

            do NOT have access to other members of the enclosing class. 

        2. Non-static nested class(inner)
            2.1 inner member class
            2.2 inner local class
            2.3 inner anonymous class

            have access to ALL(including private) other members of the enclosing class

Why Use Nested Classes?
    1. It is a way of logically grouping classes that are only used in one place.
        a class only used by one other class

    2. It increases encapsulation.
        inner class can access all members of outer class

    3. Nested classes can lead to more readable and maintainable code.

Static Nested Classes
    accessed using the enclosing class name:

    OuterClass.StaticNestedClass nestedObject =
         new OuterClass.StaticNestedClass();

Inner Classes
    Inner class的实例是包括在Outer class的实例之中的
        An instance of an inner class can exist ONLY within an instance of its enclosing class 

        其创建依赖于outer class的实例 {
            OuterClass outerObj = new OuterClass();
            OuterClass.InnerClass innerObj = outerObj.new InnerClass();
        }

    Can not define any static memeber
        because an inner class is associated with an instance, it cannot define any static members itself.

    Three categories:
        1. member classes
        2. local classes  
        3. anonymous classes (also called anonymous inner classes)

Types of Nested Classes 
    Type                        Scope                       Inner
  static nested class           member                      no
  inner [non-static] class      member                      yes
  local class                   local                       yes
  anonymous class           only the point 
                            where it is defined             yes

Categories of JAVA classes
    1. top level class 
        does not appear inside another class or interface

    2. nested class
        If a type is not top level it is nested. 
        Must have an enclosing class

        1. inner classes 
            Non-static

            1. member classes
                However, not all inner classes have enclosing instances; 
                    inner classes in static contexts, like an anonymous class used in a static initializer block, do not. 

            2. local classes
                named classes declared inside of a block like a method or constructor body

            3. anonymous classes
                unnamed classes whose instances are created in expressions and statements.

        2. static nested class
            Non-inner

            If a type is explicitly or implicitly static, it is not an inner class; 
            all member interfaces are implicitly static. 

        3. member
            a member type is directly enclosed by another type declaration.
            Must have declaring class

java.lang.Class
    getEnclosingClass
    getDeclaringClass
    getEnclosingConstructor
    getEnclosingMethod
    isAnonymousClass
    isLocalClass
    isMemberClass

