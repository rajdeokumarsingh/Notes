http-equiv 属性
    设置 <meta> 标签的内容是否捆绑到一个 HTTP 响应头
    使用带有 http-equiv 属性的<meta>标签时，服务器将把名称/值对添加到发送给浏览器的内容头部。
    例如，添加：
        <meta http-equiv="charset" content="iso-8859-1">
        <meta http-equiv="expires" content="31 Dec 2008">

        这样发送到浏览器的头部就应该包含：
        content-type: text/html
        charset:iso-8859-1
        expires:31 Dec 2007

WebCore/dom/Document.cpp|2345| <<global>> void Document::processHttpEquiv(const String& equiv, const String& content)
WebCore/dom/Document.h|701| <<global>> void processHttpEquiv(const String& equiv, const String& content);
WebCore/html/HTMLMetaElement.cpp|98| <<global>> document()->processHttpEquiv(m_equiv, m_content); 
