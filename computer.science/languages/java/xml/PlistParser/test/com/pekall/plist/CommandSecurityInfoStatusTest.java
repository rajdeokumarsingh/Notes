package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandSecurityInfoStatus;
import com.pekall.plist.beans.CommandStatusMsg;
import com.pekall.plist.beans.SecInfo;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/12/13
 * Time: 3:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandSecurityInfoStatusTest extends TestCase {
    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>SecurityInfo</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>HardwareEncryptionCaps</key>\n" +
            "\t\t<integer>1</integer>\n" +
            "\t\t<key>PasscodePresent</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>PasscodeCompliant</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>PasscodeCompliantWithProfiles</key>\n" +
            "\t\t<false/>\n" +
            "\t</dict>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>9706c6e5-5c7b-4119-aec8-77b4e3214cf3</string>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CommandSecurityInfoStatus status = new CommandSecurityInfoStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "9706c6e5-5c7b-4119-aec8-77b4e3214cf3",
                new SecInfo(1, true, true, false));
        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg status = parser.getMessage();
        PlistDebug.logTest(status.toString());

        CommandSecurityInfoStatus status1 = new CommandSecurityInfoStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "9706c6e5-5c7b-4119-aec8-77b4e3214cf3",
                new SecInfo(1, true, true, false));
        assertEquals(status, status1);
    }

    public void testTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg status = parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        assertEquals(xml, TEST_XML);
    }
}
