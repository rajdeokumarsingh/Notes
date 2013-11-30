package com.pekall.csv.bean;

public class ContactsCompanyInfo {
    /** 公司名称 */
    private String name;

    /** 公司地址 */
    private String address;

    /** 邮编 */
    private String postcode;
    /** 电话 */

    private String phoneNumber;

    /** 传真 */
    private String faxNumber;

    public ContactsCompanyInfo() {
    }

    public ContactsCompanyInfo(String name, String address, String postcode,
                               String phoneNumber, String faxNumber) {
        this.name = name;
        this.address = address;
        this.postcode = postcode;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactsCompanyInfo)) return false;

        ContactsCompanyInfo that = (ContactsCompanyInfo) o;

        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (faxNumber != null ? !faxNumber.equals(that.faxNumber) : that.faxNumber != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;
        if (postcode != null ? !postcode.equals(that.postcode) : that.postcode != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (faxNumber != null ? faxNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactsCompanyInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", postcode='" + postcode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", faxNumber='" + faxNumber + '\'' +
                '}';
    }
}
