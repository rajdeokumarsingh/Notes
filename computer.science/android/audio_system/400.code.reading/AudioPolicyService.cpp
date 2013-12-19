// [finished 2011-09-09]


/* class AudioPolicyService: 
    public BnAudioPolicyService, 
    public AudioPolicyClientInterface
    */

/* impelemented two AudioCommandThread to
    play tone
        using ToneGenerator
    send audio config commands
        encapsulating AudioSystem interfaces

    { START_TONE, STOP_TONE, SET_VOLUME, SET_PARAMETERS, 
        SET_VOICE_VOLUME, SET_FM_VOLUME };
*/

/* encapsulate AudioPolicyManager(AudioPolicyManagerBase)
    connection control:
        setDeviceConnectionState getDeviceConnectionState
    
    input/output control:
        getOutput startOutput stopOutput releaseOutput
        getInput startInput stopInput releaseInput

    volume control:
        initStreamVolume setStreamVolumeIndex getStreamVolumeIndex

    getOutputForEffect registerEffect unregisterEffect

    setPhoneState setRingerMode
    setForceUse getForceUse getStrategyForStream
*/

/*
encapsulate AudioFlinger
    input/output control:
        openOutput openDuplicateOutput closeOutput 
            suspendOutput restoreOutput setStreamOutput 
        openInput closeInput 
    moveEffects
*/

class AudioPolicyService: public BnAudioPolicyService, public AudioPolicyClientInterface, public IBinder::DeathRecipient 

    /* inner class of AudioPolicyService, used for: tone playback and 
        send audio config commands to audio flinger */
    class AudioCommandThread : public Thread 
        // commands for tone AudioCommand
        enum { START_TONE, STOP_TONE, SET_VOLUME, SET_PARAMETERS, SET_VOICE_VOLUME, SET_FM_VOLUME };

        void        startToneCommand(int type = 0, int stream = 0);
        void        stopToneCommand();
        status_t    volumeCommand(int stream, float volume, int output, int delayMs = 0);
        status_t    parametersCommand(int ioHandle, const String8& keyValuePairs, int delayMs = 0);
        status_t    voiceVolumeCommand(float volume, int delayMs = 0);
        status_t    fmVolumeCommand(float volume, int delayMs = 0);

        // inner class of AudioCommandThread
        class AudioCommand
            int mCommand;   // START_TONE, STOP_TONE ...
            nsecs_t mTime;  // time stamp
            Condition mCond; // condition for status return
            status_t mStatus; // command status
            bool mWaitStatus; // true if caller is waiting for status
            void *mParam;     // command parameter (ToneData, VolumeData, ParametersData)

        class ToneData
            int mType;      // tone type (START_TONE only)
            int mStream;    // stream type (START_TONE only)

        class VolumeData
            int mStream;
            float mVolume;
            int mIO;

        class ParametersData
            int mIO;
            String8 mKeyValuePairs;

        class VoiceVolumeData
            float mVolume;

        Vector <AudioCommand *> mAudioCommands; // list of pending commands
        ToneGenerator *mpToneGenerator;     // the tone generator
        AudioCommand mLastCommand;          // last processed command (used by dump)

        AudioPolicyInterface* mpPolicyManager; // AudioPolicyManagerBase
        sp <AudioCommandThread> mAudioCommandThread;    // audio commands thread
        sp <AudioCommandThread> mTonePlaybackThread;     // tone playback thread


AudioPolicyService::AudioPolicyService()
    // start tone playback thread  
    mTonePlaybackThread = new AudioCommandThread(String8(""));
    // start audio commands thread 
    mAudioCommandThread = new AudioCommandThread(String8("ApmCommandThread"));  

    // The HAL implements createAudioPolicyManager
    // AudioPolicyManagerBase implement almost 90% of the AudioPolicyManager
    mpPolicyManager = createAudioPolicyManager(this);
        // mpPolicyManager = new AudioPolicyManagerBase(this);


////////////////////////////////////////////////////////////////////////////////
// AudioPolicyManagerBase
////////////////////////////////////////////////////////////////////////////////
status_t AudioPolicyService::setDeviceConnectionState(AudioSystem::audio_devices device, 
        AudioSystem::device_connection_state state, const char *device_address)
    return mpPolicyManager->setDeviceConnectionState(device, state, device_address);

AudioSystem::device_connection_state AudioPolicyService::getDeviceConnectionState(
        AudioSystem::audio_devices device, const char *device_address)

