package com.pekall.plist;

import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadPasswordPolicy;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/8/13
 * Time: 10:24 AM
 * To change this template use File | Settings | File Templates.
 */
public class PayloadXmlMsgGeneratorTest extends TestCase {
    public void testGenerateXmlMsgFromBeans() throws Exception {

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
        policy.setMaxInactivity(2);
        policy.setMaxPINAgeInDays(2);
        policy.setMinComplexChars(2);
        policy.setMinLength(4);
        policy.setPinHistory(50);
        policy.setRequireAlphanumeric(true);

        PayloadArrayWrapper wrapper = new PayloadArrayWrapper();
        wrapper.setPayloadDescription("描述文件描述。wjl 测试");
        wrapper.setPayloadDisplayName("Pekall MDM Profile");
        wrapper.setPayloadIdentifier("com.pekall.profile");
        wrapper.setPayloadOrganization("Pekall Capital");
        wrapper.setPayloadType("Configuration");
        wrapper.setPayloadUUID("2ED160FF-4B6C-47DD-8105-769231367D2A");
        wrapper.setPayloadVersion(1);

        wrapper.addPayLoadContent(policy);

        PayloadXmlMsgGenerator generator = new PayloadXmlMsgGenerator(wrapper);
        String xml = generator.getXml();

        PlistDebug.logTest(xml);

        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(xml);
        assertEquals(policy, parser.getPasswordPolicy());

        PayloadBase wrapper1 = parser.getPayloadDescriptor();
        assertTrue(wrapper.baseEquals(wrapper1));
    }
}
