package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.google.gson.Gson;
import com.pekall.plist.beans.*;
import com.pekall.plist.json.PayloadJsonWrapper;
import com.pekall.plist.su.device.DeviceInfoRespSU;
import com.pekall.plist.su.device.NetSpeed;
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
import java.util.Date;
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
            "<key>PayloadContent</key>\n" +
            "<array>\n" +
            "<dict>\n" +
            "<key>EmailAccountDescription</key>\n" +
            "<string>aaa</string>\n" +
            "<key>EmailAccountType</key>\n" +
            "<string>EmailTypeIMAP</string>\n" +
            "<key>EmailAddress</key>\n" +
            "<string>aaa</string>\n" +
            "<key>IncomingMailServerHostName</key>\n" +
            "<string>aaa</string>\n" +
            "<key>IncomingMailServerPortNumber</key>\n" +
            "<integer>123</integer>\n" +
            "<key>OutgoingMailServerHostName</key>\n" +
            "<string>bbb</string>\n" +
            "<key>OutgoingMailServerPortNumber</key>\n" +
            "<integer>222</integer>\n" +
            "<key>OutgoingMailServerUseSSL</key>\n" +
            "<true/>\n" +
            "<key>senderName</key>\n" +
            "<string>123</string>\n" +
            "<key>PayloadType</key>\n" +
            "<string>com.apple.email.managed</string>\n" +
            "<key>PayloadVersion</key>\n" +
            "<integer>1</integer>\n" +
            "<key>PayloadIdentifier</key>\n" +
            "<string>com.pekall.config.email.managed</string>\n" +
            "<key>PayloadUUID</key>\n" +
            "<string>878u9x3g-ib42-ln29-u65x-74at8c681bs1</string>\n" +
            "<key>PayloadDisplayName</key>\n" +
            "<string>Email配置</string>\n" +
            "<key>PayloadDescription</key>\n" +
            "<string>Email相关配置。</string>\n" +
            "<key>PayloadOrganization</key>\n" +
            "<string>pekall Capital</string>\n" +
            "</dict>\n" +
            "</array>\n" +
            "<key>PayloadType</key>\n" +
            "<string>Configuration</string>\n" +
            "<key>PayloadVersion</key>\n" +
            "<integer>1</integer>\n" +
            "<key>PayloadIdentifier</key>\n" +
            "<string>com.pekall.profile.setting</string>\n" +
            "<key>PayloadUUID</key>\n" +
            "<string>k66dg2r8-4xqx-b6jr-6u19-6g7r7w8kbp5b</string>\n" +
            "<key>PayloadDisplayName</key>\n" +
            "<string>反对撒反对撒法2213</string>\n" +
            "<key>PayloadDescription</key>\n" +
            "<string>发大水反对撒反对撒法55</string>\n" +
            "<key>PayloadOrganization</key>\n" +
            "<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";


    public static final String JSON_CONTENT = "{\n" +
            "  \"PayloadType\": \"Configuration\",\n" +
            "  \"PayloadVersion\": 1,\n" +
            "  \"PayloadIdentifier\": \"com.pekall.profile.setting\",\n" +
            "  \"PayloadUUID\": \"1tam7zn8-5fax-16zd-izw6-78g1ai5dt333\",\n" +
            "  \"PayloadDisplayName\": \"ccc\",\n" +
            "  \"PayloadDescription\": \"cccc\",\n" +
            "  \"PayloadOrganization\": \"Pekall Capital\",\n" +
            "  \"PayloadContent\": {\n" +
            "    \"payloadEmails\": [\n" +
            "      {\n" +
            "        \"PayloadType\": \"com.apple.mail.managed\",\n" +
            "        \"PayloadVersion\": 1,\n" +
            "        \"PayloadIdentifier\": \"com.pekall.profile.email\",\n" +
            "        \"PayloadUUID\": \"9vf9w195-497x-1q2t-849e-7qd491s69b61\",\n" +
            "        \"PayloadDisplayName\": \"Email配置\",\n" +
            "        \"PayloadDescription\": \"Email相关配置\",\n" +
            "        \"PayloadOrganization\": \"pekall Capital\",\n" +
            "        \"EmailAccountDescription\": \"test email account\",\n" +
            "        \"EmailAccountType\": \"EmailTypeIMAP\",\n" +
            "        \"EmailAddress\": \"test_mdm@pekall.com\",\n" +
            "        \"IncomingMailServerHostName\": \"mail.pekall.com\",\n" +
            "        \"IncomingMailServerUsername\": \"test_mdm\",\n" +
            "        \"IncomingMailServerPortNumber\": \"993\",\n" +
            "        \"IncomingMailServerAuthentication\": \"EmailAuthPassword\",\n" +
            "        \"IncomingPassword\": \"123456\",\n" +
            "        \"IncomingMailServerUseSSL\": true,\n" +
            "        \"OutgoingMailServerHostName\": \"mail.pekall.com\",\n" +
            "        \"OutgoingMailServerUsername\": \"test_mdm\",\n" +
            "        \"OutgoingMailServerPortNumber\": 587,\n" +
            "        \"OutgoingMailServerAuthentication\": \"EmailAuthPassword\",\n" +
            "        \"OutgoingMailServerUseSSL\": true\n" +
            "      }\n" +
            "    ],\n" +
            "    \"payloadVPNs\": [\n" +
            "      {\n" +
            "        \"PayloadType\": \"com.apple.vpn.managed\",\n" +
            "        \"PayloadVersion\": 1,\n" +
            "        \"PayloadIdentifier\": \"com.pekall.profile.VPN\",\n" +
            "        \"PayloadUUID\": \"5mp21i5l-8c68-m46w-f3fs-7jd6bt1i4dt2\",\n" +
            "        \"PayloadDisplayName\": \"VPN配置\",\n" +
            "        \"PayloadDescription\": \"VPN相关配置\",\n" +
            "        \"PayloadOrganization\": \"pekall Capital\",\n" +
            "        \"UserDefinedName\": \"test vpn\",\n" +
            "        \"Proxies\": {\n" +
            "          \"HTTPEnable\": 1,\n" +
            "          \"HTTPProxy\": \"192.168.10.230\",\n" +
            "          \"HTTPPort\": \"80\",\n" +
            "          \"HTTPProxyUsername\": \"wjl\",\n" +
            "          \"HTTPProxyPassword\": \"123\"\n" +
            "        },\n" +
            "        \"VPNType\": \"PPTP\",\n" +
            "        \"PPP\": {\n" +
            "          \"CommRemoteAddress\": \"test address\",\n" +
            "          \"AuthPassword\": \"123456\",\n" +
            "          \"AuthName\": \"my vpn\",\n" +
            "          \"CCPEnabled\": 1,\n" +
            "          \"CCPMPPE40Enabled\": 1,\n" +
            "          \"CCPMPPE128Enabled\": 1\n" +
            "        },\n" +
            "        \"OverridePrimary\": true\n" +
            "      }\n" +
            "    ],\n" +
            "    \"payloadWifiConfigs\": [\n" +
            "      {\n" +
            "        \"PayloadType\": \"com.apple.wifi.managed\",\n" +
            "        \"PayloadVersion\": 1,\n" +
            "        \"PayloadIdentifier\": \"com.pekall.config.wifi.managed\",\n" +
            "        \"PayloadUUID\": \"842fzzs4-tfmf-5q93-177d-ds5y57qvx5p4\",\n" +
            "        \"PayloadDisplayName\": \"WIFI配置\",\n" +
            "        \"PayloadDescription\": \"WIFI相关配置\",\n" +
            "        \"PayloadOrganization\": \"pekall Capital\",\n" +
            "        \"SSID_STR\": \"pekall_work\",\n" +
            "        \"AutoJoin\": true,\n" +
            "        \"EncryptionType\": \"WPA\",\n" +
            "        \"Password\": \"pekallcloud\",\n" +
            "        \"ProxyType\": \"None\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"payloadCalDAVPolicies\": [\n" +
            "      {\n" +
            "        \"PayloadType\": \"com.apple.caldav.account\",\n" +
            "        \"PayloadVersion\": 1,\n" +
            "        \"PayloadIdentifier\": \"com.apple.profile.caldav.account\",\n" +
            "        \"PayloadUUID\": \"4m5lgnjg-v55r-xa19-r745-1ryte17q7t62\",\n" +
            "        \"PayloadDisplayName\": \"cal dav配置\",\n" +
            "        \"PayloadDescription\": \"cal dav相关配置\",\n" +
            "        \"PayloadOrganization\": \"pekall Capital\",\n" +
            "        \"CalDAVAccountDescription\": \"My CarlDAV 账户\",\n" +
            "        \"CalDAVHostName\": \"google.com\",\n" +
            "        \"CalDAVPort\": \"8443\",\n" +
            "        \"CalDAVUsername\": \"pekalqq\",\n" +
            "        \"CalDAVPassword\": \"pekallqqq\",\n" +
            "        \"CalDAVUseSSL\": true\n" +
            "      }\n" +
            "    ],\n" +
            "    \"payloadCalSubscriptionPolicies\": [\n" +
            "      {\n" +
            "        \"PayloadType\": \"com.apple.subscribedcalendar.account\",\n" +
            "        \"PayloadVersion\": 1,\n" +
            "        \"PayloadIdentifier\": \"com.apple.profile.subscribedcalendar.account\",\n" +
            "        \"PayloadUUID\": \"csp72gfq-j995-zqiv-9ep9-34n22323heqs\",\n" +
            "        \"PayloadDisplayName\": \"cal subscription配置\",\n" +
            "        \"PayloadDescription\": \"cal subscription相关配置\",\n" +
            "        \"PayloadOrganization\": \"pekall Capital\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"payloadCardDAVPolicies\": [\n" +
            "      {\n" +
            "        \"PayloadType\": \"com.apple.carddav.account\",\n" +
            "        \"PayloadVersion\": 1,\n" +
            "        \"PayloadIdentifier\": \"com.apple.profile.carddav.account\",\n" +
            "        \"PayloadUUID\": \"11xi675a-3a51-v6u1-dg47-b36i57q18a75\",\n" +
            "        \"PayloadDisplayName\": \"card dav配置\",\n" +
            "        \"PayloadDescription\": \"card dav相关配置\",\n" +
            "        \"PayloadOrganization\": \"pekall Capital\",\n" +
            "        \"CardDAVUseSSL\": true\n" +
            "      }\n" +
            "    ],\n" +
            "    \"payloadAPN\": {\n" +
            "      \"PayloadType\": \"com.apple.apn.managed\",\n" +
            "      \"PayloadVersion\": 1,\n" +
            "      \"PayloadIdentifier\": \"com.apple.profile.apn.managed\",\n" +
            "      \"PayloadUUID\": \"8v394u34-clda-pf7l-2g38-8gnbd41938aa\",\n" +
            "      \"PayloadDisplayName\": \"APN配置\",\n" +
            "      \"PayloadDescription\": \"APN相关配置\",\n" +
            "      \"PayloadOrganization\": \"pekall Capital\",\n" +
            "      \"PayloadContent\": [\n" +
            "        {\n" +
            "          \"DefaultsDomainName\": \"com.apple.managedCarrier\",\n" +
            "          \"DefaultsData\": {\n" +
            "            \"apns\": [\n" +
            "              {\n" +
            "                \"apn\": \"cmwap\",\n" +
            "                \"username\": \"Ray\",\n" +
            "                \"password\": [\n" +
            "                  \"a\",\n" +
            "                  \"b\",\n" +
            "                  \"c\",\n" +
            "                  \"d\",\n" +
            "                  \"e\"\n" +
            "                ],\n" +
            "                \"proxy\": \"10.0.0.72\",\n" +
            "                \"proxyPort\": \"80\"\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
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

    public void testGenIosAppLockProfile() throws Exception {
        PayloadArrayWrapper profile = createIosApplockProfile();

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

    private PayloadAppLock createAppLock(){
        PayloadAppLock appLock = new PayloadAppLock();
        appLock.setPayloadDescription("APP Lock 配置");
        appLock.setPayloadDisplayName("AppLock配置");
        appLock.setPayloadIdentifier("com.pekall.profile.app.lock");
        appLock.setPayloadOrganization(ORGANIZATION);
        appLock.setPayloadUUID(getUUID());
        appLock.setPayloadVersion(1);

        AppDict appDict = new AppDict();
        appDict.setIdentifier("com.pekall.ios.mdm");
        appLock.setApp(appDict);

        return appLock;
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

        PayloadVPN payloadVPN = createIosVPN();

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

    private PayloadArrayWrapper createIosApplockProfile() {
        PayloadAppLock appLock = createAppLock();

//        PayloadPasswordPolicy passwordPolicy = createPasswordPolicy();
        PayloadArrayWrapper wrapper = createAppLockWrapper();
        wrapper.addPayLoadContent(appLock);

//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
    }


    private PayloadArrayWrapper createIosSettingProfile() {
        PayloadWifiConfig wifiConfig = createWifiConfig();
        PayloadEmail payloadEmail = createEmail();
        PayloadVPN payloadVPN = createIosVPN();
        PayloadCardDAVPolicy payloadCardDAVPolicy = createCardDAVPolicy();
        PayloadCalDAVPolicy payloadCalDAVPolicy = createPayloadCalDAVPolicy();
        PayloadCalSubscriptionPolicy payloadCalSubscriptionPolicy = createPayloadCalSubscriptionPolicy();

        PayloadAPN payloadAPN = createPayloadAPN();

//        PayloadPasswordPolicy passwordPolicy = createPasswordPolicy();
        PayloadArrayWrapper wrapper = createSettingWrapper();
        wrapper.addPayLoadContent(wifiConfig);
        wrapper.addPayLoadContent(payloadEmail);
        wrapper.addPayLoadContent(payloadVPN);
        wrapper.addPayLoadContent(payloadCardDAVPolicy);
        wrapper.addPayLoadContent(payloadCalDAVPolicy);
        wrapper.addPayLoadContent(payloadCalSubscriptionPolicy);
        wrapper.addPayLoadContent(payloadAPN);
//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
    }


    private PayloadLDAP createLDAP() {
        PayloadLDAP ldap = new PayloadLDAP();
        ldap.setPayloadDescription("LDAP相关配置");
        ldap.setPayloadDisplayName("LDAP配置");
        ldap.setPayloadIdentifier("com.pekall.profile.LDAP");
        ldap.setPayloadOrganization("Pekall Capital");
        ldap.setPayloadUUID(getUUID());
        ldap.setPayloadVersion(1);

        ldap.setLDAPAccountDescription("test ldap account");
        ldap.setLDAPAccountHostName("192.168.10.239");
        ldap.setLDAPAccountUserName("cn=cc,cn=users,dn=mdm,dn=com");
        ldap.setLDAPAccountPassword("Lccly123456");
        ldap.setLDAPAccountUseSSL(false);

        LDAPSearchSetting setting = new LDAPSearchSetting();
        setting.setLDAPSearchSettingDescription("我的搜索");
        setting.setLDAPSearchSettingScope(LDAPSearchSetting.SCOPE_SUBTREE);
        setting.setLDAPSearchSettingSearchBase("cn=users,dn=mdm,dn=com");
        ldap.addLDAPSearchSetting(setting);

//        setting = new LDAPSearchSetting();
//        setting.setLDAPSearchSettingDescription("dsfdsf");
//        setting.setLDAPSearchSettingScope(LDAPSearchSetting.SCOPE_BASE);
//        setting.setLDAPSearchSettingSearchBase("o=mu");
//        ldap.addLDAPSearchSetting(setting);

        return ldap;
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

    private PayloadAPN createPayloadAPN() {
        PayloadAPN policy = new PayloadAPN();
        policy.setPayloadDescription("APN相关配置");
        policy.setPayloadDisplayName("APN配置");
        policy.setPayloadIdentifier("com.apple.profile.apn.managed");
        policy.setPayloadOrganization(ORGANIZATION);
        policy.setPayloadUUID(getUUID());
        policy.setPayloadVersion(VERSION);


        APNDataArray apnDataArray = new APNDataArray();

        APNSDict apnData = new APNSDict();

//        byte[] bytes = new byte[5];
//        for (int i = 0; i < bytes.length; i++) {
//            bytes[i] = (byte) i;
//        }

        APNConfig config1 = new APNConfig();
        config1.setApn("cmnet");
        apnData.addApn(config1);

//        APNConfig config2 = new APNConfig();
//        config2.setApn("cmnet");
//        config2.setUsername("Jiang");
//        config2.setPassword(bytes);
//        config2.setProxy("10.0.0.83");
//        config2.setProxyPort(81);
//        apnData.addApn(config2);

        apnDataArray.setDefaultsData(apnData);
        policy.addPayloadContent(apnDataArray);
        return policy;
    }

    private PayloadCalSubscriptionPolicy createPayloadCalSubscriptionPolicy() {
        PayloadCalSubscriptionPolicy policy = new PayloadCalSubscriptionPolicy();
        policy.setPayloadDescription("cal subscription相关配置");
        policy.setPayloadDisplayName("cal subscription配置");
        policy.setPayloadIdentifier("com.apple.profile.subscribedcalendar.account");
        policy.setPayloadOrganization(ORGANIZATION);
        policy.setPayloadUUID(getUUID());
        policy.setPayloadVersion(VERSION);

        policy.setSubCalAccountDescription("test sub cal");
        policy.setSubCalAccountHostName("google.com");
        policy.setSubCalAccountUseSSL(true);
        policy.setSubCalAccountUsername("pekallqq");
        policy.setSubCalAccountPassword("pekallqqq");
        return policy;
    }


    private PayloadCalDAVPolicy createPayloadCalDAVPolicy() {
        PayloadCalDAVPolicy policy = new PayloadCalDAVPolicy();
        policy.setPayloadDescription("cal dav相关配置");
        policy.setPayloadDisplayName("cal dav配置");
        policy.setPayloadIdentifier("com.apple.profile.caldav.account");
        policy.setPayloadOrganization(ORGANIZATION);
        policy.setPayloadUUID(getUUID());
        policy.setPayloadVersion(VERSION);

        policy.setCalDAVAccountDescription("My CarlDAV 账户");
        policy.setCalDAVHostName("google.com");
        policy.setCalDAVPort(8443);
        policy.setCalDAVUseSSL(true);
        policy.setCalDAVUsername("pekallqq");
        policy.setCalDAVPassword("pekallqqq");
        return policy;
    }

    private PayloadCardDAVPolicy createCardDAVPolicy() {
        PayloadCardDAVPolicy policy = new PayloadCardDAVPolicy();
        policy.setPayloadDescription("card dav相关配置");
        policy.setPayloadDisplayName("card dav配置");
        policy.setPayloadIdentifier("com.apple.profile.carddav.account");
        policy.setPayloadOrganization(ORGANIZATION);
        policy.setPayloadUUID(getUUID());
        policy.setPayloadVersion(VERSION);

        policy.setCardDAVAccountDescription("My CardDAV 账户");
        policy.setCardDAVHostName("google.com");
        policy.setCardDAVPort(8843);
        policy.setCardDAVUseSSL(true);
        policy.setCardDAVUsername("pekallqq");
        policy.setCardDAVPassword("pekallqqq");
        return policy;
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

    private PayloadArrayWrapper createAppLockWrapper() {
        PayloadArrayWrapper wrapper = new PayloadArrayWrapper();
        wrapper.setPayloadDescription("配置文件");
        wrapper.setPayloadDisplayName("Pekall Applock Setting");
        wrapper.setPayloadIdentifier("com.pekall.profile.setting.applock");
        wrapper.setPayloadOrganization("Pekall Capital");
        wrapper.setPayloadType("Configuration");
        wrapper.setPayloadUUID(UUID.randomUUID().toString());
        wrapper.setPayloadVersion(1);
        wrapper.setPayloadRemovalDisallowed(true);
        return wrapper;
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

        PayloadLDAP payloadLDAP = createLDAP();
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(passwordPolicy);
        wrapper.addPayLoadContent(restrictionsPolicy);
        wrapper.addPayLoadContent(payloadExchange);
        wrapper.addPayLoadContent(payloadRomingPolicy);
        wrapper.addPayLoadContent(payloadLDAP);
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
        policy.setPayloadUUID(getUUID());
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
        policy.setForcePIN(false);
        policy.setMaxFailedAttempts(10);
//        policy.setMaxGracePeriod(-1);
        policy.setPinHistory(50);
        policy.setMaxPINAgeInDays(730);
        policy.setMinLength(4);
        policy.setMaxInactivity(1f);//自动锁定
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
        policy.setAllowVideoConferencing(true);
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
 
        vpn.setUserDefinedName("test vpn");
        vpn.setVPNType(PayloadVPN.TYPE_PPTP);

        IPv4Info iPv4Info = new IPv4Info();
        iPv4Info.setOverridePrimary(1);
        vpn.setIPv4(iPv4Info);

        PPPInfo pppInfo = new PPPInfo();
//        pppInfo.enableAuthEAPPlugins();
//        pppInfo.setTokenCard(true);
//        pppInfo.enableAuthProtocol();
        pppInfo.setAuthName("my vpn");
        pppInfo.setAuthPassword("123456");
        pppInfo.setCCPEnabled(1);
        pppInfo.setCCPMPPE128Enabled(1);
        pppInfo.setCCPMPPE40Enabled(1);
        pppInfo.setCommRemoteAddress("test address");
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

        VpnProxies proxies = new VpnProxies();
        proxies.setHTTPEnable(1);
        proxies.setHTTPProxy("192.168.10.230");
        proxies.setHTTPPort(80);
        proxies.setHTTPProxyPassword("123");
        proxies.setHTTPProxyUsername("wjl");
        vpn.setProxies(proxies);

//        vpn.setServerHostName("192.168.10.220");
//        vpn.setAccount("ray");
//        vpn.setPassword("123456");
//        vpn.setSharedPassword("qwert");
//        vpn.setUserAuth(PayloadVPN.USR_AUTH_PASSWORD);
//        vpn.setVpnForAllTraffic(true);
//        vpn.setProxyHost("192.168.0.1");
//        vpn.setProxyPort(889);
//        vpn.setProxyUserName("Ray");
//        vpn.setProxyPassword("qwert");

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
