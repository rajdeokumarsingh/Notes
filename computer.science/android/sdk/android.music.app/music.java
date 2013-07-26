
MediaPlaybackService extends Service

    // service relative interface
        private final IBinder mBinder = new ServiceStub(this);

        public IBinder onBind(Intent intent) {
            mDelayedStopHandler.removeCallbacksAndMessages(null);
            mServiceInUse = true; 
            return mBinder;

        public void onRebind(Intent intent) { 
            mDelayedStopHandler.removeCallbacksAndMessages(null);
            mServiceInUse = true;

        public int onStartCommand(Intent intent, int flags, int startId) {
            mServiceStartId = startId;     
            mDelayedStopHandler.removeCallbacksAndMessages(null);

            if (intent != null) {
                String action = intent.getAction();
                String cmd = intent.getStringExtra("command");

                // According to the action and command 
                    // execute the command: next, prev, play/pause, pause, stop

            // make sure the service will shut down on its own if it was
            // just started but not bound to and nothing is playing
            mDelayedStopHandler.removeCallbacksAndMessages(null);
            Message msg = mDelayedStopHandler.obtainMessage();
            mDelayedStopHandler.sendMessageDelayed(msg, IDLE_DELAY);                                                                             
            return START_STICKY;

        public boolean onUnbind(Intent intent) {
            mServiceInUse = false;

            // Take a snapshot of the current playlist
            saveQueue(true);

            if (isPlaying() || mPausedByTransientLossOfFocus) {
                // something is currently playing, or will be playing once 
                // an in-progress action requesting audio focus ends, so don't stop the service now.
                return true;

            // If there is a playlist but playback is paused, then wait a while
            // before stopping the service, so that pause/resume isn't slow.
            // Also delay stopping the service if we're transitioning between tracks.
            if (mPlayListLen > 0  || mMediaplayerHandler.hasMessages(TRACK_ENDED)) {
                Message msg = mDelayedStopHandler.obtainMessage();
                mDelayedStopHandler.sendMessageDelayed(msg, IDLE_DELAY);
                return true;

            // No active playlist, OK to stop the service right now
            stopSelf(mServiceStartId);
            return true;

        public void onCreate() {  
            super.onCreate();

            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            mAudioManager.registerMediaButtonEventReceiver(new ComponentName(getPackageName(),
                        MediaButtonIntentReceiver.class.getName()));

            registerExternalStorageListener();

            // Wrapped a MediaPlayer
            mPlayer = new MultiPlayer();
            mPlayer.setHandler(mMediaplayerHandler);

            reloadQueue();

            // Added IntentFilter for play/stop/... action
            IntentFilter commandFilter = new IntentFilter();
            commandFilter.addAction(SERVICECMD);
            commandFilter.addAction(TOGGLEPAUSE_ACTION);
            commandFilter.addAction(PAUSE_ACTION);
            commandFilter.addAction(NEXT_ACTION);
            commandFilter.addAction(PREVIOUS_ACTION);
            registerReceiver(mIntentReceiver, commandFilter);

            // If the service was idle, but got killed before it stopped itself, the
            // system will relaunch it. Make sure it gets stopped again in that case.
            Message msg = mDelayedStopHandler.obtainMessage();
            mDelayedStopHandler.sendMessageDelayed(msg, IDLE_DELAY);

        public void onDestroy()
            // release all MediaPlayer resources, including the native player and wakelocks
            mPlayer.release();
            mPlayer = null;

            mAudioManager.abandonAudioFocus(mAudioFocusListener);

            // make sure there aren't any other messages coming
            mDelayedStopHandler.removeCallbacksAndMessages(null);
            mMediaplayerHandler.removeCallbacksAndMessages(null);

            mCursor.close();
            unregisterReceiver(mIntentReceiver);
            unregisterReceiver(mUnmountReceiver);
            mWakeLock.release();


    private Handler mMediaplayerHandler = new Handler()
        public void handleMessage(Message msg) 
            switch (msg.what) {                
                case FADEIN:
                    // 每个10毫秒增加0.01的音量, 直到1.0
                case SERVER_DIED:
                    next(true); // or
                    openCurrent();
                case TRACK_ENDED:
                    if (mRepeatMode == REPEAT_CURRENT) {
                        seek(0);
                        play();
                    } else if (!mOneShot) {
                        next(false);
                    } else {
                        notifyChange(PLAYBACK_COMPLETE);
                        mIsSupposedToBePlaying = false;
                case RELEASE_WAKELOCK:
                    mWakeLock.release();

    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) 
            String action = intent.getAction();
            String cmd = intent.getStringExtra("command");
            // 受到不同的action和command, 进行处理 
            // 和onStartCommand类似
                CMDNEXT, NEXT_ACTION : next(true)
                CMDPREVIOUS, PREVIOUS_ACTION: prev();
                CMDTOGGLEPAUSE, TOGGLEPAUSE_ACTION: play() or pause()
                CMDPAUSE, PAUSE_ACTION: pause()
                CMDSTOP: pause(); seek(0); 

    private OnAudioFocusChangeListener mAudioFocusListener = new OnAudioFocusChangeListener() 
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange)
                case AudioManager.AUDIOFOCUS_LOSS:
                    if(isPlaying()) {
                        mPausedByTransientLossOfFocus = false;
                        pause(); break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    if(isPlaying()) {
                        mPausedByTransientLossOfFocus = true;
                        pause(); break;

                case AudioManager.AUDIOFOCUS_GAIN:
                    if(!isPlaying() && mPausedByTransientLossOfFocus) {
                        mPausedByTransientLossOfFocus = false;
                        startAndFadeIn(); break;

    // interval (60 sec)after which we stop the service when idle
    private static final int IDLE_DELAY = 60000;

    private Handler mDelayedStopHandler = new Handler()
        public void handleMessage(Message msg) 
            // Check again to make sure nothing is playing right now
            if (isPlaying() || mPausedByTransientLossOfFocus || mServiceInUse
                    || mMediaplayerHandler.hasMessages(TRACK_ENDED)) {
                return;

            saveQueue(true);
            stopSelf(mServiceStartId);

    /* Registers an intent to listen for ACTION_MEDIA_EJECT notifications.
    * The intent will call closeExternalStorageFiles() if the external media
    * is going to be ejected, so applications can clean up any files they have open.*/
    public void registerExternalStorageListener() 
        mUnmountReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals(Intent.ACTION_MEDIA_EJECT)) {
                    saveQueue(true);
                    mOneShot = true; // This makes us not save the state again later,
                    // which would be wrong because the song ids and
                    // card id might not match. 
                    closeExternalStorageFiles(intent.getData().getPath());
                } else if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
                    ...}

            }
        }

        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(Intent.ACTION_MEDIA_EJECT);
        iFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        iFilter.addDataScheme("file");
        registerReceiver(mUnmountReceiver, iFilter);

    public void play() {
        mAudioManager.requestAudioFocus(mAudioFocusListener, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        mAudioManager.registerMediaButtonEventReceiver(new ComponentName(this.getPackageName(),
                    MediaButtonIntentReceiver.class.getName()));

        if (mPlayer.isInitialized()) {
            // if we are at the end of the song, go to the next song first
            long duration = mPlayer.duration();
            if (mRepeatMode != REPEAT_CURRENT && duration > 2000 &&
                    mPlayer.position() >= duration - 2000) {
                next(true);

            mPlayer.start();

            // update status bar

    private void stop(boolean remove_status_icon) {
        if (mPlayer.isInitialized()) {
            mPlayer.stop();

        mFileToPlay = null;
        mCursor.close();
        mCursor = null;

        if (remove_status_icon) {
            gotoIdleState();
        } else {
            stopForeground(false);

        if (remove_status_icon) {
            mIsSupposedToBePlaying = false;

    /** Stops playback. */
    public void stop() {
        stop(true); // remove_status_icon

    /** Pauses playback (call play() to resume) */
    public void pause() 
        if (isPlaying()) {
            mPlayer.pause();
            gotoIdleState();
            mIsSupposedToBePlaying = false;
            notifyChange(PLAYSTATE_CHANGED);
            saveBookmarkIfNeeded();
        }

    /** Returns whether something is currently playing
     *
     * @return true if something is playing (or will be playing shortly, in case
     * we're currently transitioning between tracks), false if not.
     */
    public boolean isPlaying() {
        return mIsSupposedToBePlaying;

    public void prev() {
        // select a song from the history
        stop(false);
        openCurrent();
        play();
        notifyChange(META_CHANGED);

    public void next(boolean force)
        // Pick random next track from the not-yet-played ones
        stop(false);
        openCurrent();
        play();
        notifyChange(META_CHANGED);

    private void gotoIdleState() {
        mDelayedStopHandler.removeCallbacksAndMessages(null);
        Message msg = mDelayedStopHandler.obtainMessage();
        mDelayedStopHandler.sendMessageDelayed(msg, IDLE_DELAY);
        stopForeground(true);

    public void setQueuePosition(int pos)
        stop(false);
        mPlayPos = pos;
        openCurrent();
        play();
        notifyChange(META_CHANGED);    
        if (mShuffleMode == SHUFFLE_AUTO) {
            doAutoShuffleUpdate();         
        }

    // wrapped a MediaPlayer
    private class MultiPlayer {
        private MediaPlayer mMediaPlayer = new MediaPlayer();
        /* invoking
        mMediaPlayer.reset();
        mMediaPlayer.setDataSource(path);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(preparedlistener);
        mMediaPlayer.prepareAsync();

        mMediaPlayer.setOnCompletionListener(listener);
        mMediaPlayer.setOnErrorListener(errorListener);

        mMediaPlayer.prepare();
        mMediaPlayer.start();
        mMediaPlayer.reset(); // stop()
        mMediaPlayer.release();
        mMediaPlayer.pause();

        mMediaPlayer.getDuration();
        mMediaPlayer.getCurrentPosition();

        mMediaPlayer.seekTo((int) whereto);
        mMediaPlayer.setVolume(vol, vol);
        */

        MediaPlayer.OnCompletionListener listener = new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                // Acquire a temporary wakelock, since when we return from
                // this callback the MediaPlayer will release its wakelock
                // and allow the device to go to sleep.
                // This temporary wakelock is released when the RELEASE_WAKELOCK
                // message is processed, but just in case, put a timeout on it.
                mWakeLock.acquire(30000);
                mHandler.sendEmptyMessage(TRACK_ENDED);
                mHandler.sendEmptyMessage(RELEASE_WAKELOCK);

        MediaPlayer.OnPreparedListener preparedlistener = new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                notifyChange(ASYNC_OPEN_COMPLETE);

        MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
            public boolean onError(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                        mIsInitialized = false;
                        mMediaPlayer.release();
                        // Creating a new MediaPlayer and settings its wakemode does not
                        // require the media service, so it's OK to do this now, while the
                        // service is still being restarted
                        mMediaPlayer = new MediaPlayer();
                        mMediaPlayer.setWakeMode(MediaPlaybackService.this, PowerManager.PARTIAL_WAKE_LOCK);
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(SERVER_DIED), 2000);
                        return true;



    /*
     * Wrapped a MediaPlaybackService
     * By making this a static class with a WeakReference to the Service, we
     * ensure that the Service can be GCd even when the system process still
     * has a remote reference to the stub.
     */
     static class ServiceStub extends IMediaPlaybackService.Stub {
         WeakReference<MediaPlaybackService> mService;


