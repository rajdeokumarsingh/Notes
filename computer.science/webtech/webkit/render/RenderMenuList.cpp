
RenderMenuList : public RenderFlexibleBox {
       RenderText* m_buttonText;
       RenderBlock* m_innerBlock = createAnonymousBlock();

       RefPtr<RenderStyle> m_optionStyle;
       RefPtr<PopupMenu> m_popup;

       RefPtr<PopupMenu> m_popup;
}

RenderBR


void RenderMenuList::addChild(RenderObject* newChild, RenderObject* beforeChild)
{
    createInnerBlock();
    m_innerBlock->addChild(newChild, beforeChild);
}  
        |
        V
    void RenderMenuList::createInnerBlock()
    {
        if (m_innerBlock) {
            return;
        }

        // Create an anonymous block.
        m_innerBlock = createAnonymousBlock();
        adjustInnerStyle();
        RenderFlexibleBox::addChild(m_innerBlock);
    }
                |
                V
        RenderBlock* RenderBlock::createAnonymousBlock(bool isFlexibleBox) const
        {
            RefPtr<RenderStyle> newStyle = RenderStyle::createAnonymousStyle(style());
            RenderBlock* newBox = 0;
            if (isFlexibleBox) {
                newStyle->setDisplay(BOX);
                newBox = new (renderArena()) RenderFlexibleBox(document() /* anonymous box */);
            } else {
                newStyle->setDisplay(BLOCK);
                newBox = new (renderArena()) RenderBlock(document() /* anonymous box */);
            }

            newBox->setStyle(newStyle.release());
            return newBox;
        }




bool RenderThemeAndroid::paintTextArea(RenderObject* obj, const PaintInfo& info, const IntRect& rect)
{
    if (obj->isMenuList())
        paintCombo(obj, info, rect);
    return true;
}


