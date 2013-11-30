package com.pekall.csv.bean;

public class ContactsEmployeeInfo {
    /** 部门 */
    private String department;

    /** 姓名	*/
    private String name;

    /** 办公地点	*/
    private String location;

    /** 电话	*/
    private String phoneNumber;

    /** 手机	*/
    private String cellPhoneNumber;

    /** MSN	*/
    private String msn;

    /** 公司邮箱 */
    private String email;

    public ContactsEmployeeInfo() {
    }

    public ContactsEmployeeInfo(String department, String name, String location,  String phoneNumber,
                                String cellPhoneNumber, String msn, String email) {
        this.department = department;
        this.name = name;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.cellPhoneNumber = cellPhoneNumber;
        this.msn = msn;
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactsEmployeeInfo)) return false;

        ContactsEmployeeInfo that = (ContactsEmployeeInfo) o;

        if (cellPhoneNumber != null ? !cellPhoneNumber.equals(that.cellPhoneNumber) : that.cellPhoneNumber != null)
            return false;
        if (department != null ? !department.equals(that.department) : that.department != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (msn != null ? !msn.equals(that.msn) : that.msn != null) return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(that.phoneNumber) : that.phoneNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = department != null ? department.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (cellPhoneNumber != null ? cellPhoneNumber.hashCode() : 0);
        result = 31 * result + (msn != null ? msn.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ContactsEmployeeInfo{" +
                "department='" + department + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", cellPhoneNumber='" + cellPhoneNumber + '\'' +
                ", msn='" + msn + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
