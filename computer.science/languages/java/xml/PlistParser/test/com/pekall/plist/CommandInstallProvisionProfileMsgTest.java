package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandInstallProvisionProfile;
import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandObject;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/9/13
 * Time: 9:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandInstallProvisionProfileMsgTest extends TestCase {
    private static final String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>CommandUUID</key>\n" +
            "\t<string>27ec28ef-5001-44c8-8e3b-81f8dc0013f0</string>\n" +
            "\t<key>Command</key>\n" +
            "\t<dict>\n" +
            "\t\t<key>ProvisioningProfile</key>\n" +
            "\t\t<data>\n" +
            "\t\t\tPD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPCFET0NUWVBFIHBsaXN0IFBVQkxJQyAiLS8vQXBwbGUvL0RURCBQTElTVCAxLjAvL0VOIiAiaHR0cDovL3d3dy5hcHBsZS5jb20vRFREcy9Qcm9wZXJ0eUxpc3QtMS4wLmR0ZCI+CjxwbGlzdCB2ZXJzaW9uPSIxLjAiPgo8ZGljdD4KCTxrZXk+UGF5bG9hZENvbnRlbnQ8L2tleT4KCTxhcnJheT4KCQk8ZGljdD4KCQkJPGtleT5hbGxvd1NpbXBsZTwva2V5PgoJCQk8ZmFsc2UvPgoJCQk8a2V5PmZvcmNlUElOPC9rZXk+CgkJCTx0cnVlLz4KCQkJPGtleT5tYXhGYWlsZWRBdHRlbXB0czwva2V5PgoJCQk8aW50ZWdlcj43PC9pbnRlZ2VyPgoJCQk8a2V5Pm1heEdyYWNlUGVyaW9kPC9rZXk+CgkJCTxpbnRlZ2VyPjE8L2ludGVnZXI+CgkJCTxrZXk+bWF4SW5hY3Rpdml0eTwva2V5PgoJCQk8aW50ZWdlcj4yPC9pbnRlZ2VyPgoJCQk8a2V5Pm1heFBJTkFnZUluRGF5czwva2V5PgoJCQk8aW50ZWdlcj4yPC9pbnRlZ2VyPgoJCQk8a2V5Pm1pbkNvbXBsZXhDaGFyczwva2V5PgoJCQk8aW50ZWdlcj4yPC9pbnRlZ2VyPgoJCQk8a2V5Pm1pbkxlbmd0aDwva2V5PgoJCQk8aW50ZWdlcj40PC9pbnRlZ2VyPgoJCQk8a2V5PnBpbkhpc3Rvcnk8L2tleT4KCQkJPGludGVnZXI+NTA8L2ludGVnZXI+CgkJCTxrZXk+cmVxdWlyZUFscGhhbnVtZXJpYzwva2V5PgoJCQk8dHJ1ZS8+CgkJCTxrZXk+UGF5bG9hZElkZW50aWZpZXI8L2tleT4KCQkJPHN0cmluZz5jb20ucGVrYWxsLnByb2ZpbGUucGFzc2NvZGVwb2xpY3k8L3N0cmluZz4KCQkJPGtleT5QYXlsb2FkVHlwZTwva2V5PgoJCQk8c3RyaW5nPmNvbS5hcHBsZS5tb2JpbGVkZXZpY2UucGFzc3dvcmRwb2xpY3k8L3N0cmluZz4KCQkJPGtleT5QYXlsb2FkVVVJRDwva2V5PgoJCQk8c3RyaW5nPjM4MDhENzQyLTVEMjEtNDAxRS1CODNDLUFFRDFFOTkwMzMyRDwvc3RyaW5nPgoJCQk8a2V5PlBheWxvYWRWZXJzaW9uPC9rZXk+CgkJCTxpbnRlZ2VyPjE8L2ludGVnZXI+CgkJCTxrZXk+UGF5bG9hZERlc2NyaXB0aW9uPC9rZXk+CgkJCTxzdHJpbmc+6YWN572u5LiO5a6J5YWo55u45YWz55qE6aG555uu44CCPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZERpc3BsYXlOYW1lPC9rZXk+CgkJCTxzdHJpbmc+5a+G56CBPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZE9yZ2FuaXphdGlvbjwva2V5PgoJCQk8c3RyaW5nPlBla2FsbCBDYXBpdGFsPC9zdHJpbmc+CgkJCTxrZXk+UGF5bG9hZFJlbW92YWxEaXNhbGxvd2VkPC9rZXk+CgkJCTxmYWxzZS8+CgkJPC9kaWN0PgoJPC9hcnJheT4KCTxrZXk+UGF5bG9hZElkZW50aWZpZXI8L2tleT4KCTxzdHJpbmc+Y29tLnBla2FsbC5wcm9maWxlPC9zdHJpbmc+Cgk8a2V5PlBheWxvYWRUeXBlPC9rZXk+Cgk8c3RyaW5nPkNvbmZpZ3VyYXRpb248L3N0cmluZz4KCTxrZXk+UGF5bG9hZFVVSUQ8L2tleT4KCTxzdHJpbmc+MkVEMTYwRkYtNEI2Qy00N0RELTgxMDUtNzY5MjMxMzY3RDJBPC9zdHJpbmc+Cgk8a2V5PlBheWxvYWRWZXJzaW9uPC9rZXk+Cgk8aW50ZWdlcj4xPC9pbnRlZ2VyPgoJPGtleT5QYXlsb2FkRGVzY3JpcHRpb248L2tleT4KCTxzdHJpbmc+5o+P6L+w5paH5Lu25o+P6L+w44CCd2psIOa1i+ivlTwvc3RyaW5nPgoJPGtleT5QYXlsb2FkRGlzcGxheU5hbWU8L2tleT4KCTxzdHJpbmc+UGVrYWxsIE1ETSBQcm9maWxlPC9zdHJpbmc+Cgk8a2V5PlBheWxvYWRPcmdhbml6YXRpb248L2tleT4KCTxzdHJpbmc+UGVrYWxsIENhcGl0YWw8L3N0cmluZz4KCTxrZXk+UGF5bG9hZFJlbW92YWxEaXNhbGxvd2VkPC9rZXk+Cgk8ZmFsc2UvPgo8L2RpY3Q+CjwvcGxpc3Q+\n" +
            "\t\t</data>\n" +
            "\t\t<key>RequestType</key>\n" +
            "\t\t<string>InstallProvisioningProfile</string>\n" +
            "\t</dict>\n" +
            "</dict>\n" +
            "</plist>";

    private static final String PROF_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>PayloadContent</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>allowSimple</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t\t<key>forcePIN</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>maxFailedAttempts</key>\n" +
            "\t\t\t<integer>7</integer>\n" +
            "\t\t\t<key>maxGracePeriod</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>maxInactivity</key>\n" +
            "\t\t\t<integer>2</integer>\n" +
            "\t\t\t<key>maxPINAgeInDays</key>\n" +
            "\t\t\t<integer>2</integer>\n" +
            "\t\t\t<key>minComplexChars</key>\n" +
            "\t\t\t<integer>2</integer>\n" +
            "\t\t\t<key>minLength</key>\n" +
            "\t\t\t<integer>4</integer>\n" +
            "\t\t\t<key>pinHistory</key>\n" +
            "\t\t\t<integer>50</integer>\n" +
            "\t\t\t<key>requireAlphanumeric</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.profile.passcodepolicy</string>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>com.apple.mobiledevice.passwordpolicy</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>配置与安全相关的项目。</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>密码</string>\n" +
            "\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t<string>Pekall Capital</string>\n" +
            "\t\t\t<key>PayloadRemovalDisallowed</key>\n" +
            "\t\t\t<false/>\n" +
            "\t\t</dict>\n" +
            "\t</array>\n" +
            "\t<key>PayloadIdentifier</key>\n" +
            "\t<string>com.pekall.profile</string>\n" +
            "\t<key>PayloadType</key>\n" +
            "\t<string>Configuration</string>\n" +
            "\t<key>PayloadUUID</key>\n" +
            "\t<string>2ED160FF-4B6C-47DD-8105-769231367D2A</string>\n" +
            "\t<key>PayloadVersion</key>\n" +
            "\t<integer>1</integer>\n" +
            "\t<key>PayloadDescription</key>\n" +
            "\t<string>描述文件描述。wjl 测试</string>\n" +
            "\t<key>PayloadDisplayName</key>\n" +
            "\t<string>Pekall MDM Profile</string>\n" +
            "\t<key>PayloadOrganization</key>\n" +
            "\t<string>Pekall Capital</string>\n" +
            "\t<key>PayloadRemovalDisallowed</key>\n" +
            "\t<false/>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenXml() throws Exception {
        CommandMsg msg = new CommandMsg(
                "27ec28ef-5001-44c8-8e3b-81f8dc0013f0",
                new CommandInstallProvisionProfile(PROF_XML.getBytes()));
        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(dictionary);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testFromXml() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        assertTrue(msg.getCommand() instanceof CommandInstallProvisionProfile);
        assertEquals(msg.getRequestType(), CommandObject.REQ_TYPE_INST_PROV_PROF);

        CommandInstallProvisionProfile cmd =
                (CommandInstallProvisionProfile) msg.getCommand();
        String profile = new String(cmd.getProvisioningProfile());
        PlistDebug.logTest(profile);

        assertEquals(PROF_XML,profile );

        CommandMsg msg1 = new CommandMsg(
                "27ec28ef-5001-44c8-8e3b-81f8dc0013f0",
                new CommandInstallProvisionProfile(PROF_XML.getBytes()));
        assertEquals(msg, msg1);
        assertEquals(msg.getCommand(), msg1.getCommand());
    }

    public void testTwoWay() throws Exception {
        CommandMsgParser parser = new CommandMsgParser(TEST_XML);
        CommandMsg msg = parser.getMessage();

        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(dictionary);
        assertEquals(xml, TEST_XML);
    }
}
