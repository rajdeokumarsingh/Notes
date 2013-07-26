hardware/libhardware_legacy/include/hardware_legacy/
    |-- AudioHardwareBase.h
    |-- AudioHardwareInterface.h
    |-- AudioPolicyInterface.h
    |-- AudioPolicyManagerBase.h

hardware/msm7k
    |-- libaudio
    |   |-- AudioHardware.cpp
    |   |-- AudioHardware.h
    |   |-- AudioPolicyManager.cpp
    |   |-- AudioPolicyManager.h
    |-- libaudio-msm7x30
    |   |-- AudioHardware.cpp
    |   |-- AudioHardware.h
    |   |-- AudioPolicyManager.cpp
    |   |-- AudioPolicyManager.h
    |   `-- control.h
    |-- libaudio-qdsp5v2
    |   |-- AudioHardware.cpp
    |   |-- AudioHardware.h
    |   |-- AudioPolicyManager.cpp
    |   |-- AudioPolicyManager.h
    |   |-- msm_audio.h
    |-- libaudio-qsd8k
    |   |-- AudioHardware.cpp
    |   |-- AudioHardware.h
    |   |-- AudioPolicyManager.cpp
    |   |-- AudioPolicyManager.h
    |   |-- msm_audio.h


////////////////////////////////////////////////////////////////////////////////
// Android HAL interfaces
AudioHardwareInterface
AudioHardwareBase : public AudioHardwareInterface
AudioStreamIn
AudioStreamOut

AudioPolicyInterface
AudioPolicyManagerBase : public AudioPolicyInterface

AudioStreamOut
    // AudioStreamOut is the abstraction interface for the audio output hardware.
    // It provides information about various properties of the audio output hardware driver.

    /** return audio sampling rate in hz - eg. 44100 */
    virtual uint32_t    sampleRate() const = 0;

    /** returns size of output buffer - eg. 4800 */
    virtual size_t      bufferSize() const = 0;

    /** returns the output channel mask */
    virtual uint32_t    channels() const = 0;

    /** return audio format in 8bit or 16bit PCM format - eg. AudioSystem:PCM_16_BIT */
    virtual int         format() const = 0;

    /** return the frame size (number of bytes per sample).  */
    uint32_t    frameSize() const { return AudioSystem::popCount(channels())*
        ((format()==AudioSystem::PCM_16_BIT)?sizeof(int16_t):sizeof(int8_t)); }

    /** return the audio hardware driver latency in milli seconds.  */
    virtual uint32_t    latency() const = 0;

    /**
     * Use this method in situations where audio mixing is done in the
     * hardware. This method serves as a direct interface with hardware,
     * allowing you to directly set the volume as apposed to via the framework.
     * This method might produce multiple PCM outputs or hardware accelerated
     * codecs, such as MP3 or AAC.
     */
    virtual status_t    setVolume(float left, float right) = 0;

    /** write audio buffer to driver. Returns number of bytes written */
    virtual ssize_t     write(const void* buffer, size_t bytes) = 0;

    /** Put the audio hardware output into standby mode. Returns
     * status based on include/utils/Errors.h */
    virtual status_t    standby() = 0;

    /** dump the state of the audio output device */
    virtual status_t dump(int fd, const Vector<String16>& args) = 0;

    // set/get audio output parameters. The function accepts a list of parameters
    // key value pairs in the form: key1=value1;key2=value2;...
    // Some keys are reserved for standard parameters (See AudioParameter class).
    // If the implementation does not accept a parameter change while the output is
    // active but the parameter is acceptable otherwise, it must return INVALID_OPERATION.
    // The audio flinger will put the output in standby and then change the parameter value.
    virtual status_t    setParameters(const String8& keyValuePairs) = 0;
    virtual String8     getParameters(const String8& keys) = 0;

    // return the number of audio frames written by the audio dsp to DAC since
    // the output has exited standby
    virtual status_t    getRenderPosition(uint32_t *dspFrames) = 0;

AudioStreamIn
    /** return audio sampling rate in hz - eg. 44100 */
    virtual uint32_t    sampleRate() const = 0;

    /** return the input buffer size allowed by audio driver */
    virtual size_t      bufferSize() const = 0;

    /** return input channel mask */
    virtual uint32_t    channels() const = 0;

    /** * return audio format in 8bit or 16bit PCM format - 
    * eg. AudioSystem:PCM_16_BIT */
    virtual int         format() const = 0;

    /** set the input gain for the audio driver. 
    This method is for for future use , 增益*/ 
    virtual status_t    setGain(float gain) = 0;

    /** read audio buffer in from audio driver */
    virtual ssize_t     read(void* buffer, ssize_t bytes) = 0;

    /** dump the state of the audio input device */
    virtual status_t dump(int fd, const Vector<String16>& args) = 0;

    /** * Put the audio hardware input into standby mode. Returns 
    * status based on include/utils/Errors.h */
    virtual status_t    standby() = 0;

    // set/get audio input parameters. The function accepts a list of parameters
    // key value pairs in the form: key1=value1;key2=value2;...
    // Some keys are reserved for standard parameters (See AudioParameter class).
    // If the implementation does not accept a parameter change while the output is
    // active but the parameter is acceptable otherwise, it must return INVALID_OPERATION.
    // The audio flinger will put the input in standby and then change the parameter value.
    virtual status_t    setParameters(const String8& keyValuePairs) = 0;
    virtual String8     getParameters(const String8& keys) = 0;

    // Return the amount of input frames lost in the audio driver since the last call of this function.
    // Audio driver is expected to reset the value to 0 and restart counting upon returning the current value by this function call.
    // Such loss typically occurs when the user space process is blocked longer than the capacity of audio driver buffers.
    // Unit: the number of input audio frames
    virtual unsigned int  getInputFramesLost() const = 0;


/**
 * AudioHardwareInterface.h defines the interface to the audio hardware abstraction layer.
 *
 * The interface supports setting and getting parameters, selecting audio routing
 * paths, and defining input and output streams.
 *
 * AudioFlinger initializes the audio hardware and immediately opens an output stream.
 * You can set Audio routing to output to handset, speaker, Bluetooth, or a headset.
 *
 * The audio input stream is initialized when AudioFlinger is called to carry out
 * a record operation.
 */
class AudioHardwareInterface
    /** check to see if the audio hardware interface has been initialized.
     return status based on values defined in include/utils/Errors.h */
    virtual status_t    initCheck() = 0;

   /** set the audio volume of a voice call. Range is between 0.0 and 1.0 */
   virtual status_t    setVoiceVolume(float volume) = 0;

   /** set the audio volume for all audio activities other than voice call.
    * Range between 0.0 and 1.0. If any value other than NO_ERROR is returned,
    * the software mixer will emulate this capability.  */
   virtual status_t    setMasterVolume(float volume) = 0;

   /** setMode is called when the audio mode changes. NORMAL mode is for
    * standard audio playback, RINGTONE when a ringtone is playing, and IN_CALL
    * when a call is in progress.  */
   virtual status_t    setMode(int mode) = 0;

   // mic mute
   virtual status_t    setMicMute(bool state) = 0;
   virtual status_t    getMicMute(bool* state) = 0;

   // set/get global audio parameters
   virtual status_t    setParameters(const String8& keyValuePairs) = 0;
   virtual String8     getParameters(const String8& keys) = 0;

   // Returns audio input buffer size according to parameters passed or 0 if one of the
   // parameters is not supported
   virtual size_t    getInputBufferSize(uint32_t sampleRate, int format, int channelCount) = 0;

    /** This method creates and opens the audio hardware output stream */
   virtual AudioStreamOut* openOutputStream(
           uint32_t devices,
           int *format=0,
           uint32_t *channels=0,
           uint32_t *sampleRate=0,
           status_t *status=0) = 0;

    virtual    void        closeOutputStream(AudioStreamOut* out) = 0;

    /** This method creates and opens the audio hardware input stream */
    virtual AudioStreamIn* openInputStream(
            uint32_t devices,
            int *format,
            uint32_t *channels,
            uint32_t *sampleRate,
            status_t *status,
            AudioSystem::audio_in_acoustics acoustics) = 0;
    virtual    void        closeInputStream(AudioStreamIn* in) = 0;

    /**This method dumps the state of the audio hardware */
    virtual status_t dumpState(int fd, const Vector<String16>& args) = 0;

    static AudioHardwareInterface* create();


extern "C" AudioHardwareInterface* createAudioHardware(void);



AudioSystem.h
    enum audio_devices {
        // output devices
        DEVICE_OUT_EARPIECE = 0x1,
        DEVICE_OUT_SPEAKER = 0x2,
        DEVICE_OUT_WIRED_HEADSET = 0x4,
        DEVICE_OUT_WIRED_HEADPHONE = 0x8,
        DEVICE_OUT_BLUETOOTH_SCO = 0x10,
        DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 0x20,
        DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 0x40,
        DEVICE_OUT_BLUETOOTH_A2DP = 0x80,
        DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 0x100,
        DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 0x200,
        DEVICE_OUT_AUX_DIGITAL = 0x400,
        DEVICE_OUT_HDMI = 0x2000,
    #ifdef HAVE_FM_RADIO
        DEVICE_OUT_FM = 0x800,
        DEVICE_OUT_FM_SPEAKER = 0x1000,
        DEVICE_OUT_FM_ALL = (DEVICE_OUT_FM | DEVICE_OUT_FM_SPEAKER),
    #endif
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
        DEVICE_IN_FM_RX_A2DP = 0x2000000,
#endif

    enum device_connection_state
        DEVICE_STATE_UNAVAILABLE,
        DEVICE_STATE_AVAILABLE,
        NUM_DEVICE_STATES

    enum audio_mode
        MODE_INVALID = -2,
        MODE_CURRENT = -1,
        MODE_NORMAL = 0,
        MODE_RINGTONE,
        MODE_IN_CALL,
        MODE_IN_COMMUNICATION,
        NUM_MODES  // not a valid entry, denotes end-of-list


    enum force_use 
        FOR_COMMUNICATION,
        FOR_MEDIA,
        FOR_RECORD,
        FOR_DOCK,
        NUM_FORCE_USE

    // device categories used for setForceUse()
    enum forced_config
        FORCE_NONE,
        FORCE_SPEAKER,
        FORCE_HEADPHONES,
        FORCE_BT_SCO,
        FORCE_BT_A2DP,
        FORCE_WIRED_ACCESSORY,
        FORCE_BT_CAR_DOCK,
        FORCE_BT_DESK_DOCK,
        NUM_FORCE_CONFIG,
        FORCE_DEFAULT = FORCE_NONE

    enum stream_type
        DEFAULT          =-1,
        VOICE_CALL       = 0,
        SYSTEM           = 1,
        RING             = 2,
        MUSIC            = 3,
        ALARM            = 4,
        NOTIFICATION     = 5,
        BLUETOOTH_SCO    = 6,
        ENFORCED_AUDIBLE = 7, // Sounds that cannot be muted by user and must be routed to speaker
        DTMF             = 8,
        TTS              = 9,
#ifdef HAVE_FM_RADIO
        FM              = 10,
#endif
        NUM_STREAM_TYPES

    enum audio_format
        INVALID_FORMAT      = -1,
        FORMAT_DEFAULT      = 0,
        PCM                 = 0x00000000, // must be 0 for backward compatibility
        MP3                 = 0x01000000,
        AMR_NB              = 0x02000000,
        AMR_WB              = 0x03000000,
        AAC                 = 0x04000000,
        HE_AAC_V1           = 0x05000000,
        HE_AAC_V2           = 0x06000000,
        VORBIS              = 0x07000000,
        MAIN_FORMAT_MASK    = 0xFF000000,
        SUB_FORMAT_MASK     = 0x00FFFFFF,
        // Aliases
        PCM_16_BIT          = (PCM|PCM_SUB_16_BIT),
        PCM_8_BIT          = (PCM|PCM_SUB_8_BIT)

// platform specific audio policy manager 
// Android generic audio policy manager

// ----------------------------------------------------------------------------
// The AudioPolicyInterface and AudioPolicyClientInterface classes define the communication interfaces
// between the platform specific audio policy manager and Android generic audio policy manager.
// The platform specific audio policy manager must implement methods of the AudioPolicyInterface class.
// This implementation makes use of the AudioPolicyClientInterface to control the activity and
// configuration of audio input and output streams.
//
// The platform specific audio policy manager is in charge of the audio routing and volume control
// policies for a given platform.
// The main roles of this module are:
//   - keep track of current system state (removable device connections, phone state, user requests...).
//   System state changes and user actions are notified to audio policy manager with methods of the AudioPolicyInterface.
//   - process getOutput() queries received when AudioTrack objects are created: Those queries
//   return a handler on an output that has been selected, configured and opened by the audio policy manager and that
//   must be used by the AudioTrack when registering to the AudioFlinger with the createTrack() method.
//   When the AudioTrack object is released, a putOutput() query is received and the audio policy manager can decide
//   to close or reconfigure the output depending on other streams using this output and current system state.
//   - similarly process getInput() and putInput() queries received from AudioRecord objects and configure audio inputs.
//   - process volume control requests: the stream volume is converted from an index value (received from UI) to a float value
//   applicable to each output as a function of platform specific settings and current output route (destination device). It
//   also make sure that streams are not muted if not allowed (e.g. camera shutter sound in some countries).
//  
// The platform specific audio policy manager is provided as a shared library by platform vendors (as for libaudio.so)
// and is linked with libaudioflinger.so
AudioPolicyInterface

    // indicate a change in device connection status
    virtual status_t setDeviceConnectionState(AudioSystem::audio_devices device,
            AudioSystem::device_connection_state state,
            const char *device_address) = 0;

   // retreive a device connection status
    virtual AudioSystem::device_connection_state getDeviceConnectionState(
        AudioSystem::audio_devices device, const char *device_address) = 0;

    // indicate a change in phone state. Valid phones states are defined by AudioSystem::audio_mode
    virtual void setPhoneState(int state) = 0;

    // indicate a change in ringer mode
    virtual void setRingerMode(uint32_t mode, uint32_t mask) = 0;

    // force using a specific device category for the specified usage
    virtual void setForceUse(AudioSystem::force_use usage, AudioSystem::forced_config config) = 0;
    // retreive current device category forced for a given usage
    virtual AudioSystem::forced_config getForceUse(AudioSystem::force_use usage) = 0;

    // set a system property (e.g. camera sound always audible)
    virtual void setSystemProperty(const char* property, const char* value) = 0;

    // request an output appriate for playback of the supplied stream type and parameters
    virtual audio_io_handle_t getOutput(AudioSystem::stream_type stream,
            uint32_t samplingRate = 0,
            uint32_t format = AudioSystem::FORMAT_DEFAULT,
            uint32_t channels = 0,
            AudioSystem::output_flags flags = AudioSystem::OUTPUT_FLAG_INDIRECT) = 0;

    // indicates to the audio policy manager that the output starts being used by corresponding stream.
    virtual status_t startOutput(audio_io_handle_t output,
            AudioSystem::stream_type stream,
            int session = 0) = 0;

    // indicates to the audio policy manager that the output stops being used by corresponding stream.
    virtual status_t stopOutput(audio_io_handle_t output,
            AudioSystem::stream_type stream,
            int session = 0) = 0;

    // releases the output.
    virtual void releaseOutput(audio_io_handle_t output) = 0;

    // request an input appriate for record from the supplied device with supplied parameters.
    virtual audio_io_handle_t getInput(int inputSource,
            uint32_t samplingRate = 0,
            uint32_t Format = AudioSystem::FORMAT_DEFAULT,
            uint32_t channels = 0,
            AudioSystem::audio_in_acoustics acoustics = (AudioSystem::audio_in_acoustics)0) = 0;
    // indicates to the audio policy manager that the input starts being used.
    virtual status_t startInput(audio_io_handle_t input) = 0;
    // indicates to the audio policy manager that the input stops being used.
    virtual status_t stopInput(audio_io_handle_t input) = 0;
    // releases the input.
    virtual void releaseInput(audio_io_handle_t input) = 0;


    //
    // volume control functions
    //

    // initialises stream volume conversion parameters by specifying volume index range.
    virtual void initStreamVolume(AudioSystem::stream_type stream,
            int indexMin,
            int indexMax) = 0;

    // sets the new stream volume at a level corresponding to the supplied index
    virtual status_t setStreamVolumeIndex(AudioSystem::stream_type stream, int index) = 0;
    // retreive current volume index for the specified stream
    virtual status_t getStreamVolumeIndex(AudioSystem::stream_type stream, int *index) = 0;

class AudioPolicyClientInterface
    virtual audio_io_handle_t openOutput(uint32_t *pDevices,
            uint32_t *pSamplingRate,
            uint32_t *pFormat,
            uint32_t *pChannels,
            uint32_t *pLatencyMs,
            AudioSystem::output_flags flags) = 0;

    // creates a special output that is duplicated to the two outputs passed as arguments. The duplication is performed by
    // a special mixer thread in the AudioFlinger.
    virtual audio_io_handle_t openDuplicateOutput(audio_io_handle_t output1, audio_io_handle_t output2) = 0;
    // closes the output stream
    virtual status_t closeOutput(audio_io_handle_t output) = 0;
    // suspends the output. When an output is suspended, the corresponding audio hardware output stream is placed in
    // standby and the AudioTracks attached to the mixer thread are still processed but the output mix is discarded.
    virtual status_t suspendOutput(audio_io_handle_t output) = 0;
    // restores a suspended output.
    virtual status_t restoreOutput(audio_io_handle_t output) = 0;

    // opens an audio input
    virtual audio_io_handle_t openInput(uint32_t *pDevices,
            uint32_t *pSamplingRate,
            uint32_t *pFormat,
            uint32_t *pChannels,
            uint32_t acoustics) = 0;
    // closes an audio input
    virtual status_t closeInput(audio_io_handle_t input) = 0;


    // set a stream volume for a particular output. For the same user setting, a given stream type can have different volumes
    // for each output (destination device) it is attached to.
    virtual status_t setStreamVolume(AudioSystem::stream_type stream, float volume, audio_io_handle_t output, int delayMs = 0) = 0;

    // reroute a given stream type to the specified output
    virtual status_t setStreamOutput(AudioSystem::stream_type stream, audio_io_handle_t output) = 0;

    // function enabling to send proprietary informations directly from audio policy manager to audio hardware interface.
    virtual void setParameters(audio_io_handle_t ioHandle, const String8& keyValuePairs, int delayMs = 0) = 0;
    // function enabling to receive proprietary informations directly from audio hardware interface to audio policy manager.
    virtual String8 getParameters(audio_io_handle_t ioHandle, const String8& keys) = 0;

    // request the playback of a tone on the specified stream: used for instance to replace notification sounds when playing
    // over a telephony device during a phone call.
    virtual status_t startTone(ToneGenerator::tone_type tone, AudioSystem::stream_type stream) = 0;
    virtual status_t stopTone() = 0;

    // set down link audio volume.
    virtual status_t setVoiceVolume(float volume, int delayMs = 0) = 0;

    virtual status_t setFmVolume(float volume, int delayMs = 0) { return 0; }

extern "C" AudioPolicyInterface* createAudioPolicyManager(AudioPolicyClientInterface *clientInterface);
extern "C" void destroyAudioPolicyManager(AudioPolicyInterface *interface);


// ----------------------------------------------------------------------------
// AudioPolicyManagerBase implements audio policy manager behavior common to all platforms.
// Each platform must implement an AudioPolicyManager class derived from AudioPolicyManagerBase
// and override methods for which the platform specific behavior differs from the implementation
// in AudioPolicyManagerBase. Even if no specific behavior is required, the AudioPolicyManager
// class must be implemented as well as the class factory function createAudioPolicyManager()
// and provided in a shared library libaudiopolicy.so.
// ----------------------------------------------------------------------------
AudioPolicyManagerBase: public AudioPolicyInterface

    void addOutput(audio_io_handle_t id, AudioOutputDescriptor *outputDesc);

    // return the strategy corresponding to a given stream type
    static routing_strategy getStrategy(AudioSystem::stream_type stream);

    virtual uint32_t getDeviceForStrategy(routing_strategy strategy, bool fromCache = true);

    virtual float computeVolume(int stream, int index, audio_io_handle_t output, uint32_t device);
    // check that volume change is permitted, compute and send new volume to audio hardware
    status_t checkAndSetVolume(int stream, int index, audio_io_handle_t output, uint32_t device, int delayMs = 0, bool force = false);
    // apply all stream volumes to the specified output and device
    void applyStreamVolumes(audio_io_handle_t output, uint32_t device, int delayMs = 0);
    // Mute or unmute all streams handled by the specified strategy on the specified output
    void setStrategyMute(routing_strategy strategy, bool on, audio_io_handle_t output, int delayMs = 0);
    // Mute or unmute the stream on the specified output
    void setStreamMute(int stream, bool on, audio_io_handle_t output, int delayMs = 0);

    // true if device is in a telephony or VoIP call
    virtual bool isInCall();
    // true if given state represents a device in a telephony or VoIP call
    virtual bool isStateInCall(int state);

    // descriptor for audio outputs. Used to maintain current configuration of each opened audio output
    // and keep track of the usage of this output by each audio stream type.
    class AudioOutputDescriptor
        audio_io_handle_t mId;              // output handle
        uint32_t mSamplingRate;             //
        uint32_t mFormat;                   //
        uint32_t mChannels;                 // output configuration
        uint32_t mLatency;                  //
        AudioSystem::output_flags mFlags;   //
        uint32_t mDevice;                   // current device this output is routed to
        uint32_t mRefCount[AudioSystem::NUM_STREAM_TYPES]; // number of streams of each type using this output
        AudioOutputDescriptor *mOutput1;    // used by duplicated outputs: first output
        AudioOutputDescriptor *mOutput2;    // used by duplicated outputs: second output
        float mCurVolume[AudioSystem::NUM_STREAM_TYPES];   // current stream volume
        int mMuteCount[AudioSystem::NUM_STREAM_TYPES];     // mute request counter

    // descriptor for audio inputs. Used to maintain current configuration of each opened audio input
    // and keep track of the usage of this input.
    class AudioInputDescriptor
        uint32_t mSamplingRate;                     //
        uint32_t mFormat;                           // input configuration
        uint32_t mChannels;                         //
        AudioSystem::audio_in_acoustics mAcoustics; //
        uint32_t mDevice;                           // current device this input is routed to
        uint32_t mRefCount;                         // number of AudioRecord clients using this output
        int      mInputSource;                     // input source selected by application (mediarecorder.h)

    // stream descriptor used for volume control
    class StreamDescriptor
        int mIndexMin;      // min volume index
        int mIndexMax;      // max volume index
        int mIndexCur;      // current volume index
        bool mCanBeMuted;   // true is the stream can be muted

////////////////////////////////////////////////////////////////////////////////
// Hareware specific implementation
AudioHardware[hardware]: public  AudioHardwareBase[android] : public AudioHardwareInterface[android]
    AudioStreamInMSM72xx[hardware] : public AudioStreamIn[android]
    AudioStreamOutMSM72xx[hardware] : public AudioStreamOut[android]

AudioPolicyManager[hardware]: public AudioPolicyManagerBase[android] : public AudioPolicyInterface[android]



