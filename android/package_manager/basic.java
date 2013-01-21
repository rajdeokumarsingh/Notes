Android中的packagemanager分析
http://geyubin.iteye.com/blog/1527246

1.PackageManagerService.java
    重要工具类: PackageParser.java。


4.解析/system/etc/permission下xml文件(framework/base/data/etc/)，
    包括platform.xml和系统支持的各种硬件模块的feature.主要工作：

    这些文件是从下面目录拷贝的
        android/frameworks/base/data/etc/

    ./system_permission/index.txt

    (1)建立uid和gid同permissions之间的对应
        可以指定某个组拥有某个权限，某个uid拥有某个权限

        参见./system_permission/platform.xml

    (2)给一些底层用户分配权限，如给shell授予各种permission权限；
        把一个权限赋予一个UID，当进程使用这个UID运行时，就具备了这个权限。
        参见./system_permission/platform.xml

    (3) library,系统增加的一些应用需要link的扩展jar库；
        参见./system_permission/platform.xml

    (4) feature,系统每增加一个硬件，都要添加相应的feature.
    
    将解析结果放入
        mSystemPermissions,mSharedLibraries,mSettings.mPermissions,
        mAvailableFeatures等几个集合中供系统查询和权限配置使用。


5.检查/data/system/packages.xml是否存在，
    这个文件是在解析apk时由writeLP()创建的

    记录了系统的声明的所有permissions, 已经系统应用如browser等等声明的权限

    记录了每个apk的name,codePath,uid以及该apk声明需要的权限等信息
        参见 ./packages.xml
            com.UCMobile.cmcc

    这些信息主要通过apk的AndroidManifest.xml解析获取，
    解析完apk后将更新信息写入这个文件并保存到flash，
    下次开机直接从里面读取相关信息添加到内存相关列表中。

    当有apk升级，安装或删除时会更新这个文件。


7.启动AppDirObserver线程监测下面目录
    /system/framework,/system/app,/data/app,/data/app-private

    对于目录监听底层通过inotify机制实现
        inotify 是一种文件系统的变化通知机制，
            如文件增加、删除等事件可以立刻让用户态得知

        主要监听add和remove事件。
            当有add event时调用scanPackageLI(File , int , int)处理；
            当有remove event时调用removePackageLI()处理;

8.对于以上几个目录下的apk逐个解析，
    主要是解析每个apk的AndroidManifest.xml文件，
    处理asset/res等资源文件，
    建立起每个apk的配置结构信息，
    并将每个apk的配置信息添加到全局列表进行管理。
    
    调用installer.install()进行安装工作
    检查apk里的dex文件是否需要再优化
        如果需要优化则通过辅助工 具dexopt进行优化处理
    将解析出的componet添加到pkg的对应列表里
    对apk进行签名和证书校验,进行完整性验证


9.将解析的每个apk的信息保存到packages.xml和packages.list文件里，
    packages.list记录了如下数据：
        pkgName，userId，debugFlag，dataPath（包的数据路径）

