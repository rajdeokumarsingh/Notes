./basic.java
./code_clues.java

./data.system
    /data/system/目录
        扫描apk后生成的xml文件

        packages.xml
            扫描apk后，保存apk包的相关信息, 系统和第三方的都有

        packages.list
            第三方apk的相关信息
        

<<Application Security for the Android Platform>>

Android授权机制扩展
    数据结构的扩展
        Package, parsePackage

        mContext.grantUriPermission

    xml文件的扩展
        PackageManagerService.java
            // 将信息写入到package.xml, package.list以及package-stopped.xml
            updatePermissionsLPw()
            mSettings.writeLPr()



