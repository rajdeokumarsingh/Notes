package com.pekall.plist;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Parser for XML status messages, just a simple wrapper
 */
public class CommandStatusMsgParser {
    private CommandStatusMsg mMessage;

    public CommandStatusMsgParser(String xml) {
        try {
            NSDictionary root = (NSDictionary) PlistXmlParser.fromXml(xml);
            mMessage = (CommandStatusMsg) PlistBeanConverter
                    .createBeanFromNdict(root, CommandStatusMsg.class);
            if (root.objectForKey("QueryResponses") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandDeviceInfoStatus.class);
            } else if (root.objectForKey("ProfileList") != null) {
                List<PayloadArrayWrapper> wrappers = new ArrayList<PayloadArrayWrapper>();
                NSArray profiles = (NSArray) root.objectForKey("ProfileList");
                for (int i = 0; i < profiles.count(); i++) {
                    NSDictionary profile = (NSDictionary) profiles.objectAtIndex(i);
                    PayloadXmlMsgParser parser = new PayloadXmlMsgParser(profile);
                    wrappers.add((PayloadArrayWrapper) parser.getPayloadDescriptor());
                }

                CommandProfileListStatus statusMsg = (CommandProfileListStatus) PlistBeanConverter
                        .createBeanFromNdict(root, CommandProfileListStatus.class);
                statusMsg.setProfileList(wrappers);
                mMessage = statusMsg;
            } else if (root.objectForKey("ProvisioningProfileList") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandProvisionProfileListStatus.class);
            } else if (root.objectForKey("CertificateList") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandCertificateListStatus.class);
            } else if (root.objectForKey("InstalledApplicationList") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandInstalledAppListStatus.class);
            } else if (root.objectForKey("SecurityInfo") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandSecurityInfoStatus.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandStatusMsg getMessage() {
        return mMessage;
    }
}
