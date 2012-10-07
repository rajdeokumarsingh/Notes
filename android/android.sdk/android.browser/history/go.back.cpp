================================================================================
go back
================================================================================

src/com/android/browser/BrowserActivity.java
    WebView w = current.getWebView();
    if (w.canGoBack())
        w.goBack();
            |
            V
base/core/java/android/webkit/WebView.java
            |
            V
base/core/java/android/webkit/WebViewCore.java
    case GO_BACK_FORWARD:
        if (!mBrowserFrame.committed() && msg.arg1 == -1 &&
                (mBrowserFrame.loadType() ==
                 BrowserFrame.FRAME_LOADTYPE_STANDARD)) 
            mBrowserFrame.reload(true);
                    |
                    V JNI to WebKit
        else 
            mBrowserFrame.goBackOrForward(msg.arg1);
                nativeGoBackOrForward(steps);
                    |
                    V JNI to WebKit
WebKit/android/jni/WebCoreFrameBridge.cpp
    static void GoBackOrForward(JNIEnv *env, jobject obj, jint pos)
        pFrame->page()->goBack();

            WebCore/page/Page.cpp
            bool Page::goBack()
                HistoryItem* item = m_backForwardList->backItem();
                goToItem(item, FrameLoadTypeBack);

                    void Page::goToItem(HistoryItem* item, FrameLoadType type)
                        HistoryItem* currentItem = m_mainFrame->loader()->history()->currentItem();
                        // db policy
                        {
                            // Define what to do with any open database connections. 
                            // By default we stop them and terminate the database thread.

                            // If we're navigating the history via a fragment on the same document, 
                            // then we do not want to stop databases.
                            m_mainFrame->loader()->stopAllLoaders(databasePolicy);
                                I/WebCore::FrameLoader( 2015): stopAllLoaders, 2500
                                I/WebCore ( 2015): cancelPolicyCheck, 564
                                I/WebCore::FrameLoader( 2015): stopLoadingSubframes, 2493
                                I/WebCore::FrameLoader( 2015): setProvisionalDocumentLoader, 2601
                        }
                        m_mainFrame->loader()->history()->goToItem(item, type);
                            |
                            V
