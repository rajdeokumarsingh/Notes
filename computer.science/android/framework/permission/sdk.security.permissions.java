Security Architecture
    Principle:
        no application, by default, has permission to perform any operations that would adversely impact 
            other applications, the operating system, or the user. 

    Android sandboxes applications from each other,
        applications must explicitly share resources and data. 

        by declaring the permissions they need for additional capabilities not provided by the basic sandbox

    Android has no mechanism for granting permissions dynamically (at run-time) 
        because it complicates the user experience to the detriment of security.

    In particular the Dalvik VM is not a security boundary
    Java, Natvie, hybrid都使用同一种安全机制

Application Signing
    This allows the system 
        to grant or deny applications access to signature-level permissions
            // 声明权限时的级别
            android:protectionLevel
           ./protection.level.java
            file:///home/jiangrui/android/android-sdk/docs/guide/topics/manifest/permission-element.html#plevel

            // XXX: 详细的系统权限声明参见
            frameworks/base/core/res/AndroidManifest.xml

        to grant or deny an application's request to be given the same Linux identity as another application.
            android:sharedUserId
            ./shared.user.id.java
            file:///home/jiangrui/android/android-sdk/docs/guide/topics/manifest/manifest-element.html#uid

User IDs and File Access
    // 应用的UID是由package manager在安装的时候分配的
    At install time, Android gives each package a distinct Linux user ID.
        each package has a distinct UID on a given device.

        On a different device, the same package may have a different UID

    // 安全控制实在process级别实现的
    Security enforcement happens at the process leve
        // 一般情况下，不同的package运行在不同的进程中
        the code of any two packages can not normally run in the same process, 
            since they need to run as different Linux users. 

        // 共享user id
        use the sharedUserId attribute in the AndroidManifest.xml's manifest tag 
            of each package to have them assigned the same user ID

            By doing this, for purposes of security the two packages are then treated as 
                being the same application, with the same user ID and file permissions.

            Note that in order to retain security, only two applications signed with the same signature 
                (and requesting the same sharedUserId) will be given the same user ID.

    // 程序的数据
    Any data stored by an application will be assigned that application's user ID, 
        and not normally accessible to other packages. 

    // 共享程序数据
    When creating a new file with 
        getSharedPreferences(String, int), openFileOutput(String, int), or 
        openOrCreateDatabase(String, int, SQLiteDatabase.CursorFactory), 
        you can use the MODE_WORLD_READABLE and/or MODE_WORLD_WRITEABLE flags to 
            allow any other package to read/write the file.

Using Permissions
    Android程序默认是没有任何权限的。

    申请权限：
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.android.app.myapp" >
        <uses-permission android:name="android.permission.RECEIVE_SMS" />
        ...
    </manifest>
    
    // 权限的赋予:
    At application install time, permissions requested by 
        the application are granted to it by the package installer,

        based on checks against the signatures of the applications declaring 
            those permissions and/or interaction with the user. 

        Often times a permission failure will result in a SecurityException being thrown back to the application.

    // 系统权限检查的时机：
    A particular permission may be enforced at a number of places during your program's operation:
        1. At the time of a call into the system, to prevent an application from executing certain functions.
        2. When starting an activity, to prevent applications from launching activities of other applications.
        3. Both sending and receiving broadcasts, to control who can receive your broadcast or who can send a broadcast to you.
        4. When accessing and operating on a content provider.
        5. Binding to or starting a service.

Declaring and Enforcing Permissions
    // 应用定义自己的权限：

    // an application that wants to control who can start one of its activities 
        // could declare a permission for this operation as follows:
    <manifest xmlns:android="http://schemas.android.com/apk/res/android"
        package="com.me.app.myapp" >
        <permission android:name="com.me.app.myapp.permission.DEADLY_ACTIVITY"
            android:label="@string/permlab_deadlyActivity"
            android:description="@string/permdesc_deadlyActivity"
            android:permissionGroup="android.permission-group.COST_MONEY"
            android:protectionLevel="dangerous" />
        ...
    </manifest>

    // 查询系统中的所有权限
    adb shell pm list permissions 
    adb shell pm list permissions -s

// 权限的检查
Enforcing Permissions in AndroidManifest.xml
    Activity permissions
        checked during 
            1. Context.startActivity() 
            2. Activity.startActivityForResult();

            SecurityException

    Service permissions
        checked during 
            1. Context.startService()
            2. Context.stopService() 
            3. Context.bindService()

            SecurityException

    BroadcastReceiver permissions
        1. Context.sendBroadcast()
            // 谁能够发送广播
            restrict who can send broadcasts to the associated receiver
            The permission is checked after Context.sendBroadcast() returns 
            // XXX: 不会产生SecurityException
            not result in an exception being thrown back to the caller 
            it will just not deliver the intent 

        2. Context.registerReceiver()
            // 谁能够接收广播

        3. By calling Context.sendBroadcast() with a permission string, 
            you require that a receiver's application must hold 
            that permission in order to receive your broadcast.

            Note that both a receiver and a broadcaster can require a permission. 
            When this happens, both permission checks must pass for 
            the Intent to be delivered to the associated target.

    ContentProvider permissions 
        android:readPermission restricts who can read from the provider, 
            Methods requires read permission:
                ContentResolver.query() 

        android:writePermission restricts who can write to it.
            Methods requires write permission:
                ContentResolver.insert()
                ContentResolver.update()
                ContentResolver.delete() 

            // XXX: 拥有write权限不意味着缺省拥有read权限

        URI permissions 
            对某个authority下面的部分path拥有权限。

            when starting an activity or returning a result to an activity, 
                the caller can set 
                    Intent.FLAG_GRANT_READ_URI_PERMISSION 
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION

                This grants the receiving activity permission access the specific data URI in the Intent, 
                    regardless of whether it has any permission to access data in the content provider corresponding to the Intent.

    Other Permission Enforcement
        Context.checkCallingPermission()
            // 在aidl的方法中调用, 查询client进程是否拥有某项权限
            Determine whether the calling process of an IPC 
                you are handling has been granted a particular permission. 

            Call with a desired permission string and it will return an integer indicating 
                whether that permission has been granted to the current calling process

            Note that this can only be used when you are executing a call coming in from another process, 
                usually through an IDL interface published from a service or in some other way given to another process.
                if you are not currently processing an IPC, this function will always fail.

        Context.checkPermission(String, int, int) 
            // 通过pid检查进程的是否拥有权限

        PackageManager.checkPermission(String, String)
            // 通过包名检查进程的是否拥有某项权限

