package com.pekall.plist;

import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadPasswordPolicy;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/8/13
 * Time: 9:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class PayloadXmlMsgParserTest extends TestCase {

    public void testXml2Bean() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(EnrollProfileData.ENROLL_PROFILE);

        PayloadBase desc = parser.getPayloadDescriptor();
        assertEquals(desc.getPayloadDescription(), "描述文件描述。wjl 测试");
        assertEquals(desc.getPayloadDisplayName(), "Pekall MDM Profile");
        assertEquals(desc.getPayloadIdentifier(), "com.pekall.profile");
        assertEquals(desc.getPayloadOrganization(), "Pekall Capital");
        assertEquals(desc.getPayloadType(), "Configuration");
        assertEquals(desc.getPayloadUUID(), "2ED160FF-4B6C-47DD-8105-769231367D2A");
        assertEquals(desc.getPayloadVersion(), 1);

        PayloadPasswordPolicy passwordPolicy = parser.getPasswordPolicy();
        PayloadPasswordPolicy passwordPolicy1 = new PayloadPasswordPolicy();

        passwordPolicy1.setPayloadDescription("配置与安全相关的项目。");
        passwordPolicy1.setPayloadDisplayName("密码");
        passwordPolicy1.setPayloadIdentifier("com.pekall.profile.passcodepolicy");
        passwordPolicy1.setPayloadOrganization("Pekall Capital");
        passwordPolicy1.setPayloadType("com.apple.mobiledevice.passwordpolicy");
        passwordPolicy1.setPayloadUUID("3808D742-5D21-401E-B83C-AED1E990332D");
        passwordPolicy1.setPayloadVersion(1);
        passwordPolicy1.setAllowSimple(false);
        passwordPolicy1.setForcePIN(true);
        passwordPolicy1.setMaxFailedAttempts(7);
        passwordPolicy1.setMaxGracePeriod(1);
        passwordPolicy1.setMaxInactivity(2f);
        passwordPolicy1.setMaxPINAgeInDays(2);
        passwordPolicy1.setMinComplexChars(2);
        passwordPolicy1.setMinLength(4);
        passwordPolicy1.setPinHistory(50);
        passwordPolicy1.setRequireAlphanumeric(true);

        assertEquals(passwordPolicy, passwordPolicy1);
    }
}
