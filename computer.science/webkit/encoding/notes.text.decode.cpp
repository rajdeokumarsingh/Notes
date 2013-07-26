// BOM 是 Byte Order Mark 的缩写
// 标识编码的标准标记
    // which of UTF-16 is  U+FEFF
    // which of UTF-8 is "EF BB BF"(string order)

/* 
http://en.wikipedia.org/wiki/Unicode
    Unicode 
        computing industry standard for the consistent encoding, representation and handling of text expressed

        can be implemented by different character encodings
            UTF-8 (which uses one byte for any ASCII characters, which have the same code values in both UTF-8 and ASCII encoding, 
                and up to four bytes for other characters)
            UTF-16

        Architecture and terminology
            defines a codespace of 1,114,112 code points in the range 0 hex to 10FFFF hex.[6] 
                24 bits
            a Unicode code point is referred to by writing "U+" followed by its hexadecimal number
                U+0058 for the character LATIN CAPITAL LETTER X

The Unicode codespace is divided into seventeen planes, numbered 0 to 16:
Unicode planes and code point (character) ranges
Basic   Supplementary
0000–FFFF   
Plane 0:
Basic Multilingual Plane    BMP     

10000–1FFFF     
Plane 1: Supplementary Multilingual Plane    SMP     

20000–2FFFF     
Plane 2:
Supplementary Ideographic Plane     SIP     

30000–DFFFF     
Planes 3–13:
Unassigned  

E0000–EFFFF     
Plane 14:
Supplementary Special-purpose Plane     SSP     

F0000–10FFFF
Planes 15–16:
Private Use Area    PUA

    All code points in the BMP are accessed as a single code unit in UTF-16 encoding 
        and can be encoded in one, two or three bytes in UTF-8. 

    Code points in Planes 1 through 16 (supplementary planes, or, informally, astral planes) are accessed as 
        surrogate pairs in UTF-16 and encoded in four bytes in UTF-8.

    Each code point has a single General Category property
        Letter, Mark, Number, Punctuation, Symbol, Separator and Other

    high-surrogate code points (also known as a leading surrogate)
        U+D800..U+DBFF (1,024 code points)
    low-surrogate code points (also known as a trailing surrogate)
        U+DC00..U+DFFF (1,024 code points) 

    A high-surrogate code point followed by a low-surrogate code point  together form a surrogate pair used in UTF-16 
        to represent 1,048,576 code points outside BMP.

    Thus the range of code points that are available for use as characters is 
        U+0000..U+D7FF and U+E000..U+10FFFF (1,112,064 code points)

        Certain noncharacter code points are guaranteed never to be used for encoding characters
        although applications may make use of these code points internally if they wish
        There are sixty-six noncharacters: U+FDD0..U+FDEF
            and any code point ending in the value FFFE or FFFF
            (i.e. U+FFFE, U+FFFF, U+1FFFE, U+1FFFF, ... U+10FFFE, U+10FFFF)

    Assigned characters
        Graphic characters, format characters, control code characters, and private use characters

    Mapping and encodings
        Unicode Transformation Format (UTF)
        Universal Character Set (UCS)

        An encoding maps (possibly a subset of) the range of Unicode code points to 
            sequences of values in some fixed-size range, termed code values.

        UTF encodings include:
        * UTF-1 – a retired predecessor of UTF-8, maximizes compatibility with ISO 2022, no longer part of The Unicode Standard
        * UTF-7 – a relatively unpopular 7-bit encoding, often considered obsolete (not part of The Unicode Standard but rather an RFC)

        * UTF-8 – an 8-bit variable-width encoding which maximizes compatibility with ASCII.
            one to four bytes per code point and, being compact for Latin scripts and ASCII-compatible

        * UTF-EBCDIC – an 8-bit variable-width encoding, which maximizes compatibility with EBCDIC. (not part of The Unicode Standard)

        * UTF-16 – a 16-bit, variable-width encoding
            specify the Unicode Byte Order Mark (BOM) for use at the beginnings of text files
            which may be used for byte ordering detection (or byte endianness detection).
            The BOM, code point U+FEFF has the important property of unambiguity on byte reorder
                if 1st byte is FF, little endian
                if 1st byte is FE, big endian

            For UTF-8
                c0 == 0xEF && c1 == 0xBB && c2 == 0xBF

        * UTF-32 – a 32-bit, fixed-width encoding

        GB18030 is another encoding form for Unicode
            from the Standardization Administration of China

        UTF-16 :internal character encoding
            Windows NT (and its descendants, Windows 2000, Windows XP, Windows Vista and Windows 7)
            Java .NET bytecode environments, Mac OS X, and KDE 

        UTF-8 
            main storage encoding on most Unix-like operating systems (though others are also used by some libraries) 
            because it is a relatively easy replacement for traditional extended ASCII character sets. 
            UTF-8 is also the most common Unicode encoding used in HTML documents on the World Wide Web.

    ICU - International Components for Unicode

字符集
    字符集是一个集合，描述并定义了这个集合中可以出现哪些字符，常见的字符有GB2312、GBK、GB18030、UNICODE等。
    字符集仅仅是一种规范，一种约定，我们也可以定义自己的字符集。

    不同的字符集之间很可能是有交集的，并且包含越多字符的字符集越通用。
        中国的BG2312，GBK，GB18030是在不同时期，逐步扩展而来的，所以GB18030是前者的超集。
        现今最大的字符集是UNICODE，几乎包含了世界上所有语言的字符。

字符编码
    先有字符集才有字符编码，编码是字符集的具体表示方式，
    一个字符集可以有多种编码方式，只要这种方式可以涵盖字符集中的所有字符。

    比如UNICODE字符集的具体编码方式有很多种，utf-8/utf-16/utf-32；
    而gb2312就只有常见的一种编码方式GB2312（所以有时候人们常将编码与字符集弄混）

四、Linux/unix平台
4.1 iconv
这些平台上，字符编码的转换一般使用iconv，可能是独立的iconv库也可能是glibc自带的版本
*/

