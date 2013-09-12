package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Status response for ProvisioningProfileList command
 */
public class CommandProvisionProfileListStatus extends CommandStatusMsg {
    /**
     * Array of dictionaries. Each entry describes a provisioning profile.
     * If the list is null, there are no profile installed.
     */
    List<ProvisionProfileItem> ProvisioningProfileList;

    public CommandProvisionProfileListStatus() {
    }

    public CommandProvisionProfileListStatus(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public CommandProvisionProfileListStatus(String status, String UDID,
                                             String commandUUID, List<ProvisionProfileItem> list) {
        super(status, UDID, commandUUID);
        ProvisioningProfileList = list;
    }

    public List<ProvisionProfileItem> getProvisioningProfileList() {
        return ProvisioningProfileList;
    }

    public void setProvisioningProfileList(List<ProvisionProfileItem> provisioningProfileList) {
        ProvisioningProfileList = provisioningProfileList;
    }

    public void addProfile(ProvisionProfileItem profile) {
        if (ProvisioningProfileList == null) {
            ProvisioningProfileList = new ArrayList<ProvisionProfileItem>();
        }
        ProvisioningProfileList.add(profile);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandProvisionProfileListStatus)) return false;
        if (!super.equals(o)) return false;

        CommandProvisionProfileListStatus that = (CommandProvisionProfileListStatus) o;

        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        for (ProvisionProfileItem item : ProvisioningProfileList) {
            result += item.hashCode();
        }
        return result;
    }
}
