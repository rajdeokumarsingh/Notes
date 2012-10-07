
// 1. 所有extractor的基类
// 2. 提供工厂类方法， 创建 AMRExtractor, MP3Extractor, MPEG4Extractor, OggExtractor
// 3. 提供接口， 获取媒体的track, 及track count
// 4. 提供接口， 获取媒体的meta data
// 对一个媒体文件， 播放器看到的是MetaData和几个MediaSource

class MediaExtractor : public RefBase {
public:

    static sp<MediaExtractor> Create(
            const sp<DataSource> &source, const char *mime = NULL);

    virtual size_t countTracks() = 0;
    virtual sp<MediaSource> getTrack(size_t index) = 0;

    virtual sp<MetaData> getTrackMetaData(
            size_t index, uint32_t flags = 0) = 0;
    // Return container specific meta-data. The default implementation
    // returns an empty metadata object.
    virtual sp<MetaData> getMetaData();


    enum GetTrackMetaDataFlags {
        kIncludeExtensiveMetaData = 1
    };
    enum Flags {
        CAN_SEEK_BACKWARD  = 1,
        CAN_SEEK_FORWARD   = 2,
        CAN_PAUSE          = 4,
    };

    // If subclasses do _not_ override this, the default is
    // CAN_SEEK_BACKWARD | CAN_SEEK_FORWARD | CAN_PAUSE
    virtual uint32_t flags() const;

sp<MediaExtractor> MediaExtractor::Create(
        const sp<DataSource> &source, const char *mime) {

    if (!strcasecmp(mime, MEDIA_MIMETYPE_CONTAINER_MPEG4)
            || !strcasecmp(mime, "audio/mp4")) {
        return new MPEG4Extractor(source);
    } else if (!strcasecmp(mime, MEDIA_MIMETYPE_AUDIO_MPEG)) {
        return new MP3Extractor(source);
    } else if (!strcasecmp(mime, MEDIA_MIMETYPE_AUDIO_AMR_NB)
            || !strcasecmp(mime, MEDIA_MIMETYPE_AUDIO_AMR_WB)) {
        return new AMRExtractor(source);
    } else if (!strcasecmp(mime, MEDIA_MIMETYPE_CONTAINER_WAV)) {
        return new WAVExtractor(source);
    } else if (!strcasecmp(mime, MEDIA_MIMETYPE_CONTAINER_OGG)) {
        return new OggExtractor(source);
    }


