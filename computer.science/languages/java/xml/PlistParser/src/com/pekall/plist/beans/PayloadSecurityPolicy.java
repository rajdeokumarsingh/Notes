package com.pekall.plist.beans;

public class PayloadSecurityPolicy extends PayloadBase {
    /**
     * See disableKeyguardFeatures
     */
    public static final String KEYGUARD_FEATURE_NONE = "none";
    public static final String KEYGUARD_FEATURE_CAMERA = "camera";
    public static final String KEYGUARD_FEATURE_ALL_WIDGETS = "all widgets";
    public static final String KEYGUARD_FEATURE_ALL_FEATURES = "all feature";

    /**
     * See allowNonGoogleApp, enforceAppVerify, backupMyData, automaticRestore, visiblePasswords
     */
    public static final String APP_ENABLED = "enabled";
    public static final String APP_DISABLE = "disable";
    public static final String APP_USR_CONTROLLED = "user controlled";

    /**
     * Enforce Device Encryption
     */
    private Boolean enforceEncryption;

    /**
     * Enforce SD Card Encryption
     */
    private Boolean enforceSDCardEncryption;

    /**
     * Allow SD Card
     */
    private Boolean allowSDCard;

    /**
     * Allow SD Card write
     */
    private Boolean allowSDCardWrite;

    /**
     * Disable Keyguard Features
     * See KEYGUARD_...
     */
    private String disableKeyguardFeatures;

    /**
     * Allow Installation of Non-Google Play Applications
     * See APP_...
     */
    private String allowNonGoogleApp;

    /**
     * Enforce App verification before install
     * See APP_...
     */
    private String enforceAppVerify;

    /**
     * Allow Screen Capture
     */
    private Boolean allowScreenCapture;

    /**
     * Allow Clipboard
     */
    private Boolean allowClipboard;

    /**
     * Backup my data
     * See APP_...
     */
    private String backupMyData;

    /**
     * Automatic Restore
     * See APP_...
     */
    private String automaticRestore;

    /**
     * Visible Passwords
     * See APP_...
     */
    private String visiblePasswords;

    /**
     * Allow USB Debugging
     */
    private Boolean allowUSBDebugging;

    /**
     * Allow Google Crash Report
     */
    private Boolean allowGoogleCrashReport;

    /**
     * Allow Factory Reset
     */
    private Boolean allowFactoryReset;

    /**
     * Allow OTA Upgrade
     */
    private Boolean allowOTAUpgrade;

