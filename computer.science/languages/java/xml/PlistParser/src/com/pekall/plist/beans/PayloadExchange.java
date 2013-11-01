package com.pekall.plist.beans;

import java.util.Arrays;

/**
 * Configures an Exchange Active Sync account on the device.
 */
public class PayloadExchange extends PayloadBase {

    /**
     * Specifies the full email address for the account. If not present in the payload,
     * the device prompts for this string during profile installation.
     */
    private String EmailAddress;

    /**
     * Specifies the Exchange server host name (or IP address).
     */
    private String Host;

    /**
     * Optional. Default YES. Specifies whether the Exchange server uses SSL for authentication.
     */
    private Boolean SSL;

    /**
     * DomainName name.
     */
    @PlistControl(toPlistXml = false)
    private String DomainName;
    /**
     * This string specifies the user name for this Exchange account. If missing,
     * the devices prompts for it during profile installation.
     */
    private String UserName;

    /**
     * Optional. The password of the account. Use only with encrypted profiles.
     */
    private String Password;

    /**
     * Optional. For accounts that allow authentication via blob certificate,
     * a .p12 identity certificate in NSData blob format.
     */
    private byte[] Certificate;

    /**
     * Optional. Specifies the name or description of the certificate.
     */
    private String CertificateName;

    /**
     * Optional. The password necessary for the p12 identity certificate.
     * Use only with encrypted profiles.
     */
    private byte[] CertificatePassword;

    /**
     * Optional. Default false. If set to true, messages may not be moved out of
     * this email account into another account. Also prevents forwarding or
     * replying from a different account than the message was originated from.
     */
    private Boolean PreventMove;

    /**
     * Optional. Default false. If set to true, this account will not
     * be available for sending mail in third party applications.
     */
    private Boolean PreventAppSheet;

    /**
     * UUID of the certificate payload to use for the identity credential.
     * If this field is present, the Certificate field is not used.
     */
    private String PayloadCertificateUUID;

    /**
     * Optional. Default false. If set to true, this account supports S/MIME.
     */
    private Boolean SMIMEEnabled;

    /**
     * Optional. The PayloadUUID of the identity certificate used to
     * sign messages sent from this account.
     */
    private String SMIMESigningCertificateUUID;

    /**
     * Optional. The PayloadUUID of the identity certificate used
     * to decrypt messages coming into this account.
     */
    private String SMIMEEncryptionCertificateUUID;

    /**
     * If true, this account is excluded from address Recents syncing. This defaults to false.
     */
    private Boolean disableMailRecentsSyncing;

    private Integer MailNumberOfPastDaysToSync;

