package com.pekall.csv.bean;

import java.lang.reflect.Field;

public class UserInfo implements CsvGenerator { // implements Serializable {
    private Long id;
    private Long type;
    private String password;
    private Long status;
    private Long roles;
    private String fullName;
    private String email;
    private String firstName;
    private String lastName;
    private Long enterpriseId;
    private String token;
    private Long tmpId;
    private String username;
    private Integer deleted;
    private String phoneNumber;
    private Long createTime;
    private Long deviceNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getRoles() {
        return roles;
    }

    public void setRoles(Long roles) {
        this.roles = roles;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getTmpId() {
        return tmpId;
    }

    public void setTmpId(Long tmpId) {
        this.tmpId = tmpId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(Long deviceNo) {
        this.deviceNo = deviceNo;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", type=" + type +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", roles=" + roles +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", enterpriseId=" + enterpriseId +
                ", token='" + token + '\'' +
                ", tmpId=" + tmpId +
                ", username='" + username + '\'' +
                ", deleted=" + deleted +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createTime=" + createTime +
                ", deviceNo=" + deviceNo +
                '}';
    }

    @Override
    public String toCvs() {
        StringBuilder sb = new StringBuilder();
        String[] fields = {"id", "type", "password", "status", "roles", "fullName", "email",
                "firstName", "lastName", "enterpriseId", "token", "tmpId", "username",
                "deleted", "phoneNumber", "createTime", "deviceNo"
        };
        for (String fieldName : fields) {
            try {
                Field field = this.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
                Object fieldObj = field.get(this);
                if (fieldObj != null) {
                    // add empty string "" explicitly
                    if (fieldObj.getClass().equals(String.class) &&
                            "".equals((String) fieldObj)) {
                        sb.append("\"\"");
                    } else {
                        sb.append(fieldObj.toString());
                    }
                }
                sb.append(",");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (sb.length() > 0) {
            // delete last comma
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static UserInfo fromCsv(String line) {
        String[] fieldNames = {"id", "type", "password", "status", "roles", "fullName", "email",
                "firstName", "lastName", "enterpriseId", "token", "tmpId", "username",
                "deleted", "phoneNumber", "createTime", "deviceNo"
        };

        UserInfo userInfo = new UserInfo();
        String[] tokens = line.split(",");

        for (int i = 0; i < tokens.length; i++) {
            try {
                if (tokens[i] == null || "".equals(tokens[i])) continue;

                Field field = userInfo.getClass().getDeclaredField(fieldNames[i]);
                field.setAccessible(true);
                if (field.getType().equals(Boolean.class)) {
                    field.set(userInfo, Boolean.valueOf(tokens[i]));
                } else if (field.getType().equals(Byte.class)) {
                    field.set(userInfo, Byte.valueOf(tokens[i]));
                } else if (field.getType().equals(Integer.class)) {
                    field.set(userInfo, Integer.valueOf(tokens[i]));
                } else if (field.getType().equals(Long.class)) {
                    field.set(userInfo, Long.valueOf(tokens[i]));
                } else if (field.getType().equals(Float.class)) {
                    field.set(userInfo, Float.valueOf(tokens[i]));
                } else if (field.getType().equals(Double.class)) {
                    field.set(userInfo, Double.valueOf(tokens[i]));
                } else if (field.getType().equals(String.class)) {
                    if ("\"\"".equals(tokens[i])) {
                        field.set(userInfo, "");
                    } else {
                        field.set(userInfo, String.valueOf(tokens[i]));
                    }
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;

        UserInfo userInfo = (UserInfo) o;

        if (createTime != null ? !createTime.equals(userInfo.createTime) : userInfo.createTime != null) return false;
        if (deleted != null ? !deleted.equals(userInfo.deleted) : userInfo.deleted != null) return false;
        if (deviceNo != null ? !deviceNo.equals(userInfo.deviceNo) : userInfo.deviceNo != null) return false;
        if (email != null ? !email.equals(userInfo.email) : userInfo.email != null) return false;
        if (enterpriseId != null ? !enterpriseId.equals(userInfo.enterpriseId) : userInfo.enterpriseId != null)
            return false;
        if (firstName != null ? !firstName.equals(userInfo.firstName) : userInfo.firstName != null) return false;
        if (fullName != null ? !fullName.equals(userInfo.fullName) : userInfo.fullName != null) return false;
        if (id != null ? !id.equals(userInfo.id) : userInfo.id != null) return false;
        if (lastName != null ? !lastName.equals(userInfo.lastName) : userInfo.lastName != null) return false;
        if (password != null ? !password.equals(userInfo.password) : userInfo.password != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(userInfo.phoneNumber) : userInfo.phoneNumber != null)
            return false;
        if (roles != null ? !roles.equals(userInfo.roles) : userInfo.roles != null) return false;
        if (status != null ? !status.equals(userInfo.status) : userInfo.status != null) return false;
        if (tmpId != null ? !tmpId.equals(userInfo.tmpId) : userInfo.tmpId != null) return false;
        if (token != null ? !token.equals(userInfo.token) : userInfo.token != null) return false;
        if (type != null ? !type.equals(userInfo.type) : userInfo.type != null) return false;
        if (username != null ? !username.equals(userInfo.username) : userInfo.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (enterpriseId != null ? enterpriseId.hashCode() : 0);
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (tmpId != null ? tmpId.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (deviceNo != null ? deviceNo.hashCode() : 0);
        return result;
    }
}
