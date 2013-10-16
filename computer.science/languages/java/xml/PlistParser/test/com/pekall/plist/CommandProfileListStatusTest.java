package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandProfileListStatus;
import com.pekall.plist.beans.CommandStatusMsg;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadPasswordPolicy;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/9/13
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandProfileListStatusTest extends TestCase {

    public void testGenXml() throws Exception {
        CommandProfileListStatus status = new CommandProfileListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        status.addProfile(createProfile());
        status.addProfile(createProfileSettings());
        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    public void testParseXmlMsg() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandProfileListStatus status = (CommandProfileListStatus) parser.getMessage();

        CommandProfileListStatus status1 = new CommandProfileListStatus(
                CommandStatusMsg.CMD_STAT_ACKNOWLEDGED,
                "ce39b59afb1d7c4d3a6b7eb9f60608712cbbf2bd",
                "1ed5063d-7486-483a-a53b-44364b5191e1");
        status1.addProfile(createProfile());
        status1.addProfile(createProfileSettings());

        assertEquals(status.getUDID(), status1.getUDID());
        assertEquals(status.getCommandUUID(), status1.getCommandUUID());
        assertEquals(status.getStatus(), status1.getStatus());
        assertEquals(status.getProfileList().size(), 2);

        ArrayList<PayloadArrayWrapper> wrapperList =
                (ArrayList<PayloadArrayWrapper>) status.getProfileList();
        ArrayList<PayloadArrayWrapper> wrapperList1 =
                (ArrayList<PayloadArrayWrapper>) status1.getProfileList();

        for (int i = 0; i < wrapperList.size(); i++) {
            PayloadArrayWrapper wrapper = wrapperList.get(i);
            PayloadArrayWrapper wrapper1 = wrapperList1.get(i);
            assertEquals(wrapper, wrapper1);

            assertEquals(wrapper.getPayloadContent().size(), 1);
            assertTrue(wrapper.getPayloadContent().get(0) instanceof PayloadPasswordPolicy);
            assertEquals(wrapper.getPayloadContent().get(0), createPasswordPolicy());
        }

        assertEquals(status, status1);
    }

    public void testTwoWay() throws Exception {
        CommandStatusMsgParser parser = new CommandStatusMsgParser(TEST_XML);
        CommandProfileListStatus status = (CommandProfileListStatus) parser.getMessage();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(status);
        String xml = PlistXmlParser.toXml(root);
        PlistDebug.logTest(xml);
        assertEquals(xml, TEST_XML);
    }

    private PayloadArrayWrapper createProfile() {
        PayloadPasswordPolicy policy = createPasswordPolicy();
        PayloadArrayWrapper wrapper = createWrapper();
        wrapper.addPayLoadContent(policy);
        return wrapper;
    }

    private PayloadArrayWrapper createWrapper() {
        PayloadArrayWrapper wrapper = new PayloadArrayWrapper();
        wrapper.setPayloadDescription("描述文件描述。wjl 测试");
        wrapper.setPayloadDisplayName("Pekall MDM Profile");
        wrapper.setPayloadIdentifier("com.pekall.profile");
        wrapper.setPayloadOrganization("Pekall Capital");
        wrapper.setPayloadType("Configuration");
        wrapper.setPayloadUUID("2ED160FF-4B6C-47DD-8105-769231367D2A");
        wrapper.setPayloadVersion(1);
        wrapper.setPayloadRemovalDisallowed(true);
        return wrapper;
    }

    private PayloadArrayWrapper createProfileSettings() {
        PayloadPasswordPolicy policy = createPasswordPolicy();
        PayloadArrayWrapper wrapper = createSettingWrapper();
        wrapper.addPayLoadContent(policy);
        return wrapper;
    }

    private PayloadArrayWrapper createSettingWrapper() {
        PayloadArrayWrapper wrapper = new PayloadArrayWrapper();
        wrapper.setPayloadDescription("描述文件描述");
        wrapper.setPayloadDisplayName("Pekall MDM Setting");
        wrapper.setPayloadIdentifier("com.pekall.setting");
        wrapper.setPayloadOrganization("Pekall Capital");
        wrapper.setPayloadType("Configuration");
        wrapper.setPayloadUUID("2ED160FF-4B6C-47DD-1234-56789012345A");
        wrapper.setPayloadVersion(1);
        wrapper.setPayloadRemovalDisallowed(true);
        return wrapper;
    }

    private PayloadPasswordPolicy createPasswordPolicy() {
        PayloadPasswordPolicy policy = new PayloadPasswordPolicy();
        policy.setPayloadDescription("配置与安全相关的项目。");
        policy.setPayloadDisplayName("密码");
        policy.setPayloadIdentifier("com.pekall.profile.passcodepolicy");
        policy.setPayloadOrganization("Pekall Capital");
        policy.setPayloadType("com.apple.mobiledevice.passwordpolicy");
        policy.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        policy.setPayloadVersion(1);
        policy.setAllowSimple(false);
        policy.setForcePIN(true);
        policy.setMaxFailedAttempts(7);
        policy.setMaxGracePeriod(1);
        policy.setMaxInactivity(2f);
        policy.setMaxPINAgeInDays(2);
        policy.setMinComplexChars(2);
        policy.setMinLength(4);
        policy.setPinHistory(50);
        policy.setRequireAlphanumeric(true);
        return policy;
    }

    private static final java.lang.String TEST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "\t<key>ProfileList</key>\n" +
            "\t<array>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>PayloadContent</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>allowSimple</key>\n" +
            "\t\t\t\t\t<false/>\n" +
            "\t\t\t\t\t<key>forcePIN</key>\n" +
            "\t\t\t\t\t<true/>\n" +
            "\t\t\t\t\t<key>maxFailedAttempts</key>\n" +
            "\t\t\t\t\t<integer>7</integer>\n" +
            "\t\t\t\t\t<key>maxInactivity</key>\n" +
            "\t\t\t\t\t<real>2.0</real>\n" +
            "\t\t\t\t\t<key>maxPINAgeInDays</key>\n" +
            "\t\t\t\t\t<integer>2</integer>\n" +
            "\t\t\t\t\t<key>minComplexChars</key>\n" +
            "\t\t\t\t\t<integer>2</integer>\n" +
            "\t\t\t\t\t<key>minLength</key>\n" +
            "\t\t\t\t\t<integer>4</integer>\n" +
            "\t\t\t\t\t<key>requireAlphanumeric</key>\n" +
            "\t\t\t\t\t<true/>\n" +
            "\t\t\t\t\t<key>pinHistory</key>\n" +
            "\t\t\t\t\t<integer>50</integer>\n" +
            "\t\t\t\t\t<key>maxGracePeriod</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t\t\t<string>com.apple.mobiledevice.passwordpolicy</string>\n" +
            "\t\t\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t\t\t<string>com.pekall.profile.passcodepolicy</string>\n" +
            "\t\t\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t\t\t<string>密码</string>\n" +
            "\t\t\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t\t\t<string>配置与安全相关的项目。</string>\n" +
            "\t\t\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t\t\t<string>Pekall Capital</string>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>PayloadRemovalDisallowed</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>Configuration</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.profile</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>2ED160FF-4B6C-47DD-8105-769231367D2A</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>Pekall MDM Profile</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>描述文件描述。wjl 测试</string>\n" +
            "\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t<string>Pekall Capital</string>\n" +
            "\t\t</dict>\n" +
            "\t\t<dict>\n" +
            "\t\t\t<key>PayloadContent</key>\n" +
            "\t\t\t<array>\n" +
            "\t\t\t\t<dict>\n" +
            "\t\t\t\t\t<key>allowSimple</key>\n" +
            "\t\t\t\t\t<false/>\n" +
            "\t\t\t\t\t<key>forcePIN</key>\n" +
            "\t\t\t\t\t<true/>\n" +
            "\t\t\t\t\t<key>maxFailedAttempts</key>\n" +
            "\t\t\t\t\t<integer>7</integer>\n" +
            "\t\t\t\t\t<key>maxInactivity</key>\n" +
            "\t\t\t\t\t<real>2.0</real>\n" +
            "\t\t\t\t\t<key>maxPINAgeInDays</key>\n" +
            "\t\t\t\t\t<integer>2</integer>\n" +
            "\t\t\t\t\t<key>minComplexChars</key>\n" +
            "\t\t\t\t\t<integer>2</integer>\n" +
            "\t\t\t\t\t<key>minLength</key>\n" +
            "\t\t\t\t\t<integer>4</integer>\n" +
            "\t\t\t\t\t<key>requireAlphanumeric</key>\n" +
            "\t\t\t\t\t<true/>\n" +
            "\t\t\t\t\t<key>pinHistory</key>\n" +
            "\t\t\t\t\t<integer>50</integer>\n" +
            "\t\t\t\t\t<key>maxGracePeriod</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t\t\t<string>com.apple.mobiledevice.passwordpolicy</string>\n" +
            "\t\t\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t\t\t<integer>1</integer>\n" +
            "\t\t\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t\t\t<string>com.pekall.profile.passcodepolicy</string>\n" +
            "\t\t\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t\t\t<string>3808D742-5D21-401E-B83C-AED1E990332D</string>\n" +
            "\t\t\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t\t\t<string>密码</string>\n" +
            "\t\t\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t\t\t<string>配置与安全相关的项目。</string>\n" +
            "\t\t\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t\t\t<string>Pekall Capital</string>\n" +
            "\t\t\t\t</dict>\n" +
            "\t\t\t</array>\n" +
            "\t\t\t<key>PayloadRemovalDisallowed</key>\n" +
            "\t\t\t<true/>\n" +
            "\t\t\t<key>PayloadType</key>\n" +
            "\t\t\t<string>Configuration</string>\n" +
            "\t\t\t<key>PayloadVersion</key>\n" +
            "\t\t\t<integer>1</integer>\n" +
            "\t\t\t<key>PayloadIdentifier</key>\n" +
            "\t\t\t<string>com.pekall.setting</string>\n" +
            "\t\t\t<key>PayloadUUID</key>\n" +
            "\t\t\t<string>2ED160FF-4B6C-47DD-1234-56789012345A</string>\n" +
            "\t\t\t<key>PayloadDisplayName</key>\n" +
            "\t\t\t<string>Pekall MDM Setting</string>\n" +
            "\t\t\t<key>PayloadDescription</key>\n" +
            "\t\t\t<string>描述文件描述</string>\n" +
            "\t\t\t<key>PayloadOrganization</key>\n" +
            "\t\t\t<string>Pekall Capital</string>\n" +
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
