package com.pekall.plist.beans;

/**
 * Status message for "Restrictions" command from client to server
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class CommandRestrictionsStatus extends CommandStatusMsg {

    /**
     * containing the global restrictions currently in effect.
     */
    private Restrictions GlobalRestrictions;

    /**
     * containing the restrictions enforced by each profile. Only included
     * if ProfileRestrictions is set to true in the command.
     * The keys are the identifiers of the profiles.
     */
    private AllProfileRestrictions ProfileRestrictions;

    public CommandRestrictionsStatus() {
    }

    public CommandRestrictionsStatus(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public Restrictions getGlobalRestrictions() {
        return GlobalRestrictions;
    }

    public void setGlobalRestrictions(Restrictions globalRestrictions) {
        GlobalRestrictions = globalRestrictions;
    }

    public AllProfileRestrictions getProfileRestrictions() {
        return ProfileRestrictions;
    }

    public void setProfileRestrictions(AllProfileRestrictions profileRestrictions) {
        ProfileRestrictions = profileRestrictions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandRestrictionsStatus)) return false;
        if (!super.equals(o)) return false;

        CommandRestrictionsStatus that = (CommandRestrictionsStatus) o;

        if (GlobalRestrictions != null ? !GlobalRestrictions.equals(that.GlobalRestrictions) : that.GlobalRestrictions != null)
            return false;
        return !(ProfileRestrictions != null ? !ProfileRestrictions.equals(that.ProfileRestrictions) : that.ProfileRestrictions != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (GlobalRestrictions != null ? GlobalRestrictions.hashCode() : 0);
        result = 31 * result + (ProfileRestrictions != null ? ProfileRestrictions.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CommandRestrictionsStatus{" +
                "GlobalRestrictions=" + GlobalRestrictions +
                ", ProfileRestrictions=" + ProfileRestrictions +
                '}';
    }
}
