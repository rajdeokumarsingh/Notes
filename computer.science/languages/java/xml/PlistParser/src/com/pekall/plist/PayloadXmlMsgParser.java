package com.pekall.plist;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSObject;
import com.pekall.plist.beans.PayloadArrayWrapper;
import com.pekall.plist.beans.PayloadBase;
import com.pekall.plist.beans.PayloadPasswordPolicy;

import java.util.ArrayList;

/**
 * XML parser for ios a whole payload message
 */
public class PayloadXmlMsgParser {

    // root object of the payload message
    private NSDictionary nsRoot;
    // array for the PayloadContent part of the payload message
    private NSArray nsArray;
    private PayloadArrayWrapper wrapper;
    private PayloadPasswordPolicy passwordPolicy;

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
            nsRoot = root;
            NSObject nsObject = nsRoot.objectForKey(Constants.KEY_PL_CONTENT);

            wrapper = (PayloadArrayWrapper) PlistBeanConverter
                    .createBeanFromNdict(nsRoot, PayloadArrayWrapper.class);
            if(nsObject == null) return;
            if(!(nsObject instanceof NSArray)) {
                PlistDebug.logError("Payload format not correct!");
                return;
            }

            nsArray = (NSArray) nsObject;
            ArrayList<PayloadBase> bases = (ArrayList<PayloadBase>) wrapper.getPayloadContent();
            ArrayList<PayloadBase> newBases = new ArrayList<PayloadBase>();

            for (int i = 0; i < bases.size(); i++) {
                PayloadBase base = bases.get(i);
                if (Constants.PL_ID_PASSWORD_POLICY.equals(base.getPayloadIdentifier()) ||
                        Constants.PL_TYPE_PASSWORD_POLICY.equals(base.getPayloadType())) {
                    NSDictionary dict = (NSDictionary) nsArray.objectAtIndex(i);
                    passwordPolicy = (PayloadPasswordPolicy) PlistBeanConverter
                            .createBeanFromNdict(dict, PayloadPasswordPolicy.class);
                    newBases.add(passwordPolicy);
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
     * Get bean password policy
     * @return password policy
     */
    public PayloadPasswordPolicy getPasswordPolicy() {
        return passwordPolicy;
    }
}
