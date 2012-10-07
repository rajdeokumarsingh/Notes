/* MixerThread
    1. 提供一个AudioMixer对各个Track进行混音
    2. 在本线程函数中对包括的所有Track进行混音
    3. 将混音后的数据写入到硬件中
*/

// 1. create an AudioMixer for mixing audio data
AudioFlinger::MixerThread::MixerThread(const sp<AudioFlinger>& audioFlinger, 
        AudioStreamOut* output, int id, uint32_t device)
    :PlaybackThread(audioFlinger, output, id, device), mAudioMixer(0)
    mType = PlaybackThread::MIXER;
    mAudioMixer = new AudioMixer(mFrameCount, mSampleRate);

// 1. processConfigEvents
// 2. put the hardware output to standby, if no active tracks
// 3. prepareTracks_l, set all active tracks to the mAudioMixer
// 4. lockEffectChains_l
// 5. mix buffers by AudioMixer::process()
// 6. If no tracks are ready, sleep once for the duration of 
//      an output buffer size, then write 0s to the output
// 7. write the mixed buffer to the hardware output
// 8. remove all processed tracks
bool AudioFlinger::MixerThread::threadLoop()
    while (!exitPending())
        // 处理一些请求和通知消息, 如之前在构造函数中发出的OUTPUT_OPEN等消息
        processConfigEvents();

        // 获取当前活跃的track数组 
        const SortedVector< wp<Track> >& activeTracks = mActiveTracks;

        if UNLIKELY((!activeTracks.size() && systemTime() > 
                    standbyTime) || mSuspended) 
            // put audio hardware into standby after short delay
            mOutput->standby();
            mWaitWorkCV.wait(mLock);

        // 检查mActiveTracks数组，判断是否有AudioTrack有数据需要处理
        // 例如有些AudioTrack调用了start函数，但是没有及时调用write写数据
        // 这就没有必要进行混音的操作。
        mixerStatus = prepareTracks_l(activeTracks, &tracksToRemove);

        // MIXER_TRACKS_READY表示AudioTrack已经吧数据准备好了
        if (LIKELY(mixerStatus == MIXER_TRACKS_READY)) 
            // 混音器进行工作，将混音结果保存在curBuf中
            mAudioMixer->process();
            sleepTime = 0; //设置睡眠时间为0， 表示需要将结果立即输出到Audio HAL中
            standbyTime = systemTime() + kStandbyTimeInNsecs; 
        else
            // If no tracks are ready, sleep once for the duration of an output
            // buffer size, then write 0s to the output

        // sleepTime == 0 means we must write to audio hardware right now
        if (sleepTime == 0)
            // 向Audio HAL的outputStream中写入混音后的数据
            int bytesWritten = (int)mOutput->write(mMixBuffer, mixBufferSize);
        else
            usleep(sleepTime);

        // finally let go of all our tracks, without the lock held
        // since we can't guarantee the destructors won't acquire that
        // same lock.
        tracksToRemove.clear();

    if (!mStandby) mOutput->standby();

