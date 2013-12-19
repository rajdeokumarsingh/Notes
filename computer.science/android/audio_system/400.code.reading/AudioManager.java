[Finished 2011-09-07]

/*
AudioManager基本上是对AudioService和AudioSystem的封装

AudioManager-->AudioService
            |->AudioSystem

提供:
    音量控制接口
        adjustStreamVolume, adjustVolume, adjustSuggestedStreamVolume
        getStreamMaxVolume, setStreamVolume

    声音控制接口
        setStreamSolo, setStreamMute, setMicrophoneMute
        isSpeakerphoneOn setSpeakerphoneOn, isWiredHeadsetOn, 
        setMode
        setParameters

    Bluetooth控制接口
        startBluetoothSco, stopBluetoothSco, setBluetoothScoOn

    Audio Focus控制接口:
        requestAudioFocus, abandonAudioFocus

    铃音振动控制接口:
        setRingerMode, setVibrateSetting, reloadAudioSettings
*/

// access to volume and ringer mode control

    // broadcast ACTION_AUDIO_BECOMING_NOISY, audio系统会发出噪音
        // 比如耳机拔出, a2dp audio sink断, 等等
        // 应用程序收到事件可选择pause或减小音量

    // broadcast RINGER_MODE_CHANGED_ACTION, the ringer mode has changed
        /**
         * The new ringer mode.
         * 
         * @see #RINGER_MODE_CHANGED_ACTION
         * @see #RINGER_MODE_NORMAL
         * @see #RINGER_MODE_SILENT
         * @see #RINGER_MODE_VIBRATE
         */

    // broadcast VIBRATE_SETTING_CHANGED_ACTION, the vibrate setting has changed

    // broadcast VOLUME_CHANGED_ACTION, the volume for a particular stream type changes

    // broadcast ACTION_SCO_AUDIO_STATE_CHANGED, the bluetoooth SCO audio connection state has changed

/**  @hide Default volume index values for audio streams */
public static final int[] DEFAULT_STREAM_VOLUME = new int[] {
    4,  // STREAM_VOICE_CALL
    7,  // STREAM_SYSTEM
    5,  // STREAM_RING
    11, // STREAM_MUSIC
    6,  // STREAM_ALARM
    5,  // STREAM_NOTIFICATION
    11,  // STREAM_BLUETOOTH_SCO
    7,  // STREAM_SYSTEM_ENFORCED
    11, // STREAM_DTMF
    11,  // STREAM_TTS
    11 // STREAM_FM
}

// should only be used by audio settings or main tel app
public void adjustStreamVolume(int streamType, int direction, int flags)

// Adjusts the volume of the most relevant stream, 比如在打电话，就调节电话音量；听音乐，就调节音乐音量
// should only used by audio settings or main tel app
public void adjustVolume(int direction, int flags)

// Adjusts the volume of the most relevant stream, or the given fallback stream.
// should only used by audio settings or main tel app
public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags) 

public int getStreamMaxVolume(int streamType) 

public int getStreamVolume(int streamType) 

public void setStreamVolume(int streamType, int index, int flags)

// Solo or unsolo a particular stream. All other streams are muted.
public void setStreamSolo(int streamType, boolean state)


public void setStreamMute(int streamType, boolean state) 

// should only used by audio settings or main tel app
public void setSpeakerphoneOn(boolean on)

public boolean isSpeakerphoneOn()

public void startBluetoothSco()

public void stopBluetoothSco()

// Request use of Bluetooth SCO headset for communications.
// should only used by audio settings or main tel app
public void setBluetoothScoOn(boolean on)
public boolean isBluetoothScoOn() 

public boolean isWiredHeadsetOn()
    AudioSystem.getDeviceConnectionState(AudioSystem.DEVICE_OUT_WIRED_HEADSET,"") 
    AudioSystem.getDeviceConnectionState(AudioSystem.DEVICE_OUT_WIRED_HEADPHONE,"")

public void setMicrophoneMute(boolean on)
    AudioSystem.muteMicrophone(on);

public boolean isMicrophoneMute() 

public void setMode(int mode) 
    public static final int MODE_NORMAL             = 0;
    public static final int MODE_RINGTONE           = 1;
    public static final int MODE_IN_CALL            = 2;
    public static final int MODE_IN_COMMUNICATION   = 3;

public boolean isMusicActive()
    return AudioSystem.isStreamActive(STREAM_MUSIC);


public void setParameters(String keyValuePairs) {
    AudioSystem.setParameters(keyValuePairs);

public String getParameters(String keys)
        return AudioSystem.getParameters(keys);

// Audio Focus:
    /**
    * Used to indicate a gain of audio focus, or a request of audio focus, of unknown duration.
    * @see OnAudioFocusChangeListener#onAudioFocusChange(int)
    * @see #requestAudioFocus(OnAudioFocusChangeListener, int, int)
    */
    public static final int AUDIOFOCUS_GAIN = 1;

    /* Used to indicate a temporary gain or request of audio focus, 
    anticipated to last a short amount of time */
    public static final int AUDIOFOCUS_GAIN_TRANSIENT = 2;

    /* Interface definition for a callback to be invoked 
    when the audio focus of the system is updated.*/
    public interface OnAudioFocusChangeListener 
        public void onAudioFocusChange(int focusChange);


    /* Helper class to handle the forwarding of 
    audio focus events to the appropriate listener */
    private class FocusEventHandlerDelegate

    public void registerAudioFocusListener(OnAudioFocusChangeListener l)
    public void unregisterAudioFocusListener(OnAudioFocusChangeListener l)

    /* Request audio focus 
       durationHint
        use {@link #AUDIOFOCUS_GAIN_TRANSIENT} to indicate this focus request is temporary and focus will be abandonned shortly
            Examples of transient requests are for the playback of driving directions, or notifications sounds

        Use {@link #AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK} to indicate also that it's ok for 
            the previous focus owner to keep playing if it ducks its audio output.

        Use {@link #AUDIOFOCUS_GAIN} for a focus request of unknown duration such
            as the playback of a song or a video.
    */
    public int requestAudioFocus(OnAudioFocusChangeListener l, int streamType, int durationHint)
        registerAudioFocusListener(l);

        IAudioService service = getService();
        status = service.requestAudioFocus(streamType, durationHint, mICallBack,
                mAudioFocusDispatcher, getIdForAudioFocusListener(l));

    // Abandon audio focus. Causes the previous focus owner, if any, to receive focus.
    public int abandonAudioFocus(OnAudioFocusChangeListener l)
        unregisterAudioFocusListener(l);
        status = service.abandonAudioFocus(mAudioFocusDispatcher,
            getIdForAudioFocusListener(l), mICallBack);


// Register a component to be the sole receiver of MEDIA_BUTTON intents.
public void registerMediaButtonEventReceiver(ComponentName eventReceiver) 
     service.registerMediaButtonEventReceiver(eventReceiver);

public void unregisterMediaButtonEventReceiver(ComponentName eventReceiver) 

public void reloadAudioSettings()

public int getRingerMode()
public void setRingerMode(int ringerMode)
public boolean shouldVibrate(int vibrateType)
public int getVibrateSetting(int vibrateType)
public void setVibrateSetting(int vibrateType, int vibrateSetting)
