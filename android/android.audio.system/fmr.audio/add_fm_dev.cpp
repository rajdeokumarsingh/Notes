// HAL interface
// FIXME: what the different between music
    setFmVolume
        ret = mAudioHardware->setFmVolume(value);

    setParameters
        String8 fmOnKey = String8(AudioParameter::keyFmOn);
        String8 fmOffKey = String8(AudioParameter::keyFmOff);
        if (param.getInt(fmOnKey, device) == NO_ERROR)
            mFmOn = true;
            // Call hardware to switch FM on/off
            mAudioHardware->setParameters(keyValuePairs);
        else if (param.getInt(fmOffKey, device) == NO_ERROR)
            mFmOn = false;
            // Call hardware to switch FM on/off
            mAudioHardware->setParameters(keyValuePairs);

////////////////////////////////////////////////////////////////////////////////
frameworks/base/core/jni/
    AndroidRuntime.cpp
        // Added an interface
        extern int register_android_hardware_fm_fmradio(JNIEnv* env);
        REG_JNI(register_android_hardware_fm_fmradio),

frameworks/base/include/media/
    ////////////////////////////////////////////////////////////////////////////////
    AudioSystem.h
        class AudioSystem
            // added a stream type
            enum stream_type 
                FM = 10,

            // added several audio devices
            enum audio_devices
                // output device
                DEVICE_OUT_FM = 0x800,
                DEVICE_OUT_FM_SPEAKER = 0x1000,
                DEVICE_OUT_FM_ALL = (DEVICE_OUT_FM | DEVICE_OUT_FM_SPEAKER),

                // input device
                DEVICE_IN_FM_RX = 0x1000000,
                DEVICE_IN_FM_RX_A2DP = 0x2000000,

                // expand DEVICE_OUT_ALL and DEVICE_IN_ALL
                DEVICE_OUT_ALL
                DEVICE_IN_ALL

            // added interfaces
            static status_t setFmVolume(float volume);
            static bool isFmDevice(audio_devices device);

        class AudioParameter
            // added parameter for FMR
            static const char *keyFmOn;
            static const char *keyFmOff;

    ////////////////////////////////////////////////////////////////////////////////
    IAudioFlinger.h
        class IAudioFlinger
            // added an interface
            virtual status_t setFmVolume(float volume) = 0;

    ////////////////////////////////////////////////////////////////////////////////
    mediarecorder.h
        // added two audio source
        enum audio_source
            AUDIO_SOURCE_FM_RX = 8,
            AUDIO_SOURCE_FM_RX_A2DP = 9,
    ////////////////////////////////////////////////////////////////////////////////

frameworks/base/media/libmedia/
    ////////////////////////////////////////////////////////////////////////////////
    AudioSystem.cpp
        // define parameters
        const char *AudioParameter::keyFmOn = "fm_on";
        const char *AudioParameter::keyFmOff = "fm_off";

        // implemented methods
        status_t AudioSystem::setFmVolume(float value)
            AudioSystem::get_audio_flinger()->setFmVolume(value);

        bool AudioSystem::isFmDevice(audio_devices device)
            if((popCount(device) == 1) &&
                (device & ~AudioSystem::DEVICE_OUT_FM_ALL) == 0)
                    return true;

    ////////////////////////////////////////////////////////////////////////////////
    IAudioFlinger.cpp
        // implement IPC method
        virtual status_t setFmVolume(float volume)

    ////////////////////////////////////////////////////////////////////////////////

