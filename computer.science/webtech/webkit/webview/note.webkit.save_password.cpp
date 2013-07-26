base/core/java/android/webkit/BrowserFrame.java
    /* 自动保存密码的过程:
        发送一个http post请求的时候，一般是form中的submit
            如果发现form中有type是password的input element时
                从form中获取username和password
                检查post data中是否包括username和password, 如果有
                    检查数据库中是否存在该页面的用户名和密码,如果不为空
                        更新密码
                        否则，发送消息到Browser请用户确定是否保存密码 
    */
    private LoadListener startLoadingResource(int loaderHandle, String url, String method, HashMap headers, 
            byte[] postData, long postDataIdentifier, int cacheMode, boolean mainResource, boolean userGesture, 
            boolean synchronous, String username, String password) {

        if (method.equals("POST")) {
            if (mSettings.getSavePassword() && hasPasswordField()) {
                WebAddress uri = new WebAddress(mCallbackProxy
                        .getBackForwardList().getCurrentItem().getUrl());
                String schemePlusHost = uri.mScheme + uri.mHost;
                String[] ret = getUsernamePassword();
                // Has the user entered a username/password pair and is there some POST data
                if (ret != null && postData != null && 
                        ret[0].length() > 0 && ret[1].length() > 0) {
                    // Check to see if the username & password appear in
                    // the post data (there could be another form on the
                    // page and that was posted instead.
                    String postString = new String(postData);
                    if (postString.contains(URLEncoder.encode(ret[0])) &&
                            postString.contains(URLEncoder.encode(ret[1]))) {
                        String[] saved = mDatabase.getUsernamePassword(schemePlusHost);
                        if (saved != null) {
                            // null username implies that user has chosen not to save password
                            if (saved[0] != null) {
                                // non-null username implies that user has
                                // chosen to save password, so update the 
                                // recorded password
                                mDatabase.setUsernamePassword(
                                        schemePlusHost, ret[0], ret[1]);
                        } else {
                            // CallbackProxy will handle creating the resume message
                            mCallbackProxy.onSavePassword(schemePlusHost, ret[0], 
                                    ret[1], null);
                                    |
                                    V
base/core/java/android/webkit/CallbackProxy.java
    public boolean onSavePassword(String schemePlusHost, String username, String password, Message resumeMsg) {
        // resumeMsg should be null at this point because we want to create it within the CallbackProxy.
        resumeMsg = obtainMessage(NOTIFY);

        Message msg = obtainMessage(SAVE_PASSWORD, resumeMsg);
        Bundle bundle = msg.getData();
        bundle.putString("host", schemePlusHost);
        bundle.putString("username", username);
        bundle.putString("password", password);
        synchronized (this) {
            sendMessage(msg);
                wait();  |
                         V
                case SAVE_PASSWORD:
                    Bundle bundle = msg.getData();
                    String schemePlusHost = bundle.getString("host");
                    String username = bundle.getString("username");
                    String password = bundle.getString("password");
                    // If the client returned false it means that the notify message
                    // will not be sent and we should notify WebCore ourselves.
                    if (!mWebView.onSavePassword(schemePlusHost, username, password, (Message) msg.obj))
                        synchronized (this) 
                            notify();
        return false;                                   |
                                                        V
base/core/java/android/webkit/WebView.java
    boolean onSavePassword(String schemePlusHost, String username, 
        String password, final Message resumeMsg) {
        boolean rVal = false;
        if (resumeMsg == null) {
           // null resumeMsg implies saving password silently
           mDatabase.setUsernamePassword(schemePlusHost, username, password);
        } else {
            final Message remember = mPrivateHandler.obtainMessage(
                    REMEMBER_PASSWORD);
            remember.getData().putString("host", schemePlusHost);
            remember.getData().putString("username", username);
            remember.getData().putString("password", password);
            remember.obj = resumeMsg;
                    |
                    V
                handleMessage
                    case REMEMBER_PASSWORD: {
                        mDatabase.setUsernamePassword(
                                msg.getData().getString("host"),
                                msg.getData().getString("username"),
                                msg.getData().getString("password"));
                        ((Message) msg.obj).sendToTarget();

            final Message neverRemember = mPrivateHandler.obtainMessage(
                    NEVER_REMEMBER_PASSWORD);
            neverRemember.getData().putString("host", schemePlusHost);
            neverRemember.getData().putString("username", username);
            neverRemember.getData().putString("password", password);
            neverRemember.obj = resumeMsg;
                    |
                    V
                handleMessage
                    case NEVER_REMEMBER_PASSWORD:
                        mDatabase.setUsernamePassword(
                        msg.getData().getString("host"), null, null);
                        ((Message) msg.obj).sendToTarget();

            // show an AlertDialog to prompt user

/* 自动填充密码的过程: 页面加载完成后
    如果发现页面中有password区域
        从数据库中获取该页面的username和password
        将获取到到的username和passwor设置到页面中去
*/
base/core/java/android/webkit/BrowserFrame.java
    private void setProgress(int newProgress) {
        mCallbackProxy.onProgressChanged(newProgress);
        if (newProgress == 100) {
            sendMessageDelayed(obtainMessage(FRAME_COMPLETED), 100);
                |
                V
    public void handleMessage(Message msg)
        case FRAME_COMPLETED: {
        if (mSettings.getSavePassword() && hasPasswordField()) {
          WebHistoryItem item = mCallbackProxy.getBackForwardList().getCurrentItem();
          if (item != null) {
              WebAddress uri = new WebAddress(item.getUrl());
              String schemePlusHost = uri.mScheme + uri.mHost;
              String[] up = mDatabase.getUsernamePassword(schemePlusHost);
              if (up != null && up[0] != null) {
                  setUsernamePassword(up[0], up[1]);

WebKit/android/jni/WebCoreFrameBridge.cpp

    // Java层调用该接口判断文档中是否存在密码区域

    // 遍历document中的所有form elment
    //  遍历form element中的所有input element 
    //      如果发现该input element的type是password
    //          return true
    static jboolean HasPasswordField(JNIEnv *env, jobject obj)

        WebCore::Frame* pFrame = GET_NATIVE_FRAME(env, obj);

        bool found = false;
        WTF::PassRefPtr<WebCore::HTMLCollection> form = pFrame->document()->forms(); 
        WebCore::Node* node = form->firstItem();

        // Null/Empty namespace means that node is not created in HTMLFormElement
        // class, but just normal Element class.
        while (node && !found && !node->namespaceURI().isNull() && !node->namespaceURI().isEmpty()) 
            WTF::Vector<WebCore::HTMLFormControlElement*>& elements = ((WebCore::HTMLFormElement*)node)->formElements;
            size_t size = elements.size();
            for (size_t i = 0; i< size && !found; i++) 
                WebCore::HTMLFormControlElement* e = elements[i];
                if (e->hasLocalName(WebCore::HTMLNames::inputTag)) 
                    if (((WebCore::HTMLInputElement*)e)->inputType() ==
                            WebCore::HTMLInputElement::PASSWORD)
                        found = true;
            node = form->nextItem();

        return found;

    /* Java层调用获取文档中的用户名和密码

       遍历document中的所有form elment
        遍历form element中的所有input element 
            如果发现input存在autoComplete属性, continue

            如果发现该input element的type是password
                保存到password

            如果发现该input element的type是text
                保存到username
            如果password和username都不为空，表示找到

        将找到的password和username返回到java层
     */
    static jobjectArray GetUsernamePassword(JNIEnv *env, jobject obj)
        WebCore::Frame* pFrame = GET_NATIVE_FRAME(env, obj);
        jobjectArray strArray = NULL;

        WebCore::String username, password;
        bool found = false;
        WTF::PassRefPtr<WebCore::HTMLCollection> form = pFrame->document()->forms();
        WebCore::Node* node = form->firstItem();
        while (node && !found && !node->namespaceURI().isNull() && !node->namespaceURI().isEmpty()) 
            WTF::Vector<WebCore::HTMLFormControlElement*>& elements = ((WebCore::HTMLFormElement*)node)->formElements;
            size_t size = elements.size();
            for (size_t i = 0; i< size && !found; i++) 
                WebCore::HTMLFormControlElement* e = elements[i];
                if (e->hasLocalName(WebCore::HTMLNames::inputTag)) 
                    WebCore::HTMLInputElement* input = (WebCore::HTMLInputElement*)e;
                    if (input->autoComplete() == false)
                        continue;
                    if (input->inputType() == WebCore::HTMLInputElement::PASSWORD)
                        password = input->value();
                    else if (input->inputType() == WebCore::HTMLInputElement::TEXT)
                        username = input->value();
                    if (!username.isNull() && !password.isNull())
                        found = true;
            node = form->nextItem();

        if (found) 
            jclass stringClass = env->FindClass("java/lang/String");
            strArray = env->NewObjectArray(2, stringClass, NULL);
            env->SetObjectArrayElement(strArray, 0, env->NewString((unsigned short *)
                    username.characters(), username.length()));
            env->SetObjectArrayElement(strArray, 1, env->NewString((unsigned short *)
                    password.characters(), password.length()));

        return strArray;

    /* Java层调用获取文档中的用户名和密码

       遍历document中的所有form elment
        遍历form element中的所有input element 
            如果发现input存在autoComplete属性, continue

            如果发现该input element的type是password
                保存到password
            如果发现该input element的type是text
                保存到username
            如果password和username都不为空，表示找到
    
        将java层传递过来的username和password设置到对应的input element中去
            达到自动填充密码的目的
     */
    static void SetUsernamePassword(JNIEnv *env, jobject obj, jstring username, jstring password)
    
        WebCore::Frame* pFrame = GET_NATIVE_FRAME(env, obj);

        WebCore::HTMLInputElement* usernameEle = NULL;
        WebCore::HTMLInputElement* passwordEle = NULL;
        bool found = false;
        WTF::PassRefPtr<WebCore::HTMLCollection> form = pFrame->document()->forms();
        WebCore::Node* node = form->firstItem();
        while (node && !found && !node->namespaceURI().isNull() && !node->namespaceURI().isEmpty()) 
            WTF::Vector<WebCore::HTMLFormControlElement*>& elements = ((WebCore::HTMLFormElement*)node)->formElements;
            size_t size = elements.size();
            for (size_t i = 0; i< size && !found; i++) 
                WebCore::HTMLFormControlElement* e = elements[i];
                if (e->hasLocalName(WebCore::HTMLNames::inputTag)) 
                    WebCore::HTMLInputElement* input = (WebCore::HTMLInputElement*)e;
                    if (input->autoComplete() == false)
                        continue;
                    if (input->inputType() == WebCore::HTMLInputElement::PASSWORD)
                        passwordEle = input;
                    else if (input->inputType() == WebCore::HTMLInputElement::TEXT)
                        usernameEle = input;
                    if (usernameEle != NULL && passwordEle != NULL)
                        found = true;
            node = form->nextItem();

        if (found) 
            usernameEle->setValue(to_string(env, username));
            passwordEle->setValue(to_string(env, password));


