
////////////////////////////////////////////////////////////////////////////////
// Browser
// 下载完成后，在Browser下载页面点击dcf文件
////////////////////////////////////////////////////////////////////////////////
    src/com/android/browser/BrowserDownloadPage.java

    private void openOrDeleteCurrentDownload(boolean delete, int position) {

        // 1. 获取文件名和扩展名
        int dataId = mDownloadCursor.getColumnIndexOrThrow(Downloads.Impl._DATA);
        String filename = mDownloadCursor.getString(dataId);
        String ext = MimeTypeMap.getFileExtensionFromUrl(filename);

        // 2. 获取dcf文件内容的mime type
        if ("dcf".equalsIgnoreCase(ext)) {
            String type = getMediaMimeTypeByDataBase(filename);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri data = Uri.parse("file://" + filename);
            intent.setDataAndType(data, type);

            // 3. 打开dcf文件
            startActivity(intent);
            return;                     |
                                        | getMediaMimeTypeByDataBase()
                                        V
        // 从media数据库中获取dcf文件内容的mime type
        private String getMediaMimeTypeByDataBase(String path) {
            // 1. 查询audio表
            String[] cols = new String[] { MediaStore.Audio.Media._ID, };
            String selection = MediaStore.Audio.Media.DATA + " like ?";
            String[] selectArgs = new String[] { path };
            cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cols, selection,
                    selectArgs, null);
            if (cursor != null && cursor.getCount() != 0) {
                mimeType = "audio/*";

            // 2. 查询video表
            String[] cols1 = new String[] { MediaStore.Video.Media._ID, };
            String selection1 = MediaStore.Video.Media.DATA + " like ?";
            String[] selectArgs1 = new String[] { path };
            cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, cols1, selection1,
                    selectArgs1, null);
            if (cursor != null && cursor.getCount() != 0) {
                            mimeType = "video/*";

            return mimeType;


////////////////////////////////////////////////////////////////////////////////
// Framework/StageFright
////////////////////////////////////////////////////////////////////////////////

base/media/libstagefright/AwesomePlayer.cpp

    // 1. 获取并保存DCF文件的mime type
    // 2. 设置mDrmSource为扩展了DRM的后的FileSource
        // 音频数据就是从mDrmSource中获取的
    status_t AwesomePlayer::setDataSource(const char *uri, 
            const KeyedVector<String8, String8> *headers)

        // uri : /mnt/sdcard/browser_download/ByeByeBye.dcf
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




