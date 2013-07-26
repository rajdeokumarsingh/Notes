
// 内部的content type只有四种css, html, xml, text
TextResourceDecoder::ContentType TextResourceDecoder::determineContentType(const String& mimeType)
    if (equalIgnoringCase(mimeType, "text/css"))
        return CSS;
    if (equalIgnoringCase(mimeType, "text/html"))
        return HTML;
    if (DOMImplementation::isXMLMIMEType(mimeType))                                                                                                          
        return XML;
    return PlainText;

TextResourceDecoder::TextResourceDecoder(const String& mimeType, const TextEncoding& specifiedDefaultEncoding, bool usesEncodingDetector)
    : m_contentType(determineContentType(mimeType))                                                                                                          
    , m_encoding(defaultEncoding(m_contentType, specifiedDefaultEncoding))
    , m_source(DefaultEncoding)
    , m_hintEncoding(0)
    , m_checkedForBOM(false)            // 是否从bom检测到了encoding
    , m_checkedForCSSCharset(false)     // 是否从css meta中检测到了encoding
    , m_checkedForHeadCharset(false)    // 是否从html meta中检测到了encoding
    , m_useLenientXMLDecoding(false)    // 是否从xml meta中检测到了encoding
    , m_sawError(false)
    , m_usesEncodingDetector(usesEncodingDetector)

/* 设置encoding和encoding的source

   enum EncodingSource
       DefaultEncoding,
       AutoDetectedEncoding,
       EncodingFromXMLHeader,
       EncodingFromMetaTag,
       EncodingFromCSSCharset,
       EncodingFromHTTPHeader,
       UserChosenEncoding,
       EncodingFromParentFrame
*/
void TextResourceDecoder::setEncoding(
        const TextEncoding& encoding, EncodingSource source)
        m_encoding = encoding;
         m_source = source;


bool TextResourceDecoder::checkForCSSCharset(const char* data, size_t len, bool& movedDataToBuffer)

    /* 一个页面(html, xml, css, text)会分成多次被送入到TextResourceDecoder进行解码。
        如果source是下面的值:
            AutoDetectedEncoding, EncodingFromXMLHeader, EncodingFromMetaTag,
            EncodingFromCSSCharset, UserChosenEncoding
        则说明前面已经完成了编码检测。则本次不用进行检测了。
    */
    if (m_source != DefaultEncoding
            && m_source != EncodingFromParentFrame
            && m_source != EncodingFromHTTPHeader) 
        m_checkedForCSSCharset = true;
        return true;

    // 将新的数据添加到buffer中
    size_t oldSize = m_buffer.size();
    m_buffer.grow(oldSize + len);
    memcpy(m_buffer.data() + oldSize, data, len);

    movedDataToBuffer = true;

    // 检查css中的encoding字段"@charset"
    if (m_buffer.size() > 8) { // strlen("@charset") == 8
        // 如果找到
            setEncoding(findTextEncoding(dataStart, encodingNameLength), EncodingFromCSSCharset);

        // 表示已经检查过了，但不一定找到
        m_checkedForCSSCharset = true;
        return true;
    }

    // 表示还没有进行检查
    return false;


bool TextResourceDecoder::checkForHeadCharset(const char* data, size_t len, bool& movedDataToBuffer)
    /* 一个页面(html, xml, css, text)会分成多次被送入到TextResourceDecoder进行解码。
        如果source是下面的值:
            AutoDetectedEncoding, EncodingFromXMLHeader, EncodingFromMetaTag,
            EncodingFromCSSCharset, UserChosenEncoding
        则说明前面已经完成了编码检测。则本次不用进行检测了。
    */
    if (m_source != DefaultEncoding
            && m_source != EncodingFromParentFrame
            && m_source != EncodingFromHTTPHeader) 
        m_checkedForCSSCharset = true;
        return true;


    // This is not completely efficient, since the function might go
    // through the HTML head several times.

    // 将新的数据添加到buffer中
    size_t oldSize = m_buffer.size();
    m_buffer.grow(oldSize + len);
    memcpy(m_buffer.data() + oldSize, data, len);

    movedDataToBuffer = true;

    // 如果上一次已经创建了charset检测器，直接使用
    // Continue with checking for an HTML meta tag if we were already doing so.
    if (m_charsetParser)
        return checkForMetaCharset(data, len);

    // 从xml声明中查找charset声明, 如果找到
       setEncoding(findTextEncoding(ptr + pos, len), EncodingFromXMLHeader); 
       setEncoding(UTF16LittleEndianEncoding(), AutoDetectedEncoding);
       ...
       return true;

   // 查找html meta中的charset
   m_charsetParser = HTMLMetaCharsetParser::create();
   return checkForMetaCharset(data, len); {
        // 如果没有找到
       if (!m_charsetParser->checkForMetaCharset(data, length))
           return false;

        // 找到了
       setEncoding(m_charsetParser->encoding(), EncodingFromMetaTag);
       m_charsetParser.clear();
       m_checkedForHeadCharset = true;
       return true;
   }

