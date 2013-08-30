/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */

package com.android.browser.filemanager;

import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore.Files.FileColumns;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video;
import android.util.Log;
import android.widget.ImageView;

import com.android.browser.filemanager.FileCategoryHelper.FileCategory;
import com.android.browser.filemanager.utils.Util;

public class FileIconLoader implements Callback {

	private static final String LOADER_THREAD_NAME = "FileIconLoader";

	private static final int MESSAGE_REQUEST_LOADING = 1;

	private static final int MESSAGE_ICON_LOADED = 2;

	private static abstract class ImageHolder {
		public static final int NEEDED = 0;

		public static final int LOADING = 1;

		public static final int LOADED = 2;

		int state;

		public static ImageHolder create(FileCategory cate) {
			switch (cate) {
			case Apk:
				return new DrawableHolder();
			case Picture:
			case Video:
				return new BitmapHolder();
			}

			return null;
		};

		public abstract boolean setImageView(ImageView v);

		public abstract boolean isNull();

		public abstract void setImage(Object image);
	}

	private static class BitmapHolder extends ImageHolder {
		SoftReference<Bitmap> bitmapRef;

		@Override
		public boolean setImageView(ImageView v) {
			if (bitmapRef.get() == null)
				return false;
			v.setImageBitmap(bitmapRef.get());
			return true;
		}

		@Override
		public boolean isNull() {
			return bitmapRef == null;
		}

		@Override
		public void setImage(Object image) {
			bitmapRef = image == null ? null : new SoftReference<Bitmap>((Bitmap) image);
		}
	}

	private static class DrawableHolder extends ImageHolder {
		SoftReference<Drawable> drawableRef;

		@Override
		public boolean setImageView(ImageView v) {
			if (drawableRef.get() == null)
				return false;

			v.setImageDrawable(drawableRef.get());
			return true;
		}

		@Override
		public boolean isNull() {
			return drawableRef == null;
		}

		@Override
		public void setImage(Object image) {
			drawableRef = image == null ? null : new SoftReference<Drawable>((Drawable) image);
		}
	}

	private final static ConcurrentHashMap<String, ImageHolder> mImageCache = new ConcurrentHashMap<String, ImageHolder>();

	private final ConcurrentHashMap<ImageView, FileId> mPendingRequests = new ConcurrentHashMap<ImageView, FileId>();

	private final Handler mMainThreadHandler = new Handler(this);

	private LoaderThread mLoaderThread;

	private boolean mLoadingRequested;

	private boolean mPaused;

	private final Context mContext;

	private IconLoadFinishListener iconLoadListener;

	public FileIconLoader(Context context, IconLoadFinishListener l) {
		mContext = context;
		iconLoadListener = l;
	}

	public static class FileId {
		public String mPath;

		public long mId; // database id

		public FileCategory mCategory;

		public FileId(String path, long id, FileCategory cate) {
			mPath = path;
			mId = id;
			mCategory = cate;
		}
	}

	public abstract static interface IconLoadFinishListener {
		void onIconLoadFinished(ImageView view);
	}

	public boolean loadIcon(ImageView view, String path, long id, FileCategory cate) {
		boolean loaded = loadCachedIcon(view, path, cate);
		if (loaded) {
			mPendingRequests.remove(view);
		} else {
			FileId p = new FileId(path, id, cate);
			mPendingRequests.put(view, p);
			if (!mPaused) {
				requestLoading();
			}
		}
		return loaded;
	}

	public void cancelRequest(ImageView view) {
		mPendingRequests.remove(view);
	}

	private boolean loadCachedIcon(ImageView view, String path, FileCategory cate) {
		ImageHolder holder = mImageCache.get(path);

		if (holder == null) {
			holder = ImageHolder.create(cate);
			if (holder == null)
				return false;

			mImageCache.put(path, holder);
		} else if (holder.state == ImageHolder.LOADED) {
			if (holder.isNull()) {
				return true;
			}

			if (holder.setImageView(view)) {
				return true;
			}
		}

		holder.state = ImageHolder.NEEDED;
		return false;
	}

