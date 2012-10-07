

// allocate and free shared memory for the control block, audio_track_cblk_t


// 1. allocate and initialize shared memory for the control block, audio_track_cblk_t
AudioFlinger::ThreadBase::TrackBase::TrackBase(
        const wp<ThreadBase>& thread, const sp<Client>& client, uint32_t sampleRate, int format, int channelCount,
        int frameCount, uint32_t flags, const sp<IMemory>& sharedBuffer, int sessionId)
:   RefBase(), mThread(thread), mClient(client), mCblk(0), mFrameCount(0), mState(IDLE),
    mClientTid(-1), mFormat(format), mFlags(flags & ~SYSTEM_FLAGS_MASK), mSessionId(sessionId)

    size_t size = sizeof(audio_track_cblk_t);
    size_t bufferSize = frameCount*channelCount*sizeof(int16_t);
    if (sharedBuffer == 0) {
        size += bufferSize;

    if (client != NULL) {
        mCblkMemory = client->heap()->allocate(size);
        if (mCblkMemory != 0) {
            mCblk = static_cast<audio_track_cblk_t *>(mCblkMemory->pointer());
            if (mCblk) { // construct the shared structure in-place.
                new(mCblk) audio_track_cblk_t();
                // clear all buffers
                mCblk->frameCount = frameCount;
                mCblk->sampleRate = sampleRate;
                mCblk->channelCount = (uint8_t)channelCount;
                if (sharedBuffer == 0) {
                    mBuffer = (char*)mCblk + sizeof(audio_track_cblk_t);
                    memset(mBuffer, 0, frameCount*channelCount*sizeof(int16_t));
                    // Force underrun condition to avoid false underrun callback until first data is
                    // written to buffer (other flags are cleared)
                    mCblk->flags = CBLK_UNDERRUN_ON;
                } else {
                    mBuffer = sharedBuffer->pointer();
                }
                mBufferEnd = (uint8_t *)mBuffer + bufferSize;
    else {
        mCblk = (audio_track_cblk_t *)(new uint8_t[size]);
        if (mCblk) { // construct the shared structure in-place.
            new(mCblk) audio_track_cblk_t();
            // clear all buffers
            mCblk->frameCount = frameCount;
            mCblk->sampleRate = sampleRate;
            mCblk->channelCount = (uint8_t)channelCount;
            mBuffer = (char*)mCblk + sizeof(audio_track_cblk_t);
            memset(mBuffer, 0, frameCount*channelCount*sizeof(int16_t));
            // Force underrun condition to avoid false underrun callback until first data is
            // written to buffer (other flags are cleared)
            mCblk->flags = CBLK_UNDERRUN_ON;
            mBufferEnd = (uint8_t *)mBuffer + bufferSize;

// 1. destroy our shared-structure.
// 2. free the shared memory
// 3. clear the mClient
AudioFlinger::ThreadBase::TrackBase::~TrackBase()
    if (mCblk) {
        mCblk->~audio_track_cblk_t();   // destroy our shared-structure.
        if (mClient == NULL) {
            delete mCblk;
    mCblkMemory.clear();            // and free the shared memory
    if (mClient != NULL) {
        mClient.clear();

// 1. clear buffer
// 2. invoking step
void AudioFlinger::ThreadBase::TrackBase::releaseBuffer(AudioBufferProvider::Buffer* buffer)
    buffer->raw = 0;
    mFrameCount = buffer->frameCount;
    step();
    buffer->frameCount = 0;

// invoke audio_track_cblk_t::stepServer
bool AudioFlinger::ThreadBase::TrackBase::step() {
    audio_track_cblk_t* cblk = this->cblk();
    result = cblk->stepServer(mFrameCount);

// 1. get start of the control block buffer 
void* AudioFlinger::ThreadBase::TrackBase::getBuffer(uint32_t offset, uint32_t frames) const {
    audio_track_cblk_t* cblk = this->cblk();
    int8_t *bufferStart = (int8_t *)mBuffer + (offset-cblk->serverBase)*cblk->frameSize;


