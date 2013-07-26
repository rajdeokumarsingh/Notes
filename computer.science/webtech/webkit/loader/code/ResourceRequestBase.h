


namespace WebCore {
    #include "FormData.h"
    #include "HTTPHeaderMap.h"
    #include "KURL.h"
    #include "ResourceLoadPriority.h"


    // cache策略，一般，reload, back/forward, post
    enum ResourceRequestCachePolicy {
        UseProtocolCachePolicy, // normal load
        ReloadIgnoringCacheData, // reload
        ReturnCacheDataElseLoad, // back/forward or encoding change - allow stale data
        ReturnCacheDataDontLoad  // results of a post - allow stale data and only use cache
    };

    struct CrossThreadResourceRequestData;

    class ResourceRequestBase {

        // The type of this ResourceRequest, based on 
        // how the resource will be used.
        enum TargetType {
            TargetIsMainFrame,    // 主页面 ??
            TargetIsSubframe,     // iframe ??
            TargetIsSubresource,  // Resource is a generic subresource.  
                                  // (Generally a specific type should be 
                                  // specified)
            TargetIsStyleSheet,   
            TargetIsScript,      
            TargetIsFontResource, 
            TargetIsImage,       
            TargetIsObject,         
            TargetIsMedia,
            TargetIsWorker,
            TargetIsSharedWorker,
            TargetIsPrefetch,
            TargetIsFavicon,
        };

        const KURL& url() const;
        void setURL(const KURL& url);

        void removeCredentials();

        ResourceRequestCachePolicy cachePolicy() const;
        void setCachePolicy(ResourceRequestCachePolicy cachePolicy);

         // May return 0 when using platform default.
        double timeoutInterval() const;
        void setTimeoutInterval(double timeoutInterval);
        
        const KURL& firstPartyForCookies() const;
        void setFirstPartyForCookies(const KURL& firstPartyForCookies);
        
        // http 协议相关
        const String& httpMethod() const;
        void setHTTPMethod(const String& httpMethod);
        const HTTPHeaderMap& httpHeaderFields() const;
        String httpHeaderField(const AtomicString& name) const;
        String httpHeaderField(const char* name) const;
        void setHTTPHeaderField(const AtomicString& name, const String& value);
        void setHTTPHeaderField(const char* name, const String& value);
        void addHTTPHeaderField(const AtomicString& name, const String& value);
        void addHTTPHeaderFields(const HTTPHeaderMap& headerFields);
        void clearHTTPAuthorization();
        String httpContentType() const { 
            return httpHeaderField("Content-Type");  }
        void setHTTPContentType(const String& httpContentType) { 
            setHTTPHeaderField("Content-Type", httpContentType); }
        String httpReferrer() const { return httpHeaderField("Referer"); }
        void setHTTPReferrer(const String& httpReferrer) {
            setHTTPHeaderField("Referer", httpReferrer); }
        void clearHTTPReferrer();
        String httpOrigin() const {
            return httpHeaderField("Origin"); }
        void setHTTPOrigin(const String& httpOrigin) { 
            setHTTPHeaderField("Origin", httpOrigin); }
        void clearHTTPOrigin();
        String httpUserAgent() const {
            return httpHeaderField("User-Agent"); }
        void setHTTPUserAgent(const String& httpUserAgent) {
            setHTTPHeaderField("User-Agent", httpUserAgent); }
        String httpAccept() const { return httpHeaderField("Accept"); }
        void setHTTPAccept(const String& httpAccept) {
            setHTTPHeaderField("Accept", httpAccept); }
        void setResponseContentDispositionEncodingFallbackArray(
            const String& encoding1, const String& encoding2 = String(), 
            const String& encoding3 = String());

        FormData* httpBody() const;
        void setHTTPBody(PassRefPtr<FormData> httpBody);
        bool allowCookies() const;
        void setAllowCookies(bool allowCookies);

        ResourceLoadPriority priority() const;
        void setPriority(ResourceLoadPriority);

        bool isConditional() const;

        // Whether the associated ResourceHandleClient needs to be notified of
        // upload progress made for that resource.
        bool reportUploadProgress() const {
            return m_reportUploadProgress; }
        void setReportUploadProgress(bool reportUploadProgress) {
            m_reportUploadProgress = reportUploadProgress; }

        // Whether the timing information should be collected for the request.
        bool reportLoadTiming() const { return m_reportLoadTiming; }
        void setReportLoadTiming(bool reportLoadTiming) {
            m_reportLoadTiming = reportLoadTiming; }

        // Whether actual headers being sent/received should be collected and reported for the request.
        bool reportRawHeaders() const { return m_reportRawHeaders; }
        void setReportRawHeaders(bool reportRawHeaders) { m_reportRawHeaders = reportRawHeaders; }

        // What this request is for.
        // FIXME: This should be moved out of ResourceRequestBase, <https://bugs.webkit.org/show_bug.cgi?id=48483>.
        TargetType targetType() const { return m_targetType; }
        void setTargetType(TargetType type) { m_targetType = type; }

        static double defaultTimeoutInterval(); // May return 0 when using platform default.
        static void setDefaultTimeoutInterval(double);

        static bool compare(const ResourceRequest&, const ResourceRequest&);

    protected:
        void updatePlatformRequest() const; 
        void updateResourceRequest() const; 

        KURL m_url;

        ResourceRequestCachePolicy m_cachePolicy;
        double m_timeoutInterval; // 0 is a magic value for platform default on platforms that have one.

        KURL m_firstPartyForCookies;
        String m_httpMethod;
        HTTPHeaderMap m_httpHeaderFields;
        Vector<String> m_responseContentDispositionEncodingFallbackArray;
        RefPtr<FormData> m_httpBody;
        bool m_allowCookies;
        mutable bool m_resourceRequestUpdated;
        mutable bool m_platformRequestUpdated;
        bool m_reportUploadProgress;
        bool m_reportLoadTiming;
        bool m_reportRawHeaders;
        ResourceLoadPriority m_priority;
        TargetType m_targetType;

    };

   struct CrossThreadResourceRequestDataBase {
        WTF_MAKE_NONCOPYABLE(CrossThreadResourceRequestDataBase); WTF_MAKE_FAST_ALLOCATED;
    public:
        CrossThreadResourceRequestDataBase() { }
        KURL m_url;

        ResourceRequestCachePolicy m_cachePolicy;
        double m_timeoutInterval;
        KURL m_firstPartyForCookies;

        String m_httpMethod;
        OwnPtr<CrossThreadHTTPHeaderMapData> m_httpHeaders;
        Vector<String> m_responseContentDispositionEncodingFallbackArray;
        RefPtr<FormData> m_httpBody;
        bool m_allowCookies;
        ResourceLoadPriority m_priority;
        ResourceRequestBase::TargetType m_targetType;
    };
    
    unsigned initializeMaximumHTTPConnectionCountPerHost();

} // namespace WebCore
