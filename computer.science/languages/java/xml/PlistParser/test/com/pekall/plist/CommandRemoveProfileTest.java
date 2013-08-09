package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandRemoveProfile;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/9/13
 * Time: 10:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandRemoveProfileTest extends TestCase {
    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>1ed5063d-7486-483a-a53b-44364b5191e1</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>Identifier</key>\n" +
            "\t\t<string>com.pekall.profile.mdm</string>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>RemoveProfile</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CommandMsg msg = new CommandMsg("1ed5063d-7486-483a-a53b-44364b5191e1",
                new CommandRemoveProfile("com.pekall.profile.mdm"));
        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testFromXml() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        PlistDebug.logTest(msg.toString());
        assertTrue(msg.getCommand() instanceof CommandRemoveProfile);

        CommandMsg msg1 = new CommandMsg("1ed5063d-7486-483a-a53b-44364b5191e1",
                new CommandRemoveProfile("com.pekall.profile.mdm"));
        assertEquals(msg, msg1);
        assertEquals(msg.getCommand(), msg1.getCommand());
    }

    public void testTwoWay() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        assertEquals(xml, TEST_XML);
    }
}
