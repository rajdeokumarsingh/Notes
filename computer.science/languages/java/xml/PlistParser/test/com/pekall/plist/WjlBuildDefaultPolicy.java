package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.google.gson.Gson;
import com.pekall.plist.beans.*;
import com.pekall.plist.json.PayloadJsonWrapper;
import com.pekall.plist.su.policy.*;
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
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: wjl
 * Date: 13-8-15
 * Time: 上午11:13
 */
public class WjlBuildDefaultPolicy extends TestCase {


    public static final String CONTENT =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>PayloadContent</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>SSID_STR</key>\n" +
            "\t\t\t<string>test ssid</string>\n" +
            "\t\t\t<key>HIDDEN_NETWORK</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>AutoJoin</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>EncryptionType</key>\n" +
            "\t\t\t<string>WEP</string>\n" +
            "\t\t\t<key>Password</key>\n" +
            "\t\t\t<string>123456</string>\n" +
            "\t\t\t<key>ProxyType</key>\n" +
            "\t\t\t<string>Manual</string>\n" +
            "\t\t\t<key>ProxyServer</key>\n" +
            "\t\t\t<string>192.168.10.210</string>\n" +
            "\t\t\t<key>ProxyServerPort</key>\n" +
            "\t\t\t<integer>80</integer>\n" +
            "\t\t\t<key>ProxyUsername</key>\n" +
            "\t\t\t<string>jiangrui</string>\n" +
            "\t\t\t<key>ProxyPassword</key>\n" +
            "\t\t\t<string>123456</string>\n" +
            "\t\t\t<key>ProxyPACURL</key>\n" +
            "\t\t\t<string>1.2.3.4</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.wifi.managed</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.config.wifi.managed</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>WIFI配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>WIFI相关配置</string>\n" +
            "\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t<string>Pekall Capital</string>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>SSID_STR</key>\n" +
            "\t\t\t<string>test ssid</string>\n" +
            "\t\t\t<key>HIDDEN_NETWORK</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>AutoJoin</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>EncryptionType</key>\n" +
            "\t\t\t<string>WEP</string>\n" +
            "\t\t\t<key>Password</key>\n" +
            "\t\t\t<string>123456</string>\n" +
            "\t\t\t<key>ProxyType</key>\n" +
            "\t\t\t<string>Manual</string>\n" +
            "\t\t\t<key>ProxyServer</key>\n" +
            "\t\t\t<string>192.168.10.210</string>\n" +
            "\t\t\t<key>ProxyServerPort</key>\n" +
            "\t\t\t<integer>80</integer>\n" +
            "\t\t\t<key>ProxyUsername</key>\n" +
            "\t\t\t<string>jiangrui</string>\n" +
            "\t\t\t<key>ProxyPassword</key>\n" +
            "\t\t\t<string>123456</string>\n" +
            "\t\t\t<key>ProxyPACURL</key>\n" +
            "\t\t\t<string>1.2.3.4</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.wifi.managed</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.config.wifi.managed</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>WIFI配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>WIFI相关配置</string>\n" +
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


    public static final String JSON_CONTENT = "{\n" +
            "    \"PayloadContent\": {\n" +
            "        \"payloadWifiConfig\": {\n" +
            "            \"SSID_STR\": \"pekall_work\",\n" +
            "            \"HIDDEN_NETWORK\": false,\n" +
            "            \"AutoJoin\": true,\n" +
            "            \"EncryptionType\": \"WPA\",\n" +
            "            \"Password\": \"pekallcloud\",\n" +
            "            \"PayloadType\": \"com.apple.wifi.managed\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.config.wifi.managed\",\n" +
            "            \"PayloadUUID\": \"518d778c-ebd4-4796-b864-1db162bfbb83\",\n" +
            "            \"PayloadDisplayName\": \"WIFI配置\",\n" +
            "            \"PayloadDescription\": \"WIFI相关配置\",\n" +
            "            \"PayloadOrganization\": \"Pekall Capital\"\n" +
            "        },\n" +
            "        \"payloadEmail\": {\n" +
            "            \"EmailAccountDescription\": \"test email account\",\n" +
            "            \"EmailAccountType\": \"EmailTypeIMAP\",\n" +
            "            \"EmailAddress\": \"test_mdm@pekall.com\",\n" +
            "            \"IncomingMailServerAuthentication\": \"EmailAuthPassword\",\n" +
            "            \"IncomingMailServerHostName\": \"mail.pekall.com\",\n" +
            "            \"IncomingMailServerPortNumber\": 993,\n" +
            "            \"IncomingMailServerUseSSL\": true,\n" +
            "            \"IncomingMailServerUsername\": \"test_mdm\",\n" +
            "            \"IncomingPassword\": \"123456\",\n" +
            "            \"OutgoingPasswordSameAsIncomingPassword\": true,\n" +
            "            \"OutgoingMailServerAuthentication\": \"EmailAuthPassword\",\n" +
            "            \"OutgoingMailServerHostName\": \"mail.pekall.com\",\n" +
            "            \"OutgoingMailServerPortNumber\": 587,\n" +
            "            \"OutgoingMailServerUseSSL\": true,\n" +
            "            \"OutgoingMailServerUsername\": \"test_mdm\",\n" +
            "            \"PreventMove\": false,\n" +
            "            \"PreventAppSheet\": true,\n" +
            "            \"SMIMEEnabled\": false,\n" +
            "            \"disableMailRecentsSyncing\": true,\n" +
            "            \"PayloadType\": \"com.apple.mail.managed\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.profile.email\",\n" +
            "            \"PayloadUUID\": \"85b64c9d-29a7-4782-85e6-9783f2ed7d88\",\n" +
            "            \"PayloadDisplayName\": \"Email配置\",\n" +
            "            \"PayloadDescription\": \"Email相关配置\",\n" +
            "            \"PayloadOrganization\": \"Pekall Captital\"\n" +
            "        },\n" +
            "        \"payloadVPN\": {\n" +
            "            \"UserDefinedName\": \"test vpn\",\n" +
            "            \"VPNType\": \"PPTP\",\n" +
            "            \"serverHostName\": \"192.168.10.220\",\n" +
            "            \"account\": \"ray\",\n" +
            "            \"userAuth\": \"password\",\n" +
            "            \"password\": \"123456\",\n" +
            "            \"sharedPassword\": \"qwert\",\n" +
            "            \"vpnForAllTraffic\": true,\n" +
            "            \"proxyHost\": \"192.168.0.1\",\n" +
            "            \"proxyPort\": 889,\n" +
            "            \"proxyUserName\": \"Ray\",\n" +
            "            \"proxyPassword\": \"qwert\",\n" +
            "            \"PayloadType\": \"com.apple.vpn.managed\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.profile.VPN\",\n" +
            "            \"PayloadUUID\": \"a7e55234-1961-4964-82b7-80733c20be4b\",\n" +
            "            \"PayloadDisplayName\": \"VPN配置\",\n" +
            "            \"PayloadDescription\": \"VPN相关配置\",\n" +
            "            \"PayloadOrganization\": \"Pekall Captital\"\n" +
            "        },\n" +
            "        \"payloadWallpaper\": {\n" +
            "            \"configWallpaper\": true,\n" +
            "            \"lowResolution\": \"320x240\",\n" +
            "            \"allowUsrChangeWallpaper\": false,\n" +
            "            \"PayloadType\": \"com.pekall.wallpaper.settings\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.settings.wallpaper\",\n" +
            "            \"PayloadUUID\": \"3fc2b26b-6e49-401c-8bb6-6cefe3815292\",\n" +
            "            \"PayloadDisplayName\": \"Wallpaper配置\",\n" +
            "            \"PayloadDescription\": \"Wallpaper相关配置\",\n" +
            "            \"PayloadOrganization\": \"Pekall Captital\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"PayloadType\": \"Configuration\",\n" +
            "    \"PayloadVersion\": 1,\n" +
            "    \"PayloadIdentifier\": \"com.pekall.profile.setting\",\n" +
            "    \"PayloadUUID\": \"4ec834c6-f02e-4625-9ab5-844d8b99a7bd\",\n" +
            "    \"PayloadDisplayName\": \"Pekall Default Setting\",\n" +
            "    \"PayloadDescription\": \"配置文件\",\n" +
            "    \"PayloadOrganization\": \"Pekall Capital\"\n" +
            "}";