frameworks/base/services/audioflinger/
    ////////////////////////////////////////////////////////////////////////////////
    // ----------------------------------------------------------------------------
    // AudioPolicyManagerBase implements audio policy manager behavior common to all platforms.
    // Each platform must implement an AudioPolicyManager class derived from AudioPolicyManagerBase
    // and override methods for which the platform specific behavior differs from the implementation
    // in AudioPolicyManagerBase. Even if no specific behavior is required, the AudioPolicyManager
    // class must be implemented as well as the class factory function createAudioPolicyManager()
    // and provided in a shared library libaudiopolicy.so.
    // ----------------------------------------------------------------------------
    AudioPolicyManagerBase.cpp
     //created by AudioPolicyService 
     // for GENERIC_AUDIO, AUDIO_POLICY_TEST or emulator

        // expanded this interface to switch FMR on/off
        // The FMR application invokes AudioSystem.setDeviceConnectionState to get here
        status_t AudioPolicyManagerBase::setDeviceConnectionState(
            AudioSystem::audio_devices device, 
            AudioSystem::device_connection_state state, 
            const char *device_address)

            // handle output devices
            if (AudioSystem::isOutputDevice(device)) 
                switch (state)
                    // handle output device connection
                    case AudioSystem::DEVICE_STATE_AVAILABLE:
                        // register new device as available
                        mAvailableOutputDevices |= device;
                    #ifdef HAVE_FM_RADIO
                        if (AudioSystem::isFmDevice(device))
                            AudioOutputDescriptor *hwOutputDesc = mOutputs.valueFor(mHardwareOutput);
                            hwOutputDesc->mRefCount[AudioSystem::FM] = 1;
                            AudioParameter param = AudioParameter();
                            param.addInt(String8(AudioParameter::keyFmOn), mAvailableOutputDevices);
                            mpClientInterface->setParameters(mHardwareOutput, param.toString());
                    #endif

                    // handle output device disconnection
                    case AudioSystem::DEVICE_STATE_UNAVAILABLE:
                        // remove device from available output devices
                        mAvailableOutputDevices &= ~device;
                    #ifdef HAVE_FM_RADIO
                        if (AudioSystem::isFmDevice(device))
                            AudioOutputDescriptor *hwOutputDesc = mOutputs.valueFor(mHardwareOutput);
                            hwOutputDesc->mRefCount[AudioSystem::FM] = 0;
                            AudioParameter param = AudioParameter();
                            param.addInt(String8(AudioParameter::keyFmOff), mAvailableOutputDevices);
                            mpClientInterface->setParameters(mHardwareOutput, param.toString());
                    #endif

        // expanded the method
        void AudioPolicyManagerBase::setForceUse(AudioSystem::force_use usage, AudioSystem::forced_config config)
            case AudioSystem::FOR_MEDIA:                  
            if (config != AudioSystem::FORCE_HEADPHONES && config != AudioSystem::FORCE_BT_A2DP &&
        #ifdef HAVE_FM_RADIO
                    config != AudioSystem::FORCE_WIRED_ACCESSORY && config != AudioSystem::FORCE_SPEAKER &&
                    config != AudioSystem::FORCE_NONE) {
        #else
                config != AudioSystem::FORCE_WIRED_ACCESSORY && config != AudioSystem::FORCE_NONE) {
        #endif
                LOGW("setForceUse() invalid config %d for FOR_MEDIA", config);
                return;

        // expanded the method
        AudioPolicyManagerBase::routing_strategy AudioPolicyManagerBase::getStrategy(
                        AudioSystem::stream_type stream) 
            switch (stream) 
                case AudioSystem::VOICE_CALL:
                case AudioSystem::BLUETOOTH_SCO:
                    return STRATEGY_PHONE;
                case AudioSystem::RING:
                case AudioSystem::NOTIFICATION:
                case AudioSystem::ALARM:
                case AudioSystem::ENFORCED_AUDIBLE:
                    return STRATEGY_SONIFICATION;
                case AudioSystem::DTMF:
                    return STRATEGY_DTMF;

                case AudioSystem::SYSTEM:
                    // NOTE: SYSTEM stream uses MEDIA strategy because muting music and switching outputs
                    // while key clicks are played produces a poor result
                case AudioSystem::TTS:
                case AudioSystem::MUSIC:
#ifdef HAVE_FM_RADIO
                case AudioSystem::FM:
#endif
                    return STRATEGY_MEDIA;

        // expaned the method
        // The priority of each device is different for different strategy
        // FIXME: key function, need to dig into  ...
        uint32_t AudioPolicyManagerBase::getDeviceForStrategy(routing_strategy strategy, bool fromCache)
            switch (strategy)
                ...
                case STRATEGY_PHONE:
                ...
                case STRATEGY_MEDIA:
                    uint32_t device2 = 0;
                    if (mForceUse[AudioSystem::FOR_MEDIA] == AudioSystem::FORCE_SPEAKER)
                        device2 = mAvailableOutputDevices & AudioSystem::DEVICE_OUT_SPEAKER;
                    if (device2 == 0)
                        device2 = mAvailableOutputDevices & AudioSystem::DEVICE_OUT_AUX_DIGITAL;

        // expanded the method for FMR
        status_t AudioPolicyManagerBase::checkAndSetVolume(int stream, int index, 
            audio_io_handle_t output, uint32_t device, int delayMs, bool force)
        #ifdef HAVE_FM_RADIO
            else if (stream == AudioSystem::FM) {
                float fmVolume = -1.0;
                fmVolume = computeVolume(stream, index, output, device);
                if (fmVolume >= 0 && output == mHardwareOutput) {
                    mpClientInterface->setFmVolume(fmVolume, delayMs);
                }
                return NO_ERROR;
        #endif

    ////////////////////////////////////////////////////////////////////////////////
    AudioPolicyService.h
        class AudioPolicyService: public BnAudioPolicyService, 
            public AudioPolicyClientInterface, public IBinder::DeathRecipient

            // added an interface
            virtual status_t setFmVolume(float volume, int delayMs = 0);

            // expanded the inner class
            class AudioCommandThread : public Thread
                // commands for tone AudioCommand
                // added a command
                enum
                    START_TONE,
                    STOP_TONE,
                    SET_VOLUME,
                    SET_PARAMETERS,
                    SET_VOICE_VOLUME,
                #ifdef HAVE_FM_RADIO
                    SET_FM_VOLUME
                #endif

                // added a interface
                status_t fmVolumeCommand(float volume, int delayMs = 0);

            // added an inner class for the FMR
            class FmVolumeData 
                float mVolume;

    ////////////////////////////////////////////////////////////////////////////////
    AudioPolicyService.cpp
        // implemented the interface
        status_t AudioPolicyService::setFmVolume(float volume, int delayMs)
            return mAudioCommandThread->fmVolumeCommand(volume, delayMs);

        // implemented the method
        status_t AudioPolicyService::AudioCommandThread::fmVolumeCommand(float volume, int delayMs)
            // create an FMR volume command, insert it to command queue

        // expanded the method for the FMR volume command
        // FIXME: what is a command? command list
        void AudioPolicyService::AudioCommandThread::insertCommand_l(AudioCommand *command, int delayMs)
            switch (command->mCommand)
                case SET_FM_VOLUME:
                    removedCommands.add(command2);

        // expanded the method
        bool AudioPolicyService::AudioCommandThread::threadLoop()
            while (!exitPending())
                while(!mAudioCommands.isEmpty())
                    switch (command->mCommand)
                #ifdef HAVE_FM_RADIO    
                        case SET_FM_VOLUME: {
                            FmVolumeData *data = (FmVolumeData *)command->mParam;
                            LOGV("AudioCommandThread() processing set fm volume volume %f", data->mVolume);
                            command->mStatus = AudioSystem::setFmVolume(data->mVolume);
                            if (command->mWaitStatus) {
                                command->mCond.signal();
                                mWaitWorkCV.wait(mLock);
                            }
                            delete data;
                        }break;
                #endif
 
    ////////////////////////////////////////////////////////////////////////////////
    AudioFlinger.h
        class AudioFlinger : public BinderService<AudioFlinger>, public BnAudioFlinger
            // added interfaces:
            virtual status_t setFmVolume(float volume);

            // expand a hardware state
            enum hardware_call_state
                AUDIO_SET_FM_VOLUME

            // added a state for FM
            bool mFmOn;

    ////////////////////////////////////////////////////////////////////////////////
    AudioFlinger.cpp
        // function modified
        bool AudioFlinger::isStreamActive(int stream) const
            // ...
            if (mFmOn && stream == AudioSystem::MUSIC)
                return true;

        // implemented the interface
        status_t AudioFlinger::setFmVolume(float value)
            ret = mAudioHardware->setFmVolume(value);

        // function modified, switch FMR on/off
        status_t AudioFlinger::setParameters(int ioHandle, const String8& keyValuePairs)

            String8 fmOnKey = String8(AudioParameter::keyFmOn);
            String8 fmOffKey = String8(AudioParameter::keyFmOff);
            if (param.getInt(fmOnKey, device) == NO_ERROR) {
                mFmOn = true;
                // Call hardware to switch FM on/off
                mAudioHardware->setParameters(keyValuePairs);
            } else if (param.getInt(fmOffKey, device) == NO_ERROR) {
                mFmOn = false;
                // Call hardware to switch FM on/off
                mAudioHardware->setParameters(keyValuePairs);
            }

    ////////////////////////////////////////////////////////////////////////////////
    A2dpAudioInterface.h
        // added an interface
        class A2dpAudioInterface : public AudioHardwareBase
            virtual status_t    setFmVolume(float volume);

    ////////////////////////////////////////////////////////////////////////////////
    A2dpAudioInterface.cpp
        // implemented the interface
        status_t A2dpAudioInterface::setFmVolume(float v)
            mHardwareInterface->setFmVolume(v);

    ////////////////////////////////////////////////////////////////////////////////
    AudioDumpInterface.h
        // added an interface
        class AudioDumpInterface : public AudioHardwareBase
            virtual status_t    setFmVolume(float volume)
                {return mFinalInterface->setFmVolume(volume);}

    ////////////////////////////////////////////////////////////////////////////////
    AudioHardwareGeneric.h
        class AudioHardwareGeneric : public AudioHardwareBase
            // added an interface
            virtual status_t    setFmVolume(float volume);

    ////////////////////////////////////////////////////////////////////////////////
    AudioHardwareGeneric.cpp
        // NOT implemented
        status_t AudioHardwareGeneric::setFmVolume(float v)
            return NO_ERROR;

    ////////////////////////////////////////////////////////////////////////////////
    AudioHardwareStub.h
        class AudioHardwareStub : public  AudioHardwareBase
            // added an interface
            virtual status_t    setFmVolume(float volume);

    ////////////////////////////////////////////////////////////////////////////////
    AudioHardwareStub.cpp
        // NOT implemented
        status_t AudioHardwareStub::setFmVolume(float volume)
            return NO_ERROR;    

                   
frameworks/base/media/java/android/media/

frameworks/base/libs/audioflinger/

hardware
////////////////////////////////////////////////////////////////////////////////
./libhardware_legacy/include/hardware_legacy/AudioHardwareInterface.h
    // add set volume interface
    class AudioHardwareInterface
        setFmVolume(float volume) 

./libhardware_legacy/include/hardware_legacy/AudioPolicyInterface.h
    // add set volume interface
    class AudioPolicyClientInterface
        setFmVolume(float volume, int delayMs = 0)

////////////////////////////////////////////////////////////////////////////////
./msm7k/libaudio-qsd8k/AudioHardware.h:238:#ifdef HAVE_FM_RADIO
    // add interfaces
    class AudioHardware : public  AudioHardwareBase
        setFmVolume(float volume)
        setFmOnOff(bool onoff);

./msm7k/libaudio-qsd8k/AudioHardware.cpp
    // expand setParameters
    AudioHardware::setParameters(const String8& keyValuePairs)
        key = String8(AudioParameter::keyFmOn);
        setFmOnOff(true);
        setFmOnOff(false);

    // implement set volume
    AudioHardware::setFmVolume(float v)
    set_volume_fm(uint32_t volume)

////////////////////////////////////////////////////////////////////////////////
./msm7k/libaudio-qdsp5v2/AudioPolicyManager.h
    class AudioPolicyManager: public AudioPolicyManagerBase
        // AudioPolicyInterface
        // handle device connection state
        virtual status_t setDeviceConnectionState(AudioSystem::audio_devices device,
              AudioSystem::device_connection_state state,
              const char *device_address);
        void setOutputDevice(audio_io_handle_t output, uint32_t device, bool force = false, int delayMs = 0);

./msm7k/libaudio-qdsp5v2/AudioPolicyManager.cpp
    // implement methods:
    AudioPolicyManager::setDeviceConnectionState
    void AudioPolicyManager::setOutputDevice(audio_io_handle_t output, uint32_t device, bool force, int delayMs)
        // do the routing
        AudioParameter param = AudioParameter();
        param.addInt(String8(AudioParameter::keyRouting), (int)device);
        mpClientInterface->setParameters(mHardwareOutput, param.toString(), delayMs);

    // expand method:
    AudioPolicyManager::getDeviceForStrategy(routing_strategy strategy, bool fromCache)
        case STRATEGY_MEDIA:
            //To route FM stream to speaker when headset is connected, a new switch case is added.
            //case AudioSystem::FORCE_SPEAKER for STRATEGY_MEDIA will come only when we need to route
            //FM stream to speaker.

////////////////////////////////////////////////////////////////////////////////
./msm7k/libaudio/AudioHardware.cpp
./msm7k/libaudio/AudioHardware.h
////////////////////////////////////////////////////////////////////////////////

class hierachy

AudioHardwareInterface
AudioPolicyClientInterface

AudioHardware<-AudioHardwareBase<-AudioHardwareInterface
 setFmOnOff                           setFmVolume

AudioPolicyManager<-AudioPolicyManagerBase<-AudioPolicyInterface




