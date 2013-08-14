package com.pekall.plist;

import com.pekall.plist.beans.CommandError;
import com.pekall.plist.beans.CommandSettingsStatus;
import com.pekall.plist.beans.CommandStatusMsg;
import com.pekall.plist.beans.SettingResult;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/14/13
 * Time: 2:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandSettingsStatusTest extends TestCase {
    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>Settings</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Status</key>\n" +
            "\t\t\t<string>Acknowledged</string>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Status</key>\n" +
            "\t\t\t<string>Error</string>\n" +
            "\t\t\t<key>ErrorChain</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>LocalizedDescription</key>\n" +
            "\t\t\t\t\t<string>无法设置数据漫游</string>\n" +
            "\t\t\t\t\t<key>USEnglishDescription</key>\n" +
            "\t\t\t\t\t<string>can not set data roaming</string>\n" +
            "\t\t\t\t\t<key>ErrorDomain</key>\n" +
            "\t\t\t\t\t<string>Settings</string>\n" +
            "\t\t\t\t\t<key>ErrorCode</key>\n" +
            "\t\t\t\t\t<integer>50</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>LocalizedDescription</key>\n" +
            "\t\t\t\t\t<string>无法设置数据漫游</string>\n" +
            "\t\t\t\t\t<key>USEnglishDescription</key>\n" +
            "\t\t\t\t\t<string>can not set data roaming</string>\n" +
            "\t\t\t\t\t<key>ErrorDomain</key>\n" +
            "\t\t\t\t\t<string>Settings</string>\n" +
            "\t\t\t\t\t<key>ErrorCode</key>\n" +
            "\t\t\t\t\t<integer>51</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</array>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>2ade3a08-24fc-444d-b245-5a4ba8d0a3d1</string>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CommandSettingsStatus status = createCommandSettingsStatus();

        String xml = status.toXml();
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg statusMsg = parser.getMessage();

        PlistDebug.logTest(statusMsg.toString());
        assertTrue(statusMsg instanceof CommandSettingsStatus);
        assertEquals(statusMsg, createCommandSettingsStatus());
    }

    public void testTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg statusMsg = parser.getMessage();

        assertEquals(statusMsg.toXml(), TEST_XML);
    }

    private CommandSettingsStatus createCommandSettingsStatus() {
        CommandSettingsStatus status = new CommandSettingsStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "2ade3a08-24fc-444d-b245-5a4ba8d0a3d1");
        status.addSettingResult(createOkResult());
        status.addSettingResult(createErrorResult());
        return status;
    }

    private SettingResult createOkResult() {
        return new SettingResult(CommandStatusMsg.CMD_STAT_ACKNOWLEDGED, null);
    }

    private SettingResult createErrorResult() {
        CommandError error = new CommandError();
        error.setErrorCode(50);
        error.setErrorDomain("Settings");
        error.setUSEnglishDescription("can not set data roaming");
        error.setLocalizedDescription("无法设置数据漫游");

        CommandError error1 = new CommandError();
        error1.setErrorCode(51);
        error1.setErrorDomain("Settings");
        error1.setUSEnglishDescription("can not set data roaming");
        error1.setLocalizedDescription("无法设置数据漫游");

        SettingResult result = new SettingResult(CommandStatusMsg.CMD_STAT_ERROR, null);
        result.addError(error);
        result.addError(error1);
        return result;
    }

}
