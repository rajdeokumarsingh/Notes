package com.pekall.plist.beans;

/**
 * A dictionary of boolean restrictions.
 */
public class RestrictedBool {
    /**
     * Following fields is in "Passcode Policy Payload",
     * See PayloadPasswordPolicy for details
     */
    private BooleanValue allowSimple;
    private BooleanValue forcePIN;
    private BooleanValue requireAlphanumeric;

    /**
     * Following fields is in "Restrictions Payload"
     */
    private BooleanValue allowAppInstallation;
    private BooleanValue allowAssistant;
    private BooleanValue allowAssistantWhileLocked;
    private BooleanValue allowCamera;
    private BooleanValue allowDiagnosticSubmission;
    private BooleanValue allowExplicitContent;
    private BooleanValue allowGameCenter;
    private BooleanValue allowScreenShot;
    private BooleanValue allowYouTube;
    private BooleanValue allowiTunes;
    private BooleanValue forceITunesStorePasswordEntry;
    private BooleanValue allowSafari;
    private BooleanValue allowUntrustedTLSPrompt;
    private BooleanValue allowCloudBackup;
    private BooleanValue allowCloudDocumentSync;
    private BooleanValue allowPhotoStream;
    private BooleanValue allowBookstore;
    private BooleanValue allowBookstoreErotica;
    private BooleanValue allowPassbookWhileLocked;
    private BooleanValue allowSharedStream;
    private BooleanValue allowUIConfigurationProfileInstallation;

    public RestrictedBool() {
    }

    public Boolean getAllowSimple() {
        if(allowSimple == null) return null;

        return allowSimple.getValue();
    }

    public void setAllowSimple(Boolean allowSimple) {
        if (this.allowSimple == null) {
            this.allowSimple = new BooleanValue();
        }
        this.allowSimple.setValue(allowSimple);
    }

    public Boolean getForcePIN() {
        if(forcePIN == null) return null;

        return forcePIN.getValue();
    }

    public void setForcePIN(Boolean forcePIN) {
        if (this.forcePIN == null) {
            this.forcePIN = new BooleanValue();
        }
        this.forcePIN.setValue(forcePIN);
    }

    public Boolean getRequireAlphanumeric() {
        if(requireAlphanumeric == null) return null;

        return requireAlphanumeric.getValue();
    }

    public void setRequireAlphanumeric(Boolean requireAlphanumeric) {
        if (this.requireAlphanumeric == null) {
            this.requireAlphanumeric = new BooleanValue();
        }
        this.requireAlphanumeric.setValue(requireAlphanumeric);
    }

    public Boolean getAllowAppInstallation() {
        if(allowAppInstallation == null) return null;

        return allowAppInstallation.getValue();
    }

    public void setAllowAppInstallation(Boolean allowAppInstallation) {
        if (this.allowAppInstallation == null) {
            this.allowAppInstallation = new BooleanValue();
        }
        this.allowAppInstallation.setValue(allowAppInstallation);
    }

    public Boolean getAllowAssistant() {
        if(allowAssistant == null) return null;

        return allowAssistant.getValue();
    }

    public void setAllowAssistant(Boolean allowAssistant) {
        if (this.allowAssistant == null) {
            this.allowAssistant = new BooleanValue();
        }
        this.allowAssistant.setValue(allowAssistant);
    }

    public Boolean getAllowAssistantWhileLocked() {
        if(allowAssistantWhileLocked == null) return null;

        return allowAssistantWhileLocked.getValue();
    }

    public void setAllowAssistantWhileLocked(Boolean allowAssistantWhileLocked) {
        if (this.allowAssistantWhileLocked == null) {
            this.allowAssistantWhileLocked = new BooleanValue();
        }
        this.allowAssistantWhileLocked.setValue(allowAssistantWhileLocked);
    }

    public Boolean getAllowCamera() {
        if(allowCamera == null) return null;

        return allowCamera.getValue();
    }

    public void setAllowCamera(Boolean allowCamera) {
        if (this.allowCamera == null) {
            this.allowCamera = new BooleanValue();
        }
        this.allowCamera.setValue(allowCamera);
    }

    public Boolean getAllowDiagnosticSubmission() {
        if(allowDiagnosticSubmission == null) return null;

        return allowDiagnosticSubmission.getValue();
    }

    public void setAllowDiagnosticSubmission(Boolean allowDiagnosticSubmission) {
        if (this.allowDiagnosticSubmission == null) {
            this.allowDiagnosticSubmission = new BooleanValue();
        }
        this.allowDiagnosticSubmission.setValue(allowDiagnosticSubmission);
    }

    public Boolean getAllowExplicitContent() {
        if(allowExplicitContent == null) return null;

        return allowExplicitContent.getValue();
    }

