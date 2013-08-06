package com.pekall.plist;

import com.dd.plist.NSObject;
import com.dd.plist.PropertyListParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * XML parser and generator for PLIST object(NSObject)
 */
public class PlistXmlParser {
    /**
     * Convert an NSObject to an XML string
     * @param root NSObject to convert
     * @return XML string
     * @throws IOException
     */
    public final static String toXml(NSObject root) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        PropertyListParser.saveAsXML(root, bos);
        return bos.toString();
    }

    /**
     * Convert an XML string to an NSObject object
     * @param xml string to parse
     * @return NSObject
     * @throws Exception
     */
    public final static NSObject fromXml(String xml) throws Exception {
        return PropertyListParser.parse(xml.getBytes());
    }
}
