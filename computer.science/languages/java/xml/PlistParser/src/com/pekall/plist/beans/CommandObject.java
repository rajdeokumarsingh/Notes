package com.pekall.plist.beans;

import com.pekall.plist.Utils;

/**
 * Base class for server commands
 */
public class CommandObject {
    /**  Empty message for ending a session */
    public static final String REQ_TYPE_EMPTY_MSG = "EmptyMessage";

    /**  Locks the Device Immediately */
    public static final String REQ_TYPE_DEVICE_LOCK = "DeviceLock";

    /** Remotely Erase a Device */
    public static final String REQ_TYPE_ERASE_DEVICE = "EraseDevice";

    /** Clear the Passcode for a Device */
    public static final String REQ_TYPE_CLEAR_PASSCODE = "ClearPasscode";

    /** Get information about the device */
    public static final String REQ_TYPE_DEVICE_INFO = "DeviceInformation";

    /**  Install a Configuration Profile */
    public static final String REQ_TYPE_INST_PROF = "InstallProfile";

    /** Remove a Profile From the Device */
    public static final String REQ_TYPE_RM_PROF = "RemoveProfile";

    /** Get a list of installed profiles */
    public static final String REQ_TYPE_PROF_LIST = "ProfileList";

    /** Get a List of Installed Provisioning Profiles */
    public static final String REQ_TYPE_PROV_PROF_LIST = "ProvisioningProfileList";

    /** Install Provisioning Profiles */
    public static final String REQ_TYPE_INST_PROV_PROF = "InstallProvisioningProfile";

    /** Remove Installed Provisioning Profiles */
    public static final String REQ_TYPE_RM_PROV_PROF = "RemoveProvisioningProfile";

    /** Get a List of Installed Certificates */
    public static final String REQ_TYPE_CERT_LIST = "CertificateList";

    /** Get a List of Third-Party Applications */
    public static final String REQ_TYPE_INST_APP_LIST = "InstalledApplicationList";

    /** Request Security-Related Information */
    public static final String REQ_TYPE_SECURITY_INFO = "SecurityInfo";

    // Request type, see REQ_TYPE_DEVICE_LOCK, ....
    private String RequestType;

    public CommandObject() {
    }

    public CommandObject(String requestType) {
        RequestType = requestType;
    }

    public String getRequestType() {
        return RequestType;
    }

    public void setRequestType(String requestType) {
        RequestType = requestType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandObject)) return false;

        CommandObject that = (CommandObject) o;

        if (!Utils.safeString(RequestType)
                .equals(Utils.safeString(that.RequestType))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Utils.safeString(RequestType).hashCode();
    }

    @Override
    public String toString() {
        return "CommandObject{" +
                "RequestType='" + RequestType + '\'' +
                '}';
    }
}
