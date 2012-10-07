set volume flow

////////////////////////////////////////////////////////////////////////////////
Scenario 1:
    FMR application is launched
////////////////////////////////////////////////////////////////////////////////
FMR application:
    src/com/android/fm/radio/FMRadio.java
        private void enableRadio()
            int vol = AudioSystem.getStreamVolumeIndex(AudioSystem.STREAM_FM);
            AudioSystem.setStreamVolumeIndex(AudioSystem.STREAM_FM, vol);

////////////////////////////////////////////////////////////////////////////////
Framework
frameworks/base/media/java/android/media/
    ////////////////////////////////////////////////////////////////////////////////
    AudioSystem.java
        public static native int setStreamVolumeIndex(int stream, int index);
                |
                V JNI call
frameworks/base/media/libmedia/
    ////////////////////////////////////////////////////////////////////////////////
    AudioSystem.cpp
        status_t AudioSystem::setStreamVolumeIndex(stream_type stream, int index)
            return AudioSystem::get_audio_policy_service()->setStreamVolumeIndex(stream, index);
                |
                V IPC call
frameworks/base/services/audioflinger/
    ////////////////////////////////////////////////////////////////////////////////
    AudioPolicyService.cpp
        // implemented the interface
        status_t AudioPolicyService::setFmVolume(float volume, int delayMs)
            return mAudioCommandThread->fmVolumeCommand(volume, delayMs);
            // create an FMR volume command, insert it to command queue

        // working thread, read the command from the command queue
        bool AudioPolicyService::AudioCommandThread::threadLoop()
            command->mStatus = AudioSystem::setFmVolume(data->mVolume);

    ////////////////////////////////////////////////////////////////////////////////
    AudioSystem.cpp
        status_t AudioSystem::setFmVolume(float value)
            return AudioSystem::get_audio_flinger()->setFmVolume(value);
                |
                V IPC call
    ////////////////////////////////////////////////////////////////////////////////
    AudioFlinger.cpp
        status_t AudioFlinger::setFmVolume(float value)
            ret = mAudioHardware->setFmVolume(value);
                    |
                    V
////////////////////////////////////////////////////////////////////////////////
hardware layer

    ////////////////////////////////////////////////////////////////////////////////
    interface
    libhardware_legacy/include/hardware_legacy/AudioHardwareInterface.h
        class:android::AudioHardwareInterface
        virtual status_t    setFmVolume(float volume) { return 0; }

    libhardware_legacy/include/hardware_legacy/AudioPolicyInterface.h
        class:android::AudioPolicyClientInterface
        virtual status_t setFmVolume(float volume, int delayMs = 0) { return 0; }

    ////////////////////////////////////////////////////////////////////////////////
    implementation

    msm7k/libaudio/AudioHardware.cpp
        class:android::AudioHardware
        status_t AudioHardware::setFmVolume(float v)

    msm7k/libaudio-msm7x30/AudioHardware.cpp
        class:android::AudioHardware
        status_t AudioHardware::setFmVolume(float v)

    msm7k/libaudio-qsd8k/AudioHardware.cpp
        class:android::AudioHardware
        status_t AudioHardware::setFmVolume(float v)

////////////////////////////////////////////////////////////////////////////////
Scenario 2:
    Adjust FMR volume by the volume keys

    ////////////////////////////////////////////////////////////////////////////////
    key handler
    frameworks/policies/base/phone/com/android/internal/policy/impl/PhoneWindow.java
        onKeyDown(int featureId, int keyCode, KeyEvent event)
            audioManager.adjustSuggestedStreamVolum()

    policies/base/phone/com/android/internal/policy/impl/PhoneWindow.java
       onKeyUp(int featureId, int keyCode, KeyEvent event)
            audioManager.adjustSuggestedStreamVolume
    policies/base/phone/com/android/internal/policy/impl/PhoneWindow.java
        public boolean dispatchKeyEvent(KeyEvent event)
            audioManager.adjustSuggestedStreamVolume

    ////////////////////////////////////////////////////////////////////////////////
    audio layer 
    frameworks/base/media/java/android/media/
        ////////////////////////////////////////////////////////////////////////////////
        AudioManager.java
        public void adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags) 
            int streamType = getActiveStreamType(suggestedStreamType);
            adjustStreamVolume(streamType, direction, flags);

    frameworks/base/media/java/android/media/
        ////////////////////////////////////////////////////////////////////////////////
        AudioSystem.java
            public static native int setStreamVolumeIndex(int stream, int index);
 
////////////////////////////////////////////////////////////////////////////////
const sp<IAudioPolicyService>& AudioSystem::get_audio_policy_service()
    sp<IServiceManager> sm = defaultServiceManager();
    sp<IBinder> binder;
    binder = sm->getService(String16("media.audio_policy"));

    AudioPolicyManagerBase
        startOutput
        applyStreamVolumes
        setStreamMute
        setStreamVolumeIndex
                |
                V
            checkAndSetVolume
