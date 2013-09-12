package com.pekall.plist.su;

import com.pekall.plist.beans.CommandStatusMsg;
import com.pekall.plist.su.device.DeviceInfoRespSU;

/**
 * Status response for DeviceInformation
 */
public class CommandDeviceInfoStatusSU extends CommandStatusMsg {
    private DeviceInfoRespSU QueryResponsesSU = new DeviceInfoRespSU();

    public CommandDeviceInfoStatusSU() {
    }

    public CommandDeviceInfoStatusSU(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public DeviceInfoRespSU getQueryResponses() {
        return QueryResponsesSU;
    }

    public void setQueryResponses(DeviceInfoRespSU queryResponsesSU) {
        QueryResponsesSU = queryResponsesSU;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandDeviceInfoStatusSU)) return false;
        if (!super.equals(o)) return false;

        CommandDeviceInfoStatusSU that = (CommandDeviceInfoStatusSU) o;

        if (QueryResponsesSU != null ? !QueryResponsesSU.equals(that.QueryResponsesSU) : that.QueryResponsesSU != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (QueryResponsesSU != null ? QueryResponsesSU.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandDeviceInfoStatusSU{" +
                "QueryResponsesSU=" + QueryResponsesSU +
                '}';
    }
}