status_t AudioPolicyService::setPhoneState(int state)
    AudioSystem::setMode(state);
    mpPolicyManager->setPhoneState(state);

status_t AudioPolicyService::setRingerMode(uint32_t mode, uint32_t mask)
status_t AudioPolicyService::setForceUse(AudioSystem::force_use usage, AudioSystem::forced_config config)
AudioSystem::forced_config AudioPolicyService::getForceUse(AudioSystem::force_use usage)
audio_io_handle_t AudioPolicyService::getOutput(AudioSystem::stream_type stream, uint32_t samplingRate, uint32_t format, uint32_t channels, AudioSystem::output_flags flags)
status_t AudioPolicyService::startOutput(audio_io_handle_t output, AudioSystem::stream_type stream, int session)
status_t AudioPolicyService::stopOutput(audio_io_handle_t output, AudioSystem::stream_type stream, int session)
void AudioPolicyService::releaseOutput(audio_io_handle_t output)
audio_io_handle_t AudioPolicyService::getInput(int inputSource, uint32_t samplingRate, uint32_t format, uint32_t channels, AudioSystem::audio_in_acoustics acoustics)
status_t AudioPolicyService::startInput(audio_io_handle_t input)
status_t AudioPolicyService::stopInput(audio_io_handle_t input)
void AudioPolicyService::releaseInput(audio_io_handle_t input)
status_t AudioPolicyService::initStreamVolume(AudioSystem::stream_type stream, int indexMin, int indexMax)
status_t AudioPolicyService::setStreamVolumeIndex(AudioSystem::stream_type stream, int index)
status_t AudioPolicyService::getStreamVolumeIndex(AudioSystem::stream_type stream, int *index)
uint32_t AudioPolicyService::getStrategyForStream(AudioSystem::stream_type stream)
audio_io_handle_t AudioPolicyService::getOutputForEffect(effect_descriptor_t *desc)
tatus_t AudioPolicyService::registerEffect(effect_descriptor_t *desc, audio_io_handle_t output, uint32_t strategy, int session, int id)
status_t AudioPolicyService::unregisterEffect(int id)

// ----------------------------------------------------------------------------
// AudioPolicyClientInterface implementation
// encapsulate the audio flinger interface
// ----------------------------------------------------------------------------
audio_io_handle_t AudioPolicyService::openOutput(uint32_t *pDevices, uint32_t *pSamplingRate, 
        uint32_t *pFormat, uint32_t *pChannels, uint32_t *pLatencyMs, AudioSystem::output_flags flags)
    return af->openOutput(pDevices, pSamplingRate, (uint32_t *)pFormat, pChannels, pLatencyMs, flags);

audio_io_handle_t AudioPolicyService::openDuplicateOutput(audio_io_handle_t output1, audio_io_handle_t output2)
status_t AudioPolicyService::closeOutput(audio_io_handle_t output)
status_t AudioPolicyService::suspendOutput(audio_io_handle_t output)
status_t AudioPolicyService::restoreOutput(audio_io_handle_t output)
audio_io_handle_t AudioPolicyService::openInput(uint32_t *pDevices, uint32_t *pSamplingRate, 
        uint32_t *pFormat, uint32_t *pChannels, uint32_t acoustics)
status_t AudioPolicyService::closeInput(audio_io_handle_t input)
status_t AudioPolicyService::setStreamOutput(AudioSystem::stream_type stream, audio_io_handle_t output)
status_t AudioPolicyService::moveEffects(int session, audio_io_handle_t srcOutput, audio_io_handle_t dstOutput)




////////////////////////////////////////////////////////////////////////////////
// audio command thread
////////////////////////////////////////////////////////////////////////////////
status_t AudioPolicyService::setStreamVolume(AudioSystem::stream_type stream, float volume, audio_io_handle_t output, int delayMs)
    return mAudioCommandThread->volumeCommand((int)stream, volume, (int)output, delayMs);

void AudioPolicyService::setParameters(audio_io_handle_t ioHandle, const String8& keyValuePairs, int delayMs)
    mAudioCommandThread->parametersCommand((int)ioHandle, keyValuePairs, delayMs);

status_t AudioPolicyService::startTone(ToneGenerator::tone_type tone, AudioSystem::stream_type stream)
    mTonePlaybackThread->startToneCommand(tone, stream);

status_t AudioPolicyService::stopTone()
    mTonePlaybackThread->stopToneCommand();

