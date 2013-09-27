package com.pekall.plist.beans;

public class APNDataArray {
    /**
     * The only allowed value is com.apple.managedCarrier.
     */
    private String DefaultsDomainName = "com.apple.managedCarrier";

    /**
     * Dictionary, this dictionary contains two key/value pairs.
     */
    private APNSDict DefaultsData;

    public APNSDict getDefaultsData() {
        return DefaultsData;
    }

    public void setDefaultsData(APNSDict defaultsData) {
        DefaultsData = defaultsData;
    }

    public void setDefaultsDomainName(String defaultsDomainName) {
        DefaultsDomainName = defaultsDomainName;
    }

    public APNDataArray() {
    }

    public String getDefaultsDomainName() {
        return DefaultsDomainName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof APNDataArray)) return false;

        APNDataArray that = (APNDataArray) o;

        if (DefaultsData != null ? !DefaultsData.equals(that.DefaultsData) : that.DefaultsData != null) return false;
        if (DefaultsDomainName != null ? !DefaultsDomainName.equals(that.DefaultsDomainName) : that.DefaultsDomainName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = DefaultsDomainName != null ? DefaultsDomainName.hashCode() : 0;
        result = 31 * result + (DefaultsData != null ? DefaultsData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "APNDataArray{" +
                "DefaultsDomainName='" + DefaultsDomainName + '\'' +
                ", DefaultsData=" + DefaultsData +
                '}';
    }
}
