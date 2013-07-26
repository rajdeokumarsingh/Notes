
class AudioPolicyClientInterface

    // opens an audio output with the requested parameters. The parameter values can indicate to use the default values
    // in case the audio policy manager has no specific requirements for the output being opened.
    // When the function returns, the parameter values reflect the actual values used by the audio hardware output stream.
    // The audio policy manager can check if the proposed parameters are suitable or not and act accordingly.
    // 打开硬件output. AudioPolicyManagerBase创建时会打开一个output, 默认选择speaker设备
    // 这个output是系统默认的output， 一般情况都只有这个output
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


