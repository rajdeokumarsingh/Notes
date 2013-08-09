package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandInstallProfile;
import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandObject;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/9/13
 * Time: 9:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandInstallProfileMsgTest extends TestCase {
    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>27ec28ef-5001-44c8-8e3b-81f8dc0013f0</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>Payload</key>\n" +
            "\t\t<data>\n" +
            "\t\t\tAAECAwQFBgcICQoLDA0ODxAREhMUFRYXGBkaGxwdHh8gISIjJCUmJygpKissLS4vMDE=\n" +
            "\t\t</data>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>InstallProfile</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        byte[] bytes = new byte[50];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }
        CommandMsg msg = new CommandMsg(
                "27ec28ef-5001-44c8-8e3b-81f8dc0013f0",
                new CommandInstallProfile(bytes));
        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(dictionary);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testFromXml() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        assertTrue(msg.getCommand() instanceof CommandInstallProfile);
        assertEquals(msg.getRequestType(), CommandObject.REQ_TYPE_INST_PROF);

        byte[] bytes = new byte[50];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) i;
        }
        CommandMsg msg1 = new CommandMsg(
                "27ec28ef-5001-44c8-8e3b-81f8dc0013f0",
                new CommandInstallProfile(bytes));
        assertEquals(msg, msg1);
        assertEquals(msg.getCommand(), msg1.getCommand());
    }

    public void testTwoWay() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(dictionary);
        assertEquals(xml, TEST_XML);
    }
}
