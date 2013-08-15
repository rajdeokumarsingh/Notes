package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

public class EAPClientConfigurationClass {

    /**
     * Accepted EAP types, see AcceptEAPTypes
     */
    public static final int ACCEPT_EAP_TYPE_TLS = 13;
    public static final int ACCEPT_EAP_TYPE_LEAP = 17;
    public static final int ACCEPT_EAP_TYPE_TTLS = 21;
    public static final int ACCEPT_EAP_TYPE_PEAP = 25;
    public static final int ACCEPT_EAP_TYPE_EAP_FAST = 43;

    /**
     * Optional. Unless you know the exact user name, this property won't appear in an imported
     * configuration. Users can enter this information when they authenticate.
     */
    private String UserName;

    /**
     * The following EAP types are accepted:
     * 13 = TLS
     * 17 = LEAP
     * 21 = TTLS
     * 25 = PEAP
     * 43 = EAP-FAST
     */
    private List<Integer> AcceptEAPTypes;

    /**
     * Optional. Identifies the certificates to be trusted for this authentication.
     * Each entry must contain the UUID of a certificate payload.
     * Use this key to prevent the device from asking the user if the listed certificates
     * are trusted. Dynamic trust (the certificate dialogue) is disabled if this property
     * is specified, unless TLSAllowTrustExceptions is also specified with the value true.
     */
    private List<String> PayloadCertificateAnchorUUID;

    /**
     * Optional. This is the list of server certificate common names that will be accepted.
     * You can use wildcards to specify the name, such as wpa.*.example.com.
     * If a server presents a certificate that isn't in this list, it won't be trusted.
     */
    private List<String> TLSTrustedServerNames;

    /**
     * Optional. Allows/disallows a dynamic trust decision by the user. The dynamic trust
     * is the certificate dialogue that appears when a certificate isn't trusted.
     * If this is false, the authentication fails if the certificate isn't already trusted.
     * See PayloadCertificateAnchorUUID and TLSTrustedNames above.
     */
    private Boolean TLSAllowTrustExceptions;

    /**
     * Optional. This is the inner authentication used by the TTLS module.
     * The default value is "MSCHAPv2". Possible values are "PAP", "CHAP",
     * "MSCHAP", and "MSCHAPv2".
     */
    private String TTLSInnerAuthentication;

    /**
     * Optional. This key is only relevant to TTLS, PEAP, and EAP-FAST.
     */
    private String OuterIdentity;

    // EAP-Fast Support begin

    /**
     * Optional.
     */
    private Boolean EAPFASTUsePAC;

    /**
     * Optional.
     */
    private Boolean EAPFASTProvisionPAC;

    /**
     * Optional.
     */
    private Boolean EAPFASTProvisionPACAnonymously;

    // EAP-Fast Support end


    public EAPClientConfigurationClass() {
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public List<Integer> getAcceptEAPTypes() {
        return AcceptEAPTypes;
    }

    public void setAcceptEAPTypes(List<Integer> acceptEAPTypes) {
        AcceptEAPTypes = acceptEAPTypes;
    }

    public void addAcceptEAPType(Integer integer) {
        if (AcceptEAPTypes == null) {
            AcceptEAPTypes = new ArrayList<Integer>();
        }
        AcceptEAPTypes.add(integer);
    }

    public List<String> getPayloadCertificateAnchorUUID() {
        return PayloadCertificateAnchorUUID;
    }

    public void setPayloadCertificateAnchorUUID(List<String> payloadCertificateAnchorUUID) {
        PayloadCertificateAnchorUUID = payloadCertificateAnchorUUID;
    }

    public void addPayloadCertificateAnchorUUID(String uuid) {
        if (PayloadCertificateAnchorUUID == null) {
            PayloadCertificateAnchorUUID = new ArrayList<String>();
        }
        PayloadCertificateAnchorUUID.add(uuid);
    }

    public List<String> getTLSTrustedServerNames() {
        return TLSTrustedServerNames;
    }

    public void setTLSTrustedServerNames(List<String> TLSTrustedServerNames) {
        this.TLSTrustedServerNames = TLSTrustedServerNames;
    }

    public void addTLSTrustedServerName(String name) {
        if (TLSTrustedServerNames != null) {
            TLSTrustedServerNames = new ArrayList<String>();
        }
        TLSTrustedServerNames.add(name);
    }

    public Boolean getTLSAllowTrustExceptions() {
        return TLSAllowTrustExceptions;
    }

    public void setTLSAllowTrustExceptions(Boolean TLSAllowTrustExceptions) {
        this.TLSAllowTrustExceptions = TLSAllowTrustExceptions;
    }

    public String getTTLSInnerAuthentication() {
        return TTLSInnerAuthentication;
    }

    public void setTTLSInnerAuthentication(String TTLSInnerAuthentication) {
        this.TTLSInnerAuthentication = TTLSInnerAuthentication;
    }

    public String getOuterIdentity() {
        return OuterIdentity;
    }

    public void setOuterIdentity(String outerIdentity) {
        OuterIdentity = outerIdentity;
    }

    public Boolean getEAPFASTUsePAC() {
        return EAPFASTUsePAC;
    }

    public void setEAPFASTUsePAC(Boolean EAPFASTUsePAC) {
        this.EAPFASTUsePAC = EAPFASTUsePAC;
    }

    public Boolean getEAPFASTProvisionPAC() {
        return EAPFASTProvisionPAC;
    }

    public void setEAPFASTProvisionPAC(Boolean EAPFASTProvisionPAC) {
        this.EAPFASTProvisionPAC = EAPFASTProvisionPAC;
    }

    public Boolean getEAPFASTProvisionPACAnonymously() {
        return EAPFASTProvisionPACAnonymously;
    }

    public void setEAPFASTProvisionPACAnonymously(Boolean EAPFASTProvisionPACAnonymously) {
        this.EAPFASTProvisionPACAnonymously = EAPFASTProvisionPACAnonymously;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EAPClientConfigurationClass)) return false;

