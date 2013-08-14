package com.pekall.plist.beans;

/**
 * Base class for an ios payload, containing keys common to all payloads
 */
public class PayloadBase {

    /**
     * Payload types
     */
    public static final String PAYLOAD_TYPE_PASSWORD_POLICY = "com.apple.mobiledevice.passwordpolicy";
    public static final String PAYLOAD_TYPE_WIFI_MANAGED = "com.apple.wifi.managed";

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
                '}';
    }
}
