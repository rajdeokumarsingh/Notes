#include "config.h"
#include "WebCache.h"

#include "JNIUtility.h"
#include "WebCoreJni.h"
#include "WebRequestContext.h"
#include "WebUrlLoaderClient.h"

#include <wtf/text/CString.h>

using namespace WTF;
using namespace disk_cache;
using namespace net;
using namespace std;

namespace android {

static WTF::Mutex instanceMutex;

// 调用jni返回cache的目录
static string storageDirectory()
{
    static const char* const kDirectory = "/webviewCacheChromium";

    JNIEnv* env = JSC::Bindings::getJNIEnv();
    jclass bridgeClass = env->FindClass("android/webkit/JniUtil");
    jmethodID method = env->GetStaticMethodID(bridgeClass, "getCacheDirectory", "()Ljava/lang/String;");
    string storageDirectory = jstringToStdString(env, static_cast<jstring>(env->CallStaticObjectMethod(bridgeClass, method)));
    return storageDirectory;
}

// 全局有两个静态的WebCache, 一个是for private browser, 
//      一个是for non-private browser
// 返回private/non-private的WebCache Instatnce
WebCache* WebCache::get(bool isPrivateBrowsing)
{
    MutexLocker lock(instanceMutex);
    scoped_refptr<WebCache>* instancePtr = instance(isPrivateBrowsing);
    if (!instancePtr->get())
        *instancePtr = new WebCache(isPrivateBrowsing);
    return instancePtr->get();
}

WebCache::WebCache(bool isPrivateBrowsing)
    : m_doomAllEntriesCallback(this, &WebCache::doomAllEntries)
    , m_onClearDoneCallback(this, &WebCache::onClearDone)
    , m_isClearInProgress(false)
    , m_openEntryCallback(this, &WebCache::openEntry)
    , m_onGetEntryDoneCallback(this, &WebCache::onGetEntryDone)
    , m_isGetEntryInProgress(false)
    , m_cacheBackend(0)
{
    base::Thread* ioThread = WebUrlLoaderClient::ioThread();
    scoped_refptr<base::MessageLoopProxy> cacheMessageLoopProxy = ioThread->message_loop_proxy();

    static const int kMaximumCacheSizeBytes = 20 * 1024 * 1024;
    m_hostResolver = net::CreateSystemHostResolver(net::HostResolver::kDefaultParallelism, 0, 0);

    m_proxyConfigService = new ProxyConfigServiceAndroid();
    net::HttpCache::BackendFactory* backendFactory;

    // 如果是private browsing, 使用http cache的内存策略
    if (isPrivateBrowsing)
        backendFactory = net::HttpCache::DefaultBackend::InMemory(kMaximumCacheSizeBytes / 2);
    else {
        string storage(storageDirectory());
        if (storage.empty()) // Can't get a storage directory from the OS
            backendFactory = net::HttpCache::DefaultBackend::InMemory(kMaximumCacheSizeBytes / 2);
        // 否则使用http cache的disk策略
        else {
            FilePath directoryPath(storage.c_str());
            backendFactory = new net::HttpCache::DefaultBackend(net::DISK_CACHE, directoryPath, kMaximumCacheSizeBytes, cacheMessageLoopProxy);
        }
    }

    // 创建http cache
    m_cache = new net::HttpCache(m_hostResolver.get(),
                                 new CertVerifier(),
                                 0, // dnsrr_resolver
                                 0, // dns_cert_checker
                                 net::ProxyService::CreateWithoutProxyResolver(m_proxyConfigService, 0 /* net_log */),
                                 net::SSLConfigService::CreateSystemSSLConfigService(),
                                 net::HttpAuthHandlerFactory::CreateDefault(m_hostResolver.get()),
                                 0, // network_delegate
                                 0, // net_log
                                 backendFactory);
}

void WebCache::clear()
{
    // 在io thread中执行clearImpl
    base::Thread* thread = WebUrlLoaderClient::ioThread();
    thread->message_loop()->PostTask(FROM_HERE, 
            NewRunnableMethod(this, &WebCache::clearImpl));
}

void WebCache::closeIdleConnections()
{
    // 在io thread中执行clearImpl
    base::Thread* thread = WebUrlLoaderClient::ioThread();
    thread->message_loop()->PostTask(FROM_HERE, 
            NewRunnableMethod(this, &WebCache::closeIdleImpl));
}

void WebCache::closeIdleImpl()
    m_cache->CloseIdleConnections();

void WebCache::clearImpl()
{
    doomAllEntries(0 /*unused*/);
}

void WebCache::doomAllEntries(int)
{
    // Code ERR_IO_PENDING indicates that the operation is still in progress and
    // the supplied callback will be invoked when it completes.
    m_cacheBackend->DoomAllEntries(&m_onClearDoneCallback))
}

// 通过url获取CacheResult
scoped_refptr<CacheResult> WebCache::getCacheResult(String url)
{
    // This is called on the UI thread.
    MutexLocker lock(m_getEntryMutex);

    // The Chromium methods are asynchronous, but we need this method to be
    // synchronous. Do the work on the Chromium thread but block this thread
    // here waiting for the work to complete.
    base::Thread* thread = WebUrlLoaderClient::ioThread();

    m_entry = 0;
    m_isGetEntryInProgress = true;
    m_entryUrl = url.threadsafeCopy();
    thread->message_loop()->PostTask(FROM_HERE, NewRunnableMethod(this, &WebCache::getEntryImpl));

    // 等待getEntryImpl的
    while (m_isGetEntryInProgress)
        m_getEntryCondition.wait(m_getEntryMutex);

    if (!m_entry)
        return 0;

    return new CacheResult(m_entry, url);
}

void WebCache::getEntryImpl()
{
    if (!m_cacheBackend) {
        int code = m_cache->GetBackend(&m_cacheBackend, &m_openEntryCallback);
        if (code == ERR_IO_PENDING)
            return;
        else if (code != OK) {
            onGetEntryDone(0 /*unused*/);
            return;
        }
    }
    openEntry(0 /*unused*/);
}

void WebCache::openEntry(int)
{
    if (!m_cacheBackend) {
        onGetEntryDone(0 /*unused*/);
        return;
    }

    if (m_cacheBackend->OpenEntry(string(m_entryUrl.utf8().data()), &m_entry, &m_onGetEntryDoneCallback) == ERR_IO_PENDING)
        return;
    onGetEntryDone(0 /*unused*/);
}

void WebCache::onGetEntryDone(int)
{
    // Unblock the UI thread in getEntry();
    MutexLocker lock(m_getEntryMutex);
    m_isGetEntryInProgress = false;
    m_getEntryCondition.signal();
}

} // namespace android
