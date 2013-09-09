package com.pekall.plist.beans;

/**
 * An email payload creates an email account on the device.
 */
public class PayloadEmail extends PayloadBase {
    /**
     * Email types, see EmailAccountType
     */
    public static final String EMAIL_TYPE_POP = "EmailTypePOP";
    public static final String EMAIL_TYPE_IMAP = "EmailTypeIMAP";

    /**
     * Email authentication type, see IncomingMailServerAuthentication,
     * OutgoingMailServerAuthentication
     */
    public static final String EMAIL_AUTH_PASSWORD = "EmailAuthPassword";
    public static final String EMAIL_AUTH_NONE = "EmailAuthNone";

    /**
     * Optional. A user-visible description of the email account,
     * shown in the Mail and Settings applications.
     */
    private String EmailAccountDescription;

    /**
     * Optional. The full user name for the account.
     * This is the user name in sent messages, etc.
     */
    private String EmailAccountName;

    /**
     * Allowed values are EmailTypePOP and EmailTypeIMAP.
     * Defines the protocol to be used for that account.
     * See constants EMAIL_TYPE_...
     */
    private String EmailAccountType;

    /**
     * Designates the full email address for the account. If not present in the payload,
     * the device prompts for this string during profile installation.
     */
    private String EmailAddress;

    /**
     * Designates the authentication scheme for incoming mail.
     * Allowed values are EmailAuthPassword and EmailAuthNone.
     * See EMAIL_AUTH_...
     */
    private String IncomingMailServerAuthentication;

    /**
     * Designates the incoming mail server host name (or IP address).
     */
    private String IncomingMailServerHostName;

    /**
     * Optional. Designates the incoming mail server port number.
     * If no port number is specified, the default port for a given protocol is used.
     */
    private Integer IncomingMailServerPortNumber;

    /**
     * Optional. Default true. Designates whether the incoming mail server uses SSL
     * for authentication.
     */
    private Boolean IncomingMailServerUseSSL;

    /**
     * Designates the user name for the email account, usually the same as the email address
     * up to the @ character. If not present in the payload, and the account is set up
     * to require authentication for incoming email, the device will prompt for this string
     * during profile installation.
     */
    private String IncomingMailServerUsername;

    /**
     * Optional. Password for the Incoming Mail Server. Use only with encrypted profiles.
     */
    private String IncomingPassword;

    /**
     * Optional. Password for the Outgoing Mail Server. Use only with encrypted profiles.
     */
    private String OutgoingPassword;

    /**
     * Optional. If set, the user will be prompted for the password only once
     * and it will be used for both outgoing and incoming mail.
     */
    private Boolean OutgoingPasswordSameAsIncomingPassword;

    /**
     * Designates the authentication scheme for outgoing mail. Allowed values are
     * EmailAuthPassword and EmailAuthNone. See EMAIL_AUTH_...
     */
    private String OutgoingMailServerAuthentication;

    /**
     * Designates the outgoing mail server host name (or IP address).
     */
    private String OutgoingMailServerHostName;

    /**
     * Optional. Designates the outgoing mail server port number. If no port number
     * is specified, ports 25, 587 and 465 are used, in this order.
     */
    private Integer OutgoingMailServerPortNumber;

    /**
     * Optional. Default Yes. Designates whether the outgoing mail server
     * uses SSL for authentication.
     */
    private Boolean OutgoingMailServerUseSSL;

    /**
     * Designates the user name for the email account, usually the same as the email address
     * up to the @ character. If not present in the payload, and the account is set up to
     * require authentication for outgoing email, the device prompts for this string
     * during profile installation.
     */
    private String OutgoingMailServerUsername;

    /**
     * Optional. Default false. If true, messages may not be moved out of this email account
     * into another account. Also prevents forwarding or replying from a different account
     * than the message was originated from.
     */
    private Boolean PreventMove;

    /**
     * Optional. Default false. If true, this account is not available for sending mail
     * in third-party applications.
     */
    private Boolean PreventAppSheet;

    /**
     * Optional. Default false. If true, this account supports S/MIME.
     */
    private Boolean SMIMEEnabled;

    /**
     * Optional. The PayloadUUID of the identity certificate used to sign messages sent
     * from this account.
     */
    private String SMIMESigningCertificateUUID;

    /**
     * Optional. The PayloadUUID of the identity certificate used to decrypt messages
     * coming into this account.
     */
    private String SMIMEEncryptionCertificateUUID;

