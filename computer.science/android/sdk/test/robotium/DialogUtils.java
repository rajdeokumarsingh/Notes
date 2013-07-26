// Waits for a {@link android.app.Dialog} to close.
public boolean waitForDialogToClose(long timeout)
    int elementsNow = viewFetcher.getWindowDecorViews().length;

    // 通过ViewFetcher去获得当前decorate view的数量
    // 检查当前的View的数量，如果数量变少了，则认为Dialog已经关闭了
    // 如果不变，则通过sleeper等待

