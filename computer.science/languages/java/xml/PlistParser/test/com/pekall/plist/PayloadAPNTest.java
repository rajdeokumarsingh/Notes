package com.pekall.plist;

import com.pekall.plist.beans.*;
import junit.framework.TestCase;

public class PayloadAPNTest extends TestCase {

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
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_APN), createPolicy());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_APN), createPolicy()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadArrayWrapper createProfile() {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(createPolicy());
        return wrapper;
    }

    private PayloadAPN createPolicy() {
        PayloadAPN policy = new PayloadAPN();
        policy.setPayloadDescription("APN相关配置");
        policy.setPayloadDisplayName("APN配置");
        policy.setPayloadIdentifier("com.apple.profile.apn.managed");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        policy.setPayloadVersion(1);

        APNDataArray apnDataArray = new APNDataArray();

        APNSDict apnData = new APNSDict();

        byte[] bytes = new byte[50];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }

        APNConfig config1 = new APNConfig();
        config1.setApn("cmwap");
        config1.setUsername("Ray");
        config1.setPassword(bytes);
        config1.setProxy("10.0.0.72");
        config1.setProxyPort(80);
        apnData.addApn(config1);

        APNConfig config2 = new APNConfig();
        config2.setApn("cmnet");
        config2.setUsername("Jiang");
        config2.setPassword(bytes);
        config2.setProxy("10.0.0.83");
        config2.setProxyPort(81);
        apnData.addApn(config2);

        apnDataArray.setDefaultsData(apnData);
        policy.addPayloadContent(apnDataArray);
        return policy;
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
            "\t\t\t<key>PayloadContent</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>DefaultsDomainName</key>\n" +
            "\t\t\t\t\t<string>com.apple.managedCarrier</string>\n" +
            "\t\t\t\t\t<key>DefaultsData</key>\n" +
            "\t\t\t\t\t<dict>\n" +
            "\t\t\t\t\t\t<key>apns</key>\n" +
            "\t\t\t\t\t\t<array>\n" +
            "\t\t\t\t\t\t\t<dict>\n" +
            "\t\t\t\t\t\t\t\t<key>apn</key>\n" +
            "\t\t\t\t\t\t\t\t<string>cmwap</string>\n" +
            "\t\t\t\t\t\t\t\t<key>username</key>\n" +
            "\t\t\t\t\t\t\t\t<string>Ray</string>\n" +
            "\t\t\t\t\t\t\t\t<key>password</key>\n" +
            "\t\t\t\t\t\t\t\t<data>\n" +
            "\t\t\t\t\t\t\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t\t\t\t\t\t\t</data>\n" +
            "\t\t\t\t\t\t\t\t<key>proxy</key>\n" +
            "\t\t\t\t\t\t\t\t<string>10.0.0.72</string>\n" +
            "\t\t\t\t\t\t\t\t<key>proxyPort</key>\n" +
            "\t\t\t\t\t\t\t\t<integer>80</integer>\n" +
            "\t\t\t\t\t\t\t</dict>\n" +
            "\t\t\t\t\t\t\t<dict>\n" +
            "\t\t\t\t\t\t\t\t<key>apn</key>\n" +
            "\t\t\t\t\t\t\t\t<string>cmnet</string>\n" +
            "\t\t\t\t\t\t\t\t<key>username</key>\n" +
            "\t\t\t\t\t\t\t\t<string>Jiang</string>\n" +
            "\t\t\t\t\t\t\t\t<key>password</key>\n" +
            "\t\t\t\t\t\t\t\t<data>\n" +
            "\t\t\t\t\t\t\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t\t\t\t\t\t\t</data>\n" +
            "\t\t\t\t\t\t\t\t<key>proxy</key>\n" +
            "\t\t\t\t\t\t\t\t<string>10.0.0.83</string>\n" +
            "\t\t\t\t\t\t\t\t<key>proxyPort</key>\n" +
            "\t\t\t\t\t\t\t\t<integer>81</integer>\n" +
            "\t\t\t\t\t\t\t</dict>\n" +
            "\t\t\t\t\t\t</array>\n" +
            "\t\t\t\t\t</dict>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.apn.managed</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.apple.profile.apn.managed</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>APN配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>APN相关配置</string>\n" +
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
