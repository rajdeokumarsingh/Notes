
src/com/android/browser/CrashRecoveryHandler.java

@Override
public void handleMessage(Message msg) {
    case MSG_WRITE_STATE:
        // bundle中的状态写入到cache dir中的browser_state.parcel文件中
        Bundle state = (Bundle) msg.obj;
        state.writeToParcel(p, 0);
        File stateJournal = new File(mContext.getCacheDir(),
                STATE_FILE + ".journal");
        FileOutputStream fout = new FileOutputStream(stateJournal);
        fout.write(p.marshall());
        fout.close();
        File stateFile = new File(mContext.getCacheDir(),
                STATE_FILE);
        if (!stateJournal.renameTo(stateFile)) {
            // Failed to rename, try deleting the existing
            // file and try again
            stateFile.delete();
            stateJournal.renameTo(stateFile);
        }

    case MSG_CLEAR_STATE:
        // 删除browser_state.parcel文件
        File state = new File(mContext.getCacheDir(), STATE_FILE);
        if (state.exists()) {
            state.delete();
        }

    case MSG_PRELOAD_STATE:
        // 加载crash状态
        mRecoveryState = loadCrashState();
}

// TODO：
// 在一个runnable调用onSaveInstance, 并将保存的state写到文件中
public void backupState() {
    mForegroundHandler.postDelayed(mCreateState, BACKUP_DELAY);
}

private Runnable mCreateState = new Runnable() {
    @Override
        public void run() {
            final Bundle state = new Bundle();
            mController.onSaveInstanceState(state);
            Message.obtain(mBackgroundHandler, MSG_WRITE_STATE, state)
                .sendToTarget();
            // Remove any queued up saves
            mForegroundHandler.removeCallbacks(mCreateState);
        }
};

// 从上次保存的文件中恢复bundle数据
private Bundle loadCrashState() {
    Parcel parcel = Parcel.obtain();

    File stateFile = new File(mContext.getCacheDir(), STATE_FILE);
    fin = new FileInputStream(stateFile);
    ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[BUFFER_SIZE];
    int read;
    while ((read = fin.read(buffer)) > 0) {
        dataStream.write(buffer, 0, read);
    }
    byte[] data = dataStream.toByteArray();
    parcel.unmarshall(data, 0, data.length);
    parcel.setDataPosition(0);
    state = parcel.readBundle();
    if (state != null && !state.isEmpty()) {
        return state;
    }
}

// recover状态后，调用用Controller.doStart(Bundle)
public void startRecovery(Intent intent) {
    mRecoveryState = loadCrashState();
    mController.doStart(mRecoveryState, intent, true);
}

src/com/android/browser/Controller.java
保存状态的流程:

void onSaveInstanceState(Bundle outState) {
    // the default implementation requires each view to have an id. As the
    // browser handles the state itself and it doesn't use id for the views,
    // don't call the default implementation. Otherwise it will trigger the
    // warning like this, "couldn't save which view has focus because the
    // focused view XXX has no id".
    // Save all the tabs
    mTabControl.saveState(outState);

    // Save time so that we know how old incognito tabs (if any) are.
    outState.putSerializable("lastActiveDate", Calendar.getInstance());
}
    |
    V
TabControll.saveState(Bundle outState)
    // 保存每个tab的state到bundle
    for (Tab tab : mTabs) {
        Bundle tabState = tab.saveState();
        if (tabState != null) {
            ids[i++] = tab.getId();
            String key = Long.toString(tab.getId());
            outState.putBundle(key, tabState);
        } 
    }

    // 保存tab的id列表
    outState.putLongArray(POSITIONS, ids);
    Tab current = getCurrentTab();

    // 保存当前tab的id
    cid = current.getId();
    outState.putLong(CURRENT, cid);

    |
    v
