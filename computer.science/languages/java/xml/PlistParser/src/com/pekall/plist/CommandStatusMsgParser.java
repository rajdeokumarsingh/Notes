package com.pekall.plist;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.pekall.plist.beans.*;
import com.pekall.plist.su.CommandDeviceInfoStatusSU;
import com.pekall.plist.su.CommandSettingsStatusSU;
import com.pekall.plist.su.settings.advertise.AdvertiseInfo;
import com.pekall.plist.su.settings.browser.BrowserUploadData;

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
            } else if (root.objectForKey("GlobalRestrictions") != null ||
                    root.objectForKey("ProfileRestrictions") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandRestrictionsStatus.class);
            } else if (root.objectForKey("Identifier") != null ||
                    root.objectForKey("RejectionReason") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandInstallAppStatus.class);
            } else if (root.objectForKey("ManagedApplicationList") != null) {
                CommandManageAppListStatus status = (CommandManageAppListStatus) PlistBeanConverter
                        .createBeanFromNdict(root, CommandManageAppListStatus.class);

                NSDictionary nsAppList = (NSDictionary) root.objectForKey("ManagedApplicationList");
                if (nsAppList != null) {
                    for (String s : nsAppList.keySet()) {
                        ManagedAppInfo info = (ManagedAppInfo) PlistBeanConverter.createBeanFromNdict(
                                (NSDictionary) nsAppList.objectForKey(s), ManagedAppInfo.class);
                        status.addAppInfo(s, info);
                    }
                }
                mMessage = status;
            } else if (root.objectForKey("Settings") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandSettingsStatus.class);
            } else if (root.objectForKey("Longitude") != null ||
                    root.objectForKey("Latitude") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandLocationStatus.class);
            } else if (root.objectForKey("advertiseStaInfos") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, AdvertiseInfo.class);
            } else if (root.objectForKey("historyWatchItems") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, BrowserUploadData.class);
            } else if (root.objectForKey("settingsSU") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandSettingsStatusSU.class);
            } else if (root.objectForKey("QueryResponsesSU") != null) {
                mMessage = (CommandStatusMsg) PlistBeanConverter
                        .createBeanFromNdict(root, CommandDeviceInfoStatusSU.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandStatusMsg getMessage() {
        return mMessage;
    }
}
