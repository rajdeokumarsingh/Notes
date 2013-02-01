Android Platform Security Architecture

// What to protect
1. Protect user data
2. Protect system resources (including the network)
3. Provide application isolation

// Key security features:
1. Robust security at the OS level through the Linux kernel
2. Mandatory application sandbox for all applications
3. Secure interprocess communication
4. Application signing
5. Application-defined and user-granted permissions


System and Kernel Level Security
    1. A user-based permissions model
    2. Process isolation
    3. Extensible mechanism for secure IPC
    4. The ability to remove unnecessary and potentially insecure parts of the kernel

    Prevents user A from reading user B's files
    Ensures that user A does not exhaust user B's memory
    Ensures that user A does not exhaust user B's CPU resources
    Ensures that user A does not exhaust user B's devices (e.g. telephony, GPS, bluetooth)

The Application Sandbox
    Android的安全机制是基于进程的。

    Package Manager安装了一个apk后，会为该apk创建一个UID。
        该apk的UID就和权限，签名信息绑定, 被记录到系统中 // TODO: where?
        该apk运行时，UID也和程序的进程绑定。
        
        当该程序的进程需要访问系统资源时，如启动activity, service, 访问content provider时
            系统服务可以通过其pid获取他的UID, 然后通过UID检测该应用的权限。

    The Android system assigns a unique user ID (UID) to each Android application 
        and runs it as that user in a separate process. 

        This approach is different from other operating systems 
            (including the traditional Linux configuration), 
            where multiple applications run with the same user permissions.

    This sets up a kernel-level Application Sandbox.

    // For example:
    If application A tries to do something malicious like read application B's data or 
        dial the phone without permission (which is a separate application), 
        then the operating system protects against this

System Partition and Safe Mode
    The system partition contains Android's kernel as well as 
        the operating system libraries, application runtime, application framework, and applications. 

    This partition is set to read-only. 

Filesystem Permissions
    一个应用的数据文件默认其他应用（UID不同）无法访问。

Filesystem Encryption

Rooting of Devices
    only the kernel and a small subset of the core applications run with root permissions. 

    如何防止被盗的手机的密码被人获取到


The Android Permission Model: Accessing Protected APIs
These protected APIs include:
    Camera functions
    Location data (GPS)
    Bluetooth functions
    Telephony functions
    SMS/MMS functions
    Network/data connections

Interprocess Communication




