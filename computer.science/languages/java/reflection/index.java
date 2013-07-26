Reflection features
    be able to examine private members on classes

Drawbacks of Reflection

    Performance Overhead
        Java virtual machine optimizations can not be performed
        should be avoided in sections of code which are called frequently 
            in performance-sensitive applications

    Security Restrictions
        Reflection requires a runtime permission which may 
            not be present when running under a security manage

        // FIXME:
        AccessibleObject.setAccessible() will only succeed 
            if the operation is allowed by the security context. 

    Exposure of Internals
        may render code dysfunctional and may destroy portability.
        breaks abstractions and therefore may change behavior 
            with upgrades of the platform.

// examples: com.java.examples.reflection
Lesson: Classes
    java.lang.Class
        JVM provides an immutable instance of java.lang.Class for each type of object
        provides methods to 
            1. examine the runtime properties of the object 
                including its members and type information. 

            2. create new classes and objects

        it is the entry point for all of the Reflection APIs            

    ./class.1.retrieve_class.java
    ./class.2.class_type_modifier.java
    ./class.3.class_members.java

Lesson: Members
    Reflection defines an interface java.lang.reflect.Member which is implemented by 
        java.lang.reflect.Field
        java.lang.reflect.Method 
        java.lang.reflect.Constructor 

Fields
    // get type of a field
    ./reflection/field.1.obtain_type.java

    // Retrieving and Parsing Field Modifiers
    ./reflection/field.2.retrieve_modifilers.java

    // Getting and Setting Field Values
    ./reflection/field.3.get_set_values.java

Methods
    ./reflection/method.1.obtain_type.java
    ./method.2.invoke.java

Constructor
    ./reflection/ctor.java

// FIXME: why not work ?
Class<?> c = Class.forName("ConstructorTrouble");
// solution
Class<?> c = Class.forName("com.java.examples.reflection.method.ConstructorTrouble");