// for all active tracks
// 1. set the active track to the mAudioMixer 
// 2. get the volume information from the audio_track_cblk_t of the track
// 3. set buffer provider to the mAudioMixer
// 4. enable MIXING
// 5. set parameters, VOLUME0, VOLUME1, AUXLEVEL, FORMAT, 
//      CHANNEL_COUNT, SAMPLE_RATE, MAIN_BUFFER, AUX_BUFFER
// 6. if the track is stopped or paused, add the track to tracksToRemove and 
//  disable audio MIXING end for all active tracks
// 7. remove all the tracks that need to be...
uint32_t AudioFlinger::MixerThread::prepareTracks_l(
    const SortedVector< wp<Track> >& activeTracks, 
    Vector< sp<Track> > *tracksToRemove)

    // 活跃的track数目
    size_t count = activeTracks.size();

    // 依次检查这些活跃的track的状态
    for (size_t i=0 ; i<count ; i++)
        sp<Track> t = activeTracks[i].promote();
        Track* const track = t.get();
        audio_track_cblk_t* cblk = track->cblk();

        // 一个混音器可支持32个Track
        // setActiveTrack后， mAudioMixer的接下来的后续操作都是对这个Track进行的
        mAudioMixer->setActiveTrack(track->name());
        // Track中的数据是否可用
        if (cblk->framesReady() && track->isReady() &&
                !track->isPaused() && !track->isTerminated())

            // compute volume for this track
            int16_t left, right;
            if (track->isMuted() || masterMute || track->isPausing() ||
                    mStreamTypes[track->type()].mute) {
                left = right = 0; // 如果mute了， 设置左右音量为0
            else 
                // 读取系统设置中的stream的音量, 通过master volume, stream volume
                // 和mediaplayer volume计算出最终的音量

            // 设置数据的提供者, Track是从AudioBufferProvider派生出来
            // 混音器工作时，需要从track获取数据. AudioTrack写入的数据由混音器来消费
            mAudioMixer->setBufferProvider(track);
            mAudioMixer->enable(AudioMixer::MIXING);

            // 设置音量
            mAudioMixer->setParameter(param, AudioMixer::VOLUME0, (void *)left);
            mAudioMixer->setParameter(param, AudioMixer::VOLUME1, (void *)right);
            mAudioMixer->setParameter(param, AudioMixer::AUXLEVEL, (void *)aux);
            mAudioMixer->setParameter(AudioMixer::TRACK, 
                AudioMixer::FORMAT, (void *)track->format());

            mAudioMixer->setParameter(AudioMixer::TRACK, 
                AudioMixer::CHANNEL_COUNT, (void *)track->channelCount());
            mAudioMixer->setParameter(AudioMixer::RESAMPLE, 
                AudioMixer::SAMPLE_RATE, (void *)(cblk->sampleRate));
            mAudioMixer->setParameter(AudioMixer::TRACK, 
                AudioMixer::MAIN_BUFFER, (void *)track->mainBuffer());
            mAudioMixer->setParameter(AudioMixer::TRACK, 
                AudioMixer::AUX_BUFFER, (void *)track->auxBuffer()); 
        else // paused or stopped, Track中没有数据或
            if (track->isStopped()) 
                track->reset(); // 清零读写位置, 表示没有可读数据

            if (track->isTerminated() || track->isStopped() || track->isPaused()) 
                // We have consumed all the buffers of this track.
                // Remove it from the list of active tracks.
                // 加入移除队列
                tracksToRemove->add(track);
            else
                // 暂时没有可读数据，可能是AudioTracks没有来得及写。则设置重试次数
                // No buffers for this track. Give it a few chances to
                // fill a buffer, then remove it from active list.

            // 禁止这个track进行混音
            mAudioMixer->disable(AudioMixer::MIXING);
    // for (size_t i=0 ; i<count ; i++)

    // remove all the tracks that need to be...
    count = tracksToRemove->size();
    for (size_t i=0 ; i<count ; i++) 
        const sp<Track>& track = tracksToRemove->itemAt(i);
        mActiveTracks.remove(track);
        // TODO: handle effect chains
        if (track->isTerminated()) 
            mTracks.remove(track);
            deleteTrackName_l(track->mName);

    // mix buffer must be cleared if all tracks are connected to an
    // effect chain as in this case the mixer will not write to
    // mix buffer and track effects will accumulate into it
    if (mixedTracks != 0 && mixedTracks == tracksWithEffect) 
        memset(mMixBuffer, 0, mFrameCount * mChannelCount * sizeof(int16_t));

// 1. add CBLK_INVALID_ON to the cblk of the stream type
void AudioFlinger::MixerThread::invalidateTracks(int streamType)
    size_t size = mTracks.size();
    for (size_t i = 0; i < size; i++) {
        sp<Track> t = mTracks[i];
        if (t->type() == streamType) {
            t->mCblk->lock.lock();
            t->mCblk->flags |= CBLK_INVALID_ON;
            t->mCblk->cv.signal();
            t->mCblk->lock.unlock();

// for all parameters
// 1. invoke AudioStreamOut::setParameters to set parameters 
//  to the hareware output
// 2. readOutputParameters
// 3. renew AudioMixer
bool AudioFlinger::MixerThread::checkForNewParameters_l()
    while (!mNewParameters.isEmpty()) 
        status = mOutput->setParameters(keyValuePair);
        if (status == NO_ERROR && reconfig) 
            delete mAudioMixer;
            readOutputParameters();
            mAudioMixer = new AudioMixer(mFrameCount, mSampleRate);
            for (size_t i = 0; i < mTracks.size() ; i++) {
                int name = getTrackName_l();
                mTracks[i]->mName = name;
                // limit track sample rate to 2 x new output sample rate
                if (mTracks[i]->mCblk->sampleRate > 2 * sampleRate()) {
                    mTracks[i]->mCblk->sampleRate = 2 * sampleRate();
            sendConfigEvent_l(AudioSystem::OUTPUT_CONFIG_CHANGED);

        mNewParameters.removeAt(0);
        mParamCond.signal();
        mWaitWorkCV.wait(mLock);


////////////////////////////////////////////////////////////////////////////////
// FIXME: Important function, VERBOS note
bool AudioFlinger::MixerThread::threadLoop() {
    int16_t* curBuf = mMixBuffer;       // Jiang Rui: 指向混音器的buffer，保存各条track混音完成后的声音数据
    Vector< sp<Track> > tracksToRemove; // Jiang Rui: 保存混音器处理完成，或已经停止的track
    uint32_t mixerStatus = MIXER_IDLE;
    nsecs_t standbyTime = systemTime(); // Jiang Rui: 向硬件写入数据完成后，会等待一段时间（3秒）
                                        //  然后mixer thread 才进入standy状态
    size_t mixBufferSize = mFrameCount * mFrameSize; // Jiang Rui: mixBufferSize 32768

    // FIXME: Relaxed timing because of a certain device that can't meet latency
    // Should be reduced to 2x after the vendor fixes the driver issue
    nsecs_t maxPeriod = seconds(mFrameCount) / mSampleRate * 3;     // Jiang Rui: 一般mixer thread一次会向硬件写入mFrameCount帧
                                                                    // maxPeriod是3倍的mFrameCount的播放时间
    nsecs_t lastWarning = 0; // Jiang Rui: 上一次出现硬件故障(blocked)的时间
    bool longStandbyExit = false; // Jiang Rui: 好像没啥用

    uint32_t activeSleepTime = activeSleepTimeUs(); // Jiang Rui: 硬件延时的一半 微秒
        // return (uint32_t)(mOutput->latency() * 1000) / 2;

    uint32_t idleSleepTime = idleSleepTimeUs(); // Jiang Rui: 硬件播放完mFrameCount的时间， 微秒
        // return (uint32_t)((mFrameCount * 1000) / mSampleRate) * 1000;

    uint32_t sleepTime = idleSleepTime;

    while (!exitPending())
    {
        // Jiang Rui: 检查处理mChannelCount, mSampleRate,latency之类的变化
        // 通知AudioFlinger的client这些事件
        processConfigEvents(); 

        mixerStatus = MIXER_IDLE;
        { // scope for mLock

            Mutex::Autolock _l(mLock);
            if (checkForNewParameters_l()) {    // Jiang Rui: 如果当前硬件输出的硬件参数发生变化(setParameter被调用) 
                                                // 如sample rate, format, channel, frame count 等等
                                                // 则更新相关参数
                mixBufferSize = mFrameCount * mFrameSize;
                maxPeriod = seconds(mFrameCount) / mSampleRate * 3;
                activeSleepTime = activeSleepTimeUs();
                idleSleepTime = idleSleepTimeUs();
            }

            const SortedVector< wp<Track> >& activeTracks = mActiveTracks;

            // put audio hardware into standby after short delay
            if UNLIKELY((!activeTracks.size() && systemTime() > standbyTime) ||
                        mSuspended) { // Jiang Rui:
                                      // 如果没有活跃的track, 且已经等待时间超过3秒
                                      // 或被外部接口suspend, 则准备standby

                LOGI("MixerThread, threadLoop, ready for standby");
                if (!mStandby) {    // Jiang Rui: 硬件standy
                    LOGI("Audio hardware entering standby, mixer %p, mSuspended %d\n", this, mSuspended);
                    mOutput->standby();
                    mStandby = true;
                    mBytesWritten = 0;
                }

                if (!activeTracks.size() && mConfigEvents.isEmpty()) {
                    // we're about to wait, flush the binder command buffer
                    IPCThreadState::self()->flushCommands();

                    LOGI("MixerThread, threadLoop, about to wait");
                    if (exitPending()) break;   // Jiang Rui: 如果线程处于退出状态

                    // wait until we have something to do...
                    LOGI("MixerThread %p TID %d going to sleep\n", this, gettid());
                    mWaitWorkCV.wait(mLock);
                    LOGI("MixerThread %p TID %d waking up\n", this, gettid());

                    if (mMasterMute == false) { // Jiang Rui: 全部静音
                        char value[PROPERTY_VALUE_MAX];
                        property_get("ro.audio.silent", value, "0");
                        if (atoi(value)) {
                            LOGD("Silence is golden");
                            setMasterMute(true);
                        }
                    }

                    standbyTime = systemTime() + kStandbyTimeInNsecs; // Jiang Rui: 更新standy等待时间
                    sleepTime = idleSleepTime;  // Jiang Rui: 更新sleepTime等待时间
                    continue;
                }
            }
            mixerStatus = prepareTracks_l(activeTracks, &tracksToRemove); // Jiang Rui: 检查是否有track需要混音
       }

        if (LIKELY(mixerStatus == MIXER_TRACKS_READY)) {
            LOGI("mix buffers...");
            mAudioMixer->process(curBuf); // Jiang Rui: 混音
            sleepTime = 0;  // Jiang Rui: 立即向硬件写声音数据
            standbyTime = systemTime() + kStandbyTimeInNsecs; // Jiang Rui: 更新等待时间
            // doneMix = true;
        } else {
            // If no tracks are ready, sleep once for the duration of an output
            // buffer size, then write 0s to the output
            if (sleepTime == 0) {
                if (mixerStatus == MIXER_TRACKS_ENABLED) {
                    sleepTime = activeSleepTime; // Jiang Rui: 如果mixer处于enabled状态, 等一个较短的时间
                } else {
                    sleepTime = idleSleepTime; // Jiang Rui: 如果mixer处于enabled状态, 等一个较长的时间
                }
            } else if (mBytesWritten != 0 ||
                       (mixerStatus == MIXER_TRACKS_ENABLED && longStandbyExit)) {
                // FIXME: Jiang Rui: why write 0s to the output? 特殊的硬件要求? Wang Hui?
                // memset (curBuf, 0, mixBufferSize);
                // sleepTime = 0;
                // LOGI_IF((mBytesWritten == 0 && (mixerStatus == MIXER_TRACKS_ENABLED && longStandbyExit)), "anticipated start");
                LOGI("MixerThread, anticipated start");
            }
        }

        if (mSuspended) {  // Jiang Rui: 外部接口suspend()被调用, 一直等待直到restore
            LOGI("MixerThread, threadLoop, suspend");
            sleepTime = idleSleepTime;
        }

        // sleepTime == 0 means we must write to audio hardware
        if (sleepTime == 0) {
            mLastWriteTime = systemTime();  // Jiang Rui: 向硬件写数据之前的时间
            mInWrite = true;                // Jiang Rui: 进入写数据状态
            mBytesWritten += mixBufferSize;

            int bytesWritten = (int)mOutput->write(curBuf, mixBufferSize);  // Jiang Rui: 向硬件写数据
            LOGI("MixerThread, mixBufferSize: %d, bytesWritten: %d bytes, write count: %d", 
                    mixBufferSize, bytesWritten, mNumWrites);
            if (bytesWritten < 0) mBytesWritten -= mixBufferSize; // Jiang Rui: 写入失败, roll back mBytesWritten
            mInWrite = false;
            nsecs_t now = systemTime();
            nsecs_t delta = now - mLastWriteTime;   // Jiang Rui: delta是向硬件写数据花费的时间
            if (delta > maxPeriod) {            // Jiang Rui: 如果delta大于了3倍的数据的播放时间， 说明硬件出现了故障
                mNumDelayedWrites++;
                if ((now - lastWarning) > kWarningThrottle) {  // Jiang Rui: 如果现在据上一次出现硬件故障的时间超过5秒
                    LOGW("write blocked for %llu msecs, %d delayed writes, thread %p",
                            ns2ms(delta), mNumDelayedWrites, this);
                    lastWarning = now;
                }
                if (mStandby) {
                    longStandbyExit = true;
                }
            }
            mStandby = false;
        } else {
            usleep(sleepTime); // Jiang Rui: 休眠一段时间, 然后检查是否有上层输入声音数据
                                   // 避免太快地standby硬件
        }

        // finally let go of all our tracks, without the lock held
        // since we can't guarantee the destructors won't acquire that
        // same lock.
        tracksToRemove.clear();
    }

    if (!mStandby) {
        mOutput->standby();
    }

    LOGI("MixerThread %p exiting", this);
    return false;
}

// 播放一次dial tone的log 
/*{
    // 混音完成
    01-01 01:20:35.728 I/AudioFlinger(  624): mix buffers...
    // 向硬件输出完成
    01-01 01:20:35.728 I/AudioFlinger(  624): MixerThread, mixBufferSize: 32768, bytesWritten: 32768 bytes, write count: 245
    // Track reset
    01-01 01:20:35.728 I/AudioFlinger(  624): TrackBase::reset

    // 等待一段时间, 3秒,定义在
    {    
        base/libs/audioflinger/AudioFlinger.h
        static const nsecs_t kStandbyTimeInNsecs = seconds(3);
    }
    01-01 01:20:35.921 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:36.108 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:36.294 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:36.481 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:36.668 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:36.854 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:37.040 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:37.225 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:37.411 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:37.598 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:37.784 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:37.970 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:38.155 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:38.342 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:38.528 I/AudioFlinger(  624): MixerThread, anticipated start
    01-01 01:20:38.714 I/AudioFlinger(  624): MixerThread, anticipated start

    // 没有等待到上层的声音输出数据, mixer thread进入standby
    01-01 01:20:38.900 I/AudioFlinger(  624): MixerThread, threadLoop, ready for standby
    01-01 01:20:38.900 I/AudioFlinger(  624): Audio hardware entering standby, mixer 0x32aa8, mSuspended 0
    01-01 01:20:38.900 I/AudioFlinger(  624): MixerThread, threadLoop, about to wait
    01-01 01:20:38.900 I/AudioFlinger(  624): MixerThread 0x32aa8 TID 683 going to sleep
}*/

