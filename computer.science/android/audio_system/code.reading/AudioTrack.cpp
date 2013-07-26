head file

// 控制一个缓冲区，生产者和消费者模型
struct audio_track_cblk_t
    // 两个同步变量， 支持跨进程共享内存
    Mutex       lock;
    Condition   cv;

    volatile    uint32_t    user;       // 生产者当前写位置
    volatile    uint32_t    server;     // 消费者当前读位置
    uint32_t    userBase;   //和user, server结合起来用
    uint32_t    serverBase;

    void*       buffers;    // 指向数据缓冲区的首地址
    uint32_t    frameCount; // 缓冲区的总大小，以frame为单位

    // Cache line boundary
    uint32_t    loopStart;  //设置播放的起点和终点
    uint32_t    loopEnd;
    int         loopCount;  // 循环播放的次数

    volatile    union {
        uint16_t    volume[2];  // 左右声道的音量
        uint32_t    volumeLR;
    }; 

    uint32_t    sampleRate;     // 采样率
    // NOTE: audio_track_cblk_t::frameSize is not equal to AudioTrack::frameSize() for
    // 8 bit PCM data: in this case,  mCblk->frameSize is based on a sample size of
    // 16 bit because data is converted to 16 bit before being stored in buffer
    uint32_t    frameSize;      // 一帧数据的大小
    uint8_t     channels;       // 声道数
    uint8_t     flowControlFlag; // underrun (out) or overrrun (in) indication
    uint8_t     out;        // out equals 1 for AudioTrack and 0 for AudioRecord
    uint8_t     forceReady;
    uint16_t    bufferTimeoutMs; // Maximum cumulated timeout before restarting audioflinger
    uint16_t    waitTimeMs;      // Cumulated wait time
    // Cache line boundary (32 bytes)

    uint32_t    stepUser(uint32_t frameCount);      // 更新写位置
    bool        stepServer(uint32_t frameCount);    // 更新读位置
    void*       buffer(uint32_t offset) const;      // 返回可写空间的开始地址
    uint32_t    framesAvailable();      // 还剩多少空间可写 
    uint32_t    framesAvailable_l();
    uint32_t    framesReady();          // 是否有数据可读

flowControlFlag
    输出: 对应underrun状态
            生产者提供数据的速度跟不上消费者消耗的速度
            如果没有新的数据时，消费者会循环使用缓冲中的数据
            用户会听到重复的声音
    输入: 对应overrun状态, 和underrun一样
            生产者提供数据的速度跟不上消费者消耗的速度

数据的push or pull
    push: 用户主动调用write写数据. MediaPlayerService一般使用这种方式
    pull: AudioTrackThread调用回调函数，以EVENT_MORE_DATA方式从用户那儿获取数据. ToneGenerator使用这种方式

/* Events used by AudioTrack callback function (audio_track_cblk_t). */
enum event_type {
    EVENT_MORE_DATA = 0,        // AudioTrack需要更多的数据
    EVENT_UNDERRUN = 1,         // 硬件处于低负荷状态
    EVENT_LOOP_END = 2,         // Sample loop end was reached; playback restarted from loop start if loop count was not 0.

    EVENT_MARKER = 3,           // Playback head is at the specified marker position (See setMarkerPosition()).
                                // 播放位置(消费者)已经超过了某个阈值
    EVENT_NEW_POS = 4,          // Playback head is at a new position (See setPositionUpdatePeriod()).
                                // 数据进度通知(如果500帧通知一次)
    EVENT_BUFFER_END = 5        // Playback head is at the end of the buffer.
                                // 数据全部被消耗
};

分析push模式
    目前已有的信息
        一片共享内存
        一个控制结构， 包括了跨进程的同步变量

/* 两种数据传递方式
    1. 使用者创建AudioTrack时传递一个callback
        使用者调用AudioTrack::start
        AudioTrack回调callback
        使用者在callback中写入数据

    2. 使用者将所有pcm数据拷贝到一个sharedBuffer中
        创建AudioTrack时传递该sharedBuffer到AudioTrack
        使用者调用AudioTrack::start

        使用者也可以提供一个callback
        AudioTrack播放完成后，调用callback, 传递EVENT_UNDERRUN给使用者。表示播放完成
*/

