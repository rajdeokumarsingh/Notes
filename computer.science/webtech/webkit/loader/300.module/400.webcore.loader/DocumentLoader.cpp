include
    a MainResourceLoader
        create the MainResourceLoader
        trigger start, cancel of the main loader

    SubresourceLoaders
        send cancel to all sub loaders
    
. manager SubstituteResources with a map
    typedef HashMap<RefPtr<ResourceLoader>, RefPtr<SubstituteResource> > SubstituteResourceMap;
    SubstituteResourceMap m_pendingSubstituteResources;

    trigger 
        didReceiveResponse, didReceiveData, didFinishLoading

. when receivedData()
    get encoding from response or overrideEncoding
    set encoding to DocumentWriter
    append data to DocumentWriter

. when stop loading (stopLoading)
    inform FrameLoader to stopLoading()
    cancel all multipart subresource loaders
    inform MainResourceLoader to cancel()

    set error status
        setMainDocumentError()

    stopLoadingSubresources();
    stopLoadingPlugIns();
             
. when finishedLoading
    inform frame loader 
        commitProvisionalLoad()
        finishedLoadingDocument()
    
    inform DocumentWriter end()

. trigger defer loading
    inform MainResourceLoader to setDefersLoading
    inform all SubresourceLoader to setDefersLoading

. if title changed
    informa FrameLoader 
        willChangeTitle() 
        didChangeTitle()


. has a buffer for main resource data

. has origin resource request and current resource request
    http redirect?

. send status to FrameLoader
    redirect
        didReceiveServerRedirectForProvisionalLoadForFrame
    
    main document error
        setMainDocumentError
        mainReceivedCompleteError

. has achiveResources for cache?
    OwnPtr<ArchiveResourceCollection> m_archiveResourceCollection;

    DocumentLoader::mainResource() 
        encapsulate response and data buffer of MainResource in an ArchiveResource

    DocumentLoader::subresource()
        encapsulate response and data buffer of CachedResource in an ArchiveResource
