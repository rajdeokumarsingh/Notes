package com.pekall.plist;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.pekall.plist.beans.BeanBase;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadPasswordPolicy;
import junit.framework.TestCase;

public class PayloadPasswordPolicyTest extends TestCase {
    public void testXml2Bean() throws Exception {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(EnrollProfileData.ENROLL_PROFILE);
        PayloadPasswordPolicy policy = (PayloadPasswordPolicy) parser.getPayload(
                PayloadBase.PAYLOAD_TYPE_PASSWORD_POLICY);

        PlistDebug.logTest("password policy: " + policy.toString());
        assertEquals(policy.getPayloadDescription(), "配置与安全相关的项目。");
        assertEquals(policy.getPayloadDisplayName(), "密码");
        assertEquals(policy.getPayloadIdentifier(), "com.pekall.profile.passcodepolicy");
        assertEquals(policy.getPayloadOrganization(), "Pekall Capital");
        assertEquals(policy.getPayloadType(), "com.apple.mobiledevice.passwordpolicy");
        assertEquals(policy.getPayloadUUID(), "3808D742-5D21-401E-B83C-AED1E990332D");
        assertEquals(policy.getPayloadVersion(), 1);

        assertEquals(policy.getAllowSimple(), Boolean.valueOf(false));
        assertEquals(policy.getForcePIN(), Boolean.valueOf(true));
        assertEquals(policy.getMaxFailedAttempts(), Integer.valueOf(7));
        assertEquals(policy.getMaxGracePeriod(), Integer.valueOf(1));
        assertEquals(policy.getMaxInactivity(), Integer.valueOf(2));
        assertEquals(policy.getMaxPINAgeInDays(), Integer.valueOf(2));
        assertEquals(policy.getMinComplexChars(), Integer.valueOf(2));
        assertEquals(policy.getMinLength(), Integer.valueOf(4));
        assertEquals(policy.getPinHistory(), Integer.valueOf(50));
        assertEquals(policy.getRequireAlphanumeric(), Boolean.valueOf(true));
    }

    public void testBean2Xml() throws Exception {
        PayloadPasswordPolicy policy = createPasswordPolicy();
        String xml = policy.toXml();
        PlistDebug.logTest("xml: " + xml);

        PayloadPasswordPolicy policy1 = BeanBase.fromXmlT(xml, PayloadPasswordPolicy.class);
        assertEquals(policy, policy1);
    }

    public void testToXml() throws Exception {
        PayloadPasswordPolicy policy = createPasswordPolicy();
        String xml = policy.toXml();
        PlistDebug.logTest("xml: " + xml);

        PayloadPasswordPolicy policy2 = (PayloadPasswordPolicy)
                BeanBase.fromXml(xml, PayloadPasswordPolicy.class);
        assertEquals(policy, policy2);
        assertEquals(xml, policy2.toXml());
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
        policy.setMaxInactivity(2);
        policy.setMaxPINAgeInDays(2);
        policy.setMinComplexChars(2);
        policy.setMinLength(4);
        policy.setPinHistory(50);
        policy.setRequireAlphanumeric(true);

        policy.setQuality(PayloadPasswordPolicy.QUALITY_ALPHABETIC);
        return policy;
    }
}
