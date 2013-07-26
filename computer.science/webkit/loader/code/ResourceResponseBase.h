
namespace WebCore {
    #include "HTTPHeaderMap.h"
    #include "KURL.h"
    #include "ResourceLoadInfo.h"
    #include "ResourceLoadTiming.h"

    class ResourceResponse;
    struct CrossThreadResourceResponseData;

// Do not use this class directly, use the class ResponseResponse instead
class ResourceResponseBase {
    static PassOwnPtr<ResourceResponse> adopt(PassOwnPtr<CrossThreadResourceResponseData>);

    // Gets a copy of the data suitable for passing to another thread.
    PassOwnPtr<CrossThreadResourceResponseData> copyData() const;

    bool isNull() const { return m_isNull; }
    bool isHTTP() const;

    const KURL& url() const;
    void setURL(const KURL& url);

    const String& mimeType() const;
    void setMimeType(const String& mimeType);

    long long expectedContentLength() const;
    void setExpectedContentLength(long long expectedContentLength);

    const String& textEncodingName() const;
    void setTextEncodingName(const String& name);

    bool isOverrideEncoding() { return m_overrideEncoding; }
    void setOverrideEncoding(bool o) { m_overrideEncoding = o;}

    // FIXME should compute this on the fly
    const String& suggestedFilename() const;
    void setSuggestedFilename(const String&);

    int httpStatusCode() const;
    void setHTTPStatusCode(int);
    
    const String& httpStatusText() const;
    void setHTTPStatusText(const String&);
    
    String httpHeaderField(const AtomicString& name) const;
    String httpHeaderField(const char* name) const;
    void setHTTPHeaderField(const AtomicString& name, const String& value);
    const HTTPHeaderMap& httpHeaderFields() const;

    bool isMultipart() const { return mimeType() == "multipart/x-mixed-replace"; }

    bool isAttachment() const;
    
    // FIXME: These are used by PluginStream on some platforms. Calculations may differ from just returning plain Last-odified header.
    // Leaving it for now but this should go away in favor of generic solution.
    void setLastModifiedDate(time_t);
    time_t lastModifiedDate() const; 

    // These functions return parsed values of the corresponding response headers.
    // NaN means that the header was not present or had invalid value.
    bool cacheControlContainsNoCache() const;
    bool cacheControlContainsNoStore() const;
    bool cacheControlContainsMustRevalidate() const;
    bool hasCacheValidatorFields() const;
    double cacheControlMaxAge() const;
    double date() const;
    double age() const;
    double expires() const;
    double lastModified() const;

    unsigned connectionID() const;
    void setConnectionID(unsigned);

    bool connectionReused() const;
    void setConnectionReused(bool);

    bool wasCached() const;
    void setWasCached(bool);

    ResourceLoadTiming* resourceLoadTiming() const;
    void setResourceLoadTiming(PassRefPtr<ResourceLoadTiming>);

    PassRefPtr<ResourceLoadInfo> resourceLoadInfo() const;
    void setResourceLoadInfo(PassRefPtr<ResourceLoadInfo>);

    // The ResourceResponse subclass may "shadow" this method to provide platform-specific memory usage information
    unsigned memoryUsage() const
    {
        // average size, mostly due to URL and Header Map strings
        return 1280;
    }

    static bool compare(const ResourceResponse&, const ResourceResponse&);

protected:
    enum InitLevel {
        Uninitialized,
        CommonFieldsOnly,
        AllFields
    };

    ResourceResponseBase();
    ResourceResponseBase(const KURL& url, const String& mimeType, long long expectedLength, const String& textEncodingName, const String& filename);

    void lazyInit(InitLevel) const;

    // The ResourceResponse subclass may "shadow" this method to lazily initialize platform specific fields
    void platformLazyInit(InitLevel) { }

    // The ResourceResponse subclass may "shadow" this method to compare platform specific fields
    static bool platformCompare(const ResourceResponse&, const ResourceResponse&) { return true; }

    KURL m_url;
    String m_mimeType;
    long long m_expectedContentLength;
    String m_textEncodingName;
    bool m_overrideEncoding;
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
    
private:
    const ResourceResponse& asResourceResponse() const;
    void parseCacheControlDirectives() const;

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
};

inline bool operator==(const ResourceResponse& a, const ResourceResponse& b) { return ResourceResponseBase::compare(a, b); }
inline bool operator!=(const ResourceResponse& a, const ResourceResponse& b) { return !(a == b); }

struct CrossThreadResourceResponseDataBase {
    WTF_MAKE_NONCOPYABLE(CrossThreadResourceResponseDataBase);
public:
    CrossThreadResourceResponseDataBase() { }
    KURL m_url;
    String m_mimeType;
    long long m_expectedContentLength;
    String m_textEncodingName;
    bool m_overrideEncoding;
    String m_suggestedFilename;
    int m_httpStatusCode;
    String m_httpStatusText;
    OwnPtr<CrossThreadHTTPHeaderMapData> m_httpHeaders;
    time_t m_lastModifiedDate;
    RefPtr<ResourceLoadTiming> m_resourceLoadTiming;
};

} // namespace WebCore

#endif // ResourceResponseBase_h
