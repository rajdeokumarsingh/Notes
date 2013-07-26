

// 定义了内部类
class AMRSource : public MediaSource {
public:
    AMRSource(const sp<DataSource> &source,
            const sp<MetaData> &meta,
            size_t frameSize,
            bool isWide);

    virtual status_t start(MetaData *params = NULL);
    virtual status_t stop();

    virtual sp<MetaData> getFormat();

    virtual status_t read(
            MediaBuffer **buffer, const ReadOptions *options = NULL);


// 获取文件的相关信息
AMRExtractor::AMRExtractor(const sp<DataSource> &source)
    : mDataSource(source), mInitCheck(NO_INIT) 

    mIsWide = (mimeType == MEDIA_MIMETYPE_AUDIO_AMR_WB);
    mFrameSize = getFrameSize(mIsWide, FT);

// 有一条track
size_t AMRExtractor::countTracks() {
    return 1;

// track是一个MediaSource
sp<MediaSource> AMRExtractor::getTrack(size_t index) {
    return new AMRSource(mDataSource, mMeta, mFrameSize, mIsWide);

sp<MetaData> AMRExtractor::getTrackMetaData(size_t index, uint32_t flags) {
    return mMeta;

AMRSource::AMRSource(
    const sp<DataSource> &source, const sp<MetaData> &meta,
    size_t frameSize, bool isWide)
: mDataSource(source), mMeta(meta), mFrameSize(frameSize), mIsWide(isWide), 
  mOffset(mIsWide ? 9 : 6), mCurrentTimeUs(0), mStarted(false), mGroup(NULL) { }


status_t AMRSource::start(MetaData *params) {
    mOffset = mIsWide ? 9 : 6;
    mCurrentTimeUs = 0;
    mGroup = new MediaBufferGroup;
    mGroup->add_buffer(new MediaBuffer(128));
    mStarted = true;

    return OK;

status_t AMRSource::stop() 
    delete mGroup; 
    mStarted = false;

status_t AMRSource::read(
        MediaBuffer **out, const ReadOptions *options) {
    *out = NULL;

    // 从什么位置开始读
    int64_t seekTimeUs;
    if (options && options->getSeekTo(&seekTimeUs)) {
        int64_t seekFrame = seekTimeUs / 20000ll;  // 20ms per frame.
        mCurrentTimeUs = seekFrame * 20000ll;
        mOffset = seekFrame * mFrameSize + (mIsWide ? 9 : 6);


    uint8_t header;
    ssize_t n = mDataSource->readAt(mOffset, &header, 1);
    if (n < 1) {
        return ERROR_END_OF_STREAM;


    MediaBuffer *buffer;
    status_t err = mGroup->acquire_buffer(&buffer);
    n = mDataSource->readAt(mOffset, buffer->data(), frameSize);

    buffer->set_range(0, frameSize);
    buffer->meta_data()->setInt64(kKeyTime, mCurrentTimeUs);

    mOffset += frameSize;
    mCurrentTimeUs += 20000;  // Each frame is 20ms

    *out = buffer;




