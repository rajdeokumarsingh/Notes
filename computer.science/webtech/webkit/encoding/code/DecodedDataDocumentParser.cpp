
WebCore/dom/DecodedDataDocumentParser.cpp


DecodedDataDocumentParser::DecodedDataDocumentParser(Document* document)
: DocumentParser(document)


void DecodedDataDocumentParser::appendBytes(DocumentWriter* writer , const char* data, int length, bool shouldFlush)

    // 创建decoder
    TextResourceDecoder* decoder = writer->createDecoderIfNeeded();
    String decoded = decoder->decode(data, length);
    if (shouldFlush)
        decoded += decoder->flush();

    if (decoded.isEmpty())
        return;

    writer->reportDataReceived();

    append(decoded);

