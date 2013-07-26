


/*

Ringer open player and setDataSource

    01-01 09:58:55.538 D/Ringer  (  698): ring()...
    01-01 09:58:55.568 D/Ringer  (  698): mRingHandler: PLAY_RING_ONCE...
    01-01 09:58:55.568 D/Ringer  (  698): creating ringtone: content://settings/system/ringtone

    01-01 09:58:55.847 D/AwesomePlayer(  568): Awesome player-> setDataSource, fd


Phone setAudioMode to MODE_RINGTONE

    01-01 09:58:56.968 D/PhoneUtils(  698): setAudioMode(MODE_RINGTONE)...
    01-01 09:58:56.977 I/AudioPolicyService(  568): setPhoneState() tid 568
    01-01 09:58:56.977 D/AudioHardwareInterface(  568): setMode(RINGTONE)
    01-01 09:58:56.977 D/ALSAModule(  568): route called for devices 00000002 in mode 1...
    01-01 09:58:56.977 D/ALSAModule(  568): open called for devices 00000002 in mode 1...
    01-01 09:58:57.017 V/ALSAModule(  568): Set PLAYBACK PCM format to S16_LE (Signed 16 bit Little Endian)
    01-01 09:58:57.038 V/ALSAModule(  568): Using 2 channels for PLAYBACK.
    01-01 09:58:57.047 V/ALSAModule(  568): Set PLAYBACK sample rate to 44100 HZ
    01-01 09:58:57.047 V/ALSAModule(  568): Buffer size: 8192
    01-01 09:58:57.058 V/ALSAModule(  568): Latency: 185759
    01-01 09:58:57.077 I/ALSAModule(  568): Initialized ALSA PLAYBACK device AndroidPlayback_Speaker_ringtone
    01-01 09:58:57.077 D/ALSAModule(  568): route called for devices 00000000 in mode 1...
    01-01 09:58:57.077 D/ALSAModule(  568): open called for devices 00000000 in mode 1...
    01-01 09:58:57.128 V/ALSAModule(  568): Set CAPTURE PCM format to S16_LE (Signed 16 bit Little Endian)
    01-01 09:58:57.128 V/ALSAModule(  568): Using 1 channel for CAPTURE.
    01-01 09:58:57.128 V/ALSAModule(  568): Set CAPTURE sample rate to 8000 HZ
    01-01 09:58:57.128 V/ALSAModule(  568): Buffer size: 4096
    01-01 09:58:57.128 V/ALSAModule(  568): Latency: 512000
    01-01 09:58:57.157 I/ALSAModule(  568): Initialized ALSA CAPTURE device AndroidCapture

    系统默认输出设备就是耳机, setOutputDevice跳过
    01-01 09:58:57.157 I/AudioPolicyManagerBase(  568): setOutputDevice() output 1 device 0 delayMs 0
    01-01 09:58:57.157 I/AudioPolicyManagerBase(  568): setOutputDevice() fm output 0
    01-01 09:58:57.157 I/AudioPolicyManagerBase(  568): setOutputDevice() prevDevice 2
    01-01 09:58:57.157 I/AudioPolicyManagerBase(  568): setOutputDevice() setting same device 0 or null device for output 1

getOutput
    01-01 09:58:57.208 I/AudioPolicyService(  568): getOutput() tid 631
    01-01 09:58:57.208 I/AudioPolicyManagerBase(  568): getDeviceForStrategy() from cache strategy 2, device 2
    01-01 09:58:57.208 I/AudioPolicyManagerBase(  568): getOutput() stream 2, samplingRate 8000, format 1, channels 4, flags 0

startOutput
    01-01 09:58:57.208 I/AudioFlinger(  568): Track constructor name 4096, calling thread 698
    01-01 09:58:57.208 I/AudioFlinger(  568): start(4096), calling thread 698
    01-01 09:58:57.208 I/AudioFlinger(  568): ? => ACTIVE (4096) on thread 0x45e78

    01-01 09:58:57.208 I/AudioPolicyService(  568): startOutput() tid 631
    01-01 09:58:57.208 I/AudioPolicyManagerBase(  568): startOutput() output 1, stream 2
    01-01 09:58:57.208 I/AudioPolicyManagerBase(  568): changeRefCount() stream 2, count 1

    01-01 09:58:57.208 I/AudioPolicyManagerBase(  568): getDeviceForStrategy() from cache strategy 2, device 2
    01-01 09:58:57.208 I/AudioPolicyManagerBase(  568): getNewDevice() selected device 2

    系统默认输出设备就是耳机, setOutputDevice跳过
    01-01 09:58:57.208 I/AudioPolicyManagerBase(  568): setOutputDevice() output 1 device 2 delayMs 0
    01-01 09:58:57.208 I/AudioPolicyManagerBase(  568): setOutputDevice() fm output 0
    01-01 09:58:57.208 I/AudioPolicyManagerBase(  568): setOutputDevice() prevDevice 2
    01-01 09:58:57.208 I/AudioPolicyManagerBase(  568): setOutputDevice() setting same device 2 or null device for output 1


Ringer stop
    01-01 09:59:12.227 D/Ringer  (  698): ring()...
    01-01 09:59:12.257 D/Ringer  (  698): delaying ring by 1652

    01-01 09:59:13.907 D/Ringer  (  698): mRingHandler: PLAY_RING_ONCE...
    01-01 09:59:14.078 I/AudioService(  632):  AudioFocus  abandonAudioFocus() from AudioFocus_For_Phone_Ring_And_Calls

stopOutput
    01-01 09:59:14.138 I/AudioFlinger(  568): stop(4096), calling thread 698
    01-01 09:59:14.138 I/AudioFlinger(  568): (> STOPPED) => STOPPED (4096) on thread 0x2e340
    01-01 09:59:14.138 I/AudioPolicyService(  568): stopOutput() tid 631
    01-01 09:59:14.138 I/AudioPolicyManagerBase(  568): stopOutput() output 1, stream 2
    01-01 09:59:14.138 I/AudioPolicyManagerBase(  568): changeRefCount() stream 2, count 0
    01-01 09:59:14.138 I/AudioPolicyManagerBase(  568): getNewDevice() selected device 0

    // setOutputDevice skip
    01-01 09:59:14.138 I/AudioPolicyManagerBase(  568): setOutputDevice() output 1 device 0 delayMs 0
    01-01 09:59:14.138 I/AudioPolicyManagerBase(  568): setOutputDevice() fm output 0
    01-01 09:59:14.138 I/AudioPolicyManagerBase(  568): setOutputDevice() prevDevice 2
    01-01 09:59:14.138 I/AudioPolicyManagerBase(  568): setOutputDevice() setting same device 0 or null device for output 1

    01-01 09:59:14.138 I/AudioPolicyService(  568): releaseOutput() tid 631
    01-01 09:59:14.138 I/AudioPolicyManagerBase(  568): releaseOutput() 1

setPhoneState(MODE_NORMAL)
    01-01 09:59:14.147 D/PhoneUtils(  698): setAudioMode(MODE_NORMAL)...
    01-01 09:59:14.158 I/AudioPolicyService(  568): setPhoneState() tid 631


    // switch device to speaker and CAPTURE
    01-01 09:59:14.158 D/AudioHardwareInterface(  568): setMode(NORMAL)
    01-01 09:59:14.158 D/ALSAModule(  568): route called for devices 00000002 in mode 0...
    01-01 09:59:14.177 I/AudioFlinger(  568): remove track (4096) and delete from mixer
    01-01 09:59:14.367 I/AudioFlinger(  568): PlaybackThread::Track destructor
    01-01 09:59:14.367 I/AudioFlinger(  568): removeClient_l() pid 568, tid 630, calling tid 568
    01-01 09:59:14.468 D/ALSAModule(  568): open called for devices 00000002 in mode 0...
    01-01 09:59:14.497 V/ALSAModule(  568): Set PLAYBACK PCM format to S16_LE (Signed 16 bit Little Endian)
    01-01 09:59:14.518 V/ALSAModule(  568): Using 2 channels for PLAYBACK.
    01-01 09:59:14.518 V/ALSAModule(  568): Set PLAYBACK sample rate to 44100 HZ
    01-01 09:59:14.518 V/ALSAModule(  568): Buffer size: 8192
    01-01 09:59:14.527 V/ALSAModule(  568): Latency: 185759
    01-01 09:59:14.568 I/ALSAModule(  568): Initialized ALSA PLAYBACK device AndroidPlayback_Speaker_normal
    01-01 09:59:14.568 D/ALSAModule(  568): route called for devices 00000000 in mode 0...
    01-01 09:59:14.568 D/ALSAModule(  568): open called for devices 00000000 in mode 0...
    01-01 09:59:14.618 V/ALSAModule(  568): Set CAPTURE PCM format to S16_LE (Signed 16 bit Little Endian)
    01-01 09:59:14.618 V/ALSAModule(  568): Using 1 channel for CAPTURE.
    01-01 09:59:14.627 V/ALSAModule(  568): Set CAPTURE sample rate to 8000 HZ
    01-01 09:59:14.627 V/ALSAModule(  568): Buffer size: 4096
    01-01 09:59:14.627 V/ALSAModule(  568): Latency: 512000
    01-01 09:59:14.658 I/ALSAModule(  568): Initialized ALSA CAPTURE device AndroidCapture

releaseOutput
    01-01 09:59:15.528 I/AudioPolicyService(  568): releaseOutput() tid 670
    01-01 09:59:15.528 I/AudioPolicyManagerBase(  568): releaseOutput() 1
    01-01 09:59:15.528 I/AudioFlinger(  568): remove track (4097) and delete from mixer
    01-01 09:59:15.528 I/AudioFlinger(  568): PlaybackThread::Track destructor

================================================================================
// use expired dcf as ringtone

01-01 09:15:24.742 I/AudioService(  637):  AudioFocus  requestAudioFocus() from: AudioFocus_For_Phone_Ring_And_Calls, mainStreamType: 2, focusChangeHint: 2

Ringer opened player:
    01-01 09:15:25.881 D/Ringer  (  703): ring()...
    01-01 09:15:25.951 D/Ringer  (  703): mRingHandler: PLAY_RING_ONCE...
    01-01 09:15:25.951 D/Ringer  (  703): creating ringtone: content://settings/system/ringtone
    01-01 09:15:26.192 D/InCallScreen(  703): onCreate()...  this = com.android.phone.InCallScreen@43e9f498
    01-01 09:15:26.202 D/InCallScreen(  703): isScreenLand:false
    01-01 09:15:26.231 D/AwesomePlayer(  568): Awesome player-> setDataSource, fd
    01-01 09:15:26.242 D/        (  568): proc link name is /proc/568/fd/25
    01-01 09:15:26.321 D/        (  568): handle 25 is a DRM file
    01-01 09:15:26.321 D/        (  568): Enter DRM_Initialize
    01-01 09:15:26.321 D/        (  568): proc link name is /proc/568/fd/25
    01-01 09:15:26.321 D/        (  568): Enter directIO_open_dcfcb_init
    01-01 09:15:26.321 D/        (  568): enter init_with_mmap

    01-01 09:15:26.401 D/AwesomePlayer(  568): Awesome player-> has no license
    01-01 09:15:26.412 E/MediaPlayer(  703): Unable to to create media player
    01-01 09:15:26.412 D/MediaPlayer(  703): Couldn't open file on client side, trying server side

    01-01 09:15:26.542 D/AwesomePlayer(  568): Awesome player-> setDataSource, fd
    01-01 09:15:26.542 D/        (  568): proc link name is /proc/568/fd/0
    01-01 09:15:26.542 D/        (  568): handle 0 is a DRM file
    01-01 09:15:26.542 D/        (  568): Enter DRM_Initialize
    01-01 09:15:26.542 D/        (  568): proc link name is /proc/568/fd/0
    01-01 09:15:26.542 D/        (  568): Enter directIO_open_dcfcb_init
    01-01 09:15:26.542 D/        (  568): enter init_with_mmap
    01-01 09:15:26.542 D/        (  568): enter init_with_pointer
    01-01 09:15:26.542 D/        (  568): ret is 0, cid is cid:z20101217032
    01-01 09:15:26.542 D/        (  568): drm_getKey error
    01-01 09:15:26.542 E/        (  568): lseek 1 erro, fd = 0, errno = 9, desc:Bad file number
    01-01 09:15:26.542 E/        (  568): lseek 1 erro, fd = 0, errno = 9, desc:Bad file number
    01-01 09:15:26.542 E/        (  568): parse dcf file in DRM_GetDRMMetaDataInFile error
    01-01 09:15:26.542 E/        (  568): lseek 1 erro, fd = 0, errno = 9, desc:Bad file number
    01-01 09:15:26.542 E/        (  568): DRM_GetDRMMetaDataInFile error in DRM_IsRightValid
    01-01 09:15:26.542 D/        (  568): proc link name is /proc/568/fd/0
    01-01 09:15:26.542 E/        (  568): Failed to get fileName by fd 0
    01-01 09:15:26.542 D/AwesomePlayer(  568): Awesome player-> has no license
    01-01 09:15:26.542 E/MediaPlayer(  703): Unable to to create media player

    01-01 09:15:26.562 E/RingtoneManager(  703): Failed to open ringtone content://settings/system/ringtone
    01-01 09:15:26.562 E/RingtoneManager(  703): try to use fallbackring since the specified ringtone is missing


*/

