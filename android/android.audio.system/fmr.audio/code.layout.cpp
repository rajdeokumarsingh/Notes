
// question
1. what is the "AudioSystem::popCount"
    population count
    guess: 每一帧的采样次数 

2. what is the "the input/output channel mask"?


frameworks/base/media/java/android/media
    |-- AmrInputStream.java
    |-- AsyncPlayer.java
    |-- AudioFormat.java
    |-- AudioManager.java
    |-- AudioRecord.java
    |-- AudioService.java
    |-- AudioSystem.java
    |-- AudioTrack.java
    |-- IAudioFocusDispatcher.aidl
    |-- IAudioService.aidl
    |-- IMediaScannerListener.aidl
    |-- IMediaScannerService.aidl
    |-- MediaFile.java
    |-- MediaPlayer.java
    |-- MediaRecorder.java

frameworks/base/services/audioflinger
    |-- AudioFlinger.cpp
    |-- AudioFlinger.h

    |-- AudioPolicyManagerBase.cpp
    |-- AudioPolicyService.cpp
    |-- AudioPolicyService.h

    |-- AudioHardwareGeneric.cpp
    |-- AudioHardwareGeneric.h
    |-- AudioHardwareInterface.cpp
    |-- AudioHardwareStub.cpp
    |-- AudioHardwareStub.h
    |-- AudioDumpInterface.cpp
    |-- AudioDumpInterface.h

frameworks/base/include/media
    |-- AudioSystem.h
    |-- IAudioFlinger.h
    |-- IAudioFlingerClient.h
    |-- IAudioPolicyService.h


