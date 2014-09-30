package com.ray.demo.ssl;

import org.springframework.util.StringUtils;

/**
 * The description filed contains imsi, imei and police number
 * of the police officer.
 */
public class PoliceOfficerDesc {

    private String imsi;

    private String imei;

    private String policeNum;

    public PoliceOfficerDesc(String description) {
        if (StringUtils.isEmpty(description)) {
            throw new IllegalArgumentException("description should not be empty!");
        }

        String[] tokens = description.trim().split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("description should contain 3 tokens, but : " + tokens.length);
        }

        imsi = tokens[0];
        imei = tokens[1];
        policeNum = tokens[2];
    }

    public String getImsi() {
        return imsi;
    }

    public String getImei() {
        return imei;
    }

    public String getPoliceNum() {
        return policeNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliceOfficerDesc that = (PoliceOfficerDesc) o;

        if (imei != null ? !imei.equals(that.imei) : that.imei != null) return false;
        if (imsi != null ? !imsi.equals(that.imsi) : that.imsi != null) return false;
        if (policeNum != null ? !policeNum.equals(that.policeNum) : that.policeNum != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = imsi != null ? imsi.hashCode() : 0;
        result = 31 * result + (imei != null ? imei.hashCode() : 0);
        result = 31 * result + (policeNum != null ? policeNum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PoliceOfficerDesc{" +
                "imsi='" + imsi + '\'' +
                ", imei='" + imei + '\'' +
                ", policeNum='" + policeNum + '\'' +
                '}';
    }
}
