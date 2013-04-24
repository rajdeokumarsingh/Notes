// 设备上的audio_policy配置文件
./hardware/libhardware_legacy/audio/audio_policy.conf
./device/samsung/manta/audio_policy.conf
./device/samsung/crespo/libaudio/audio_policy.conf
./device/samsung/tuna/audio/audio_policy.conf
./device/ti/panda/audio/audio_policy.conf
./device/asus/grouper/audio_policy.conf
./device/lge/mako/audio_policy.conf

// audio_policy配置的层次结构
audio_hw_modules {
    primary {
        outputs {
            primary {
                sampling_rates 44100
                channel_masks AUDIO_CHANNEL_OUT_STEREO
                formats AUDIO_FORMAT_PCM_16_BIT
                devices AUDIO_DEVICE_OUT_SPEAKER|AUDIO_DEVICE_OUT_WIRED_HEADSET|
                AUDIO_DEVICE_OUT_WIRED_HEADPHONE|AUDIO_DEVICE_OUT_ALL_SCO|
                AUDIO_DEVICE_OUT_AUX_DIGITAL|AUDIO_DEVICE_OUT_DGTL_DOCK_HEADSET
                flags AUDIO_OUTPUT_FLAG_PRIMARY
            }
            hdmi {
                sampling_rates 44100|48000
                channel_masks dynamic
                formats AUDIO_FORMAT_PCM_16_BIT
                devices AUDIO_DEVICE_OUT_AUX_DIGITAL
                flags AUDIO_OUTPUT_FLAG_DIRECT
            }
        }
        inputs {
            primary {
                sampling_rates 8000|11025|16000|22050|32000|44100|48000
                channel_masks AUDIO_CHANNEL_IN_MONO|AUDIO_CHANNEL_IN_STEREO
                formats AUDIO_FORMAT_PCM_16_BIT
                devices AUDIO_DEVICE_IN_BUILTIN_MIC|AUDIO_DEVICE_IN_BLUETOOTH_SCO_HEADSET|AUDIO_DEVICE_IN_WIRED_HEADSET
            }
        }
    }
    a2dp {
        outputs {
            a2dp {
                sampling_rates 44100
                channel_masks AUDIO_CHANNEL_OUT_STEREO
                formats AUDIO_FORMAT_PCM_16_BIT
                devices AUDIO_DEVICE_OUT_ALL_A2DP
            }
        }
    }
    usb {
        outputs {
            usb_accessory {
                sampling_rates 44100
                channel_masks AUDIO_CHANNEL_OUT_STEREO
                formats AUDIO_FORMAT_PCM_16_BIT
                devices AUDIO_DEVICE_OUT_USB_ACCESSORY
            }
            usb_device {
                sampling_rates 44100
                channel_masks AUDIO_CHANNEL_OUT_STEREO
                formats AUDIO_FORMAT_PCM_16_BIT
                devices AUDIO_DEVICE_OUT_USB_DEVICE
            }
        }
    }
}

// 开机时默认打开的设备
global_configuration {
    attached_output_devices AUDIO_DEVICE_OUT_SPEAKER
    default_output_device AUDIO_DEVICE_OUT_SPEAKER
    attached_input_devices AUDIO_DEVICE_IN_BUILTIN_MIC
}

/* HwModule 
 *
 *  对应一个声音硬件模块，例如primary, a2dp, usb
 *  一个声音硬件模块包括了一组输出和一组输入
 *  每个输入/输出通过IOProfile来描述
 *  数组mOutputProfiles记录了所有输出设备
 *  数组mInputProfiles记录了所有输入设备
 *
 *  AudioPolicyManagerBase.h 包括一个全局的数组
 *          Vector <HwModule *> mHwModules; 
 *  记录所有的HwModule
 *
 */
class HwModule {
    // base name of the audio HW module (primary, a2dp, usb ...)
    const char *const mName; 

    // 声音硬件模块的handle
    audio_module_handle_t mHandle;

    // output profiles exposed by this module
    Vector <IOProfile *> mOutputProfiles; 
    // input profiles exposed by this module
    Vector <IOProfile *> mInputProfiles;
};

/* IOProfile
 *
 *  对应HwModule中一个输入或输出
 *  一个输入或输出中包括一个或多个设备
 *  IOProfile描述了该输入/输出的
 *      sample rate, channel masks, formats, devices
 *      flags
 *
 */
class IOProfile
{
public:
    bool isCompatibleProfile(audio_devices_t device,
            uint32_t samplingRate,
            uint32_t format,
            uint32_t channelMask,
            audio_output_flags_t flags) const;

    // by convention, "0' in the first entry in mSamplingRates, mChannelMasks or mFormats
    // indicates the supported parameters should be read from the output stream
    // after it is opened for the first time
    Vector <uint32_t> mSamplingRates; // supported sampling rates
    Vector <audio_channel_mask_t> mChannelMasks; // supported channel masks
    Vector <audio_format_t> mFormats; // supported audio formats

