package com.java.examples.xml.plist;

import com.dd.plist.*;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Converter for PLIST object and bean object
 */
public class PlistBeanConverter {

    /**
     * Create an NSDictionary object from a bean object
     * @param data bean object
     * @return NSDictionary object
     */
    public static NSDictionary createNdictFromBean(Object data) {
        NSDictionary root = new NSDictionary();
        try {
            appendData2Ndict(data, root);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return root;
    }

    private static void appendData2Ndict(Object data, NSDictionary root) throws IllegalAccessException {
        Class clz = data.getClass();

        // If the bean object has super classes, need to also
        // append fields of its ancestors.
        while (clz != null && !clz.equals(Object.class)) {

            // Add all fields to NSObject
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Class type = field.getType();

                PlistDebug.log("========================================");
                PlistDebug.log(field.getName());
                PlistDebug.log(type.getName());

                // Ignore final fields
                if ((field.getModifiers() & Modifier.FINAL) != 0) {
                    PlistDebug.log("final field, continue!");
                    continue;
                }

                if (boolean.class.equals(type)) {
                    root.put(field.getName(), field.getBoolean(data));
                } else if (byte.class.equals(type)) {
                    root.put(field.getName(), field.getByte(data));
                } else if (int.class.equals(type)) {
                    root.put(field.getName(), field.getInt(data));
                } else if (long.class.equals(type)) {
                    root.put(field.getName(), field.getLong(data));
                } else if (double.class.equals(type)) {
                    root.put(field.getName(), field.getDouble(data));
                } else if (String.class.equals(type)) {
                    root.put(field.getName(), (String) field.get(data));
                } else if (List.class.equals(type)) {
                    PlistDebug.log("type list");
                    List list = (List) field.get(data);
                    NSArray array = new NSArray(list.size());
                    for (int i = 0; i < list.size(); i++) {
                        array.setValue(i, createNdictFromBean(list.get(i)));
                    }
                    root.put(field.getName(), array);
                } else if (byte[].class.equals(type)) {
                    PlistDebug.log("type byte[]");
                    root.put(field.getName(), new NSData((byte[]) field.get(data)));
                } else {
                    PlistDebug.log("type object");
                    Object obj = field.get(data);
                    NSDictionary dictionary = createNdictFromBean(obj);
                    root.put(field.getName(), dictionary);
                }
            }

            // Append fields of ancestors
            clz = clz.getSuperclass();
        }
    }

