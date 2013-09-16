package com.pekall.plist;

import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadPasswordPolicy;
import com.pekall.plist.beans.PayloadWifiConfig;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class PayloadMultiSameConfigTest extends TestCase {

    private static final String TEST_MULTI_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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
            "\t\t\t<string>test ssid 1</string>\n" +
            "\t\t\t<key>HIDDEN_NETWORK</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>AutoJoin</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>EncryptionType</key>\n" +
            "\t\t\t<string>WEP</string>\n" +
            "\t\t\t<key>Password</key>\n" +
            "\t\t\t<string>1234567890</string>\n" +
            "\t\t\t<key>ProxyType</key>\n" +
            "\t\t\t<string>Manual</string>\n" +
            "\t\t\t<key>ProxyServer</key>\n" +
            "\t\t\t<string>192.168.10.219</string>\n" +
            "\t\t\t<key>ProxyServerPort</key>\n" +
            "\t\t\t<integer>80</integer>\n" +
            "\t\t\t<key>ProxyUsername</key>\n" +
            "\t\t\t<string>jiangrui test</string>\n" +
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
            "\t\t\t<string>WIFI配置 1</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>WIFI相关配置 1</string>\n" +
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

    private static final String TEST_EMPTY_CONTENT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
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

    private static final String TEST_SINGLE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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

    public void testEmptyContent() throws Exception {
        PayloadArrayWrapper wrapper = createWrapper();
        String xml = wrapper.toXml();

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_EMPTY_CONTENT_XML);
    }

    public void testParseEmptyXml() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_EMPTY_CONTENT_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        PlistDebug.logTest(profile.toString());

        PayloadArrayWrapper wrapper = createWrapper();
        assertEquals(profile, wrapper);
        assertNull(wrapper.getPayloadContent());
    }

    public void testGenMultipleXml() throws Exception {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(createWifiConfig());
        wrapper.addPayLoadContent(createWifiConfig1());

        String xml = wrapper.toXml();

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_MULTI_XML);
    }

    public void testParseMultipleXml() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_MULTI_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        PlistDebug.logTest(profile.toString());

        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(createWifiConfig());
        wrapper.addPayLoadContent(createWifiConfig1());

        assertEquals(profile, wrapper);

        ArrayList<PayloadBase> payloads = (ArrayList<PayloadBase>)
                parser.getPayloads(PayloadBase.PAYLOAD_TYPE_WIFI_MANAGED);
        assertEquals(payloads.size(), 2);
        assertEquals(payloads.get(0).getClass(), PayloadWifiConfig.class);
        assertEquals(payloads.get(0), createWifiConfig());
        assertEquals(payloads.get(1).getClass(), PayloadWifiConfig.class);
        assertEquals(payloads.get(1), createWifiConfig1());
    }

    public void testGenSingleXml() throws Exception {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(createWifiConfig());

        String xml = wrapper.toXml();

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_SINGLE_XML);
    }

    public void testParseSingleXml() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_SINGLE_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        PlistDebug.logTest(profile.toString());

        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(createWifiConfig());

        assertEquals(profile, wrapper);
        assertEquals(parser.getWifiConfig(), createWifiConfig());

        List<PayloadBase> payloads =
                parser.getPayloads(PayloadBase.PAYLOAD_TYPE_WIFI_MANAGED);
        assertEquals(payloads.size(), 1);
        for (PayloadBase payload : payloads) {
            assertTrue(PayloadWifiConfig.class.equals(payload.getClass()));
            assertEquals(payload, createWifiConfig());
        }
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

    private PayloadWifiConfig createWifiConfig() {
        PayloadWifiConfig wifiConfig = new PayloadWifiConfig();

        wifiConfig.setPayloadDescription("WIFI相关配置");
        wifiConfig.setPayloadDisplayName("WIFI配置");
        wifiConfig.setPayloadIdentifier("com.pekall.config.wifi.managed");
        wifiConfig.setPayloadOrganization("Pekall Capital");
        wifiConfig.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        wifiConfig.setPayloadVersion(1);

        wifiConfig.setSSID_STR("test ssid");
        wifiConfig.setHIDDEN_NETWORK(true);
        wifiConfig.setAutoJoin(true);
        wifiConfig.setEncryptionType(PayloadWifiConfig.ENCRYPTION_TYPE_WEP);
        wifiConfig.setPassword("123456");
        wifiConfig.setProxyType(PayloadWifiConfig.PROXY_TYPE_MANUAL);
        wifiConfig.setProxyServer("192.168.10.210");
        wifiConfig.setProxyServerPort(80);
        wifiConfig.setProxyUsername("jiangrui");
        wifiConfig.setProxyPassword("123456");
        wifiConfig.setProxyPACURL("1.2.3.4");
        // TODO: add EAP
        // wifiConfig.setEAPClientConfiguration(new EAPClientConfigurationClass());
        return wifiConfig;
    }

    private PayloadWifiConfig createWifiConfig1() {
        PayloadWifiConfig wifiConfig = new PayloadWifiConfig();

        wifiConfig.setPayloadDescription("WIFI相关配置 1");
        wifiConfig.setPayloadDisplayName("WIFI配置 1");
        wifiConfig.setPayloadIdentifier("com.pekall.config.wifi.managed");
        wifiConfig.setPayloadOrganization("Pekall Capital");
        wifiConfig.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        wifiConfig.setPayloadVersion(1);

        wifiConfig.setSSID_STR("test ssid 1");
        wifiConfig.setHIDDEN_NETWORK(true);
        wifiConfig.setAutoJoin(true);
        wifiConfig.setEncryptionType(PayloadWifiConfig.ENCRYPTION_TYPE_WEP);
        wifiConfig.setPassword("1234567890");
        wifiConfig.setProxyType(PayloadWifiConfig.PROXY_TYPE_MANUAL);
        wifiConfig.setProxyServer("192.168.10.219");
        wifiConfig.setProxyServerPort(80);
        wifiConfig.setProxyUsername("jiangrui test");
        wifiConfig.setProxyPassword("123456");
        wifiConfig.setProxyPACURL("1.2.3.4");
        // TODO: add EAP
        // wifiConfig.setEAPClientConfiguration(new EAPClientConfigurationClass());
        return wifiConfig;
    }
}
