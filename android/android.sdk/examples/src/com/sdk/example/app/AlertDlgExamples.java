package com.sdk.example.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sdk.example.R;

public class AlertDlgExamples extends Activity {
	private static final int DIALOG_YES_NO_MESSAGE = 1;
	private static final int DIALOG_YES_NO_LONG_MESSAGE = 2;
	private static final int DIALOG_LIST = 3;
	private static final int DIALOG_PROGRESS = 4;
	private static final int DIALOG_SINGLE_CHOICE = 5;
	private static final int DIALOG_MULTIPLE_CHOICE = 6;
	private static final int DIALOG_TEXT_ENTRY = 7;
	private static final int DIALOG_MULTIPLE_CHOICE_CURSOR = 8;
	private static final int DIALOG_YES_NO_ULTRA_LONG_MESSAGE = 9;
	private static final int DIALOG_YES_NO_OLD_SCHOOL_MESSAGE = 10;
	private static final int DIALOG_YES_NO_HOLO_LIGHT_MESSAGE = 11;

	private static final int MAX_PROGRESS = 100;

	private ProgressDialog mProgressDialog;
	private int mProgress;
	private Handler mProgressHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.alert_dialog);

		registerDialog(R.id.two_buttons, DIALOG_YES_NO_MESSAGE);

		registerDialog(R.id.two_buttons_old_school,
				DIALOG_YES_NO_OLD_SCHOOL_MESSAGE);

		registerDialog(R.id.two_buttons_holo_light,
				DIALOG_YES_NO_HOLO_LIGHT_MESSAGE);

		registerDialog(R.id.two_buttons2, DIALOG_YES_NO_LONG_MESSAGE);

		registerDialog(R.id.two_buttons2ultra, DIALOG_YES_NO_ULTRA_LONG_MESSAGE);

		registerDialog(R.id.select_button, DIALOG_LIST);

		registerDialog(R.id.radio_button, DIALOG_SINGLE_CHOICE);

		registerDialog(R.id.checkbox_button, DIALOG_MULTIPLE_CHOICE);

		registerDialog(R.id.checkbox_button2, DIALOG_MULTIPLE_CHOICE_CURSOR);

		registerDialog(R.id.text_entry_button, DIALOG_TEXT_ENTRY);

		/* Display a custom progress bar */
		Button progressButton = (Button) findViewById(R.id.progress_button);
		progressButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showDialog(DIALOG_PROGRESS);
				mProgress = 0;
				mProgressDialog.setProgress(0);
				mProgressHandler.sendEmptyMessage(0);
			}
		});

		mProgressHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (mProgress >= MAX_PROGRESS) {
					mProgressDialog.dismiss();
				} else {
					mProgress++;
					mProgressDialog.incrementProgressBy(1);
					mProgressHandler.sendEmptyMessageDelayed(0, 100);
				}
			}
		};
	}

	private void registerDialog(int id, final int dialogId) {
		Button dialogTwoBtn = (Button) findViewById(id);
		dialogTwoBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(dialogId);
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_YES_NO_MESSAGE: {
			return createTwoBtnDialog();
		}
		case DIALOG_YES_NO_OLD_SCHOOL_MESSAGE: {
			return createTwoBtnDialog(AlertDialog.THEME_TRADITIONAL);
		}
		case DIALOG_YES_NO_HOLO_LIGHT_MESSAGE: {
			return createTwoBtnDialog(AlertDialog.THEME_HOLO_LIGHT);
		}
		case DIALOG_YES_NO_LONG_MESSAGE: {
			return create3BtnDialog(R.string.alert_dialog_two_buttons2_msg);
		}
		case DIALOG_YES_NO_ULTRA_LONG_MESSAGE: {
			return create3BtnDialog(R.string.alert_dialog_two_buttons2ultra_msg);
		}
		case DIALOG_LIST: {
			return createDialogBuilder()
					.setTitle(R.string.select_dialog)
					.setItems(R.array.select_dialog_items,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {

									/* User clicked so do some stuff */
									String[] items = getResources()
											.getStringArray(
													R.array.select_dialog_items);
									createDialogBuilder().setMessage(
											"You selected: " + which + " , "
													+ items[which]).show();
								}
							}).create();
		}
		case DIALOG_PROGRESS: {
			mProgressDialog = new ProgressDialog(AlertDlgExamples.this);
			mProgressDialog.setIconAttribute(android.R.attr.alertDialogIcon);
			mProgressDialog.setTitle(R.string.select_dialog);
			// mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setMessage("test spinner");
			mProgressDialog.setMax(MAX_PROGRESS);

			// No setPositive, setNegative interfaces
			mProgressDialog.setButton(DialogInterface.BUTTON_POSITIVE,
					getText(R.string.alert_dialog_hide),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE,
					getText(R.string.alert_dialog_cancel),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int whichButton) {
						}
					});
			return mProgressDialog;
		}
		
		case DIALOG_SINGLE_CHOICE: {
			AlertDialog.Builder builder = createDialogBuilder();
			_createTwoBtnDialog(builder);
			builder.setSingleChoiceItems(R.array.select_dialog_items2, 0,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							Toast.makeText(AlertDlgExamples.this,
									"Item selected: " + whichButton,
									Toast.LENGTH_SHORT).show();
						}
					});
			return builder.create();
		}

		case DIALOG_MULTIPLE_CHOICE: {
			AlertDialog.Builder builder = createDialogBuilder();
			_createTwoBtnDialog(builder);
			builder.setMultiChoiceItems(R.array.select_dialog_items3,
					new boolean[] { false, true, false, true, false, false,
							false },
					new DialogInterface.OnMultiChoiceClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							Toast.makeText(
									AlertDlgExamples.this,
									"Item selected: " + which + "," + isChecked,
									Toast.LENGTH_SHORT).show();
						}
					});
			return builder.create();
		}

		case DIALOG_MULTIPLE_CHOICE_CURSOR: {
			String[] projection = new String[] { ContactsContract.Contacts._ID,
					ContactsContract.Contacts.DISPLAY_NAME,
					ContactsContract.Contacts.SEND_TO_VOICEMAIL };
			Cursor cursor = managedQuery(ContactsContract.Contacts.CONTENT_URI,
					projection, null, null, null);

			AlertDialog.Builder builder = createDialogBuilder();
			_createTwoBtnDialog(builder);
			builder.setMultiChoiceItems(cursor,
					ContactsContract.Contacts.SEND_TO_VOICEMAIL,
					ContactsContract.Contacts.DISPLAY_NAME,
					new DialogInterface.OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							Toast.makeText(
									AlertDlgExamples.this,
									"Item selected: " + which + "," + isChecked,
									Toast.LENGTH_SHORT).show();
						}
					});
			return builder.create();
		}

		case DIALOG_TEXT_ENTRY: {
			LayoutInflater factory = LayoutInflater.from(AlertDlgExamples.this);
			final View textEntryView = factory.inflate(
					R.layout.alert_dialog_text_entry, null);

			AlertDialog.Builder builder = createDialogBuilder();
			_createTwoBtnDialog(builder);
			builder.setView(textEntryView);
			builder.setPositiveButton(R.string.alert_dialog_ok,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							EditText username = (EditText) textEntryView
									.findViewById(R.id.username_edit);
							EditText password = (EditText) textEntryView
									.findViewById(R.id.password_edit);
							Toast.makeText(
									AlertDlgExamples.this,
									"username is: "
											+ username.getText().toString()
											+ ", password: "
											+ password.getText().toString(),
									Toast.LENGTH_SHORT).show();
						}
					});
			return builder.create();
		}

		}

		return super.onCreateDialog(id);
	}

	private AlertDialog.Builder createDialogBuilder() {
		return new AlertDialog.Builder(AlertDlgExamples.this);
	}

	private AlertDialog.Builder createDialogBuilder(int style) {
		return new AlertDialog.Builder(AlertDlgExamples.this, style);
	}

	private Dialog createTwoBtnDialog(int style) {
		AlertDialog.Builder builder = createDialogBuilder(style);
		_createTwoBtnDialog(builder);
		return builder.create();
	}

	private Dialog createTwoBtnDialog() {
		AlertDialog.Builder builder = createDialogBuilder();
		_createTwoBtnDialog(builder);
		return builder.create();
	}

	private void _createTwoBtnDialog(AlertDialog.Builder builder) {
		builder.setIconAttribute(android.R.attr.alertDialogIcon);
		builder.setTitle(R.string.alert_dialog_two_buttons);
		builder.setPositiveButton(R.string.alert_dialog_ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.setNegativeButton(R.string.alert_dialog_cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
	}

	private Dialog create3BtnDialog(int msgId) {
		AlertDialog.Builder builder = createDialogBuilder();
		builder.setIconAttribute(android.R.attr.alertDialogIcon);
		builder.setTitle(R.string.alert_dialog_two_buttons);
		builder.setMessage(msgId);
		builder.setPositiveButton(R.string.alert_dialog_ok,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.setNeutralButton(R.string.alert_dialog_something,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.setNegativeButton(R.string.alert_dialog_cancel,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		return builder.create();
	}

}
