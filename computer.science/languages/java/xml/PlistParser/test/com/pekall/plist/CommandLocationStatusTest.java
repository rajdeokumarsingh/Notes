package com.pekall.plist;

import com.pekall.plist.beans.CommandLocationStatus;
import com.pekall.plist.beans.CommandStatusMsg;
import junit.framework.TestCase;

public class CommandLocationStatusTest extends TestCase {
    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>Longitude</key>\n" +
            "\t<real>50.13</real>\n" +
            "\t<key>Latitude</key>\n" +
            "\t<real>40.12</real>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>2ade3a08-24fc-444d-b245-5a4ba8d0a3d1</string>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CommandLocationStatus status = (CommandLocationStatus) createCommandLocationStatus();

        String xml = status.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg statusMsg = parser.getMessage();

        PlistDebug.logTest(statusMsg.toString());
        assertEquals(statusMsg, createCommandLocationStatus());
    }

    public void testTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg statusMsg = parser.getMessage();

        assertEquals(statusMsg.toXml(), TEST_XML);
    }

    private CommandStatusMsg createCommandLocationStatus() {
        CommandLocationStatus status = new CommandLocationStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "2ade3a08-24fc-444d-b245-5a4ba8d0a3d1");
        status.setLatitude(40.12);
        status.setLongitude(50.13);
        return status;
    }
}
