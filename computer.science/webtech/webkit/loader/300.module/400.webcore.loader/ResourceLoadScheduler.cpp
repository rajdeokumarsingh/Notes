1. has a map of HostInformation for http protocol
    The key is name of the host
    The value is HostInformation pointer

    HostInformation 
        has the name of the request host, like "www.sina.com", "192.168.10.82"
        has a array of queue which stores pending ResourceLoaders
            The index of the array is the priority of the queue
                enum ResourceLoadPriority {
                    ResourceLoadPriorityUnresolved = -1,
                    ResourceLoadPriorityVeryLow = 0,
                    ResourceLoadPriorityLow,
                    ResourceLoadPriorityMedium,
                    ResourceLoadPriorityHigh,
                    ResourceLoadPriorityLowest = ResourceLoadPriorityVeryLow,
                    ResourceLoadPriorityHighest = ResourceLoadPriorityHigh,
                };

            The element of queue is ResouceLoader

        has a hashmap, which store in progress ResourceLoaders

2. has one HostInformation for non http protocol

3. When a main resource loader begin, MainResourceLoader will loading itself
    then add itself to ResourceLoadScheduler by addMainResourceLoad()
        only add the ResourceLoader to the in progress map

4. ResourceLoadScheduler::scheduleSubresourceLoad
    1. create a SubresourceLoader
        with params Frame, SubresourceLoaderClient, ResourceRequest, ResourceLoadPriority, ...

    2. schedule the loader with its priority
        add the loader to its host's priority queue

    3. then trigger the timer

5. when crossOriginRedirectReceived, which means the origin request move to other domain address
    move the loader from origin HostInformation to a new HostInformation for the new address

6. suspendPendingRequests(), resumePendingRequests()
    use to suspend/resume serving pending request

7. when a pending loader become active
    it is added to the in progress hashmap
    it is removed from the pending queue

    invoke start() of the loader
        which will create an io thread

8. the maximium number of io thread is limited by chromium network library

9. has a timer


