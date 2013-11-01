package com.pekall.plist.json;

import com.pekall.plist.beans.*;
import com.pekall.plist.su.policy.AppControlList;
import com.pekall.plist.su.policy.MemorySizePolicy;
import com.pekall.plist.su.policy.SystemExceptionPolicy;
import com.pekall.plist.su.settings.SystemSettings;
import com.pekall.plist.su.settings.advertise.AdvertiseDownloadSettings;
import com.pekall.plist.su.settings.browser.BrowserSettings;
import com.pekall.plist.su.settings.launcher.LauncherSettings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * PayloadArrayWrapper的一个json封装
 */
public class PayloadJsonWrapper extends PayloadBase {

    public PayloadJsonWrapper(PayloadArrayWrapper arrayWrapper) {
        this.setPayloadType(arrayWrapper.getPayloadType());
        this.setPayloadVersion(arrayWrapper.getPayloadVersion());
        this.setPayloadIdentifier(arrayWrapper.getPayloadIdentifier());
        this.setPayloadUUID(arrayWrapper.getPayloadUUID());
        this.setPayloadDisplayName(arrayWrapper.getPayloadDisplayName());
        this.setPayloadDescription(arrayWrapper.getPayloadDescription());
        this.setPayloadOrganization(arrayWrapper.getPayloadOrganization());

        this.setHasRemovalPasscode(arrayWrapper.getHasRemovalPasscode());
        this.setIsEncrypted(arrayWrapper.getIsEncrypted());
        this.setPayloadRemovalDisallowed(arrayWrapper.getPayloadRemovalDisallowed());
        this.setPayloadScope(arrayWrapper.getPayloadScope());
        this.setRemovalDate(arrayWrapper.getRemovalDate());
        this.setDurationUntilRemoval(arrayWrapper.getDurationUntilRemoval());
        List<PayloadBase> payloadContent = arrayWrapper.getPayloadContent();
        if (payloadContent != null) {
            this.PayloadContent = new PayloadJsonObject();

            for (PayloadBase payload : payloadContent) {
                if (PayloadBase.PAYLOAD_TYPE_EMAIL.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.addPayloadEmail((PayloadEmail) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_WIFI_MANAGED.equalsIgnoreCase(payload.getPayloadType())) {

                    this.PayloadContent.addPayloadWifiConfig((PayloadWifiConfig) payload);
//                    this.PayloadContent.setPayloadWifiConfig((PayloadWifiConfig) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_PASSWORD_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadPasswordPolicy((PayloadPasswordPolicy) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_RESTRICTIONS.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadRestrictionsPolicy((PayloadRestrictionsPolicy) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_REMOVAL_PASSWORD.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadRemovalPassword((PayloadRemovalPassword) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_WEB_CLIP.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.addPayloadWebClip((PayloadWebClip) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_LDAP.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.addPayloadLDAP((PayloadLDAP) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_IOS_EXCHANGE.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.addPayloadExchange((PayloadExchange) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_VPN.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.addPayloadVPN((PayloadVPN) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_SE_BROWSER_SETTINGS.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setBrowserSettings((BrowserSettings) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_SYSTEM_SETTINGS.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setSystemSettings((SystemSettings) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_LAUNCHER_SETTINGS.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setLauncherSettings((LauncherSettings) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_ADVT_SETTINGS.equalsIgnoreCase(payload.getPayloadType())) {
//                    this.PayloadContent.setAdvertiseDownloadSettings((AdvertiseDownloadSettings) payload);
                    this.PayloadContent.addAdvertiseDownloadSettings((AdvertiseDownloadSettings) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_MEMORY_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setMemorySizePolicy((MemorySizePolicy) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_APP_CONTROL_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setAppControlList((AppControlList) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_SYSTEM_EXCEPTION_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setSystemExceptionPolicy((SystemExceptionPolicy) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_SECURITY_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadSecurityPolicy((PayloadSecurityPolicy)payload);
                }else if (PayloadBase.PAYLOAD_TYPE_RESTRICTIONS_ANDROID_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadRestrictionsAndroidPolicy((PayloadRestrictionsAndroidPolicy)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_NATIVE_APP_CONTROL_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadNativeAppCtrlPolicy((PayloadNativeAppCtrlPolicy)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_BLUETOOTH_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadBluetoothPolicy((PayloadBluetoothPolicy)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_NET_RESTRICT_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadNetRestrictPolicy((PayloadNetRestrictPolicy)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_ACTIVE_SYNC_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadActiveSyncPolicy((PayloadActiveSyncPolicy)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_WALLPAPER_SETTINGS.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadWallpaper((PayloadWallpaper)payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_ROAMING_POLICY.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.setPayloadRoamingPolicy((PayloadRoamingPolicy) payload);
                } else if (PayloadBase.PAYLOAD_TYPE_CARD_DAV.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.addPayloadCardDAVPolicy((PayloadCardDAVPolicy) payload);
                }else if (PayloadBase.PAYLOAD_TYPE_CAL_DAV.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.addPayloadCalDAVPolicy((PayloadCalDAVPolicy) payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_CAL_SUB.equalsIgnoreCase(payload.getPayloadType())) {
                    this.PayloadContent.addPayloadCalSubscriptionPolicy((PayloadCalSubscriptionPolicy) payload);
                }
                else if (PayloadBase.PAYLOAD_TYPE_APN.equalsIgnoreCase(payload.getPayloadType())) {
//                    this.PayloadContent.addPayloadCalSubscriptionPolicy((PayloadCalSubscriptionPolicy) payload);
                    this.PayloadContent.setPayloadAPN((PayloadAPN) payload);
                }
            }
        }
    }

    /**
     * Optional. Set to true if there is a removal passcode.
     */
    private Boolean HasRemovalPasscode;

    /**
     * Optional. Set to true if the profile is encrypted.
     */
    private Boolean IsEncrypted;

    /**
     * Optional. Array of payload dictionaries. Not present if IsEncrypted is true.
     */
    private PayloadJsonObject PayloadContent;

    /**
     * Optional. If present and set to true, the user cannot delete the profile
     * (unless the profile has a removal password and the user provides it).
     */
    private Boolean PayloadRemovalDisallowed;

    /**
     * Determines if the profile should be installed for the system or the user.
     */
    private Boolean PayloadScope;

    /**
     * Optional. The date on which the profile will be automatically removed.
     */
    private Date RemovalDate;

    /**
     * FIXME: seem it should be a Long or Integer
     * Optional. Number of seconds until the profile is automatically removed.
     */
    private Float DurationUntilRemoval;

    // TODO: can not find in document
    // private Dictionary ConsentText;

    public PayloadJsonWrapper() {
        super();
    }
//
//    public void addPayLoadContent(PayloadBase content) {
//        if (PayloadContent == null) {
//            PayloadContent = new ArrayList<PayloadBase>();
//        }
//        PayloadContent.add(content);
//    }

    public Boolean getHasRemovalPasscode() {
        return HasRemovalPasscode;
    }

    public void setHasRemovalPasscode(Boolean hasRemovalPasscode) {
        HasRemovalPasscode = hasRemovalPasscode;
    }

    public Boolean getIsEncrypted() {
        return IsEncrypted;
    }

    public void setIsEncrypted(Boolean isEncrypted) {
        IsEncrypted = isEncrypted;
    }

    public PayloadJsonObject getPayloadContent() {
        return PayloadContent;
    }

    public void setPayloadContent(PayloadJsonObject payloadContent) {
        PayloadContent = payloadContent;
    }

    //    public List<PayloadBase> getPayloadContent() {
//        return PayloadContent;
//    }
//
//    public void setPayloadContent(List<PayloadBase> payloadContent) {
//        PayloadContent = payloadContent;
//    }

    public Boolean getPayloadRemovalDisallowed() {
        return PayloadRemovalDisallowed;
    }

    public void setPayloadRemovalDisallowed(Boolean payloadRemovalDisallowed) {
        PayloadRemovalDisallowed = payloadRemovalDisallowed;
    }

    public Boolean getPayloadScope() {
        return PayloadScope;
    }

    public void setPayloadScope(Boolean payloadScope) {
        PayloadScope = payloadScope;
    }

    public Date getRemovalDate() {
        return RemovalDate;
    }

    public void setRemovalDate(Date removalDate) {
        RemovalDate = removalDate;
    }

    public Float getDurationUntilRemoval() {
        return DurationUntilRemoval;
    }

    public void setDurationUntilRemoval(Float durationUntilRemoval) {
        DurationUntilRemoval = durationUntilRemoval;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PayloadJsonWrapper)) return false;
        if (!super.equals(o)) return false;

        PayloadJsonWrapper that = (PayloadJsonWrapper) o;

        if(this.hashCode() != that.hashCode()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (HasRemovalPasscode != null ? HasRemovalPasscode.hashCode() : 0);
        result = 31 * result + (IsEncrypted != null ? IsEncrypted.hashCode() : 0);
        result = 31 * result + (PayloadContent != null ? PayloadContent.hashCode() : 0);
        result = 31 * result + (PayloadRemovalDisallowed != null ? PayloadRemovalDisallowed.hashCode() : 0);
        result = 31 * result + (PayloadScope != null ? PayloadScope.hashCode() : 0);
        result = 31 * result + (RemovalDate != null ? RemovalDate.hashCode() : 0);
        result = 31 * result + (DurationUntilRemoval != null ? DurationUntilRemoval.hashCode() : 0);
        return result;
    }

    public PayloadArrayWrapper getPayloadArrayWrapper() {
        PayloadArrayWrapper wrapper = new PayloadArrayWrapper();
        wrapper.setPayloadType(this.getPayloadType());
        wrapper.setPayloadVersion(this.getPayloadVersion());
        wrapper.setPayloadIdentifier(this.getPayloadIdentifier());
        wrapper.setPayloadUUID(this.getPayloadUUID());
        wrapper.setPayloadDisplayName(this.getPayloadDisplayName());
        wrapper.setPayloadDescription(this.getPayloadDescription());
        wrapper.setPayloadOrganization(this.getPayloadOrganization());

        wrapper.setHasRemovalPasscode(this.getHasRemovalPasscode());
        wrapper.setIsEncrypted(this.getIsEncrypted());
        wrapper.setPayloadRemovalDisallowed(this.getPayloadRemovalDisallowed());
        wrapper.setPayloadScope(this.getPayloadScope());
        wrapper.setRemovalDate(this.getRemovalDate());
        wrapper.setDurationUntilRemoval(this.getDurationUntilRemoval());
        PayloadJsonObject payloadContent = this.getPayloadContent();


        List<PayloadEmail> payloadEmails = payloadContent.getPayloadEmails();

        if(payloadEmails != null){
            for(PayloadEmail payloadEmail : payloadEmails){
                wrapper.addPayLoadContent(payloadEmail);
            }
        }

//        List<PayloadWifiConfig> payloadWifiConfigs = payloadContent.getPayloadWifiConfigs();
//        for(PayloadWifiConfig payloadWifiConfig : payloadWifiConfigs){
//            if(payloadWifiConfig != null){
//                wrapper.addPayLoadContent(payloadWifiConfig);
//            }
//
//        }
        List<PayloadWifiConfig> payloadWifiConfigs = payloadContent.getPayloadWifiConfigs();
        if(payloadWifiConfigs != null){
            for(PayloadWifiConfig payloadWifiConfig : payloadWifiConfigs){

                Integer securityType = payloadWifiConfig.getSecurityType();
                if(securityType != null){
                    switch (securityType){
                        case PayloadWifiConfig.SECURITY_TYPE_NO:
                            payloadWifiConfig.setEncryptionType(PayloadWifiConfig.ENCRYPTION_TYPE_NONE);
                            break;
                        case PayloadWifiConfig.SECURITY_TYPE_WEP_PERSON:
                            payloadWifiConfig.setEncryptionType(PayloadWifiConfig.ENCRYPTION_TYPE_WEP);
                            break;
                        case PayloadWifiConfig.SECURITY_TYPE_WPA_PERSON:
                            payloadWifiConfig.setEncryptionType(PayloadWifiConfig.ENCRYPTION_TYPE_WPA);
                            break;
                        case PayloadWifiConfig.SECURITY_TYPE_ANY_PERSON:
                            payloadWifiConfig.setEncryptionType(PayloadWifiConfig.ENCRYPTION_TYPE_ANY);
                            break;
                        case PayloadWifiConfig.SECURITY_TYPE_WEP_ENTERPRISE:
                            payloadWifiConfig.setEncryptionType(PayloadWifiConfig.ENCRYPTION_TYPE_WEP);
                            break;
                        case PayloadWifiConfig.SECURITY_TYPE_WPA_ENTERPRISE:
                            payloadWifiConfig.setEncryptionType(PayloadWifiConfig.ENCRYPTION_TYPE_WPA);
                            break;
                        case PayloadWifiConfig.SECURITY_TYPE_ANY_ENTERPRISE:
                            payloadWifiConfig.setEncryptionType(PayloadWifiConfig.ENCRYPTION_TYPE_ANY);
                            break;
                    }
                }

                EAPClientConfigurationClass eapClientConfiguration = payloadWifiConfig.getEAPClientConfiguration();
                if(eapClientConfiguration != null){

                    List<Integer> acceptEAPTypes = new ArrayList<Integer>();

                    Boolean acceptEAPTypeTLS = eapClientConfiguration.getAcceptEAPTypeTLS();
                    if(acceptEAPTypeTLS != null && acceptEAPTypeTLS){
                        acceptEAPTypes.add(13);
                    }

                    Boolean acceptEAPTypeLEAP = eapClientConfiguration.getAcceptEAPTypeLEAP();
                    if(acceptEAPTypeLEAP != null && acceptEAPTypeLEAP){
                        acceptEAPTypes.add(17);
                    }

                    Boolean acceptEAPTypeEAP_fast = eapClientConfiguration.getAcceptEAPTypeEAP_FAST();
                    if(acceptEAPTypeEAP_fast != null && acceptEAPTypeEAP_fast){
                        acceptEAPTypes.add(43);
                    }

                    Boolean acceptEAPTypeTTLS = eapClientConfiguration.getAcceptEAPTypeTTLS();
                    if(acceptEAPTypeTTLS != null && acceptEAPTypeTTLS){
                        acceptEAPTypes.add(21);
                    }

                    Boolean acceptEAPTypePEAP = eapClientConfiguration.getAcceptEAPTypePEAP();
                    if(acceptEAPTypePEAP != null && acceptEAPTypePEAP){
                        acceptEAPTypes.add(25);
                    }

                    Boolean acceptEAPTypeEAP_sim = eapClientConfiguration.getAcceptEAPTypeEAP_SIM();
                    if(acceptEAPTypeEAP_sim != null && acceptEAPTypeEAP_sim){
                        acceptEAPTypes.add(18);
                    }

                    eapClientConfiguration.setAcceptEAPTypes(acceptEAPTypes);
                }

                wrapper.addPayLoadContent(payloadWifiConfig);
            }
        }
        PayloadPasswordPolicy payloadPasswordPolicy = payloadContent.getPayloadPasswordPolicy();
        if(payloadPasswordPolicy != null){
            wrapper.addPayLoadContent(payloadPasswordPolicy);
        }

        PayloadRestrictionsPolicy payloadRestrictionsPolicy = payloadContent.getPayloadRestrictionsPolicy();
        if(payloadRestrictionsPolicy != null){
            wrapper.addPayLoadContent(payloadRestrictionsPolicy);
        }
        PayloadRemovalPassword payloadRemovalPassword = payloadContent.getPayloadRemovalPassword();
        if(payloadRemovalPassword != null){
            wrapper.addPayLoadContent(payloadRemovalPassword);
        }
        List<PayloadWebClip> payloadWebClips = payloadContent.getPayloadWebClips();
        if(payloadWebClips != null){
            for(PayloadWebClip payloadWebClip : payloadWebClips){
                wrapper.addPayLoadContent(payloadWebClip);
            }
        }
        List<PayloadLDAP> payloadLDAPs = payloadContent.getPayloadLDAPs();

        if(payloadLDAPs != null){
            for(PayloadLDAP payloadLDAP : payloadLDAPs){
                wrapper.addPayLoadContent(payloadLDAP);
            }
        }

        List<PayloadExchange> payloadExchanges = payloadContent.getPayloadExchanges();

        if(payloadExchanges != null){
            for(PayloadExchange payloadExchange : payloadExchanges){
                String domainName = payloadExchange.getDomainName();
                if(domainName != null && domainName.length() > 0){
                    payloadExchange.setUserName(domainName+"\\"+payloadExchange.getUserName());
                }
                wrapper.addPayLoadContent(payloadExchange);
            }
        }

        List<PayloadVPN> payloadVPNs = payloadContent.getPayloadVPNs();
        if(payloadVPNs != null){
            for(PayloadVPN payloadVPN : payloadVPNs){
                parseVpnJson2PlistBean(payloadVPN);
                wrapper.addPayLoadContent(payloadVPN);
            }
        }

        BrowserSettings browserSettings = payloadContent.getBrowserSettings();
        if(browserSettings != null){
            wrapper.addPayLoadContent(browserSettings);
        }
        SystemSettings systemSettings = payloadContent.getSystemSettings();
        if(systemSettings != null){
            wrapper.addPayLoadContent(systemSettings);
        }

        LauncherSettings launcherSettings = payloadContent.getLauncherSettings();
        if(launcherSettings != null){
            wrapper.addPayLoadContent(launcherSettings);
        }

        List<AdvertiseDownloadSettings> advertiseDownloadSettingses = payloadContent.getAdvertiseDownloadSettingses();
//        AdvertiseDownloadSettings advertiseDownloadSettings = payloadContent.getAdvertiseDownloadSettings();
        if(advertiseDownloadSettingses != null){
            for(AdvertiseDownloadSettings advertiseDownloadSettings : advertiseDownloadSettingses){
                wrapper.addPayLoadContent(advertiseDownloadSettings);
            }
        }

        MemorySizePolicy memorySizePolicy = payloadContent.getMemorySizePolicy();
        if(memorySizePolicy != null){
            wrapper.addPayLoadContent(memorySizePolicy);
        }

        AppControlList appControlList = payloadContent.getAppControlList();
        if(appControlList != null){
            wrapper.addPayLoadContent(appControlList);
        }

        SystemExceptionPolicy systemExceptionPolicy = payloadContent.getSystemExceptionPolicy();
        if(systemExceptionPolicy != null){
            wrapper.addPayLoadContent(systemExceptionPolicy);
        }

        PayloadSecurityPolicy payloadSecurityPolicy = payloadContent.getPayloadSecurityPolicy();
        if(payloadSecurityPolicy != null){
            wrapper.addPayLoadContent(payloadSecurityPolicy);
        }

        PayloadRestrictionsAndroidPolicy payloadRestrictionsAndroidPolicy = payloadContent.getPayloadRestrictionsAndroidPolicy();
        if(payloadRestrictionsAndroidPolicy != null){
            wrapper.addPayLoadContent(payloadRestrictionsAndroidPolicy);
        }

        PayloadNativeAppCtrlPolicy payloadNativeAppCtrlPolicy = payloadContent.getPayloadNativeAppCtrlPolicy();
        if(payloadNativeAppCtrlPolicy != null){
            wrapper.addPayLoadContent(payloadNativeAppCtrlPolicy);
        }

        PayloadBluetoothPolicy payloadBluetoothPolicy = payloadContent.getPayloadBluetoothPolicy();
        if(payloadBluetoothPolicy != null){
            wrapper.addPayLoadContent(payloadBluetoothPolicy);
        }
        PayloadNetRestrictPolicy payloadNetRestrictPolicy = payloadContent.getPayloadNetRestrictPolicy();
        if(payloadNetRestrictPolicy != null){
            wrapper.addPayLoadContent(payloadNetRestrictPolicy);
        }

        PayloadActiveSyncPolicy payloadActiveSyncPolicy = payloadContent.getPayloadActiveSyncPolicy();
        if(payloadActiveSyncPolicy != null){
            wrapper.addPayLoadContent(payloadActiveSyncPolicy);
        }
        PayloadWallpaper payloadWallpaper = payloadContent.getPayloadWallpaper();
        if(payloadWallpaper != null){
            wrapper.addPayLoadContent(payloadWallpaper);
        }

        PayloadRoamingPolicy payloadRoamingPolicy = payloadContent.getPayloadRoamingPolicy();
        if(payloadRoamingPolicy != null){
            wrapper.addPayLoadContent(payloadRoamingPolicy);
        }

        List<PayloadCardDAVPolicy> payloadCardDAVPolicies = payloadContent.getPayloadCardDAVPolicies();
        if(payloadCardDAVPolicies != null){
            for(PayloadCardDAVPolicy payloadCardDAVPolicy : payloadCardDAVPolicies){
                wrapper.addPayLoadContent(payloadCardDAVPolicy);
            }
        }

        List<PayloadCalDAVPolicy> payloadCalDAVPolicies = payloadContent.getPayloadCalDAVPolicies();
        if(payloadCalDAVPolicies != null){
            for(PayloadCalDAVPolicy payloadCalDAVPolicy : payloadCalDAVPolicies){
                wrapper.addPayLoadContent(payloadCalDAVPolicy);
            }
        }

        List<PayloadCalSubscriptionPolicy> payloadCalSubscriptionPolicies = payloadContent.getPayloadCalSubscriptionPolicies();
        if(payloadCalSubscriptionPolicies != null){
            for(PayloadCalSubscriptionPolicy payloadCalSubscriptionPolicy : payloadCalSubscriptionPolicies){
                wrapper.addPayLoadContent(payloadCalSubscriptionPolicy);
            }
        }

        PayloadAPN payloadAPN =  payloadContent.getPayloadAPN();
        if(payloadAPN != null){
            APNDataArray apnDataArray = new APNDataArray();

            APNSDict apnsDict = new APNSDict();

            List<APNConfig> apnConfigs = new ArrayList<APNConfig>();
            APNConfig apnConfig= new APNConfig();
            apnConfig.setApn(payloadAPN.getApn());
            apnConfig.setUsername(payloadAPN.getUsername());
            String password = payloadAPN.getPassword();
            if(password != null && password.length() > 0){
                apnConfig.setPassword(payloadAPN.getPassword().getBytes());
            }
            apnConfig.setProxy(payloadAPN.getProxy());
            apnConfig.setProxyPort(payloadAPN.getProxyPort());
            apnConfigs.add(apnConfig);

            apnsDict.setApns(apnConfigs);

            apnDataArray.setDefaultsData(apnsDict);
            payloadAPN.addPayloadContent(apnDataArray);

            wrapper.addPayLoadContent(payloadAPN);
        }
        return wrapper;
    }

//    private void parseVpnPlist2Json(PayloadVPN payloadVPN){
//
//        IPv4Info iPv4Info = payloadVPN.getIPv4();
//        if(iPv4Info != null){
//            payloadVPN.setOverridePrimary(iPv4Info.getOverridePrimary() == 1? true : false);
//        }
//
//        String vpnType = payloadVPN.getVPNType();
//        if(vpnType!=null){
//            if(PayloadVPN.TYPE_L2TP.equalsIgnoreCase(vpnType)){
//
//                PPPInfo pppInfo = payloadVPN.getPPP();
//                if(pppInfo != null){
//
//                    payloadVPN.setAuthName(pppInfo.getAuthName());
//                    payloadVPN.setCommRemoteAddress(pppInfo.getCommRemoteAddress());
//
//                    Boolean tokenCard = pppInfo.getTokenCard();
//                    if(tokenCard != null){
//                        payloadVPN.setAuthType(PayloadVPN.AUTH_TYPE_RSA_SEC_ID);
//                    }else{
//                        payloadVPN.setAuthType(PayloadVPN.AUTH_TYPE_PASS);
//                        payloadVPN.setAuthPassword(pppInfo.getAuthPassword());
//                    }
//                    payloadVPN.setPPP(null);
//                }
//                IPSecInfo ipSec = payloadVPN.getIPSec();
//                if(ipSec != null){
//                    payloadVPN.setSharedSecret(new String(ipSec.getSharedSecret()));
//                    payloadVPN.setIPSec(null);
//                }
//            }else if(PayloadVPN.TYPE_PPTP.equalsIgnoreCase(vpnType)){
//
//                PPPInfo pppInfo = payloadVPN.getPPP();
//                if(pppInfo != null){
//                    payloadVPN.setPPP(null);
//                    payloadVPN.setAuthName(pppInfo.getAuthName());
//                    payloadVPN.setAuthPassword(new String(pppInfo.getAuthPassword()));
//                    payloadVPN.setCommRemoteAddress(pppInfo.getCommRemoteAddress());
//                    Boolean tokenCard = pppInfo.getTokenCard();
//                    if(tokenCard != null){
//                        payloadVPN.setAuthType(PayloadVPN.AUTH_TYPE_RSA_SEC_ID);
//                    }else{
//                        payloadVPN.setAuthType(PayloadVPN.AUTH_TYPE_PASS);
//                        payloadVPN.setAuthPassword(pppInfo.getAuthPassword());
//                    }
//                    payloadVPN.setEncryptionLevel();
//                }
//            }
//        }
//    }
    private void parseVpnJson2PlistBean(PayloadVPN payloadVPN) {
        IPv4Info iPv4Info = new IPv4Info();
        iPv4Info.setOverridePrimary(payloadVPN.isOverridePrimary()? 1: 0);


        Integer vpnProxiesType = payloadVPN.getVpnProxiesType();
        if(vpnProxiesType != null){
            VpnProxies vpnProxies = new VpnProxies();
            switch (vpnProxiesType){
                case PayloadVPN.PROXY_TYPE_NO:

                    break;
                case PayloadVPN.PROXY_TYPE_AUTO:
                    vpnProxies.setProxyAutoConfigEnable(1);
                    vpnProxies.setProxyAutoConfigURLString(payloadVPN.getHTTPProxy());
                    break;
                case PayloadVPN.PROXY_TYPE_HAND:
                    vpnProxies.setHTTPEnable(1);
                    vpnProxies.setHTTPProxy(payloadVPN.getHTTPProxy());
                    vpnProxies.setHTTPPort(payloadVPN.getHTTPPort());
                    vpnProxies.setHTTPProxyUsername(payloadVPN.getHTTPProxyUsername());
                    vpnProxies.setHTTPProxyPassword(payloadVPN.getHTTPProxyPassword());
                    vpnProxies.setHTTPSEnable(1);
                    vpnProxies.setHTTPSProxy(payloadVPN.getHTTPProxy());
                    vpnProxies.setHTTPSPort(payloadVPN.getHTTPPort());
                    break;
            }
            payloadVPN.setProxies(vpnProxies);
        }

        String vpnType = payloadVPN.getVPNType();
        if(PayloadVPN.TYPE_L2TP.equalsIgnoreCase(vpnType)){

            PPPInfo pppInfo =new PPPInfo();
            pppInfo.setAuthName(payloadVPN.getAuthName());
            pppInfo.setCommRemoteAddress(payloadVPN.getCommRemoteAddress());
            Integer authType = payloadVPN.getAuthType();
            if(authType != null){
                if(authType==PayloadVPN.AUTH_TYPE_PASS){
                    pppInfo.setAuthPassword(payloadVPN.getAuthPassword());
                }else{
                    pppInfo.setTokenCard(true);
                    pppInfo.enableAuthEAPPlugins();
                    pppInfo.enableAuthProtocol();
                }
            }
            payloadVPN.setPPP(pppInfo);

            IPSecInfo ipSecInfo = new IPSecInfo();
            ipSecInfo.setAuthenticationMethod("SharedSecret");
            if(payloadVPN.getSharedSecret() != null){
                ipSecInfo.setSharedSecret(payloadVPN.getSharedSecret().getBytes());
            }
            payloadVPN.setIPSec(ipSecInfo);

        }else if(PayloadVPN.TYPE_PPTP.equalsIgnoreCase(vpnType)){
            PPPInfo pppInfo =new PPPInfo();
            pppInfo.setAuthName(payloadVPN.getAuthName());
            pppInfo.setAuthPassword(payloadVPN.getAuthPassword());
            pppInfo.setCommRemoteAddress(payloadVPN.getCommRemoteAddress());
            Integer authType = payloadVPN.getAuthType();
            if(authType != null){
                if(authType==PayloadVPN.AUTH_TYPE_PASS){

                }else{
                    pppInfo.setTokenCard(true);
                    pppInfo.enableAuthEAPPlugins();
                    pppInfo.enableAuthProtocol();
                }
            }
            Integer encryptionLevel = payloadVPN.getEncryptionLevel();
            if(encryptionLevel != null){
                switch (encryptionLevel){
                    case PayloadVPN.ENCRYPTION_LEVEL_NO:
                        pppInfo.setCCPEnabled(0);
                        pppInfo.setCCPMPPE40Enabled(0);
                        pppInfo.setCCPMPPE128Enabled(0);
                        break;
                    case PayloadVPN.ENCRYPTION_LEVEL_AUTO:
                        pppInfo.setCCPEnabled(1);
                        pppInfo.setCCPMPPE40Enabled(1);
                        pppInfo.setCCPMPPE128Enabled(1);
                        break;
                    case PayloadVPN.ENCRYPTION_LEVEL_MAX:
                        pppInfo.setCCPEnabled(1);
                        pppInfo.setCCPMPPE40Enabled(0);
                        pppInfo.setCCPMPPE128Enabled(1);
                        break;
                }
            }
            payloadVPN.setPPP(pppInfo);
            payloadVPN.setIPSec(null);
        }else if(PayloadVPN.TYPE_IPSEC.equalsIgnoreCase(vpnType)){

            iPv4Info.setOverridePrimary(1);

            IPSecInfo ipSecInfo = new IPSecInfo();
            ipSecInfo.setRemoteAddress(payloadVPN.getCommRemoteAddress());

            Integer machineAuth = payloadVPN.getMachineAuth();
            if(machineAuth != null){
                switch (machineAuth){
                    case PayloadVPN.MACHINE_AUTH_SHAREDPASS:
                        ipSecInfo.setAuthenticationMethod("SharedSecret");
                        break;
                }
            }

            ipSecInfo.setPromptForVPNPIN(payloadVPN.getPromptForVPNPIN());
            ipSecInfo.setXAuthName(payloadVPN.getAuthName());
            ipSecInfo.setXAuthPassword(payloadVPN.getAuthPassword());
            ipSecInfo.setXAuthEnabled(1);
            if(payloadVPN.getSharedSecret() != null){
                ipSecInfo.setAuthenticationMethod("SharedSecret");
                ipSecInfo.setSharedSecret(payloadVPN.getSharedSecret().getBytes());
            }
            if(payloadVPN.getLocalIdentifier() != null)
                ipSecInfo.setLocalIdentifier(payloadVPN.getLocalIdentifier());
            if(payloadVPN.getUseMixedIdentifi()){
                ipSecInfo.setLocalIdentifierType("KeyID");
            }
            payloadVPN.setPPP(null);
            payloadVPN.setIPSec(ipSecInfo);
        }
        payloadVPN.setIPv4(iPv4Info);
    }
}
