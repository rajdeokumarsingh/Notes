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
            "\t\t\t<key>allowSimple</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t\t<key>forcePIN</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>maxFailedAttempts</key>\n" +
            "\t\t\t<integer>7</integer>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.mobiledevice.passwordpolicy</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.profile.passcodepolicy</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>51a9cfc2-b1a0-458e-a7cc-32c2775933b4</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>密码</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>配置与安全相关的项目。</string>\n" +
            "\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t<string>Pekall Capital</string>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>allowAppInstallation</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowAssistant</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowAssistantWhileLocked</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowCamera</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowDiagnosticSubmission</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowExplicitContent</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowGameCenter</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowScreenShot</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowYouTube</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowiTunes</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>forceITunesStorePasswordEntry</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowSafari</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowUntrustedTLSPrompt</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowCloudBackup</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowCloudDocumentSync</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowPhotoStream</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowBookstore</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowBookstoreErotica</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowPassbookWhileLocked</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowSharedStream</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowUIConfigurationProfileInstallation</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.applicationaccess</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.profile.applicationaccess</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>ff571cc5-1f23-4315-8bda-08c5709b140b</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>限制</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>配置与设备限制相关的项目</string>\n" +
            "\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t<string>Pekall Captital</string>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>PayloadRemovalDisallowed</key>\n" +
            "\t<true/>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>Configuration</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.profile.policy</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>0c9846e2-a6e3-4119-8eaa-13db0db5dee6</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>Pekall Default Policy</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>策略文件</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";


    public static final String JSON_CONTENT = "{\n" +
            "    \"PayloadContent\": {\n" +
            "        \"payloadPasswordPolicy\": {\n" +
            "            \"allowSimple\": false,\n" +
            "            \"forcePIN\": true,\n" +
            "            \"maxFailedAttempts\": 7,\n" +
            "            \"PayloadType\": \"com.apple.mobiledevice.passwordpolicy\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.profile.passcodepolicy\",\n" +
            "            \"PayloadUUID\": \"51a9cfc2-b1a0-458e-a7cc-32c2775933b4\",\n" +
            "            \"PayloadDisplayName\": \"密码\",\n" +
            "            \"PayloadDescription\": \"配置与安全相关的项目。\",\n" +
            "            \"PayloadOrganization\": \"Pekall Capital\"\n" +
            "        },\n" +
            "        \"payloadRestrictionsPolicy\": {\n" +
            "            \"allowAppInstallation\": true,\n" +
            "            \"allowAssistant\": true,\n" +
            "            \"allowAssistantWhileLocked\": true,\n" +
            "            \"allowCamera\": true,\n" +
            "            \"allowDiagnosticSubmission\": true,\n" +
            "            \"allowExplicitContent\": true,\n" +
            "            \"allowGameCenter\": true,\n" +
            "            \"allowScreenShot\": true,\n" +
            "            \"allowYouTube\": true,\n" +
            "            \"allowiTunes\": true,\n" +
            "            \"forceITunesStorePasswordEntry\": true,\n" +
            "            \"allowSafari\": true,\n" +
            "            \"allowUntrustedTLSPrompt\": true,\n" +
            "            \"allowCloudBackup\": true,\n" +
            "            \"allowCloudDocumentSync\": true,\n" +
            "            \"allowPhotoStream\": true,\n" +
            "            \"allowBookstore\": true,\n" +
            "            \"allowBookstoreErotica\": true,\n" +
            "            \"allowPassbookWhileLocked\": true,\n" +
            "            \"allowSharedStream\": true,\n" +
            "            \"allowUIConfigurationProfileInstallation\": true,\n" +
            "            \"PayloadType\": \"com.apple.applicationaccess\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.profile.applicationaccess\",\n" +
            "            \"PayloadUUID\": \"ff571cc5-1f23-4315-8bda-08c5709b140b\",\n" +
            "            \"PayloadDisplayName\": \"限制\",\n" +
            "            \"PayloadDescription\": \"配置与设备限制相关的项目\",\n" +
            "            \"PayloadOrganization\": \"Pekall Captital\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"PayloadRemovalDisallowed\": true,\n" +
            "    \"PayloadType\": \"Configuration\",\n" +
            "    \"PayloadVersion\": 1,\n" +
            "    \"PayloadIdentifier\": \"com.pekall.profile.policy\",\n" +
            "    \"PayloadUUID\": \"0c9846e2-a6e3-4119-8eaa-13db0db5dee6\",\n" +
            "    \"PayloadDisplayName\": \"Pekall Default Policy\",\n" +
            "    \"PayloadDescription\": \"策略文件\",\n" +
            "    \"PayloadOrganization\": \"Pekall Capital\"\n" +
            "}";

    public void testXMl2Json(){
        System.out.println(xml2Json(CONTENT));;
    }

    public void testJson2Xml(){
        System.out.println(json2Xml(JSON_CONTENT));;
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
            System.out.println("json 解析异常");
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

        AdvertiseDownloadSettings advertiseDownloadSettings = getAdvertiseDownloadSettings();
        SystemSettings systemSettings = getSystemSettings();
        LauncherSettings launcherSettings = getLauncherSettings();
        BrowserSettings browserSettings = getBrowserSettings();


        wrapper.addPayLoadContent(wifiConfig);
        wrapper.addPayLoadContent(advertiseDownloadSettings);
        wrapper.addPayLoadContent(systemSettings);
        wrapper.addPayLoadContent(launcherSettings);
        wrapper.addPayLoadContent(browserSettings);

//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
    }


    private PayloadArrayWrapper createIosSettingProfile() {
        PayloadWifiConfig wifiConfig = createWifiConfig();
//        PayloadPasswordPolicy passwordPolicy = createPasswordPolicy();
        PayloadArrayWrapper wrapper = createSettingWrapper();
        wrapper.addPayLoadContent(wifiConfig);
//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
    }

    private SystemSettings getSystemSettings() {
        SystemSettings settings = new SystemSettings();
        settings.setWifi(1);
        settings.setAirplane(1);
        settings.setBluetooth(1);
        settings.setCamera(1);
        settings.setDataRoaming(1);
        settings.setMicrophone(1);
        settings.setMobileData(1);
        settings.setNfc(1);
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

        historyWatches.add(new HistoryWatchItem("baidu"));
        historyWatches.add(new HistoryWatchItem("baidu", UrlMatchRule.MATCH_TYPE_PREFIX));

        BrowserSettings settings = new BrowserSettings(1, "https://www.ccb.com.cn",
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
        PayloadRestrictionsPolicy restrictionsPolicy = createIosRestrictionsPolicy();

        MemorySizePolicy memorySizePolicy = getMemorySizePolicy();
        AppControlList appControlList = getAppControlList();
        SystemExceptionPolicy systemExceptionPolicy = getSystemExceptionPolicy();

        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(passwordPolicy);
        wrapper.addPayLoadContent(restrictionsPolicy);
        wrapper.addPayLoadContent(memorySizePolicy);
        wrapper.addPayLoadContent(appControlList);
        wrapper.addPayLoadContent(systemExceptionPolicy);
//        wrapper.addPayLoadContent(removalPassword);
//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
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
        must.addInfo(new AppInfo("test1", 0, "com.test.1", "1.3", "http://1.2.3/"));
        must.addInfo(new AppInfo("test11", 0, "com.test.11", "1.13", "http://1.2.3/"));
        white.addInfo(new AppInfo("test2", 1, "com.test.2", "1.4", "http://1.2.5/"));
        white.addInfo(new AppInfo("test22", 1, "com.test.22", "1.4", "http://1.2.5/"));
        black.addInfo(new AppInfo("test3", 2, "com.test.3", "1.5", "http://1.2.9/"));
        grey.addInfo(new AppInfo("test4", 3, "com.test.4", "1.6", "http://1.2.ap/"));

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
        MemoryLimit memoryLimit = new MemoryLimit(80, "Notification");
        DiskLimit diskLimit = new DiskLimit(95, "Notification");
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

        PayloadRemovalPassword removalPassword = createRemovalPassword();

        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(passwordPolicy);
        wrapper.addPayLoadContent(restrictionsPolicy);
//        wrapper.addPayLoadContent(removalPassword);
//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
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
        policy.setAllowSimple(false);
        policy.setForcePIN(true);
        policy.setMaxFailedAttempts(7);
//        policy.setMaxGracePeriod(1);
//        policy.setMaxInactivity(2);
//        policy.setMaxPINAgeInDays(2);
//        policy.setMinComplexChars(2);
//        policy.setMinLength(4);
//        policy.setPinHistory(50);
//        policy.setRequireAlphanumeric(true);
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
        policy.setForceITunesStorePasswordEntry(true);
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
}
