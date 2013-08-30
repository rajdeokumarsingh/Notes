package com.android.browser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmActivity extends Activity implements View.OnClickListener{
    private TextView confirm;
    private Button cancel = null;
    private Button ok = null;
    private String url = null;
    private String mimetype = null;
    private boolean privateBrowsing = false;
    private String filename = null;
    private String userAgent = null;
    private String contentLength = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_dialog_cp);
        setTitle(R.string.cu_download);
        confirm = (TextView)findViewById(R.id.cu_confirm_download);
        ok = (Button)findViewById(R.id.ok);
        ok.setOnClickListener(this);
        ok.setText(R.string.ok);
        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        cancel.setText(R.string.cancel);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        mimetype = intent.getStringExtra("mimetype");
        privateBrowsing = intent.getBooleanExtra("privateBrowsing", false);
        filename = intent.getStringExtra("filename");
        userAgent = intent.getStringExtra("userAgent");
        contentLength = intent.getStringExtra("contentLength");
        
        String msg = null;
        if (contentLength != null) {
            String contentSize = Formatter.formatFileSize(this, Long.parseLong(contentLength));
            msg = getString(R.string.cu_download_size, contentSize);
         } else {
            msg = getString(R.string.cu_download_no_size);
         }
        confirm.setText(msg);
        
    }
    
    public void onClick(View v) {
        // TODO Auto-generated method stub
          int id = v.getId();
          switch (id) {
            case R.id.cancel:
                this.finish();
                break;
            case R.id.ok:
                Intent intent = new Intent(this, DownloadDialog.class);
                intent.putExtra("url", url);
                intent.putExtra("mimetype", mimetype);
                intent.putExtra("privateBrowsing", privateBrowsing);
                intent.putExtra("filename", filename);
                intent.putExtra("userAgent", userAgent);
                this.startActivity(intent);
                finish();
                break;        
            default:
                break;
        }
        
    }
}
