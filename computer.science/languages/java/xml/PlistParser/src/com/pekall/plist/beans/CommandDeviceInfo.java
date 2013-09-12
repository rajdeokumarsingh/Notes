package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

public class CommandDeviceInfo extends CommandObject {
    private List<String> Queries = new ArrayList<String>();

    public CommandDeviceInfo() {
        super(CommandObject.REQ_TYPE_DEVICE_INFO);

        Queries.add("UDID");
        Queries.add("DeviceName");
        Queries.add("OSVersion");
        Queries.add("BuildVersion");
        Queries.add("ModelName");
        Queries.add("Model");
        Queries.add("ProductName");
        Queries.add("SerialNumber");
        Queries.add("DeviceCapacity");
        Queries.add("AvailableDeviceCapacity");
        Queries.add("BatteryLevel");
        Queries.add("CellularTechnology");
        Queries.add("IMEI");
        Queries.add("MEID");
        Queries.add("ModemFirmwareVersion");
        Queries.add("ICCID");
        Queries.add("BluetoothMAC");
        Queries.add("WiFiMAC");
        Queries.add("EthernetMAC");
        Queries.add("CurrentCarrierNetwork");
        Queries.add("SIMCarrierNetwork");
        Queries.add("SubscriberCarrierNetwork");
        Queries.add("CarrierSettingsVersion");
        Queries.add("PhoneNumber");
        Queries.add("VoiceRoamingEnabled");
        Queries.add("DataRoamingEnabled");
        Queries.add("IsRoaming");
        Queries.add("SubscriberMCC");
        Queries.add("SubscriberMNC");
        Queries.add("SIMMCC");
        Queries.add("SIMMNC");
        Queries.add("CurrentMCC");
        Queries.add("CurrentMNC");
    }

    public List<String> getQueries() {
        return Queries;
    }

    public void setQueries(List<String> queries) {
        Queries = queries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandDeviceInfo)) return false;
        if (!super.equals(o)) return false;

        CommandDeviceInfo that = (CommandDeviceInfo) o;

        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        for (String query : Queries) {
            result += query.hashCode();
        }
        return result;
    }
}
