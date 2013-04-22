package com.sdk.example.app.service;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sdk.example.R;

/**
 * This is an example of implementing an application service that runs in a
 * different process than the application. Because it can be in another process,
 * we must use IPC to interact with it. The {@link Controller} and
 * {@link Binding} classes show how to interact with the service.
 * 
 * <p>
 * Note that most applications <strong>do not</strong> need to deal with the
 * complexity shown here. If your application simply has a service running in
 * its own process, the {@link LocalService} sample shows a much simpler way to
 * interact with it.
 */
public class ServiceRemoteController extends Service {
	private static final boolean ENABLE_LOG = true;
	private static final String LOGTAG = "ServiceRemoteController";

	/**
	 * This is a list of callbacks that have been registered with the service.
	 * Note that this is package scoped (instead of private) so that it can be
	 * accessed more efficiently from inner classes.
	 */
	final RemoteCallbackList<IRemoteServiceCallback> mCallbacks = 
			new RemoteCallbackList<IRemoteServiceCallback>();

	int mValue = 0;
	NotificationManager mNM;

	@Override
	public void onCreate() {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onCreate()");

		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		// Display a notification about us starting.
		showNotification();

		// While this service is running, it will continually increment a
		// number. Send the first message that is used to perform the
		// increment.
		mHandler.sendEmptyMessage(REPORT_MSG);
	}

	@Override
	public void onDestroy() {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onDestroy()");

		// Cancel the persistent notification.
		mNM.cancel(R.string.remote_service_started);

		// Tell the user we stopped.
		Toast.makeText(this, R.string.remote_service_stopped,
				Toast.LENGTH_SHORT).show();

		// Unregister all callbacks.
		mCallbacks.kill();

		// Remove the next pending message to increment the counter, stopping
		// the increment loop.
		mHandler.removeMessages(REPORT_MSG);
	}

	@Override
	public IBinder onBind(Intent intent) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onBind()");

