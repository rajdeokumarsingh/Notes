// [finished 2011-09-09]

/* encapsulate interfaces
    AudioFlinger 
        setParameters getParameters 

        volume control:
            setMasterVolume getMasterVolume setMasterMute getMasterMute setStreamVolume 
                setStreamMute getStreamVolume getStreamMute setFmVolume setVoiceVolume

        mic mute:                        
            muteMicrophone isMicrophoneMuted

        input/output parameter:
            getOutputSamplingRate getOutputFrameCount getOutputLatency 
            getInputBufferSize getRenderPosition getInputFramesLost

        setMode isStreamActive newAudioSessionId

    AudioPolicyService
        connection state:
            setDeviceConnectionState getDeviceConnectionState

        input/output control:
            getOutput startOutput stopOutput releaseOutput
            getInput startInput stopInput releaseInput

        volume control:
            initStreamVolume setStreamVolumeIndex getStreamVolumeIndex
        
        stream strategy:
            setForceUse getForceUse getStrategyForStream

        reffect:
            registerEffect unregisterEffect

        setPhoneState setRingerMode
 */

// implement class AudioParameter

// define stream_type in cpp layer
// define audio_devices in cpp layer
// define audio_format
// define audio_channels, input/output channel

// devices和channel的区别:
    // device是物理设备，如earpiece, wired headset, speaker等
    // channel是音效的概念，如高音，低音，重低音，左声道， 右声道, mono, stereo等等

// used to cache output configurations in client process 
// to avoid frequent calls through IAudioFlinger
class OutputDescriptor 
    uint32_t samplingRate;
    int32_t format;
    int32_t channels;
    size_t frameCount;
    uint32_t latency;

// mapping between stream types and outputs
static DefaultKeyedVector<int, audio_io_handle_t> gStreamOutputMap;

// list of output descritor containing cached parameters (sampling rate, framecount, channel count...)
static DefaultKeyedVector<audio_io_handle_t, OutputDescriptor *> gOutputs;

// AudioFlinger's callbak
class AudioFlingerClient: public IBinder::DeathRecipient, public BnAudioFlingerClient
    // indicate a change in the configuration of an output or input: keeps the cached
    // values for output/input parameters up to date in client process
    virtual void ioConfigChanged(int event, int ioHandle, void *param2);

// AudioPolicyService's callbak
class AudioPolicyServiceClient: public IBinder::DeathRecipient

class AudioParameter 
    // reserved parameter keys 
        //  keyRouting: to change audio routing, value is an int in AudioSystem::audio_devices
        //  keySamplingRate: to change sampling rate routing, value is an int
        //  keyFormat: to change audio format, value is an int in AudioSystem::audio_format
        //  keyChannels: to change audio channel configuration, value is an int in AudioSystem::audio_channels
        //  keyFrameCount: to change audio output frame count, value is an int
        //  keyInputSource: to change audio input source, value is an int in audio_source
        //     (defined in media/mediarecorder.h)
    // for fm radio
    const char *AudioParameter::keyFmOn = "fm_on";
    const char *AudioParameter::keyFmOff = "fm_off";

    const char *AudioParameter::keyRouting = "routing";
    const char *AudioParameter::keySamplingRate = "sampling_rate";
    const char *AudioParameter::keyFormat = "format";
    const char *AudioParameter::keyChannels = "channels";
    const char *AudioParameter::keyFrameCount = "frame_count";
    const char *AudioParameter::keyInputSource = "input_source";
    
    // key-value pair
        // key is a String8
        // value could be a String8, a integer or a float
    // example: "routing=4;sampling_rate=343;format=20"

