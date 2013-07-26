namespace WebCore {

    class MainResourceLoader : public ResourceLoader {
    public:
        static PassRefPtr<MainResourceLoader> create(Frame*);
        virtual ~MainResourceLoader();

        virtual bool load(const ResourceRequest&, const SubstituteData&);
        virtual void addData(const char*, int, bool allAtOnce);

        virtual void setDefersLoading(bool);

        virtual void willSendRequest(ResourceRequest&, const ResourceResponse& redirectResponse);
        virtual void didReceiveResponse(const ResourceResponse&);
        virtual void didReceiveData(const char*, int, long long encodedDataLength, bool allAtOnce);
        virtual void didFinishLoading(double finishTime);
        virtual void didFail(const ResourceError&);

        typedef Timer<MainResourceLoader> MainResourceLoaderTimer;

        void handleDataLoadNow(MainResourceLoaderTimer*);

        bool isLoadingMultipartContent() const { return m_loadingMultipartContent; }

    private:
        MainResourceLoader(Frame*);

        virtual void didCancel(const ResourceError&);

        bool loadNow(ResourceRequest&);

        void handleEmptyLoad(const KURL&, bool forURLScheme);
        void handleDataLoadSoon(const ResourceRequest& r);

        void startDataLoadTimer();
        void handleDataLoad(ResourceRequest&);

        void receivedError(const ResourceError&);
        ResourceError interruptionForPolicyChangeError() const;
        void stopLoadingForPolicyChange();
        bool isPostOrRedirectAfterPost(const ResourceRequest& newRequest, const ResourceResponse& redirectResponse);

        // 
        static void callContinueAfterNavigationPolicy(void*, const ResourceRequest&, PassRefPtr<FormState>, bool shouldContinue);
        void continueAfterNavigationPolicy(const ResourceRequest&, bool shouldContinue);

        static void callContinueAfterContentPolicy(void*, PolicyAction);
        void continueAfterContentPolicy(PolicyAction);
        void continueAfterContentPolicy(PolicyAction, const ResourceResponse&);
        
#if PLATFORM(QT)
        void substituteMIMETypeFromPluginDatabase(const ResourceResponse&);
#endif

        ResourceRequest m_initialRequest;
        SubstituteData m_substituteData;

        MainResourceLoaderTimer m_dataLoadTimer;

        bool m_loadingMultipartContent;
        bool m_waitingForContentPolicy;
        double m_timeOfLastDataReceived;
    };

}

#endif
