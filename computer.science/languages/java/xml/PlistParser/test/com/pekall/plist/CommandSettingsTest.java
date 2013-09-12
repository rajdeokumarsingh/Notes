package com.pekall.plist;

import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandSettings;
import com.pekall.plist.beans.PhoneSetting;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/14/13
 * Time: 1:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandSettingsTest extends TestCase {
    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>51da6d87-8bd0-4c43-a7c3-377bdb260516</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>Settings</key>\n" +
            "\t\t<array>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>Item</key>\n" +
            "\t\t\t\t<string>VoiceRoaming</string>\n" +
            "\t\t\t\t<key>Enabled</key>\n" +
            "\t\t\t\t<true/>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>Item</key>\n" +
            "\t\t\t\t<string>DataRoaming</string>\n" +
            "\t\t\t\t<key>Enabled</key>\n" +
            "\t\t\t\t<false/>\n" +
            "\t\t\t</dict>\n" +
            "\t\t</array>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>Settings</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        String xml = createCommandMsg().toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();
        PlistDebug.logTest(msg.toString());

        assertEquals(msg, createCommandMsg());
    }

    public void testTwoWay() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        assertEquals(msg.toXml(), TEST_XML);
    }

    private CommandMsg createCommandMsg() {
        CommandSettings settings = new CommandSettings();
        settings.addSetting(new PhoneSetting(PhoneSetting.ITEM_VOICE_ROAMING, true));
        settings.addSetting(new PhoneSetting(PhoneSetting.ITEM_DATA_ROAMING, false));
        return new CommandMsg(
                "51da6d87-8bd0-4c43-a7c3-377bdb260516", settings);
    }
}
