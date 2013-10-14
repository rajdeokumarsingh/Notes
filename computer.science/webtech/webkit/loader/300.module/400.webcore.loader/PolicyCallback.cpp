encapsulate policy callback

    NavigationPolicyDecisionFunction m_navigationFunction;
    NewWindowPolicyDecisionFunction m_newWindowFunction;
    ContentPolicyDecisionFunction m_contentFunction;

    NavigationAction m_navigationAction;
    ResourceRequest m_request;
    RefPtr<FormState> m_formState;
    String m_frameName;

    void* m_argument;

