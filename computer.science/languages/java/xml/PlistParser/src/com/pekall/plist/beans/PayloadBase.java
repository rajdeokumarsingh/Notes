package com.pekall.plist.beans;

/**
 * Base class for an ios payload
 */
public class PayloadBase {
    private String PayloadIdentifier = "";
    private String PayloadType = "";
    private String PayloadUUID = "";
    private int PayloadVersion;
    private String PayloadDescription = "";
    private String PayloadDisplayName = "";
    private String PayloadOrganization = "";
    private boolean PayloadRemovalDisallowed;

    // PayloadContent could be <dict>, <array> or <data>
    // private Object PayloadContent;

    public PayloadBase() {
    }

    public PayloadBase(String payloadIdentifier, String payloadType, String payloadUUID,
                       int payloadVersion, String payloadDescription, String payloadDisplayName,
                       String payloadOrganization, boolean payloadRemovalDisallowed) {
        PayloadIdentifier = payloadIdentifier;
        PayloadType = payloadType;
        PayloadUUID = payloadUUID;
        PayloadVersion = payloadVersion;
        PayloadDescription = payloadDescription;
        PayloadDisplayName = payloadDisplayName;
        PayloadOrganization = payloadOrganization;
        PayloadRemovalDisallowed = payloadRemovalDisallowed;
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

    public boolean isPayloadRemovalDisallowed() {
        return PayloadRemovalDisallowed;
    }

    public void setPayloadRemovalDisallowed(boolean payloadRemovalDisallowed) {
        PayloadRemovalDisallowed = payloadRemovalDisallowed;
    }

    public boolean baseEquals(Object o) {
        if (this == o) return true;
        if (o == null || !PayloadBase.class.isAssignableFrom(o.getClass())) return false;

        PayloadBase that = (PayloadBase) o;

        if (PayloadRemovalDisallowed != that.PayloadRemovalDisallowed) return false;
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
        if (o == null || getClass() != o.getClass()) return false;

        PayloadBase that = (PayloadBase) o;

        if (PayloadRemovalDisallowed != that.PayloadRemovalDisallowed) return false;
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
    public int hashCode() {
        int result = PayloadIdentifier.hashCode();
        result = 31 * result + PayloadType.hashCode();
        result = 31 * result + PayloadUUID.hashCode();
        result = 31 * result + PayloadVersion;
        result = 31 * result + PayloadDescription.hashCode();
        result = 31 * result + PayloadDisplayName.hashCode();
        result = 31 * result + PayloadOrganization.hashCode();
        result = 31 * result + (PayloadRemovalDisallowed ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadBase{" +
                "PayloadIdentifier='" + PayloadIdentifier + '\'' +
                ", PayloadType='" + PayloadType + '\'' +
                ", PayloadUUID='" + PayloadUUID + '\'' +
                ", PayloadVersion=" + PayloadVersion +
                ", PayloadDescription='" + PayloadDescription + '\'' +
                ", PayloadDisplayName='" + PayloadDisplayName + '\'' +
                ", PayloadOrganization='" + PayloadOrganization + '\'' +
                ", PayloadRemovalDisallowed=" + PayloadRemovalDisallowed +
                '}';
    }
}
