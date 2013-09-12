package com.pekall.plist.su.policy;

import com.pekall.plist.ObjectComparator;
import com.pekall.plist.PayloadXmlMsgParser;
import com.pekall.plist.PlistDebug;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import junit.framework.TestCase;

public class MemorySizePolicyTest extends TestCase {

    public void testGenAppXml() throws Exception {
        PayloadBase settings = getPolicy();
        String xml = settings.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_APP_XML);
    }

    public void testParseAppXml() throws Exception {
        MemorySizePolicy policy = MemorySizePolicy.fromXmlT(
                TEST_APP_XML, MemorySizePolicy.class);

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
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_MEMORY_POLICY),
                getPolicy());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_MEMORY_POLICY), getPolicy()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadBase getPolicy() {
        MemoryLimit memoryLimit = new MemoryLimit(80, "Notification");
        DiskLimit diskLimit = new DiskLimit(95, "Notification");
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
            "\t\t\t<key>memory</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>memoryLimit</key>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>maxRatio</key>\n" +
            "\t\t\t\t\t<integer>80</integer>\n" +
            "\t\t\t\t\t<key>eventId</key>\n" +
            "\t\t\t\t\t<string>Notification</string>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<key>diskLimit</key>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>maxRatio</key>\n" +
            "\t\t\t\t\t<integer>95</integer>\n" +
            "\t\t\t\t\t<key>eventId</key>\n" +
            "\t\t\t\t\t<string>Notification</string>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<key>name</key>\n" +
            "\t\t\t<string>Memory Policy</string>\n" +
            "\t\t\t<key>status</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>defaultPolicy</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t\t<key>description</key>\n" +
            "\t\t\t<string>Default Policy</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.pekall.memory.policy</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.policy.memory</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>Memory配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>Memory相关配置</string>\n" +
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
            "\t<key>memory</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>memoryLimit</key>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>maxRatio</key>\n" +
            "\t\t\t<integer>80</integer>\n" +
            "\t\t\t<key>eventId</key>\n" +
            "\t\t\t<string>Notification</string>\n" +
            "\t\t</dict>\n" +
            "\t\t<key>diskLimit</key>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>maxRatio</key>\n" +
            "\t\t\t<integer>95</integer>\n" +
            "\t\t\t<key>eventId</key>\n" +
            "\t\t\t<string>Notification</string>\n" +
            "\t\t</dict>\n" +
            "\t</dict>\n" +
            "\t<key>name</key>\n" +
            "\t<string>Memory Policy</string>\n" +
            "\t<key>status</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>defaultPolicy</key>\n" +
            "\t<false/>\n" +
            "\t<key>description</key>\n" +
            "\t<string>Default Policy</string>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>com.pekall.memory.policy</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.policy.memory</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>Memory配置</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>Memory相关配置</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";
}
