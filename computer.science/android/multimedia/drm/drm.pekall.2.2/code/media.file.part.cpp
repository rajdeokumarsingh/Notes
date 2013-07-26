// 1. Add dcf file type
base/media/java/android/media/MediaFile.java:176:        addFileType("DCF", FILE_TYPE_DRM, "application/vnd.oma.drm.content");


base/media/libstagefright/StagefrightMediaScanner.cpp
    // 1. 添加了.dcf的文件后缀名

    // 2. 扫描文件时, 调用HandleDCF
    static status_t HandleDCF(const char *filename, MediaScannerClient *client) {

        int fn =DRM_Open(filename);

        // 2.1 获取dcf的meta信息
        DRM_METADATA_T ContentMetaData;
        memset(&ContentMetaData, 0, sizeof(DRM_METADATA_T));
        DRM_GetDRMMetaDataInFile(fn, &ContentMetaData);

        // 2.2 获取并设置dcf的mimetype
        client->setMimeType((char *)ContentMetaData.contentType);

        // 2.3 查询dcf的权限是否可用
            // 如果不可用就不再继续扫描
        if (DRM_IsRightValid(fn) != DRM_SUCCESS)
            return -1;

        DRM_Finalize(fn);

// 封装了媒体文件的操作接口, open, close, read, seek, ...
base/media/libstagefright/FileSource.cpp

    // 1. 对于后缀名为".dcf"文件进行特殊处理
    base/media/libstagefright/FileSource.cpp:42:        mIsDRMFile = true;
    base/media/libstagefright/FileSource.cpp:43:        initDrm();

    // 2.
    void FileSource::initDrm() 
        1. DRM_Initialize

        2. 获取DRM的Metadata
            uint8_t contentID[512];
            uint8_t contentType[256];
            uint8_t rightsIssuerURL[1024];
            uint32_t contentLen;

    // 3. 打开文件时
        DRM_Initialize()

    // 4. 关闭文件时
        DRM_Finalize()

    // 5. 读文件
        if (mIsDRMFile)
            int err = DRM_Seek(mFd, offset + mOffset, SEEK_SET);
            return DRM_Read(mFd, data, size);

    // 6. 获取文件size
    if (mIsDRMFile) 
        DRM_METADATA_T ContentMetaData;
        memset(&ContentMetaData, 0, sizeof(DRM_METADATA_T));
        DRM_GetDRMMetaDataInFile(mFd, &ContentMetaData);
        *size = (off_t)ContentMetaData.contentLen;

    // 7. 播放文件时需要消费权限
    if (mIsDRMFile) {
        DRM_ConsumeRight(mFd);


base/media/libstagefright/AwesomePlayer.cpp

    // 1. 获取并保存DCF文件的mime type
    // 2. 设置mDrmSource为扩展了DRM的后的FileSource
        // 音频数据就是从mDrmSource中获取的
    status_t AwesomePlayer::setDataSource(const char *uri, 
            const KeyedVector<String8, String8> *headers)

        if (strncmp(uri + len - 4, ".dcf", 4) == 0) {
            int fd = open(uri,O_RDONLY);   

            if (DRM_IsDRMFile(fd) == 1) {  
                mIsDRM = true;
                mIsDRMFirstPlay = false;                

                if (DRM_Initialize(fd) ==1) {  
                    // 查询dcf文件的权限, 如果权限消费完毕，返回错误
                    if (DRM_CheckLicense(fd) != 1) {
                        DRM_Finalize(fd);              
                        return UNKNOWN_ERROR;          

                    // 获取文件的mime type
                    DRM_METADATA_T ContentMetaData;
                    memset(&ContentMetaData, 0, sizeof(DRM_METADATA_T));
                    if (DRM_GetDRMMetaDataInFile(fd, &ContentMetaData) ==0) {
                        return UNKNOWN_ERROR;

                    mMimeType = strdup((char *)ContentMetaData.contentType);
                    DRM_Finalize(fd);

    // 3. 创建extractor的时候设置drm的mimetype
    status_t AwesomePlayer::finishSetDataSource_l()
#ifdef PEKALL_DRM_SUPPORT
        if (mIsDRM) {
            extractor = MediaExtractor::Create(dataSource, mMimeType);
            mDrmSource = dataSource;
        }
        else
#endif
        extractor = MediaExtractor::Create(dataSource);


    // 4. 播放之前, 消费权限
    status_t AwesomePlayer::play_l()
        if (mIsDRM && !mIsDRMFirstPlay) {
            mIsDRMFirstPlay = true;
            mDrmSource->consumeDrmRight();
                ->FileSource::consumeDrmRight();
                    ->DRM_ConsumeRight(mFd);

// 设置铃声时, 检查是否有访问DRM的权限, ACCESS_DRM_PERMISSION
base/packages/SettingsProvider/src/com/android/providers/settings/SettingsProvider.java:42:import android.provider.DrmStore;
    // Check DRM access permission here, since once we
    // do the below call the DRM will be checking our
    // permission, not our caller's permission
    DrmStore.enforceAccessDrmPermission(context);
 
// 从drm provider中获取铃声的title
base/media/java/android/media/Ringtone.java:106:     * Returns a human-presentable title for ringtone. Looks in media and DRM
base/media/java/android/media/Ringtone.java:136:                if (DrmStore.AUTHORITY.equals(authority)) {
base/media/java/android/media/Ringtone.java:137:                    cursor = res.query(uri, DRM_COLUMNS, null, null, null);

// 1. 设置ringtone picker是否包括drm文件
// 2. 从drm store中查询文件的信息
base/media/java/android/media/RingtoneManager.java:233:    private boolean mIncludeDrm;
base/media/java/android/media/RingtoneManager.java:495:    private Cursor getDrmRingtones() {
base/media/java/android/media/RingtoneManager.java:498:                DrmStore.Audio.CONTENT_URI, DRM_COLUMNS,
base/media/java/android/media/RingtoneManager.java:499:                null, null, DrmStore.Audio.TITLE);

// 处理应用程序中包括DRM部分?
base/services/java/com/android/server/PackageManagerService.java:217:    // This is the object monitoring mDrmAppPrivateInstallDir.


