package com.pekall.plist.beans;

import com.pekall.plist.json.SkipFiled;

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
     * See AuthType
     */
    public static final int AUTH_TYPE_PASS = 0;
    public static final int AUTH_TYPE_RSA_SEC_ID = 1;


    /**
     *see  EncryptionLevel
     */
    public static final int ENCRYPTION_LEVEL_NO = 0;
    public static final int ENCRYPTION_LEVEL_AUTO = 1;
    public static final int ENCRYPTION_LEVEL_MAX = 2;

    /**
     * See userAuth
     */
    public static final String USR_AUTH_PASSWORD = "password";
    public static final String USR_AUTH_RSA_SEC_ID = "RSA SecurId";

    /**
     * see VpnProxiesType
     */
    public static final int PROXY_TYPE_NO = 0;
    public static final int PROXY_TYPE_HAND = 1;
    public static final int PROXY_TYPE_AUTO = 2;

    /**
     * see MachineAuth
     */
    public static final int MACHINE_AUTH_SHAREDPASS = 0;
    public static final int MACHINE_AUTH_CERT = 1;

    /**
     * Description of the VPN connection displayed on the device.
     */
    private String UserDefinedName;


    /**
     * Determines the settings available in the payload for this type of VPN connection.
     * It can have three possible values: "L2TP", "PPTP", or "IPSec", representing L2TP, PPTP
     * and Cisco IPSec respectively. This key can also be “VPN” to support additional
     * services via it’s VPNSubType key.
     */
    private String VPNType;

    @SkipFiled(skip = true)
    private  IPv4Info IPv4;
    @SkipFiled(skip = true)
    private  PPPInfo PPP;
    @SkipFiled(skip = true)
    private  IPSecInfo IPSec;

    /**
     * proxies type,see PROXY_TYPE...
     */
    @PlistControl(toPlistXml = false)
    private Integer VpnProxiesType;

    @SkipFiled(skip = true)
    private VpnProxies Proxies;

    /**
     * IP Host name or dns
     */
    @PlistControl(toPlistXml = false)
    private String CommRemoteAddress;
    @PlistControl(toPlistXml = false)
    private String AuthName;
    @PlistControl(toPlistXml = false)
    private String AuthPassword;

    /**
     *SEE AUTH_TYPE
     */
    @PlistControl(toPlistXml = false)
    private Integer AuthType;
    @PlistControl(toPlistXml = false)
    private String SharedSecret;

    @PlistControl(toPlistXml = false)
    private boolean OverridePrimary;

    /**
     *SEE ENCRYPTION_LEVEL_NO
     */
    @PlistControl(toPlistXml = false)
    private Integer EncryptionLevel;

    public Integer getEncryptionLevel() {
        return EncryptionLevel;
    }


    public void setEncryptionLevel(Integer encryptionLevel) {
        EncryptionLevel = encryptionLevel;
    }

    /**
     *机器鉴定，SEE MACHINE_AUTH_SHAREDPASS
     */
    @PlistControl(toPlistXml = false)
    private Integer MachineAuth;

    public Integer getMachineAuth() {
        return MachineAuth;
    }

    public void setMachineAuth(Integer machineAuth) {
        MachineAuth = machineAuth;
    }

    /**
     *提示输入密码，
     */
    @PlistControl(toPlistXml = false)
    private Boolean PromptForVPNPIN;

    public Boolean getPromptForVPNPIN() {
        return PromptForVPNPIN;
    }

    public void setPromptForVPNPIN(Boolean promptForVPNPIN) {
        PromptForVPNPIN = promptForVPNPIN;
    }

    /**
     * 组别名称
     */
    @PlistControl(toPlistXml = false)
    private String LocalIdentifier;

    public String getLocalIdentifier() {
        return LocalIdentifier;
    }

    public void setLocalIdentifier(String localIdentifier) {
        LocalIdentifier = localIdentifier;
    }

    /**
     * 是否使用混合鉴定
     */
    @PlistControl(toPlistXml = false)
    private Boolean isUseMixedIdentifi;

    public Boolean getUseMixedIdentifi() {
        return isUseMixedIdentifi;
    }

    public void setUseMixedIdentifi(Boolean useMixedIdentifi) {
        isUseMixedIdentifi = useMixedIdentifi;
    }

    @PlistControl(toPlistXml = false)
    private String HTTPProxy;
    @PlistControl(toPlistXml = false)
    private Integer HTTPPort;
    @PlistControl(toPlistXml = false)
    private String HTTPProxyUsername;
    @PlistControl(toPlistXml = false)
    private String HTTPProxyPassword;

    public Integer getHTTPPort() {
        return HTTPPort;
    }

    public void setHTTPPort(Integer HTTPPort) {
        this.HTTPPort = HTTPPort;
    }

    public String getHTTPProxy() {
        return HTTPProxy;
    }

    public void setHTTPProxy(String HTTPProxy) {
        this.HTTPProxy = HTTPProxy;
    }

    public String getHTTPProxyPassword() {
        return HTTPProxyPassword;
    }

    public void setHTTPProxyPassword(String HTTPProxyPassword) {
        this.HTTPProxyPassword = HTTPProxyPassword;
    }

    public String getHTTPProxyUsername() {
        return HTTPProxyUsername;
    }

    public void setHTTPProxyUsername(String HTTPProxyUsername) {
        this.HTTPProxyUsername = HTTPProxyUsername;
    }

    //    /**
