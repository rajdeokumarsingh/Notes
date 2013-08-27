package com.pekall.plist.su.settings.browser;

import com.pekall.plist.ObjectComparator;
import com.pekall.plist.PayloadXmlMsgParser;
import com.pekall.plist.PlistDebug;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class BrowserSettingsTest extends TestCase {

    public void testGenAppXml() throws Exception {
        PayloadBase settings = getSettings();
        String xml = settings.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_APP_XML);
    }

    public void testParseAppXml() throws Exception {
        BrowserSettings settings = BrowserSettings.fromXmlT(TEST_APP_XML, BrowserSettings.class);

        assertEquals(settings, getSettings());
        assertTrue(ObjectComparator.equals(settings, getSettings()));
    }

   public void testGenXml() throws Exception {
        PayloadArrayWrapper profile = createProfile();
        String xml = profile.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        PlistDebug.logTest(profile.toString());

        assertEquals(profile, createProfile());
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_SE_BROWSER_SETTINGS),
                getSettings());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_SE_BROWSER_SETTINGS), getSettings()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    public void testGenXmlPartial() throws Exception {
        PayloadArrayWrapper profile = createProfilePartial();
        String xml = profile.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML_PARTIAL);
    }

    public void testParseXmlPartial() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML_PARTIAL);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        PlistDebug.logTest(profile.toString());

        assertEquals(profile, createProfilePartial());
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_SE_BROWSER_SETTINGS), getBrowserSettingsPartial());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_SE_BROWSER_SETTINGS), getBrowserSettingsPartial()));
    }

    public void testTwoWayPartial() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML_PARTIAL);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML_PARTIAL, profile.toXml());
    }

    private PayloadArrayWrapper createProfile() {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(getSettings());
        return wrapper;
    }

    private PayloadArrayWrapper createProfilePartial() {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(getBrowserSettingsPartial());
        return wrapper;
    }

    private PayloadArrayWrapper createWrapper() {
        PayloadArrayWrapper wrapper = new PayloadArrayWrapper();
        wrapper.setPayloadDescription("描述文件描述。wjl 测试");
        wrapper.setPayloadDisplayName("Pekall MDM Profile");
        wrapper.setPayloadIdentifier("com.pekall.profile");
        wrapper.setPayloadOrganization("Pekall Capital");
        wrapper.setPayloadType("Configuration");
        wrapper.setPayloadUUID("2ED160FF-4B6C-47DD-8105-769231367D2A");
        wrapper.setPayloadVersion(1);
        wrapper.setPayloadRemovalDisallowed(true);
        return wrapper;
    }

    private BrowserSettings getSettings() {
        List<QuickLaunchItem> quickLaunches = new ArrayList<QuickLaunchItem>();
        List<UrlMatchRule> whiteList = new ArrayList<UrlMatchRule>();
        List<HistoryWatchItem> historyWatches = new ArrayList<HistoryWatchItem>();

        quickLaunches.add(new QuickLaunchItem("", "Test1", "http://www.sina.com"));
        quickLaunches.add(new QuickLaunchItem("", "Test2", "http://wap.sina.com"));
        quickLaunches.add(new QuickLaunchItem("", "测试", "http://m.sina.com"));

        whiteList.add(new UrlMatchRule("sina.com"));
        whiteList.add(new UrlMatchRule("baidu.com", UrlMatchRule.MATCH_TYPE_EQUAL));
        whiteList.add(new UrlMatchRule("24", "baidu.com", UrlMatchRule.MATCH_TYPE_EQUAL));

        historyWatches.add(new HistoryWatchItem("baidu"));
        historyWatches.add(new HistoryWatchItem("baidu", UrlMatchRule.MATCH_TYPE_PREFIX));

        BrowserSettings settings = new BrowserSettings(1, "https://www.ccb.com.cn",
                quickLaunches, whiteList, historyWatches);

        settings.setPayloadDescription("SeBrowser相关配置");
        settings.setPayloadDisplayName("SeBrowser配置");
        settings.setPayloadIdentifier("com.pekall.settings.sebrowser");
        settings.setPayloadOrganization("Pekall Capital");
        settings.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        settings.setPayloadVersion(1);
        return settings;
    }
    private BrowserSettings getBrowserSettingsPartial() {
        List<HistoryWatchItem> historyWatches = new ArrayList<HistoryWatchItem>();

        historyWatches.add(new HistoryWatchItem("baidu"));
        historyWatches.add(new HistoryWatchItem("baidu", UrlMatchRule.MATCH_TYPE_PREFIX));

        BrowserSettings settings = new BrowserSettings(1, "https://www.ccb.com.cn",
                null, null, historyWatches);

        settings.setPayloadDescription("SeBrowser相关配置");
        settings.setPayloadDisplayName("SeBrowser配置");
        settings.setPayloadIdentifier("com.pekall.settings.sebrowser");
        settings.setPayloadOrganization("Pekall Capital");
        settings.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        settings.setPayloadVersion(1);
        return settings;
    }

    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>PayloadContent</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>addressBarDisplay</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>homePageUrl</key>\n" +
            "\t\t\t<string>https://www.ccb.com.cn</string>\n" +
            "\t\t\t<key>quickLaunchItems</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>id</key>\n" +
            "\t\t\t\t\t<string></string>\n" +
            "\t\t\t\t\t<key>title</key>\n" +
            "\t\t\t\t\t<string>Test1</string>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>http://www.sina.com</string>\n" +
            "\t\t\t\t\t<key>needUpdate</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t<key>blob</key>\n" +
            "\t\t\t\t\t<data>\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t\t\t</data>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>id</key>\n" +
            "\t\t\t\t\t<string></string>\n" +
            "\t\t\t\t\t<key>title</key>\n" +
            "\t\t\t\t\t<string>Test2</string>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>http://wap.sina.com</string>\n" +
            "\t\t\t\t\t<key>needUpdate</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t<key>blob</key>\n" +
            "\t\t\t\t\t<data>\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t\t\t</data>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>id</key>\n" +
            "\t\t\t\t\t<string></string>\n" +
            "\t\t\t\t\t<key>title</key>\n" +
            "\t\t\t\t\t<string>测试</string>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>http://m.sina.com</string>\n" +
            "\t\t\t\t\t<key>needUpdate</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t<key>blob</key>\n" +
            "\t\t\t\t\t<data>\n" +
            "\t\t\t\t\t\t\n" +
            "\t\t\t\t\t</data>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>whiteList</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>sina.com</string>\n" +
            "\t\t\t\t\t<key>matchType</key>\n" +
            "\t\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>baidu.com</string>\n" +
            "\t\t\t\t\t<key>matchType</key>\n" +
            "\t\t\t\t\t<integer>3</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>id</key>\n" +
            "\t\t\t\t\t<string>24</string>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>baidu.com</string>\n" +
            "\t\t\t\t\t<key>matchType</key>\n" +
            "\t\t\t\t\t<integer>3</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>historyWatchItems</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>count</key>\n" +
            "\t\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t\t<key>dates</key>\n" +
            "\t\t\t\t\t<string></string>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>baidu</string>\n" +
            "\t\t\t\t\t<key>matchType</key>\n" +
            "\t\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>count</key>\n" +
            "\t\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t\t<key>dates</key>\n" +
            "\t\t\t\t\t<string></string>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>baidu</string>\n" +
            "\t\t\t\t\t<key>matchType</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.pekall.sebrowser.settings</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.settings.sebrowser</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>SeBrowser配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>SeBrowser相关配置</string>\n" +
            "\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t<string>Pekall Capital</string>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>PayloadRemovalDisallowed</key>\n" +
            "\t<true/>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>Configuration</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.profile</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>2ED160FF-4B6C-47DD-8105-769231367D2A</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>Pekall MDM Profile</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>描述文件描述。wjl 测试</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final java.lang.String TEST_APP_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>addressBarDisplay</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>homePageUrl</key>\n" +
            "\t<string>https://www.ccb.com.cn</string>\n" +
            "\t<key>quickLaunchItems</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>id</key>\n" +
            "\t\t\t<string></string>\n" +
            "\t\t\t<key>title</key>\n" +
            "\t\t\t<string>Test1</string>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>http://www.sina.com</string>\n" +
            "\t\t\t<key>needUpdate</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>blob</key>\n" +
            "\t\t\t<data>\n" +
            "\t\t\t\t\n" +
            "\t\t\t</data>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>id</key>\n" +
            "\t\t\t<string></string>\n" +
            "\t\t\t<key>title</key>\n" +
            "\t\t\t<string>Test2</string>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>http://wap.sina.com</string>\n" +
            "\t\t\t<key>needUpdate</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>blob</key>\n" +
            "\t\t\t<data>\n" +
            "\t\t\t\t\n" +
            "\t\t\t</data>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>id</key>\n" +
            "\t\t\t<string></string>\n" +
            "\t\t\t<key>title</key>\n" +
            "\t\t\t<string>测试</string>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>http://m.sina.com</string>\n" +
            "\t\t\t<key>needUpdate</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>blob</key>\n" +
            "\t\t\t<data>\n" +
            "\t\t\t\t\n" +
            "\t\t\t</data>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>whiteList</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>sina.com</string>\n" +
            "\t\t\t<key>matchType</key>\n" +
            "\t\t\t<integer>0</integer>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>baidu.com</string>\n" +
            "\t\t\t<key>matchType</key>\n" +
            "\t\t\t<integer>3</integer>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>id</key>\n" +
            "\t\t\t<string>24</string>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>baidu.com</string>\n" +
            "\t\t\t<key>matchType</key>\n" +
            "\t\t\t<integer>3</integer>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>historyWatchItems</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>count</key>\n" +
            "\t\t\t<integer>0</integer>\n" +
            "\t\t\t<key>dates</key>\n" +
            "\t\t\t<string></string>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>baidu</string>\n" +
            "\t\t\t<key>matchType</key>\n" +
            "\t\t\t<integer>0</integer>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>count</key>\n" +
            "\t\t\t<integer>0</integer>\n" +
            "\t\t\t<key>dates</key>\n" +
            "\t\t\t<string></string>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>baidu</string>\n" +
            "\t\t\t<key>matchType</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>com.pekall.sebrowser.settings</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.settings.sebrowser</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>SeBrowser配置</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>SeBrowser相关配置</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final java.lang.String TEST_XML_PARTIAL = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>PayloadContent</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>addressBarDisplay</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>homePageUrl</key>\n" +
            "\t\t\t<string>https://www.ccb.com.cn</string>\n" +
            "\t\t\t<key>historyWatchItems</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>count</key>\n" +
            "\t\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t\t<key>dates</key>\n" +
            "\t\t\t\t\t<string></string>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>baidu</string>\n" +
            "\t\t\t\t\t<key>matchType</key>\n" +
            "\t\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>count</key>\n" +
            "\t\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t\t<key>dates</key>\n" +
            "\t\t\t\t\t<string></string>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>baidu</string>\n" +
            "\t\t\t\t\t<key>matchType</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.pekall.sebrowser.settings</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.settings.sebrowser</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>SeBrowser配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>SeBrowser相关配置</string>\n" +
            "\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t<string>Pekall Capital</string>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>PayloadRemovalDisallowed</key>\n" +
            "\t<true/>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>Configuration</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.profile</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>2ED160FF-4B6C-47DD-8105-769231367D2A</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>Pekall MDM Profile</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>描述文件描述。wjl 测试</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";
}
