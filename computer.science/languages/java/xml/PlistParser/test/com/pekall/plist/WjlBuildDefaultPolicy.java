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
            "            \"quality\": \"numeric\",\n" +
            "            \"PayloadType\": \"com.apple.mobiledevice.passwordpolicy\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.profile.passcodepolicy\",\n" +
            "            \"PayloadUUID\": \"c3dff07e-7865-4cfd-b5bd-bae15e699332\",\n" +
            "            \"PayloadDisplayName\": \"密码\",\n" +
            "            \"PayloadDescription\": \"配置与安全相关的项目。\",\n" +
            "            \"PayloadOrganization\": \"Pekall Capital\"\n" +
            "        },\n" +
            "        \"appControlList\": {\n" +
            "            \"mustInstall\": {\n" +
            "                \"infos\": [],\n" +
            "                \"eventId\": \"1\"\n" +
            "            },\n" +
            "            \"whiteList\": {\n" +
            "                \"infos\": [\n" +
            "                    {\n" +
            "                        \"appName\": \"QQ通讯录\",\n" +
            "                        \"controlType\": 1,\n" +
            "                        \"matchRule\": 1,\n" +
            "                        \"packageName\": \"com.tencent.qqphonebook\",\n" +
            "                        \"versionCode\": \"920\",\n" +
            "                        \"downloadUrl\": \"http://192.168.10.223/resources/download?uuid=d30b22ea659342a3be70631d7821aaee\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"appName\": \"Gmail\",\n" +
            "                        \"controlType\": 1,\n" +
            "                        \"matchRule\": 1,\n" +
            "                        \"packageName\": \"com.google.android.gm\",\n" +
            "                        \"versionCode\": \"176\",\n" +
            "                        \"downloadUrl\": \"http://192.168.10.223/resources/download?uuid=f8619d930b464d958f9f9ccf5afbe3d6\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"eventId\": \"2\"\n" +
            "            },\n" +
            "            \"blackList\": {\n" +
            "                \"infos\": [\n" +
            "                    {\n" +
            "                        \"appName\": \"UC浏览器\",\n" +
            "                        \"controlType\": 2,\n" +
            "                        \"matchRule\": 1,\n" +
            "                        \"packageName\": \"com.UCMobile\",\n" +
            "                        \"versionCode\": \"30\",\n" +
            "                        \"downloadUrl\": \"http://192.168.10.223/resources/download?uuid=81f95afadc154c64801b23b7b4889189\"\n" +
            "                    },\n" +
            "                    {\n" +
            "                        \"appName\": \"支付宝\",\n" +
            "                        \"controlType\": 2,\n" +
            "                        \"matchRule\": 1,\n" +
            "                        \"packageName\": \"com.eg.android.AlipayGphone\",\n" +
            "                        \"versionCode\": \"29\",\n" +
            "                        \"downloadUrl\": \"http://192.168.10.223/resources/download?uuid=bbbbd5ad28df4a39b414edf5fe680a72\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"eventId\": \"\"\n" +
            "            },\n" +
            "            \"greyList\": {\n" +
            "                \"infos\": [\n" +
            "                    {\n" +
            "                        \"appName\": \"QQ阅读 \",\n" +
            "                        \"controlType\": 3,\n" +
            "                        \"matchRule\": 1,\n" +
            "                        \"packageName\": \"com.qq.reader\",\n" +
            "                        \"versionCode\": \"24\",\n" +
            "                        \"downloadUrl\": \"http://192.168.10.223/resources/download?uuid=50b1296b415a4be6af4707c88bbb68c1\"\n" +
            "                    }\n" +
            "                ],\n" +
            "                \"eventId\": \"4\"\n" +
            "            },\n" +
            "            \"name\": \"App Control\",\n" +
            "            \"status\": 1,\n" +
            "            \"defaultPolicy\": false,\n" +
            "            \"description\": \"Default application control\",\n" +
            "            \"PayloadType\": \"com.pekall.app.control.policy\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.policy.app.control\",\n" +
            "            \"PayloadUUID\": \"3808D742-5D21-401E-B83C-AED1E990332D\",\n" +
            "            \"PayloadDisplayName\": \"App Control\",\n" +
            "            \"PayloadDescription\": \"App Control相关配置\",\n" +
            "            \"PayloadOrganization\": \"Pekall Capital\"\n" +
            "        },\n" +
            "        \"payloadSecurityPolicy\": {\n" +
            "            \"enforceEncryption\": false,\n" +
            "            \"enforceSDCardEncryption\": false,\n" +
            "            \"allowSDCardWrite\": true,\n" +
            "            \"disableKeyguardFeatures\": \"all feature\",\n" +
            "            \"allowNonGoogleApp\": \"user controlled\",\n" +
            "            \"enforceAppVerify\": \"user controlled\",\n" +
            "            \"allowScreenCapture\": true,\n" +
            "            \"allowClipboard\": true,\n" +
            "            \"backupMyData\": \"user controlled\",\n" +
            "            \"automaticRestore\": \"user controlled\",\n" +
            "            \"visiblePasswords\": \"user controlled\",\n" +
            "            \"allowUSBDebugging\": true,\n" +
            "            \"allowGoogleCrashReport\": true,\n" +
            "            \"allowFactoryReset\": true,\n" +
            "            \"allowOTAUpgrade\": true,\n" +
            "            \"PayloadType\": \"com.pekall.security.policy\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.policy.security\",\n" +
            "            \"PayloadUUID\": \"d19ff869-9bd4-4793-bdc1-b0eca2c759d4\",\n" +
            "            \"PayloadDisplayName\": \"Security配置\",\n" +
            "            \"PayloadDescription\": \"Security相关配置\",\n" +
            "            \"PayloadOrganization\": \"Pekall Captital\"\n" +
            "        },\n" +
            "        \"payloadRestrictionsAndroidPolicy\": {\n" +
            "            \"backgroundDataSync\": \"user controlled\",\n" +
            "            \"autoSync\": \"user controlled\",\n" +
            "            \"camera\": \"user controlled\",\n" +
            "            \"bluetooth\": \"user controlled\",\n" +
            "            \"allowUSBMassStorage\": \"user controlled\",\n" +
            "            \"allowUsbMediaPlayer\": true,\n" +
            "            \"useNetworkDateTime\": \"user controlled\",\n" +
            "            \"allowMicrophone\": \"user controlled\",\n" +
            "            \"allowNFC\": \"user controlled\",\n" +
            "            \"useWirelessNetworkForLocation\": \"user controlled\",\n" +
            "            \"useGPSForLocation\": \"user controlled\",\n" +
            "            \"useSensorAidingForLocation\": \"user controlled\",\n" +
            "            \"allowMockLocation\": \"user controlled\",\n" +
            "            \"PayloadType\": \"com.pekall.restrictions.policy\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.policy.restriction\",\n" +
            "            \"PayloadUUID\": \"a980cb44-bb44-4b94-ba64-ef913b1a2604\",\n" +
            "            \"PayloadDisplayName\": \"Restriction配置\",\n" +
            "            \"PayloadDescription\": \"Restriction相关配置\",\n" +
            "            \"PayloadOrganization\": \"Pekall Captital\"\n" +
            "        },\n" +
            "        \"payloadNativeAppCtrlPolicy\": {\n" +
            "            \"youtube\": false,\n" +
            "            \"browser\": true,\n" +
            "            \"settings\": true,\n" +
            "            \"gallery\": false,\n" +
            "            \"gmail\": true,\n" +
            "            \"googleMap\": true,\n" +
            "            \"voiceDialer\": true,\n" +
            "            \"PayloadType\": \"com.pekall.native.app.control.policy\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.policy.native.app\",\n" +
            "            \"PayloadUUID\": \"1f7bbc20-f0bd-47dd-9c99-3b158003d766\",\n" +
            "            \"PayloadDisplayName\": \"Native app配置\",\n" +
            "            \"PayloadDescription\": \"Native app相关配置\",\n" +
            "            \"PayloadOrganization\": \"Pekall Captital\"\n" +
            "        },\n" +
            "        \"payloadNetRestrictPolicy\": {\n" +
            "            \"allowEmgCallOnly\": true,\n" +
            "            \"allowWifi\": true,\n" +
            "            \"WhitelistedSSIDs\": [\n" +
            "                \"xx-xxxx-xx-xx-xx\"\n" +
            "            ],\n" +
            "            \"BlacklistedSSIDs\": [\n" +
            "                \"xxxx-xx-xx-xx\"\n" +
            "            ],\n" +
            "            \"allowDataNetwork\": \"user controlled\",\n" +
            "            \"mobileAP\": \"disable\",\n" +
            "            \"allowMessage\": \"enabled\",\n" +
            "            \"PayloadType\": \"com.pekall.network.restriction.policy\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.policy.network.restriction\",\n" +
            "            \"PayloadUUID\": \"b3b6b3e4-e865-47ef-a1ec-0e4c4d705115\",\n" +
            "            \"PayloadDisplayName\": \"Network Restriction配置\",\n" +
            "            \"PayloadDescription\": \"Network Restriction相关配置\",\n" +
            "            \"PayloadOrganization\": \"Pekall Captital\"\n" +
            "        },\n" +
            "        \"payloadBluetoothPolicy\": {\n" +
            "            \"allowDeviceDiscovery\": true,\n" +
            "            \"allowHeadset\": true,\n" +
            "            \"allowA2DP\": true,\n" +
            "            \"allowOutgoingCalls\": true,\n" +
            "            \"allowDataTransfer\": true,\n" +
            "            \"allow2Desktop\": false,\n" +
            "            \"PayloadType\": \"com.pekall.bluetooth.policy\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.policy.bluetooth\",\n" +
            "            \"PayloadUUID\": \"83f8b125-4334-46a7-adb6-a63b5d7df2cb\",\n" +
            "            \"PayloadDisplayName\": \"Bluetooth配置\",\n" +
            "            \"PayloadDescription\": \"Bluetooth相关配置\",\n" +
            "            \"PayloadOrganization\": \"Pekall Captital\"\n" +
            "        },\n" +
            "        \"payloadActiveSyncPolicy\": {\n" +
            "            \"hostName\": \"test host\",\n" +
            "            \"account\": \"ray\",\n" +
            "            \"displayName\": \"my active sync\",\n" +
            "            \"acceptAllCertificates\": true,\n" +
            "            \"configurePasscode\": true,\n" +
            "            \"allowBackup\": true,\n" +
            "            \"allowHtmlEmail\": true,\n" +
            "            \"allowAttachments\": false,\n" +
            "            \"emailSignature\": \"BRs\",\n" +
            "            \"policyKey\": \"xx-xx-xxxx-xx-x\",\n" +
            "            \"PayloadType\": \"com.pekall.network.active.sync.policy\",\n" +
            "            \"PayloadVersion\": 1,\n" +
            "            \"PayloadIdentifier\": \"com.pekall.policy.active.sync\",\n" +
            "            \"PayloadUUID\": \"3808D742-5D21-401E-B83C-AED1E990332D\",\n" +
            "            \"PayloadDisplayName\": \"Active Sync配置\",\n" +
            "            \"PayloadDescription\": \"Active Sync相关配置\",\n" +
            "            \"PayloadOrganization\": \"Pekall Capital\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"PayloadType\": \"Configuration\",\n" +
            "    \"PayloadVersion\": 1,\n" +
            "    \"PayloadIdentifier\": \"com.pekall.profile.policy\",\n" +
            "    \"PayloadUUID\": \"63b59cfe-3018-4b7f-bb32-abcfbbaa771f\",\n" +
            "    \"PayloadDisplayName\": \"Pekall Default Policy\",\n" +
            "    \"PayloadDescription\": \"策略文件\",\n" +
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
<<<<<<< HEAD
        exchange.setUserName("mdm\\bingxing.wang");
