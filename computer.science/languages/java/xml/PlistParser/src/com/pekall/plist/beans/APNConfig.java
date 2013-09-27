package com.pekall.plist.beans;

import java.util.Arrays;

public class APNConfig {
    /**
     * This string specifies the Access Point Name.
     */
    private String apn;

    /**
     * This string specifies the user name for this APN. If it is
     * missing, the device prompts for it during profile installation.
     */
    private String username;

    /**
     * Optional. This data represents the password for the user for this APN.
     * For obfuscation purposes, the password is encoded. If it is missing from the payload,
     * the device prompts for the password during profile installation.
     */
    private byte[] password;

    /**
     * Optional. The IP address or URL of the APN proxy.
     */
    private String proxy;

    /**
     * Optional. The port number of the APN proxy.
     */
    private Integer proxyPort;

    public APNConfig() {
    }

    public String getApn() {
        return apn;
    }

    public void setApn(String apn) {
        this.apn = apn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public Integer getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(Integer proxyPort) {
        this.proxyPort = proxyPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof APNConfig)) return false;

        APNConfig apnConfig = (APNConfig) o;

        if (apn != null ? !apn.equals(apnConfig.apn) : apnConfig.apn != null) return false;
        if (!Arrays.equals(password, apnConfig.password)) return false;
        if (proxy != null ? !proxy.equals(apnConfig.proxy) : apnConfig.proxy != null) return false;
        if (proxyPort != null ? !proxyPort.equals(apnConfig.proxyPort) : apnConfig.proxyPort != null) return false;
        if (username != null ? !username.equals(apnConfig.username) : apnConfig.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = apn != null ? apn.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? Arrays.hashCode(password) : 0);
        result = 31 * result + (proxy != null ? proxy.hashCode() : 0);
        result = 31 * result + (proxyPort != null ? proxyPort.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "APNConfig{" +
                "apn='" + apn + '\'' +
                ", username='" + username + '\'' +
                ", password=" + Arrays.toString(password) +
                ", proxy='" + proxy + '\'' +
                ", proxyPort=" + proxyPort +
                '}';
    }
}
