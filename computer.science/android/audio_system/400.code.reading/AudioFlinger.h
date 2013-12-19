

// 1. include a PlaybackThread vector, mPlaybackThreads, which maps output and PlaybackThread
// 2. include a RecordThread vector, mRecordThreads, which maps input device and RecordThread
class AudioFlinger : public BinderService<AudioFlinger>, public BnAudioFlinger

////////////////////////////////////////////////////////////////////////////////
// IAudioFlinger interface
////////////////////////////////////////////////////////////////////////////////
virtual sp<IAudioTrack> createTrack( pid_t pid, int streamType, uint32_t sampleRate, 
    int format, int channelCount, int frameCount, uint32_t flags, 
    const sp<IMemory>& sharedBuffer, int output, int *sessionId, status_t *status);

// hareware parameters
virtual     uint32_t    sampleRate(int output) const;
virtual     int         channelCount(int output) const;
virtual     int         format(int output) const;
virtual     size_t      frameCount(int output) const;
virtual     uint32_t    latency(int output) const;

// volume control
virtual     status_t    setMasterVolume(float value);
virtual     status_t    setMasterMute(bool muted);
virtual     float       masterVolume() const;
virtual     bool        masterMute() const;
virtual     status_t    setStreamVolume(int stream, float value, int output);
virtual     status_t    setStreamMute(int stream, bool muted);
virtual     float       streamVolume(int stream, int output) const;
virtual     bool        streamMute(int stream) const;
virtual status_t setVoiceVolume(float volume);
#ifdef HAVE_FM_RADIO
virtual status_t setFmVolume(float volume);
#endif


// mic
virtual     status_t    setMicMute(bool state);
virtual     bool        getMicMute() const;

// set haredware parameter
virtual     status_t    setParameters(int ioHandle, const String8& keyValuePairs);
virtual     String8     getParameters(int ioHandle, const String8& keys);

virtual     void        registerClient(const sp<IAudioFlingerClient>& client);

virtual     size_t      getInputBufferSize(uint32_t sampleRate, int format, int channelCount);
virtual     unsigned int  getInputFramesLost(int ioHandle);

// output control
virtual int openOutput(uint32_t *pDevices, uint32_t *pSamplingRate, uint32_t *pFormat, 
        uint32_t *pChannels, uint32_t *pLatencyMs, uint32_t flags);
virtual int openDuplicateOutput(int output1, int output2);
virtual status_t closeOutput(int output);
virtual status_t suspendOutput(int output);
virtual status_t restoreOutput(int output);
virtual status_t setStreamOutput(uint32_t stream, int output);

// input control
virtual int openInput(uint32_t *pDevices, uint32_t *pSamplingRate, uint32_t *pFormat, uint32_t *pChannels, uint32_t acoustics);
virtual status_t closeInput(int input);

// effect control
virtual status_t loadEffectLibrary(const char *libPath, int *handle);
virtual status_t unloadEffectLibrary(int handle);
virtual status_t queryNumberEffects(uint32_t *numEffects);
virtual status_t queryEffect(uint32_t index, effect_descriptor_t *descriptor);
virtual status_t getEffectDescriptor(effect_uuid_t *pUuid, effect_descriptor_t *descriptor);
virtual sp<IEffect> createEffect(pid_t pid, effect_descriptor_t *pDesc, const sp<IEffectClient>& effectClient, 
        int32_t priority, int output, int sessionId, status_t *status, int *id, int *enabled);
virtual status_t moveEffects(int session, int srcOutput, int dstOutput);

// record interface
virtual sp<IAudioRecord> openRecord( pid_t pid, int input, uint32_t sampleRate, int format, 
int channelCount, int frameCount, uint32_t flags, int *sessionId, status_t *status);


virtual     status_t    setMode(int mode);
uint32_t getMode() { return mMode; }
virtual     bool        isStreamActive(int stream) const;
virtual status_t getRenderPosition(uint32_t *halFrames, uint32_t *dspFrames, int output);
virtual int newAudioSessionId();

