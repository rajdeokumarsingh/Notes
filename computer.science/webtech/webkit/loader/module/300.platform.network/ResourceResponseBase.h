including:
    KURL m_url;
    String m_mimeType;
    long long m_expectedContentLength;
    String m_textEncodingName;
    String m_suggestedFilename;
    int m_httpStatusCode;
    String m_httpStatusText;
    HTTPHeaderMap m_httpHeaderFields;

    time_t m_lastModifiedDate;
    bool m_wasCached : 1;
    unsigned m_connectionID;
    bool m_connectionReused : 1;
    RefPtr<ResourceLoadTiming> m_resourceLoadTiming;
    RefPtr<ResourceLoadInfo> m_resourceLoadInfo;

    bool m_isNull : 1;

    mutable bool m_haveParsedCacheControlHeader : 1;
    mutable bool m_haveParsedAgeHeader : 1;
    mutable bool m_haveParsedDateHeader : 1;
    mutable bool m_haveParsedExpiresHeader : 1;
    mutable bool m_haveParsedLastModifiedHeader : 1;

    mutable bool m_cacheControlContainsNoCache : 1;
    mutable bool m_cacheControlContainsNoStore : 1;
    mutable bool m_cacheControlContainsMustRevalidate : 1;
    mutable double m_cacheControlMaxAge;

    mutable double m_age;
    mutable double m_date;
    mutable double m_expires;
    mutable double m_lastModified;

