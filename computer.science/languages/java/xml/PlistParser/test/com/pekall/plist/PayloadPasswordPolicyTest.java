package com.pekall.plist;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadPasswordPolicy;
import junit.framework.TestCase;

public class PayloadPasswordPolicyTest extends TestCase {
    public void testXml2Bean() throws Exception {
        PayloadPasswordPolicy policy = null;

        // The dictionary represents the whole xml data
        NSDictionary dictionary = (NSDictionary) PlistXmlParser.fromXml(
                EnrollProfileData.ENROLL_PROFILE);

        NSObject content = dictionary.objectForKey(Constants.KEY_PL_CONTENT);
        if (content instanceof NSDictionary) {
        } else if (content instanceof NSArray) {
            NSArray array = (NSArray) content;
            for (int i = 0; i < array.count(); i++) {
                NSObject nsObject = array.objectAtIndex(i);
                if (nsObject instanceof NSDictionary) {
                    NSDictionary dict = (NSDictionary) nsObject;
                    PayloadBase base = (PayloadBase) PlistBeanConverter
                            .createBeanFromNdict(dict, PayloadBase.class);
                    if (Constants.PL_TYPE_PASSWORD_POLICY.equals(base.getPayloadType())
                            || Constants.PL_ID_PASSWORD_POLICY.equals(base.getPayloadIdentifier())) {
                        policy = (PayloadPasswordPolicy) PlistBeanConverter
                                .createBeanFromNdict(dict, PayloadPasswordPolicy.class);
                        break;
                    }
                }
            }
        }

        PlistDebug.logTest("password policy: " + policy.toString());
        assertEquals(policy.getPayloadDescription(), "配置与安全相关的项目。");
        assertEquals(policy.getPayloadDisplayName(), "密码");
        assertEquals(policy.getPayloadIdentifier(), "com.pekall.profile.passcodepolicy");
        assertEquals(policy.getPayloadOrganization(), "Pekall Capital");
        assertEquals(policy.getPayloadType(), "com.apple.mobiledevice.passwordpolicy");
        assertEquals(policy.getPayloadUUID(), "3808D742-5D21-401E-B83C-AED1E990332D");
        assertEquals(policy.getPayloadVersion(), 1);

        assertEquals(policy.isAllowSimple(), false);
        assertEquals(policy.isForcePIN(), true);
        assertEquals(policy.getMaxFailedAttempts(), 7);
        assertEquals(policy.getMaxGracePeriod(), 1);
        assertEquals(policy.getMaxInactivity(), 2);
        assertEquals(policy.getMaxPINAgeInDays(), 2);
        assertEquals(policy.getMinComplexChars(), 2);
        assertEquals(policy.getMinLength(), 4);
        assertEquals(policy.getPinHistory(), 50);
        assertEquals(policy.isRequireAlphanumeric(), true);
    }

    public void testBean2Xml() throws Exception {
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

        NSDictionary root = PlistBeanConverter.createNdictFromBean(policy);
        String xml = PlistXmlParser.toXml(root);
        PlistDebug.logTest("xml: " + xml);

        NSDictionary root1 = (NSDictionary) PlistXmlParser.fromXml(xml);
        PayloadPasswordPolicy policy1 = (PayloadPasswordPolicy) PlistBeanConverter
                .createBeanFromNdict(root1, PayloadPasswordPolicy.class);
        assertEquals(policy, policy1);
    }
}
