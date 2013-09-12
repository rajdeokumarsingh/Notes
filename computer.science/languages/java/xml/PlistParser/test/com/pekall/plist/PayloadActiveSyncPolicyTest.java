package com.pekall.plist;


import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadActiveSyncPolicy;
import junit.framework.TestCase;

public class PayloadActiveSyncPolicyTest extends TestCase {

    public void testGenAppXml() throws Exception {
        PayloadBase settings = getPolicy();
        String xml = settings.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_APP_XML);
    }

    public void testParseAppXml() throws Exception {
        PayloadActiveSyncPolicy policy = PayloadActiveSyncPolicy.fromXmlT(
                TEST_APP_XML, PayloadActiveSyncPolicy.class);

        assertEquals(policy, getPolicy());
        assertTrue(ObjectComparator.equals(policy, getPolicy()));
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
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_ACTIVE_SYNC_POLICY),
                getPolicy());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_ACTIVE_SYNC_POLICY), getPolicy()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadBase getPolicy() {

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
            "\t\t\t<key>hostName</key>\n" +
            "\t\t\t<string>test host</string>\n" +
            "\t\t\t<key>account</key>\n" +
            "\t\t\t<string>ray</string>\n" +
            "\t\t\t<key>displayName</key>\n" +
            "\t\t\t<string>my active sync</string>\n" +
            "\t\t\t<key>acceptAllCertificates</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>configurePasscode</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowBackup</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowHtmlEmail</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>allowAttachments</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t\t<key>emailSignature</key>\n" +
            "\t\t\t<string>BRs</string>\n" +
            "\t\t\t<key>policyKey</key>\n" +
            "\t\t\t<string>xx-xx-xxxx-xx-x</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.pekall.network.active.sync.policy</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.policy.active.sync</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>Active Sync配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>Active Sync相关配置</string>\n" +
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
            "\t<key>hostName</key>\n" +
            "\t<string>test host</string>\n" +
            "\t<key>account</key>\n" +
            "\t<string>ray</string>\n" +
            "\t<key>displayName</key>\n" +
            "\t<string>my active sync</string>\n" +
            "\t<key>acceptAllCertificates</key>\n" +
            "\t<true/>\n" +
            "\t<key>configurePasscode</key>\n" +
            "\t<true/>\n" +
            "\t<key>allowBackup</key>\n" +
            "\t<true/>\n" +
            "\t<key>allowHtmlEmail</key>\n" +
            "\t<true/>\n" +
            "\t<key>allowAttachments</key>\n" +
            "\t<false/>\n" +
            "\t<key>emailSignature</key>\n" +
            "\t<string>BRs</string>\n" +
            "\t<key>policyKey</key>\n" +
            "\t<string>xx-xx-xxxx-xx-x</string>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>com.pekall.network.active.sync.policy</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.policy.active.sync</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>Active Sync配置</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>Active Sync相关配置</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";
}
