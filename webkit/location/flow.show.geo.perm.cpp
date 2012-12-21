// ================================================================================
//                              WebKit
// ================================================================================

WebCore/page/Geolocation.cpp
    void Geolocation::requestPermission()


WebKit/android/WebCoreSupport/ChromeClientAndroid.cpp
    if (!m_geolocationPermissions) {
        m_geolocationPermissions = new GeolocationPermissions(android::WebViewCore::getWebViewCore(frame->view()),
                m_webFrame->page()->mainFrame());
    }
    m_geolocationPermissions->queryPermissionState(frame);

WebKit/android/WebCoreSupport/GeolocationPermissions.cpp

./GeolocationPermissions.cpp

WebKit/android/jni/WebViewCore.cpp
void WebViewCore::geolocationPermissionsShowPrompt(const WTF::String& origin)
{
   jstring originString = wtfStringToJstring(env, origin);
    env->CallVoidMethod(javaObject.get(),
            m_javaGlue->m_geolocationPermissionsShowPrompt,
            originString);
}

// ================================================================================
//                              Framework
// ================================================================================

base/core/java/android/webkit/WebViewCore.java
/**
 * Shows a prompt to ask the user to set the Geolocation permission state
 * for the given origin.
 * @param origin The origin for which Geolocation permissions are
 *     requested.
 */   
protected void geolocationPermissionsShowPrompt(String origin) {
    mCallbackProxy.onGeolocationPermissionsShowPrompt(origin,
        new GeolocationPermissions.Callback() {
            public void invoke(String origin, boolean allow, boolean remember) {
                GeolocationPermissionsData data = new GeolocationPermissionsData();
                data.mOrigin = origin;
                data.mAllow = allow;
                data.mRemember = remember;
                // Marshall to WebCore thread.
                sendMessage(EventHub.GEOLOCATION_PERMISSIONS_PROVIDE, data);
            }
        });
}

base/core/java/android/webkit/CallbackProxy.java
/**
 * Called by WebViewCore to instruct the browser to display a prompt to ask
 * the user to set the Geolocation permission state for the given origin.
 * @param origin The origin requesting Geolocation permsissions.
 * @param callback The callback to call once a permission state has been
 *     obtained.
 */
public void onGeolocationPermissionsShowPrompt(String origin,
        GeolocationPermissions.Callback callback) {
    Message showMessage =
        obtainMessage(GEOLOCATION_PERMISSIONS_SHOW_PROMPT);
    HashMap<String, Object> map = new HashMap();
    map.put("origin", origin);
    map.put("callback", callback);
    showMessage.obj = map;
    sendMessage(showMessage);
}
        |
        V
case GEOLOCATION_PERMISSIONS_SHOW_PROMPT:
    HashMap<String, Object> map =
        (HashMap<String, Object>) msg.obj;
    String origin = (String) map.get("origin");
    GeolocationPermissions.Callback callback =
        (GeolocationPermissions.Callback)map.get("callback");
    mWebChromeClient.onGeolocationPermissionsShowPrompt(origin, callback);
            |
            V
// ================================================================================
//                              Browser
// ================================================================================

src/com/android/browser/Tab.java
private final WebChromeClient mWebChromeClient = new WebChromeClient() {
    /**
     * Instructs the browser to show a prompt to ask the user to set the
     * Geolocation permission state for the specified origin.
     * @param origin The origin for which Geolocation permissions are
     *     requested.
     * @param callback The callback to call once the user has set the
     *     Geolocation permission state.
     */
    @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                GeolocationPermissions.Callback callback) {
                getGeolocationPermissionsPrompt().show(origin, callback);
        }

    /**
     * Instructs the browser to hide the Geolocation permissions prompt.
     */
    @Override
    public void onGeolocationPermissionsHidePrompt() {
            mGeolocationPermissionsPrompt.hide();
    }

}
