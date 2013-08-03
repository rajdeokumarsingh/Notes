package com.java.examples.xml.plist;

import com.dd.plist.*;
import com.java.examples.xml.plist.browser.*;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.*;
import java.util.ArrayList;
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

    public void testListType() throws Exception {
        BrowserXmlData data = createBrowserData();

        Field field = data.getClass().getDeclaredField("whiteList");
        Type genericFieldType = field.getGenericType();
        if(genericFieldType instanceof ParameterizedType){
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for(Type fieldArgType : fieldArgTypes){
                Class fieldArgClass = (Class) fieldArgType;
                System.out.println("fieldArgClass = " + fieldArgClass);
            }
        }
    }

    public void testBean2Ndict() throws Exception {
        NSDictionary root = PlistBeanConverter.createNdictFromBean(createBrowserData());
        System.out.println("xml: " + PlistXmlParser.toXml(root));
    }

    public void testNdict2Bean() throws Exception {
        NSDictionary root = PlistBeanConverter.createNdictFromBean(createBrowserData());
        BrowserXmlData data = (BrowserXmlData) createBeanFromNdict(root, BrowserXmlData.class);
        PlistDebug.log(data.toString());
    }

    private Object createBeanFromNdict(NSDictionary root, Class clz) throws
            NoSuchMethodException,  IllegalAccessException,
            InvocationTargetException, InstantiationException {

        Constructor ctor = clz.getConstructor();
        ctor.setAccessible(true);
        Object data =  ctor.newInstance();

        for(Field field : clz.getDeclaredFields()) {
            PlistDebug.log("----------------------------------------");
            PlistDebug.log("field name: " + field.getName());
            PlistDebug.log("field class: " + field.getType());

            field.setAccessible(true);

            NSObject nsObject = root.objectForKey(field.getName());
            if(nsObject == null)  continue;
            if (nsObject.getClass().equals(NSNumber.class)) {
                assignNumberField(data, field, (NSNumber) nsObject);
            } else if(nsObject.getClass().equals(NSString.class)) {
                assignStringField(data, field, (NSString) nsObject);
            } else if(nsObject.getClass().equals(NSDate.class)) {
                assignDateField(data, field, (NSDate) nsObject);
            } else if(nsObject.getClass().equals(NSData.class)) {
                assignByteArrayField(data, field, (NSData) nsObject);
            } else if(nsObject.getClass().equals(NSDictionary.class)) {
                NSDictionary dict = (NSDictionary) nsObject;
                field.set(data, createBeanFromNdict(dict, field.getType()));
            } else if(nsObject.getClass().equals(NSArray.class)) {
                // TODO: refactory to method
                Class fieldArgClass = null;
                Type genericFieldType = field.getGenericType();
                if(genericFieldType instanceof ParameterizedType){
                    ParameterizedType aType = (ParameterizedType) genericFieldType;
                    Type[] fieldArgTypes = aType.getActualTypeArguments();
                    for(Type fieldArgType : fieldArgTypes){
                        fieldArgClass = (Class) fieldArgType;
                        System.out.println("fieldArgClass = " + fieldArgClass);
                    }
                }
                if (fieldArgClass != null && List.class.isAssignableFrom(field.getType())) {
                    NSArray array = (NSArray) nsObject;
                    field.set(data, createListFromNSArray(array, fieldArgClass));
                } else {
                    PlistDebug.log("error type!");
                }
                PlistDebug.log("TODO:");
            } else {
                PlistDebug.log("error type!");
            }
        }
        return data;
    }

    private void assignByteArrayField(Object data, Field field, NSData nsObject) throws IllegalAccessException {
        if(field.getType().equals(byte[].class)) {
            field.set(data, ((NSData) nsObject).bytes());
        } else {
            PlistDebug.log("error type!");
        }
    }

    private void assignDateField(Object data, Field field, NSDate nsObject) throws IllegalAccessException {
        if(field.getType().equals(Date.class)) {
            field.set(data, ((NSDate) nsObject).getDate());
        } else {
            PlistDebug.log("error type!");
        }
    }

    private void assignStringField(Object data, Field field, NSString nsObject) throws IllegalAccessException {
        if(field.getType().equals(String.class)) {
            field.set(data, ((NSString) nsObject).getContent());
        } else {
            PlistDebug.log("error type!");
        }
    }

    public List createListFromNSArray(NSArray array, Class elementClass) {
        ArrayList list = new ArrayList();
        for (NSObject nsObject : array.getArray()) {
            // TODO:
        }
        return list;
    }

    private void assignNumberField(Object data, Field field, NSNumber number) throws IllegalAccessException {
        Class fieldClass = field.getType();
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
