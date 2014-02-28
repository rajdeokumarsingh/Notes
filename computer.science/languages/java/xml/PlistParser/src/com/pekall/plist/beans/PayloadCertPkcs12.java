package com.pekall.plist.beans;

import java.util.Arrays;

@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class PayloadCertPkcs12 extends PayloadBase {
    private String Password;
    private String PayloadCertificateFileName;
    private byte[] PayloadContent;

    public PayloadCertPkcs12() {
        setPayloadType(PAYLOAD_TYPE_CERT_PKCS12);
    }

    public PayloadCertPkcs12(String payloadIdentifier, String payloadType, String payloadUUID,
                             int payloadVersion, String payloadDescription, String payloadDisplayName,
                             String payloadOrganization) {
        super(payloadIdentifier, payloadType, payloadUUID, payloadVersion,
                payloadDescription, payloadDisplayName, payloadOrganization);
        setPayloadType(PAYLOAD_TYPE_CERT_PKCS12);
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
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
        if (!(o instanceof PayloadCertPkcs12)) return false;
        if (!super.equals(o)) return false;

        PayloadCertPkcs12 that = (PayloadCertPkcs12) o;

        if (Password != null ? !Password.equals(that.Password) : that.Password != null) return false;
        if (PayloadCertificateFileName != null ? !PayloadCertificateFileName.equals(that.PayloadCertificateFileName) : that.PayloadCertificateFileName != null)
            return false;
        return Arrays.equals(PayloadContent, that.PayloadContent);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (Password != null ? Password.hashCode() : 0);
        result = 31 * result + (PayloadCertificateFileName != null ? PayloadCertificateFileName.hashCode() : 0);
        result = 31 * result + (PayloadContent != null ? Arrays.hashCode(PayloadContent) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadCertPkcs12{" +
                "Password='" + Password + '\'' +
                ", PayloadCertificateFileName='" + PayloadCertificateFileName + '\'' +
                ", PayloadContent=" + Arrays.toString(PayloadContent) +
                '}';
    }
}