// hareware call state
enum hardware_call_state {
    AUDIO_HW_IDLE = 0,
    AUDIO_HW_INIT,
    AUDIO_HW_OUTPUT_OPEN,
    AUDIO_HW_OUTPUT_CLOSE,
    AUDIO_HW_INPUT_OPEN,
    AUDIO_HW_INPUT_CLOSE,
    AUDIO_HW_STANDBY,
    AUDIO_HW_SET_MASTER_VOLUME,
    AUDIO_HW_GET_ROUTING,
    AUDIO_HW_SET_ROUTING,
    AUDIO_HW_GET_MODE,
    AUDIO_HW_SET_MODE,
    AUDIO_HW_GET_MIC_MUTE,
    AUDIO_HW_SET_MIC_MUTE,
    AUDIO_SET_VOICE_VOLUME,
    AUDIO_SET_PARAMETER,
#ifdef HAVE_FM_RADIO
    AUDIO_SET_FM_VOLUME
#endif

////////////////////////////////////////////////////////////////////////////////
// IAudioFlinger interface end
////////////////////////////////////////////////////////////////////////////////

PlaybackThread *checkPlaybackThread_l(int output) const;
MixerThread *checkMixerThread_l(int output) const;
RecordThread *checkRecordThread_l(int input) const;

float streamVolumeInternal(int stream) const { return mStreamTypes[stream].volume; }
void audioConfigChanged_l(int event, int ioHandle, void *param2);

int  nextUniqueId();
status_t moveEffectChain_l(int session, AudioFlinger::PlaybackThread *srcThread, 

AudioFlinger::PlaybackThread *dstThread, bool reRegister);

mutable Mutex mLock;
mutable Mutex mHardwareLock;
DefaultKeyedVector< pid_t, wp<Client> >     mClients;

AudioHardwareInterface*             mAudioHardware;
mutable int mHardwareStatus;

DefaultKeyedVector< int, sp<PlaybackThread> >  mPlaybackThreads;
PlaybackThread::stream_type_t       mStreamTypes[AudioSystem::NUM_STREAM_TYPES];
float                               mMasterVolume;
bool                                mMasterMute;

DefaultKeyedVector< int, sp<RecordThread> >    mRecordThreads;
DefaultKeyedVector< pid_t, sp<NotificationClient> >    mNotificationClients;
volatile int32_t                    mNextUniqueId;
uint32_t mMode;

////////////////////////////////////////////////////////////////////////////////
// Internal classes
////////////////////////////////////////////////////////////////////////////////

class TrackHandle;
class RecordHandle;
class RecordThread;
class PlaybackThread;
class MixerThread;
class DirectOutputThread;
class DuplicatingThread;
class Track;
class RecordTrack;

class EffectModule;
class EffectHandle;
class EffectChain;

// base class for record and playback
// 是一个AudioBufferProvider, 提供声音的IPC共享内存
// 提供track控制接口，start, stop, step, reset
// 提供session控制
// 提供声音配置的参数
// FIXME: what is a client? 
class TrackBase : public AudioBufferProvider, public RefBase {
    enum track_state { IDLE, TERMINATED, STOPPED, RESUMING, ACTIVE, PAUSING, PAUSED };
    enum track_flags {
        STEPSERVER_FAILED = 0x01, //  StepServer could not acquire cblk->lock mutex
        SYSTEM_FLAGS_MASK = 0x0000ffffUL, // The upper 16 bits are used for track-specific flags.

    TrackBase(const wp<ThreadBase>& thread, const sp<Client>& client, uint32_t sampleRate, int format, 
            int channelCount, int frameCount, uint32_t flags, const sp<IMemory>& sharedBuffer, int sessionId);

    virtual status_t    start() = 0;
    virtual void        stop() = 0;
    sp<IMemory> getCblk() const;
    audio_track_cblk_t* cblk() const { return mCblk; }
    int         sessionId() { return mSessionId; }

    protected:
    virtual status_t    start() = 0;
    virtual void        stop() = 0;
    sp<IMemory> getCblk() const;
    audio_track_cblk_t* cblk() const { return mCblk; }
    int         sessionId() { return mSessionId; }

    int format() const { return mFormat; }
    int channelCount() const ;
    int sampleRate() const;
    void* getBuffer(uint32_t offset, uint32_t frames) const;
    bool isStopped() const { return mState == STOPPED; }
    bool isTerminated() const { return mState == TERMINATED; }
    bool step();
    void reset();

    wp<ThreadBase>      mThread;
    sp<Client>          mClient;
    sp<IMemory>         mCblkMemory;
    audio_track_cblk_t* mCblk;
    void*               mBuffer;
    void*               mBufferEnd;
    uint32_t            mFrameCount;
    // we don't really need a lock for these
    int                 mState;
    int                 mClientTid;
    uint8_t             mFormat;
    uint32_t            mFlags;
    int                 mSessionId;


// 提供一个线程用于播放或录制
// 提供线程同步的Mutex和Condition
// 声音配置变量
class ThreadBase : public Thread 
    ThreadBase (const sp<AudioFlinger>& audioFlinger, int id);

