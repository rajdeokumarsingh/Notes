package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Status response for ProfileList command
 */
public class CommandProfileListStatus extends CommandStatusMsg {

    /**
     * Array of dictionaries. Each entry describes an installed profile.
     * If the list is null, there are no profile installed.
     */
    List<PayloadArrayWrapper> ProfileList;

    public CommandProfileListStatus() {
    }

    public CommandProfileListStatus(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public CommandProfileListStatus(String status, String UDID,
                                    String commandUUID, List<PayloadArrayWrapper> list) {
        super(status, UDID, commandUUID);
        ProfileList = list;
    }

    public List<PayloadArrayWrapper> getProfileList() {
        return ProfileList;
    }

    public void setProfileList(List<PayloadArrayWrapper> profileList) {
        ProfileList = profileList;
    }

    public void addProfile(PayloadArrayWrapper profile) {
        if (ProfileList == null) {
            ProfileList = new ArrayList<PayloadArrayWrapper>();
        }
        ProfileList.add(profile);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandProfileListStatus)) return false;
        if (!super.equals(o)) return false;

        CommandProfileListStatus that = (CommandProfileListStatus) o;

        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        for (PayloadArrayWrapper wrapper : ProfileList) {
            result += wrapper.hashCode();
        }
        return result;
    }
}