// 核心数据结构
    sp<IAudioTrack>         mAudioTrack;        // 指向AudioFlinger中创建的IAudioTrack接口
    sp<AudioTrackThread>    mAudioTrackThread;  // 在该线程中调用回调函数
    audio_track_cblk_t*     mCblk;              // 共享内存控制块区域
    sp<IMemory>             mCblkMemory;        // 共享内存区域

    /*
    mCblkMemory = track->getCblk();

    mCblk = static_cast<audio_track_cblk_t*>(cblk->pointer());
    mCblk->buffers = (char*)mCblk + sizeof(audio_track_cblk_t);
    共享内存结构
        1. audio_track_cblk_t <--- mCblk;
        2. Buffer
    */


    /* Create Buffer on the stack and pass it to 
        obtainBuffer() and releaseBuffer().  */ 
    // buffer结构体
    class Buffer {
        enum {
            MUTE    = 0x00000001       
        };
        uint32_t    flags;
        int         channelCount;      
        int         format;
        size_t      frameCount;
        size_t      size;
        union {
            void*       raw;
            short*      i16;
            int8_t*     i8;
    };


/* As a convenience, if a callback is supplied, a handler thread
 * is automatically created with the appropriate priority. This thread
 * invokes the callback when a new buffer becomes availlable or an underrun condition occurs.
 * Parameters:
 *
 * event:   type of event notified (see enum AudioTrack::event_type).
 * user:    Pointer to context for use by the callback receiver.
 * info:    Pointer to optional parameter according to event type:
 *          - EVENT_MORE_DATA: pointer to AudioTrack::Buffer struct. The callback must not write
 *          more bytes than indicated by 'size' field and update 'size' if less bytes are
 *          written.
 *          - EVENT_UNDERRUN: unused.
 *          - EVENT_LOOP_END: pointer to an int indicating the number of loops remaining.
 *          - EVENT_MARKER: pointer to an uin32_t containing the marker position in frames.
 *          - EVENT_NEW_POS: pointer to an uin32_t containing the new position in frames.
 *          - EVENT_BUFFER_END: unused.
 */

typedef void (*callback_t)(int event, void* user, void *info);

/* Creates an audio track and registers it with AudioFlinger.
 * Once created, the track needs to be started before it can be used.
 * Unspecified values are set to the audio hardware's current
 * values.
 *
 * Parameters:
 *
 * streamType:         Select the type of audio stream this track is attached to
 *                     (e.g. AudioSystem::MUSIC).
 * sampleRate:         Track sampling rate in Hz.
 * format:             Audio format (e.g AudioSystem::PCM_16_BIT for signed
 *                     16 bits per sample).
 * channels:           Channel mask: see AudioSystem::audio_channels.
 * frameCount:         Total size of track PCM buffer in frames. This defines the
 *                     latency of the track.
 * flags:              Reserved for future use.
 * cbf:                Callback function. If not null, this function is called periodically
 *                     to request new PCM data.
 * notificationFrames: The callback function is called each time notificationFrames PCM
 *                     frames have been comsumed from track input buffer.
 * user                Context for use by the callback receiver.
 * 
 */
AudioTrack( int streamType,
        uint32_t sampleRate  = 0,
        int format           = 0,
        int channels         = 0,
        int frameCount       = 0,
        uint32_t flags       = 0,
        callback_t cbf       = 0,
        void* user           = 0,
        int notificationFrames = 0);
    // 调用set

    

/* Creates an audio track and registers it with AudioFlinger. With this constructor,
 * The PCM data to be rendered by AudioTrack is passed in a shared memory buffer
 * identified by the argument sharedBuffer. This prototype is for static buffer playback.
 * PCM data must be present into memory before the AudioTrack is started.
 * The Write() and Flush() methods are not supported in this case.
 * It is recommented to pass a callback function to be notified of playback end by an
 * EVENT_UNDERRUN event.
 * 
 * 通过sharedBuffer一次性将所有数据写入
 */
AudioTrack( int streamType,
        uint32_t sampleRate = 0,
        int format          = 0,
        int channels        = 0,
        const sp<IMemory>& sharedBuffer = 0,
        uint32_t flags      = 0,
        callback_t cbf      = 0,
        void* user          = 0,
        int notificationFrames = 0);
    // 调用set

/* Initialize an uninitialized AudioTrack.
 */
status_t    set(int streamType      =-1,
        uint32_t sampleRate = 0,
        int format          = 0,
        int channels        = 0,
        int frameCount      = 0,
        uint32_t flags      = 0,
        callback_t cbf      = 0,
        void* user          = 0,
        int notificationFrames = 0,
        const sp<IMemory>& sharedBuffer = 0,
        bool threadCanCallJava = false);


    // 一般返回系统默认的output， mHardwareOutput
    audio_io_handle_t output = AudioSystem::getOutput((AudioSystem::stream_type)streamType,
                sampleRate, format, channels, (AudioSystem::output_flags)flags);

    // Ensure that buffer depth covers at least audio hardware latency
    // 计算buffer的大小, buffer的最小长度要等于一个硬件latency时长包括的数据量
        uint32_t minBufCount = afLatency*(afSampleRate/1000) / afFrameCount;
            // afLatency是硬件延时， 单位是毫秒
            // afSampleRate/1000是一毫秒的采样率
            // afLatency*(afSampleRate/1000)是在硬件延时的时长内， 采样了多少帧
            // afFrameCount是指硬件buffer能容纳多少帧

            // 所以minBufCount 是一个硬件延时内，输出的帧数和硬件buffer包括帧数大小的比值

        int minFrameCount = (afFrameCount*sampleRate*minBufCount)/afSampleRate;
            // (sampleRate == afSampleRate)
            // int minFrameCount = afLatency*(afSampleRate/1000);
            // 所以minFrameCount是一个硬件延时内输出的帧数


    // 如果使用callback方式获取数据
    if (sharedBuffer == 0) 
        // frameCount是每次写入AudioTrack的帧数?
        if (frameCount == 0) 
            frameCount = minFrameCount;

        // 输出完一定数量的帧后，向使用者报告
        if (notificationFrames == 0) 
            notificationFrames = frameCount/2;
    else // 使用sharedBuffer获取数据
        frameCount = sharedBuffer->size()/channelCount/sizeof(int16_t);

    // 初始化音量
    mVolume[LEFT] = 1.0f;
    mVolume[RIGHT] = 1.0f;

    // create the remote interface, IAudioTrack
    status_t status = createTrack(streamType, sampleRate, format, channelCount,
            frameCount, flags, sharedBuffer, output, true);

    // create a thread to handle audio callback, if it is set
    if (cbf != 0)
        mAudioTrackThread = new AudioTrackThread(*this, threadCanCallJava);

    // 初始化内部状态变量
    mStatus = NO_ERROR;
    mStreamType = streamType;
    mFormat = format;
    mChannels = channels;
    mChannelCount = channelCount;
    mSharedBuffer = sharedBuffer;
    mMuted = false;
    mActive = 0;
    mCbf = cbf;
    mNotificationFrames = notificationFrames;
    mRemainingFrames = notificationFrames;
    mUserData = user;   // 调用callback函数是传递的context

    // 硬件延时+控制块中包括数据的播放时间
    mLatency = afLatency + (1000*mFrameCount) / sampleRate;
    mLoopCount = 0;
    mMarkerPosition = 0;
    mMarkerReached = false;
    mNewPosition = 0;
    mUpdatePeriod = 0;
    mFlags = flags;


status_t AudioTrack::createTrack(      
        int streamType,
        uint32_t sampleRate,
        int format,
        int channelCount,
        int frameCount,
        uint32_t flags,
        const sp<IMemory>& sharedBuffer,
        audio_io_handle_t output)

    sp<IAudioTrack> track = audioFlinger->createTrack(getpid(),                      
            streamType, sampleRate, format, channelCount,
            frameCount, ((uint16_t)flags) << 16,
            sharedBuffer, output, &status);

    sp<IMemory> cblk = track->getCblk();

    mAudioTrack.clear();
    mAudioTrack = track;

    // pointer to a shared memory
    mCblkMemory.clear();
    mCblkMemory = cblk;

    // pointer to the control block at the head of the shared memory
    mCblk = static_cast<audio_track_cblk_t*>(cblk->pointer());
    mCblk->out = 1;

    // Update buffer size in case it has been limited by AudioFlinger during track creation
    mFrameCount = mCblk->frameCount;

    if (sharedBuffer == 0) {
        // 使用control block后面的buffer
        mCblk->buffers = (char*)mCblk + sizeof(audio_track_cblk_t);
    } else {
        // 使用用户提供的shared buffer
        mCblk->buffers = sharedBuffer->pointer();
        // Force buffer full condition as data is already present in shared memory
        mCblk->stepUser(mFrameCount);
    }

    // FIXME: 为什么
    mCblk->volumeLR = (int32_t(int16_t(mVolume[LEFT] * 0x1000)) << 16) 
        | int16_t(mVolume[RIGHT] * 0x1000);

    mCblk->bufferTimeoutMs = MAX_STARTUP_TIMEOUT_MS;
    mCblk->waitTimeMs = 0;



/* After it's created the track is not active. Call start() to
 * make it active. If set, the callback will start being called.  */
void AudioTrack::start()
    sp<AudioTrackThread> t = mAudioTrackThread;

    // 启动callback handle线程
    t->run("AudioTrackThread", THREAD_PRIORITY_AUDIO_CLIENT);

    // 启动AudioFlinger中的IAudioTrack
    status_t status = mAudioTrack->start();

/* Stop a track. If set, the callback will cease being called and
 * obtainBuffer returns STOPPED. Note that obtainBuffer() still works
 * and will fill up buffers until the pool is exhausted.  */
void AudioTrack::stop()
    // 停止AudioFlinger中的IAudioTrack
    mAudioTrack->stop();
    if (mSharedBuffer != 0) {
        flush(); ----> mAudioTrack->flush();

    // 停止callback handle线程
    sp<AudioTrackThread> t = mAudioTrackThread;
    t->requestExit();

/* flush a stopped track. All pending buffers are discarded.
 * This function has no effect if the track is not stoped.  */
void flush();
    mAudioTrack->flush();

/* Pause a track. If set, the callback will cease being called and
* obtainBuffer returns STOPPED. Note that obtainBuffer() still works
* and will fill up buffers until the pool is exhausted.  */
void        pause();
    mAudioTrack->pause();

/* mute or unmutes this track.
 * While mutted, the callback, if set, is still called.  */
void        mute(bool);
    mAudioTrack->mute(e);
    mMuted = e;

/* set volume for this track, mostly used for games' sound effects */
void AudioTrack::setVolume(float left, float right)
    mVolume[LEFT] = left;
    mVolume[RIGHT] = right;
    mCblk->volumeLR = (int32_t(int16_t(left * 0x1000)) << 16) | int16_t(right * 0x1000);

status_t AudioTrack::setSampleRate(int rate)
    int afSamplingRate;

    // 获取输出设备的采样率
    if (AudioSystem::getOutputSamplingRate(&afSamplingRate, mStreamType) != NO_ERROR) {
        return NO_INIT;

    // Resampler implementation limits input sampling rate to 2 x output sampling rate.
    if (rate <= 0 || rate > afSamplingRate*2 ) return BAD_VALUE;
    mCblk->sampleRate = rate;


/* Enables looping and sets the start and end points of looping.
 *
 * Parameters:
 *
 * loopStart:   loop start expressed as the number of PCM frames played since AudioTrack start.
 * loopEnd:     loop end expressed as the number of PCM frames played since AudioTrack start.
 * loopCount:   number of loops to execute. Calling setLoop() with loopCount == 0 cancels any pending or
 *          active loop. loopCount = -1 means infinite looping.
 *
 * For proper operation the following condition must be respected:
 *          (loopEnd-loopStart) <= framecount()
 */
status_t AudioTrack::setLoop(uint32_t loopStart, uint32_t loopEnd, int loopCount)
    audio_track_cblk_t* cblk = mCblk;

    // 设置控制块中的loop信息
    if (loopCount == 0) {
        cblk->loopStart = UINT_MAX;
        cblk->loopEnd = UINT_MAX;
        cblk->loopCount = 0;
        mLoopCount = 0;
        return NO_ERROR;

    cblk->loopStart = loopStart;
    cblk->loopEnd = loopEnd;
    cblk->loopCount = loopCount;
    mLoopCount = loopCount;
    return NO_ERROR;

/* Sets marker position. When playback reaches the number of frames specified, a callback with event 
 * type EVENT_MARKER is called. Calling setMarkerPosition with marker == 0 cancels marker notification 
 * callback. 
 * If the AudioTrack has been opened with no callback function associated, the operation will fail.
 *
 * Parameters:
 *
 * marker:   marker position expressed in frames.
 *
 * Returned status (from utils/Errors.h) can be:
 *  - NO_ERROR: successful operation
 *  - INVALID_OPERATION: the AudioTrack has no callback installed.
 */
status_t    setMarkerPosition(uint32_t marker);
status_t    getMarkerPosition(uint32_t *marker);

/* Sets position update period. Every time the number of frames specified has been played, 
 * a callback with event type EVENT_NEW_POS is called. 
 * Calling setPositionUpdatePeriod with updatePeriod == 0 cancels new position notification 
 * callback. 
 * If the AudioTrack has been opened with no callback function associated, the operation will fail.
 *
 * Parameters:
 *
 * updatePeriod:  position update notification period expressed in frames.
 *
 * Returned status (from utils/Errors.h) can be:
 *  - NO_ERROR: successful operation
 *  - INVALID_OPERATION: the AudioTrack has no callback installed.
 */
status_t    setPositionUpdatePeriod(uint32_t updatePeriod);
status_t    getPositionUpdatePeriod(uint32_t *updatePeriod);


/* Sets playback head position within AudioTrack buffer. The new position is specified
 * in number of frames. 
 * This method must be called with the AudioTrack in paused or stopped state.
 * Note that the actual position set is <position> modulo the AudioTrack buffer size in frames. 
 * Therefore using this method makes sense only when playing a "static" audio buffer 
 * as opposed to streaming.
 * The getPosition() method on the other hand returns the total number of frames played since
 * playback start.
 *
 * Parameters:
 *
 * position:  New playback head position within AudioTrack buffer.
 *
 * Returned status (from utils/Errors.h) can be:
 *  - NO_ERROR: successful operation
 *  - INVALID_OPERATION: the AudioTrack is not stopped.
 *  - BAD_VALUE: The specified position is beyond the number of frames present in AudioTrack buffer 
 */
status_t    setPosition(uint32_t position);
status_t    getPosition(uint32_t *position);

status_t AudioTrack::setPosition(uint32_t position)
    if (!stopped()) return INVALID_OPERATION;
    if (position > mCblk->user) return BAD_VALUE;

    // 将消费者指针指向position
    mCblk->server = position;
    mCblk->forceReady = 1;

/* Forces AudioTrack buffer full condition. When playing a static buffer, this method avoids 
 * rewriting the buffer before restarting playback after a stop.
 * This method must be called with the AudioTrack in paused or stopped state.
 *
 * Returned status (from utils/Errors.h) can be:
 *  - NO_ERROR: successful operation
 *  - INVALID_OPERATION: the AudioTrack is not stopped.
 */
status_t AudioTrack::reload()
{
    if (!stopped()) return INVALID_OPERATION;
    flush();
    mCblk->stepUser(mFrameCount);
    return NO_ERROR;
}

/* returns a handle on the audio output used by this AudioTrack.  */
audio_io_handle_t    getOutput();

/* As a convenience we provide a write() interface to the audio buffer.
 * This is implemented on top of lockBuffer/unlockBuffer. For best performance */
// 将buffer的数据写入到AudioTrack中
// buffer, 输出的数据
// userSize, 输出数据大小
ssize_t AudioTrack::write(const void* buffer, size_t userSize) 

    // write不能用于shared buffer模式
    if (mSharedBuffer != 0) return INVALID_OPERATION;

    ssize_t written = 0;
    const int8_t *src = (const int8_t *)buffer; 
    Buffer audioBuffer;   

    // 不停的从控制块中获取buffer
    // 将使用者数据写入buffer, 直到所有的data都写入到clb的buffer中
    do {
        // 计算出数据的frame count
        audioBuffer.frameCount = userSize/frameSize();

        // 从控制块中获取buffer
        // -1 表示block
        status_t err = obtainBuffer(&audioBuffer, -1);
        if (err < 0) {
            // out of buffers, return #bytes written
            if (err == status_t(NO_MORE_BUFFERS))
                break;
            return ssize_t(err);

        // 获取到了buffer
        size_t toWrite;

        // 如果是pcm 8bit, 需要做一些转换
        if (mFormat == AudioSystem::PCM_8_BIT && 
                !(mFlags & AudioSystem::OUTPUT_FLAG_DIRECT)) {
            // FIXME: Jiang Rui, What is the expansion
            // Divide capacity by 2 to take expansion into account
            toWrite = audioBuffer.size>>1;
            // 8 to 16 bit conversion
            int count = toWrite;
            int16_t *dst = (int16_t *)(audioBuffer.i8);
            while(count--) {
                // FIXME: Jiang Rui, What is the expansion
                *dst++ = (int16_t)(*src++^0x80) << 8;
            }
        } else {
            // 如果是pcm 16bit， 直接拷贝
            toWrite = audioBuffer.size;
            memcpy(audioBuffer.i8, src, toWrite);
            src += toWrite;
        }
        userSize -= toWrite;
        written += toWrite;

        releaseBuffer(&audioBuffer);
    } while (userSize);

    return written;

/* obtains a buffer of "frameCount" frames. The buffer must be
 * filled entirely. If the track is stopped, obtainBuffer() returns
 * STOPPED instead of NO_ERROR as long as there are buffers availlable,
 * at which point NO_MORE_BUFFERS is returned.
 * Buffers will be returned until the pool (buffercount())
 * is exhausted, at which point obtainBuffer() will either block
 * or return WOULD_BLOCK depending on the value of the "blocking"
 * parameter.
 */
enum { NO_MORE_BUFFERS = 0x80000001, STOPPED = 1 };

// write之前
// 1. 如果没有共享内存空间可写，则等待在一个条件变量上
// 2. 等到可写的空间后， 获取共享缓存的位置, 大小, 并填写如buffer
status_t AudioTrack::obtainBuffer(Buffer* audioBuffer, int32_t waitCount)

    audio_track_cblk_t* cblk = mCblk;
    uint32_t waitTimeMs = (waitCount < 0) ?       // waitCount = -1
        cblk->bufferTimeoutMs : WAIT_PERIOD_MS;

    uint32_t framesReq = audioBuffer->frameCount; // 使用者希望获取buffer的大小
    audioBuffer->frameCount  = 0;  
    audioBuffer->size = 0;

    uint32_t framesAvail = cblk->framesAvailable(); // 获取控制块中可写的buffer帧数

    while (framesAvail == 0) 
        // 如果没有空间可写，则等待
        result = cblk->cv.waitRelative(cblk->lock, milliseconds(waitTimeMs));

    cblk->waitTimeMs = 0;

    // 有共享内存空间可写
    if (framesReq > framesAvail) {
        framesReq = framesAvail;

    // 生产者写的开始地址
    uint32_t u = cblk->user;

    // 调整framesReq的大小
    // FIXME: 要搞清cblk(user, userBase, frameCount)的意义
    uint32_t bufferEnd = cblk->userBase + cblk->frameCount;
    if (u + framesReq > bufferEnd) {
        framesReq = bufferEnd - u;
    }

    audioBuffer->flags = mMuted ? Buffer::MUTE : 0;
    audioBuffer->channelCount = mChannelCount;
    audioBuffer->frameCount = framesReq;
    audioBuffer->size = framesReq * cblk->frameSize;
    // 如果是pcm格式(8bit or 16bit), 都转化为16bit
    if (AudioSystem::isLinearPCM(mFormat)) {
        audioBuffer->format = AudioSystem::PCM_16_BIT;
    } else {
        audioBuffer->format = mFormat;
    }
    audioBuffer->raw = (int8_t *)cblk->buffer(u);
    active = mActive;
    return active ? status_t(NO_ERROR) : status_t(STOPPED);

// write之后
// 将生产者的指针向前移动
void AudioTrack::releaseBuffer(Buffer* audioBuffer)
    audio_track_cblk_t* cblk = mCblk;
    cblk->stepUser(audioBuffer->frameCount);


// 被callback thread调用
bool AudioTrack::processAudioBuffer(const sp<AudioTrackThread>& thread) {
    Buffer audioBuffer;
    uint32_t frames;
    size_t writtenSize;

    // Manage underrun callback    
    // 消费者已经读取了所有frame
    if (mActive && (mCblk->framesReady() == 0)) {
        LOGV("Underrun user: %x, server: %x, flowControlFlag %d", 
                mCblk->user, mCblk->server, mCblk->flowControlFlag);
        if (mCblk->flowControlFlag == 0) { 
            // 通知生产者， underrun情况发生
            mCbf(EVENT_UNDERRUN, mUserData, 0);
            if (mCblk->server == mCblk->frameCount) {
                // 通知生产者， 所有数据已经成功输出
                mCbf(EVENT_BUFFER_END, mUserData, 0);                                                                                                                           
            mCblk->flowControlFlag = 1;
            if (mSharedBuffer != 0) return false; 

    // 通知使用者，loop结束
    while (mLoopCount > mCblk->loopCount) {
        mLoopCount--;
        mCbf(EVENT_LOOP_END, mUserData, (void *)&loopCount);


    // Manage marker callback
    if (!mMarkerReached && (mMarkerPosition > 0)) {
        if (mCblk->server >= mMarkerPosition) {
            mCbf(EVENT_MARKER, mUserData, (void *)&mMarkerPosition);
            mMarkerReached = true;

    // Manage new position callback
    if (mUpdatePeriod > 0) {
        while (mCblk->server >= mNewPosition) {
            mCbf(EVENT_NEW_POS, mUserData, (void *)&mNewPosition);
            mNewPosition += mUpdatePeriod;

    // If Shared buffer is used, no data is requested from client.
    if (mSharedBuffer != 0) {
        frames = 0;
    } else {
        frames = mRemainingFrames;

    do {
        audioBuffer.frameCount = frames;
        // 1 means wait time to WAIT_PERIOD_MS 
        status_t err = obtainBuffer(&audioBuffer, 1);

        // Divide buffer size by 2 to take into account the expansion
        // due to 8 to 16 bit conversion: the callback must fill only half
        // of the destination buffer
        if (mFormat == AudioSystem::PCM_8_BIT && 
                !(mFlags & AudioSystem::OUTPUT_FLAG_DIRECT)) {
            audioBuffer.size >>= 1;

        // 调用回调函数，向使用者请求数据。使用者会将数据写入到申请的
        // buffer中
        size_t reqSize = audioBuffer.size;
        mCbf(EVENT_MORE_DATA, mUserData, &audioBuffer);
        writtenSize = audioBuffer.size;


        if (writtenSize > reqSize) writtenSize = reqSize;

        // 8 to 16 bit conversion
        if (mFormat == AudioSystem::PCM_8_BIT && 
                !(mFlags & AudioSystem::OUTPUT_FLAG_DIRECT)) {
            const int8_t *src = audioBuffer.i8 + writtenSize-1;
            int count = writtenSize;
            int16_t *dst = audioBuffer.i16 + writtenSize-1;
            while(count--) {
                *dst-- = (int16_t)(*src--^0x80) << 8;
            }
            writtenSize <<= 1;
        }

        audioBuffer.size = writtenSize;
        // NOTE: mCblk->frameSize is not equal to AudioTrack::frameSize() for
        // 8 bit PCM data: in this case,  mCblk->frameSize is based on a sampel size of
        // 16 bit.
        audioBuffer.frameCount = writtenSize/mCblk->frameSize;

        frames -= audioBuffer.frameCount;

        releaseBuffer(&audioBuffer);
    } while (frames);

    if (frames == 0) {
        mRemainingFrames = mNotificationFrames;
    } else {
        mRemainingFrames = frames;
    }
    return true;



/* a small internal class to handle the callback */
class AudioTrackThread : public Thread {
public:
    AudioTrackThread(AudioTrack& receiver, bool bCanCallJava = false);
private:
    friend class AudioTrack;
    virtual bool        threadLoop();
    virtual status_t    readyToRun();
    virtual void        onFirstRef();
    AudioTrack& mReceiver;
    Mutex       mLock;
};


AudioTrack::~AudioTrack()
    // Make sure that callback function exits in the case where
    // it is looping on buffer full condition in obtainBuffer().
    // Otherwise the callback thread will never exit.
    stop();
    if (mAudioTrackThread != 0) {  
        mAudioTrackThread->requestExitAndWait();
        mAudioTrackThread.clear();     
    }
    mAudioTrack.clear();
    IPCThreadState::self()->flushCommands();


// 1. 停止audioflinger端播放
// 2. 清空循环播放设置
// 3. 请求退出AudioTrackThread
void AudioTrack::stop() 
    sp<AudioTrackThread> t = mAudioTrackThread;

    if (android_atomic_and(~1, &mActive) == 1) {
        mCblk->cv.signal();
        // 停止audioflinger端播放
        mAudioTrack->stop();
        // 清空循环播放设置
        setLoop(0, 0, 0);
        if (mSharedBuffer != 0) {      
            flush();
        }
        if (t != 0) {
            // 请求退出AudioTrackThread
            t->requestExit();


