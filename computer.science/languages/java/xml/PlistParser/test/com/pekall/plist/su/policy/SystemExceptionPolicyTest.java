package com.pekall.plist.su.policy;

import com.pekall.plist.ObjectComparator;
import com.pekall.plist.PayloadXmlMsgParser;
import com.pekall.plist.PlistDebug;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class SystemExceptionPolicyTest extends TestCase {

    public void testGenAppXml() throws Exception {
        PayloadBase settings = getPolicy();
        String xml = settings.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_APP_XML);
    }

    public void testParseAppXml() throws Exception {
        SystemExceptionPolicy policy = SystemExceptionPolicy.fromXmlT(
                TEST_APP_XML, SystemExceptionPolicy.class);

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
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_SYSTEM_EXCEPTION_POLICY),
                getPolicy());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_SYSTEM_EXCEPTION_POLICY), getPolicy()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadBase getPolicy() {
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
            "\t\t\t<key>exceptions</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>status</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t<key>type</key>\n" +
            "\t\t\t\t\t<string>memory</string>\n" +
            "\t\t\t\t\t<key>description</key>\n" +
            "\t\t\t\t\t<string>Internal/External Storage存储空间满</string>\n" +
            "\t\t\t\t\t<key>eventId</key>\n" +
            "\t\t\t\t\t<string>5</string>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>status</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t<key>type</key>\n" +
            "\t\t\t\t\t<string>application</string>\n" +
            "\t\t\t\t\t<key>description</key>\n" +
            "\t\t\t\t\t<string>关键应用出现异常</string>\n" +
            "\t\t\t\t\t<key>eventId</key>\n" +
            "\t\t\t\t\t<string>6</string>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>status</key>\n" +
            "\t\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t\t<key>type</key>\n" +
            "\t\t\t\t\t<string>configuration</string>\n" +
            "\t\t\t\t\t<key>description</key>\n" +
            "\t\t\t\t\t<string>配置被本地管理员修改</string>\n" +
            "\t\t\t\t\t<key>eventId</key>\n" +
            "\t\t\t\t\t<string>7</string>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>status</key>\n" +
            "\t\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t\t<key>type</key>\n" +
            "\t\t\t\t\t<string>application install</string>\n" +
            "\t\t\t\t\t<key>description</key>\n" +
            "\t\t\t\t\t<string>系统安装的应用与服务器下发的列表不符</string>\n" +
            "\t\t\t\t\t<key>eventId</key>\n" +
            "\t\t\t\t\t<string>8</string>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>name</key>\n" +
            "\t\t\t<string>test</string>\n" +
            "\t\t\t<key>status</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>defaultPolicy</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>description</key>\n" +
            "\t\t\t<string>test desc</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.pekall.system.exception.policy</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.policy.system.exception</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>System Exception</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>System Exception相关配置</string>\n" +
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
            "\t<key>exceptions</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>status</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>type</key>\n" +
            "\t\t\t<string>memory</string>\n" +
            "\t\t\t<key>description</key>\n" +
            "\t\t\t<string>Internal/External Storage存储空间满</string>\n" +
            "\t\t\t<key>eventId</key>\n" +
            "\t\t\t<string>5</string>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>status</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>type</key>\n" +
            "\t\t\t<string>application</string>\n" +
            "\t\t\t<key>description</key>\n" +
            "\t\t\t<string>关键应用出现异常</string>\n" +
            "\t\t\t<key>eventId</key>\n" +
            "\t\t\t<string>6</string>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>status</key>\n" +
            "\t\t\t<integer>0</integer>\n" +
            "\t\t\t<key>type</key>\n" +
            "\t\t\t<string>configuration</string>\n" +
            "\t\t\t<key>description</key>\n" +
            "\t\t\t<string>配置被本地管理员修改</string>\n" +
            "\t\t\t<key>eventId</key>\n" +
            "\t\t\t<string>7</string>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>status</key>\n" +
            "\t\t\t<integer>0</integer>\n" +
            "\t\t\t<key>type</key>\n" +
            "\t\t\t<string>application install</string>\n" +
            "\t\t\t<key>description</key>\n" +
            "\t\t\t<string>系统安装的应用与服务器下发的列表不符</string>\n" +
            "\t\t\t<key>eventId</key>\n" +
            "\t\t\t<string>8</string>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>name</key>\n" +
            "\t<string>test</string>\n" +
            "\t<key>status</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>defaultPolicy</key>\n" +
            "\t<true/>\n" +
            "\t<key>description</key>\n" +
            "\t<string>test desc</string>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>com.pekall.system.exception.policy</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.policy.system.exception</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>System Exception</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>System Exception相关配置</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";

}