    public void setAllowExplicitContent(Boolean allowExplicitContent) {
        if (this.allowExplicitContent == null) {
            this.allowExplicitContent =  new BooleanValue();
        }
        this.allowExplicitContent.setValue(allowExplicitContent);
    }

    public Boolean getAllowGameCenter() {
        if(allowGameCenter == null) return null;

        return allowGameCenter.getValue();
    }

    public void setAllowGameCenter(Boolean allowGameCenter) {
        if (this.allowGameCenter == null) {
            this.allowGameCenter = new BooleanValue();
        }
        this.allowGameCenter.setValue(allowGameCenter);
    }

    public Boolean getAllowScreenShot() {
        if(allowScreenShot == null) return null;

        return allowScreenShot.getValue();
    }

    public void setAllowScreenShot(Boolean allowScreenShot) {
        if (this.allowScreenShot == null) {
            this.allowScreenShot = new BooleanValue();
        }
        this.allowScreenShot.setValue(allowScreenShot);
    }

    public Boolean getAllowYouTube() {
        if(allowYouTube == null) return null;

        return allowYouTube.getValue();
    }

    public void setAllowYouTube(Boolean allowYouTube) {
        if (this.allowYouTube == null) {
            this.allowYouTube = new BooleanValue();
        }
        this.allowYouTube.setValue(allowYouTube);
    }

    public Boolean getAllowiTunes() {
        if(allowiTunes == null) return null;

        return allowiTunes.getValue();
    }

    public void setAllowiTunes(Boolean allowiTunes) {
        if (this.allowiTunes == null) {
            this.allowiTunes = new BooleanValue();
        }
        this.allowiTunes.setValue(allowiTunes);
    }

    public Boolean getForceITunesStorePasswordEntry() {
        if(forceITunesStorePasswordEntry == null) return null;

        return forceITunesStorePasswordEntry.getValue();
    }

    public void setForceITunesStorePasswordEntry(Boolean forceITunesStorePasswordEntry) {
        if (this.forceITunesStorePasswordEntry == null) {
            this.forceITunesStorePasswordEntry = new BooleanValue();
        }
        this.forceITunesStorePasswordEntry.setValue(forceITunesStorePasswordEntry);
    }

    public Boolean getAllowSafari() {
        if(allowSafari == null) return null;

        return allowSafari.getValue();
    }

    public void setAllowSafari(Boolean allowSafari) {
        if (this.allowSafari == null) {
            this.allowSafari = new BooleanValue();
        }
        this.allowSafari.setValue(allowSafari);
    }

    public Boolean getAllowUntrustedTLSPrompt() {
        if(allowUntrustedTLSPrompt == null) return null;

        return allowUntrustedTLSPrompt.getValue();
    }

    public void setAllowUntrustedTLSPrompt(Boolean allowUntrustedTLSPrompt) {
        if (this.allowUntrustedTLSPrompt == null) {
            this.allowUntrustedTLSPrompt = new BooleanValue();
        }
        this.allowUntrustedTLSPrompt.setValue(allowUntrustedTLSPrompt);
    }


    public Boolean getAllowCloudBackup() {
        if(allowCloudBackup == null) return null;

        return allowCloudBackup.getValue();
    }

    public void setAllowCloudBackup(Boolean allowCloudBackup) {
        if (this.allowCloudBackup == null) {
            this.allowCloudBackup = new BooleanValue();
        }
        this.allowCloudBackup.setValue(allowCloudBackup);
    }

    public Boolean getAllowCloudDocumentSync() {
        if(allowCloudDocumentSync == null) return null;

        return allowCloudDocumentSync.getValue();
    }

    public void setAllowCloudDocumentSync(Boolean allowCloudDocumentSync) {
        if (this.allowCloudDocumentSync == null) {
            this.allowCloudDocumentSync = new BooleanValue();
        }
        this.allowCloudDocumentSync.setValue(allowCloudDocumentSync);
    }

    public Boolean getAllowPhotoStream() {
        if(allowPhotoStream == null) return null;

        return allowPhotoStream.getValue();
    }

    public void setAllowPhotoStream(Boolean allowPhotoStream) {
        if (this.allowPhotoStream == null) {
            this.allowPhotoStream = new BooleanValue();
        }
        this.allowPhotoStream.setValue(allowPhotoStream);
    }

    public Boolean getAllowBookstore() {
        if(allowBookstore == null) return null;

        return allowBookstore.getValue();
    }

    public void setAllowBookstore(Boolean allowBookstore) {
        if (this.allowBookstore == null) {
            this.allowBookstore = new BooleanValue();
        }
        this.allowBookstore.setValue(allowBookstore);
    }

    public Boolean getAllowBookstoreErotica() {
        if(allowBookstoreErotica == null) return null;

        return allowBookstoreErotica.getValue();
    }

