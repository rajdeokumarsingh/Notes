[Finished 2011-09-08]

/* AudioService
    由system server启动
    base/services/java/com/android/server/SystemServer.java
        try {
            Slog.i(TAG, "Audio Service");
            ServiceManager.addService(Context.AUDIO_SERVICE, new AudioService(context));
        } catch (Throwable e) {
            Slog.e(TAG, "Failure starting Audio Service", e);
        } 

    上层
        AudioManager, Applicaiton
    下层
        AudioSystem, Settings,

    主要提供：
        音量控制的UI, 设置和实现(调用AudioSystem接口)
        铃音，振动的设置，控制

    implement a separate thread 
        1. set the system volume
        2. Synchronize the internal variables of ring mode, 
            vibrate setting, volume to the system settings

    maintain the volume states of all stream types
    create and display the volume adjusting UI 
    create a SettingsObserver to monitor the changes of the system settings

    monitor events of dock, BT a2dp, BT headset, wired headset, etc
    monitor events of the media button
    monitor the Phone states an request the audio focus for the ringer
        
    provide API to control SCO connection

    Handle the settings of ring mode, vibrate

    Handle force use of the speaker, bluetooth sco

    Enable/disable BT A2DP

    provider the mechanism of audio focus to multimedia applications
        the applications should use this mechanism to control playing of media 
*/

// implement separate thread to set the system volume and later persist to the database
// setting the ringer mode in a separate thread and later persist the ringer mode
public class AudioService extends IAudioService.Stub

    // sync volume from System and the audio service
    VolumeStreamState
        private final int mStreamType;

        private String mVolumeIndexSettingName;
        private String mLastAudibleVolumeIndexSettingName;

        private int mIndexMax;
        private int mIndex;
        private int mLastAudibleIndex;

        private ArrayList<VolumeDeathHandler> mDeathHandlers; //handles mute/solo requests client death

        private VolumeStreamState(String settingName, int streamType)
            setVolumeIndexSettingName(settingName);
            mStreamType = streamType;

            final ContentResolver cr = mContentResolver;
            mIndexMax = MAX_STREAM_VOLUME[streamType];

            // Sync with the system settings
            mIndex = Settings.System.getInt(cr, mVolumeIndexSettingName, 
                AudioManager.DEFAULT_STREAM_VOLUME[streamType]);
            mLastAudibleIndex = Settings.System.getInt(cr, mLastAudibleVolumeIndexSettingName,
                (mIndex > 0) ? mIndex : AudioManager.DEFAULT_STREAM_VOLUME[streamType]);

            AudioSystem.initStreamVolume(streamType, 0, mIndexMax);
            setStreamVolumeIndex(streamType, mIndex);

        public void mute(IBinder cb, boolean state)
            VolumeDeathHandler handler = getDeathHandler(cb, state);
            handler.mute(state);

        private class VolumeDeathHandler implements IBinder.DeathRecipient
            private IBinder mICallback; // To be notified of client's death
            private int mMuteCount; // Number of active mutes for this client


    // The UI
    private VolumePanel mVolumePanel; 

    // AudioHandler message.whats  
    private static final int MSG_SET_SYSTEM_VOLUME = 0; 
    private static final int MSG_PERSIST_VOLUME = 1;
    private static final int MSG_PERSIST_RINGER_MODE = 3;
    private static final int MSG_PERSIST_VIBRATE_SETTING = 4;
    private static final int MSG_MEDIA_SERVER_DIED = 5; 
    private static final int MSG_MEDIA_SERVER_STARTED = 6;
    private static final int MSG_PLAY_SOUND_EFFECT = 7; 
    private static final int MSG_BTA2DP_DOCK_TIMEOUT = 8; 


    private AudioSystemThread mAudioSystemThread;
        // run in AudioSystemThread
        private AudioHandler mAudioHandler

    /** @see VolumeStreamState */
    private VolumeStreamState[] mStreamStates;

    // settings change
    private SettingsObserver mSettingsObserver;

    /** @hide Maximum volume index values for audio streams */
    private int[] MAX_STREAM_VOLUME = new int[] 
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
    private int[] STREAM_VOLUME_ALIAS = new int[] 
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
        AudioSystem.STREAM_MUSIC  // STREAM_FM
    };

    // implement media server crash-recover mechanism
    private AudioSystem.ErrorCallback mAudioSystemCallback = new AudioSystem.ErrorCallback() 

    /* Sound effect file names  */

    /* Sound effect file name mapping sound effect id (AudioManager.FX_xxx) to
     * file index in SOUND_EFFECT_FILES[] (first column) and indicating if effect
     * uses soundpool (second column) */

    /*@link AudioManager#RINGER_MODE_NORMAL},
     * @link AudioManager#RINGER_MODE_SILENT},
     * @link AudioManager#RINGER_MODE_VIBRATE}.*/
    private int mRingerMode;
    private int mVibrateSetting;
    private int mNotificationsUseRingVolume;

    // Broadcast receiver for device connections intent broadcasts
    private final BroadcastReceiver mReceiver = new AudioServiceBroadcastReceiver();

    //  Broadcast receiver for media button broadcasts (separate from mReceiver to
    //  independently change its priority)
    private final BroadcastReceiver mMediaButtonReceiver = new MediaButtonBroadcastReceiver();

    // Devices currently connected
    private HashMap <Integer, String> mConnectedDevices = new HashMap <Integer, String>();

    // List of clients having issued a SCO start request
    private ArrayList <ScoClient> mScoClients = new ArrayList <ScoClient>();

    // BluetoothHeadset API to control SCO connection
    private BluetoothHeadset mBluetoothHeadset;

    // Bluetooth headset connection state
    private boolean mBluetoothHeadsetConnected;