    uint32_t    sampleRate() const;
    int         channelCount() const;
    int         format() const;
    size_t      frameCount() const;
    void        wakeUp()    { mWaitWorkCV.broadcast(); }
    void        exit();
    virtual     bool        checkForNewParameters_l() = 0;
    virtual     status_t    setParameters(const String8& keyValuePairs);
    virtual     String8     getParameters(const String8& keys) = 0;
    virtual     void        audioConfigChanged_l(int event, int param = 0) = 0;
    void        sendConfigEvent(int event, int param = 0);
    void        sendConfigEvent_l(int event, int param = 0);
    void        processConfigEvents();
    int         id() const { return mId;}
    bool        standby() { return mStandby; }

    mutable     Mutex                   mLock;
    Condition               mWaitWorkCV;
    sp<AudioFlinger>        mAudioFlinger;
    uint32_t                mSampleRate;
    size_t                  mFrameCount;
    uint32_t                mChannels;
    uint16_t                mChannelCount;
    uint16_t                mFrameSize;
    int                     mFormat;
    Condition               mParamCond;
    Vector<String8>         mNewParameters;
    status_t                mParamStatus;
    Vector<ConfigEvent *>   mConfigEvents;
    bool                    mStandby;
    int                     mId;
    bool                    mExiting;

// 播放线程
// 1. 提供音量控制接口
// 2. 提供创建和控制track的接口
// 3. 提供effect相关接口
// 4. 提供内部类Track->OutputTrack
class PlaybackThread : public ThreadBase 
    enum type { MIXER, DIRECT, DUPLICATING };
    enum mixer_state { MIXER_IDLE, MIXER_TRACKS_ENABLED, MIXER_TRACKS_READY };

    // Thread virtuals
    virtual     status_t    readyToRun();
    virtual     void        onFirstRef();                                                                                                

    virtual     uint32_t    latency() const;                                                                                             

    // volume control
    virtual     status_t    setMasterVolume(float value);  
    virtual     status_t    setMasterMute(bool muted);                                                                                   
    virtual     float       masterVolume() const;
    virtual     bool        masterMute() const;                                                                                          
    virtual     status_t    setStreamVolume(int stream, float value);
    virtual     status_t    setStreamMute(int stream, bool muted);                                                                       
    virtual     float       streamVolume(int stream) const;
    virtual     bool        streamMute(int stream) const;

    bool        isStreamActive(int stream) const;

    sp<Track>   createTrack_l(const sp<AudioFlinger::Client>& client, int streamType, uint32_t sampleRate, int format,
            int channelCount, int frameCount, const sp<IMemory>& sharedBuffer, int sessionId, status_t *status);

    AudioStreamOut* getOutput() { return mOutput; }

    virtual     int         type() const { return mType; }
    void        suspend() { mSuspended++; }
    void        restore() { if (mSuspended) mSuspended--; }
    bool        isSuspended() { return (mSuspended != 0); }
    virtual     String8     getParameters(const String8& keys);
    virtual     void        audioConfigChanged_l(int event, int param = 0);
    virtual     status_t    getRenderPosition(uint32_t *halFrames, uint32_t *dspFrames);
    int16_t     *mixBuffer() { return mMixBuffer; };

    // effect
    sp<EffectHandle> createEffect_l( const sp<AudioFlinger::Client>& client, const sp<IEffectClient>& effectClient,
            int32_t priority, int sessionId, effect_descriptor_t *desc, int *enabled, status_t *status);
    void disconnectEffect(const sp< EffectModule>& effect, const wp<EffectHandle>& handle);

    // return values for hasAudioSession (bit field)
    enum effect_state {
        EFFECT_SESSION = 0x1,   // the audio session corresponds to at least one
        // effect
        TRACK_SESSION = 0x2     // the audio session corresponds to at least one
            // track
    uint32_t hasAudioSession(int sessionId);
    sp<EffectChain> getEffectChain(int sessionId);
    sp<EffectChain> getEffectChain_l(int sessionId);
    status_t addEffectChain_l(const sp<EffectChain>& chain);
    size_t removeEffectChain_l(const sp<EffectChain>& chain);
    void lockEffectChains_l(Vector<sp <EffectChain> >& effectChains);
    void unlockEffectChains(Vector<sp <EffectChain> >& effectChains);
    sp<AudioFlinger::EffectModule> getEffect_l(int sessionId, int effectId);

