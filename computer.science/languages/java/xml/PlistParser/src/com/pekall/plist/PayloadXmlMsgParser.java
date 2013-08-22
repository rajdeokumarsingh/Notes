package com.pekall.plist;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.pekall.plist.beans.*;

import java.util.ArrayList;

/**
 * XML parser for ios a whole payload message
 */
public class PayloadXmlMsgParser {

    private PayloadArrayWrapper wrapper;
    private PayloadPasswordPolicy passwordPolicy;
    private PayloadWifiConfig wifiConfig;
    private PayloadRestrictionsPolicy restrictionsPolicy;
    private PayloadEmail email;
    private PayloadRemovalPassword removalPassword;
    private PayloadWebClip webClip;
    private PayloadLDAP ldap;
    private PayloadExchange exchange;
    private PayloadVPN vpn;

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
                if (PayloadBase.PAYLOAD_TYPE_PASSWORD_POLICY.equals(base.getPayloadType())) {
                    NSDictionary dict = (NSDictionary) nsArray.objectAtIndex(i);
                    passwordPolicy = (PayloadPasswordPolicy) PlistBeanConverter
                            .createBeanFromNdict(dict, PayloadPasswordPolicy.class);
                    newBases.add(passwordPolicy);
                } else if (PayloadBase.PAYLOAD_TYPE_WIFI_MANAGED.equals(base.getPayloadType())) {
                    NSDictionary dict = (NSDictionary) nsArray.objectAtIndex(i);
                    wifiConfig = (PayloadWifiConfig) PlistBeanConverter
                            .createBeanFromNdict(dict, PayloadWifiConfig.class);
                    newBases.add(wifiConfig);
                } else if (PayloadBase.PAYLOAD_TYPE_RESTRICTIONS.equals(base.getPayloadType())) {
                    NSDictionary dict = (NSDictionary) nsArray.objectAtIndex(i);
                    restrictionsPolicy = (PayloadRestrictionsPolicy) PlistBeanConverter
                            .createBeanFromNdict(dict, PayloadRestrictionsPolicy.class);
                    newBases.add(restrictionsPolicy);
                } else if (PayloadBase.PAYLOAD_TYPE_EMAIL.equals(base.getPayloadType())) {
                    NSDictionary dict = (NSDictionary) nsArray.objectAtIndex(i);
                    email = (PayloadEmail) PlistBeanConverter
                            .createBeanFromNdict(dict, PayloadEmail.class);
                    newBases.add(email);
                } else if (PayloadBase.PAYLOAD_TYPE_REMOVAL_PASSWORD.equals(base.getPayloadType())) {
                    NSDictionary dict = (NSDictionary) nsArray.objectAtIndex(i);
                    removalPassword = (PayloadRemovalPassword) PlistBeanConverter
                            .createBeanFromNdict(dict, PayloadRemovalPassword.class);
                    newBases.add(removalPassword);
                } else if (PayloadBase.PAYLOAD_TYPE_WEB_CLIP.equals(base.getPayloadType())) {
                    NSDictionary dict = (NSDictionary) nsArray.objectAtIndex(i);
                    webClip = (PayloadWebClip) PlistBeanConverter
                            .createBeanFromNdict(dict, PayloadWebClip.class);
                    newBases.add(webClip);
                } else if (PayloadBase.PAYLOAD_TYPE_LDAP.equals(base.getPayloadType())) {
                    NSDictionary dict = (NSDictionary) nsArray.objectAtIndex(i);
                    ldap = (PayloadLDAP) PlistBeanConverter
                            .createBeanFromNdict(dict, PayloadLDAP.class);
                    newBases.add(ldap);
                } else if (PayloadBase.PAYLOAD_TYPE_IOS_EXCHANGE.equals(base.getPayloadType())) {
                    NSDictionary dict = (NSDictionary) nsArray.objectAtIndex(i);
                    exchange = (PayloadExchange) PlistBeanConverter
                            .createBeanFromNdict(dict, PayloadExchange.class);
                    newBases.add(exchange);
                } else if (PayloadBase.PAYLOAD_TYPE_VPN.equals(base.getPayloadType())) {
                    NSDictionary dict = (NSDictionary) nsArray.objectAtIndex(i);
                    vpn = (PayloadVPN) PlistBeanConverter
                            .createBeanFromNdict(dict, PayloadVPN.class);
                    newBases.add(vpn);
                }
            }
            wrapper.setPayloadContent(newBases);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     */
    public PayloadPasswordPolicy getPasswordPolicy() {
        return passwordPolicy;
    }

    /**
     * Get bean of wifi config
     * @return wifi config
     */
    public PayloadWifiConfig getWifiConfig() {
        return wifiConfig;
    }

    /**
     * Get bean of restrictions policy
     * @return restrictions policy
     */
    public PayloadRestrictionsPolicy getRestrictionsPolicy() {
        return restrictionsPolicy;
    }

    /**
     * Get bean object of email policy
     * @return bean object of email policy
     */
    public PayloadEmail getEmail() {
        return email;
    }

    /**
     * Get bean object of removal password policy
     * @return bean object of removal password policy
     */
    public PayloadRemovalPassword getRemovalPassword() {
        return removalPassword;
    }

    /**
     * Get bean object of web clip
     * @return bean object of web clip
     */
    public PayloadWebClip getWebClip() {
        return webClip;
    }

    /**
     * Get bean object of LDAP policy
     * @return bean object of LDAP policy
     */
    public PayloadLDAP getLDAP() {
        return ldap;
    }

    /**
     * Get bean object of Exchange policy
     * @return bean object of Exchange policy
     */
    public PayloadExchange getExchange() {
        return exchange;
    }

    /**
     * Get bean object of VPN policy
     * @return bean object of VPN policy
     */
    public PayloadVPN getVPN() {
        return vpn;
    }
}
