[Finished 2011-09-13]

class AudioPolicyManagerBase: public AudioPolicyInterface

    enum routing_strategy {
        STRATEGY_MEDIA,
        STRATEGY_PHONE,
        STRATEGY_SONIFICATION,
        STRATEGY_DTMF,
        STRATEGY_MEDIA_SONIFICATION,
        NUM_STRATEGIES
    };

    // FIXME:
    select device according to [strategy + force use]

    // 代表一个打开的声音输出，如输出中可包括headset, speaker, BT headset等多个设备
    // 记录了每种stream对该声音设备对引用情况
    // mDevice表示该设备的类型, mId是该设备的一个handle, 随机数, 没有具体的意义
    // 本结构体记录了对于这个声音设备, 每个stream类型的音量, 每个stream类型的mute请求数量
    class AudioOutputDescriptor
        bool isUsedByStrategy(routing_strategy strategy) { return (strategyRefCount(strategy) != 0);}
        bool isDuplicated() { return (mOutput1 != NULL && mOutput2 != NULL); }

        // output handle, FIXME: just a handle, random mumber, bind to the device
        // return by AudioPolicyClientInterface::openOutput
        audio_io_handle_t mId;              

        // current device this output is routed to, FIXME: it's a device type of
        // audio_devices defined in AudioSystem.h
        uint32_t mDevice;                   

        uint32_t mSamplingRate;             //
        uint32_t mFormat;                   //
        uint32_t mChannels;                 // output configuration of audio_channels defined in AudioSystem.h
        uint32_t mLatency;                  //
        AudioSystem::output_flags mFlags;   //
        uint32_t mRefCount[AudioSystem::NUM_STREAM_TYPES]; // number of streams of each type using this output

        AudioOutputDescriptor *mOutput1;    // used by duplicated outputs: first output
        AudioOutputDescriptor *mOutput2;    // used by duplicated outputs: second output
        float mCurVolume[AudioSystem::NUM_STREAM_TYPES];   // current stream volume
        int mMuteCount[AudioSystem::NUM_STREAM_TYPES];     // mute request counter

        // descriptor for audio inputs. Used to maintain current configuration of each opened audio input
        // and keep track of the usage of this input.
        // 
        // mDevice 是定义在AudioSystem.h中的audio_devices中的一种输入类型
        // mInputSource 是定义在mediarecorder.h 中的audio_source中的一种
        // 和AudioOutputDescriptor比较,没有stream的音量
        // AudioPolicyClientInterface::openInput 返回input device的handle
        class AudioInputDescriptor
            uint32_t mSamplingRate;                     //
            uint32_t mFormat;                           // input configuration
            uint32_t mChannels;                         //
            AudioSystem::audio_in_acoustics mAcoustics; //
            uint32_t mDevice;                           // current device this input is routed to
            uint32_t mRefCount;                         // number of AudioRecord clients using this output
            int      mInputSource;                      // input source selected by application (mediarecorder.h)

        // stream descriptor used for volume control
        class StreamDescriptor
            int mIndexMin;      // min volume index
            int mIndexMax;      // max volume index
            int mIndexCur;      // current volume index
            bool mCanBeMuted;   // true is the stream can be muted

        AudioPolicyClientInterface *mpClientInterface;  // audio policy client interface, AudioPolicyService
        audio_io_handle_t mHardwareOutput;              // hardware output handler
        audio_io_handle_t mA2dpOutput;                  // A2DP output handler
        audio_io_handle_t mDuplicatedOutput;            // duplicated output handler: outputs to hardware and A2DP.

        KeyedVector<audio_io_handle_t, AudioOutputDescriptor *> mOutputs;   // list of output descriptors
        KeyedVector<audio_io_handle_t, AudioInputDescriptor *> mInputs;     // list of input descriptors
        uint32_t mAvailableOutputDevices;                                   // bit field of all available output devices
        uint32_t mAvailableInputDevices;                                    // bit field of all available input devices
        int mPhoneState;                                                    // current phone state
        uint32_t                 mRingerMode;                               // current ringer mode
        AudioSystem::forced_config mForceUse[AudioSystem::NUM_FORCE_USE];   // current forced use configuration

