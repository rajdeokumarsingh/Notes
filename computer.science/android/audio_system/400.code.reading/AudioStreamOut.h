// the abstraction interface for the audio output hardware
class AudioStreamOut
    /** return audio sampling rate in hz - eg. 44100 */
    virtual uint32_t    sampleRate() const = 0;

    /** returns size of output buffer - eg. 4800 */
    virtual size_t      bufferSize() const = 0;

    /** returns the output channel nask */
    virtual uint32_t    channels() const = 0;

    /** return audio format in 8bit or 16bit PCM format - * eg. AudioSystem:PCM_16_BIT */
    virtual int         format() const = 0;

    /** return the frame size (number of bytes per sample).  */
    uint32_t    frameSize() const { return AudioSystem::popCount(channels())*((format()==AudioSystem::PCM_16_BIT)?
            sizeof(int16_t):sizeof(int8_t)); }

    /** return the audio hardware driver latency in milli seconds.  */
    virtual uint32_t    latency() const = 0;

    /**
     * Use this method in situations where audio mixing is done in the
     * hardware. This method serves as a direct interface with hardware,
     * allowing you to directly set the volume as apposed to via the framework.
     * This method might produce multiple PCM outputs or hardware accelerated
     * codecs, such as MP3 or AAC.
     * FIXME: Jiang Rui, this interface is not used by the framework. The framework 
     *      adjust the volume of media player in a "software" way.
     */
    virtual status_t    setVolume(float left, float right) = 0;

    /** write audio buffer to driver. Returns number of bytes written */
    virtual ssize_t     write(const void* buffer, size_t bytes) = 0;

    /** Put the audio hardware output into standby mode. */
    virtual status_t    standby() = 0;

    virtual status_t    setParameters(const String8& keyValuePairs) = 0;
    virtual String8     getParameters(const String8& keys) = 0;

    // return the number of audio frames written by the audio dsp to DAC since
    // the output has exited standby
    virtual status_t    getRenderPosition(uint32_t *dspFrames) = 0;



