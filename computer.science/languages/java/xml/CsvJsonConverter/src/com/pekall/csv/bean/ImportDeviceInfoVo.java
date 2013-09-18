package com.pekall.csv.bean;


import java.lang.reflect.Field;

public class ImportDeviceInfoVo {
    private Integer type;
    private Integer os;
    private String language;
    private String securityInfo;
    private String tmpUuid;
    private String username;

    /**
     * Check whether this is an pure empty bean
     * @return whether this is an pure empty bean
     */
    public boolean isEmptyDevice() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object obj = field.get(this);
                if(obj != null) return false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static ImportDeviceInfoVo fromCsvInfo(CsvLine csvUserInfo) {
        ImportDeviceInfoVo importInfo = new ImportDeviceInfoVo();
        importInfo.setType(csvUserInfo.getDeviceType());
        importInfo.setOs(csvUserInfo.getOs());
        importInfo.setLanguage(csvUserInfo.getLanguage());
        importInfo.setSecurityInfo(csvUserInfo.getSecurityInfo());
        importInfo.setTmpUuid(csvUserInfo.getTmpUuid());
        if (importInfo.isEmptyDevice()) {
            return null;
        }

        importInfo.setUsername(csvUserInfo.getUsername());
        return importInfo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImportDeviceInfoVo)) return false;

        ImportDeviceInfoVo that = (ImportDeviceInfoVo) o;

        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (os != null ? !os.equals(that.os) : that.os != null) return false;
        if (securityInfo != null ? !securityInfo.equals(that.securityInfo) : that.securityInfo != null) return false;
        if (tmpUuid != null ? !tmpUuid.equals(that.tmpUuid) : that.tmpUuid != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (os != null ? os.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (securityInfo != null ? securityInfo.hashCode() : 0);
        result = 31 * result + (tmpUuid != null ? tmpUuid.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ImportDeviceInfoVo{" +
                "type=" + type +
                ", os=" + os +
                ", language='" + language + '\'' +
                ", securityInfo='" + securityInfo + '\'' +
                ", tmpUuid='" + tmpUuid + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
