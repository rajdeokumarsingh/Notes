package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class APNSDict {
    /**
     * This array contains an arbitrary number of dictionaries,
     * each describing an APN configuration, with the key/value pairs below.
     */
    private List<APNConfig> apns;

    public APNSDict() {
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
        if (!(o instanceof APNSDict)) return false;

        APNSDict apnsDict = (APNSDict) o;

        return !(apns != null ? !apns.equals(apnsDict.apns) : apnsDict.apns != null);

    }

    @Override
    public int hashCode() {
        return apns != null ? apns.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "APNSDict{" +
                "apns=" + apns +
                '}';
    }
}

