WebCore是如何创建一个select节点的

html element是什么：
    HTMLOptionElement : public HTMLFormControlElement : public HTMLElement : 
        public StyledElement : public Element : public ContainerNode : public Node 
            Node : public EventTarget, public TreeShared<ContainerNode>, public ScriptWrappable

好像始终找不到HTMLOptionElement::create()
最后在下面文件夹中找到:
    android/out/target/product/S899t/
        obj/STATIC_LIBRARIES/libwebcore_intermediates/Source/WebCore/

WebCore/dom/DOMImplementation.cpp
WebCore/dom/ContainerNode.cpp
WebCore/html/parser/HTMLConstructionSite.cpp
WebCore/html/parser/HTMLTreeBuilder.cpp

WebCore/html/parser/HTMLTreeBuilder.cpp
if (token.name() == selectTag) {
    m_tree.reconstructTheActiveFormattingElements();
    m_tree.insertHTMLElement(token);
    m_framesetOk = false;
    if (m_insertionMode == InTableMode
            || m_insertionMode == InCaptionMode
            || m_insertionMode == InColumnGroupMode
            || m_insertionMode == InTableBodyMode
            || m_insertionMode == InRowMode
            || m_insertionMode == InCellMode)
        setInsertionMode(InSelectInTableMode);
    else
        setInsertionMode(InSelectMode);
    return;
            |
            V
void HTMLConstructionSite::insertHTMLElement(AtomicHTMLToken& token)
    m_openElements.push(attachToCurrent(createHTMLElement(token)));
                |
                V
PassRefPtr<Element> HTMLConstructionSite::createHTMLElement(AtomicHTMLToken& token)
    QualifiedName tagName(nullAtom, token.name(), xhtmlNamespaceURI);
    // FIXME: This can't use HTMLConstructionSite::createElement because we
    // have to pass the current form element.  We should rework form association
    // to occur after construction to allow better code sharing here.
    RefPtr<Element> element = HTMLElementFactory::createHTMLElement(tagName, currentNode()->document(), form(), true);
    element->setAttributeMap(token.takeAtributes(), m_fragmentScriptingPermission);
    return element.release();
                    |
                    |  !!!请注意, HTMLElementFactory.cpp是被脚本文件创建出来的, 在下面目录
                    |  android/out/target/product/S899t/
                    |       obj/STATIC_LIBRARIES/libwebcore_intermediates/Source/WebCore/
                    |
                    V
PassRefPtr<HTMLElement> HTMLElementFactory::createHTMLElement(const QualifiedName& qName, 
        Document* document, HTMLFormElement* formElement, bool createdByParser)
    if (ConstructorFunction function = gFunctionMap->get(qName.localName().impl()))
        return function(qName, document, formElement, createdByParser); 
                |
                V
static PassRefPtr<HTMLElement> selectConstructor(const QualifiedName& tagName, 
        Document* document, HTMLFormElement* formElement, bool)
    return HTMLSelectElement::create(tagName, document, formElement);

