package com.pekall.plist;

public class Constants {
    /**
     * Keys for plist profiles
     */
    public static final String KEY_PL_CONTENT = "PayloadContent";
    public static final String KEY_PL_DESCRIPTION = "PayloadDescription";
    public static final String KEY_PL_DISPLAY_NAME = "PayloadDisplayName";
    public static final String KEY_PL_IDENTIFIER = "PayloadIdentifier";
    public static final String KEY_PL_ORGANIZATION = "PayloadOrganization";
    public static final String KEY_PL_TYPE = "PayloadType";
    public static final String KEY_PL_UUID = "PayloadUUID";
    public static final String KEY_PL_VERSION = "PayloadVersion";
    public static final String KEY_PL_REMOVAL_DISALLOWED = "PayloadRemovalDisallowed";

    public static final String PL_TYPE_PASSWORD_POLICY =
            "com.apple.mobiledevice.passwordpolicy";

    public static final String PL_ID_PASSWORD_POLICY =
            "com.apple.mobiledevice.passwordpolicy";

    public static final String PL_TYPE_WIFI_MANAGED = "com.apple.wifi.managed";

    public static final String EMPTY_PLIST_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
            "<plist version=\"1.0\">\n" +
            "<dict>\n" +
            "</dict>\n" +
            "</plist>";
}
