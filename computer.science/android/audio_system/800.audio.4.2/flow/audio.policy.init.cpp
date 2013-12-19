// TODO: 增加hardware中的log

// main_mediaserver.cpp
int main(int argc, char** argv)
{
    sp<ProcessState> proc(ProcessState::self());
    sp<IServiceManager> sm = defaultServiceManager();

    ALOGI("ServiceManager: %p", sm.get());

    AudioFlinger::instantiate();
    MediaPlayerService::instantiate();
    CameraService::instantiate();
    AudioPolicyService::instantiate();
    ProcessState::self()->startThreadPool();
    IPCThreadState::self()->joinThreadPool();
}
        |
        V
AudioPolicyService::AudioPolicyService()
    : BnAudioPolicyService() , mpAudioPolicyDev(NULL) , mpAudioPolicy(NULL)
{
    /* instantiate the audio policy manager */
    //  struct audio_policy_device *mpAudioPolicyDev;
    //  struct audio_policy *mpAudioPolicy;
    const struct hw_module_t *module;
    rc = hw_get_module(AUDIO_POLICY_HARDWARE_MODULE_ID, &module);
    {
        //  加载下面几个so文件之一
        // #define AUDIO_POLICY_HARDWARE_MODULE_ID "audio_policy"
        // /system/lib/hw/audio_policy.default.so, 
        // /vendor/lib/hw/audio_policy.vendor_name(like sc8825).so, 
        // /system/lib/hw/audio_policy.vendor_name(like sc8825).so, 
        static int load(const char *id, // "audio_policy"
                const char *path,       // "/vendor/lib/hw/audio_policy.default.so"
                const struct hw_module_t **pHmi)

        {
            handle = dlopen(path, RTLD_NOW);
            /* Get the address of the struct hal_module_info. */
            const char *sym = HAL_MODULE_INFO_SYM_AS_STR;
            hmi = (struct hw_module_t *)dlsym(handle, sym);
            // 加载类库中的hw_module_t信息，关键函数是open, for example: legacy_ap_dev_open
            {
                static struct hw_module_methods_t legacy_ap_module_methods = {
                    open: legacy_ap_dev_open
                };

                struct legacy_ap_module HAL_MODULE_INFO_SYM = {
                    module: {
                        common: {
                            tag: HARDWARE_MODULE_TAG,
                            version_major: 1,
                            version_minor: 0,
                            id: AUDIO_POLICY_HARDWARE_MODULE_ID,
                            name: "LEGACY Audio Policy HAL",
                            author: "The Android Open Source Project",
                            methods: &legacy_ap_module_methods,
                            dso : NULL,
                            reserved : {0},
                        },
                    },
                };
            }
            hmi->dso = handle;
            *pHmi = hmi;  // 这时module就被赋值了为hmi了
        }
    }

    rc = audio_policy_dev_open(module, &mpAudioPolicyDev);
    {
        // 打开audio_policy.default.so动态库的open函数
        return module->methods->open(module, AUDIO_POLICY_INTERFACE,
                (hw_device_t**)device);
            {

                // 注册了create_audio_policy的钩子函数
                static int legacy_ap_dev_open(const hw_module_t* module, const char* name,
                                                    hw_device_t** device)
                {
                    struct legacy_ap_device *dev;

                    dev = (struct legacy_ap_device *)calloc(1, sizeof(*dev));
                    dev->device.common.tag = HARDWARE_DEVICE_TAG;
                    dev->device.common.version = 0;
                    dev->device.common.module = const_cast<hw_module_t*>(module);
                    dev->device.common.close = legacy_ap_dev_close;
                    // 注册了create_audio_policy的钩子函数
                    dev->device.create_audio_policy = create_legacy_ap;
                    dev->device.destroy_audio_policy = destroy_legacy_ap;

                    *device = &dev->device.common;
                    return 0;
                }
            }
    }
    // 调用create_audio_policy的钩子函数
    rc = mpAudioPolicyDev->create_audio_policy(mpAudioPolicyDev, &aps_ops, this, &mpAudioPolicy);
    static int create_legacy_ap(const struct audio_policy_device *device,
                                struct audio_policy_service_ops *aps_ops,  // 硬件无关的audio policy接口, 
                                                                                // 定义在AudioPolicyService.cpp中
                                void *service,                             // AudioPolicyService.this
                                struct audio_policy **ap        // 硬件相关的audio policy接口
                                                                // 在这个函数中将被赋值
                                )
    {
        struct legacy_audio_policy *lap;
        lap = (struct legacy_audio_policy *)calloc(1, sizeof(*lap));

        // 为硬件相关的audio policy钩子函数赋值
        lap->policy.set_device_connection_state = ap_set_device_connection_state;
        lap->policy.get_device_connection_state = ap_get_device_connection_state;
        lap->policy.set_phone_state = ap_set_phone_state;
        lap->policy.set_ringer_mode = ap_set_ringer_mode;
        lap->policy.set_force_use = ap_set_force_use;
        lap->policy.get_force_use = ap_get_force_use;
        lap->policy.set_can_mute_enforced_audible =
            ap_set_can_mute_enforced_audible;
        lap->policy.init_check = ap_init_check;
        lap->policy.get_output = ap_get_output;
        lap->policy.start_output = ap_start_output;
        lap->policy.stop_output = ap_stop_output;
        lap->policy.release_output = ap_release_output;
        lap->policy.get_input = ap_get_input;
        lap->policy.start_input = ap_start_input;
        lap->policy.stop_input = ap_stop_input;
        lap->policy.release_input = ap_release_input;
        lap->policy.init_stream_volume = ap_init_stream_volume;
        lap->policy.set_stream_volume_index = ap_set_stream_volume_index;
        lap->policy.get_stream_volume_index = ap_get_stream_volume_index;
        lap->policy.set_stream_volume_index_for_device = ap_set_stream_volume_index_for_device;
        lap->policy.get_stream_volume_index_for_device = ap_get_stream_volume_index_for_device;
        lap->policy.get_strategy_for_stream = ap_get_strategy_for_stream;
        lap->policy.get_devices_for_stream = ap_get_devices_for_stream;
        lap->policy.get_output_for_effect = ap_get_output_for_effect;
        lap->policy.register_effect = ap_register_effect;
        lap->policy.unregister_effect = ap_unregister_effect;
        lap->policy.set_effect_enabled = ap_set_effect_enabled;
        lap->policy.is_stream_active = ap_is_stream_active;
        lap->policy.is_source_active = ap_is_source_active;
        lap->policy.dump = ap_dump;

        lap->service = service;
        lap->aps_ops = aps_ops;
        lap->service_client =
            new AudioPolicyCompatClient(aps_ops, service);
        if (!lap->service_client) {
            ret = -ENOMEM;
            goto err_new_compat_client;
        }

        // 创建硬件无关的policy接口
        lap->apm = createAudioPolicyManager(lap->service_client);
        {
            // 各个平台的AudioPolicyManager基本都等于AudioPolicyManagerBase
            return new AudioPolicyManagerDefault(clientInterface); 
        }

        // 为硬件相关的audio policy钩子函数赋值
        *ap = &lap->policy;
        return 0;
    }

    rc = mpAudioPolicy->init_check(mpAudioPolicy); // 基本没有干啥事
    {
        status_t AudioPolicyManagerBase::initCheck()
            return (mPrimaryOutput == 0) ? NO_INIT : NO_ERROR;
    }
     
    // 加载声音效果配置文件
    // load audio pre processing modules
    // #define AUDIO_EFFECT_DEFAULT_CONFIG_FILE "/system/etc/audio_effects.conf"
    // #define AUDIO_EFFECT_VENDOR_CONFIG_FILE "/vendor/etc/audio_effects.conf"
    if (access(AUDIO_EFFECT_VENDOR_CONFIG_FILE, R_OK) == 0) {
        loadPreProcessorConfig(AUDIO_EFFECT_VENDOR_CONFIG_FILE);
    } else if (access(AUDIO_EFFECT_DEFAULT_CONFIG_FILE, R_OK) == 0) {
        loadPreProcessorConfig(AUDIO_EFFECT_DEFAULT_CONFIG_FILE);
    }
}

