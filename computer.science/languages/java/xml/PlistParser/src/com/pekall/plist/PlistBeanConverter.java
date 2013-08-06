package com.pekall.plist;

import com.dd.plist.*;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Converter for PLIST objects and bean objects
 */
public class PlistBeanConverter {
    // TODO: handle key like "Key Usage"
    /* TODO: handle nested array like:
        <array>
            <array>
                <array>
                    <string>O</string>
                    <string>Pekall</string>
                </array>
            </array>
            <array>
                <array>
                    <string>CN</string>
                    <string>PEKALL MDM</string>
                </array>
            </array>
        </array>
     */

    /**
     * Create an NSDictionary object from a bean object
     * @param data bean object
     * @return NSDictionary object
     */
    public static NSDictionary createNdictFromBean(Object data) {
        NSDictionary root = new NSDictionary();
        appendData2Ndict(data, root);
        return root;
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
                PlistDebug.logVerbose("----------------------------------------");
                PlistDebug.logVerbose("field name: " + field.getName());
                PlistDebug.logVerbose("field class: " + field.getType());

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
                        PlistDebug.logError("error type!");
                    }
                } else {
                    PlistDebug.logError("error type!");
                }
            }
            // Append fields of ancestors
            clz = clz.getSuperclass();
        }
        return data;
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

    private static void appendData2Ndict(Object data, NSDictionary root) {
        if (data == null || root == null) {
            PlistDebug.logError("appendData2Ndict null argument," +
                    " data: " + data + ", root: " + root);
            return;
        }

        Class clz = data.getClass();

        // If the bean object has super classes, need to also
        // append fields of its ancestors.
        while (clz != null && !clz.equals(Object.class)) {

            // Add all fields to NSObject
            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Class type = field.getType();

                PlistDebug.logVerbose("========================================");
                PlistDebug.logVerbose(field.getName());
                PlistDebug.logVerbose(type.getName());

                // Ignore final fields
                if ((field.getModifiers() & Modifier.FINAL) != 0) {
                    PlistDebug.logVerbose("final field, continue!");
                    continue;
                }

                try {
                    if (boolean.class.equals(type)) {
                        root.put(field.getName(), field.getBoolean(data));
                    } else if (byte.class.equals(type)) {
                        root.put(field.getName(), field.getByte(data));
                    } else if (int.class.equals(type)) {
                        root.put(field.getName(), field.getInt(data));
                    } else if (long.class.equals(type)) {
                        root.put(field.getName(), field.getLong(data));
                    } else if (float.class.equals(type)) {
                        root.put(field.getName(), field.getFloat(data));
                    } else if (double.class.equals(type)) {
                        root.put(field.getName(), field.getDouble(data));
                    } else if (String.class.equals(type)) {
                        if (field.get(data) == null) continue;

                        root.put(field.getName(), (String) field.get(data));
                    } else if (List.class.equals(type)) {
                        if (field.get(data) == null) continue;

                        List list = (List) field.get(data);
                        NSArray array = new NSArray(list.size());
                        for (int i = 0; i < list.size(); i++) {
                            // TODO:
                            Object object = list.get(i);
                            if (object.getClass().equals(Boolean.class)) {
                                array.setValue(i, NSObject.wrap(new NSNumber(((Boolean)object)).boolValue()));
                            } else if (object.getClass().equals(Byte.class)) {
                            } else if (object.getClass().equals(Character.class)) {
                            } else if (object.getClass().equals(Integer.class)) {
                                array.setValue(i, NSObject.wrap(new NSNumber(((Integer) object)).intValue()));
                            } else if (object.getClass().equals(Long.class)) {
                            } else if (object.getClass().equals(Float.class)) {
                            } else if (object.getClass().equals(Double.class)) {
                            } else if (object.getClass().equals(String.class)) {
                                array.setValue(i, NSObject.wrap(new NSString((String)object)));
                            } else if (List.class.isAssignableFrom(object.getClass())) {
                                PlistDebug.logVerbose("TODO:");
                            } else {
                                array.setValue(i, createNdictFromBean(list.get(i)));
                            }
                        }
                        root.put(field.getName(), array);
                    } else if (byte[].class.equals(type)) {
                        if (field.get(data) == null) continue;

                        root.put(field.getName(), new NSData((byte[]) field.get(data)));
                    } else if (Date.class.equals(type)) {
                        if (field.get(data) == null) continue;

                        root.put(field.getName(), new NSDate((Date) field.get(data)));
                    } else {
                        if (field.get(data) == null) continue;

                        NSDictionary dictionary = createNdictFromBean(field.get(data));
                        root.put(field.getName(), dictionary);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

            // Append fields of ancestors
            clz = clz.getSuperclass();
        }
    }

    private static Class getGenericType(Field field) {
        Class fieldArgClass = null;
        Type genericFieldType = field.getGenericType();
        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for (Type fieldArgType : fieldArgTypes) {
                fieldArgClass = (Class) fieldArgType;
                PlistDebug.logVerbose("fieldArgClass = " + fieldArgClass);
            }
        }
        return fieldArgClass;
    }

    private static void assignByteArrayField(Object data, Field field, NSData nsObject) throws IllegalAccessException {
        if (field.getType().equals(byte[].class)) {
            field.set(data, ((NSData) nsObject).bytes());
        } else {
            PlistDebug.logError("error type!");
        }
    }

    private static void assignDateField(Object data, Field field, NSDate nsObject) throws IllegalAccessException {
        if (field.getType().equals(Date.class)) {
            field.set(data, ((NSDate) nsObject).getDate());
        } else {
            PlistDebug.logError("error type!");
        }
    }

    private static void assignStringField(Object data, Field field, NSString nsObject) throws IllegalAccessException {
        if (field.getType().equals(String.class)) {
            field.set(data, ((NSString) nsObject).getContent());
        } else {
            PlistDebug.logError("error type!");
        }
    }


    private static void assignNumberField(Object data, Field field, NSNumber number) throws IllegalAccessException {
        Class fieldClass = field.getType();
        switch (number.type()) {
            case NSNumber.BOOLEAN:
                if (fieldClass.equals(boolean.class)) {
                    field.setBoolean(data, number.boolValue());
                } else {
                    PlistDebug.logError("Convert type error!");
                }
                break;
            case NSNumber.INTEGER:
                if (fieldClass.equals(int.class)) {
                    field.setInt(data, number.intValue());
                } else if (fieldClass.equals(long.class)) {
                    field.setLong(data, number.longValue());
                } else {
                    PlistDebug.logError("Convert type error!");
                }
                break;
            case NSNumber.REAL: {
                if (fieldClass.equals(float.class)) {
                    field.setFloat(data, number.floatValue());
                } else if (fieldClass.equals(double.class)) {
                    field.setDouble(data, number.doubleValue());
                } else {
                    PlistDebug.logError("Convert type error!");
                }
                break;
            }
        }
    }
}
