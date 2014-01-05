package com.pekall.plist.beans;

@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class LDAPSearchSetting {
    /**
     * see LDAPSearchSettingScope
     */
    public static final String SCOPE_BASE = "LDAPSearchSettingScopeBase";
    public static final String SCOPE_ONE_LEVEL = "LDAPSearchSettingScopeOneLevel";
    public static final String SCOPE_SUBTREE = "LDAPSearchSettingScopeSubtree";

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

    public LDAPSearchSetting() {
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
        if (!(o instanceof LDAPSearchSetting)) return false;

        LDAPSearchSetting that = (LDAPSearchSetting) o;

        if (LDAPSearchSettingDescription != null ? !LDAPSearchSettingDescription.equals(that.LDAPSearchSettingDescription) : that.LDAPSearchSettingDescription != null)
            return false;
        if (LDAPSearchSettingScope != null ? !LDAPSearchSettingScope.equals(that.LDAPSearchSettingScope) : that.LDAPSearchSettingScope != null)
            return false;
        return !(LDAPSearchSettingSearchBase != null ? !LDAPSearchSettingSearchBase.equals(that.LDAPSearchSettingSearchBase) : that.LDAPSearchSettingSearchBase != null);

    }

    @Override
    public int hashCode() {
        int result = LDAPSearchSettingDescription != null ? LDAPSearchSettingDescription.hashCode() : 0;
        result = 31 * result + (LDAPSearchSettingSearchBase != null ? LDAPSearchSettingSearchBase.hashCode() : 0);
        result = 31 * result + (LDAPSearchSettingScope != null ? LDAPSearchSettingScope.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LDAPSearchSetting{" +
                "LDAPSearchSettingDescription='" + LDAPSearchSettingDescription + '\'' +
                ", LDAPSearchSettingSearchBase='" + LDAPSearchSettingSearchBase + '\'' +
                ", LDAPSearchSettingScope='" + LDAPSearchSettingScope + '\'' +
                '}';
    }
}
