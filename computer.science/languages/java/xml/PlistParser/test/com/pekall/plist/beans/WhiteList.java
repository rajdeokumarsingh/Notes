package com.pekall.plist.beans;


import java.util.ArrayList;
import java.util.List;

public class WhiteList {
    /**
     * List contains white list items
     * Any element named item should go into this list
     */
    List<UrlMatchRule> white_list = new ArrayList<UrlMatchRule>();


    public WhiteList() {
        this(new ArrayList<UrlMatchRule>());
    }

    public WhiteList(List<UrlMatchRule> rules) {
        this.white_list = rules;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (UrlMatchRule rule : white_list) {
            sb.append(rule.toString());
            sb.append('\n');
        }
        return "WhiteList{" +
                "historyWatch=" + sb.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WhiteList)) return false;

        WhiteList whiteList = (WhiteList) o;

        // if (!whiteList.equals(whiteList.whiteList)) return false;
        if(this.hashCode() != this.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int ret = 0;
        for (UrlMatchRule rule : white_list) {
            ret += rule.hashCode();
        }
        return ret;
    }

    public void addRule(UrlMatchRule rule) {
        white_list.add(rule);
    }

    public List<UrlMatchRule> getWhite_list() {
        return white_list;
    }

    public void setWhite_list(List<UrlMatchRule> white_list) {
        this.white_list = white_list;
    }
}
