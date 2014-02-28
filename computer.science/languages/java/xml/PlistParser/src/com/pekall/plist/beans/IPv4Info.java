package com.pekall.plist.beans;

@SuppressWarnings("UnusedDeclaration")
public class IPv4Info {

    /**
     * Specifies whether to send all traffic through the VPN interface.
     * If true, all network traffic is sent over VPN.
     */
    private Integer OverridePrimary;

    public Integer getOverridePrimary() {
        return OverridePrimary;
    }

    public void setOverridePrimary(Integer overridePrimary) {
        OverridePrimary = overridePrimary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IPv4Info)) return false;

        IPv4Info iPv4Info = (IPv4Info) o;

        return !(OverridePrimary != null ? !OverridePrimary.equals(iPv4Info.OverridePrimary) : iPv4Info.OverridePrimary != null);

    }

    @Override
    public int hashCode() {
        return OverridePrimary != null ? OverridePrimary.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "IPv4Info{" +
                "OverridePrimary=" + OverridePrimary +
                "} " + super.toString();
    }
}
