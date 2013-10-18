package com.java.examples.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

class Test {
    // String string;
    // List<String> stringList;
    // HashMap<Integer, String> map;
    // List<List<String>> nest2List;

    // List<List<List<String>>> nest3List;

    List<byte[]> bytesList;
    // byte[] bytes;
}

public class R900NestedListType {
    public static void main(String[] args) throws NoSuchFieldException {
        for (Field field : Test.class.getDeclaredFields()) {
            System.out.println("------------------------------------------------------------");
            System.out.println("generic type: " + getGenericTypeClass(field.getGenericType()));
            System.out.println("------------------------------------------------------------");
        }

        /* Field fieldBytes = Test.class.getDeclaredField("bytes");
        System.out.println("generic type: " + fieldBytes.getGenericType());
        System.out.println("The first parameter of this method is GenericArrayType." +
                (fieldBytes.getGenericType() instanceof GenericArrayType));
                */
    }

    private static Class getGenericTypeClass(Type genericType) {
        System.out.println("getGenericType: " + genericType.toString());

        if (genericType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for (Type fieldArgType : fieldArgTypes) {
                System.out.println("--------------------");
                System.out.println("getActualTypeArguments: " + fieldArgType);

                if(fieldArgType instanceof  ParameterizedType) {
                    return getGenericTypeClass(fieldArgType);
                }

                if (fieldArgType instanceof GenericArrayType) {
                    GenericArrayType genericArrayType = (GenericArrayType) fieldArgType;
                    System.out.println("array type class: " + genericArrayType.toString());
                    System.out.println("component: " + genericArrayType.getGenericComponentType());

                    if (fieldArgType.equals((Type)byte[].class)) {
                        System.out.println("got byte type");
                    }
                    continue;
                }

                Class fieldArgClass = (Class) fieldArgType;
                System.out.println("fieldArgClass = " + fieldArgClass);
                System.out.println("--------------------");
                return fieldArgClass;
            }
        } else if (genericType instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) genericType;
            System.out.println("array type class: " + genericArrayType.toString());
            System.out.println("component: " + genericArrayType.getGenericComponentType());

            if (genericType.equals(byte[].class)) {
                System.out.println("got byte type");
            }
        }

        return null;
    }
}
