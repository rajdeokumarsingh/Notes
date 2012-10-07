
// 1. create an audio HAL interface
// 2. set hardware status
// 3. do hardware initCheck
// 4. set normal mode
// 5. set master volume to 1.0
AudioFlinger::AudioFlinger()  : BnAudioFlinger(), mAudioHardware(0), mMasterVolume(1.0f), 
    mMasterMute(false), mNextUniqueId(1),  mFmOn(false)                                          

    mHardwareStatus = AUDIO_HW_IDLE;

    // create an audio HAL interface
    mAudioHardware = AudioHardwareInterface::create();

    mHardwareStatus = AUDIO_HW_INIT;

    if (mAudioHardware->initCheck() == NO_ERROR) {
        // open 16-bit output stream for s/w mixer
        mMode = AudioSystem::MODE_NORMAL;
        setMode(mMode);

        setMasterVolume(1.0f);
        setMasterMute(false);


// 1. close all input and output device
// 2. delete the hardware structure
AudioFlinger::~AudioFlinger()
    while (!mRecordThreads.isEmpty()) {
        // closeInput() will remove first entry from mRecordThreads
        closeInput(mRecordThreads.keyAt(0));

    while (!mPlaybackThreads.isEmpty()) {
        // closeOutput() will remove first entry from mPlaybackThreads
        closeOutput(mPlaybackThreads.keyAt(0));

    if (mAudioHardware) {
        delete mAudioHardware;

////////////////////////////////////////////////////////////////////////////////
// IAudioFlinger interface
////////////////////////////////////////////////////////////////////////////////

// 1. find a PlaybackThread for the output device
// 2. create a session id
// 3. create a Track by PlaybackThread::createTrack_l()
// 4. Create a TrackHandle for the Track
sp<IAudioTrack> AudioFlinger::createTrack( pid_t pid, int streamType, uint32_t sampleRate, 
        int format, int channelCount, int frameCount, uint32_t flags, 
        const sp<IMemory>& sharedBuffer, int output, int *sessionId, status_t *status) 

    PlaybackThread *thread = checkPlaybackThread_l(output);

    // create a session id
    lSessionId = nextUniqueId();

    track = thread->createTrack_l(client, streamType, sampleRate, format,
            channelCount, frameCount, sharedBuffer, lSessionId, &lStatus); 

    trackHandle = new TrackHandle(track);

// get the sample rate of the output device
uint32_t AudioFlinger::sampleRate(int output) const
    PlaybackThread *thread = checkPlaybackThread_l(output);
    return thread->sampleRate();

// get the channel count of the output device, like AudioFlinger::sampleRate()
int AudioFlinger::channelCount(int output) const
    PlaybackThread *thread = checkPlaybackThread_l(output);
    return thread->channelCount();

// get the format of the output device, like AudioFlinger::sampleRate()
int AudioFlinger::format(int output) const

// get the frame count of the output device, like AudioFlinger::sampleRate()
size_t AudioFlinger::frameCount(int output) const

// get the latency of the output device, like AudioFlinger::sampleRate()
uint32_t AudioFlinger::latency(int output) const


// 1. mAudioHardware->setMasterVolume(value)
// 2. set master volume for all mPlaybackThreads
status_t AudioFlinger::setMasterVolume(float value)
    mHardwareStatus = AUDIO_HW_SET_MASTER_VOLUME;
    if (mAudioHardware->setMasterVolume(value) == NO_ERROR) {
        value = 1.0f;
    }
    mHardwareStatus = AUDIO_HW_IDLE;

    mMasterVolume = value;
    for (uint32_t i = 0; i < mPlaybackThreads.size(); i++)
        mPlaybackThreads.valueAt(i)->setMasterVolume(value);

// 1. mAudioHardware->setMode(mode)
// 2. set mode for all mPlaybackThreads
status_t AudioFlinger::setMode(int mode)
    mHardwareStatus = AUDIO_HW_SET_MODE;
    ret = mAudioHardware->setMode(mode);
    mHardwareStatus = AUDIO_HW_IDLE;

    for (uint32_t i = 0; i < mPlaybackThreads.size(); i++)
        mPlaybackThreads.valueAt(i)->setMode(mode);


status_t AudioFlinger::setMicMute(bool state)
    mHardwareStatus = AUDIO_HW_SET_MIC_MUTE;
    status_t ret = mAudioHardware->setMicMute(state);
    mHardwareStatus = AUDIO_HW_IDLE;

bool AudioFlinger::getMicMute() const
    mHardwareStatus = AUDIO_HW_GET_MIC_MUTE;
    mAudioHardware->getMicMute(&state);
    mHardwareStatus = AUDIO_HW_IDLE;

// setMasterMute for all mPlaybackThreads
status_t AudioFlinger::setMasterMute(bool muted)

// 1. set the stream volume for the given output device or
// 2. set the stream volume for all mPlaybackThreads, if device is null
status_t AudioFlinger::setStreamVolume(int stream, float value, int output)
    thread = checkPlaybackThread_l(output);
    mStreamTypes[stream].volume = value;
    thread->setStreamVolume(stream, value);

// 1. setStreamMute for all mPlaybackThreads
status_t AudioFlinger::setStreamMute(int stream, bool muted)

// 1. get streamVolume for the given output device or
// 2. from mStreamTypes[stream].volume cache, if can not find the given thread
float AudioFlinger::streamVolume(int stream, int output) const
    PlaybackThread *thread = checkPlaybackThread_l(output);
    volume = thread->streamVolume(stream);


// 1. check if there is active stream in all mPlaybackThreads
bool AudioFlinger::isStreamActive(int stream) const
    for (uint32_t i = 0; i < mPlaybackThreads.size(); i++) {
        if (mPlaybackThreads.valueAt(i)->isStreamActive(stream)) {
            return true;


// 1. setParameters for the global mAudioHardware if ioHandle is zero or
// 2. setParameters for the the given input/output device(ioHandle)
status_t AudioFlinger::setParameters(int ioHandle, const String8& keyValuePairs)
    // ioHandle == 0 means the parameters are global to the audio hardware interface
    if (ioHandle == 0) {
        mHardwareStatus = AUDIO_SET_PARAMETER;
        result = mAudioHardware->setParameters(keyValuePairs);
        mHardwareStatus = AUDIO_HW_IDLE;
        return result;

    // ioHandle != 0
    thread = checkPlaybackThread_l(ioHandle);
    if (thread == NULL) {
        thread = checkRecordThread_l(ioHandle);

    result = thread->setParameters(keyValuePairs);

// 1. getParameters for the global mAudioHardware if ioHandle is zero or
// 2. getParameters for the the given input/output device(ioHandle)
String8 AudioFlinger::getParameters(int ioHandle, const String8& keys)


size_t AudioFlinger::getInputBufferSize(uint32_t sampleRate, int format, int channelCount)
    return mAudioHardware->getInputBufferSize(sampleRate, format, channelCount);

// 1. getInputFramesLost for the given ioHandle
unsigned int AudioFlinger::getInputFramesLost(int ioHandle)
    RecordThread *recordThread = checkRecordThread_l(ioHandle);
    return recordThread->getInputFramesLost();

// 1. setVoiceVolume for mAudioHardware
status_t AudioFlinger::setVoiceVolume(float value)
    mHardwareStatus = AUDIO_SET_VOICE_VOLUME;
    status_t ret = mAudioHardware->setVoiceVolume(value);
    mHardwareStatus = AUDIO_HW_IDLE;

// 1. getRenderPosition for the given output device
status_t AudioFlinger::getRenderPosition(uint32_t *halFrames, uint32_t *dspFrames, int output)
    PlaybackThread *playbackThread = checkPlaybackThread_l(output);
    return playbackThread->getRenderPosition(halFrames, dspFrames);

// 1. setFmVolume for mAudioHardware
status_t AudioFlinger::setFmVolume(float value)

// 1. register client callback to AudioFlinger
// 2. send config events for all mPlaybackThreads and mRecordThreads
void AudioFlinger::registerClient(const sp<IAudioFlingerClient>& client)
    int pid = IPCThreadState::self()->getCallingPid();
    if (mNotificationClients.indexOfKey(pid) < 0) {
        sp<NotificationClient> notificationClient = new NotificationClient(this, client, pid);
        mNotificationClients.add(pid, notificationClient);

        sp<IBinder> binder = client->asBinder();
        binder->linkToDeath(notificationClient);

        for (size_t i = 0; i < mPlaybackThreads.size(); i++) {
            mPlaybackThreads.valueAt(i)->sendConfigEvent(AudioSystem::OUTPUT_OPENED);

        for (size_t i = 0; i < mRecordThreads.size(); i++) {
            mRecordThreads.valueAt(i)->sendConfigEvent(AudioSystem::INPUT_OPENED);


void AudioFlinger::removeNotificationClient(pid_t pid)
    mNotificationClients.removeItem(pid);

// 1. callback to all client's ioConfigChanged()
void AudioFlinger::audioConfigChanged_l(int event, int ioHandle, void *param2)
    size_t size = mNotificationClients.size();
    for (size_t i = 0; i < size; i++) {
        mNotificationClients.valueAt(i)->client()->ioConfigChanged(event, ioHandle, param2);

void AudioFlinger::removeClient_l(pid_t pid)
    mClients.removeItem(pid);



