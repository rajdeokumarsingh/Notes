KURL->c_str()

    KURL url = m_frame->document()->url();
    android_printLog(ANDROID_LOG_ERROR, LOG_TAG, "%s", 
            url.string().latin1().data() ? url.string().latin1().data() : "");

frame->url
m_frame->document()->url(); 
