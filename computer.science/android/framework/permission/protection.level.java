// 声明权限时的级别
file:///home/jiangrui/android/android-sdk/docs/guide/topics/manifest/permission-element.html#plevel

android:protectionLevel

安全级别：
    "normal" < "dangerous" < "signature" == "signatureOrSystem"

"normal" {
    The default value. 
    A lower-risk permission, with minimal risk 

    // 用户安装应用的时候，没有完全显示出来(隐藏在"全部显示中")
    The system automatically grants this type of permission to a requesting application at installation, 
        without asking for the user's explicit approval 
        (though the user always has the option to review these permissions before installing). 

    // 例如安装一个"百度应用", 隐藏在"全部显示中"的权限如下:
    {
        // 如查看电池电量, 
        <!-- Allows an application to collect battery statistics -->
        <permission android:name="android.permission.BATTERY_STATS"
            android:label="@string/permlab_batteryStats"
            android:description="@string/permdesc_batteryStats"
            android:protectionLevel="normal" />

        // 如查看wifi状态, 隐藏在"全部显示中"
        <!-- Allows applications to access information about Wi-Fi networks -->
        <permission android:name="android.permission.ACCESS_WIFI_STATE"
            android:permissionGroup="android.permission-group.NETWORK"
            android:protectionLevel="normal"
            android:description="@string/permdesc_accessWifiState"
            android:label="@string/permlab_accessWifiState" />

        // 如查看网络状态, 隐藏在"全部显示中"
        <!-- Allows applications to access information about networks -->
        <permission android:name="android.permission.ACCESS_NETWORK_STATE"
            android:permissionGroup="android.permission-group.NETWORK"
            android:protectionLevel="normal"
            android:description="@string/permdesc_accessNetworkState"
            android:label="@string/permlab_accessNetworkState" />
    }
}

"dangerous" {
    A higher-risk permission
    // 需要用户在安装时看到，并确认
    the system may not automatically grant it to the requesting application
    may be displayed to the user and require confirmation before proceeding

    // 例如安装一个"百度应用", 显示给用户的权限如下:
    {
        <!-- Allows an application to read the user's contacts data. -->
            <permission android:name="android.permission.READ_CONTACTS"
            android:permissionGroup="android.permission-group.PERSONAL_INFO"
            android:protectionLevel="dangerous"
            android:label="@string/permlab_readContacts"
            android:description="@string/permdesc_readContacts" />

        <!-- Allows an application to record audio -->
        <permission android:name="android.permission.RECORD_AUDIO"
            android:permissionGroup="android.permission-group.HARDWARE_CONTROLS"
            android:protectionLevel="dangerous"
            android:label="@string/permlab_recordAudio"
            android:description="@string/permdesc_recordAudio" />

        <!-- Allows an application to access coarse (e.g., Cell-ID, WiFi) location -->
        <permission android:name="android.permission.ACCESS_COARSE_LOCATION"
            android:permissionGroup="android.permission-group.LOCATION"
            android:protectionLevel="dangerous"
            android:label="@string/permlab_accessCoarseLocation"
            android:description="@string/permdesc_accessCoarseLocation" />
    }
}

"signature" {
    // 仅仅允许相同签名的应用访问
    grants only if the requesting application is signed with the same certificate as 
        the application that declared the permission
    If the certificates match, the system automatically grants the permission 
        without notifying the user or asking for the user's explicit approval. 

    // 下载中的权限
    {
        <!-- Allows to send download completed intents -->
        <permission android:name="android.permission.SEND_DOWNLOAD_COMPLETED_INTENTS"
            android:label="@string/permlab_downloadCompletedIntent"
            android:description="@string/permdesc_downloadCompletedIntent"
            android:protectionLevel="signature" />

        <!-- Allows an app to access all downloads in the system via the /all_downloads/ URIs.  
            The protection level could be relaxed in the future to support third-party download
            managers. -->
        <permission android:name="android.permission.ACCESS_ALL_DOWNLOADS"
            android:label="@string/permlab_accessAllDownloads"
            android:description="@string/permdesc_accessAllDownloads"
            android:protectionLevel="signature"/>
    }
}

"signatureOrSystem" {
    grants only to applications that 
        1. are in the Android system image or that 
        2. are signed with the same certificates as those in the system image. 
        // 对于"signatureOrSystem", 
            // 1. 所有system image中的应用都能够访问
            // 2. 和system image中应用签名相同的应用都能够访问
                // system image中应用的签名有多种，如platform, system
                // 匹配任意一种即可

        // FIXME: Please avoid using this option
        Because the "signature" protection level should be sufficient for 
            most needs and works regardless of exactly where applications are installed. 

        The "signatureOrSystem" permission is used for certain special situations 
            where multiple vendors have applications built into a system image 
            and need to share specific features explicitly because they are being built together. 

        <!-- Allows access to the Download Manager -->
        <permission android:name="android.permission.ACCESS_DOWNLOAD_MANAGER"
            android:label="@string/permlab_downloadManager"
            android:description="@string/permdesc_downloadManager"
            android:protectionLevel="signatureOrSystem" />
}