        EAPClientConfigurationClass that = (EAPClientConfigurationClass) o;


        if (EAPFASTProvisionPAC != null ? !EAPFASTProvisionPAC.equals(that.EAPFASTProvisionPAC) : that.EAPFASTProvisionPAC != null)
            return false;
        if (EAPFASTProvisionPACAnonymously != null ? !EAPFASTProvisionPACAnonymously.equals(that.EAPFASTProvisionPACAnonymously) : that.EAPFASTProvisionPACAnonymously != null)
            return false;
        if (EAPFASTUsePAC != null ? !EAPFASTUsePAC.equals(that.EAPFASTUsePAC) : that.EAPFASTUsePAC != null)
            return false;
        if (OuterIdentity != null ? !OuterIdentity.equals(that.OuterIdentity) : that.OuterIdentity != null)
            return false;
        if (TLSAllowTrustExceptions != null ? !TLSAllowTrustExceptions.equals(that.TLSAllowTrustExceptions) : that.TLSAllowTrustExceptions != null)
            return false;
        if (TTLSInnerAuthentication != null ? !TTLSInnerAuthentication.equals(that.TTLSInnerAuthentication) : that.TTLSInnerAuthentication != null)
            return false;
        if (UserName != null ? !UserName.equals(that.UserName) : that.UserName != null) return false;

        if (this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = UserName != null ? UserName.hashCode() : 0;
        if (AcceptEAPTypes != null) {
            for (Integer acceptEAPType : AcceptEAPTypes) {
                result += acceptEAPType.hashCode();
            }
        }
        if (PayloadCertificateAnchorUUID != null) {
            for (String s : PayloadCertificateAnchorUUID) {
                result += s.hashCode();
            }
        }
        if (TLSTrustedServerNames != null) {
            for (String tlsTrustedServerName : TLSTrustedServerNames) {
                result += tlsTrustedServerName.hashCode();
            }
        }
        result = 31 * result + (TLSAllowTrustExceptions != null ? TLSAllowTrustExceptions.hashCode() : 0);
        result = 31 * result + (TTLSInnerAuthentication != null ? TTLSInnerAuthentication.hashCode() : 0);
        result = 31 * result + (OuterIdentity != null ? OuterIdentity.hashCode() : 0);
        result = 31 * result + (EAPFASTUsePAC != null ? EAPFASTUsePAC.hashCode() : 0);
        result = 31 * result + (EAPFASTProvisionPAC != null ? EAPFASTProvisionPAC.hashCode() : 0);
        result = 31 * result + (EAPFASTProvisionPACAnonymously != null ? EAPFASTProvisionPACAnonymously.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EAPClientConfigurationClass{" +
                "UserName='" + UserName + '\'' +
                ", AcceptEAPTypes=" + AcceptEAPTypes +
                ", PayloadCertificateAnchorUUID=" + PayloadCertificateAnchorUUID +
                ", TLSTrustedServerNames=" + TLSTrustedServerNames +
                ", TLSAllowTrustExceptions=" + TLSAllowTrustExceptions +
                ", TTLSInnerAuthentication='" + TTLSInnerAuthentication + '\'' +
                ", OuterIdentity='" + OuterIdentity + '\'' +
                ", EAPFASTUsePAC=" + EAPFASTUsePAC +
                ", EAPFASTProvisionPAC=" + EAPFASTProvisionPAC +
                ", EAPFASTProvisionPACAnonymously=" + EAPFASTProvisionPACAnonymously +
                '}';
    }
}
