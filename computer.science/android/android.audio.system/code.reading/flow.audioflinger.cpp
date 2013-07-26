
// AudioFlinger的创建
base/media/mediaserver/main_mediaserver.cpp
    int main(int argc, char** argv)
        sp<ProcessState> proc(ProcessState::self());
        sp<IServiceManager> sm = defaultServiceManager();
        LOGI("ServiceManager: %p", sm.get());

        AudioFlinger::instantiate();
        MediaPlayerService::instantiate();
        CameraService::instantiate();
        AudioPolicyService::instantiate();

        //add by pekall for video call (start)
    #ifdef ANDROID_HAS_VIDEOCALL
        VideoTelephonyServiceInstantiate();
    #endif
        //add by pekall for video call (end)

        ProcessState::self()->startThreadPool();
        IPCThreadState::self()->joinThreadPool();

            |
            V
    void AudioFlinger::instantiate() {
        defaultServiceManager()->addService(
            String16("media.audio_flinger"), new AudioFlinger());

AudioFlinger::AudioFlinger()  : BnAudioFlinger(),       
    mAudioHardware(0), mMasterVolume(1.0f), mMasterMute(false), 
    mNextThreadId(0), mFMRadioStream(0)
        
    mAudioHardware = AudioHardwareInterface::create(); 
                |
                V
        // create AudioHardwareGeneric() for generic or emulator
        // otherwise, invoking hal function to create vendor hardware
        AudioHardwareInterface* AudioHardwareInterface::create();
#ifdef GENERIC_AUDIO                                                   
            hw = new AudioHardwareGeneric();                                   
#else                                                                  
            // if running in emulation - use the emulator driver               
            if (property_get("ro.kernel.qemu", value, 0)) {                    
                LOGD("Running in emulation - using generic audio driver");     
                hw = new AudioHardwareGeneric();                               
            }                                                                  
            else {                                                             
                LOGV("Creating Vendor Specific AudioHardware");                
                hw = createAudioHardware();                                    
            }                                                                  
#endif  
            if (hw->initCheck() != NO_ERROR) 

#ifdef WITH_A2DP
            hw = new A2dpAudioInterface(hw);
#endif

    if (mAudioHardware->initCheck() == NO_ERROR) 
        setMode(AudioSystem::MODE_NORMAL);

        setMasterVolume(1.0f);
        setMasterMute(false);

================================================================================
user端调用createTrack的流程
sp<IAudioTrack> AudioFlinger::createTrack(
        pid_t pid,
        int streamType,     
        uint32_t sampleRate,  
        int format,         
        int channelCount,   
        int frameCount,     
        uint32_t flags,     
        const sp<IMemory>& sharedBuffer,
        int output,
        status_t *status) 

        // output的是一个索引号，通过它可找到一个工作线程(同时对应着一个hardware output)
        // 一般系统中有一个默认的output mHardwareOutpu, A2DP有一个output
        PlaybackThread *thread = checkPlaybackThread_l(output);

        // 看看这个线程是否是AudioFlinger的client, 通过pid来区别不同的client
        wclient = mClients.valueFor(pid);
        if (wclient != NULL) {
            client = wclient.promote();
        } else {
            client = new Client(this, pid);
            mClients.add(pid, client);
        }

        // 在找到的工作线程中创建一个track
        track = thread->createTrack_l(client, streamType, sampleRate, format,
                        channelCount, frameCount, sharedBuffer, &lStatus);

        // TrackHandle是Track对象的proxy对象，在user端的AudioTrack中就是IAudioTrack
        trackHandle = new TrackHandle(track);
        return TrackHandle;

sp<AudioFlinger::PlaybackThread::Track>  AudioFlinger::PlaybackThread::createTrack_l(
        const sp<AudioFlinger::Client>& client,
        int streamType,
        uint32_t sampleRate,
        int format,
        int channelCount,
        int frameCount,
        const sp<IMemory>& sharedBuffer, // 0
        status_t *status)

    Mutex::Autolock _l(mLock);
    track = new Track(this, client, streamType, sampleRate, format,
            channelCount, frameCount, sharedBuffer);
    mTracks.add(track);

AudioFlinger::PlaybackThread::Track::Track(
        const wp<ThreadBase>& thread,
        const sp<Client>& client,      
        int streamType,
        uint32_t sampleRate,           
        int format,
        int channelCount,
        int frameCount,
        const sp<IMemory>& sharedBuffer)
    :   TrackBase(thread, client, sampleRate, format, channelCount, frameCount, 0, sharedBuffer),
    mMute(false), mSharedBuffer(sharedBuffer), mName(-1) 

    if (mCblk != NULL) {  // create by TrackBase
        sp<ThreadBase> baseThread = thread.promote(); 
        if (baseThread != 0) {
            PlaybackThread *playbackThread = (PlaybackThread *)baseThread.get();
            mName = playbackThread->getTrackName_l();
        }
        mVolume[0] = 1.0f;
        mVolume[1] = 1.0f;
        mStreamType = streamType;
        mCblk->frameSize = AudioSystem::isLinearPCM(format) ? channelCount * sizeof(int16_t) : sizeof(int8_t);


// 1. TrackBase创建了一片共享内存
// 2. CB对象通过placement new的放在在这边内存上构造
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

    size_t size = sizeof(audio_track_cblk_t);       // CB的大小
    size_t bufferSize = frameCount*channelCount*sizeof(int16_t); // 缓冲区的大小
    if (sharedBuffer == 0) 
        size += bufferSize; // 共享内存 CB+Buffer

    // 创建一片共享内存
    mCblkMemory = client->heap()->allocate(size);
    // pointer()返回共享内存的首地址
    mCblk = static_cast<audio_track_cblk_t *>(mCblkMemory->pointer());
    new(mCblk) audio_track_cblk_t();

    // clear all buffers
    mCblk->frameCount = frameCount;
    mCblk->sampleRate = sampleRate;
    mCblk->channels = (uint8_t)channelCount;

    if (sharedBuffer == 0)
        // 清空数据区
        mBuffer = (char*)mCblk + sizeof(audio_track_cblk_t);
        memset(mBuffer, 0, frameCount*channelCount*sizeof(int16_t));
        // Force underrun condition to avoid false underrun callback until first data is written to buffer
        mCblk->flowControlFlag = 1;

    mBufferEnd = (uint8_t *)mBuffer + bufferSize;


// --- Client ---
// 对AudioFlinger客户端的封装, 凡是使用了AudioTrack和AudioRecord的进程
// 都会被当成是AF的Client. Client已其pid为id
// 一个Client进程可以创建多个AudioTrack, 这些AudioTrack属于同一个Client
class Client : public RefBase {
public:
    Client(const sp<AudioFlinger>& audioFlinger, pid_t pid);
    virtual             ~Client();
    const sp<MemoryDealer>&     heap() const;
    pid_t               pid() const { return mPid; }
    sp<AudioFlinger>    audioFlinger() { return mAudioFlinger; }

private:
    Client(const Client&);
    Client& operator = (const Client&);
    sp<AudioFlinger>    mAudioFlinger;
    sp<MemoryDealer>    mMemoryDealer; // 内存分配器
    pid_t               mPid;
};

工作线程介绍:
    PlaybackThread : public ThreadBase
        回放线程，用于音频输出
        成员变量
            AudioStreamOut * mOutput;
            描述一个硬件输出

            维护两个Track数组
                SortedVector< sp<Track> > mTracks;          // 线程中所有track
                SortedVector< wp<Track> > mActiveTracks;    // 线程中活跃的track

        MixerThread : public PlaybackThread
            混音线程，将来自于多个源的音频数据混音后再输出 
            会将多个AudioTrack中的数据进行混音，然后输出到同一个AudioStreamOut中

            DuplicatingThread : public MixerThread 
                多路输出线程，也可以混音。将混音后的数据输出到多个output.
                目前仅在蓝牙A2DP中使用。

                维护数组：
                    SortedVector < sp<OutputTrack> >  mOutputTracks; //多路输出的目的设备
                    OutputTrack包括了一个DuplicatingThread索引

        DirectOutputThread : public PlaybackThread 
            直接输出线程, 选择一路音频流后直接将音频输出。
            由于没有混音的操作，可以减少很多延时。


    RecordThread : public ThreadBase, public AudioBufferProvider
        录音线程，用于音频输入
        成员变量
            AudioStreamIn * mInput;
            描述一个硬件输入

MixerThread分析
    一个MixerThread和一个AudioStreamOut, 已经一个线程索引号对应 

    创建者
    int AudioFlinger::openOutput(uint32_t *pDevices,
            uint32_t *pSamplingRate,       
            uint32_t *pFormat,             
            uint32_t *pChannels,           
            uint32_t *pLatencyMs,          
            uint32_t flags)  

        AudioStreamOut *output = mAudioHardware->openOutputStream(*pDevices,
                (int *)&format,
                &channels,
                &samplingRate,
                &status);
        thread = new MixerThread(this, output, ++mNextThreadId);
        mPlaybackThreads.add(mNextThreadId, thread);
        return mNextThreadId;

    ./code.reading/AudioFlinger_MixerThread.cpp
    ./code.reading/AudioFlinger_PlaybackThread.cpp
    ./code.reading/AudioFlinger_PlaybackThread_Track.cpp
