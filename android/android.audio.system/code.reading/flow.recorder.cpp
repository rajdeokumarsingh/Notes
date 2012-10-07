//////////////////////////////////////////////////////////////////////////////// 
/* 录制声音的基本步骤
    获取原始音频数据（PCM）
    对原始数据的编码
    将编码后的数据写入文件

    录音相关的线程结构
        media server
            主线程
                下发命令 start, stop

            Stagefright::AMRWriter线程(消费者)
                从共享内存读数据, 编码，写文件

            AudioFlinger::RecordThread线程(生产者)
                从硬件读数据， 写共享内存
*/
//////////////////////////////////////////////////////////////////////////////// 
/*
basic call flow:

Application
        |
        V
base/media/java/android/media/MediaRecorder.java
MediaRecorder
        |
        | JNI
        V 
base/media/jni/android_media_MediaRecorder.cpp
        |
        V
base/media/libmedia/mediarecorder.cpp
MediaRecorder 
        |
        V
base/include/media/IMediaRecorder.h
IMediaRecorder
            |           application process
            | IPC ----------------------------------------
            |           media player service process
            V
base/media/libmediaplayerservice/MediaRecorderClient.cpp
MediaRecorderClient: public BnMediaRecorder
        |
        V
base/media/libmediaplayerservice/StagefrightRecorder.cpp
StagefrightRecorder/PVMediaRecorder/GStreamer recorder
        |
        V
base/media/libmedia/AudioRecord.cpp
AudioRecord
        |
        v
base/include/media/IAudioRecord.h
IAudioRecord
            |           media player service process
            | IPC ----------------------------------------
            |           audio flinger process
            V
base/libs/audioflinger/AudioFlinger.cpp
AudioFlinger {
    RecordHandle : public android::BnAudioRecord
        |
        V
    RecordThread::RecordTrack : public TrackBase
    RecordThread : public ThreadBase, public AudioBufferProvider
}
*/
////////////////////////////////////////////////////////////////////////////////


////////////////////////////////////////////////////////////////////////////////
// Application
////////////////////////////////////////////////////////////////////////////////
/*
Audio Capture

    1. Create a new instance of android.media.MediaRecorder using new
    2. Set the audio source using MediaRecorder.setAudioSource(). 
        MediaRecorder.AudioSource.MIC
    3. Set output file format using MediaRecorder.setOutputFormat()
    4. Set output file name using MediaRecorder.setOutputFile()
    5. Set the audio encoder using MediaRecorder.setAudioEncoder()
    6. Call MediaRecorder.prepare() on the MediaRecorder instance.
    7. To start audio capture, call MediaRecorder.start().
    8. To stop audio capture, call MediaRecorder.stop().
    9. When you are done with the MediaRecorder instance, call MediaRecorder.release() on it. 
        Calling MediaRecorder.release() is always recommended to free the resource immediately.
*/
    private void start(){  
        try {  
            recorder = new MediaRecorder();  
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);  
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);  
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.ACC);  
            String path = Environment.getExternalStorageDirectory()+File.separator + "test.mp3";  
            if(!new File(path).exists()) new File(path).createNewFile();  
            recorder.setOutputFile(path);  
            recorder.prepare();  
            recorder.start();   // Recording is now started  
        } catch (Exception e) {  
            Log.e("", "prepare() failed");  
        }  
    }  

    private void stopAndSave(){  
        recorder.stop();  
        recorder.release();  
        recorder = null;  
    }  

////////////////////////////////////////////////////////////////////////////////
// Framework
////////////////////////////////////////////////////////////////////////////////
base/media/java/android/media/MediaRecorder.java
// Basically, a wrapper of native class
public class MediaRecorder
    public final class AudioSource
        MIC, VOICE_UPLINK, VOICE_DOWNLINK, VOICE_CALL, ..., FM_RX, FM_RX_A2DP

    public final class OutputFormat 
        THREE_GPP, MPEG_4, RAW_AMR, ...

    public final class AudioEncoder
        AMR_NB, AMR_WB, AAC, ...

    public MediaRecorder() 
        native_setup(new WeakReference<MediaRecorder>(this));

    // implement interfaces
    status_t    initCheck();
    status_t    setAudioSource(int as);
    status_t    setOutputFormat(int of);
    status_t    setAudioEncoder(int ae);
    status_t    setOutputFile(const char* path);
    status_t    setOutputFile(int fd, int64_t offset, int64_t length);
    status_t    setParameters(const String8& params);
    status_t    setListener(const sp<MediaRecorderListener>& listener);
    status_t    getMaxAmplitude(int* max);

    status_t    prepare();
    status_t    start();
    status_t    stop();
    status_t    reset();
    status_t    init();
    status_t    close();
    status_t    release();

    status_t    setCamera(const sp<ICamera>& camera);
    status_t    setPreviewSurface(const sp<Surface>& surface);
    status_t    setVideoSource(int vs);
    status_t    setVideoEncoder(int ve);
    status_t    setVideoSize(int width, int height);
    status_t    setVideoFrameRate(int frames_per_second);
    status_t    setCameraParameters(const String8& params);

    void        notify(int msg, int ext1, int ext2);

// 1. JNI implementation of MediaRecorder.java
// 2. Basically, a wrapper of MediaRecorder.cpp
base/media/jni/android_media_MediaRecorder.cpp

    static void android_media_MediaRecorder_native_setup(JNIEnv *env, jobject thiz, jobject weak_this)
        sp<MediaRecorder> mr = new MediaRecorder();
        mr->initCheck();
        setMediaRecorder(env, thiz, mr);

