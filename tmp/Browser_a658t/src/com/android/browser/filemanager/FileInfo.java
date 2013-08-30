/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */
package com.android.browser.filemanager;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author haoanbang
 * 
 *         FileColumns._ID, FileColumns.DATA, FileColumns.SIZE,
 *         FileColumns.DATE_MODIFIED, FileColumns.PARENT, FileColumns.MIME_TYPE
 */
public class FileInfo implements Parcelable {
	public String fileName;

	public String filePath;

	public long fileSize;

	public boolean isDir;

	public int count;

	public long modifiedDate;

	public boolean selected;

	public boolean canRead;

	public boolean canWrite;

	public boolean isHidden;

	public long dbId;

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(fileName);
		dest.writeString(filePath);
		dest.writeLong(fileSize);
		dest.writeValue(isDir);
		dest.writeInt(count);
		dest.writeLong(modifiedDate);
		dest.writeValue(selected);
		dest.writeValue(canRead);
		dest.writeValue(canWrite);
		dest.writeValue(isHidden);
		dest.writeLong(dbId);
	}

	public static final Parcelable.Creator<FileInfo> CREATOR = new Parcelable.Creator<FileInfo>() {

		@Override
		public FileInfo createFromParcel(Parcel source) {
			FileInfo fileInfo = new FileInfo();
			fileInfo.fileName = source.readString();
			fileInfo.filePath = source.readString();
			fileInfo.fileSize = source.readLong();
			fileInfo.isDir = (Boolean) source.readValue(Boolean.class.getClassLoader());
			fileInfo.count = source.readInt();
			fileInfo.modifiedDate = source.readLong();
			fileInfo.selected = (Boolean) source.readValue(Boolean.class.getClassLoader());
			fileInfo.canRead = (Boolean) source.readValue(Boolean.class.getClassLoader());
			fileInfo.canWrite = (Boolean) source.readValue(Boolean.class.getClassLoader());
			fileInfo.isHidden = (Boolean) source.readValue(Boolean.class.getClassLoader());
			fileInfo.dbId = source.readLong();
			return fileInfo;
		}

		@Override
		public FileInfo[] newArray(int size) {
			return null;
		}
	};

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (canRead ? 1231 : 1237);
		result = prime * result + (canWrite ? 1231 : 1237);
		result = prime * result + count;
		result = prime * result + (int) (dbId ^ (dbId >>> 32));
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
		result = prime * result + (int) (fileSize ^ (fileSize >>> 32));
		result = prime * result + (isDir ? 1231 : 1237);
		result = prime * result + (isHidden ? 1231 : 1237);
		result = prime * result + (int) (modifiedDate ^ (modifiedDate >>> 32));
		result = prime * result + (selected ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileInfo other = (FileInfo) obj;
		if (filePath == null) {
			if (other.filePath != null)
				return false;
		} else if (!filePath.equals(other.filePath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FileInfo [fileName=" + fileName + ", filePath=" + filePath + ", fileSize=" + fileSize + ", isDir="
				+ isDir + ", count=" + count + ", modifiedDate=" + modifiedDate + ", selected=" + selected
				+ ", canRead=" + canRead + ", canWrite=" + canWrite + ", isHidden=" + isHidden + ", dbId=" + dbId + "]";
	}

	
	
}
