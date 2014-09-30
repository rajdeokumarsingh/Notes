package com.ray.demo.ssl;

import org.springframework.util.StringUtils;

/**
 * The CN filed of certificate contains name and id number
 * of the policy officer.
 */
public class PoliceOfficerCN {

    private String name;

    private String idNum;

    public PoliceOfficerCN(String cn) {
        if (StringUtils.isEmpty(cn)) {
            throw new IllegalArgumentException("cn should not be empty!");
        }

        String[] tokens = cn.trim().split(" ");
        if (tokens.length != 2) {
            throw new IllegalArgumentException("cn tokens length is not 2, but: " + tokens.length);
        }

        name = tokens[0];
        idNum = tokens[1];
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PoliceOfficerCN that = (PoliceOfficerCN) o;

        if (idNum != null ? !idNum.equals(that.idNum) : that.idNum != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (idNum != null ? idNum.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PoliceOfficerCN{" +
                "name='" + name + '\'' +
                ", idNum='" + idNum + '\'' +
                '}';
    }
}