public AudioService(Context context)
    mVolumePanel = new VolumePanel(context, this);
    mSettingsObserver = new SettingsObserver();
    createAudioSystemThread();

    // read ringer mode, vibrate setting, etc. from System
    // broadcast ringer mode, vibrate setting
    readPersistedSettings();

    // create stream state array from System.VOLUME_SETTINGS
    createStreamStates();
        

    loadSoundEffects();
    mBluetoothHeadset = new BluetoothHeadset(
        context, mBluetoothHeadsetServiceListener);

    // Register for device connection intent broadcasts
    Intent.ACTION_HEADSET_PLUG
    Intent.ACTION_DOCK_EVENT
    BluetoothA2dp.ACTION_SINK_STATE_CHANGED
    BluetoothHeadset.ACTION_STATE_CHANGED
    BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED

    // Register for media button intent broadcasts
    Intent.ACTION_MEDIA_BUTTON

    // Register for phone state monitoring
    tmgr.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags)
    int streamType = getActiveStreamType(suggestedStreamType);
    adjustStreamVolume(streamType, direction, flags);
        |
        V
// set the volume and persist it in System
public void adjustStreamVolume(int streamType, int direction, int flags)
    VolumeStreamState streamState = mStreamStates[STREAM_VOLUME_ALIAS[streamType]];
    // refer to:   ../flow.volume.adjusting.cpp


public void setStreamSolo(int streamType, boolean state, IBinder cb) 
    for (int stream = 0; stream < mStreamStates.length; stream++) 
        if (!isStreamAffectedByMute(stream) || stream == streamType) continue;                                                           
        // Bring back last audible volume
        mStreamStates[stream].mute(cb, state);                                                                                           

public void setStreamMute(int streamType, boolean state, IBinder cb) 
    if (isStreamAffectedByMute(streamType)) 
        mStreamStates[streamType].mute(cb, state);

public int getStreamVolume(int streamType) 
    return (mStreamStates[streamType].mIndex + 5) / 10;

public int getStreamMaxVolume(int streamType) 
    return (mStreamStates[streamType].getMaxIndex() + 5) / 10;

public int getRingerMode()
public void setRingerMode(int ringerMode)
    setRingerModeInt(ringerMode, true);
    // Send sticky broadcast
    broadcastRingerMode();

