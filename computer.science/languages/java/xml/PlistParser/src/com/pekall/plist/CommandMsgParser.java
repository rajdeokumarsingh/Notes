package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.*;

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
            NSDictionary pc = (NSDictionary) root.objectForKey("Command");
            if (CommandObject.REQ_TYPE_CLEAR_PASSCODE.equals(msg.getRequestType())) {
                CommandClearPasscode msg1 = (CommandClearPasscode) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandClearPasscode.class);
                mMessage.setCommand(msg1);
            } else if (CommandObject.REQ_TYPE_DEVICE_INFO.equals(msg.getRequestType())) {
                CommandDeviceInfo msg1 = (CommandDeviceInfo) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandDeviceInfo.class);
                mMessage.setCommand(msg1);
            } else if (CommandObject.REQ_TYPE_INST_PROF.equals(msg.getRequestType())) {
                CommandInstallProfile msg1 = (CommandInstallProfile) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandInstallProfile.class);
                mMessage.setCommand(msg1);
            } else if (CommandObject.REQ_TYPE_RM_PROF.equals(msg.getRequestType())) {
                CommandRemoveProfile msg1 = (CommandRemoveProfile) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandRemoveProfile.class);
                mMessage.setCommand(msg1);
            } else if (CommandObject.REQ_TYPE_INST_PROV_PROF.equals(msg.getRequestType())) {
                CommandInstallProvisionProfile msg1 = (CommandInstallProvisionProfile) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandInstallProvisionProfile.class);
                mMessage.setCommand(msg1);
            } else if (CommandObject.REQ_TYPE_RM_PROV_PROF.equals(msg.getRequestType())) {
                CommandRemoveProvisionProfile msg1 = (CommandRemoveProvisionProfile) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandRemoveProvisionProfile.class);
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