    /**
     * Create a bean object from an NSDictionary object
     * @param root the NSDictionary object to convert
     * @param clz class of the bean object
     * @return the bean object
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public static Object createBeanFromNdict(NSDictionary root, Class clz) throws
            NoSuchMethodException, IllegalAccessException,
                InvocationTargetException, InstantiationException {
        // TODO: remove unused exception

        Constructor ctor = clz.getConstructor();
        ctor.setAccessible(true);
        Object data = ctor.newInstance();

        // If the bean object has super classes, need to also
        // append fields of its ancestors.
        while (clz != null && !clz.equals(Object.class)) {
            for (Field field : clz.getDeclaredFields()) {
                PlistDebug.log("----------------------------------------");
                PlistDebug.log("field name: " + field.getName());
                PlistDebug.log("field class: " + field.getType());

                field.setAccessible(true);

                NSObject nsObject = root.objectForKey(field.getName());
                if (nsObject == null) continue;
                if (nsObject.getClass().equals(NSNumber.class)) {
                    assignNumberField(data, field, (NSNumber) nsObject);
                } else if (nsObject.getClass().equals(NSString.class)) {
                    assignStringField(data, field, (NSString) nsObject);
                } else if (nsObject.getClass().equals(NSDate.class)) {
                    assignDateField(data, field, (NSDate) nsObject);
                } else if (nsObject.getClass().equals(NSData.class)) {
                    assignByteArrayField(data, field, (NSData) nsObject);
                } else if (nsObject.getClass().equals(NSDictionary.class)) {
                    NSDictionary dict = (NSDictionary) nsObject;
                    field.set(data, createBeanFromNdict(dict, field.getType()));
                } else if (nsObject.getClass().equals(NSArray.class)) {
                    Class fieldArgClass = getGenericType(field);
                    if (fieldArgClass != null && List.class.isAssignableFrom(field.getType())) {
                        NSArray array = (NSArray) nsObject;
                        field.set(data, createListFromNSArray(array, fieldArgClass));
                    } else {
                        PlistDebug.log("error type!");
                    }
                } else {
                    PlistDebug.log("error type!");
                }
            }
            // Append fields of ancestors
            clz = clz.getSuperclass();
        }
        return data;
    }

    private static Class getGenericType(Field field) {
        Class fieldArgClass = null;
        Type genericFieldType = field.getGenericType();
        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for (Type fieldArgType : fieldArgTypes) {
                fieldArgClass = (Class) fieldArgType;
                PlistDebug.log("fieldArgClass = " + fieldArgClass);
            }
        }
        return fieldArgClass;
    }

    private static void assignByteArrayField(Object data, Field field, NSData nsObject) throws IllegalAccessException {
        if (field.getType().equals(byte[].class)) {
            field.set(data, ((NSData) nsObject).bytes());
        } else {
            PlistDebug.log("error type!");
        }
    }

    private static void assignDateField(Object data, Field field, NSDate nsObject) throws IllegalAccessException {
        if (field.getType().equals(Date.class)) {
            field.set(data, ((NSDate) nsObject).getDate());
        } else {
            PlistDebug.log("error type!");
        }
    }

    private static void assignStringField(Object data, Field field, NSString nsObject) throws IllegalAccessException {
        if (field.getType().equals(String.class)) {
            field.set(data, ((NSString) nsObject).getContent());
        } else {
            PlistDebug.log("error type!");
        }
    }

    public static List createListFromNSArray(NSArray array, Class elementClass) {
        ArrayList list = new ArrayList();
        for (NSObject nsObject : array.getArray()) {
            if (Boolean.class.equals(elementClass)) {
                list.add(((NSNumber) nsObject).boolValue());
            } else if (Byte.class.equals(elementClass)) {
                list.add(((NSNumber) nsObject).intValue());
            } else if (Integer.class.equals(elementClass)) {
                list.add(((NSNumber) nsObject).intValue());
            } else if (Long.class.equals(elementClass)) {
                list.add(((NSNumber) nsObject).longValue());
            } else if (Double.class.equals(elementClass)) {
                list.add(((NSNumber) nsObject).doubleValue());
            } else if (Byte[].class.equals(elementClass)) {
                list.add(((NSData) nsObject).bytes());
            } else if (String.class.equals(elementClass)) {
                list.add(((NSString) nsObject).getContent());
            } else if (List.class.equals(elementClass)) {
                // TODO: nest list
            } else {
                if (NSDictionary.class.equals(nsObject.getClass())) {
                    try {
                        list.add(createBeanFromNdict((NSDictionary) nsObject, elementClass));
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return list;
    }

    private static void assignNumberField(Object data, Field field, NSNumber number) throws IllegalAccessException {
        Class fieldClass = field.getType();
        switch (number.type()) {
            case NSNumber.BOOLEAN:
                if (fieldClass.equals(boolean.class)) {
                    field.setBoolean(data, number.boolValue());
                } else {
                    PlistDebug.log("Convert type error!");
                }
                break;
            case NSNumber.INTEGER:
                if (fieldClass.equals(int.class)) {
                    field.setInt(data, number.intValue());
                } else if (fieldClass.equals(long.class)) {
                    field.setLong(data, number.longValue());
                } else {
                    PlistDebug.log("Convert type error!");
                }
                break;
            case NSNumber.REAL: {
                if (fieldClass.equals(float.class)) {
                    field.setFloat(data, number.floatValue());
                } else if (fieldClass.equals(double.class)) {
                    field.setDouble(data, number.doubleValue());
                } else {
                    PlistDebug.log("Convert type error!");
                }
                break;
            }
        }
    }
}
