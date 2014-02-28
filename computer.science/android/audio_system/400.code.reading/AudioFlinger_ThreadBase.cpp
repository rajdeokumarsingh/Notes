/* ThreadBase
    1. 提供线程之间同步机制
    2. 提供mNewParameters数组缓存parameters
    3. 提供mConfigEvents保存configEvent
    4. 提供处理configEvent的函数
        ConfigEvent是channel, sample rate, format, latency的变化
        AudioFlinger会将这些变化通知到所有注册的客户端
*/
AudioFlinger::ThreadBase::ThreadBase(const sp<AudioFlinger>& audioFlinger, int id)
    :   Thread(false),
    mAudioFlinger(audioFlinger), mSampleRate(0), mFrameCount(0), mChannelCount(0),
    mFrameSize(1), mFormat(0), mStandby(false), mId(id), mExiting(false) 

AudioFlinger::ThreadBase::~ThreadBase()
    mParamCond.broadcast();
    mNewParameters.clear();

void AudioFlinger::ThreadBase::exit()
    // keep a strong ref on ourself so that we wont get
    // destroyed in the middle of requestExitAndWait()
    sp <ThreadBase> strongMe = this;
    {
        AutoMutex lock(&mLock);
        mExiting = true;
        requestExit();
        mWaitWorkCV.signal();
    }
    requestExitAndWait();

// 1. add the parameters to mNewParameters
// 2. wake up woking thread to handle the parameters
// 3. wait working thread's result
status_t AudioFlinger::ThreadBase::setParameters(const String8& keyValuePairs)
    mNewParameters.add(keyValuePairs);
    mWaitWorkCV.signal();

    // wait condition with timeout in case the thread loop has exited
    // before the request could be processed
    if (mParamCond.waitRelative(mLock, seconds(2)) == NO_ERROR) {
        status = mParamStatus;
        mWaitWorkCV.signal();
    } else {
        status = TIMED_OUT;
    }

// 1. create a config event
// 2. add the event to mConfigEvents
// 3. wake up the working thread to handle the event
void AudioFlinger::ThreadBase::sendConfigEvent_l(int event, int param)
    ConfigEvent *configEvent = new ConfigEvent();
    configEvent->mEvent = event;
    configEvent->mParam = param;
    mConfigEvents.add(configEvent);
    mWaitWorkCV.signal();

// 1. handle each event in mConfigEvents by invoking audioConfigChanged_l()
// 2. delete the handled event
void AudioFlinger::ThreadBase::processConfigEvents()
    while(!mConfigEvents.isEmpty())
        ConfigEvent *configEvent = mConfigEvents[0];
        mConfigEvents.removeAt(0);
        audioConfigChanged_l(configEvent->mEvent, configEvent->mParam);
        delete configEvent;

