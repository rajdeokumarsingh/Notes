/*
todo:

Model是什么?
结果是什么?


mCanBeMuted, 哪些stream不能mute?

AUDIO_DEVICE_IN_VOICE_CALL是个啥？
打电话时，输入设备可以用mic, headset, 但是AUDIO_DEVICE_IN_VOICE_CALL是什么输入设备
AUDIO_DEVICE_IN_BUILTIN_MIC           = AUDIO_DEVICE_BIT_IN | 0x4,
AUDIO_DEVICE_IN_BLUETOOTH_SCO_HEADSET = AUDIO_DEVICE_BIT_IN | 0x8,
AUDIO_DEVICE_IN_WIRED_HEADSET         = AUDIO_DEVICE_BIT_IN | 0x10,
AUDIO_DEVICE_IN_VOICE_CALL            = AUDIO_DEVICE_BIT_IN | 0x40,


每个设备的index从哪儿设置的？
streamDesc.getVolumeIndex(device)
*/
                     
class AudioOutputDescriptor
bool mStrategyMutedByDevice[NUM_STRATEGIES]; // strategies muted because of incompatible
                                            // device selection. See checkDeviceMuteStrategies()

bool AudioPolicyManagerBase::needsDirectOuput(audio_stream_type_t stream,
        uint32_t samplingRate,
        audio_format_t format,
        audio_channel_mask_t channelMask,
        audio_output_flags_t flags,
        audio_devices_t device)
{
    return ((flags & AudioSystem::OUTPUT_FLAG_DIRECT) ||
            (format != 0 && !AudioSystem::isLinearPCM(format)));
}



