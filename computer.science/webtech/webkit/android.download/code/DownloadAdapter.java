/**
 * List adapter for Cursors returned by {@link DownloadManager}.
 */
public class DownloadAdapter extends CursorAdapter {
    private final DownloadList mDownloadList;
    private Cursor mCursor;

    private final int mTitleColumnId;
    private final int mDescriptionColumnId;
    private final int mStatusColumnId;
    private final int mReasonColumnId;
    private final int mTotalBytesColumnId;
    private final int mMediaTypeColumnId;
    private final int mDateColumnId;
    private final int mIdColumnId;
    private final int mFileNameColumnId;
    private final int mCurrentBytesColumnId;
    private final int mErrorMsg;

    public DownloadAdapter(DownloadList downloadList, Cursor cursor) {
        super(downloadList, cursor);
        mDownloadList = downloadList;
        mCursor = cursor;

        mIdColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_ID);
        mTitleColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TITLE);
        mDescriptionColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_DESCRIPTION);
        mStatusColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS);
        mReasonColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_REASON);
        mTotalBytesColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
        mMediaTypeColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_MEDIA_TYPE);
        mDateColumnId =
            cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP);
        mFileNameColumnId =
            cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_FILENAME);
        mCurrentBytesColumnId =
            cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
        mErrorMsg = 
            cursor.getColumnIndexOrThrow(Downloads.Impl.COLUMN_ERROR_MSG);
    }

    void refreshData() {
        if (mCursor.isClosed()) {
            return;
        }
        mCursor.requery();
    }
    public View newView() {
        final DownloadItem view = (DownloadItem) LayoutInflater.from(mDownloadList)
            .inflate(R.layout.download_list_item, null);
        view.setDownloadListObj(mDownloadList);
        return view;
    }
    public void bindView(View convertView, int position) {
        // update UI according the data
    }
}
