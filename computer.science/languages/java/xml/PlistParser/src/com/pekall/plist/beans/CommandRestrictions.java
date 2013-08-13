package com.pekall.plist.beans;

/**
 * Restrictions commands get a list of installed restrictions
 */
public class CommandRestrictions extends CommandObject {

    /**
     * Optional. If true, the device will report restrictions enforced by each profile.
     */
    private boolean ProfileRestrictions;

    public CommandRestrictions() {
        super(CommandObject.REQ_TYPE_RESTRICTIONS);
    }

    public CommandRestrictions(boolean profileRestrictions) {
        super(CommandObject.REQ_TYPE_RESTRICTIONS);
        ProfileRestrictions = profileRestrictions;
    }

    public boolean isProfileRestrictions() {
        return ProfileRestrictions;
    }

    public void setProfileRestrictions(boolean profileRestrictions) {
        ProfileRestrictions = profileRestrictions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandRestrictions)) return false;
        if (!super.equals(o)) return false;

        CommandRestrictions that = (CommandRestrictions) o;

        if (ProfileRestrictions != that.ProfileRestrictions) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (ProfileRestrictions ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandRestrictions{" +
                "super=" + super.toString() + "," +
                "ProfileRestrictions=" + ProfileRestrictions +
                '}';
    }
}
