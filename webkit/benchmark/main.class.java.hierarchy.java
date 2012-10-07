Java层基本对应关系 {
    WebView 1 ---> {
        WebViewCore 1 [create] ---> {
            WebSettings 1 [create] 

            BrowserFrame 1 [create] {
                JWebCoreJavaBridge 1 [create]
                CookieSyncManager 1 [create]

                CacheManage 
                PluginManager [global]

                WebSettings
                CallbackProxy

                WebViewDatabase [global]

                JNI {
                    .mNativeFrame ---> WebCore::Frame [native function]
                }
            }

            WebIconDatabase static
            WebStorage static
            GeolocationPermissions static
        }

        CallbackProxy 1 [create] 
        ViewManager 1 [create] 
        OverScroller 1 [create] 
        ZoomManager 1 [create] 

        WebViewDatabase static
        WebViewCore.AutoFillData 1 [create] 
    }
}