    public void testXMl2Json(){
        System.out.println(xml2Json(CONTENT));
    }

    public void testJson2Xml(){
        System.out.println(json2Xml(JSON_CONTENT));
    }

    public String json2Xml(String json) {
        Gson gson = new Gson();
        PayloadJsonWrapper wrapper = null;

        String xml = null;
        try {


            wrapper = gson.fromJson(json, PayloadJsonWrapper.class);
            if (wrapper != null) {
                xml = wrapper.getPayloadArrayWrapper().toXml();
            }
        } catch (Exception e) {
            System.out.println("json 解析异常" + e);
        }
        return xml;
    }


    public String xml2Json(String xml) {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(xml);
        PayloadArrayWrapper wrapper = (PayloadArrayWrapper) parser.getPayloadDescriptor();
        if(wrapper == null){
            return null;
        }

        PayloadJsonWrapper jsonWrapper = new PayloadJsonWrapper(wrapper);
        Gson gson = new Gson();
        return gson.toJson(jsonWrapper);
    }


    public void testGenIosPolicyXml() throws Exception {
        PayloadArrayWrapper profile = createIosPolicyProfile();

        System.out.println(xml2Json(profile.toXml()));

        System.out.println("----------------------");
        NSDictionary root = PlistBeanConverter.createNdictFromBean(profile);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
//        assertEquals(xml, TEST_XML);
    }

    public void testGenAndroidPolicyXml() throws Exception {
        PayloadArrayWrapper profile = createAndroidPolicyProfile();


//        BeanBase.fromXmlT()
        System.out.println(xml2Json(profile.toXml()));

        System.out.println("----------------------");
        NSDictionary root = PlistBeanConverter.createNdictFromBean(profile);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
//        assertEquals(xml, TEST_XML);
    }

    public void testGenIosSettingXml() throws Exception {
        PayloadArrayWrapper profile = createIosSettingProfile();

        System.out.println(xml2Json(profile.toXml()));


        System.out.println("----------------------");

        NSDictionary root = PlistBeanConverter.createNdictFromBean(profile);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
//        assertEquals(xml, TEST_XML);
    }

    public void testGenAndroidSettingXml() throws Exception {
        PayloadArrayWrapper profile = createAndroidSettingProfile();

        System.out.println(xml2Json(profile.toXml()));

        System.out.println("----------------------");

        NSDictionary root = PlistBeanConverter.createNdictFromBean(profile);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
//        assertEquals(xml, TEST_XML);
    }

    private PayloadEmail createEmail() {
        PayloadEmail email = new PayloadEmail();
        email.setPayloadDescription("Email相关配置");
        email.setPayloadDisplayName("Email配置");
        email.setPayloadIdentifier("com.pekall.profile.email");
        email.setPayloadOrganization(ORGANIZATION);
        email.setPayloadUUID(getUUID());
        email.setPayloadVersion(VERSION);

        email.setDisableMailRecentsSyncing(true);
        email.setEmailAccountDescription("test email account");
        email.setEmailAccountType(PayloadEmail.EMAIL_TYPE_IMAP);
        email.setEmailAddress("test_mdm@pekall.com");

        email.setIncomingMailServerAuthentication(PayloadEmail.EMAIL_AUTH_PASSWORD);
        email.setIncomingMailServerPortNumber(993);
        email.setIncomingMailServerUseSSL(true);
        email.setIncomingPassword("123456");
        email.setIncomingMailServerHostName("mail.pekall.com");
        email.setIncomingMailServerUsername("test_mdm");

        email.setOutgoingMailServerAuthentication(PayloadEmail.EMAIL_AUTH_PASSWORD);
        email.setOutgoingMailServerHostName("mail.pekall.com");
        email.setOutgoingMailServerPortNumber(587);
        email.setOutgoingMailServerUseSSL(true);
        email.setOutgoingMailServerUsername("test_mdm");
        email.setOutgoingPasswordSameAsIncomingPassword(true);

        email.setPreventAppSheet(true);
        email.setSMIMEEnabled(false);
        email.setPreventMove(false);
        return email;
    }

