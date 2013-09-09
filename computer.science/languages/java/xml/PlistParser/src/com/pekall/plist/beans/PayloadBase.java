package com.pekall.plist.beans;

import com.dd.plist.NSDictionary;
import com.pekall.plist.Constants;
import com.pekall.plist.PlistBeanConverter;
import com.pekall.plist.PlistXmlParser;

import java.io.IOException;

/**
 * Base class for an ios payload, containing keys common to all payloads
 */
public class PayloadBase extends BeanBase {

    /**
     * Payload types
     */
    public static final String PAYLOAD_TYPE_PASSWORD_POLICY = "com.apple.mobiledevice.passwordpolicy";
    public static final String PAYLOAD_TYPE_WIFI_MANAGED = "com.apple.wifi.managed";
    public static final String PAYLOAD_TYPE_RESTRICTIONS = "com.apple.applicationaccess";
    public static final String PAYLOAD_TYPE_EMAIL = "com.apple.mail.managed";
    public static final String PAYLOAD_TYPE_REMOVAL_PASSWORD = "com.apple.profileRemovalPassword";
    public static final String PAYLOAD_TYPE_WEB_CLIP = "com.apple.webClip.managed";
    public static final String PAYLOAD_TYPE_LDAP = "com.apple.ldap.account";
    public static final String PAYLOAD_TYPE_IOS_EXCHANGE = "com.apple.eas.account";
    public static final String PAYLOAD_TYPE_OSX_EXCHANGE = "com.apple.ews.account";
    public static final String PAYLOAD_TYPE_VPN = "com.apple.vpn.managed";

    // Just for SU MDM project
    public static final String PAYLOAD_TYPE_SE_BROWSER_SETTINGS = "com.pekall.sebrowser.settings";
    public static final String PAYLOAD_TYPE_SYSTEM_SETTINGS = "com.pekall.system.settings";
    public static final String PAYLOAD_TYPE_LAUNCHER_SETTINGS = "com.pekall.launcher.settings";
    public static final String PAYLOAD_TYPE_ADVT_SETTINGS = "com.pekall.advertise.settings";
    public static final String PAYLOAD_TYPE_WALLPAPER_SETTINGS = "com.pekall.wallpaper.settings";

    public static final String PAYLOAD_TYPE_MEMORY_POLICY = "com.pekall.memory.policy";
    public static final String PAYLOAD_TYPE_APP_CONTROL_POLICY = "com.pekall.app.control.policy";
    public static final String PAYLOAD_TYPE_SYSTEM_EXCEPTION_POLICY = "com.pekall.system.exception.policy";

    public static final String PAYLOAD_TYPE_SECURITY_POLICY = "com.pekall.security.policy";
    public static final String PAYLOAD_TYPE_RESTRICTIONS_ANDROID_POLICY = "com.pekall.restrictions.policy";
    public static final String PAYLOAD_TYPE_NATIVE_APP_CONTROL_POLICY = "com.pekall.native.app.control.policy";
    public static final String PAYLOAD_TYPE_BLUETOOTH_POLICY = "com.pekall.bluetooth.policy";
    public static final String PAYLOAD_TYPE_NET_RESTRICT_POLICY = "com.pekall.network.restriction.policy";
    public static final String PAYLOAD_TYPE_ACTIVE_SYNC_POLICY = "com.pekall.network.active.sync.policy";

    /**
     * The payload type, see PAYLOAD_TYPE_...
     */
    private String PayloadType;
    /**
     * The version number of the individual payload.
     */
    private int PayloadVersion;
    /**
     * A reverse-DNS-style identifier for the specific payload.
     */
    private String PayloadIdentifier;
    /**
     * A globally unique identifier for the payload.
     */
    private String PayloadUUID;
    /**
     * Optional. A human-readable name for the profile payload.
     */
    private String PayloadDisplayName;
    /**
     * Optional. A human-readable description of this payload.
     */
    private String PayloadDescription;
    /**
     * Optional. A human-readable string containing the name of the organization that provided the profile.
     */
    private String PayloadOrganization;

    // PayloadContent could be <dict>, <array> or <data>
    // private Object PayloadContent;

    /**
     * Just for android policy
     * 1 for activated, 0 for sleeping
     */
    private Integer PayloadStatus;

    public PayloadBase() {
    }

    public PayloadBase(String payloadIdentifier, String payloadType, String payloadUUID,
                       int payloadVersion, String payloadDescription, String payloadDisplayName,
                       String payloadOrganization) {
        PayloadIdentifier = payloadIdentifier;
        PayloadType = payloadType;
        PayloadUUID = payloadUUID;
        PayloadVersion = payloadVersion;
        PayloadDescription = payloadDescription;
        PayloadDisplayName = payloadDisplayName;
        PayloadOrganization = payloadOrganization;
    }

