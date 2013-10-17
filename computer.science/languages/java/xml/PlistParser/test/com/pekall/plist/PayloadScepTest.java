package com.pekall.plist;


import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadScep;
import com.pekall.plist.beans.ScepContent;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class PayloadScepTest extends TestCase {

    public void testGenAppXml() throws Exception {
        PayloadBase settings = getPolicy();
        String xml = settings.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_APP_XML);
    }

    public void testParseAppXml() throws Exception {
        PayloadScep policy = PayloadScep.fromXmlT(
                TEST_APP_XML, PayloadScep.class);

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

        assertEquals(profile, createProfile());
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_SCEP),
                getPolicy());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_SCEP), getPolicy()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadBase getPolicy() {

        PayloadScep policy = new PayloadScep();
        policy.setPayloadDescription("配置“移动设备管理”");
        policy.setPayloadDisplayName("移动设备管理");
        policy.setPayloadIdentifier("com.pekall.profile.mdm");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadUUID("8DB93E58-49E5-448A-8697-052A9C28541D");
        policy.setPayloadVersion(1);

        ScepContent content = new ScepContent();
        content.setChallenge("388AA78F15B91B75D629B45FE1D584DE");
        content.setKeyType("RSA");
        content.setKeyUsage(5);
        content.setKeysize(1024);
        content.setName("PEKALL-CA");
        content.setRetries(2);

        List<List<List<String>>> subject = new ArrayList<List<List<String>>>();
        List<List<String>> list1 = new ArrayList<List<String>>();
        List<String> list11 = new ArrayList<String>();
        list11.add("O");
        list11.add("Pekall");
        list1.add(list11);
        subject.add(list1);
        List<List<String>> list2 = new ArrayList<List<String>>();
        List<String> list22 = new ArrayList<String>();
        list22.add("O");
        list22.add("Pekall");
        list2.add(list22);
        subject.add(list2);
        content.setSubject(subject);

        content.setURL("http://192.168.10.23:3337/rest/mdm/v1/ios/scep");

        policy.setPayloadContent(content);
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
            "\t\t\t<key>PayloadContent</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>Challenge</key>\n" +
            "\t\t\t\t<string>388AA78F15B91B75D629B45FE1D584DE</string>\n" +
            "\t\t\t\t<key>Key Type</key>\n" +
            "\t\t\t\t<string>RSA</string>\n" +
            "\t\t\t\t<key>Key Usage</key>\n" +
            "\t\t\t\t<integer>5</integer>\n" +
            "\t\t\t\t<key>Keysize</key>\n" +
            "\t\t\t\t<integer>1024</integer>\n" +
            "\t\t\t\t<key>Name</key>\n" +
            "\t\t\t\t<string>PEKALL-CA</string>\n" +
            "\t\t\t\t<key>Retries</key>\n" +
            "\t\t\t\t<integer>2</integer>\n" +
            "\t\t\t\t<key>Subject</key>\n" +
            "\t\t\t\t<array>\n" +
            "\t\t\t\t\t<array>\n" +
            "\t\t\t\t\t\t<array>\n" +
            "\t\t\t\t\t\t\t<string>O</string>\n" +
            "\t\t\t\t\t\t\t<string>Pekall</string>\n" +
            "\t\t\t\t\t\t</array>\n" +
            "\t\t\t\t\t</array>\n" +
            "\t\t\t\t\t<array>\n" +
            "\t\t\t\t\t\t<array>\n" +
            "\t\t\t\t\t\t\t<string>O</string>\n" +
            "\t\t\t\t\t\t\t<string>Pekall</string>\n" +
            "\t\t\t\t\t\t</array>\n" +
            "\t\t\t\t\t</array>\n" +
            "\t\t\t\t</array>\n" +
            "\t\t\t\t<key>URL</key>\n" +
            "\t\t\t\t<string>http://192.168.10.23:3337/rest/mdm/v1/ios/scep</string>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.security.scep</string>\n" +
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
            "\t<key>PayloadContent</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>Challenge</key>\n" +
            "\t\t<string>388AA78F15B91B75D629B45FE1D584DE</string>\n" +
            "\t\t<key>Key Type</key>\n" +
            "\t\t<string>RSA</string>\n" +
            "\t\t<key>Key Usage</key>\n" +
            "\t\t<integer>5</integer>\n" +
            "\t\t<key>Keysize</key>\n" +
            "\t\t<integer>1024</integer>\n" +
            "\t\t<key>Name</key>\n" +
            "\t\t<string>PEKALL-CA</string>\n" +
            "\t\t<key>Retries</key>\n" +
            "\t\t<integer>2</integer>\n" +
            "\t\t<key>Subject</key>\n" +
            "\t\t<array>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<array>\n" +
            "\t\t\t\t\t<string>O</string>\n" +
            "\t\t\t\t\t<string>Pekall</string>\n" +
            "\t\t\t\t</array>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<array>\n" +
            "\t\t\t\t\t<string>O</string>\n" +
            "\t\t\t\t\t<string>Pekall</string>\n" +
            "\t\t\t\t</array>\n" +
            "\t\t\t</array>\n" +
            "\t\t</array>\n" +
            "\t\t<key>URL</key>\n" +
            "\t\t<string>http://192.168.10.23:3337/rest/mdm/v1/ios/scep</string>\n" +
            "\t</dict>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>com.apple.security.scep</string>\n" +
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
