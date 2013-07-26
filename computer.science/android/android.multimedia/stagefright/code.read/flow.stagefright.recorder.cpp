
问题：
    MediaSource是什么
    AudioSource是什么
    MetaData

StagefrightRecorder--封装->AMRWriter--封装-->
    MediaSource(audioEncoder)--封装-->AudioSource(MediaSource)--封装-->AudioRecord

================================================================================
base/media/libmediaplayerservice/StagefrightRecorder.cpp
    包括
        mAudioSource
            音频输入设备
            enum audio_source {
                AUDIO_SOURCE_DEFAULT = 0,
                AUDIO_SOURCE_MIC = 1,
                AUDIO_SOURCE_VOICE_UPLINK = 2, 
                ...

        mVideoSource
            视频输入设备
            enum video_source {
                VIDEO_SOURCE_DEFAULT = 0
                VIDEO_SOURCE_CAMERA = 1
                ...

        mOutputFd 
            录音/录像输出文件句柄

        mOutputFormat
            输出文件格式
            enum output_format {
                OUTPUT_FORMAT_DEFAULT = 0,
                OUTPUT_FORMAT_THREE_GPP = 1,                                                        
                OUTPUT_FORMAT_MPEG_4 = 2,                                                           

                ...
                /* These are audio only file formats */                                             
                OUTPUT_FORMAT_RAW_AMR = 3, //to be backward compatible                              
                OUTPUT_FORMAT_AMR_NB = 3,
                OUTPUT_FORMAT_AMR_WB = 4,                                                           
                OUTPUT_FORMAT_AAC_ADIF = 5,                                                         
                OUTPUT_FORMAT_AAC_ADTS = 6,     

        mAudioEncoder
            音频编码器
            enum audio_encoder
                AUDIO_ENCODER_DEFAULT = 0
                AUDIO_ENCODER_AMR_NB = 1
                AUDIO_ENCODER_AMR_WB = 2
                AUDIO_ENCODER_AAC = 3
                AUDIO_ENCODER_AAC_PLUS = 4
                AUDIO_ENCODER_EAAC_PLUS = 5

        mVideoEncoder
            视频编码器
            enum video_encoder
                VIDEO_ENCODER_DEFAULT = 0,
                VIDEO_ENCODER_H263 = 1,
                VIDEO_ENCODER_H264 = 2, 
                VIDEO_ENCODER_MPEG_4_SP = 3,

        mVideoWidth
        mVideoHeight
        mFrameRate // frame per second

        mCamera:ICamera
        mPreviewSurface:ISurface

// 关键函数和调用流程
status_t StagefrightRecorder::start()
    // 录音
    startAMRRecording();

    // 录像
    startMPEG4Recording();


// 1. 创建编码器 MediaSource
// 2. 创建文件writer, AMRWriter
// 3. 将编码器关联到writer
// 4. Writer开始
status_t StagefrightRecorder::startAMRRecording() 
    sp<MediaSource> audioEncoder = createAMRAudioSource();

    mWriter = new AMRWriter(dup(mOutputFd));
    mWriter->addSource(audioEncoder);
    mWriter->start(NULL);  /** TODO: meta data! where?? */ 


// 1. 创建编码器 MediaSource
// 1.1 目前仅仅支持amr_nb, amr_wb两种编码, 采样率分别是8K和16k
// 1.2 创建AudioSource
// 1.3 创建MetaData, 写入mime, maxInputSize, channel count, sample rate
// 1.4 创建编码器
sp<MediaSource> StagefrightRecorder::createAMRAudioSource() {
    uint32_t sampleRate =
            mAudioEncoder == AUDIO_ENCODER_AMR_NB ? 8000 : 16000;

    sp<AudioSource> audioSource = new AudioSource(
        mAudioSource, sampleRate, AudioSystem::CHANNEL_IN_MONO);

        status_t err = audioSource->initCheck();
        sp<MetaData> encMeta = new MetaData;
        ...

        OMXClient client;     
        CHECK_EQ(client.connect(), OK);

        sp<MediaSource> audioEncoder =
        OMXCodec::Create(client.interface(), encMeta,
                true /* createEncoder */, audioSource);

        return audioEncoder;  

// 1. writer stop()
status_t StagefrightRecorder::stop() {
    mWriter->stop();
================================================================================
AudioSource
    封装了AudioRecord

    // 启动AudioRecord
    status_t AudioSource::start(MetaData *params) {
        status_t err = mRecord->start();
        mGroup = new MediaBufferGroup;
        mGroup->add_buffer(new MediaBuffer(kMaxBufferSize));

    // 停止AudioRecord
    status_t AudioSource::stop() {
        mRecord->stop();
        delete mGroup;

    // 获取MetaData
    sp<MetaData> AudioSource::getFormat() {
        sp<MetaData> meta = new MetaData;
        meta->setCString(kKeyMIMEType, MEDIA_MIMETYPE_AUDIO_RAW);
        meta->setInt32(kKeySampleRate, mRecord->getSampleRate());
        meta->setInt32(kKeyChannelCount, mRecord->channelCount());
        meta->setInt32(kKeyMaxInputSize, kMaxBufferSize);
        return meta;

    // 获取buffer
    // 设置kKeyTime, 及当前录制了多少微秒
    // 从AudioRecord读数据
    status_t AudioSource::read(
            MediaBuffer **out, const ReadOptions *options) {

        MediaBuffer *buffer;
        CHECK_EQ(mGroup->acquire_buffer(&buffer), OK);

        uint32_t numFramesRecorded;
        mRecord->getPosition(&numFramesRecorded);

        // FIXME: 在这儿可以写duration
        buffer->meta_data()->setInt64(
                kKeyTime,
                (1000000ll * numFramesRecorded) / mRecord->getSampleRate()
                - mRecord->latency() * 1000);

        ssize_t n = mRecord->read(buffer->data(), buffer->size());
        buffer->set_range(0, n);
        *out = buffer;

================================================================================
AMRWriter
    包括
        mSource:MediaSource

// 从MediaSource获取meta data
// 检查meta data， 确定文件格式是amr-nb, amr-wb
// 写入将格式信息写入录音文件
status_t AMRWriter::addSource(const sp<MediaSource> &source)
    sp<MetaData> meta = source->getFormat();
    // check channel, sample rate from meta ...
    const char *kHeader = isWide ? "#!AMR-WB\n" : "#!AMR\n";
    fwrite(kHeader, 1, n, mFile)

// 启动MediaSource
// 启动工作线程， 开始从source中读数据
status_t AMRWriter::start(MetaData *params) {                                           
    status_t err = mSource->start(params);                                              

    pthread_attr_t attr;
    pthread_attr_init(&attr);
    pthread_attr_setdetachstate(&attr, PTHREAD_CREATE_JOINABLE);

    mReachedEOS = false;
    mDone = false;

    pthread_create(&mThread, &attr, ThreadWrapper, this);
    pthread_attr_destroy(&attr);

    mStarted = true;

    return OK;

// 停止工作线程
// 停止MediaSource
void AMRWriter::stop()
    void *dummy;
    pthread_join(mThread, &dummy);
    mSource->stop();

// 工作线程, 从source读数据，写到录音文件中
void AMRWriter::threadFunc() {
    for (;;) 
        MediaBuffer *buffer;
        status_t err = mSource->read(&buffer);

        ssize_t n = fwrite(
            (const uint8_t *)buffer->data() + buffer->range_offset(),
            1, buffer->range_length(), mFile);


