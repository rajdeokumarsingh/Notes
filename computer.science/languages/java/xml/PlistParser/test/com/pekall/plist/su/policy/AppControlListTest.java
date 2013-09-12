package com.pekall.plist.su.policy;

import com.pekall.plist.ObjectComparator;
import com.pekall.plist.PayloadXmlMsgParser;
import com.pekall.plist.PlistDebug;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import junit.framework.TestCase;

public class AppControlListTest extends TestCase {

    public void testGenAppXml() throws Exception {
        PayloadBase settings = getPolicy();
        String xml = settings.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_APP_XML);
    }

    public void testParseAppXml() throws Exception {
        AppControlList policy = AppControlList.fromXmlT(
                TEST_APP_XML, AppControlList.class);

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
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_APP_CONTROL_POLICY),
                getPolicy());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_APP_CONTROL_POLICY), getPolicy()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadBase getPolicy() {
        AppInfoWrapper must = new AppInfoWrapper("1");
        AppInfoWrapper white = new AppInfoWrapper("2");
        AppInfoWrapper black = new AppInfoWrapper();
        AppInfoWrapper grey = new AppInfoWrapper("4");
        must.addInfo(new AppInfo("test1", 0, "com.test.1", "1.3", "http://1.2.3/"));
        must.addInfo(new AppInfo("test11", 0, "com.test.11", "1.13", "http://1.2.3/"));
        white.addInfo(new AppInfo("test2", 1, "com.test.2", "1.4", "http://1.2.5/"));
        white.addInfo(new AppInfo("test22", 1, "com.test.22", "1.4", "http://1.2.5/"));
        black.addInfo(new AppInfo("test3", 2, "com.test.3", "1.5", "http://1.2.9/"));
        grey.addInfo(new AppInfo("test4", 3, "com.test.4", "1.6", "http://1.2.ap/"));

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
            "\t\t\t<key>mustInstall</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>infos</key>\n" +
            "\t\t\t\t<array>\n" +
            "\t\t\t\t\t<dict>\n" +
            "\t\t\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t\t\t<string>test1</string>\n" +
            "\t\t\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t\t\t<string>com.test.1</string>\n" +
            "\t\t\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t\t\t<string>1.3</string>\n" +
            "\t\t\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t\t\t<string>http://1.2.3/</string>\n" +
            "\t\t\t\t\t</dict>\n" +
            "\t\t\t\t\t<dict>\n" +
            "\t\t\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t\t\t<string>test11</string>\n" +
            "\t\t\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t\t\t<string>com.test.11</string>\n" +
            "\t\t\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t\t\t<string>1.13</string>\n" +
            "\t\t\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t\t\t<string>http://1.2.3/</string>\n" +
            "\t\t\t\t\t</dict>\n" +
            "\t\t\t\t</array>\n" +
            "\t\t\t\t<key>eventId</key>\n" +
            "\t\t\t\t<string>1</string>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<key>whiteList</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>infos</key>\n" +
            "\t\t\t\t<array>\n" +
            "\t\t\t\t\t<dict>\n" +
            "\t\t\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t\t\t<string>test2</string>\n" +
            "\t\t\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t\t\t<string>com.test.2</string>\n" +
            "\t\t\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t\t\t<string>1.4</string>\n" +
            "\t\t\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t\t\t<string>http://1.2.5/</string>\n" +
            "\t\t\t\t\t</dict>\n" +
            "\t\t\t\t\t<dict>\n" +
            "\t\t\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t\t\t<string>test22</string>\n" +
            "\t\t\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t\t\t<string>com.test.22</string>\n" +
            "\t\t\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t\t\t<string>1.4</string>\n" +
            "\t\t\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t\t\t<string>http://1.2.5/</string>\n" +
            "\t\t\t\t\t</dict>\n" +
            "\t\t\t\t</array>\n" +
            "\t\t\t\t<key>eventId</key>\n" +
            "\t\t\t\t<string>2</string>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<key>blackList</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>infos</key>\n" +
            "\t\t\t\t<array>\n" +
            "\t\t\t\t\t<dict>\n" +
            "\t\t\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t\t\t<string>test3</string>\n" +
            "\t\t\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t\t\t<integer>2</integer>\n" +
            "\t\t\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t\t\t<string>com.test.3</string>\n" +
            "\t\t\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t\t\t<string>1.5</string>\n" +
            "\t\t\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t\t\t<string>http://1.2.9/</string>\n" +
            "\t\t\t\t\t</dict>\n" +
            "\t\t\t\t</array>\n" +
            "\t\t\t\t<key>eventId</key>\n" +
            "\t\t\t\t<string></string>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<key>greyList</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>infos</key>\n" +
            "\t\t\t\t<array>\n" +
            "\t\t\t\t\t<dict>\n" +
            "\t\t\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t\t\t<string>test4</string>\n" +
            "\t\t\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t\t\t<integer>3</integer>\n" +
            "\t\t\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t\t\t<string>com.test.4</string>\n" +
            "\t\t\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t\t\t<string>1.6</string>\n" +
            "\t\t\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t\t\t<string>http://1.2.ap/</string>\n" +
            "\t\t\t\t\t</dict>\n" +
            "\t\t\t\t</array>\n" +
            "\t\t\t\t<key>eventId</key>\n" +
            "\t\t\t\t<string>4</string>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<key>name</key>\n" +
            "\t\t\t<string>App Control</string>\n" +
            "\t\t\t<key>status</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>defaultPolicy</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t\t<key>description</key>\n" +
            "\t\t\t<string>Default application control</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.pekall.app.control.policy</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.policy.app.control</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>App Control</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>App Control相关配置</string>\n" +
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
            "\t<key>mustInstall</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>infos</key>\n" +
            "\t\t<array>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t<string>test1</string>\n" +
            "\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t<string>com.test.1</string>\n" +
            "\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t<string>1.3</string>\n" +
            "\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t<string>http://1.2.3/</string>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t<string>test11</string>\n" +
            "\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t<integer>0</integer>\n" +
            "\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t<string>com.test.11</string>\n" +
            "\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t<string>1.13</string>\n" +
            "\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t<string>http://1.2.3/</string>\n" +
            "\t\t\t</dict>\n" +
            "\t\t</array>\n" +
            "\t\t<key>eventId</key>\n" +
            "\t\t<string>1</string>\n" +
            "\t</dict>\n" +
            "\t<key>whiteList</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>infos</key>\n" +
            "\t\t<array>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t<string>test2</string>\n" +
            "\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t<string>com.test.2</string>\n" +
            "\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t<string>1.4</string>\n" +
            "\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t<string>http://1.2.5/</string>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t<string>test22</string>\n" +
            "\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t<string>com.test.22</string>\n" +
            "\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t<string>1.4</string>\n" +
            "\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t<string>http://1.2.5/</string>\n" +
            "\t\t\t</dict>\n" +
            "\t\t</array>\n" +
            "\t\t<key>eventId</key>\n" +
            "\t\t<string>2</string>\n" +
            "\t</dict>\n" +
            "\t<key>blackList</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>infos</key>\n" +
            "\t\t<array>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t<string>test3</string>\n" +
            "\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t<integer>2</integer>\n" +
            "\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t<string>com.test.3</string>\n" +
            "\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t<string>1.5</string>\n" +
            "\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t<string>http://1.2.9/</string>\n" +
            "\t\t\t</dict>\n" +
            "\t\t</array>\n" +
            "\t\t<key>eventId</key>\n" +
            "\t\t<string></string>\n" +
            "\t</dict>\n" +
            "\t<key>greyList</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>infos</key>\n" +
            "\t\t<array>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>appName</key>\n" +
            "\t\t\t\t<string>test4</string>\n" +
            "\t\t\t\t<key>controlType</key>\n" +
            "\t\t\t\t<integer>3</integer>\n" +
            "\t\t\t\t<key>matchRule</key>\n" +
            "\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t<key>packageName</key>\n" +
            "\t\t\t\t<string>com.test.4</string>\n" +
            "\t\t\t\t<key>versionCode</key>\n" +
            "\t\t\t\t<string>1.6</string>\n" +
            "\t\t\t\t<key>downloadUrl</key>\n" +
            "\t\t\t\t<string>http://1.2.ap/</string>\n" +
            "\t\t\t</dict>\n" +
            "\t\t</array>\n" +
            "\t\t<key>eventId</key>\n" +
            "\t\t<string>4</string>\n" +
            "\t</dict>\n" +
            "\t<key>name</key>\n" +
            "\t<string>App Control</string>\n" +
            "\t<key>status</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>defaultPolicy</key>\n" +
            "\t<false/>\n" +
            "\t<key>description</key>\n" +
            "\t<string>Default application control</string>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>com.pekall.app.control.policy</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.policy.app.control</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>App Control</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>App Control相关配置</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";

}
