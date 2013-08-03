package com.java.examples.xml.plist;

import com.dd.plist.*;
import com.java.examples.xml.plist.browser.*;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.*;
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

    public void testBrowserData() throws Exception {
        BrowserXmlData data = createBrowserData();
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
        data.addHistoryItem(new HistoryWatchItem("baidu", UrlMatchRule.MATCH_TYPE_PREFIX));

        return data;
    }

    public void testBrowserClass() throws Exception {
        Constructor ctor = BrowserXmlData.class.getConstructor();
        ctor.setAccessible(true);
        BrowserXmlData data = (BrowserXmlData) ctor.newInstance();

        Field[] fields = BrowserXmlData.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field.getName());
            Class type = field.getType();
            System.out.println(type.getName());
        }
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

    public void testBrowser2Plist() throws Exception {
        NSDictionary root = createNdict(createBrowserData());

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PropertyListParser.saveAsXML(root, bos);
        System.out.println("xml: " + bos.toString());
    }

    private NSDictionary createNdict(Object data) throws IllegalAccessException {
        NSDictionary root = new NSDictionary();
        appendData2Ndict(data, root);
        return root;
    }

    private void appendData2Ndict(Object data, NSDictionary root) throws IllegalAccessException {
        Field[] fields = data.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Class type = field.getType();

            System.out.println("========================================");
            System.out.println(field.getName());
            System.out.println(type.getName());
            System.out.println("declaring: " + type.getDeclaringClass());

            // Ignore final fields
            if ((field.getModifiers() & Modifier.FINAL) != 0) {
                System.out.println("final field, continue!");
                continue;
            }

            if (int.class.equals(type)) {
                root.put(field.getName(), field.getInt(data));
            } else if (String.class.equals(type)) {
                root.put(field.getName(), (String) field.get(data));
            } else if (List.class.equals(type)) {
                System.out.println("type list");
                List list = (List) field.get(data);
                NSArray array = new NSArray(list.size());
                for (int i = 0; i < list.size(); i++) {
                    array.setValue(i, createNdict(list.get(i)));
                }
                root.put(field.getName(), array);
            } else if (byte[].class.equals(type)) {
                System.out.println("type byte[]");
                root.put(field.getName(), new NSData((byte[]) field.get(data)));
            } else {
                System.out.println("type object");
                Object obj = field.get(data);
                NSDictionary dictionary = createNdict(obj);
                // TODO: handle super class, see testSuperClass
                root.put(field.getName(), dictionary);
            }
        }
    }
}
