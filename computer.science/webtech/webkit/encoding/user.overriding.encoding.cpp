
////////////////////////////////////////////////////////////////////////////////
// encoding overriding by user
////////////////////////////////////////////////////////////////////////////////

// oms
void FrameLoader::setEncoding(const String& name, bool userChosen)
{
    __android_log_print(5, "OMS:", "FrameLoader::setEncoding, name=%s\n", name.latin1().data());
    if (!m_workingURL.isEmpty())
        receivedFirstData();
    if (m_encodingWasChosenByUser) {
        return;
    }

    m_encoding = name;
    m_encodingWasChosenByUser = userChosen;
}

void FrameLoader::setEncodingByUser(const String& name, bool userChosen)
{
    m_encoding = name;
    m_encodingWasChosenByUser = userChosen;
}

TextResourceDecoder.cpp

////////////////////////////////////////////////////////////////////////////////
// set charset from meta element
////////////////////////////////////////////////////////////////////////////////
void FrameLoader::write(const char* str, int len, bool flush)
    if (!m_decoder)
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
                m_decoder->setEncoding(parentFrame->document()->inputEncoding(), TextResourceDecoder::EncodingFromParentFrame);              
        } else {
            m_decoder->setEncoding(m_encoding,
                    m_encodingWasChosenByUser ? TextResourceDecoder::UserChosenEncoding : TextResourceDecoder::EncodingFromHTTPHeader);
        }
        m_frame->document()->setDecoder(m_decoder.get());

    String decoded = m_decoder->decode(str, len);

String TextResourceDecoder::decode(const char* data, size_t len)
    if ((m_contentType == HTML || m_contentType == XML) && !m_checkedForHeadCharset) // HTML and XML
        if (!checkForHeadCharset(data, len, movedDataToBuffer))
            return "";

bool TextResourceDecoder::checkForHeadCharset(const char* data, size_t len, bool& movedDataToBuffer)
    setEncoding(findTextEncoding(str + pos, end - pos), EncodingFromMetaTag);
    if (m_source == EncodingFromMetaTag)
        return true;

void TextResourceDecoder::setEncoding(const TextEncoding& encoding, EncodingSource source)
    // When encoding comes from meta tag (i.e. it cannot be XML files sent via XHR),
    // treat x-user-defined as windows-1252 (bug 18270)
    if (source == EncodingFromMetaTag && strcasecmp(encoding.name(), "x-user-defined") == 0)
        m_encoding = "windows-1252";
    else if (source == EncodingFromMetaTag || source == EncodingFromXMLHeader || source == EncodingFromCSSCharset)
        m_encoding = encoding.closestByteBasedEquivalent();
    else
        m_encoding = encoding;

////////////////////////////////////////////////////////////////////////////////
// user choose encoding by settings
////////////////////////////////////////////////////////////////////////////////

// JAVA framework layer
// Add a mUserTextEncoding flag in java framework layer
// If it is set, WebKit should override encoding from html
public class WebSettings 

    private boolean         mUserTextEncoding;

    WebSettings(Context context, WebView webview) 

        mDefaultTextEncoding = context.getString(com.android.internal.
                R.string.default_text_encoding);
        mUserTextEncoding = false;

    public synchronized void setUserTextEncoding(boolean b)
            mUserTextEncoding = b;
            postSync();

    public synchronized boolean getUserTextEncoding()
        return mUserTextEncoding;

// WebKit JNI layer
// Get user-selected encoding from java, then set to Settings in WebCore
WebKit/android/jni/WebSettings.cpp
    struct FieldIds {
    ...
        mUserTextEncoding = env->GetFieldID(clazz, "mUserTextEncoding", "Z");
    ...

        jfieldID mUserTextEncoding;
    }

    static void WebSettings::Sync(JNIEnv* env, jobject obj, jint frame)
        // get encoding setting from java layer
        jboolean userTextEncoding = env->GetBooleanField(
                obj, gFieldIds->mUserTextEncoding);
        s->setUserTextEncoding(userTextEncoding);
        if(!userTextEncoding && pFrame->loader() &&
                pFrame->loader()->documentLoader())
            pFrame->loader()->documentLoader()->
                setOverrideEncoding(WebCore::String());

// WebCore Settings
WebCore/page/Settings.h
    void setUserTextEncoding(bool b);
    bool getUserTextEncoding () const { return mUserTextEncoding; }
    bool mUserTextEncoding;

WebCore/page/Settings.cpp
    void Settings::setUserTextEncoding(bool b)
        mUserTextEncoding = b;

// overrideEncoding will override encoding from html
WebCore/loader/DocumentLoader.h
    const String& overrideEncoding() const { return m_overrideEncoding; }

WebCore/loader/DocumentLoader.h
    void DocumentLoader::setFrame(Frame* frame)
        ...
        // Set m_overrideEncoding to user-selected encoding
        // before document is loaded
        Settings* settings = m_frame->settings();
        if(settings && settings->getUserTextEncoding())
            m_overrideEncoding = settings->defaultTextEncodingName();


// get user-selected encoding
void FrameLoaderClientAndroid::committedLoad(DocumentLoader* loader, const char* data, int length) 
    String encoding = loader->overrideEncoding();
    bool userChosen = !encoding.isNull();
    if (encoding.isNull()) encoding = loader->response().textEncodingName();
    loader->frameLoader()->setEncoding(encoding, userChosen);

void FrameLoader::setEncoding(const String& name, bool userChosen)
    m_encoding = name;
    m_encodingWasChosenByUser = userChosen;

void FrameLoader::write(const char* str, int len, bool flush)
    if (!m_decoder)
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
                m_decoder->setEncoding(parentFrame->document()->inputEncoding(), TextResourceDecoder::EncodingFromParentFrame);              
        } else {
            m_decoder->setEncoding(m_encoding,
                    m_encodingWasChosenByUser ? TextResourceDecoder::UserChosenEncoding : TextResourceDecoder::EncodingFromHTTPHeader);
        }
        m_frame->document()->setDecoder(m_decoder.get());

String FrameLoader::encoding() const
    if (m_encodingWasChosenByUser && !m_encoding.isEmpty())
        return m_encoding;
    if (m_decoder && m_decoder->encoding().isValid())
        return m_decoder->encoding().name();
    Settings* settings = m_frame->settings();
    return settings ? settings->defaultTextEncodingName() : String();


// ################################################################################
// Encoding auto-detector
// ################################################################################

WebCore/loader/TextResourceDecoder.h|91| <<global>> bool m_usesEncodingDetector;                                                             
WebCore/page/Settings.cpp|118| <<global>> , m_usesEncodingDetector(false)
WebCore/page/Settings.cpp|696| <<global>> m_usesEncodingDetector = usesEncodingDetector;
WebCore/page/Settings.h|179| <<global>> bool usesEncodingDetector() const { return m_usesEncodingDetector; }
WebCore/page/Settings.h|464| <<global>> bool m_usesEncodingDetector : 1;
WebCore/loader/TextResourceDecoder.cpp|336| <<TextResourceDecoder>> , m_usesEncodingDetector(usesEncodingDetector)
WebCore/loader/TextResourceDecoder.cpp|781| <<shouldAutoDetect>> return m_usesEncodingDetector






