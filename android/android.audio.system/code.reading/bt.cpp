
当蓝牙设备连接时，audio系统是如何将各种stream从系统默认的output关联到a2dp output


status_t AudioPolicyManagerBase::setDeviceConnectionState(AudioSystem::audio_devices device, 
        AudioSystem::device_connection_state state, const char *device_address)

#ifdef WITH_A2DP
    checkOutputForAllStrategies(newDevice);
#endif

void AudioPolicyManagerBase::setPhoneState(int state)
#ifdef WITH_A2DP
    checkOutputForAllStrategies(newDevice);
#endif

void AudioPolicyManagerBase::setForceUse(AudioSystem::force_use usage, AudioSystem::forced_config config)
#ifdef WITH_A2DP
    checkOutputForAllStrategies(newDevice);
    checkA2dpSuspend();
#endif


void AudioPolicyManagerBase::checkOutputForAllStrategies(uint32_t &newDevice)
    // Check strategies in order of priority so that once newDevice is set
    // for a given strategy it is not modified by subsequent calls to
    // checkOutputForStrategy()
    checkOutputForStrategy(STRATEGY_PHONE, newDevice);
    checkOutputForStrategy(STRATEGY_SONIFICATION, newDevice);
    checkOutputForStrategy(STRATEGY_MEDIA, newDevice);
    checkOutputForStrategy(STRATEGY_DTMF, newDevice);
        |
        V
void AudioPolicyManagerBase::checkOutputForStrategy(routing_strategy strategy, uint32_t &newDevice)
    mpClientInterface->setStreamOutput((AudioSystem::stream_type)i, mHardwareOutput);
    // or 
    mpClientInterface->setStreamOutput((AudioSystem::stream_type)i, a2dpOutput);
