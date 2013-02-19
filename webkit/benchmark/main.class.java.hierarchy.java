Java层基本对应关系 {
    WebView 1 ---> {
        CallbackProxy 1 [create] 
        ViewManager 1 [create] 
        OverScroller 1 [create] 
        ZoomManager 1 [create] 

        WebViewCore.AutoFillData 1 [create] 

        WebViewDatabase [global]

        WebViewCore 1 [create] ---> {
            WebSettings 1 [create] 
            WebIconDatabase [global]
            WebStorage [global]
            GeolocationPermissions [global]

            BrowserFrame 1 [create] {
                JWebCoreJavaBridge [global]
                CookieSyncManager [global]
                CacheManager [global]
                PluginManager [global]

                SearchBoxImpl 1 [create]
                Map<String, Object> javascriptInterfaces  1

                ConfigCallback [global]

                JNI {
                    .mNativeFrame ---> WebCore::Frame [native层Page的main Frame]
                }

                Key functions {
                    /**
                     * Create a new native frame for a given WebView
                     * @param w     A WebView that the frame draws into.
                     * @param am    AssetManager to use to get assets.
                     * @param list  The native side will add and remove items from this list as
                     *              the native list changes.
                     */
                    private native void nativeCreateFrame(WebViewCore w, AssetManager am,
                            WebBackForwardList list);
                                |
                                | JNI 
                                V
                    webkit/Source/WebKit/android/jni/WebCoreFrameBridge.cpp
                        static void CreateFrame(
                                JNIEnv* env, jobject obj, jobject javaview, 
                                jobject jAssetManager, jobject historyList)

                                // 创建一个WebView对应的几乎所有native层的结构, 参见
                                    ./main.class.native.hierarchy.cpp
                }
            }
        }
    }
}