WebCore/loader/HistoryController.cpp
    // Main funnel for navigating to a previous location (back/forward, non-search snap-back)
    // This includes recursion to handle loading into framesets properly
    void HistoryController::goToItem(HistoryItem* targetItem, FrameLoadType type)
        if (!m_frame->loader()->client()->shouldGoToHistoryItem(targetItem)) return;

        // Set the BF cursor before commit, which lets the user quickly click back/forward again.
        // - plus, it only makes sense for the top level of the operation through the frametree,
        // as opposed to happening for some/one of the page commits that might happen soon
        BackForwardList* bfList = page->backForwardList();
        HistoryItem* currentItem = bfList->currentItem();
        bfList->goToItem(targetItem);
            m_page->mainFrame()->loader()->client()->dispatchDidChangeBackForwardIndex();
                m_webFrame->updateHistoryIndex(list->backListCount());
                // FIXME: anchor_updateHistoryIndex

        Settings* settings = m_frame->settings();
        page->setGlobalHistoryItem((!settings || settings->privateBrowsingEnabled()) ? 0 : targetItem);
        recursiveGoToItem(targetItem, currentItem, type);
            // The general idea here is to traverse the frame tree and the item tree in parallel,
            // tracking whether each frame already has the content the item requests.  If there is
            // a match (by URL), we just restore scroll position and recurse.  Otherwise we must
            // reload that frame, and all its kids.
            void HistoryController::recursiveGoToItem(HistoryItem* item, HistoryItem* fromItem, FrameLoadType type)
                for(all children)
                    childFrame->loader()->history()->recursiveGoToItem(childItems[i].get(), fromChildItem, type);
                // or
                m_frame->loader()->loadItem(item, type); 
                    // Loads content into this frame, as specified by history item
                    void FrameLoader::loadItem(HistoryItem* item, FrameLoadType loadType)
                        if (sameDocumentNavigation)
                            navigateWithinDocument(item);
                        else
                            navigateToDifferentDocument(item, loadType);
                                void FrameLoader::navigateToDifferentDocument(HistoryItem* item, FrameLoadType loadType)
                                    if (RefPtr<CachedPage> cachedPage = pageCache()->get(item))
                                        loadWithDocumentLoader(cachedPage->documentLoader(), loadType, 0);

                                    RefPtr<FormData> formData = item->formData();
                                    ResourceRequest request(itemURL);
                                    if (formData) {
                                        formData->generateFiles(m_frame->page()->chrome()->client());

                                        request.setHTTPMethod("POST");
                                        request.setHTTPBody(formData);
                                        request.setHTTPContentType(item->formContentType());
                                        RefPtr<SecurityOrigin> securityOrigin = SecurityOrigin::createFromString(item->referrer());
                                        addHTTPOriginIfNeeded(request, securityOrigin->toString());
                                        ...
                                        if (ResourceHandle::willLoadFromCache(request, m_frame))
                                            // FIXME: anchor_ResourceHandle_willLoadFromCache
                                            action = NavigationAction(itemURL, loadType, false);
                                        else {
                                            request.setCachePolicy(ReloadIgnoringCacheData);
                                            action = NavigationAction(itemURL, NavigationTypeFormResubmitted);
                                        }

                                        loadWithNavigationAction(request, action, false, loadType, 0);
                                            RefPtr<DocumentLoader> loader = m_client->createDocumentLoader(request, SubstituteData());
                                            loadWithDocumentLoader(loader.get(), type, formState);
                                                bool isFormSubmission = formState;
                                                if (shouldScrollToAnchor(isFormSubmission, policyChecker()->loadType(), newURL))
                                                else
                                                    policyChecker()->stopCheck();
                                                    setPolicyDocumentLoader(loader);
                                                        m_policyDocumentLoader = loader;
                                                    policyChecker()->checkNavigationPolicy(loader->request(), loader, formState,
                                                            callContinueLoadAfterNavigationPolicy, this);
                                                            |
                                                            V
void PolicyChecker::checkNavigationPolicy(const ResourceRequest& request, DocumentLoader* loader,
    PassRefPtr<FormState> formState, NavigationPolicyDecisionFunction function, void* argument)

    m_callback.set(request, formState.get(), function, argument);
    m_frame->loader()->client()->dispatchDecidePolicyForNavigationAction(
        &PolicyChecker::continueAfterNavigationPolicy, action, request, formState);
            |
            V
void FrameLoaderClientAndroid::dispatchDecidePolicyForNavigationAction(
    FramePolicyFunction func, const NavigationAction& action, 
    const ResourceRequest& request, PassRefPtr<FormState> formState) 

    if (action.type() == NavigationTypeFormResubmitted)
        m_webFrame->decidePolicyForFormResubmission(func);
        return; |
                V
void WebFrame::decidePolicyForFormResubmission(WebCore::FramePolicyFunction func)
    PolicyFunctionWrapper* p = new PolicyFunctionWrapper;
    p->func = func;
    env->CallVoidMethod(mJavaFrame->frame(env).get(), mJavaFrame->mDecidePolicyForFormResubmission, p);
        |
        V JNI to JAVA
base/core/java/android/webkit/BrowserFrame.java
    private void decidePolicyForFormResubmission(int policyFunction) {
    Message dontResend = obtainMessage(POLICY_FUNCTION, policyFunction, POLICY_IGNORE);
    Message resend = obtainMessage(POLICY_FUNCTION, policyFunction, POLICY_USE);
    mCallbackProxy.onFormResubmission(dontResend, resend);
    // FIXME: anchor_onFormResubmission



// FIXME: anchor_updateHistoryIndex
WebKit/android/jni/WebCoreFrameBridge.cpp
    WebFrame::updateHistoryIndex(int newIndex)
        WebHistory::UpdateHistoryIndex(mJavaFrame->history(env), newIndex);
            list.env()->CallVoidMethod(list.get(), gWebBackForwardList.mSetCurrentIndex, newIndex);
                    |
                    V JNI to java
base/core/java/android/webkit/WebBackForwardList.java
    synchronized void setCurrentIndex(int newIndex)
        mCallbackProxy.onIndexChanged(getItemAtIndex(newIndex), newIndex);
            mWebBackForwardListClient.onIndexChanged((WebHistoryItem) msg.obj, msg.arg1);
            ...
                | 
                V Browser
            src/com/android/browser/Tab.java
                // JUST
                activateVoiceSearchMode((Intent) data);


// FIXME: anchor_ResourceHandle_willLoadFromCache
WebCore/platform/network/android/ResourceHandleAndroid.cpp
    // This static method is called to check to see if a POST response is in
    // the cache. The JNI call through to the HTTP cache stored on the Java
    // side may be slow, but is only used during a navigation to
    // a POST response.
    bool ResourceHandle::willLoadFromCache(ResourceRequest& request, Frame*)
        // set the cache policy correctly, copied from
        // network/mac/ResourceHandleMac.mm
        request.setCachePolicy(ReturnCacheDataDontLoad);
        FormData* formData = request.httpBody();
        return ResourceLoaderAndroid::willLoadFromCache(request.url(), formData ? formData->identifier() : 0);
            WebCoreResourceLoader::willLoadFromCache(url, identifier);               
                jclass resourceLoader = env->FindClass("android/webkit/LoadListener");
                bool val = env->CallStaticBooleanMethod(resourceLoader,
                        gResourceLoader.mWillLoadFromCacheMethodID, jUrlStr, identifier);
                            | 
                            V JNI to JAVA
base/core/java/android/webkit/LoadListener.java
    static boolean willLoadFromCache(String url, long identifier)
        boolean inCache = CacheManager.getCacheFile(url, identifier, null) != null;


// FIXME: anchor_onFormResubmission
base/core/java/android/webkit/BrowserFrame.java
    decidePolicyForFormResubmission(int policyFunction)
        Message dontResend = obtainMessage(POLICY_FUNCTION, policyFunction, POLICY_IGNORE);
        Message resend = obtainMessage(POLICY_FUNCTION, policyFunction, POLICY_USE);
        mCallbackProxy.onFormResubmission(dontResend, resend);
                |
                V
base/core/java/android/webkit/CallbackProxy.java
    Message msg = obtainMessage(RESEND_POST_DATA);
    Bundle bundle = msg.getData();
    bundle.putParcelable("resend", resend);
    bundle.putParcelable("dontResend", dontResend);
    sendMessage(msg);
            |
            V
mWebViewClient.onFormResubmission(mWebView, dontResend, resend);
        |
        V
src/com/android/browser/Tab.java
    private final WebViewClient mWebViewClient = new WebViewClient() 
        public void onFormResubmission(WebView view, final Message dontResend, final Message resend) 
            ...
            mResend.sendToTarget();
                |
                V
base/core/java/android/webkit/BrowserFrame.java
    public void handleMessage(Message msg) 
        case POLICY_FUNCTION: 
            // arg1 policyFunction, arg2 POLICY_USE
            nativeCallPolicyFunction(msg.arg1, msg.arg2);
                |
                V JNI to WebKit
WebKit/android/jni/WebCoreFrameBridge.cpp
static void CallPolicyFunction(JNIEnv* env, jobject obj, jint func, jint decision)
    (pFrame->loader()->policyChecker()->*(pFunc->func))((WebCore::PolicyAction)decision);
                | 
                V callback of policy checker
        PolicyChecker::continueAfterNavigationPolicy
            callback.call(shouldContinue);
                |
                V callback of frame loader 
void FrameLoader::callContinueLoadAfterNavigationPolicy(void* argument,
        const ResourceRequest& request, PassRefPtr<FormState> formState, bool shouldContinue)
    loader->continueLoadAfterNavigationPolicy(request, formState, shouldContinue);
        FrameLoadType type = policyChecker()->loadType();
        stopAllLoaders();

        setProvisionalDocumentLoader(m_policyDocumentLoader.get());
        m_loadType = type;
        setState(FrameStateProvisional);
            if (newState == FrameStateProvisional)
                provisionalLoadStarted();
                    m_client->provisionalLoadStarted();
                        void FrameLoaderClientAndroid::provisionalLoadStarted()
                            m_webFrame->loadStarted(m_frame);
                                env->CallVoidMethod(mJavaFrame->frame(env).get(), mJavaFrame->mLoadStarted, urlStr, favicon,
                                        (int)loadType, isMainFrame);
                                            |
                                            V JNI to JAVA
                                        base/core/java/android/webkit/BrowserFrame.java

                                ChromeClientAndroid* client = static_cast<ChromeClientAndroid*>(chrome->client());
                                    if (client)
                                        client->onMainFrameLoadStarted();

        setPolicyDocumentLoader(0);

        continueLoadAfterWillSubmitForm();

================================================================================
Add history item
================================================================================

WebCore/history/BackForwardList.cpp
    void BackForwardList::addItem(PassRefPtr<HistoryItem> prpItem)
        m_page->mainFrame()->loader()->client()->dispatchDidAddBackForwardItem(currentItem());

WebKit/android/WebCoreSupport/FrameLoaderClientAndroid.cpp
    void FrameLoaderClientAndroid::dispatchDidAddBackForwardItem(HistoryItem* item) const 
        m_webFrame->addHistoryItem(item);
            |
            V
WebKit/android/jni/WebCoreFrameBridge.cpp
    WebFrame::addHistoryItem(WebCore::HistoryItem* item) 
        WebHistory::AddItem(mJavaFrame->history(env), item);
            |
            V
WebKit/android/jni/WebHistory.cpp
    void WebHistory::AddItem(const AutoJObject& list, WebCore::HistoryItem* item)
        // Allocate a java blank WebHistoryItem
        jclass clazz = env->FindClass("android/webkit/WebHistoryItem");
        jobject newItem = env->NewObject(clazz, gWebHistoryItem.mInit);

        // Add it to the list.
        env->CallVoidMethod(list.get(), gWebBackForwardList.mAddHistoryItem, newItem);


#1  0x00002aaaac74c742 in WebCore::BackForwardList::addItem (this=0x723590, prpItem=...) at ../../../WebCore/history/BackForwardList.cpp:62
#2  0x00002aaaac892445 in WebCore::HistoryController::updateBackForwardListClippedAtTarget (this=0x72e228, doClip=true)
at ../../../WebCore/loader/HistoryController.cpp:624
#3  0x00002aaaac89046c in WebCore::HistoryController::updateForStandardLoad (this=0x72e228)
at ../../../WebCore/loader/HistoryController.cpp:292
#4  0x00002aaaac884701 in WebCore::FrameLoader::transitionToCommitted (this=0x72e100, cachedPage=...)
at ../../../WebCore/loader/FrameLoader.cpp:2546
#5  0x00002aaaac883d59 in WebCore::FrameLoader::commitProvisionalLoad (this=0x72e100, prpCachedPage=...)
at ../../../WebCore/loader/FrameLoader.cpp:2424
#6  0x00002aaaac867593 in WebCore::DocumentLoader::commitIfReady (this=0x949e50) at ../../../WebCore/loader/DocumentLoader.cpp:320
#7  0x00002aaaac86765b in WebCore::DocumentLoader::commitLoad (this=0x949e50, 
data=0x96fb08 "<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=gb2312\"><title>\260ٶ\310һ\317£\254\304\343\276\315֪\265\300      </title><style>html{overflow-y:auto}body{font:12px arial;text-align:center;"..., length=7958)
at ../../../WebCore/loader/DocumentLoader.cpp:340
#8  0x00002aaaac86776c in WebCore::DocumentLoader::receivedData (this=0x949e50, 
data=0x96fb08 "<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=gb2312\"><title>\260ٶ\310һ\317£\254\304\343\276\315֪\265\300      </title><style>html{overflow-y:auto}body{font:12px arial;text-align:center;"..., length=7958)
at ../../../WebCore/loader/DocumentLoader.cpp:354
#9  0x00002aaaac8821d3 in WebCore::FrameLoader::receivedData (this=0x72e100, 
data=0x96fb08 "<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=gb2312\"><title>\260ٶ\310һ\317£\254\304\343\276\315֪\265\300      </title><style>html{overflow-y:auto}body{font:12px arial;text-align:center;"..., length=7958)
at ../../../WebCore/loader/FrameLoader.cpp:2088
#10 0x00002aaaac8acb15 in WebCore::MainResourceLoader::addData (this=0x94c9b0, 
data=0x96fb08 "<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=gb2312\"><title>\260ٶ\310һ\317£\254\304\343\276\315֪\265\300      </title><style>html{overflow-y:auto}body{font:12px arial;text-align:center;"..., length=7958, 
allAtOnce=false) at ../../../WebCore/loader/MainResourceLoader.cpp:143
#11 0x00002aaaac8bb6c1 in WebCore::ResourceLoader::didReceiveData (this=0x94c9b0, 
data=0x96fb08 "<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=gb2312\"><title>\260ٶ\310һ\317£\254\304\343\276\315֪\265\300      </title><style>html{overflow-y:auto}body{font:12px arial;text-align:center;"..., length=7958, 
lengthReceived=7958, allAtOnce=false) at ../../../WebCore/loader/ResourceLoader.cpp:248
#12 0x00002aaaac8adc1d in WebCore::MainResourceLoader::didReceiveData (this=0x94c9b0, 
data=0x96fb08 "<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=gb2312\"><title>\260ٶ\310һ\317£\254\304\343\276\315֪\265\300      </title><style>html{overflow-y:auto}body{font:12px arial;text-align:center;"..., length=7958, 
---Type <return> to continue, or q <return> to quit---
lengthReceived=7958, allAtOnce=false) at ../../../WebCore/loader/MainResourceLoader.cpp:374
#13 0x00002aaaac8bc00c in WebCore::ResourceLoader::didReceiveData (this=0x94c9b0, 
data=0x96fb08 "<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=gb2312\"><title>\260ٶ\310һ\317£\254\304\343\276\315֪\265\300      </title><style>html{overflow-y:auto}body{font:12px arial;text-align:center;"..., length=7958, 
lengthReceived=7958) at ../../../WebCore/loader/ResourceLoader.cpp:398
#14 0x00002aaaacb807a5 in WebCore::QNetworkReplyHandler::forwardData (this=0x94c900)
at ../../../WebCore/platform/network/qt/QNetworkReplyHandler.cpp:368
#15 0x00002aaaacb81272 in WebCore::QNetworkReplyHandler::qt_metacall (this=0x94c900, _c=QMetaObject::InvokeMetaMethod, _id=3, _a=0x954f60)
at ./moc_QNetworkReplyHandler.cpp:78
#16 0x00002aaaaf4e2d49 in QObject::event(QEvent*) () from /usr/lib/libQtCore.so.4
#17 0x00002aaaae55e22c in QApplicationPrivate::notify_helper(QObject*, QEvent*) () from /usr/lib/libQtGui.so.4
#18 0x00002aaaae5646fb in QApplication::notify(QObject*, QEvent*) () from /usr/lib/libQtGui.so.4
#19 0x00002aaaaf4d306c in QCoreApplication::notifyInternal(QObject*, QEvent*) () from /usr/lib/libQtCore.so.4
#20 0x00002aaaaf4d57e7 in QCoreApplicationPrivate::sendPostedEvents(QObject*, int, QThreadData*) () from /usr/lib/libQtCore.so.4
#21 0x00002aaaaf4fc9d3 in ?? () from /usr/lib/libQtCore.so.4
#22 0x00002aaab1a688c2 in g_main_context_dispatch () from /lib/libglib-2.0.so.0
#23 0x00002aaab1a6c748 in ?? () from /lib/libglib-2.0.so.0
#24 0x00002aaab1a6c8fc in g_main_context_iteration () from /lib/libglib-2.0.so.0
#25 0x00002aaaaf4fc513 in QEventDispatcherGlib::processEvents(QFlags<QEventLoop::ProcessEventsFlag>) () from /usr/lib/libQtCore.so.4
#26 0x00002aaaae60e46e in ?? () from /usr/lib/libQtGui.so.4
#27 0x00002aaaaf4d1992 in QEventLoop::processEvents(QFlags<QEventLoop::ProcessEventsFlag>) () from /usr/lib/libQtCore.so.4
#28 0x00002aaaaf4d1d6c in QEventLoop::exec(QFlags<QEventLoop::ProcessEventsFlag>) () from /usr/lib/libQtCore.so.4
#29 0x00002aaaaf4d5aab in QCoreApplication::exec() () from /usr/lib/libQtCore.so.4
#30 0x0000000000418523 in launcherMain (app=...) at /home/jiangrui/webkit/webkit/WebKit/qt/QtLauncher/main.cpp:591
#31 0x0000000000418e38 in main (argc=1, argv=0x7fffffffe3e8) at /home/jiangrui/webkit/webkit/WebKit/qt/QtLauncher/main.cpp:654

