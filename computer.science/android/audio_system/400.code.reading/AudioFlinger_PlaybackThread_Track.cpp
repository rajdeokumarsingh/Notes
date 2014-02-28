

// 1. 调用基类TrackBase构造函数
// 2. 设置音频相关参数
AudioFlinger::PlaybackThread::Track::Track( const wp<ThreadBase>& thread, const sp<Client>& client,
        int streamType, uint32_t sampleRate, int format, int channelCount, int frameCount,
        const sp<IMemory>& sharedBuffer, int sessionId)

:   TrackBase(thread, client, sampleRate, format, channelCount, frameCount, 0, sharedBuffer, sessionId),
    mMute(false), mSharedBuffer(sharedBuffer), mName(-1), mMainBuffer(NULL), mAuxBuffer(NULL),
    mAuxEffectId(0), mHasVolumeController(false)

    if (mCblk != NULL) {
        sp<ThreadBase> baseThread = thread.promote();
        if (baseThread != 0) {
            PlaybackThread *playbackThread = (PlaybackThread *)baseThread.get();
            mName = playbackThread->getTrackName_l();
            mMainBuffer = playbackThread->mixBuffer();

        mVolume[0] = 1.0f;
        mVolume[1] = 1.0f;
        mStreamType = streamType;
        // NOTE: audio_track_cblk_t::frameSize for 8 bit PCM data is based on a sample size of
        // 16 bit because data is converted to 16 bit before being stored in buffer by AudioTrack
        mCblk->frameSize = AudioSystem::isLinearPCM(format) ? channelCount * sizeof(int16_t) : sizeof(int8_t);

// 1. 设置状态为TERMINATED
// 2. 调用基类析构函数
AudioFlinger::PlaybackThread::Track::~Track()
    mState = TERMINATED;

// 1. release the hardware IO device
// 2. delete itself by PlaybackThread::destroyTrack_l
void AudioFlinger::PlaybackThread::Track::destroy()
    sp<ThreadBase> thread = mThread.promote();
    AudioSystem::stopOutput(thread->id(), (AudioSystem::stream_type)mStreamType, mSessionId);
    AudioSystem::releaseOutput(thread->id());

    PlaybackThread *playbackThread = (PlaybackThread *)thread.get();
    playbackThread->destroyTrack_l(this);

status_t AudioFlinger::PlaybackThread::Track::getNextBuffer(AudioBufferProvider::Buffer* buffer)
    // 需要的申请多少帧
    uint32_t framesReq = buffer->frameCount;

    // 返回音频buffer区中有多少帧已经写入的数据
    framesReady = cblk->framesReady();

    if (LIKELY(framesReady)) {

        // 读指针的开始
        uint32_t s = cblk->server;
        // cblk->frameCount是缓冲区的总大小，以frame为单位
        // bufferEnd是音频buffer的结束地址
        uint32_t bufferEnd = cblk->serverBase + cblk->frameCount;

        // 调整bufferEnd的大小，不可超出缓冲区的结束地址
        bufferEnd = (cblk->loopEnd < bufferEnd) ? cblk->loopEnd : bufferEnd;

        // 调整请求帧数的大小，不能超过已经写入的帧数
        if (framesReq > framesReady)
            framesReq = framesReady;

        // 调整请求帧数的大小
        if (s + framesReq > bufferEnd) {
            framesReq = bufferEnd - s;

        // 获取音频共享内存中已经填好的音频数据的开始地址
        buffer->raw = getBuffer(s, framesReq);
        buffer->frameCount = framesReq;


// 表示Track中buffer已经被填满
bool AudioFlinger::PlaybackThread::Track::isReady() const {
    if (mFillingUpStatus != FS_FILLING) return true;

    if (mCblk->framesReady() >= mCblk->frameCount 
            || mCblk->forceReady)
        mFillingUpStatus = FS_FILLED;
        mCblk->forceReady = 0;
        return true;

    return false;

// 1. start the hardware output by AudioSystem::startOutput()
// 2. add the track to its PlaybackThread
status_t AudioFlinger::PlaybackThread::Track::start()
    sp<ThreadBase> thread = mThread.promote();
    if (state != ACTIVE && state != RESUMING) {
        thread->mLock.unlock();
        status = AudioSystem::startOutput(thread->id(), (AudioSystem::stream_type)mStreamType, mSessionId);
        thread->mLock.lock();

    playbackThread->addTrack_l(this);

// 1. stop the hardware output by AudioSystem::stopOutput()
void AudioFlinger::PlaybackThread::Track::stop()
    Mutex::Autolock _l(thread->mLock);

    if (mState > STOPPED)
        mState = STOPPED;
        // If the track is not active (PAUSED and buffers full), flush buffers
        if (playbackThread->mActiveTracks.indexOf(this) < 0) {
            reset();

    if (!isOutputTrack() && (state == ACTIVE || state == RESUMING)) {
        thread->mLock.unlock();
        AudioSystem::stopOutput(thread->id(), (AudioSystem::stream_type)mStreamType);
        thread->mLock.lock();
    }


// 1. stop the hardware output by AudioSystem::stopOutput()
void AudioFlinger::PlaybackThread::Track::pause()
    mState = PAUSING;
    AudioSystem::stopOutput(thread->id(), (AudioSystem::stream_type)mStreamType, mSessionId); 

void AudioFlinger::PlaybackThread::Track::flush()
     mState = STOPPED;

     mCblk->lock.lock();
    // NOTE: reset() will reset cblk->user and cblk->server with
    // the risk that at the same time, the AudioMixer is trying to read
    // data. In this case, getNextBuffer() would return a NULL pointer
    // as audio buffer => the AudioMixer code MUST always test that pointer
    // returned by getNextBuffer() is not NULL!
    reset();
    mCblk->lock.unlock();

void AudioFlinger::PlaybackThread::Track::reset()
     TrackBase::reset();

void AudioFlinger::PlaybackThread::Track::mute(bool muted)
    mMute = muted;

void AudioFlinger::PlaybackThread::Track::setVolume(float left, float right)
    mVolume[0] = left;
    mVolume[1] = right;

