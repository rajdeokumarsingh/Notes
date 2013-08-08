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
