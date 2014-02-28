package com.pekall.plist.beans;

@SuppressWarnings("UnusedDeclaration")
public class PayloadScep extends PayloadBase {

    private ScepContent PayloadContent;

    public PayloadScep() {
        setPayloadType(PAYLOAD_TYPE_SCEP);
    }

    public PayloadScep(String payloadIdentifier, String payloadType, String payloadUUID,
                       int payloadVersion, String payloadDescription, String payloadDisplayName,
                       String payloadOrganization) {
        super(payloadIdentifier, payloadType, payloadUUID, payloadVersion,
                payloadDescription, payloadDisplayName, payloadOrganization);
        setPayloadType(PAYLOAD_TYPE_SCEP);
    }

    public ScepContent getPayloadContent() {
        return PayloadContent;
    }

    public void setPayloadContent(ScepContent payloadContent) {
        PayloadContent = payloadContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadScep)) return false;
        if (!super.equals(o)) return false;

        PayloadScep that = (PayloadScep) o;

        return !(PayloadContent != null ? !PayloadContent.equals(that.PayloadContent) : that.PayloadContent != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (PayloadContent != null ? PayloadContent.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadScep{" +
                "PayloadContent=" + PayloadContent +
                '}';
    }
}
