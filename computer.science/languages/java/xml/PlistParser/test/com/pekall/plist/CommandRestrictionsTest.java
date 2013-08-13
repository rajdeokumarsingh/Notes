package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandObject;
import com.pekall.plist.beans.CommandRestrictions;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/13/13
 * Time: 9:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandRestrictionsTest extends TestCase {

    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>27ec28ef-5001-44c8-8e3b-81f8dc0013f0</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>ProfileRestrictions</key>\n" +
            "\t\t<true/>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>Restrictions</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CommandMsg msg = new CommandMsg(
                "27ec28ef-5001-44c8-8e3b-81f8dc0013f0",
                new CommandRestrictions(true));
        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(dictionary);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();
        assertTrue(msg.getCommand() instanceof CommandRestrictions);
        assertEquals(msg.getRequestType(), CommandObject.REQ_TYPE_RESTRICTIONS);

        CommandMsg msg1 = new CommandMsg(
                "27ec28ef-5001-44c8-8e3b-81f8dc0013f0",
                new CommandRestrictions(true));
        assertEquals(msg, msg1);
    }

    public void testTwoWay() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(dictionary);
        assertEquals(xml, TEST_XML);
    }
}
