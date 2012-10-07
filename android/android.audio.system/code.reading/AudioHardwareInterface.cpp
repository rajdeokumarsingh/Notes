
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
    /**
     * check to see if the audio hardware interface has been initialized.
     */
    virtual status_t    initCheck() = 0;

    /** set the audio volume of a voice call. Range is between 0.0 and 1.0 */
    virtual status_t    setVoiceVolume(float volume) = 0;

    /**
     * set the audio volume for all audio activities other than voice call.
     * Range between 0.0 and 1.0. If any value other than NO_ERROR is returned,
     * the software mixer will emulate this capability.
     * FIXME: Jiang Rui, seem the framework only set master volume to 1.0
     */
    virtual status_t    setMasterVolume(float volume) = 0;

    /**
     * setMode is called when the audio mode changes. NORMAL mode is for
     * standard audio playback, RINGTONE when a ringtone is playing, and IN_CALL
     * when a call is in progress.
     */
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

    static AudioHardwareInterface* create();


AudioHardwareInterface* AudioHardwareInterface::create()
#ifdef GENERIC_AUDIO
    hw = new AudioHardwareGeneric();
#else
    // creating vendor specific audiohardware
    hw = createAudioHardware();
#endif 

#ifdef WITH_A2DP
    hw = new A2dpAudioInterface(hw);
#endif
    return hw;