    /**
     * If true, this account is excluded from address Recents syncing. This defaults to false.
     */
    private Boolean disableMailRecentsSyncing;

    /**
     * Whether it is a default email account.
     * Just for android.
     */
    private Boolean defaultAccount;

    /**
     * Accept all certificates for incoming mail
     * Just for android.
     */
    private Boolean acceptAllCertForIncomingMail;

    /**
     * Accept All Certificates for Outgoing Mail
     * Just for android.
     */
    private Boolean acceptAllCertForOutgoingMail;

    /**
     * Name of the sender
     * Just for android.
     */
    private String senderName;

    /**
     * Just for android.
     */
    private String signature;

    /**
     * Always vibrate on new email notification
     * Just for android.
     */
    private Boolean vibrateOnNewEmail;

    /**
     * Vibrate on new email notification if device is silent
     * Just for android.
     */
    private Boolean vibrateOnNewEmailIfSilent;

    public PayloadEmail() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_EMAIL);
    }

    public String getEmailAccountDescription() {
        return EmailAccountDescription;
    }

    public void setEmailAccountDescription(String emailAccountDescription) {
        EmailAccountDescription = emailAccountDescription;
    }

    public String getEmailAccountName() {
        return EmailAccountName;
    }

    public void setEmailAccountName(String emailAccountName) {
        EmailAccountName = emailAccountName;
    }

    public String getEmailAccountType() {
        return EmailAccountType;
    }

    public void setEmailAccountType(String emailAccountType) {
        EmailAccountType = emailAccountType;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getIncomingMailServerAuthentication() {
        return IncomingMailServerAuthentication;
    }

    public void setIncomingMailServerAuthentication(String incomingMailServerAuthentication) {
        IncomingMailServerAuthentication = incomingMailServerAuthentication;
    }

    public String getIncomingMailServerHostName() {
        return IncomingMailServerHostName;
    }

    public void setIncomingMailServerHostName(String incomingMailServerHostName) {
        IncomingMailServerHostName = incomingMailServerHostName;
    }

    public Integer getIncomingMailServerPortNumber() {
        return IncomingMailServerPortNumber;
    }

    public void setIncomingMailServerPortNumber(Integer incomingMailServerPortNumber) {
        IncomingMailServerPortNumber = incomingMailServerPortNumber;
    }

    public Boolean getIncomingMailServerUseSSL() {
        return IncomingMailServerUseSSL;
    }

    public void setIncomingMailServerUseSSL(Boolean incomingMailServerUseSSL) {
        IncomingMailServerUseSSL = incomingMailServerUseSSL;
    }

    public String getIncomingMailServerUsername() {
        return IncomingMailServerUsername;
    }

    public void setIncomingMailServerUsername(String incomingMailServerUsername) {
        IncomingMailServerUsername = incomingMailServerUsername;
    }

    public String getIncomingPassword() {
        return IncomingPassword;
    }

    public void setIncomingPassword(String incomingPassword) {
        IncomingPassword = incomingPassword;
    }

    public String getOutgoingPassword() {
        return OutgoingPassword;
    }

    public void setOutgoingPassword(String outgoingPassword) {
        OutgoingPassword = outgoingPassword;
    }

    public Boolean getOutgoingPasswordSameAsIncomingPassword() {
        return OutgoingPasswordSameAsIncomingPassword;
    }

    public void setOutgoingPasswordSameAsIncomingPassword(Boolean outgoingPasswordSameAsIncomingPassword) {
        OutgoingPasswordSameAsIncomingPassword = outgoingPasswordSameAsIncomingPassword;
    }

    public String getOutgoingMailServerAuthentication() {
        return OutgoingMailServerAuthentication;
    }

    public void setOutgoingMailServerAuthentication(String outgoingMailServerAuthentication) {
        OutgoingMailServerAuthentication = outgoingMailServerAuthentication;
    }

    public String getOutgoingMailServerHostName() {
        return OutgoingMailServerHostName;
    }

    public void setOutgoingMailServerHostName(String outgoingMailServerHostName) {
        OutgoingMailServerHostName = outgoingMailServerHostName;
    }

    public Integer getOutgoingMailServerPortNumber() {
        return OutgoingMailServerPortNumber;
    }

    public void setOutgoingMailServerPortNumber(Integer outgoingMailServerPortNumber) {
        OutgoingMailServerPortNumber = outgoingMailServerPortNumber;
    }

    public Boolean getOutgoingMailServerUseSSL() {
        return OutgoingMailServerUseSSL;
    }

    public void setOutgoingMailServerUseSSL(Boolean outgoingMailServerUseSSL) {
        OutgoingMailServerUseSSL = outgoingMailServerUseSSL;
    }

    public String getOutgoingMailServerUsername() {
        return OutgoingMailServerUsername;
    }

    public void setOutgoingMailServerUsername(String outgoingMailServerUsername) {
        OutgoingMailServerUsername = outgoingMailServerUsername;
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

    public Boolean isDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(Boolean defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    public Boolean getDefaultAccount() {
        return defaultAccount;
    }

    public Boolean getAcceptAllCertForIncomingMail() {
        return acceptAllCertForIncomingMail;
    }

    public void setAcceptAllCertForIncomingMail(Boolean acceptAllCertForIncomingMail) {
        this.acceptAllCertForIncomingMail = acceptAllCertForIncomingMail;
    }

    public Boolean getAcceptAllCertForOutgoingMail() {
        return acceptAllCertForOutgoingMail;
    }

    public void setAcceptAllCertForOutgoingMail(Boolean acceptAllCertForOutgoingMail) {
        this.acceptAllCertForOutgoingMail = acceptAllCertForOutgoingMail;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Boolean getVibrateOnNewEmail() {
        return vibrateOnNewEmail;
    }

    public void setVibrateOnNewEmail(Boolean vibrateOnNewEmail) {
        this.vibrateOnNewEmail = vibrateOnNewEmail;
    }

    public Boolean getVibrateOnNewEmailIfSilent() {
        return vibrateOnNewEmailIfSilent;
    }

    public void setVibrateOnNewEmailIfSilent(Boolean vibrateOnNewEmailIfSilent) {
        this.vibrateOnNewEmailIfSilent = vibrateOnNewEmailIfSilent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadEmail that = (PayloadEmail) o;

        if (EmailAccountDescription != null ? !EmailAccountDescription.equals(that.EmailAccountDescription) : that.EmailAccountDescription != null)
            return false;
        if (EmailAccountName != null ? !EmailAccountName.equals(that.EmailAccountName) : that.EmailAccountName != null)
            return false;
        if (EmailAccountType != null ? !EmailAccountType.equals(that.EmailAccountType) : that.EmailAccountType != null)
            return false;
        if (EmailAddress != null ? !EmailAddress.equals(that.EmailAddress) : that.EmailAddress != null) return false;
        if (IncomingMailServerAuthentication != null ? !IncomingMailServerAuthentication.equals(that.IncomingMailServerAuthentication) : that.IncomingMailServerAuthentication != null)
            return false;
        if (IncomingMailServerHostName != null ? !IncomingMailServerHostName.equals(that.IncomingMailServerHostName) : that.IncomingMailServerHostName != null)
            return false;
        if (IncomingMailServerPortNumber != null ? !IncomingMailServerPortNumber.equals(that.IncomingMailServerPortNumber) : that.IncomingMailServerPortNumber != null)
            return false;
        if (IncomingMailServerUseSSL != null ? !IncomingMailServerUseSSL.equals(that.IncomingMailServerUseSSL) : that.IncomingMailServerUseSSL != null)
            return false;
        if (IncomingMailServerUsername != null ? !IncomingMailServerUsername.equals(that.IncomingMailServerUsername) : that.IncomingMailServerUsername != null)
            return false;
        if (IncomingPassword != null ? !IncomingPassword.equals(that.IncomingPassword) : that.IncomingPassword != null)
            return false;
        if (OutgoingMailServerAuthentication != null ? !OutgoingMailServerAuthentication.equals(that.OutgoingMailServerAuthentication) : that.OutgoingMailServerAuthentication != null)
            return false;
        if (OutgoingMailServerHostName != null ? !OutgoingMailServerHostName.equals(that.OutgoingMailServerHostName) : that.OutgoingMailServerHostName != null)
            return false;
        if (OutgoingMailServerPortNumber != null ? !OutgoingMailServerPortNumber.equals(that.OutgoingMailServerPortNumber) : that.OutgoingMailServerPortNumber != null)
            return false;
        if (OutgoingMailServerUseSSL != null ? !OutgoingMailServerUseSSL.equals(that.OutgoingMailServerUseSSL) : that.OutgoingMailServerUseSSL != null)
            return false;
        if (OutgoingMailServerUsername != null ? !OutgoingMailServerUsername.equals(that.OutgoingMailServerUsername) : that.OutgoingMailServerUsername != null)
            return false;
        if (OutgoingPassword != null ? !OutgoingPassword.equals(that.OutgoingPassword) : that.OutgoingPassword != null)
            return false;
        if (OutgoingPasswordSameAsIncomingPassword != null ? !OutgoingPasswordSameAsIncomingPassword.equals(that.OutgoingPasswordSameAsIncomingPassword) : that.OutgoingPasswordSameAsIncomingPassword != null)
            return false;
        if (PreventAppSheet != null ? !PreventAppSheet.equals(that.PreventAppSheet) : that.PreventAppSheet != null)
            return false;
        if (PreventMove != null ? !PreventMove.equals(that.PreventMove) : that.PreventMove != null) return false;
        if (SMIMEEnabled != null ? !SMIMEEnabled.equals(that.SMIMEEnabled) : that.SMIMEEnabled != null) return false;
        if (SMIMEEncryptionCertificateUUID != null ? !SMIMEEncryptionCertificateUUID.equals(that.SMIMEEncryptionCertificateUUID) : that.SMIMEEncryptionCertificateUUID != null)
            return false;
        if (SMIMESigningCertificateUUID != null ? !SMIMESigningCertificateUUID.equals(that.SMIMESigningCertificateUUID) : that.SMIMESigningCertificateUUID != null)
            return false;
        if (acceptAllCertForIncomingMail != null ? !acceptAllCertForIncomingMail.equals(that.acceptAllCertForIncomingMail) : that.acceptAllCertForIncomingMail != null)
            return false;
        if (acceptAllCertForOutgoingMail != null ? !acceptAllCertForOutgoingMail.equals(that.acceptAllCertForOutgoingMail) : that.acceptAllCertForOutgoingMail != null)
            return false;
        if (defaultAccount != null ? !defaultAccount.equals(that.defaultAccount) : that.defaultAccount != null)
            return false;
        if (disableMailRecentsSyncing != null ? !disableMailRecentsSyncing.equals(that.disableMailRecentsSyncing) : that.disableMailRecentsSyncing != null)
            return false;
        if (senderName != null ? !senderName.equals(that.senderName) : that.senderName != null) return false;
        if (signature != null ? !signature.equals(that.signature) : that.signature != null) return false;
        if (vibrateOnNewEmail != null ? !vibrateOnNewEmail.equals(that.vibrateOnNewEmail) : that.vibrateOnNewEmail != null)
            return false;
        if (vibrateOnNewEmailIfSilent != null ? !vibrateOnNewEmailIfSilent.equals(that.vibrateOnNewEmailIfSilent) : that.vibrateOnNewEmailIfSilent != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (EmailAccountDescription != null ? EmailAccountDescription.hashCode() : 0);
        result = 31 * result + (EmailAccountName != null ? EmailAccountName.hashCode() : 0);
        result = 31 * result + (EmailAccountType != null ? EmailAccountType.hashCode() : 0);
        result = 31 * result + (EmailAddress != null ? EmailAddress.hashCode() : 0);
        result = 31 * result + (IncomingMailServerAuthentication != null ? IncomingMailServerAuthentication.hashCode() : 0);
        result = 31 * result + (IncomingMailServerHostName != null ? IncomingMailServerHostName.hashCode() : 0);
        result = 31 * result + (IncomingMailServerPortNumber != null ? IncomingMailServerPortNumber.hashCode() : 0);
        result = 31 * result + (IncomingMailServerUseSSL != null ? IncomingMailServerUseSSL.hashCode() : 0);
        result = 31 * result + (IncomingMailServerUsername != null ? IncomingMailServerUsername.hashCode() : 0);
        result = 31 * result + (IncomingPassword != null ? IncomingPassword.hashCode() : 0);
        result = 31 * result + (OutgoingPassword != null ? OutgoingPassword.hashCode() : 0);
        result = 31 * result + (OutgoingPasswordSameAsIncomingPassword != null ? OutgoingPasswordSameAsIncomingPassword.hashCode() : 0);
        result = 31 * result + (OutgoingMailServerAuthentication != null ? OutgoingMailServerAuthentication.hashCode() : 0);
        result = 31 * result + (OutgoingMailServerHostName != null ? OutgoingMailServerHostName.hashCode() : 0);
        result = 31 * result + (OutgoingMailServerPortNumber != null ? OutgoingMailServerPortNumber.hashCode() : 0);
        result = 31 * result + (OutgoingMailServerUseSSL != null ? OutgoingMailServerUseSSL.hashCode() : 0);
        result = 31 * result + (OutgoingMailServerUsername != null ? OutgoingMailServerUsername.hashCode() : 0);
        result = 31 * result + (PreventMove != null ? PreventMove.hashCode() : 0);
        result = 31 * result + (PreventAppSheet != null ? PreventAppSheet.hashCode() : 0);
        result = 31 * result + (SMIMEEnabled != null ? SMIMEEnabled.hashCode() : 0);
        result = 31 * result + (SMIMESigningCertificateUUID != null ? SMIMESigningCertificateUUID.hashCode() : 0);
        result = 31 * result + (SMIMEEncryptionCertificateUUID != null ? SMIMEEncryptionCertificateUUID.hashCode() : 0);
        result = 31 * result + (disableMailRecentsSyncing != null ? disableMailRecentsSyncing.hashCode() : 0);
        result = 31 * result + (defaultAccount != null ? defaultAccount.hashCode() : 0);
        result = 31 * result + (acceptAllCertForIncomingMail != null ? acceptAllCertForIncomingMail.hashCode() : 0);
        result = 31 * result + (acceptAllCertForOutgoingMail != null ? acceptAllCertForOutgoingMail.hashCode() : 0);
        result = 31 * result + (senderName != null ? senderName.hashCode() : 0);
        result = 31 * result + (signature != null ? signature.hashCode() : 0);
        result = 31 * result + (vibrateOnNewEmail != null ? vibrateOnNewEmail.hashCode() : 0);
        result = 31 * result + (vibrateOnNewEmailIfSilent != null ? vibrateOnNewEmailIfSilent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadEmail{" +
                "EmailAccountDescription='" + EmailAccountDescription + '\'' +
                ", EmailAccountName='" + EmailAccountName + '\'' +
                ", EmailAccountType='" + EmailAccountType + '\'' +
                ", EmailAddress='" + EmailAddress + '\'' +
                ", IncomingMailServerAuthentication='" + IncomingMailServerAuthentication + '\'' +
                ", IncomingMailServerHostName='" + IncomingMailServerHostName + '\'' +
                ", IncomingMailServerPortNumber=" + IncomingMailServerPortNumber +
                ", IncomingMailServerUseSSL=" + IncomingMailServerUseSSL +
                ", IncomingMailServerUsername='" + IncomingMailServerUsername + '\'' +
                ", IncomingPassword='" + IncomingPassword + '\'' +
                ", OutgoingPassword='" + OutgoingPassword + '\'' +
                ", OutgoingPasswordSameAsIncomingPassword=" + OutgoingPasswordSameAsIncomingPassword +
                ", OutgoingMailServerAuthentication='" + OutgoingMailServerAuthentication + '\'' +
                ", OutgoingMailServerHostName='" + OutgoingMailServerHostName + '\'' +
                ", OutgoingMailServerPortNumber=" + OutgoingMailServerPortNumber +
                ", OutgoingMailServerUseSSL=" + OutgoingMailServerUseSSL +
                ", OutgoingMailServerUsername='" + OutgoingMailServerUsername + '\'' +
                ", PreventMove=" + PreventMove +
                ", PreventAppSheet=" + PreventAppSheet +
                ", SMIMEEnabled=" + SMIMEEnabled +
                ", SMIMESigningCertificateUUID='" + SMIMESigningCertificateUUID + '\'' +
                ", SMIMEEncryptionCertificateUUID='" + SMIMEEncryptionCertificateUUID + '\'' +
                ", disableMailRecentsSyncing=" + disableMailRecentsSyncing +
                ", defaultAccount=" + defaultAccount +
                ", acceptAllCertForIncomingMail=" + acceptAllCertForIncomingMail +
                ", acceptAllCertForOutgoingMail=" + acceptAllCertForOutgoingMail +
                ", senderName='" + senderName + '\'' +
                ", signature='" + signature + '\'' +
                ", vibrateOnNewEmail=" + vibrateOnNewEmail +
                ", vibrateOnNewEmailIfSilent=" + vibrateOnNewEmailIfSilent +
                '}';
    }
}
