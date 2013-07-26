
src/com/android/fm/radio/FMRadio.java
    private void switchToSpeaker()
        AudioSystem.setForceUse(AudioSystem.FOR_MEDIA, AudioSystem.FORCE_SPEAKER);                                                           
        AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_FM, AudioSystem.DEVICE_STATE_UNAVAILABLE, "");                           
        AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_FM, AudioSystem.DEVICE_STATE_AVAILABLE, "");

    private void switchToHeadset() 
        AudioSystem.setForceUse(AudioSystem.FOR_MEDIA, AudioSystem.FORCE_NONE);                                                              
        AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_FM, AudioSystem.DEVICE_STATE_UNAVAILABLE, "");                           
        AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_FM, AudioSystem.DEVICE_STATE_AVAILABLE, "");   

AudioSystem.setForceUse(AudioSystem.FOR_MEDIA, AudioSystem.FORCE_NONE);                                                              
        |
        V function call
base/media/java/android/media/AudioSystem.java
    public static native int setForceUse(int usage, int config);
        |
        V JNI 
base/media/libmedia/AudioSystem.cpp
    status_t AudioSystem::setForceUse(force_use usage, forced_config config)
        return AudioSystem::get_audio_policy_service()->setForceUse(usage, config);
            |
            V IPC call
base/services/audioflinger/AudioPolicyService.cpp
    status_t AudioPolicyService::setForceUse(
        AudioSystem::force_use usage, AudioSystem::forced_config config)
        mpPolicyManager->setForceUse(usage, config);
            |
            V function call
base/services/audioflinger/AudioPolicyManagerBase.cpp
    void AudioPolicyManagerBase::setForceUse(AudioSystem::force_use usage, 
            AudioSystem::forced_config config)
        case AudioSystem::FOR_MEDIA:
            mForceUse[usage] = config;

AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_FM, AudioSystem.DEVICE_STATE_UNAVAILABLE, "");                           
    |
    V
status_t AudioPolicyManagerBase::setDeviceConnectionState(AudioSystem::audio_devices device,
                                                  AudioSystem::device_connection_state state,
                                                  const char *device_address)
        #ifdef HAVE_FM_RADIO
        if (AudioSystem::isFmDevice(device)) {
            AudioOutputDescriptor *hwOutputDesc = mOutputs.valueFor(mHardwareOutput);
            hwOutputDesc->mRefCount[AudioSystem::FM] = 0;
            AudioParameter param = AudioParameter();
            param.addInt(String8(AudioParameter::keyFmOff), mAvailableOutputDevices);
            mpClientInterface->setParameters(mHardwareOutput, param.toString());
        }
        #endif

        // request routing change if necessary
        uint32_t newDevice = getNewDevice(mHardwareOutput, false); func call -->{
            device = getDeviceForStrategy(strategy); func call-->{
                uint32_t device = 0;
                case STRATEGY_MEDIA:
                 uint32_t device2 = 0;
                 if (mForceUse[AudioSystem::FOR_MEDIA] == AudioSystem::FORCE_SPEAKER) {
                     device2 = mAvailableOutputDevices & AudioSystem::DEVICE_OUT_SPEAKER;
                 }
                 if (device2 == 0) {
                     device2 = mAvailableOutputDevices & AudioSystem::DEVICE_OUT_AUX_DIGITAL;
                 }
            }
        }
        updateDeviceForStrategy();
        setOutputDevice(mHardwareOutput, newDevice); {
            AudioOutputDescriptor *outputDesc = mOutputs.valueFor(output);
            outputDesc->mDevice = device;

            // FIXME: do the routing
            AudioParameter param = AudioParameter();
            param.addInt(String8(AudioParameter::keyRouting), (int)device);
            mpClientInterface->setParameters(mHardwareOutput, param.toString(), delayMs);

            // update stream volumes according to new device
            applyStreamVolumes(output, device, delayMs);
        }

/*
audio_io_handle_t AudioPolicyManagerBase::getOutput(
    AudioSystem::stream_type stream, uint32_t samplingRate, 
    uint32_t format, uint32_t channels, AudioSystem::output_flags flags)

    routing_strategy strategy = getStrategy((AudioSystem::stream_type)stream); func call-->{
        case AudioSystem::FM: return STRATEGY_MEDIA;
    }
    uint32_t device = getDeviceForStrategy(strategy); func call-->{
        uint32_t device = 0;
            case STRATEGY_MEDIA: {
    #ifdef HAVE_FM_RADIO
            uint32_t device2 = 0;
            if (mForceUse[AudioSystem::FOR_MEDIA] == AudioSystem::FORCE_SPEAKER) {
                device2 = mAvailableOutputDevices & AudioSystem::DEVICE_OUT_SPEAKER;
            }
            if (device2 == 0) {
                device2 = mAvailableOutputDevices & AudioSystem::DEVICE_OUT_AUX_DIGITAL;
            }
    #else
        return device;
    }
*/

