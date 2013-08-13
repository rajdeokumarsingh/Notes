package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.*;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/13/13
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandRestrictionsStatusTest extends TestCase {

    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>GlobalRestrictions</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>restrictedBool</key>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>forcePIN</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>value</key>\n" +
            "\t\t\t\t<true/>\n" +
            "\t\t\t</dict>\n" +
            "\t\t</dict>\n" +
            "\t\t<key>restrictedValue</key>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>minLength</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>value</key>\n" +
            "\t\t\t\t<integer>9</integer>\n" +
            "\t\t\t</dict>\n" +
            "\t\t</dict>\n" +
            "\t</dict>\n" +
            "\t<key>ProfileRestrictions</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>com.pekall.mdm.password.profile</key>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>restrictedBool</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>allowSimple</key>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>value</key>\n" +
            "\t\t\t\t\t<true/>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t\t<key>forcePIN</key>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>value</key>\n" +
            "\t\t\t\t\t<true/>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</dict>\n" +
            "\t\t\t<key>restrictedValue</key>\n" +
            "\t\t\t<dict>\n" +
            "\t\t\t\t<key>minLength</key>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>value</key>\n" +
            "\t\t\t\t\t<integer>9</integer>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</dict>\n" +
            "\t\t</dict>\n" +
            "\t</dict>\n" +
            "\t<key>Status</key>\n" +
            "\t<string>Acknowledged</string>\n" +
            "\t<key>UDID</key>\n" +
            "\t<string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>2ade3a08-24fc-444d-b245-5a4ba8d0a3d1</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final String REAL_MSG_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "    <dict>\n" +
            "        <key>CommandUUID</key>\n" +
            "        <string>2ade3a08-24fc-444d-b245-5a4ba8d0a3d1</string>\n" +
            "        <key>GlobalRestrictions</key>\n" +
            "        <dict>\n" +
            "            <key>restrictedBool</key>\n" +
            "            <dict>\n" +
            "                <key>forcePIN</key>\n" +
            "                <dict>\n" +
            "                    <key>value</key>\n" +
            "                    <true/>\n" +
            "                </dict>\n" +
            "            </dict>\n" +
            "            <key>restrictedValue</key>\n" +
            "            <dict>\n" +
            "                <key>minLength</key>\n" +
            "                <dict>\n" +
            "                    <key>value</key>\n" +
            "                    <integer>9</integer>\n" +
            "                </dict>\n" +
            "            </dict>\n" +
            "        </dict>\n" +
            "        <key>ProfileRestrictions</key>\n" +
            "        <dict>\n" +
            "            <key>com.pekall.mdm.password.profile</key>\n" +
            "            <dict>\n" +
            "                <key>restrictedBool</key>\n" +
            "                <dict>\n" +
            "                    <key>allowSimple</key>\n" +
            "                    <dict>\n" +
            "                        <key>value</key>\n" +
            "                        <true/>\n" +
            "                    </dict>\n" +
            "                    <key>forcePIN</key>\n" +
            "                    <dict>\n" +
            "                        <key>value</key>\n" +
            "                        <true/>\n" +
            "                    </dict>\n" +
            "                </dict>\n" +
            "                <key>restrictedValue</key>\n" +
            "                <dict>\n" +
            "                    <key>minLength</key>\n" +
            "                    <dict>\n" +
            "                        <key>value</key>\n" +
            "                        <integer>9</integer>\n" +
            "                    </dict>\n" +
            "                </dict>\n" +
            "            </dict>\n" +
            "            <key>com.pekall.profile</key>\n" +
            "            <dict>\n" +
            "                <key>restrictedBool</key>\n" +
            "                <dict/>\n" +
            "                <key>restrictedValue</key>\n" +
            "                <dict/>\n" +
            "            </dict>\n" +
            "        </dict>\n" +
            "        <key>Status</key>\n" +
            "        <string>Acknowledged</string>\n" +
            "        <key>UDID</key>\n" +
            "        <string>ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd</string>\n" +
            "    </dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CommandRestrictionsStatus status = new CommandRestrictionsStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "2ade3a08-24fc-444d-b245-5a4ba8d0a3d1");
        status.setGlobalRestrictions(createRestrictions());
        status.setProfileRestrictions(createAllProfileRestrictions());

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParserXml() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandStatusMsg status = parser.getMessage();

        assertTrue(status instanceof CommandRestrictionsStatus);
        CommandRestrictionsStatus status1 = new CommandRestrictionsStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "2ade3a08-24fc-444d-b245-5a4ba8d0a3d1");
        status1.setGlobalRestrictions(createRestrictions());
        status1.setProfileRestrictions(createAllProfileRestrictions());

        assertEquals(status, status1);
    }

    public void testTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(REAL_MSG_XML);
        CommandStatusMsg status = parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        // TODO: Check com.pekall.profile
        // assertEquals(xml, REAL_MSG_XML);
    }

    private AllProfileRestrictions createAllProfileRestrictions() {
        AllProfileRestrictions restrictions = new AllProfileRestrictions();
        restrictions.setPasswordProfile(createProfileRestrictions());
        return restrictions;
    }

    private Restrictions createRestrictions() {
        RestrictedBool bool = new RestrictedBool();
        bool.setForcePIN(true);
        RestrictedValue value = new RestrictedValue();
        value.setMinLength(9);

        Restrictions restrictions = new Restrictions();
        restrictions.setRestrictedBool(bool);
        restrictions.setRestrictedValue(value);
        return restrictions;
    }

    private Restrictions createProfileRestrictions() {
        RestrictedBool bool = new RestrictedBool();
        bool.setForcePIN(true);
        bool.setAllowSimple(true);
        RestrictedValue value = new RestrictedValue();
        value.setMinLength(9);

        Restrictions restrictions = new Restrictions();
        restrictions.setRestrictedBool(bool);
        restrictions.setRestrictedValue(value);
        return restrictions;
    }
}
