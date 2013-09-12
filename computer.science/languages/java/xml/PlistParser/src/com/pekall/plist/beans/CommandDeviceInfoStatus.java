package com.pekall.plist.beans;

/**
 * Status response for DeviceInformation
 */
public class CommandDeviceInfoStatus extends CommandStatusMsg {
    private DeviceInfoResp QueryResponses = new DeviceInfoResp();

    public CommandDeviceInfoStatus() {
    }

    public DeviceInfoResp getQueryResponses() {
        return QueryResponses;
    }

    public void setQueryResponses(DeviceInfoResp queryResponses) {
        QueryResponses = queryResponses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandDeviceInfoStatus)) return false;
        if (!super.equals(o)) return false;

        CommandDeviceInfoStatus that = (CommandDeviceInfoStatus) o;

        if (!QueryResponses.equals(that.QueryResponses)) return false;

        return true;
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
