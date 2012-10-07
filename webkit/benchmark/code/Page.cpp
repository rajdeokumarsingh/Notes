// 对每个Frame的遍历 
{
    // 通知所有document, offline/online事件发生
    static void networkStateChanged()
        遍历所有Page中的每个Frame 
            AtomicString eventName = networkStateNotifier().onLine() ? 
                eventNames().onlineEvent : eventNames().offlineEvent;
            frames[i]->document()->dispatchWindowEvent(Event::create(eventName, false, false));

    static void onPackageResultAvailable()
        遍历所有Page中的每个Frame 
            frame->domWindow()->navigator()->onPackageResult();

    // 通知所有document需要重新计算style
    void Page::scheduleForcedStyleRecalcForAllPages()
        遍历所有Page中的每个Frame 
        frame->document()->scheduleForcedStyleRecalc();

    // 通知所有document, style发生变化
    void Page::setNeedsRecalcStyleInAllFrames()
        遍历所有Page中的每个Frame 
        frame->document()->styleSelectorChanged(DeferRecalcStyle);

    void Page::refreshPlugins(bool reload)
        遍历所有Page中的每个Frame, 如果frame中包括了plugin, reload
        if (frame->loader()->subframeLoader()->containsPlugins())
            framesNeedingReload[i]->loader()->reload();

    inline MediaCanStartListener* Page::takeAnyMediaCanStartListener()                                     
        // 遍历当前Page的所有Frame
        for (Frame* frame = mainFrame(); frame; frame = frame->tree()->traverseNext())
            if (MediaCanStartListener* listener = frame->document()->takeAnyMediaCanStartListener())       
                return listener;                                                                           

    // 对于当前Page的所有Frame, 如果canStartMeida, mediaCanStart()
    void Page::setCanStartMedia(bool canStartMedia)                                                        
        while (m_canStartMedia)
            MediaCanStartListener* listener = takeAnyMediaCanStartListener();                              
            if (!listener)                                                                                 
                break;
            listener->mediaCanStart();                                                                     

    // 遍历frame tree, wrap表示搜索到了结尾是否从头开始继续搜索
    static Frame* incrementFrame(Frame* curr, bool forward, bool wrapFlag)
        return forward
        ? curr->tree()->traverseNextWithWrap(wrapFlag)
        : curr->tree()->traversePreviousWithWrap(wrapFlag);

    // 从当前的focused或main frame开始搜索字符串
    // 通过Frame->Editor实现
    bool Page::findString(const String& target, FindOptions options) {
        Frame* frame = focusController()->focusedOrMainFrame();
        Frame* startFrame = frame;
        do {
            if (frame->editor()->findString(target, (options & ~WrapAround) | StartInSelection)) {
                if (frame != startFrame)
                    startFrame->selection()->clear();
                focusController()->setFocusedFrame(frame);
                return true;
            }
            // 遍历frame tree
            frame = incrementFrame(frame, !(options & Backwards), shouldWrap);
        } while (frame && frame != startFrame);
    }

    // 标记所有匹配的字符串，高亮显示，并返回匹配到的数量
    // 通过Frame->Editor实现
    unsigned int Page::markAllMatchesForText(const String& target, FindOptions options, bool shouldHighlight, unsigned limit) {
        Frame* frame = mainFrame();
        do {
            frame->editor()->setMarkedTextMatchesAreHighlighted(shouldHighlight);
            matches += frame->editor()->countMatchesForText(target, options, limit ? (limit - matches) : 0, true);
            frame = incrementFrame(frame, true, false);
        } while (frame);
        return matches;
    }

    // 取消当前page中所有frame的标记
    void Page::unmarkAllTextMatches() {
        Frame* frame = mainFrame();
        do {
            frame->document()->markers()->removeMarkers(DocumentMarker::TextMatch);
            frame = incrementFrame(frame, true, false);
        } while (frame);
    }

    // 设置媒体内容的音量
    void Page::setMediaVolume(float volume) {
        m_mediaVolume = volume;
        for (Frame* frame = mainFrame(); frame; frame = frame->tree()->traverseNext()) {
            frame->document()->mediaVolumeDidChange();
        }
    }

    void Page::didMoveOnscreen() {
        for (Frame* frame = mainFrame(); frame; frame = frame->tree()->traverseNext()) {
            if (frame->view())
                frame->view()->didMoveOnscreen();
        }
    }

    void Page::willMoveOffscreen() {
        for (Frame* frame = mainFrame(); frame; frame = frame->tree()->traverseNext()) {
            if (frame->view())
                frame->view()->willMoveOffscreen();
        }
    }

    void Page::setDebuggerForAllPages(JSC::Debugger* debugger)
        // 遍历所有Page的所有Frame
        frame->script()->attachDebugger(m_debugger);

    void Page::setMemoryCacheClientCallsEnabled(bool enabled) {
        m_areMemoryCacheClientCallsEnabled = enabled;
        // 遍历当前Page的所有Frame
        for (RefPtr<Frame> frame = mainFrame(); frame; frame = frame->tree()->traverseNext())
            frame->loader()->tellClientAboutPastMemoryCacheLoads();
    }

    void Page::setMinimumTimerInterval(double minimumTimerInterval)
    {
        double oldTimerInterval = m_minimumTimerInterval;
        m_minimumTimerInterval = minimumTimerInterval;
        // 遍历当前Page的所有Frame
        // TODO: 为什么设置的是oldTimerInterval
        for (Frame* frame = mainFrame(); frame; frame = frame->tree()->traverseNextWithWrap(false)) {
                frame->document()->adjustMinimumTimerInterval(oldTimerInterval);
        }
    }

    void Page::dnsPrefetchingStateChanged()
        // 遍历当前Page的所有Frame
        frame->document()->initDNSPrefetch();

    void Page::privateBrowsingStateChanged() {
        bool privateBrowsingEnabled = m_settings->privateBrowsingEnabled();

        // 通知当前页面的所有frame的document
        frame->document()->privateBrowsingStateDidChange();

        // 通知当前页面的所有frame的view的所有child
        for (Frame* frame = mainFrame(); frame; frame = frame->tree()->traverseNext()) {
            FrameView* view = frame->view();

            const HashSet<RefPtr<Widget> >* children = view->children();
            HashSet<RefPtr<Widget> >::const_iterator end = children->end();
            for (HashSet<RefPtr<Widget> >::const_iterator it = children->begin(); it != end; ++it) {
                Widget* widget = (*it).get();
                if (widget->isPluginViewBase())
                    widget->privateBrowsingStateChanged(privateBrowsingEnabled);
            }

        }
    }

}

