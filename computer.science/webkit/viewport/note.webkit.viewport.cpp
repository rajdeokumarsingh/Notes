

http://10086.cn/m
<meta id="viewport" name="viewport" content="initial-scale=0.7; minimum-scale=0.7;maximum-scale=1.0; user-scalable=1;width=device-width; height=device-height;" />

为什么10086页面中的元素放大后会错乱。
由于使用了float属性， 
    #search{float:right;padding-top:15px;color:#266aaa;}
    当页面空间无法容纳下放大的元素时， float的元素会自动跳至下一行

注释：假如在一行之上只有极少的空间可供浮动元素，那么这个元素会跳至下一行，这个过程会持续到某一行拥有足够的空间为止。

WebCore/page/Settings.cpp
    // read meta content from <meta> element
    void Settings::setMetadataSettings(const String& key, const String& value)
        m_viewport_width
        m_viewport_height
        m_viewport_initial_scale
        m_viewport_minimum_scale
        m_viewport_maximum_scale
        m_viewport_user_scalable


    static void SetViewportSettingsFromNative(JNIEnv *env, jobject obj)
    #ifdef ANDROID_META_SUPPORT
        env->SetIntField(obj, gWebViewCoreFields.m_viewportWidth, s->viewportWidth()); // mViewportWidth
        env->SetIntField(obj, gWebViewCoreFields.m_viewportHeight, s->viewportHeight()); // mViewportHeight
        env->SetIntField(obj, gWebViewCoreFields.m_viewportInitialScale, s->viewportInitialScale()); // mViewportInitialScale
        env->SetIntField(obj, gWebViewCoreFields.m_viewportMinimumScale, s->viewportMinimumScale()); // mViewportMinimumScale
        env->SetIntField(obj, gWebViewCoreFields.m_viewportMaximumScale, s->viewportMaximumScale()); // mViewportMaximumScale
        env->SetBooleanField(obj, gWebViewCoreFields.m_viewportUserScalable, s->viewportUserScalable()); // mViewportUserScalable
        env->SetIntField(obj, gWebViewCoreFields.m_viewportDensityDpi, s->viewportTargetDensityDpi()); // mViewportDensityDpi
    #endif

base/core/java/android/webkit/WebView.java
    // Compute unzoomed width and height, and if they differ from the last
    // values we sent, send them to webkit (to be used has new viewport)                           
    private boolean sendViewSizeZoom()
        int viewWidth = getViewWidth();                             // viewWidth: 240, not changed
        int newWidth = Math.round(viewWidth * mInvActualScale);     //mInvActualScale: 1.923077, 1.5384616, 1.3333334
        int newHeight = Math.round(getViewHeight() * mInvActualScale);


base/core/java/android/webkit/WebViewCore.java
    // notify webkit that our virtual view size changed size (after inv-zoom)
    private void viewSizeChanged(int w, int h, int textwrapWidth, float scale,
            int anchorX, int anchorY, boolean ignoreHeight) 

WebKit/android/jni/WebViewCore.cpp

    SetSize(JNIEnv *env, jobject obj, jint width, jint height,
            jint screenWidth, jfloat scale, jint realScreenWidth, jint screenHeight,
                    jint anchorX, jint anchorY, jboolean ignoreHeight)

        WebViewCore* viewImpl = GET_NATIVE_VIEW(env, obj);
        viewImpl->setSizeScreenWidthAndScale(width, height, screenWidth, scale,
                realScreenWidth, screenHeight, anchorX, anchorY, ignoreHeight);

