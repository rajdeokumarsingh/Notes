package com.pekall.plist.beans;

@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class PayloadBluetoothPolicy extends PayloadBase {
    /**
     * Allow Device discovery via Bluetooth
     */
    private Boolean allowDeviceDiscovery;

    /**
     * Allow Bluetooth Pairing
     */
    private Boolean allowPairing;

    /**
     * Allow Bluetooth Headset devices
     */
    private Boolean allowHeadset;

    /**
     * Allow Bluetooth Hands-free devices
     */
    private Boolean allowHandsFree;

    /**
     * Allow Bluetooth A2DP (Advanced Audio Distribution Profile) devices
     */
    private Boolean allowA2DP;

    /**
     * Allow Outgoing Calls
     */
    private Boolean allowOutgoingCalls;

    /**
     * Allow Data Transfer via Bluetooth
     */
    private Boolean allowDataTransfer;

    /**
     * Allow Bluetooth Tethering
     */
    private Boolean allowTethering;

    /**
     * Allow connection to Desktop or Laptop via Bluetooth
     */
    private Boolean allow2Desktop;

    public PayloadBluetoothPolicy() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_BLUETOOTH_POLICY);
    }

    public PayloadBluetoothPolicy(String payloadIdentifier, String payloadType, String payloadUUID, int payloadVersion, String payloadDescription, String payloadDisplayName, String payloadOrganization) {
        super(payloadIdentifier, payloadType, payloadUUID, payloadVersion, payloadDescription, payloadDisplayName, payloadOrganization);
        setPayloadType(PayloadBase.PAYLOAD_TYPE_BLUETOOTH_POLICY);
    }

    public Boolean getAllowDeviceDiscovery() {
        return allowDeviceDiscovery;
    }

    public void setAllowDeviceDiscovery(Boolean allowDeviceDiscovery) {
        this.allowDeviceDiscovery = allowDeviceDiscovery;
    }

    public Boolean getAllowPairing() {
        return allowPairing;
    }

    public void setAllowPairing(Boolean allowPairing) {
        this.allowPairing = allowPairing;
    }

    public Boolean getAllowHeadset() {
        return allowHeadset;
    }

    public void setAllowHeadset(Boolean allowHeadset) {
        this.allowHeadset = allowHeadset;
    }

    public Boolean getAllowHandsFree() {
        return allowHandsFree;
    }

    public void setAllowHandsFree(Boolean allowHandsFree) {
        this.allowHandsFree = allowHandsFree;
    }

    public Boolean getAllowA2DP() {
        return allowA2DP;
    }

    public void setAllowA2DP(Boolean allowA2DP) {
        this.allowA2DP = allowA2DP;
    }

    public Boolean getAllowOutgoingCalls() {
        return allowOutgoingCalls;
    }

    public void setAllowOutgoingCalls(Boolean allowOutgoingCalls) {
        this.allowOutgoingCalls = allowOutgoingCalls;
    }

    public Boolean getAllowDataTransfer() {
        return allowDataTransfer;
    }

    public void setAllowDataTransfer(Boolean allowDataTransfer) {
        this.allowDataTransfer = allowDataTransfer;
    }

    public Boolean getAllowTethering() {
        return allowTethering;
    }

    public void setAllowTethering(Boolean allowTethering) {
        this.allowTethering = allowTethering;
    }

    public Boolean getAllow2Desktop() {
        return allow2Desktop;
    }

    public void setAllow2Desktop(Boolean allow2Desktop) {
        this.allow2Desktop = allow2Desktop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadBluetoothPolicy that = (PayloadBluetoothPolicy) o;

        if (allow2Desktop != null ? !allow2Desktop.equals(that.allow2Desktop) : that.allow2Desktop != null)
            return false;
        if (allowA2DP != null ? !allowA2DP.equals(that.allowA2DP) : that.allowA2DP != null) return false;
        if (allowDataTransfer != null ? !allowDataTransfer.equals(that.allowDataTransfer) : that.allowDataTransfer != null)
            return false;
        if (allowDeviceDiscovery != null ? !allowDeviceDiscovery.equals(that.allowDeviceDiscovery) : that.allowDeviceDiscovery != null)
            return false;
        if (allowHandsFree != null ? !allowHandsFree.equals(that.allowHandsFree) : that.allowHandsFree != null)
            return false;
        if (allowHeadset != null ? !allowHeadset.equals(that.allowHeadset) : that.allowHeadset != null) return false;
        if (allowOutgoingCalls != null ? !allowOutgoingCalls.equals(that.allowOutgoingCalls) : that.allowOutgoingCalls != null)
            return false;
        if (allowPairing != null ? !allowPairing.equals(that.allowPairing) : that.allowPairing != null) return false;
        return !(allowTethering != null ? !allowTethering.equals(that.allowTethering) : that.allowTethering != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (allowDeviceDiscovery != null ? allowDeviceDiscovery.hashCode() : 0);
        result = 31 * result + (allowPairing != null ? allowPairing.hashCode() : 0);
        result = 31 * result + (allowHeadset != null ? allowHeadset.hashCode() : 0);
        result = 31 * result + (allowHandsFree != null ? allowHandsFree.hashCode() : 0);
        result = 31 * result + (allowA2DP != null ? allowA2DP.hashCode() : 0);
        result = 31 * result + (allowOutgoingCalls != null ? allowOutgoingCalls.hashCode() : 0);
        result = 31 * result + (allowDataTransfer != null ? allowDataTransfer.hashCode() : 0);
        result = 31 * result + (allowTethering != null ? allowTethering.hashCode() : 0);
        result = 31 * result + (allow2Desktop != null ? allow2Desktop.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadBluetoothPolicy{" +
                "allowDeviceDiscovery=" + allowDeviceDiscovery +
                ", allowPairing=" + allowPairing +
                ", allowHeadset=" + allowHeadset +
                ", allowHandsFree=" + allowHandsFree +
                ", allowA2DP=" + allowA2DP +
                ", allowOutgoingCalls=" + allowOutgoingCalls +
                ", allowDataTransfer=" + allowDataTransfer +
                ", allowTethering=" + allowTethering +
                ", allow2Desktop=" + allow2Desktop +
                '}';
    }
}