// 1. initialize internal status, mForceUse
// 2. set status, devices available by default are speaker, ear piece and microphone
// 3. open hardware output, the out speaker
// 4. set routing to the opened output device by setOutputDevice
// 5. update device cache by updateDeviceForStrategy() 
AudioPolicyManagerBase::AudioPolicyManagerBase(AudioPolicyClientInterface *clientInterface)
    // init mForceUse[] to AudioSystem::FORCE_NONE

    // devices available by default are speaker, ear piece and microphone
    mAvailableOutputDevices = AudioSystem::DEVICE_OUT_EARPIECE | AudioSystem::DEVICE_OUT_SPEAKER;
    mAvailableInputDevices = AudioSystem::DEVICE_IN_BUILTIN_MIC;

    // open hardware output, the out speaker
    AudioOutputDescriptor *outputDesc = new AudioOutputDescriptor();
    outputDesc->mDevice = (uint32_t)AudioSystem::DEVICE_OUT_SPEAKER;
    mHardwareOutput = mpClientInterface->openOutput(&outputDesc->mDevice, &outputDesc->mSamplingRate, 
            &outputDesc->mFormat, &outputDesc->mChannels, &outputDesc->mLatency, outputDesc->mFlags);

    addOutput(mHardwareOutput, outputDesc);
    setOutputDevice(mHardwareOutput, (uint32_t)AudioSystem::DEVICE_OUT_SPEAKER, true);

    updateDeviceForStrategy();

// ----------------------------------------------------------------------------                                                              
// AudioPolicyInterface implementation
// ----------------------------------------------------------------------------  