// AudioPolicyManagerBase的初始化
AudioPolicyManagerBase::AudioPolicyManagerBase(AudioPolicyClientInterface *clientInterface)
    // 初始化内部变量
    mPrimaryOutput((audio_io_handle_t)0),
    mAvailableOutputDevices(AUDIO_DEVICE_NONE),
    mPhoneState(AudioSystem::MODE_NORMAL),
    mLimitRingtoneVolume(false), 
    mLastVoiceVolume(-1.0f),
    mTotalEffectsCpuLoad(0), 
    mTotalEffectsMemory(0),
    mA2dpSuspended(false), 
    mHasA2dp(false),
    mHasUsb(false), 
    mHasRemoteSubmix(false)
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

    // 1. 加载Hw Module
<<<<<<< HEAD
        // 每个config中的HwModule对应一个audio.(primary|a2dp|usb).default.so
=======
        // 每个config中的HwModule对应一个audio.(primary|audio|usb).default.so
>>>>>>> a8862944d8b095cc4ae22aa3da5cde4e4dbebb13
    // 2. 打开默认声音输出, primary
    // 3. 默认声音输出的设备, primary->speaker
    for (size_t i = 0; i < mHwModules.size(); i++) {
        mHwModules[i]->mHandle = mpClientInterface->loadHwModule(mHwModules[i]->mName);
        {
            audio_module_handle_t AudioPolicyCompatClient::loadHwModule(const char *moduleName)
                return mServiceOps->load_hw_module(mService, moduleName);

            static audio_module_handle_t aps_load_hw_module(void *service, const char *name)
                return af->loadHwModule(name);

            audio_module_handle_t AudioFlinger::loadHwModule(const char *name)
                return loadHwModule_l(name);

            audio_module_handle_t AudioFlinger::loadHwModule_l(const char *name)
            {
                audio_hw_device_t *dev;
                int rc = load_audio_interface(name, &dev);
                {
                    const hw_module_t *mod;
                    rc = hw_get_module_by_class(AUDIO_HARDWARE_MODULE_ID, if_name, &mod);
                        // 加载下面so文件
                        // audio.primary.default.so
                        // audio.a2dp.default.so
                        // audio.usb.default.so
                        // ... 
                    rc = audio_hw_device_open(mod, dev);
                    {
                        static int legacy_adev_open(const hw_module_t* module, const char* name,
                                hw_device_t** device) {

                            // 注册设备信息
                            ladev->device.common.tag = HARDWARE_DEVICE_TAG;
                            ladev->device.common.version = AUDIO_DEVICE_API_VERSION_2_0;
                            ladev->device.common.module = const_cast<hw_module_t*>(module);
                            ladev->device.common.close = legacy_adev_close;


                            // 注册回调函数
                            ladev->device.init_check = adev_init_check;
                            ladev->device.set_voice_volume = adev_set_voice_volume;
                            ladev->device.set_fm_volume = adev_set_fm_volume;
                            ladev->device.set_master_volume = adev_set_master_volume;
                            ladev->device.get_master_volume = adev_get_master_volume;
                            ladev->device.set_mode = adev_set_mode;
                            ladev->device.set_mic_mute = adev_set_mic_mute;
                            ladev->device.get_mic_mute = adev_get_mic_mute;
                            ladev->device.set_parameters = adev_set_parameters;
                            ladev->device.get_parameters = adev_get_parameters;
                            ladev->device.get_input_buffer_size = adev_get_input_buffer_size;
                            ladev->device.open_output_stream = adev_open_output_stream;
                            ladev->device.close_output_stream = adev_close_output_stream;
                            ladev->device.open_input_stream = adev_open_input_stream;
                            ladev->device.close_input_stream = adev_close_input_stream;
                            ladev->device.dump = adev_dump;

                            ladev->hwif = createAudioHardware();
                            *device = &ladev->device.common;
                        }
                    }
                }
            }
        }

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
// 硬件配置的初始化
../../Audio.HAL.config.cpp

接口
hardware/libhardware/include/hardware/audio_policy.h 
hardware/libhardware_legacy/audio/audio_policy_hal.cpp
hardware/qcom/audio/alsa_sound/audio_policy_hal.cpp 
hardware/libhardware/modules/audio/audio_policy.c

