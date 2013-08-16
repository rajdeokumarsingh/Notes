package com.pekall.plist;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class ObjectComparator {
    /**
     * Compare two object
     * @param thiz
     * @param that
     * @return true if two objects are equal, otherwise false
     */
    public static boolean equals(Object thiz, Object that) {
        if (thiz == that) return true;
        if (thiz == null || that == null) return false;

        if (!thiz.getClass().equals(that.getClass())) return false;

        Class clz = thiz.getClass();

        // If the bean object has super classes, need to also
        // check fields of its ancestors.
        while (clz != null && !clz.equals(Object.class)) {
            for (Field field : clz.getDeclaredFields()) {
                try {
                    field.setAccessible(true);

                    // primitive type
                    if (!Object.class.isAssignableFrom(field.getType())) {
                        if (!comparePrimitiveType(thiz, that, field)) return false;
                        continue;
                    }

                    // object type
                    Object thisObj = field.get(thiz);
                    Object thatObj = field.get(that);

                    if (thisObj == thatObj) continue;
                    if (thisObj == null || thatObj == null) return false;

                    // FIXME: Hack for Date field, just compare its toString
                    if (Date.class.isAssignableFrom(field.getType())) {
                        if (!thisObj.toString().equals(thatObj.toString())) return false;
                    } else if (List.class.isAssignableFrom(field.getType())) {
                        if (!compareListType((List) thisObj, (List) thatObj)) return false;
                    } else {
                        if (!thisObj.equals(thatObj)) return false;
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

    /**
     * Compare two list
     * @param thisObj
     * @param thatObj
     * @return true if all object in the list are equal, otherwise false
     */
    private static boolean compareListType(List thisObj, List thatObj) {
        if (thisObj.size() != thatObj.size()) return false;

        for (int i = 0; i < thisObj.size(); i++) {
            Object ListThisObj = thisObj.get(i);
            Object ListThatObj = thatObj.get(i);
            if (ListThisObj == ListThatObj) continue;
            if (ListThisObj == null || ListThatObj == null) return false;
            if (!ListThisObj.equals(ListThatObj)) return false;
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
        } else if (Double.class.equals(field.getType())) {
            if (Double.compare(field.getDouble(thiz), field.getDouble(that)) != 0) return false;
        }
        return true;
    }
}