status_t AudioPolicyService::setVoiceVolume(float volume, int delayMs)
    return mAudioCommandThread->voiceVolumeCommand(volume, delayMs);

status_t AudioPolicyService::setFmVolume(float volume, int delayMs)
    return mAudioCommandThread->fmVolumeCommand(volume, delayMs);

bool AudioPolicyService::AudioCommandThread::threadLoop()
    while (!exitPending())
        while(!mAudioCommands.isEmpty()) 
            nsecs_t curTime = systemTime();
            // commands are sorted by increasing time stamp: execute them from index 0 and up
            if (mAudioCommands[0]->mTime <= curTime)
            switch (command->mCommand) 
            case START_TONE: 
                // play tone by ToneGenerator
            case STOP_TONE:
                // stop tone
            case SET_VOLUME: 
                command->mStatus = AudioSystem::setStreamVolume(data->mStream, data->mVolume, data->mIO);
                if (command->mWaitStatus)
                    command->mCond.signal(); // wake up the calling thread to read command->mStatus
                    mWaitWorkCV.wait(mLock); // the calling thread has read mStatus
                delete data;
            case SET_PARAMETERS: 
                command->mStatus = AudioSystem::setParameters(data->mIO, data->mKeyValuePairs);
                if (command->mWaitStatus) {
                    command->mCond.signal();
                    mWaitWorkCV.wait(mLock);
                delete data;
            case SET_VOICE_VOLUME: {
               command->mStatus = AudioSystem::setVoiceVolume(data->mVolume);
               if (command->mWaitStatus) {
                   command->mCond.signal();
                   mWaitWorkCV.wait(mLock);
               delete data;
           case SET_FM_VOLUME: {
               command->mStatus = AudioSystem::setFmVolume(data->mVolume);
               if (command->mWaitStatus) {
                   command->mCond.signal();
                   mWaitWorkCV.wait(mLock);
                delete data;

        mWaitWorkCV.waitRelative(mLock, waitTime);

void AudioPolicyService::AudioCommandThread::startToneCommand(int type, int stream)
    AudioCommand *command = new AudioCommand();
    command->mCommand = START_TONE;
    ToneData *data = new ToneData();
    data->mType = type;
    data->mStream = stream;
    command->mParam = (void *)data;
    command->mWaitStatus = false;

    Mutex::Autolock _l(mLock);
    insertCommand_l(command);
    mWaitWorkCV.signal();

// similar with startToneCommand
void AudioPolicyService::AudioCommandThread::stopToneCommand()


status_t AudioPolicyService::AudioCommandThread::volumeCommand(
    int stream, float volume, int output, int delayMs)

    AudioCommand *command = new AudioCommand();
    command->mCommand = SET_VOLUME;
    VolumeData *data = new VolumeData();
    data->mStream = stream;
    data->mVolume = volume;
    data->mIO = output;
    command->mParam = data;
    if (delayMs == 0) {
        command->mWaitStatus = true;
    else {
        command->mWaitStatus = false;

    Mutex::Autolock _l(mLock);
    insertCommand_l(command, delayMs);
    LOGV("AudioCommandThread() adding set volume stream %d, volume %f, output %d",
    stream, volume, output);
    mWaitWorkCV.signal();
    if (command->mWaitStatus)
        command->mCond.wait(mLock);
        status =  command->mStatus;
        mWaitWorkCV.signal();
    return status;

status_t AudioPolicyService::AudioCommandThread::parametersCommand(int ioHandle, const String8& keyValuePairs, int delayMs)

status_t AudioPolicyService::AudioCommandThread::fmVolumeCommand(float volume, int delayMs)
    AudioCommand *command = new AudioCommand();
    command->mCommand = SET_FM_VOLUME;
    FmVolumeData *data = new FmVolumeData();
    data->mVolume = volume;
    command->mParam = data;
    if (delayMs == 0) {
        command->mWaitStatus = true;
    else {
        command->mWaitStatus = false;

    Mutex::Autolock _l(mLock);
    insertCommand_l(command, delayMs);
    mWaitWorkCV.signal();
    if (command->mWaitStatus) {
        command->mCond.wait(mLock);
        status =  command->mStatus;
        mWaitWorkCV.signal();
    return status;

// insertCommand_l() must be called with mLock held
void AudioPolicyService::AudioCommandThread::insertCommand_l(AudioCommand *command, int delayMs)
    // check same pending commands with later time stamps and eliminate them
    // remove filtered commands
    mAudioCommands.insertAt(command, i + 1);







