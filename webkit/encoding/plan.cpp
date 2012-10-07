
DocumentWriter
    // NEW:
    DocumentWriter::createDecoderAnyway()
        无论如何都创建一个decoder

    // NEW:
    bool DocumentWriter::needSpecialDecoding()
        // url匹配http://www.sc.10086.cn/service/index.jsp


DecodedDataDocumentParser
    // NEW:
    char * mDecBuffer;
    char * mDecBufferLen;

    // NEW:
    void appendDecBuffer(const char * data, int length) 
    {
        if(data == NULL || length <= 0)
            return;

        char * tmp = (char *) malloc(mDecBufferLen+length);
        memset(tmp, 0, mDecBufferLen+length);
        if(mDecBuffer != NULL && mDecBufferLen !=0)
        {
            memcpy(tmp, mDecBuffer, mDecBufferLen);
            free(mDecBuffer);
        }
        memcpy(tmp+mDecBufferLen, data, length);

        mDecBuffer = tmp;
        mDecBufferLen += length;
    }

    void freeDecBuffer() 
    {
        if(mDecBuffer != NULL && mDecBufferLen !=0)
        {
            free(mDecBuffer);
            mDecBuffer = NULL;
            mDecBufferLen = 0;
        }
    }

    // MODIFY:
    DecodedDataDocumentParser::DecodedDataDocumentParser(Document* document)
    : DocumentParser(document)
    : mDecBuffer(NULL)
    : mDecBufferLen(0)

    // MODIFY:
    void DecodedDataDocumentParser::appendBytes(DocumentWriter* writer , const char* data, int length, bool shouldFlush)

        if(writer->needSpecialDecoding()) {
            if(!shouldFlush) {
                appendDecBuffer(data, length);
            } else {
                // 
            }
        }

        // 创建decoder
        TextResourceDecoder* decoder = writer->createDecoderIfNeeded();
        String decoded = decoder->decode(data, length);
        if (shouldFlush)
            decoded += decoder->flush();

        if (decoded.isEmpty())
            return;

        writer->reportDataReceived();

        append(decoded);

