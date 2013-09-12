package com.pekall.plist.beans;

import java.util.HashMap;

/**
 * Since some plist keys like "com.pekall.mdm.password.profile", contain illegal characters
 * as a java identifier, we need to translate these plist keys to java identifiers and vice versa
 */
public class KeyFieldTranslation {
    static HashMap<String, String> field2Key = new HashMap<String, String>();
    static HashMap<String, String> key2field = new HashMap<String, String>();
    static {
        field2Key.put("com_pekall_mdm_password_profile", "com.pekall.mdm.password.profile");
        key2field.put("com.pekall.mdm.password.profile", "com_pekall_mdm_password_profile");
    }

    /**
     * Translate java field name to plist key if need
     * @param fieldName field name to translate
     * @return plist key or null
     */
    public static String translateJavaField(String fieldName) {
        return field2Key.get(fieldName);
    }

    /**
     * Translate plist key to java field name if need
     * @param key to translate
     * @return java field name or null
     */
    public static String translatePlistKey(String key) {
        return key2field.get(key);
    }
}
