

// ################################################################################
// init wifi service
// ################################################################################
ConnectivityService(Context context) 
    WifiStateTracker wst = new WifiStateTracker(context, mHandler);
    WifiService wifiService = new WifiService(context, wst);
    ServiceManager.addService(Context.WIFI_SERVICE, wifiService);

    wifiService.startWifi();
    mNetTrackers[ConnectivityManager.TYPE_WIFI] = wst;

    wst.startMonitoring();

    // WifiService 和 WifiMonitor 是整个模块的核心。
        // WifiService 负责启动关闭 wpa_supplicant、启动关闭WifiMonitor监视线程和把命令下发给wpa_supplicant

    // WifiMonitor 则负责从 wpa_supplicant 接收事件通知
        // WifiStateTracker 会创建 WifiMonitor 接收来自底层的事件

// ################################################################################
// 连接 AP
// ################################################################################

1. 使能 WIFI
WirelessSettings 在初始化的时候配置了由 WifiEnabler 来处理 Wifi 按钮,
public class WirelessSettings extends PreferenceActivity
    onCreate()
         CheckBoxPreference wifi = (CheckBoxPreference) findPreference(KEY_TOGGLE_WIFI);
         mWifiEnabler = new WifiEnabler(this, wifi);

public class WifiEnabler implements Preference.OnPreferenceChangeListener
    // wifi 开关 
    public boolean onPreferenceChange
        if (mWifiManager.setWifiEnabled(enable))
            |
            V
base/wifi/java/android/net/wifi/WifiManager.java
        |
        v
base/services/java/com/android/server/WifiService.java
    sendEnableMessage(enable, true, Binder.getCallingUid());
        |
        V
    case MESSAGE_ENABLE_WIFI:
    setWifiEnabledBlocking(true, msg.arg1 == 1, msg.arg2);
        setWifiEnabledState(enable ? WIFI_STATE_ENABLING : WIFI_STATE_DISABLING, uid);
        if (enable) {
            if (!mWifiStateTracker.loadDriver()) {
                Slog.e(TAG, "Failed to load Wi-Fi driver.");
                setWifiEnabledState(WIFI_STATE_UNKNOWN, uid);
                return false;
            if (!mWifiStateTracker.startSupplicant()) {
                mWifiStateTracker.unloadDriver();
                Slog.e(TAG, "Failed to start supplicant daemon.");
                setWifiEnabledState(WIFI_STATE_UNKNOWN, uid);
                return false;
            registerForBroadcasts();
            mWifiStateTracker.startEventLoop();

        persistWifiEnabled(enable);
        setWifiEnabledState(eventualWifiState, uid);
...
// enable 成功后
系统广播消息 WifiManager.WIFI_STATE_CHANGED_ACTION
    |
    V
Settings/src/com/android/settings/wifi/WifiEnabler.java
    if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(action))
        handleWifiStateChanged(intent.getIntExtra( 
            WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN));
                |
                V
private class Scanner extends Handler 
    public void handleMessage(Message message) {
        if (mWifiManager.startScanActive()) {
            |
            V
base/wifi/java/android/net/wifi/WifiManager.java
    public boolean startScanActive()
    public boolean startScan()
        |
        V
base/services/java/com/android/server/WifiService.java
    mWifiStateTracker.scan(forceActive);
        |
        V
base/wifi/java/android/net/wifi/WifiStateTracker.java
    return WifiNative.scanCommand(forceActive);
        |
        V JNI
base/core/jni/android_net_wifi_Wifi.cpp
    static jboolean android_net_wifi_scanCommand(JNIEnv* env, jobject clazz, jboolean forceActive)

    if (forceActive && !sScanModeActive)
        doSetScanMode(true);
    result = doBooleanCommand("SCAN", "OK");
    if (forceActive && !sScanModeActive)
        doSetScanMode(sScanModeActive);
    return result;

wpa_supplicant扫描完成后, 通过控制管道发送消息
    






