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

            CommandObject command = null;
            NSDictionary pc = (NSDictionary) root.objectForKey("Command");
            if (CommandObject.REQ_TYPE_CLEAR_PASSCODE.equals(msg.getRequestType())) {
                command = (CommandObject) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandClearPasscode.class);
            } else if (CommandObject.REQ_TYPE_DEVICE_INFO.equals(msg.getRequestType())) {
                command = (CommandObject) PlistBeanConverter.createBeanFromNdict(pc, CommandDeviceInfo.class);
            } else if (CommandObject.REQ_TYPE_INST_PROF.equals(msg.getRequestType())) {
                command = (CommandObject) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandInstallProfile.class);
            } else if (CommandObject.REQ_TYPE_RM_PROF.equals(msg.getRequestType())) {
                command = (CommandObject) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandRemoveProfile.class);
            } else if (CommandObject.REQ_TYPE_INST_PROV_PROF.equals(msg.getRequestType())) {
                command = (CommandObject) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandInstallProvisionProfile.class);
            } else if (CommandObject.REQ_TYPE_RM_PROV_PROF.equals(msg.getRequestType())) {
                command = (CommandObject) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandRemoveProvisionProfile.class);
            } else if (CommandObject.REQ_TYPE_RESTRICTIONS.equals(msg.getRequestType())) {
                command = (CommandObject) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandRestrictions.class);
            } else if (CommandObject.REQ_TYPE_INST_APP.equals(msg.getRequestType())) {
                command = (CommandObject) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandInstallApp.class);
            } else if (CommandObject.REQ_TYPE_APPLY_REDEMPTION_CODE.equals(msg.getRequestType())) {
                command = (CommandObject) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandApplyRedemptionCode.class);
            } else if (CommandObject.REQ_TYPE_RM_APP.equals(msg.getRequestType())) {
                command = (CommandObject) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandRemoveApp.class);
            } else if (CommandObject.REQ_TYPE_SETTINGS.equals(msg.getRequestType())) {
                command = (CommandObject) PlistBeanConverter
                        .createBeanFromNdict(pc, CommandSettings.class);
            }
            if (command != null)  mMessage.setCommand(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandMsg getMessage() {
        return mMessage;
    }
}
