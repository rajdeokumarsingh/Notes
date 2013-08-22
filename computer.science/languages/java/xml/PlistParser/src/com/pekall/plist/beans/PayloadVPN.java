package com.pekall.plist.beans;

/**
 * VPN profile
 */
public class PayloadVPN extends PayloadBase {

    /**
     * See VPNType
     */
    public static final String TYPE_L2TP = "L2TP";
    public static final String TYPE_PPTP = "PPTP";
    public static final String TYPE_IPSEC = "IPSec";

    /**
     * Description of the VPN connection displayed on the device.
     */
    private String UserDefinedName;

    /**
     * Specifies whether to send all traffic through the VPN interface.
     * If true, all network traffic is sent over VPN.
     */
    private Boolean OverridePrimary;

    /**
     * Determines the settings available in the payload for this type of VPN connection.
     * It can have three possible values: "L2TP", "PPTP", or "IPSec", representing L2TP, PPTP
     * and Cisco IPSec respectively. This key can also be “VPN” to support additional
     * services via it’s VPNSubType key.
     */
    private String VPNType;

    private PPPInfo PPP;
    private IPSecInfo IPSec;

    public PayloadVPN() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_VPN);
    }

    public String getUserDefinedName() {
        return UserDefinedName;
    }

    public void setUserDefinedName(String userDefinedName) {
        UserDefinedName = userDefinedName;
    }

    public Boolean getOverridePrimary() {
        return OverridePrimary;
    }

    public void setOverridePrimary(Boolean overridePrimary) {
        OverridePrimary = overridePrimary;
    }

    public String getVPNType() {
        return VPNType;
    }

    public void setVPNType(String VPNType) {
        this.VPNType = VPNType;
    }

    public PPPInfo getPPP() {
        return PPP;
    }

    public void setPPP(PPPInfo PPP) {
        this.PPP = PPP;
    }

    public IPSecInfo getIPSec() {
        return IPSec;
    }

    public void setIPSec(IPSecInfo IPSec) {
        this.IPSec = IPSec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadVPN)) return false;
        if (!super.equals(o)) return false;

        PayloadVPN that = (PayloadVPN) o;

        if (IPSec != null ? !IPSec.equals(that.IPSec) : that.IPSec != null) return false;
        if (OverridePrimary != null ? !OverridePrimary.equals(that.OverridePrimary) : that.OverridePrimary != null)
            return false;
        if (PPP != null ? !PPP.equals(that.PPP) : that.PPP != null) return false;
        if (UserDefinedName != null ? !UserDefinedName.equals(that.UserDefinedName) : that.UserDefinedName != null)
            return false;
        if (VPNType != null ? !VPNType.equals(that.VPNType) : that.VPNType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (UserDefinedName != null ? UserDefinedName.hashCode() : 0);
        result = 31 * result + (OverridePrimary != null ? OverridePrimary.hashCode() : 0);
        result = 31 * result + (VPNType != null ? VPNType.hashCode() : 0);
        result = 31 * result + (PPP != null ? PPP.hashCode() : 0);
        result = 31 * result + (IPSec != null ? IPSec.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadVPN{" +
                "UserDefinedName='" + UserDefinedName + '\'' +
                ", OverridePrimary=" + OverridePrimary +
                ", VPNType='" + VPNType + '\'' +
                ", PPP=" + PPP +
                ", IPSec=" + IPSec +
                '}';
    }
}
