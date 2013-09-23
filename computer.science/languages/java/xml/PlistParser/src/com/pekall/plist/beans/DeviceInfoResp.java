package com.pekall.plist.beans;

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
    private Double DeviceCapacity;
    /** Floating-point gibibytes (base-1024 gigabytes). */
    private Double AvailableDeviceCapacity;
    /** Floating-point percentage expressed as a value between 0.0 and
     1.0, or -1.0 if battery level cannot be determined. */
    private Double BatteryLevel;
    /** Returns the type of cellular technology.
     0—none 1—GSM 2—CDMA */
    private Integer CellularTechnology;
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
    private Boolean VoiceRoamingEnabled;
    /** The current setting of the Data Roaming setting. */
    private Boolean DataRoamingEnabled;
    /** Returns whether the device is currently roaming. */
    private Boolean IsRoaming;
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

    /**
     * Whether the handset has Samsung safe API
     * Only for Android
     */
    private Boolean HasSamsungSafeAPI;
    /**
     * Whether MDM can disable system functions of the handset
     * Only for Android
     */
    private Boolean CanDisableSystemFunction;

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

    public Double getDeviceCapacity() {
        return DeviceCapacity;
    }

    public void setDeviceCapacity(Double deviceCapacity) {
        DeviceCapacity = deviceCapacity;
    }

    public Double getAvailableDeviceCapacity() {
        return AvailableDeviceCapacity;
    }

    public void setAvailableDeviceCapacity(Double availableDeviceCapacity) {
        AvailableDeviceCapacity = availableDeviceCapacity;
    }

    public Double getBatteryLevel() {
        return BatteryLevel;
    }

    public void setBatteryLevel(Double batteryLevel) {
        BatteryLevel = batteryLevel;
    }

    public Integer getCellularTechnology() {
        return CellularTechnology;
    }

    public void setCellularTechnology(Integer cellularTechnology) {
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

    public Boolean getVoiceRoamingEnabled() {
        return VoiceRoamingEnabled;
    }

    public void setVoiceRoamingEnabled(Boolean voiceRoamingEnabled) {
        VoiceRoamingEnabled = voiceRoamingEnabled;
    }

    public Boolean getDataRoamingEnabled() {
        return DataRoamingEnabled;
    }

    public void setDataRoamingEnabled(Boolean dataRoamingEnabled) {
        DataRoamingEnabled = dataRoamingEnabled;
    }

    public Boolean getIsRoaming() {
        return IsRoaming;
    }

    public void setIsRoaming(Boolean isRoaming) {
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

    public Boolean getHasSamsungSafeAPI() {
        return HasSamsungSafeAPI;
    }

    public void setHasSamsungSafeAPI(Boolean hasSamsungSafeAPI) {
        HasSamsungSafeAPI = hasSamsungSafeAPI;
    }

    public Boolean getCanDisableSystemFunction() {
        return CanDisableSystemFunction;
    }

    public void setCanDisableSystemFunction(Boolean canDisableSystemFunction) {
        this.CanDisableSystemFunction = canDisableSystemFunction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceInfoResp)) return false;

        DeviceInfoResp that = (DeviceInfoResp) o;

        if (AvailableDeviceCapacity != null ? !AvailableDeviceCapacity.equals(that.AvailableDeviceCapacity) : that.AvailableDeviceCapacity != null)
            return false;
        if (BatteryLevel != null ? !BatteryLevel.equals(that.BatteryLevel) : that.BatteryLevel != null) return false;
        if (BluetoothMAC != null ? !BluetoothMAC.equals(that.BluetoothMAC) : that.BluetoothMAC != null) return false;
        if (BuildVersion != null ? !BuildVersion.equals(that.BuildVersion) : that.BuildVersion != null) return false;
        if (CarrierSettingsVersion != null ? !CarrierSettingsVersion.equals(that.CarrierSettingsVersion) : that.CarrierSettingsVersion != null)
            return false;
        if (CellularTechnology != null ? !CellularTechnology.equals(that.CellularTechnology) : that.CellularTechnology != null)
            return false;
        if (CurrentCarrierNetwork != null ? !CurrentCarrierNetwork.equals(that.CurrentCarrierNetwork) : that.CurrentCarrierNetwork != null)
            return false;
        if (CurrentMCC != null ? !CurrentMCC.equals(that.CurrentMCC) : that.CurrentMCC != null) return false;
        if (CurrentMNC != null ? !CurrentMNC.equals(that.CurrentMNC) : that.CurrentMNC != null) return false;
        if (DataRoamingEnabled != null ? !DataRoamingEnabled.equals(that.DataRoamingEnabled) : that.DataRoamingEnabled != null)
            return false;
        if (DeviceCapacity != null ? !DeviceCapacity.equals(that.DeviceCapacity) : that.DeviceCapacity != null)
            return false;
        if (DeviceName != null ? !DeviceName.equals(that.DeviceName) : that.DeviceName != null) return false;
        if (EthernetMAC != null ? !EthernetMAC.equals(that.EthernetMAC) : that.EthernetMAC != null) return false;
        if (HasSamsungSafeAPI != null ? !HasSamsungSafeAPI.equals(that.HasSamsungSafeAPI) : that.HasSamsungSafeAPI != null)
            return false;
        if (ICCID != null ? !ICCID.equals(that.ICCID) : that.ICCID != null) return false;
        if (IMEI != null ? !IMEI.equals(that.IMEI) : that.IMEI != null) return false;
        if (IsRoaming != null ? !IsRoaming.equals(that.IsRoaming) : that.IsRoaming != null) return false;
        if (MEID != null ? !MEID.equals(that.MEID) : that.MEID != null) return false;
        if (Model != null ? !Model.equals(that.Model) : that.Model != null) return false;
        if (ModelName != null ? !ModelName.equals(that.ModelName) : that.ModelName != null) return false;
        if (ModemFirmwareVersion != null ? !ModemFirmwareVersion.equals(that.ModemFirmwareVersion) : that.ModemFirmwareVersion != null)
            return false;
        if (OSVersion != null ? !OSVersion.equals(that.OSVersion) : that.OSVersion != null) return false;
        if (PhoneNumber != null ? !PhoneNumber.equals(that.PhoneNumber) : that.PhoneNumber != null) return false;
        if (ProductName != null ? !ProductName.equals(that.ProductName) : that.ProductName != null) return false;
        if (SIMCarrierNetwork != null ? !SIMCarrierNetwork.equals(that.SIMCarrierNetwork) : that.SIMCarrierNetwork != null)
            return false;
        if (SIMMCC != null ? !SIMMCC.equals(that.SIMMCC) : that.SIMMCC != null) return false;
        if (SIMMNC != null ? !SIMMNC.equals(that.SIMMNC) : that.SIMMNC != null) return false;
        if (SerialNumber != null ? !SerialNumber.equals(that.SerialNumber) : that.SerialNumber != null) return false;
        if (SubscriberCarrierNetwork != null ? !SubscriberCarrierNetwork.equals(that.SubscriberCarrierNetwork) : that.SubscriberCarrierNetwork != null)
            return false;
        if (SubscriberMCC != null ? !SubscriberMCC.equals(that.SubscriberMCC) : that.SubscriberMCC != null)
            return false;
        if (SubscriberMNC != null ? !SubscriberMNC.equals(that.SubscriberMNC) : that.SubscriberMNC != null)
            return false;
        if (UDID != null ? !UDID.equals(that.UDID) : that.UDID != null) return false;
        if (VoiceRoamingEnabled != null ? !VoiceRoamingEnabled.equals(that.VoiceRoamingEnabled) : that.VoiceRoamingEnabled != null)
            return false;
        if (WiFiMAC != null ? !WiFiMAC.equals(that.WiFiMAC) : that.WiFiMAC != null) return false;
        if (CanDisableSystemFunction != null ? !CanDisableSystemFunction.equals(that.CanDisableSystemFunction) : that.CanDisableSystemFunction != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = UDID != null ? UDID.hashCode() : 0;
        result = 31 * result + (DeviceName != null ? DeviceName.hashCode() : 0);
        result = 31 * result + (OSVersion != null ? OSVersion.hashCode() : 0);
        result = 31 * result + (BuildVersion != null ? BuildVersion.hashCode() : 0);
        result = 31 * result + (ModelName != null ? ModelName.hashCode() : 0);
        result = 31 * result + (Model != null ? Model.hashCode() : 0);
        result = 31 * result + (ProductName != null ? ProductName.hashCode() : 0);
        result = 31 * result + (SerialNumber != null ? SerialNumber.hashCode() : 0);
        result = 31 * result + (DeviceCapacity != null ? DeviceCapacity.hashCode() : 0);
        result = 31 * result + (AvailableDeviceCapacity != null ? AvailableDeviceCapacity.hashCode() : 0);
        result = 31 * result + (BatteryLevel != null ? BatteryLevel.hashCode() : 0);
        result = 31 * result + (CellularTechnology != null ? CellularTechnology.hashCode() : 0);
        result = 31 * result + (IMEI != null ? IMEI.hashCode() : 0);
        result = 31 * result + (MEID != null ? MEID.hashCode() : 0);
        result = 31 * result + (ModemFirmwareVersion != null ? ModemFirmwareVersion.hashCode() : 0);
        result = 31 * result + (ICCID != null ? ICCID.hashCode() : 0);
        result = 31 * result + (BluetoothMAC != null ? BluetoothMAC.hashCode() : 0);
        result = 31 * result + (WiFiMAC != null ? WiFiMAC.hashCode() : 0);
        result = 31 * result + (EthernetMAC != null ? EthernetMAC.hashCode() : 0);
        result = 31 * result + (CurrentCarrierNetwork != null ? CurrentCarrierNetwork.hashCode() : 0);
        result = 31 * result + (SIMCarrierNetwork != null ? SIMCarrierNetwork.hashCode() : 0);
        result = 31 * result + (SubscriberCarrierNetwork != null ? SubscriberCarrierNetwork.hashCode() : 0);
        result = 31 * result + (CarrierSettingsVersion != null ? CarrierSettingsVersion.hashCode() : 0);
        result = 31 * result + (PhoneNumber != null ? PhoneNumber.hashCode() : 0);
        result = 31 * result + (VoiceRoamingEnabled != null ? VoiceRoamingEnabled.hashCode() : 0);
        result = 31 * result + (DataRoamingEnabled != null ? DataRoamingEnabled.hashCode() : 0);
        result = 31 * result + (IsRoaming != null ? IsRoaming.hashCode() : 0);
        result = 31 * result + (SubscriberMCC != null ? SubscriberMCC.hashCode() : 0);
        result = 31 * result + (SubscriberMNC != null ? SubscriberMNC.hashCode() : 0);
        result = 31 * result + (SIMMCC != null ? SIMMCC.hashCode() : 0);
        result = 31 * result + (SIMMNC != null ? SIMMNC.hashCode() : 0);
        result = 31 * result + (CurrentMCC != null ? CurrentMCC.hashCode() : 0);
        result = 31 * result + (CurrentMNC != null ? CurrentMNC.hashCode() : 0);
        result = 31 * result + (HasSamsungSafeAPI != null ? HasSamsungSafeAPI.hashCode() : 0);
        result = 31 * result + (CanDisableSystemFunction != null ? CanDisableSystemFunction.hashCode() : 0);
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
                ", HasSamsungSafeAPI=" + HasSamsungSafeAPI +
                ", CanDisableSystemFunction=" + CanDisableSystemFunction +
                '}';
    }
}
