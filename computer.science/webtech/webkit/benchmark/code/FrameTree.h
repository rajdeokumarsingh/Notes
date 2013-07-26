一个简单的tree
    节点是Frame
    关联节点是其父亲，兄弟，子女

自身节点 {
    Frame* m_thisFrame;
}

关联节点 {
    Frame* m_parent;

    RefPtr<Frame> m_nextSibling;
    Frame* m_previousSibling;

    RefPtr<Frame> m_firstChild;
    Frame* m_lastChild;
}