		// Select the interface to return. If your service only implements
		// a single interface, you can just return it here without checking
		// the Intent.
		if (IRemoteService.class.getName().equals(intent.getAction())) {
			return mBinder;
		}
		if (ISecondary.class.getName().equals(intent.getAction())) {
			return mSecondaryBinder;
		}
		return null;
	}

	/**
	 * The IRemoteInterface is defined through IDL
	 */
	private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
		private static final String LOGTAG = "IRemoteService.Stub";

		public void registerCallback(IRemoteServiceCallback cb) {
			if (ENABLE_LOG)
				Log.v(LOGTAG, "enter registerCallback()");

			if (cb != null)
				mCallbacks.register(cb);
		}

		public void unregisterCallback(IRemoteServiceCallback cb) {
			if (ENABLE_LOG)
				Log.v(LOGTAG, "enter unregisterCallback()");

			if (cb != null)
				mCallbacks.unregister(cb);
		}
	};

	/**
	 * A secondary interface to the service.
	 */
	private final ISecondary.Stub mSecondaryBinder = new ISecondary.Stub() {
		private static final String LOGTAG = "ISecondary.Stub";

		public int getPid() {
			if (ENABLE_LOG)
				Log.v(LOGTAG, "enter getPid()");

			return Process.myPid();
		}

		public void basicTypes(int anInt, long aLong, boolean aBoolean,
				float aFloat, double aDouble, String aString) {
			if (ENABLE_LOG)
				Log.v(LOGTAG, "enter basicTypes()");

		}
	};

	@Override
	public void onTaskRemoved(Intent rootIntent) {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter onTaskRemoved()");

		Toast.makeText(this, "Task removed: " + rootIntent, Toast.LENGTH_LONG)
				.show();
	}

	private static final int REPORT_MSG = 1;

	/**
	 * Our Handler used to execute operations on the main thread. This is used
	 * to schedule increments of our value.
	 */
	private final Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (ENABLE_LOG)
				Log.v(LOGTAG, "enter handleMessage()");

			switch (msg.what) {

			// It is time to bump the value!
			case REPORT_MSG: {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter handleMessage(), report message");

				// Up it goes.
				int value = ++mValue;

				// Broadcast to all clients the new value.
				final int N = mCallbacks.beginBroadcast();
				for (int i = 0; i < N; i++) {
					try {
						if (ENABLE_LOG)
							Log.v(LOGTAG,
									"enter handleMessage(), value changed");

						mCallbacks.getBroadcastItem(i).valueChanged(value);
					} catch (RemoteException e) {

						// The RemoteCallbackList will take care of removing
						// the dead object for us.
					}
				}
				mCallbacks.finishBroadcast();

				// Repeat every 1 second.
				sendMessageDelayed(obtainMessage(REPORT_MSG), 1 * 1000);
			}
				break;
			default:
				super.handleMessage(msg);
			}
		}
	};

	/**
	 * Show a notification while this service is running.
	 */
	private void showNotification() {
		if (ENABLE_LOG)
			Log.v(LOGTAG, "enter showNotification()");

		// In this sample, we'll use the same text for the ticker and the
		// expanded notification
		CharSequence text = getText(R.string.remote_service_started);

		// Set the icon, scrolling text and timestamp
		Notification notification = new Notification(R.drawable.stat_sample,
				text, System.currentTimeMillis());

		// The PendingIntent to launch our activity if the user selects this
		// notification
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, Controller.class), 0);

		// Set the info for the views that show in the notification panel.
		notification.setLatestEventInfo(this,
				getText(R.string.remote_service_label), text, contentIntent);

		// Send the notification.
		// We use a string id because it is a unique number. We use it later to
		// cancel.
		mNM.notify(R.string.remote_service_started, notification);
	}

	// ----------------------------------------------------------------------

	/**
	 * <p>
	 * Example of explicitly starting and stopping the remove service. This
	 * demonstrates the implementation of a service that runs in a different
	 * process than the rest of the application, which is explicitly started and
	 * stopped as desired.
	 * </p>
	 * 
	 * <p>
	 * Note that this is implemented as an inner class only keep the sample all
	 * together; typically this code would appear in some separate class.
	 */
	public static class Controller extends Activity {
		private static final String LOGTAG = "ServiceRemoteController.Controller";

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			if (ENABLE_LOG)
				Log.v(LOGTAG, "enter onCreate()");

			super.onCreate(savedInstanceState);

			setContentView(R.layout.remote_service_controller);

			// Watch for button clicks.
			Button button = (Button) findViewById(R.id.start);
			button.setOnClickListener(mStartListener);
			button = (Button) findViewById(R.id.stop);
			button.setOnClickListener(mStopListener);
		}

		private OnClickListener mStartListener = new OnClickListener() {

			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				// Make sure the service is started. It will continue running
				// until someone calls stopService().
				// We use an action code here, instead of explictly supplying
				// the component name, so that other packages can replace
				// the service.
				startService(new Intent(
						"com.sdk.example.app.service.REMOTE_SERVICE"));
			}
		};

		private OnClickListener mStopListener = new OnClickListener() {

			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				// Cancel a previous call to startService(). Note that the
				// service will not actually stop at this point if there are
				// still bound clients.
				stopService(new Intent(
						"com.sdk.example.app.service.REMOTE_SERVICE"));
			}
		};
	}

	// ----------------------------------------------------------------------

	/**
	 * Example of binding and unbinding to the remote service. This demonstrates
	 * the implementation of a service which the client will bind to,
	 * interacting with it through an aidl interface.</p>
	 * 
	 * <p>
	 * Note that this is implemented as an inner class only keep the sample all
	 * together; typically this code would appear in some separate class.
	 */

	public static class Binding extends Activity {
		private static final String LOGTAG = "ServiceRemoteController.Binding";

		/** The primary interface we will be calling on the service. */
		IRemoteService mService = null;
		/** Another interface we use on the service. */
		ISecondary mSecondaryService = null;

		Button mKillButton;
		TextView mCallbackText;

		private boolean mIsBound;

		/**
		 * Standard initialization of this activity. Set up the UI, then wait
		 * for the user to poke it before doing anything.
		 */
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			if (ENABLE_LOG)
				Log.v(LOGTAG, "enter onCreate()");

			super.onCreate(savedInstanceState);

			setContentView(R.layout.remote_service_binding);

			// Watch for button clicks.
			Button button = (Button) findViewById(R.id.bind);
			button.setOnClickListener(mBindListener);
			button = (Button) findViewById(R.id.unbind);
			button.setOnClickListener(mUnbindListener);
			mKillButton = (Button) findViewById(R.id.kill);
			mKillButton.setOnClickListener(mKillListener);
			mKillButton.setEnabled(false);

			mCallbackText = (TextView) findViewById(R.id.callback);
			mCallbackText.setText("Not attached.");
		}

		/**
		 * Class for interacting with the main interface of the service.
		 */
		private ServiceConnection mConnection = new ServiceConnection() {
			private static final String LOGTAG = 
					"ServiceRemoteController.Binding.IRemoteService";

			public void onServiceConnected(ComponentName className,
					IBinder service) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onServiceConnected()");

				// This is called when the connection with the service has been
				// established, giving us the service object we can use to
				// interact with the service. We are communicating with our
				// service through an IDL interface, so get a client-side
				// representation of that from the raw service object.
				mService = IRemoteService.Stub.asInterface(service);
				mKillButton.setEnabled(true);
				mCallbackText.setText("Attached.");

				// We want to monitor the service for as long as we are
				// connected to it.
				try {
					mService.registerCallback(mCallback);
				} catch (RemoteException e) {

					// In this case the service has crashed before we could even
					// do anything with it; we can count on soon being
					// disconnected (and then reconnected if it can be
					// restarted)
					// so there is no need to do anything here.
				}

				// As part of the sample, tell the user what happened.
				Toast.makeText(Binding.this, R.string.remote_service_connected,
						Toast.LENGTH_SHORT).show();
			}

			public void onServiceDisconnected(ComponentName className) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onServiceDisconnected()");

				// This is called when the connection with the service has been
				// unexpectedly disconnected -- that is, its process crashed.
				mService = null;
				mKillButton.setEnabled(false);
				mCallbackText.setText("Disconnected.");

				// As part of the sample, tell the user what happened.
				Toast.makeText(Binding.this,
						R.string.remote_service_disconnected,
						Toast.LENGTH_SHORT).show();
			}
		};

		/**
		 * Class for interacting with the secondary interface of the service.
		 */
		private ServiceConnection mSecondaryConnection = new ServiceConnection() {
			private static final String LOGTAG = 
					"ServiceRemoteController.Binding.ISecondary";
			
			public void onServiceConnected(ComponentName className,
					IBinder service) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onServiceConnected()");

				// Connecting to a secondary interface is the same as any
				// other interface.
				mSecondaryService = ISecondary.Stub.asInterface(service);
				mKillButton.setEnabled(true);
			}

			public void onServiceDisconnected(ComponentName className) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onServiceDisconnected()");

				mSecondaryService = null;
				mKillButton.setEnabled(false);
			}
		};

		private OnClickListener mBindListener = new OnClickListener() {

			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				// Establish a couple connections with the service, binding
				// by interface names. This allows other applications to be
				// installed that replace the remote service by implementing
				// the same interface.
				bindService(new Intent(IRemoteService.class.getName()),
						mConnection, Context.BIND_AUTO_CREATE);
				bindService(new Intent(ISecondary.class.getName()),
						mSecondaryConnection, Context.BIND_AUTO_CREATE);
				mIsBound = true;
				mCallbackText.setText("Binding.");
			}
		};

		private OnClickListener mUnbindListener = new OnClickListener() {

			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				if (mIsBound) {

					// If we have received the service, and hence registered
					// with
					// it, then now is the time to unregister.
					if (mService != null) {
						try {
							mService.unregisterCallback(mCallback);
						} catch (RemoteException e) {

							// There is nothing special we need to do if the
							// service
							// has crashed.
						}
					}

					// Detach our existing connection.
					unbindService(mConnection);
					unbindService(mSecondaryConnection);
					mKillButton.setEnabled(false);
					mIsBound = false;
					mCallbackText.setText("Unbinding.");
				}
			}
		};

		private OnClickListener mKillListener = new OnClickListener() {

			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				// To kill the process hosting our service, we need to know its
				// PID. Conveniently our service has a call that will return
				// to us that information.
				if (mSecondaryService != null) {
					try {
						int pid = mSecondaryService.getPid();
						// Note that, though this API allows us to request to
						// kill any process based on its PID, the kernel will
						// still impose standard restrictions on which PIDs you
						// are actually able to kill. Typically this means only
						// the process running your application and any
						// additional
						// processes created by that app as shown here; packages
						// sharing a common UID will also be able to kill each
						// other's processes.
						Process.killProcess(pid);
						mCallbackText.setText("Killed service process.");
					} catch (RemoteException ex) {

						// Recover gracefully from the process hosting the
						// server dying.
						// Just for purposes of the sample, put up a
						// notification.
						Toast.makeText(Binding.this,
								R.string.remote_call_failed, Toast.LENGTH_SHORT)
								.show();
					}
				}
			}
		};

		// ----------------------------------------------------------------------
		// Code showing how to deal with callbacks.
		// ----------------------------------------------------------------------

		/**
		 * This implementation is used to receive callbacks from the remote
		 * service.
		 */
		private IRemoteServiceCallback mCallback = new IRemoteServiceCallback.Stub() {
			/**
			 * This is called by the remote service regularly to tell us about
			 * new values. Note that IPC calls are dispatched through a thread
			 * pool running in each process, so the code executing here will NOT
			 * be running in our main thread like most other things -- so, to
			 * update the UI, we need to use a Handler to hop over there.
			 */
			public void valueChanged(int value) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter valueChanged()");

				mHandler.sendMessage(mHandler.obtainMessage(BUMP_MSG, value, 0));
			}
		};

		private static final int BUMP_MSG = 1;

		private Handler mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {

				switch (msg.what) {

				case BUMP_MSG:
					if (ENABLE_LOG)
						Log.v(LOGTAG, "enter handleMessage(), "
								+ "Received from service: " + msg.arg1);

					mCallbackText.setText("Received from service: " + msg.arg1);
					break;
				default:
					super.handleMessage(msg);
				}
			}

		};
	}

	// ----------------------------------------------------------------------

	/**
	 * Examples of behavior of different bind flags.</p>
	 */

	public static class BindingOptions extends Activity {
		private static final String LOGTAG = "ServiceRemoteController.BindingOptions";

		ServiceConnection mCurConnection;
		TextView mCallbackText;

		class MyServiceConnection implements ServiceConnection {
			private static final String LOGTAG = 
					"ServiceRemoteController.BindingOptions.MyServiceConnection";
			
			final boolean mUnbindOnDisconnect;

			public MyServiceConnection() {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter MyServiceConnection()");

				mUnbindOnDisconnect = false;
			}

			public MyServiceConnection(boolean unbindOnDisconnect) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter MyServiceConnection()");

				mUnbindOnDisconnect = unbindOnDisconnect;
			}

			public void onServiceConnected(ComponentName className,
					IBinder service) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onServiceConnected()");

				if (mCurConnection != this) {
					return;
				}
				mCallbackText.setText("Attached.");
				Toast.makeText(BindingOptions.this,
						R.string.remote_service_connected, Toast.LENGTH_SHORT)
						.show();
			}

			public void onServiceDisconnected(ComponentName className) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onServiceDisconnected()");

				if (mCurConnection != this) {
					return;
				}
				mCallbackText.setText("Disconnected.");
				Toast.makeText(BindingOptions.this,
						R.string.remote_service_disconnected,
						Toast.LENGTH_SHORT).show();
				if (mUnbindOnDisconnect) {

					unbindService(this);
					mCurConnection = null;
					Toast.makeText(BindingOptions.this,
							R.string.remote_service_unbind_disconn,
							Toast.LENGTH_SHORT).show();
				}
			}
		}

		/**
		 * Standard initialization of this activity. Set up the UI, then wait
		 * for the user to poke it before doing anything.
		 */
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			if (ENABLE_LOG)
				Log.v(LOGTAG, "enter onCreate()");

			super.onCreate(savedInstanceState);

			setContentView(R.layout.remote_binding_options);

			// Watch for button clicks.
			Button button = (Button) findViewById(R.id.bind_normal);
			button.setOnClickListener(mBindNormalListener);
			button = (Button) findViewById(R.id.bind_not_foreground);
			button.setOnClickListener(mBindNotForegroundListener);
			button = (Button) findViewById(R.id.bind_above_client);
			button.setOnClickListener(mBindAboveClientListener);
			button = (Button) findViewById(R.id.bind_allow_oom);
			button.setOnClickListener(mBindAllowOomListener);
			button = (Button) findViewById(R.id.bind_waive_priority);
			button.setOnClickListener(mBindWaivePriorityListener);
			button = (Button) findViewById(R.id.bind_important);
			button.setOnClickListener(mBindImportantListener);
			button = (Button) findViewById(R.id.bind_with_activity);
			button.setOnClickListener(mBindWithActivityListener);
			button = (Button) findViewById(R.id.unbind);
			button.setOnClickListener(mUnbindListener);

			mCallbackText = (TextView) findViewById(R.id.callback);
			mCallbackText.setText("Not attached.");
		}

		private OnClickListener mBindNormalListener = new OnClickListener() {
			private static final String LOGTAG = 
					"ServiceRemoteController.BindingOptions.NormalListener";
			
			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				if (mCurConnection != null) {
					unbindService(mCurConnection);
					mCurConnection = null;
				}
				ServiceConnection conn = new MyServiceConnection();
				if (bindService(new Intent(IRemoteService.class.getName()),
						conn, Context.BIND_AUTO_CREATE)) {
					mCurConnection = conn;
				}
			}
		};

		private OnClickListener mBindNotForegroundListener = new OnClickListener() {
			private static final String LOGTAG = 
					"ServiceRemoteController.BindingOptions.NotForegroundListener";
			
			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				if (mCurConnection != null) {
					unbindService(mCurConnection);
					mCurConnection = null;
				}
				ServiceConnection conn = new MyServiceConnection();
				if (bindService(new Intent(IRemoteService.class.getName()),
						conn, Context.BIND_AUTO_CREATE
								| Context.BIND_NOT_FOREGROUND)) {
					mCurConnection = conn;
				}
			}
		};

		private OnClickListener mBindAboveClientListener = new OnClickListener() {
			private static final String LOGTAG = 
					"ServiceRemoteController.BindingOptions.AboveClientListener";
			
			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				if (mCurConnection != null) {
					unbindService(mCurConnection);
					mCurConnection = null;
				}
				ServiceConnection conn = new MyServiceConnection();
				if (bindService(new Intent(IRemoteService.class.getName()),
						conn, Context.BIND_AUTO_CREATE
								| Context.BIND_ABOVE_CLIENT)) {
					mCurConnection = conn;
				}
			}
		};

		private OnClickListener mBindAllowOomListener = new OnClickListener() {
			private static final String LOGTAG = 
					"ServiceRemoteController.BindingOptions.AllowOomListener";
			
			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				if (mCurConnection != null) {
					unbindService(mCurConnection);
					mCurConnection = null;
				}
				ServiceConnection conn = new MyServiceConnection();
				if (bindService(new Intent(IRemoteService.class.getName()),
						conn, Context.BIND_AUTO_CREATE
								| Context.BIND_ALLOW_OOM_MANAGEMENT)) {
					mCurConnection = conn;
				}
			}
		};

		private OnClickListener mBindWaivePriorityListener = new OnClickListener() {
			private static final String LOGTAG = 
					"ServiceRemoteController.BindingOptions.WaivePriorityListener";
			
			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				if (mCurConnection != null) {
					unbindService(mCurConnection);
					mCurConnection = null;
				}
				ServiceConnection conn = new MyServiceConnection(true);
				if (bindService(new Intent(IRemoteService.class.getName()),
						conn, Context.BIND_AUTO_CREATE
								| Context.BIND_WAIVE_PRIORITY)) {
					mCurConnection = conn;
				}
			}
		};

		private OnClickListener mBindImportantListener = new OnClickListener() {
			private static final String LOGTAG = 
					"ServiceRemoteController.BindingOptions.ImportantListener";
			
			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				if (mCurConnection != null) {
					unbindService(mCurConnection);
					mCurConnection = null;
				}
				ServiceConnection conn = new MyServiceConnection();
				if (bindService(new Intent(IRemoteService.class.getName()),
						conn, Context.BIND_AUTO_CREATE | Context.BIND_IMPORTANT)) {
					mCurConnection = conn;
				}
			}
		};

		private OnClickListener mBindWithActivityListener = new OnClickListener() {
			private static final String LOGTAG = 
					"ServiceRemoteController.BindingOptions.WithActivityListener";
			
			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				if (mCurConnection != null) {
					unbindService(mCurConnection);
					mCurConnection = null;
				}
				ServiceConnection conn = new MyServiceConnection();
				if (bindService(new Intent(IRemoteService.class.getName()),
						conn, Context.BIND_AUTO_CREATE
								| Context.BIND_ADJUST_WITH_ACTIVITY
								| Context.BIND_WAIVE_PRIORITY)) {
					mCurConnection = conn;
				}
			}
		};

		private OnClickListener mUnbindListener = new OnClickListener() {
			private static final String LOGTAG = 
					"ServiceRemoteController.BindingOptions.UnbindListener";
			
			public void onClick(View v) {
				if (ENABLE_LOG)
					Log.v(LOGTAG, "enter onClick()");

				if (mCurConnection != null) {
					unbindService(mCurConnection);
					mCurConnection = null;
				}
			}
		};
	}
}
