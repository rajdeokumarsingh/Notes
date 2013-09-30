package com.pekall.plist.beans;

/**
 * VPN profile
 */
public class PayloadVPN extends PayloadBase {

    /**
     * See VPNType
     */
    public static final String TYPE_L2TP = "L2TP";
    public static final String TYPE_PPTP = "PPTP";
    public static final String TYPE_IPSEC = "IPSec";

    /**
     * See userAuth
     */
    public static final String USR_AUTH_PASSWORD = "password";
    public static final String USR_AUTH_RSA_SEC_ID = "RSA SecurId";
    /**
     * Description of the VPN connection displayed on the device.
     */
    private String UserDefinedName;

    /**
     * Specifies whether to send all traffic through the VPN interface.
     * If true, all network traffic is sent over VPN.
     */
    private Boolean OverridePrimary;

    /**
     * Determines the settings available in the payload for this type of VPN connection.
     * It can have three possible values: "L2TP", "PPTP", or "IPSec", representing L2TP, PPTP
     * and Cisco IPSec respectively. This key can also be “VPN” to support additional
     * services via it’s VPNSubType key.
     */
    private String VPNType;

    private PPPInfo PPP;
    private IPSecInfo IPSec;

    private VpnProxies Proxies;

    /**
     * Host name or ip address of the VPN server
     * Just for android.
     */
    private String serverHostName;

    /**
     * Just for android.
     */
    private String account;


    /**
     * See USR_AUTH_...
     * Just for android
     */
    private String userAuth;

    /**
     * Just for android.
     */
    private String password;

    /**
     * Just for android.
     */
    private String sharedPassword;

    /**
     * Just for android.
     */
    private Boolean vpnForAllTraffic;

    /**
     * Just for android.
     */
    private String proxyHost;
    /**
     * Just for android.
     */
    private Integer proxyPort;
    /**
     * Just for android.
     */
    private String proxyUserName;
    /**
     * Just for android.
     */
    private String proxyPassword;

    /* todo, maas360
    private Boolean enableL2TPSecret;
    private String L2TPSecret;
    private String DNSSearchDomains;
    */

