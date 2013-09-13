package com.pekall.plist.beans;

public class PayloadNativeAppCtrlPolicy extends PayloadBase {
    private Boolean googlePlay;
    private Boolean youtube;
    private Boolean mail;
    private Boolean browser;
    private Boolean settings;
    private Boolean gallery;
    private Boolean gmail;
    private Boolean googleMap;
    private Boolean voiceDialer;

    public PayloadNativeAppCtrlPolicy() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_NATIVE_APP_CONTROL_POLICY);
    }

    public PayloadNativeAppCtrlPolicy(String payloadIdentifier, String payloadType, String payloadUUID, int payloadVersion, String payloadDescription, String payloadDisplayName, String payloadOrganization) {
        super(payloadIdentifier, payloadType, payloadUUID, payloadVersion, payloadDescription, payloadDisplayName, payloadOrganization);
        setPayloadType(PayloadBase.PAYLOAD_TYPE_NATIVE_APP_CONTROL_POLICY);
    }

    public Boolean getGooglePlay() {
        return googlePlay;
    }

    public void setGooglePlay(Boolean googlePlay) {
        this.googlePlay = googlePlay;
    }

    public Boolean getYoutube() {
        return youtube;
    }

    public void setYoutube(Boolean youtube) {
        this.youtube = youtube;
    }

    public Boolean getMail() {
        return mail;
    }

    public void setMail(Boolean mail) {
        this.mail = mail;
    }

    public Boolean getBrowser() {
        return browser;
    }

    public void setBrowser(Boolean browser) {
        this.browser = browser;
    }

    public Boolean getSettings() {
        return settings;
    }

    public void setSettings(Boolean settings) {
        this.settings = settings;
    }

    public Boolean getGallery() {
        return gallery;
    }

    public void setGallery(Boolean gallery) {
        this.gallery = gallery;
    }

    public Boolean getGmail() {
        return gmail;
    }

    public void setGmail(Boolean gmail) {
        this.gmail = gmail;
    }

    public Boolean getGoogleMap() {
        return googleMap;
    }

    public void setGoogleMap(Boolean googleMap) {
        this.googleMap = googleMap;
    }

    public Boolean getVoiceDialer() {
        return voiceDialer;
    }

    public void setVoiceDialer(Boolean voiceDialer) {
        this.voiceDialer = voiceDialer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadNativeAppCtrlPolicy that = (PayloadNativeAppCtrlPolicy) o;

        if (browser != null ? !browser.equals(that.browser) : that.browser != null) return false;
        if (gallery != null ? !gallery.equals(that.gallery) : that.gallery != null) return false;
        if (gmail != null ? !gmail.equals(that.gmail) : that.gmail != null) return false;
        if (googleMap != null ? !googleMap.equals(that.googleMap) : that.googleMap != null) return false;
        if (googlePlay != null ? !googlePlay.equals(that.googlePlay) : that.googlePlay != null) return false;
        if (mail != null ? !mail.equals(that.mail) : that.mail != null) return false;
        if (settings != null ? !settings.equals(that.settings) : that.settings != null) return false;
        if (voiceDialer != null ? !voiceDialer.equals(that.voiceDialer) : that.voiceDialer != null) return false;
        if (youtube != null ? !youtube.equals(that.youtube) : that.youtube != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (googlePlay != null ? googlePlay.hashCode() : 0);
        result = 31 * result + (youtube != null ? youtube.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (browser != null ? browser.hashCode() : 0);
        result = 31 * result + (settings != null ? settings.hashCode() : 0);
        result = 31 * result + (gallery != null ? gallery.hashCode() : 0);
        result = 31 * result + (gmail != null ? gmail.hashCode() : 0);
        result = 31 * result + (googleMap != null ? googleMap.hashCode() : 0);
        result = 31 * result + (voiceDialer != null ? voiceDialer.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadNativeAppCtrlPolicy{" +
                "googlePlay=" + googlePlay +
                ", youtube=" + youtube +
                ", mail=" + mail +
                ", browser=" + browser +
                ", settings=" + settings +
                ", gallery=" + gallery +
                ", gmail=" + gmail +
                ", googleMap=" + googleMap +
                ", voiceDialer=" + voiceDialer +
                '}';
    }
}
