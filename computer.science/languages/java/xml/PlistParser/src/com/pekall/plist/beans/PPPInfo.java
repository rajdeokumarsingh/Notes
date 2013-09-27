package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

public class PPPInfo {
    /**
     * See AuthEAPPlugins
     */
    public static final String AUTH_PLUGIN_ENTRY = "EAP-RSA";

    /**
     * See AuthProtocol
     */
    public static final String AUTH_PROT_ENTRY = "EAP";

    /**
     * The VPN account user name. Used for L2TP and PPTP.
     */
    private String AuthName;

    /**
     * Optional. Only visible if TokenCard is false. Used for L2TP and PPTP.
     */
    private String AuthPassword;

    /**
     * Whether to use a token card such as an RSA SecurID card for connecting. Used for L2TP.
     */
    private Boolean TokenCard;

    /**
     * IP address or host name of VPN server. Used for L2TP and PPTP.
     */
    private String CommRemoteAddress;

    /**
     * Only present if RSA SecurID is being used, in which case it has one entry,
     * a string with value "EAP-RSA". Used for L2TP and PPTP.
     */
    private List<String> AuthEAPPlugins;

    /**
     * Only present if RSA SecurID is being used, in which case it has one entry,
     * a string with value "EAP". Used for L2TP and PPTP.
     */
    private List<String> AuthProtocol;

    /**
     * See discussion under CCPEnabled. Used for PPTP.
     */
    private Integer CCPMPPE40Enabled;

    /**
     * See discussion under CCPEnabled. Used for PPTP.
     */
    private Integer CCPMPPE128Enabled;

    /**
     * Enables encryption on the connection. If this key and CCPMPPE40Enabled are true,
     * represents automatic encryption level; if this key and CCPMPPE128Enabled are true,
     * represents maximum encryption level. If no encryption is used,
     * then none of the CCP keys are true. Used for PPTP.
     */
    private Integer CCPEnabled;

    public String getAuthName() {
        return AuthName;
    }

    public void setAuthName(String authName) {
        AuthName = authName;
    }

    public String getAuthPassword() {
        return AuthPassword;
    }

    public void setAuthPassword(String authPassword) {
        AuthPassword = authPassword;
    }

    public Boolean getTokenCard() {
        return TokenCard;
    }

    public void setTokenCard(Boolean tokenCard) {
        TokenCard = tokenCard;
    }

    public String getCommRemoteAddress() {
        return CommRemoteAddress;
    }

    public void setCommRemoteAddress(String commRemoteAddress) {
        CommRemoteAddress = commRemoteAddress;
    }

    public void enableAuthEAPPlugins() {
        if (AuthEAPPlugins == null) {
            AuthEAPPlugins = new ArrayList<String>();
        }
        AuthEAPPlugins.add(AUTH_PLUGIN_ENTRY);
    }

    public void enableAuthProtocol() {
        if (AuthProtocol == null) {
            AuthProtocol = new ArrayList<String>();
        }
        AuthProtocol.add(AUTH_PROT_ENTRY);
    }

    public Integer getCCPMPPE40Enabled() {
        return CCPMPPE40Enabled;
    }

    public void setCCPMPPE40Enabled(Integer CCPMPPE40Enabled) {
        this.CCPMPPE40Enabled = CCPMPPE40Enabled;
    }

    public Integer getCCPMPPE128Enabled() {
        return CCPMPPE128Enabled;
    }

    public void setCCPMPPE128Enabled(Integer CCPMPPE128Enabled) {
        this.CCPMPPE128Enabled = CCPMPPE128Enabled;
    }

    public Integer getCCPEnabled() {
        return CCPEnabled;
    }

    public void setCCPEnabled(Integer CCPEnabled) {
        this.CCPEnabled = CCPEnabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PPPInfo)) return false;

        PPPInfo pppInfo = (PPPInfo) o;

        if (AuthName != null ? !AuthName.equals(pppInfo.AuthName) : pppInfo.AuthName != null) return false;
        if (AuthPassword != null ? !AuthPassword.equals(pppInfo.AuthPassword) : pppInfo.AuthPassword != null)
            return false;

        if (CCPEnabled != null ? !CCPEnabled.equals(pppInfo.CCPEnabled) : pppInfo.CCPEnabled != null) return false;
        if (CCPMPPE128Enabled != null ? !CCPMPPE128Enabled.equals(pppInfo.CCPMPPE128Enabled) : pppInfo.CCPMPPE128Enabled != null)
            return false;
        if (CCPMPPE40Enabled != null ? !CCPMPPE40Enabled.equals(pppInfo.CCPMPPE40Enabled) : pppInfo.CCPMPPE40Enabled != null)
            return false;
        if (CommRemoteAddress != null ? !CommRemoteAddress.equals(pppInfo.CommRemoteAddress) : pppInfo.CommRemoteAddress != null)
            return false;
        if (TokenCard != null ? !TokenCard.equals(pppInfo.TokenCard) : pppInfo.TokenCard != null) return false;

        // TODO:
        if (AuthEAPPlugins != null ? !AuthEAPPlugins.equals(pppInfo.AuthEAPPlugins) : pppInfo.AuthEAPPlugins != null)
            return false;
        if (AuthProtocol != null ? !AuthProtocol.equals(pppInfo.AuthProtocol) : pppInfo.AuthProtocol != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = AuthName != null ? AuthName.hashCode() : 0;
        result = 31 * result + (AuthPassword != null ? AuthPassword.hashCode() : 0);
        result = 31 * result + (TokenCard != null ? TokenCard.hashCode() : 0);
        result = 31 * result + (CommRemoteAddress != null ? CommRemoteAddress.hashCode() : 0);
        result = 31 * result + (CCPMPPE40Enabled != null ? CCPMPPE40Enabled.hashCode() : 0);
        result = 31 * result + (CCPMPPE128Enabled != null ? CCPMPPE128Enabled.hashCode() : 0);
        result = 31 * result + (CCPEnabled != null ? CCPEnabled.hashCode() : 0);

        // TODO:
        result = 31 * result + (AuthProtocol != null ? AuthProtocol.hashCode() : 0);
        result = 31 * result + (AuthEAPPlugins != null ? AuthEAPPlugins.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PPPInfo{" +
                "AuthName='" + AuthName + '\'' +
                ", AuthPassword='" + AuthPassword + '\'' +
                ", TokenCard=" + TokenCard +
                ", CommRemoteAddress='" + CommRemoteAddress + '\'' +
                ", AuthEAPPlugins=" + AuthEAPPlugins +
                ", AuthProtocol=" + AuthProtocol +
                ", CCPMPPE40Enabled=" + CCPMPPE40Enabled +
                ", CCPMPPE128Enabled=" + CCPMPPE128Enabled +
                ", CCPEnabled=" + CCPEnabled +
                '}';
    }
}