// 1. Maitain a state machine for error checking
// 2. Basically, a wrapper of IMediaRecorder implemented by MediaRecorderClient
base/media/libmedia/mediarecorder.cpp
    class MediaRecorder : public BnMediaRecorderClient,
                          public virtual IMediaDeathNotifier

    sp<IMediaRecorder>          mMediaRecorder;
    sp<MediaRecorderListener>   mListener;

    MediaRecorder::MediaRecorder()
        const sp<IMediaPlayerService>& service(getMediaPlayerService());
        mMediaRecorder = service->createMediaRecorder(getpid());
            |
            V
        1 F   f    createMediaRecorder  base/media/libmedia/IMediaPlayerService.cpp
        class:android::BpMediaPlayerService
          virtual sp<IMediaRecorder> createMediaRecorder(pid_t pid)
                |
                V
        2 F   f    createMediaRecorder  base/media/libmediaplayerservice/MediaPlayerService.cpp
        class:android::MediaPlayerService
          sp<IMediaRecorder> MediaPlayerService::createMediaRecorder(pid_t pid)

              sp<MediaRecorderClient> recorder = new MediaRecorderClient(this, pid);
              wp<MediaRecorderClient> w = recorder;
              mMediaRecorderClients.add(w);  

// Basically, a wrapper of MediaRecorderBase, which could be implemented by 
//      StagefrightRecorder, PVMediaRecorder
base/media/libmediaplayerservice/MediaRecorderClient.cpp
MediaRecorderClient
    class MediaRecorderClient : public BnMediaRecorder
        pid_t                       mPid;
        Mutex                       mLock;
        MediaRecorderBase           *mRecorder;
        sp<MediaPlayerService>      mMediaPlayerService;

    MediaRecorderClient::MediaRecorderClient(const sp<MediaPlayerService>& service, pid_t pid)
        mPid = pid;
    #ifdef USE_BOARD_MEDIARECORDER
        mRecorder = createMediaRecorderHardware(); 
    #else  
        char value[PROPERTY_VALUE_MAX];
        if (!property_get("media.stagefright.enable-record", value, NULL)
                || !strcmp(value, "1") || !strcasecmp(value, "true")) {
            mRecorder = new StagefrightRecorder;
        } else
    #ifndef NO_OPENCORE
    {
        mRecorder = new PVMediaRecorder();
    }
    #else
    {
        mRecorder = NULL;
    }
    #endif
    #endif
    mMediaPlayerService = service;

////////////////////////////////////////////////////////////////////////////////
// libstagefright
////////////////////////////////////////////////////////////////////////////////
struct StagefrightRecorder : public MediaRecorderBase 

    sp<MediaSource> StagefrightRecorder::createAudioSource()
         sp<AudioSource> audioSource = new AudioSource( 
                 mAudioSource, mSampleRate, mAudioChannels);

         status_t err = audioSource->initCheck();

         OMXClient client;
         // audioEncoder-wrap-> AudioSource-wrap-> AudioRecord
         sp<MediaSource> audioEncoder = OMXCodec::Create(client.interface(), 
                 encMeta, true /* createEncoder */, audioSource);

status_t StagefrightRecorder::startAMRRecording()
    sp<MediaSource> audioEncoder = createAudioSource();
    mWriter = new AMRWriter(dup(mOutputFd));
    mWriter->addSource(audioEncoder);
    mWriter->setListener(mListener); 
    mWriter->start();
        |
        V
    status_t AMRWriter::start(MetaData *params)
        // start the encoder, then the AudioRecord
        status_t err = mSource->start();
        pthread_create(&mThread, &attr, ThreadWrapper, this);
            |
            V
        status_t AMRWriter::threadFunc()
            while (!mDone)
                // read data from the encoder
                err = mSource->read(&buffer);
                if (mPaused) { 
                    buffer->release(); 
                    buffer = NULL; 
                    continue; 

                // check file size limit and duration limit

                // write data to target file
                ssize_t n = fwrite(
                    (const uint8_t *)buffer->data() + buffer->range_offset(),
                    1,
                    buffer->range_length(),
                    mFile);

base/include/media/stagefright/AudioSource.h
    struct AudioSource : public MediaSource 
        virtual status_t read(MediaBuffer **buffer, const ReadOptions *options = NULL);
        AudioRecord *mRecord;

// wrap AudioRecord
base/media/libstagefright/AudioSource.cpp

    AudioSource::AudioSource(int inputSource, uint32_t sampleRate, uint32_t channels)
        mRecord = new AudioRecord( inputSource, sampleRate, AudioSystem::PCM_16_BIT,
                channels > 1 ? AudioSystem::CHANNEL_IN_STEREO: AudioSystem::CHANNEL_IN_MONO,
                4 * kMaxBufferSize / sizeof(int16_t), /* Enable ping-pong buffers */ flags);

        mInitCheck = mRecord->initCheck();

    status_t AudioSource::start(MetaData *params) 
        status_t err = mRecord->start();
        mGroup = new MediaBufferGroup;
        mGroup->add_buffer(new MediaBuffer(kMaxBufferSize));

    status_t AudioSource::stop() 
        mRecord->stop();
        delete mGroup;
        mGroup = NULL;

    // key function
    status_t AudioSource::read(MediaBuffer **out, const ReadOptions *options) 
        MediaBuffer *buffer;
        CHECK_EQ(mGroup->acquire_buffer(&buffer), OK);
        while (mStarted)
            mRecord->getPosition(&numFramesRecorded);
            uint32_t sampleRate = mRecord->getSampleRate();
            ssize_t n = mRecord->read(buffer->data(), buffer->size());

////////////////////////////////////////////////////////////////////////////////
// AudioFlinger
////////////////////////////////////////////////////////////////////////////////
code.reading/AudioFlinger_RecordHandle.cpp

