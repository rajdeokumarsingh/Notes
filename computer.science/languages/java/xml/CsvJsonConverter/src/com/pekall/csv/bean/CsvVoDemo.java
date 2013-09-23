package com.pekall.csv.bean;

public class CsvVoDemo extends CsvVoBase {

    private Integer type;
    private Integer os;
    private String language;
    private String securityInfo;
    private String tmpUuid;
    private String username;

    public CsvVoDemo() {
        super(new String[]{"type", "os", "language", "securityInfo", "tmpUuid", "username"});
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
}
