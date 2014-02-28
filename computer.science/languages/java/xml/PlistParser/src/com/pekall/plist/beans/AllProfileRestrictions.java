package com.pekall.plist.beans;

/**
 * Containing the restrictions enforced by each profile. Only included
 * if ProfileRestrictions is set to true in the command.
 * The keys are the identifiers of the profiles.
 */
@SuppressWarnings("UnusedDeclaration")
public class AllProfileRestrictions {
    /**
     * Restrictions in the password profile. The key is the identifier of the profiles,
     * which is "com.pekall.mdm.password.profile". But it can not be a java variable name.
     * Use com_pekall_mdm_password_profile for its name and will translate it in XML string.
     */
    private Restrictions com_pekall_mdm_password_profile;

    public AllProfileRestrictions() {
    }

    public Restrictions getPasswordProfile() {
        return com_pekall_mdm_password_profile;
    }

    public void setPasswordProfile(Restrictions restrictions) {
        this.com_pekall_mdm_password_profile = restrictions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AllProfileRestrictions)) return false;

        AllProfileRestrictions that = (AllProfileRestrictions) o;

        return !(com_pekall_mdm_password_profile != null ? !com_pekall_mdm_password_profile.equals(that.com_pekall_mdm_password_profile) : that.com_pekall_mdm_password_profile != null);

    }

    @Override
    public int hashCode() {
        return com_pekall_mdm_password_profile != null ? com_pekall_mdm_password_profile.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "AllProfileRestrictions{" +
                "com_pekall_mdm_password_profile=" + com_pekall_mdm_password_profile +
                '}';
    }
}