////////////////////////////////////////////////////////////////////////////////
// AudioFlinger function
////////////////////////////////////////////////////////////////////////////////
status_t AudioSystem::muteMicrophone(bool state)
status_t AudioSystem::isMicrophoneMuted(bool* state)
status_t AudioSystem::setMasterVolume(float value)
status_t AudioSystem::getMasterVolume(float* volume)
status_t AudioSystem::setMasterMute(bool mute)
status_t AudioSystem::getMasterMute(bool* mute)
status_t AudioSystem::setStreamVolume(int stream, float value, int output)
status_t AudioSystem::setStreamMute(int stream, bool mute)
status_t AudioSystem::getStreamVolume(int stream, float* volume, int output)
status_t AudioSystem::getStreamMute(int stream, bool* mute)
status_t AudioSystem::setMode(int mode)
status_t AudioSystem::isStreamActive(int stream, bool* state)
status_t AudioSystem::setParameters(audio_io_handle_t ioHandle, const String8& keyValuePairs)
String8 AudioSystem::getParameters(audio_io_handle_t ioHandle, const String8& keys) 

status_t AudioSystem::getOutputSamplingRate(int* samplingRate, int streamType)
    audio_io_handle_t output = getOutput((stream_type)streamType);
    OutputDescriptor * outputDesc = AudioSystem::gOutputs.valueFor(output);
    if (outputDesc == 0) // no cached data, get directly from audio flinger
        *samplingRate = af->sampleRate(output);
    else    // get from cached data
        *samplingRate = outputDesc->samplingRate;

// get output frame count from the cached data 
// or the audio flinger if no cache data found
status_t AudioSystem::getOutputFrameCount(int* frameCount, int streamType)

// get output latency from the cached data 
// or the audio flinger if no cache data found
status_t AudioSystem::getOutputLatency(uint32_t* latency, int streamType)

// get input buffer size from the cached data 
// or the audio flinger if no cache data found
status_t AudioSystem::getInputBufferSize(uint32_t sampleRate, int format, 
        int channelCount, size_t* buffSize)

status_t AudioSystem::setVoiceVolume(float value)
status_t AudioSystem::getRenderPosition(uint32_t *halFrames, uint32_t *dspFrames, int stream)
unsigned int AudioSystem::getInputFramesLost(audio_io_handle_t ioHandle)
int AudioSystem::newAudioSessionId()

status_t AudioSystem::setFmVolume(float value)

// invoked when the audio flinger server dies
void AudioSystem::AudioFlingerClient::binderDied(const wp<IBinder>& who)
    AudioSystem::gAudioFlinger.clear();
    // clear output handles and stream to output map caches
    AudioSystem::gStreamOutputMap.clear();
    AudioSystem::gOutputs.clear();
    gAudioErrorCallback(DEAD_OBJECT);

