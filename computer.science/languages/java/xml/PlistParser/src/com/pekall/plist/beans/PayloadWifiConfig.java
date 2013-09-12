package com.pekall.plist.beans;

public class PayloadWifiConfig extends PayloadBase {
    /**
     * Encryption types
     */
    public static final String ENCRYPTION_TYPE_WEP = "WEP";
    public static final String ENCRYPTION_TYPE_WPA = "WPA";
    public static final String ENCRYPTION_TYPE_ANY = "Any";
    public static final String ENCRYPTION_TYPE_NONE = "None";

    /**
     * Proxy types
     */
    public static final String PROXY_TYPE_NONE = "None";
    public static final String PROXY_TYPE_MANUAL = "Manual";
    public static final String PROXY_TYPE_AUTO = "Auto";

    /**
     * SSID of the Wi-Fi network to be used.
     */
    private String SSID_STR;

    /**
     * Besides SSID, the device uses information such as broadcast type and encryption type
     * to differentiate a network. By default (false), it is assumed that all configured
     * networks are open or broadcast. To specify a hidden network, must be true.
     */
    private Boolean HIDDEN_NETWORK;

    /**
     * Optional. Default true. If true, the network is auto-joined.
     * If false, the user has to tap the network name to join it.
     */
    private Boolean AutoJoin;

    /**
     * Encryption type, see ENCRYPTION_TYPE_...
     */
    private String EncryptionType;

    /**
     * Optional.
     */
    private String Password;

    /**
     * Optional, see PROXY_TYPE_...
     */
    private String ProxyType;

    // If the ProxyType field is set to Manual, the following fields must also be provided

    /**
     * The proxy server's network address.
     */
    private String ProxyServer;

    /**
     * The proxy server's port.
     */
    private Integer ProxyServerPort;

    /**
     * Optional. The username used to authenticate to the proxy server.
     */
    private String ProxyUsername;

    /**
     * Optional. The password used to authenticate to the proxy server.
     */
    private String ProxyPassword;

    /**
     * Optional. The URL of the PAC file that defines the proxy configuration.
     */
    private String ProxyPACURL;

    /**
     * In addition to the standard encryption types, it is possible to specify
     * an enterprise profile for a given network via the "EAPClientConfiguration" key.
     */
    private EAPClientConfigurationClass EAPClientConfiguration;

    /**
     * UUID of the certificate payload to use for the identity credential.
     */
    private String PayloadCertificateUUID;

