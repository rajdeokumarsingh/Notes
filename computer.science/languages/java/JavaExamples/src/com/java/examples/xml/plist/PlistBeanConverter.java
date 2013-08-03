package com.java.examples.xml.plist;

import com.dd.plist.NSArray;
import com.dd.plist.NSData;
import com.dd.plist.NSDictionary;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
                } else if (char.class.equals(type)) {
                    root.put(field.getName(), field.getChar(data));
                } else if (short.class.equals(type)) {
                    root.put(field.getName(), field.getShort(data));
                } else if (int.class.equals(type)) {
                    root.put(field.getName(), field.getInt(data));
                } else if (long.class.equals(type)) {
                    root.put(field.getName(), field.getLong(data));
                } else if (float.class.equals(type)) {
                    root.put(field.getName(), field.getFloat(data));
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
}
