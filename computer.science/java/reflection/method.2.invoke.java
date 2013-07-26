Invoking Methods
    java.lang.reflect.Method.invoke(). 
        1. The first argument is the object instance on which this particular method is to be invoked. 
            If the method is static, the first argument should be null.

        2. Subsequent arguments are the method's parameters.

        If the underlying method throws an exception, 
            it will be wrapped by an java.lang.reflect.InvocationTargetException
            The method''s original exception may be retrieved using the exception 
                chaining mechanism''s InvocationTargetException.getCause() method

Class<?> c = Deet.class;
Object t = c.newInstance();

// invoking instance method
Method methodTest = c.getDeclaredMethod("testBar", int.class);
methodTest.invoke(t, 25);
                                    
// invoking static method
Method main = c.getDeclaredMethod("main", String[].class);
String[] mainArgs = Arrays.copyOfRange(args, 1, args.length);
main.invoke(null, (Object)mainArgs);
                                                                        
// search method
Method[] allMethods = c.getDeclaredMethods();
String mname = m.getName();
m.getGenericReturnType()
Type[] pType = m.getGenericParameterTypes();
if ((pType.length != 1)
    || Locale.class.isAssignableFrom(pType[0].getClass()))

m.setAccessible(true);
Object o = m.invoke(t,  // t was created by c.newInstance()
    new Locale(args[1], args[2], args[3]));

// translate reflection exception to the method exception
} catch (InvocationTargetException x) {
    Throwable cause = x.getCause();
    err.format("invocation of %s failed: %s%n", mname,
            cause.getMessage());
}

// FIXME: tip
// Tip: Always pass the upper bound of the parameterized type when searching for a method. 

