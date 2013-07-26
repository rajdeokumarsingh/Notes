
base/include/ui/EventHub.h
    class EventHub : public RefBase

        // bit fields for classes of devices.
        enum {
         CLASS_KEYBOARD      = 0x00000001,
         CLASS_ALPHAKEY      = 0x00000002,
         CLASS_TOUCHSCREEN   = 0x00000004,
         CLASS_TRACKBALL     = 0x00000008,
         CLASS_TOUCHSCREEN_MT= 0x00000010,
         CLASS_DPAD          = 0x00000020
        };

        // 维护了一组device, 每个device中包括了一个KeyLayoutMap
        struct device_t
            const int32_t   id;               
            const String8   path; 
            String8         name; 
            uint32_t        classes;
            uint8_t*        keyBitmask;
            KeyLayoutMap*   layoutMap;
            String8         keylayoutFilename;
            device_t*       next;            

            device_t(int32_t _id, const char* _path, const char* name);

        device_t        *mOpeningDevices;
        device_t        *mClosingDevices;

        device_t        **mDevices;
        struct pollfd   *mFDs;
        int             mFDCount;

        bool            mOpened;
        List<String8>   mExcludedDevices;

    event
    absolute info
    switch state

static const char *device_path = "/dev/input";
    # cd /dev/input
    # ls
    event1
    event0

EventHub::device_t::device_t(int32_t _id, const char* _path, const char* name)
    : id(_id), path(_path), name(name), classes(0)
    , keyBitmask(NULL), layoutMap(new KeyLayoutMap()), next(NULL) 


// 从mDevicesById数组中获取device
EventHub::device_t* EventHub::getDevice(int32_t deviceId) const
    device_t* dev = mDevicesById[id].device;

#if 0
    确定设备能力和特性

    对于一些设备，也许知道设备的身份信息就足够了，因为它允许你根据设备的使用情况处理设备的任何 case。但是这总做法的尺度不好。比如，你有一个设备仅有一个滑轮，你想使能处理滑轮的 handler，但是你并不想在 code里列出每个带有滑轮的鼠标的 vendor/product信息。为此， event interface允许你对于某个设备确定有哪些功能和特性。 Event interface支持的 feature types有：
    EV_KEY: absolute binary results, such as keys and buttons.
    EV_REL: relative results, such as the axes on a mouse.
    EV_MSC: miscellaneous uses that didn't fit anywhere else.
    EV_LED: LEDs and similar indications.
    EV_SND: sound output, such as buzzers.
    EV_REP: enables autorepeat of keys in the input core.
    EV_FF: sends force-feedback effects to a device.
    EV_FF_STATUS: device reporting of force-feedback effects back to the host.
    EV_PWR: power management events

    EV_ABS: absolute integer results, such as the axes on a joystick or for a tablet.

    /**
    * struct input_absinfo - used by EVIOCGABS/EVIOCSABS ioctls
    * @value: latest reported value for the axis.
    * @minimum: specifies minimum value for the axis.
    * @maximum: specifies maximum value for the axis.
    * @fuzz: specifies fuzz value that is used to filter noise from
    *      the event stream.
    * @flat: values that are within this value will be discarded by
    *      joydev interface and reported as 0 instead.
    * @resolution: specifies resolution for the values reported for
    *      the axis.
    *
    * Note that input core does not clamp reported values to the
    * [minimum, maximum] limits, such task is left to userspace.
    *
    * Resolution for main axes (ABS_X, ABS_Y, ABS_Z) is reported in
    * units per millimeter (units/mm), resolution for rotational axes
    * (ABS_RX, ABS_RY, ABS_RZ) is reported in units per radian.
    */
    struct input_absinfo {
    __s32 value;
    __s32 minimum;
    __s32 maximum;
    __s32 fuzz;
    __s32 flat;
    __s32 resolution;
    };

    #endif