    private PayloadExchange createExchange() {
        PayloadExchange exchange = new PayloadExchange();
        exchange.setPayloadDescription("Exchange相关配置");
        exchange.setPayloadDisplayName("Exchange配置");
        exchange.setPayloadIdentifier("com.pekall.profile.exchange");
        exchange.setPayloadOrganization(ORGANIZATION);
        exchange.setPayloadUUID(getUUID());
        exchange.setPayloadVersion(1);

        exchange.setMailNumberOfPastDaysToSync(3);
        exchange.setDisableMailRecentsSyncing(false);
        exchange.setEmailAddress("xiaoliang.li@mdm.com");
        exchange.setHost("192.168.10.239");
        exchange.setUserName("mdm\\xiaoliang.li");
        exchange.setPassword("Pekall123");
        exchange.setSSL(false);
        exchange.setSMIMEEnabled(false);
        exchange.setPreventAppSheet(false);

        return exchange;
    }

    private PayloadWifiConfig createWifiConfig() {
        PayloadWifiConfig wifiConfig = new PayloadWifiConfig();

        wifiConfig.setPayloadDescription("WIFI相关配置");
        wifiConfig.setPayloadDisplayName("WIFI配置");
        wifiConfig.setPayloadIdentifier("com.pekall.config.wifi.managed");
        wifiConfig.setPayloadOrganization("Pekall Capital");
        wifiConfig.setPayloadUUID(UUID.randomUUID().toString());
        wifiConfig.setPayloadVersion(1);

        wifiConfig.setSSID_STR("pekall_work");
        wifiConfig.setHIDDEN_NETWORK(false);
        wifiConfig.setAutoJoin(true);
        wifiConfig.setEncryptionType(PayloadWifiConfig.ENCRYPTION_TYPE_WPA);
        wifiConfig.setPassword("pekallcloud");
//        wifiConfig.setProxyType(PayloadWifiConfig.PROXY_TYPE_MANUAL);
//        wifiConfig.setProxyServer("192.168.10.210");
//        wifiConfig.setProxyServerPort(80);
//        wifiConfig.setProxyUsername("jiangrui");
//        wifiConfig.setProxyPassword("123456");
//        wifiConfig.setProxyPACURL("1.2.3.4");
        // TODO: add EAP
        // wifiConfig.setEAPClientConfiguration(new EAPClientConfigurationClass());
        return wifiConfig;
    }

    private PayloadArrayWrapper createAndroidSettingProfile() {
        PayloadWifiConfig wifiConfig = createWifiConfig();
//        PayloadPasswordPolicy passwordPolicy = createPasswordPolicy();
        PayloadArrayWrapper wrapper = createSettingWrapper();
        PayloadEmail payloadEmail = createEmail();

        PayloadVPN payloadVPN = createAndroidVPN();

        PayloadWallpaper payloadWallpaper = createPayloadWallpaper();
        AdvertiseDownloadSettings advertiseDownloadSettings = getAdvertiseDownloadSettings();
        SystemSettings systemSettings = getSystemSettings();
        LauncherSettings launcherSettings = getLauncherSettings();
        BrowserSettings browserSettings = getBrowserSettings();


        wrapper.addPayLoadContent(wifiConfig);
        wrapper.addPayLoadContent(payloadEmail);
        wrapper.addPayLoadContent(payloadVPN);
        wrapper.addPayLoadContent(payloadWallpaper);
//        wrapper.addPayLoadContent(advertiseDownloadSettings);
//        wrapper.addPayLoadContent(browserSettings);
//        wrapper.addPayLoadContent(launcherSettings);
//        wrapper.addPayLoadContent(systemSettings);



//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
    }


    private PayloadArrayWrapper createIosSettingProfile() {
        PayloadWifiConfig wifiConfig = createWifiConfig();
        PayloadEmail payloadEmail = createEmail();
        PayloadVPN payloadVPN = createIosVPN();
//        PayloadPasswordPolicy passwordPolicy = createPasswordPolicy();
        PayloadArrayWrapper wrapper = createSettingWrapper();
        wrapper.addPayLoadContent(wifiConfig);
        wrapper.addPayLoadContent(payloadEmail);
        wrapper.addPayLoadContent(payloadVPN);
//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
    }

    private PayloadWallpaper createPayloadWallpaper() {
        PayloadWallpaper settings = new PayloadWallpaper();
        settings.setPayloadDescription("Wallpaper相关配置");
        settings.setPayloadDisplayName("Wallpaper配置");
        settings.setPayloadIdentifier("com.pekall.settings.wallpaper");
        settings.setPayloadOrganization(ORGANIZATION);
        settings.setPayloadUUID(getUUID());
        settings.setPayloadVersion(VERSION);

        settings.setConfigWallpaper(true);
        settings.setAllowUsrChangeWallpaper(false);
        settings.setLowResolution("320x240");

        return settings;
    }

    private SystemSettings getSystemSettings() {
        SystemSettings settings = new SystemSettings();
        settings.setWifi(1);
        settings.setAirplane(0);
        settings.setBluetooth(0);
        settings.setCamera(1);
        settings.setDataRoaming(1);
        settings.setMicrophone(1);
        settings.setMobileData(1);
        settings.setNfc(0);
        settings.setSdcard(1);
        settings.setUsb(1);

        settings.setPayloadDescription("System相关配置");
        settings.setPayloadDisplayName("System配置");
        settings.setPayloadIdentifier("com.pekall.settings.system");
        settings.setPayloadOrganization(ORGANIZATION);
        settings.setPayloadUUID(getUUID());
        settings.setPayloadVersion(VERSION);

        return settings;
    }

