package com.pekall.csv.bean;

import java.util.ArrayList;
import java.util.List;

public class ContactInfo implements CsvGenerator { // implements Serializable {
    private String result;
    private Long records;

    private List<UserInfo> users;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

    public void addUserInfo(UserInfo info) {
        if (users == null) {
            users = new ArrayList<UserInfo>();
        }
        users.add(info);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (users != null) {
            sb.append("{");
            for (UserInfo user : users) {
                sb.append(user.toString() + "\n");
            }
            sb.append("}");
        }
        return "ContactInfo{" +
                "result=" + result +
                ", records=" + records +
                ", users=" + sb.toString() +
                '}';
    }

    @Override
    public String toCvs() {
        StringBuilder sb = new StringBuilder();
        if (users == null) {
            return sb.toString();
        }

        for (UserInfo user : users) {
            sb.append(user.toCvs() + "\n");
        }

        if (sb.length() > 0) {
            // delete last new line
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactInfo)) return false;

        ContactInfo info = (ContactInfo) o;

        if (records != null ? !records.equals(info.records) : info.records != null) return false;
        if (result != null ? !result.equals(info.result) : info.result != null) return false;
        // if (users != null ? !users.equals(info.users) : info.users != null) return false;
        if (this.hashCode() != info.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = result != null ? result.hashCode() : 0;
        result1 = 31 * result1 + (records != null ? records.hashCode() : 0);
        if (users != null) {
            for (UserInfo user : users) {
                result1 += user.hashCode();
            }
        }
        // result1 = 31 * result1 + (users != null ? users.hashCode() : 0);
        return result1;
    }
}
