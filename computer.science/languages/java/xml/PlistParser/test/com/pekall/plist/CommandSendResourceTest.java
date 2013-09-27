package com.pekall.plist;

import com.pekall.plist.beans.CommandSendResource;
import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandObject;
import junit.framework.TestCase;

public class CommandSendResourceTest extends TestCase {

    private CommandSendResource getCommand() {
        byte[] bytes = new byte[50];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }

        CommandSendResource command = new CommandSendResource();
        command.setResourceType(CommandSendResource.RES_TYPE_ADVERTISE);
        command.setIntent("play");
        command.setData(bytes);
        return command;
    }

    public void testGenXmlMsg() throws Exception {
        CommandMsg msg = new CommandMsg(
                "51da6d87-8bd0-4c43-a7c3-377bdb260516", getCommand());
        String xml = msg.toXml();

        PlistDebug.logTest(xml);
        assertEquals(xml, XML_MSG);
    }

    public void testParseXmlMsg() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(XML_MSG);
        CommandMsg msg = parser.getMessage();

        assertEquals(msg.getRequestType(), CommandObject.REQ_TYPE_SEND_RES);
        assertTrue(msg.getCommand() instanceof CommandSendResource);
        PlistDebug.logTest("msg: " + msg.toString());

        CommandMsg msg1 = new CommandMsg("51da6d87-8bd0-4c43-a7c3-377bdb260516", getCommand());
        assertEquals(msg, msg1);
    }

    public void testTwoWay() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(XML_MSG);
        CommandMsg msg = parser.getMessage();

        String xml = msg.toXml();

        PlistDebug.logTest("xml: " + xml);
        assertEquals(xml, XML_MSG);
    }

    private static final String XML_MSG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>51da6d87-8bd0-4c43-a7c3-377bdb260516</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>ResourceType</key>\n" +
            "\t\t<string>advertise</string>\n" +
            "\t\t<key>Intent</key>\n" +
            "\t\t<string>play</string>\n" +
            "\t\t<key>Data</key>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>SendRes</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";
}
