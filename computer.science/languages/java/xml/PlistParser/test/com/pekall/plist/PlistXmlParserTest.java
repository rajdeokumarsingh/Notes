package com.pekall.plist;

import com.dd.plist.*;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/5/13
 * Time: 3:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlistXmlParserTest extends TestCase {

    public void testNsdictBasicType2Xml() throws Exception {
        String xml = PlistXmlParser.toXml(createNsdictWithBasicType());
        PlistDebug.log(xml);
        assertEquals(xml, NSDICT_WITH_BASIC_TYPE);
    }

    public void testNsdictBasicTypeFromXml() throws Exception {
        assertNsdictWithBasicType(
                (NSDictionary) PlistXmlParser.fromXml(NSDICT_WITH_BASIC_TYPE));
    }

    private void assertNsdictWithBasicType(NSDictionary root) {
        assertEquals(((NSString) root.objectForKey("string")).getContent(), "test string");
        assertEquals(((NSNumber) root.objectForKey("integer")).intValue(), 10);
        assertEquals(((NSNumber) root.objectForKey("long")).longValue(), 100l);
        assertEquals(((NSNumber) root.objectForKey("double")).doubleValue(), 3.1415926);
        assertEquals(((NSNumber) root.objectForKey("float")).floatValue(), 3.1415926f);
        assertEquals(((NSNumber) root.objectForKey("boolean")).boolValue(), true);

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 1, 13, 9, 28, 49);
        Date date = cal.getTime();
        Date date1 = ((NSDate) root.objectForKey("date")).getDate();
        assertEquals(date.toString(), date1.toString());

        byte[] data = new byte[50];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        assertTrue(Arrays.equals(((NSData) root.objectForKey("data")).bytes(), data));
    }

    private NSDictionary createNsdictWithBasicType() {
        NSDictionary root = new NSDictionary();
        root.put("string", "test string");
        root.put("integer", 10);
        root.put("long", 100l);
        root.put("double", 3.1415926);
        root.put("float", 3.1415926f);
        root.put("boolean", true);

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 1, 13, 9, 28, 49);
        root.put("date", cal.getTime());

        byte[] data = new byte[50];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        root.put("data", data);
        return root;
    }

    public void testNsdictArray2Xml() throws Exception {
        NSDictionary root = new NSDictionary();
        root.put("array1", createNsarrayWithBasicType());
        root.put("array2", createNsarrayWithBasicType());

        String xml = PlistXmlParser.toXml(root);
        PlistDebug.log(xml);
        assertEquals(xml, NSDICT_WITH_NSARRAY);
    }

    public void testNsdictArrayFromXml() throws Exception {
        NSDictionary root = (NSDictionary) PlistXmlParser.fromXml(NSDICT_WITH_NSARRAY);
        assertNsarrayWithBasicType((NSArray) root.objectForKey("array1"));
        assertNsarrayWithBasicType((NSArray) root.objectForKey("array2"));
    }

    public void testNsdictNestNSDict2Xml() throws Exception {
        NSDictionary root = new NSDictionary();
        root.put("dict1", createNsdictWithBasicType());
        root.put("dict2", createNsdictWithBasicType());

        String xml = PlistXmlParser.toXml(root);
        PlistDebug.log(xml);
        assertEquals(xml, NSDICT_WITH_NSDICT);
    }

    public void testNsdictNestNSDictFromXml() throws Exception {
        NSDictionary root = (NSDictionary) PlistXmlParser.fromXml(NSDICT_WITH_NSDICT);
        assertNsdictWithBasicType((NSDictionary) root.objectForKey("dict1"));
        assertNsdictWithBasicType((NSDictionary) root.objectForKey("dict2"));
    }

    public void testNsdictWithComboType() throws Exception {
        NSDictionary root = createNsdictWithBasicType();
        root.put("array1", createNsarrayWithBasicType());
        root.put("array2", createNsarrayWithBasicType());

        root.put("dict1", createNsdictWithBasicType());
        root.put("dict2", createNsdictWithBasicType());

        String xml = PlistXmlParser.toXml(root);
        PlistDebug.log(xml);
        assertEquals(xml, NSDICT_WITH_COMBO_TYPE);
    }

    public void testNsdictWithComboTypeFromXml() throws Exception {
        NSDictionary root = (NSDictionary) PlistXmlParser.fromXml(NSDICT_WITH_COMBO_TYPE);

        assertNsdictWithBasicType(root);
        assertNsarrayWithBasicType((NSArray) root.objectForKey("array1"));
        assertNsarrayWithBasicType((NSArray) root.objectForKey("array2"));
        assertNsdictWithBasicType((NSDictionary) root.objectForKey("dict1"));
        assertNsdictWithBasicType((NSDictionary) root.objectForKey("dict2"));
    }

    private void assertNsarrayWithBasicType(NSArray array) {
        assertEquals(6, array.count());
        assertNsarrayInternal(array);
    }

    private void assertNsarrayInternal(NSArray array) {
        assertEquals(((NSString) array.objectAtIndex(0)).getContent(), "test string");
        assertEquals(((NSNumber) array.objectAtIndex(1)).doubleValue(), 10.0);
        assertEquals(((NSNumber) array.objectAtIndex(2)).intValue(), 5);
        assertEquals(((NSNumber) array.objectAtIndex(3)).boolValue(), true);

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 1, 13, 9, 28, 49);
        Date date = cal.getTime();
        Date date1 = ((NSDate) array.objectAtIndex(4)).getDate();
        assertEquals(date.toString(), date1.toString());

        byte[] data = new byte[50];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        assertTrue(Arrays.equals(((NSData) array.objectAtIndex(5)).bytes(), data));
    }

    private NSArray createNsarrayWithBasicType() {
        NSArray array = new NSArray(6);
        array.setValue(0, new NSString("test string"));
        array.setValue(1, new NSNumber(10.0));
        array.setValue(2, new NSNumber(5));
        array.setValue(3, new NSNumber(true));

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 1, 13, 9, 28, 49);
        array.setValue(4, new NSDate(cal.getTime()));

        byte[] data = new byte[50];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        array.setValue(5, new NSData(data));

        return array;
    }

    public void testNsarrayWithDict() throws Exception {
        NSArray array = new NSArray(2);
        array.setValue(0, createNsdictWithBasicType());
        array.setValue(1, createNsdictWithBasicType());

        String xml = PlistXmlParser.toXml(array);
        PlistDebug.log(xml);
        assertEquals(xml, NSARRAY_WITH_DICT);
    }

    public void testNsarrayWithDictFromXml() throws Exception {
        NSArray array = (NSArray) PlistXmlParser.fromXml(NSARRAY_WITH_DICT);
        assertNsdictWithBasicType((NSDictionary) array.objectAtIndex(0));
        assertNsdictWithBasicType((NSDictionary) array.objectAtIndex(1));
    }


    public void testNsarrayWithArray() throws Exception {
        NSArray array = new NSArray(2);
        array.setValue(0, createNsarrayWithBasicType());
        array.setValue(1, createNsarrayWithBasicType());

        String xml = PlistXmlParser.toXml(array);
        PlistDebug.log(xml);
        assertEquals(xml, NSARRAY_WITH_ARRAY);
    }

    public void testNsarrayWithArrayFromXml() throws Exception {
        NSArray array = (NSArray) PlistXmlParser.fromXml(NSARRAY_WITH_ARRAY);
        assertNsarrayWithBasicType((NSArray) array.objectAtIndex(0));
        assertNsarrayWithBasicType((NSArray) array.objectAtIndex(1));
    }

    public void testNsarrayWithComboType() throws Exception {
        NSArray array = new NSArray(8);
        array.setValue(0, new NSString("test string"));
        array.setValue(1, new NSNumber(10.0));
        array.setValue(2, new NSNumber(5));
        array.setValue(3, new NSNumber(true));

        Calendar cal = Calendar.getInstance();
        cal.set(2011, 1, 13, 9, 28, 49);
        array.setValue(4, new NSDate(cal.getTime()));

        byte[] data = new byte[50];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        array.setValue(5, new NSData(data));

        array.setValue(6, createNsarrayWithBasicType());
        array.setValue(7, createNsdictWithBasicType());

        String xml = PlistXmlParser.toXml(array);
        PlistDebug.log(xml);
        assertEquals(xml, NSARRAY_WITH_COMBO);
    }

    public void testNsarrayWithComboTypeFromXml() throws Exception {
        NSArray array = (NSArray) PlistXmlParser.fromXml(NSARRAY_WITH_COMBO);
        assertNsarrayInternal(array);
        assertNsarrayWithBasicType((NSArray) array.objectAtIndex(6));
        assertNsdictWithBasicType((NSDictionary) array.objectAtIndex(7));
    }

    private static final java.lang.String NSDICT_WITH_BASIC_TYPE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>string</key>\n" +
            "\t<string>test string</string>\n" +
            "\t<key>integer</key>\n" +
            "\t<integer>10</integer>\n" +
            "\t<key>long</key>\n" +
            "\t<integer>100</integer>\n" +
            "\t<key>double</key>\n" +
            "\t<real>3.1415926</real>\n" +
            "\t<key>float</key>\n" +
            "\t<real>3.141592502593994</real>\n" +
            "\t<key>boolean</key>\n" +
            "\t<true/>\n" +
            "\t<key>date</key>\n" +
            "\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t<key>data</key>\n" +
            "\t<data>\n" +
            "\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t</data>\n" +
            "</dict>\n" +
            "</plist>";

    private static final java.lang.String NSDICT_WITH_NSARRAY = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>array1</key>\n" +
            "\t<array>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<real>10.0</real>\n" +
            "\t\t<integer>5</integer>\n" +
            "\t\t<true/>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</array>\n" +
            "\t<key>array2</key>\n" +
            "\t<array>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<real>10.0</real>\n" +
            "\t\t<integer>5</integer>\n" +
            "\t\t<true/>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</array>\n" +
            "</dict>\n" +
            "</plist>";

    private static final java.lang.String NSDICT_WITH_NSDICT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>dict1</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>string</key>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<key>integer</key>\n" +
            "\t\t<integer>10</integer>\n" +
            "\t\t<key>long</key>\n" +
            "\t\t<integer>100</integer>\n" +
            "\t\t<key>double</key>\n" +
            "\t\t<real>3.1415926</real>\n" +
            "\t\t<key>float</key>\n" +
            "\t\t<real>3.141592502593994</real>\n" +
            "\t\t<key>boolean</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>date</key>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<key>data</key>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</dict>\n" +
            "\t<key>dict2</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>string</key>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<key>integer</key>\n" +
            "\t\t<integer>10</integer>\n" +
            "\t\t<key>long</key>\n" +
            "\t\t<integer>100</integer>\n" +
            "\t\t<key>double</key>\n" +
            "\t\t<real>3.1415926</real>\n" +
            "\t\t<key>float</key>\n" +
            "\t\t<real>3.141592502593994</real>\n" +
            "\t\t<key>boolean</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>date</key>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<key>data</key>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    private static final java.lang.String NSDICT_WITH_COMBO_TYPE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>string</key>\n" +
            "\t<string>test string</string>\n" +
            "\t<key>integer</key>\n" +
            "\t<integer>10</integer>\n" +
            "\t<key>long</key>\n" +
            "\t<integer>100</integer>\n" +
            "\t<key>double</key>\n" +
            "\t<real>3.1415926</real>\n" +
            "\t<key>float</key>\n" +
            "\t<real>3.141592502593994</real>\n" +
            "\t<key>boolean</key>\n" +
            "\t<true/>\n" +
            "\t<key>date</key>\n" +
            "\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t<key>data</key>\n" +
            "\t<data>\n" +
            "\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t</data>\n" +
            "\t<key>array1</key>\n" +
            "\t<array>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<real>10.0</real>\n" +
            "\t\t<integer>5</integer>\n" +
            "\t\t<true/>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</array>\n" +
            "\t<key>array2</key>\n" +
            "\t<array>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<real>10.0</real>\n" +
            "\t\t<integer>5</integer>\n" +
            "\t\t<true/>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</array>\n" +
            "\t<key>dict1</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>string</key>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<key>integer</key>\n" +
            "\t\t<integer>10</integer>\n" +
            "\t\t<key>long</key>\n" +
            "\t\t<integer>100</integer>\n" +
            "\t\t<key>double</key>\n" +
            "\t\t<real>3.1415926</real>\n" +
            "\t\t<key>float</key>\n" +
            "\t\t<real>3.141592502593994</real>\n" +
            "\t\t<key>boolean</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>date</key>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<key>data</key>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</dict>\n" +
            "\t<key>dict2</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>string</key>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<key>integer</key>\n" +
            "\t\t<integer>10</integer>\n" +
            "\t\t<key>long</key>\n" +
            "\t\t<integer>100</integer>\n" +
            "\t\t<key>double</key>\n" +
            "\t\t<real>3.1415926</real>\n" +
            "\t\t<key>float</key>\n" +
            "\t\t<real>3.141592502593994</real>\n" +
            "\t\t<key>boolean</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>date</key>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<key>data</key>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    private static final java.lang.String NSARRAY_WITH_DICT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<array>\n" +
            "\t<dict>\n" +
            "\t\t<key>string</key>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<key>integer</key>\n" +
            "\t\t<integer>10</integer>\n" +
            "\t\t<key>long</key>\n" +
            "\t\t<integer>100</integer>\n" +
            "\t\t<key>double</key>\n" +
            "\t\t<real>3.1415926</real>\n" +
            "\t\t<key>float</key>\n" +
            "\t\t<real>3.141592502593994</real>\n" +
            "\t\t<key>boolean</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>date</key>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<key>data</key>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</dict>\n" +
            "\t<dict>\n" +
            "\t\t<key>string</key>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<key>integer</key>\n" +
            "\t\t<integer>10</integer>\n" +
            "\t\t<key>long</key>\n" +
            "\t\t<integer>100</integer>\n" +
            "\t\t<key>double</key>\n" +
            "\t\t<real>3.1415926</real>\n" +
            "\t\t<key>float</key>\n" +
            "\t\t<real>3.141592502593994</real>\n" +
            "\t\t<key>boolean</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>date</key>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<key>data</key>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</dict>\n" +
            "</array>\n" +
            "</plist>";

    private static final java.lang.String NSARRAY_WITH_ARRAY = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<array>\n" +
            "\t<array>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<real>10.0</real>\n" +
            "\t\t<integer>5</integer>\n" +
            "\t\t<true/>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</array>\n" +
            "\t<array>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<real>10.0</real>\n" +
            "\t\t<integer>5</integer>\n" +
            "\t\t<true/>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</array>\n" +
            "</array>\n" +
            "</plist>";

    private static final java.lang.String NSARRAY_WITH_COMBO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<array>\n" +
            "\t<string>test string</string>\n" +
            "\t<real>10.0</real>\n" +
            "\t<integer>5</integer>\n" +
            "\t<true/>\n" +
            "\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t<data>\n" +
            "\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t</data>\n" +
            "\t<array>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<real>10.0</real>\n" +
            "\t\t<integer>5</integer>\n" +
            "\t\t<true/>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</array>\n" +
            "\t<dict>\n" +
            "\t\t<key>string</key>\n" +
            "\t\t<string>test string</string>\n" +
            "\t\t<key>integer</key>\n" +
            "\t\t<integer>10</integer>\n" +
            "\t\t<key>long</key>\n" +
            "\t\t<integer>100</integer>\n" +
            "\t\t<key>double</key>\n" +
            "\t\t<real>3.1415926</real>\n" +
            "\t\t<key>float</key>\n" +
            "\t\t<real>3.141592502593994</real>\n" +
            "\t\t<key>boolean</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>date</key>\n" +
            "\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t<key>data</key>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t</dict>\n" +
            "</array>\n" +
            "</plist>";
}