    public PayloadVPN() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_VPN);
    }

    public String getUserDefinedName() {
        return UserDefinedName;
    }

    public void setUserDefinedName(String userDefinedName) {
        UserDefinedName = userDefinedName;
    }

    public Boolean getOverridePrimary() {
        return OverridePrimary;
    }

    public void setOverridePrimary(Boolean overridePrimary) {
        OverridePrimary = overridePrimary;
    }

    public String getVPNType() {
        return VPNType;
    }

    public void setVPNType(String VPNType) {
        this.VPNType = VPNType;
    }

    public PPPInfo getPPP() {
        return PPP;
    }

    public void setPPP(PPPInfo PPP) {
        this.PPP = PPP;
    }

    public IPSecInfo getIPSec() {
        return IPSec;
    }

    public void setIPSec(IPSecInfo IPSec) {
        this.IPSec = IPSec;
    }

    public String getServerHostName() {
        return serverHostName;
    }

    public void setServerHostName(String serverHostName) {
        this.serverHostName = serverHostName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(String userAuth) {
        this.userAuth = userAuth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSharedPassword() {
        return sharedPassword;
    }

    public void setSharedPassword(String sharedPassword) {
        this.sharedPassword = sharedPassword;
    }

    public Boolean getVpnForAllTraffic() {
        return vpnForAllTraffic;
    }

    public void setVpnForAllTraffic(Boolean vpnForAllTraffic) {
        this.vpnForAllTraffic = vpnForAllTraffic;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    public String getProxyUserName() {
        return proxyUserName;
    }

    public void setProxyUserName(String proxyUserName) {
        this.proxyUserName = proxyUserName;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        this.proxyPassword = proxyPassword;
    }

    public VpnProxies getProxies() {
        return Proxies;
    }

    public void setProxies(VpnProxies proxies) {
        Proxies = proxies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadVPN)) return false;
        if (!super.equals(o)) return false;

        PayloadVPN that = (PayloadVPN) o;

        if (IPSec != null ? !IPSec.equals(that.IPSec) : that.IPSec != null) return false;
        if (OverridePrimary != null ? !OverridePrimary.equals(that.OverridePrimary) : that.OverridePrimary != null)
            return false;
        if (PPP != null ? !PPP.equals(that.PPP) : that.PPP != null) return false;
        if (Proxies != null ? !Proxies.equals(that.Proxies) : that.Proxies != null) return false;
        if (UserDefinedName != null ? !UserDefinedName.equals(that.UserDefinedName) : that.UserDefinedName != null)
            return false;
        if (VPNType != null ? !VPNType.equals(that.VPNType) : that.VPNType != null) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (proxyHost != null ? !proxyHost.equals(that.proxyHost) : that.proxyHost != null) return false;
        if (proxyPassword != null ? !proxyPassword.equals(that.proxyPassword) : that.proxyPassword != null)
            return false;
        if (proxyPort != null ? !proxyPort.equals(that.proxyPort) : that.proxyPort != null) return false;
        if (proxyUserName != null ? !proxyUserName.equals(that.proxyUserName) : that.proxyUserName != null)
            return false;
        if (serverHostName != null ? !serverHostName.equals(that.serverHostName) : that.serverHostName != null)
            return false;
        if (sharedPassword != null ? !sharedPassword.equals(that.sharedPassword) : that.sharedPassword != null)
            return false;
        if (userAuth != null ? !userAuth.equals(that.userAuth) : that.userAuth != null) return false;
        if (vpnForAllTraffic != null ? !vpnForAllTraffic.equals(that.vpnForAllTraffic) : that.vpnForAllTraffic != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (UserDefinedName != null ? UserDefinedName.hashCode() : 0);
        result = 31 * result + (OverridePrimary != null ? OverridePrimary.hashCode() : 0);
        result = 31 * result + (VPNType != null ? VPNType.hashCode() : 0);
        result = 31 * result + (PPP != null ? PPP.hashCode() : 0);
        result = 31 * result + (IPSec != null ? IPSec.hashCode() : 0);
        result = 31 * result + (Proxies != null ? Proxies.hashCode() : 0);
        result = 31 * result + (serverHostName != null ? serverHostName.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (userAuth != null ? userAuth.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (sharedPassword != null ? sharedPassword.hashCode() : 0);
        result = 31 * result + (vpnForAllTraffic != null ? vpnForAllTraffic.hashCode() : 0);
        result = 31 * result + (proxyHost != null ? proxyHost.hashCode() : 0);
        result = 31 * result + (proxyPort != null ? proxyPort.hashCode() : 0);
        result = 31 * result + (proxyUserName != null ? proxyUserName.hashCode() : 0);
        result = 31 * result + (proxyPassword != null ? proxyPassword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadVPN{" +
                "account='" + account + '\'' +
                ", UserDefinedName='" + UserDefinedName + '\'' +
                ", OverridePrimary=" + OverridePrimary +
                ", VPNType='" + VPNType + '\'' +
                ", PPP=" + PPP +
                ", IPSec=" + IPSec +
                ", Proxies=" + Proxies +
                ", serverHostName='" + serverHostName + '\'' +
                ", userAuth='" + userAuth + '\'' +
                ", password='" + password + '\'' +
                ", sharedPassword='" + sharedPassword + '\'' +
                ", vpnForAllTraffic=" + vpnForAllTraffic +
                ", proxyHost='" + proxyHost + '\'' +
                ", proxyPort=" + proxyPort +
                ", proxyUserName='" + proxyUserName + '\'' +
                ", proxyPassword='" + proxyPassword + '\'' +
                "} " + super.toString();
    }
}
