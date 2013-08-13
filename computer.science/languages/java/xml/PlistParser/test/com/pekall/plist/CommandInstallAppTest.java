package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandInstallApp;
import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandObject;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/13/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandInstallAppTest extends TestCase {
    public void testGenXml() throws Exception {
        CommandMsg msg = new CommandMsg(
                "aa483d15-168d-4022-b69f-dac292096176", createCommandInstallApplication());
        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testGenPartialXml() throws Exception {
        CommandMsg msg = new CommandMsg(
                "aa483d15-168d-4022-b69f-dac292096176", createPartialCommand());
        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_PART_XML);
    }

    public void testGenEmptyXml() throws Exception {
        CommandMsg msg = new CommandMsg(
                "aa483d15-168d-4022-b69f-dac292096176", new CommandInstallApp());
        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_EMPTY_XML);
    }

    public void testParseXml() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        assertTrue(msg.getCommand() instanceof CommandInstallApp);
        assertEquals(msg.getRequestType(), CommandObject.REQ_TYPE_INST_APP);

        CommandMsg msg1 = new CommandMsg(
                "aa483d15-168d-4022-b69f-dac292096176", createCommandInstallApplication());
        assertEquals(msg, msg1);
    }

    public void testTwoWay() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_PART_XML);
        CommandMsg msg = parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_PART_XML);
    }

    private CommandInstallApp createCommandInstallApplication() {
        CommandInstallApp cmd = new CommandInstallApp();
        cmd.setITunesStoreID(Long.valueOf(361285480));
        cmd.setManifestURL("http://itunes.apple.com/us/app/keynote/id361285480?mt=8");
        cmd.setManagementFlags(Integer.valueOf(1));
        return cmd;
    }

    private CommandInstallApp createPartialCommand() {
        CommandInstallApp cmd = new CommandInstallApp();
        cmd.setManifestURL("http://itunes.apple.com/us/app/keynote/id361285480?mt=8");
        return cmd;
    }
    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>aa483d15-168d-4022-b69f-dac292096176</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>iTunesStoreID</key>\n" +
            "\t\t<integer>361285480</integer>\n" +
            "\t\t<key>ManifestURL</key>\n" +
            "\t\t<string>http://itunes.apple.com/us/app/keynote/id361285480?mt=8</string>\n" +
            "\t\t<key>ManagementFlags</key>\n" +
            "\t\t<integer>1</integer>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>InstallApplication</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    private static final java.lang.String TEST_EMPTY_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>aa483d15-168d-4022-b69f-dac292096176</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>InstallApplication</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    private static final java.lang.String TEST_PART_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>aa483d15-168d-4022-b69f-dac292096176</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>ManifestURL</key>\n" +
            "\t\t<string>http://itunes.apple.com/us/app/keynote/id361285480?mt=8</string>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>InstallApplication</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";
}
