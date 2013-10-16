package com.pekall.plist;


import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadCertPkcs12;
import junit.framework.TestCase;

public class PayloadCertPkcs12Test extends TestCase {

    public void testGenAppXml() throws Exception {
        PayloadBase settings = getPolicy();
        String xml = settings.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_APP_XML);
    }

    public void testParseAppXml() throws Exception {
        PayloadCertPkcs12 policy = PayloadCertPkcs12.fromXmlT(
                TEST_APP_XML, PayloadCertPkcs12.class);

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
        assertEquals(parser.getPayload(PayloadBase.PAYLOAD_TYPE_CERT_PKCS12),
                getPolicy());
        assertTrue(ObjectComparator.equals(
                parser.getPayload(PayloadBase.PAYLOAD_TYPE_CERT_PKCS12), getPolicy()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadBase getPolicy() {

        PayloadCertPkcs12 policy = new PayloadCertPkcs12();
        policy.setPayloadDescription("提供设备鉴定（证书或身份）。");
        policy.setPayloadDisplayName("Identity.p12");
        policy.setPayloadIdentifier("com.pekall.profile.ide.凭证");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadUUID("AAF39645-6E96-4B8C-90F5-29A55F357383");
        policy.setPayloadVersion(1);

        policy.setPassword("pekallsu");
        policy.setPayloadCertificateFileName("Identity.p12");
        byte[] data = new byte[50];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        policy.setPayloadContent(data);

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
            "\t\t\t<key>Password</key>\n" +
            "\t\t\t<string>pekallsu</string>\n" +
            "\t\t\t<key>PayloadCertificateFileName</key>\n" +
            "\t\t\t<string>Identity.p12</string>\n" +
            "\t\t\t<key>PayloadContent</key>\n" +
            "\t\t\t<data>\n" +
            "\t\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t\t</data>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.security.pkcs12</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.profile.ide.凭证</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>AAF39645-6E96-4B8C-90F5-29A55F357383</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>Identity.p12</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>提供设备鉴定（证书或身份）。</string>\n" +
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
            "\t<key>Password</key>\n" +
            "\t<string>pekallsu</string>\n" +
            "\t<key>PayloadCertificateFileName</key>\n" +
            "\t<string>Identity.p12</string>\n" +
            "\t<key>PayloadContent</key>\n" +
            "\t<data>\n" +
            "\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t</data>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>com.apple.security.pkcs12</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.profile.ide.凭证</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>AAF39645-6E96-4B8C-90F5-29A55F357383</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>Identity.p12</string>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>提供设备鉴定（证书或身份）。</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "</dict>\n" +
            "</plist>";
}
