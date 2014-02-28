package com.pekall.plist;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Comparator of two objects. It compares fields with following types
 *
 * 1. Primitive type
 * 2. Primitive array type
 * 3. Primitive object type
 * 4.. Primitive object array type
 * 5. String type
 * 6. Date type
 * 7. List type
 */
@SuppressWarnings({"UnusedDeclaration", "BooleanMethodIsAlwaysInverted"})
public class ObjectComparator {
    /**
     * Compare two object
     * @param thiz object to compare
     * @param that object to compare
     * @return true if two objects are equal, otherwise false
     */
    public static boolean equals(Object thiz, Object that) {
        PlistDebug.logVerbose("equals 1");

        if (thiz == that) return true;
        if (thiz == null || that == null) return false;

        if (!thiz.getClass().equals(that.getClass())) return false;

        Class clz = thiz.getClass();

        // If the bean object has super classes, need to also
        // check fields of its ancestors.
        while (clz != null && !clz.equals(Object.class)) {
            for (Field field : clz.getDeclaredFields()) {
                Class fieldType = field.getType();
                try {
                    field.setAccessible(true);

                    PlistDebug.logVerbose("type name: " + field.getName());
                    PlistDebug.logVerbose("type : " + fieldType);

                     // primitive type
                    if (!Object.class.isAssignableFrom(fieldType)) {
                        PlistDebug.logVerbose("comparePrimitiveType");
                        if (!comparePrimitiveType(thiz, that, field)) return false;
                        continue;
                    }

                    // object type
                    Object thisObj = field.get(thiz);
                    Object thatObj = field.get(that);
                    if (thisObj == thatObj) continue;
                    if (thisObj == null || thatObj == null) return false;

                    if (isPrimitiveObject(fieldType) || String.class.equals(fieldType)) {
                        PlistDebug.logVerbose("compare primitive object type or string");
                        if (!thisObj.equals(thatObj)) return false;
                    } else if (Date.class.isAssignableFrom(fieldType)) {
                        // FIXME: Hack for Date field, just compare its toString
                        if (!thisObj.toString().equals(thatObj.toString())) return false;
                    } else if (List.class.isAssignableFrom(fieldType)) {
                        if (!compareListType((List) thisObj, (List) thatObj)) return false;
                    } else if(isPrimitiveArray(fieldType)) {
                        if(!comparePrimitiveArray(thisObj, thatObj, field)) return false;
                    } else if (Object[].class.isAssignableFrom(fieldType)) {
                        if(!compareObjectArray((Object[])thisObj, (Object[])thatObj)) return false;
                    } else {
                        // complex object, check recursively
                        if (!equals(thisObj, thatObj)) return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            // Append fields of ancestors
            clz = clz.getSuperclass();
        }
        return true;
    }

    private static boolean isPrimitiveObject(Class<?> type) {
        if (Boolean.class.equals(type) || Byte.class.equals(type) ||
                Character.class.equals(type) || Short.class.equals(type) ||
                Integer.class.equals(type) || Long.class.equals(type) ||
                Float.class.equals(type) || Double.class.equals(type)) {
            PlistDebug.logVerbose("isPrimitiveObject true");
            return true;
        }

        PlistDebug.logVerbose("isPrimitiveObject false");
        return false;
    }

    /**
     * Compare two list
     * @param thisObj list to compare
     * @param thatObj list to compare
     * @return true if all object in the list are equal, otherwise false
     */
    private static boolean compareListType(List thisObj, List thatObj) {
        if (thisObj.size() != thatObj.size()) return false;

        for (int i = 0; i < thisObj.size(); i++) {
            Object ListThisObj = thisObj.get(i);
            Object ListThatObj = thatObj.get(i);
            if (ListThisObj == ListThatObj) continue;
            if (ListThisObj == null || ListThatObj == null) return false;

            if (!equals(ListThisObj, ListThatObj)) return false;
        }
        return true;
    }

    private static boolean compareObjectArray(Object[] array1, Object[] array2) {
        if (array1.length != array2.length) return false;

        for (int i = 0; i < array1.length; i++) {
            Object object1 = array1[i];
            Object object2 = array2[i];
            if (object1 == object2) continue;
            if (object1 == null || object2 == null) return false;

            if (!equals(object1, object2)) return false;
        }
        return true;
    }

    /**
     * The field to compare is primitive type.
     *
     * @param thiz first object to compare
     * @param that second object to compare
     * @param field object field to compare
     * @return true if the values of the field are equal
     * @throws IllegalAccessException
     */
    private static boolean comparePrimitiveType(Object thiz, Object that, Field field) throws IllegalAccessException {
        if (boolean.class.equals(field.getType())) {
            if (field.getBoolean(thiz) != field.getBoolean(that)) return false;
        } else if (byte.class.equals(field.getType())) {
            if (field.getByte(thiz) != field.getByte(that)) return false;
        } else if (char.class.equals(field.getType())) {
            if (field.getChar(thiz) != field.getChar(that)) return false;
        } else if (short.class.equals(field.getType())) {
            if (field.getShort(thiz) != field.getShort(that)) return false;
        } else if (int.class.equals(field.getType())) {
            if (field.getInt(thiz) != field.getInt(that)) return false;
        } else if (long.class.equals(field.getType())) {
            if (field.getLong(thiz) != field.getLong(that)) return false;
        } else if (float.class.equals(field.getType())) {
            if (Float.compare(field.getFloat(thiz), field.getFloat(that)) != 0) return false;
        } else if (double.class.equals(field.getType())) {
            if (Double.compare(field.getDouble(thiz), field.getDouble(that)) != 0) return false;
        }
        return true;
    }

    private static boolean isPrimitiveArray(Class clz) {
        return boolean[].class.equals(clz) || byte[].class.equals(clz) ||
                char[].class.equals(clz) || short[].class.equals(clz) ||
                int[].class.equals(clz) || long[].class.equals(clz) ||
                float[].class.equals(clz) || double[].class.equals(clz);
    }

    private static boolean comparePrimitiveArray(Object thisObj, Object thatObj, Field field) {
        if(boolean[].class.equals(field.getType())) {
            if(!Arrays.equals((boolean[])thisObj, (boolean[])thatObj)) return false;
        } else if(byte[].class.equals(field.getType())) {
            if(!Arrays.equals((byte[])thisObj, (byte[])thatObj)) return false;
        } else if(char[].class.equals(field.getType())) {
            if(!Arrays.equals((char[])thisObj, (char[])thatObj)) return false;
        } else if(short[].class.equals(field.getType())) {
            if(!Arrays.equals((short[])thisObj, (short[])thatObj)) return false;
        } else if(int[].class.equals(field.getType())) {
            if(!Arrays.equals((int[])thisObj, (int[])thatObj)) return false;
        } else if(long[].class.equals(field.getType())) {
            if(!Arrays.equals((long[])thisObj, (long[])thatObj)) return false;
        } else if(float[].class.equals(field.getType())) {
            if(!Arrays.equals((float[])thisObj, (float[])thatObj)) return false;
        } else if(double[].class.equals(field.getType())) {
            if(!Arrays.equals((double[])thisObj, (double[])thatObj)) return false;
        }
        return true;
    }
}
