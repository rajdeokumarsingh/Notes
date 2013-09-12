package com.pekall.plist;

import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandObject;
import com.pekall.plist.beans.CommandRemoveApp;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/14/13
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandRemoveAppTest extends TestCase {
    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>27ec28ef-5001-44c8-8e3b-81f8dc0013f0</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>Identifier</key>\n" +
            "\t\t<string>test app id</string>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>RemoveApplication</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CommandMsg msg = new CommandMsg(
                "27ec28ef-5001-44c8-8e3b-81f8dc0013f0",
                new CommandRemoveApp("test app id"));
        String xml = msg.toXml();

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        assertTrue(msg.getCommand() instanceof CommandRemoveApp);
        assertEquals(msg.getRequestType(), CommandObject.REQ_TYPE_RM_APP);

        CommandMsg msg1 = new CommandMsg(
                "27ec28ef-5001-44c8-8e3b-81f8dc0013f0",
                new CommandRemoveApp("test app id"));
        assertEquals(msg, msg1);
    }

    public void testTwoWay() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        assertEquals(msg.toXml(), TEST_XML);
    }
}
