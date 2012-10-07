
Java层的AudioTrack很少有人使用
    base/media/java/android/media/AudioTrack.java
        也是在native层包装了AudioTrack.cpp

Media Player的上层流程:
    code.reading/flow.player.cpp

AudioTrack的数据加载模式
    public static final int MODE_STREAM = 1;
        // 通过write多次将数据写入到AudioTrack中
        // 可能会引起延时

    public static final int MODE_STATIC = 0;
        // 通过write一次将数据写入到AudioTrack中
        // 不会引起延时
        // 消耗内存比较多，适合与比较小的音频文件

Frame
    1帧 = 一个采样点的字节数 * 声道数
    例如:
        PCM16(2字节), 立体声(2声道)的一个帧等于 2*2 = 4 字节

AudioTrackJniStorage
    包括了一片共享内存

Android Framework的音频子系统中，
    每一个音频流对应着一个AudioTrack类的一个实例，
    每个AudioTrack会在创建时注册到 AudioFlinger中，
    由AudioFlinger把所有的AudioTrack进行混合（Mixer），然后输送到AudioHardware中进行播放，
    目前Android的Froyo版本设定了同时最多可以创建32个音频流，也就是说，Mixer最多会同时处理32个AudioTrack的数据流。

如何使用AudioTrack
    frameworks/base/media/libmedia/audiotrack.cpp

    ToneGenerator是android中产生电话拨号音和其他音调波形的一个实现
        // 初始化audiotrack
        bool ToneGenerator::initAudioTrack() {  
            // Open audio track in mono, PCM 16bit, default sampling rate, default buffer size  
            mpAudioTrack = new AudioTrack();  
            mpAudioTrack->set(mStreamType,   // 然后调用set成员函数完成参数的设置并注册到AudioFlinger中
                    0,  
                    AudioSystem::PCM_16_BIT,  
                    AudioSystem::CHANNEL_OUT_MONO,  
                    0,  
                    0,  
                    audioCallback,  // audioCallback是一个回调函数，负责响应AudioTrack的通知，例如填充数据、循环播放、播放位置触发等等。
                    this,  
                    0,  
                    0,  
                    mThreadCanCallJava);  
            if (mpAudioTrack->initCheck() != NO_ERROR) {  
                LOGE("AudioTrack->initCheck failed");  
                goto initAudioTrack_exit;  
            }  
            mpAudioTrack->setVolume(mVolume, mVolume);  
            mState = TONE_INIT;  
            ......  
        }  

        // 回调函数的写法通常像这样：
        void ToneGenerator::audioCallback(int event, void* user, void *info) {  
            if (event != AudioTrack::EVENT_MORE_DATA) return;  

            // 后续的代码会填充相应的音频数据后返回
            AudioTrack::Buffer *buffer = static_cast<AudioTrack::Buffer *>(info);  
            ToneGenerator *lpToneGen = static_cast<ToneGenerator *>(user);  
            short *lpOut = buffer->i16;  
            unsigned int lNumSmp = buffer->size/sizeof(short);  
            const ToneDescriptor *lpToneDesc = lpToneGen->mpToneDesc;  
            if (buffer->size == 0) return;  

            // Clear output buffer: WaveGenerator accumulates into lpOut buffer  
            memset(lpOut, 0, buffer->size);  
            ......  
                // 以下是产生音调数据的代码，略....  
        }  

        enum event_type {  
             EVENT_MORE_DATA = 0,        // Request to write more data to PCM buffer.  

             EVENT_UNDERRUN = 1,         // PCM buffer underrun occured.  
                                         // 和overflow正好相反, buffer空了。生产者速度赶不上消费者

             EVENT_LOOP_END = 2,         // Sample loop end was reached; playback restarted from loop start if loop count was not 0.  
             EVENT_MARKER = 3,           // Playback head is at the specified marker position (See setMarkerPosition()).  
             EVENT_NEW_POS = 4,          // Playback head is at a new position (See setPositionUpdatePeriod()).  
             EVENT_BUFFER_END = 5        // Playback head is at the end of the buffer.  
        };  

    开始播放：
        1. mpAudioTrack->start();  
    停止播放：
        1. mpAudioTrack->stop();  

audio_track_cblk_t
    ./code.reading/AudioTrack.cpp



