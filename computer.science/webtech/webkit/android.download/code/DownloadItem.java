/**
 * This class customizes RelativeLayout to directly handle clicks on the left part of the view and
 * treat them at clicks on the checkbox. This makes rapid selection of many items easier. This class
 * also keeps an ID associated with the currently displayed download and notifies a listener upon
 * selection changes with that ID.
 */
public class DownloadItem extends RelativeLayout implements Checkable {

    private CheckBox mCheckBox;
    private long mDownloadId;
    private String mFileName;
    private String mMimeType;
    private DownloadList mDownloadList;
    private int mPosition;

    public void setData(long downloadId, int position, String fileName, String mimeType) {
        mDownloadId = downloadId;
        mPosition = position;
        mFileName = fileName;
        mMimeType = mimeType;
        if (mDownloadList.isDownloadSelected(downloadId)) {
            setChecked(true);
        }
    }

    @Override
    public void setChecked(boolean checked) {
        mCheckBox.setChecked(checked);
        mDownloadList.onDownloadSelectionChanged(mDownloadId, mCheckBox.isChecked(),
                mFileName, mMimeType);
        mDownloadList.getCurrentView().setItemChecked(mPosition, mCheckBox.isChecked());
    }
}
