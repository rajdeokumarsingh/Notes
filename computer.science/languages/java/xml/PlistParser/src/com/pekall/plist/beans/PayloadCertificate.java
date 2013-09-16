package com.pekall.plist.beans;

import java.util.Arrays;

public class PayloadCertificate extends PayloadBase {
    private String PayloadCertificateFileName;
    private byte[] PayloadContent;

    public PayloadCertificate() {
        setPayloadType(PAYLOAD_TYPE_CERTIFICATE);
    }

    public PayloadCertificate(String payloadIdentifier, String payloadType, String payloadUUID, int payloadVersion, String payloadDescription, String payloadDisplayName, String payloadOrganization) {
        super(payloadIdentifier, payloadType, payloadUUID, payloadVersion, payloadDescription, payloadDisplayName, payloadOrganization);
        setPayloadType(PAYLOAD_TYPE_CERTIFICATE);
    }

    public String getPayloadCertificateFileName() {
        return PayloadCertificateFileName;
    }

    public void setPayloadCertificateFileName(String payloadCertificateFileName) {
        PayloadCertificateFileName = payloadCertificateFileName;
    }

    public byte[] getPayloadContent() {
        return PayloadContent;
    }

    public void setPayloadContent(byte[] payloadContent) {
        PayloadContent = payloadContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadCertificate)) return false;
        if (!super.equals(o)) return false;

        PayloadCertificate that = (PayloadCertificate) o;

        if (PayloadCertificateFileName != null ? !PayloadCertificateFileName.equals(that.PayloadCertificateFileName) : that.PayloadCertificateFileName != null)
            return false;
        if (!Arrays.equals(PayloadContent, that.PayloadContent)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (PayloadCertificateFileName != null ? PayloadCertificateFileName.hashCode() : 0);
        result = 31 * result + (PayloadContent != null ? Arrays.hashCode(PayloadContent) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadCertificate{" +
                "super='" + super.toString() + '\'' +
                "PayloadCertificateFileName='" + PayloadCertificateFileName + '\'' +
                ", PayloadContent=" + Arrays.toString(PayloadContent) +
                '}';
    }
}
