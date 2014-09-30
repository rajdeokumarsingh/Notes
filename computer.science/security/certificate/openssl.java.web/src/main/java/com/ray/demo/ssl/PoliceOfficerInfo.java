package com.ray.demo.ssl;

/**
 * Util class which contain information of a police officer
 * got from a X509 certificate.
 */
public class PoliceOfficerInfo {

    private String name;

    private String idNum;

    private String email;

    private String imsi;

    private String imei;

    private String policeNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getPoliceNum() {
        return policeNum;
    }

    public void setPoliceNum(String policeNum) {
        this.policeNum = policeNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliceOfficerInfo that = (PoliceOfficerInfo) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (idNum != null ? !idNum.equals(that.idNum) : that.idNum != null) return false;
        if (imei != null ? !imei.equals(that.imei) : that.imei != null) return false;
        if (imsi != null ? !imsi.equals(that.imsi) : that.imsi != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (policeNum != null ? !policeNum.equals(that.policeNum) : that.policeNum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (idNum != null ? idNum.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (imsi != null ? imsi.hashCode() : 0);
        result = 31 * result + (imei != null ? imei.hashCode() : 0);
        result = 31 * result + (policeNum != null ? policeNum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PoliceOfficerInfo{" +
                "name='" + name + '\'' +
                ", idNum='" + idNum + '\'' +
                ", email='" + email + '\'' +
                ", imsi='" + imsi + '\'' +
                ", imei='" + imei + '\'' +
                ", policeNum='" + policeNum + '\'' +
                '}';
    }
}
