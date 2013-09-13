package com.pekall.plist.beans;

/**
 * Just for android
 */
public class PayloadWallpaper extends PayloadBase {
    /**
     * Upload wallpaper images using the Manage Policy Files workflow and then select the required
     * wallpaper images for different device types here.
     */
    private Boolean configWallpaper;

    /**
     *  For devices with a resolution <= 800px
     */
    private String lowResolution;

    /**
     * For devices with a resolution > 800px and <= 1024px
     */
    private String mediumResolution;

    /**
     * For devices with a resolution > 1024px
     */
    private String highResolution;

    /**
     * Allow User to change Wallpaper
     */
    private Boolean allowUsrChangeWallpaper;

    public PayloadWallpaper() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_WALLPAPER_SETTINGS);
    }

    public PayloadWallpaper(String payloadIdentifier, String payloadType,
                            String payloadUUID,  int payloadVersion, String payloadDescription,
                            String payloadDisplayName, String payloadOrganization) {
        super(payloadIdentifier, payloadType, payloadUUID, payloadVersion,
                payloadDescription, payloadDisplayName, payloadOrganization);
        setPayloadType(PayloadBase.PAYLOAD_TYPE_WALLPAPER_SETTINGS);
    }

    public Boolean getConfigWallpaper() {
        return configWallpaper;
    }

    public void setConfigWallpaper(Boolean configWallpaper) {
        this.configWallpaper = configWallpaper;
    }

    public String getLowResolution() {
        return lowResolution;
    }

    public void setLowResolution(String lowResolution) {
        this.lowResolution = lowResolution;
    }

    public String getMediumResolution() {
        return mediumResolution;
    }

    public void setMediumResolution(String mediumResolution) {
        this.mediumResolution = mediumResolution;
    }

    public String getHighResolution() {
        return highResolution;
    }

    public void setHighResolution(String highResolution) {
        this.highResolution = highResolution;
    }

    public Boolean getAllowUsrChangeWallpaper() {
        return allowUsrChangeWallpaper;
    }

    public void setAllowUsrChangeWallpaper(Boolean allowUsrChangeWallpaper) {
        this.allowUsrChangeWallpaper = allowUsrChangeWallpaper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadWallpaper that = (PayloadWallpaper) o;

        if (allowUsrChangeWallpaper != null ? !allowUsrChangeWallpaper.equals(that.allowUsrChangeWallpaper) : that.allowUsrChangeWallpaper != null)
            return false;
        if (configWallpaper != null ? !configWallpaper.equals(that.configWallpaper) : that.configWallpaper != null)
            return false;
        if (highResolution != null ? !highResolution.equals(that.highResolution) : that.highResolution != null)
            return false;
        if (lowResolution != null ? !lowResolution.equals(that.lowResolution) : that.lowResolution != null)
            return false;
        if (mediumResolution != null ? !mediumResolution.equals(that.mediumResolution) : that.mediumResolution != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (configWallpaper != null ? configWallpaper.hashCode() : 0);
        result = 31 * result + (lowResolution != null ? lowResolution.hashCode() : 0);
        result = 31 * result + (mediumResolution != null ? mediumResolution.hashCode() : 0);
        result = 31 * result + (highResolution != null ? highResolution.hashCode() : 0);
        result = 31 * result + (allowUsrChangeWallpaper != null ? allowUsrChangeWallpaper.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadWallpaper{" +
                "configWallpaper=" + configWallpaper +
                ", lowResolution='" + lowResolution + '\'' +
                ", mediumResolution='" + mediumResolution + '\'' +
                ", highResolution='" + highResolution + '\'' +
                ", allowUsrChangeWallpaper=" + allowUsrChangeWallpaper +
                '}';
    }
}
