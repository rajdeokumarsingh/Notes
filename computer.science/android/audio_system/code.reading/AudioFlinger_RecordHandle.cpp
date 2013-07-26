/* class
    1. RecordHandle
        provide binder native IPC interface for the client AudioRecord
            start(): start recording
            stop(): stop recording
            getCblk() : get IPC memory

        wraps RecordThread::RecordTrack

    2. RecordThread
        provide a thread for recording
        bind to a hardware input device
        read raw data from the input device
        write the raw data to the RecordTrack

    3. RecordThread::RecordTrack
        wraps the IPC buffer 
*/

////////////////////////////////////////////////////////////////////////////////
// IPC interface for record
class IAudioRecord : public IInterface
    /* After it's created the track is not active. Call start() to
     * make it active. If set, the callback will start being called.
     */
    virtual status_t    start() = 0;

    /* Stop a track. If set, the callback will cease being called and
     * obtainBuffer will return an error. Buffers that are already released 
     * will be processed, unless flush() is called.
     */
    virtual void        stop() = 0;

    /* get this tracks control block */
    virtual sp<IMemory> getCblk() const = 0;


////////////////////////////////////////////////////////////////////////////////
// RecordHandle
////////////////////////////////////////////////////////////////////////////////
/*
1. implement server side function
2. wraps a RecordThread::RecordTrack
*/
class RecordHandle : public android::BnAudioRecord 
    virtual status_t    start();
    virtual void        stop();
    virtual sp<IMemory> getCblk() const;

    sp<RecordThread::RecordTrack> mRecordTrack;

/*
1. create a RecordThread::RecordTrack
2. create a RecordHandle which wraps the RecordThread::RecordTrack
3. return the RecordHandle to remote client
*/
sp<IAudioRecord> AudioFlinger::openRecord(
        pid_t pid, int input, uint32_t sampleRate, int format, 
        int channelCount, int frameCount, uint32_t flags, status_t *status)

    // get the RecordThread which is created by AudioFlinger::openInput()
    thread = checkRecordThread_l(input);

    // create new record track. The record track uses one track in mHardwareMixerThread by convention.
    recordTrack = new RecordThread::RecordTrack(thread, client, sampleRate,
            format, channelCount, frameCount, flags);

    // return to handle to client
    recordHandle = new RecordHandle(recordTrack);

