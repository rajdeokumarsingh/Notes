
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

// 9. 从数据源中获取数据
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


