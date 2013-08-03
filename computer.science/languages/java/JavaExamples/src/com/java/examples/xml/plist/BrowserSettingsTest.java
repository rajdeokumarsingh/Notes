package com.java.examples.xml.plist;

import com.dd.plist.*;
import com.java.examples.xml.plist.browser.*;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 7/31/13
 * Time: 5:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class BrowserSettingsTest extends TestCase {
    public void testGenXml() throws Exception {
        NSDictionary root = new NSDictionary();
        root.put("address_bar_display", 0);
        root.put("home_page_url", "http://www.baidu.com");

        NSDictionary quick = new NSDictionary();
        root.put("quick_launch", quick);

        NSArray array = new NSArray(2);
        NSDictionary q1 = new NSDictionary();
        q1.put("mId", "1");
        q1.put("mTitle", "baidu");
        q1.put("mNeedUpdate", 1);
        q1.put("mBlob", new byte[0]);

        NSDictionary q2 = new NSDictionary();
        q2.put("mId", "1");
        q2.put("mTitle", "baidu");
        q2.put("mNeedUpdate", 1);
        // q2.put("mBlob", new byte[0]);
        array.setValue(0, q1);
        array.setValue(1, q2);
        quick.put("quick_launches", array);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PropertyListParser.saveAsXML(root, bos);
        System.out.println("xml: " + bos.toString());
    }

    private BrowserXmlData createBrowserData() {
        QuickLaunch quickLaunches = new QuickLaunch();
        quickLaunches.addQuickLaunch(new QuickLaunchItem("", "Test1", "http://www.sina.com"));
        quickLaunches.addQuickLaunch(new QuickLaunchItem("", "Test2", "http://wap.sina.com"));
        quickLaunches.addQuickLaunch(new QuickLaunchItem("", "测试", "http://m.sina.com"));

        BrowserXmlData data = new BrowserXmlData(1, "https://www.ccb.com.cn");
        data.setQuickLaunch(quickLaunches);
        data.addWhiteListItem(new UrlMatchRule("sina.com"));
        data.addWhiteListItem(new UrlMatchRule("baidu.com", UrlMatchRule.MATCH_TYPE_EQUAL));
        data.addWhiteListItem(new UrlMatchRule("24", "baidu.com", UrlMatchRule.MATCH_TYPE_EQUAL));
        data.addHistoryItem(new HistoryWatchItem("baidu"));
        data.addHistoryItem(new HistoryWatchItem("sina", UrlMatchRule.MATCH_TYPE_PREFIX));

        return data;
    }

    public void testSuperClass() throws Exception {
        Object item = new HistoryWatchItem("http://www.baidu.com");
        Class clz = item.getClass();

        while (clz != null && !clz.equals(Object.class)) {
            System.out.println("----------------------------------------");
            System.out.println("class: " + clz);
            for (Field field : clz.getDeclaredFields()) {
                field.setAccessible(true);
                Class type = field.getType();
                if (int.class.equals(type)) {
                    System.out.println("int: " +
                            field.getName() + "," + field.getInt(item));
                } else if (String.class.equals(type)) {
                    System.out.println("string: " +
                            field.getName() + "," + (String)field.get(item));
                } else {
                    System.out.println("object: " +
                            field.getName() + "," + field.get(item).toString());
                }
            }
            clz = clz.getSuperclass();
        }
    }

    public void testBean2Ndict() throws Exception {
        NSDictionary root = PlistBeanConverter.createNdictFromBean(createBrowserData());
        System.out.println("xml: " + PlistXmlParser.toXml(root));
    }

    public void testNdict2Bean() throws Exception {
        NSDictionary root = PlistBeanConverter.createNdictFromBean(createBrowserData());
        BrowserXmlData data = (BrowserXmlData) createBeanFromNdict(root, BrowserSettingsTest.class);
        PlistDebug.log(data.toString());
    }

    private Object createBeanFromNdict(NSDictionary root, Class clz) throws
            NoSuchMethodException,  IllegalAccessException,
            InvocationTargetException, InstantiationException {

        Constructor ctor = clz.getConstructor();
        ctor.setAccessible(true);
        Object data =  ctor.newInstance();

        for(Field field : clz.getDeclaredFields()) {
            String fieldName = field.getName();
            Class fieldClass = field.getType();

            NSObject nsObject = root.objectForKey(fieldName);
            if(nsObject == null)  continue;
            if (NSNumber.class.equals(nsObject.getClass())) {
                NSNumber number = (NSNumber) nsObject;
                switch(number.type()) {
                    case NSNumber.BOOLEAN:
                        if (fieldClass.equals(boolean.class)) {
                            field.setBoolean(data, number.boolValue());
                        } else {
                            PlistDebug.log("Convert type error!");
                        }
                        break;
                    case NSNumber.INTEGER :
                        if (fieldClass.equals(int.class)) {
                            field.setInt(data, number.intValue());
                        } else if (fieldClass.equals(long.class)) {
                            field.setLong(data, number.longValue());
                        } else {
                            PlistDebug.log("Convert type error!");
                        }
                        break;
                    case NSNumber.REAL : {
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
        /*
        {
        NSDictionary rootDict = (NSDictionary) PlistXmlParser.fromXml(TEST_XML);
        NSArray array = (NSArray) rootDict.objectForKey("People");

        NSDictionary person1 = (NSDictionary) array.objectAtIndex(0);
        NSString name1 = (NSString) person1.objectForKey("Name");
        String sname1 = name1.getContent();
        NSDate date1 = (NSDate) person1.objectForKey("RegistrationDate");
        Date ddate1 = date1.getDate();
        NSNumber number1 = (NSNumber) person1.objectForKey("Age");
        int age1 = number1.intValue();

        System.out.println("parse data: " + sname1 + "," + ddate1 + "," + age1);
        }
        */

        return null;
    }
}