    public PayloadWifiConfig() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_WIFI_MANAGED);
    }

    public String getSSID_STR() {
        return SSID_STR;
    }

    public void setSSID_STR(String SSID_STR) {
        this.SSID_STR = SSID_STR;
    }

    public Boolean getHIDDEN_NETWORK() {
        return HIDDEN_NETWORK;
    }

    public void setHIDDEN_NETWORK(Boolean HIDDEN_NETWORK) {
        this.HIDDEN_NETWORK = HIDDEN_NETWORK;
    }

    public Boolean getAutoJoin() {
        return AutoJoin;
    }

    public void setAutoJoin(Boolean autoJoin) {
        AutoJoin = autoJoin;
    }

    public String getEncryptionType() {
        return EncryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        EncryptionType = encryptionType;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getProxyType() {
        return ProxyType;
    }

    public void setProxyType(String proxyType) {
        ProxyType = proxyType;
    }

    public String getProxyServer() {
        return ProxyServer;
    }

    public void setProxyServer(String proxyServer) {
        ProxyServer = proxyServer;
    }

    public Integer getProxyServerPort() {
        return ProxyServerPort;
    }

    public void setProxyServerPort(Integer proxyServerPort) {
        ProxyServerPort = proxyServerPort;
    }

    public String getProxyUsername() {
        return ProxyUsername;
    }

    public void setProxyUsername(String proxyUsername) {
        ProxyUsername = proxyUsername;
    }

    public String getProxyPassword() {
        return ProxyPassword;
    }

    public void setProxyPassword(String proxyPassword) {
        ProxyPassword = proxyPassword;
    }

    public String getProxyPACURL() {
        return ProxyPACURL;
    }

    public void setProxyPACURL(String proxyPACURL) {
        ProxyPACURL = proxyPACURL;
    }

    public EAPClientConfigurationClass getEAPClientConfiguration() {
        return EAPClientConfiguration;
    }

    public void setEAPClientConfiguration(EAPClientConfigurationClass EAPClientConfiguration) {
        this.EAPClientConfiguration = EAPClientConfiguration;
    }

    public String getPayloadCertificateUUID() {
        return PayloadCertificateUUID;
    }

    public void setPayloadCertificateUUID(String payloadCertificateUUID) {
        PayloadCertificateUUID = payloadCertificateUUID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadWifiConfig)) return false;
        if (!super.equals(o)) return false;

        PayloadWifiConfig that = (PayloadWifiConfig) o;

        if (AutoJoin != null ? !AutoJoin.equals(that.AutoJoin) : that.AutoJoin != null) return false;
        if (EAPClientConfiguration != null ? !EAPClientConfiguration.equals(that.EAPClientConfiguration) : that.EAPClientConfiguration != null)
            return false;
        if (EncryptionType != null ? !EncryptionType.equals(that.EncryptionType) : that.EncryptionType != null)
            return false;
        if (HIDDEN_NETWORK != null ? !HIDDEN_NETWORK.equals(that.HIDDEN_NETWORK) : that.HIDDEN_NETWORK != null)
            return false;
        if (Password != null ? !Password.equals(that.Password) : that.Password != null) return false;
        if (PayloadCertificateUUID != null ? !PayloadCertificateUUID.equals(that.PayloadCertificateUUID) : that.PayloadCertificateUUID != null)
            return false;
        if (ProxyPACURL != null ? !ProxyPACURL.equals(that.ProxyPACURL) : that.ProxyPACURL != null) return false;
        if (ProxyPassword != null ? !ProxyPassword.equals(that.ProxyPassword) : that.ProxyPassword != null)
            return false;
        if (ProxyServer != null ? !ProxyServer.equals(that.ProxyServer) : that.ProxyServer != null) return false;
        if (ProxyServerPort != null ? !ProxyServerPort.equals(that.ProxyServerPort) : that.ProxyServerPort != null)
            return false;
        if (ProxyType != null ? !ProxyType.equals(that.ProxyType) : that.ProxyType != null) return false;
        if (ProxyUsername != null ? !ProxyUsername.equals(that.ProxyUsername) : that.ProxyUsername != null)
            return false;
        if (SSID_STR != null ? !SSID_STR.equals(that.SSID_STR) : that.SSID_STR != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (SSID_STR != null ? SSID_STR.hashCode() : 0);
        result = 31 * result + (HIDDEN_NETWORK != null ? HIDDEN_NETWORK.hashCode() : 0);
        result = 31 * result + (AutoJoin != null ? AutoJoin.hashCode() : 0);
        result = 31 * result + (EncryptionType != null ? EncryptionType.hashCode() : 0);
        result = 31 * result + (Password != null ? Password.hashCode() : 0);
        result = 31 * result + (ProxyType != null ? ProxyType.hashCode() : 0);
        result = 31 * result + (ProxyServer != null ? ProxyServer.hashCode() : 0);
        result = 31 * result + (ProxyServerPort != null ? ProxyServerPort.hashCode() : 0);
        result = 31 * result + (ProxyUsername != null ? ProxyUsername.hashCode() : 0);
        result = 31 * result + (ProxyPassword != null ? ProxyPassword.hashCode() : 0);
        result = 31 * result + (ProxyPACURL != null ? ProxyPACURL.hashCode() : 0);
        result = 31 * result + (EAPClientConfiguration != null ? EAPClientConfiguration.hashCode() : 0);
        result = 31 * result + (PayloadCertificateUUID != null ? PayloadCertificateUUID.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadWifiConfig{" +
                "super='" + super.toString() + '\'' +
                "SSID_STR='" + SSID_STR + '\'' +
                ", HIDDEN_NETWORK=" + HIDDEN_NETWORK +
                ", AutoJoin=" + AutoJoin +
                ", EncryptionType='" + EncryptionType + '\'' +
                ", Password='" + Password + '\'' +
                ", ProxyType='" + ProxyType + '\'' +
                ", ProxyServer='" + ProxyServer + '\'' +
                ", ProxyServerPort=" + ProxyServerPort +
                ", ProxyUsername='" + ProxyUsername + '\'' +
                ", ProxyPassword='" + ProxyPassword + '\'' +
                ", ProxyPACURL='" + ProxyPACURL + '\'' +
                ", EAPClientConfiguration=" + EAPClientConfiguration +
                ", PayloadCertificateUUID='" + PayloadCertificateUUID + '\'' +
                '}';
    }
}
