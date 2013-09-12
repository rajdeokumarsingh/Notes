package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.PayloadArrayWrapper;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/8/13
 * Time: 8:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class PayloadArrayWrapperTest extends TestCase {
    public void testXml2Bean() throws Exception {
        NSDictionary dictionary = (NSDictionary) PlistXmlParser
                .fromXml(EnrollProfileData.ENROLL_PROFILE);
        PayloadArrayWrapper wrapper = (PayloadArrayWrapper) PlistBeanConverter
                .createBeanFromNdict(dictionary, PayloadArrayWrapper.class);

        assertEquals(wrapper.getPayloadDescription(), "描述文件描述。wjl 测试");
        assertEquals(wrapper.getPayloadDisplayName(), "Pekall MDM Profile");
        assertEquals(wrapper.getPayloadIdentifier(), "com.pekall.profile");
        assertEquals(wrapper.getPayloadOrganization(), "Pekall Capital");
        assertEquals(wrapper.getPayloadType(), "Configuration");
        assertEquals(wrapper.getPayloadUUID(), "2ED160FF-4B6C-47DD-8105-769231367D2A");
        assertEquals(wrapper.getPayloadVersion(), 1);

        assertEquals(wrapper.getPayloadContent().size(), 7);
        PlistDebug.logTest("Bean: " + wrapper.toString());
    }

    public void testBean2Xml() throws Exception {
        NSDictionary dictionary = (NSDictionary) PlistXmlParser
                .fromXml(EnrollProfileData.ENROLL_PROFILE);
        PayloadArrayWrapper wrapper = (PayloadArrayWrapper) PlistBeanConverter
                .createBeanFromNdict(dictionary, PayloadArrayWrapper.class);

        NSDictionary dictionary1 = PlistBeanConverter.createNdictFromBean(wrapper);
        String xml = PlistXmlParser.toXml(dictionary1);

        PlistDebug.logTest("xml : " + xml);
    }
}
