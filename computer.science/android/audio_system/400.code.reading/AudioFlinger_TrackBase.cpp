// base class for Track and RecordTrack
// 是一个AudioBufferProvider, 提供声音的IPC共享内存
// 提供track控制接口，start, stop, step, reset
// 提供声音配置的参数
// FIXME: what is a client? 
class TrackBase : public AudioBufferProvider, public RefBase


// 1. 计算共享内存的大小
    // 一般情况， 共享内存分成：音频控制块+音频buffer
// 2. 分配共享内存
// 3. 在共享内存上构造音频控制块
// 4. 设置音频参数
// 5. 清空共享内存的音频buffer区
AudioFlinger::ThreadBase::TrackBase::TrackBase(
        const wp<ThreadBase>& thread,  
        const sp<Client>& client,      
        uint32_t sampleRate,           
        int format,
        int channelCount,
        int frameCount,
        uint32_t flags,
        const sp<IMemory>& sharedBuffer)
:   RefBase(),
    mThread(thread),
    mClient(client),
    mCblk(0),
    mFrameCount(0),
    mState(IDLE),
    mClientTid(-1),
    mFormat(format),
    mFlags(flags & ~SYSTEM_FLAGS_MASK)  


    // 计算共享内存的大小
    // 一般情况， 共享内存分成：音频控制块+音频buffer
    size_t size = sizeof(audio_track_cblk_t);
    size_t bufferSize = frameCount*channelCount*sizeof(int16_t);
    if (sharedBuffer == 0)
        size += bufferSize;

    if (client != NULL) 
        // 分配共享内存
        mCblkMemory = client->heap()->allocate(size);

        // 在共享内存上创建控制块结构
        mCblk = static_cast<audio_track_cblk_t *>(mCblkMemory->pointer());
        new(mCblk) audio_track_cblk_t();

        // 设置音频参数
        mCblk->frameCount = frameCount;
        mCblk->sampleRate = sampleRate;
        mCblk->channels = (uint8_t)channelCount;

        // 清空共享内存的音频buffer区
        if (sharedBuffer == 0) {
            mBuffer = (char*)mCblk + sizeof(audio_track_cblk_t);
            memset(mBuffer, 0, frameCount*channelCount*sizeof(int16_t));
            mCblk->flowControlFlag = 1;
        } else {
            mBuffer = sharedBuffer->pointer();
        }
        mBufferEnd = (uint8_t *)mBuffer + bufferSize;


// 1. 析构音频控制块
// 2. 释放共享内存
AudioFlinger::ThreadBase::TrackBase::~TrackBase()
    // 析构音频控制块
    if (mCblk)
        mCblk->~audio_track_cblk_t();   // destroy our shared-structure.
        if (mClient == NULL)
            delete mCblk;

    // 释放共享内存
    mCblkMemory.clear();            // and free the shared memory
    if (mClient != NULL)
        Mutex::Autolock _l(mClient->audioFlinger()->mLock);
        mClient.clear();


// 没干啥事
void AudioFlinger::ThreadBase::TrackBase::releaseBuffer(
        AudioBufferProvider::Buffer* buffer)
    buffer->raw = 0;
    mFrameCount = buffer->frameCount;
    step();
    buffer->frameCount = 0;

// 将读指针向前移动mFrameCount帧
bool AudioFlinger::ThreadBase::TrackBase::step() {
    audio_track_cblk_t* cblk = this->cblk();
    result = cblk->stepServer(mFrameCount);
    return result;

// 清空音频控制块结构
void AudioFlinger::ThreadBase::TrackBase::reset() {
    audio_track_cblk_t* cblk = this->cblk();

    cblk->user = 0;
    cblk->server = 0;
    cblk->userBase = 0;
    cblk->serverBase = 0;
    mFlags &= (uint32_t)(~SYSTEM_FLAGS_MASK);


void* AudioFlinger::ThreadBase::TrackBase::getBuffer(
        uint32_t offset, uint32_t frames) const {

    // 计算buffer在音频共享内存区中的开始和结束地址
    // FIXME: 目前可假设serverBase为0
    audio_track_cblk_t* cblk = this->cblk();
    int8_t *bufferStart = (int8_t *)mBuffer + 
        (offset-cblk->serverBase)*cblk->frameSize;
    int8_t *bufferEnd = bufferStart + 
        frames * cblk->frameSize;

    return bufferStart;








