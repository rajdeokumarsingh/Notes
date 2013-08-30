/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */
package com.android.browser.filemanager;

import java.util.ArrayList;

public interface IFileOperater {
	public ArrayList<FileInfo> getAllFileInfos();

	public void onDataChange();

	public void onDataReflush();

	public int getTotalCount();

	public FileInfo getDestFileInfo();

	public void setOperationBarVisibility(boolean visibility);

	public void go2Folder(int cardId, FileInfo fileInfo);

	public void onSDCardStateChange(boolean isMounted);

	public void onUpdateMenu(boolean visibility, int position);

	public void removeDatas(ArrayList<FileInfo> datas);
	
	public void addDatas(ArrayList<FileInfo> datas);
	
	public void replaceDatas(ArrayList<FileInfo> oDatas,ArrayList<FileInfo> dDatas);
}
