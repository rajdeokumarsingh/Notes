

AudioFlinger::DirectOutputThread::DirectOutputThread(const sp<AudioFlinger>& audioFlinger, 
        AudioStreamOut* output, int id, uint32_t device)
        :PlaybackThread(audioFlinger, output, id, device)
    mType = PlaybackThread::DIRECT;


// 1. seems to calculate volume
void AudioFlinger::DirectOutputThread::applyVolume(uint16_t leftVol, uint16_t rightVol, bool ramp)
    // Do not apply volume on compressed audio
    if (!AudioSystem::isLinearPCM(mFormat)) return;


// for all pending events
// 1. if no active tracks, put audio hardware into standby after short delay 
// 2. find out which tracks need to be processe
// 3. calculate volume and setVolume
// 4. remove all the tracks that need to be...
// 5. copy data in the cblk of the track to the mMixBuffer
// 6. write the data in the mMixBuffer to the hardware output by
//      AudioStreamOut::write()
// 7. finally let go of removed tracks
// end ;for all pending events
// 8. put the hardware output to standby
bool AudioFlinger::DirectOutputThread::threadLoop()
    while (!exitPending())
        processConfigEvents();

        // put audio hardware into standby after short delay, if no active tracks
        mOutput->standby();

        // find out which tracks need to be processe
        if (mActiveTracks.size() != 0)
            sp<Track> t = mActiveTracks[0].promote();

        Track* const track = t.get();
        audio_track_cblk_t* cblk = track->cblk();

        if (cblk->framesReady() && track->isReady() &&
                !track->isPaused() && !track->isTerminated())

            // calculate volume information
            mOutput->setVolume(left, right)
        else
            track->reset();

        // remove all the tracks that need to be...

        if (LIKELY(mixerStatus == MIXER_TRACKS_READY))  {
            AudioBufferProvider::Buffer buffer;
            curBuf = (int8_t *)mMixBuffer;
            // output audio to hardware
            while (frameCount) {
                buffer.frameCount = frameCount;
                activeTrack->getNextBuffer(&buffer);
                memcpy(curBuf, buffer.raw, buffer.frameCount * mFrameSize);
                curBuf += buffer.frameCount * mFrameSize;
                activeTrack->releaseBuffer(&buffer);

        // sleepTime == 0 means we must write to audio hardware
        if (sleepTime == 0) {
            if (mixerStatus == MIXER_TRACKS_READY) {
                applyVolume(leftVol, rightVol, rampVolume);

            int bytesWritten = (int)mOutput->write(
                    mMixBuffer, mixBufferSize);

        // finally let go of removed track
        trackToRemove.clear();
        activeTrack.clear();

    if (!mStandby) mOutput->standby();


// 1. sand all parameters to the hardware output by AudioStreamOut::setParameters
bool AudioFlinger::DirectOutputThread::checkForNewParameters_l()
    while (!mNewParameters.isEmpty())
        status = mOutput->setParameters(keyValuePair);
        readOutputParameters();
        sendConfigEvent_l(AudioSystem::OUTPUT_CONFIG_CHANGED);


        mNewParameters.removeAt(0);

        mParamStatus = status;
        mParamCond.signal();
        mWaitWorkCV.wait(mLock);