WebCore/platform/text/

TextCodec
    // provide encode and decode interfaces
    // inherit by: 
    ./TextCodecUserDefined.h:33:    class TextCodecUserDefined : public TextCodec {
    ./mac/TextCodecMac.h:38:    class TextCodecMac : public TextCodec {
    ./TextCodecLatin1.h:33:    class TextCodecLatin1 : public TextCodec {
    ./TextCodecICU.h:39:    class TextCodecICU : public TextCodec {
    ./gtk/TextCodecGtk.h:38:    class TextCodecGtk : public TextCodec {
    ./TextCodecUTF16.h:33:    class TextCodecUTF16 : public TextCodec {
    ./qt/TextCodecQt.h:35:    class TextCodecQt : public TextCodec {

class TextCodecICU : public TextCodec

    void TextCodecICU::registerBaseEncodingNames(EncodingNameRegistrar registrar)
        registrar("UTF-8", "UTF-8");                                                                                                             
    void TextCodecICU::registerExtendedEncodingNames(EncodingNameRegistrar registrar)
        registrar("ISO-8859-8-I", "ISO-8859-8-I");
            | link lib
            V
        external/icu4c/common/ucnv.c


TextEncoding
    // encapsulate a string which the name of encoding, like GBK, UTF-8,...
    const char* m_name; 

    // provide encode and decode method
    // which uses TextCodec for encoding/decoding

TextResourceDecoder
    enum EncodingSource {
        DefaultEncoding,
        AutoDetectedEncoding, //from BOM
        EncodingFromXMLHeader,
        EncodingFromMetaTag,
        EncodingFromCSSCharset,
        EncodingFromHTTPHeader,
        UserChosenEncoding,     // Highest priority
        EncodingFromParentFrame
    };

    // different ways to get encoding for
    // different files
    ContentType m_contentType;

    TextEncoding m_encoding;
    EncodingSource m_source;
    OwnPtr<TextCodec> m_codec;

    const char* m_hintEncoding;

    Vector<char> m_buffer;

    // set if do the checking
    bool m_checkedForBOM;
    bool m_checkedForCSSCharset;
    bool m_checkedForHeadCharset;
    bool m_useLenientXMLDecoding; // Don't stop on XML decoding errors.
    bool m_sawError;
    bool m_usesEncodingDetector;

String TextResourceDecoder::decode(const char* data, size_t len)
    if (!m_checkedForBOM)
        // setEncoding from BOM if exist
        lengthOfBOM = checkForBOM(data, len);

    if (m_contentType == CSS && !m_checkedForCSSCharset)
        if (!checkForCSSCharset(data, len, movedDataToBuffer))
            return "";

    if ((m_contentType == HTML || m_contentType == XML) && !m_checkedForHeadCharset) // HTML and XML
        // check charset attribute in html header
        if (!checkForHeadCharset(data, len, movedDataToBuffer)) 
                ()->{
                // FIXME: Jiang Rui, what is EncodingFromParentFrame, from parent frame
                if (m_source != DefaultEncoding && m_source != EncodingFromParentFrame)
                    m_checkedForHeadCharset = true;
                    return true;
                }
            return "";

    // FIXME: It is wrong to change the encoding downstream after we have already done some decoding.
    if (shouldAutoDetect())
        TextEncoding detectedEncoding;
        if (detectTextEncoding(data, len, m_hintEncoding, &detectedEncoding))
            setEncoding(detectedEncoding, AutoDetectedEncoding);

    if (!m_codec)
        m_codec.set(newTextCodec(m_encoding).release());

    if (m_buffer.isEmpty())
        return m_codec->decode(data + lengthOfBOM, len - lengthOfBOM, 
                false, m_contentType == XML, m_sawError);

    if (!movedDataToBuffer)
        size_t oldSize = m_buffer.size();
        m_buffer.grow(oldSize + len);
        memcpy(m_buffer.data() + oldSize, data, len);

    String result = m_codec->decode(m_buffer.data() + lengthOfBOM, 
        m_buffer.size() - lengthOfBOM, false, m_contentType == XML 
        && !m_useLenientXMLDecoding, m_sawError);

    return result;


void FrameLoader::finishedLoadingDocument(DocumentLoader* loader)
    // set encoding choosed by user/from http header
    String userChosenEncoding = documentLoader()->overrideEncoding();
    bool encodingIsUserChosen = !userChosenEncoding.isNull();
    setEncoding(encodingIsUserChosen ? userChosenEncoding : mainResource->textEncoding(), encodingIsUserChosen);

    addData(mainResource->data()->data(), mainResource->data()->size());
        |
        V
// Decode the data from http protocol and send them to tokenizer
void FrameLoader::write(const char* str, int len, bool flush)
    if (!m_decoder) {
        if (Settings* settings = m_frame->settings()) {
            m_decoder = TextResourceDecoder::create(m_responseMIMEType,
                settings->defaultTextEncodingName(),
                settings->usesEncodingDetector());
            Frame* parentFrame = m_frame->tree()->parent();
            // Set the hint encoding to the parent frame encoding only if
            // the parent and the current frames share the security origin.
            // We impose this condition because somebody can make a child frame 
            // containing a carefully crafted html/javascript in one encoding
            // that can be mistaken for hintEncoding (or related encoding) by
            // an auto detector. When interpreted in the latter, it could be
            // an attack vector.
            // FIXME: This might be too cautious for non-7bit-encodings and
            // we may consider relaxing this later after testing.
            if (canReferToParentFrameEncoding(m_frame, parentFrame))
                m_decoder->setHintEncoding(parentFrame->document()->decoder());
        } else
            m_decoder = TextResourceDecoder::create(m_responseMIMEType, String());
        Frame* parentFrame = m_frame->tree()->parent();
        if (m_encoding.isEmpty()) {
            if (canReferToParentFrameEncoding(m_frame, parentFrame))
                m_decoder->setEncoding(parentFrame->document()->inputEncoding(), 
                        TextResourceDecoder::EncodingFromParentFrame);
        } else {
            m_decoder->setEncoding(m_encoding,
                m_encodingWasChosenByUser ? TextResourceDecoder::UserChosenEncoding : 
                TextResourceDecoder::EncodingFromHTTPHeader);
        }
        m_frame->document()->setDecoder(m_decoder.get());
    }

    String decoded = m_decoder->decode(str, len);
    if (flush)
        decoded += m_decoder->flush();

    tokenizer->write(decoded, true);

// get encoding from http header
void MainResourceLoader::didReceiveResponse(const ResourceResponse& r)
    m_documentLoader->setResponse(r);
        |
        V
PassRefPtr<ArchiveResource> DocumentLoader::mainResource() const
    const ResourceResponse& r = response(); // get which was set by setResponse()
    // get encoding from http response
    return ArchiveResource::create(mainResourceBuffer, r.url(), r.mimeType(), 
            r.textEncodingName(), frame()->tree()->name());
                |
                V
void FrameLoader::finishedLoadingDocument(DocumentLoader* loader)
    // set encoding choosed by user/from http header
    String userChosenEncoding = documentLoader()->overrideEncoding();
    bool encodingIsUserChosen = !userChosenEncoding.isNull();
    setEncoding(encodingIsUserChosen ? userChosenEncoding : mainResource->textEncoding(), encodingIsUserChosen);

    addData(mainResource->data()->data(), mainResource->data()->size());
 