// 历史项的访问
{
    BackForwardList* Page::backForwardList() const
        return m_backForwardController->client();

    BackForwardController* backForward() const 
        return m_backForwardController.get();

    bool Page::goBack()
        HistoryItem* item = backForward()->backItem();
        goToItem(item, FrameLoadTypeBack);

    bool Page::goForward()
        HistoryItem* item = backForward()->forwardItem();
        goToItem(item, FrameLoadTypeForward);

    void Page::goToItem(HistoryItem* item, FrameLoadType type)
        if (m_mainFrame->loader()->history()->shouldStopLoadingForHistoryItem(item))
            m_mainFrame->loader()->stopAllLoaders();

        m_mainFrame->loader()->history()->goToItem(item, type);
}

UI {
    // 让chrome通知UI层, viewport参数发生改变
    void Page::updateViewportArguments()
        m_viewportArguments = mainFrame()->document()->viewportArguments();
        chrome()->dispatchViewportDataDidChange(m_viewportArguments);

    // 返回当前的selection
    const VisibleSelection& Page::selection() const
        return focusController()->focusedOrMainFrame()->selection()->selection();

    // 用户指定的css style sheet, 可在setting中设置/获取
    const String& Page::userStyleSheet() 
        KURL url = m_settings->userStyleSheetLocation();
        m_userStyleSheetPath = url.fileSystemPath();
        
    void Page::addScrollableArea(ScrollableArea* scrollableArea)
        m_scrollableAreaSet->add(scrollableArea);

}

PageGroup {
    // TODO: visited links访问过的链接？
    // 有什么作用?
    // 和style sheet相关
    void Page::removeAllVisitedLinks()
        遍历所有的page, 如果page的m_group不为空，则
            m_group->removeVisitedLinks()

    void Page::allVisitedStateChanged(PageGroup* group)
        如果page的m_group等于参数group，则对于page的所有frame
            frame->document()->styleSelector()->allVisitedStateChanged();

    void Page::visitedStateChanged(PageGroup* group, LinkHash visitedLinkHash)
        如果page的m_group等于参数group，则对于page的所有frame
            frame->document()->styleSelector()->visitedStateChanged(visitedLinkHash);
}

void Page::setJavaScriptURLsAreAllowed(bool areAllowed)
    m_javaScriptURLsAreAllowed = areAllowed;

