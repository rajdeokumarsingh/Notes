package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Status response for InstalledApplicationList command
 */
public class CommandInstalledAppListStatus extends CommandStatusMsg {
    /**
     * Array of installed applications.
     */
    List<InstalledAppInfo> InstalledApplicationList;

    public CommandInstalledAppListStatus() {
    }

    public CommandInstalledAppListStatus(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public CommandInstalledAppListStatus(String status, String UDID,
                                         String commandUUID, List<InstalledAppInfo> list) {
        super(status, UDID, commandUUID);
        InstalledApplicationList = list;
    }

    public List<InstalledAppInfo> getInstalledApplicationList() {
        return InstalledApplicationList;
    }

    public void setInstalledApplicationList(List<InstalledAppInfo> installedApplicationList) {
        InstalledApplicationList = installedApplicationList;
    }

    public void addAppInfo(InstalledAppInfo info) {
        if (InstalledApplicationList == null) {
            InstalledApplicationList = new ArrayList<InstalledAppInfo>();
        }
        InstalledApplicationList.add(info);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandInstalledAppListStatus)) return false;
        if (!super.equals(o)) return false;

        CommandInstalledAppListStatus that = (CommandInstalledAppListStatus) o;

        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        for (InstalledAppInfo item : InstalledApplicationList) {
            result += item.hashCode();
        }
        return result;
    }
}
