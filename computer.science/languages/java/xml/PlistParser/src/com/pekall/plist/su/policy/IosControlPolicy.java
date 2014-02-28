package com.pekall.plist.su.policy;


/**
 * Policy for IOS devices
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class IosControlPolicy extends Policy {

    /**
     * Policy for different os version
     */
    private OsVersion os_version;

    /**
     * Policy when data protection disabled
     */
    private EventWrapper data_protetion_disable;

    /**
     * Policy when device is compromised
     */
    private EventWrapper device_compromised;

    /**
     * Policy when mdm is deactivated
     */
    private EventWrapper mdm_deactivated;

    /**
     * Policy for disallowed devices
     */
    private DisallowedDevices disallowed_devices;

    public IosControlPolicy() {
        this("", -1, "", new OsVersion(), new EventWrapper(),
                new EventWrapper(), new EventWrapper(), new DisallowedDevices());
    }

    private IosControlPolicy(String name, int status, String description,
                             OsVersion os_version,
                             EventWrapper data_protetion_disable,
                             EventWrapper device_compromised,
                             EventWrapper mdm_deactivated,
                             DisallowedDevices disallowed_devices) {
        super(name, status, description);
        this.os_version = os_version;
        this.data_protetion_disable = data_protetion_disable;
        this.device_compromised = device_compromised;
        this.mdm_deactivated = mdm_deactivated;
        this.disallowed_devices = disallowed_devices;
    }

    public OsVersion getOsVersion() {
        return os_version;
    }

    public void setOsVersion(OsVersion os_version) {
        this.os_version = os_version;
    }

    public EventWrapper getDataProtetionDisable() {
        return data_protetion_disable;
    }

    public void setDataProtetionDisable(EventWrapper data_protetion_disable) {
        this.data_protetion_disable = data_protetion_disable;
    }

    public EventWrapper getDeviceCompromised() {
        return device_compromised;
    }

    public void setDeviceCompromised(EventWrapper device_compromised) {
        this.device_compromised = device_compromised;
    }

    public EventWrapper getMdmDeactivated() {
        return mdm_deactivated;
    }

    public void setMdmDeactivated(EventWrapper mdm_deactivated) {
        this.mdm_deactivated = mdm_deactivated;
    }

    public DisallowedDevices getDisallowedDevices() {
        return disallowed_devices;
    }

    public void setDisallowedDevices(DisallowedDevices disallowed_devices) {
        this.disallowed_devices = disallowed_devices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IosControlPolicy)) return false;
        if (!super.equals(o)) return false;

        IosControlPolicy that = (IosControlPolicy) o;

        if (!data_protetion_disable.equals(that.data_protetion_disable)) return false;

        if (!device_compromised.equals(that.device_compromised)) return false;
        if (!disallowed_devices.equals(that.disallowed_devices)) return false;
        if (!mdm_deactivated.equals(that.mdm_deactivated)) return false;
        return os_version.equals(that.os_version);

    }

    @Override
    public int hashCode() {
        int result = os_version.hashCode();
        result = 31 * result + data_protetion_disable.hashCode();
        result = 31 * result + device_compromised.hashCode();
        result = 31 * result + mdm_deactivated.hashCode();
        result = 31 * result + disallowed_devices.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "IosControlPolicy{" +
                "os_version=" + os_version +
                ", data_protetion_disable=" + data_protetion_disable +
                ", device_compromised=" + device_compromised +
                ", mdm_deactivated=" + mdm_deactivated +
                ", disallowed_devices=" + disallowed_devices +
                '}';
    }
}
