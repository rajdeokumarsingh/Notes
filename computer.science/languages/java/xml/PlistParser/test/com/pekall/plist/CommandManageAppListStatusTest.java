package com.pekall.plist;

import com.pekall.plist.beans.CommandManageAppListStatus;
import com.pekall.plist.beans.CommandStatusMsg;
import com.pekall.plist.beans.ManagedAppInfo;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/14/13
 * Time: 9:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandManageAppListStatusTest extends TestCase {

    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>ManagedApplicationList</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>test app id 1</key>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Status</key>\n" +
            "\t\t\t<string>Managed</string>\n" +
            "\t\t\t<key>ManagementFlags</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>UnusedRedemptionCode</key>\n" +
            "\t\t\t<string>1234567890</string>\n" +
            "\t\t</dict>\n" +
            "\t\t<key>test app id 2</key>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Status</key>\n" +
            "\t\t\t<string>ManagedButUninstalled</string>\n" +
            "\t\t\t<key>UnusedRedemptionCode</key>\n" +
            "\t\t\t<string>4452347890</string>\n" +
            "\t\t</dict>\n" +
            "\t\t<key>test app id 3</key>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Status</key>\n" +
            "\t\t\t<string>Failed</string>\n" +
            "\t\t</dict>\n" +
            "\t</dict>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>1ed5063d-7486-483a-a53b-44364b5191e1</string>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CommandManageAppListStatus status = createCommandManageAppListStatus();
        // use new api
        String xml = status.toXml();

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXml() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg statusMsg = parser.getMessage();

        PlistDebug.logTest(statusMsg.toString());
        assertEquals(statusMsg, createCommandManageAppListStatus());
    }

    public void testTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg statusMsg = parser.getMessage();

        assertEquals(statusMsg.toXml(), TEST_XML);
    }

    private CommandManageAppListStatus createCommandManageAppListStatus() {
        CommandManageAppListStatus status = new CommandManageAppListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        status.addAppInfo("test app id 1",
                new ManagedAppInfo(ManagedAppInfo.STATUS_MANAGED, 1, "1234567890"));
        status.addAppInfo("test app id 2",
                new ManagedAppInfo(ManagedAppInfo.STATUS_MANAGED_BUT_UNINSTALLED, null, "4452347890"));
        status.addAppInfo("test app id 3",
                new ManagedAppInfo(ManagedAppInfo.STATUS_FAILED, null, null));
        return status;
    }
}

