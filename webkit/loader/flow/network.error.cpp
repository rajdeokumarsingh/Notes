// ================================================================================
//                              Chromium
// ================================================================================
net/socket/tcp_client_socket_libevent.cc

// ================================================================================
//                              WebKit
// ================================================================================
// todo:
WebKit/android/WebCoreSupport/WebRequest.cpp
    open LOG_REQUESTS

WebKit/android/WebCoreSupport/WebRequest.cpp
WebKit/android/WebCoreSupport/WebUrlLoaderClient.cpp

void WebRequest::finish(bool success)
{
    m_runnableFactory.RevokeAll();

    if (m_loadState >= Finished)
        return;

    m_loadState = Finished;
    if (success) {
        m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                    m_urlLoader.get(), &WebUrlLoaderClient::didFinishLoading));
    } else {
        if (m_interceptResponse == NULL) {
            OwnPtr<WebResponse> webResponse(new WebResponse(m_request.get()));
            m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                        m_urlLoader.get(), &WebUrlLoaderClient::didFail, webResponse.release()));
        } else {
            OwnPtr<WebResponse> webResponse(new WebResponse(m_url, m_interceptResponse->mimeType(), 0,
                        m_interceptResponse->encoding(), m_interceptResponse->status()));
            m_urlLoader->maybeCallOnMainThread(NewRunnableMethod(
                        m_urlLoader.get(), &WebUrlLoaderClient::didFail, webResponse.release()));
        }
    }
}
            |
            V           
void WebUrlLoaderClient::didFail(PassOwnPtr<WebResponse> webResponse)
{
    m_resourceHandle->client()->didFail(m_resourceHandle.get(), webResponse->createResourceError());
    // Always finish a request, if not it will leak
    finish();
}

WebCore::ResourceError WebResponse::createResourceError()
{
    // XXX: m_error 是chromium的error number
    WebCore::ResourceError error(m_host.c_str(), ToWebViewClientError(m_error), m_url.c_str(), WTF::String());
    return error;
}
                    |
                    |
                    |
                    |
                    V
void MainResourceLoader::didFail(const ResourceError& error)
{
    receivedError(error);
}
            |
            V
void MainResourceLoader::receivedError(const ResourceError& error)
{
    // It is important that we call FrameLoader::receivedMainResourceError before calling 
    // FrameLoader::didFailToLoad because receivedMainResourceError clears out the relevant
    // document loaders. Also, receivedMainResourceError ends up calling a FrameLoadDelegate method
    // and didFailToLoad calls a ResourceLoadDelegate method and they need to be in the correct order.
    frameLoader()->receivedMainResourceError(error, true);

    if (!cancelled()) {
        frameLoader()->notifier()->didFailToLoad(this, error);
        releaseResources();
    }
}
            |
            V
void FrameLoader::receivedMainResourceError(const ResourceError& error, bool isComplete) {
    RefPtr<DocumentLoader> loader = activeDocumentLoader();
    loader->mainReceivedError(error, isComplete);
}
            |
            V
void DocumentLoader::mainReceivedError(const ResourceError& error, bool isComplete)
{
    setMainDocumentError(error);
    if (isComplete)
        frameLoader()->mainReceivedCompleteError(this, error);
}
            |
            V
void DocumentLoader::setMainDocumentError(const ResourceError& error)
{
    m_mainDocumentError = error;    
    frameLoader()->setMainDocumentError(this, error);
}
            |
            V
void FrameLoader::setMainDocumentError(DocumentLoader* loader, const ResourceError& error)
{
    m_client->setMainDocumentError(loader, error);
}
            |
            |  // platform relative layer
            V
void FrameLoaderClientAndroid::setMainDocumentError(DocumentLoader* docLoader, const ResourceError& error) {
    if (m_manualLoader) {
        m_manualLoader->didFail(error);
        m_manualLoader = NULL;
        m_hasSentResponseToPlugin = false;
    } else {
        if (!error.isNull() && error.errorCode() >= InternalErrorLast && error.errorCode() != ERROR_OK)
            m_webFrame->reportError(error.errorCode(),
                    error.localizedDescription(), error.failingURL());
    }
}
                |
                V
WebKit/android/jni/WebCoreFrameBridge.cpp
void WebFrame::reportError(int errorCode, 
        const WTF::String& description, const WTF::String& failingUrl)
{
    jstring descStr = wtfStringToJstring(env, description);
    jstring failUrl = wtfStringToJstring(env, failingUrl);
    env->CallVoidMethod(javaFrame.get(), mJavaFrame->mReportError, errorCode, descStr, failUrl);
}
                |
                |  JNI to java
                V