    public void setAllowBookstoreErotica(Boolean allowBookstoreErotica) {
        if (this.allowBookstoreErotica == null) {
            this.allowBookstoreErotica = new BooleanValue();
        }
        this.allowBookstoreErotica.setValue(allowBookstoreErotica);
    }

    public Boolean getAllowPassbookWhileLocked() {
        if(allowPassbookWhileLocked == null) return null;

        return allowPassbookWhileLocked.getValue();
    }

    public void setAllowPassbookWhileLocked(Boolean allowPassbookWhileLocked) {
        if (this.allowPassbookWhileLocked == null) {
            this.allowPassbookWhileLocked = new BooleanValue();
        }
        this.allowPassbookWhileLocked.setValue(allowPassbookWhileLocked);
    }

    public Boolean getAllowSharedStream() {
        if(allowSharedStream == null) return null;
        return allowSharedStream.getValue();
    }

    public void setAllowSharedStream(Boolean allowSharedStream) {
        if (this.allowSharedStream == null) {
            this.allowSharedStream = new BooleanValue();
        }
        this.allowSharedStream.setValue(allowSharedStream);
    }

    public Boolean getAllowUIConfigurationProfileInstallation() {
        if(allowUIConfigurationProfileInstallation == null) return null;

        return allowUIConfigurationProfileInstallation.getValue();
    }

    public void setAllowUIConfigurationProfileInstallation(Boolean allowUIConfigurationProfileInstallation) {
        if (this.allowUIConfigurationProfileInstallation == null) {
            this.allowUIConfigurationProfileInstallation = new BooleanValue();
        }
        this.allowUIConfigurationProfileInstallation.setValue(allowUIConfigurationProfileInstallation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RestrictedBool)) return false;

        RestrictedBool that = (RestrictedBool) o;

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
        if (allowPassbookWhileLocked != null ? !allowPassbookWhileLocked.equals(that.allowPassbookWhileLocked) : that.allowPassbookWhileLocked != null)
            return false;
        if (allowPhotoStream != null ? !allowPhotoStream.equals(that.allowPhotoStream) : that.allowPhotoStream != null)
            return false;
        if (allowSafari != null ? !allowSafari.equals(that.allowSafari) : that.allowSafari != null) return false;
        if (allowScreenShot != null ? !allowScreenShot.equals(that.allowScreenShot) : that.allowScreenShot != null)
            return false;
        if (allowSharedStream != null ? !allowSharedStream.equals(that.allowSharedStream) : that.allowSharedStream != null)
            return false;
        if (allowSimple != null ? !allowSimple.equals(that.allowSimple) : that.allowSimple != null) return false;
        if (allowUIConfigurationProfileInstallation != null ? !allowUIConfigurationProfileInstallation.equals(that.allowUIConfigurationProfileInstallation) : that.allowUIConfigurationProfileInstallation != null)
            return false;
        if (allowUntrustedTLSPrompt != null ? !allowUntrustedTLSPrompt.equals(that.allowUntrustedTLSPrompt) : that.allowUntrustedTLSPrompt != null)
            return false;
        if (allowYouTube != null ? !allowYouTube.equals(that.allowYouTube) : that.allowYouTube != null) return false;
        if (allowiTunes != null ? !allowiTunes.equals(that.allowiTunes) : that.allowiTunes != null) return false;
        if (forceITunesStorePasswordEntry != null ? !forceITunesStorePasswordEntry.equals(that.forceITunesStorePasswordEntry) : that.forceITunesStorePasswordEntry != null)
            return false;
        if (forcePIN != null ? !forcePIN.equals(that.forcePIN) : that.forcePIN != null) return false;
        if (requireAlphanumeric != null ? !requireAlphanumeric.equals(that.requireAlphanumeric) : that.requireAlphanumeric != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = allowSimple != null ? allowSimple.hashCode() : 0;
        result = 31 * result + (forcePIN != null ? forcePIN.hashCode() : 0);
        result = 31 * result + (requireAlphanumeric != null ? requireAlphanumeric.hashCode() : 0);
        result = 31 * result + (allowAppInstallation != null ? allowAppInstallation.hashCode() : 0);
        result = 31 * result + (allowAssistant != null ? allowAssistant.hashCode() : 0);
        result = 31 * result + (allowAssistantWhileLocked != null ? allowAssistantWhileLocked.hashCode() : 0);
        result = 31 * result + (allowCamera != null ? allowCamera.hashCode() : 0);
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
        return result;
    }

    @Override
    public String toString() {
        return "RestrictedBool{" +
                "allowSimple=" + allowSimple +
                ", forcePIN=" + forcePIN +
                ", requireAlphanumeric=" + requireAlphanumeric +
                ", allowAppInstallation=" + allowAppInstallation +
                ", allowAssistant=" + allowAssistant +
                ", allowAssistantWhileLocked=" + allowAssistantWhileLocked +
                ", allowCamera=" + allowCamera +
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
                '}';
    }
}
