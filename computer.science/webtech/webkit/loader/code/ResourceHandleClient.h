
// ResouceHandleClient 的接口同网络传输过程息息相关，一般为某一个网络事件对应的回调。
class ResourceHandleClient
    public:
        virtual ~ResourceHandleClient()

        // 一般情况下，在发起网络请求前调用，可以设置特定的 Http 头部，比如 user agent 等
        // 在重定向请求的时候，也会自动调 用 
        virtual void willSendRequest(ResourceHandle*, ResourceRequest&,
                const ResourceResponse& /*redirectResponse*/)

        // 上传数据的时候，在 TCP wrtie 事件的时候，向对方发送数据的时候调用， 
        // loader 可以根据这个回调显示上传进度。 
        virtual void didSendData(ResourceHandle*, unsigned long long
                /*bytesSent*/, unsigned long long /*totalBytesToBeSent*/)

        // 收到第一个响应包，此时至少 http 的部分头部已经解析（如status code），
        // loader根据响应的头部信息判断请求是否成功等。 
        virtual void didReceiveResponse(ResourceHandle*,
                const ResourceResponse&)

        // 收到 HTTP 响应数据，类似 tcp 的 read 事件，来 http 响应数据了，
        // Network 的设计机制是来一段数据上传一段数据。 
        virtual void didReceiveData(ResourceHandle*, const char*, int,
                int /*encodedDataLength*/)

        virtual void didReceiveCachedMetadata(ResourceHandle*,
                const char*, int)

        // 加载完成，数据来齐
        virtual void didFinishLoading(ResourceHandle*,
                double /*finishTime*/)

        // 加载失败
        virtual void didFail(ResourceHandle*, const ResourceError&)

        // 获取数据是否是block
        virtual void wasBlocked(ResourceHandle*)

        virtual void cannotShowURL(ResourceHandle*)

        virtual void willCacheResponse(ResourceHandle*, CacheStoragePolicy&)

        virtual bool shouldUseCredentialStorage(ResourceHandle*)
             return false;

        // 收到了http authentication response, 要求用户鉴权 
        virtual void didReceiveAuthenticationChallenge(ResourceHandle*,
                const AuthenticationChallenge&)

        virtual void didCancelAuthenticationChallenge(ResourceHandle*,
                const AuthenticationChallenge&)

        virtual void receivedCancellation(ResourceHandle*,
                const AuthenticationChallenge&)


