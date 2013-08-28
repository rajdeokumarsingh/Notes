package com.pekall.plist.su.settings;

import com.pekall.plist.CommandStatusMsgParser;
import com.pekall.plist.PlistDebug;
import com.pekall.plist.beans.CommandStatusMsg;
import com.pekall.plist.su.settings.advertise.AdvertiseInfo;
import com.pekall.plist.su.settings.advertise.AdvertiseStaInfo;
import junit.framework.TestCase;

public class AdvertiseInfoTest extends TestCase {

    public void testGenXml() throws Exception {
        AdvertiseInfo status = createStatusMsg();
        String xml = status.toXml();

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

   public void testParseXml() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg statusMsg = parser.getMessage();

        PlistDebug.logTest(statusMsg.toString());
        assertEquals(statusMsg, createStatusMsg());
    }

    public void testTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg statusMsg = parser.getMessage();

        assertEquals(statusMsg.toXml(), TEST_XML);
    }

    private AdvertiseInfo createStatusMsg() {
        AdvertiseInfo info = new AdvertiseInfo(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");

        info.addItem(new AdvertiseStaInfo("test1", 1, 500l));
        info.addItem(new AdvertiseStaInfo());
        info.addItem(new AdvertiseStaInfo("test2", 10, 5000l));
        return info;
    }

    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>advertiseStaInfos</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>id</key>\n" +
            "\t\t\t<string>test1</string>\n" +
            "\t\t\t<key>count</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>totalDuration</key>\n" +
            "\t\t\t<integer>500</integer>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>id</key>\n" +
            "\t\t\t<string>test2</string>\n" +
            "\t\t\t<key>count</key>\n" +
            "\t\t\t<integer>10</integer>\n" +
            "\t\t\t<key>totalDuration</key>\n" +
            "\t\t\t<integer>5000</integer>\n" +
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
}