	public long getDbId(String path, boolean isVideo) {
		String volumeName = "external";
		Uri uri = isVideo ? Video.Media.getContentUri(volumeName) : Images.Media.getContentUri(volumeName);
		String selection = FileColumns.DATA + "=?";
		;
		String[] selectionArgs = new String[] { path };

		String[] columns = new String[] { FileColumns._ID, FileColumns.DATA };

		Cursor c = mContext.getContentResolver().query(uri, columns, selection, selectionArgs, null);
		if (c == null) {
			return 0;
		}
		long id = 0;
		if (c.moveToNext()) {
			id = c.getLong(0);
		}
		c.close();
		return id;
	}

	public void stop() {
		pause();

		if (mLoaderThread != null) {
			mLoaderThread.quit();
			mLoaderThread = null;
		}

		clear();
	}

	public void clear() {
		mPendingRequests.clear();
		mImageCache.clear();
	}

	public void pause() {
		mPaused = true;
	}

	public void resume() {
		mPaused = false;
		if (!mPendingRequests.isEmpty()) {
			requestLoading();
		}
	}

	private void requestLoading() {
		if (!mLoadingRequested) {
			mLoadingRequested = true;
			mMainThreadHandler.sendEmptyMessage(MESSAGE_REQUEST_LOADING);
		}
	}

	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case MESSAGE_REQUEST_LOADING: {
			mLoadingRequested = false;
			if (!mPaused) {
				if (mLoaderThread == null) {
					mLoaderThread = new LoaderThread();
					mLoaderThread.start();
				}

				mLoaderThread.requestLoading();
			}
			return true;
		}

		case MESSAGE_ICON_LOADED: {
			if (!mPaused) {
				processLoadedIcons();
			}
			return true;
		}
		}
		return false;
	}

	private void processLoadedIcons() {
		Iterator<ImageView> iterator = mPendingRequests.keySet().iterator();
		while (iterator.hasNext()) {
			ImageView view = iterator.next();
			FileId fileId = mPendingRequests.get(view);
			boolean loaded = loadCachedIcon(view, fileId.mPath, fileId.mCategory);
			if (loaded) {
				iterator.remove();
				iconLoadListener.onIconLoadFinished(view);
			}
		}

		if (!mPendingRequests.isEmpty()) {
			requestLoading();
		}
	}

	private class LoaderThread extends HandlerThread implements Callback {
		private Handler mLoaderThreadHandler;

		public LoaderThread() {
			super(LOADER_THREAD_NAME);
		}

		public void requestLoading() {
			if (mLoaderThreadHandler == null) {
				mLoaderThreadHandler = new Handler(getLooper(), this);
			}
			mLoaderThreadHandler.sendEmptyMessage(0);
		}

		public boolean handleMessage(Message msg) {
			Iterator<FileId> iterator = mPendingRequests.values().iterator();
			while (iterator.hasNext()) {
				FileId id = iterator.next();
				ImageHolder holder = mImageCache.get(id.mPath);
				if (holder != null && holder.state == ImageHolder.NEEDED) {
					// Assuming atomic behavior
					holder.state = ImageHolder.LOADING;
					switch (id.mCategory) {
					case Apk:
						Drawable icon = Util.getApkIcon(mContext, id.mPath);
						holder.setImage(icon);
						break;
					case Picture:
					case Video:
						boolean isVideo = id.mCategory == FileCategory.Video;
						if (id.mId == 0)
							id.mId = getDbId(id.mPath, isVideo);
						if (id.mId == 0) {
							Log.e("FileIconLoader", "Fail to get dababase id for:" + id.mPath);
						}
						holder.setImage(isVideo ? getVideoThumbnail(id.mId) : getImageThumbnail(id.mId));
						break;
					}

					holder.state = BitmapHolder.LOADED;
					mImageCache.put(id.mPath, holder);
				}
			}

			mMainThreadHandler.sendEmptyMessage(MESSAGE_ICON_LOADED);
			return true;
		}

		private static final int MICRO_KIND = 3;

		private Bitmap getImageThumbnail(long id) {
			if (id == -1)
				return null;
			return Images.Thumbnails.getThumbnail(mContext.getContentResolver(), id, MICRO_KIND, null);
		}

		private Bitmap getVideoThumbnail(long id) {
			if (id == -1)
				return null;
			return Video.Thumbnails.getThumbnail(mContext.getContentResolver(), id, MICRO_KIND, null);
		}
	}
}
