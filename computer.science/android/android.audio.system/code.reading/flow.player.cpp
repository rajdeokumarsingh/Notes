////////////////////////////////////////////////////////////////////////////////
/*
TODO：
    线程结构
 */
////////////////////////////////////////////////////////////////////////////////
/* 
basic call flow:

Application
    |
    V
base/media/java/android/media/MediaPlayer.java
MediaPlayer
    | JNI
    V 
base/media/jni/android_media_MediaPlayer.cpp
    |
    V
base/media/libmedia/mediaplayer.cpp
MediaPlayer
    |
    V
base/include/media/IMediaPlayer.h
IMediaPlayer
        |           application process
        | IPC ----------------------------------------
        |           media player service process
        V
base/media/libmediaplayerservice/MediaPlayerService.h
MediaPlayerService::Client: public BnMediaPlayer
    |
    V
base/include/media/MediaPlayerInterface.h
MediaPlayerBase (interface)
    |
    | implemented by 
    V
PVPlayer | MidiFile | VorbisPlayer | StagefrightPlayer
                                        | for example
                                        V
base/media/libmediaplayerservice/StagefrightPlayer.cpp
StagefrightPlayer
    |
    V
base/media/libstagefright/AwesomePlayer.cpp
AwesomePlayer
    |
    V
base/media/libstagefright/AudioPlayer.cpp
AudioPlayer
    |
    V
base/media/libmedia/AudioTrack.cpp
AudioTrack
    |
    V
base/include/media/IAudioTrack.h
IAudioTrack
        |           media player service process
        | IPC ----------------------------------------
        |           audio flinger process
        V
base/libs/audioflinger/AudioFlinger.cpp
AudioFlinger {
    TrackHandle : public android::BnAudioTrack
        |
        V
    PlaybackThread::Track : public TrackBase
    PlaybackThread : public ThreadBase
}

================================================================================
Music Application:

src/com/android/music/MediaPlaybackService.java
    private MediaPlayer mMediaPlayer = new MediaPlayer(); 

    mMediaPlayer.reset();          

    mMediaPlayer.setOnPreparedListener(null);

    mMediaPlayer.setDataSource(path);                                                                                        

    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);                                                                  

    mMediaPlayer.prepare(); 

    mMediaPlayer.setOnCompletionListener(listener);
    mMediaPlayer.setOnErrorListener(errorListener);

    mMediaPlayer.start();

    // stop
    mMediaPlayer.reset();

    mMediaPlayer.pause();

    mMediaPlayer.seekTo((int) whereto);

    mMediaPlayer.setVolume(vol, vol);

    mMediaPlayer.isPlaying()
================================================================================
                        |
                        | Java function call
                        V
================================================================================
Framework
base/media/java/android/media/MediaPlayer.java
            | 
            V JNI
base/media/jni/android_media_MediaPlayer.cpp

--------------------------------------------------------------------------------
// Wrap a cpp MediaPlayer to do the job.
static void android_media_MediaPlayer_native_setup(JNIEnv *env, jobject thiz, jobject weak_this)
    sp<MediaPlayer> mp = new MediaPlayer();

    // create new listener and give it to MediaPlayer
    sp<JNIMediaPlayerListener> listener = new JNIMediaPlayerListener(env, thiz, weak_this);
    mp->setListener(listener);

    setMediaPlayer(env, thiz, mp);

--------------------------------------------------------------------------------
// wrap an IMediaPlayer to do the IPC job
class MediaPlayer : public BnMediaPlayerClient,                                                               
    public virtual IMediaDeathNotifier 
        sp<IMediaPlayer>            mPlayer;

status_t MediaPlayer::setDataSource(int fd, int64_t offset, int64_t length)
    status_t err = UNKNOWN_ERROR;  
    const sp<IMediaPlayerService>& service(getMediaPlayerService());
    if (service != 0) {
        sp<IMediaPlayer> player(service->create(getpid(), this, fd, offset, length));
        err = setDataSource(player);   
    }  
    return err;

status_t MediaPlayer::setDataSource(const sp<IMediaPlayer>& player)
    // MediaPlayer just wraps a IMediaPlayer, which is MediaPlayerService::Client
    mPlayer = player;

--------------------------------------------------------------------------------
// IPC native implementation
base/media/libmediaplayerservice/MediaPlayerService.h
base/media/libmediaplayerservice/MediaPlayerService.cpp

class MediaPlayerService : public BnMediaPlayerService
    // wrap a AudioTrack
     class AudioOutput : public MediaPlayerBase::AudioSink
        AudioTrack*             mTrack;
        AudioCallback           mCallback;

    // The Client wraps a MediaPlayerBase, which could be
    class Client : public BnMediaPlayer
        sp<MediaPlayerBase>         mPlayer; 
        sp<MediaPlayerBase>     getPlayer() const { Mutex::Autolock lock(mLock); return mPlayer; }


// 创建player的流程
// 1. 根据url的类型获取player的类型，如PV, GST, STAGEFRIGHT_PLAYER
// 2. 创建player
// 3. 设置player的output
// 4. 设置player的data source
status_t MediaPlayerService::Client::setDataSource(
        const char *url, const KeyedVector<String8, String8> *headers)
    player_type playerType = getPlayerType(url);
    sp<MediaPlayerBase> p = createPlayer(playerType);

    if (!p->hardwareOutput()) {
        mAudioOutput = new AudioOutput();
        static_cast<MediaPlayerInterface*>(p.get())->setAudioSink(mAudioOutput);
    }

    // now set data source
    LOGV(" setDataSource");
    mStatus = p->setDataSource(url, headers);
    if (mStatus == NO_ERROR) {
        mPlayer = p;
    }

// Player的类型
enum player_type {
    PV_PLAYER = 1,
    SONIVOX_PLAYER = 2,
    VORBIS_PLAYER = 3,
    STAGEFRIGHT_PLAYER = 4,
    // Test players are available only in the 'test' and 'eng' builds.
    // The shared library with the test player is passed passed as an
    // argument to the 'test:' url in the setDataSource call.
#ifdef ANDROID_USE_GSTREAMER
    GST_PLAYER = 5, //STEricsson
#endif
    TEST_PLAYER = 6,
};

// 播放流程 : 例 StagefrightPlayer --(wrap)--> AwesomePlayer
// 1. 创建一个AudioPlayer, 并设置audio sink和audio source
// 2. 调用AudioPlayer的start()方法
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
    mTimeSource = mAudioPlayer;
        |
        V 调用AudioPlayer::start()
base/include/media/stagefright/AudioPlayer.h
class AudioPlayer : public TimeSource 
    sp<MediaSource> mSource;
    MediaBuffer *mInputBuffer;

    AudioTrack *mAudioTrack;
    sp<MediaPlayerBase::AudioSink> mAudioSink;

// 在AudioPlayer的start()方法中
// 3. 从数据源中读数据到buffer
// 4. 从数据源获取 format, sample rate, channel count 等信息
// 5. 如果mAudioSink不为空，调用其open函数，传入sample rate, channel, format 
//      及其一个callback, 然后调用start()
// 6. 否则， 创建一个AudioTrack, 传入sample rate, channel, format 
//      及其一个callback, 然后调用start()
// AudioTrack需要读数据时，会调用这个callbak来获取数据
status_t AudioPlayer::start(bool sourceAlreadyStarted)
    if (!sourceAlreadyStarted) 
        err = mSource->start();

    mFirstBufferResult = mSource->read(&mFirstBuffer);
    if (mAudioSink.get() != NULL)
        status_t err = mAudioSink->open(
                mSampleRate, numChannels, AudioSystem::PCM_16_BIT,
                DEFAULT_AUDIOSINK_BUFFERCOUNT,
                &AudioPlayer::AudioSinkCallback, this);

        mAudioSink->start();
    else
        mAudioTrack = new AudioTrack(
                AudioSystem::MUSIC, mSampleRate, AudioSystem::PCM_16_BIT,
                (numChannels == 2)
                ? AudioSystem::CHANNEL_OUT_STEREO
                : AudioSystem::CHANNEL_OUT_MONO,
                0, 0, &AudioCallback, this, 0);

        if ((err = mAudioTrack->initCheck()) != OK)

// 7. AudioTrack调用audio callback请求数据
// 8. 调用fillBuffer填充缓冲区
void AudioPlayer::AudioCallback(int event, void *info) {
    if (event != AudioTrack::EVENT_MORE_DATA) { return; }

    AudioTrack::Buffer *buffer = (AudioTrack::Buffer *)info;
    size_t numBytesWritten = fillBuffer(buffer->raw, buffer->size);
    buffer->size = numBytesWritten;
}

// 9. 从数据源(解码器)中获取数据
// 10 .将获取到的数据拷贝到buffer中
// 11. 函数返回后AudioFlinger端AudioTrack将会收到媒体数据
size_t AudioPlayer::fillBuffer(void *data, size_t size)
    while (size_remaining > 0)
        MediaSource::ReadOptions options;
        if (mSeeking) 
            // do not care for seeking

        err = mSource->read(&mInputBuffer, &options);
        // calculate time

        memcpy((char *)data + size_done, (const char *)
            mInputBuffer->data() + mInputBuffer->range_offset(), copy);

================================================================================
AudioFlinger流程
================================================================================
每一个音频流对应着一个AudioTrack类的一个实例
每个AudioTrack会在创建时注册到AudioFlinger中
由AudioFlinger把所有的AudioTrack进行混合（Mixer），然后输送到AudioHardware中进行播放

最多可以创建32个音频流

frameworks/base/media/libmedia/audiotrack.cpp

如何使用AudioTrack
    ToneGenerator是android中产生电话拨号音和其他音调波形的一个实现

    ToneGenerator的初始化函数：
     bool ToneGenerator::initAudioTrack() {  
        // Open audio track in mono, PCM 16bit, default sampling rate, default buffer size  
         mpAudioTrack = new AudioTrack();  
          // 参数的设置, 并注册到AudioFlinger中
         mpAudioTrack->set(mStreamType,  
                           0,  
                           AudioSystem::PCM_16_BIT,  
                           AudioSystem::CHANNEL_OUT_MONO,  
                           0,  
                           0,  
                           audioCallback,  
                           this,  
                           0,  
                           0,  
                           mThreadCanCallJava);  
         if (mpAudioTrack->initCheck() != NO_ERROR) {  
             LOGE("AudioTrack->initCheck failed");  
             goto initAudioTrack_exit;  
         }  
         mpAudioTrack->setVolume(mVolume, mVolume);  
         mState = TONE_INIT;  
         ......  
      } 

      // audioCallback是一个回调函数，负责响应AudioTrack的通知，例如填充数据、循环播放、播放位置触发等等
        void ToneGenerator::audioCallback(int event, void* user, void *info) {  
            if (event != AudioTrack::EVENT_MORE_DATA) return;  
            AudioTrack::Buffer *buffer = static_cast<AudioTrack::Buffer *>(info);  
            ToneGenerator *lpToneGen = static_cast<ToneGenerator *>(user);  
            short *lpOut = buffer->i16;  
            unsigned int lNumSmp = buffer->size/sizeof(short);  
            const ToneDescriptor *lpToneDesc = lpToneGen->mpToneDesc;  
            if (buffer->size == 0) return;  

            // Clear output buffer: WaveGenerator accumulates into lpOut buffer  
            memset(lpOut, 0, buffer->size);  
            ......  
                // 以下是产生音调数据的代码，略....  
        }  

        enum event_type {  
            EVENT_MORE_DATA = 0,        // Request to write more data to PCM buffer.  
            EVENT_UNDERRUN = 1,         // PCM buffer underrun occured.  
            EVENT_LOOP_END = 2,         // Sample loop end was reached; playback restarted from loop start if loop count was not 0.  
            EVENT_MARKER = 3,           // Playback head is at the specified marker position (See setMarkerPosition()).  
            EVENT_NEW_POS = 4,          // Playback head is at a new position (See setPositionUpdatePeriod()).  
            EVENT_BUFFER_END = 5        // Playback head is at the end of the buffer.  
        };  

开始播放：
    mpAudioTrack->start();  

停止播放：
    mpAudioTrack->stop(); 

AudioTrack和AudioFlinger的通信机制

================================================================================

base/media/java/android/media/AudioTrack.java
base/core/jni/android_media_AudioTrack.cpp

MediaPlayerService::AudioOutput::start
    mTrack->setVolume(mLeftVolume, mRightVolume);
    mTrack->start();
    mTrack->getPosition(&mNumFramesWritten);

AudioTrack::start
    mAudioTrack->start() IAudioTrack

    BpAudioTrack::start()
        status_t status = remote()->transact(START, data, &reply);

    BnAudioTrack::onTransact
        case START: reply->writeInt32(start());

//class TrackHandle : public android::BnAudioTrack
AudioFlinger::TrackHandle::start
    //PlaybackThread::Track
    return mTrack->start();

[framework/base/libs/audioflinger/]
AudioFlinger::PlaybackThread::Track::start

[framework/base/media/libmedia/]
status_t AudioSystem::startOutput(audio_io_handle_t output, AudioSystem::stream_type stream)
    const sp<IAudioPolicyService>& aps = AudioSystem::get_audio_policy_service();
    return aps->startOutput(output, stream);


    BpAudioPolicyService::startOutput(audio_io_handle_t output, AudioSystem::stream_type stream)
        remote()->transact(START_OUTPUT, data, &reply);

BnAudioPolicyService::onTransact
    case START_OUTPUT: 
        reply->writeInt32(static_cast <uint32_t>(startOutput(output, (AudioSystem::stream_type)stream)));

[framework/base/libs/audioflinger/]
AudioPolicyService::startOutput
    mpPolicyManager->startOutput(output, stream);
        |
        V
[hardware/alsa_sound_Eclair]
AudioPolicyManagerALSA::startOutput(audio_io_handle_t output, AudioSystem::stream_type stream)

==============================================================================
44 F   f    start             base/libs/audioflinger/AudioFlinger.cpp
class:android::AudioFlinger::PlaybackThread::OutputTrack
status_t AudioFlinger::PlaybackThread::OutputTrack::start()

45 F   f    start             base/libs/audioflinger/AudioFlinger.cpp
class:android::AudioFlinger::PlaybackThread::Track
status_t AudioFlinger::PlaybackThread::Track::start()

49 F   f    start             base/libs/audioflinger/AudioFlinger.cpp
class:android::AudioFlinger::TrackHandle
status_t AudioFlinger::TrackHandle::start() {

55 F   f    start             base/media/libmedia/AudioTrack.cpp
class:android::AudioTrack
void AudioTrack::start()
==============================================================================

