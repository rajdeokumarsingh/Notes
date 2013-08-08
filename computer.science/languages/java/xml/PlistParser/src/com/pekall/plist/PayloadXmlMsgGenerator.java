package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.PayloadArrayWrapper;

import java.io.IOException;

/**
 * Generator for converting bean to IOS payload message
 */
public class PayloadXmlMsgGenerator {
    private String genXml;

    /**
     * Constructor, in which the bean "wrapper" will be converted to a XML string
     * @param wrapper
     */
    public PayloadXmlMsgGenerator(PayloadArrayWrapper wrapper) {
        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(wrapper);
        try {
            genXml = PlistXmlParser.toXml(dictionary);
        } catch (IOException e) {
            e.printStackTrace();
            genXml = null;
        }
    }

    public String getXml() {
        return genXml;
    }
}
