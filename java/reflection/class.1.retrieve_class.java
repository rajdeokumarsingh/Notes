/* 记忆要点： 
    get a Class:
        1. full class name(Class.forName(String))
            .class
        2. Object
            .getClass 
            .getSuperClass

    get Classes:
        .getClasses, .getDeclaredClasses
        .getDeclaringClasses
        .getEnclosingClasses
*/
Retrieving Class Objects

    1. Object.getClass()
        // good practice Class<?>
        Class<?> c = warn.getClass(); // maybe c has some type parameters
        Method m = c.getMethod("m");
    
    2. The .class Syntax
        1. get Class objects from a type instead of an object
        2. also the easiest way to obtain the Class for a primitive type

    3. Class.forName()
        This syntax is applicable to references and primitive types.

        Class c = Class.forName("com.duke.MyLocaleServiceProvider");
        Class cDoubleArray = Class.forName("[D");
        Class cStringArray = Class.forName("[[Ljava.lang.String;");

TYPE Field for Primitive Type Wrappers
    Class c = Double.TYPE;
    Class c = Void.TYPE;

Methods that Return Classes
    Class.getSuperclass()
        Returns the super class for the given class.
    
    Class.getClasses()
        Returns all the public classes, interfaces, and enums that 
            are members of the class including inherited members.

    Class.getDeclaredClasses()
        Returns all of the classes interfaces, and enums that are 
            explicitly declared in this class.

        Differences between getClasses and getDeclaredClasses
            getClasses: public, including inherited members
            getDeclaredClasses: all, not including inherited members

    Class.getDeclaringClass()
    java.lang.reflect.Field.getDeclaringClass()
    java.lang.reflect.Method.getDeclaringClass()
    java.lang.reflect.Constructor.getDeclaringClass()
        Returns the Class in which these members were declared. 
        Anonymous Class Declarations will not have a declaring class but will have an enclosing class.

    Class.getEnclosingClass()
        Returns the immediately enclosing class of the class. 

