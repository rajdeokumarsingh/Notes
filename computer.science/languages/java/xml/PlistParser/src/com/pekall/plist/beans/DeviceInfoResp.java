package com.pekall.plist.beans;

import com.pekall.plist.Utils;

/**
 * Dictionary QueryResponses for DeviceInformation command
 */
public class DeviceInfoResp extends BeanBase{
    /** The unique device identifier (UDID) of the device. */
    private String UDID;
    /** The name given to the iOS device via iTunes or the OS X device
     via System Preferences. */
    private String DeviceName;
    /** The version of iOS the device is running. */
    private String OSVersion;
    /** The build number */
    private String BuildVersion;
    /** Name of the device model, e.g. “MacBook Pro” */
    private String ModelName;
    /** The device’s model number (MC319LL, for example). */
    private String Model;
    /** The model code for the device (iPhone3,1, for example). */
    private String ProductName;
    /** The device’s serial number. */
    private String SerialNumber;
    /** Floating-point gibibytes (base-1024 gigabytes). */
    private double DeviceCapacity;
    /** Floating-point gibibytes (base-1024 gigabytes). */
    private double AvailableDeviceCapacity;
    /** Floating-point percentage expressed as a value between 0.0 and
     1.0, or -1.0 if battery level cannot be determined. */
    private double BatteryLevel;
    /** Returns the type of cellular technology.
     0—none 1—GSM 2—CDMA */
    private int CellularTechnology;
    /** The device’s IMEI number. Ignored if the device does not support GSM. */
    private String IMEI;
    /** The device’s MEID number. Ignored if the device does not support CDMA. */
    private String MEID;
    /** The baseband firmware version. */
    private String ModemFirmwareVersion;
    /** The ICC identifier for the installed SIM card. */
    private String ICCID;
    /** Bluetooth MAC address. */
    private String BluetoothMAC;
    /** Wi-Fi MAC address. */
    private String WiFiMAC;
    /** Ethernet MAC address. */
    private String EthernetMAC;
    /** Name of the current carrier network. */
    private String CurrentCarrierNetwork;
    /** Name of the home carrier network. (Note: this query is
     supported on CDMA in spite of its name.) */
    private String SIMCarrierNetwork;
    /** Name of the home carrier network. (Replaces SIMCarrierNetwork.) */
    private String SubscriberCarrierNetwork;
    /** Version of the currently-installed carrier settings file. */
    private String CarrierSettingsVersion;
    /** Raw phone number without punctuation, including country code. */
    private String PhoneNumber;
    /** The current setting of the Voice Roaming setting. This is only
     available on certain carriers. */
    private boolean VoiceRoamingEnabled;
    /** The current setting of the Data Roaming setting. */
    private boolean DataRoamingEnabled;
    /** Returns whether the device is currently roaming. */
    private boolean IsRoaming;
    /** Home Mobile Country Code (numeric string). */
    private String SubscriberMCC;
    /** Home Mobile Network Code (numeric string). */
    private String SubscriberMNC;
    /** Deprecated. Use SubscriberMCC instead. Home Mobile
     Country Code (numeric string). */
    private String SIMMCC;
    /** Deprecated. Use SubscriberMNC instead. Home Mobile
     Network Code (numeric string). */
    private String SIMMNC;
    /** Current Mobile Country Code (numeric string). */
    private String CurrentMCC;
    /** Current Mobile Network Code (numeric string). */
    private String CurrentMNC;

    public DeviceInfoResp() {
    }

    public String getUDID() {
        return UDID;
    }

