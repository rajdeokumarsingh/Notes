/*
 * Copyright (C) 2013 Capital Alliance Software LTD (Pekall)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pekall.demo.bean;

/**
 * Bean for a line of CSV file
 */
public class DemoBean {

    // user information
    public String username;
    public String password;
    public String email;
    public Integer userType;
    public String fullName;
    public String firstName;
    public String lastName;
    public String phone;

    // device information
    public Integer deviceType;
    public Integer os;
    public String language;
    public String securityInfo;
    public String tmpUuid;

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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getOs() {
        return os;
    }

    public void setOs(Integer os) {
        this.os = os;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(String securityInfo) {
        this.securityInfo = securityInfo;
    }

    public String getTmpUuid() {
        return tmpUuid;
    }

    public void setTmpUuid(String tmpUuid) {
        this.tmpUuid = tmpUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DemoBean)) return false;

        DemoBean that = (DemoBean) o;

        if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (os != null ? !os.equals(that.os) : that.os != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (securityInfo != null ? !securityInfo.equals(that.securityInfo) : that.securityInfo != null) return false;
        if (tmpUuid != null ? !tmpUuid.equals(that.tmpUuid) : that.tmpUuid != null) return false;
        if (userType != null ? !userType.equals(that.userType) : that.userType != null) return false;
        //noinspection RedundantIfStatement
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
        result = 31 * result + (os != null ? os.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (securityInfo != null ? securityInfo.hashCode() : 0);
        result = 31 * result + (tmpUuid != null ? tmpUuid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DemoBean{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", fullName='" + fullName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", deviceType=" + deviceType +
                ", os=" + os +
                ", language='" + language + '\'' +
                ", securityInfo='" + securityInfo + '\'' +
                ", tmpUuid='" + tmpUuid + '\'' +
                '}';
    }
}