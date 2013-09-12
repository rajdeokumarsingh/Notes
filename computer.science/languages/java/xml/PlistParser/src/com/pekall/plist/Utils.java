package com.pekall.plist;

import com.pekall.plist.beans.CommandObject;

/**
 * Utility class
 */
public class Utils {

    /**
     * Return a safe string from the input string
     * @param str input string
     * @return "" if str is null, or str
     */
    public static String safeString(String str) {
        return (str != null ? str : "");
    }

    public static CommandObject safeCommandObject(CommandObject cmd) {
        return (cmd != null ? cmd : new CommandObject());
    }

    public static byte[] safeByteArray(byte[] bytes) {
        return (bytes != null ? bytes : new byte[0]);
    }
}