    private LauncherSettings getLauncherSettings() {
        LauncherSettings settings = new LauncherSettings();
        settings.setPhoneModel("HTC ONE");
        settings.setRegisterBank("建设银行");
        settings.setPhoneNumber("13811255530");
        settings.setRegisterDate("2013-7-10");
        settings.setAdmin("思创");
        settings.setDeviceState("active");
        settings.setIsRegistered(1);
        settings.setAdminPassword("123456");
        settings.setWallpaper("wallpaper_sky.jpg");
        ApkItem item1 = new ApkItem("建行客户端", "com.chinamworld.main",
                "com.chinamworld.main.BTCCMWStartActivity",
                "http://www.pekall.com/icon/1234", 1, 2, 1);
        ApkItem item2 = new ApkItem("广告", "com.pekall.advert",
                "com.pekall.advert.MainAct",
                "http://www.pekall.com/icon/12345", 1, 3, 1);
        ApkItem item3 = new ApkItem("意见薄", "com.pekall.proposal",
                "com.pekall.proposal.ProposalActivity",
                "http://www.pekall.com/icon/12345", 1, 4, 1);
        ApkItem item31 = new ApkItem("历史消息", "com.pekall.launcher",
                "com.pekall.launcher2.ui.HistoryMessageActivity",
                "http://www.pekall.com/icon/12345", 1, 5, 1);
        WebItem item4 = new WebItem("建行主页", "http://www.ccb.com",
                "http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=de6e5f8b63bc46ea32d7f46c0c33",
                0, 1, 1);
        WebItem item5 = new WebItem("个人网银", "https://ibsbjstar.ccb.com.cn/app/V5/CN/STY1/login.jsp",
                "http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=8a81ac2ae7534d64b2c7e166e69c",
                0, 2, 1);
        WebItem item6 = new WebItem("私人网银", "https://ibsbjstar.ccb.com.cn/app/V5/CN/STY6/login_pbc.jsp",
                "http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=e431cf4fb8334374eb1919fd51f9",
                0, 3, 1);
        WebItem item7 = new WebItem("企业网银", "http://ebank.ccb.com/cn/ebank/homepage_corporate.html",
                "http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=ab8d412191d64c1d891e7f55676f",
                0, 4, 1);
        WebItem item8 = new WebItem("小微网银", "http://ccb.com/cn/home/s_company_index.html",
                "http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=293e4ac13cc74a08b13304ed06f1",
                0, 5, 1);
        WebItem item9 = new WebItem("善融商城", "http://e.ccb.com/cn/home/ecp_index.html",
                "http://119.161.242.248:3342/resources/api/v1/download/guest?uuid=5c318e33a3e340b40bcbfe20816f",
                0, 6, 1);

        settings.addApkItem(item1);
        settings.addApkItem(item2);
        settings.addApkItem(item3);
        settings.addApkItem(item31);
        settings.addWebItem(item4);
        settings.addWebItem(item5);
        settings.addWebItem(item6);
        settings.addWebItem(item7);
        settings.addWebItem(item8);
        settings.addWebItem(item9);


        settings.setPayloadDescription("Launcher相关配置");
        settings.setPayloadDisplayName("Launcher配置");
        settings.setPayloadIdentifier("com.pekall.settings.launcher");
        settings.setPayloadOrganization(ORGANIZATION);
        settings.setPayloadUUID(getUUID());
        settings.setPayloadVersion(VERSION);

        return settings;
    }
    private PayloadBase getAdvertiseSettings() {
        AdvertiseDownloadSettings settings = new AdvertiseDownloadSettings();
        settings.setDownloadUrl("http://www.pekall.com/adv.zip");
        settings.setVersion("25");

        settings.setPayloadDescription("广告下载相关配置");
        settings.setPayloadDisplayName("广告下载配置");
        settings.setPayloadIdentifier("com.pekall.settings.advertise.download");
        settings.setPayloadOrganization(ORGANIZATION);
        settings.setPayloadUUID(getUUID());
        settings.setPayloadVersion(VERSION);

        return settings;
    }

    private BrowserSettings getBrowserSettings() {
        List<QuickLaunchItem> quickLaunches = new ArrayList<QuickLaunchItem>();
        List<UrlMatchRule> whiteList = new ArrayList<UrlMatchRule>();
        List<HistoryWatchItem> historyWatches = new ArrayList<HistoryWatchItem>();

        quickLaunches.add(new QuickLaunchItem("1", "建行主页", "http://www.ccb.com"));
        quickLaunches.add(new QuickLaunchItem("2", "个人网上银行", "https://ibsbjstar.ccb.com.cn/app/V5/CN/STY1/login.jsp"));
        quickLaunches.add(new QuickLaunchItem("3", "企业电子银行", "http://ebank.ccb.com/cn/ebank/homepage_corporate.html"));
        quickLaunches.add(new QuickLaunchItem("4", "私人银行上网银行", "https://ibsbjstar.ccb.com.cn/app/V5/CN/STY6/login_pbc.jsp"));
        quickLaunches.add(new QuickLaunchItem("5", "小型微企", "http://ccb.com/cn/home/s_company_index.html"));
        quickLaunches.add(new QuickLaunchItem("6", "善融商城", "http://e.ccb.com/cn/home/ecp_index.html"));

        whiteList.add(new UrlMatchRule("sina.com"));
        whiteList.add(new UrlMatchRule("baidu.com", UrlMatchRule.MATCH_TYPE_EQUAL));
        whiteList.add(new UrlMatchRule("24", "ccb.com", UrlMatchRule.MATCH_TYPE_EQUAL));

        historyWatches.add(new HistoryWatchItem("baidu.com"));
        historyWatches.add(new HistoryWatchItem("ccb.com", UrlMatchRule.MATCH_TYPE_PREFIX));

        BrowserSettings settings = new BrowserSettings(1, "http://www.ccb.com",
                quickLaunches, whiteList, historyWatches);

        settings.setPayloadDescription("SeBrowser相关配置");
        settings.setPayloadDisplayName("SeBrowser配置");
        settings.setPayloadIdentifier("com.pekall.settings.sebrowser");
        settings.setPayloadOrganization(ORGANIZATION);
        settings.setPayloadUUID(getUUID());
        settings.setPayloadVersion(VERSION);
        return settings;
    }

