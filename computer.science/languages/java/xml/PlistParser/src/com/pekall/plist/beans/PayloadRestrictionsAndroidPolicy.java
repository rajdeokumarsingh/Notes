package com.pekall.plist.beans;

@SuppressWarnings({"UnusedDeclaration", "SimplifiableIfStatement"})
public class PayloadRestrictionsAndroidPolicy extends PayloadBase {

    public static final String CTRL_ENABLED = "enabled";
    public static final String CTRL_DISABLE = "disable";
    public static final String CTRL_USR_CONTROLLED = "user controlled";

    /**
     * Enable Background Data Synchronization
     * See CTRL_...
     */
    private String backgroundDataSync;

    /**
     * Auto-Sync
     * See CTRL_...
     */
    private String autoSync;

    /**
     * Camera
     * See CTRL_...
     */
    private String camera;

    /**
     * Bluetooth
     * See CTRL_...
     */
    private String bluetooth;

    /**
     * Allow USB Mass Storage
     * See CTRL_...
     */
    private String allowUSBMassStorage;

    /**
     * Allow USB Media Player (MTP, PTP)
     */
    private Boolean allowUsbMediaPlayer;

    /**
     * Use Network-provided Date & Time
     * See CTRL_...
     */
    private String useNetworkDateTime;

    /**
     * Allow Microphone
     * See CTRL_...
     */
    private String allowMicrophone;

    /**
     * Allow Near Field Communication (NFC)
     * See CTRL_...
     */
    private String allowNFC;

    /**
     * Use Wireless Networks / Google's Location Service for Location Detection
     * See CTRL_...
     */
    private String useWirelessNetworkForLocation;

    /**
     * Use GPS satellites for Location Detection
     * See CTRL_...
     */
    private String useGPSForLocation;

    /**
     * Use sensor aiding for Location Detection
     * See CTRL_...
     */
    private String useSensorAidingForLocation;

    /**
     * Allow Mock Locations
     */
    private String allowMockLocation;

    public PayloadRestrictionsAndroidPolicy() {
        setPayloadType(PayloadBase.PAYLOAD_TYPE_RESTRICTIONS_ANDROID_POLICY);
    }

    public String getBackgroundDataSync() {
        return backgroundDataSync;
    }

    public void setBackgroundDataSync(String backgroundDataSync) {
        this.backgroundDataSync = backgroundDataSync;
    }

    public String getAutoSync() {
        return autoSync;
    }