    void detachAuxEffect_l(int effectId);
    status_t attachAuxEffect(const sp<AudioFlinger::PlaybackThread::Track> track, int EffectId);
    status_t attachAuxEffect_l(const sp<AudioFlinger::PlaybackThread::Track> track, int EffectId);
    void setMode(uint32_t mode);
    status_t addEffect_l(const sp< EffectModule>& effect);
    void removeEffect_l(const sp< EffectModule>& effect);

    SortedVector< wp<Track> >       mActiveTracks;

    SortedVector< sp<Track> >       mTracks;
    // mStreamTypes[] uses 1 additionnal stream type internally for the OutputTrack used by DuplicatingThread
    stream_type_t                   mStreamTypes[AudioSystem::NUM_STREAM_TYPES + 1];
    AudioStreamOut*                 mOutput;
    float                           mMasterVolume;
    nsecs_t                         mLastWriteTime;
    int                             mNumWrites;
    int                             mNumDelayedWrites;
    bool                            mInWrite;
    Vector< sp<EffectChain> >       mEffectChains;
    uint32_t                        mDevice;

    /* 1. 提供track控制方法: start(), stop(), pause(), flush(), destroy(), 
            mute(bool), setVolume(float left, float right)
       2. 提供AUX buffer, main buffer */
    class Track : public TrackBase {
        Track(const wp<ThreadBase>& thread, const sp<Client>& client, int streamType, uint32_t sampleRate, 
                int format, int channelCount, int frameCount, const sp<IMemory>& sharedBuffer, int sessionId);

        status_t    attachAuxEffect(int EffectId);
        void        setAuxBuffer(int EffectId, int32_t *buffer);
        int32_t     *auxBuffer() { return mAuxBuffer; }
        void        setMainBuffer(int16_t *buffer) { mMainBuffer = buffer; }
        int16_t     *mainBuffer() { return mMainBuffer; }
        int         auxEffectId() { return mAuxEffectId; }

        virtual status_t getNextBuffer(AudioBufferProvider::Buffer* buffer);

        // we don't really need a lock for these
        float               mVolume[2];
        volatile bool       mMute;
        // FILLED state is used for suppressing volume ramp at begin of playing
        enum {FS_FILLING, FS_FILLED, FS_ACTIVE};
        mutable uint8_t     mFillingUpStatus;
        int8_t              mRetryCount;
        sp<IMemory>         mSharedBuffer;
        bool                mResetDone;
        int                 mStreamType;
        int                 mName;
        int16_t             *mMainBuffer;
        int32_t             *mAuxBuffer;
        int                 mAuxEffectId;
        bool                mHasVolumeController;

    // playback track
    // 1. 提供track控制方法: start(), stop(), write()
    // 2. 提供一个buffer queue
    // 3. 包含一个DuplicatingThread
    class OutputTrack : public Track 
    
// 1. 包括了一个AudioMixer
class MixerThread : public PlaybackThread
    MixerThread (const sp<AudioFlinger>& audioFlinger, AudioStreamOut* output, int id, uint32_t device);

    void        invalidateTracks(int streamType);
    virtual     bool        checkForNewParameters_l();

    uint32_t    prepareTracks_l(const SortedVector< wp<Track> >& activeTracks, Vector< sp<Track> > *tracksToRemove);
    virtual     int         getTrackName_l();
    virtual     void        deleteTrackName_l(int name);
    virtual     uint32_t    activeSleepTimeUs();
    virtual     uint32_t    idleSleepTimeUs();
    virtual     uint32_t    suspendSleepTimeUs();

    AudioMixer*                     mAudioMixer;

// 
class DirectOutputThread : public PlaybackThread
    DirectOutputThread (const sp<AudioFlinger>& audioFlinger, AudioStreamOut* output, int id, uint32_t device);

    virtual     int         getTrackName_l();
    virtual     void        deleteTrackName_l(int name);
    virtual     uint32_t    activeSleepTimeUs();
    virtual     uint32_t    idleSleepTimeUs();
    virtual     uint32_t    suspendSleepTimeUs();

    void applyVolume(uint16_t leftVol, uint16_t rightVol, bool ramp);

    float mLeftVolFloat;
    float mRightVolFloat;
    uint16_t mLeftVolShort;
    uint16_t mRightVolShort;

// 1. 提供了添加和删除track的接口
class DuplicatingThread : public MixerThread
    DuplicatingThread (const sp<AudioFlinger>& audioFlinger, MixerThread* mainThread, int id);

    virtual     bool        threadLoop();
    void        addOutputTrack(MixerThread* thread);
    void        removeOutputTrack(MixerThread* thread);
    uint32_t    waitTimeMs() { return mWaitTimeMs; }

