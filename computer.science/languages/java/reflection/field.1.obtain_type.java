Class<?> c = Class.forName(args[0]);
Field f = c.getField(args[1]);
System.out.format("Type: %s%n", f.getType());
// getGenericType can get Signature Attrbite(or template parameters) in the class
// if it's present
System.out.format("GenericType: %s%n", f.getGenericType());
