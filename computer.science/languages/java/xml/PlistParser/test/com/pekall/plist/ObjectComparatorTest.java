package com.pekall.plist;

import com.pekall.plist.beans.BasicArrayType;
import com.pekall.plist.beans.BasicObjectType;
import com.pekall.plist.beans.BasicType;
import com.pekall.plist.beans.BeanBasicType;
import junit.framework.TestCase;

public class ObjectComparatorTest extends TestCase {
     public void testNull() throws Exception {
        assertTrue(ObjectComparator.equals(null, null));

        assertFalse(ObjectComparator.equals(new Object(), null));
        assertFalse(ObjectComparator.equals(null, new Object()));

        assertFalse(ObjectComparator.equals(null,new BeanBasicType()));
    }

    public void testBasicType() throws Exception {
        assertTrue(ObjectComparator.equals(new BasicType(), new BasicType()));
        assertTrue(ObjectComparator.equals(createBasicType(), createBasicType()));

        BasicType b1 = createBasicType();
        b1.setBoolean(false);
        assertFalse(ObjectComparator.equals(b1, createBasicType()));

        BasicType b2 = createBasicType();
        b2.setByte((byte) 90);
        assertFalse(ObjectComparator.equals(b2, createBasicType()));

        BasicType b3 = createBasicType();
        b3.setChar('3');
        assertFalse(ObjectComparator.equals(b3, createBasicType()));

        BasicType b4 = createBasicType();
        b4.setShort((short) 12345);
        assertFalse(ObjectComparator.equals(b4, createBasicType()));

        BasicType b5 = createBasicType();
        b5.setInt(899);
        assertFalse(ObjectComparator.equals(b5, createBasicType()));

        BasicType b6 = createBasicType();
        b6.setLong(9987);
        assertFalse(ObjectComparator.equals(b6, createBasicType()));

        BasicType b7 = createBasicType();
        b7.setFloat(384.21f);
        assertFalse(ObjectComparator.equals(b7, createBasicType()));

        BasicType b8 = createBasicType();
        b8.setDouble(438948.439);
        assertFalse(ObjectComparator.equals(b8, createBasicType()));
    }

    public void testBasicObjectType() throws Exception {
        assertTrue(ObjectComparator.equals(new BasicObjectType(), new BasicObjectType()));
        assertTrue(ObjectComparator.equals(createBasicObjectType(), createBasicObjectType()));

        BasicObjectType b1 = createBasicObjectType();
        b1.setBoolean(false);
        assertFalse(ObjectComparator.equals(b1, createBasicObjectType()));

        BasicObjectType b2 = createBasicObjectType();
        b2.setByte((byte) 90);
        assertFalse(ObjectComparator.equals(b2, createBasicObjectType()));

        BasicObjectType b3 = createBasicObjectType();
        b3.setChar('3');
        assertFalse(ObjectComparator.equals(b3, createBasicObjectType()));

        BasicObjectType b4 = createBasicObjectType();
        b4.setShort((short) 12345);
        assertFalse(ObjectComparator.equals(b4, createBasicObjectType()));

        BasicObjectType b5 = createBasicObjectType();
        b5.setInt(899);
        assertFalse(ObjectComparator.equals(b5, createBasicObjectType()));

        BasicObjectType b6 = createBasicObjectType();
        b6.setLong(9987l);
        assertFalse(ObjectComparator.equals(b6, createBasicObjectType()));

        BasicObjectType b7 = createBasicObjectType();
        b7.setFloat(384.21f);
        assertFalse(ObjectComparator.equals(b7, createBasicObjectType()));

        BasicObjectType b8 = createBasicObjectType();
        b8.setDouble(438948.439);
        assertFalse(ObjectComparator.equals(b8, createBasicObjectType()));

        BasicObjectType b9 = createBasicObjectType();
        b9.setDouble(null);
        assertFalse(ObjectComparator.equals(b9, createBasicObjectType()));
    }

    public void testBasicArrayType() throws Exception {
        assertTrue(ObjectComparator.equals(new BasicArrayType(), new BasicArrayType()));
        assertTrue(ObjectComparator.equals(createBasicArrayType(), createBasicArrayType()));

        BasicArrayType b1 = createBasicArrayType();
        b1.setBooleans(new boolean[0]);
        assertFalse(ObjectComparator.equals(b1, createBasicArrayType()));

        BasicArrayType b2 = createBasicArrayType();
        b2.setBytes(new byte[]{});
        assertFalse(ObjectComparator.equals(b2, createBasicArrayType()));

        BasicArrayType b3 = createBasicArrayType();
        b3.setChars(new char[] {'a', 'b', 'c'});
        assertFalse(ObjectComparator.equals(b3, createBasicArrayType()));

        BasicArrayType b4 = createBasicArrayType();
        b4.setShorts(new short[]{1, 2, 3, 4, 5});
        assertFalse(ObjectComparator.equals(b4, createBasicArrayType()));

        BasicArrayType b5 = createBasicArrayType();
        b5.setIntegers(new int[]{1, 2, 3, 4, 5});
        assertFalse(ObjectComparator.equals(b5, createBasicArrayType()));

        BasicArrayType b6 = createBasicArrayType();
        b6.setLongs(new long[]{1, 3, 2, 4});
        assertFalse(ObjectComparator.equals(b6, createBasicArrayType()));

        BasicArrayType b7 = createBasicArrayType();
        b7.setFloats(new float[]{(float) 34.3, (float) 24.9, (float) 23.3});
        assertFalse(ObjectComparator.equals(b7, createBasicArrayType()));

        BasicArrayType b8 = createBasicArrayType();
        b8.setDoubles(new double[] { 12.5, 23.4, 23, 234, 2222});
        assertFalse(ObjectComparator.equals(b8, createBasicArrayType()));

        BasicArrayType b9 = createBasicArrayType();
        b9.setDoubles(null);
        assertFalse(ObjectComparator.equals(b9, createBasicArrayType()));
    }

    private BasicType createBasicType() {
        BasicType b1;
        b1 = new BasicType();
        b1.setBoolean(true);
        b1.setByte((byte) 20);
        b1.setChar('5');
        b1.setShort((short) 59);
        b1.setInt(10);
        b1.setLong(102l);
        b1.setFloat(30.0f);
        b1.setDouble(34.0);
        return b1;
    }

    private BasicObjectType createBasicObjectType() {
        BasicObjectType b1;
        b1 = new BasicObjectType();
        b1.setBoolean(true);
        b1.setByte((byte) 20);
        b1.setChar('5');
        b1.setShort((short) 59);
        b1.setInt(10);
        b1.setLong(102l);
        b1.setFloat(30.0f);
        b1.setDouble(34.0);
        return b1;
    }

    private BasicArrayType createBasicArrayType() {
        BasicArrayType b1 = new BasicArrayType();
        b1.setBooleans(new boolean[]{true, false});
        b1.setBytes(new byte[]{(byte) 20});
        b1.setChars(new char[]{'5'});
        b1.setShorts(new short[] { (short) 59 });
        b1.setIntegers(new int[0]);
        b1.setLongs(new long[] {1l, 2l, 10l});
        b1.setFloats(new float[] {45.9f, 34.0f});
        b1.setDoubles(new double[] {99.3, 9.0, 123.4, 34.2});
        return b1;
    }

    // todo: test
}
