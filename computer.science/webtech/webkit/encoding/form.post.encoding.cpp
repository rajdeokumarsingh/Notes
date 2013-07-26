////////////////////////////////////////////////////////////////////////////////
// save data to form , encoding

PassRefPtr<FormData> HTMLFormElement::createFormData(const CString& boundary)
    TextEncoding encoding = dataEncoding().encodingForFormSubmission();
        return m_formDataBuilder.dataEncoding(document())
        // #anchor_1

     if (!control->disabled() && control->appendFormData(list, m_formDataBuilder.isMultiPartForm())) {
            |
            v
bool HTMLInputElement::appendFormData(FormDataList& encoding, bool multipart)
    encoding.appendData(name(), value());
            |
            V
        void FormData::appendData(const void* data, size_t size)
            if (m_elements.isEmpty() || m_elements.last().m_type != FormDataElement::data)
                m_elements.append(FormDataElement());
            FormDataElement& e = m_elements.last();
            size_t oldSize = e.m_data.size();
            e.m_data.grow(oldSize + size);
            memcpy(e.m_data.data() + oldSize, data, size);


// #anchor_1
WebCore/platform/network/FormDataBuilder.cpp
    TextEncoding FormDataBuilder::dataEncoding(Document* document) const
        // get encoding from accept-charset in from element

    if (Frame* frame = document->frame())
        return frame->loader()->encoding();
            |
            V
String FrameLoader::encoding() const
    if (m_encodingWasChosenByUser && !m_encoding.isEmpty())
        return m_encoding;
    if (m_decoder && m_decoder->encoding().isValid())
        return m_decoder->encoding().name();
    Settings* settings = m_frame->settings();
    return settings ? settings->defaultTextEncodingName() : String();

// send form data to network
WebFrame::startLoadingResource
    jPostDataStr = env->NewByteArray(size);
    jbyte* bytes = env->GetByteArrayElements(jPostDataStr, NULL);
    int offset = 0;
    for (size_t i = 0; i < n; ++i) {
        const WebCore::FormDataElement& e = elements[i];
        if (e.m_type == WebCore::FormDataElement::data)
            int delta = e.m_data.size();
            memcpy(bytes + offset, e.m_data.data(), delta);
            offset += delta;

    jobject jLoadListener =
        env->CallObjectMethod(obj.get(), mJavaFrame->mStartLoadingResource,
                (int)loader, jUrlStr, jMethodStr, jHeaderMap,
                jPostDataStr, formdata ? formdata->identifier(): 0,
                cacheMode, mainResource, request.getUserGesture(),
                synchronous, jUsernameString, jPasswordString);
                    |
                    V JNI to JAVA
private LoadListener startLoadingResource
    FrameLoader loader = new FrameLoader(loadListener, mSettings, method);
    loader.setHeaders(headers);
    loader.setPostData(postData);




