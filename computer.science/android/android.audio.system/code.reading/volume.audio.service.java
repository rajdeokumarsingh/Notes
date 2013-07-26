AudioService.java

    /** The UI */
    private VolumePanel mVolumePanel;

    // AudioHandler message.whats
    private static final int MSG_SET_SYSTEM_VOLUME = 0;
    private static final int MSG_PERSIST_VOLUME = 1;

    /** @see VolumeStreamState */
    private VolumeStreamState[] mStreamStates;

   /** @hide Maximum volume index values for audio streams */
    private int[] MAX_STREAM_VOLUME = new int[] {
        5,  // STREAM_VOICE_CALL
        7,  // STREAM_SYSTEM
        7,  // STREAM_RING
        15, // STREAM_MUSIC
        7,  // STREAM_ALARM
        7,  // STREAM_NOTIFICATION
        15, // STREAM_BLUETOOTH_SCO
        7,  // STREAM_SYSTEM_ENFORCED
        15, // STREAM_DTMF
        15,  // STREAM_TTS
        15 // STREAM_FM
    };

    /* STREAM_VOLUME_ALIAS[] indicates for each stream if it uses the volume settings
     * of another stream: This avoids multiplying the volume settings for hidden
     * stream types that follow other stream behavior for volume settings
     * NOTE: do not create loops in aliases! */
    private int[] STREAM_VOLUME_ALIAS = new int[] {
        AudioSystem.STREAM_VOICE_CALL,  // STREAM_VOICE_CALL
        AudioSystem.STREAM_SYSTEM,  // STREAM_SYSTEM
        AudioSystem.STREAM_RING,  // STREAM_RING
        AudioSystem.STREAM_MUSIC, // STREAM_MUSIC
        AudioSystem.STREAM_ALARM,  // STREAM_ALARM
        AudioSystem.STREAM_NOTIFICATION,  // STREAM_NOTIFICATION
        AudioSystem.STREAM_BLUETOOTH_SCO, // STREAM_BLUETOOTH_SCO
        AudioSystem.STREAM_SYSTEM,  // STREAM_SYSTEM_ENFORCED
        AudioSystem.STREAM_VOICE_CALL, // STREAM_DTMF
        AudioSystem.STREAM_MUSIC,  // STREAM_TTS
        AudioSystem.STREAM_FM // STREAM_FM
    };

    /** @see System#NOTIFICATIONS_USE_RING_VOLUME */
    private int mNotificationsUseRingVolume;

    内部音量的状态
    public class VolumeStreamState {
        private final int mStreamType;

        private String mVolumeIndexSettingName;
        private int mIndexMax;
        private int mIndex;
        private ArrayList<VolumeDeathHandler> mDeathHandlers; //handles mute/solo requests client death

        // ??
        private int mLastAudibleIndex;
        private String mLastAudibleVolumeIndexSettingName;

        private VolumeStreamState(String settingName, int streamType) {
            setVolumeIndexSettingName(settingName);
            mStreamType = streamType;

            final ContentResolver cr = mContentResolver;
            mIndexMax = MAX_STREAM_VOLUME[streamType];
            // 从setting中获取当前音量
            mIndex = Settings.System.getInt(cr,
                    mVolumeIndexSettingName,
                    AudioManager.DEFAULT_STREAM_VOLUME[streamType]);
            mLastAudibleIndex = Settings.System.getInt(cr,
                    mLastAudibleVolumeIndexSettingName,
                    (mIndex > 0) ? mIndex : AudioManager.DEFAULT_STREAM_VOLUME[streamType]);

            // 设置audio policy manager中的volume的最大值和最小值
            AudioSystem.initStreamVolume(streamType, 0, mIndexMax);

            // FIXME: 为什么要 * 10
            mIndexMax *= 10;
            mIndex = getValidIndex(10 * mIndex);
            mLastAudibleIndex = getValidIndex(10 * mLastAudibleIndex);
            setStreamVolumeIndex(streamType, mIndex);
            mDeathHandlers = new ArrayList<VolumeDeathHandler>();
        }

        public boolean setIndex(int index, boolean lastAudible) {
            int oldIndex = mIndex;
            mIndex = getValidIndex(index);

            if (oldIndex != mIndex) {
                if (lastAudible) 
                    mLastAudibleIndex = mIndex;
                // Apply change to all streams using this one as alias

        public void setLastAudibleIndex(int index) {
            mLastAudibleIndex = getValidIndex(index);

        public void adjustLastAudibleIndex(int deltaIndex) {
            setLastAudibleIndex(mLastAudibleIndex + deltaIndex * 10);

        public void mute(IBinder cb, boolean state) {
            VolumeDeathHandler handler = getDeathHandler(cb, state);
            handler.mute(state);

        private class VolumeDeathHandler implements IBinder.DeathRecipient {
            private IBinder mICallback; // To be notified of client's death
            private int mMuteCount; // Number of active mutes for this client

            VolumeDeathHandler(IBinder cb) {
                mICallback = cb;

            public void mute(boolean state) {
                    if (state) {
                        if (mMuteCount == 0) {
                            // Register for client death notification
                            try {
                                // mICallback can be 0 if muted by AudioService
                                if (mICallback != null) {
                                    mICallback.linkToDeath(this, 0);

                                mDeathHandlers.add(this);
                                // If the stream is not yet muted by any client, set lvel to 0
                                if (muteCount() == 0) {
                                    setIndex(0, false);
                                    sendMsg(mAudioHandler, MSG_SET_SYSTEM_VOLUME, mStreamType, SENDMSG_NOOP, 0, 0,
                                            VolumeStreamState.this, 0);
                            } catch (RemoteException e) {
                                // Client has died!
                                binderDied();
                                mDeathHandlers.notify();
                                return;
                        mMuteCount++;
                    mDeathHandlers.notify();

            public void binderDied() {
                    mMuteCount = 1;
                    mute(false);

        // 返回有多少个client希望stream mute
        private int muteCount() {
            int count = 0;
            int size = mDeathHandlers.size();
            for (int i = 0; i < size; i++) {
                count += mDeathHandlers.get(i).mMuteCount;
            return count;

        private VolumeDeathHandler getDeathHandler(IBinder cb, boolean state) {
            VolumeDeathHandler handler;
            int size = mDeathHandlers.size();
            for (int i = 0; i < size; i++) {
                handler = mDeathHandlers.get(i);
                if (cb == handler.mICallback) {
                    return handler;

public AudioService(Context context) {
        // Intialized volume
        MAX_STREAM_VOLUME[AudioSystem.STREAM_VOICE_CALL] = SystemProperties.getInt(
                "ro.config.vc_call_vol_steps",
                MAX_STREAM_VOLUME[AudioSystem.STREAM_VOICE_CALL]);

        mVolumePanel = new VolumePanel(context, this);
        ...
    }

    System.VOLUME_SETTINGS[]
        系统的音量设置
        保存在系统设置数据库中

    // 创建volume state数组，记录每个stream的volume状态
    private void createStreamStates() {
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        VolumeStreamState[] streams = mStreamStates = new VolumeStreamState[numStreamTypes];

        for (int i = 0; i < numStreamTypes; i++) {
            streams[i] = new VolumeStreamState(System.VOLUME_SETTINGS[
                    streamTypeToSettingType(STREAM_VOLUME_ALIAS[i])], i);
        }

    public void setStreamMute(int streamType, boolean state, IBinder cb) {
        if (isStreamAffectedByMute(streamType)) {
            mStreamStates[streamType].mute(cb, state);


