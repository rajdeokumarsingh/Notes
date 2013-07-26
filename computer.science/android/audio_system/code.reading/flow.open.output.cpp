
output和device的关系
    device代表具体的物理设备，如耳机，speaker, earpiece
        定义在frameworks/base/include/media/AudioSystem.h enum audio_devices中 

    output是一组物理设备的集合
        定义在libhardware_legacy/include/hardware_legacy/AudioPolicyManagerBase.h的
            AudioOutputDescriptor中

        每个output有一个handle, audio_io_handle_t(uint_32), 对应一个AudioOutputDescriptor

        系统中的output
            1. 系统中默认的output是mHardwareOutput
                其中可以包括speaker, earpiece, headset等等device
                一般情况系统中只有这个output

            2. BT A2DP是一个独立的output

            3. direct output是独立的output
                什么是direct output
                    不是linearPCM或指定了OUTPUT_FLAG_DIRECT

                    // force direct flag if format is not linear PCM
                    if (!AudioSystem::isLinearPCM(format)) {
                        flags |= AudioSystem::OUTPUT_FLAG_DIRECT;
                    }  

        output之间的切换
            系统开机时，会通过openOutput默认打开default output
            蓝牙连接上时，会通过openOutput打开a2dp output

            这是系统中就有了两个output, 播放音乐的时候如何选择
                AudioPolicyManagerBase::getOutput()
                    初始化AudioTrack的时候，AudioTrack::set()会调用getOutput()
                    getOutput会判断到底声音需要从那个output输出, 将output返回给AudioTrack
                    startOutput时就会选择这个output输出声音

            如果a2dp连接断开时，会通过checkOutputForAllStrategies()
                设置每个stream的output
                    setStreamOutput((AudioSystem::stream_type)i, mHardwareOutput);
           
// 场景1: 系统启动声音设备初始化
// 1.1. 添加可用输出设备earpiece和speaker
// 1.2. 添加可用输入设备mic
// 1.3. 打开输出设备
// 1.4. 将设备的路径切换到speaker, 这时speaker是可用的并且打开的输出设备
AudioPolicyManagerBase::AudioPolicyManagerBase(AudioPolicyClientInterface *clientInterface)
    // devices available by default are speaker, ear piece and microphone
    mAvailableOutputDevices = AudioSystem::DEVICE_OUT_EARPIECE |
                              AudioSystem::DEVICE_OUT_SPEAKER;
    mAvailableInputDevices = AudioSystem::DEVICE_IN_BUILTIN_MIC;

    // open hardware output
    AudioOutputDescriptor *outputDesc = new AudioOutputDescriptor();
    outputDesc->mDevice = (uint32_t)AudioSystem::DEVICE_OUT_SPEAKER;
    mHardwareOutput = mpClientInterface->openOutput(&outputDesc->mDevice,
            &outputDesc->mSamplingRate,
            &outputDesc->mFormat,
            &outputDesc->mChannels,
            &outputDesc->mLatency,
            outputDesc->mFlags);

    addOutput(mHardwareOutput, outputDesc);
    setOutputDevice(mHardwareOutput, (uint32_t)AudioSystem::DEVICE_OUT_SPEAKER, true);

    // update devices for each strategy in cache
    updateDeviceForStrategy();

