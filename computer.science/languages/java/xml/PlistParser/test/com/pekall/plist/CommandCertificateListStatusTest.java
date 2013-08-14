package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CertificateItem;
import com.pekall.plist.beans.CommandCertificateListStatus;
import com.pekall.plist.beans.CommandStatusMsg;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/12/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandCertificateListStatusTest extends TestCase {
    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CertificateList</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>CommonName</key>\n" +
            "\t\t\t<string>test certificate item</string>\n" +
            "\t\t\t<key>IsIdentity</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t\t<key>Data</key>\n" +
            "\t\t\t<data>\n" +
            "\t\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t\t</data>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>CommonName</key>\n" +
            "\t\t\t<string>test certificate item</string>\n" +
            "\t\t\t<key>IsIdentity</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t\t<key>Data</key>\n" +
            "\t\t\t<data>\n" +
            "\t\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t\t</data>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>CommonName</key>\n" +
            "\t\t\t<string>test certificate item</string>\n" +
            "\t\t\t<key>IsIdentity</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t\t<key>Data</key>\n" +
            "\t\t\t<data>\n" +
            "\t\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t\t</data>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>1ed5063d-7486-483a-a53b-44364b5191e1</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final java.lang.String EMPTY_TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>1ed5063d-7486-483a-a53b-44364b5191e1</string>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {

        CommandCertificateListStatus status = new CommandCertificateListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        status.addCertItem(createCertItem());
        status.addCertItem(createCertItem());
        status.addCertItem(createCertItem());
        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testGenEmptyListXml() throws Exception {

        CommandCertificateListStatus status = new CommandCertificateListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, EMPTY_TEST_XML);
    }

    public void testParseMsg() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandCertificateListStatus status =
                (CommandCertificateListStatus) parser.getMessage();

        CommandCertificateListStatus status1 = new CommandCertificateListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        status1.addCertItem(createCertItem());
        status1.addCertItem(createCertItem());
        status1.addCertItem(createCertItem());

        assertEquals(status, status1);
    }

    public void testTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandCertificateListStatus status =
                (CommandCertificateListStatus) parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);
        PlistDebug.logTest(xml);

        assertEquals(xml, TEST_XML);
    }

    /* FIXME: can not detect type of the empty message
    public void testParseEmptyMsg() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(EMPTY_TEST_XML);
        CommandCertificateListStatus status =
                (CommandCertificateListStatus) parser.getMessage();

        CommandCertificateListStatus status1 = new CommandCertificateListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        assertEquals(status, status1);
    } */

    private CertificateItem createCertItem() {
        byte[] bytes = new byte[50];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }
        return new CertificateItem("test certificate item", false, bytes);
    }
}
