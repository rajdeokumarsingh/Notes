package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandObject;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/8/13
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandMsgTest extends TestCase {
    private static final String XML_CMD_MSG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\"\n" +
            "\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "<key>Command</key>\n" +
            "<dict>\n" +
            "<key>RequestType</key>\n" +
            "<string>DeviceLock</string>\n" +
            "</dict>\n" +
            "<key>CommandUUID</key>\n" +
            "<string>51da6d87-8bd0-4c43-a7c3-377bdb260516</string></dict>\n" +
            "</plist>";

    public void testGenerateMsgFromXml() throws Exception {
        NSDictionary root = (NSDictionary) PlistXmlParser.fromXml(XML_CMD_MSG);

        CommandMsg msg = (CommandMsg) PlistBeanConverter
                .createBeanFromNdict(root, CommandMsg.class);
        assertEquals(msg.getCommandUUID(), "51da6d87-8bd0-4c43-a7c3-377bdb260516");

        CommandObject cmdObj = msg.getCommand();
        assertEquals(cmdObj.getRequestType(), CommandObject.REQ_TYPE_DEVICE_LOCK);
    }

    public void testGenerateXmlMsgFromBean() throws Exception {
        CommandMsg msg = new CommandMsg("51da6d87-8bd0-4c43-a7c3-377bdb260516",
                new CommandObject(CommandObject.REQ_TYPE_DEVICE_LOCK));

        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest("xml: " + xml);
        NSDictionary rootParsed = (NSDictionary) PlistXmlParser.fromXml(xml);
        CommandMsg msgParsed = (CommandMsg) PlistBeanConverter
                .createBeanFromNdict(root, CommandMsg.class);
        assertEquals(msg, msgParsed);
    }

    public void testEmptyMsg() throws Exception {
        CommandMsg msg = new CommandMsg();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest("xml: " + xml);

        NSDictionary rootParsed = (NSDictionary) PlistXmlParser.fromXml(xml);
        CommandMsg msgParsed = (CommandMsg) PlistBeanConverter
                .createBeanFromNdict(root, CommandMsg.class);

        assertTrue(msgParsed.isEmptyCommand());
        assertEquals(msg, msgParsed);
    }
}

