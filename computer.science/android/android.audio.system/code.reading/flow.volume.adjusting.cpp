系统中stream的音量管理

    在AudioSystem.java, 总共定义了11种stream
    /* The audio stream for phone calls */
    public static final int STREAM_VOICE_CALL = 0;
    /* The audio stream for system sounds */
    public static final int STREAM_SYSTEM = 1;
    /* The audio stream for the phone ring and message alerts */
    public static final int STREAM_RING = 2;
    /* The audio stream for music playback */
    public static final int STREAM_MUSIC = 3;
    /* The audio stream for alarms */
    public static final int STREAM_ALARM = 4;
    /* The audio stream for notifications */
    public static final int STREAM_NOTIFICATION = 5;
    /* @hide The audio stream for phone calls when connected on bluetooth */
    public static final int STREAM_BLUETOOTH_SCO = 6;
    /* @hide The audio stream for enforced system sounds in certain countries (e.g camera in Japan) */
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    /* @hide The audio stream for DTMF tones */
    public static final int STREAM_DTMF = 8;
    /* @hide The audio stream for text to speech (TTS) */
    public static final int STREAM_TTS = 9;
    /* @hide The audio stream for FM */
    public static final int STREAM_FM = 10;

    private static final int NUM_STREAM_TYPES = 11;
    public static final int getNumStreamTypes()  return NUM_STREAM_TYPES; }

    在AudioService.java中定义了多个stream如何共享同一个音量设置
        如STREAM_MUSIC, STREAM_TTS, STREAM_FM共享音量设置
        如STREAM_VOICE_CALL, STREAM_DTMF共享音量设置
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

