package com.sdk.example.app.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.sdk.example.R;

/**
 * This is an example of implementing an application service that can run in the
 * "foreground". It shows how to code this to work well by using the improved
 * Android 2.0 APIs when available and otherwise falling back to the original
 * APIs. Yes: you can take this exact code, compile it against the Android 2.0
 * SDK, and it will against everything down to Android 1.0.
 */
public class ServiceForeground extends Service {
	static final String ACTION_FOREGROUND = "com.example.android.apis.FOREGROUND";
	static final String ACTION_BACKGROUND = "com.example.android.apis.BACKGROUND";

	private static final boolean ENABLE_LOG = true;
	private static final String LOGTAG = "ServiceForeground";

	private static final Class<?>[] mSetForegroundSignature = new Class[] { boolean.class };
	private static final Class<?>[] mStartForegroundSignature = new Class[] {
			int.class, Notification.class };
	private static final Class<?>[] mStopForegroundSignature = new Class[] { boolean.class };

	private NotificationManager mNM;
	private Method mSetForeground;
	private Method mStartForeground;
	private Method mStopForeground;
	private Object[] mSetForegroundArgs = new Object[1];
	private Object[] mStartForegroundArgs = new Object[2];
	private Object[] mStopForegroundArgs = new Object[1];

	void invokeMethod(Method method, Object[] args) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter invokeMethod()");

		try {
			method.invoke(this, args);
		} catch (InvocationTargetException e) {

			// Should not happen.
			Log.w("ApiDemos", "Unable to invoke method", e);
		} catch (IllegalAccessException e) {

			// Should not happen.
			Log.w("ApiDemos", "Unable to invoke method", e);
		}
	}

	/**
	 * This is a wrapper around the new startForeground method, using the older
	 * APIs if it is not available.
	 */
	void startForegroundCompat(int id, Notification notification) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter startForegroundCompat()");

		// If we have the new startForeground API, then use it.
		if (mStartForeground != null) {
			mStartForegroundArgs[0] = Integer.valueOf(id);
			mStartForegroundArgs[1] = notification;
			invokeMethod(mStartForeground, mStartForegroundArgs);
			return;
		}

		// Fall back on the old API.
		mSetForegroundArgs[0] = Boolean.TRUE;
		invokeMethod(mSetForeground, mSetForegroundArgs);
		mNM.notify(id, notification);
	}

	/**
	 * This is a wrapper around the new stopForeground method, using the older
	 * APIs if it is not available.
	 */
	void stopForegroundCompat(int id) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter stopForegroundCompat()");

		// If we have the new stopForeground API, then use it.
		if (mStopForeground != null) {
			mStopForegroundArgs[0] = Boolean.TRUE;
			invokeMethod(mStopForeground, mStopForegroundArgs);
			return;
		}

		// Fall back on the old API. Note to cancel BEFORE changing the
		// foreground state, since we could be killed at that point.
		mNM.cancel(id);
		mSetForegroundArgs[0] = Boolean.FALSE;
		invokeMethod(mSetForeground, mSetForegroundArgs);
	}

	@Override
	public void onCreate() {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onCreate()");

		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		try {
			mStartForeground = getClass().getMethod("startForeground",
					mStartForegroundSignature);
			mStopForeground = getClass().getMethod("stopForeground",
					mStopForegroundSignature);
			return;
		} catch (NoSuchMethodException e) {

			// Running on an older platform.
			mStartForeground = mStopForeground = null;
		}
		try {
			mSetForeground = getClass().getMethod("setForeground",
					mSetForegroundSignature);
		} catch (NoSuchMethodException e) {

			throw new IllegalStateException(
					"OS doesn't have Service.startForeground OR Service.setForeground!");
		}
	}

	@Override
	public void onDestroy() {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onDestroy()");

		// Make sure our notification is gone.
		stopForegroundCompat(R.string.foreground_service_started);
	}

	// This is the old onStart method that will be called on the pre-2.0
	// platform. On 2.0 or later we override onStartCommand() so this
	// method will not be called.
	@Override
	public void onStart(Intent intent, int startId) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onStart()");

		handleCommand(intent);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onStartCommand()");

		handleCommand(intent);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	void handleCommand(Intent intent) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter handleCommand()");

		if (ACTION_FOREGROUND.equals(intent.getAction())) {
			// In this sample, we'll use the same text for the ticker and the
			// expanded notification
			CharSequence text = getText(R.string.foreground_service_started);

			// Set the icon, scrolling text and timestamp
			Notification notification = new Notification(
					R.drawable.stat_sample, text, System.currentTimeMillis());

			// The PendingIntent to launch our activity if the user selects this
			// notification
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, Controller.class), 0);

			// Set the info for the views that show in the notification panel.
			notification.setLatestEventInfo(this,
					getText(R.string.local_service_label), text, contentIntent);

			startForegroundCompat(R.string.foreground_service_started,
					notification);

		} else if (ACTION_BACKGROUND.equals(intent.getAction())) {
			stopForegroundCompat(R.string.foreground_service_started);
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onBind()");

		return null;
	}

	// ----------------------------------------------------------------------

	/**
	 * <p>
	 * Example of explicitly starting and stopping the {@link ForegroundService}.
	 * 
	 * <p>
	 * Note that this is implemented as an inner class only keep the sample all
	 * together; typically this code would appear in some separate class.
	 */
	public static class Controller extends Activity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			if (ENABLE_LOG)
				Log.v(LOGTAG, "enter onCreate()");

			super.onCreate(savedInstanceState);

			setContentView(R.layout.foreground_service_controller);

			// Watch for button clicks.
			Button button = (Button) findViewById(R.id.start_foreground);
			button.setOnClickListener(mForegroundListener);
			button = (Button) findViewById(R.id.start_background);
			button.setOnClickListener(mBackgroundListener);
			button = (Button) findViewById(R.id.stop);
			button.setOnClickListener(mStopListener);
		}

		private OnClickListener mForegroundListener = new OnClickListener() {

			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				Intent intent = new Intent(ServiceForeground.ACTION_FOREGROUND);
				intent.setClass(Controller.this, ServiceForeground.class);
				startService(intent);
			}
		};

		private OnClickListener mBackgroundListener = new OnClickListener() {

			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				Intent intent = new Intent(ServiceForeground.ACTION_BACKGROUND);
				intent.setClass(Controller.this, ServiceForeground.class);
				startService(intent);
			}
		};

		private OnClickListener mStopListener = new OnClickListener() {

			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				stopService(new Intent(Controller.this, ServiceForeground.class));
			}
		};
	}
}