=======
        exchange.setUserName("mdm\\xiaoliang.li");
>>>>>>> 2f841b9362c2036e0ddd17b2069d6b5067d30899
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

        AdvertiseDownloadSettings advertiseDownloadSettings = getAdvertiseDownloadSettings();
        SystemSettings systemSettings = getSystemSettings();
        LauncherSettings launcherSettings = getLauncherSettings();
        BrowserSettings browserSettings = getBrowserSettings();


        wrapper.addPayLoadContent(wifiConfig);
<<<<<<< HEAD
        wrapper.addPayLoadContent(advertiseDownloadSettings);
        wrapper.addPayLoadContent(browserSettings);
        wrapper.addPayLoadContent(launcherSettings);
        wrapper.addPayLoadContent(systemSettings);
=======
        wrapper.addPayLoadContent(payloadEmail);
//        wrapper.addPayLoadContent(advertiseDownloadSettings);
//        wrapper.addPayLoadContent(browserSettings);
//        wrapper.addPayLoadContent(launcherSettings);
//        wrapper.addPayLoadContent(systemSettings);
>>>>>>> 2f841b9362c2036e0ddd17b2069d6b5067d30899



//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
    }


    private PayloadArrayWrapper createIosSettingProfile() {
        PayloadWifiConfig wifiConfig = createWifiConfig();
        PayloadEmail payloadEmail = createEmail();
<<<<<<< HEAD
=======
        PayloadVPN payloadVPN = createVPN();
>>>>>>> 2f841b9362c2036e0ddd17b2069d6b5067d30899
//        PayloadPasswordPolicy passwordPolicy = createPasswordPolicy();
        PayloadArrayWrapper wrapper = createSettingWrapper();
        wrapper.addPayLoadContent(wifiConfig);
        wrapper.addPayLoadContent(payloadEmail);
<<<<<<< HEAD
=======
        wrapper.addPayLoadContent(payloadVPN);
>>>>>>> 2f841b9362c2036e0ddd17b2069d6b5067d30899
//        wrapper.addPayLoadContent(wifiConfig);
        return wrapper;
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

        policy.setAllowDataNetwork(PayloadNetRestrictPolicy.CTRL_USR_CONTROLLED);
        policy.setAllowEmgCallOnly(true);
        policy.setAllowMessage(PayloadNetRestrictPolicy.CTRL_ENABLED);
        policy.addBlacklistedSSIDs("xxxx-xx-xx-xx");
        policy.addWhitelistedSSID("xx-xxxx-xx-xx-xx");
        policy.setAllowWifi(true);
        policy.setMobileAP(PayloadNetRestrictPolicy.CTRL_DISABLE);
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

        PayloadRemovalPassword removalPassword = createRemovalPassword();

        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(passwordPolicy);
        wrapper.addPayLoadContent(restrictionsPolicy);
        wrapper.addPayLoadContent(payloadExchange);
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

    private PayloadVPN createVPN() {
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
}
