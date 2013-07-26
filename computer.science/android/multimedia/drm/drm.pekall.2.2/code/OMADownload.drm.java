// 下载完成后
// 1. 为DRM文件选择保存目录

    // DRM messages should be temporarily stored internally and then 
    // passed to the DRM content provider
    if (DrmRawContent.DRM_MIMETYPE_MESSAGE_STRING   // "application/vnd.oma.drm.message"
            .equalsIgnoreCase(mimeType)) {

        base = Environment.getDownloadCacheDirectory();
        ...


// 2. 将DRM文件加入到DMR系统中
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

// 3. 不用扫描DRM文件
private boolean shouldScanFile(int arrayPos) {
    DownloadInfo info = (DownloadInfo) mDownloads.get(arrayPos);
    return !info.mMediaScanned
        && info.mDestination == Downloads.Impl.DESTINATION_EXTERNAL
        && Downloads.Impl.isStatusSuccess(info.mStatus)
        && !DrmRawContent.DRM_MIMETYPE_MESSAGE_STRING.equalsIgnoreCase(info.mMimeType);
}

