package com.pekall.plist;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;
import com.pekall.plist.beans.*;
import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
        quickLaunches.addQuickLaunch(new QuickLaunchItem("1", "Test1", "http://www.sina.com"));
        quickLaunches.addQuickLaunch(new QuickLaunchItem("2", "Test2", "http://wap.sina.com"));
        quickLaunches.addQuickLaunch(new QuickLaunchItem("3", "测试", "http://m.sina.com"));

        BrowserXmlData data = new BrowserXmlData(1, "https://www.ccb.com.cn");
        data.setQuickLaunch(quickLaunches);
        data.addWhiteListItem(new UrlMatchRule("sina.com"));
        data.addWhiteListItem(new UrlMatchRule("baidu.com", UrlMatchRule.MATCH_TYPE_EQUAL));
        data.addWhiteListItem(new UrlMatchRule("24", "baidu.com", UrlMatchRule.MATCH_TYPE_EQUAL));

        HistoryWatchItem item = new HistoryWatchItem("baidu");
        item.setCount(20);
        item.setDate(";1234;3234234;23432");
        data.addHistoryItem(item);
        HistoryWatchItem item1 = new HistoryWatchItem("www.baidu.com",
                UrlMatchRule.MATCH_TYPE_EQUAL);
        item1.setCount(4);
        item1.setDate(";1234;3234234;23432");
        data.addHistoryItem(item1);

        return data;
    }

    public void testListType() throws Exception {
        BrowserXmlData data = createBrowserData();

        Field field = data.getClass().getDeclaredField("whiteList");
        Type genericFieldType = field.getGenericType();
        if (genericFieldType instanceof ParameterizedType) {
            ParameterizedType aType = (ParameterizedType) genericFieldType;
            Type[] fieldArgTypes = aType.getActualTypeArguments();
            for (Type fieldArgType : fieldArgTypes) {
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
        BrowserXmlData data = (BrowserXmlData) PlistBeanConverter.createBeanFromNdict(root, BrowserXmlData.class);
        PlistDebug.log(data.toString());
        assertEquals(data, createBrowserData());
    }
}
