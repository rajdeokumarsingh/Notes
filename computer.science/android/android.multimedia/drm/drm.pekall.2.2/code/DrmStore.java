/**
 * The DRM provider contains forward locked DRM content.
 * @hide
 */
public final class DrmStore

    public interface Columns extends BaseColumns
        // The data stream for the file
        public static final String DATA = "_data";
        public static final String SIZE = "_size";
        public static final String TITLE = "title";
        public static final String MIME_TYPE = "mime_type";

    public interface Images extends Columns {
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/images");

    public interface Audio extends Columns {
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/audio");

    // DRM文件下载完成后， DownloadProvider或OMADownload会调用接口，将文件加入到DRM系统
    // Utility function for inserting a file into the DRM content provider.
    public static final Intent addDrmFile(ContentResolver cr, FileInputStream fis, String title)

        DrmRawContent content = new DrmRawContent(fis, (int) fis.available(),
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


