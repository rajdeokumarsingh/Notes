package com.sdk.example.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.sdk.example.R;

/**
 * This is an example of implementing an application service that runs locally
 * in the same process as the application. The
 * {@link LocalServiceActivities.Controller} and
 * {@link LocalServiceActivities.Binding} classes show how to interact with the
 * service.
 * 
 * <p>
 * Notice the use of the {@link NotificationManager} when interesting things
 * happen in the service. This is generally how background services should
 * interact with the user, rather than doing something more disruptive such as
 * calling startActivity().
 */

public class ServiceLocalBinding extends Service {
	public static final boolean ENABLE_LOG = true;
	public static final String LOGTAG = "ServiceLocalBinding";

	private NotificationManager mNM;

	// Unique Identification Number for the Notification.
	// We use it on Notification start, and to cancel it.
	private int NOTIFICATION = R.string.local_service_started;

	private static final int MSG_STOP_SERVICE = 0;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_STOP_SERVICE:
				if (ENABLE_LOG)
					Log.v(LOGTAG, "handleMessage MSG_STOP_SERVICE: " + msg.arg1);
				
				stopSelf(msg.arg1);
				return;
			}

			super.handleMessage(msg);
		}

	};

	/**
	 * Class for clients to access. Because we know this service always runs in
	 * the same process as its clients, we don't need to deal with IPC.
	 */
	public class LocalBinder extends Binder {
		ServiceLocalBinding getService() {
			if (ENABLE_LOG)
				Log.v(LOGTAG, "enter getService()");

			return ServiceLocalBinding.this;
		}
	}

	@Override
	public void onCreate() {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onCreate()");

		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// Display a notification about us starting. We put an icon in the
		// status bar.
		showNotification();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onStartCommand()");

		Log.i(LOGTAG, "Received start id " + startId + ": " + intent);
		mHandler.sendMessageDelayed(
				Message.obtain(mHandler, MSG_STOP_SERVICE, startId, -1), 5000);
		
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onDestroy()");

		// Cancel the persistent notification.
		mNM.cancel(NOTIFICATION);

		// Tell the user we stopped.
		Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onBind()");

		return mBinder;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onUnBind()");
		
		return super.onUnbind(intent);
	}

	// This is the object that receives interactions from clients. See
	// RemoteService for a more complete example.
	private final IBinder mBinder = new LocalBinder();

	/**
	 * Show a notification while this service is running.
	 */
	private void showNotification() {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter showNotification()");

		// In this sample, we'll use the same text for the ticker and the
		// expanded notification
		CharSequence text = getText(R.string.local_service_started);

		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(R.drawable.stat_sample,
				text, System.currentTimeMillis());

		// The PendingIntent to launch our activity if the user selects this
		// notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, ServiceLocalBindingActivity.Controller.class),
				0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this,
				getText(R.string.local_service_label), text, contentIntent);

		// Send the notification.
		mNM.notify(NOTIFICATION, notification);
	}
	
	public void service1() {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter service1()");
	}
	
	public void service2() {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter service2()");
	}
}
