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

        /* todo:
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
        */

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

    private static final String TEST_XML = "";

    private static final String TEST_APP_XML = "";
}
