package com.pekall.plist;

import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.*;

/**
 * Parser for XML status messages, just a simple wrapper
 */
public class CommandStatusMsgParser {
    private CommandStatusMsg mMessage;

    public CommandStatusMsgParser(String xml) {
        try {
            NSDictionary root = (NSDictionary) PlistXmlParser.fromXml(xml);
            CommandStatusMsg msg = (CommandStatusMsg) PlistBeanConverter
                    .createBeanFromNdict(root, CommandStatusMsg.class);
            mMessage = msg;
            if (root.objectForKey("QueryResponses") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandDeviceInfoStatus.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandStatusMsg getMessage() {
        return mMessage;
    }
}