    public PayloadExchange() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_IOS_EXCHANGE);
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getDomainName() {
        return DomainName;
    }

    public void setDomainName(String domainName) {
        DomainName = domainName;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String host) {
        Host = host;
    }

    public Boolean getSSL() {
        return SSL;
    }

    public void setSSL(Boolean SSL) {
        this.SSL = SSL;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public byte[] getCertificate() {
        return Certificate;
    }

    public void setCertificate(byte[] certificate) {
        Certificate = certificate;
    }

    public String getCertificateName() {
        return CertificateName;
    }

    public void setCertificateName(String certificateName) {
        CertificateName = certificateName;
    }

    public byte[] getCertificatePassword() {
        return CertificatePassword;
    }

    public void setCertificatePassword(byte[] certificatePassword) {
        CertificatePassword = certificatePassword;
    }

    public Boolean getPreventMove() {
        return PreventMove;
    }

    public void setPreventMove(Boolean preventMove) {
        PreventMove = preventMove;
    }

    public Boolean getPreventAppSheet() {
        return PreventAppSheet;
    }

    public void setPreventAppSheet(Boolean preventAppSheet) {
        PreventAppSheet = preventAppSheet;
    }

    public String getPayloadCertificateUUID() {
        return PayloadCertificateUUID;
    }

    public void setPayloadCertificateUUID(String payloadCertificateUUID) {
        PayloadCertificateUUID = payloadCertificateUUID;
    }

    public Boolean getSMIMEEnabled() {
        return SMIMEEnabled;
    }

    public void setSMIMEEnabled(Boolean SMIMEEnabled) {
        this.SMIMEEnabled = SMIMEEnabled;
    }

    public String getSMIMESigningCertificateUUID() {
        return SMIMESigningCertificateUUID;
    }

    public void setSMIMESigningCertificateUUID(String SMIMESigningCertificateUUID) {
        this.SMIMESigningCertificateUUID = SMIMESigningCertificateUUID;
    }

    public String getSMIMEEncryptionCertificateUUID() {
        return SMIMEEncryptionCertificateUUID;
    }

    public void setSMIMEEncryptionCertificateUUID(String SMIMEEncryptionCertificateUUID) {
        this.SMIMEEncryptionCertificateUUID = SMIMEEncryptionCertificateUUID;
    }

    public Boolean getDisableMailRecentsSyncing() {
        return disableMailRecentsSyncing;
    }

    public void setDisableMailRecentsSyncing(Boolean disableMailRecentsSyncing) {
        this.disableMailRecentsSyncing = disableMailRecentsSyncing;
    }

    public Integer getMailNumberOfPastDaysToSync() {
        return MailNumberOfPastDaysToSync;
    }

    public void setMailNumberOfPastDaysToSync(Integer mailNumberOfPastDaysToSync) {
        MailNumberOfPastDaysToSync = mailNumberOfPastDaysToSync;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadExchange)) return false;
        if (!super.equals(o)) return false;

        PayloadExchange that = (PayloadExchange) o;

        if (!Arrays.equals(Certificate, that.Certificate)) return false;
        if (CertificateName != null ? !CertificateName.equals(that.CertificateName) : that.CertificateName != null)
            return false;
        if (!Arrays.equals(CertificatePassword, that.CertificatePassword)) return false;
        if (EmailAddress != null ? !EmailAddress.equals(that.EmailAddress) : that.EmailAddress != null) return false;
        if (Host != null ? !Host.equals(that.Host) : that.Host != null) return false;
        if (Password != null ? !Password.equals(that.Password) : that.Password != null) return false;
        if (PayloadCertificateUUID != null ? !PayloadCertificateUUID.equals(that.PayloadCertificateUUID) : that.PayloadCertificateUUID != null)
            return false;
        if (PreventAppSheet != null ? !PreventAppSheet.equals(that.PreventAppSheet) : that.PreventAppSheet != null)
            return false;
        if (PreventMove != null ? !PreventMove.equals(that.PreventMove) : that.PreventMove != null) return false;
        if (SMIMEEnabled != null ? !SMIMEEnabled.equals(that.SMIMEEnabled) : that.SMIMEEnabled != null) return false;
        if (SMIMEEncryptionCertificateUUID != null ? !SMIMEEncryptionCertificateUUID.equals(that.SMIMEEncryptionCertificateUUID) : that.SMIMEEncryptionCertificateUUID != null)
            return false;
        if (SMIMESigningCertificateUUID != null ? !SMIMESigningCertificateUUID.equals(that.SMIMESigningCertificateUUID) : that.SMIMESigningCertificateUUID != null)
            return false;
        if (SSL != null ? !SSL.equals(that.SSL) : that.SSL != null) return false;
        if (UserName != null ? !UserName.equals(that.UserName) : that.UserName != null) return false;
        if (disableMailRecentsSyncing != null ? !disableMailRecentsSyncing.equals(that.disableMailRecentsSyncing) : that.disableMailRecentsSyncing != null)
            return false;
        if (MailNumberOfPastDaysToSync != null ? !MailNumberOfPastDaysToSync.equals(that.MailNumberOfPastDaysToSync) : that.MailNumberOfPastDaysToSync != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (EmailAddress != null ? EmailAddress.hashCode() : 0);
        result = 31 * result + (Host != null ? Host.hashCode() : 0);
        result = 31 * result + (SSL != null ? SSL.hashCode() : 0);
        result = 31 * result + (UserName != null ? UserName.hashCode() : 0);
        result = 31 * result + (Password != null ? Password.hashCode() : 0);
        result = 31 * result + (Certificate != null ? Arrays.hashCode(Certificate) : 0);
        result = 31 * result + (CertificateName != null ? CertificateName.hashCode() : 0);
        result = 31 * result + (CertificatePassword != null ? Arrays.hashCode(CertificatePassword) : 0);
        result = 31 * result + (PreventMove != null ? PreventMove.hashCode() : 0);
        result = 31 * result + (PreventAppSheet != null ? PreventAppSheet.hashCode() : 0);
        result = 31 * result + (PayloadCertificateUUID != null ? PayloadCertificateUUID.hashCode() : 0);
        result = 31 * result + (SMIMEEnabled != null ? SMIMEEnabled.hashCode() : 0);
        result = 31 * result + (SMIMESigningCertificateUUID != null ? SMIMESigningCertificateUUID.hashCode() : 0);
        result = 31 * result + (SMIMEEncryptionCertificateUUID != null ? SMIMEEncryptionCertificateUUID.hashCode() : 0);
        result = 31 * result + (disableMailRecentsSyncing != null ? disableMailRecentsSyncing.hashCode() : 0);
        result = 31 * result + (MailNumberOfPastDaysToSync != null ? MailNumberOfPastDaysToSync.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadExchange{" +
                "EmailAddress='" + EmailAddress + '\'' +
                ", Host='" + Host + '\'' +
                ", SSL=" + SSL +
                ", UserName='" + UserName + '\'' +
                ", Password='" + Password + '\'' +
                ", Certificate=" + Arrays.toString(Certificate) +
                ", CertificateName='" + CertificateName + '\'' +
                ", CertificatePassword=" + Arrays.toString(CertificatePassword) +
                ", PreventMove=" + PreventMove +
                ", PreventAppSheet=" + PreventAppSheet +
                ", PayloadCertificateUUID='" + PayloadCertificateUUID + '\'' +
                ", SMIMEEnabled=" + SMIMEEnabled +
                ", SMIMESigningCertificateUUID='" + SMIMESigningCertificateUUID + '\'' +
                ", SMIMEEncryptionCertificateUUID='" + SMIMEEncryptionCertificateUUID + '\'' +
                ", disableMailRecentsSyncing=" + disableMailRecentsSyncing +
                ", MailNumberOfPastDaysToSync=" + MailNumberOfPastDaysToSync +
                '}';
    }
}
