/* PlaybackThread
1. 提供readOutputParameters方法从硬件获取 sample rate, channel, format, frame size等参数
2. 申请mMixBuffer, 以供混音使用
3. 提供创建Track的接口createTrack_l
4. 提供数组mTracks管理创建的所有Track
5. 提供数组mStreamTypes保存各个stream的音量
6. 提供数组mActiveTracks管理活跃的Track, 接口addTrack_l, destroyTrack_l
7. 提供机制通知注册的client(AudioTrack), 声音配置修改, audioConfigChanged_l
8. 提供获取硬件render position的接口
*/

// 1. readOutputParameters
// 2. set master volume and mute in the caches
// 3. initialize volume for each stream
AudioFlinger::PlaybackThread::PlaybackThread(
    const sp<AudioFlinger>& audioFlinger, AudioStreamOut* output, 
    int id, uint32_t device)
    :   ThreadBase(audioFlinger, id), mMixBuffer(0), mSuspended(0), 
        mBytesWritten(0), mOutput(output), mLastWriteTime(0), mNumWrites(0), 
        mNumDelayedWrites(0), mInWrite(false), mDevice(device)

    readOutputParameters();

    mMasterVolume = mAudioFlinger->masterVolume();
    mMasterMute = mAudioFlinger->masterMute();

    for (int stream = 0; stream < AudioSystem::NUM_STREAM_TYPES; stream++) {
        mStreamTypes[stream].volume = 
            mAudioFlinger->streamVolumeInternal(stream);
        mStreamTypes[stream].mute = 
            mAudioFlinger->streamMute(stream);

AudioFlinger::PlaybackThread::~PlaybackThread()
    delete [] mMixBuffer;

void AudioFlinger::PlaybackThread::onFirstRef()
    run(buffer, ANDROID_PRIORITY_URGENT_AUDIO);

// 1. create a Track
// 2. add the Track to mTracks
sp<AudioFlinger::PlaybackThread::Track>  AudioFlinger::PlaybackThread::
    createTrack_l( const sp<AudioFlinger::Client>& client, int streamType, 
    uint32_t sampleRate, int format, int channelCount, int frameCount, 
    const sp<IMemory>& sharedBuffer, int sessionId, status_t *status)

    // all tracks in same audio session must share the same routing strategy otherwise
    // conflicts will happen when tracks are moved from one output to another by audio policy manager
    track = new Track(this, client, streamType, sampleRate, format,
            channelCount, frameCount, sharedBuffer, sessionId);
    mTracks.add(track);

    sp<EffectChain> chain = getEffectChain_l(sessionId);
    if (chain != 0) {
        track->setMainBuffer(chain->inBuffer());
        chain->setStrategy(AudioSystem::getStrategyForStream(
            (AudioSystem::stream_type)track->type()));


status_t AudioFlinger::PlaybackThread::setMasterVolume(float value)
    mMasterVolume = value;

status_t AudioFlinger::PlaybackThread::setMasterMute(bool muted)
    mMasterMute = muted;

bool AudioFlinger::PlaybackThread::masterMute() const
    return mMasterMute;

status_t AudioFlinger::PlaybackThread::setStreamVolume(
    int stream, float value)
    mStreamTypes[stream].volume = value;

status_t AudioFlinger::PlaybackThread::setStreamMute(
    int stream, bool muted)
    mStreamTypes[stream].mute = muted;

// 1. search mActiveTracks for the stream
bool AudioFlinger::PlaybackThread::isStreamActive(int stream) const


// 1. add the track to mActiveTracks
status_t AudioFlinger::PlaybackThread::addTrack_l(const sp<Track>& track)
    if (mActiveTracks.indexOf(track) < 0) 
        mActiveTracks.add(track);

    // 唤醒MixerThread, 通知它有活跃Track, 需要开工干活
    mWaitWorkCV.broadcast();

// 1. remove the track from mTracks
// 2. invoke deleteTrackName_l()
void AudioFlinger::PlaybackThread::destroyTrack_l(const sp<Track>& track)

// 1. invoke AudioStreamOut::getParameters
String8 AudioFlinger::PlaybackThread::getParameters(const String8& keys)
    return mOutput->getParameters(keys);


// 1. save mChannels, mSampleRate, mFormat, mFrameCount, latency() in OutputDescriptor
// 2. invoke mAudioFlinger->audioConfigChanged_l(event, mId, param2);
void AudioFlinger::PlaybackThread::audioConfigChanged_l(int event, int param) {
    AudioSystem::OutputDescriptor desc;
 
// 1. read mSampleRate, mChannels, mChannelCount, mFormat, mFrameSize, mFrameCount, 
//      from the hardware, mOutput(AudioStreamOut)
// 2. allocate mMixBuffer for the PlaybackThread
// 3. TODO: effect chain, moveEffectChain_l()
void AudioFlinger::PlaybackThread::readOutputParameters()


// 1. invoke AudioStreamOut::getRenderPosition() to get pos from the hardware
status_t AudioFlinger::PlaybackThread::getRenderPosition(
        uint32_t *halFrames, uint32_t *dspFrames)
    return mOutput->getRenderPosition(dspFrames);

// TODO: what is a session
// clue:
//  EFFECT_SESSION, TRACK_SESSION
//  each track has a session id
//  each effect chain has a session id
uint32_t AudioFlinger::PlaybackThread::hasAudioSession(int sessionId)

// 1. get stream type of the track according to  the sessionId
// 2. invoke AudioSystem::getStrategyForStream(
//      (AudioSystem::stream_type) track->type()) 
uint32_t AudioFlinger::PlaybackThread::getStrategyForSession_l(int sessionId)


sp<AudioFlinger::EffectChain> AudioFlinger::PlaybackThread::
    getEffectChain(int sessionId)
            |
            V
// 1. get EffectChain from the array mEffectChains according to the sessionId
sp<AudioFlinger::EffectChain> AudioFlinger::PlaybackThread::
getEffectChain_l(int sessionId)

// 1. invoke EffectChain::setMode_l for each EffectChain in mEffectChains
void AudioFlinger::PlaybackThread::setMode(uint32_t mode)




