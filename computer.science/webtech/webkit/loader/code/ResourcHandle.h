#include "AuthenticationChallenge.h"
#include "AuthenticationClient.h"
#include "HTTPHeaderMap.h"
#include "NetworkingContext.h"
#include "ThreadableLoader.h"
#include <wtf/OwnPtr.h>

namespace WebCore {

class AuthenticationChallenge;
class Credential;
class FormData;
class Frame;
class KURL;
class ProtectionSpace;
class ResourceError;
class ResourceHandleClient;
class ResourceHandleInternal;
class ResourceRequest;
class ResourceResponse;
class SchedulePair;
class SharedBuffer;

template <typename T> class Timer;

class ResourceHandle : public RefCounted<ResourceHandle>
    {
public:
    static PassRefPtr<ResourceHandle> create(NetworkingContext*, const ResourceRequest&, ResourceHandleClient*, bool defersLoading, bool shouldContentSniff);
    static void loadResourceSynchronously(NetworkingContext*, const ResourceRequest&, StoredCredentials, ResourceError&, ResourceResponse&, Vector<char>& data);

    static void prepareForURL(const KURL&);
    static bool willLoadFromCache(ResourceRequest&, Frame*);
    static void cacheMetadata(const ResourceResponse&, const Vector<char>&);

    virtual ~ResourceHandle();

    PassRefPtr<SharedBuffer> bufferedData();
    static bool supportsBufferedData();

    bool shouldContentSniff() const;
    static bool shouldContentSniffURL(const KURL&);

    static void forceContentSniffing();


#if PLATFORM(QT) || USE(CURL) || USE(SOUP) || PLATFORM(ANDROID)
    ResourceHandleInternal* getInternal() { return d.get(); }
#endif

    // Used to work around the fact that you don't get any more NSURLConnection callbacks until you return from the one you're in.
    static bool loadsBlocked();    

    bool hasAuthenticationChallenge() const;
    void clearAuthentication();
    virtual void cancel();

    // The client may be 0, in which case no callbacks will be made.
    ResourceHandleClient* client() const;
    void setClient(ResourceHandleClient*);

    void setDefersLoading(bool);
#if PLATFORM(ANDROID)
// TODO: this needs upstreaming.
    void pauseLoad(bool);
#endif
      
    ResourceRequest& firstRequest();
    const String& lastHTTPMethod() const;

    void fireFailure(Timer<ResourceHandle>*);

    using RefCounted<ResourceHandle>::ref;
    using RefCounted<ResourceHandle>::deref;

private:
    enum FailureType {
        NoFailure,
        BlockedFailure,
        InvalidURLFailure
    };

    void platformSetDefersLoading(bool);

    void scheduleFailure(FailureType);

    bool start(NetworkingContext*);

    virtual void refAuthenticationClient() { ref(); }
    virtual void derefAuthenticationClient() { deref(); }

    friend class ResourceHandleInternal;
    OwnPtr<ResourceHandleInternal> d;
};

}

