base/media/java/android/media/MediaRecorder.java
    class:MediaRecorder
    public native void setAudioSource(int audio_source)
        |
        V
base/media/jni/android_media_MediaRecorder.cpp
    sp<MediaRecorder> mr = getMediaRecorder(env, thiz);
    process_media_recorder_call(env, mr->setAudioSource(as), 
        "java/lang/RuntimeException", "setAudioSource failed.");
            |
            V
base/media/libmedia/mediarecorder.cpp
    class:android::MediaRecorder
    status_t MediaRecorder::setAudioSource(int as)
        sp<IMediaRecorder> mMediaRecorder;
            |
            V
base/media/libmedia/IMediaRecorder.cpp
    class:android::BpMediaRecorder
    status_t setAudioSource(int as)
                |
                | binder IPC
                V
base/media/libmediaplayerservice/MediaRecorderClient.cpp
    class MediaRecorderClient : public BnMediaRecorder
    status_t MediaRecorderClient::setAudioSource(int as)
                    |
                    V
base/media/libmediaplayerservice/StagefrightRecorder.cpp
    class:android::StagefrightRecorder
    status_t StagefrightRecorder::setAudioSource(audio_source as) 
        mAudioSource = as;
                        |
                        |
                        V
// FIXME: mAudioSource is used for create AudioSource
struct StagefrightRecorder : public MediaRecorderBase 
    sp<MediaSource> StagefrightRecorder::createAudioSource()
         sp<AudioSource> audioSource = new AudioSource( 
                 mAudioSource, mSampleRate, mAudioChannels);

////////////////////////////////////////////////////////////////////////////////
base/media/libmedia/AudioRecord.cpp

AudioRecord::AudioRecord( int inputSource, uint32_t sampleRate, int format, 
    uint32_t channels, int frameCount, uint32_t flags, callback_t cbf, void* user, 
    int notificationFrames, int sessionId) : mStatus(NO_INIT), mSessionId(0)
    mStatus = set(inputSource, sampleRate, format, channels, 
            frameCount, flags, cbf, user, notificationFrames, sessionId);
                |
                V
    status_t AudioRecord::set(int inputSource, uint32_t sampleRate, int format, 
        uint32_t channels, int frameCount, uint32_t flags, callback_t cbf, void* user, 
        int notificationFrames, bool threadCanCallJava, int sessionId)
    audio_io_handle_t input = AudioSystem::getInput(inputSource,
            sampleRate, format, channels, (AudioSystem::audio_in_acoustics)flags);
                    |
                    V
audio_io_handle_t AudioSystem::getInput(int inputSource, uint32_t samplingRate,
        uint32_t format, uint32_t channels, audio_in_acoustics acoustics)
    const sp<IAudioPolicyService>& aps = AudioSystem::get_audio_policy_service();
    return aps->getInput(inputSource, samplingRate, format, channels, acoustics);
                        |
                        V
audio_io_handle_t AudioPolicyService::getInput(int inputSource, uint32_t samplingRate, 
uint32_t format, uint32_t channels, AudioSystem::audio_in_acoustics acoustics)
    return mpPolicyManager->getInput(inputSource, samplingRate, format, channels, acoustics);
                            |
                            V
audio_io_handle_t AudioPolicyManagerBase::getInput(int inputSource, uint32_t samplingRate, 
    uint32_t format, uint32_t channels, AudioSystem::audio_in_acoustics acoustics)
    // AUDIO_SOURCE_FM_RX = 8, is for FMR recording
    uint32_t device = getDeviceForInputSource(inputSource);
    AudioInputDescriptor *inputDesc = new AudioInputDescriptor();
    inputDesc->mInputSource = inputSource;
    inputDesc->mDevice = device;
    inputDesc->mSamplingRate = samplingRate;
    inputDesc->mFormat = format;
    inputDesc->mChannels = channels;
    inputDesc->mAcoustics = acoustics;
    inputDesc->mRefCount = 0;
    input = mpClientInterface->openInput(&inputDesc->mDevice,
            &inputDesc->mSamplingRate,
            &inputDesc->mFormat,
            &inputDesc->mChannels,
            inputDesc->mAcoustics);

                base/include/media/AudioSystem.h
                    enum audio_devices 
                        ...
                        // input devices
                        DEVICE_IN_COMMUNICATION = 0x10000,
                        DEVICE_IN_AMBIENT = 0x20000,
                        DEVICE_IN_BUILTIN_MIC = 0x40000,
                        DEVICE_IN_BLUETOOTH_SCO_HEADSET = 0x80000,
                        DEVICE_IN_WIRED_HEADSET = 0x100000,
                        DEVICE_IN_AUX_DIGITAL = 0x200000,
                        DEVICE_IN_VOICE_CALL = 0x400000,
                        DEVICE_IN_BACK_MIC = 0x800000,
                #ifdef HAVE_FM_RADIO
                        DEVICE_IN_FM_RX = 0x1000000,
                #endif

    mInputs.add(input, inputDesc);
    return input;

int AudioFlinger::openInput(uint32_t *pDevices, uint32_t *pSamplingRate, 
        uint32_t *pFormat, uint32_t *pChannels,uint32_t acoustics)
    // FIXME: key function
    AudioStreamIn *input = mAudioHardware->openInputStream(*pDevices,
            (int *)&format,
            &channels,
            &samplingRate,
            &status,
            (AudioSystem::audio_in_acoustics)acoustics);

    int id = nextUniqueId();       
    // Start record thread         
    thread = new RecordThread(this, input, reqSamplingRate, reqChannels, id);
    mRecordThreads.add(id, thread);

    input->standby();

    // notify client processes of the new input creation
    thread->audioConfigChanged_l(AudioSystem::INPUT_OPENED);
    return id;

status_t AudioRecord::set( int inputSource, uint32_t sampleRate, int format, 
    uint32_t channels, int frameCount, uint32_t flags, callback_t cbf, void* user, 
    int notificationFrames, bool threadCanCallJava, int sessionId)

    audio_io_handle_t input = AudioSystem::getInput(inputSource,
            sampleRate, format, channels, (AudioSystem::audio_in_acoustics)flags);

    // create the IAudioRecord
    status = openRecord(sampleRate, format, channelCount,
            frameCount, flags, input);

    mClientRecordThread = new ClientRecordThread(*this, threadCanCallJava);

    mInput = input;

