package com.pekall.plist.beans;

import com.dd.plist.NSDictionary;
import com.pekall.plist.Constants;
import com.pekall.plist.PlistBeanConverter;
import com.pekall.plist.PlistXmlParser;

import java.io.IOException;

/**
 * Base class for all beans
 */
public abstract class BeanBase {
    /**
     * Convert bean to xml string
     * @return xml for the bean object
     */
    public String toXml() {
        NSDictionary dictionary = PlistBeanConverter.createNdictFromBean(this);
        String xml = Constants.EMPTY_PLIST_XML;
        try {
            xml = PlistXmlParser.toXml(dictionary);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xml;
    }

    /**
     * Create a bean from xml string.
     * This method can be used for CommandMsg, CommandStatusMsg, PayloadBase
     * and any subclasses of them except PayloadArrayWrapper,
     * since it is a dynamic-generated bean object.
     *
     * Only use it if you know the exact type of the xml message.
     * Otherwise, use CommandMsgParser, CommandStatusMsgParser, PayloadXmlMsgParser
     *
     * @param xml to parse
     * @param clz class of the bean
     * @return bean object of the given class
     */
    public static BeanBase fromXml(String xml, Class clz) {
        NSDictionary root;
        BeanBase bean = null;
        try {
            root = (NSDictionary) PlistXmlParser.fromXml(xml);
            bean = (BeanBase) PlistBeanConverter.createBeanFromNdict(root, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }


    /**
     * Create a bean from xml string.
     *
     * @param xml
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T fromXmlT(String xml, Class<T> clz) {
        NSDictionary root;
        BeanBase bean = null;
        Object object = null;
        try {
            root = (NSDictionary) PlistXmlParser.fromXml(xml);
            object = PlistBeanConverter.createBeanFromNdict(root, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T)object;
    }
}
