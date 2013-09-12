package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandInstalledAppListStatus;
import com.pekall.plist.beans.CommandStatusMsg;
import com.pekall.plist.beans.InstalledAppInfo;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/12/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandInstalledAppListStatusTest extends TestCase {
    public void testGenXml() throws Exception {

        CommandInstalledAppListStatus status = new CommandInstalledAppListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        status.addAppInfo(createAppInfo());
        status.addAppInfo(createAppInfo());
        status.addAppInfo(createAppInfo());
        String xml = status.toXml();

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testGenEmptyListXml() throws Exception {
        CommandInstalledAppListStatus status = new CommandInstalledAppListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, EMPTY_TEST_XML);
    }

    public void testParseMsg() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandInstalledAppListStatus status =
                (CommandInstalledAppListStatus) parser.getMessage();

        CommandInstalledAppListStatus status1 = new CommandInstalledAppListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        status1.addAppInfo(createAppInfo());
        status1.addAppInfo(createAppInfo());
        status1.addAppInfo(createAppInfo());

        assertEquals(status, status1);
    }

    public void testTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandInstalledAppListStatus status =
                (CommandInstalledAppListStatus) parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);
        PlistDebug.logTest(xml);

        assertEquals(xml, TEST_XML);
    }

    /* FIXME: can not detect type of the empty message
    public void testParseEmptyMsg() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(EMPTY_TEST_XML);
        CommandInstalledAppListStatus status =
                (CommandInstalledAppListStatus) parser.getMessage();

        CommandInstalledAppListStatus status1 = new CommandInstalledAppListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        assertEquals(status, status1);
    } */

    private InstalledAppInfo createAppInfo() {
        InstalledAppInfo appInfo = new InstalledAppInfo("test id",
                "test version", "tv", "pekall mdm test", 5000, 8000);
        appInfo.addAppPermission("android.permission.ACCESS_COARSE_LOCATION");
        appInfo.addAppPermission("android.permission.ACCESS_NETWORK_STATE");
        appInfo.addAppPermission("android.permission.WAKE_LOCK");

        return appInfo;
    }

    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>InstalledApplicationList</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Identifier</key>\n" +
            "\t\t\t<string>test id</string>\n" +
            "\t\t\t<key>Version</key>\n" +
            "\t\t\t<string>test version</string>\n" +
            "\t\t\t<key>ShortVersion</key>\n" +
            "\t\t\t<string>tv</string>\n" +
            "\t\t\t<key>Name</key>\n" +
            "\t\t\t<string>pekall mdm test</string>\n" +
            "\t\t\t<key>BundleSize</key>\n" +
            "\t\t\t<integer>5000</integer>\n" +
            "\t\t\t<key>DynamicSize</key>\n" +
            "\t\t\t<integer>8000</integer>\n" +
            "\t\t\t<key>AppPermissions</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<string>android.permission.ACCESS_COARSE_LOCATION</string>\n" +
            "\t\t\t\t<string>android.permission.ACCESS_NETWORK_STATE</string>\n" +
            "\t\t\t\t<string>android.permission.WAKE_LOCK</string>\n" +
            "\t\t\t</array>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Identifier</key>\n" +
            "\t\t\t<string>test id</string>\n" +
            "\t\t\t<key>Version</key>\n" +
            "\t\t\t<string>test version</string>\n" +
            "\t\t\t<key>ShortVersion</key>\n" +
            "\t\t\t<string>tv</string>\n" +
            "\t\t\t<key>Name</key>\n" +
            "\t\t\t<string>pekall mdm test</string>\n" +
            "\t\t\t<key>BundleSize</key>\n" +
            "\t\t\t<integer>5000</integer>\n" +
            "\t\t\t<key>DynamicSize</key>\n" +
            "\t\t\t<integer>8000</integer>\n" +
            "\t\t\t<key>AppPermissions</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<string>android.permission.ACCESS_COARSE_LOCATION</string>\n" +
            "\t\t\t\t<string>android.permission.ACCESS_NETWORK_STATE</string>\n" +
            "\t\t\t\t<string>android.permission.WAKE_LOCK</string>\n" +
            "\t\t\t</array>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Identifier</key>\n" +
            "\t\t\t<string>test id</string>\n" +
            "\t\t\t<key>Version</key>\n" +
            "\t\t\t<string>test version</string>\n" +
            "\t\t\t<key>ShortVersion</key>\n" +
            "\t\t\t<string>tv</string>\n" +
            "\t\t\t<key>Name</key>\n" +
            "\t\t\t<string>pekall mdm test</string>\n" +
            "\t\t\t<key>BundleSize</key>\n" +
            "\t\t\t<integer>5000</integer>\n" +
            "\t\t\t<key>DynamicSize</key>\n" +
            "\t\t\t<integer>8000</integer>\n" +
            "\t\t\t<key>AppPermissions</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<string>android.permission.ACCESS_COARSE_LOCATION</string>\n" +
            "\t\t\t\t<string>android.permission.ACCESS_NETWORK_STATE</string>\n" +
            "\t\t\t\t<string>android.permission.WAKE_LOCK</string>\n" +
            "\t\t\t</array>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>1ed5063d-7486-483a-a53b-44364b5191e1</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final String EMPTY_TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>1ed5063d-7486-483a-a53b-44364b5191e1</string>\n" +
            "</dict>\n" +
            "</plist>";


}