    audio_devices_t mSupportedDevices; // supported devices (devices this output can be
    // routed to)
    audio_output_flags_t mFlags; // attribute flags (e.g primary output,
    // direct output...). For outputs only.
    HwModule *mModule;                     // audio HW module exposing this I/O stream
};

AudioPolicyManagerBase中的数据结构 {

    AudioPolicyClientInterface *mpClientInterface;  // audio policy client interface

    // output
    {
        // list of descriptors for outputs currently opened
        DefaultKeyedVector<audio_io_handle_t, AudioOutputDescriptor *> mOutputs;
        // copy of mOutputs before setDeviceConnectionState() opens new outputs
        // reset to mOutputs when updateDevicesAndOutputs() is called.
        DefaultKeyedVector<audio_io_handle_t, AudioOutputDescriptor *> mPreviousOutputs;

        audio_io_handle_t mPrimaryOutput;              // primary output handle
        audio_devices_t mAvailableOutputDevices; // bit field of all available output devices

        audio_devices_t mAttachedOutputDevices; // output devices always available on the platform
        audio_devices_t mDefaultOutputDevice; // output device selected by default at boot time
        // (must be in mAttachedOutputDevices)
    }

    // input
    {
        DefaultKeyedVector<audio_io_handle_t, AudioInputDescriptor *> mInputs;     // list of input descriptors
        audio_devices_t mAvailableInputDevices; // bit field of all available input devices
        // without AUDIO_DEVICE_BIT_IN to allow direct bit
        // field comparisons
    }

    // 不同stratigy的device的缓存
    audio_devices_t mDeviceForStrategy[NUM_STRATEGIES];
}


AudioPolicyManagerBase::AudioPolicyManagerBase(AudioPolicyClientInterface *clientInterface)
{
    // TODO:
    initializeVolumeCurves();

    // 根据配置文件创建HwModule层次结构
    if (loadAudioPolicyConfig(AUDIO_POLICY_VENDOR_CONFIG_FILE) != NO_ERROR) {
            #define AUDIO_POLICY_VENDOR_CONFIG_FILE "/vendor/etc/audio_policy.conf"
            {
                loadGlobalConfig(root);
                loadHwModules(root);
            }

        if (loadAudioPolicyConfig(AUDIO_POLICY_CONFIG_FILE) != NO_ERROR) {
            #define AUDIO_POLICY_CONFIG_FILE "/system/etc/audio_policy.conf"
            defaultAudioPolicyConfig();
        }
    }

    // TODO: client interface的层次结构
    for (size_t i = 0; i < mHwModules.size(); i++) {
        mHwModules[i]->mHandle = mpClientInterface->loadHwModule(mHwModules[i]->mName);

        // open all output streams needed to access attached devices
        for (size_t j = 0; j < mHwModules[i]->mOutputProfiles.size(); j++)
        {
            const IOProfile *outProfile = mHwModules[i]->mOutputProfiles[j];

            // 打开默认连接上的output, 目前是primary hw module中组中的primary output
            if (outProfile->mSupportedDevices & mAttachedOutputDevices) {
                // mAttachedOutputDevices一般是AUDIO_DEVICE_OUT_SPEAKER
                /* 参见 global_configuration 
                    {
                       attached_output_devices AUDIO_DEVICE_OUT_SPEAKER
                       default_output_device AUDIO_DEVICE_OUT_SPEAKER
                       attached_input_devices AUDIO_DEVICE_IN_BUILTIN_MIC
                    }
                */
                AudioOutputDescriptor *outputDesc = new AudioOutputDescriptor(outProfile);
                outputDesc->mDevice = (audio_devices_t)(mDefaultOutputDevice & // AUDIO_DEVICE_OUT_SPEAKER
                        outProfile->mSupportedDevices);  
                audio_io_handle_t output = mpClientInterface->openOutput(   // 打开output
                        outProfile->mModule->mHandle,
                        &outputDesc->mDevice,
                        &outputDesc->mSamplingRate,
                        &outputDesc->mFormat,
                        &outputDesc->mChannelMask,
                        &outputDesc->mLatency,
                        outputDesc->mFlags
                        );
                if (output != 0) {
                    mAvailableOutputDevices = (audio_devices_t)(mAvailableOutputDevices |
                            (outProfile->mSupportedDevices & mAttachedOutputDevices));
                    if (mPrimaryOutput == 0 &&
                            outProfile->mFlags & AUDIO_OUTPUT_FLAG_PRIMARY) {
                        mPrimaryOutput = output;
                    }
                    addOutput(output, outputDesc); // 增加descriptor到数组
                    setOutputDevice(output,        // setParameters()
                            (audio_devices_t)(mDefaultOutputDevice &
                                outProfile->mSupportedDevices), true);
                }
            }
        }
    }

    updateDevicesAndOutputs(); // 更新各个策略的device
    {
        for (int i = 0; i < NUM_STRATEGIES; i++) {
            mDeviceForStrategy[i] = getDeviceForStrategy((routing_strategy)i, false /*fromCache*/);
        }
        mPreviousOutputs = mOutputs;
    }

}