    private String getUUID(){
        return UUID.randomUUID().toString();
    }

    private AdvertiseDownloadSettings getAdvertiseDownloadSettings() {
        AdvertiseDownloadSettings settings = new AdvertiseDownloadSettings();
        settings.setDownloadUrl("http://221.122.32.42:8098/Zip/130803_180440_5314/130803_180508_6659.zip");
        settings.setVersion("1.0");

        settings.setPayloadDescription("广告下载相关配置");
        settings.setPayloadDisplayName("广告下载配置");
        settings.setPayloadIdentifier("com.pekall.settings.advertise.download");
        settings.setPayloadOrganization(ORGANIZATION);
        settings.setPayloadUUID(getUUID());
        settings.setPayloadVersion(VERSION);

        return settings;
    }
    private PayloadArrayWrapper createSettingWrapper() {
        PayloadArrayWrapper wrapper = new PayloadArrayWrapper();
        wrapper.setPayloadDescription("配置文件");
        wrapper.setPayloadDisplayName("Pekall Default Setting");
        wrapper.setPayloadIdentifier("com.pekall.profile.setting");
        wrapper.setPayloadOrganization("Pekall Capital");
        wrapper.setPayloadType("Configuration");
        wrapper.setPayloadUUID(UUID.randomUUID().toString());
        wrapper.setPayloadVersion(1);
        wrapper.setPayloadRemovalDisallowed(true);
        return wrapper;
    }


    private PayloadArrayWrapper createAndroidPolicyProfile() {
//        PayloadWifiConfig wifiConfig = createWifiConfig();
        PayloadPasswordPolicy passwordPolicy = createPasswordPolicy();
        PayloadSecurityPolicy payloadSecurityPolicy =  createAndroidSecurityPolicy();
        PayloadRestrictionsAndroidPolicy payloadRestrictionsAndroidPolicy = createAndroidRestrictionsPolicy();
        AppControlList appControlList = getAppControlList();
        PayloadActiveSyncPolicy payloadActiveSyncPolicy =   createAndroidActiveSyncPolicy();
        PayloadNativeAppCtrlPolicy payloadNativeAppCtrlPolicy = createAndroidNativeAppCtrlPolicy();

        PayloadNetRestrictPolicy payloadNetRestrictPolicy = createAndroidNetRestrictPolicy();
        PayloadBluetoothPolicy payloadBluetoothPolicy = createAndroidBluetoothPolicy();

//        MemorySizePolicy memorySizePolicy = getMemorySizePolicy();
//        AppControlList appControlList = getAppControlList();
//        SystemExceptionPolicy systemExceptionPolicy = getSystemExceptionPolicy();

        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(passwordPolicy);
        wrapper.addPayLoadContent(payloadActiveSyncPolicy);
        wrapper.addPayLoadContent(payloadRestrictionsAndroidPolicy);
        wrapper.addPayLoadContent(payloadSecurityPolicy);
        wrapper.addPayLoadContent(payloadBluetoothPolicy);
        wrapper.addPayLoadContent(payloadNativeAppCtrlPolicy);
        wrapper.addPayLoadContent(payloadNetRestrictPolicy);
//        wrapper.addPayLoadContent(memorySizePolicy);
        wrapper.addPayLoadContent(appControlList);
//        wrapper.addPayLoadContent(systemExceptionPolicy);
//        wrapper.addPayLoadContent(removalPassword);
//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
    }
    private PayloadActiveSyncPolicy createAndroidActiveSyncPolicy() {

        PayloadActiveSyncPolicy policy = new PayloadActiveSyncPolicy();
        policy.setPayloadDescription("Active Sync相关配置");
        policy.setPayloadDisplayName("Active Sync配置");
        policy.setPayloadIdentifier("com.pekall.policy.active.sync");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        policy.setPayloadVersion(1);

        policy.setAcceptAllCertificates(true);
        policy.setAccount("ray");
        policy.setAllowAttachments(false);
        policy.setAllowHtmlEmail(true);
        policy.setAllowBackup(true);
        policy.setConfigurePasscode(true);
        policy.setPolicyKey("xx-xx-xxxx-xx-x");
        policy.setDisplayName("my active sync");
        policy.setEmailSignature("BRs");
        policy.setHostName("test host");
        return policy;
    }

    private PayloadBluetoothPolicy createAndroidBluetoothPolicy() {

        PayloadBluetoothPolicy policy = new PayloadBluetoothPolicy();
        policy.setPayloadDescription("Bluetooth相关配置");
        policy.setPayloadDisplayName("Bluetooth配置");
        policy.setPayloadIdentifier("com.pekall.policy.bluetooth");
        policy.setPayloadOrganization(ORGANIZATION);
        policy.setPayloadUUID(getUUID());
        policy.setPayloadVersion(VERSION);

        policy.setAllow2Desktop(false);
        policy.setAllowA2DP(true);
        policy.setAllowDataTransfer(true);
        policy.setAllowDeviceDiscovery(true);
        policy.setAllowHeadset(true);
        policy.setAllowOutgoingCalls(true);
        return policy;
    }


