package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.PlistBeanConverter;
import com.pekall.plist.PlistDebug;
import com.pekall.plist.PlistXmlParser;
import com.pekall.plist.beans.CommandClearPasscode;
import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandObject;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/8/13
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandClearPasscodeTest extends TestCase {
    private static final String XML_MSG_WITH_NO_TOKEN = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>51da6d87-8bd0-4c43-a7c3-377bdb260516</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>ClearPasscode</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    private static final java.lang.String XML_MSG_WITH_TOKEN = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>51da6d87-8bd0-4c43-a7c3-377bdb260516</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>UnlockToken</key>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>ClearPasscode</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXmlMsgWithoutToken() throws Exception {
        CommandMsg msg = new CommandMsg(
                "51da6d87-8bd0-4c43-a7c3-377bdb260516", new CommandClearPasscode());
        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, XML_MSG_WITH_NO_TOKEN);
    }

    public void testGenXmlMsgWithToken() throws Exception {
        byte[] bytes = new byte[50];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)i;
        }
        CommandMsg msg = new CommandMsg(
                "51da6d87-8bd0-4c43-a7c3-377bdb260516",
                new CommandClearPasscode(bytes));
        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, XML_MSG_WITH_TOKEN);
    }

    public void testParseXmlMsgNoToken() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(XML_MSG_WITH_NO_TOKEN);
        CommandMsg msg = parser.getMessage();

        assertEquals(msg.getRequestType(), CommandObject.REQ_TYPE_CLEAR_PASSCODE);
        assertTrue(msg.getCommand() instanceof CommandClearPasscode);
        PlistDebug.logTest("msg: " + msg.toString());

        CommandMsg msg1 = new CommandMsg(
                "51da6d87-8bd0-4c43-a7c3-377bdb260516", new CommandClearPasscode());

        assertEquals(msg, msg1);
    }

    public void testParseXmlMsgWithToken() throws Exception {
        byte[] bytes = new byte[50];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)i;
        }
        CommandMsgParser parser = new CommandMsgParser(XML_MSG_WITH_TOKEN);
        CommandMsg msg = parser.getMessage();
        assertEquals(msg.getRequestType(), CommandObject.REQ_TYPE_CLEAR_PASSCODE);
        assertTrue(msg.getCommand() instanceof CommandClearPasscode);
        PlistDebug.logTest("msg: " + msg.toString());

        CommandMsg msg1 = new CommandMsg(
                "51da6d87-8bd0-4c43-a7c3-377bdb260516",
                new CommandClearPasscode(bytes));
        PlistDebug.logTest("msg1: " + msg.toString());

        assertEquals(msg, msg1);
    }

    public void testTwoWay() throws Exception {
        byte[] bytes = new byte[50];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)i;
        }
        CommandMsgParser parser = new CommandMsgParser(XML_MSG_WITH_TOKEN);
        CommandMsg msg = parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest("xml: " + xml);
        assertEquals(xml, XML_MSG_WITH_TOKEN);
    }
}