    virtual     uint32_t    activeSleepTimeUs();
    bool        outputsReady(SortedVector< sp<OutputTrack> > &outputTracks);
    void        updateWaitTime();


// 1. 提供了控制track的接口 start() stop() flush() mute(bool) pause() 
// setVolume(float left, float right)
class TrackHandle : public android::BnAudioTrack {
    TrackHandle(const sp<PlaybackThread::Track>& track);

    virtual sp<IMemory> getCblk() const;
    virtual status_t    attachAuxEffect(int effectId);

    sp<PlaybackThread::Track> mTrack;


// record thread
// 1. provide track control interfaces, start, stop
// 2. provide buffer control interfaces
// 3. hardware parameters
class RecordThread : public ThreadBase, public AudioBufferProvider

    RecordThread(const sp<AudioFlinger>& audioFlinger, AudioStreamIn *input, 
            uint32_t sampleRate, uint32_t channels, int id);

    status_t    start(RecordTrack* recordTrack);
    void        stop(RecordTrack* recordTrack);
    AudioStreamIn* getInput() { return mInput; }

    virtual status_t    getNextBuffer(AudioBufferProvider::Buffer* buffer);
    virtual void        releaseBuffer(AudioBufferProvider::Buffer* buffer);
    virtual bool        checkForNewParameters_l();
    virtual String8     getParameters(const String8& keys);
    virtual void        audioConfigChanged_l(int event, int param = 0);
    void        readInputParameters();
    virtual unsigned int  getInputFramesLost();

    AudioStreamIn                       *mInput;
    sp<RecordTrack>                     mActiveTrack;
    Condition                           mStartStopCond;
    AudioResampler                      *mResampler;
    int32_t                             *mRsmpOutBuffer;
    int16_t                             *mRsmpInBuffer;
    size_t                              mRsmpInIndex;
    size_t                              mInputBytes;
    int                                 mReqChannelCount;
    uint32_t                            mReqSampleRate;
    ssize_t                             mBytesRead;

    // record track
    // 1. track control interfaces, start, stop
    // 2. buffer managerment
    class RecordTrack : public TrackBase {
        RecordTrack(const wp<ThreadBase>& thread, const sp<Client>& client, uint32_t sampleRate, 
            int format, int channelCount, int frameCount, uint32_t flags, int sessionId);

        virtual status_t    start();
        virtual void        stop();

        virtual status_t getNextBuffer(AudioBufferProvider::Buffer* buffer);
        bool mOverflow;

    // 1. track control, start, stop
    class RecordHandle : public android::BnAudioRecord {
            RecordHandle(const sp<RecordThread::RecordTrack>& recordTrack);
            virtual             ~RecordHandle();
            virtual status_t    start();
            virtual void        stop();
            virtual sp<IMemory> getCblk() const;
            virtual status_t onTransact(
                    uint32_t code, const Parcel& data, Parcel* reply, uint32_t flags);

            sp<RecordThread::RecordTrack> mRecordTrack;

    //--- Audio Effect Management TODO:


struct audio_track_cblk_t
    // The data members are grouped so that members accessed frequently and in the same context
    // are in the same line of data cache.
    Mutex       lock;
    Condition   cv;
    volatile    uint32_t    user;
    volatile    uint32_t    server;
    uint32_t    userBase;
    uint32_t    serverBase;
    void*       buffers;
    uint32_t    frameCount;
    // Cache line boundary
    uint32_t    loopStart;
    uint32_t    loopEnd;
    int         loopCount;
    volatile    union {
        uint16_t    volume[2];
        uint32_t    volumeLR;
    };
    uint32_t    sampleRate;
    // NOTE: audio_track_cblk_t::frameSize is not equal to AudioTrack::frameSize() for
    // 8 bit PCM data: in this case,  mCblk->frameSize is based on a sample size of
    // 16 bit because data is converted to 16 bit before being stored in buffer

    uint8_t     frameSize;
    uint8_t     channelCount;
    uint16_t    flags;

    uint16_t    bufferTimeoutMs; // Maximum cumulated timeout before restarting audioflinger
    uint16_t    waitTimeMs;      // Cumulated wait time

    uint16_t    sendLevel;
    uint16_t    reserved;
    // Cache line boundary (32 bytes)
    audio_track_cblk_t();
    uint32_t    stepUser(uint32_t frameCount);
    bool        stepServer(uint32_t frameCount);
    void*       buffer(uint32_t offset) const;
    uint32_t    framesAvailable();
    uint32_t    framesAvailable_l();
    uint32_t    framesReady();