    private PayloadNetRestrictPolicy createAndroidNetRestrictPolicy() {

        PayloadNetRestrictPolicy policy = new PayloadNetRestrictPolicy();
        policy.setPayloadDescription("Network Restriction相关配置");
        policy.setPayloadDisplayName("Network Restriction配置");
        policy.setPayloadIdentifier("com.pekall.policy.network.restriction");
        policy.setPayloadOrganization(ORGANIZATION);
        policy.setPayloadUUID(getUUID());
        policy.setPayloadVersion(VERSION);

        policy.setAllowEmgCallOnly(true);
        policy.setAllowWifi(true);
        policy.setWhitelistedSSIDs(new ArrayList<String>());
        policy.setBlacklistedSSIDs(new ArrayList<String>());
        policy.setAllowUsrAddWifi(true);
        policy.setMinSecurityLevel(PayloadNetRestrictPolicy.SEC_LEVEL_EAP_FAST);
        policy.setAllowDataNetwork(PayloadNetRestrictPolicy.CTRL_USR_CONTROLLED);
        policy.setMobileAP(PayloadNetRestrictPolicy.CTRL_USR_CONTROLLED);
        policy.setAllowMessage(PayloadNetRestrictPolicy.ALLOW_MSG_ALL);
        policy.setAllowDataRoaming(PayloadNetRestrictPolicy.CTRL_USR_CONTROLLED);
        policy.setAllowSyncRoaming(true);
        policy.setAllowVoiceRoaming(true);
        policy.setUsbTethering(PayloadNetRestrictPolicy.CTRL_USR_CONTROLLED);

//        policy.setAllowDataNetwork(PayloadNetRestrictPolicy.CTRL_USR_CONTROLLED);
//        policy.setAllowEmgCallOnly(true);
//        policy.setAllowMessage(PayloadNetRestrictPolicy.CTRL_ENABLED);
//        policy.addBlacklistedSSIDs("xxxx-xx-xx-xx");
//        policy.addWhitelistedSSID("xx-xxxx-xx-xx-xx");
//        policy.setAllowWifi(true);
//        policy.setMobileAP(PayloadNetRestrictPolicy.CTRL_DISABLE);
        return policy;
    }

    private PayloadNativeAppCtrlPolicy createAndroidNativeAppCtrlPolicy() {

        PayloadNativeAppCtrlPolicy policy = new PayloadNativeAppCtrlPolicy();
        policy.setPayloadDescription("Native app相关配置");
        policy.setPayloadDisplayName("Native app配置");
        policy.setPayloadIdentifier("com.pekall.policy.native.app");
        policy.setPayloadOrganization(ORGANIZATION);
        policy.setPayloadUUID(getUUID());
        policy.setPayloadVersion(VERSION);

        policy.setBrowser(true);
        policy.setGallery(false);
        policy.setGmail(true);
        policy.setGoogleMap(true);
        policy.setSettings(true);
        policy.setYoutube(false);
        policy.setVoiceDialer(true);
        return policy;
    }

