Constructors

Key classes 
    1. java.lang.Class 
    2. java.lang.reflect.Constructor

Class<?> c = Class.forName("java.lang.String");
Constructor[] allConstructors = c.getDeclaredConstructors();
for (Constructor ctor : allConstructors) {
    out.format("%s%n", ctor.toGenericString());

    out.format("[ synthetic=%-5b var_args=%-5b ]%n", ctor.isSynthetic(), ctor.isVarArgs());

    out.format("%s%n", Modifier.toString(ctor.getModifiers()));



    Class<?>[] pType = ctor.getParameterTypes();
    Type[] gpType = ctor.getGenericParameterTypes();
}

Creating New Class Instances
    two ways:
        1. java.lang.reflect.Constructor.newInstance()
            prefered
            
        2. Class.newInstance()
            can only invoke the zero-argument constructor
            throws any exception thrown by the constructor, regardless of whether it is checked or unchecked
            requires that the constructor be visible 

{
    Constructor ctor = Console.class.getDeclaredConstructor(null);
    ctor.setAccessible(true);
    Console con = (Console) ctor.newInstance();

    Field f = con.getClass().getDeclaredField("cs");
    f.setAccessible(true);
    System.out.format("Console charset         :  %s%n", f.get(con));
    System.out.format("Charset.defaultCharset():  %s%n", Charset.defaultCharset());
}

{
    Constructor ctor = EmailAliases.class.getDeclaredConstructor(HashMap.class);
    ctor.setAccessible(true);
    EmailAliases email = (EmailAliases)ctor.newInstance(new HashMap<String, String>);
    email.printKeys();
}




