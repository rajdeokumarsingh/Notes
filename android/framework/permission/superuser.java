Android原生su分析:
    基本原理：
        对于root和shell用户，可通过setuid和setgid的切换到别的uid执行各种命令

    代码目录android/system/extra/su
        生成可执行文件/system/xbin/su

    代码分析./su.orig.c


    使用方法
    $su
    $su 1000
    $su root ls

    /system/xbin/su
    -rwsr-xr-x root     root         5432 2013-01-08 15:26 su
    06755
    注意上面权限的s位(set user ID upon execution)

superuser分析：
    基本原理：
        superuser修改了su, 如果一个应用请求root权限，会通过am启动一个activity去提示用户。
        告诉用户那个程序在请求root权限。用户也可选择允许，拒绝和总是允许。

        允许和总是允许后，su会setuid和setgid去切换uid。总是允许后还会将该用户加入到白名带中。

    源代码下载：
        http://code.google.com/p/superuser/
        http://androidsu.com/superuser/

    源代码分析：
        ./su.superuser.c

    http://blog.csdn.net/jinzhu117/article/details/8233255
    http://blog.csdn.net/weihongcsu/article/details/7040354

    https://github.com/wendal/android_su
    这是Android下获取root用户权限的程序,修改自Superuser.apk的su源代码
    不需要任何手工确认,任何程序均可获取通过本程序获取root权限
    必须设置su程序的权限为6777,并确保所在分区支持suid
    chmod 6777 su

    // java程序运行su
    Process process = null;
    try {
        process = Runtime.getRuntime().exec("su"); // 执行su, su命令行中就会以root执行sh

        DataOutputStream os = new DataOutputStream(process.getOutputStream());
        os.writeBytes("rm /data/local/zz\n");
        os.writeBytes("touch /data/local/zzz\n");
        os.writeBytes("exit\n");
        os.flush();
    } catch (IOException e) {
        Log.e("MainActivity", "", e);
    }

adb push su /system/xbin
chmod 4755 su


