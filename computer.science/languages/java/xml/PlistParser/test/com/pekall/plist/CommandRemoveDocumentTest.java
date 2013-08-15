package com.pekall.plist;

import com.pekall.plist.beans.CommandInstallDocument;
import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandRemoveDocument;
import junit.framework.TestCase;

public class CommandRemoveDocumentTest extends TestCase {

    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>51da6d87-8bd0-4c43-a7c3-377bdb260516</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>DocumentUUID</key>\n" +
            "\t\t<string>23456d87-xxd0-4ff3-axx3-gg7ssb2dd516</string>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>RemoveDocument</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CommandMsg msg = createCommandMsg();
        String xml = msg.toXml();

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        assertTrue(msg.getCommand() instanceof CommandRemoveDocument);
        PlistDebug.logTest(msg.toString());
        assertEquals(msg, createCommandMsg());
    }

    public void testTwoWay() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        assertEquals(msg.toXml(), TEST_XML);
    }

    private CommandMsg createCommandMsg() {
        CommandRemoveDocument cmd = new CommandRemoveDocument();
        cmd.setDocumentUUID("23456d87-xxd0-4ff3-axx3-gg7ssb2dd516");

        return new CommandMsg("51da6d87-8bd0-4c43-a7c3-377bdb260516", cmd);
    }
}
