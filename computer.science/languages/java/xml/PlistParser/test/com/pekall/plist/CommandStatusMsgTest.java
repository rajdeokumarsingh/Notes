package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandError;
import com.pekall.plist.beans.CommandStatusMsg;
import junit.framework.TestCase;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 8/8/13
 * Time: 1:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class CommandStatusMsgTest extends TestCase {

    private static final java.lang.String EMPTY_MSG_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "</dict>\n" +
            "</plist>";

    public void testGenEmptyMsg() throws Exception {
        CommandStatusMsg msg = new CommandStatusMsg();

        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest("xml: " + xml);
        assertEquals(xml, EMPTY_MSG_XML);
        NSDictionary rootParsed = (NSDictionary) PlistXmlParser.fromXml(xml);
        CommandStatusMsg msgParsed = (CommandStatusMsg) PlistBeanConverter
                .createBeanFromNdict(rootParsed, CommandStatusMsg.class);
        assertEquals(msg, msgParsed);
    }

    public void testParseIdleMsg() throws Exception {
        NSDictionary root = (NSDictionary) PlistXmlParser.fromXml(IDLE_XML_MSG);
        CommandStatusMsg msg = (CommandStatusMsg) PlistBeanConverter
                .createBeanFromNdict(root, CommandStatusMsg.class);

        assertEquals(msg.getUDID(), "34ce984eb2c6d94f152144590492bf6dcce804d1");
        assertEquals(msg.getStatus(), CommandStatusMsg.CMD_STAT_IDLE);
        assertNull(msg.getCommandUUID());
    }

    public void testGenerateIdleXmlMsg() throws Exception {
        CommandStatusMsg msg = new CommandStatusMsg();
        msg.setStatus(CommandStatusMsg.CMD_STAT_IDLE);
        msg.setUDID("34ce984eb2c6d94f152144590492bf6dcce804d1");

        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest("xml: " + xml);
        NSDictionary rootParsed = (NSDictionary) PlistXmlParser.fromXml(xml);
        CommandStatusMsg msgParsed = (CommandStatusMsg) PlistBeanConverter
                .createBeanFromNdict(rootParsed, CommandStatusMsg.class);
        assertEquals(msg, msgParsed);
    }

    public void testParseAckMessage() throws Exception {
        NSDictionary root = (NSDictionary) PlistXmlParser.fromXml(ACK_XML_MSG);
        CommandStatusMsg msg = (CommandStatusMsg) PlistBeanConverter
                .createBeanFromNdict(root, CommandStatusMsg.class);

        assertEquals(msg.getCommandUUID(), "51da6d87-8bd0-4c43-a7c3-377bdb260516");
        assertEquals(msg.getStatus(), CommandStatusMsg.CMD_STAT_ACKNOWLEDGED);
        assertEquals(msg.getUDID(), "34ce984eb2c6d94f152144590492bf6dcce804d1");
    }

    public void testGenerateAckXmlMsg() throws Exception {
        CommandStatusMsg msg = new CommandStatusMsg();
        msg.setUDID("34ce984eb2c6d94f152144590492bf6dcce804d1");
        msg.setStatus(CommandStatusMsg.CMD_STAT_ACKNOWLEDGED);
        msg.setCommandUUID("51da6d87-8bd0-4c43-a7c3-377bdb260516");

        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);

        PlistDebug.logTest("xml: " + xml);
        NSDictionary rootParsed = (NSDictionary) PlistXmlParser.fromXml(xml);
        CommandStatusMsg msgParsed = (CommandStatusMsg) PlistBeanConverter
                .createBeanFromNdict(rootParsed, CommandStatusMsg.class);
        assertEquals(root, rootParsed);
    }

    public void testErrorXmlMsg() throws Exception {
        CommandStatusMsg msg = new CommandStatusMsg();
        msg.setCommandUUID("51da6d87-8bd0-4c43-a7c3-377bdb260516");
        msg.setStatus(CommandStatusMsg.CMD_STAT_ERROR);
        msg.setUDID("34ce984eb2c6d94f152144590492bf6dcce804d1");

        CommandError error = new CommandError();
        error.setErrorCode(50);
        error.setErrorDomain("password");
        error.setUSEnglishDescription("can not set password");
        error.setLocalizedDescription("无法设置密码");

        CommandError error1 = new CommandError();
        error1.setErrorCode(51);
        error1.setErrorDomain("password");
        error1.setUSEnglishDescription("can not get password");
        error1.setLocalizedDescription("无法获取密码");

        msg.addError(error);
        msg.addError(error1);

        NSDictionary root = PlistBeanConverter.createNdictFromBean(msg);
        String xml = PlistXmlParser.toXml(root);
        PlistDebug.logTest("xml: " + xml);

        NSDictionary rootParsed = (NSDictionary) PlistXmlParser.fromXml(xml);
        CommandStatusMsg msgParsed = (CommandStatusMsg) PlistBeanConverter
                .createBeanFromNdict(rootParsed, CommandStatusMsg.class);
        assertEquals(root, rootParsed);
    }

    private static final String IDLE_XML_MSG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\"\n" +
            "\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "<key>Status</key>\n" +
            "<string>Idle</string>\n" +
            "<key>UDID</key>\n" +
            "<string>34ce984eb2c6d94f152144590492bf6dcce804d1</string>\n" +
            "</dict>\n" +
            "</plist>";

    private static final String ACK_XML_MSG = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\"\n" +
            "\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "<key>CommandUUID</key>\n" +
            "<string>51da6d87-8bd0-4c43-a7c3-377bdb260516</string>\n" +
            "<key>Status</key>\n" +
            "<string>Acknowledged</string>\n" +
            "<key>UDID</key>\n" +
            "<string>34ce984eb2c6d94f152144590492bf6dcce804d1</string>\n" +
            "</dict>\n" +
            "</plist>";
}
