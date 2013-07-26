
class CachedNode
    // 猜想：        
    对应一个DOM node

    bounds, 边界, cursorRing边界, localBounds
        originalAbsoluteBounds
        rawBounds

        WebCore::IntRect mBounds;
        WebCore::IntRect mHitBounds;
        WebCore::IntRect mOriginalAbsoluteBounds;


    mIndex?

    有类型
        参见CachedNodeType


    hint: 
        CachedNode::bounds
        const CachedNode* parent() const { return document() + mParentIndex; }
        isFrame

        return mIsInLayer ? frame->adjustBounds(this, bounds) : bounds;


        WebCore::IntRect CachedNode::hitBounds(const CachedFrame* frame) const
            return mIsInLayer ? frame->adjustBounds(this, mHitBounds) : mHitBounds;

    bool CachedNode::isTextField(const CachedFrame* frame) const
        const CachedInput* input = frame->textInput(this);
        return input ? input->isTextField() : false;

        WebCore::IntRect CachedNode::localBounds(const CachedFrame* frame) const
            return mIsInLayer ? frame->localBounds(this, mBounds) : mBounds;

    WebCore::IntRect CachedNode::localHitBounds(const CachedFrame* frame) const


