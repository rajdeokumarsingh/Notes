
package com.pekall.demo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.util.Log;

public class Utility {
    private static final boolean ENABLE_DEBUG = true;

    public static void log(String msg) {
        if (ENABLE_DEBUG) {
            StackTraceElement st = Thread.currentThread().getStackTrace()[3];
            Log.d("", "[---" + st.getFileName() + ": " + st.getLineNumber() + "---]  " + msg);
        }
    }

    public static void log(String tag, String msg) {
        if (ENABLE_DEBUG) {
            StackTraceElement st = Thread.currentThread().getStackTrace()[3];
            Log.d(tag, "[---" + st.getFileName() + ": " + st.getLineNumber() + "---]" + msg);
        }
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isHaveNetwork(Context ctx) {
        return isWifiConnected(ctx) || isMobileConnected(ctx);
    }

    private static boolean isWifiConnected(Context context) {
        return getNetworkState(context, ConnectivityManager.TYPE_WIFI) == State.CONNECTED;
    }

    private static boolean isMobileConnected(Context context) {
        return getNetworkState(context, ConnectivityManager.TYPE_MOBILE) == State.CONNECTED;
    }

    private static State getNetworkState(Context context, int networkType) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(networkType);

        return info == null ? null : info.getState();
    }

}
