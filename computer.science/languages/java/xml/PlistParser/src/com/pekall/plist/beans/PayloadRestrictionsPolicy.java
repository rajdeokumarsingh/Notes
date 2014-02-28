package com.pekall.plist.beans;

/**
 * A Restrictions payload allows the administrator to restrict the user
 * from doing certain things with the device, such as using the camera.
 */
@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class PayloadRestrictionsPolicy extends PayloadBase {

    /**
     * Optional. When false, the App Store is disabled and its icon is removed from the
     * Home screen. Users are unable to install or update their applications.
     */
    private Boolean allowAppInstallation;

    /**
     * Optional. When false, disables Siri. Defaults to true.
     */
    private Boolean allowAssistant;

    /**
     * Optional. When false, the user is unable to use Siri when the device is locked.
     * Defaults to true. This restriction is ignored if the device does not have a passcode set.
     */
    private Boolean allowAssistantWhileLocked;

    /**
     * Optional. When false, the camera is completely disabled and its icon is removed from
     * the Home screen. Users are unable to take photographs.
     */
    private Boolean allowCamera;

    /**
     * Optional. Allowing users to receive or make Face Time video calls.
     */
    private Boolean allowVideoConferencing;

    /**
     * Optional. When false, this prevents the device from automatically submitting
     * diagnostic reports to Apple. Defaults to true.
     */
    private Boolean allowDiagnosticSubmission;

    /**
     * Optional. When false, explicit music or video content purchased from the iTunes Store
     * is hidden. Explicit content is marked as such by content providers,
     * such as record labels, when sold through the iTunes Store.
     */
    private Boolean allowExplicitContent;

    /**
     * Optional. When false, Game Center is disabled and its icon is removed from
     * the Home screen. Supervised only. Default is true.
     */
    private Boolean allowGameCenter;

    /**
     * Optional. When false, users are unable to save a screenshot of the display.
     */
    private Boolean allowScreenShot;

    /**
     * Optional. When false, the YouTube application is disabled and its icon
     * is removed from the Home screen.
     */
    private Boolean allowYouTube;

    /**
     * Optional. When false, the iTunes Music Store is disabled and its icon is removed
     * from the Home screen. Users cannot preview, purchase, or download content.
     */
    private Boolean allowiTunes;

    /**
     * Optional. When true, forces user to enter their iTunes password for each transaction.
     */
    private Boolean forceITunesStorePasswordEntry;

    /**
     * Optional. When false, the Safari web browser application is disabled and its icon
     * removed from the Home screen. This also prevents users from opening web clips.
     */
    private Boolean allowSafari;

    /**
     * Optional. When false, automatically rejects untrusted HTTPS certificates
     * without prompting the user.
     */
    private Boolean allowUntrustedTLSPrompt;

    /**
     * Optional. When false, disables backing up the device to iCloud.
     */
    private Boolean allowCloudBackup;

    /**
     * Optional. When false, disables document and key-value syncing to iCloud.
     */
    private Boolean allowCloudDocumentSync;

    /**
     * Optional. When false, disables Photo Stream.
     */
    private Boolean allowPhotoStream;

    /**
     * Optional. If set to false, iBookstore will be disabled. This will default to true.
     * Supervised only.
     */
    private Boolean allowBookstore;

    /**
     * Optional. If set to false, the user will not be able to download media from
     * the iBookstore that has been tagged as erotica. This will default to true.
     * Supervised only.
     */
    private Boolean allowBookstoreErotica;

    /**
     * Optional. If set to false, Passbook notifications will not be shown on the lock screen.
     * This will default to true.
     */
    private Boolean allowPassbookWhileLocked;

    /**
     * Optional. If set to false, Shared Photo Stream will be disabled.
     * This will default to true.
     */
    private Boolean allowSharedStream;

    /**
     * Optional. If set to false, the user is prohibited from installing configuration
     * profiles and certificates interactively.
     * This will default to true. Supervised only.
     */
    private Boolean allowUIConfigurationProfileInstallation;

    private Boolean allowVoiceDialing;
    private Boolean allowInAppPurchases;
    private Boolean allowMultiplayerGaming;
    private Boolean allowAddingGameCenterFriends;
    private Boolean safariAllowAutoFill;
    private Boolean safariAllowJavaScript;
    private Boolean safariAllowPopups;
    private Boolean safariForceFraudWarning;
    private Integer safariAcceptCookies;
    private Boolean forceEncryptedBackUp;
    private Boolean allowGlobalBackgroundFetchWhenRoaming;

    public PayloadRestrictionsPolicy() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_RESTRICTIONS);
    }

    public Boolean getAllowVideoConferencing() {
        return allowVideoConferencing;
    }

    public void setAllowVideoConferencing(Boolean allowVideoConferencing) {
        this.allowVideoConferencing = allowVideoConferencing;
    }

    public Boolean getAllowAppInstallation() {
        return allowAppInstallation;
    }

    public void setAllowAppInstallation(Boolean allowAppInstallation) {
        this.allowAppInstallation = allowAppInstallation;
    }

    public Boolean getAllowAssistant() {
        return allowAssistant;
    }

    public void setAllowAssistant(Boolean allowAssistant) {
        this.allowAssistant = allowAssistant;
    }

    public Boolean getAllowAssistantWhileLocked() {
        return allowAssistantWhileLocked;
    }

    public void setAllowAssistantWhileLocked(Boolean allowAssistantWhileLocked) {
        this.allowAssistantWhileLocked = allowAssistantWhileLocked;
    }

    public Boolean getAllowCamera() {
        return allowCamera;
    }

    public void setAllowCamera(Boolean allowCamera) {
        this.allowCamera = allowCamera;
    }

    public Boolean getAllowDiagnosticSubmission() {
        return allowDiagnosticSubmission;
    }

    public void setAllowDiagnosticSubmission(Boolean allowDiagnosticSubmission) {
        this.allowDiagnosticSubmission = allowDiagnosticSubmission;
    }

    public Boolean getAllowExplicitContent() {
        return allowExplicitContent;
    }

    public void setAllowExplicitContent(Boolean allowExplicitContent) {
        this.allowExplicitContent = allowExplicitContent;
    }

    public Boolean getAllowGameCenter() {
        return allowGameCenter;
    }

    public void setAllowGameCenter(Boolean allowGameCenter) {
        this.allowGameCenter = allowGameCenter;
    }

    public Boolean getAllowScreenShot() {
        return allowScreenShot;
    }

    public void setAllowScreenShot(Boolean allowScreenShot) {
        this.allowScreenShot = allowScreenShot;
    }

    public Boolean getAllowYouTube() {
        return allowYouTube;
    }

    public void setAllowYouTube(Boolean allowYouTube) {
        this.allowYouTube = allowYouTube;
    }

    public Boolean getAllowiTunes() {
        return allowiTunes;
    }

    public void setAllowiTunes(Boolean allowiTunes) {
        this.allowiTunes = allowiTunes;
    }

    public Boolean getForceITunesStorePasswordEntry() {
        return forceITunesStorePasswordEntry;
    }

    public void setForceITunesStorePasswordEntry(Boolean forceITunesStorePasswordEntry) {
        this.forceITunesStorePasswordEntry = forceITunesStorePasswordEntry;
    }

    public Boolean getAllowSafari() {
        return allowSafari;
    }

    public void setAllowSafari(Boolean allowSafari) {
        this.allowSafari = allowSafari;
    }

    public Boolean getAllowUntrustedTLSPrompt() {
        return allowUntrustedTLSPrompt;
    }

    public void setAllowUntrustedTLSPrompt(Boolean allowUntrustedTLSPrompt) {
        this.allowUntrustedTLSPrompt = allowUntrustedTLSPrompt;
    }

    public Boolean getAllowCloudBackup() {
        return allowCloudBackup;
    }

    public void setAllowCloudBackup(Boolean allowCloudBackup) {
        this.allowCloudBackup = allowCloudBackup;
    }

    public Boolean getAllowCloudDocumentSync() {
        return allowCloudDocumentSync;
    }

    public void setAllowCloudDocumentSync(Boolean allowCloudDocumentSync) {
        this.allowCloudDocumentSync = allowCloudDocumentSync;
    }

    public Boolean getAllowPhotoStream() {
        return allowPhotoStream;
    }

    public void setAllowPhotoStream(Boolean allowPhotoStream) {
        this.allowPhotoStream = allowPhotoStream;
    }

    public Boolean getAllowBookstore() {
        return allowBookstore;
    }

    public void setAllowBookstore(Boolean allowBookstore) {
        this.allowBookstore = allowBookstore;
    }

    public Boolean getAllowBookstoreErotica() {
        return allowBookstoreErotica;
    }

    public void setAllowBookstoreErotica(Boolean allowBookstoreErotica) {
        this.allowBookstoreErotica = allowBookstoreErotica;
    }

    public Boolean getAllowPassbookWhileLocked() {
        return allowPassbookWhileLocked;
    }

    public void setAllowPassbookWhileLocked(Boolean allowPassbookWhileLocked) {
        this.allowPassbookWhileLocked = allowPassbookWhileLocked;
    }

    public Boolean getAllowSharedStream() {
        return allowSharedStream;
    }

    public void setAllowSharedStream(Boolean allowSharedStream) {
        this.allowSharedStream = allowSharedStream;
    }

    public Boolean getAllowUIConfigurationProfileInstallation() {
        return allowUIConfigurationProfileInstallation;
    }

    public void setAllowUIConfigurationProfileInstallation(Boolean allowUIConfigurationProfileInstallation) {
        this.allowUIConfigurationProfileInstallation = allowUIConfigurationProfileInstallation;
    }

    public Boolean getAllowVoiceDialing() {
        return allowVoiceDialing;
    }

    public void setAllowVoiceDialing(Boolean allowVoiceDialing) {
        this.allowVoiceDialing = allowVoiceDialing;
    }

    public Boolean getAllowInAppPurchases() {
        return allowInAppPurchases;
    }

    public void setAllowInAppPurchases(Boolean allowInAppPurchases) {
        this.allowInAppPurchases = allowInAppPurchases;
    }

    public Boolean getAllowMultiplayerGaming() {
        return allowMultiplayerGaming;
    }

    public void setAllowMultiplayerGaming(Boolean allowMultiplayerGaming) {
        this.allowMultiplayerGaming = allowMultiplayerGaming;
    }

    public Boolean getAllowAddingGameCenterFriends() {
        return allowAddingGameCenterFriends;
    }

    public void setAllowAddingGameCenterFriends(Boolean allowAddingGameCenterFriends) {
        this.allowAddingGameCenterFriends = allowAddingGameCenterFriends;
    }

    public Boolean getSafariAllowAutoFill() {
        return safariAllowAutoFill;
    }

    public void setSafariAllowAutoFill(Boolean safariAllowAutoFill) {
        this.safariAllowAutoFill = safariAllowAutoFill;
    }

    public Boolean getSafariAllowJavaScript() {
        return safariAllowJavaScript;
    }

    public void setSafariAllowJavaScript(Boolean safariAllowJavaScript) {
        this.safariAllowJavaScript = safariAllowJavaScript;
    }

    public Boolean getSafariAllowPopups() {
        return safariAllowPopups;
    }

    public void setSafariAllowPopups(Boolean safariAllowPopups) {
        this.safariAllowPopups = safariAllowPopups;
    }

    public Boolean getSafariForceFraudWarning() {
        return safariForceFraudWarning;
    }

    public void setSafariForceFraudWarning(Boolean safariForceFraudWarning) {
        this.safariForceFraudWarning = safariForceFraudWarning;
    }

    public Integer getSafariAcceptCookies() {
        return safariAcceptCookies;
    }

    public void setSafariAcceptCookies(Integer safariAcceptCookies) {
        this.safariAcceptCookies = safariAcceptCookies;
    }

    public Boolean getForceEncryptedBackUp() {
        return forceEncryptedBackUp;
    }

    public void setForceEncryptedBackUp(Boolean forceEncryptedBackUp) {
        this.forceEncryptedBackUp = forceEncryptedBackUp;
    }

    public Boolean getAllowGlobalBackgroundFetchWhenRoaming() {
        return allowGlobalBackgroundFetchWhenRoaming;
    }

    public void setAllowGlobalBackgroundFetchWhenRoaming(Boolean allowGlobalBackgroundFetchWhenRoaming) {
        this.allowGlobalBackgroundFetchWhenRoaming = allowGlobalBackgroundFetchWhenRoaming;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadRestrictionsPolicy)) return false;
        if (!super.equals(o)) return false;

        PayloadRestrictionsPolicy that = (PayloadRestrictionsPolicy) o;

        if (allowAddingGameCenterFriends != null ? !allowAddingGameCenterFriends.equals(that.allowAddingGameCenterFriends) : that.allowAddingGameCenterFriends != null)
            return false;
        if (allowAppInstallation != null ? !allowAppInstallation.equals(that.allowAppInstallation) : that.allowAppInstallation != null)
            return false;
        if (allowAssistant != null ? !allowAssistant.equals(that.allowAssistant) : that.allowAssistant != null)
            return false;
        if (allowAssistantWhileLocked != null ? !allowAssistantWhileLocked.equals(that.allowAssistantWhileLocked) : that.allowAssistantWhileLocked != null)
            return false;
        if (allowBookstore != null ? !allowBookstore.equals(that.allowBookstore) : that.allowBookstore != null)
            return false;
        if (allowBookstoreErotica != null ? !allowBookstoreErotica.equals(that.allowBookstoreErotica) : that.allowBookstoreErotica != null)
            return false;
        if (allowCamera != null ? !allowCamera.equals(that.allowCamera) : that.allowCamera != null) return false;
        if (allowCloudBackup != null ? !allowCloudBackup.equals(that.allowCloudBackup) : that.allowCloudBackup != null)
            return false;
        if (allowCloudDocumentSync != null ? !allowCloudDocumentSync.equals(that.allowCloudDocumentSync) : that.allowCloudDocumentSync != null)
            return false;
        if (allowDiagnosticSubmission != null ? !allowDiagnosticSubmission.equals(that.allowDiagnosticSubmission) : that.allowDiagnosticSubmission != null)
            return false;
        if (allowExplicitContent != null ? !allowExplicitContent.equals(that.allowExplicitContent) : that.allowExplicitContent != null)
            return false;
        if (allowGameCenter != null ? !allowGameCenter.equals(that.allowGameCenter) : that.allowGameCenter != null)
            return false;
        if (allowGlobalBackgroundFetchWhenRoaming != null ? !allowGlobalBackgroundFetchWhenRoaming.equals(that.allowGlobalBackgroundFetchWhenRoaming) : that.allowGlobalBackgroundFetchWhenRoaming != null)
            return false;
        if (allowInAppPurchases != null ? !allowInAppPurchases.equals(that.allowInAppPurchases) : that.allowInAppPurchases != null)
            return false;
        if (allowMultiplayerGaming != null ? !allowMultiplayerGaming.equals(that.allowMultiplayerGaming) : that.allowMultiplayerGaming != null)
            return false;
        if (allowPassbookWhileLocked != null ? !allowPassbookWhileLocked.equals(that.allowPassbookWhileLocked) : that.allowPassbookWhileLocked != null)
            return false;
        if (allowPhotoStream != null ? !allowPhotoStream.equals(that.allowPhotoStream) : that.allowPhotoStream != null)
            return false;
        if (allowSafari != null ? !allowSafari.equals(that.allowSafari) : that.allowSafari != null) return false;
        if (allowScreenShot != null ? !allowScreenShot.equals(that.allowScreenShot) : that.allowScreenShot != null)
            return false;
        if (allowSharedStream != null ? !allowSharedStream.equals(that.allowSharedStream) : that.allowSharedStream != null)
            return false;
        if (allowUIConfigurationProfileInstallation != null ? !allowUIConfigurationProfileInstallation.equals(that.allowUIConfigurationProfileInstallation) : that.allowUIConfigurationProfileInstallation != null)
            return false;
        if (allowUntrustedTLSPrompt != null ? !allowUntrustedTLSPrompt.equals(that.allowUntrustedTLSPrompt) : that.allowUntrustedTLSPrompt != null)
            return false;
        if (allowVideoConferencing != null ? !allowVideoConferencing.equals(that.allowVideoConferencing) : that.allowVideoConferencing != null)
            return false;
        if (allowVoiceDialing != null ? !allowVoiceDialing.equals(that.allowVoiceDialing) : that.allowVoiceDialing != null)
            return false;
        if (allowYouTube != null ? !allowYouTube.equals(that.allowYouTube) : that.allowYouTube != null) return false;
        if (allowiTunes != null ? !allowiTunes.equals(that.allowiTunes) : that.allowiTunes != null) return false;
        if (forceEncryptedBackUp != null ? !forceEncryptedBackUp.equals(that.forceEncryptedBackUp) : that.forceEncryptedBackUp != null)
            return false;
        if (forceITunesStorePasswordEntry != null ? !forceITunesStorePasswordEntry.equals(that.forceITunesStorePasswordEntry) : that.forceITunesStorePasswordEntry != null)
            return false;
        if (safariAcceptCookies != null ? !safariAcceptCookies.equals(that.safariAcceptCookies) : that.safariAcceptCookies != null)
            return false;
        if (safariAllowAutoFill != null ? !safariAllowAutoFill.equals(that.safariAllowAutoFill) : that.safariAllowAutoFill != null)
            return false;
        if (safariAllowJavaScript != null ? !safariAllowJavaScript.equals(that.safariAllowJavaScript) : that.safariAllowJavaScript != null)
            return false;
        if (safariAllowPopups != null ? !safariAllowPopups.equals(that.safariAllowPopups) : that.safariAllowPopups != null)
            return false;
        return !(safariForceFraudWarning != null ? !safariForceFraudWarning.equals(that.safariForceFraudWarning) : that.safariForceFraudWarning != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (allowAppInstallation != null ? allowAppInstallation.hashCode() : 0);
        result = 31 * result + (allowAssistant != null ? allowAssistant.hashCode() : 0);
        result = 31 * result + (allowAssistantWhileLocked != null ? allowAssistantWhileLocked.hashCode() : 0);
        result = 31 * result + (allowCamera != null ? allowCamera.hashCode() : 0);
        result = 31 * result + (allowVideoConferencing != null ? allowVideoConferencing.hashCode() : 0);
        result = 31 * result + (allowDiagnosticSubmission != null ? allowDiagnosticSubmission.hashCode() : 0);
        result = 31 * result + (allowExplicitContent != null ? allowExplicitContent.hashCode() : 0);
        result = 31 * result + (allowGameCenter != null ? allowGameCenter.hashCode() : 0);
        result = 31 * result + (allowScreenShot != null ? allowScreenShot.hashCode() : 0);
        result = 31 * result + (allowYouTube != null ? allowYouTube.hashCode() : 0);
        result = 31 * result + (allowiTunes != null ? allowiTunes.hashCode() : 0);
        result = 31 * result + (forceITunesStorePasswordEntry != null ? forceITunesStorePasswordEntry.hashCode() : 0);
        result = 31 * result + (allowSafari != null ? allowSafari.hashCode() : 0);
        result = 31 * result + (allowUntrustedTLSPrompt != null ? allowUntrustedTLSPrompt.hashCode() : 0);
        result = 31 * result + (allowCloudBackup != null ? allowCloudBackup.hashCode() : 0);
        result = 31 * result + (allowCloudDocumentSync != null ? allowCloudDocumentSync.hashCode() : 0);
        result = 31 * result + (allowPhotoStream != null ? allowPhotoStream.hashCode() : 0);
        result = 31 * result + (allowBookstore != null ? allowBookstore.hashCode() : 0);
        result = 31 * result + (allowBookstoreErotica != null ? allowBookstoreErotica.hashCode() : 0);
        result = 31 * result + (allowPassbookWhileLocked != null ? allowPassbookWhileLocked.hashCode() : 0);
        result = 31 * result + (allowSharedStream != null ? allowSharedStream.hashCode() : 0);
        result = 31 * result + (allowUIConfigurationProfileInstallation != null ? allowUIConfigurationProfileInstallation.hashCode() : 0);
        result = 31 * result + (allowVoiceDialing != null ? allowVoiceDialing.hashCode() : 0);
        result = 31 * result + (allowInAppPurchases != null ? allowInAppPurchases.hashCode() : 0);
        result = 31 * result + (allowMultiplayerGaming != null ? allowMultiplayerGaming.hashCode() : 0);
        result = 31 * result + (allowAddingGameCenterFriends != null ? allowAddingGameCenterFriends.hashCode() : 0);
        result = 31 * result + (safariAllowAutoFill != null ? safariAllowAutoFill.hashCode() : 0);
        result = 31 * result + (safariAllowJavaScript != null ? safariAllowJavaScript.hashCode() : 0);
        result = 31 * result + (safariAllowPopups != null ? safariAllowPopups.hashCode() : 0);
        result = 31 * result + (safariForceFraudWarning != null ? safariForceFraudWarning.hashCode() : 0);
        result = 31 * result + (safariAcceptCookies != null ? safariAcceptCookies.hashCode() : 0);
        result = 31 * result + (forceEncryptedBackUp != null ? forceEncryptedBackUp.hashCode() : 0);
        result = 31 * result + (allowGlobalBackgroundFetchWhenRoaming != null ? allowGlobalBackgroundFetchWhenRoaming.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadRestrictionsPolicy{" +
                "allowAddingGameCenterFriends=" + allowAddingGameCenterFriends +
                ", allowAppInstallation=" + allowAppInstallation +
                ", allowAssistant=" + allowAssistant +
                ", allowAssistantWhileLocked=" + allowAssistantWhileLocked +
                ", allowCamera=" + allowCamera +
                ", allowVideoConferencing=" + allowVideoConferencing +
                ", allowDiagnosticSubmission=" + allowDiagnosticSubmission +
                ", allowExplicitContent=" + allowExplicitContent +
                ", allowGameCenter=" + allowGameCenter +
                ", allowScreenShot=" + allowScreenShot +
                ", allowYouTube=" + allowYouTube +
                ", allowiTunes=" + allowiTunes +
                ", forceITunesStorePasswordEntry=" + forceITunesStorePasswordEntry +
                ", allowSafari=" + allowSafari +
                ", allowUntrustedTLSPrompt=" + allowUntrustedTLSPrompt +
                ", allowCloudBackup=" + allowCloudBackup +
                ", allowCloudDocumentSync=" + allowCloudDocumentSync +
                ", allowPhotoStream=" + allowPhotoStream +
                ", allowBookstore=" + allowBookstore +
                ", allowBookstoreErotica=" + allowBookstoreErotica +
                ", allowPassbookWhileLocked=" + allowPassbookWhileLocked +
                ", allowSharedStream=" + allowSharedStream +
                ", allowUIConfigurationProfileInstallation=" + allowUIConfigurationProfileInstallation +
                ", allowVoiceDialing=" + allowVoiceDialing +
                ", allowInAppPurchases=" + allowInAppPurchases +
                ", allowMultiplayerGaming=" + allowMultiplayerGaming +
                ", safariAllowAutoFill=" + safariAllowAutoFill +
                ", safariAllowJavaScript=" + safariAllowJavaScript +
                ", safariAllowPopups=" + safariAllowPopups +
                ", safariForceFraudWarning=" + safariForceFraudWarning +
                ", safariAcceptCookies=" + safariAcceptCookies +
                ", forceEncryptedBackUp=" + forceEncryptedBackUp +
                ", allowGlobalBackgroundFetchWhenRoaming=" + allowGlobalBackgroundFetchWhenRoaming +
                "} " + super.toString();
    }
}
