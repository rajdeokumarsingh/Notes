package com.pekall.plist;

import com.pekall.plist.beans.*;
import junit.framework.TestCase;

import java.util.*;

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

        BasicObjectType b10 = createBasicObjectType();
        b10.setString("test");
        assertFalse(ObjectComparator.equals(b10, createBasicObjectType()));
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

    public void testBasicObjectArrayType() throws Exception {
        assertTrue(ObjectComparator.equals(new BasicObjectArrayType(), new BasicObjectArrayType()));
        assertTrue(ObjectComparator.equals(createBasicObjectArrayType(), createBasicObjectArrayType()));

        BasicObjectArrayType b1 = createBasicObjectArrayType();
        b1.setBooleans(new Boolean[0]);
        assertFalse(ObjectComparator.equals(b1, createBasicObjectArrayType()));

        BasicObjectArrayType b2 = createBasicObjectArrayType();
        b2.setBytes(new Byte[]{});
        assertFalse(ObjectComparator.equals(b2, createBasicObjectArrayType()));

        BasicObjectArrayType b3 = createBasicObjectArrayType();
        b3.setChars(new Character[]{'5', 'a', 'b'});
        assertFalse(ObjectComparator.equals(b3, createBasicObjectArrayType()));

        BasicObjectArrayType b4 = createBasicObjectArrayType();
        b4.setShorts(new Short[]{4, 5});
        assertFalse(ObjectComparator.equals(b4, createBasicObjectArrayType()));

        BasicObjectArrayType b5 = createBasicObjectArrayType();
        b5.setIntegers(new Integer[]{1, 2, 3, 4, 5});
        assertFalse(ObjectComparator.equals(b5, createBasicObjectArrayType()));

        BasicObjectArrayType b6 = createBasicObjectArrayType();
        b6.setLongs(new Long[]{1l, 3l, 2l, 4l});
        assertFalse(ObjectComparator.equals(b6, createBasicObjectArrayType()));

        BasicObjectArrayType b7 = createBasicObjectArrayType();
        b7.setFloats(new Float[]{(float) 34.3, (float) 24.9, (float) 23.3});
        assertFalse(ObjectComparator.equals(b7, createBasicObjectArrayType()));

        BasicObjectArrayType b8 = createBasicObjectArrayType();
        b8.setDoubles(new Double[] { 12.5, 23.4, 23.0, 234.3, 2222.2});
        assertFalse(ObjectComparator.equals(b8, createBasicObjectArrayType()));

        BasicObjectArrayType b9 = createBasicObjectArrayType();
        b9.setDoubles(null);
        assertFalse(ObjectComparator.equals(b9, createBasicObjectArrayType()));

        BasicObjectArrayType b10 = createBasicObjectArrayType();
        // compare with null
        b10.setObjects(null);
        assertFalse(ObjectComparator.equals(b10, createBasicObjectArrayType()));
        b10.setObjects(new BasicType[] {createBasicType(), createBasicType(), createBasicType()});
        assertTrue(ObjectComparator.equals(b10, createBasicObjectArrayType()));

        // compare with array[0]
        b10.setObjects(new BasicType[0]);
        assertFalse(ObjectComparator.equals(b10, createBasicObjectArrayType()));
        b10.setObjects(new BasicType[] {createBasicType(), createBasicType(), createBasicType()});
        assertTrue(ObjectComparator.equals(b10, createBasicObjectArrayType()));

        // compare with different array number
        b10.setObjects(new BasicType[] {createBasicType()});
        assertFalse(ObjectComparator.equals(b10, createBasicObjectArrayType()));
        b10.setObjects(new BasicType[] {createBasicType(), createBasicType(), createBasicType()});
        assertTrue(ObjectComparator.equals(b10, createBasicObjectArrayType()));

        // compare with different array element content
        BasicType bt = createBasicType();
        bt.setLong(123499l);
        b10.setObjects(new BasicType[] {createBasicType(), createBasicType(), bt});
        assertFalse(ObjectComparator.equals(b10, createBasicObjectArrayType()));
     }

    public void testDateType() throws Exception {
        assertTrue(ObjectComparator.equals(new BeanBasicType(), new BeanBasicType()));
        assertTrue(ObjectComparator.equals(createBeanBasicType(), createBeanBasicType()));

        BeanBasicType basicType1 = createBeanBasicType();
        basicType1.setDate(new Date());
        BeanBasicType basicType2 = createBeanBasicType();
        basicType2.setDate(new Date());
        assertTrue(ObjectComparator.equals(basicType1, basicType2));

        BeanBasicType basicType3 = createBeanBasicType();
        basicType3.setDate(null);
        assertFalse(ObjectComparator.equals(basicType1, basicType3));
        assertFalse(ObjectComparator.equals(basicType3, basicType1));

        assertFalse(ObjectComparator.equals(basicType1, createBeanBasicType()));
    }

    /* todo:
    public void testBeanWithList() throws Exception {
        assertTrue(ObjectComparator.equals(new BeanWithList(), new BeanWithList()));
        assertFalse(ObjectComparator.equals(new BeanWithList(), createBeanWithList()));

        BeanWithList b1 = createBeanWithList();
        b1.setBooleans(Arrays.asList(new Boolean[0]));
        assertFalse(ObjectComparator.equals(b1, createBeanWithList()));
        b1.setBooleans(Arrays.asList(new Boolean[]{true}));
        assertFalse(ObjectComparator.equals(b1, createBeanWithList()));
        b1.setBooleans(Arrays.asList(new Boolean[]{false, true}));
        assertFalse(ObjectComparator.equals(b1, createBeanWithList()));

        BeanWithList b5 = createBeanWithList();
        b5.setIntegers(Arrays.asList(new Integer[]{1, 2, 3, 4, 5}));
        assertFalse(ObjectComparator.equals(b5, createBeanWithList()));

        BeanWithList b6 = createBeanWithList();
        b6.setLongs(Arrays.asList(new Long[]{1l, 3l, 2l, 4l}));
        assertFalse(ObjectComparator.equals(b6, createBeanWithList()));

        BeanWithList b8 = createBeanWithList();
        b8.setDoubles(Arrays.asList(new Double[]{12.5, 23.4, 23.0, 234.3, 2222.2}));
        assertFalse(ObjectComparator.equals(b8, createBeanWithList()));

        BeanWithList b9 = createBeanWithList();
        b9.setDoubles(null);
        assertFalse(ObjectComparator.equals(b9, createBeanWithList()));

        BeanWithList b10 = createBeanWithList();
        BeanBasicType bbt = createBeanBasicType();
        bbt.setIntNumber(234567);
        b10.setObjects(Arrays.asList(new BeanBasicType[] {createBeanBasicType(), bbt}));
        assertFalse(ObjectComparator.equals(b10, createBeanWithList()));

        assertTrue(ObjectComparator.equals(createBeanWithList(), createBeanWithList()));
    }
    */

    public void testComboType() throws Exception {
        assertTrue(ObjectComparator.equals(new BeanComboType(), new BeanComboType()));
        assertTrue(ObjectComparator.equals(createBeanComboType(), createBeanComboType()));

        BeanComboType comboType = createBeanComboType();
        comboType.setList(null);
        assertFalse(ObjectComparator.equals(comboType, createBeanComboType()));
        comboType.addListItem(createBeanBasicType());
        assertFalse(ObjectComparator.equals(comboType, createBeanComboType()));

        BeanBasicType basicType = createBeanBasicType();
        basicType.setLongNumber(12098l);
        comboType.addListItem(basicType);
        assertFalse(ObjectComparator.equals(comboType, createBeanComboType()));

        BeanComboType comboType1 = createBeanComboType();
        comboType1.setFoo(null);
        assertFalse(ObjectComparator.equals(comboType, createBeanComboType()));
        comboType1.setFoo(basicType);
        assertFalse(ObjectComparator.equals(comboType, createBeanComboType()));
    }

    public void testBaseType() throws Exception {
        assertTrue(ObjectComparator.equals(new BeanWithBaseClass(), new BeanWithBaseClass()));
        assertTrue(ObjectComparator.equals(createBeanWithBaseClass(), createBeanWithBaseClass()));

        BeanWithBaseClass baseClass = createBeanWithBaseClass();
        assertTrue(ObjectComparator.equals(baseClass, createBeanWithBaseClass()));
        baseClass.setLongNumber(23480l);
        assertFalse(ObjectComparator.equals(baseClass, createBeanWithBaseClass()));
        baseClass.setLongNumber(0l);
        assertTrue(ObjectComparator.equals(baseClass, createBeanWithBaseClass()));

        baseClass.setList(null);
        assertFalse(ObjectComparator.equals(baseClass, createBeanWithBaseClass()));
        baseClass.addListItem(createBeanBasicType());
        assertFalse(ObjectComparator.equals(baseClass, createBeanWithBaseClass()));

        BeanBasicType basicType = createBeanBasicType();
        basicType.setLongNumber(12098l);
        baseClass.addListItem(basicType);
        assertFalse(ObjectComparator.equals(baseClass, createBeanWithBaseClass()));

        BeanWithBaseClass comboType1 = createBeanWithBaseClass();
        comboType1.setFoo(null);
        assertFalse(ObjectComparator.equals(baseClass, createBeanWithBaseClass()));
        comboType1.setFoo(basicType);
        assertFalse(ObjectComparator.equals(baseClass, createBeanWithBaseClass()));
    }

    private BeanComboType createBeanComboType() {
        BeanComboType comboType = new BeanComboType();
        comboType.addListItem(createBeanBasicType());
        comboType.addListItem(createBeanBasicType());

        comboType.setFoo(createBeanBasicType());
        comboType.setBar(createBeanBasicType());
        return comboType;
    }

    private BeanWithBaseClass createBeanWithBaseClass() {
        BeanWithBaseClass comboType = new BeanWithBaseClass();
        comboType.addListItem(createBeanBasicType());
        comboType.addListItem(createBeanBasicType());

        comboType.setFoo(createBeanBasicType());
        comboType.setBar(createBeanBasicType());
        return comboType;
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
        b1.setString("test object");
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

    private BasicObjectArrayType createBasicObjectArrayType() {
        BasicObjectArrayType b1 = new BasicObjectArrayType();
        b1.setBooleans(new Boolean[]{true, false});
        b1.setBytes(new Byte[]{(byte) 20});
        b1.setChars(new Character[]{'5'});
        b1.setShorts(new Short[] { (short) 59 });
        b1.setIntegers(new Integer[0]);
        b1.setLongs(new Long[] {1l, 2l, 10l});
        b1.setFloats(new Float[] {45.9f, 34.0f});
        b1.setDoubles(new Double[] {99.3, 9.0, 123.4, 34.2});
        b1.setObjects(new BasicType[] {createBasicType(), createBasicType(), createBasicType()});
        return b1;
    }

    private BeanBasicType createBeanBasicType() {
        BeanBasicType bean = new BeanBasicType();
        bean.setBooleanValue(false);
        bean.setDoubleNumber(3.1425);
        bean.setFloatNumber(503.0f);
        bean.setIntNumber(451);
        bean.setLongNumber(590l);
        bean.setString("test string");
        return bean;
    }

    private BeanWithList createBeanWithList() {
        BeanWithList b1 = new BeanWithList();
        List<Boolean> booleans = Arrays.asList(new Boolean[]{true, false});
        Collections.sort(booleans);
        b1.setBooleans(booleans);

        List<Integer> integers = Arrays.asList(new Integer[0]);
        Collections.sort(integers);
        b1.setIntegers(integers);

        List<Long> longs = Arrays.asList(new Long[]{1l, 2l, 10l});
        Collections.sort(longs);
        b1.setLongs(longs);

        List<Double> doubles = Arrays.asList(new Double[]{99.3, 9.0, 123.4, 34.2});
        Collections.sort(doubles);
        b1.setDoubles(doubles);

        List<Date> dates = Arrays.asList(new Date[]{new Date()});
        Collections.sort(dates);
        b1.setDates(dates);

        List<BeanBasicType> types = Arrays.asList(new BeanBasicType[]{createBeanBasicType(), createBeanBasicType()});
        Collections.sort(types);
        b1.setObjects(types);
        return b1;
    }

    // todo: test
}