status_t AudioFlinger::RecordHandle::start() {
    return mRecordTrack->start();

void AudioFlinger::RecordHandle::stop() {
    mRecordTrack->stop();

////////////////////////////////////////////////////////////////////////////////
// RecordTrack
////////////////////////////////////////////////////////////////////////////////
class RecordTrack : public TrackBase : public AudioBufferProvider, public RefBase 
    RecordTrack(const wp<ThreadBase>& thread, const sp<Client>& client, uint32_t sampleRate,
         int format, int channelCount, int frameCount, uint32_t flags);

    virtual status_t    start();
    virtual void        stop();

    bool        overflow() { bool tmp = mOverflow; mOverflow = false; return tmp; }
    bool        setOverflow() { bool tmp = mOverflow; mOverflow = true; return tmp; }

    virtual status_t getNextBuffer(AudioBufferProvider::Buffer* buffer);

    bool                mOverflow;

// 1. allocate mCblk in its base class constructor
// 2. set frame size of the mCblk
AudioFlinger::RecordThread::RecordTrack::RecordTrack(
    const wp<ThreadBase>& thread, const sp<Client>& client, 
    uint32_t sampleRate, int format, int channelCount, int frameCount, 
    uint32_t flags) :   
    TrackBase(thread, client, sampleRate, format, channelCount, frameCount, flags, 0), mOverflow(false)
    // mCblk is created in the constructor of TrackBase 
    if (mCblk != NULL) {
        if (format == AudioSystem::PCM_16_BIT) {
            mCblk->frameSize = channelCount * sizeof(int16_t);
        } else if (format == AudioSystem::PCM_8_BIT) {
            mCblk->frameSize = channelCount * sizeof(int8_t);
        } else {
            mCblk->frameSize = sizeof(int8_t);

// 1. releaseInput
AudioFlinger::RecordThread::RecordTrack::~RecordTrack()
    sp<ThreadBase> thread = mThread.promote();
    if (thread != 0) {
        AudioSystem::releaseInput(thread->id());

// pending!
status_t AudioFlinger::RecordThread::RecordTrack::getNextBuffer(AudioBufferProvider::Buffer* buffer)

status_t AudioFlinger::RecordThread::RecordTrack::start()
    sp<ThreadBase> thread = mThread.promote();
    if (thread != 0) {
        RecordThread *recordThread = (RecordThread *)thread.get();
        return recordThread->start(this);

void AudioFlinger::RecordThread::RecordTrack::stop()
    sp<ThreadBase> thread = mThread.promote();
    if (thread != 0) {
        RecordThread *recordThread = (RecordThread *)thread.get();
        recordThread->stop(this);
        TrackBase::reset();
        // Force overerrun condition to avoid false overrun callback until first data is
        // read from buffer
        mCblk->flowControlFlag = 1;

////////////////////////////////////////////////////////////////////////////////
// RecordThread
// 1. including a AudioStreamIn from which to read the audio data
// 2. Wraps a RecordTrack which contains an IPC control block 
// 3. AudioResampler
////////////////////////////////////////////////////////////////////////////////
class RecordThread : public ThreadBase, public AudioBufferProvider

    class RecordTrack : public TrackBase

    RecordThread(const sp<AudioFlinger>& audioFlinger, AudioStreamIn *input, 
            uint32_t sampleRate, uint32_t channels, int id);

    status_t    start(RecordTrack* recordTrack);
    void        stop(RecordTrack* recordTrack);
    AudioStreamIn* getInput() { return mInput; }

    virtual status_t    getNextBuffer(AudioBufferProvider::Buffer* buffer);
    virtual void        releaseBuffer(AudioBufferProvider::Buffer* buffer);
    virtual bool        checkForNewParameters_l();
    virtual String8     getParameters(const String8& keys);
    virtual void        audioConfigChanged(int event, int param = 0);
    void        readInputParameters();
    virtual unsigned int  getInputFramesLost();

    AudioStreamIn*      mInput;
    sp<RecordTrack>     mActiveTrack;   // only one track in a RecordThread
    Condition           mStartStopCond;
    AudioResampler*     mResampler;
    int32_t *           mRsmpOutBuffer;
    int16_t *           mRsmpInBuffer;
    size_t              mRsmpInIndex;
    size_t              mInputBytes;
    int                 mReqChannelCount;
    uint32_t            mReqSampleRate;
    ssize_t             mBytesRead;

// creating of the RecordThread
status_t AudioRecord::set( int inputSource, uint32_t sampleRate, int format, uint32_t channels, int frameCount, 
        uint32_t flags, callback_t cbf, void* user, int notificationFrames, bool threadCanCallJava)

    audio_io_handle_t input = AudioSystem::getInput(inputSource,
            sampleRate, format, channels, (AudioSystem::audio_in_acoustics)flags);
            |
            | IPC...
            V
audio_io_handle_t AudioPolicyManagerBase::getInput(int inputSource, 
    uint32_t samplingRate, uint32_t format, uint32_t channels,
    AudioSystem::audio_in_acoustics acoustics)

    // convert the source to hardware device type
    uint32_t device = getDeviceForInputSource(inputSource);

    AudioInputDescriptor *inputDesc = new AudioInputDescriptor();
    inputDesc->mInputSource = inputSource;
    inputDesc->mDevice = device;
    inputDesc->mSamplingRate = samplingRate;
    inputDesc->mFormat = format;
    inputDesc->mChannels = channels;
    inputDesc->mAcoustics = acoustics;
    inputDesc->mRefCount = 0;

    input = mpClientInterface->openInput(&inputDesc->mDevice,
            &inputDesc->mSamplingRate,
            &inputDesc->mFormat,
            &inputDesc->mChannels,
            inputDesc->mAcoustics);

    mInputs.add(input, inputDesc);
    return input;
            |
            V
int AudioFlinger::openInput(uint32_t *pDevices, uint32_t *pSamplingRate,
        uint32_t *pFormat, uint32_t *pChannels, uint32_t acoustics)

    AudioStreamIn *input = mAudioHardware->openInputStream(*pDevices,
            (int *)&format,
            &channels,
            &samplingRate,
            &status,
            (AudioSystem::audio_in_acoustics)acoustics);

    if (input != 0)
        // Start record thread
        thread = new RecordThread(this, input, reqSamplingRate, reqChannels, ++mNextThreadId);
        mRecordThreads.add(mNextThreadId, thread);
        input->standby();
        return mNextThreadId;

AudioFlinger::RecordThread::RecordThread(const sp<AudioFlinger>& audioFlinger, 
    AudioStreamIn *input, uint32_t sampleRate, uint32_t channels, int id) :       
        ThreadBase(audioFlinger, id), mInput(input), 
        mResampler(0), mRsmpOutBuffer(0), mRsmpInBuffer(0)

    mReqChannelCount = AudioSystem::popCount(channels);
    mReqSampleRate = sampleRate;
    readInputParameters(); 
    sendConfigEvent(AudioSystem::INPUT_OPENED);


AudioFlinger::RecordThread::~RecordThread()
    delete[] mRsmpInBuffer;
    if (mResampler != 0) {
        delete mResampler;
        delete[] mRsmpOutBuffer;

void AudioFlinger::RecordThread::onFirstRef()
    run(buffer, PRIORITY_URGENT_AUDIO);

// 1. read input data from hardware output
// 2. write the data to IPC buffer
bool AudioFlinger::RecordThread::threadLoop()
    // start recording
    while (!exitPending())
        processConfigEvents();

        checkForNewParameters_l();
        if (mActiveTrack == 0 && mConfigEvents.isEmpty()) {
            if (!mStandby) {
                mInput->standby();
                mStandby = true;

                if (exitPending()) break;

                // go to sleep
                mWaitWorkCV.wait(mLock);
                continue;
            }

        // have active tracks
        if (mActiveTrack != 0) {
            // read the data from hardware 
            mBytesRead = mInput->read(buffer.raw, mInputBytes);

            // get ipc buffer and write to it
            if (LIKELY(mActiveTrack->getNextBuffer(&buffer) == NO_ERROR)) {
                // copy audio data to the ipc buffer
                int8_t *src = (int8_t *)mRsmpInBuffer + mRsmpInIndex * mFrameSize;
                int8_t *dst = buffer.i8 + (buffer.frameCount - framesOut) * mActiveTrack->mCblk->frameSize;
                memcpy(dst, src, framesIn * mFrameSize);

            if (mBytesRead < 0)
                mInput->standby();

    // end of while (!exitPending())
    if (!mStandby)
        mInput->standby();
    mActiveTrack.clear();
    mStartStopCond.broadcast();


// 1. start the hardware output
// 2. signal thread to start
status_t AudioFlinger::RecordThread::start(RecordThread::RecordTrack* recordTrack)
    recordTrack->mState = TrackBase::IDLE;
    mActiveTrack = recordTrack;

    // start the hardware input by setParameters(keyRouting, input_device)
    status_t status = AudioSystem::startInput(mId);

    // signal thread to start
    mWaitWorkCV.signal();

    mStartStopCond.wait(mLock);

void AudioFlinger::RecordThread::stop(RecordThread::RecordTrack* recordTrack) 
    if (mActiveTrack != 0 && recordTrack == mActiveTrack.get()) 
        mActiveTrack->mState = TrackBase::PAUSING;

        // do not wait for mStartStopCond if exiting
        if (mExiting) {
            return;
        mStartStopCond.wait(mLock);

        // if we have been restarted, recordTrack == mActiveTrack.get() here
        if (mActiveTrack == 0 || recordTrack != mActiveTrack.get()) {
            // stop the hardware input by setParameters(keyRouting, input_device)
            AudioSystem::stopInput(mId);

// 1. read hareware data and send it to ipc buffer
// 2. use for resample
status_t AudioFlinger::RecordThread::getNextBuffer(AudioBufferProvider::Buffer* buffer)
    if (framesReady == 0) {
        mBytesRead = mInput->read(mRsmpInBuffer, mInputBytes);

// 1. read all parameters from the list mNewParameters 
// 2. set the parameters to the input hardware
// 3. if reconfig happens, readInputParameters();
bool AudioFlinger::RecordThread::checkForNewParameters_l()

String8 AudioFlinger::RecordThread::getParameters(const String8& keys)
    return mInput->getParameters(keys);

void AudioFlinger::RecordThread::audioConfigChanged(int event, int param) {
    AudioSystem::OutputDescriptor desc;

    switch (event) 
        case AudioSystem::INPUT_OPENED:
        case AudioSystem::INPUT_CONFIG_CHANGED:
            desc.channels = mChannelCount;
            desc.samplingRate = mSampleRate;
            desc.format = mFormat;
            desc.frameCount = mFrameCount;
            desc.latency = 0;
            param2 = &desc;
            break;

        case AudioSystem::INPUT_CLOSED:
        default:
            break;

    mAudioFlinger->audioConfigChanged_l(event, mId, param2);


void AudioFlinger::RecordThread::readInputParameters()
    if (mRsmpInBuffer) delete mRsmpInBuffer;
    if (mRsmpOutBuffer) delete mRsmpOutBuffer;
    if (mResampler) delete mResampler;

    mResampler = AudioResampler::create(16, channelCount, mReqSampleRate);
    mResampler->setSampleRate(mSampleRate);
    mResampler->setVolume(AudioMixer::UNITY_GAIN, AudioMixer::UNITY_GAIN);