// ================================================================================
//                              Framework
// ================================================================================
base/core/java/android/webkit/BrowserFrame.java
/**
 * native callback
 * Report an error to an activity.
 * @param errorCode The HTTP error code.
 * @param description Optional human-readable description. If no description
 *     is given, we'll use a standard localized error message.
 * @param failingUrl The URL that was being loaded when the error occurred.
 * TODO: Report all errors including resource errors but include some kind
 * of domain identifier. Change errorCode to an enum for a cleaner
 * interface. 
 */ 
private void reportError(int errorCode, String description, String failingUrl) {
    // As this is called for the main resource and loading will be stopped
    // after, reset the state variables.
    resetLoadingStates();

    // 通过base/core/java/android/net/http/ErrorStrings.java
    // 获取到error number对应的error string
    // error number对应base/core/java/android/net/http/EventHandler.java
    description = ErrorStrings.getString(errorCode, mContext);


    mCallbackProxy.onReceivedError(errorCode, description, failingUrl);
}
        |
        V
base/core/java/android/webkit/CallbackProxy.java
    public void onReceivedError(int errorCode, String description,
            String failingUrl) {

        Message msg = obtainMessage(REPORT_ERROR);
        msg.arg1 = errorCode;
        msg.getData().putString("description", description);
        msg.getData().putString("failingUrl", failingUrl);
        sendMessage(msg);
    }
                |
                V
@Override
public void handleMessage(Message msg) {
    switch (msg.what) {
        ...
        case REPORT_ERROR: 
            if (mWebViewClient != null) {
                int reasonCode = msg.arg1;
                final String description  = msg.getData().getString("description");
                final String failUrl  = msg.getData().getString("failingUrl");
                mWebViewClient.onReceivedError(mWebView, reasonCode,
                        description, failUrl);
            }
            break;
        ...
    }
            |
            V
base/core/java/android/webkit/WebViewClient.java
                |
                |
                V
// ================================================================================
//                          Browser application
// ================================================================================
src/com/android/browser/Tab.java {
    private final WebViewClient mWebViewClient = new WebViewClient() {
        ...
        public void onReceivedError(WebView view, int errorCode,
                String description, String failingUrl) 
        ...
        }

}

// error number
{
    WebKit/android/WebCoreSupport/WebViewClientError.cpp
        // convert chromium to webkit error
        WebViewClientError ToWebViewClientError(net::Error error) {
            // Note: many net::Error constants don't have an obvious mapping.
            // These will be handled by the default case, ERROR_UNKNOWN.
            switch(error) {
                case ERR_UNSUPPORTED_AUTH_SCHEME:
                    return ERROR_UNSUPPORTED_AUTH_SCHEME;

                case ERR_ADDRESS_INVALID:
                case ERR_ADDRESS_UNREACHABLE:
                case ERR_NAME_NOT_RESOLVED:
                case ERR_NAME_RESOLUTION_FAILED:
                    return ERROR_HOST_LOOKUP;

                   return ERROR_PROXY_AUTHENTICATION;
           }
        }

    base/core/java/android/webkit/WebViewClient.java {
        // These ints must match up to the hidden values in EventHandler.
        /** Generic error */
        public static final int ERROR_UNKNOWN = -1;
        /** Server or proxy hostname lookup failed */
        public static final int ERROR_HOST_LOOKUP = -2;
        /** Unsupported authentication scheme (not basic or digest) */
        public static final int ERROR_UNSUPPORTED_AUTH_SCHEME = -3;
        ...
    }

    base/core/java/android/net/http/EventHandler.java {
        /** Success */
        public static final int OK = 0;
        /** Generic error */
        public static final int ERROR = -1;
        /** Server or proxy hostname lookup failed */
        public static final int ERROR_LOOKUP = -2;
        ...
    }

// Error numbers in Chromium
net/base/net_errors.h

// Error values are negative.
enum Error {
    // No error.
    OK = 0,

#define NET_ERROR(label, value) ERR_ ## label = value,
#include "net/base/net_error_list.h"
#undef NET_ERROR

    // The value of the first certificate error code.
    ERR_CERT_BEGIN = ERR_CERT_COMMON_NAME_INVALID,
};

net/base/net_error_list.h {
// Ranges:
//     0- 99 System related errors
//   100-199 Connection related errors
//   200-299 Certificate errors
//   300-399 HTTP errors
//   400-499 Cache errors
//   500-599 ?
//   600-699 FTP errors
//   700-799 Certificate manager errors
// An asynchronous IO operation is not yet complete.  This usually does not
// indicate a fatal error.  Typically this error will be generated as a
// notification to wait for some external notification that the IO operation
// finally completed.
NET_ERROR(IO_PENDING, -1)

// A generic failure occurred.
NET_ERROR(FAILED, -2)

// An operation was aborted (due to user action).
NET_ERROR(ABORTED, -3)
...

}

net/socket/tcp_client_socket_libevent.cc
int MapConnectError(int os_error)  
{ 
}

}
