package com.design.pattern.proxy;

/**
 * Created with IntelliJ IDEA.
 * User: jiangrui
 * Date: 13-6-18
 * Time: 下午5:47
 * To change this template use File | Settings | File Templates.
 */
public class OrderData {
    private String cid;
    private String oid;

    public OrderData(String cid, String oid) {
        this.cid = cid;
        this.oid = oid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }
}
