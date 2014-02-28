package com.pekall.plist.beans;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class PayloadNetRestrictPolicy extends PayloadBase {
    /**
     * See minSecurityLevel
     */
    public static final String SEC_LEVEL_OPEN = "open";
    public static final String SEC_LEVEL_WEP = "wep";
    public static final String SEC_LEVEL_WPA = "wpa";
    public static final String SEC_LEVEL_EAP_LEAP = "eap leap";
    public static final String SEC_LEVEL_EAP_FAST = "eap fast";
    public static final String SEC_LEVEL_EAP_PEAP = "eap peap";
    public static final String SEC_LEVEL_EAP_TTLS = "eap ttls";
    public static final String SEC_LEVEL_EAP_TLS = "eap tls";

    public static final String CTRL_ENABLED = "enabled";
    public static final String CTRL_DISABLE = "disable";
    public static final String CTRL_USR_CONTROLLED = "user controlled";

    public static final String ALLOW_MSG_ALL = "all";
    public static final String ALLOW_MSG_INCOMING_SMS_ONLY = "incoming sms only";
    public static final String ALLOW_MSG_OUTGOING_SMS_ONLY = "outgoing sms only";
    public static final String ALLOW_MSG_NONE = "none";

    /**
     * Allow Emergency Calls only
     */
    private Boolean allowEmgCallOnly;

    /**
     * Allow Wi-Fi
     */
    private Boolean allowWifi;

    /**
     * Whitelisted SSIDs
     */
    private List<String> WhitelistedSSIDs;

    /**
     * Blacklisted SSIDs
     */
    private List<String> BlacklistedSSIDs;

    /**
     * Wi-Fi Network Minimum Security Level
     * See SEC_LEVEL_...
     */
    private String minSecurityLevel;

    /**
     * Allow user to add Wi-Fi networks
     */
    private Boolean allowUsrAddWifi;

    /**
     * Allow Data Network
     * See CTRL_...
     */
    private String allowDataNetwork;

    /**
     * Mobile AP
     * See CTRL_...
     */
    private String mobileAP;

    /**
     * USB Tethering
     */
    private String usbTethering;

    /**
     * Allow SMS & MMS
     * See ALLOW_MSG_...
     */
    private String allowMessage;

    /**
     * Allow Data Roaming
     * See ALLOW_MSG_...
     */
    private String allowDataRoaming;

    /**
     * Allow Sync during Roaming
     */
    private Boolean allowSyncRoaming;

    /**
     * Allow Voice Roaming
     */
    private Boolean allowVoiceRoaming;

    public PayloadNetRestrictPolicy() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_NET_RESTRICT_POLICY);
    }

    public PayloadNetRestrictPolicy(String payloadIdentifier, String payloadType, String payloadUUID, int payloadVersion, String payloadDescription, String payloadDisplayName, String payloadOrganization) {
        super(payloadIdentifier, payloadType, payloadUUID, payloadVersion, payloadDescription, payloadDisplayName, payloadOrganization);
        setPayloadType(PayloadBase.PAYLOAD_TYPE_NET_RESTRICT_POLICY);
    }

    public Boolean getAllowEmgCallOnly() {
        return allowEmgCallOnly;
    }

    public void setAllowEmgCallOnly(Boolean allowEmgCallOnly) {
        this.allowEmgCallOnly = allowEmgCallOnly;
    }

    public Boolean getAllowWifi() {
        return allowWifi;
    }

    public void setAllowWifi(Boolean allowWifi) {
        this.allowWifi = allowWifi;
    }

    public List<String> getWhitelistedSSIDs() {
        return WhitelistedSSIDs;
    }

    public void setWhitelistedSSIDs(ArrayList<String> whitelistedSSIDs) {
        WhitelistedSSIDs = whitelistedSSIDs;
    }

    public void addWhitelistedSSID(String ssid) {
        if (WhitelistedSSIDs == null) {
            WhitelistedSSIDs = new ArrayList<String>();
        }
        WhitelistedSSIDs.add(ssid);
    }

    public List<String> getBlacklistedSSIDs() {
        return BlacklistedSSIDs;
    }

    public void setBlacklistedSSIDs(ArrayList<String> blacklistedSSIDs) {
        BlacklistedSSIDs = blacklistedSSIDs;
    }

    public void addBlacklistedSSIDs(String ssid) {
        if (BlacklistedSSIDs == null) {
            BlacklistedSSIDs = new ArrayList<String>();
        }
        BlacklistedSSIDs.add(ssid);
    }

    public String getMinSecurityLevel() {
        return minSecurityLevel;
    }

    public void setMinSecurityLevel(String minSecurityLevel) {
        this.minSecurityLevel = minSecurityLevel;
    }

    public Boolean getAllowUsrAddWifi() {
        return allowUsrAddWifi;
    }

    public void setAllowUsrAddWifi(Boolean allowUsrAddWifi) {
        this.allowUsrAddWifi = allowUsrAddWifi;
    }

    public String getAllowDataNetwork() {
        return allowDataNetwork;
    }

    public void setAllowDataNetwork(String allowDataNetwork) {
        this.allowDataNetwork = allowDataNetwork;
    }

    public String getMobileAP() {
        return mobileAP;
    }

    public void setMobileAP(String mobileAP) {
        this.mobileAP = mobileAP;
    }

    public String getUsbTethering() {
        return usbTethering;
    }

    public void setUsbTethering(String usbTethering) {
        this.usbTethering = usbTethering;
    }

    public String getAllowMessage() {
        return allowMessage;
    }

    public void setAllowMessage(String allowMessage) {
        this.allowMessage = allowMessage;
    }

    public String getAllowDataRoaming() {
        return allowDataRoaming;
    }

    public void setAllowDataRoaming(String allowDataRoaming) {
        this.allowDataRoaming = allowDataRoaming;
    }

    public Boolean getAllowSyncRoaming() {
        return allowSyncRoaming;
    }

    public void setAllowSyncRoaming(Boolean allowSyncRoaming) {
        this.allowSyncRoaming = allowSyncRoaming;
    }

    public Boolean getAllowVoiceRoaming() {
        return allowVoiceRoaming;
    }

    public void setAllowVoiceRoaming(Boolean allowVoiceRoaming) {
        this.allowVoiceRoaming = allowVoiceRoaming;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadNetRestrictPolicy that = (PayloadNetRestrictPolicy) o;

        if (BlacklistedSSIDs != null ? !BlacklistedSSIDs.equals(that.BlacklistedSSIDs) : that.BlacklistedSSIDs != null)
            return false;
        if (WhitelistedSSIDs != null ? !WhitelistedSSIDs.equals(that.WhitelistedSSIDs) : that.WhitelistedSSIDs != null)
            return false;
        if (allowDataNetwork != null ? !allowDataNetwork.equals(that.allowDataNetwork) : that.allowDataNetwork != null)
            return false;
        if (allowDataRoaming != null ? !allowDataRoaming.equals(that.allowDataRoaming) : that.allowDataRoaming != null)
            return false;
        if (allowEmgCallOnly != null ? !allowEmgCallOnly.equals(that.allowEmgCallOnly) : that.allowEmgCallOnly != null)
            return false;
        if (allowMessage != null ? !allowMessage.equals(that.allowMessage) : that.allowMessage != null) return false;
        if (allowSyncRoaming != null ? !allowSyncRoaming.equals(that.allowSyncRoaming) : that.allowSyncRoaming != null)
            return false;
        if (allowUsrAddWifi != null ? !allowUsrAddWifi.equals(that.allowUsrAddWifi) : that.allowUsrAddWifi != null)
            return false;
        if (allowVoiceRoaming != null ? !allowVoiceRoaming.equals(that.allowVoiceRoaming) : that.allowVoiceRoaming != null)
            return false;
        if (allowWifi != null ? !allowWifi.equals(that.allowWifi) : that.allowWifi != null) return false;
        if (minSecurityLevel != null ? !minSecurityLevel.equals(that.minSecurityLevel) : that.minSecurityLevel != null)
            return false;
        if (mobileAP != null ? !mobileAP.equals(that.mobileAP) : that.mobileAP != null) return false;
        return !(usbTethering != null ? !usbTethering.equals(that.usbTethering) : that.usbTethering != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (allowEmgCallOnly != null ? allowEmgCallOnly.hashCode() : 0);
        result = 31 * result + (allowWifi != null ? allowWifi.hashCode() : 0);
        result = 31 * result + (WhitelistedSSIDs != null ? WhitelistedSSIDs.hashCode() : 0);
        result = 31 * result + (BlacklistedSSIDs != null ? BlacklistedSSIDs.hashCode() : 0);
        result = 31 * result + (minSecurityLevel != null ? minSecurityLevel.hashCode() : 0);
        result = 31 * result + (allowUsrAddWifi != null ? allowUsrAddWifi.hashCode() : 0);
        result = 31 * result + (allowDataNetwork != null ? allowDataNetwork.hashCode() : 0);
        result = 31 * result + (mobileAP != null ? mobileAP.hashCode() : 0);
        result = 31 * result + (usbTethering != null ? usbTethering.hashCode() : 0);
        result = 31 * result + (allowMessage != null ? allowMessage.hashCode() : 0);
        result = 31 * result + (allowDataRoaming != null ? allowDataRoaming.hashCode() : 0);
        result = 31 * result + (allowSyncRoaming != null ? allowSyncRoaming.hashCode() : 0);
        result = 31 * result + (allowVoiceRoaming != null ? allowVoiceRoaming.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadNetRestrictPolicy{" +
                "allowEmgCallOnly=" + allowEmgCallOnly +
                ", allowWifi=" + allowWifi +
                ", WhitelistedSSIDs=" + WhitelistedSSIDs +
                ", BlacklistedSSIDs=" + BlacklistedSSIDs +
                ", minSecurityLevel='" + minSecurityLevel + '\'' +
                ", allowUsrAddWifi=" + allowUsrAddWifi +
                ", allowDataNetwork='" + allowDataNetwork + '\'' +
                ", mobileAP='" + mobileAP + '\'' +
                ", usbTethering='" + usbTethering + '\'' +
                ", allowMessage='" + allowMessage + '\'' +
                ", allowDataRoaming='" + allowDataRoaming + '\'' +
                ", allowSyncRoaming=" + allowSyncRoaming +
                ", allowVoiceRoaming=" + allowVoiceRoaming +
                '}';
    }
}
