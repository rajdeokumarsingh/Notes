package com.pekall.plist.beans;

import java.util.Arrays;

/**
 * Entry of the CertificateList array
 */
public class CertificateItem {
    /** Common name of the certificate */
    private String CommonName;

    /** Set to true if this is an identity certificate */
    private boolean IsIdentity;

    /** The certificate in DER-encoded X.509 format. */
    private byte[] Data;

    public CertificateItem() {
    }

    public CertificateItem(String commonName, Boolean isIdentity, byte[] data) {
        CommonName = commonName;
        IsIdentity = isIdentity;
        Data = data;
    }

    public String getCommonName() {
        return CommonName;
    }

    public void setCommonName(String commonName) {
        CommonName = commonName;
    }

    public boolean isIsIdentity() {
        return IsIdentity;
    }

    public void setIsIdentity(boolean isIdentity) {
        IsIdentity = isIdentity;
    }

    public byte[] getData() {
        return Data;
    }

    public void setData(byte[] data) {
        Data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CertificateItem)) return false;

        CertificateItem item = (CertificateItem) o;

        if (IsIdentity != item.IsIdentity) return false;
        if (CommonName != null ? !CommonName.equals(item.CommonName) : item.CommonName != null) return false;
        if (!Arrays.equals(Data, item.Data)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = CommonName != null ? CommonName.hashCode() : 0;
        result = 31 * result + (IsIdentity ? 1 : 0);
        result = 31 * result + (Data != null ? Arrays.hashCode(Data) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CertificateItem{" +
                "CommonName='" + CommonName + '\'' +
                ", IsIdentity=" + IsIdentity +
                ", Data=" + Arrays.toString(Data) +
                '}';
    }
}