// 1. set device bit in mAvailableOutputDevices/mAvailableInputDevices
// 2. check routing and implement new routing by setParameters()
// 3. For the output devices, also need to check a2dp, bt status
status_t AudioPolicyManagerBase::setDeviceConnectionState(AudioSystem::audio_devices device, 
        AudioSystem::device_connection_state state, const char *device_address) 
    // handle output devices
    if (AudioSystem::isOutputDevice(device)) 
        switch (state)
            // handle output device connection
            case AudioSystem::DEVICE_STATE_AVAILABLE:
                // register new device as available
                mAvailableOutputDevices |= device;

                // handle a2dp device
                // handle bt sco device
                // handle FMR
                if (AudioSystem::isFmDevice(device)) 
                    // get output descriptor from cache, and add referenece for FM
                    AudioOutputDescriptor * hwOutputDesc = mOutputs.valueFor(mHardwareOutput);
                    hwOutputDesc->mRefCount[AudioSystem::FM] = 1;

                    // set parameter to open FM
                    AudioParameter param = AudioParameter();
                    param.addInt(String8(AudioParameter::keyFmOn), mAvailableOutputDevices);
                    mpClientInterface->setParameters(mHardwareOutput, param.toString());

            // handle output device disconnection
            case AudioSystem::DEVICE_STATE_UNAVAILABLE:
                // remove device from available output devices
                mAvailableOutputDevices &= ~device;

                // handle A2DP device disconnection
                // handle BT sco device disconnection
                
                // handle FMR 
                if (AudioSystem::isFmDevice(device)) {
                    // remove the reference count in the output descriptor
                    AudioOutputDescriptor *hwOutputDesc = mOutputs.valueFor(mHardwareOutput);
                    hwOutputDesc->mRefCount[AudioSystem::FM] = 0;

                    // set parameter to close FM
                    AudioParameter param = AudioParameter();
                    param.addInt(String8(AudioParameter::keyFmOff), mAvailableOutputDevices);
                    mpClientInterface->setParameters(mHardwareOutput, param.toString());

        // request routing change if necessary
        // 通过当前设备mHardwareOutput的strategy状态, 调用 
        // getDeviceForStrategy(current strategy, fromCache = false);
        // 获取新的符合当前strategy的设备
        uint32_t newDevice = getNewDevice(mHardwareOutput, false);

        // 更新cache, mDeviceForStrategy[i], 既每种strategy对应的设备
        updateDeviceForStrategy();

        // Route sound to the new device
        // 调用setParameters(AudioParameter::keyRouting, newDevice)设置新的输出设备
        setOutputDevice(mHardwareOutput, newDevice);

    // handle input devices
    if (AudioSystem::isInputDevice(device))
        switch (state)
            // handle input device connection
            case AudioSystem::DEVICE_STATE_AVAILABLE: 
                mAvailableInputDevices |= device;

            // handle input device disconnection
            case AudioSystem::DEVICE_STATE_UNAVAILABLE: {
                mAvailableInputDevices &= ~device;

        audio_io_handle_t activeInput = getActiveInput();
        if (activeInput != 0) 
            AudioInputDescriptor *inputDesc = mInputs.valueFor(activeInput);
            // 获取当前优先级最高的输入设备
            uint32_t newDevice = getDeviceForInputSource(inputDesc->mInputSource);
            // 通过调用setParameters设置新的输入设备
            if (newDevice != inputDesc->mDevice) {
                inputDesc->mDevice = newDevice;
                AudioParameter param = AudioParameter();
                param.addInt(String8(AudioParameter::keyRouting), (int)newDevice);
                mpClientInterface->setParameters(activeInput, param.toString());

// get device state in mAvailableOutputDevices/mAvailableInputDevices
AudioSystem::device_connection_state AudioPolicyManagerBase::getDeviceConnectionState(
        AudioSystem::audio_devices device, const char *device_address)

// 1. get current phone state and the old phone state
// 2. check for device and output changes triggered by new phone state
// 3. update device for strategy
// 4. change routing
// 5. mute ringtone if enter call connected state
void AudioPolicyManagerBase::setPhoneState(int state)
    if (isInCall())  // 比如正在播放闹钟， 这时来电, 需要mute/play tone
        for (int stream = 0; stream < AudioSystem::NUM_STREAM_TYPES; stream++) {
            handleIncallSonification(stream, false, true);

    // check for device and output changes triggered by new phone state
    newDevice = getNewDevice(mHardwareOutput, false);
    updateDeviceForStrategy();

    AudioOutputDescriptor *hwOutputDesc = mOutputs.valueFor(mHardwareOutput);
    // force routing command to audio hardware when ending call
    // even if no device change is needed
    if (isStateInCall(oldState) && newDevice == 0)
        newDevice = hwOutputDesc->device();

    // when changing from ring tone to in call mode, mute the ringing tone
    // immediately and delay the route change to avoid sending the ring tone
    // tail into the earpiece or headset.
    int delayMs = 0;
    if (isStateInCall(state) && oldState == AudioSystem::MODE_RINGTONE) {
        // delay the device change command by twice the output latency to have some margin
        // and be sure that audio buffers not yet affected by the mute are out when
        // we actually apply the route change
        delayMs = hwOutputDesc->mLatency*2;
        setStreamMute(AudioSystem::RING, true, mHardwareOutput);

    // change routing is necessary
    setOutputDevice(mHardwareOutput, newDevice, force, delayMs);

    // if entering in call state, handle special case of active streams
    // pertaining to sonification strategy see handleIncallSonification()
    if (isStateInCall(state)) 
        // unmute the ringing tone after a sufficient delay if it was muted before
        // setting output device above
        if (oldState == AudioSystem::MODE_RINGTONE) {
            setStreamMute(AudioSystem::RING, false, mHardwareOutput, MUTE_TIME_MS);

        for (int stream = 0; stream < AudioSystem::NUM_STREAM_TYPES; stream++) {
            handleIncallSonification(stream, true, true);

void AudioPolicyManagerBase::setRingerMode(uint32_t mode, uint32_t mask)
    mRingerMode = mode;


// 1. add the config to mForceUse
// 2. update output routing
// 3. update input routing
void AudioPolicyManagerBase::setForceUse(AudioSystem::force_use usage, AudioSystem::forced_config config)
    mForceUse[usage] = config;

    // check for device and output changes triggered by new phone state
    uint32_t newDevice = getNewDevice(mHardwareOutput, false);
    // update devices in the cache
    updateDeviceForStrategy();
    // route the mHardwareOutput to the newDevice
    setOutputDevice(mHardwareOutput, newDevice);

    // route current input to the newDevice
    audio_io_handle_t activeInput = getActiveInput();
    if (activeInput != 0) {
        AudioInputDescriptor *inputDesc = mInputs.valueFor(activeInput);
        newDevice = getDeviceForInputSource(inputDesc->mInputSource);
        if (newDevice != inputDesc->mDevice) {
            inputDesc->mDevice = newDevice;
            AudioParameter param = AudioParameter();
            param.addInt(String8(AudioParameter::keyRouting), (int)newDevice);
            mpClientInterface->setParameters(activeInput, param.toString());


AudioSystem::forced_config AudioPolicyManagerBase::getForceUse(AudioSystem::force_use usage)
     return mForceUse[usage];


// 1. if needs a direct output, create an out by AudioPolicyClientInterface::openOutput
// 2. else return mHardwareOutput
audio_io_handle_t AudioPolicyManagerBase::getOutput(AudioSystem::stream_type stream, 
    uint32_t samplingRate, uint32_t format, uint32_t channels, AudioSystem::output_flags flags) 

    routing_strategy strategy = getStrategy((AudioSystem::stream_type)stream);
    uint32_t device = getDeviceForStrategy(strategy);

    // open a direct output if required by specified parameters
    // 默认的output都不是direct output。如果output的格式不是Linear PCM(PCM_16_BIT, PCM_8_BIT)
    // 才会需要direct output
    if (needsDirectOuput(stream, samplingRate, format, channels, flags, device)) 
        AudioOutputDescriptor *outputDesc = new AudioOutputDescriptor();
        outputDesc->mDevice = device;
        outputDesc->mSamplingRate = samplingRate;
        outputDesc->mFormat = format;
        outputDesc->mChannels = channels;
        outputDesc->mLatency = 0;
        outputDesc->mFlags = (AudioSystem::output_flags)(flags | AudioSystem::OUTPUT_FLAG_DIRECT);
        outputDesc->mRefCount[stream] = 0;
        audio_io_handle_t output = mpClientInterface->openOutput(&outputDesc->mDevice, &outputDesc->mSamplingRate,
                &outputDesc->mFormat, &outputDesc->mChannels, &outputDesc->mLatency, outputDesc->mFlags);
        addOutput(output, outputDesc);
        return output;

    // open a non direct output
    output = mHardwareOutput;
    return output;
        
// 1. get the strategy from the stream type
// 2. incremenent usage count for this stream
// 3. routing to new device
// 4. handle special case for sonification while in call
// 5. apply volume rules for current stream and device if necessary
status_t AudioPolicyManagerBase::startOutput(audio_io_handle_t output, 
        AudioSystem::stream_type stream, int session)
    ssize_t index = mOutputs.indexOfKey(output);

    AudioOutputDescriptor *outputDesc = mOutputs.valueAt(index);
    routing_strategy strategy = getStrategy((AudioSystem::stream_type)stream);

    // incremenent usage count for this stream on the requested output:
    outputDesc->changeRefCount(stream, 1);

    setOutputDevice(output, getNewDevice(output));

    // handle special case for sonification while in call
    if (isInCall()) handleIncallSonification(stream, true, false);

    // apply volume rules for current stream and device if necessary
    checkAndSetVolume(stream, mStreams[stream].mIndexCur, output, outputDesc->device());


// 1. decrement usage count of this stream on the output
// 2. update the output device by setOutputDevice()
status_t AudioPolicyManagerBase::stopOutput(audio_io_handle_t output, 
        AudioSystem::stream_type stream, int session)

    ssize_t index = mOutputs.indexOfKey(output);
    AudioOutputDescriptor *outputDesc = mOutputs.valueAt(index);
    routing_strategy strategy = getStrategy((AudioSystem::stream_type)stream);

    // handle special case for sonification while in call
    if (isInCall()) {
        handleIncallSonification(stream, false, false);

    if (outputDesc->mRefCount[stream] > 0) 
        // decrement usage count of this stream on the output
        outputDesc->changeRefCount(stream, -1);

        // store time at which the last music track was stopped - see computeVolume()
        if (stream == AudioSystem::MUSIC) {
            mMusicStopTime = systemTime();

        setOutputDevice(output, getNewDevice(output));

        if (output != mHardwareOutput) {
            setOutputDevice(mHardwareOutput, getNewDevice(mHardwareOutput), true);
        return NO_ERROR;

// 1. release and close a direct output, do nothing for a indirect output
void AudioPolicyManagerBase::releaseOutput(audio_io_handle_t output)

// 1. create a audio input and open it by AudioPolicyClientInterface::openInput
// 2. add the input to the system
audio_io_handle_t AudioPolicyManagerBase::getInput(int inputSource, uint32_t samplingRate, 
        uint32_t format, uint32_t channels, AudioSystem::audio_in_acoustics acoustics)

    uint32_t device = getDeviceForInputSource(inputSource);

    // adapt channel selection to input source
    switch(inputSource) {
        case AUDIO_SOURCE_VOICE_UPLINK:
            channels = AudioSystem::CHANNEL_IN_VOICE_UPLINK;
        case AUDIO_SOURCE_VOICE_DOWNLINK:
            channels = AudioSystem::CHANNEL_IN_VOICE_DNLINK;
        case AUDIO_SOURCE_VOICE_CALL:
            channels = (AudioSystem::CHANNEL_IN_VOICE_UPLINK | AudioSystem::CHANNEL_IN_VOICE_DNLINK);

    AudioInputDescriptor *inputDesc = new AudioInputDescriptor();
    inputDesc->mInputSource = inputSource;
    inputDesc->mDevice = device;
    inputDesc->mSamplingRate = samplingRate;
    inputDesc->mFormat = format;
    inputDesc->mChannels = channels;
    inputDesc->mAcoustics = acoustics;
    inputDesc->mRefCount = 0;
    audio_io_handle_t input = mpClientInterface->openInput(&inputDesc->mDevice, 
        &inputDesc->mSamplingRate, &inputDesc->mFormat, &inputDesc->mChannels, inputDesc->mAcoustics);

    mInputs.add(input, inputDesc);
    return input;

// 1. get input descriptor from the system
// 2. set parameters, keyRouting and keyInputSource for the input device
status_t AudioPolicyManagerBase::startInput(audio_io_handle_t input)
        ssize_t index = mInputs.indexOfKey(input);

    AudioInputDescriptor *inputDesc = mInputs.valueAt(index);
    AudioParameter param = AudioParameter();
    param.addInt(String8(AudioParameter::keyRouting), (int)inputDesc->mDevice);
    param.addInt(String8(AudioParameter::keyInputSource), (int)inputDesc->mInputSource);
    mpClientInterface->setParameters(input, param.toString());
    inputDesc->mRefCount = 1;

// 1. get the input descriptor from the system
// 2. set parameters, keyRouting, to 0
status_t AudioPolicyManagerBase::stopInput(audio_io_handle_t input)
    ssize_t index = mInputs.indexOfKey(input);
    AudioInputDescriptor *inputDesc = mInputs.valueAt(index);

    if (inputDesc->mRefCount != 0)
        AudioParameter param = AudioParameter();
        param.addInt(String8(AudioParameter::keyRouting), 0);
        mpClientInterface->setParameters(input, param.toString());
        inputDesc->mRefCount = 0;
        return NO_ERROR;

// 1. get the input from the system
// 2. close input by AudioPolicyClientInterface::closeInput
// 3. delete the input descriptor and remove it from the system
void AudioPolicyManagerBase::releaseInput(audio_io_handle_t input)
    ssize_t index = mInputs.indexOfKey(input);
    mpClientInterface->closeInput(input);
    delete mInputs.valueAt(index);
    mInputs.removeItem(input);

// 1. set the min and max volume for a stream
void AudioPolicyManagerBase::initStreamVolume(
        AudioSystem::stream_type stream, int indexMin, int indexMax)
    mStreams[stream].mIndexMin = indexMin;
    mStreams[stream].mIndexMax = indexMax;

// 1. set current volume index
// 2. compute and apply stream volume on all outputs according to connected device
//      because a stream could be outputed from several devices at same time
status_t AudioPolicyManagerBase::setStreamVolumeIndex(AudioSystem::stream_type stream, int index)
    mStreams[stream].mIndexCur = index;

    // compute and apply stream volume on all outputs according to connected device
    status_t status = NO_ERROR;
    for (size_t i = 0; i < mOutputs.size(); i++) {
        status_t volStatus = checkAndSetVolume(stream, index, mOutputs.keyAt(i), mOutputs.valueAt(i)->device());


status_t AudioPolicyManagerBase::getStreamVolumeIndex(AudioSystem::stream_type stream, int *index)
    *index =  mStreams[stream].mIndexCur;

// ----------------------------------------------------------------------------
// AudioPolicyManagerBase
// ----------------------------------------------------------------------------
AudioPolicyManagerBase::AudioPolicyManagerBase(AudioPolicyClientInterface *clientInterface)
    // init mForceUse[] to AudioSystem::FORCE_NONE

    // devices available by default are speaker, ear piece and microphone
    mAvailableOutputDevices = AudioSystem::DEVICE_OUT_EARPIECE | AudioSystem::DEVICE_OUT_SPEAKER;
    mAvailableInputDevices = AudioSystem::DEVICE_IN_BUILTIN_MIC;

    // open hardware output, the out speaker
    AudioOutputDescriptor *outputDesc = new AudioOutputDescriptor();
    outputDesc->mDevice = (uint32_t)AudioSystem::DEVICE_OUT_SPEAKER;
    mHardwareOutput = mpClientInterface->openOutput(&outputDesc->mDevice, 
        &outputDesc->mSamplingRate, &outputDesc->mFormat, &outputDesc->mChannels, 
        &outputDesc->mLatency, outputDesc->mFlags);

    addOutput(mHardwareOutput, outputDesc);
    setOutputDevice(mHardwareOutput, (uint32_t)AudioSystem::DEVICE_OUT_SPEAKER, true);
    //TODO: configure audio effect output stage here

    updateDeviceForStrategy();

// 1. close and delete all output devices by AudioPolicyClientInterface::closeOutput
// 2. close and delete all input devices by AudioPolicyClientInterface::closeInput
AudioPolicyManagerBase::~AudioPolicyManagerBase()
    
// 1. add the output descriptor to the device cache 
void AudioPolicyManagerBase::addOutput(audio_io_handle_t id, AudioOutputDescriptor *outputDesc)

uint32_t AudioPolicyManagerBase::getNewDevice(audio_io_handle_t output, bool fromCache)
    AudioOutputDescriptor *outputDesc = mOutputs.valueFor(output);
    // check the following by order of priority to request a routing change if necessary:
    // 1: we are in call or the strategy phone is active on the hardware output:
    //      use device for strategy phone
    // 2: the strategy sonification is active on the hardware output:
    //      use device for strategy sonification
    // 3: the strategy media sonification is active on the hardware output:
    //      use device for strategy media sonification
    // 4: the strategy media is active on the hardware output:
    //      use device for strategy media
    // 5: the strategy DTMF is active on the hardware output:
    //      use device for strategy DTMF
    return device = getDeviceForStrategy(STRATEGY_PHONE/STRATEGY_SONIFICATION, ..., fromCache);

AudioPolicyManagerBase::routing_strategy AudioPolicyManagerBase::getStrategy(AudioSystem::stream_type stream) 
    // stream to strategy mapping  
    switch (stream) {
        case AudioSystem::VOICE_CALL:  
        case AudioSystem::BLUETOOTH_SCO:   
            return STRATEGY_PHONE;

        case AudioSystem::RING:
        case AudioSystem::NOTIFICATION:
        case AudioSystem::ALARM:
        case AudioSystem::ENFORCED_AUDIBLE:
            return STRATEGY_SONIFICATION;

        case AudioSystem::DTMF:
            return STRATEGY_DTMF;

        case AudioSystem::SYSTEM:
            // NOTE: SYSTEM stream uses MEDIA strategy because muting music and switching outputs
            // while key clicks are played produces a poor result
        case AudioSystem::TTS:
        case AudioSystem::MUSIC:
            return STRATEGY_MEDIA;

#ifdef HAVE_FM_RADIO
        case AudioSystem::FM:
#endif
            return STRATEGY_MEDIA;

/* 1. define the priority of different devices in each strategy
在phone strategy下
    如果没有force use[AudioSystem::FOR_COMMUNICATION], 
        DEVICE_OUT_WIRED_HEADPHONE > DEVICE_OUT_WIRED_HEADSET > DEVICE_OUT_EARPIECE

    如果force use[AudioSystem::FOR_COMMUNICATION]为AudioSystem::FORCE_BT_SCO:
        DEVICE_OUT_BLUETOOTH_SCO_CARKIT(!isIncall) > DEVICE_OUT_BLUETOOTH_SCO_HEADSET > DEVICE_OUT_BLUETOOTH_SCO

    如果force use[AudioSystem::FOR_COMMUNICATION]为FORCE_SPEAKER
        在in call的情况使用 speaker

对于notification
    如果in call状态, 则按照phone strategy来选择设备
    非in call状态, 优先选择speaker
 
对于media strategy  
    AUX_DIGITAL > WIRED_HEADPHONE > WIRED_HEADSET > BLUETOOTH_A2DP > SPEAKER

对于STRATEGY_DTMF
    如果in call, 同phone strategy
    如果非in call, 同media strategy
*/
uint32_t AudioPolicyManagerBase::getDeviceForStrategy(routing_strategy strategy, bool fromCache)
    if (fromCache)
        return mDeviceForStrategy[strategy];

    switch (strategy) 
        case STRATEGY_DTMF:
        ...

    return device;

// 1.update devices for every strategy
// 2. cache the devices
void AudioPolicyManagerBase::updateDeviceForStrategy()
    for (int i = 0; i < NUM_STRATEGIES; i++) {
        mDeviceForStrategy[i] = getDeviceForStrategy((routing_strategy)i, false);

// 1. attach the "device" to the "output" descriptor, if no same device
// 2. do the routing by setParameters
// 3. update stream volumes according to new device
void AudioPolicyManagerBase::setOutputDevice(audio_io_handle_t output, uint32_t device, bool force, int delayMs)
    AudioOutputDescriptor *outputDesc = mOutputs.valueFor(output);

    // FIXME: what is duplicated?
    if (outputDesc->isDuplicated()) {
        setOutputDevice(outputDesc->mOutput1->mId, device, force, delayMs);
        setOutputDevice(outputDesc->mOutput2->mId, device, force, delayMs);
        return;

    // Do not change the routing if:
    //  - the requestede device is 0
    //  - the requested device is the same as current device and force is not specified.
    // Doing this check here allows the caller to call setOutputDevice() without conditions

    outputDesc->mDevice = device;

    // mute media streams if both speaker and headset are selected
    // will unmute before this function returns
    if (output == mHardwareOutput && AudioSystem::popCount(device) == 2) {
        setStrategyMute(STRATEGY_MEDIA, true, output);
        // wait for the PCM output buffers to empty before proceeding with the rest of the command
        usleep(outputDesc->mLatency*2*1000);

    // do the routing
    AudioParameter param = AudioParameter();
    param.addInt(String8(AudioParameter::keyRouting), (int)device);
    mpClientInterface->setParameters(mHardwareOutput, param.toString(), delayMs);

    // FIXME: set the inital volume for the stream
    // update stream volumes according to new device
    applyStreamVolumes(output, device, delayMs);

    // if changing from a combined headset + speaker route, unmute media streams
    if (output == mHardwareOutput && AudioSystem::popCount(prevDevice) == 2) {
        setStrategyMute(STRATEGY_MEDIA, false, output, delayMs);


/* 1. 对于audio_source AUDIO_SOURCE_DEFAULT: AUDIO_SOURCE_MIC: AUDIO_SOURCE_VOICE_RECOGNITION: AUDIO_SOURCE_VOICE_COMMUNICATION:
    DEVICE_IN_BLUETOOTH_SCO_HEADSET ((mForceUse[AudioSystem::FOR_RECORD] == AudioSystem::FORCE_BT_SCO )
    > DEVICE_IN_WIRED_HEADSET > DEVICE_IN_BUILTIN_MIC

   2. 对于AUDIO_SOURCE_CAMCORDER:
        DEVICE_IN_BACK_MIC > DEVICE_IN_BUILTIN_MIC

   3. 对于AUDIO_SOURCE_VOICE_UPLINK: AUDIO_SOURCE_VOICE_DOWNLINK: AUDIO_SOURCE_VOICE_CALL:
        使用DEVICE_IN_VOICE_CALL */
uint32_t AudioPolicyManagerBase::getDeviceForInputSource(int inputSource)

// return an active input from the input device cache list
audio_io_handle_t AudioPolicyManagerBase::getActiveInput()

// convert the volume index to a float, from 0 to 1.0
float AudioPolicyManagerBase::computeVolume(int stream, int index, audio_io_handle_t output, uint32_t device)
    float volume = 1.0;
    AudioOutputDescriptor *outputDesc = mOutputs.valueFor(output);
    StreamDescriptor &streamDesc = mStreams[stream];

    int volInt = (100 * (index - streamDesc.mIndexMin)) / (streamDesc.mIndexMax - streamDesc.mIndexMin);
    volume = AudioSystem::linearToLog(volInt);

    // if a headset is connected, apply the following rules to ring tones and notifications
    // to avoid sound level bursts in user's ears:
    // - always attenuate ring tones and notifications volume by 6dB
    // - if music is playing, always limit the volume to current music volume,
    // with a minimum threshold at -36dB so that notification is always perceived.
    return volume;


/* 1. compute the volume for the stream and device
   2. set volume by 
    AudioPolicyClientInterface::setStreamVolumeIndex() or 
    AudioPolicyClientInterface::setVoiceVolume() or 
    AudioPolicyClientInterface::setFmVolume() */
status_t AudioPolicyManagerBase::checkAndSetVolume(int stream, int index, 
    audio_io_handle_t output, uint32_t device, int delayMs, bool force)

// apply volumes for every stream
void AudioPolicyManagerBase::applyStreamVolumes(audio_io_handle_t output, uint32_t device, int delayMs)
    for (int stream = 0; stream < AudioSystem::NUM_STREAM_TYPES; stream++) {
        checkAndSetVolume(stream, mStreams[stream].mIndexCur, output, device, delayMs);

// 对符合strategy的所有stream, 调用mute
void AudioPolicyManagerBase::setStrategyMute(routing_strategy strategy, bool on, audio_io_handle_t output, int delayMs)
    for (int stream = 0; stream < AudioSystem::NUM_STREAM_TYPES; stream++) {
        if (getStrategy((AudioSystem::stream_type)stream) == strategy) {
            setStreamMute(stream, on, output, delayMs);

// 0. 如果output中存在打开的stream
// 1. invoking checkAndSetVolume(0) to mute 
// 2. invoking checkAndSetVolume(streamDesc.mIndexCur) to unmute
void AudioPolicyManagerBase::setStreamMute(int stream, bool on, audio_io_handle_t output, int delayMs)

// 1. if the notification is low visibility, mute the stream
// 2. if the notification is high visibility, play a tone by AudioPolicyClientInterface::startTone
void AudioPolicyManagerBase::handleIncallSonification(int stream, bool starting, bool stateChange)

// 不是linear pcm的声音格式需要direct output
bool AudioPolicyManagerBase::needsDirectOuput(AudioSystem::stream_type stream, uint32_t samplingRate, 
        uint32_t format, uint32_t channels, AudioSystem::output_flags flags, uint32_t device)
    return ((flags & AudioSystem::OUTPUT_FLAG_DIRECT) ||
            (format !=0 && !AudioSystem::isLinearPCM(format)));

