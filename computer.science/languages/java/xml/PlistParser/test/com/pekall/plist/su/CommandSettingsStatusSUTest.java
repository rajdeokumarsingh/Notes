package com.pekall.plist.su;

import com.pekall.plist.CommandStatusMsgParser;
import com.pekall.plist.PayloadXmlMsgParser;
import com.pekall.plist.PlistDebug;
import com.pekall.plist.beans.*;
import com.pekall.plist.su.settings.SystemSettings;
import com.pekall.plist.su.settings.advertise.AdvertiseDownloadSettings;
import com.pekall.plist.su.settings.browser.BrowserSettings;
import com.pekall.plist.su.settings.browser.HistoryWatchItem;
import com.pekall.plist.su.settings.browser.QuickLaunchItem;
import com.pekall.plist.su.settings.browser.UrlMatchRule;
import com.pekall.plist.su.settings.launcher.ApkItem;
import com.pekall.plist.su.settings.launcher.LauncherSettings;
import com.pekall.plist.su.settings.launcher.WebItem;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class CommandSettingsStatusSUTest extends TestCase {

    public void testGenXml() throws Exception {
        CommandSettingsStatusSU status = createStatusMsg();

        String xml = status.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg statusMsg = parser.getMessage();

        PlistDebug.logTest(statusMsg.toString());
        assertTrue(statusMsg instanceof CommandSettingsStatusSU);
        assertEquals(statusMsg, createStatusMsg());

        // parse settings xml
        CommandSettingsStatusSU msg = (CommandSettingsStatusSU) statusMsg;
        String settings = new String(msg.getSettingsSU());
        PlistDebug.logTest(settings);
        PayloadXmlMsgParser parser1 = new PayloadXmlMsgParser(settings);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser1.getPayloadDescriptor();

        assertEquals(profile, createProfile());
        assertEquals(parser1.getPayload(PayloadBase.PAYLOAD_TYPE_ADVT_SETTINGS), getAdvertiseSettings());
        assertEquals(parser1.getPayload(PayloadBase.PAYLOAD_TYPE_SE_BROWSER_SETTINGS), getBrowserSettings());
        assertEquals(parser1.getPayload(PayloadBase.PAYLOAD_TYPE_LAUNCHER_SETTINGS), getLauncherSettings());
        assertEquals(parser1.getPayload(PayloadBase.PAYLOAD_TYPE_SYSTEM_SETTINGS), getSystemSettings());
    }

    public void testTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg statusMsg = parser.getMessage();

        assertEquals(statusMsg.toXml(), TEST_XML);
    }

    private CommandSettingsStatusSU createStatusMsg() {
        CommandSettingsStatusSU status = new CommandSettingsStatusSU(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "2ade3a08-24fc-444d-b245-5a4ba8d0a3d1");

        PayloadArrayWrapper profile = createProfile();
        String xml = profile.toXml();

        status.setSettingsSU(xml.getBytes());
        status.setDescription("test upload settings");
        status.setName("test settings");
        status.setOsType(0);

        return status;
    }

    private PayloadArrayWrapper createProfile() {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(getAdvertiseSettings());
        wrapper.addPayLoadContent(getBrowserSettings());
        wrapper.addPayLoadContent(getLauncherSettings());
        wrapper.addPayLoadContent(getSystemSettings());
        return wrapper;
    }

    private PayloadBase getAdvertiseSettings() {
        AdvertiseDownloadSettings settings = new AdvertiseDownloadSettings();
        settings.setDownloadUrl("http://www.pekall.com/adv.zip");
        settings.setVersion("25");

        settings.setPayloadDescription("广告下载相关配置");
        settings.setPayloadDisplayName("广告下载配置");
        settings.setPayloadIdentifier("com.pekall.settings.advertise.download");
        settings.setPayloadOrganization("Pekall Capital");
        settings.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        settings.setPayloadVersion(1);

        return settings;
    }

    private BrowserSettings getBrowserSettings() {
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

    private PayloadBase getLauncherSettings() {
        LauncherSettings settings = new LauncherSettings();
        settings.setPhoneModel("HTC ONE");
        settings.setRegisterBank("望京分行");
        settings.setPhoneNumber("84622775");
        settings.setRegisterDate("2013-7-10");
        settings.setAdmin("张三");
        settings.setDeviceState("active");
        settings.setIsRegistered(1);
        settings.setAdminPassword("123456");
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

    private PayloadBase getSystemSettings() {
        SystemSettings settings = new SystemSettings();
        settings.setAirplane(0);
        settings.setBluetooth(1);
        settings.setCamera(0);
        settings.setMobileData(0);
        settings.setNfc(0);

        settings.setPayloadDescription("System相关配置");
        settings.setPayloadDisplayName("System配置");
        settings.setPayloadIdentifier("com.pekall.settings.system");
        settings.setPayloadOrganization("Pekall Capital");
        settings.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        settings.setPayloadVersion(1);

        return settings;
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

    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>settingsSU</key>\n" +
            "\t<data>\n" +
            "\t\tPD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPCFET0NUWVBFIHBsaXN0IFBVQkxJQyAiLS8vQXBwbGUvL0RURCBQTElTVCAxLjAvL0VOIiAiaHR0cDovL3d3dy5hcHBsZS5jb20vRFREcy9Qcm9wZXJ0eUxpc3QtMS4wLmR0ZCI+CjxwbGlzdCB2ZXJzaW9uPSIxLjAiPgo8ZGljdD4KCTxrZXk+UGF5bG9hZENvbnRlbnQ8L2tleT4KCTxhcnJheT4KCQk8ZGljdD4KCQkJPGtleT52ZXJzaW9uPC9rZXk+CgkJCTxzdHJpbmc+MjU8L3N0cmluZz4KCQkJPGtleT5kb3dubG9hZFVybDwva2V5PgoJCQk8c3RyaW5nPmh0dHA6Ly93d3cucGVrYWxsLmNvbS9hZHYuemlwPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZFR5cGU8L2tleT4KCQkJPHN0cmluZz5jb20ucGVrYWxsLmFkdmVydGlzZS5zZXR0aW5nczwvc3RyaW5nPgoJCQk8a2V5PlBheWxvYWRWZXJzaW9uPC9rZXk+CgkJCTxpbnRlZ2VyPjE8L2ludGVnZXI+CgkJCTxrZXk+UGF5bG9hZElkZW50aWZpZXI8L2tleT4KCQkJPHN0cmluZz5jb20ucGVrYWxsLnNldHRpbmdzLmFkdmVydGlzZS5kb3dubG9hZDwvc3RyaW5nPgoJCQk8a2V5PlBheWxvYWRVVUlEPC9rZXk+CgkJCTxzdHJpbmc+MzgwOEQ3NDItNUQyMS00MDFFLUI4M0MtQUVEMUU5OTAzMzJEPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZERpc3BsYXlOYW1lPC9rZXk+CgkJCTxzdHJpbmc+5bm/5ZGK5LiL6L296YWN572uPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZERlc2NyaXB0aW9uPC9rZXk+CgkJCTxzdHJpbmc+5bm/5ZGK5LiL6L2955u45YWz6YWN572uPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZE9yZ2FuaXphdGlvbjwva2V5PgoJCQk8c3RyaW5nPlBla2FsbCBDYXBpdGFsPC9zdHJpbmc+CgkJPC9kaWN0PgoJCTxkaWN0PgoJCQk8a2V5PmFkZHJlc3NCYXJEaXNwbGF5PC9rZXk+CgkJCTxpbnRlZ2VyPjE8L2ludGVnZXI+CgkJCTxrZXk+aG9tZVBhZ2VVcmw8L2tleT4KCQkJPHN0cmluZz5odHRwczovL3d3dy5jY2IuY29tLmNuPC9zdHJpbmc+CgkJCTxrZXk+cXVpY2tMYXVuY2hJdGVtczwva2V5PgoJCQk8YXJyYXk+CgkJCQk8ZGljdD4KCQkJCQk8a2V5PmlkPC9rZXk+CgkJCQkJPHN0cmluZz48L3N0cmluZz4KCQkJCQk8a2V5PnRpdGxlPC9rZXk+CgkJCQkJPHN0cmluZz5UZXN0MTwvc3RyaW5nPgoJCQkJCTxrZXk+dXJsPC9rZXk+CgkJCQkJPHN0cmluZz5odHRwOi8vd3d3LnNpbmEuY29tPC9zdHJpbmc+CgkJCQkJPGtleT5uZWVkVXBkYXRlPC9rZXk+CgkJCQkJPGludGVnZXI+MTwvaW50ZWdlcj4KCQkJCQk8a2V5PmJsb2I8L2tleT4KCQkJCQk8ZGF0YT4KCQkJCQkJCgkJCQkJPC9kYXRhPgoJCQkJPC9kaWN0PgoJCQkJPGRpY3Q+CgkJCQkJPGtleT5pZDwva2V5PgoJCQkJCTxzdHJpbmc+PC9zdHJpbmc+CgkJCQkJPGtleT50aXRsZTwva2V5PgoJCQkJCTxzdHJpbmc+VGVzdDI8L3N0cmluZz4KCQkJCQk8a2V5PnVybDwva2V5PgoJCQkJCTxzdHJpbmc+aHR0cDovL3dhcC5zaW5hLmNvbTwvc3RyaW5nPgoJCQkJCTxrZXk+bmVlZFVwZGF0ZTwva2V5PgoJCQkJCTxpbnRlZ2VyPjE8L2ludGVnZXI+CgkJCQkJPGtleT5ibG9iPC9rZXk+CgkJCQkJPGRhdGE+CgkJCQkJCQoJCQkJCTwvZGF0YT4KCQkJCTwvZGljdD4KCQkJCTxkaWN0PgoJCQkJCTxrZXk+aWQ8L2tleT4KCQkJCQk8c3RyaW5nPjwvc3RyaW5nPgoJCQkJCTxrZXk+dGl0bGU8L2tleT4KCQkJCQk8c3RyaW5nPua1i+ivlTwvc3RyaW5nPgoJCQkJCTxrZXk+dXJsPC9rZXk+CgkJCQkJPHN0cmluZz5odHRwOi8vbS5zaW5hLmNvbTwvc3RyaW5nPgoJCQkJCTxrZXk+bmVlZFVwZGF0ZTwva2V5PgoJCQkJCTxpbnRlZ2VyPjE8L2ludGVnZXI+CgkJCQkJPGtleT5ibG9iPC9rZXk+CgkJCQkJPGRhdGE+CgkJCQkJCQoJCQkJCTwvZGF0YT4KCQkJCTwvZGljdD4KCQkJPC9hcnJheT4KCQkJPGtleT53aGl0ZUxpc3Q8L2tleT4KCQkJPGFycmF5PgoJCQkJPGRpY3Q+CgkJCQkJPGtleT51cmw8L2tleT4KCQkJCQk8c3RyaW5nPnNpbmEuY29tPC9zdHJpbmc+CgkJCQkJPGtleT5tYXRjaFR5cGU8L2tleT4KCQkJCQk8aW50ZWdlcj4wPC9pbnRlZ2VyPgoJCQkJPC9kaWN0PgoJCQkJPGRpY3Q+CgkJCQkJPGtleT51cmw8L2tleT4KCQkJCQk8c3RyaW5nPmJhaWR1LmNvbTwvc3RyaW5nPgoJCQkJCTxrZXk+bWF0Y2hUeXBlPC9rZXk+CgkJCQkJPGludGVnZXI+MzwvaW50ZWdlcj4KCQkJCTwvZGljdD4KCQkJCTxkaWN0PgoJCQkJCTxrZXk+aWQ8L2tleT4KCQkJCQk8c3RyaW5nPjI0PC9zdHJpbmc+CgkJCQkJPGtleT51cmw8L2tleT4KCQkJCQk8c3RyaW5nPmJhaWR1LmNvbTwvc3RyaW5nPgoJCQkJCTxrZXk+bWF0Y2hUeXBlPC9rZXk+CgkJCQkJPGludGVnZXI+MzwvaW50ZWdlcj4KCQkJCTwvZGljdD4KCQkJPC9hcnJheT4KCQkJPGtleT5oaXN0b3J5V2F0Y2hJdGVtczwva2V5PgoJCQk8YXJyYXk+CgkJCQk8ZGljdD4KCQkJCQk8a2V5PmNvdW50PC9rZXk+CgkJCQkJPGludGVnZXI+MDwvaW50ZWdlcj4KCQkJCQk8a2V5PmRhdGVzPC9rZXk+CgkJCQkJPHN0cmluZz48L3N0cmluZz4KCQkJCQk8a2V5PnVybDwva2V5PgoJCQkJCTxzdHJpbmc+YmFpZHU8L3N0cmluZz4KCQkJCQk8a2V5Pm1hdGNoVHlwZTwva2V5PgoJCQkJCTxpbnRlZ2VyPjA8L2ludGVnZXI+CgkJCQk8L2RpY3Q+CgkJCQk8ZGljdD4KCQkJCQk8a2V5PmNvdW50PC9rZXk+CgkJCQkJPGludGVnZXI+MDwvaW50ZWdlcj4KCQkJCQk8a2V5PmRhdGVzPC9rZXk+CgkJCQkJPHN0cmluZz48L3N0cmluZz4KCQkJCQk8a2V5PnVybDwva2V5PgoJCQkJCTxzdHJpbmc+YmFpZHU8L3N0cmluZz4KCQkJCQk8a2V5Pm1hdGNoVHlwZTwva2V5PgoJCQkJCTxpbnRlZ2VyPjE8L2ludGVnZXI+CgkJCQk8L2RpY3Q+CgkJCTwvYXJyYXk+CgkJCTxrZXk+UGF5bG9hZFR5cGU8L2tleT4KCQkJPHN0cmluZz5jb20ucGVrYWxsLnNlYnJvd3Nlci5zZXR0aW5nczwvc3RyaW5nPgoJCQk8a2V5PlBheWxvYWRWZXJzaW9uPC9rZXk+CgkJCTxpbnRlZ2VyPjE8L2ludGVnZXI+CgkJCTxrZXk+UGF5bG9hZElkZW50aWZpZXI8L2tleT4KCQkJPHN0cmluZz5jb20ucGVrYWxsLnNldHRpbmdzLnNlYnJvd3Nlcjwvc3RyaW5nPgoJCQk8a2V5PlBheWxvYWRVVUlEPC9rZXk+CgkJCTxzdHJpbmc+MzgwOEQ3NDItNUQyMS00MDFFLUI4M0MtQUVEMUU5OTAzMzJEPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZERpc3BsYXlOYW1lPC9rZXk+CgkJCTxzdHJpbmc+U2VCcm93c2Vy6YWN572uPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZERlc2NyaXB0aW9uPC9rZXk+CgkJCTxzdHJpbmc+U2VCcm93c2Vy55u45YWz6YWN572uPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZE9yZ2FuaXphdGlvbjwva2V5PgoJCQk8c3RyaW5nPlBla2FsbCBDYXBpdGFsPC9zdHJpbmc+CgkJPC9kaWN0PgoJCTxkaWN0PgoJCQk8a2V5PnBob25lTW9kZWw8L2tleT4KCQkJPHN0cmluZz5IVEMgT05FPC9zdHJpbmc+CgkJCTxrZXk+cmVnaXN0ZXJCYW5rPC9rZXk+CgkJCTxzdHJpbmc+5pyb5Lqs5YiG6KGMPC9zdHJpbmc+CgkJCTxrZXk+cGhvbmVOdW1iZXI8L2tleT4KCQkJPHN0cmluZz44NDYyMjc3NTwvc3RyaW5nPgoJCQk8a2V5PnJlZ2lzdGVyRGF0ZTwva2V5PgoJCQk8c3RyaW5nPjIwMTMtNy0xMDwvc3RyaW5nPgoJCQk8a2V5PmFkbWluPC9rZXk+CgkJCTxzdHJpbmc+5byg5LiJPC9zdHJpbmc+CgkJCTxrZXk+ZGV2aWNlU3RhdGU8L2tleT4KCQkJPHN0cmluZz5hY3RpdmU8L3N0cmluZz4KCQkJPGtleT5pc1JlZ2lzdGVyZWQ8L2tleT4KCQkJPGludGVnZXI+MTwvaW50ZWdlcj4KCQkJPGtleT5hZG1pblBhc3N3b3JkPC9rZXk+CgkJCTxzdHJpbmc+MTIzNDU2PC9zdHJpbmc+CgkJCTxrZXk+YXBrSXRlbXM8L2tleT4KCQkJPGFycmF5PgoJCQkJPGRpY3Q+CgkJCQkJPGtleT5uYW1lPC9rZXk+CgkJCQkJPHN0cmluZz7mtY/op4jlmag8L3N0cmluZz4KCQkJCQk8a2V5PnBhY2thZ2VOYW1lPC9rZXk+CgkJCQkJPHN0cmluZz5jb20uYW5kcm9pZC5icm93c2VyPC9zdHJpbmc+CgkJCQkJPGtleT5jbGFzc05hbWU8L2tleT4KCQkJCQk8c3RyaW5nPmNvbS5hbmRyb2lkLmJyb3dzZXIuQnJvd3NlckFjdGl2aXR5PC9zdHJpbmc+CgkJCQkJPGtleT5zY3JlZW48L2tleT4KCQkJCQk8aW50ZWdlcj4zPC9pbnRlZ2VyPgoJCQkJCTxrZXk+cm93PC9rZXk+CgkJCQkJPGludGVnZXI+MzwvaW50ZWdlcj4KCQkJCQk8a2V5PmNvbHVtbjwva2V5PgoJCQkJCTxpbnRlZ2VyPjM8L2ludGVnZXI+CgkJCQkJPGtleT5pY29uX3VybDwva2V5PgoJCQkJCTxzdHJpbmc+aHR0cDovL3d3dy5wZWthbGwuY29tL2ljb24vMTIzNDwvc3RyaW5nPgoJCQkJPC9kaWN0PgoJCQkJPGRpY3Q+CgkJCQkJPGtleT5uYW1lPC9rZXk+CgkJCQkJPHN0cmluZz5NRE3lrqLmiLfnq688L3N0cmluZz4KCQkJCQk8a2V5PnBhY2thZ2VOYW1lPC9rZXk+CgkJCQkJPHN0cmluZz5jb20ucGVrYWxsLm1kbTwvc3RyaW5nPgoJCQkJCTxrZXk+Y2xhc3NOYW1lPC9rZXk+CgkJCQkJPHN0cmluZz5jb20ucGVrYWxsLm1kbS5NYWluQWN0aXZpdHk8L3N0cmluZz4KCQkJCQk8a2V5PnNjcmVlbjwva2V5PgoJCQkJCTxpbnRlZ2VyPjE8L2ludGVnZXI+CgkJCQkJPGtleT5yb3c8L2tleT4KCQkJCQk8aW50ZWdlcj40PC9pbnRlZ2VyPgoJCQkJCTxrZXk+Y29sdW1uPC9rZXk+CgkJCQkJPGludGVnZXI+MjwvaW50ZWdlcj4KCQkJCQk8a2V5Pmljb25fdXJsPC9rZXk+CgkJCQkJPHN0cmluZz5odHRwOi8vd3d3LnBla2FsbC5jb20vaWNvbi8xMjM0NTwvc3RyaW5nPgoJCQkJPC9kaWN0PgoJCQk8L2FycmF5PgoJCQk8a2V5PndlYkl0ZW1zPC9rZXk+CgkJCTxhcnJheT4KCQkJCTxkaWN0PgoJCQkJCTxrZXk+dGl0bGU8L2tleT4KCQkJCQk8c3RyaW5nPuW7uuihjOS4u+mhtTwvc3RyaW5nPgoJCQkJCTxrZXk+dXJsPC9rZXk+CgkJCQkJPHN0cmluZz5odHRwOi8vd3d3LmNjYi5jb20uY248L3N0cmluZz4KCQkJCQk8a2V5Pmljb25Vcmw8L2tleT4KCQkJCQk8c3RyaW5nPmh0dHA6Ly93d3cucGVrYWxsLmNvbS9pY29uLzEyMzQ1PC9zdHJpbmc+CgkJCQkJPGtleT5zY3JlZW48L2tleT4KCQkJCQk8aW50ZWdlcj4zPC9pbnRlZ2VyPgoJCQkJCTxrZXk+cm93PC9rZXk+CgkJCQkJPGludGVnZXI+MzwvaW50ZWdlcj4KCQkJCQk8a2V5PmNvbHVtbjwva2V5PgoJCQkJCTxpbnRlZ2VyPjM8L2ludGVnZXI+CgkJCQk8L2RpY3Q+CgkJCQk8ZGljdD4KCQkJCQk8a2V5PnRpdGxlPC9rZXk+CgkJCQkJPHN0cmluZz7lu7rooYznkIbotKI8L3N0cmluZz4KCQkJCQk8a2V5PnVybDwva2V5PgoJCQkJCTxzdHJpbmc+aHR0cDovL3d3dy5jY2IuY29tLmNuL2xpY2FpPC9zdHJpbmc+CgkJCQkJPGtleT5pY29uVXJsPC9rZXk+CgkJCQkJPHN0cmluZz5odHRwOi8vd3d3LnBla2FsbC5jb20vaWNvbi8xMjM0NTwvc3RyaW5nPgoJCQkJCTxrZXk+c2NyZWVuPC9rZXk+CgkJCQkJPGludGVnZXI+MTwvaW50ZWdlcj4KCQkJCQk8a2V5PnJvdzwva2V5PgoJCQkJCTxpbnRlZ2VyPjQ8L2ludGVnZXI+CgkJCQkJPGtleT5jb2x1bW48L2tleT4KCQkJCQk8aW50ZWdlcj4yPC9pbnRlZ2VyPgoJCQkJPC9kaWN0PgoJCQk8L2FycmF5PgoJCQk8a2V5PlBheWxvYWRUeXBlPC9rZXk+CgkJCTxzdHJpbmc+Y29tLnBla2FsbC5sYXVuY2hlci5zZXR0aW5nczwvc3RyaW5nPgoJCQk8a2V5PlBheWxvYWRWZXJzaW9uPC9rZXk+CgkJCTxpbnRlZ2VyPjE8L2ludGVnZXI+CgkJCTxrZXk+UGF5bG9hZElkZW50aWZpZXI8L2tleT4KCQkJPHN0cmluZz5jb20ucGVrYWxsLnNldHRpbmdzLmxhdW5jaGVyPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZFVVSUQ8L2tleT4KCQkJPHN0cmluZz4zODA4RDc0Mi01RDIxLTQwMUUtQjgzQy1BRUQxRTk5MDMzMkQ8L3N0cmluZz4KCQkJPGtleT5QYXlsb2FkRGlzcGxheU5hbWU8L2tleT4KCQkJPHN0cmluZz5MYXVuY2hlcumFjee9rjwvc3RyaW5nPgoJCQk8a2V5PlBheWxvYWREZXNjcmlwdGlvbjwva2V5PgoJCQk8c3RyaW5nPkxhdW5jaGVy55u45YWz6YWN572uPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZE9yZ2FuaXphdGlvbjwva2V5PgoJCQk8c3RyaW5nPlBla2FsbCBDYXBpdGFsPC9zdHJpbmc+CgkJPC9kaWN0PgoJCTxkaWN0PgoJCQk8a2V5PmJsdWV0b290aDwva2V5PgoJCQk8aW50ZWdlcj4xPC9pbnRlZ2VyPgoJCQk8a2V5PmNhbWVyYTwva2V5PgoJCQk8aW50ZWdlcj4wPC9pbnRlZ2VyPgoJCQk8a2V5Pm5mYzwva2V5PgoJCQk8aW50ZWdlcj4wPC9pbnRlZ2VyPgoJCQk8a2V5PmFpcnBsYW5lPC9rZXk+CgkJCTxpbnRlZ2VyPjA8L2ludGVnZXI+CgkJCTxrZXk+bW9iaWxlRGF0YTwva2V5PgoJCQk8aW50ZWdlcj4wPC9pbnRlZ2VyPgoJCQk8a2V5PlBheWxvYWRUeXBlPC9rZXk+CgkJCTxzdHJpbmc+Y29tLnBla2FsbC5zeXN0ZW0uc2V0dGluZ3M8L3N0cmluZz4KCQkJPGtleT5QYXlsb2FkVmVyc2lvbjwva2V5PgoJCQk8aW50ZWdlcj4xPC9pbnRlZ2VyPgoJCQk8a2V5PlBheWxvYWRJZGVudGlmaWVyPC9rZXk+CgkJCTxzdHJpbmc+Y29tLnBla2FsbC5zZXR0aW5ncy5zeXN0ZW08L3N0cmluZz4KCQkJPGtleT5QYXlsb2FkVVVJRDwva2V5PgoJCQk8c3RyaW5nPjM4MDhENzQyLTVEMjEtNDAxRS1CODNDLUFFRDFFOTkwMzMyRDwvc3RyaW5nPgoJCQk8a2V5PlBheWxvYWREaXNwbGF5TmFtZTwva2V5PgoJCQk8c3RyaW5nPlN5c3RlbemFjee9rjwvc3RyaW5nPgoJCQk8a2V5PlBheWxvYWREZXNjcmlwdGlvbjwva2V5PgoJCQk8c3RyaW5nPlN5c3RlbeebuOWFs+mFjee9rjwvc3RyaW5nPgoJCQk8a2V5PlBheWxvYWRPcmdhbml6YXRpb248L2tleT4KCQkJPHN0cmluZz5QZWthbGwgQ2FwaXRhbDwvc3RyaW5nPgoJCTwvZGljdD4KCTwvYXJyYXk+Cgk8a2V5PlBheWxvYWRSZW1vdmFsRGlzYWxsb3dlZDwva2V5PgoJPHRydWUvPgoJPGtleT5QYXlsb2FkVHlwZTwva2V5PgoJPHN0cmluZz5Db25maWd1cmF0aW9uPC9zdHJpbmc+Cgk8a2V5PlBheWxvYWRWZXJzaW9uPC9rZXk+Cgk8aW50ZWdlcj4xPC9pbnRlZ2VyPgoJPGtleT5QYXlsb2FkSWRlbnRpZmllcjwva2V5PgoJPHN0cmluZz5jb20ucGVrYWxsLnByb2ZpbGU8L3N0cmluZz4KCTxrZXk+UGF5bG9hZFVVSUQ8L2tleT4KCTxzdHJpbmc+MkVEMTYwRkYtNEI2Qy00N0RELTgxMDUtNzY5MjMxMzY3RDJBPC9zdHJpbmc+Cgk8a2V5PlBheWxvYWREaXNwbGF5TmFtZTwva2V5PgoJPHN0cmluZz5QZWthbGwgTURNIFByb2ZpbGU8L3N0cmluZz4KCTxrZXk+UGF5bG9hZERlc2NyaXB0aW9uPC9rZXk+Cgk8c3RyaW5nPuaPj+i/sOaWh+S7tuaPj+i/sOOAgndqbCDmtYvor5U8L3N0cmluZz4KCTxrZXk+UGF5bG9hZE9yZ2FuaXphdGlvbjwva2V5PgoJPHN0cmluZz5QZWthbGwgQ2FwaXRhbDwvc3RyaW5nPgo8L2RpY3Q+CjwvcGxpc3Q+\n" +
            "\t</data>\n" +
            "\t<key>name</key>\n" +
            "\t<string>test settings</string>\n" +
            "\t<key>osType</key>\n" +
            "\t<integer>0</integer>\n" +
            "\t<key>description</key>\n" +
            "\t<string>test upload settings</string>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>2ade3a08-24fc-444d-b245-5a4ba8d0a3d1</string>\n" +
            "</dict>\n" +
            "</plist>";

}
