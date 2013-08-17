package com.pekall.plist;

import java.lang.reflect.Field;
import java.util.Arrays;
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
        PlistDebug.logVerbose("equals 1");

        if (thiz == that) return true;
        if (thiz == null || that == null) return false;

        if (!thiz.getClass().equals(that.getClass())) return false;

        Class clz = thiz.getClass();

        PlistDebug.logVerbose("equals 2");
        // If the bean object has super classes, need to also
        // check fields of its ancestors.
        while (clz != null && !clz.equals(Object.class)) {
            for (Field field : clz.getDeclaredFields()) {
                try {
                    field.setAccessible(true);

                    PlistDebug.logVerbose("type name: " + field.getName());
                    PlistDebug.logVerbose("type : " + field.getType());

                     // primitive type
                    if (!Object.class.isAssignableFrom(field.getType())) {
                        PlistDebug.logVerbose("comparePrimitiveType");
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
                    } else if(isPrimitiveArray(field.getType())) {
                        if(!comparePrimitiveArray(thisObj, thatObj, field)) return false;
                        // TODO: object array
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
        } else if (double.class.equals(field.getType())) {
            if (Double.compare(field.getDouble(thiz), field.getDouble(that)) != 0) return false;
        }
        return true;
    }

    private static boolean isPrimitiveArray(Class clz) {
        if (boolean[].class.equals(clz) ||
                byte[].class.equals(clz) ||
                char[].class.equals(clz) ||
                short[].class.equals(clz) ||
                int[].class.equals(clz) ||
                long[].class.equals(clz) ||
                float[].class.equals(clz) ||
                double[].class.equals(clz)) {
            return true;
        }
        return false;
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
