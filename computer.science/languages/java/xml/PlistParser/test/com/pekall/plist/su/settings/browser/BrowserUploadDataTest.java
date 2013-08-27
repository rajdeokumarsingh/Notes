package com.pekall.plist.su.settings.browser;

import com.pekall.plist.CommandStatusMsgParser;
import com.pekall.plist.PlistDebug;
import com.pekall.plist.beans.CommandStatusMsg;
import com.pekall.plist.su.settings.browser.BrowserUploadData;
import com.pekall.plist.su.settings.browser.HistoryWatchItem;
import com.pekall.plist.su.settings.browser.UrlMatchRule;
import junit.framework.TestCase;

public class BrowserUploadDataTest extends TestCase {

    public void testGenAppXml() throws Exception {
        BrowserUploadData status = createStatusMsg();
        String xml = status.toXml();

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

   public void testParseAppXml() throws Exception {
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

    private BrowserUploadData createStatusMsg() {
        BrowserUploadData info = new BrowserUploadData(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");

        HistoryWatchItem item1 = new HistoryWatchItem("http://www.pekall.com");
        item1.setCount(1);
        item1.setDate(";13541345");

        HistoryWatchItem item2 = new HistoryWatchItem("http://www.baidu.com", UrlMatchRule.MATCH_TYPE_EQUAL);
        item2.setCount(2);
        item2.setDate(";13541345;3439284");

        info.addItem(item1);
        info.addItem(item2);
        info.addItem(new HistoryWatchItem());
        return info;
    }

    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>historyWatchItems</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>count</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>dates</key>\n" +
            "\t\t\t<string>;13541345</string>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>http://www.pekall.com</string>\n" +
            "\t\t\t<key>matchType</key>\n" +
            "\t\t\t<integer>0</integer>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>count</key>\n" +
            "\t\t\t<integer>2</integer>\n" +
            "\t\t\t<key>dates</key>\n" +
            "\t\t\t<string>;13541345;3439284</string>\n" +
            "\t\t\t<key>url</key>\n" +
            "\t\t\t<string>http://www.baidu.com</string>\n" +
            "\t\t\t<key>matchType</key>\n" +
            "\t\t\t<integer>3</integer>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>count</key>\n" +
            "\t\t\t<integer>0</integer>\n" +
            "\t\t\t<key>dates</key>\n" +
            "\t\t\t<string></string>\n" +
            "\t\t\t<key>matchType</key>\n" +
            "\t\t\t<integer>0</integer>\n" +
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
