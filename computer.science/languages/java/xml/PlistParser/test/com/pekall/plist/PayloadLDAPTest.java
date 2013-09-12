package com.pekall.plist;

import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadLDAP;
import junit.framework.TestCase;

public class PayloadLDAPTest extends TestCase {

    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>PayloadContent</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>LDAPAccountDescription</key>\n" +
            "\t\t\t<string>test ldap account</string>\n" +
            "\t\t\t<key>LDAPAccountHostName</key>\n" +
            "\t\t\t<string>ldap.pekall.com</string>\n" +
            "\t\t\t<key>LDAPAccountUseSSL</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>LDAPAccountUserName</key>\n" +
            "\t\t\t<string>admin</string>\n" +
            "\t\t\t<key>LDAPAccountPassword</key>\n" +
            "\t\t\t<string>123456</string>\n" +
            "\t\t\t<key>LDAPSearchSettingScope</key>\n" +
            "\t\t\t<string>LDAPSearchSettingScopeBase</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.ldap.account</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.profile.LDAP</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>LDAP配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>LDAP相关配置</string>\n" +
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
        assertEquals(parser.getLDAP(), createLDAP());
        assertTrue(ObjectComparator.equals(
                parser.getLDAP(), createLDAP()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadArrayWrapper createProfile() {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(createLDAP());
        return wrapper;
    }

    private PayloadLDAP createLDAP() {
        PayloadLDAP ldap = new PayloadLDAP();
        ldap.setPayloadDescription("LDAP相关配置");
        ldap.setPayloadDisplayName("LDAP配置");
        ldap.setPayloadIdentifier("com.pekall.profile.LDAP");
        ldap.setPayloadOrganization("Pekall Capital");
        ldap.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        ldap.setPayloadVersion(1);

        ldap.setLDAPAccountDescription("test ldap account");
        ldap.setLDAPAccountHostName("ldap.pekall.com");
        ldap.setLDAPAccountUserName("admin");
        ldap.setLDAPAccountPassword("123456");
        ldap.setLDAPAccountUseSSL(true);
        ldap.setLDAPSearchSettingScope(PayloadLDAP.SCOPE_BASE);

        return ldap;
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
}
