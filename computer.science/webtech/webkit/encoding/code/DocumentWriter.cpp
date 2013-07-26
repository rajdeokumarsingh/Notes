

DocumentWriter::DocumentWriter(Frame* frame)
    : m_frame(frame)
    , m_receivedData(false)
    , m_encodingWasChosenByUser(false) // 用户强制指定encoding


void DocumentWriter::begin()
    begin(KURL());



PassRefPtr<Document> DocumentWriter::createDocument(const KURL& url)
    // 创建plugin document
    if (!m_frame->loader()->stateMachine()->isDisplayingInitialEmptyDocument() && 
            m_frame->loader()->client()->shouldUsePluginDocument(m_mimeType))
        return PluginDocument::create(m_frame, url);

    // 如果没有html view，创建PlaceholderDocument
    if (!m_frame->loader()->client()->hasHTMLView())
        return PlaceholderDocument::create(m_frame, url);

    // 创建文档
    return DOMImplementation::createDocument(
            m_mimeType, m_frame, url, m_frame->inViewSourceMode());


void DocumentWriter::begin(const KURL& url, bool dispatch, SecurityOrigin* origin)
    RefPtr<Document> document = createDocument(url);

    clear();

    m_frame->loader()->setOutgoingReferrer(url);
    // 将文档设置到frame中
    m_frame->setDocument(document);

    // 设置文档的decoder
    if (m_decoder)
        document->setDecoder(m_decoder.get());

    // 设置dom的相关信息
    m_frame->domWindow()->setURL(document->url());
    m_frame->domWindow()->setSecurityOrigin(document->securityOrigin());

    m_frame->loader()->didBeginDocument(dispatch);


// 创建解码器
TextResourceDecoder* DocumentWriter::createDecoderIfNeeded()

    if (!m_decoder) // 还没有创建解码器

        if (Settings* settings = m_frame->settings())
            // 创建decoder
            m_decoder = TextResourceDecoder::create(m_mimeType,
                    settings->defaultTextEncodingName(),
                    settings->usesEncodingDetector());
            if (canReferToParentFrameEncoding(m_frame, parentFrame))
               m_decoder->setHintEncoding(parentFrame->document()->decoder());

        else
            m_decoder = TextResourceDecoder::create(m_mimeType, String());

        // 设置decoder的encoding (parent frame || user chosen || http header)
        if (m_encoding.isEmpty())
            if (canReferToParentFrameEncoding(m_frame, parentFrame))
                m_decoder->setEncoding(parentFrame->document()->inputEncoding(),
                        TextResourceDecoder::EncodingFromParentFrame);
        else
            m_decoder->setEncoding(m_encoding,
                    m_encodingWasChosenByUser ?
                    TextResourceDecoder::UserChosenEncoding :
                    TextResourceDecoder::EncodingFromHTTPHeader);

        m_frame->document()->setDecoder(m_decoder.get());
    return m_decoder.get();


// 向文档中添加数据
void DocumentWriter::addData(const char* str, int len, bool flush)
    if (len == -1)
        len = strlen(str);

    DocumentParser* parser = m_frame->document()->parser();
    if (parser)
        parser->appendBytes(this, str, len, flush);


void DocumentWriter::end()
    m_frame->loader()->didEndDocument();
    endIfNotLoadingMainResource();


void DocumentWriter::endIfNotLoadingMainResource()
    if (m_frame->loader()->isLoadingMainResource() || !m_frame->page() || !m_frame->document())
        return;

    // make sure nothing's left in there
    addData(0, 0, true);
    m_frame->document()->finishParsing();


String DocumentWriter::encoding() const
    // user chosen的优先级最高
    if (m_encodingWasChosenByUser && !m_encoding.isEmpty())
        return m_encoding;

    // 其次检查m_decoder
    if (m_decoder && m_decoder->encoding().isValid())
        String ename = m_decoder->encoding().name();

        return m_decoder->encoding().name();

    // 最后使用setting中的encoding
    Settings* settings = m_frame->settings();
    return settings ? settings->defaultTextEncodingName() : String();

// 设置encoding
void DocumentWriter::setEncoding(const String& name, bool userChosen)
    m_frame->loader()->willSetEncoding();
    m_encoding = name;
    m_encodingWasChosenByUser = userChosen;

void DocumentWriter::setDecoder(TextResourceDecoder* decoder)
    m_decoder = decoder;

