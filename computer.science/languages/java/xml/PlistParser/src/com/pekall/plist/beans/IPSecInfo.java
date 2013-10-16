package com.pekall.plist.beans;

import java.util.Arrays;

public class IPSecInfo {
    /**
     * IP address or host name of the VPN server. Used for Cisco IPSec.
     */
    private String RemoteAddress;

    /**
     * Either SharedSecret or Certificate. Used for L2TP and Cisco IPSec.
     */
    private String AuthenticationMethod;

    /**
     * User name for VPN account. Used for Cisco IPSec.
     */
    private String XAuthName;

    /**
     * password for VPN account. Used for Cisco IPSec.
     */
    private String XAuthPassword;

    /**
     * 1 if Xauth is on, 0 if it is off. Used for Cisco IPSec.
     */
    private Integer XAuthEnabled;

    /**
     * Present only if AuthenticationMethod is SharedSecret. The name of the group to use.
     * If Hybrid Authentication is used, the string must end with [hybrid].
     * Used for Cisco IPSec.
     */
    private String LocalIdentifier;

    /**
     * Present only if AuthenticationMethod is SharedSecret.
     * The value is KeyID. Used for L2TP and Cisco IPSec.
     */
    private String LocalIdentifierType;

    /**
     * The shared secret for this VPN account. Only present if AuthenticationMethod is
     * SharedSecret. Used for L2TP and Cisco IPSec.
     */
    private byte[] SharedSecret;

    /**
     * The UUID of the certificate to use for the account credentials.
     * Only present if AuthenticationMethod is Certificate. Used for Cisco IPSec.
     */
    private String PayloadCertificateUUID;

    /**
     * Tells whether to prompt for a PIN when connecting. Used for Cisco IPSec.
     */
    private Boolean PromptForVPNPIN;

    public String getRemoteAddress() {
        return RemoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        RemoteAddress = remoteAddress;
    }

    public String getAuthenticationMethod() {
        return AuthenticationMethod;
    }

    public void setAuthenticationMethod(String authenticationMethod) {
        AuthenticationMethod = authenticationMethod;
    }

    public String getXAuthName() {
        return XAuthName;
    }

    public void setXAuthName(String XAuthName) {
        this.XAuthName = XAuthName;
    }

    public Integer getXAuthEnabled() {
        return XAuthEnabled;
    }

    public void setXAuthEnabled(Integer XAuthEnabled) {
        this.XAuthEnabled = XAuthEnabled;
    }

    public String getLocalIdentifier() {
        return LocalIdentifier;
    }

    public void setLocalIdentifier(String localIdentifier) {
        LocalIdentifier = localIdentifier;
    }

    public String getLocalIdentifierType() {
        return LocalIdentifierType;
    }

    public void setLocalIdentifierType(String localIdentifierType) {
        LocalIdentifierType = localIdentifierType;
    }

    public byte[] getSharedSecret() {
        return SharedSecret;
    }

    public void setSharedSecret(byte[] sharedSecret) {
        SharedSecret = sharedSecret;
    }

    public String getPayloadCertificateUUID() {
        return PayloadCertificateUUID;
    }

    public void setPayloadCertificateUUID(String payloadCertificateUUID) {
        PayloadCertificateUUID = payloadCertificateUUID;
    }

    public Boolean getPromptForVPNPIN() {
        return PromptForVPNPIN;
    }

    public void setPromptForVPNPIN(Boolean promptForVPNPIN) {
        PromptForVPNPIN = promptForVPNPIN;
    }

    public String getXAuthPassword() {
        return XAuthPassword;
    }

    public void setXAuthPassword(String XAuthPassword) {
        this.XAuthPassword = XAuthPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IPSecInfo)) return false;

        IPSecInfo ipSecInfo = (IPSecInfo) o;

        if (AuthenticationMethod != null ? !AuthenticationMethod.equals(ipSecInfo.AuthenticationMethod) : ipSecInfo.AuthenticationMethod != null)
            return false;
        if (LocalIdentifier != null ? !LocalIdentifier.equals(ipSecInfo.LocalIdentifier) : ipSecInfo.LocalIdentifier != null)
            return false;
        if (LocalIdentifierType != null ? !LocalIdentifierType.equals(ipSecInfo.LocalIdentifierType) : ipSecInfo.LocalIdentifierType != null)
            return false;
        if (PayloadCertificateUUID != null ? !PayloadCertificateUUID.equals(ipSecInfo.PayloadCertificateUUID) : ipSecInfo.PayloadCertificateUUID != null)
            return false;
        if (PromptForVPNPIN != null ? !PromptForVPNPIN.equals(ipSecInfo.PromptForVPNPIN) : ipSecInfo.PromptForVPNPIN != null)
            return false;
        if (RemoteAddress != null ? !RemoteAddress.equals(ipSecInfo.RemoteAddress) : ipSecInfo.RemoteAddress != null)
            return false;
        if (!Arrays.equals(SharedSecret, ipSecInfo.SharedSecret)) return false;
        if (XAuthEnabled != null ? !XAuthEnabled.equals(ipSecInfo.XAuthEnabled) : ipSecInfo.XAuthEnabled != null)
            return false;
        if (XAuthName != null ? !XAuthName.equals(ipSecInfo.XAuthName) : ipSecInfo.XAuthName != null) return false;
        if (XAuthPassword != null ? !XAuthPassword.equals(ipSecInfo.XAuthPassword) : ipSecInfo.XAuthPassword != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = RemoteAddress != null ? RemoteAddress.hashCode() : 0;
        result = 31 * result + (AuthenticationMethod != null ? AuthenticationMethod.hashCode() : 0);
        result = 31 * result + (XAuthName != null ? XAuthName.hashCode() : 0);
        result = 31 * result + (XAuthPassword != null ? XAuthPassword.hashCode() : 0);
        result = 31 * result + (XAuthEnabled != null ? XAuthEnabled.hashCode() : 0);
        result = 31 * result + (LocalIdentifier != null ? LocalIdentifier.hashCode() : 0);
        result = 31 * result + (LocalIdentifierType != null ? LocalIdentifierType.hashCode() : 0);
        result = 31 * result + (SharedSecret != null ? Arrays.hashCode(SharedSecret) : 0);
        result = 31 * result + (PayloadCertificateUUID != null ? PayloadCertificateUUID.hashCode() : 0);
        result = 31 * result + (PromptForVPNPIN != null ? PromptForVPNPIN.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "IPSecInfo{" +
                "AuthenticationMethod='" + AuthenticationMethod + '\'' +
                ", RemoteAddress='" + RemoteAddress + '\'' +
                ", XAuthName='" + XAuthName + '\'' +
                ", XAuthPassword='" + XAuthPassword + '\'' +
                ", XAuthEnabled=" + XAuthEnabled +
                ", LocalIdentifier='" + LocalIdentifier + '\'' +
                ", LocalIdentifierType='" + LocalIdentifierType + '\'' +
                ", SharedSecret=" + Arrays.toString(SharedSecret) +
                ", PayloadCertificateUUID='" + PayloadCertificateUUID + '\'' +
                ", PromptForVPNPIN=" + PromptForVPNPIN +
                "} " + super.toString();
    }
}
