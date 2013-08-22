package com.pekall.plist.beans;

/**
 * An LDAP payload provides information about an LDAP server to use, including account information
 * if required, and a set of LDAP search policies to use when querying that LDAP server.
 */
public class PayloadLDAP extends PayloadBase {

    /**
     * see LDAPSearchSettingScope
     */
    public static final String SCOPE_BASE = "LDAPSearchSettingScopeBase";
    public static final String SCOPE_ONE_LEVEL = "LDAPSearchSettingScopeOneLevel";
    public static final String SCOPE_SUBTREE = "LDAPSearchSettingScopeSubtree";

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

    // TODO: find the definition of it
    // LDAPSearchSettings

    /**
     * Optional. Description of this search setting.
     */
    private String LDAPSearchSettingDescription;

    /**
     * Conceptually, the path to the node to start a search at.
     * For example: ou=people,o=example corp
     */
    private String LDAPSearchSettingSearchBase;

    /**
     * Defines what recursion to use in the search. Can be one of the following 3 values:
     * LDAPSearchSettingScopeBase: Just the immediate node pointed to by SearchBase
     * LDAPSearchSettingScopeOneLevel: The node plus its immediate children.
     * LDAPSearchSettingScopeSubtree: The node plus all children, regardless of depth.
     * See SCOPE_BASE, SCOPE_ONE_LEVEL, SCOPE_SUBTREE
     */
    private String LDAPSearchSettingScope;

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

    public String getLDAPSearchSettingDescription() {
        return LDAPSearchSettingDescription;
    }

    public void setLDAPSearchSettingDescription(String LDAPSearchSettingDescription) {
        this.LDAPSearchSettingDescription = LDAPSearchSettingDescription;
    }

    public String getLDAPSearchSettingSearchBase() {
        return LDAPSearchSettingSearchBase;
    }

    public void setLDAPSearchSettingSearchBase(String LDAPSearchSettingSearchBase) {
        this.LDAPSearchSettingSearchBase = LDAPSearchSettingSearchBase;
    }

    public String getLDAPSearchSettingScope() {
        return LDAPSearchSettingScope;
    }

    public void setLDAPSearchSettingScope(String LDAPSearchSettingScope) {
        this.LDAPSearchSettingScope = LDAPSearchSettingScope;
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
        if (LDAPSearchSettingDescription != null ? !LDAPSearchSettingDescription.equals(that.LDAPSearchSettingDescription) : that.LDAPSearchSettingDescription != null)
            return false;
        if (LDAPSearchSettingScope != null ? !LDAPSearchSettingScope.equals(that.LDAPSearchSettingScope) : that.LDAPSearchSettingScope != null)
            return false;
        if (LDAPSearchSettingSearchBase != null ? !LDAPSearchSettingSearchBase.equals(that.LDAPSearchSettingSearchBase) : that.LDAPSearchSettingSearchBase != null)
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
        result = 31 * result + (LDAPSearchSettingDescription != null ? LDAPSearchSettingDescription.hashCode() : 0);
        result = 31 * result + (LDAPSearchSettingSearchBase != null ? LDAPSearchSettingSearchBase.hashCode() : 0);
        result = 31 * result + (LDAPSearchSettingScope != null ? LDAPSearchSettingScope.hashCode() : 0);
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
                ", LDAPSearchSettingDescription='" + LDAPSearchSettingDescription + '\'' +
                ", LDAPSearchSettingSearchBase='" + LDAPSearchSettingSearchBase + '\'' +
                ", LDAPSearchSettingScope='" + LDAPSearchSettingScope + '\'' +
                '}';
    }
}
