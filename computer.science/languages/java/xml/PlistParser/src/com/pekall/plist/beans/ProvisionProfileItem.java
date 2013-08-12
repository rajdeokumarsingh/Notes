package com.pekall.plist.beans;

import java.util.Date;

/**
 * Entry in the ProvisioningProfileList array
 */
public class ProvisionProfileItem {
    /** The display name of the profile. */
    private String Name;

    /** The UUID of the profile. */
    private String UUID;

    /** The expiry date of the profile. */
    private Date ExpiryDate;

    public ProvisionProfileItem() {
    }

    public ProvisionProfileItem(String name, String UUID, Date expiryDate) {
        Name = name;
        this.UUID = UUID;
        ExpiryDate = expiryDate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Date getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        ExpiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProvisionProfileItem)) return false;

        ProvisionProfileItem that = (ProvisionProfileItem) o;

        if (ExpiryDate != null ?
                !ExpiryDate.toString().equals(that.ExpiryDate.toString()) :
                that.ExpiryDate != null) return false;
        if (Name != null ? !Name.equals(that.Name) : that.Name != null) return false;
        if (UUID != null ? !UUID.equals(that.UUID) : that.UUID != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = Name != null ? Name.hashCode() : 0;
        result = 31 * result + (UUID != null ? UUID.hashCode() : 0);
        result = 31 * result + (ExpiryDate != null ? ExpiryDate.toString().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProvisionProfileItem{" +
                "Name='" + Name + '\'' +
                ", UUID='" + UUID + '\'' +
                ", ExpiryDate=" + (ExpiryDate != null ? ExpiryDate.toString() : "")+
                '}';
    }
}
