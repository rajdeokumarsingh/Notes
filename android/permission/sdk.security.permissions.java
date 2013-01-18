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
        // 一般不同的package运行在不同的进程中
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

    adb shell pm list permissions 
    adb shell pm list permissions -s
    // TODO:


// 权限的检查
Enforcing Permissions in AndroidManifest.xml
    Activity permissions
        checked during 
            1. Context.startActivity() 
            2. Activity.startActivityForResult();

    Service permissions
        checked during 
            1. Context.startService()
            2. Context.stopService() 
            3. Context.bindService()

    BroadcastReceiver permissions
        The permission is checked after Context.sendBroadcast() returns,

    ContentProvider permissions 
        android:readPermission restricts who can read from the provider, 
            Using ContentResolver.query() requires the read permission; 

        android:writePermission restricts who can write to it.
            using ContentResolver.insert(), ContentResolver.update(), ContentResolver.delete() 

        URI permissions 

    Other Permission Enforcement
        Context.checkCallingPermission()

            Call with a desired permission string and it will return an integer indicating 
                whether that permission has been granted to the current calling process

            Note that this can only be used when you are executing a call coming in from another process, 
                usually through an IDL interface published from a service or in some other way given to another process.

        Context.checkPermission(String, int, int) 
        PackageManager.checkPermission(String, String)

URI Permissions
    // TODO:
