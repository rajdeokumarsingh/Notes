

// FIXME: 点击/长按非check box区域时的事件处理

public class DownloadItem extends RelativeLayout implements Checkable

    // 对应 ./download_list_item.xml

    // 下载项对应的数据
    {
        // COLUMN_ID
        private long mDownloadId;
        // COLUMN_STATUS
        private int mStatus;
        // COLUMN_LOCAL_FILE_NAME
        private String mFileName;
        // COLUMN_MEDIA_TYPE
        private String mMimeType;
    }

    // 指向DownloadItem的container
    private DownloadList mDownloadList;

    // UI组件和状态
    {
        // 选中下载项的check box
        private CheckBox mCheckBox;

        // item在adapter中的id
        private int mPosition;
    }

    // 获取checkbox的区域
    private void initialize() {
        if (CHECKMARK_AREA == -1) {
            CHECKMARK_AREA = getResources().getDimensionPixelSize(
                    R.dimen.checkmark_area);
        }                                                                     
    }  

    // 获取check box
    @Override                                                                 
    protected void onFinishInflate() {                                        
        super.onFinishInflate();                                              
        mCheckBox = (CheckBox) findViewById(R.id.download_checkbox);          
    }

    public void setData(long downloadId, int position, 
            String fileName, String mimeType, int status) {
        mDownloadId = downloadId;
        mPosition = position;
        mFileName = fileName;
        mMimeType = mimeType;
        mStatus = status;
        if (mDownloadList.isDownloadSelected(downloadId)) {
            setChecked(true);
        }
    }

    // 只有点击check box的区域，才会toggle check box
    public boolean onTouchEvent(MotionEvent event)

    // 通知DownloadList, 本item被选中了
    @Override
    public void setChecked(boolean checked) {
        mCheckBox.setChecked(checked);
        mDownloadList.onDownloadSelectionChanged(
                mDownloadId, mCheckBox.isChecked(),
                mFileName, mMimeType, mStatus);
        mDownloadList.getCurrentView().setItemChecked(
                mPosition, mCheckBox.isChecked());
    }




