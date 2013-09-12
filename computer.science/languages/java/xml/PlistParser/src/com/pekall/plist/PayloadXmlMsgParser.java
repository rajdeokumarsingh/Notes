package com.pekall.plist;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.pekall.plist.beans.*;
import com.pekall.plist.su.policy.AppControlList;
import com.pekall.plist.su.policy.MemorySizePolicy;
import com.pekall.plist.su.policy.SystemExceptionPolicy;
import com.pekall.plist.su.settings.SystemSettings;
import com.pekall.plist.su.settings.advertise.AdvertiseDownloadSettings;
import com.pekall.plist.su.settings.browser.BrowserSettings;
import com.pekall.plist.su.settings.launcher.LauncherSettings;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * XML parser for ios a whole payload message
 */
public class PayloadXmlMsgParser {
    private static HashMap<String, Class> payloadTypes = new HashMap<String, Class>();

    static {
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_PASSWORD_POLICY, PayloadPasswordPolicy.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_WIFI_MANAGED, PayloadWifiConfig.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_RESTRICTIONS, PayloadRestrictionsPolicy.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_EMAIL, PayloadEmail.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_REMOVAL_PASSWORD, PayloadRemovalPassword.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_WEB_CLIP, PayloadWebClip.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_LDAP, PayloadLDAP.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_IOS_EXCHANGE, PayloadExchange.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_VPN, PayloadVPN.class);

