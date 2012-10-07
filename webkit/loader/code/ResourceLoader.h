
#ifndef ResourceLoader_h
#define ResourceLoader_h

#include "ResourceHandleClient.h"

#include "ResourceRequest.h"
#include "ResourceResponse.h"
#include "AuthenticationChallenge.h"

#include <wtf/Forward.h>

namespace WebCore {
    
    class ResourceLoader : public RefCounted<ResourceLoader>, protected ResourceHandleClient {

        void cancel();

        virtual bool init(const ResourceRequest&);

        FrameLoader* frameLoader() const;
        DocumentLoader* documentLoader() const { return m_documentLoader.get(); }
        
        virtual void cancel(const ResourceError&);
        ResourceError cancelledError();
        ResourceError blockedError();
        ResourceError cannotShowURLError();
        
        virtual void setDefersLoading(bool);
#if PLATFORM(ANDROID)
// TODO: This needs upstreaming to WebKit.
        virtual void pauseLoad(bool);
#endif

        void setIdentifier(unsigned long identifier) { m_identifier = identifier; }
        unsigned long identifier() const { return m_identifier; }

        virtual void releaseResources();
        const ResourceResponse& response() const;

        virtual void addData(const char*, int, bool allAtOnce);
        virtual PassRefPtr<SharedBuffer> resourceData();
        void clearResourceData();
        
        virtual void willSendRequest(ResourceRequest&, const ResourceResponse& redirectResponse);
        virtual void didSendData(unsigned long long bytesSent, unsigned long long totalBytesToBeSent);
        virtual void didReceiveResponse(const ResourceResponse&);
        virtual void didReceiveData(const char*, int, long long encodedDataLength, bool allAtOnce);
        virtual void didReceiveCachedMetadata(const char*, int) { }
        void willStopBufferingData(const char*, int);
        virtual void didFinishLoading(double finishTime);
        virtual void didFail(const ResourceError&);
        virtual bool shouldUseCredentialStorage();
        virtual void didReceiveAuthenticationChallenge(const AuthenticationChallenge&);
        void didCancelAuthenticationChallenge(const AuthenticationChallenge&);
        virtual void receivedCancellation(const AuthenticationChallenge&);

        // ResourceHandleClient
        virtual void willSendRequest(ResourceHandle*, ResourceRequest&, const ResourceResponse& redirectResponse);
        virtual void didSendData(ResourceHandle*, unsigned long long bytesSent, unsigned long long totalBytesToBeSent);
        virtual void didReceiveResponse(ResourceHandle*, const ResourceResponse&);
        virtual void didReceiveData(ResourceHandle*, const char*, int, int encodedDataLength);
        virtual void didReceiveCachedMetadata(ResourceHandle*, const char* data, int length) { didReceiveCachedMetadata(data, length); }
        virtual void didFinishLoading(ResourceHandle*, double finishTime);
        virtual void didFail(ResourceHandle*, const ResourceError&);
        virtual void wasBlocked(ResourceHandle*);
        virtual void cannotShowURL(ResourceHandle*);
        virtual void willStopBufferingData(ResourceHandle*, const char* data, int length) { willStopBufferingData(data, length); } 
        virtual bool shouldUseCredentialStorage(ResourceHandle*) { return shouldUseCredentialStorage(); }
        virtual void didReceiveAuthenticationChallenge(ResourceHandle*, const AuthenticationChallenge& challenge) { didReceiveAuthenticationChallenge(challenge); } 
        virtual void didCancelAuthenticationChallenge(ResourceHandle*, const AuthenticationChallenge& challenge) { didCancelAuthenticationChallenge(challenge); } 
        virtual void receivedCancellation(ResourceHandle*, const AuthenticationChallenge& challenge) { receivedCancellation(challenge); }
        virtual void willCacheResponse(ResourceHandle*, CacheStoragePolicy&);

        const KURL& url() const { return m_request.url(); } 
        ResourceHandle* handle() const { return m_handle.get(); }
        bool sendResourceLoadCallbacks() const { return m_sendResourceLoadCallbacks; }

        bool reachedTerminalState() const { return m_reachedTerminalState; }

        void setShouldBufferData(bool shouldBufferData);

    protected:
        ResourceLoader(Frame*, bool sendResourceLoadCallbacks, bool shouldContentSniff);

        // start() actually sends the load to the network (unless the load is being 
        // deferred) and should only be called by ResourceLoadScheduler or setDefersLoading().
        void start();
        
        virtual void didCancel(const ResourceError&);
        void didFinishLoadingOnePart(double finishTime);

        const ResourceRequest& request() const { return m_request; }
        bool cancelled() const { return m_cancelled; }
        bool defersLoading() const { return m_defersLoading; }

        RefPtr<ResourceHandle> m_handle;
        RefPtr<Frame> m_frame;
        RefPtr<DocumentLoader> m_documentLoader;
        ResourceResponse m_response;
        
    private:
        ResourceRequest m_request;
        RefPtr<SharedBuffer> m_resourceData;
        
        unsigned long m_identifier;

        bool m_reachedTerminalState;
        bool m_cancelled;
        bool m_calledDidFinishLoad;

        bool m_sendResourceLoadCallbacks;
        bool m_shouldContentSniff;
        bool m_shouldBufferData;
        bool m_defersLoading;
        ResourceRequest m_deferredRequest;
    };

}

#endif
