package com.pekall.plist;

import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadExchange;
import junit.framework.TestCase;

public class PayloadExchangeTest extends TestCase {

    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>PayloadContent</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>EmailAddress</key>\n" +
            "\t\t\t<string>rui.jiang@pekall.com</string>\n" +
            "\t\t\t<key>CertificateName</key>\n" +
            "\t\t\t<string>test cert</string>\n" +
            "\t\t\t<key>CertificatePassword</key>\n" +
            "\t\t\t<data>\n" +
            "\t\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDEyMzQ1Njc4OTo7PD0+P0BBQkNERUZHSElKS0xNTk8=\n" +
            "\t\t\t</data>\n" +
            "\t\t\t<key>PreventAppSheet</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>PayloadCertificateUUID</key>\n" +
            "\t\t\t<string>ssss-xxx-xxdd-xx-dd-xx</string>\n" +
            "\t\t\t<key>disableMailRecentsSyncing</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t\t<key>MailNumberOfPastDaysToSync</key>\n" +
            "\t\t\t<integer>50</integer>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.eas.account</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.profile.exchange</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>Exchange配置</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>Exchange相关配置</string>\n" +
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
        assertEquals(parser.getExchange(), createExchange());
        assertTrue(ObjectComparator.equals(
                parser.getExchange(), createExchange()));
    }

    public void testTwoWay() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(TEST_XML);
        PayloadArrayWrapper profile = (PayloadArrayWrapper) parser.getPayloadDescriptor();

        assertEquals(TEST_XML, profile.toXml());
    }

    private PayloadArrayWrapper createProfile() {
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(createExchange());
        return wrapper;
    }

    private PayloadExchange createExchange() {
        PayloadExchange exchange = new PayloadExchange();
        exchange.setPayloadDescription("Exchange相关配置");
        exchange.setPayloadDisplayName("Exchange配置");
        exchange.setPayloadIdentifier("com.pekall.profile.exchange");
        exchange.setPayloadOrganization("Pekall Capital");
        exchange.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        exchange.setPayloadVersion(1);

        exchange.setCertificateName("test cert");
        exchange.setDisableMailRecentsSyncing(false);
        exchange.setEmailAddress("rui.jiang@pekall.com");
        exchange.setPreventAppSheet(true);
        exchange.setPayloadCertificateUUID("ssss-xxx-xxdd-xx-dd-xx");
        exchange.setMailNumberOfPastDaysToSync(50);
        byte[] data = new byte[80];
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) i;
        }
        exchange.setCertificatePassword(data);

        return exchange;
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