        // Just for SU MDM
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_SE_BROWSER_SETTINGS, BrowserSettings.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_SYSTEM_SETTINGS, SystemSettings.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_LAUNCHER_SETTINGS, LauncherSettings.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_ADVT_SETTINGS, AdvertiseDownloadSettings.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_WALLPAPER_SETTINGS, PayloadWallpaper.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_MEMORY_POLICY, MemorySizePolicy.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_APP_CONTROL_POLICY, AppControlList.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_SYSTEM_EXCEPTION_POLICY, SystemExceptionPolicy.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_SECURITY_POLICY, PayloadSecurityPolicy.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_RESTRICTIONS_ANDROID_POLICY, PayloadRestrictionsAndroidPolicy.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_NATIVE_APP_CONTROL_POLICY, PayloadNativeAppCtrlPolicy.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_BLUETOOTH_POLICY, PayloadBluetoothPolicy.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_NET_RESTRICT_POLICY, PayloadNetRestrictPolicy.class);
        payloadTypes.put(PayloadBase.PAYLOAD_TYPE_ACTIVE_SYNC_POLICY, PayloadActiveSyncPolicy.class);
    }

    private HashMap<String, PayloadBase> payloads = new HashMap<String, PayloadBase>();

    private PayloadArrayWrapper wrapper;
    /**
     * Constructor, in which the xml string parameter will be parsed into beans
     *
     * @param xml to parse
     */
    public PayloadXmlMsgParser(String xml) {
        try {
            NSDictionary root = (NSDictionary) PlistXmlParser.fromXml(xml);
            internalParse(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructor, in which the xml string parameter will be parsed into beans
     *
     * @param root of the profile
     */
    public PayloadXmlMsgParser(NSDictionary root) {
        internalParse(root);
    }

    private void internalParse(NSDictionary root) {
        try {
            NSObject nsObject = root.objectForKey(Constants.KEY_PL_CONTENT);

            wrapper = (PayloadArrayWrapper) PlistBeanConverter
                    .createBeanFromNdict(root, PayloadArrayWrapper.class);
            if(nsObject == null) return;
            if(!(nsObject instanceof NSArray)) {
                PlistDebug.logError("Payload format not correct!");
                return;
            }

            NSArray nsArray = (NSArray) nsObject;
            ArrayList<PayloadBase> bases = (ArrayList<PayloadBase>) wrapper.getPayloadContent();
            ArrayList<PayloadBase> newBases = new ArrayList<PayloadBase>();

            for (int i = 0; i < bases.size(); i++) {
                PayloadBase base = bases.get(i);
                for (String type : payloadTypes.keySet()) {
                    if (type.equals(base.getPayloadType())) {
                        NSDictionary dict = (NSDictionary) nsArray.objectAtIndex(i);
                        PayloadBase payload = (PayloadBase) PlistBeanConverter
                                .createBeanFromNdict(dict, payloadTypes.get(type));
                        newBases.add(payload);
                        payloads.put(type, payload);
                        break;
                    }
                }
            }
            wrapper.setPayloadContent(newBases);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class getPayloadClass(String type) {
        return payloadTypes.get(type);
    }

    /**
     * Get descriptor for the payload, which is encapsulated in the wrapper
     *
     * @return descriptor
     */
    public PayloadBase getPayloadDescriptor() {
        return this.wrapper;
    }

    /**
     * Get bean of password policy
     * @return password policy
     *
     * @deprecated use getPayloadType
     */
    public PayloadPasswordPolicy getPasswordPolicy() {
        return (PayloadPasswordPolicy) payloads.get(PayloadBase.PAYLOAD_TYPE_PASSWORD_POLICY);
    }

    /**
     * Get bean of wifi config
     * @return wifi config
     *
     * @deprecated use getPayloadType
     */
    public PayloadWifiConfig getWifiConfig() {
        return (PayloadWifiConfig) payloads.get(PayloadBase.PAYLOAD_TYPE_WIFI_MANAGED);
    }

    /**
     * Get bean of restrictions policy
     * @return restrictions policy
     *
     * @deprecated use getPayloadType
     */
    public PayloadRestrictionsPolicy getRestrictionsPolicy() {
        return (PayloadRestrictionsPolicy) payloads.get(PayloadBase.PAYLOAD_TYPE_RESTRICTIONS);
    }

    /**
     * Get bean object of email policy
     * @return bean object of email policy
     *
     * @deprecated use getPayloadType
     */
    public PayloadEmail getEmail() {
        return (PayloadEmail) payloads.get(PayloadBase.PAYLOAD_TYPE_EMAIL);
    }

    /**
     * Get bean object of removal password policy
     * @return bean object of removal password policy
     *
     * @deprecated use getPayloadType
     */
    public PayloadRemovalPassword getRemovalPassword() {
        return (PayloadRemovalPassword) payloads.get(PayloadBase.PAYLOAD_TYPE_REMOVAL_PASSWORD);
    }

    /**
     * Get bean object of web clip
     * @return bean object of web clip
     *
     * @deprecated use getPayloadType
     */
    public PayloadWebClip getWebClip() {
        return (PayloadWebClip) payloads.get(PayloadBase.PAYLOAD_TYPE_WEB_CLIP);
    }

    /**
     * Get bean object of LDAP policy
     * @return bean object of LDAP policy
     *
     * @deprecated use getPayloadType
     */
    public PayloadLDAP getLDAP() {
        return (PayloadLDAP) payloads.get(PayloadBase.PAYLOAD_TYPE_LDAP);
    }

    /**
     * Get bean object of Exchange policy
     * @return bean object of Exchange policy
     *
     * @deprecated use getPayloadType
     */
    public PayloadExchange getExchange() {
        return (PayloadExchange) payloads.get(PayloadBase.PAYLOAD_TYPE_IOS_EXCHANGE);
    }

    /**
     * Get bean object of VPN policy
     * @return bean object of VPN policy
     *
     * @deprecated use getPayloadType
     */
    public PayloadVPN getVPN() {
        return (PayloadVPN) payloads.get(PayloadBase.PAYLOAD_TYPE_VPN);
    }

    /**
     * Get bean object of the payload
     * @param payloadType type of the payload
     * @return bean object
     */
    public PayloadBase getPayload(String payloadType) {
        return payloads.get(payloadType);
    }
}