////////////////////////////////////////////////////////////////////////////////
// pressing up/down key
policies/base/phone/com/android/internal/policy/impl/PhoneWindow.java|1154| <<global>> audioManager.adjustSuggestedStreamVolume(
    onKeyDown(int featureId, int keyCode, KeyEvent event)
policies/base/phone/com/android/internal/policy/impl/PhoneWindow.java|1335| <<global>> audioManager.adjustSuggestedStreamVolume(
    onKeyUp(int featureId, int keyCode, KeyEvent event)
policies/base/phone/com/android/internal/policy/impl/PhoneWindow.java|1627| <<global>> audioManager.adjustSuggestedStreamVolume(
    public boolean dispatchKeyEvent(KeyEvent event)
        |
        V
base/media/java/android/media/AudioManager.java|428| <<adjustSuggestedStreamVolume>> 
    public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags) 
            |
            V
android.media.AudioService.adjustSuggestedStreamVolume(AudioService.java:425)
    int streamType = getActiveStreamType(suggestedStreamType);
        if (AudioSystem.getForceUse(AudioSystem.FOR_COMMUNICATION) == AudioSystem.FORCE_BT_SCO) 
            return AudioSystem.STREAM_BLUETOOTH_SCO;
         else if (isOffhook || AudioSystem.isStreamActive(AudioSystem.STREAM_VOICE_CALL)) 
            return AudioSystem.STREAM_VOICE_CALL;
         else if (AudioSystem.isStreamActive(AudioSystem.STREAM_MUSIC)) 
            return AudioSystem.STREAM_MUSIC;
         // Hack for FM Radio volume, begin
         else if (AudioSystem.isStreamActive(AudioSystem.STREAM_FM_RADIO)) 
            bool AudioFlinger::isStreamActive(int stream) const
                if(stream == 11) 
                    return (gFMRadioStream == 1);
            return AudioSystem.STREAM_FM_RADIO;
         // Hack for FM Radio volume, begin

         else if (suggestedStreamType == AudioManager.USE_DEFAULT_STREAM_TYPE) 
            return AudioSystem.STREAM_RING;
         else 
            return suggestedStreamType;

    // Hack for FM Radio volume, begin
    if(streamType == AudioSystem.STREAM_FM_RADIO ) 
        if(direction == AudioManager.ADJUST_RAISE ||
                direction == AudioManager.ADJUST_LOWER) 
            Intent intent = new Intent(FM_VOLUME_CHANGE);
            intent.putExtra("type", direction == AudioManager.ADJUST_RAISE ?
                    FM_VOLUME_UP_KEY_PRESS : FM_VOLUME_DOWN_KEY_PRESS);
            mContext.sendBroadcast(intent);
         else if(direction == AudioManager.ADJUST_SAME) 
            // do nothing
        return;
    // Hack for FM radio volume, end

    adjustStreamVolume(streamType, direction, flags);
        // get volume state
        VolumeStreamState streamState = mStreamStates[STREAM_VOLUME_ALIAS[streamType]];

        if (streamState.muteCount() != 0) // If stream is muted, adjust last audible index only
            streamState.adjustLastAudibleIndex(direction);
            sendMsg(mAudioHandler, MSG_PERSIST_VOLUME, streamType,
                    SENDMSG_REPLACE, 0, 1, streamState, PERSIST_DELAY);
            index = streamState.mLastAudibleIndex;
        else
            if (adjustVolume && streamState.adjustIndex(direction))
                sendMsg(mAudioHandler, MSG_SET_SYSTEM_VOLUME, 
                    STREAM_VOLUME_ALIAS[streamType], SENDMSG_NOOP, 0, 0, streamState, 0);
            index = streamState.mIndex;                         |
        // UI                                                   |
        mVolumePanel.postVolumeChanged(streamType, flags);      |
        ./base/core/java/android/view/VolumePanel.java          |
            postVolumeChanged() -> MSG_VOLUME_CHANGED ->        |
               -> onVolumeChanged() -> onShowVolumeChanged()    |
        sendVolumeUpdate(streamType, oldIndex, index);          |
                                                                V
                                    public void handleMessage(Message msg)
                                        case MSG_SET_SYSTEM_VOLUME:
                                            setSystemVolume((VolumeStreamState) msg.obj);
                                                |
                                                V
AudioService.AudioHandler
private void setSystemVolume(VolumeStreamState streamState)
    setStreamVolumeIndex(streamState.mStreamType, streamState.mIndex);
        AudioSystem.setStreamVolumeIndex(stream, (index + 5)/10);
            #anchor_AudioSystem_setStreamVolumeIndex

    // Apply change to all streams using this one as alias
    // for example, 如STREAM_MUSIC, STREAM_TTS, STREAM_FM共享音量设置
    int numStreamTypes = AudioSystem.getNumStreamTypes();
    for (int streamType = numStreamTypes - 1; streamType >= 0; streamType--) 
        if (streamType != streamState.mStreamType &&
                STREAM_VOLUME_ALIAS[streamType] == streamState.mStreamType) 
            setStreamVolumeIndex(streamType, mStreamStates[streamType].mIndex);
            #anchor_AudioSystem_setStreamVolumeIndex

    // Post a persist volume msg
    sendMsg(mAudioHandler, MSG_PERSIST_VOLUME, streamState.mStreamType,
        SENDMSG_REPLACE, 1, 1, streamState, PERSIST_DELAY);
            |
            V
public void handleMessage(Message msg)
    case MSG_PERSIST_VOLUME:
        persistVolume((VolumeStreamState) msg.obj, (msg.arg1 != 0), (msg.arg2 != 0));
        if (current)
            System.putInt(mContentResolver, streamState.mVolumeIndexSettingName, 
                (streamState.mIndex + 5)/ 10);
        if (lastAudible)
            System.putInt(mContentResolver, streamState.mLastAudibleVolumeIndexSettingName, 
                (streamState.mLastAudibleIndex + 5) / 10);

#anchor_AudioSystem_setStreamVolumeIndex
6 FS  m    setStreamVolumeIndex  base/media/java/android/media/AudioService.java
    private void setStreamVolumeIndex(int stream, int index)
       |
       V
4 F   m    setStreamVolumeIndex  base/media/java/android/media/AudioSystem.java
    public static native int setStreamVolumeIndex(int stream, int index);
        |
        V JNI to cpp
1 F C f    setStreamVolumeIndex  base/media/libmedia/AudioSystem.cpp
    status_t AudioSystem::setStreamVolumeIndex(stream_type stream, int index)
           |
           V
5 F   f    setStreamVolumeIndex  base/media/libmedia/IAudioPolicyService.cpp
    virtual status_t setStreamVolumeIndex(AudioSystem::stream_type stream, int index)
               |
               V
3 F   f    setStreamVolumeIndex  base/libs/audioflinger/AudioPolicyService.cpp
    status_t AudioPolicyService::setStreamVolumeIndex(AudioSystem::stream_type stream, int index)
    return mpPolicyManager->setStreamVolumeIndex(stream, index);
                    |
                    V HAL
base/libs/audioflinger/AudioPolicyManagerBase.cpp
    status_t AudioPolicyManagerBase::setStreamVolumeIndex(AudioSystem::stream_type stream, int index)
        mStreams[stream].mIndexCur = index;
        // compute and apply stream volume on all outputs according to connected device
        for (size_t i = 0; i < mOutputs.size(); i++) 
            status_t volStatus = checkAndSetVolume(stream, index, 
                    mOutputs.keyAt(i), mOutputs.valueAt(i)->device()); 
                        |
                        V
status_t AudioPolicyManagerBase::checkAndSetVolume(int stream, int index, 
        audio_io_handle_t output, uint32_t device, int delayMs, bool force)
        // do not change actual stream volume if the stream is muted
        // do not change in call volume if bluetooth is connected and vice versa
        float volume = computeVolume(stream, index, output, device);
        mOutputs.valueFor(output)->mCurVolume[stream] = volume;
        if (stream == AudioSystem::FM) 
            mpClientInterface->setFmVolume(fmVolume, delayMs);
            return;
        else if (voice call)
            mpClientInterface->setVoiceVolume(voiceVolume, delayMs);

        mpClientInterface->setStreamVolume((AudioSystem::stream_type)stream, volume, output, delayMs);
            |
            |
            V
base/libs/audioflinger/AudioPolicyService.cpp
    status_t AudioPolicyService::setStreamVolume(AudioSystem::stream_type stream, 
            float volume, audio_io_handle_t output, int delayMs)
        return mAudioCommandThread->volumeCommand((int)stream, volume, (int)output, delayMs);
            |
            V
status_t AudioPolicyService::AudioCommandThread::volumeCommand(int stream, float volume, int output, int delayMs)
    AudioCommand *command = new AudioCommand();
        command->mCommand = SET_VOLUME;
            |
            V // send the command
bool AudioPolicyService::AudioCommandThread::threadLoop()
    case SET_VOLUME:
    VolumeData *data = (VolumeData *)command->mParam;
    LOGV("AudioCommandThread() processing set volume stream %d, volume %f, output %d", data->mStream, data->mVolume, data->mIO);         
    command->mStatus = AudioSystem::setStreamVolume(data->mStream, data->mVolume, data->mIO);
        |
        V
status_t AudioSystem::setStreamVolume(int stream, float value, int output)
    const sp<IAudioFlinger>& af = AudioSystem::get_audio_flinger();
    af->setStreamVolume(stream, value, output);
        |
        V
status_t AudioFlinger::setStreamVolume(int stream, float value, int output)
    // check if there are any playback thread for the output
    PlaybackThread *thread = NULL;
    if (output) 
        thread = checkPlaybackThread_l(output);
        if (thread == NULL) 
            return BAD_VALUE;

    mStreamTypes[stream].volume = value;
    if (thread == NULL)
        for (uint32_t i = 0; i < mPlaybackThreads.size(); i++)
            mPlaybackThreads.valueAt(i)->setStreamVolume(stream, value);
    else 
        thread->setStreamVolume(stream, value);
            |
            V
status_t AudioFlinger::PlaybackThread::setStreamVolume(int stream, float value)
        mStreamTypes[stream].volume = value;

////////////////////////////////////////////////////////////////////////////////
对于MediaPlayer, 调节音量实际上是调节PCM码的值. 比如一个8bit的PCM的振幅是从0到255
用户增加音量实际上是在PCM码的原始的值增加一个量

实现是在在AudioMixer中
    // one track, 16 bits stereo without resampling is the most common case
    void AudioMixer::process__OneTrack16BitsStereoNoResampling(state_t* state, void* output)
        while (numFrames)   
            // 读入一个pcm输入
            b.frameCount = numFrames;      
            t.bufferProvider->getNextBuffer(&b);
            int16_t const *in = b.i16; 
            do {
                uint32_t rl = *reinterpret_cast<uint32_t const *>(in);
                in += 2;
                // 计算输出的音量
                int32_t l = mulRL(1, rl, vrl) >> 12;
                int32_t r = mulRL(0, rl, vrl) >> 12;
                // clamping...
                l = clamp16(l);
                r = clamp16(r);
                *out++ = (r<<16) | (l & 0xFFFF);
            } while (--outFrames);


////////////////////////////////////////////////////////////////////////////////

hardware/alsa_sound/AudioPolicyManagerALSA.h
V/AudioPolicyManagerALSA( 1637): setStreamVolumeIndex() stream 5, index 2
V/AudioPolicyManagerALSA( 1637): setStreamVolume() for output 1 stream 5, volume 0.083176, delay 0

setVolumeControlStream
    1 F   m    setVolumeControlStream  base/core/java/android/app/Activity.java
        public final void setVolumeControlStream(int streamType) 
    2 F   m    setVolumeControlStream  base/core/java/android/app/Dialog.java
        public final void setVolumeControlStream(int streamType) 
            |
            V
    3 F   m    setVolumeControlStream  base/core/java/android/view/Window.java
        public abstract void setVolumeControlStream(int streamType);
            |
            V
    5 F   m    setVolumeControlStream  policies/base/phone/com/android/internal/policy/impl/PhoneWindow.java
        public void setVolumeControlStream(int streamType) 

status_t AudioPolicyManagerBase::checkAndSetVolume(int stream, int index, audio_io_handle_t output, uint32_t device, int delayMs, bool force)
    mpClientInterface->setStreamVolume((AudioSystem::stream_type)stream, volume, output, delayMs);



policies/base/phone/com/android/internal/policy/impl/PhoneWindowManager.java

./package
    ./apps/Music/src/com/android/music/VideoBrowserActivity.java:44:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/StreamStarter.java:40:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/AlbumBrowserActivity.java:97:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/WeekSelector.java:46:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/PlaylistBrowserActivity.java:96:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/MediaPlaybackActivity.java:99:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/TrackBrowserActivity.java:118:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/ArtistAlbumBrowserActivity.java:88:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/CreatePlaylist.java:49:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/QueryBrowserActivity.java:80:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/ScanningProgress.java:65:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/DeleteItems.java:48:        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    ./apps/Music/src/com/android/music/RenamePlaylist.java:51:        setVolumeControlStream(AudioManager.STREAM_MUSIC);


////////////////////////////////////////////////////////////////////////////////
// set fm radio stream
1 F C m    setParameters     base/media/java/android/media/AudioManager.java
public void setParameters(String keyValuePairs)
    |
    V
 23 F   m    setParameters     base/media/java/android/media/AudioSystem.java
public static native int setParameters(String keyValuePairs);
        |
        V
24 F   f    setParameters     base/media/libmedia/AudioSystem.cpp
status_t AudioSystem::setParameters(audio_io_handle_t ioHandle, const String8& keyValuePairs) 
            |
            V
25 F   f    setParameters     base/media/libmedia/IAudioFlinger.cpp
class:android::BpAudioFlinger
virtual status_t setParameters(int ioHandle, const String8& keyValuePairs)
                |
                V
13 F   f    setParameters     base/libs/audioflinger/AudioFlinger.cpp
status_t AudioFlinger::setParameters(int ioHandle, const String8& keyValuePairs)
      // Hack for FM Radio volume, begin
      if(0 == strncmp(keyValuePairs.string(), "FM_DEVICE=ON",
                  strlen("FM_DEVICE=ON"))) 
          LOGI("setParameters FM Radio ON");
          gFMRadioStream = 1;
      } else if(0 == strncmp(keyValuePairs.string(), "FM_DEVICE=OFF",
                  strlen("FM_DEVICE=OFF"))) 
          LOGI("setParameters FM Radio OFF");
          gFMRadioStream = 0;
      }
      // Hack for FM Radio volume, end
            |
            V
    result = mAudioHardware->setParameters(keyValuePairs);
        |
        V
