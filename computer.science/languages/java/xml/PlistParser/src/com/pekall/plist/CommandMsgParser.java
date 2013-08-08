package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.CommandClearPasscode;
import com.pekall.plist.beans.CommandDeviceInfo;
import com.pekall.plist.beans.CommandMsg;
import com.pekall.plist.beans.CommandObject;

/**
 * Parser for XML command messages, just a simple wrapper
 */
public class CommandMsgParser {
    private CommandMsg mMessage;

    public CommandMsgParser(String xml) {
        try {
            NSDictionary root = (NSDictionary) PlistXmlParser.fromXml(xml);
            CommandMsg msg = (CommandMsg) PlistBeanConverter
                    .createBeanFromNdict(root, CommandMsg.class);
            mMessage = msg;
            if (CommandObject.REQ_TYPE_CLEAR_PASSCODE.equals(msg.getRequestType())) {
                NSDictionary pc = (NSDictionary) root.objectForKey("Command");
                CommandClearPasscode msg1 = (CommandClearPasscode) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandClearPasscode.class);
                mMessage.setCommand(msg1);
            } else if (CommandObject.REQ_TYPE_DEVICE_INFO.equals(msg.getRequestType())) {
                NSDictionary pc = (NSDictionary) root.objectForKey("Command");
                CommandDeviceInfo msg1 = (CommandDeviceInfo) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandDeviceInfo.class);
                mMessage.setCommand(msg1);
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public CommandMsg getMessage() {
        return mMessage;
    }
}
