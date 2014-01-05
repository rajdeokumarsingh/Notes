package com.pekall.plist.beans;

import com.dd.plist.NSDictionary;
import com.pekall.plist.PlistBeanConverter;
import com.pekall.plist.PlistXmlParser;

import java.io.IOException;
import java.util.HashMap;

/**
 * Status message for command ManagedApplicationList from client to server
 */
@SuppressWarnings("UnusedDeclaration")
public class CommandManageAppListStatus extends CommandStatusMsg {
    /**
     * A dictionary of managed apps.
     */
    private ManagedAppList ManagedApplicationList;

    public CommandManageAppListStatus() {
    }

    public CommandManageAppListStatus(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    ManagedAppList getManagedApplicationList() {
        return ManagedApplicationList;
    }

    @Override
    public String toXml() {
        NSDictionary root = PlistBeanConverter.createNdictFromBean(this);
        NSDictionary nsAppList = (NSDictionary) root.objectForKey("ManagedApplicationList");
        ManagedAppList appList = this.getManagedApplicationList();
        if (nsAppList != null && appList != null) {
            HashMap<String, ManagedAppInfo> map = appList.getManagedApps();
            if (map != null) {
                for (String s : map.keySet()) {
                    nsAppList.put(s, PlistBeanConverter.createNdictFromBean(map.get(s)));
                }
            }
        }

        String xml;
        try {
            xml = PlistXmlParser.toXml(root);
        } catch (IOException e) {
            e.printStackTrace();
            xml = super.toXml();
        }
        return xml;
    }

    public void setManagedApplicationList(ManagedAppList managedApplicationList) {
        ManagedApplicationList = managedApplicationList;
    }

    public void addAppInfo(String AppId, ManagedAppInfo info) {
        if (ManagedApplicationList == null) {
            ManagedApplicationList = new ManagedAppList();
        }
        ManagedApplicationList.addAppInfo(AppId, info);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandManageAppListStatus)) return false;
        if (!super.equals(o)) return false;

        CommandManageAppListStatus that = (CommandManageAppListStatus) o;

        return !(ManagedApplicationList != null ? !ManagedApplicationList.equals(that.ManagedApplicationList) : that.ManagedApplicationList != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ManagedApplicationList != null ? ManagedApplicationList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandManageAppListStatus{" +
                "ManagedApplicationList=" + ManagedApplicationList.toString() +
                '}';
    }
}