void TextResourceDecoder::setHintEncoding(
        const TextResourceDecoder* hintDecoder)
    // hintEncoding is for use with autodetection, which should be 
    // only invoked when hintEncoding comes from auto-detection.

    // if (hintDecoder && hintDecoder->m_source == AutoDetectedEncoding)
    if (hintDecoder) {
        m_hintEncoding = hintDecoder->encoding().name();

// We use the encoding detector in two cases:
//   1. Encoding detector is turned ON and no other encoding source is
//      available (that is, it's DefaultEncoding).
//   2. Encoding detector is turned ON and the encoding is set to
//      the encoding of the parent frame, which is also auto-detected.
//   Note that condition #2 is NOT satisfied unless parent-child frame
//   relationship is compliant to the same-origin policy. If they're from
//   different domains, |m_source| would not be set to EncodingFromParentFrame
//   in the first place. 
bool TextResourceDecoder::shouldAutoDetect() const

    // The DefaultEncoding, EncodingFromParentFrame, EncodingFromHTTPHeader(CMCC WAP)
    // sources are not reliable, we'd better also try autodetection
    return m_usesEncodingDetector
        && (m_source == DefaultEncoding ||
        ((m_source == EncodingFromParentFrame ||
          m_source == EncodingFromHTTPHeader) && m_hintEncoding));


String TextResourceDecoder::decode(const char* data, size_t len)

    if (!m_checkedForBOM)
        lengthOfBOM = checkForBOM(data, len);

    bool movedDataToBuffer = false;

    // 如果文件类型是css, 而且还没有进行css charset检测
    if (m_contentType == CSS && !m_checkedForCSSCharset)
        if (!checkForCSSCharset(data, len, movedDataToBuffer))
            // 如果没有进行css charset检测， 就推迟到下一次进行
            // 注意这一次已经将data拷贝到m_buffer中了
            return "";

    // 如果文件类型是html, xml, 而且还没有进行charset检测
    if ((m_contentType == HTML || m_contentType == XML) && 
            !m_checkedForHeadCharset) 
        if (!checkForHeadCharset(data, len, movedDataToBuffer))
            // 如果没有进行charset检测， 就推迟到下一次进行
            // 注意这一次已经将data拷贝到m_buffer中了
            return "";

    // FIXME: It is wrong to change the encoding downstream 
    // after we have already done some decoding.
    if (shouldAutoDetect()) {
        if (m_encoding.isJapanese()) {
            detectJapaneseEncoding(data, len); 
        else 
            TextEncoding detectedEncoding;
            // FIXME: why using NULL for hint encoding in 2.2
            // if (detectTextEncoding(data, len, NULL, &detectedEncoding)) {
            if (detectTextEncoding(data, len, m_hintEncoding, &detectedEncoding))
                setEncoding(detectedEncoding, AutoDetectedEncoding);


    // 创建codec
    if (!m_codec)
        m_codec = newTextCodec(m_encoding);

    // 返回解码后的文字
    if (m_buffer.isEmpty())
        return m_codec->decode(data + lengthOfBOM, len - lengthOfBOM, false, m_contentType == XML, m_sawError);


    String result = m_codec->decode(m_buffer.data() + lengthOfBOM, m_buffer.size() - lengthOfBOM, false, m_contentType == XML && !m_useLenientXMLDecoding, m_sawError);
    m_buffer.clear();
    return result;


String TextResourceDecoder::flush()
    // If we can not identify the encoding even after a document is completely
    // loaded, we need to detect the encoding if other conditions for
    // autodetection is satisfied.
    if (m_buffer.size() && shouldAutoDetect() && 
                ((!m_checkedForHeadCharset && (m_contentType == HTML || m_contentType == XML)) || 
                (!m_checkedForCSSCharset && (m_contentType == CSS)))) {
        TextEncoding detectedEncoding;
        if (detectTextEncoding(m_buffer.data(), m_buffer.size(),
                    m_hintEncoding, &detectedEncoding))
            setEncoding(detectedEncoding, AutoDetectedEncoding);

    if (!m_codec)
        m_codec = newTextCodec(m_encoding);

    String result = m_codec->decode(m_buffer.data(), m_buffer.size(), true, m_contentType == XML && !m_useLenientXMLDecoding, m_sawError);
    m_buffer.clear();
    m_codec.clear();
    m_checkedForBOM = false; // Skip BOM again when re-decoding.
    return result;