//     * Host name or ip address of the VPN server
//     * Just for android.
//     */
//    private String serverHostName;
//
//    /**
//     * Just for android.
//     */
//    private String account;
//
//
//    /**
//     * See USR_AUTH_...
//     * Just for android
//     */
//    private String userAuth;
//
//    /**
//     * Just for android.
//     */
//    private String password;
//
//    /**
//     * Just for android.
//     */
//    private String sharedPassword;
//
//    /**
//     * Just for android.
//     */
//    private Boolean vpnForAllTraffic;
//
//    /**
//     * Just for android.
//     */
//    private String proxyHost;
//    /**
//     * Just for android.
//     */
//    private Integer proxyPort;
//    /**
//     * Just for android.
//     */
//    private String proxyUserName;
//    /**
//     * Just for android.
//     */
//    private String proxyPassword;

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

    public String getVPNType() {
        return VPNType;
    }

    public void setVPNType(String VPNType) {
        this.VPNType = VPNType;
    }


    public IPv4Info getIPv4() {
        return IPv4;
    }

    public void setIPv4(IPv4Info IPv4) {
        this.IPv4 = IPv4;
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

//    public String getServerHostName() {
//        return serverHostName;
//    }
//
//    public void setServerHostName(String serverHostName) {
//        this.serverHostName = serverHostName;
//    }
//
//    public String getAccount() {
//        return account;
//    }
//
//    public void setAccount(String account) {
//        this.account = account;
//    }
//
//    public String getUserAuth() {
//        return userAuth;
//    }
//
//    public void setUserAuth(String userAuth) {
//        this.userAuth = userAuth;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getSharedPassword() {
//        return sharedPassword;
//    }
//
//    public void setSharedPassword(String sharedPassword) {
//        this.sharedPassword = sharedPassword;
//    }
//
//    public Boolean getVpnForAllTraffic() {
//        return vpnForAllTraffic;
//    }
//
//    public void setVpnForAllTraffic(Boolean vpnForAllTraffic) {
//        this.vpnForAllTraffic = vpnForAllTraffic;
//    }
//
//    public String getProxyHost() {
//        return proxyHost;
//    }
//
//    public void setProxyHost(String proxyHost) {
//        this.proxyHost = proxyHost;
//    }
//
//    public Integer getProxyPort() {
//        return proxyPort;
//    }
//
//    public void setProxyPort(Integer proxyPort) {
//        this.proxyPort = proxyPort;
//    }
//
//    public String getProxyUserName() {
//        return proxyUserName;
//    }
//
//    public void setProxyUserName(String proxyUserName) {
//        this.proxyUserName = proxyUserName;
//    }
//
//    public String getProxyPassword() {
//        return proxyPassword;
//    }
//
//    public void setProxyPassword(String proxyPassword) {
//        this.proxyPassword = proxyPassword;
//    }

    public VpnProxies getProxies() {
        return Proxies;
    }

    public void setProxies(VpnProxies proxies) {
        Proxies = proxies;
    }

    public Integer getVpnProxiesType() {
        return VpnProxiesType;
    }

    public void setVpnProxiesType(Integer vpnProxiesType) {
        VpnProxiesType = vpnProxiesType;
    }

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

    public Integer getAuthType() {
        return AuthType;
    }

    public void setAuthType(Integer authType) {
        AuthType = authType;
    }

    public String getCommRemoteAddress() {
        return CommRemoteAddress;
    }

    public void setCommRemoteAddress(String commRemoteAddress) {
        CommRemoteAddress = commRemoteAddress;
    }

    public boolean isOverridePrimary() {
        return OverridePrimary;
    }

    public void setOverridePrimary(boolean overridePrimary) {
        OverridePrimary = overridePrimary;
    }

    public String getSharedSecret() {
        return SharedSecret;
    }

    public void setSharedSecret(String sharedSecret) {
        SharedSecret = sharedSecret;
    }
}
