// the abstraction interface for the audio input hardware
class AudioStreamIn
    /** return audio sampling rate in hz - eg. 44100 */
    virtual uint32_t    sampleRate() const = 0;

    /** return the input buffer size allowed by audio driver */
    virtual size_t      bufferSize() const = 0;

    /** return input channel mask */
    virtual uint32_t    channels() const = 0;

    /** return audio format in 8bit or 16bit PCM format - * eg. AudioSystem:PCM_16_BIT */
    virtual int         format() const = 0;

    /** return the frame size (number of bytes per sample).  */
    uint32_t    frameSize() const { return AudioSystem::popCount(channels())*((format()==AudioSystem::PCM_16_BIT)?
    sizeof(int16_t):sizeof(int8_t)); }

    /** set the input gain for the audio driver. This method is for for future use */
    virtual status_t    setGain(float gain) = 0;

    /** read audio buffer in from audio driver */
    virtual ssize_t     read(void* buffer, ssize_t bytes) = 0;

    /** Put the audio hardware input into standby mode. */
    virtual status_t    standby() = 0;

    virtual status_t    setParameters(const String8& keyValuePairs) = 0;
    virtual String8     getParameters(const String8& keys) = 0;

    virtual unsigned int  getInputFramesLost() const = 0;


