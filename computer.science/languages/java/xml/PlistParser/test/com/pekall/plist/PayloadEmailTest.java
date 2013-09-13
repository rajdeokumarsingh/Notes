package com.pekall.plist;

import com.pekall.plist.beans.BeanBase;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadEmail;
import junit.framework.TestCase;

public class PayloadEmailTest extends TestCase {

    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>PayloadContent</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>EmailAccountDescription</key>\n" +
            "\t\t\t<string>test email account</string>\n" +
            "\t\t\t<key>EmailAccountName</key>\n" +
            "\t\t\t<string>Jiang Rui</string>\n" +
            "\t\t\t<key>EmailAccountType</key>\n" +
            "\t\t\t<string>EmailTypePOP</string>\n" +
            "\t\t\t<key>EmailAddress</key>\n" +
            "\t\t\t<string>rui.jiang@pekall.com</string>\n" +
            "\t\t\t<key>IncomingMailServerAuthentication</key>\n" +
            "\t\t\t<string>EmailAuthPassword</string>\n" +
            "\t\t\t<key>IncomingMailServerHostName</key>\n" +
            "\t\t\t<string>192.168.10.230</string>\n" +
            "\t\t\t<key>IncomingMailServerPortNumber</key>\n" +
            "\t\t\t<integer>8088</integer>\n" +
            "\t\t\t<key>IncomingMailServerUseSSL</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>IncomingPassword</key>\n" +
            "\t\t\t<string>123456</string>\n" +
            "\t\t\t<key>OutgoingPasswordSameAsIncomingPassword</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>OutgoingMailServerHostName</key>\n" +
            "\t\t\t<string>192.168.10.230</string>\n" +
            "\t\t\t<key>disableMailRecentsSyncing</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>defaultAccount</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>acceptAllCertForIncomingMail</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>acceptAllCertForOutgoingMail</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t\t<key>senderName</key>\n" +
            "\t\t\t<string>Ray</string>\n" +
            "\t\t\t<key>signature</key>\n" +
            "\t\t\t<string>BRs</string>\n" +
            "\t\t\t<key>vibrateOnNewEmail</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t\t<key>vibrateOnNewEmailIfSilent</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.mail.managed</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.profile.email</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>Email配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>Email相关配置</string>\n" +
            "\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t<string>Pekall Capital</string>\n" +
            "\t\t\t<key>PayloadStatus</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
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
        assertEquals(parser.getEmail(), createEmail());
    }

    private PayloadArrayWrapper createProfile() {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(createEmail());
        return wrapper;
    }

    private PayloadEmail createEmail() {
        PayloadEmail email = new PayloadEmail();
        email.setPayloadDescription("Email相关配置");
        email.setPayloadDisplayName("Email配置");
        email.setPayloadIdentifier("com.pekall.profile.email");
        email.setPayloadOrganization("Pekall Capital");
        email.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        email.setPayloadVersion(1);
        email.setPayloadStatus(1);

        email.setDisableMailRecentsSyncing(true);
        email.setEmailAccountDescription("test email account");
        email.setEmailAccountType(PayloadEmail.EMAIL_TYPE_POP);
        email.setEmailAccountName("Jiang Rui");
        email.setEmailAddress("rui.jiang@pekall.com");
        email.setIncomingMailServerHostName("192.168.10.230");
        email.setIncomingMailServerAuthentication(PayloadEmail.EMAIL_AUTH_PASSWORD);
        email.setIncomingMailServerPortNumber(8088);
        email.setIncomingMailServerUseSSL(true);
        email.setIncomingPassword("123456");
        email.setOutgoingMailServerHostName("192.168.10.230");
        email.setOutgoingPasswordSameAsIncomingPassword(true);
        email.setDefaultAccount(true);
        email.setAcceptAllCertForIncomingMail(true);
        email.setAcceptAllCertForOutgoingMail(false);
        email.setSenderName("Ray");
        email.setSignature("BRs");
        email.setVibrateOnNewEmail(false);
        email.setVibrateOnNewEmailIfSilent(true);
        return email;
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
