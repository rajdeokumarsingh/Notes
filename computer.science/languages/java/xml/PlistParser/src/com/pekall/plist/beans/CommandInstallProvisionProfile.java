package com.pekall.plist.beans;

import java.util.Arrays;

/**
 * Command to install provisioning profiles
 */
@SuppressWarnings("UnusedDeclaration")
public class CommandInstallProvisionProfile extends CommandObject {

    public static final String KEY_PROVISIONING_PROFILE = "ProvisioningProfile";
    /**
     * The provisioning profile to install.
     */
    private byte[] ProvisioningProfile;

    public CommandInstallProvisionProfile() {
        super(CommandObject.REQ_TYPE_INST_PROV_PROF);
    }

    public CommandInstallProvisionProfile(byte[] payload) {
        super(CommandObject.REQ_TYPE_INST_PROV_PROF);
        ProvisioningProfile = payload;
    }

    public byte[] getProvisioningProfile() {
        return ProvisioningProfile;
    }

    public void setProvisioningProfile(byte[] provisioningProfile) {
        ProvisioningProfile = provisioningProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandInstallProvisionProfile)) return false;
        if (!super.equals(o)) return false;

        CommandInstallProvisionProfile that = (CommandInstallProvisionProfile) o;

        return Arrays.equals(ProvisioningProfile, that.ProvisioningProfile);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ProvisioningProfile != null ? Arrays.hashCode(ProvisioningProfile) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandInstallProvisionProfile{" +
                "ProvisioningProfile=" + Arrays.toString(ProvisioningProfile) +
                '}';
    }
}