    private PayloadRestrictionsAndroidPolicy createAndroidRestrictionsPolicy() {

        PayloadRestrictionsAndroidPolicy policy = new PayloadRestrictionsAndroidPolicy();
        policy.setPayloadDescription("Restriction相关配置");
        policy.setPayloadDisplayName("Restriction配置");
        policy.setPayloadIdentifier("com.pekall.policy.restriction");
        policy.setPayloadOrganization(ORGANIZATION);
        policy.setPayloadUUID(getUUID());
        policy.setPayloadVersion(VERSION);


        policy.setBackgroundDataSync(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        policy.setAutoSync(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        policy.setCamera(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        policy.setBluetooth(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        policy.setAllowUSBMassStorage(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        policy.setAllowUsbMediaPlayer(true);
        policy.setUseNetworkDateTime(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        policy.setAllowMicrophone(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        policy.setAllowNFC(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        policy.setUseWirelessNetworkForLocation(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        policy.setUseGPSForLocation(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        policy.setUseSensorAidingForLocation(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        policy.setAllowMockLocation(PayloadRestrictionsAndroidPolicy.CTRL_USR_CONTROLLED);
        return policy;
    }

    private PayloadSecurityPolicy createAndroidSecurityPolicy() {

        PayloadSecurityPolicy policy = new PayloadSecurityPolicy();
        policy.setPayloadDescription("Security相关配置");
        policy.setPayloadDisplayName("Security配置");
        policy.setPayloadIdentifier("com.pekall.policy.security");
        policy.setPayloadOrganization(ORGANIZATION);
        policy.setPayloadUUID(getUUID());
        policy.setPayloadVersion(VERSION);

        policy.setEnforceEncryption(false);
        policy.setEnforceSDCardEncryption(false);
        policy.setAllowSDCardWrite(true);
        policy.setDisableKeyguardFeatures(PayloadSecurityPolicy.KEYGUARD_FEATURE_ALL_FEATURES);
        policy.setAllowNonGoogleApp(PayloadSecurityPolicy.APP_USR_CONTROLLED);
        policy.setEnforceAppVerify(PayloadSecurityPolicy.APP_USR_CONTROLLED);
        policy.setAllowScreenCapture(true);

        policy.setAllowClipboard(true);
        policy.setBackupMyData(PayloadSecurityPolicy.APP_USR_CONTROLLED);
        policy.setAutomaticRestore(PayloadSecurityPolicy.APP_USR_CONTROLLED);
        policy.setVisiblePasswords(PayloadSecurityPolicy.APP_USR_CONTROLLED);
        policy.setAllowUSBDebugging(true);
        policy.setAllowGoogleCrashReport(true);
        policy.setAllowFactoryReset(true);
        policy.setAllowOTAUpgrade(true);
        return policy;
    }

    private SystemExceptionPolicy getSystemExceptionPolicy() {
        List<SystemException> exceptions = new ArrayList<SystemException>();
        exceptions.add(new SystemException(
                1, "memory", "Internal/External Storage存储空间满", "5"));
        exceptions.add(new SystemException(
                1, "application", "关键应用出现异常", "6"));
        exceptions.add(new SystemException(
                0, "configuration", "配置被本地管理员修改", "7"));
        exceptions.add(new SystemException(
                0, "application install", "系统安装的应用与服务器下发的列表不符", "8"));
        SystemExceptionPolicy policy = new SystemExceptionPolicy("test", 1, "test desc", exceptions);
        policy.setDefault(true);

        policy.setPayloadDescription("System Exception相关配置");
        policy.setPayloadDisplayName("System Exception");
        policy.setPayloadIdentifier("com.pekall.policy.system.exception");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        policy.setPayloadVersion(1);

        return policy;
    }

    private AppControlList getAppControlList() {
        AppInfoWrapper must = new AppInfoWrapper("1");
        AppInfoWrapper white = new AppInfoWrapper("2");
        AppInfoWrapper black = new AppInfoWrapper();
        AppInfoWrapper grey = new AppInfoWrapper("4");
       /* must.addInfo(new AppInfo("test1", 0, "com.test.1", "1.3", "http://1.2.3/"));
        must.addInfo(new AppInfo("test11", 0, "com.test.11", "1.13", "http://1.2.3/"));*/
        white.addInfo(new AppInfo("QQ通讯录", 1, "com.tencent.qqphonebook", "920", "http://192.168.10.223/resources/download?uuid=d30b22ea659342a3be70631d7821aaee"));
        white.addInfo(new AppInfo("Gmail", 1, "com.google.android.gm", "176", "http://192.168.10.223/resources/download?uuid=f8619d930b464d958f9f9ccf5afbe3d6"));
        black.addInfo(new AppInfo("UC浏览器", 2, "com.UCMobile", "30", "http://192.168.10.223/resources/download?uuid=81f95afadc154c64801b23b7b4889189"));
        black.addInfo(new AppInfo("支付宝", 2, "com.eg.android.AlipayGphone", "29", "http://192.168.10.223/resources/download?uuid=bbbbd5ad28df4a39b414edf5fe680a72"));
        grey.addInfo(new AppInfo("QQ阅读 ", 3, "com.qq.reader", "24", "http://192.168.10.223/resources/download?uuid=50b1296b415a4be6af4707c88bbb68c1"));

        AppControlList policy = new AppControlList("App Control", 1,
                "Default application control", must, white, black, grey);

        policy.setPayloadDescription("App Control相关配置");
        policy.setPayloadDisplayName("App Control");
        policy.setPayloadIdentifier("com.pekall.policy.app.control");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        policy.setPayloadVersion(1);

        return policy;
    }

    private MemorySizePolicy getMemorySizePolicy() {
        MemoryLimit memoryLimit = new MemoryLimit(80, "5");
        DiskLimit diskLimit = new DiskLimit(95, "5");
        Memory memory = new Memory(memoryLimit, diskLimit);
        MemorySizePolicy policy = new MemorySizePolicy(
                "Memory Policy", 1, "Default Policy", memory);

        policy.setPayloadDescription("Memory相关配置");
        policy.setPayloadDisplayName("Memory配置");
        policy.setPayloadIdentifier("com.pekall.policy.memory");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        policy.setPayloadVersion(1);

        return policy;
    }

    private PayloadArrayWrapper createIosPolicyProfile() {
//        PayloadWifiConfig wifiConfig = createWifiConfig();
        PayloadPasswordPolicy passwordPolicy = createPasswordPolicy();
        PayloadRestrictionsPolicy restrictionsPolicy = createIosRestrictionsPolicy();
        PayloadExchange payloadExchange = createExchange();

        PayloadRoamingPolicy payloadRomingPolicy = createPayloadRomingPolicy();
        PayloadRemovalPassword removalPassword = createRemovalPassword();

        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(passwordPolicy);
        wrapper.addPayLoadContent(restrictionsPolicy);
        wrapper.addPayLoadContent(payloadExchange);
        wrapper.addPayLoadContent(payloadRomingPolicy);
//        wrapper.addPayLoadContent(removalPassword);
//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
    }

    private PayloadRoamingPolicy createPayloadRomingPolicy() {

        PayloadRoamingPolicy policy = new PayloadRoamingPolicy();
        policy.setPayloadDescription("Roming相关配置");
        policy.setPayloadDisplayName("Roming配置");
        policy.setPayloadIdentifier("com.pekall.policy.roming");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        policy.setPayloadVersion(1);

        policy.setDataRoaming(false);
        policy.setVoiceRoaming(false);
        return policy;
    }

    private PayloadArrayWrapper createWrapper() {
        PayloadArrayWrapper wrapper = new PayloadArrayWrapper();
        wrapper.setPayloadDescription("策略文件");
        wrapper.setPayloadDisplayName("Pekall Default Policy");
        wrapper.setPayloadIdentifier("com.pekall.profile.policy");
        wrapper.setPayloadOrganization("Pekall Capital");
        wrapper.setPayloadType("Configuration");
        wrapper.setPayloadUUID(UUID.randomUUID().toString());
        wrapper.setPayloadVersion(1);
        wrapper.setPayloadRemovalDisallowed(true);
        return wrapper;
    }

    private PayloadPasswordPolicy createPasswordPolicy() {
        PayloadPasswordPolicy policy = new PayloadPasswordPolicy();
        policy.setPayloadDescription("配置与安全相关的项目。");
        policy.setPayloadDisplayName("密码");
        policy.setPayloadIdentifier("com.pekall.profile.passcodepolicy");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadUUID(UUID.randomUUID().toString());
        policy.setPayloadVersion(1);
        policy.setAllowSimple(true);
        policy.setForcePIN(true);
        policy.setMaxFailedAttempts(10);
//        policy.setMaxGracePeriod(-1);
        policy.setPinHistory(50);
        policy.setMaxPINAgeInDays(730);
        policy.setMinLength(4);
        policy.setMaxInactivity(1);//自动锁定
        policy.setRequireAlphanumeric(false);
//        policy.setMinComplexChars(-1);

        policy.setQuality(PayloadPasswordPolicy.QUALITY_NUMERIC);
        return policy;
    }

    public static final String ORGANIZATION = "Pekall Captital";
    public static final int VERSION = 1;

    public static PayloadRestrictionsPolicy createIosRestrictionsPolicy() {
        PayloadRestrictionsPolicy policy = new PayloadRestrictionsPolicy();
        policy.setPayloadDescription("配置与设备限制相关的项目");
        policy.setPayloadDisplayName("限制");
        policy.setPayloadIdentifier("com.pekall.profile.applicationaccess");
        policy.setPayloadOrganization(ORGANIZATION);
        policy.setPayloadUUID(UUID.randomUUID().toString());
        policy.setPayloadVersion(VERSION);

        policy.setAllowAppInstallation(true);
        policy.setAllowAssistant(true);
        policy.setAllowAssistantWhileLocked(true);
        policy.setAllowCamera(true);
        policy.setAllowDiagnosticSubmission(true);
        policy.setAllowExplicitContent(true);
        policy.setAllowGameCenter(true);
        policy.setAllowScreenShot(true);
        policy.setAllowYouTube(true);
        policy.setAllowiTunes(true);
        policy.setForceITunesStorePasswordEntry(false);
        policy.setAllowSafari(true);
        policy.setAllowUntrustedTLSPrompt(true);
        policy.setAllowCloudBackup(true);
        policy.setAllowCloudDocumentSync(true);
        policy.setAllowPhotoStream(true);
        policy.setAllowBookstore(true);
        policy.setAllowBookstoreErotica(true);
        policy.setAllowPassbookWhileLocked(true);
        policy.setAllowSharedStream(true);
        policy.setAllowUIConfigurationProfileInstallation(true);

        return policy;
    }



    private PayloadVPN createAndroidVPN() {
        PayloadVPN vpn = new PayloadVPN();
        vpn.setPayloadDescription("VPN相关配置");
        vpn.setPayloadDisplayName("VPN配置");
        vpn.setPayloadIdentifier("com.pekall.profile.VPN");
        vpn.setPayloadOrganization(ORGANIZATION);
        vpn.setPayloadUUID(getUUID());
        vpn.setPayloadVersion(VERSION);

        vpn.setUserDefinedName("test vpn");
        vpn.setVPNType(PayloadVPN.TYPE_PPTP);

        vpn.setServerHostName("192.168.10.220");
        vpn.setAccount("ray");
        vpn.setPassword("123456");
        vpn.setSharedPassword("qwert");
        vpn.setUserAuth(PayloadVPN.USR_AUTH_PASSWORD);
        vpn.setVpnForAllTraffic(true);
        vpn.setProxyHost("192.168.0.1");
        vpn.setProxyPort(889);
        vpn.setProxyUserName("Ray");
        vpn.setProxyPassword("qwert");

//        IPSecInfo ipSecInfo = new IPSecInfo();
//        ipSecInfo.setAuthenticationMethod("test method");
//        ipSecInfo.setLocalIdentifier("my ipsec id");
//        ipSecInfo.setLocalIdentifierType("key id 1003");
//        ipSecInfo.setPromptForVPNPIN(true);
//        ipSecInfo.setXAuthEnabled(1);
//        byte[] data = new byte[10];
//        for (int i = 0; i < data.length; i++) {
//            data[i] = (byte) i;
//        }
//        ipSecInfo.setSharedSecret(data);
//        vpn.setIPSec(ipSecInfo);

        return vpn;
    }
    private PayloadVPN createIosVPN() {
        PayloadVPN vpn = new PayloadVPN();
        vpn.setPayloadDescription("VPN相关配置");
        vpn.setPayloadDisplayName("VPN配置");
        vpn.setPayloadIdentifier("com.pekall.profile.VPN");
        vpn.setPayloadOrganization(ORGANIZATION);
        vpn.setPayloadUUID(getUUID());
        vpn.setPayloadVersion(VERSION);

        vpn.setOverridePrimary(true);
        vpn.setUserDefinedName("test vpn");
        vpn.setVPNType(PayloadVPN.TYPE_PPTP);

        PPPInfo pppInfo = new PPPInfo();
//        pppInfo.enableAuthEAPPlugins();
//        pppInfo.enableAuthProtocol();
        pppInfo.setAuthName("ooo4561213");
        pppInfo.setAuthPassword("xxxxxxxx");
        pppInfo.setCCPEnabled(false);
        pppInfo.setCCPMPPE128Enabled(false);
        pppInfo.setCommRemoteAddress("mail.pekall.com");
        vpn.setPPP(pppInfo);

//        IPSecInfo ipSecInfo = new IPSecInfo();
//        ipSecInfo.setAuthenticationMethod("test method");
//        ipSecInfo.setLocalIdentifier("my ipsec id");
//        ipSecInfo.setLocalIdentifierType("key id 1003");
//        ipSecInfo.setPromptForVPNPIN(true);
//        ipSecInfo.setXAuthEnabled(1);
//        byte[] data = new byte[10];
//        for (int i = 0; i < data.length; i++) {
//            data[i] = (byte) i;
//        }
//        ipSecInfo.setSharedSecret(data);
//        vpn.setIPSec(ipSecInfo);

        return vpn;
    }


    private PayloadRemovalPassword createRemovalPassword() {
        PayloadRemovalPassword removalPassword = new PayloadRemovalPassword();
        removalPassword.setPayloadDescription("RM PW相关配置");
        removalPassword.setPayloadDisplayName("RM PW配置");
        removalPassword.setPayloadIdentifier("com.pekall.profile.removalpassword");
        removalPassword.setPayloadOrganization(ORGANIZATION);
        removalPassword.setPayloadUUID(UUID.randomUUID().toString());
        removalPassword.setPayloadVersion(VERSION);

        removalPassword.setRemovalPassword("123456");
        return removalPassword;
    }


    class TestVo{
        private  Integer test;

        Integer getTest() {
            return test;
        }

        void setTest(Integer test) {
            this.test = test;
        }
    }
    public void testJson(){


        System.out.println( String.format("%1$.3f", 12312323123.00000));

        TestVo testVo = new TestVo();
        testVo.setTest(null);

        String respond = "{\"test\":null}\n";


        Gson gson = new Gson();


        System.out.println(gson.toJson(testVo));

        TestVo testVo1 = gson.fromJson(respond, TestVo.class);
        System.out.println(testVo1.getTest());

//        JsonObject jsonObject =  gson.fromJson(respond,JsonObject.class);
//        System.out.println(jsonObject);
//        System.out.println(jsonObject.get("result").getAsInt());
    }
}
