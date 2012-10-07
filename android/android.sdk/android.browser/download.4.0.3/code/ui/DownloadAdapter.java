// 为下载项视图提供数据
public class DownloadAdapter extends CursorAdapter 

    // 数据源
    {
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
    }

    // 作为context
    private final DownloadList mDownloadList;

    public View newView() {
        final DownloadItem view = (DownloadItem) LayoutInflater.from(mDownloadList)
            .inflate(R.layout.download_list_item, null);
        view.setDownloadListObj(mDownloadList);
        return view;
    }

    // 从cursor中获取数据，并设置到DownloadItem中
    public void bindView(View convertView, int position);


