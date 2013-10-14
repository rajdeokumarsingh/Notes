1. implement ResourceLoader
2. reference to DocumentLoader
    set request to DocumentLoader
    set response to DocumentLoader
    send data to DocumentLoader

3. invoke content policy logic
    implemented by FrameLoaderClientAndroid
        decide whether show or download 

4. handle defer loading
5. handle substitute data loading
6. handle redirect response
    update redirect information to DocumentLoadTiming

7. handle empty load
8. handle data load

9. has a data loading timer to handle defer load substitude data
10. has a flag to indicate loading multi-part content
11. has a m_timeOfLastDataReceived to remember last time when receiving network data
    which is set to DocumentLoadTiming in DocumentLoader
12. inform FrameLoader if error happens 
    by receivedMainResourceError()

