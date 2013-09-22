package com.pekall.csv.bean;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Bean for a line of CSV file
 */
public class CsvLine {

    /**
     * Fields in CSV file. If you change the format of CSV, this must be changed accordingly.
     */
    private static String[] CSV_FIELDS  = new String[]{
            "username", "password", "email", "userType", "fullName", "firstName", "lastName", "phone",
            "deviceType", "os", "language", "securityInfo", "tmpUuid"};

    // user information
    private String username;
    private String password;
    private String email;
    private Integer userType;
    private String fullName;
    private String firstName;
    private String lastName;
    private String phone;

    // device information
    private Integer deviceType;
    private Integer os;
    private String language;
    private String securityInfo;
    private String tmpUuid;

    /**
     * Convert a CSV line to a bean object
     * @param tokens
     * @return bean object
     */
    public static CsvLine fromCsv(String[] tokens) {
        return createCsvLine(tokens);
    }

    /**
     * Convert a CSV line to a bean object
     * @param line
     * @return bean object
     */
    public static CsvLine fromCsv(String line) {
        String[] tokens = combineTokens(line.split(","));
        return createCsvLine(tokens);
    }

    private static CsvLine createCsvLine(String[] tokens) {
        CsvLine userInfo = new CsvLine();
        for (int i = 0; i < tokens.length; i++) {
            try {
                if (tokens[i] == null || "".equals(tokens[i])) continue;

                Field field = userInfo.getClass().getDeclaredField(CSV_FIELDS[i]);
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
                    /* if ("\"\"".equals(tokens[i])) {
                        field.set(userInfo, "");
                    } else {
                        field.set(userInfo, String.valueOf(tokens[i]));
                    } */
                    field.set(userInfo, String.valueOf(tokens[i]));
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return userInfo;
    }

    /* Handle commas in a CSV field. If there is a comma in a CSV field,
        the field will be surrounded by double quotes.
    */
    static String[] combineTokens(String[] tokens) {
        ArrayList<String> combinedTokens = new ArrayList<String>();
        String combine = null;
        for (String token : tokens) {
            if (combine == null && token.startsWith("\"")) {
                // start to combine tokens in quotes
                combine = token;
                continue;
            }
            if (combine != null) {
                combine += "," + token;
                if(token.endsWith("\"")) {
                    // token finished
                    combinedTokens.add(combine.replace("\"", ""));
                    combine = null;
                }
                continue;
            }
            // normal tokens
            combinedTokens.add(token);
        }

        String[] tmp = new String[combinedTokens.size()];
        combinedTokens.toArray(tmp);
        return tmp;
    }

    /**
     * Convert bean to a csv line
     * @return a csv line from the bean
     */
    public String toCvs() {
        StringBuilder sb = new StringBuilder();
        for (String fieldName : CSV_FIELDS) {
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
                // ",," mean a null object
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
        if (!(o instanceof CsvLine)) return false;

        CsvLine that = (CsvLine) o;

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
        return "CsvLine{" +
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
