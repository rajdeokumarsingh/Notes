package com.pekall.plist;

import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadNetRestrictPolicy;
import junit.framework.TestCase;

public class PayloadNetRestrictionPolicyTest extends TestCase {

    public void testGenAppXml() throws Exception {
        PayloadBase settings = getPolicy();
        String xml = settings.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_APP_XML);
    }

    public void testParseAppXml() throws Exception {
        PayloadNetRestrictPolicy policy = PayloadNetRestrictPolicy.fromXmlT(
                TEST_APP_XML, PayloadNetRestrictPolicy.class);

        assertEquals(policy, getPolicy());
        // assertTrue(ObjectComparator.equals(policy, getPolicy()));
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

        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_NET_RESTRICT_POLICY),
                getPolicy());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_NET_RESTRICT_POLICY), getPolicy()));
        assertEquals(profile, createProfile());
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadBase getPolicy() {

        PayloadNetRestrictPolicy policy = new PayloadNetRestrictPolicy();
        policy.setPayloadDescription("Network Restriction相关配置");
        policy.setPayloadDisplayName("Network Restriction配置");
        policy.setPayloadIdentifier("com.pekall.policy.network.restriction");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        policy.setPayloadVersion(1);

        policy.setAllowDataNetwork(PayloadNetRestrictPolicy.CTRL_USR_CONTROLLED);
        policy.setAllowEmgCallOnly(true);
        policy.setAllowMessage(PayloadNetRestrictPolicy.CTRL_ENABLED);
        policy.addBlacklistedSSIDs("xxxx-xx-xx-xx");
        policy.addWhitelistedSSID("xx-xxxx-xx-xx-xx");
        policy.setAllowWifi(true);
        policy.setMobileAP(PayloadNetRestrictPolicy.CTRL_DISABLE);
        return policy;
    }

    private PayloadArrayWrapper createProfile() {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(getPolicy());
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
            "\t\t\t<key>allowEmgCallOnly</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowWifi</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>WhitelistedSSIDs</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<string>xx-xxxx-xx-xx-xx</string>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>BlacklistedSSIDs</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<string>xxxx-xx-xx-xx</string>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>allowDataNetwork</key>\n" +
            "\t\t\t<string>user controlled</string>\n" +
            "\t\t\t<key>mobileAP</key>\n" +
            "\t\t\t<string>disable</string>\n" +
            "\t\t\t<key>allowMessage</key>\n" +
            "\t\t\t<string>enabled</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.pekall.network.restriction.policy</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.policy.network.restriction</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>Network Restriction配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>Network Restriction相关配置</string>\n" +
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

    private static final String TEST_APP_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>allowEmgCallOnly</key>\n" +
            "\t<true/>\n" +
            "\t<key>allowWifi</key>\n" +
            "\t<true/>\n" +
            "\t<key>WhitelistedSSIDs</key>\n" +
            "\t<array>\n" +
            "\t\t<string>xx-xxxx-xx-xx-xx</string>\n" +
            "\t</array>\n" +
            "\t<key>BlacklistedSSIDs</key>\n" +
            "\t<array>\n" +
            "\t\t<string>xxxx-xx-xx-xx</string>\n" +
            "\t</array>\n" +
            "\t<key>allowDataNetwork</key>\n" +
            "\t<string>user controlled</string>\n" +
            "\t<key>mobileAP</key>\n" +
            "\t<string>disable</string>\n" +
            "\t<key>allowMessage</key>\n" +
            "\t<string>enabled</string>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>com.pekall.network.restriction.policy</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.policy.network.restriction</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>Network Restriction配置</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>Network Restriction相关配置</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";

}
