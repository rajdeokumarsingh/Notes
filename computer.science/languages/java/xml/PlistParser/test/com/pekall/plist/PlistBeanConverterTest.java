package com.pekall.plist;

import com.dd.plist.*;
import com.pekall.plist.beans.*;
import junit.framework.TestCase;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/5/13
 * Time: 3:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlistBeanConverterTest extends TestCase {
    private static final java.lang.String XML_BEAN_BASIC_TYPE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>string</key>\n" +
            "\t<string>test string</string>\n" +
            "\t<key>float_number</key>\n" +
            "\t<real>503.0</real>\n" +
            "\t<key>double_number</key>\n" +
            "\t<real>3.1425</real>\n" +
            "\t<key>int_number</key>\n" +
            "\t<integer>451</integer>\n" +
            "\t<key>long_number</key>\n" +
            "\t<integer>590</integer>\n" +
            "\t<key>boolean_value</key>\n" +
            "\t<false/>\n" +
            "\t<key>date</key>\n" +
            "\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t<key>byte_array</key>\n" +
            "\t<data>\n" +
            "\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t</data>\n" +
            "</dict>\n" +
            "</plist>";

    public void testNsdictWithBasicType2Xml() throws Exception {
        BeanBasicType bean = createBeanBasicType();
        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(bean);
        String xml = PlistXmlParser.toXml(dictionary);

        assertEquals(xml, XML_BEAN_BASIC_TYPE);
        PlistDebug.logTest(xml);
    }

    public void testNsdictWithBasicTypeFromXml() throws Exception {
        NSDictionary dictionary = (NSDictionary) PlistXmlParser.fromXml(XML_BEAN_BASIC_TYPE);
        BeanBasicType bean = (BeanBasicType) PlistBeanConverter
                .createBeanFromNdict(dictionary, BeanBasicType.class);
        assertEquals(bean, createBeanBasicType());
    }

    public void testNsdictWithArray() throws Exception {
        BeanWithList beanWithList = new BeanWithList();
        beanWithList.addListItem(createBeanBasicType());
        beanWithList.addListItem(createBeanBasicType());

        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(beanWithList);
        String xml = PlistXmlParser.toXml(dictionary);

        // assertEquals(xml, XML_BEAN_BASIC_TYPE);
        PlistDebug.logTest(xml);

        NSDictionary dictionary1 = (NSDictionary) PlistXmlParser.fromXml(xml);
        BeanWithList beanWithList1 = (BeanWithList) PlistBeanConverter
                .createBeanFromNdict(dictionary1, BeanWithList.class);

        assertEquals(beanWithList.toString(), beanWithList1.toString());
        assertEquals(beanWithList, beanWithList1);
    }

    public void testNsdictWithDict() throws Exception {
        BeanWithObject beanWithObject = new BeanWithObject(
                createBeanBasicType(), createBeanBasicType());

        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(beanWithObject);
        String xml = PlistXmlParser.toXml(dictionary);

        PlistDebug.logTest("xml : " + xml);

        NSDictionary dictionary1 = (NSDictionary) PlistXmlParser.fromXml(xml);
        BeanWithObject beanWithObject1 = (BeanWithObject) PlistBeanConverter
                .createBeanFromNdict(dictionary1, BeanWithObject.class);
        assertEquals(beanWithObject, beanWithObject1);
    }

    public void testNsdictWithComboType() throws Exception {
        BeanComboType comboType = new BeanComboType();
        comboType.addListItem(createBeanBasicType());
        comboType.addListItem(createBeanBasicType());

        comboType.setFoo(createBeanBasicType());
        comboType.setBar(createBeanBasicType());

        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(comboType);
        String xml = PlistXmlParser.toXml(dictionary);
        PlistDebug.logTest(xml);

        NSDictionary dictionary1 = (NSDictionary) PlistXmlParser.fromXml(xml);
        BeanComboType comboType1 = (BeanComboType) PlistBeanConverter
                .createBeanFromNdict(dictionary1, BeanComboType.class);
        assertEquals(comboType, comboType1);
    }

    public void testNsdictWithBaseClass() throws Exception {
        BeanWithBaseClass baseType = new BeanWithBaseClass();
        baseType.addListItem(createBeanBasicType());
        baseType.addListItem(createBeanBasicType());

        baseType.setFoo(createBeanBasicType());
        baseType.setBar(createBeanBasicType());

        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(baseType);
        String xml = PlistXmlParser.toXml(dictionary);
        PlistDebug.logTest(xml);

        NSDictionary dictionary1 = (NSDictionary) PlistXmlParser.fromXml(xml);
        BeanWithBaseClass baseType1 = (BeanWithBaseClass) PlistBeanConverter
                .createBeanFromNdict(dictionary1, BeanWithBaseClass.class);
        assertEquals(baseType, baseType1);
    }

    public void testNullObject() throws Exception {
        BeanWithNullObject nullObject = new BeanWithNullObject();
        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(nullObject);
        String xml = PlistXmlParser.toXml(dictionary);

        PlistDebug.logTest(xml);

        NSDictionary dictionary1 = (NSDictionary) PlistXmlParser.fromXml(xml);
        BeanWithNullObject nullObject1 = (BeanWithNullObject) PlistBeanConverter
                .createBeanFromNdict(dictionary1, BeanWithNullObject.class);
        assertEquals(nullObject, nullObject1);
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

    public void testNestArray() throws Exception {
        PlistDebug.setVerboseDebugLog(true);
        BeanWithNestArray arrayBean = new BeanWithNestArray();
        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(arrayBean);
        String xml = PlistXmlParser.toXml(dictionary);

        PlistDebug.logTest(xml);
    }
}