Tab.saveState() 
    mSavedState = new Bundle();
    // 通过webview保存状态
    WebBackForwardList savedList = mMainView.saveState(mSavedState);

    // 保存id, url, title, appid, ...
    mSavedState.putLong(ID, mId);
    mSavedState.putString(CURRURL, mCurrentState.mUrl);
    mSavedState.putString(CURRTITLE, mCurrentState.mTitle);
    mSavedState.putBoolean(INCOGNITO, mMainView.isPrivateBrowsingEnabled());
    if (mAppId != null) {
        mSavedState.putString(APPID, mAppId);
    }
    mSavedState.putBoolean(CLOSEFLAG, mCloseOnBack);
    // Remember the parent tab so the relationship can be restored.
    if (mParent != null) {
        mSavedState.putLong(PARENTTAB, mParent.mId);
    }
    mSavedState.putBoolean(USERAGENT,
            mSettings.hasDesktopUseragent(getWebView()));


WebView.saveState(Bundle outState)
    // 保存历史列表的当前index
    WebBackForwardList list = copyBackForwardList();
    final int currentIndex = list.getCurrentIndex();
    outState.putInt("index", currentIndex);

    // 保存所有的history item
    ArrayList<byte[]> history = new ArrayList<byte[]>(size);
    for (int i = 0; i < size; i++) {
        WebHistoryItem item = list.getItemAtIndex(i);
        byte[] data = item.getFlattenedData();
        history.add(data);
    }
    outState.putSerializable("history", history);

    // 保存certificate, zoom
    if (mCertificate != null) {
        outState.putBundle("certificate",
                SslCertificate.saveState(mCertificate));
    }
    outState.putBoolean("privateBrowsingEnabled", isPrivateBrowsingEnabled());
    mZoomManager.saveZoomState(outState);


恢复状态的流程:
src/com/android/browser/BrowserActivity.java
    public void onCreate(Bundle icicle)
        mController = new Controller(this, icicle == null);

        Bundle state = getIntent().getBundleExtra(EXTRA_STATE);
        if (state != null && icicle == null) {
            icicle = state;
        }

        mController.start(icicle, getIntent());

            | 
            V
src/com/android/browser/Controller.java
public Controller(Activity browser, boolean preloadCrashState)
    // preloadCrashState <==> Bundle icicle == null
    // 如果启动时没有传如bundle,则开始preloadCrashState()
    if (preloadCrashState) {
        mCrashRecoveryHandler.preloadCrashState();
    }

void start(final Bundle icicle, final Intent intent) 
    boolean noCrashRecovery = intent.getBooleanExtra(NO_CRASH_RECOVERY, false);

    if (icicle != null || noCrashRecovery) {
        doStart(icicle, intent, false);
    } else {
        mCrashRecoveryHandler.startRecovery(intent);
    }

    |
    | recovery path 1
    V
void doStart(final Bundle icicle, final Intent intent, final boolean fromCrash)
     final long currentTabId = mTabControl.canRestoreState(icicle, restoreIncognitoTabs);

     GoogleAccountLogin.startLoginIfNeeded(mActivity,
         new Runnable() {
             @Override public void run() {

             onPreloginFinished(icicle, intent, currentTabId, restoreIncognitoTabs,
                 fromCrash); // fromCrash == false
         }
         });
}

private void onPreloginFinished(Bundle icicle, Intent intent, long currentTabId,
        boolean restoreIncognitoTabs, boolean fromCrash) {

    if (currentTabId == -1) {
    ...
    } else { // restore state
        mTabControl.restoreState(icicle, currentTabId, restoreIncognitoTabs,
                mUi.needsRestoreAllTabs());
        List<Tab> tabs = mTabControl.getTabs();
        ArrayList<Long> restoredTabs = new ArrayList<Long>(tabs.size());
        for (Tab t : tabs) {
            restoredTabs.add(t.getId());
        }
        BackgroundHandler.execute(new PruneThumbnails(mActivity, restoredTabs));
        if (tabs.size() == 0) {
            openTabToHomePage();
        }
        mUi.updateTabs(tabs);
        // TabControl.restoreState() will create a new tab even if
        // restoring the state fails.
        setActiveTab(mTabControl.getCurrentTab());
        // Handle the intent if needed. If icicle != null, we are restoring
        // and the intent will be stale - ignore it.
        if (icicle == null || fromCrash) {
            mIntentHandler.onNewIntent(intent);
        }
    }
    |
    V
src/com/android/browser/TabControl.java
    void restoreState(Bundle inState, long currentId,
        boolean restoreIncognitoTabs, boolean restoreAll) 




    |
    | recovery path 2
    V
mCrashRecoveryHandler.startRecovery(intent);
