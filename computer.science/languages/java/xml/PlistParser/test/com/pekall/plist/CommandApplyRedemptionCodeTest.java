package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandApplyRedemptionCode;
import com.pekall.plist.beans.CommandMsg;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/13/13
 * Time: 5:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandApplyRedemptionCodeTest extends TestCase {

    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>aa483d15-168d-4022-b69f-dac292096176</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>Identifier</key>\n" +
            "\t\t<string>com.pekall.mdm.app</string>\n" +
            "\t\t<key>RedemptionCode</key>\n" +
            "\t\t<string>1234567890</string>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>ApplyRedemptionCode</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CommandMsg msg = new CommandMsg(
                "aa483d15-168d-4022-b69f-dac292096176", createCommandApplyRedemptionCode());
        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    private CommandApplyRedemptionCode createCommandApplyRedemptionCode() {
        CommandApplyRedemptionCode cmd = new CommandApplyRedemptionCode();
        cmd.setIdentifier("com.pekall.mdm.app");
        cmd.setRedemptionCode("1234567890");
        return cmd;
    }

    public void testParseXml() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        CommandMsg msg1 = new CommandMsg(
                "aa483d15-168d-4022-b69f-dac292096176", createCommandApplyRedemptionCode());
        assertEquals(msg, msg1);
    }

    public void testTwoWay() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        assertEquals(xml, TEST_XML);
    }
}
