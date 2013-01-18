android:sharedUserId

The name of a Linux user ID that will be shared with other applications. 
By default, Android assigns each application its own unique user ID. 

1. However, if this attribute is set to the same value for two or more applications, 
    they will all share the same ID
2. provided that they are also signed by the same certificate. 
3. Application with the same user ID can access each other's data and, 
    if desired, run in the same process.

// 举例, DownloadProvider
{
    // 如Download就是用的media的UID
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.android.providers.downloads"
        android:sharedUserId="android.media">

    // 并且运行在media进程
    <application android:process="android.process.media"
    android:label="@string/app_label">

    // 并且使用media的certificate
    LOCAL_PACKAGE_NAME := DownloadProvider
    LOCAL_CERTIFICATE := media
}
