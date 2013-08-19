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

    public PayloadEmail getEmail() {
        return email;
    }
}