int EventHub::getAbsoluteInfo(int32_t deviceId, int axis, int *outMinValue, 
        int* outMaxValue, int* outFlat, int* outFuzz) const   

    device_t* device = getDevice(deviceId);
    ioctl(mFDs[id_to_index(device->id)].fd, EVIOCGABS(axis), &info)


// 120 #define EVIOCGSW(len)           _IOC(_IOC_READ, 'E', 0x1b, len)         /* get all switch states */
int EventHub::getSwitchState(int32_t deviceId, int sw) const
    if (ioctl(mFDs[id_to_index(device->id)].fd,
                EVIOCGSW(sizeof(sw_bitmask)), sw_bitmask) >= 0) {
        return test_bit(sw, sw_bitmask) ? 1 : 0;

/* 现在我们清楚的知道如何接收或者发送一个事件 ---key按下 /抬起，鼠标移动等等。
对于一些程序，可能还需要知道设备的一些全局状态。比如，一个管理 keyboard的程序需要知道当前的哪些指示灯在亮，哪些键被释放。
EVIOCGKEY ioctl用于确定一个设备的全局 key/button状态，它在 bit array里设置了每个 key/button是否被释放。 */

// 确定这个scan code是否是enable的, test_bit是1/0
int EventHub::getScancodeState(int32_t deviceId, int code) const
    if (ioctl(mFDs[id_to_index(device->id)].fd,
                EVIOCGKEY(sizeof(key_bitmask)), key_bitmask) >= 0) {
        return test_bit(code, key_bitmask) ? 1 : 0;

bool EventHub::getEvent(int32_t* outDeviceId, int32_t* outType, int32_t* outScancode, 
        int32_t* outKeycode, uint32_t *outFlags, int32_t* outValue, nsecs_t* outWhen)

    mError = openPlatformInput() ? NO_ERROR : UNKNOWN_ERROR;
    while(1) 
        pollres = poll(mFDs, mFDCount, -1);

        // mFDs[0] is used for inotify, so process regular events starting at mFDs[1]
        for(i = 1; i < mFDCount; i++)
            if(mFDs[i].revents)
                if(mFDs[i].revents & POLLIN) 
                    res = read(mFDs[i].fd, &iev, sizeof(iev));
                    *outType = iev.type;
                    *outScancode = iev.code;

                    if (iev.type == EV_KEY) 
                        err = mDevices[i]->layoutMap->map(iev.code, outKeycode, outFlags);
                        *outKeycode = iev.code;

                    *outValue = iev.value;

        // read_notify() will modify mFDs and mFDCount, so this must be done after
        // processing all other events.
        if(mFDs[0].revents & POLLIN) {
            read_notify(mFDs[0].fd);

/* 添加一个对目录"/dev/input"的监控fd, 文件的删除和创建创建事件都会通过该fd上报
    这个fd保存在mFDs[0]中 */
bool EventHub::openPlatformInput(void)
    int res;

    mFDCount = 1;
    mFDs = (pollfd *)calloc(1, sizeof(mFDs[0]));
    mDevices = (device_t **)calloc(1, sizeof(mDevices[0]));
    mFDs[0].events = POLLIN;
    mDevices[0] = NULL;

    mFDs[0].fd = inotify_init();
    res = inotify_add_watch(mFDs[0].fd, "/dev/input", IN_DELETE | IN_CREATE);
        // IN_CREATE  File/directory created in watched directory (*).
        // IN_DELETE  File/directory deleted from watched directory (*).

    res = scan_dir("/dev/input");


int EventHub::open_device(const char *deviceName)
    fd = open(deviceName, O_RDWR);
    ioctl(fd, EVIOCGVERSION, &version)
    ioctl(fd, EVIOCGID, &id)
    ioctl(fd, EVIOCGNAME(sizeof(name) - 1), &name)
    ioctl(fd, EVIOCGPHYS(sizeof(location) - 1), &location)
    ioctl(fd, EVIOCGUNIQ(sizeof(idstr) - 1), &idstr)


