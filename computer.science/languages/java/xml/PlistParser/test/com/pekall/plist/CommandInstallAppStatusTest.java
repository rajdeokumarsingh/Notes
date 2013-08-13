package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandInstallAppStatus;
import com.pekall.plist.beans.CommandStatusMsg;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/13/13
 * Time: 4:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandInstallAppStatusTest extends TestCase {

    public void testGenOkXml() throws Exception {
        CommandInstallAppStatus status = new CommandInstallAppStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "2ade3a08-24fc-444d-b245-5a4ba8d0a3d1");
        status.setIdentifier("com.pekall.mdm.app");
        status.setState("OK");

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_OK_XML);
    }

    public void testGenErrorXml() throws Exception {
        CommandInstallAppStatus status = new CommandInstallAppStatus(
                CommandStatusMsg.CMD_STAT_ERROR,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "2ade3a08-24fc-444d-b245-5a4ba8d0a3d1");
        status.setRejectionReason(CommandInstallAppStatus.REJECT_REASON_NOT_SUPPORTED);

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_FAIL_XML);
    }

    public void testParseOkXml() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_OK_XML);
        CommandStatusMsg status = parser.getMessage();
        PlistDebug.logTest(status.toString());

        CommandInstallAppStatus status1 = new CommandInstallAppStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "2ade3a08-24fc-444d-b245-5a4ba8d0a3d1");
        status1.setIdentifier("com.pekall.mdm.app");
        status1.setState("OK");

        assertEquals(status, status1);
    }

    public void testParseFailedXml() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_FAIL_XML);
        CommandStatusMsg status = parser.getMessage();
        PlistDebug.logTest(status.toString());

        CommandInstallAppStatus status1 = new CommandInstallAppStatus(
                CommandStatusMsg.CMD_STAT_ERROR,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "2ade3a08-24fc-444d-b245-5a4ba8d0a3d1");
        status1.setRejectionReason(CommandInstallAppStatus.REJECT_REASON_NOT_SUPPORTED);

        assertEquals(status, status1);
    }

    public void testParseOkTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_OK_XML);
        CommandStatusMsg status = parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_OK_XML);
    }

    public void testParseFailedTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_FAIL_XML);
        CommandStatusMsg status = parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_FAIL_XML);
    }

    private static final java.lang.String TEST_OK_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>Identifier</key>\n" +
            "\t<string>com.pekall.mdm.app</string>\n" +
            "\t<key>State</key>\n" +
            "\t<string>OK</string>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>2ade3a08-24fc-444d-b245-5a4ba8d0a3d1</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final java.lang.String TEST_FAIL_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>RejectionReason</key>\n" +
            "\t<string>NotSupported</string>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Error</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>2ade3a08-24fc-444d-b245-5a4ba8d0a3d1</string>\n" +
            "</dict>\n" +
            "</plist>";
}
