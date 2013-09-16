package com.pekall.plist.su.settings;

import com.pekall.plist.ObjectComparator;
import com.pekall.plist.PayloadXmlMsgParser;
import com.pekall.plist.PlistDebug;
import com.pekall.plist.beans.BeanBase;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.su.settings.advertise.AdvertiseDownloadSettings;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class AdvertiseDownloadSettingsTest extends TestCase {

    public void testGenAppXml() throws Exception {
        PayloadBase settings = getSettings();
        String xml = settings.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_APP_XML);
    }

    public void testParseAppXml() throws Exception {
        AdvertiseDownloadSettings settings = AdvertiseDownloadSettings.fromXmlT(TEST_APP_XML, AdvertiseDownloadSettings.class);

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
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_ADVT_SETTINGS),
                getSettings());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_ADVT_SETTINGS), getSettings()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    public void testParseSuAdvXml() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(ADV_TWO_XML);

        ArrayList<PayloadBase> settings = (ArrayList<PayloadBase>)
                parser.getPayloads(PayloadBase.PAYLOAD_TYPE_ADVT_SETTINGS);
        assertTrue(settings != null);
        assertEquals(settings.size(), 2);
        assertTrue(settings.get(0) instanceof AdvertiseDownloadSettings);
        assertTrue(settings.get(1) instanceof AdvertiseDownloadSettings);

        AdvertiseDownloadSettings s0 = (AdvertiseDownloadSettings) settings.get(0);
        assertEquals(s0.getDownloadUrl(), "http://221.122.32.42:8098/Zip/130803_180440_5314/130803_180508_6600.zip");
        assertEquals(s0.getPlayType(), AdvertiseDownloadSettings.PLAY_TYPE_AUTO);

        AdvertiseDownloadSettings s1 = (AdvertiseDownloadSettings) settings.get(1);
        assertEquals(s1.getDownloadUrl(), "http://221.122.32.42:8098/Zip/130803_180440_5314/130803_180508_6659.zip");
        assertEquals(s1.getPlayType(), AdvertiseDownloadSettings.PLAY_TYPE_MANUAL);
    }

    private PayloadBase getSettings() {
        AdvertiseDownloadSettings settings = new AdvertiseDownloadSettings();
        settings.setDownloadUrl("http://www.pekall.com/adv.zip");
        settings.setVersion("25");
        settings.setPlayType(AdvertiseDownloadSettings.PLAY_TYPE_AUTO);

        settings.setPayloadDescription("广告下载相关配置");
        settings.setPayloadDisplayName("广告下载配置");
        settings.setPayloadIdentifier("com.pekall.settings.advertise.download");
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

    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>PayloadContent</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>version</key>\n" +
            "\t\t\t<string>25</string>\n" +
            "\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t<string>http://www.pekall.com/adv.zip</string>\n" +
            "\t\t\t<key>playType</key>\n" +
            "\t\t\t<string>auto</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.pekall.advertise.settings</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.settings.advertise.download</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>广告下载配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>广告下载相关配置</string>\n" +
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
            "\t<key>version</key>\n" +
            "\t<string>25</string>\n" +
            "\t<key>downloadUrl</key>\n" +
            "\t<string>http://www.pekall.com/adv.zip</string>\n" +
            "\t<key>playType</key>\n" +
            "\t<string>auto</string>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>com.pekall.advertise.settings</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.settings.advertise.download</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>广告下载配置</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>广告下载相关配置</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";

    private final static String ADV_TWO_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "<key>PayloadContent</key>\n" +
            "<array>\n" +
            "<dict>\n" +
            "<key>SSID_STR</key>\n" +
            "<string>pekall_work</string>\n" +
            "<key>HIDDEN_NETWORK</key>\n" +
            "<false/>\n" +
            "<key>AutoJoin</key>\n" +
            "<true/>\n" +
            "<key>EncryptionType</key>\n" +
            "<string>WPA</string>\n" +
            "<key>Password</key>\n" +
            "<string>pekallcloud</string>\n" +
            "<key>PayloadType</key>\n" +
            "<string>com.apple.wifi.managed</string>\n" +
            "<key>PayloadVersion</key>\n" +
            "<integer>1</integer>\n" +
            "<key>PayloadIdentifier</key>\n" +
            "<string>com.pekall.config.wifi.managed</string>\n" +
            "<key>PayloadUUID</key>\n" +
            "<string>e5bbeb20-710f-435c-9f2d-41e1b19874ab</string>\n" +
            "<key>PayloadDisplayName</key>\n" +
            "<string>WIFI配置</string>\n" +
            "<key>PayloadDescription</key>\n" +
            "<string>WIFI相关配置</string>\n" +
            "<key>PayloadOrganization</key>\n" +
            "<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>version</key>\n" +
            "<string>1.0</string>\n" +
            "<key>downloadUrl</key>\n" +
            "<string>http://221.122.32.42:8098/Zip/130803_180440_5314/130803_180508_6600.zip</string>\n" +
            "<key>playType</key>\n" +
            "<string>auto</string>\n" +
            "<key>PayloadType</key>\n" +
            "<string>com.pekall.advertise.settings</string>\n" +
            "<key>PayloadVersion</key>\n" +
            "<integer>1</integer>\n" +
            "<key>PayloadIdentifier</key>\n" +
            "<string>com.pekall.settings.advertise.download</string>\n" +
            "<key>PayloadUUID</key>\n" +
            "<string>1a923216-0dca-4d83-95b6-f162b2f11586</string>\n" +
            "<key>PayloadDisplayName</key>\n" +
            "<string>广告下载配置</string>\n" +
            "<key>PayloadDescription</key>\n" +
            "<string>广告下载相关配置</string>\n" +
            "<key>PayloadOrganization</key>\n" +
            "<string>Pekall Captital</string>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>version</key>\n" +
            "<string>1.0</string>\n" +
            "<key>downloadUrl</key>\n" +
            "<string>http://221.122.32.42:8098/Zip/130803_180440_5314/130803_180508_6659.zip</string>\n" +
            "<key>playType</key>\n" +
            "<string>manual</string>\n" +
            "<key>PayloadType</key>\n" +
            "<string>com.pekall.advertise.settings</string>\n" +
            "<key>PayloadVersion</key>\n" +
            "<integer>1</integer>\n" +
            "<key>PayloadIdentifier</key>\n" +
            "<string>com.pekall.settings.advertise.download</string>\n" +
            "<key>PayloadUUID</key>\n" +
            "<string>33ca7c3c-2c53-4375-a5b6-2df6111e79a4</string>\n" +
            "<key>PayloadDisplayName</key>\n" +
            "<string>广告下载配置</string>\n" +
            "<key>PayloadDescription</key>\n" +
            "<string>广告下载相关配置</string>\n" +
            "<key>PayloadOrganization</key>\n" +
            "<string>Pekall Captital</string>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>addressBarDisplay</key>\n" +
            "<integer>1</integer>\n" +
            "<key>homePageUrl</key>\n" +
            "<string>http://www.ccb.com</string>\n" +
            "<key>quickLaunchItems</key>\n" +
            "<array>\n" +
            "<dict>\n" +
            "<key>id</key>\n" +
            "<string>1</string>\n" +
            "<key>title</key>\n" +
            "<string>建行主页</string>\n" +
            "<key>url</key>\n" +
            "<string>http://www.ccb.com</string>\n" +
            "<key>needUpdate</key>\n" +
            "<integer>1</integer>\n" +
            "<key>blob</key>\n" +
            "<data>\n" +
            "\n" +
            "</data>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>id</key>\n" +
            "<string>2</string>\n" +
            "<key>title</key>\n" +
            "<string>个人网上银行</string>\n" +
            "<key>url</key>\n" +
            "<string>https://ibsbjstar.ccb.com.cn/app/V5/CN/STY1/login.jsp</string>\n" +
            "<key>needUpdate</key>\n" +
            "<integer>1</integer>\n" +
            "<key>blob</key>\n" +
            "<data>\n" +
            "\n" +
            "</data>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>id</key>\n" +
            "<string>3</string>\n" +
            "<key>title</key>\n" +
            "<string>企业电子银行</string>\n" +
            "<key>url</key>\n" +
            "<string>http://ebank.ccb.com/cn/ebank/homepage_corporate.html</string>\n" +
            "<key>needUpdate</key>\n" +
            "<integer>1</integer>\n" +
            "<key>blob</key>\n" +
            "<data>\n" +
            "\n" +
            "</data>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>id</key>\n" +
            "<string>4</string>\n" +
            "<key>title</key>\n" +
            "<string>私人银行上网银行</string>\n" +
            "<key>url</key>\n" +
            "<string>https://ibsbjstar.ccb.com.cn/app/V5/CN/STY6/login_pbc.jsp</string>\n" +
            "<key>needUpdate</key>\n" +
            "<integer>1</integer>\n" +
            "<key>blob</key>\n" +
            "<data>\n" +
            "\n" +
            "</data>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>id</key>\n" +
            "<string>5</string>\n" +
            "<key>title</key>\n" +
            "<string>小型微企</string>\n" +
            "<key>url</key>\n" +
            "<string>http://ccb.com/cn/home/s_company_index.html</string>\n" +
            "<key>needUpdate</key>\n" +
            "<integer>1</integer>\n" +
            "<key>blob</key>\n" +
            "<data>\n" +
            "\n" +
            "</data>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>id</key>\n" +
            "<string>6</string>\n" +
            "<key>title</key>\n" +
            "<string>善融商城</string>\n" +
            "<key>url</key>\n" +
            "<string>http://e.ccb.com/cn/home/ecp_index.html</string>\n" +
            "<key>needUpdate</key>\n" +
            "<integer>1</integer>\n" +
            "<key>blob</key>\n" +
            "<data>\n" +
            "\n" +
            "</data>\n" +
            "</dict>\n" +
            "</array>\n" +
            "<key>whiteList</key>\n" +
            "<array>\n" +
            "<dict>\n" +
            "<key>url</key>\n" +
            "<string>sina.com</string>\n" +
            "<key>matchType</key>\n" +
            "<integer>0</integer>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>url</key>\n" +
            "<string>baidu.com</string>\n" +
            "<key>matchType</key>\n" +
            "<integer>3</integer>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>id</key>\n" +
            "<string>24</string>\n" +
            "<key>url</key>\n" +
            "<string>ccb.com</string>\n" +
            "<key>matchType</key>\n" +
            "<integer>3</integer>\n" +
            "</dict>\n" +
            "</array>\n" +
            "<key>historyWatchItems</key>\n" +
            "<array>\n" +
            "<dict>\n" +
            "<key>count</key>\n" +
            "<integer>0</integer>\n" +
            "<key>dates</key>\n" +
            "<string></string>\n" +
            "<key>url</key>\n" +
            "<string>baidu.com</string>\n" +
            "<key>matchType</key>\n" +
            "<integer>0</integer>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>count</key>\n" +
            "<integer>0</integer>\n" +
            "<key>dates</key>\n" +
            "<string></string>\n" +
            "<key>url</key>\n" +
            "<string>ccb.com</string>\n" +
            "<key>matchType</key>\n" +
            "<integer>1</integer>\n" +
            "</dict>\n" +
            "</array>\n" +
            "<key>PayloadType</key>\n" +
            "<string>com.pekall.sebrowser.settings</string>\n" +
            "<key>PayloadVersion</key>\n" +
            "<integer>1</integer>\n" +
            "<key>PayloadIdentifier</key>\n" +
            "<string>com.pekall.settings.sebrowser</string>\n" +
            "<key>PayloadUUID</key>\n" +
            "<string>ee1273fa-2228-4701-876b-adf93056ed0d</string>\n" +
            "<key>PayloadDisplayName</key>\n" +
            "<string>SeBrowser配置</string>\n" +
            "<key>PayloadDescription</key>\n" +
            "<string>SeBrowser相关配置</string>\n" +
            "<key>PayloadOrganization</key>\n" +
            "<string>Pekall Captital</string>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>phoneModel</key>\n" +
            "<string>HTC ONE</string>\n" +
            "<key>registerBank</key>\n" +
            "<string>建设银行</string>\n" +
            "<key>phoneNumber</key>\n" +
            "<string>13811255530</string>\n" +
            "<key>registerDate</key>\n" +
            "<string>2013-7-10</string>\n" +
            "<key>admin</key>\n" +
            "<string>思创</string>\n" +
            "<key>deviceState</key>\n" +
            "<string>active</string>\n" +
            "<key>isRegistered</key>\n" +
            "<integer>1</integer>\n" +
            "<key>adminPassword</key>\n" +
            "<string>123456</string>\n" +
            "<key>wallpaper</key>\n" +
            "<string>wallpaper_sky.jpg</string>\n" +
            "<key>apkItems</key>\n" +
            "<array>\n" +
            "<dict>\n" +
            "<key>name</key>\n" +
            "<string>建行客户端</string>\n" +
            "<key>packageName</key>\n" +
            "<string>com.chinamworld.main</string>\n" +
            "<key>className</key>\n" +
            "<string>com.chinamworld.main.BTCCMWStartActivity</string>\n" +
            "<key>screen</key>\n" +
            "<integer>1</integer>\n" +
            "<key>row</key>\n" +
            "<integer>2</integer>\n" +
            "<key>column</key>\n" +
            "<integer>1</integer>\n" +
            "<key>icon_url</key>\n" +
            "<string>http://www.pekall.com/icon/1234</string>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>name</key>\n" +
            "<string>广告</string>\n" +
            "<key>packageName</key>\n" +
            "<string>com.pekall.advert</string>\n" +
            "<key>className</key>\n" +
            "<string>com.pekall.advert.MainAct</string>\n" +
            "<key>screen</key>\n" +
            "<integer>1</integer>\n" +
            "<key>row</key>\n" +
            "<integer>3</integer>\n" +
            "<key>column</key>\n" +
            "<integer>1</integer>\n" +
            "<key>icon_url</key>\n" +
            "<string>http://www.pekall.com/icon/12345</string>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>name</key>\n" +
            "<string>意见薄</string>\n" +
            "<key>packageName</key>\n" +
            "<string>com.pekall.proposal</string>\n" +
            "<key>className</key>\n" +
            "<string>com.pekall.proposal.ProposalActivity</string>\n" +
            "<key>screen</key>\n" +
            "<integer>1</integer>\n" +
            "<key>row</key>\n" +
            "<integer>4</integer>\n" +
            "<key>column</key>\n" +
            "<integer>1</integer>\n" +
            "<key>icon_url</key>\n" +
            "<string>http://www.pekall.com/icon/12345</string>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>name</key>\n" +
            "<string>历史消息</string>\n" +
            "<key>packageName</key>\n" +
            "<string>com.pekall.launcher</string>\n" +
            "<key>className</key>\n" +
            "<string>com.pekall.launcher2.ui.HistoryMessageActivity</string>\n" +
            "<key>screen</key>\n" +
            "<integer>1</integer>\n" +
            "<key>row</key>\n" +
            "<integer>5</integer>\n" +
            "<key>column</key>\n" +
            "<integer>1</integer>\n" +
            "<key>icon_url</key>\n" +
            "<string>http://www.pekall.com/icon/12345</string>\n" +
            "</dict>\n" +
            "</array>\n" +
            "<key>webItems</key>\n" +
            "<array>\n" +
            "<dict>\n" +
            "<key>title</key>\n" +
            "<string>建行主页</string>\n" +
            "<key>url</key>\n" +
            "<string>http://www.ccb.com</string>\n" +
            "<key>iconUrl</key>\n" +
            "<string>http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=de6e5f8b63bc46ea32d7f46c0c33</string>\n" +
            "<key>screen</key>\n" +
            "<integer>0</integer>\n" +
            "<key>row</key>\n" +
            "<integer>1</integer>\n" +
            "<key>column</key>\n" +
            "<integer>1</integer>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>title</key>\n" +
            "<string>个人网银</string>\n" +
            "<key>url</key>\n" +
            "<string>https://ibsbjstar.ccb.com.cn/app/V5/CN/STY1/login.jsp</string>\n" +
            "<key>iconUrl</key>\n" +
            "<string>http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=8a81ac2ae7534d64b2c7e166e69c</string>\n" +
            "<key>screen</key>\n" +
            "<integer>0</integer>\n" +
            "<key>row</key>\n" +
            "<integer>2</integer>\n" +
            "<key>column</key>\n" +
            "<integer>1</integer>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>title</key>\n" +
            "<string>私人网银</string>\n" +
            "<key>url</key>\n" +
            "<string>https://ibsbjstar.ccb.com.cn/app/V5/CN/STY6/login_pbc.jsp</string>\n" +
            "<key>iconUrl</key>\n" +
            "<string>http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=e431cf4fb8334374eb1919fd51f9</string>\n" +
            "<key>screen</key>\n" +
            "<integer>0</integer>\n" +
            "<key>row</key>\n" +
            "<integer>3</integer>\n" +
            "<key>column</key>\n" +
            "<integer>1</integer>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>title</key>\n" +
            "<string>企业网银</string>\n" +
            "<key>url</key>\n" +
            "<string>http://ebank.ccb.com/cn/ebank/homepage_corporate.html</string>\n" +
            "<key>iconUrl</key>\n" +
            "<string>http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=ab8d412191d64c1d891e7f55676f</string>\n" +
            "<key>screen</key>\n" +
            "<integer>0</integer>\n" +
            "<key>row</key>\n" +
            "<integer>4</integer>\n" +
            "<key>column</key>\n" +
            "<integer>1</integer>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>title</key>\n" +
            "<string>小微网银</string>\n" +
            "<key>url</key>\n" +
            "<string>http://ccb.com/cn/home/s_company_index.html</string>\n" +
            "<key>iconUrl</key>\n" +
            "<string>http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=293e4ac13cc74a08b13304ed06f1</string>\n" +
            "<key>screen</key>\n" +
            "<integer>0</integer>\n" +
            "<key>row</key>\n" +
            "<integer>5</integer>\n" +
            "<key>column</key>\n" +
            "<integer>1</integer>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>title</key>\n" +
            "<string>善融商城</string>\n" +
            "<key>url</key>\n" +
            "<string>http://e.ccb.com/cn/home/ecp_index.html</string>\n" +
            "<key>iconUrl</key>\n" +
            "<string>http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=5c318e33a3e340b40bcbfe20816f</string>\n" +
            "<key>screen</key>\n" +
            "<integer>0</integer>\n" +
            "<key>row</key>\n" +
            "<integer>6</integer>\n" +
            "<key>column</key>\n" +
            "<integer>1</integer>\n" +
            "</dict>\n" +
            "</array>\n" +
            "<key>PayloadType</key>\n" +
            "<string>com.pekall.launcher.settings</string>\n" +
            "<key>PayloadVersion</key>\n" +
            "<integer>1</integer>\n" +
            "<key>PayloadIdentifier</key>\n" +
            "<string>com.pekall.settings.launcher</string>\n" +
            "<key>PayloadUUID</key>\n" +
            "<string>b0a9cd5d-eed2-40d1-a0e9-eec31d5b0b4b</string>\n" +
            "<key>PayloadDisplayName</key>\n" +
            "<string>Launcher配置</string>\n" +
            "<key>PayloadDescription</key>\n" +
            "<string>Launcher相关配置</string>\n" +
            "<key>PayloadOrganization</key>\n" +
            "<string>Pekall Captital</string>\n" +
            "</dict>\n" +
            "<dict>\n" +
            "<key>wifi</key>\n" +
            "<integer>1</integer>\n" +
            "<key>bluetooth</key>\n" +
            "<integer>0</integer>\n" +
            "<key>camera</key>\n" +
            "<integer>1</integer>\n" +
            "<key>microphone</key>\n" +
            "<integer>1</integer>\n" +
            "<key>nfc</key>\n" +
            "<integer>0</integer>\n" +
            "<key>dataRoaming</key>\n" +
            "<integer>1</integer>\n" +
            "<key>sdcard</key>\n" +
            "<integer>1</integer>\n" +
            "<key>usb</key>\n" +
            "<integer>1</integer>\n" +
            "<key>airplane</key>\n" +
            "<integer>0</integer>\n" +
            "<key>mobileData</key>\n" +
            "<integer>1</integer>\n" +
            "<key>PayloadType</key>\n" +
            "<string>com.pekall.system.settings</string>\n" +
            "<key>PayloadVersion</key>\n" +
            "<integer>1</integer>\n" +
            "<key>PayloadIdentifier</key>\n" +
            "<string>com.pekall.settings.system</string>\n" +
            "<key>PayloadUUID</key>\n" +
            "<string>9c1fd65c-be5d-43dc-be47-b8aa0d43f1eb</string>\n" +
            "<key>PayloadDisplayName</key>\n" +
            "<string>System配置</string>\n" +
            "<key>PayloadDescription</key>\n" +
            "<string>System相关配置</string>\n" +
            "<key>PayloadOrganization</key>\n" +
            "<string>Pekall Captital</string>\n" +
            "</dict>\n" +
            "</array>\n" +
            "<key>PayloadRemovalDisallowed</key>\n" +
            "<true/>\n" +
            "<key>PayloadType</key>\n" +
            "<string>Configuration</string>\n" +
            "<key>PayloadVersion</key>\n" +
            "<integer>1</integer>\n" +
            "<key>PayloadIdentifier</key>\n" +
            "<string>com.pekall.profile.setting</string>\n" +
            "<key>PayloadUUID</key>\n" +
            "<string>9119396f-da11-4168-a806-bbb8317fe556</string>\n" +
            "<key>PayloadDisplayName</key>\n" +
            "<string>Pekall Default Setting</string>\n" +
            "<key>PayloadDescription</key>\n" +
            "<string>配置文件</string>\n" +
            "<key>PayloadOrganization</key>\n" +
            "<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";
}
