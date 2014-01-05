package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Status response for CertificateList command
 */
@SuppressWarnings("UnusedDeclaration")
public class CommandCertificateListStatus extends CommandStatusMsg {
    /**
     * Array of certificate dictionaries.
     */
    private List<CertificateItem> CertificateList;

    public CommandCertificateListStatus() {
    }

    public CommandCertificateListStatus(String status, String UDID, String commandUUID) {
        super(status, UDID, commandUUID);
    }

    public CommandCertificateListStatus(String status, String UDID,
                                        String commandUUID, List<CertificateItem> list) {
        super(status, UDID, commandUUID);
        CertificateList = list;
    }

    public List<CertificateItem> getCertificateList() {
        return CertificateList;
    }

    public void setCertificateList(List<CertificateItem> certificateList) {
        this.CertificateList = certificateList;
    }

    public void addCertItem(CertificateItem item) {
        if (CertificateList == null) {
            CertificateList = new ArrayList<CertificateItem>();
        }
        CertificateList.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandCertificateListStatus)) return false;
        if (!super.equals(o)) return false;

        CommandCertificateListStatus that = (CommandCertificateListStatus) o;

        return this.hashCode() == that.hashCode();

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        for (CertificateItem item : CertificateList) {
            result += item.hashCode();
        }
        return result;
    }
}
