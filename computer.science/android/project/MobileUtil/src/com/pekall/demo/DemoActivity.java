package com.pekall.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.pekall.demo.bean.DemoBeanCollection;
import com.pekall.demo.cache.CacheManager;
import com.pekall.demo.transinfo.DemoTransInfo;
import com.pekall.mobileutil.*;

@SuppressWarnings("UnusedDeclaration")
public class DemoActivity extends Activity {
    private static final String LOGTAG = "DemoActivity";
    private long mTransId;

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            TransResult result = (TransResult) msg.obj;
            if (result.getTransId() != mTransId) {
                Log.e(LOGTAG, "transaction id not match!");
                return;
            }

            if (result.getResult() == Helper.RESULT_DONE) {
                DemoBeanCollection file = CacheManager.getInstance().getDemoBeanCollection();
                Toast.makeText(DemoActivity.this,
                        "got " + file.getBeans().size() + " record", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(DemoActivity.this,
                        "request error: " + result.getResult(), Toast.LENGTH_LONG).show();
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_layout);

        Button button = (Button) findViewById(R.id.btn_send_request);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransInfo info = new DemoTransInfo(mHandler);
                mTransId = info.getTransId();
                Transaction.enqueueTransInfo(info);
            }
        });

        Button cache = (Button) findViewById(R.id.btn_clear_cache);
        cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheManager.getInstance().clearAllCache();
            }
        });

        Button btnNetwork = (Button) findViewById(R.id.btn_always_network);
        btnNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransInfo info = new DemoTransInfo(mHandler);
                mTransId = info.getTransId();
                info.setRefresh(true);  // will clear cache and send request to network
                Transaction.enqueueTransInfo(info);
            }
        });
    }
}