    public void setAutoSync(String autoSync) {
        this.autoSync = autoSync;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(String bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getAllowUSBMassStorage() {
        return allowUSBMassStorage;
    }

    public void setAllowUSBMassStorage(String allowUSBMassStorage) {
        this.allowUSBMassStorage = allowUSBMassStorage;
    }

    public Boolean getAllowUsbMediaPlayer() {
        return allowUsbMediaPlayer;
    }

    public void setAllowUsbMediaPlayer(Boolean allowUsbMediaPlayer) {
        this.allowUsbMediaPlayer = allowUsbMediaPlayer;
    }

    public String getUseNetworkDateTime() {
        return useNetworkDateTime;
    }

    public void setUseNetworkDateTime(String useNetworkDateTime) {
        this.useNetworkDateTime = useNetworkDateTime;
    }

    public String getAllowMicrophone() {
        return allowMicrophone;
    }

    public void setAllowMicrophone(String allowMicrophone) {
        this.allowMicrophone = allowMicrophone;
    }

    public String getAllowNFC() {
        return allowNFC;
    }

    public void setAllowNFC(String allowNFC) {
        this.allowNFC = allowNFC;
    }

    public String getUseWirelessNetworkForLocation() {
        return useWirelessNetworkForLocation;
    }

    public void setUseWirelessNetworkForLocation(String useWirelessNetworkForLocation) {
        this.useWirelessNetworkForLocation = useWirelessNetworkForLocation;
    }

    public String getUseGPSForLocation() {
        return useGPSForLocation;
    }

    public void setUseGPSForLocation(String useGPSForLocation) {
        this.useGPSForLocation = useGPSForLocation;
    }

    public String getUseSensorAidingForLocation() {
        return useSensorAidingForLocation;
    }

    public void setUseSensorAidingForLocation(String useSensorAidingForLocation) {
        this.useSensorAidingForLocation = useSensorAidingForLocation;
    }

    public String getAllowMockLocation() {
        return allowMockLocation;
    }

    public void setAllowMockLocation(String allowMockLocation) {
        this.allowMockLocation = allowMockLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PayloadRestrictionsAndroidPolicy that = (PayloadRestrictionsAndroidPolicy) o;

        if (allowMicrophone != null ? !allowMicrophone.equals(that.allowMicrophone) : that.allowMicrophone != null)
            return false;
        if (allowMockLocation != null ? !allowMockLocation.equals(that.allowMockLocation) : that.allowMockLocation != null)
            return false;
        if (allowNFC != null ? !allowNFC.equals(that.allowNFC) : that.allowNFC != null) return false;
        if (allowUSBMassStorage != null ? !allowUSBMassStorage.equals(that.allowUSBMassStorage) : that.allowUSBMassStorage != null)
            return false;
        if (allowUsbMediaPlayer != null ? !allowUsbMediaPlayer.equals(that.allowUsbMediaPlayer) : that.allowUsbMediaPlayer != null)
            return false;
        if (autoSync != null ? !autoSync.equals(that.autoSync) : that.autoSync != null) return false;
        if (backgroundDataSync != null ? !backgroundDataSync.equals(that.backgroundDataSync) : that.backgroundDataSync != null)
            return false;
        if (bluetooth != null ? !bluetooth.equals(that.bluetooth) : that.bluetooth != null) return false;
        if (camera != null ? !camera.equals(that.camera) : that.camera != null) return false;
        if (useGPSForLocation != null ? !useGPSForLocation.equals(that.useGPSForLocation) : that.useGPSForLocation != null)
            return false;
        if (useNetworkDateTime != null ? !useNetworkDateTime.equals(that.useNetworkDateTime) : that.useNetworkDateTime != null)
            return false;
        if (useSensorAidingForLocation != null ? !useSensorAidingForLocation.equals(that.useSensorAidingForLocation) : that.useSensorAidingForLocation != null)
            return false;
        return !(useWirelessNetworkForLocation != null ? !useWirelessNetworkForLocation.equals(that.useWirelessNetworkForLocation) : that.useWirelessNetworkForLocation != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (backgroundDataSync != null ? backgroundDataSync.hashCode() : 0);
        result = 31 * result + (autoSync != null ? autoSync.hashCode() : 0);
        result = 31 * result + (camera != null ? camera.hashCode() : 0);
        result = 31 * result + (bluetooth != null ? bluetooth.hashCode() : 0);
        result = 31 * result + (allowUSBMassStorage != null ? allowUSBMassStorage.hashCode() : 0);
        result = 31 * result + (allowUsbMediaPlayer != null ? allowUsbMediaPlayer.hashCode() : 0);
        result = 31 * result + (useNetworkDateTime != null ? useNetworkDateTime.hashCode() : 0);
        result = 31 * result + (allowMicrophone != null ? allowMicrophone.hashCode() : 0);
        result = 31 * result + (allowNFC != null ? allowNFC.hashCode() : 0);
        result = 31 * result + (useWirelessNetworkForLocation != null ? useWirelessNetworkForLocation.hashCode() : 0);
        result = 31 * result + (useGPSForLocation != null ? useGPSForLocation.hashCode() : 0);
        result = 31 * result + (useSensorAidingForLocation != null ? useSensorAidingForLocation.hashCode() : 0);
        result = 31 * result + (allowMockLocation != null ? allowMockLocation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PayloadRestrictionsAndroidPolicy{" +
                "super='" + super.toString() + '\'' +
                "backgroundDataSync='" + backgroundDataSync + '\'' +
                ", autoSync='" + autoSync + '\'' +
                ", camera='" + camera + '\'' +
                ", bluetooth='" + bluetooth + '\'' +
                ", allowUSBMassStorage='" + allowUSBMassStorage + '\'' +
                ", allowUsbMediaPlayer=" + allowUsbMediaPlayer +
                ", useNetworkDateTime='" + useNetworkDateTime + '\'' +
                ", allowMicrophone='" + allowMicrophone + '\'' +
                ", allowNFC='" + allowNFC + '\'' +
                ", useWirelessNetworkForLocation='" + useWirelessNetworkForLocation + '\'' +
                ", useGPSForLocation='" + useGPSForLocation + '\'' +
                ", useSensorAidingForLocation='" + useSensorAidingForLocation + '\'' +
                ", allowMockLocation='" + allowMockLocation + '\'' +
                '}';
    }
}
