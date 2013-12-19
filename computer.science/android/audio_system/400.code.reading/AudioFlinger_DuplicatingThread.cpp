
// send the stream to more than one hardware output
AudioFlinger::DuplicatingThread::DuplicatingThread(const sp<AudioFlinger>& audioFlinger, AudioFlinger::MixerThread* mainThread, int id)
:   MixerThread(audioFlinger, mainThread->getOutput(), id, mainThread->device()), mWaitTimeMs(UINT_MAX)
    mType = PlaybackThread::DUPLICATING;
    addOutputTrack(mainThread);

// 1. destroy all tracks
AudioFlinger::DuplicatingThread::~DuplicatingThread()
    for (size_t i = 0; i < mOutputTracks.size(); i++) {
        mOutputTracks[i]->destroy();
    mOutputTracks.clear();

// 1. add all tracks to outputTracks
// 2. prepareTracks_l
// 3. mAudioMixer->process();  
// FIXME: outputTracks wrap a MixerThread, not a Track
// 4. for all OutputTrack, invoke write()
    // current DuplicatingThread is added in the constructor
    // another MixerThread was add in function
    // int AudioFlinger::openDuplicateOutput(int output1, int output2) 
bool AudioFlinger::DuplicatingThread::threadLoop()
    while (!exitPending())
        checkForNewParameters_l()
        for (size_t i = 0; i < mOutputTracks.size(); i++) 
            outputTracks.add(mOutputTracks[i]);

        // if no active tracks, put audio hardware into standby after short delay

        mixerStatus = prepareTracks_l(activeTracks, &tracksToRemove);
        if (LIKELY(mixerStatus == MIXER_TRACKS_READY)) 
            // mix buffers...
            if (outputsReady(outputTracks)) 
                mAudioMixer->process();

        // sleepTime == 0 means we must write to audio hardware
        if (sleepTime == 0) {
            for (size_t i = 0; i < outputTracks.size(); i++) {
                outputTracks[i]->write(mMixBuffer, writeFrames);

        // finally let go of all our tracks, without the lock held
        // since we can't guarantee the destructors won't acquire that
        // same lock.
        tracksToRemove.clear();
        outputTracks.clear();

// 1. create a OutputTrack which wraps the MixerThread
// 2. set the volume of the MixerThread
// 3. add the OutputTrack to mOutputTracks
void AudioFlinger::DuplicatingThread::addOutputTrack(MixerThread *thread)
    int frameCount = (3 * mFrameCount * mSampleRate) / thread->sampleRate();
    OutputTrack *outputTrack = new OutputTrack((ThreadBase *)thread,
            this, mSampleRate, mFormat, mChannelCount, frameCount);
    if (outputTrack->cblk() != NULL) {
        thread->setStreamVolume(AudioSystem::NUM_STREAM_TYPES, 1.0f);
        mOutputTracks.add(outputTrack);



// 1. remove the MixerThread from the DuplicatingThread
void AudioFlinger::DuplicatingThread::removeOutputTrack(MixerThread *thread)
    for (size_t i = 0; i < mOutputTracks.size(); i++) {
        if (mOutputTracks[i]->thread() == (ThreadBase *)thread) {
            mOutputTracks[i]->destroy();
            mOutputTracks.removeAt(i);
            return;

// thread is not (playbackThread->standby() && !playbackThread->isSuspended())
bool AudioFlinger::DuplicatingThread::outputsReady(SortedVector< sp<OutputTrack> > &outputTracks)






