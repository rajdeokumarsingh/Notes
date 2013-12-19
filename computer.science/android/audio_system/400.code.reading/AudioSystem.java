[Finished 2011-09-08]

// AudioSystem

// provide definitioin: 
    // stream type
    // output devices
    // input devices
    // device states
    // force use

// provide declaration of native methods

////////////////////////////////////////////////////////////////////////////////
// Defien stream type
    /* The audio stream for phone calls */
    public static final int STREAM_VOICE_CALL = 0;
    /* The audio stream for system sounds */
    public static final int STREAM_SYSTEM = 1;
    /* The audio stream for the phone ring and message alerts */
    public static final int STREAM_RING = 2;
    /* The audio stream for music playback */
    public static final int STREAM_MUSIC = 3;
    /* The audio stream for alarms */
    public static final int STREAM_ALARM = 4;
    /* The audio stream for notifications */
    public static final int STREAM_NOTIFICATION = 5;
    /* @hide The audio stream for phone calls when connected on bluetooth */
    public static final int STREAM_BLUETOOTH_SCO = 6;
    /* @hide The audio stream for enforced system sounds in certain countries (e.g camera in Japan) */
    public static final int STREAM_SYSTEM_ENFORCED = 7;
    /* @hide The audio stream for DTMF tones */
    public static final int STREAM_DTMF = 8;
    /* @hide The audio stream for text to speech (TTS) */
    public static final int STREAM_TTS = 9;

    /* @hide The audio stream for FM */
    public static final int STREAM_FM = 10; //FIXME: add stream fm

    // Expose only the getter method publicly so we can change it in the future
    private static final int NUM_STREAM_TYPES = 11;
    public static final int getNumStreamTypes() { return NUM_STREAM_TYPES; }

////////////////////////////////////////////////////////////////////////////////
// output devices
public static final int DEVICE_OUT_EARPIECE = 0x1;
public static final int DEVICE_OUT_SPEAKER = 0x2;
public static final int DEVICE_OUT_WIRED_HEADSET = 0x4;
public static final int DEVICE_OUT_WIRED_HEADPHONE = 0x8;
public static final int DEVICE_OUT_BLUETOOTH_SCO = 0x10;
public static final int DEVICE_OUT_BLUETOOTH_SCO_HEADSET = 0x20;
public static final int DEVICE_OUT_BLUETOOTH_SCO_CARKIT = 0x40;
public static final int DEVICE_OUT_BLUETOOTH_A2DP = 0x80;
public static final int DEVICE_OUT_BLUETOOTH_A2DP_HEADPHONES = 0x100;
public static final int DEVICE_OUT_BLUETOOTH_A2DP_SPEAKER = 0x200;
public static final int DEVICE_OUT_AUX_DIGITAL = 0x400;
public static final int DEVICE_OUT_FM = 0x800;
public static final int DEVICE_OUT_DEFAULT = 0x8000;

// input devices
public static final int DEVICE_IN_COMMUNICATION = 0x10000;
public static final int DEVICE_IN_AMBIENT = 0x20000;
public static final int DEVICE_IN_BUILTIN_MIC1 = 0x40000;
public static final int DEVICE_IN_BUILTIN_MIC2 = 0x80000;
public static final int DEVICE_IN_MIC_ARRAY = 0x100000;
public static final int DEVICE_IN_BLUETOOTH_SCO_HEADSET = 0x200000;
public static final int DEVICE_IN_WIRED_HEADSET = 0x400000;
public static final int DEVICE_IN_AUX_DIGITAL = 0x800000;
public static final int DEVICe_IN_FM_RX = 0x1000000;
public static final int DEVICe_IN_FM_RXA2DP = 0x2000000;
public static final int DEVICE_IN_DEFAULT = 0x80000000;

// device states
public static final int DEVICE_STATE_UNAVAILABLE = 0;
public static final int DEVICE_STATE_AVAILABLE = 1;

// config for setForceUse
public static final int FORCE_NONE = 0;
public static final int FORCE_SPEAKER = 1;
public static final int FORCE_HEADPHONES = 2;
public static final int FORCE_BT_SCO = 3;
public static final int FORCE_BT_A2DP = 4;
public static final int FORCE_WIRED_ACCESSORY = 5;
public static final int FORCE_BT_CAR_DOCK = 6;
public static final int FORCE_BT_DESK_DOCK = 7;
public static final int FORCE_DEFAULT = FORCE_NONE;

// usage for serForceUse
public static final int FOR_COMMUNICATION = 0;
public static final int FOR_MEDIA = 1;
public static final int FOR_RECORD = 2;
public static final int FOR_DOCK = 3;

// declare native method

public static native int muteMicrophone(boolean on);
public static native boolean isMicrophoneMuted();

public static native boolean isStreamActive(int stream);

public static native int setParameters(String keyValuePairs);
public static native String getParameters(String keys);

public static native int setDeviceConnectionState(int device, int state, String device_address);
public static native int getDeviceConnectionState(int device, String device_address);

public static native int setForceUse(int usage, int config);
public static native int getForceUse(int usage);

public static native int setPhoneState(int state);
public static native int setRingerMode(int mode, int mask);

public static native int initStreamVolume(int stream, int indexMin, int indexMax);
public static native int setStreamVolumeIndex(int stream, int index);
public static native int getStreamVolumeIndex(int stream);







