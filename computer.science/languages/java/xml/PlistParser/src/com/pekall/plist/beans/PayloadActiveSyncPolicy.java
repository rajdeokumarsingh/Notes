package com.pekall.plist.beans;

@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class PayloadActiveSyncPolicy extends PayloadBase {
    /**
     * Configure ActiveSync Account
     */
    private Boolean configAccount;

    /**
     * Host name of the Server
     */
    private String hostName;

    /**
     * Use SSL
     */
    private Boolean useSSL;

    public static final String ACCOUNT_TYPE_DEVICE_CRED = "use device enrollment credentials";
    public static final String ACCOUNT_TYPE_PROMPT_USER = "prompt user for credentials";
    /**
     * See ACCOUNT_TYPE_...
     */
    private String account;

    /**
     * Identity Certificate
     */
    private String identityCertificate;

    /**
     * Account Display Name
     */
    private String displayName;

    /**
     * Set as Default Account
     */
    private Boolean defaultAccount;

    /**
     * Accept All Certificates
     */
    private Boolean acceptAllCertificates;

    /**
     * Prompt User to Install TouchDown
     */
    private Boolean PromptUserInstall;

    /**
     * License Key
     */
    private String licenseKey;

    /**
     * Configure TouchDown Passcode
     */
    private Boolean configurePasscode;

    /**
     * Suppress TouchDown specific Passcode policy enforced via ActiveSync
     */
    private Boolean suppressPasswordPolicy;

    /**
     * Encrypt Emails
     */
    private Boolean encryptEmails;

    /**
     * Encrypt Attachments
     */
    private Boolean encryptAttachments;

    /**
     * Allow Backup of Emails and Settings
     */
    private Boolean allowBackup;

    /**
     * Disable Copy of Contacts to Phone
     */
    private Boolean disableCopyContacts;

    /**
     * Disable Copy-Paste from Email
     */
    private Boolean disableCopyPasteEmail;

    /**
     * Device Type reported in Exchange Server
     */
    private String deviceType;

    /**
     * Prevent Moving Mail to other Accounts
     */
    private Boolean preventMoving;

    /**
     * Allow HTML Formatted Email
     */
    private Boolean allowHtmlEmail;

    /**
     * Maximum Email Size (KB)
     */
    private Long maxEmailSize;

    public static final String RANGE_ALL = "all";
    public static final String RANGE_1_DAY = "1 day";
    public static final String RANGE_3_DAY = "3 days";
    public static final String RANGE_1_WEEK = "1 week";
    public static final String RANGE_2_WEEK = "2 weeks";
    public static final String RANGE_1_MONTH = "1 month";
    public static final String RANGE_3_MONTH = "3 months";
    public static final String RANGE_6_MONTH = "6 months";
    /**
     * Include Past Emails for Selected Date Range
     * See RANGE_...
     */
    private String includePastEmails;

    /**
     * Include Past Calendar Items for Selected Date Range*
     * See RANGE_...
     */
    private String includePastCalendar;

    /**
     * Allow Attachments
     */
    private Boolean allowAttachments;

    /**
     * Maximum Attachment Size (KB)
     */
    private Long maxAttachmentSize;

    /**
     * Sync Contacts
     */
    private Boolean syncContacts;

    /**
     * Sync Calendar
     */
    private Boolean syncCalendar;

    /**
     * Sync Tasks
     */
    private Boolean syncTasks;

    /**
     * Sync Notes
     */
    private Boolean syncNotes;

    /**
     * Email Signature
     */
    private String emailSignature;

    /**
     * Allow User to change Email Signature
     */
    private Boolean allowChangeSignature;

    /**
     * Manual Sync when Roaming
     */
    private Boolean manualSyncRoaming;

    /**
     * Enable TouchDown Widgets
     */
    private Boolean enableTouchDownWidgets;

    /**
     * Always Vibrate on New Email Notification
     */
    private Boolean vibrateOnEmail;

    /**
     Vibrate on New Email Notification if device is silent
     */
    private Boolean vibrateOnEmailIfSilent;

    /**
     * Policy Key
     */
    private String policyKey;

    /**
     * Value
     */
    private String value;

    public PayloadActiveSyncPolicy() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_ACTIVE_SYNC_POLICY);
    }

    public PayloadActiveSyncPolicy(String payloadIdentifier, String payloadType, String payloadUUID, int payloadVersion, String payloadDescription, String payloadDisplayName, String payloadOrganization) {
        super(payloadIdentifier, payloadType, payloadUUID, payloadVersion, payloadDescription, payloadDisplayName, payloadOrganization);
        setPayloadType(PayloadBase.PAYLOAD_TYPE_ACTIVE_SYNC_POLICY);
    }

    public Boolean getConfigAccount() {
        return configAccount;
    }

    public void setConfigAccount(Boolean configAccount) {
        this.configAccount = configAccount;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Boolean getUseSSL() {
        return useSSL;
    }

    public void setUseSSL(Boolean useSSL) {
        this.useSSL = useSSL;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIdentityCertificate() {
        return identityCertificate;
    }

    public void setIdentityCertificate(String identityCertificate) {
        this.identityCertificate = identityCertificate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(Boolean defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    public Boolean getAcceptAllCertificates() {
        return acceptAllCertificates;
    }

    public void setAcceptAllCertificates(Boolean acceptAllCertificates) {
        this.acceptAllCertificates = acceptAllCertificates;
    }

    public Boolean getPromptUserInstall() {
        return PromptUserInstall;
    }

    public void setPromptUserInstall(Boolean promptUserInstall) {
        PromptUserInstall = promptUserInstall;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public Boolean getConfigurePasscode() {
        return configurePasscode;
    }

    public void setConfigurePasscode(Boolean configurePasscode) {
        this.configurePasscode = configurePasscode;
    }

    public Boolean getSuppressPasswordPolicy() {
        return suppressPasswordPolicy;
    }

    public void setSuppressPasswordPolicy(Boolean suppressPasswordPolicy) {
        this.suppressPasswordPolicy = suppressPasswordPolicy;
    }

    public Boolean getEncryptEmails() {
        return encryptEmails;
    }

    public void setEncryptEmails(Boolean encryptEmails) {
        this.encryptEmails = encryptEmails;
    }

    public Boolean getEncryptAttachments() {
        return encryptAttachments;
    }

    public void setEncryptAttachments(Boolean encryptAttachments) {
        this.encryptAttachments = encryptAttachments;
    }

    public Boolean getAllowBackup() {
        return allowBackup;
    }

    public void setAllowBackup(Boolean allowBackup) {
        this.allowBackup = allowBackup;
    }

    public Boolean getDisableCopyContacts() {
        return disableCopyContacts;
    }

    public void setDisableCopyContacts(Boolean disableCopyContacts) {
        this.disableCopyContacts = disableCopyContacts;
    }

    public Boolean getDisableCopyPasteEmail() {
        return disableCopyPasteEmail;
    }

    public void setDisableCopyPasteEmail(Boolean disableCopyPasteEmail) {
        this.disableCopyPasteEmail = disableCopyPasteEmail;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Boolean getPreventMoving() {
        return preventMoving;
    }

    public void setPreventMoving(Boolean preventMoving) {
        this.preventMoving = preventMoving;
    }

    public Boolean getAllowHtmlEmail() {
        return allowHtmlEmail;
    }

    public void setAllowHtmlEmail(Boolean allowHtmlEmail) {
        this.allowHtmlEmail = allowHtmlEmail;
    }

    public Long getMaxEmailSize() {
        return maxEmailSize;
    }

    public void setMaxEmailSize(Long maxEmailSize) {
        this.maxEmailSize = maxEmailSize;
    }

    public String getIncludePastEmails() {
        return includePastEmails;
    }

    public void setIncludePastEmails(String includePastEmails) {
        this.includePastEmails = includePastEmails;
    }

    public String getIncludePastCalendar() {
        return includePastCalendar;
    }

    public void setIncludePastCalendar(String includePastCalendar) {
        this.includePastCalendar = includePastCalendar;
    }

    public Boolean getAllowAttachments() {
        return allowAttachments;
    }

    public void setAllowAttachments(Boolean allowAttachments) {
        this.allowAttachments = allowAttachments;
    }

    public Long getMaxAttachmentSize() {
        return maxAttachmentSize;
    }

    public void setMaxAttachmentSize(Long maxAttachmentSize) {
        this.maxAttachmentSize = maxAttachmentSize;
    }

    public Boolean getSyncContacts() {
        return syncContacts;
    }

    public void setSyncContacts(Boolean syncContacts) {
        this.syncContacts = syncContacts;
    }

    public Boolean getSyncCalendar() {
        return syncCalendar;
    }

    public void setSyncCalendar(Boolean syncCalendar) {
        this.syncCalendar = syncCalendar;
    }

    public Boolean getSyncTasks() {
        return syncTasks;
    }

    public void setSyncTasks(Boolean syncTasks) {
        this.syncTasks = syncTasks;
    }

    public Boolean getSyncNotes() {
        return syncNotes;
    }

    public void setSyncNotes(Boolean syncNotes) {
        this.syncNotes = syncNotes;
    }

    public String getEmailSignature() {
        return emailSignature;
    }

    public void setEmailSignature(String emailSignature) {
        this.emailSignature = emailSignature;
    }

    public Boolean getAllowChangeSignature() {
        return allowChangeSignature;
    }

    public void setAllowChangeSignature(Boolean allowChangeSignature) {
        this.allowChangeSignature = allowChangeSignature;
    }

    public Boolean getManualSyncRoaming() {
        return manualSyncRoaming;
    }

    public void setManualSyncRoaming(Boolean manualSyncRoaming) {
        this.manualSyncRoaming = manualSyncRoaming;
    }

    public Boolean getEnableTouchDownWidgets() {
        return enableTouchDownWidgets;
    }

    public void setEnableTouchDownWidgets(Boolean enableTouchDownWidgets) {
        this.enableTouchDownWidgets = enableTouchDownWidgets;
    }

    public Boolean getVibrateOnEmail() {
        return vibrateOnEmail;
    }

    public void setVibrateOnEmail(Boolean vibrateOnEmail) {
        this.vibrateOnEmail = vibrateOnEmail;
    }

    public Boolean getVibrateOnEmailIfSilent() {
        return vibrateOnEmailIfSilent;
    }

    public void setVibrateOnEmailIfSilent(Boolean vibrateOnEmailIfSilent) {
        this.vibrateOnEmailIfSilent = vibrateOnEmailIfSilent;
    }

    public String getPolicyKey() {
        return policyKey;
    }

    public void setPolicyKey(String policyKey) {
        this.policyKey = policyKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadActiveSyncPolicy that = (PayloadActiveSyncPolicy) o;

        if (PromptUserInstall != null ? !PromptUserInstall.equals(that.PromptUserInstall) : that.PromptUserInstall != null)
            return false;
        if (acceptAllCertificates != null ? !acceptAllCertificates.equals(that.acceptAllCertificates) : that.acceptAllCertificates != null)
            return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (allowAttachments != null ? !allowAttachments.equals(that.allowAttachments) : that.allowAttachments != null)
            return false;
        if (allowBackup != null ? !allowBackup.equals(that.allowBackup) : that.allowBackup != null) return false;
        if (allowChangeSignature != null ? !allowChangeSignature.equals(that.allowChangeSignature) : that.allowChangeSignature != null)
            return false;
        if (allowHtmlEmail != null ? !allowHtmlEmail.equals(that.allowHtmlEmail) : that.allowHtmlEmail != null)
            return false;
        if (configAccount != null ? !configAccount.equals(that.configAccount) : that.configAccount != null)
            return false;
        if (configurePasscode != null ? !configurePasscode.equals(that.configurePasscode) : that.configurePasscode != null)
            return false;
        if (defaultAccount != null ? !defaultAccount.equals(that.defaultAccount) : that.defaultAccount != null)
            return false;
        if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null) return false;
        if (disableCopyContacts != null ? !disableCopyContacts.equals(that.disableCopyContacts) : that.disableCopyContacts != null)
            return false;
        if (disableCopyPasteEmail != null ? !disableCopyPasteEmail.equals(that.disableCopyPasteEmail) : that.disableCopyPasteEmail != null)
            return false;
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
        if (emailSignature != null ? !emailSignature.equals(that.emailSignature) : that.emailSignature != null)
            return false;
        if (enableTouchDownWidgets != null ? !enableTouchDownWidgets.equals(that.enableTouchDownWidgets) : that.enableTouchDownWidgets != null)
            return false;
        if (encryptAttachments != null ? !encryptAttachments.equals(that.encryptAttachments) : that.encryptAttachments != null)
            return false;
        if (encryptEmails != null ? !encryptEmails.equals(that.encryptEmails) : that.encryptEmails != null)
            return false;
        if (hostName != null ? !hostName.equals(that.hostName) : that.hostName != null) return false;
        if (identityCertificate != null ? !identityCertificate.equals(that.identityCertificate) : that.identityCertificate != null)
            return false;
        if (includePastCalendar != null ? !includePastCalendar.equals(that.includePastCalendar) : that.includePastCalendar != null)
            return false;
        if (includePastEmails != null ? !includePastEmails.equals(that.includePastEmails) : that.includePastEmails != null)
            return false;
        if (licenseKey != null ? !licenseKey.equals(that.licenseKey) : that.licenseKey != null) return false;
        if (manualSyncRoaming != null ? !manualSyncRoaming.equals(that.manualSyncRoaming) : that.manualSyncRoaming != null)
            return false;
        if (maxAttachmentSize != null ? !maxAttachmentSize.equals(that.maxAttachmentSize) : that.maxAttachmentSize != null)
            return false;
        if (maxEmailSize != null ? !maxEmailSize.equals(that.maxEmailSize) : that.maxEmailSize != null) return false;
        if (policyKey != null ? !policyKey.equals(that.policyKey) : that.policyKey != null) return false;
        if (preventMoving != null ? !preventMoving.equals(that.preventMoving) : that.preventMoving != null)
            return false;
        if (suppressPasswordPolicy != null ? !suppressPasswordPolicy.equals(that.suppressPasswordPolicy) : that.suppressPasswordPolicy != null)
            return false;
        if (syncCalendar != null ? !syncCalendar.equals(that.syncCalendar) : that.syncCalendar != null) return false;
        if (syncContacts != null ? !syncContacts.equals(that.syncContacts) : that.syncContacts != null) return false;
        if (syncNotes != null ? !syncNotes.equals(that.syncNotes) : that.syncNotes != null) return false;
        if (syncTasks != null ? !syncTasks.equals(that.syncTasks) : that.syncTasks != null) return false;
        if (useSSL != null ? !useSSL.equals(that.useSSL) : that.useSSL != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        if (vibrateOnEmail != null ? !vibrateOnEmail.equals(that.vibrateOnEmail) : that.vibrateOnEmail != null)
            return false;
        return !(vibrateOnEmailIfSilent != null ? !vibrateOnEmailIfSilent.equals(that.vibrateOnEmailIfSilent) : that.vibrateOnEmailIfSilent != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (configAccount != null ? configAccount.hashCode() : 0);
        result = 31 * result + (hostName != null ? hostName.hashCode() : 0);
        result = 31 * result + (useSSL != null ? useSSL.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (identityCertificate != null ? identityCertificate.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (defaultAccount != null ? defaultAccount.hashCode() : 0);
        result = 31 * result + (acceptAllCertificates != null ? acceptAllCertificates.hashCode() : 0);
        result = 31 * result + (PromptUserInstall != null ? PromptUserInstall.hashCode() : 0);
        result = 31 * result + (licenseKey != null ? licenseKey.hashCode() : 0);
        result = 31 * result + (configurePasscode != null ? configurePasscode.hashCode() : 0);
        result = 31 * result + (suppressPasswordPolicy != null ? suppressPasswordPolicy.hashCode() : 0);
        result = 31 * result + (encryptEmails != null ? encryptEmails.hashCode() : 0);
        result = 31 * result + (encryptAttachments != null ? encryptAttachments.hashCode() : 0);
        result = 31 * result + (allowBackup != null ? allowBackup.hashCode() : 0);
        result = 31 * result + (disableCopyContacts != null ? disableCopyContacts.hashCode() : 0);
        result = 31 * result + (disableCopyPasteEmail != null ? disableCopyPasteEmail.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (preventMoving != null ? preventMoving.hashCode() : 0);
        result = 31 * result + (allowHtmlEmail != null ? allowHtmlEmail.hashCode() : 0);
        result = 31 * result + (maxEmailSize != null ? maxEmailSize.hashCode() : 0);
        result = 31 * result + (includePastEmails != null ? includePastEmails.hashCode() : 0);
        result = 31 * result + (includePastCalendar != null ? includePastCalendar.hashCode() : 0);
        result = 31 * result + (allowAttachments != null ? allowAttachments.hashCode() : 0);
        result = 31 * result + (maxAttachmentSize != null ? maxAttachmentSize.hashCode() : 0);
        result = 31 * result + (syncContacts != null ? syncContacts.hashCode() : 0);
        result = 31 * result + (syncCalendar != null ? syncCalendar.hashCode() : 0);
        result = 31 * result + (syncTasks != null ? syncTasks.hashCode() : 0);
        result = 31 * result + (syncNotes != null ? syncNotes.hashCode() : 0);
        result = 31 * result + (emailSignature != null ? emailSignature.hashCode() : 0);
        result = 31 * result + (allowChangeSignature != null ? allowChangeSignature.hashCode() : 0);
        result = 31 * result + (manualSyncRoaming != null ? manualSyncRoaming.hashCode() : 0);
        result = 31 * result + (enableTouchDownWidgets != null ? enableTouchDownWidgets.hashCode() : 0);
        result = 31 * result + (vibrateOnEmail != null ? vibrateOnEmail.hashCode() : 0);
        result = 31 * result + (vibrateOnEmailIfSilent != null ? vibrateOnEmailIfSilent.hashCode() : 0);
        result = 31 * result + (policyKey != null ? policyKey.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadActiveSyncPolicy{" +
                "configAccount=" + configAccount +
                ", hostName='" + hostName + '\'' +
                ", useSSL=" + useSSL +
                ", account='" + account + '\'' +
                ", identityCertificate='" + identityCertificate + '\'' +
                ", displayName='" + displayName + '\'' +
                ", defaultAccount=" + defaultAccount +
                ", acceptAllCertificates=" + acceptAllCertificates +
                ", PromptUserInstall=" + PromptUserInstall +
                ", licenseKey='" + licenseKey + '\'' +
                ", configurePasscode=" + configurePasscode +
                ", suppressPasswordPolicy=" + suppressPasswordPolicy +
                ", encryptEmails=" + encryptEmails +
                ", encryptAttachments=" + encryptAttachments +
                ", allowBackup=" + allowBackup +
                ", disableCopyContacts=" + disableCopyContacts +
                ", disableCopyPasteEmail=" + disableCopyPasteEmail +
                ", deviceType='" + deviceType + '\'' +
                ", preventMoving=" + preventMoving +
                ", allowHtmlEmail=" + allowHtmlEmail +
                ", maxEmailSize=" + maxEmailSize +
                ", includePastEmails='" + includePastEmails + '\'' +
                ", includePastCalendar='" + includePastCalendar + '\'' +
                ", allowAttachments=" + allowAttachments +
                ", maxAttachmentSize=" + maxAttachmentSize +
                ", syncContacts=" + syncContacts +
                ", syncCalendar=" + syncCalendar +
                ", syncTasks=" + syncTasks +
                ", syncNotes=" + syncNotes +
                ", emailSignature='" + emailSignature + '\'' +
                ", allowChangeSignature=" + allowChangeSignature +
                ", manualSyncRoaming=" + manualSyncRoaming +
                ", enableTouchDownWidgets=" + enableTouchDownWidgets +
                ", vibrateOnEmail=" + vibrateOnEmail +
                ", vibrateOnEmailIfSilent=" + vibrateOnEmailIfSilent +
                ", policyKey='" + policyKey + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
