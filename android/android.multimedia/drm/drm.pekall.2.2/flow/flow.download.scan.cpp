/*
下载完成后
对于"application/vnd.oma.drm.content"类型的文件, 加密类型
    直接使用media scanner进行扫描

对于"application/vnd.oma.drm.message"类型的文件,like FL,
    调用DrmStore的接口将文件加入到DRM系统
    同时不会使用media scanner进行扫描
*/

////////////////////////////////////////////////////////////////////////////////
// DRM DCF(application/vnd.oma.drm.content) 类型文件下载扫描流程
// 1. DownloadProvider中的处理流程和一般文件下载完全一样
// 2. 在Media Scanner中会调用专门的接口扫描DCF文件
////////////////////////////////////////////////////////////////////////////////
// 下载完成后
// 1. DownloadProvider, ./src/com/android/providers/downloads/DownloadService.java

private boolean scanFile(Cursor cursor, int arrayPos)
    mMediaScannerService.scanFile(info.mFileName, info.mMimeType);
        |
        | into framework
        V
base/media/libstagefright/StagefrightMediaScanner.cpp
    // 1. 扫描文件时, 如果文件的后缀名为".dcf", 调用HandleDCF
    static status_t HandleDCF(const char *filename, MediaScannerClient *client) 
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

////////////////////////////////////////////////////////////////////////////////
// DRM Message (application/vnd.oma.drm.message) 类型文件下载扫描流程
////////////////////////////////////////////////////////////////////////////////
// 下载完成后
// 1. DownloadProvider, 为DRM文件选择保存目录

    // DRM messages should be temporarily stored internally and then 
    // passed to the DRM content provider
    if (DrmRawContent.DRM_MIMETYPE_MESSAGE_STRING   // "application/vnd.oma.drm.message"
            .equalsIgnoreCase(mimeType)) {

        base = Environment.getDownloadCacheDirectory();
        ...

// 2. DownloadProvider, 不会调用Media Scanner扫描DRM Message文件
private boolean shouldScanFile(int arrayPos)
    DownloadInfo info = (DownloadInfo) mDownloads.get(arrayPos);
    return !info.mMediaScanned
        && info.mDestination == Downloads.Impl.DESTINATION_EXTERNAL
        && Downloads.Impl.isStatusSuccess(info.mStatus)
        && !DrmRawContent.DRM_MIMETYPE_MESSAGE_STRING.equalsIgnoreCase(info.mMimeType);


// 3. DownloadProvider, 将DRM Message文件加入到DMR系统中
if (Downloads.Impl.isStatusSuccess(finalStatus) &&
        DrmRawContent.DRM_MIMETYPE_MESSAGE_STRING   // "application/vnd.oma.drm.message"
        .equalsIgnoreCase(mimeType)) { 
    // transfer the file to the DRM content provider 
    File file = new File(filename);
    Intent item = DrmStore.addDrmFile(mContext.getContentResolver(), file, null);
    if (item == null) {
        Log.i("HttpDownload", "unable to add file " + filename + " to DrmProvider");
        finalStatus = Downloads.Impl.STATUS_UNKNOWN_ERROR;
    } else {
        filename = item.getDataString();
        mimeType = item.getType();
    }
    file.delete();
}
    |
    | DrmStore.addDrmFile()
    V 
// 4. DrmStore, DRM Message 文件下载完成后， DownloadProvider或OMADownload会调用接口，将文件加入到DRM系统
    // Utility function for inserting a file into the DRM content provider.
    public static final Intent addDrmFile(ContentResolver cr, FileInputStream fis, String title)

        /*DrmRawContent content = new DrmRawContent(fis, (int) fis.available(),
                DrmRawContent.DRM_MIMETYPE_MESSAGE_STRING);

        // 获取DRM文件的content type, audio, image, ...
        String mimeType = content.getContentType();

        // 查询DRM文件的权限
        DrmRightsManager manager = manager = DrmRightsManager.getInstance();
        DrmRights rights = manager.queryRights(content);
        InputStream stream = content.getContentInputStream(rights);
        long size = stream.available();

        // 将DRM文件信息加入到DRM Provider
        ContentValues values = new ContentValues(3);
        values.put(DrmStore.Columns.TITLE, title);
        values.put(DrmStore.Columns.SIZE, size);
        values.put(DrmStore.Columns.MIME_TYPE, mimeType);
        Uri uri = cr.insert(contentUri, values);

        // DRM文件内容写入到DRM Provider
        os = cr.openOutputStream(uri);
        byte[] buffer = new byte[1000];
        int count;
        while ((count = stream.read(buffer)) != -1)
            os.write(buffer, 0, count);
         */

        new implementation {
            // 1.将DRM Message转化为.dcf
            drmfile = mdirectory + title + ".dcf";
            DrmRawContent content = new DrmRawContent(fis, 
                    (int) fis.available(), DRMMimeType, drmfile);
            String mimeType = content.getContentType();

            // 2. 返回消息给download provider
            Uri contentUri = null;
            contentUri = Uri.parse(drmfile);
            if (contentUri != null) {
                result = new Intent("com.pekall.android.intent.action.DRM_CONTENT_DOWNLOAD_COMPLETE");
                result.setDataAndType(contentUri, mimeType);
            }
        }




