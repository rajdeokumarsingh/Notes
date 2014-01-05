package com.pekall.plist.beans;

import com.pekall.plist.su.device.DeviceInfoRespSU;

/**
 * Status response for DeviceInformation
 */
@SuppressWarnings("UnusedDeclaration")
public class CommandDeviceInfoStatus extends CommandStatusMsg {
    private DeviceInfoRespSU QueryResponses = new DeviceInfoRespSU();

    public CommandDeviceInfoStatus() {
    }

    public DeviceInfoRespSU getQueryResponses() {
        return QueryResponses;
    }

    public void setQueryResponses(DeviceInfoRespSU queryResponses) {
        QueryResponses = queryResponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandDeviceInfoStatus)) return false;
        if (!super.equals(o)) return false;

        CommandDeviceInfoStatus that = (CommandDeviceInfoStatus) o;

        return QueryResponses.equals(that.QueryResponses);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + QueryResponses.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CommandDeviceInfoStatus{" +
                "supper=" + super.toString() +
                "QueryResponses=" + QueryResponses.toString() +
                '}';
    }
}