private void setRingerModeInt(int ringerMode, boolean persist) 
        mRingerMode = ringerMode;
        // Mute stream if not previously muted by ringer mode and ringer mode
        // is not RINGER_MODE_NORMAL and stream is affected by ringer mode.
        // Unmute stream if previously muted by ringer mode and ringer mode
        // is RINGER_MODE_NORMAL or stream is not affected by ringer mode.
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        for (int streamType = numStreamTypes - 1; streamType >= 0; streamType--) 
            if (isStreamMutedByRingerMode(streamType)) 
                if (!isStreamAffectedByRingerMode(streamType) ||
                        mRingerMode == AudioManager.RINGER_MODE_NORMAL) 
                    mStreamStates[streamType].mute(null, false);
                    mRingerModeMutedStreams &= ~(1 << streamType);
            else 
                if (isStreamAffectedByRingerMode(streamType) &&
                        mRingerMode != AudioManager.RINGER_MODE_NORMAL) 
                    mStreamStates[streamType].mute(null, true);
                    mRingerModeMutedStreams |= (1 << streamType);

        // Post a persist ringer mode msg
        if (persist)
            sendMsg(mAudioHandler, MSG_PERSIST_RINGER_MODE, SHARED_MSG,
                    SENDMSG_REPLACE, 0, 0, null, PERSIST_DELAY);

    public boolean shouldVibrate(int vibrateType) 
        switch (getVibrateSetting(vibrateType)) {
            case AudioManager.VIBRATE_SETTING_ON:
                return mRingerMode != AudioManager.RINGER_MODE_SILENT;
            case AudioManager.VIBRATE_SETTING_ONLY_SILENT:
                return mRingerMode == AudioManager.RINGER_MODE_VIBRATE;
            case AudioManager.VIBRATE_SETTING_OFF:
                // return false, even for incoming calls
                return false;


    public int getVibrateSetting(int vibrateType)
    public void setVibrateSetting(int vibrateType, int vibrateSetting)
        mVibrateSetting = getValueForVibrateSetting(mVibrateSetting, vibrateType, vibrateSetting);
        // Broadcast change
        // Post message to set ringer mode (it in turn will post a message to persist)


    private class SetModeDeathHandler implements IBinder.DeathRecipient 
        private IBinder mCb; // To be notified of client's death


    public void setMode(int mode, IBinder cb)

    public void playSoundEffect(int effectType)
    public void playSoundEffectVolume(int effectType, float volume)
        // send MSG_PLAY_SOUND_EFFECT

    public boolean loadSoundEffects()
    public void unloadSoundEffects()

    // readPersistedSettings and sync internal variable 
    public void reloadAudioSettings() 

    public void setSpeakerphoneOn(boolean on)
        if (on) {
            AudioSystem.setForceUse(AudioSystem.FOR_COMMUNICATION, AudioSystem.FORCE_SPEAKER);
            mForcedUseForComm = AudioSystem.FORCE_SPEAKER;
        else {
            AudioSystem.setForceUse(AudioSystem.FOR_COMMUNICATION, AudioSystem.FORCE_NONE);
            mForcedUseForComm = AudioSystem.FORCE_NONE;

    public void setBluetoothScoOn(boolean on)
        if (on) {
            AudioSystem.setForceUse(AudioSystem.FOR_COMMUNICATION, AudioSystem.FORCE_BT_SCO);
            AudioSystem.setForceUse(AudioSystem.FOR_RECORD, AudioSystem.FORCE_BT_SCO);
            mForcedUseForComm = AudioSystem.FORCE_BT_SCO;
        else {
            AudioSystem.setForceUse(AudioSystem.FOR_COMMUNICATION, AudioSystem.FORCE_NONE);
            AudioSystem.setForceUse(AudioSystem.FOR_RECORD, AudioSystem.FORCE_NONE);
            mForcedUseForComm = AudioSystem.FORCE_NONE;

    public void startBluetoothSco(IBinder cb)
        ScoClient client = getScoClient(cb);
        client.incCount();

    public void stopBluetoothSco(IBinder cb)
        ScoClient client = getScoClient(cb);
        client.decCount();

    private BluetoothHeadset.ServiceListener mBluetoothHeadsetServiceListener =
        new BluetoothHeadset.ServiceListener() {
            public void onServiceConnected() {
                if (mBluetoothHeadset != null) {
                    BluetoothDevice device = mBluetoothHeadset.getCurrentHeadset();
                    if (mBluetoothHeadset.getState(device) == BluetoothHeadset.STATE_CONNECTED) {
                        mBluetoothHeadsetConnected = true;

            public void onServiceDisconnected() {
                if (mBluetoothHeadset != null) {
                    BluetoothDevice device = mBluetoothHeadset.getCurrentHeadset();
                    if (mBluetoothHeadset.getState(device) == BluetoothHeadset.STATE_DISCONNECTED) {
                        mBluetoothHeadsetConnected = false;
                        clearAllScoClients();

    /** Checks if the adjustment should change ringer mode instead of just
     * adjusting volume. If so, this will set the proper ringer mode and volume
     * indices on the stream states.  */
    private boolean checkForRingerModeChange(int oldIndex, int direction)


    private int getActiveStreamType(int suggestedStreamType) 
        // refer to: ../flow.volume.adjusting.cpp

    private void broadcastRingerMode()
        /* AudioManager.RINGER_MODE_CHANGED_ACTION 
            putExtra: AudioManager.EXTRA_RINGER_MODE, mRingerMode
            addFlags: Intent.FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT | 
                      Intent.FLAG_RECEIVER_REPLACE_PENDING
        */

    private void broadcastVibrateSetting(int vibrateType) 
        /* AudioManager.VIBRATE_SETTING_CHANGED_ACTION
            putExtra(AudioManager.EXTRA_VIBRATE_TYPE, vibrateType)
            putExtra(AudioManager.EXTRA_VIBRATE_SETTING, getVibrateSetting(vibrateType))
        */


    private class AudioHandler extends Handler
        // set volume to low level
        // Post a persist volume msg
        private void setSystemVolume(VolumeStreamState streamState)
            
        private void persistVolume(VolumeStreamState streamState, boolean current, boolean lastAudible)
        private void persistRingerMode()
        private void persistVibrateSetting() 

        // use SoundPool or MediaPlayer to play the sound
        private void playSoundEffect(int effectType, int volume)
        
        public void handleMessage(Message msg) 
            case MSG_SET_SYSTEM_VOLUME:
                setSystemVolume((VolumeStreamState) msg.obj);
            case MSG_PERSIST_VOLUME:
                persistVolume((VolumeStreamState) msg.obj, (msg.arg1 != 0), (msg.arg2 != 0));
            case MSG_PERSIST_RINGER_MODE:
                persistRingerMode();
            case MSG_PERSIST_VIBRATE_SETTING:
                persistVibrateSetting();
            case MSG_MEDIA_SERVER_DIED:
                // TODO: to investigate
            case MSG_MEDIA_SERVER_STARTED:
                // TODO: to investigate
                // Media server started.
            case MSG_PLAY_SOUND_EFFECT:
                playSoundEffect(msg.arg1, msg.arg2);
            case MSG_BTA2DP_DOCK_TIMEOUT:
                makeA2dpDeviceUnavailableNow( (String) msg.obj );

    /* Observe following settings:
        Settings.System.MODE_RINGER_STREAMS_AFFECTED
        Settings.System.NOTIFICATIONS_USE_RING_VOLUME 
        and sync internal state variables */
    private class SettingsObserver extends ContentObserver

    private void makeA2dpDeviceAvailable(String address) {
        AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_BLUETOOTH_A2DP,
                AudioSystem.DEVICE_STATE_AVAILABLE, address);
        // Reset A2DP suspend state each time a new sink is connected
        AudioSystem.setParameters("A2dpSuspended=false");
        mConnectedDevices.put( new Integer(AudioSystem.DEVICE_OUT_BLUETOOTH_A2DP), address);

    private void makeA2dpDeviceUnavailableLater(String address) {
        // prevent any activity on the A2DP audio output to avoid unwanted
        // reconnection of the sink.
        AudioSystem.setParameters("A2dpSuspended=true");
        // the device will be made unavailable later, so consider it disconnected right away
        mConnectedDevices.remove(AudioSystem.DEVICE_OUT_BLUETOOTH_A2DP);
        // send the delayed message to make the device unavailable later
        Message msg = mAudioHandler.obtainMessage(MSG_BTA2DP_DOCK_TIMEOUT, address);
        mAudioHandler.sendMessageDelayed(msg, BTA2DP_DOCK_TIMEOUT_MILLIS);
                |
                V
    private void makeA2dpDeviceUnavailableNow(String address) {
        Intent noisyIntent = new Intent(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        mContext.sendBroadcast(noisyIntent);
        AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_BLUETOOTH_A2DP,
                AudioSystem.DEVICE_STATE_UNAVAILABLE, address);
        mConnectedDevices.remove(AudioSystem.DEVICE_OUT_BLUETOOTH_A2DP);


    // Receiver for misc intent broadcasts the Phone app cares about.
    private class AudioServiceBroadcastReceiver extends BroadcastReceiver
        /* Intent.ACTION_DOCK_EVENT
            getIntExtra Intent.EXTRA_DOCK_STATE
                Intent.EXTRA_DOCK_STATE_DESK:
                Intent.EXTRA_DOCK_STATE_CAR:
                    AudioSystem.setForceUse(AudioSystem.FOR_DOCK, config);

            BluetoothA2dp.ACTION_SINK_STATE_CHANGED
            BluetoothHeadset.ACTION_STATE_CHANGED
            BluetoothHeadset.ACTION_AUDIO_STATE_CHANGED

            Intent.ACTION_HEADSET_PLUG
                boolean isConnected = mConnectedDevices.containsKey(AudioSystem.DEVICE_OUT_WIRED_HEADPHONE);
                if (state == 0 && isConnected) {
                    AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_WIRED_HEADPHONE,
                        AudioSystem.DEVICE_STATE_UNAVAILABLE, "");
                    mConnectedDevices.remove(AudioSystem.DEVICE_OUT_WIRED_HEADPHONE);
                } else if (state == 1 && !isConnected)  {
                    AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_WIRED_HEADPHONE,
                        AudioSystem.DEVICE_STATE_AVAILABLE, "");
                    mConnectedDevices.put( new Integer(AudioSystem.DEVICE_OUT_WIRED_HEADPHONE), "");
        */

    //==========================================================================================
    // AudioFocus
    //==========================================================================================
    private PhoneStateListener mPhoneStateListener = new PhoneStateListener() 
        public void onCallStateChanged(int state, String incomingNumber)
            if (state == TelephonyManager.CALL_STATE_RINGING)
                mIsRinging = true;
                int ringVolume = AudioService.this.getStreamVolume(AudioManager.STREAM_RING);
                requestAudioFocus(AudioManager.STREAM_RING,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT,
                        null, null /* both allowed to be null only for this clientId */,
                        IN_VOICE_COMM_FOCUS_ID /*clientId*/);

            else if (state == TelephonyManager.CALL_STATE_OFFHOOK)
                mIsRinging = false;
                requestAudioFocus(AudioManager.STREAM_RING,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT,
                        null, null /* both allowed to be null only for this clientId */,
                        IN_VOICE_COMM_FOCUS_ID /*clientId*/);

            else if (state == TelephonyManager.CALL_STATE_IDLE)
                mIsRinging = false;
                abandonAudioFocus(null, IN_VOICE_COMM_FOCUS_ID, null);


    private static class FocusStackEntry

    // notify the top of the stack it gained focus
    private void notifyTopOfAudioFocusStack() 

    // Remove a focus listener from the focus stack
    private void removeFocusStackEntry(String clientToRemove, boolean signal)

    // Remove focus listeners from the focus stack for a particular client.
    private void removeFocusStackEntryForClient(IBinder cb)

    // true if the system is in a state where the focus can be reevaluated
    private boolean canReassignAudioFocus()

    // Inner class to monitor audio focus client deaths, 
    // and remove them from the audio focus stack if necessary.
    private class AudioFocusDeathHandler implements IBinder.DeathRecipient
        public void binderDied() {
            synchronized(mAudioFocusLock) {
                removeFocusStackEntryForClient(mCb);
                mAudioFocusDeathHandlers.remove(this);

    // create a new focus stack entry and push it to the focus stack
    // notify the focus change to the listeners
    public int requestAudioFocus(int mainStreamType, int focusChangeHint, 
        IBinder cb, IAudioFocusDispatcher fd, String clientId) 
        /* FIXME: No hardware operation, how the focus mechanism works
            Need the application to handle the focus events
            if it recieves  
                AudioManager.AUDIOFOCUS_LOSS:
                    it should pause
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    it could pause or duck
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    it should pause
                AudioManager.AUDIOFOCUS_GAIN:
                    it should play
         */
           
    // remove the focus entry from the focus stack
    public int abandonAudioFocus(IAudioFocusDispatcher fl, String clientId, IBinder cb) 

    public void unregisterAudioFocusClient(String clientId, IBinder cb)

    /* Receiver for media button intents. Handles the dispatching of the media button event
     * to one of the registered listeners, or if there was none, resumes the intent broadcast
     * to the rest of the system. */
    private class MediaButtonBroadcastReceiver extends BroadcastReceiver


// FIXME: dig more: AudioSystem.initStreamVolume(streamType, 0, mIndexMax);