    public void setUDID(String UDID) {
        this.UDID = UDID;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public void setOSVersion(String OSVersion) {
        this.OSVersion = OSVersion;
    }

    public String getBuildVersion() {
        return BuildVersion;
    }

    public void setBuildVersion(String buildVersion) {
        BuildVersion = buildVersion;
    }

    public String getModelName() {
        return ModelName;
    }

    public void setModelName(String modelName) {
        ModelName = modelName;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public double getDeviceCapacity() {
        return DeviceCapacity;
    }

    public void setDeviceCapacity(double deviceCapacity) {
        DeviceCapacity = deviceCapacity;
    }

    public double getAvailableDeviceCapacity() {
        return AvailableDeviceCapacity;
    }

    public void setAvailableDeviceCapacity(double availableDeviceCapacity) {
        AvailableDeviceCapacity = availableDeviceCapacity;
    }

    public double getBatteryLevel() {
        return BatteryLevel;
    }

    public void setBatteryLevel(double batteryLevel) {
        BatteryLevel = batteryLevel;
    }

    public int getCellularTechnology() {
        return CellularTechnology;
    }

    public void setCellularTechnology(int cellularTechnology) {
        CellularTechnology = cellularTechnology;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getMEID() {
        return MEID;
    }

    public void setMEID(String MEID) {
        this.MEID = MEID;
    }

    public String getModemFirmwareVersion() {
        return ModemFirmwareVersion;
    }

    public void setModemFirmwareVersion(String modemFirmwareVersion) {
        ModemFirmwareVersion = modemFirmwareVersion;
    }

    public String getICCID() {
        return ICCID;
    }

    public void setICCID(String ICCID) {
        this.ICCID = ICCID;
    }

    public String getBluetoothMAC() {
        return BluetoothMAC;
    }

    public void setBluetoothMAC(String bluetoothMAC) {
        BluetoothMAC = bluetoothMAC;
    }

    public String getWiFiMAC() {
        return WiFiMAC;
    }

    public void setWiFiMAC(String wiFiMAC) {
        WiFiMAC = wiFiMAC;
    }

    public String getEthernetMAC() {
        return EthernetMAC;
    }

    public void setEthernetMAC(String ethernetMAC) {
        EthernetMAC = ethernetMAC;
    }

    public String getCurrentCarrierNetwork() {
        return CurrentCarrierNetwork;
    }

    public void setCurrentCarrierNetwork(String currentCarrierNetwork) {
        CurrentCarrierNetwork = currentCarrierNetwork;
    }

    public String getSIMCarrierNetwork() {
        return SIMCarrierNetwork;
    }

    public void setSIMCarrierNetwork(String SIMCarrierNetwork) {
        this.SIMCarrierNetwork = SIMCarrierNetwork;
    }

    public String getSubscriberCarrierNetwork() {
        return SubscriberCarrierNetwork;
    }

    public void setSubscriberCarrierNetwork(String subscriberCarrierNetwork) {
        SubscriberCarrierNetwork = subscriberCarrierNetwork;
    }

    public String getCarrierSettingsVersion() {
        return CarrierSettingsVersion;
    }

    public void setCarrierSettingsVersion(String carrierSettingsVersion) {
        CarrierSettingsVersion = carrierSettingsVersion;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public boolean getVoiceRoamingEnabled() {
        return VoiceRoamingEnabled;
    }

    public void setVoiceRoamingEnabled(boolean voiceRoamingEnabled) {
        VoiceRoamingEnabled = voiceRoamingEnabled;
    }

    public boolean getDataRoamingEnabled() {
        return DataRoamingEnabled;
    }

    public void setDataRoamingEnabled(boolean dataRoamingEnabled) {
        DataRoamingEnabled = dataRoamingEnabled;
    }

    public boolean getIsRoaming() {
        return IsRoaming;
    }

    public void setIsRoaming(boolean isRoaming) {
        IsRoaming = isRoaming;
    }

    public String getSubscriberMCC() {
        return SubscriberMCC;
    }

    public void setSubscriberMCC(String subscriberMCC) {
        SubscriberMCC = subscriberMCC;
    }

    public String getSubscriberMNC() {
        return SubscriberMNC;
    }

    public void setSubscriberMNC(String subscriberMNC) {
        SubscriberMNC = subscriberMNC;
    }

    public String getSIMMCC() {
        return SIMMCC;
    }

    public void setSIMMCC(String SIMMCC) {
        this.SIMMCC = SIMMCC;
    }

    public String getSIMMNC() {
        return SIMMNC;
    }

    public void setSIMMNC(String SIMMNC) {
        this.SIMMNC = SIMMNC;
    }

    public String getCurrentMCC() {
        return CurrentMCC;
    }

    public void setCurrentMCC(String currentMCC) {
        CurrentMCC = currentMCC;
    }

    public String getCurrentMNC() {
        return CurrentMNC;
    }

    public void setCurrentMNC(String currentMNC) {
        CurrentMNC = currentMNC;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceInfoResp)) return false;

        DeviceInfoResp that = (DeviceInfoResp) o;
        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = Utils.safeString(UDID).hashCode();
        result = 31 * result + Utils.safeString(DeviceName).hashCode();
        result = 31 * result + Utils.safeString(OSVersion).hashCode();
        result = 31 * result + Utils.safeString(BuildVersion).hashCode();
        result = 31 * result + Utils.safeString(ModelName).hashCode();
        result = 31 * result + Utils.safeString(Model).hashCode();
        result = 31 * result + Utils.safeString(ProductName).hashCode();
        result = 31 * result + Utils.safeString(SerialNumber).hashCode();
        temp = Double.doubleToLongBits(DeviceCapacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(AvailableDeviceCapacity);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(BatteryLevel);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + CellularTechnology;
        result = 31 * result + Utils.safeString(IMEI).hashCode();
        result = 31 * result + Utils.safeString(MEID).hashCode();
        result = 31 * result + Utils.safeString(ModemFirmwareVersion).hashCode();
        result = 31 * result + Utils.safeString(ICCID).hashCode();
        result = 31 * result + Utils.safeString(BluetoothMAC).hashCode();
        result = 31 * result + Utils.safeString(WiFiMAC).hashCode();
        result = 31 * result + Utils.safeString(EthernetMAC).hashCode();
        result = 31 * result + Utils.safeString(CurrentCarrierNetwork).hashCode();
        result = 31 * result + Utils.safeString(SIMCarrierNetwork).hashCode();
        result = 31 * result + Utils.safeString(SubscriberCarrierNetwork).hashCode();
        result = 31 * result + Utils.safeString(CarrierSettingsVersion).hashCode();
        result = 31 * result + Utils.safeString(PhoneNumber).hashCode();
        result = 31 * result + (VoiceRoamingEnabled ? 1 : 0);
        result = 31 * result + (DataRoamingEnabled ? 1 : 0);
        result = 31 * result + (IsRoaming ? 1 : 0);
        result = 31 * result + Utils.safeString(SubscriberMCC).hashCode();
        result = 31 * result + Utils.safeString(SubscriberMNC).hashCode();
        result = 31 * result + Utils.safeString(SIMMCC).hashCode();
        result = 31 * result + Utils.safeString(SIMMNC).hashCode();
        result = 31 * result + Utils.safeString(CurrentMCC).hashCode();
        result = 31 * result + Utils.safeString(CurrentMNC).hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DeviceInfoResp{" +
                "UDID='" + UDID + '\'' +
                ", DeviceName='" + DeviceName + '\'' +
                ", OSVersion='" + OSVersion + '\'' +
                ", BuildVersion='" + BuildVersion + '\'' +
                ", ModelName='" + ModelName + '\'' +
                ", Model='" + Model + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", SerialNumber='" + SerialNumber + '\'' +
                ", DeviceCapacity=" + DeviceCapacity +
                ", AvailableDeviceCapacity=" + AvailableDeviceCapacity +
                ", BatteryLevel=" + BatteryLevel +
                ", CellularTechnology=" + CellularTechnology +
                ", IMEI='" + IMEI + '\'' +
                ", MEID='" + MEID + '\'' +
                ", ModemFirmwareVersion='" + ModemFirmwareVersion + '\'' +
                ", ICCID='" + ICCID + '\'' +
                ", BluetoothMAC='" + BluetoothMAC + '\'' +
                ", WiFiMAC='" + WiFiMAC + '\'' +
                ", EthernetMAC='" + EthernetMAC + '\'' +
                ", CurrentCarrierNetwork='" + CurrentCarrierNetwork + '\'' +
                ", SIMCarrierNetwork='" + SIMCarrierNetwork + '\'' +
                ", SubscriberCarrierNetwork='" + SubscriberCarrierNetwork + '\'' +
                ", CarrierSettingsVersion='" + CarrierSettingsVersion + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", VoiceRoamingEnabled=" + VoiceRoamingEnabled +
                ", DataRoamingEnabled=" + DataRoamingEnabled +
                ", IsRoaming=" + IsRoaming +
                ", SubscriberMCC='" + SubscriberMCC + '\'' +
                ", SubscriberMNC='" + SubscriberMNC + '\'' +
                ", SIMMCC='" + SIMMCC + '\'' +
                ", SIMMNC='" + SIMMNC + '\'' +
                ", CurrentMCC='" + CurrentMCC + '\'' +
                ", CurrentMNC='" + CurrentMNC + '\'' +
                '}';
    }
}