    public PayloadSecurityPolicy() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_SECURITY_POLICY);
    }

    public PayloadSecurityPolicy(String payloadIdentifier, String payloadType, String payloadUUID, int payloadVersion, String payloadDescription, String payloadDisplayName, String payloadOrganization) {
        super(payloadIdentifier, payloadType, payloadUUID, payloadVersion, payloadDescription, payloadDisplayName, payloadOrganization);
        setPayloadType(PayloadBase.PAYLOAD_TYPE_SECURITY_POLICY);
    }

    public Boolean getEnforceEncryption() {
        return enforceEncryption;
    }

    public void setEnforceEncryption(Boolean enforceEncryption) {
        this.enforceEncryption = enforceEncryption;
    }

    public Boolean getEnforceSDCardEncryption() {
        return enforceSDCardEncryption;
    }

    public void setEnforceSDCardEncryption(Boolean enforceSDCardEncryption) {
        this.enforceSDCardEncryption = enforceSDCardEncryption;
    }

    public Boolean getAllowSDCard() {
        return allowSDCard;
    }

    public void setAllowSDCard(Boolean allowSDCard) {
        this.allowSDCard = allowSDCard;
    }

    public Boolean getAllowSDCardWrite() {
        return allowSDCardWrite;
    }

    public void setAllowSDCardWrite(Boolean allowSDCardWrite) {
        this.allowSDCardWrite = allowSDCardWrite;
    }

    public String getDisableKeyguardFeatures() {
        return disableKeyguardFeatures;
    }

    public void setDisableKeyguardFeatures(String disableKeyguardFeatures) {
        this.disableKeyguardFeatures = disableKeyguardFeatures;
    }

    public String getAllowNonGoogleApp() {
        return allowNonGoogleApp;
    }

    public void setAllowNonGoogleApp(String allowNonGoogleApp) {
        this.allowNonGoogleApp = allowNonGoogleApp;
    }

    public String getEnforceAppVerify() {
        return enforceAppVerify;
    }

    public void setEnforceAppVerify(String enforceAppVerify) {
        this.enforceAppVerify = enforceAppVerify;
    }

    public Boolean getAllowScreenCapture() {
        return allowScreenCapture;
    }

    public void setAllowScreenCapture(Boolean allowScreenCapture) {
        this.allowScreenCapture = allowScreenCapture;
    }

    public Boolean getAllowClipboard() {
        return allowClipboard;
    }

    public void setAllowClipboard(Boolean allowClipboard) {
        this.allowClipboard = allowClipboard;
    }

    public String getBackupMyData() {
        return backupMyData;
    }

    public void setBackupMyData(String backupMyData) {
        this.backupMyData = backupMyData;
    }

    public String getAutomaticRestore() {
        return automaticRestore;
    }

    public void setAutomaticRestore(String automaticRestore) {
        this.automaticRestore = automaticRestore;
    }

    public String getVisiblePasswords() {
        return visiblePasswords;
    }

    public void setVisiblePasswords(String visiblePasswords) {
        this.visiblePasswords = visiblePasswords;
    }

    public Boolean getAllowUSBDebugging() {
        return allowUSBDebugging;
    }

    public void setAllowUSBDebugging(Boolean allowUSBDebugging) {
        this.allowUSBDebugging = allowUSBDebugging;
    }

    public Boolean getAllowGoogleCrashReport() {
        return allowGoogleCrashReport;
    }

    public void setAllowGoogleCrashReport(Boolean allowGoogleCrashReport) {
        this.allowGoogleCrashReport = allowGoogleCrashReport;
    }

    public Boolean getAllowFactoryReset() {
        return allowFactoryReset;
    }

    public void setAllowFactoryReset(Boolean allowFactoryReset) {
        this.allowFactoryReset = allowFactoryReset;
    }

    public Boolean getAllowOTAUpgrade() {
        return allowOTAUpgrade;
    }

    public void setAllowOTAUpgrade(Boolean allowOTAUpgrade) {
        this.allowOTAUpgrade = allowOTAUpgrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadSecurityPolicy that = (PayloadSecurityPolicy) o;

        if (allowClipboard != null ? !allowClipboard.equals(that.allowClipboard) : that.allowClipboard != null)
            return false;
        if (allowFactoryReset != null ? !allowFactoryReset.equals(that.allowFactoryReset) : that.allowFactoryReset != null)
            return false;
        if (allowGoogleCrashReport != null ? !allowGoogleCrashReport.equals(that.allowGoogleCrashReport) : that.allowGoogleCrashReport != null)
            return false;
        if (allowNonGoogleApp != null ? !allowNonGoogleApp.equals(that.allowNonGoogleApp) : that.allowNonGoogleApp != null)
            return false;
        if (allowOTAUpgrade != null ? !allowOTAUpgrade.equals(that.allowOTAUpgrade) : that.allowOTAUpgrade != null)
            return false;
        if (allowSDCard != null ? !allowSDCard.equals(that.allowSDCard) : that.allowSDCard != null) return false;
        if (allowSDCardWrite != null ? !allowSDCardWrite.equals(that.allowSDCardWrite) : that.allowSDCardWrite != null)
            return false;
        if (allowScreenCapture != null ? !allowScreenCapture.equals(that.allowScreenCapture) : that.allowScreenCapture != null)
            return false;
        if (allowUSBDebugging != null ? !allowUSBDebugging.equals(that.allowUSBDebugging) : that.allowUSBDebugging != null)
            return false;
        if (automaticRestore != null ? !automaticRestore.equals(that.automaticRestore) : that.automaticRestore != null)
            return false;
        if (backupMyData != null ? !backupMyData.equals(that.backupMyData) : that.backupMyData != null) return false;
        if (disableKeyguardFeatures != null ? !disableKeyguardFeatures.equals(that.disableKeyguardFeatures) : that.disableKeyguardFeatures != null)
            return false;
        if (enforceAppVerify != null ? !enforceAppVerify.equals(that.enforceAppVerify) : that.enforceAppVerify != null)
            return false;
        if (enforceEncryption != null ? !enforceEncryption.equals(that.enforceEncryption) : that.enforceEncryption != null)
            return false;
        if (enforceSDCardEncryption != null ? !enforceSDCardEncryption.equals(that.enforceSDCardEncryption) : that.enforceSDCardEncryption != null)
            return false;
        if (visiblePasswords != null ? !visiblePasswords.equals(that.visiblePasswords) : that.visiblePasswords != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (enforceEncryption != null ? enforceEncryption.hashCode() : 0);
        result = 31 * result + (enforceSDCardEncryption != null ? enforceSDCardEncryption.hashCode() : 0);
        result = 31 * result + (allowSDCard != null ? allowSDCard.hashCode() : 0);
        result = 31 * result + (allowSDCardWrite != null ? allowSDCardWrite.hashCode() : 0);
        result = 31 * result + (disableKeyguardFeatures != null ? disableKeyguardFeatures.hashCode() : 0);
        result = 31 * result + (allowNonGoogleApp != null ? allowNonGoogleApp.hashCode() : 0);
        result = 31 * result + (enforceAppVerify != null ? enforceAppVerify.hashCode() : 0);
        result = 31 * result + (allowScreenCapture != null ? allowScreenCapture.hashCode() : 0);
        result = 31 * result + (allowClipboard != null ? allowClipboard.hashCode() : 0);
        result = 31 * result + (backupMyData != null ? backupMyData.hashCode() : 0);
        result = 31 * result + (automaticRestore != null ? automaticRestore.hashCode() : 0);
        result = 31 * result + (visiblePasswords != null ? visiblePasswords.hashCode() : 0);
        result = 31 * result + (allowUSBDebugging != null ? allowUSBDebugging.hashCode() : 0);
        result = 31 * result + (allowGoogleCrashReport != null ? allowGoogleCrashReport.hashCode() : 0);
        result = 31 * result + (allowFactoryReset != null ? allowFactoryReset.hashCode() : 0);
        result = 31 * result + (allowOTAUpgrade != null ? allowOTAUpgrade.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadSecurityPolicy{" +
                "enforceEncryption=" + enforceEncryption +
                ", enforceSDCardEncryption=" + enforceSDCardEncryption +
                ", allowSDCard=" + allowSDCard +
                ", allowSDCardWrite=" + allowSDCardWrite +
                ", disableKeyguardFeatures='" + disableKeyguardFeatures + '\'' +
                ", allowNonGoogleApp='" + allowNonGoogleApp + '\'' +
                ", enforceAppVerify='" + enforceAppVerify + '\'' +
                ", allowScreenCapture=" + allowScreenCapture +
                ", allowClipboard=" + allowClipboard +
                ", backupMyData='" + backupMyData + '\'' +
                ", automaticRestore='" + automaticRestore + '\'' +
                ", visiblePasswords='" + visiblePasswords + '\'' +
                ", allowUSBDebugging=" + allowUSBDebugging +
                ", allowGoogleCrashReport=" + allowGoogleCrashReport +
                ", allowFactoryReset=" + allowFactoryReset +
                ", allowOTAUpgrade=" + allowOTAUpgrade +
                '}';
    }
}
