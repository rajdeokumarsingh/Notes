package com.pekall.plist.beans;

import java.util.Arrays;

/**
 * A Web Clip payload provides a web clipping on the user’s home screen
 * as though the user had saved a bookmark to the home screen.
 */
public class PayloadWebClip extends PayloadBase {

    /**
     * The URL that the Web Clip should open when clicked.
     * The URL must begin with HTTP or HTTPS or it won't work.
     */
    private String URL;

    /**
     * The name of the Web Clip as displayed on the Home screen.
     */
    private String Label;

    /**
     * Optional. A PNG icon to be shown on the Home screen.
     * Should be 59 x 60 pixels in size. If not specified, a white square will be shown.
     */
    private byte[] Icon;

    /**
     * Optional. If No, the user cannot remove the Web Clip,
     * but it will be removed if the profile is deleted.
     */
    private Boolean IsRemovable;

    public PayloadWebClip() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_WEB_CLIP);
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public byte[] getIcon() {
        return Icon;
    }

    public void setIcon(byte[] icon) {
        Icon = icon;
    }

    public Boolean getIsRemovable() {
        return IsRemovable;
    }

    public void setIsRemovable(Boolean isRemovable) {
        IsRemovable = isRemovable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadWebClip)) return false;
        if (!super.equals(o)) return false;

        PayloadWebClip that = (PayloadWebClip) o;

        if (!Arrays.equals(Icon, that.Icon)) return false;
        if (IsRemovable != null ? !IsRemovable.equals(that.IsRemovable) : that.IsRemovable != null) return false;
        if (Label != null ? !Label.equals(that.Label) : that.Label != null) return false;
        if (URL != null ? !URL.equals(that.URL) : that.URL != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (URL != null ? URL.hashCode() : 0);
        result = 31 * result + (Label != null ? Label.hashCode() : 0);
        result = 31 * result + (Icon != null ? Arrays.hashCode(Icon) : 0);
        result = 31 * result + (IsRemovable != null ? IsRemovable.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadWebClip{" +
                "URL='" + URL + '\'' +
                ", Label='" + Label + '\'' +
                ", Icon=" + Arrays.toString(Icon) +
                ", IsRemovable=" + IsRemovable +
                '}';
    }
}
