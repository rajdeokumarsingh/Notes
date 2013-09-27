package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * An LDAP payload provides information about an LDAP server to use, including account information
 * if required, and a set of LDAP search policies to use when querying that LDAP server.
 */
public class PayloadLDAP extends PayloadBase {

    /**
     * Optional. Description of the account.
     */
    private String LDAPAccountDescription;

    /**
     * LDAPAccountHostName
     */
    private String LDAPAccountHostName;

    /**
     * Whether or not to use SSL.
     */
    private Boolean LDAPAccountUseSSL;

    /**
     * Optional. The username.
     */
    private String LDAPAccountUserName;

    /**
     * Optional. Use only with encrypted profiles.
     */
    private String LDAPAccountPassword;

    /**
     * Top level container object. Can have many of these for one account. Should have
     * at least one for the account to be useful. Each LDAPSearchSettings object represents
     * a node in the LDAP tree to start searching from, and tells what scope to search in
     * (the node, the node plus one level of children,
     * or the node plus all levels of children).
     */
    private List<LDAPSearchSetting> LDAPSearchSettings;

    public PayloadLDAP() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_LDAP);
    }

    public String getLDAPAccountDescription() {
        return LDAPAccountDescription;
    }

    public void setLDAPAccountDescription(String LDAPAccountDescription) {
        this.LDAPAccountDescription = LDAPAccountDescription;
    }

    public String getLDAPAccountHostName() {
        return LDAPAccountHostName;
    }

    public void setLDAPAccountHostName(String LDAPAccountHostName) {
        this.LDAPAccountHostName = LDAPAccountHostName;
    }

    public Boolean getLDAPAccountUseSSL() {
        return LDAPAccountUseSSL;
    }

    public void setLDAPAccountUseSSL(Boolean LDAPAccountUseSSL) {
        this.LDAPAccountUseSSL = LDAPAccountUseSSL;
    }

    public String getLDAPAccountUserName() {
        return LDAPAccountUserName;
    }

    public void setLDAPAccountUserName(String LDAPAccountUserName) {
        this.LDAPAccountUserName = LDAPAccountUserName;
    }

    public String getLDAPAccountPassword() {
        return LDAPAccountPassword;
    }

    public void setLDAPAccountPassword(String LDAPAccountPassword) {
        this.LDAPAccountPassword = LDAPAccountPassword;
    }

    public List<LDAPSearchSetting> getLDAPSearchSettings() {
        return LDAPSearchSettings;
    }

    public void setLDAPSearchSettings(List<LDAPSearchSetting> LDAPSearchSettings) {
        this.LDAPSearchSettings = LDAPSearchSettings;
    }

    public void addLDAPSearchSetting(LDAPSearchSetting setting) {
        if (LDAPSearchSettings == null) {
            LDAPSearchSettings = new ArrayList<LDAPSearchSetting>();
        }
        LDAPSearchSettings.add(setting);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadLDAP)) return false;
        if (!super.equals(o)) return false;

        PayloadLDAP that = (PayloadLDAP) o;

        if (LDAPAccountDescription != null ? !LDAPAccountDescription.equals(that.LDAPAccountDescription) : that.LDAPAccountDescription != null)
            return false;
        if (LDAPAccountHostName != null ? !LDAPAccountHostName.equals(that.LDAPAccountHostName) : that.LDAPAccountHostName != null)
            return false;
        if (LDAPAccountPassword != null ? !LDAPAccountPassword.equals(that.LDAPAccountPassword) : that.LDAPAccountPassword != null)
            return false;
        if (LDAPAccountUseSSL != null ? !LDAPAccountUseSSL.equals(that.LDAPAccountUseSSL) : that.LDAPAccountUseSSL != null)
            return false;
        if (LDAPAccountUserName != null ? !LDAPAccountUserName.equals(that.LDAPAccountUserName) : that.LDAPAccountUserName != null)
            return false;
        if (LDAPSearchSettings != null ? !LDAPSearchSettings.equals(that.LDAPSearchSettings) : that.LDAPSearchSettings != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (LDAPAccountDescription != null ? LDAPAccountDescription.hashCode() : 0);
        result = 31 * result + (LDAPAccountHostName != null ? LDAPAccountHostName.hashCode() : 0);
        result = 31 * result + (LDAPAccountUseSSL != null ? LDAPAccountUseSSL.hashCode() : 0);
        result = 31 * result + (LDAPAccountUserName != null ? LDAPAccountUserName.hashCode() : 0);
        result = 31 * result + (LDAPAccountPassword != null ? LDAPAccountPassword.hashCode() : 0);
        result = 31 * result + (LDAPSearchSettings != null ? LDAPSearchSettings.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadLDAP{" +
                "LDAPAccountDescription='" + LDAPAccountDescription + '\'' +
                ", LDAPAccountHostName='" + LDAPAccountHostName + '\'' +
                ", LDAPAccountUseSSL=" + LDAPAccountUseSSL +
                ", LDAPAccountUserName='" + LDAPAccountUserName + '\'' +
                ", LDAPAccountPassword='" + LDAPAccountPassword + '\'' +
                ", LDAPSearchSettings=" + LDAPSearchSettings +
                '}';
    }
}
