package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
class APNData {
    /**
     * The only allowed value is com.apple.managedCarrier.
     */
    private final String DefaultsDomainName = "com.apple.managedCarrier";

    /**
     * This array contains an arbitrary number of dictionaries,
     * each describing an APN configuration, with the key/value pairs below.
     */
    private List<APNConfig> apns;

    public APNData() {
    }

    public String getDefaultsDomainName() {
        return DefaultsDomainName;
    }

    public List<APNConfig> getApns() {
        return apns;
    }

    public void setApns(List<APNConfig> apns) {
        this.apns = apns;
    }

    public void addApn(APNConfig apn) {
        if (apns == null) {
            apns = new ArrayList<APNConfig>();
        }
        apns.add(apn);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof APNData)) return false;

        APNData apnData = (APNData) o;

        if (!DefaultsDomainName.equals(apnData.DefaultsDomainName))
            return false;
        return !(apns != null ? !apns.equals(apnData.apns) : apnData.apns != null);

    }

    @Override
    public int hashCode() {
        int result = DefaultsDomainName.hashCode();
        result = 31 * result + (apns != null ? apns.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "APNData{" +
                "DefaultsDomainName='" + DefaultsDomainName + '\'' +
                ", apns=" + apns +
                '}';
    }
}
