package com.pekall.plist.su.policy;


/**
 * Policy for Android devices
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class AndroidControlPolicy extends Policy {
    /**
     * Policy for different os version
     */
    private OsVersion os_version;

    /**
     * Policy when device is compromised
     */
    private EventWrapper device_compromised;

    /**
     * Policy when data protection disabled
     */
    private EventWrapper data_encryption_disable;

    /**
     * Policy when mdm is deactivated
     */
    private EventWrapper mdm_deactivated;

    public AndroidControlPolicy() {
        this("", -1, "", new OsVersion(),
                new EventWrapper(), new EventWrapper(), new EventWrapper());
    }

    private AndroidControlPolicy(String name, int status, String description, OsVersion os_version,
                                 EventWrapper device_compromised, EventWrapper data_encryption_disable,
                                 EventWrapper mdm_deactivated) {
        super(name, status, description);
        this.os_version = os_version;
        this.device_compromised = device_compromised;
        this.data_encryption_disable = data_encryption_disable;
        this.mdm_deactivated = mdm_deactivated;
    }

    public OsVersion getOsVersion() {
        return os_version;
    }

    public void setOsVersion(OsVersion os_version) {
        this.os_version = os_version;
    }

    public EventWrapper getDeviceCompromised() {
        return device_compromised;
    }

    public void setDeviceCompromised(EventWrapper device_compromised) {
        this.device_compromised = device_compromised;
    }

    public EventWrapper getDataEncryptionDisable() {
        return data_encryption_disable;
    }

    public void setDataEncryptionDisable(EventWrapper data_encryption_disable) {
        this.data_encryption_disable = data_encryption_disable;
    }

    public EventWrapper getMdmDeactivated() {
        return mdm_deactivated;
    }

    public void setMdmDeactivated(EventWrapper mdm_deactivated) {
        this.mdm_deactivated = mdm_deactivated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AndroidControlPolicy)) return false;
        if (!super.equals(o)) return false;

        AndroidControlPolicy that = (AndroidControlPolicy) o;

        if (!data_encryption_disable.equals(that.data_encryption_disable)) return false;
        if (!device_compromised.equals(that.device_compromised)) return false;
        if (!mdm_deactivated.equals(that.mdm_deactivated)) return false;
        return os_version.equals(that.os_version);

    }

    @Override
    public int hashCode() {
        int result = os_version.hashCode();
        result = 31 * result + device_compromised.hashCode();
        result = 31 * result + data_encryption_disable.hashCode();
        result = 31 * result + mdm_deactivated.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "AndroidControlPolicy{" +
                "os_version=" + os_version +
                ", device_compromised=" + device_compromised +
                ", data_encryption_disable=" + data_encryption_disable +
                ", mdm_deactivated=" + mdm_deactivated +
                '}';
    }
}