    public String getPayloadIdentifier() {
        return PayloadIdentifier;
    }

    public void setPayloadIdentifier(String payloadIdentifier) {
        PayloadIdentifier = payloadIdentifier;
    }

    public String getPayloadType() {
        return PayloadType;
    }

    public void setPayloadType(String payloadType) {
        PayloadType = payloadType;
    }

    public String getPayloadUUID() {
        return PayloadUUID;
    }

    public void setPayloadUUID(String payloadUUID) {
        PayloadUUID = payloadUUID;
    }

    public int getPayloadVersion() {
        return PayloadVersion;
    }

    public void setPayloadVersion(int payloadVersion) {
        PayloadVersion = payloadVersion;
    }

    public String getPayloadDescription() {
        return PayloadDescription;
    }

    public void setPayloadDescription(String payloadDescription) {
        PayloadDescription = payloadDescription;
    }

    public String getPayloadDisplayName() {
        return PayloadDisplayName;
    }

    public void setPayloadDisplayName(String payloadDisplayName) {
        PayloadDisplayName = payloadDisplayName;
    }

    public String getPayloadOrganization() {
        return PayloadOrganization;
    }

    public void setPayloadOrganization(String payloadOrganization) {
        PayloadOrganization = payloadOrganization;
    }

    public Integer getPayloadStatus() {
        return PayloadStatus;
    }

    public void setPayloadStatus(Integer payloadStatus) {
        PayloadStatus = payloadStatus;
    }

    public boolean baseEquals(Object o) {
        if (this == o) return true;
        if (o == null || !PayloadBase.class.isAssignableFrom(o.getClass())) return false;

        PayloadBase that = (PayloadBase) o;

        if (PayloadVersion != that.PayloadVersion) return false;
        if (!PayloadDescription.equals(that.PayloadDescription)) return false;
        if (!PayloadDisplayName.equals(that.PayloadDisplayName)) return false;
        if (!PayloadIdentifier.equals(that.PayloadIdentifier)) return false;
        if (!PayloadOrganization.equals(that.PayloadOrganization)) return false;
        if (!PayloadType.equals(that.PayloadType)) return false;
        if (!PayloadUUID.equals(that.PayloadUUID)) return false;

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadBase)) return false;

        PayloadBase base = (PayloadBase) o;

        if (PayloadVersion != base.PayloadVersion) return false;
        if (PayloadDescription != null ? !PayloadDescription.equals(base.PayloadDescription) : base.PayloadDescription != null)
            return false;
        if (PayloadDisplayName != null ? !PayloadDisplayName.equals(base.PayloadDisplayName) : base.PayloadDisplayName != null)
            return false;
        if (PayloadIdentifier != null ? !PayloadIdentifier.equals(base.PayloadIdentifier) : base.PayloadIdentifier != null)
            return false;
        if (PayloadOrganization != null ? !PayloadOrganization.equals(base.PayloadOrganization) : base.PayloadOrganization != null)
            return false;
        if (PayloadType != null ? !PayloadType.equals(base.PayloadType) : base.PayloadType != null) return false;
        if (PayloadUUID != null ? !PayloadUUID.equals(base.PayloadUUID) : base.PayloadUUID != null) return false;
        if (PayloadStatus != null ? !PayloadStatus.equals(base.PayloadStatus) : base.PayloadStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = PayloadType != null ? PayloadType.hashCode() : 0;
        result = 31 * result + PayloadVersion;
        result = 31 * result + (PayloadIdentifier != null ? PayloadIdentifier.hashCode() : 0);
        result = 31 * result + (PayloadUUID != null ? PayloadUUID.hashCode() : 0);
        result = 31 * result + (PayloadDisplayName != null ? PayloadDisplayName.hashCode() : 0);
        result = 31 * result + (PayloadDescription != null ? PayloadDescription.hashCode() : 0);
        result = 31 * result + (PayloadOrganization != null ? PayloadOrganization.hashCode() : 0);
        result = 31 * result + (PayloadStatus != null ? PayloadStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadBase{" +
                "PayloadType='" + PayloadType + '\'' +
                ", PayloadVersion=" + PayloadVersion +
                ", PayloadIdentifier='" + PayloadIdentifier + '\'' +
                ", PayloadUUID='" + PayloadUUID + '\'' +
                ", PayloadDisplayName='" + PayloadDisplayName + '\'' +
                ", PayloadDescription='" + PayloadDescription + '\'' +
                ", PayloadOrganization='" + PayloadOrganization + '\'' +
                ", PayloadStatus='" + PayloadStatus + '\'' +
                '}';
    }
}
