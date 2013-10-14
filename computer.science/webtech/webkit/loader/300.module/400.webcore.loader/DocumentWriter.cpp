
. create document by
    return DOMImplementation::createDocument(m_mimeType, m_frame, url, m_frame->inViewSourceMode());

. when DocumentWriter begin()
    {
        m_frame->loader()->setOutgoingReferrer(url);
        m_frame->setDocument(document);

        if (m_decoder)
            document->setDecoder(m_decoder.get());
        if (forcedSecurityOrigin)
            document->setSecurityOrigin(forcedSecurityOrigin.get());

        m_frame->domWindow()->setURL(document->url());
        m_frame->domWindow()->setSecurityOrigin(document->securityOrigin());

        m_frame->loader()->didBeginDocument(dispatch);

        document->implicitOpen();

        if (m_frame->view() && m_frame->loader()->client()->hasHTMLView())
            m_frame->view()->setContentsSize(IntSize());

    }

. create text decoder for the document

. when receive data
    add data to DocumentParser of the Document







