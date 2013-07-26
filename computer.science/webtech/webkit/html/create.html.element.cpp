
void HTMLConstructionSite::insertHTMLElement(AtomicHTMLToken& token)
    m_openElements.push(attachToCurrent(createHTMLElement(token)));
            |                               |
            V                               V
                        PassRefPtr<Element> HTMLConstructionSite::createHTMLElement(AtomicHTMLToken& token)
                            QualifiedName tagName(nullAtom, token.name(), xhtmlNamespaceURI);
                            // FIXME: HTMLElementFactory这个文件是使用脚本生成的中间文件
                            RefPtr<Element> element = HTMLElementFactory::createHTMLElement(tagName, currentNode()->document(), form(), true);
                            element->setAttributeMap(token.takeAtributes(), m_fragmentScriptingPermission);

PassRefPtr<Element> HTMLConstructionSite::attachToCurrent(PassRefPtr<Element> child)
    return attach(currentNode(), child);
                |
                V
PassRefPtr<ChildType> HTMLConstructionSite::attach(ContainerNode* rawParent, PassRefPtr<ChildType> prpChild)
    if (parent->attached() && !child->attached()) 
        child->attach();
            |
            |
            | // render的创建
            |
            V
void Element::attach()
    createRendererIfNeeded();
        |
        V
void Node::createRendererIfNeeded()
    RenderObject* newRenderer = createRendererAndStyle();
    parentNodeForRenderingAndStyle()->renderer()->addChild(newRenderer, nextRenderer());
        |
        V
RenderObject* Node::createRendererAndStyle()
    RefPtr<RenderStyle> style = styleForRenderer();
    if (!rendererIsNeeded(style.get())) return 0;
        
    RenderObject* newRenderer = createRenderer(document()->renderArena(), style.get());
        |
        V
RenderObject* HTMLElement::createRenderer(RenderArena* arena, RenderStyle* style)
    // 根据style创建render
    return RenderObject::createObject(this, style);
        |
        V
RenderObject* RenderObject::createObject(Node* node, RenderStyle* style)
    // if image
    RenderImage* image = new (arena) RenderImage(node);

    switch (style->display()) {
        case NONE:
            return 0;
        case INLINE:
            return new (arena) RenderInline(node);
        case BLOCK:
        case INLINE_BLOCK:
        case RUN_IN:
        case COMPACT:
            return new (arena) RenderBlock(node);
        case LIST_ITEM:
            return new (arena) RenderListItem(node);
        case TABLE:
        case INLINE_TABLE:
            return new (arena) RenderTable(node);
        case TABLE_ROW_GROUP:
        case TABLE_HEADER_GROUP:
        case TABLE_FOOTER_GROUP:
            return new (arena) RenderTableSection(node);
        case TABLE_ROW:
            return new (arena) RenderTableRow(node);
        case TABLE_COLUMN_GROUP:
        case TABLE_COLUMN:
            return new (arena) RenderTableCol(node);
        case TABLE_CELL:
            return new (arena) RenderTableCell(node);
        case TABLE_CAPTION:
            return new (arena) RenderBlock(node);
        case BOX:
        case INLINE_BOX:
            return new (arena) RenderFlexibleBox(node);

                        PassRefPtr<RenderStyle> Node::styleForRenderer()
                            return document()->styleSelector()->styleForElement(static_cast<Element*>(this), 0, allowSharing);



