// [Revised 2011-09-08]

android/media/AudioManager.setParameters (Ljava/lang/String;)V
.android/media/AudioSystem.setParameters (Ljava/lang/String;)I
    public static native int setParameters(String keyValuePairs);
        |
        V JNI
status_t AudioSystem::setParameters(audio_io_handle_t ioHandle, const String8& keyValuePairs) 
    const sp<IAudioFlinger>& af = AudioSystem::get_audio_flinger();
    return af->setParameters(ioHandle, keyValuePairs);
        |
        V
status_t AudioFlinger::setParameters(int ioHandle, const String8& keyValuePairs)
    thread = checkPlaybackThread_l(ioHandle);
    result = thread->setParameters(keyValuePairs);
        |
        V
status_t AudioFlinger::ThreadBase::setParameters(const String8& keyValuePairs)
    mNewParameters.add(keyValuePairs);
    mWaitWorkCV.signal();
    // wait condition with timeout in case the thread loop has exited
    // before the request could be processed
    if (mParamCond.waitRelative(mLock, seconds(2)) == NO_ERROR)
        status = mParamStatus;
        mWaitWorkCV.signal();
    else
        status = TIMED_OUT;
    return status;

    signal()
        |
        V
bool AudioFlinger::MixerThread::checkForNewParameters_l()
    while (!mNewParameters.isEmpty())
        String8 keyValuePair = mNewParameters[0];
        AudioParameter param = AudioParameter(keyValuePair);

        mOutput->setParameters(keyValuePair)  // FIXME: --> #anchor_AudioStreamOutALSA
            
        sendConfigEvent_l(AudioSystem::OUTPUT_CONFIG_CHANGED);

        mNewParameters.removeAt(0);
        mParamStatus = status;
        mParamCond.signal();
        mWaitWorkCV.wait(mLock);

// #anchor_AudioStreamOutALSA
    class AudioStreamOutALSA : public AudioStreamOut, public ALSAStreamOps
        virtual status_t    setParameters(const String8& keyValuePairs)
            return ALSAStreamOps::setParameters(keyValuePairs);                                                                                  
                |
                V
    status_t ALSAStreamOps::setParameters(const String8& keyValuePairs)
        status = mParent->mALSADevice->set(keyValuePairs);

Log:
    E/AudioFlinger( 1516): setParameters(): io 1, keyvalue routing=1, tid 1552, calling tid 1516
    E/AudioFlinger( 1516): ThreadBase::setParameters() routing=1
    E/AudioFlinger( 1516): MixerThread 0x30130 TID 1554 waking up
    D/ALSAStreamOps( 1516): setParameters() routing=1

int AudioFlinger::openOutput(uint32_t *pDevices, uint32_t *pSamplingRate, 
    uint32_t *pFormat, uint32_t *pChannels, uint32_t *pLatencyMs, uint32_t flags) 

    AudioStreamOut *output = mAudioHardware->openOutputStream(*pDevices,
            (int *)&format, &channels, &samplingRate, &status);

mAudioHardware 
    mAudioHardware = AudioHardwareInterface::create();


# pri kind tag               file
1 F C f    openOutputStream  base/libs/audioflinger/AudioHardwareGeneric.cpp
class:android::AudioHardwareGeneric
AudioStreamOut* AudioHardwareGeneric::openOutputStream(

5 F   f    openOutputStream  base/libs/audioflinger/AudioDumpInterface.cpp
class:android::AudioDumpInterface
AudioStreamOut* AudioDumpInterface::openOutputStream(

6 F   f    openOutputStream  base/libs/audioflinger/AudioHardwareStub.cpp
class:android::AudioHardwareStub
AudioStreamOut* AudioHardwareStub::openOutputStream(

class AudioPolicyManagerALSA: public AudioPolicyInterface

////////////////////////////////////////////////////////////////////////////////
// Implement in HAL
1 F C f    setParameters     alsa_sound/AudioHardwareALSA.h
class:android::AudioStreamOutALSA
virtual status_t    setParameters(const String8& keyValuePairs) 
2 F C f    setParameters     alsa_sound/AudioHardwareALSA.h
class:android::AudioStreamInALSA
virtual status_t    setParameters(const String8& keyValuePairs)
3 F   f    setParameters     alsa_sound/ALSAStreamOps.cpp
class:android::ALSAStreamOps
status_t ALSAStreamOps::setParameters(const String8& keyValuePairs)
4 F   f    setParameters     alsa_sound/AudioHardwareALSA.cpp
class:android::AudioHardwareALSA
status_t AudioHardwareALSA::setParameters(const String8& keyValuePairs)
5 F   f    setParameters     msm7k/libaudio-msm7x30/AudioHardware.cpp
class:android::AudioHardware::AudioSessionOutMSM7xxx
status_t AudioHardware::AudioSessionOutMSM7xxx::setParameters(const String8& keyValuePairs)
6 F   f    setParameters     msm7k/libaudio-msm7x30/AudioHardware.cpp
class:android::AudioHardware::AudioStreamInMSM72xx
status_t AudioHardware::AudioStreamInMSM72xx::setParameters(const String8& keyValuePairs)
7 F   f    setParameters     msm7k/libaudio-msm7x30/AudioHardware.cpp
class:android::AudioHardware::AudioStreamOutMSM72xx
status_t AudioHardware::AudioStreamOutMSM72xx::setParameters(const String8& keyValuePairs)
8 F   f    setParameters     msm7k/libaudio-msm7x30/AudioHardware.cpp
class:android::AudioHardware
status_t AudioHardware::setParameters(const String8& keyValuePairs)
9 F   f    setParameters     msm7k/libaudio-qdsp5v2/AudioHardware.cpp
class:android::AudioHardware::AudioStreamOutQ5V2
status_t AudioHardware::AudioStreamOutQ5V2::setParameters(const String8& keyValuePairs)
10 F   f    setParameters     msm7k/libaudio-qdsp5v2/AudioHardware.cpp
class:android::AudioHardware
status_t AudioHardware::setParameters(const String8& keyValuePairs)
11 F   f    setParameters     msm7k/libaudio-qsd8k/AudioHardware.cpp
class:android::AudioHardware::AudioStreamInMSM72xx
12 F   f    setParameters     msm7k/libaudio-qsd8k/AudioHardware.cpp
class:android::AudioHardware::AudioStreamOutMSM72xx
status_t AudioHardware::AudioStreamOutMSM72xx::setParameters(const String8& keyValuePairs)
13 F   f    setParameters     msm7k/libaudio-qsd8k/AudioHardware.cpp
class:android::AudioHardware
status_t AudioHardware::setParameters(const String8& keyValuePairs)
14 F   f    setParameters     msm7k/libaudio/AudioHardware.cpp
class:android::AudioHardware::AudioStreamInMSM72xx
status_t AudioHardware::AudioStreamInMSM72xx::setParameters(const String8& keyValuePairs)
15 F   f    setParameters     msm7k/libaudio/AudioHardware.cpp
class:android::AudioHardware::AudioStreamOutMSM72xx
status_t AudioHardware::AudioStreamOutMSM72xx::setParameters(const String8& keyValuePairs)
16 F   f    setParameters     msm7k/libaudio/AudioHardware.cpp
class:android::AudioHardware


