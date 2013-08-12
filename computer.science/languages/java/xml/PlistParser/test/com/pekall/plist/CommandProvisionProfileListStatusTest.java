package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandProfileListStatus;
import com.pekall.plist.beans.CommandProvisionProfileListStatus;
import com.pekall.plist.beans.CommandStatusMsg;
import com.pekall.plist.beans.ProvisionProfileItem;
import junit.framework.TestCase;

import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/12/13
 * Time: 11:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandProvisionProfileListStatusTest extends TestCase {
    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>ProvisioningProfileList</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Name</key>\n" +
            "\t\t\t<string>test profile1</string>\n" +
            "\t\t\t<key>UUID</key>\n" +
            "\t\t\t<string>1ed5063d-7486-483a-a53b-44364b5191e1</string>\n" +
            "\t\t\t<key>ExpiryDate</key>\n" +
            "\t\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Name</key>\n" +
            "\t\t\t<string>test profile1</string>\n" +
            "\t\t\t<key>UUID</key>\n" +
            "\t\t\t<string>1ed5063d-7486-483a-a53b-44364b5191e1</string>\n" +
            "\t\t\t<key>ExpiryDate</key>\n" +
            "\t\t\t<date>2011-02-13T01:28:49Z</date>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>Name</key>\n" +
            "\t\t\t<string>test profile1</string>\n" +
            "\t\t\t<key>UUID</key>\n" +
            "\t\t\t<string>1ed5063d-7486-483a-a53b-44364b5191e1</string>\n" +
            "\t\t\t<key>ExpiryDate</key>\n" +
            "\t\t\t<date>2011-02-13T01:28:49Z</date>\n" +
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

    private static final java.lang.String EMPTY_TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
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

    public void testGenXml() throws Exception {

        CommandProvisionProfileListStatus status = new CommandProvisionProfileListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        status.addProfile(createProvisionProfileItem());
        status.addProfile(createProvisionProfileItem());
        status.addProfile(createProvisionProfileItem());
        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testGenEmptyListXml() throws Exception {

        CommandProvisionProfileListStatus status = new CommandProvisionProfileListStatus(
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
        CommandProvisionProfileListStatus status =
                (CommandProvisionProfileListStatus) parser.getMessage();

        CommandProvisionProfileListStatus status1 = new CommandProvisionProfileListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        status1.addProfile(createProvisionProfileItem());
        status1.addProfile(createProvisionProfileItem());
        status1.addProfile(createProvisionProfileItem());

        assertEquals(status, status1);
    }

    public void testTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandProvisionProfileListStatus status =
                (CommandProvisionProfileListStatus) parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);
        PlistDebug.logTest(xml);

        assertEquals(xml, TEST_XML);
    }

    /* FIXME: can not detect type of the empty message
    public void testParseEmptyMsg() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(EMPTY_TEST_XML);
        CommandProvisionProfileListStatus status =
                (CommandProvisionProfileListStatus) parser.getMessage();

        CommandProvisionProfileListStatus status1 = new CommandProvisionProfileListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        assertEquals(status, status1);
    } */

    private ProvisionProfileItem createProvisionProfileItem() {
        Calendar cal = Calendar.getInstance();
        cal.set(2011, 1, 13, 9, 28, 49);
        return new ProvisionProfileItem("test profile1",
                "1ed5063d-7486-483a-a53b-44364b5191e1", cal.getTime());
    }
}
