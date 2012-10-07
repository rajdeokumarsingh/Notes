
// 问题 H2是什么element?
    ./html/HTMLTagNames.in
    HTMLHeadingElement
    class HTMLHeadingElement : public HTMLElement

// H2的render是
// H2 什么时候换行?
 

base/core/java/android/webkit/WebViewCore.java
    data.mWidth = mWebView.mLastWidthSent;
    data.mHeight = 0;
    // if mHeightCanMeasure is true, getUseWideViewPort() can't be
    // true. It is safe to use mWidth for mTextWrapWidth.
    data.mTextWrapWidth = data.mWidth;
        ...
    nativeSetSize(width, height, textwrapWidth, scale, w,
           data.mActualViewHeight > 0 ? data.mActualViewHeight : h,
           data.mAnchorX, data.mAnchorY, data.mIgnoreHeight);


static void SetSize(JNIEnv *env, jobject obj, jint width, jint height,
       jint textWrapWidth, jfloat scale, jint screenWidth, jint screenHeight,
       jint anchorX, jint anchorY, jboolean ignoreHeight)
{
#ifdef LOG_WEBKIT_ANDROID_JNI_WEBVIEWCORE
    android_printLog(ANDROID_LOG_ERROR, LOG_TAG, "%s, %d\n", __FUNCTION__, __LINE__);
#endif

#ifdef ANDROID_INSTRUMENT
    TimeCounterAuto counter(TimeCounter::WebViewCoreTimeCounter);
#endif
    WebViewCore* viewImpl = GET_NATIVE_VIEW(env, obj);
    LOGV("webviewcore::nativeSetSize(%u %u)\n viewImpl: %p", (unsigned)width, (unsigned)height, viewImpl);
    LOG_ASSERT(viewImpl, "viewImpl not set in nativeSetSize");
    viewImpl->setSizeScreenWidthAndScale(width, height, textWrapWidth, scale,
            screenWidth, screenHeight, anchorX, anchorY, ignoreHeight);
}