// called when audio config is changed
// synchronize the cached states with the audio flinger
void AudioSystem::AudioFlingerClient::ioConfigChanged(int event, int ioHandle, void *param2)
    switch (event)
    // remap the stream to a new ioHandle
    case STREAM_CONFIG_CHANGED:
        gStreamOutputMap.replaceValueFor(stream, ioHandle);

    // create a new OutputDescriptor if it is not found in the cache
    case OUTPUT_OPENED: 
        desc = (OutputDescriptor *)param2;
        OutputDescriptor *outputDesc =  new OutputDescriptor(*desc);
        gOutputs.add(ioHandle, outputDesc);

    // remove OutputDescriptor from the cache
    case OUTPUT_CLOSED:
        gOutputs.removeItem(ioHandle);
        for (int i = gStreamOutputMap.size() - 1; i >= 0 ; i--) {
            if (gStreamOutputMap.valueAt(i) == ioHandle) {
                gStreamOutputMap.removeItemsAt(i);

    // replace the OutputDescriptor in the cache
    case OUTPUT_CONFIG_CHANGED:

void AudioSystem::setErrorCallback(audio_error_callback cb)

bool AudioSystem::routedToA2dpOutput(int streamType)
    switch(streamType) 
    case MUSIC: case VOICE_CALL: case BLUETOOTH_SCO: case SYSTEM:
        return true;
    default:
        return false;


////////////////////////////////////////////////////////////////////////////////
// AudioPolicyService function
////////////////////////////////////////////////////////////////////////////////

status_t AudioSystem::setDeviceConnectionState(
    audio_devices device, device_connection_state state, const char *device_address)
AudioSystem::device_connection_state AudioSystem::getDeviceConnectionState(
    audio_devices device, const char *device_address)

status_t AudioSystem::setPhoneState(int state)
status_t AudioSystem::setRingerMode(uint32_t mode, uint32_t mask)
status_t AudioSystem::setForceUse(force_use usage, forced_config config)
AudioSystem::forced_config AudioSystem::getForceUse(force_use usage)

// get output from the cache
// or from the audio policy service if not found
audio_io_handle_t AudioSystem::getOutput(stream_type stream, uint32_t samplingRate, 
        uint32_t format, uint32_t channels, output_flags flags)
status_t AudioSystem::startOutput(audio_io_handle_t output, 
        AudioSystem::stream_type stream, int session)
status_t AudioSystem::stopOutput(audio_io_handle_t output, 
        AudioSystem::stream_type stream, int session)
void AudioSystem::releaseOutput(audio_io_handle_t output)

audio_io_handle_t AudioSystem::getInput(int inputSource, uint32_t samplingRate, 
        uint32_t format, uint32_t channels, audio_in_acoustics acoustics)
status_t AudioSystem::startInput(audio_io_handle_t input)
status_t AudioSystem::stopInput(audio_io_handle_t input)
void AudioSystem::releaseInput(audio_io_handle_t input)


status_t AudioSystem::initStreamVolume(stream_type stream, int indexMin, int indexMax)
status_t AudioSystem::setStreamVolumeIndex(stream_type stream, int index)
status_t AudioSystem::getStreamVolumeIndex(stream_type stream, int *index)

uint32_t AudioSystem::getStrategyForStream(AudioSystem::stream_type stream)
    enum routing_strategy {
        STRATEGY_MEDIA,
        STRATEGY_PHONE,
        STRATEGY_SONIFICATION,
        STRATEGY_DTMF,
        STRATEGY_MEDIA_SONIFICATION,
        NUM_STRATEGIES
    };

status_t AudioSystem::registerEffect(effect_descriptor_t *desc, 
    audio_io_handle_t output, uint32_t strategy, int session, int id)
status_t AudioSystem::unregisterEffect(int id)

// called when the audio policy manager dies
void AudioSystem::AudioPolicyServiceClient::binderDied(const wp<IBinder>& who)
    AudioSystem::gAudioPolicyService.clear();

// use emulated popcount optimization
// http://www.df.lth.se/~john_e/gems/gem002d.html
// FIXME: what is pop count
uint32_t AudioSystem::popCount(uint32_t u)

// true if ((popCount(device) == 1 ) && ((device & ~AudioSystem::DEVICE_IN_ALL) == 0))
bool AudioSystem::isInputDevice(audio_devices device)
// true if ((popCount(device) == 1 ) && ((device & ~AudioSystem::DEVICE_OUT_ALL) == 0))
bool AudioSystem::isOutputDevice(audio_devices device)
// true if ((popCount(device) == 1 ) && ((device & ~AudioSystem::DEVICE_OUT_FM_ALL) == 0))
bool AudioSystem::isFmDevice(audio_devices device)
bool AudioSystem::isA2dpDevice(audio_devices device)
bool AudioSystem::isBluetoothScoDevice(audio_devices device)
bool AudioSystem::isInputChannel(uint32_t channel)
bool AudioSystem::isOutputChannel(uint32_t channel)

// true if (stream == AudioSystem::SYSTEM || stream == AudioSystem::NOTIFICATION || stream == AudioSystem::RING)
bool AudioSystem::isLowVisibility(stream_type stream)
bool AudioSystem::isValidFormat(uint32_t format)
bool AudioSystem::isLinearPCM(uint32_t format)





