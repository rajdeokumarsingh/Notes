http://stackoverflow.com/questions/5329662/android-webview-geolocation

// 在android应用中enable geolocation

1. 权限：
<uses-permission android:name="android.permission.ACCESS_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_GPS" />
<uses-permission android:name="android.permission.ACCESS_ASSISTED_GPS" />

2. 实现接口
webView.setWebChromeClient(new WebChromeClient() {
    public void onGeolocationPermissionsShowPrompt(String origin, 
        GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
    }
});

webView.getSettings().setGeolocationDatabasePath("/data/data/<my-app>");

// In some cases, HTML5 require to use storage, you must enable some properties 
// so that webview has full access to run normal.
// HTML5 API flags
webView.getSettings().setAppCacheEnabled(true);
webView.getSettings().setDatabaseEnabled(true);
webView.getSettings().setDomStorageEnabled(true);


