package com.pekall.plist.json;

import com.google.gson.Gson;
import com.pekall.plist.PayloadXmlMsgParser;
import com.pekall.plist.beans.PayloadArrayWrapper;

/**
 * Utility class for converting between xml and json
 */
@SuppressWarnings("UnusedDeclaration")
class XmlAndJsonConvertUtil {
    public static String json2Xml(String json) {
        Gson gson = new Gson();
        PayloadJsonWrapper wrapper;

        String xml = null;
        try {
            wrapper = gson.fromJson(json, PayloadJsonWrapper.class);
            if (wrapper != null) {
                xml = wrapper.getPayloadArrayWrapper().toXml();
            }
        } catch (Exception e) {
            System.out.println("json 解析异常");
        }
        return xml;
    }


    public static String xml2Json(String xml) {
        PayloadXmlMsgParser parser = new PayloadXmlMsgParser(xml);
        PayloadArrayWrapper wrapper = (PayloadArrayWrapper) parser.getPayloadDescriptor();
        if(wrapper == null){
            return null;
        }

        PayloadJsonWrapper jsonWrapper = new PayloadJsonWrapper(wrapper);
        Gson gson = new Gson();
        return gson.toJson(jsonWrapper);
    }
}
