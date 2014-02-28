
如何播放
    如何解码
如何停止
如何seek
声音和视频如何同步

如何播放视频

DataSource
// 设置播放数据源， 如文件名， 文件句柄， 播放uir等等

MediaSource 
    // 包装了解码器

struct AwesomePlayer 

    // 状态机
    enum {
        PLAYING             = 1,
        LOOPING             = 2,
        FIRST_FRAME         = 4,
        PREPARING           = 8,
        PREPARED            = 16,
        AT_EOS              = 32,
        PREPARE_CANCELLED   = 64,


    OMXClient mClient;
    TimedEventQueue mQueue;

    // MediaPlayerBase回调
    wp<MediaPlayerBase> mListener;

    // 设置播放数据源， 如文件名， 文件句柄， 播放uir等等
    status_t setDataSource( const char *uri, const KeyedVector<String8, String8> *headers = NULL);
    status_t setDataSource(int fd, int64_t offset, int64_t length);
    sp<DataSource> mFileSource;

    sp<ISurface> mISurface;

    TimeSource *mTimeSource;

    // MediaSource 包装了解码器
    sp<MediaSource> mVideoTrack;
    sp<MediaSource> mVideoSource;

    sp<MediaSource> mAudioTrack;
    sp<MediaSource> mAudioSource;

    AudioPlayer *mAudioPlayer;


    struct SuspensionState



// 1. 连接omx
// 2. 注册文件格式检测器，如mp3, amr, ogg
// 3. 创建一些awesome events
AwesomePlayer::AwesomePlayer()
    : mQueueStarted(false), mTimeSource(NULL), mVideoRendererIsPreview(false), 
    mAudioPlayer(NULL), mFlags(0), mExtractorFlags(0), mLastVideoBuffer(NULL), 
    mVideoBuffer(NULL), mSuspensionState(NULL) {

    // 连接到omx
    mClient.connect()

    // 注册文件格式检测器，如mp3, amr, ogg
    DataSource::RegisterDefaultSniffers();

    mVideoEvent = new AwesomeEvent(this, &AwesomePlayer::onVideoEvent);
    mStreamDoneEvent = new AwesomeEvent(this, &AwesomePlayer::onStreamDone);
    mBufferingEvent = new AwesomeEvent(this, &AwesomePlayer::onBufferingUpdate);
    mCheckAudioStatusEvent = new AwesomeEvent(this, &AwesomePlayer::onCheckAudioStatus);

    reset();

AwesomePlayer::~AwesomePlayer() 
    if (mQueueStarted) mQueue.stop();

    reset();
    mClient.disconnect();

// cancel 所有awesome event
void AwesomePlayer::cancelPlayerEvents(bool keepBufferingGoing)

status_t AwesomePlayer::setDataSource(const char *uri, 
        const KeyedVector<String8, String8> *headers)


// 创建一个FileSource
status_t AwesomePlayer::setDataSource(
        int fd, int64_t offset, int64_t length)

    sp<DataSource> dataSource = new FileSource(fd, offset, length);
    status_t err = dataSource->initCheck();

    mFileSource = dataSource;
    return setDataSource_l(dataSource);

// 将DataSource封装到一个MediaExtractor中
status_t AwesomePlayer::setDataSource_l(const sp<DataSource> &dataSource) {
    sp<MediaExtractor> extractor = MediaExtractor::Create(dataSource);
    return setDataSource_l(extractor);


status_t AwesomePlayer::setDataSource_l(const sp<MediaExtractor> &extractor) {
    for (size_t i = 0; i < extractor->countTracks(); ++i) {
        sp<MetaData> meta = extractor->getTrackMetaData(i);

            // 从track的meta中获取mime, 如果mime包含vedio/, 则 设置video source
            setVideoSource(extractor->getTrack(i));

            // 从track的meta中获取mime, 如果mime包含audio/, 则 设置audio source
            setAudioSource(extractor->getTrack(i));

            // FIXME: 每个媒体只有不超过一条的audio track和不超过一条的vedio track
            // 每条track有一个metadata

    mExtractorFlags = extractor->flags();

// clear internal audio tracks, source,
void AwesomePlayer::reset_l() 
    cancelPlayerEvents();
    mPrefetcher.clear();
    mAudioTrack.clear();
    mVideoTrack.clear();

    mAudioSource.clear();

    delete mAudioPlayer;
    mAudioPlayer = NULL;

    mLastVideoBuffer->release();
    mLastVideoBuffer = NULL;

    mVideoBuffer->release();
    mVideoBuffer = NULL;

    mVideoSource->stop();
    mUriHeaders.clear();
    mFileSource.clear();

void AwesomePlayer::onBufferingUpdate() {

status_t AwesomePlayer::play() {
    return play_l();

status_t AwesomePlayer::play_l() 
    status_t err = prepare_l();

    mFlags |= PLAYING;
    mFlags |= FIRST_FRAME;

    mAudioPlayer = new AudioPlayer(mAudioSink);
    mAudioPlayer->setSource(mAudioSource);

    // We've already started the MediaSource in order to enable
    // the prefetcher to read its data.
    status_t err = mAudioPlayer->start(
            true /* sourceAlreadyStarted */);

    delete mTimeSource;
    mTimeSource = mAudioPlayer;

    deferredAudioSeek = true;
    mWatchForAudioSeekComplete = false;
    mWatchForAudioEOS = true;

    if (mTimeSource == NULL && mAudioPlayer == NULL) {
        mTimeSource = new SystemTimeSource;



