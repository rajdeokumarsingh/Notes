package com.pekall.plist.su.settings;

import com.pekall.plist.ObjectComparator;
import com.pekall.plist.PayloadXmlMsgParser;
import com.pekall.plist.PlistDebug;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.su.settings.launcher.ApkItem;
import com.pekall.plist.su.settings.launcher.LauncherSettings;
import com.pekall.plist.su.settings.launcher.WebItem;
import junit.framework.TestCase;

public class LauncherSettingsTest extends TestCase {

    public void testGenAppXml() throws Exception {
        PayloadBase settings = getSettings();
        String xml = settings.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_APP_XML);
    }

    public void testParseAppXml() throws Exception {
        LauncherSettings settings = LauncherSettings.fromXmlT(TEST_APP_XML, LauncherSettings.class);

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
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_LAUNCHER_SETTINGS),
                getSettings());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_LAUNCHER_SETTINGS), getSettings()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadBase getSettings() {
        LauncherSettings settings = new LauncherSettings();
        settings.setPhoneModel("HTC ONE");
        settings.setRegisterBank("望京分行");
        settings.setPhoneNumber("84622775");
        settings.setRegisterDate("2013-7-10");
        settings.setAdmin("张三");
        settings.setDeviceState("active");
        settings.setIsRegistered(1);
        settings.setAdminPassword("123456");
        settings.setWallpaper("test.png");
        ApkItem item1 = new ApkItem("浏览器", "com.android.browser",
                "com.android.browser.BrowserActivity",
                "http://www.pekall.com/icon/1234", 3, 3, 3);
        ApkItem item2 = new ApkItem("MDM客户端", "com.pekall.mdm",
                "com.pekall.mdm.MainActivity",
                "http://www.pekall.com/icon/12345", 1, 4, 2);
        WebItem item3 = new WebItem("建行主页", "http://www.ccb.com.cn",
                "http://www.pekall.com/icon/12345", 3, 3, 3);
        WebItem item4 = new WebItem("建行理财", "http://www.ccb.com.cn/licai",
                "http://www.pekall.com/icon/12345", 1, 4, 2);
        settings.addApkItem(item1);
        settings.addApkItem(item2);
        settings.addWebItem(item3);
        settings.addWebItem(item4);

        settings.setPayloadDescription("Launcher相关配置");
        settings.setPayloadDisplayName("Launcher配置");
        settings.setPayloadIdentifier("com.pekall.settings.launcher");
        settings.setPayloadOrganization("Pekall Capital");
        settings.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        settings.setPayloadVersion(1);

        return settings;
    }

    private PayloadArrayWrapper createProfile() {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(getSettings());
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

    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>PayloadContent</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>phoneModel</key>\n" +
            "\t\t\t<string>HTC ONE</string>\n" +
            "\t\t\t<key>registerBank</key>\n" +
            "\t\t\t<string>望京分行</string>\n" +
            "\t\t\t<key>phoneNumber</key>\n" +
            "\t\t\t<string>84622775</string>\n" +
            "\t\t\t<key>registerDate</key>\n" +
            "\t\t\t<string>2013-7-10</string>\n" +
            "\t\t\t<key>admin</key>\n" +
            "\t\t\t<string>张三</string>\n" +
            "\t\t\t<key>deviceState</key>\n" +
            "\t\t\t<string>active</string>\n" +
            "\t\t\t<key>isRegistered</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>adminPassword</key>\n" +
            "\t\t\t<string>123456</string>\n" +
            "\t\t\t<key>wallpaper</key>\n" +
            "\t\t\t<string>test.png</string>\n" +
            "\t\t\t<key>apkItems</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>name</key>\n" +
            "\t\t\t\t\t<string>浏览器</string>\n" +
            "\t\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t\t<string>com.android.browser</string>\n" +
            "\t\t\t\t\t<key>className</key>\n" +
            "\t\t\t\t\t<string>com.android.browser.BrowserActivity</string>\n" +
            "\t\t\t\t\t<key>screen</key>\n" +
            "\t\t\t\t\t<integer>3</integer>\n" +
            "\t\t\t\t\t<key>row</key>\n" +
            "\t\t\t\t\t<integer>3</integer>\n" +
            "\t\t\t\t\t<key>column</key>\n" +
            "\t\t\t\t\t<integer>3</integer>\n" +
            "\t\t\t\t\t<key>icon_url</key>\n" +
            "\t\t\t\t\t<string>http://www.pekall.com/icon/1234</string>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>name</key>\n" +
            "\t\t\t\t\t<string>MDM客户端</string>\n" +
            "\t\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t\t<string>com.pekall.mdm</string>\n" +
            "\t\t\t\t\t<key>className</key>\n" +
            "\t\t\t\t\t<string>com.pekall.mdm.MainActivity</string>\n" +
            "\t\t\t\t\t<key>screen</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t<key>row</key>\n" +
            "\t\t\t\t\t<integer>4</integer>\n" +
            "\t\t\t\t\t<key>column</key>\n" +
            "\t\t\t\t\t<integer>2</integer>\n" +
            "\t\t\t\t\t<key>icon_url</key>\n" +
            "\t\t\t\t\t<string>http://www.pekall.com/icon/12345</string>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>webItems</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>title</key>\n" +
            "\t\t\t\t\t<string>建行主页</string>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>http://www.ccb.com.cn</string>\n" +
            "\t\t\t\t\t<key>iconUrl</key>\n" +
            "\t\t\t\t\t<string>http://www.pekall.com/icon/12345</string>\n" +
            "\t\t\t\t\t<key>screen</key>\n" +
            "\t\t\t\t\t<integer>3</integer>\n" +
            "\t\t\t\t\t<key>row</key>\n" +
            "\t\t\t\t\t<integer>3</integer>\n" +
            "\t\t\t\t\t<key>column</key>\n" +
            "\t\t\t\t\t<integer>3</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>title</key>\n" +
            "\t\t\t\t\t<string>建行理财</string>\n" +
            "\t\t\t\t\t<key>url</key>\n" +
            "\t\t\t\t\t<string>http://www.ccb.com.cn/licai</string>\n" +
            "\t\t\t\t\t<key>iconUrl</key>\n" +
            "\t\t\t\t\t<string>http://www.pekall.com/icon/12345</string>\n" +
            "\t\t\t\t\t<key>screen</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t<key>row</key>\n" +
            "\t\t\t\t\t<integer>4</integer>\n" +
            "\t\t\t\t\t<key>column</key>\n" +
            "\t\t\t\t\t<integer>2</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.pekall.launcher.settings</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.settings.launcher</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>Launcher配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>Launcher相关配置</string>\n" +
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
            "\t<key>phoneModel</key>\n" +
            "\t<string>HTC ONE</string>\n" +
            "\t<key>registerBank</key>\n" +
            "\t<string>望京分行</string>\n" +
            "\t<key>phoneNumber</key>\n" +
            "\t<string>84622775</string>\n" +
            "\t<key>registerDate</key>\n" +
            "\t<string>2013-7-10</string>\n" +
            "\t<key>admin</key>\n" +
            "\t<string>张三</string>\n" +
            "\t<key>deviceState</key>\n" +
            "\t<string>active</string>\n" +
            "\t<key>isRegistered</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>adminPassword</key>\n" +
            "\t<string>123456</string>\n" +
            "\t<key>wallpaper</key>\n" +
            "\t<string>test.png</string>\n" +
            "\t<key>apkItems</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>name</key>\n" +
            "\t\t\t<string>浏览器</string>\n" +
            "\t\t\t<key>packageName</key>\n" +
            "\t\t\t<string>com.android.browser</string>\n" +
            "\t\t\t<key>className</key>\n" +
            "\t\t\t<string>com.android.browser.BrowserActivity</string>\n" +
            "\t\t\t<key>screen</key>\n" +
            "\t\t\t<integer>3</integer>\n" +
            "\t\t\t<key>row</key>\n" +
            "\t\t\t<integer>3</integer>\n" +
            "\t\t\t<key>column</key>\n" +
            "\t\t\t<integer>3</integer>\n" +
            "\t\t\t<key>icon_url</key>\n" +
            "\t\t\t<string>http://www.pekall.com/icon/1234</string>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>name</key>\n" +
            "\t\t\t<string>MDM客户端</string>\n" +
            "\t\t\t<key>packageName</key>\n" +
            "\t\t\t<string>com.pekall.mdm</string>\n" +
            "\t\t\t<key>className</key>\n" +
            "\t\t\t<string>com.pekall.mdm.MainActivity</string>\n" +
            "\t\t\t<key>screen</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>row</key>\n" +
            "\t\t\t<integer>4</integer>\n" +
            "\t\t\t<key>column</key>\n" +
            "\t\t\t<integer>2</integer>\n" +
            "\t\t\t<key>icon_url</key>\n" +
            "\t\t\t<string>http://www.pekall.com/icon/12345</string>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>webItems</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>title</key>\n" +
            "\t\t\t<string>建行主页</string>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>http://www.ccb.com.cn</string>\n" +
            "\t\t\t<key>iconUrl</key>\n" +
            "\t\t\t<string>http://www.pekall.com/icon/12345</string>\n" +
            "\t\t\t<key>screen</key>\n" +
            "\t\t\t<integer>3</integer>\n" +
            "\t\t\t<key>row</key>\n" +
            "\t\t\t<integer>3</integer>\n" +
            "\t\t\t<key>column</key>\n" +
            "\t\t\t<integer>3</integer>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>title</key>\n" +
            "\t\t\t<string>建行理财</string>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>http://www.ccb.com.cn/licai</string>\n" +
            "\t\t\t<key>iconUrl</key>\n" +
            "\t\t\t<string>http://www.pekall.com/icon/12345</string>\n" +
            "\t\t\t<key>screen</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>row</key>\n" +
            "\t\t\t<integer>4</integer>\n" +
            "\t\t\t<key>column</key>\n" +
            "\t\t\t<integer>2</integer>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>com.pekall.launcher.settings</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.settings.launcher</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>Launcher配置</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>Launcher相关配置</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";

}