hardware/alsa_sound/AudioHardwareALSA.h
    virtual status_t    setParameters(const String8& keyValuePairs) 
        return ALSAStreamOps::setParameters(keyValuePairs);

V/AudioHardwareALSA( 1641): setParameters() FM_DEVICE=OFF
D/AudioHardwareALSA( 1641): setParameter [FM_DEVICE][OFF]
D/AudioHardwareALSA( 1641): Disable FM audio path control
D/AudioHardwareALSA( 1641): setAnalogLoop close


19 F   f    setParameters     base/libs/audioflinger/AudioPolicyService.cpp
class:android::AudioPolicyService
void AudioPolicyService::setParameters(audio_io_handle_t ioHandle, const String8& keyValuePairs, int delayMs)


================================================================================
需要将FM Radio的音量从音乐中独立出来
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
    // FIXME: AudioSystem.STREAM_MUSIC  // STREAM_FM
    AudioSystem.STREAM_FM  // STREAM_FM
};

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
    20 // STREAM_FM FIXME: step 10
};


// Added string for FM UI 
mVolumePanel.postVolumeChanged(streamType, flags);      
./base/core/java/android/view/VolumePanel.java         
    postVolumeChanged() -> MSG_VOLUME_CHANGED ->      
       -> onVolumeChanged() -> onShowVolumeChanged() 
    sendVolumeUpdate(streamType, oldIndex, index);      

    // FIXME: I need to add a string in PEKALL resource
    base/core/java/android/view/VolumePanel.java
        protected void onShowVolumeChanged(int streamType, int flags) 

添加setting中的改动
    System.VOLUME_SETTINGS
    System.VOLUME_SETTINGS

VolumeStreamState

bug
    applyStreamVolumes need to speed up
    0 volume , power off fm, power on fm

