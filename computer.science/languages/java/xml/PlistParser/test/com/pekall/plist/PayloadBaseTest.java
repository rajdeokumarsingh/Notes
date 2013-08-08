package com.pekall.plist;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.pekall.plist.beans.PayloadBase;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-8-7
 * Time: 下午5:11
 * To change this template use File | Settings | File Templates.
 */
public class PayloadBaseTest extends TestCase {
    public void testBeanFromXml() throws Exception {
        NSDictionary dictionary = (NSDictionary) PlistXmlParser.fromXml(
                EnrollProfileData.ENROLL_PROFILE);
        PayloadBase base = (PayloadBase) PlistBeanConverter
                .createBeanFromNdict(dictionary, PayloadBase.class);
        assertEquals(base.getPayloadDescription(), "描述文件描述。wjl 测试");
        assertEquals(base.getPayloadDisplayName(), "Pekall MDM Profile");
        assertEquals(base.getPayloadIdentifier(), "com.pekall.profile");
        assertEquals(base.getPayloadOrganization(), "Pekall Capital");
        assertEquals(base.isPayloadRemovalDisallowed(), false);
        assertEquals(base.getPayloadType(), "Configuration");
        assertEquals(base.getPayloadUUID(), "2ED160FF-4B6C-47DD-8105-769231367D2A");
        assertEquals(base.getPayloadVersion(), 1);
    }

    public void testBean2Xml() throws Exception {
        PayloadBase base = new PayloadBase();
        base.setPayloadDescription("描述文件描述。wjl 测试");
        base.setPayloadDisplayName("Pekall MDM Profile");
        base.setPayloadIdentifier("com.pekall.profile");
        base.setPayloadOrganization("Pekall Capital");
        base.setPayloadRemovalDisallowed(false);
        base.setPayloadType("Configuration");
        base.setPayloadUUID("2ED160FF-4B6C-47DD-8105-769231367D2A");
        base.setPayloadVersion(1);

        NSDictionary root = PlistBeanConverter.createNdictFromBean(base);
        NSArray array = new NSArray(3);
        for (int i = 0; i < 3; i++) {
            array.setValue(i, new NSNumber(i));
        }
        root.put(Constants.KEY_PL_CONTENT, array);

        String xml = PlistXmlParser.toXml(root);
        PlistDebug.logTest("xml: " + xml);

        NSDictionary root1 = (NSDictionary) PlistXmlParser.fromXml(xml);
        PayloadBase base1 = (PayloadBase) PlistBeanConverter
                .createBeanFromNdict(root1, PayloadBase.class);
        assertEquals(root, root1);
    }
}
