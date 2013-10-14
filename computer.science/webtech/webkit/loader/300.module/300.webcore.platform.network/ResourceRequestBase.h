including:
    KURL m_url;
    String m_httpMethod;
    HTTPHeaderMap m_httpHeaderFields;
    RefPtr<FormData> m_httpBody;
    Vector<String> m_responseContentDispositionEncodingFallbackArray;
    bool m_allowCookies;

    bool m_reportUploadProgress;

    bool m_reportLoadTiming;

    bool m_reportRawHeaders;

    ResourceLoadPriority m_priority;

    TargetType m_targetType;

        // The type of this ResourceRequest, based on how the resource will be used.
        enum TargetType {
            TargetIsMainFrame,
            TargetIsSubframe,
            TargetIsSubresource,  // Resource is a generic subresource.  (Generally a specific type should be specified)
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

    ResourceRequestCachePolicy m_cachePolicy;

        enum ResourceRequestCachePolicy {
            UseProtocolCachePolicy, // normal load
            ReloadIgnoringCacheData, // reload
            ReturnCacheDataElseLoad, // back/forward or encoding change - allow stale data
            ReturnCacheDataDontLoad  // results of a post - allow stale data and only use cache
        };




