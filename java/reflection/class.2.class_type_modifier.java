Examining Class Modifiers and Types
    // Memory Template:
    // public static final MyClass<K,V>
        // extends ConcurrentMap<K,V>, NavigableMap<K,V>
        // implements BaseClass<K,V>

    Modifiers:
        Access modifiers: public, protected, and private
        Modifier requiring override: abstract
        Modifier restricting to one instance: static
        Modifier prohibiting value modification: final
        Modifier forcing strict floating point behavior: strictfp
        Annotations

        // Modifiers
        Class<?> c = Class.forName(args[0]);
        out.format("Modifiers:%n  %s%n%n",
                Modifier.toString(c.getModifiers()));


    // Type variables
    TypeVariable[] tv = c.getTypeParameters();
    if (tv.length != 0) {
        out.format("  ");
        for (TypeVariable t : tv)
            out.format("%s ", t.getName());
        out.format("%n%n");
    }

    // Implemented Interfaces
    Type[] intfs = c.getGenericInterfaces();
    if (intfs.length != 0) {
        for (Type intf : intfs)
            out.format("  %s%n", intf.toString());
    } 

    // Inherited
    c.getSuperClass().getSuperClass()...

    // Annotation
    Annotation[] ann = c.getAnnotations();
    if (ann.length != 0) {
        for (Annotation a : ann)
            out.format("  %s%n", a.toString());
        out.format("%n");
    }


