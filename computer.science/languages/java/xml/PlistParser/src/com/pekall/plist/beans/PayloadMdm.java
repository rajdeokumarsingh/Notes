package com.pekall.plist.beans;

public class PayloadMdm extends PayloadBase {
    private Integer AccessRights;
    private String CheckInURL;
    private Boolean CheckOutWhenRemoved;
    private String IdentityCertificateUUID;
    private String ServerURL;
    private Boolean SignMessage;
    private String Topic;

    public PayloadMdm() {
        setPayloadType(PAYLOAD_TYPE_MDM);
    }

    public PayloadMdm(String payloadIdentifier, String payloadType, String payloadUUID,
                      int payloadVersion, String payloadDescription, String payloadDisplayName,
                      String payloadOrganization) {
        super(payloadIdentifier, payloadType, payloadUUID, payloadVersion,
                payloadDescription, payloadDisplayName, payloadOrganization);
        setPayloadType(PAYLOAD_TYPE_MDM);
    }

    public Integer getAccessRights() {
        return AccessRights;
    }

    public void setAccessRights(Integer accessRights) {
        AccessRights = accessRights;
    }

    public String getCheckInURL() {
        return CheckInURL;
    }

    public void setCheckInURL(String checkInURL) {
        CheckInURL = checkInURL;
    }

    public Boolean getCheckOutWhenRemoved() {
        return CheckOutWhenRemoved;
    }

    public void setCheckOutWhenRemoved(Boolean checkOutWhenRemoved) {
        CheckOutWhenRemoved = checkOutWhenRemoved;
    }

    public String getIdentityCertificateUUID() {
        return IdentityCertificateUUID;
    }

    public void setIdentityCertificateUUID(String identityCertificateUUID) {
        IdentityCertificateUUID = identityCertificateUUID;
    }

    public String getServerURL() {
        return ServerURL;
    }

    public void setServerURL(String serverURL) {
        ServerURL = serverURL;
    }

    public Boolean getSignMessage() {
        return SignMessage;
    }

    public void setSignMessage(Boolean signMessage) {
        SignMessage = signMessage;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadMdm)) return false;
        if (!super.equals(o)) return false;

        PayloadMdm that = (PayloadMdm) o;

        if (AccessRights != null ? !AccessRights.equals(that.AccessRights) : that.AccessRights != null) return false;
        if (CheckInURL != null ? !CheckInURL.equals(that.CheckInURL) : that.CheckInURL != null) return false;
        if (CheckOutWhenRemoved != null ? !CheckOutWhenRemoved.equals(that.CheckOutWhenRemoved) : that.CheckOutWhenRemoved != null)
            return false;
        if (IdentityCertificateUUID != null ? !IdentityCertificateUUID.equals(that.IdentityCertificateUUID) : that.IdentityCertificateUUID != null)
            return false;
        if (ServerURL != null ? !ServerURL.equals(that.ServerURL) : that.ServerURL != null) return false;
        if (SignMessage != null ? !SignMessage.equals(that.SignMessage) : that.SignMessage != null) return false;
        if (Topic != null ? !Topic.equals(that.Topic) : that.Topic != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (AccessRights != null ? AccessRights.hashCode() : 0);
        result = 31 * result + (CheckInURL != null ? CheckInURL.hashCode() : 0);
        result = 31 * result + (CheckOutWhenRemoved != null ? CheckOutWhenRemoved.hashCode() : 0);
        result = 31 * result + (IdentityCertificateUUID != null ? IdentityCertificateUUID.hashCode() : 0);
        result = 31 * result + (ServerURL != null ? ServerURL.hashCode() : 0);
        result = 31 * result + (SignMessage != null ? SignMessage.hashCode() : 0);
        result = 31 * result + (Topic != null ? Topic.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadMdm{" +
                "AccessRights=" + AccessRights +
                ", CheckInURL='" + CheckInURL + '\'' +
                ", CheckOutWhenRemoved=" + CheckOutWhenRemoved +
                ", IdentityCertificateUUID='" + IdentityCertificateUUID + '\'' +
                ", ServerURL='" + ServerURL + '\'' +
                ", SignMessage=" + SignMessage +
                ", Topic='" + Topic + '\'' +
                '}';
    }
}
