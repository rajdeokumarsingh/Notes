
// start output flow
status_t AudioFlinger::PlaybackThread::Track::start()
    status = AudioSystem::startOutput(thread->id(),
            (AudioSystem::stream_type)mStreamType,
            mSessionId);
            |
            V
status_t AudioSystem::startOutput(audio_io_handle_t output, AudioSystem::stream_type stream, int session)
    const sp<IAudioPolicyService>& aps = AudioSystem::get_audio_policy_service();
    return aps->startOutput(output, stream, session);
            |
            V
status_t AudioPolicyService::startOutput(audio_io_handle_t output,
        AudioSystem::stream_type stream,
    return mpPolicyManager->startOutput(output, stream, session);
            |
            V
// 1. increase reference count for the output 
// 2. select proper deivces from the output by setOutputDevice(output, getNewDevice(output))
// 3. handle special case for sonification while in call
// 4. apply volume rules for current stream and device if necessary
status_t AudioPolicyManagerBase::startOutput(audio_io_handle_t output, AudioSystem::stream_type stream, int session)
    AudioOutputDescriptor *outputDesc = mOutputs.valueAt(index);
    routing_strategy strategy = getStrategy((AudioSystem::stream_type)stream);

    // incremenent usage count for this stream on the requested output:
    // NOTE that the usage count is the same for duplicated output and hardware output which is
    // necassary for a correct control of hardware output routing by startOutput() and stopOutput()

    // Jiang Rui, after increase the reference count, in getNewDevice()
    /* if (outputDesc->isUsedByStrategy(STRATEGY_MEDIA)) // enter this branch
        device = getDeviceForStrategy(STRATEGY_MEDIA, fromCache); */
    outputDesc->changeRefCount(stream, 1);

    setOutputDevice(output, getNewDevice(output));

    // handle special case for sonification while in call
    if (isInCall()) 
        handleIncallSonification(stream, true, false);


    // apply volume rules for current stream and device if necessary
    checkAndSetVolume(stream, mStreams[stream].mIndexCur, output, outputDesc->device());

// stop output flow
void AudioFlinger::PlaybackThread::Track::stop()
void AudioFlinger::PlaybackThread::Track::pause()
void AudioFlinger::PlaybackThread::Track::destroy() 
    AudioSystem::stopOutput(thread->id(),
        (AudioSystem::stream_type)mStreamType, mSessionId);
            |
            V
status_t AudioSystem::stopOutput(audio_io_handle_t output,
        AudioSystem::stream_type stream,
        int session)
    const sp<IAudioPolicyService>& aps = AudioSystem::get_audio_policy_service();
    return aps->stopOutput(output, stream, session);
        |
        V
status_t AudioPolicyService::stopOutput(audio_io_handle_t output,
        AudioSystem::stream_type stream, int session)            
    return mpPolicyManager->stopOutput(output, stream, session);
        |
        V
// 1. decrease the output reference count
// 2. handle in call notification
// 3. select proper devices from the output by invoking setOutputDevice()
status_t AudioPolicyManagerBase::stopOutput(audio_io_handle_t output, 
        AudioSystem::stream_type stream, int session)

    AudioOutputDescriptor *outputDesc = mOutputs.valueAt(index);
    routing_strategy strategy = getStrategy((AudioSystem::stream_type)stream);

    // handle special case for sonification while in call
    if (isInCall())
        handleIncallSonification(stream, false, false);

    if (outputDesc->mRefCount[stream] > 0)
        // decrement usage count of this stream on the output
        outputDesc->changeRefCount(stream, -1);
        // store time at which the last music track was stopped - see computeVolume()
        if (stream == AudioSystem::MUSIC) 
            mMusicStopTime = systemTime();

        setOutputDevice(output, getNewDevice(output));

#ifdef WITH_A2DP
        if (mA2dpOutput != 0 && !a2dpUsedForSonification() &&
                strategy == STRATEGY_SONIFICATION) 
            setStrategyMute(STRATEGY_MEDIA,
                    false,
                    mA2dpOutput,
                    mOutputs.valueFor(mHardwareOutput)->mLatency*2);
#endif
        // restore the default system output
        if (output != mHardwareOutput) 
            setOutputDevice(mHardwareOutput, getNewDevice(mHardwareOutput), true);


每次播放声音之前都会startOutput, 播放完成之后都会stopOutput
jiangrui@jiangrui-desktop:~/log/intel.fm/start.output$ adb logcat
--------- beginning of /dev/log/main
I/AudioFlinger( 1636): start(4097), calling thread 1800
I/AudioFlinger( 1636): ? => ACTIVE (4097) on thread 0xa3eb8
I/AudioPolicyService( 1636): startOutput() tid 1683
I/AudioPolicyManagerBase( 1636): startOutput() output 1, stream 1
I/AudioPolicyManagerBase( 1636): changeRefCount() stream 1, count 1
I/AudioPolicyManagerBase( 1636): getDeviceForStrategy() from cache strategy 0, device 2
I/AudioPolicyManagerBase( 1636): getNewDevice() selected device 2
I/AudioPolicyManagerBase( 1636): setOutputDevice() output 1 device 2 delayMs 0
I/AudioPolicyManagerBase( 1636): setOutputDevice() fm output 0
I/AudioPolicyManagerBase( 1636): setOutputDevice() prevDevice 2
I/AudioPolicyManagerBase( 1636): setOutputDevice() setting same device 2 or null device for output 1
I/AudioFlinger( 1636): mWaitWorkCV.broadcast
I/AudioFlinger( 1636): MixerThread 0x2f7f0 TID 1679 waking up
W/KeyCharacterMap( 1800): No keyboard for id 0
W/KeyCharacterMap( 1800): Using default keymap: /system/usr/keychars/qwerty.kcm.bin
W/AudioHardwareALSA( 1636): Write request but device is in state SND_PCM_STATE_SETUP
D/AudioHardwareALSA( 1636): call snd_pcm_prepare

I/AudioFlinger( 1636): stop(4097), calling thread 1800
I/AudioFlinger( 1636): (> STOPPED) => STOPPED (4097) on thread 0x2f7f0
I/AudioPolicyService( 1636): stopOutput() tid 1691
I/AudioPolicyManagerBase( 1636): stopOutput() output 1, stream 1
I/AudioPolicyManagerBase( 1636): changeRefCount() stream 1, count 0
I/AudioPolicyManagerBase( 1636): getNewDevice() selected device 0
I/AudioPolicyManagerBase( 1636): setOutputDevice() output 1 device 0 delayMs 0
I/AudioPolicyManagerBase( 1636): setOutputDevice() fm output 0
I/AudioPolicyManagerBase( 1636): setOutputDevice() prevDevice 2
I/AudioPolicyManagerBase( 1636): setOutputDevice() setting same device 0 or null device for output 1
W/AudioHardwareALSA( 1636): current_state=1 - Wait for correct device state
I/AudioFlinger( 1636): TrackBase::reset
I/AudioFlinger( 1636): Audio hardware entering standby, mixer 0x2f7f0, mSuspended 0
D/AudioHardwareALSA( 1636): standby()
D/AudioHardwareALSA( 1636): mStreamType=0, mParent->getAnalogLoop()=0
I/AudioFlinger( 1636): MixerThread 0x2f7f0 TID 1679 going to sleep


