package com.pekall.plist;


import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadMdm;
import junit.framework.TestCase;

public class PayloadMdmTest extends TestCase {

    public void testGenAppXml() throws Exception {
        PayloadBase settings = getPolicy();
        String xml = settings.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_APP_XML);
    }

    public void testParseAppXml() throws Exception {
        PayloadMdm policy = PayloadMdm.fromXmlT(
                TEST_APP_XML, PayloadMdm.class);

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
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_MDM),
                getPolicy());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_MDM), getPolicy()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadBase getPolicy() {

        PayloadMdm policy = new PayloadMdm();
        policy.setPayloadDescription("配置“移动设备管理”");
        policy.setPayloadDisplayName("移动设备管理");
        policy.setPayloadIdentifier("com.pekall.profile.mdm");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadUUID("8DB93E58-49E5-448A-8697-052A9C28541D");
        policy.setPayloadVersion(1);

        policy.setAccessRights(8191);
        policy.setCheckInURL("https://192.168.10.23:3345/rest/mdm/v1/ios/checkin");
        policy.setCheckOutWhenRemoved(true);
        policy.setIdentityCertificateUUID("AAF39645-6E96-4B8C-90F5-29A55F357383");
        policy.setServerURL("https://192.168.10.23:3345/rest/mdm/v1/ios/server");
        policy.setSignMessage(true);
        policy.setTopic("com.apple.mgmt.External.807b8095-ae8b-4cbe-82df-126a58c4cd8c");
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
            "\t\t\t<key>AccessRights</key>\n" +
            "\t\t\t<integer>8191</integer>\n" +
            "\t\t\t<key>CheckInURL</key>\n" +
            "\t\t\t<string>https://192.168.10.23:3345/rest/mdm/v1/ios/checkin</string>\n" +
            "\t\t\t<key>CheckOutWhenRemoved</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>IdentityCertificateUUID</key>\n" +
            "\t\t\t<string>AAF39645-6E96-4B8C-90F5-29A55F357383</string>\n" +
            "\t\t\t<key>ServerURL</key>\n" +
            "\t\t\t<string>https://192.168.10.23:3345/rest/mdm/v1/ios/server</string>\n" +
            "\t\t\t<key>SignMessage</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>Topic</key>\n" +
            "\t\t\t<string>com.apple.mgmt.External.807b8095-ae8b-4cbe-82df-126a58c4cd8c</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.mdm</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.profile.mdm</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>8DB93E58-49E5-448A-8697-052A9C28541D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>移动设备管理</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>配置“移动设备管理”</string>\n" +
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
            "\t<key>AccessRights</key>\n" +
            "\t<integer>8191</integer>\n" +
            "\t<key>CheckInURL</key>\n" +
            "\t<string>https://192.168.10.23:3345/rest/mdm/v1/ios/checkin</string>\n" +
            "\t<key>CheckOutWhenRemoved</key>\n" +
            "\t<true/>\n" +
            "\t<key>IdentityCertificateUUID</key>\n" +
            "\t<string>AAF39645-6E96-4B8C-90F5-29A55F357383</string>\n" +
            "\t<key>ServerURL</key>\n" +
            "\t<string>https://192.168.10.23:3345/rest/mdm/v1/ios/server</string>\n" +
            "\t<key>SignMessage</key>\n" +
            "\t<true/>\n" +
            "\t<key>Topic</key>\n" +
            "\t<string>com.apple.mgmt.External.807b8095-ae8b-4cbe-82df-126a58c4cd8c</string>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>com.apple.mdm</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.profile.mdm</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>8DB93E58-49E5-448A-8697-052A9C28541D</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>移动设备管理</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>配置“移动设备管理”</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";
}