// 1.3.1 openOutputStream打开硬件output
// 1.3.2 为该output分配一个id, 也就是该output的audio_io_handle_t
// 1.3.3 如果是direct output, 启动一个DirectOutputThread, 该thread和output的id绑定
// 1.3.4 否则, 启动一个MixerThread来等待处理AudioTracker, 该thread和output的id绑定
// 1.3.5 将thread和id加入到mPlaybackThreads中
// 1.3.6 返回id
int AudioFlinger::openOutput(uint32_t *pDevices,
        uint32_t *pSamplingRate,
        uint32_t *pFormat,
        uint32_t *pChannels,
        uint32_t *pLatencyMs,
        uint32_t flags)

    AudioStreamOut *output = mAudioHardware->openOutputStream(*pDevices,
            (int *)&format,
            &channels,
            &samplingRate,
            &status);

    mHardwareStatus = AUDIO_HW_IDLE;
    if (output != 0) {
        int id = nextUniqueId();
        if ((flags & AudioSystem::OUTPUT_FLAG_DIRECT) ||
                (format != AudioSystem::PCM_16_BIT) ||
                (channels != AudioSystem::CHANNEL_OUT_STEREO)) {
            thread = new DirectOutputThread(this, output, id, *pDevices);
            LOGV("openOutput() created direct output: ID %d thread %p", id, thread);
        } else {
            thread = new MixerThread(this, output, id, *pDevices);
            LOGV("openOutput() created mixer output: ID %d thread %p", id, thread);

    mPlaybackThreads.add(id, thread);
    return id;

// 1.4 设置/切换output的物理设备
// 1.4.1  如果设置的设备数目大于2，需要将output mute。并睡眠硬件latency的2倍时间
//      比如系统连着耳机，Music应用正在播放歌曲，这时来了一个notification。
//      按照默认的strategy, 音乐应该停止, notifcation应该从耳机和speaker中播放出来
//      音乐停止后，硬件buffer中还是会有一些声音数据需要输出
//      如果设备切换很快，那么这些声音会从speaker中播放出来。
// FIXME: 照理说只要有媒体正在播放(music, fm)的情况，切换声音通道播放另外一种声音, 都需要delay
// 1.4.2 调用setParameters切换声音通道
// 1.4.3 更新设备的音量
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

    // update stream volumes according to new device
    applyStreamVolumes(output, device, delayMs);

    // if changing from a combined headset + speaker route, unmute media streams
    if (output == mHardwareOutput && AudioSystem::popCount(prevDevice) == 2) {
        setStrategyMute(STRATEGY_MEDIA, false, output, delayMs);

// 场景2：当有物理设备连接到手机时
// 2.1 setDeviceConnectionState会被调用
// 2.2 设置mAvailableOutputDevices/mAvailableInputDevices中对应device的bit位
//      手机会有一个当前正在播放的策略，通过这个保存的策略获取到device. 而不是通过stream type
//      获取设备. 比如通话时，插入耳机，那么会使用strategy_phone获取device. 
//      如果耳机连接时没有任何声音活动， 获取到的device为0，不会切换声音通道
//      下一次有声音活动的时候，getDeviceForStrategy会从mAvailableOutputDevices中获取设备
// 2.3 获取符合strategy的设备，并设置该设备
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

            // handle output device disconnection
            case AudioSystem::DEVICE_STATE_UNAVAILABLE:
                // remove device from available output devices
                mAvailableOutputDevices &= ~device;

                // handle A2DP device disconnection
                // handle BT sco device disconnection

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

// 场景3：播放音乐
status_t AudioTrack::set( int streamType, uint32_t sampleRate, int format, 
        int channels, int frameCount, uint32_t flags, callback_t cbf, void* user, 
        int notificationFrames, const sp<IMemory>& sharedBuffer, bool threadCanCallJava, int sessionId)
    audio_io_handle_t output = AudioSystem::getOutput((AudioSystem::stream_type)streamType,
            sampleRate, format, channels, (AudioSystem::output_flags)flags);
                |
                V
// 3.1 通过stream type获取设备
// 3.2 如果是direct output, 返回一个新的output
// 3.3 如果a2dp连接上, 返回a2dp output
// 3.4 否则返回系统默认output， mHardwareOutput;
audio_io_handle_t AudioPolicyManagerBase::getOutput(AudioSystem::stream_type stream,
        uint32_t samplingRate,
        uint32_t format,
        uint32_t channels,
        AudioSystem::output_flags flags)

    routing_strategy strategy = getStrategy((AudioSystem::stream_type)stream);
    uint32_t device = getDeviceForStrategy(strategy);

    // open a direct output if required by specified parameters
    if (needsDirectOuput(stream, samplingRate, format, channels, flags, device)) {
        ...
        return output;

    // get which output is suitable for the specified stream. The actual routing change will happen
    // when startOutput() will be called
    uint32_t a2dpDevice = device & AudioSystem::DEVICE_OUT_ALL_A2DP;
    if (AudioSystem::popCount((AudioSystem::audio_devices)device) == 2) {
        // A2DP case ...

        // if playing on 2 devices among which none is A2DP, use hardware output
        output = mHardwareOutput;
    else 
        // if playing on not A2DP device, use hardware output
        output = mHardwareOutput;

    return output;

// 3.3 开始播放
// 3.4 调用startOutput, 进而调用setOutputDevice设置选中的设备
//  具体可参考 code.reading/flow.start-stop.output.cpp
status_t AudioFlinger::PlaybackThread::Track::start()
    status = AudioSystem::startOutput(thread->id(), 
        (AudioSystem::stream_type)mStreamType, mSessionId);
            |
            V
status_t AudioPolicyManagerBase::startOutput(audio_io_handle_t output, AudioSystem::stream_type stream, int session)
    routing_strategy strategy = getStrategy((AudioSystem::stream_type)stream);
    // 影响该设备的strategy, 进而影响device
    outputDesc->changeRefCount(stream, 1);
    setOutputDevice(output, getNewDevice(output));




