package com.pekall.plist.beans;

/**
 * Command to remove a provisioning profile
 */
@SuppressWarnings("UnusedDeclaration")
public class CommandRemoveProvisionProfile extends CommandObject {

    public static final String KEY_UUID = "UUID";
    /**
     * The UUID of the provisioning profile to remove.
     */
    private String UUID;

    public CommandRemoveProvisionProfile() {
        super(CommandObject.REQ_TYPE_RM_PROV_PROF);
    }

    public CommandRemoveProvisionProfile(String UUID) {
        super(CommandObject.REQ_TYPE_RM_PROV_PROF);
        this.UUID = UUID;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandRemoveProvisionProfile)) return false;
        if (!super.equals(o)) return false;

        CommandRemoveProvisionProfile that = (CommandRemoveProvisionProfile) o;

        return !(UUID != null ? !UUID.equals(that.UUID) : that.UUID != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (UUID != null ? UUID.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandRemoveProvisionProfile{" +
                "super='" + super.toString() + '\'' +
                "UUID='" + UUID + '\'' +
                '}';
    }
}
