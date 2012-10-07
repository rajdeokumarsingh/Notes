
// using setDeviceConnectionState to open/close FMR in the framework
// using setParameters to open/close FMR in the HAL

src/com/android/fm/radio/FMRadioService.java
    mAudioManager.registerMediaButtonEventReceiver(
        new ComponentName(getPackageName(), FMMediaButtonIntentReceiver.class.getName()));
    mAudioManager.requestAudioFocus(
        mAudioFocusListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

     startFM();
        AudioSystem.setDeviceConnectionState(AudioSystem.DEVICE_OUT_FM, AudioSystem.DEVICE_STATE_AVAILABLE, "");
    /* Put the FMR hardware into normal mode */             |
    bStatus = setLowPowerMode(false);                       |
                                                            |
frameworks/base/media/java/android/media/AudioSystem.java   V function call
    public static native int setDeviceConnectionState(int device, int state, String device_address);
        |
        V JNI 
frameworks/base/media/libmedia/AudioSystem.cpp
    status_t AudioSystem::setDeviceConnectionState(audio_devices device,
            device_connection_state state, const char *device_address)
    return AudioSystem::get_audio_policy_service()->setDeviceConnectionState(device, state, device_address);
            |
            V function call
frameworks/base/services/audioflinger/AudioPolicyService.cpp
    status_t AudioPolicyService::setDeviceConnectionState(AudioSystem::audio_devices device,
            AudioSystem::device_connection_state state, const char *device_address)

        if (!AudioSystem::isOutputDevice(device) && !AudioSystem::isInputDevice(device))
            return BAD_VALUE;                         

        return mpPolicyManager->setDeviceConnectionState(device, state, device_address);
                |
                V function call
frameworks/base/services/audioflinger/AudioPolicyManagerBase.cpp
        // expanded this interface to switch FMR on/off
        // AudioPolicyManagerBase is base class of hardware AudioPolicyManger
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
                    #endif      |
                                V function call
frameworks/base/services/audioflinger/AudioPolicyService.cpp
    void AudioPolicyService::setParameters(audio_io_handle_t ioHandle,
            const String8& keyValuePairs, int delayMs)
        mAudioCommandThread->parametersCommand((int)ioHandle, keyValuePairs, delayMs);
                |
                V function call
    status_t AudioPolicyService::AudioCommandThread::parametersCommand(int ioHandle,
            const String8& keyValuePairs, int delayMs)
        // create a command and insert it to the queue
        AudioCommand *command = new AudioCommand();
        command->mCommand = SET_PARAMETERS;
        ParametersData *data = new ParametersData();
        data->mIO = ioHandle;
        data->mKeyValuePairs = keyValuePairs;
        command->mParam = data;

        if (delayMs == 0)
            command->mWaitStatus = true;
        else 
            command->mWaitStatus = false;

        insertCommand_l(command, delayMs);
            void AudioPolicyService::AudioCommandThread::insertCommand_l(AudioCommand *command, int delayMs)
                mAudioCommands.insertAt(command, i + 1);

        // wake up working thread to handle the new command
        mWaitWorkCV.signal();---thread wake--->AudioPolicyService::AudioCommandThread::threadLoop
        if (command->mWaitStatus) {
            command->mCond.wait(mLock);
            status =  command->mStatus;
            mWaitWorkCV.signal();
        return status;

    bool AudioPolicyService::AudioCommandThread::threadLoop()
        nsecs_t waitTime = INT64_MAX;  
        while (!exitPending())
            while(!mAudioCommands.isEmpty())
                switch (command->mCommand) 
                case SET_PARAMETERS: 
                     ParametersData *data = (ParametersData *)command->mParam;
                     command->mStatus = AudioSystem::setParameters(data->mIO, data->mKeyValuePairs);
                     if (command->mWaitStatus) {
                         command->mCond.signal();
                         mWaitWorkCV.wait(mLock);
                     }
                     delete data;
                 }break;
                    |
                    V function call
frameworks/base/media/libmedia/AudioSystem.cpp
    status_t AudioSystem::setParameters(audio_io_handle_t ioHandle, const String8& keyValuePairs)
        return AudioSystem::get_audio_flinger()->setParameters(ioHandle, keyValuePairs);
                        |
                        v IPC call
frameworks/base/services/audioflinger/AudioFlinger.cpp
    status_t AudioFlinger::setParameters(int ioHandle, const String8& keyValuePairs)
    mHardwareStatus = AUDIO_SET_PARAMETER;
    result = mAudioHardware->setParameters(keyValuePairs);
                            |
                            V to HAL
hardware/msm7k/libaudio/AudioHardware.cpp
    status_t AudioHardware::setParameters(const String8& keyValuePairs)
        #ifdef HAVE_FM_RADIO
            key = String8(AudioParameter::keyFmOn);
            int devices;
            if (param.getInt(key, devices) == NO_ERROR)
               setFmOnOff(true);
            key = String8(AudioParameter::keyFmOff);
            if (param.getInt(key, devices) == NO_ERROR)
               setFmOnOff(false);
        #endif
            |
            V function call
    status_t AudioHardware::setFmOnOff(bool onoff)
        mFmRadioEnabled = onoff;
        return doRouting();

