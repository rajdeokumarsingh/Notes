package com.pekall.plist.su.policy;

import java.util.ArrayList;
import java.util.List;

/**
 * XML element for ios_control_policy.device_compromised
 */
public class DisallowedDevices extends EventWrapper {
    List<String> devices = new ArrayList<String>();

    public DisallowedDevices() {
        super();
    }

    public DisallowedDevices(String event, List<String> devices) {
        super(event);
        this.devices = devices;
    }

    public List<String> getDevices() {
        return devices;
    }

    public void setDevices(List<String> devices) {
        this.devices = devices;
    }

    public void addDevice(String device) {
        devices.add(device);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DisallowedDevices)) return false;
        if (!super.equals(o)) return false;

        DisallowedDevices that = (DisallowedDevices) o;
        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        for(String s : devices) {
            result = 31 * result + s.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        for(String s : devices) {
            sb.append(s + ",");
        }
        sb.append("}");
        return "DisallowedDevices{" +
                "super=" + super.toString() +
                "devices=" + sb.toString() +
                '}';
    }
}
