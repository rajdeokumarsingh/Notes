/* ---------------------------------------------------------------------------------------------
 *
 *            Capital Alliance Software Confidential Proprietary
 *            (c) Copyright CAS 201{x}, All Rights Reserved
 *                          www.pekall.com
 *
 * ----------------------------------------------------------------------------------------------
 */

package com.android.browser.filemanager;

import com.android.browser.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TextInputDialog extends AlertDialog {
	private String mInputText;
	private String mTitle;
	private String mMsg;
	private OnFinishListener mListener;
	private Context mContext;
	private View mView;
	private EditText mFolderName;

	public interface OnFinishListener {
		// return true to accept and dismiss, false reject
		boolean onFinish(String text);
	}

	public TextInputDialog(Context context, String title, String msg, String text, OnFinishListener listener) {
		super(context);
		mTitle = title;
		mMsg = msg;
		mListener = listener;
		mInputText = text;
		mContext = context;
	}

	public String getInputText() {
		return mInputText;
	}

	protected void onCreate(Bundle savedInstanceState) {
		mView = getLayoutInflater().inflate(R.layout.textinput_dialog, null);

		setTitle(mTitle);
		setMessage(mMsg);

		mFolderName = (EditText) mView.findViewById(R.id.text);
		mFolderName.setText(mInputText);
		mFolderName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String text = s.toString();
				getButton(BUTTON_POSITIVE).setEnabled(TextUtils.isEmpty(text) ? false : true);
				if (text.length() > 100) {
					s.replace(100, text.length(), "");
				}
			}
		});

		setView(mView);
		setButton(BUTTON_POSITIVE, mContext.getString(android.R.string.ok), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (which == BUTTON_POSITIVE) {
					mInputText = mFolderName.getText().toString();
					if (TextUtils.isEmpty(mInputText)) {
						Toast.makeText(mContext, R.string.toast_filename_is_not_empty, Toast.LENGTH_SHORT).show();
						return;
					}
					if (mListener.onFinish(mInputText)) {
						dismiss();
					}
				}
			}
		});
		setButton(BUTTON_NEGATIVE, mContext.getString(android.R.string.cancel), (DialogInterface.OnClickListener) null);

		super.onCreate(savedInstanceState);
	}
}
