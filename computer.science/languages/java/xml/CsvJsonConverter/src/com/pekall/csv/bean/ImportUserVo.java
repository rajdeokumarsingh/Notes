package com.pekall.csv.bean;

import java.util.ArrayList;
import java.util.List;

public class ImportUserVo {
    private String username;
    private String password;
    private String email;
    private Integer type;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;

    List<ImportDeviceInfoVo> devices;

    public static ImportUserVo fromCsvUserInfo(CsvLine csvInfo) {
        ImportUserVo importInfo = new ImportUserVo();
        importInfo.setUsername(csvInfo.getUsername());
        importInfo.setPassword(csvInfo.getPassword());
        importInfo.setEmail(csvInfo.getEmail());
        importInfo.setType(csvInfo.getUserType());
        importInfo.setFirstName(csvInfo.getFirstName());
        importInfo.setLastName(csvInfo.getLastName());
        importInfo.setFullName(csvInfo.getFullName());
        importInfo.setPhone(csvInfo.getPhone());

        return importInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ImportDeviceInfoVo> getDevices() {
        return devices;
    }

    public void setDevices(List<ImportDeviceInfoVo> devices) {
        this.devices = devices;
    }

    public void addDevice(ImportDeviceInfoVo device) {
        if (this.devices == null) {
            this.devices = new ArrayList<ImportDeviceInfoVo>();
        }
        this.devices.add(device);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImportUserVo)) return false;

        ImportUserVo that = (ImportUserVo) o;

        if (devices != null ? !devices.equals(that.devices) : that.devices != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (devices != null ? devices.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ImportUserVo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", devices=" + devices +
                '}';
    }
}


