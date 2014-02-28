
package com.pekall.demo;

import android.app.Application;
import com.pekall.demo.cache.CacheManager;
import com.pekall.mobileutil.HttpTestServer;
import com.pekall.mobileutil.NetworkThread;

import java.io.UnsupportedEncodingException;

@SuppressWarnings("UnusedDeclaration")
public class DemoApplication extends Application {

    @SuppressWarnings("UnusedDeclaration")
    @Override
    public void onCreate() {
        super.onCreate();

        CacheManager.getInstance().clearAllCache();
        if (!NetworkThread.getThreadInstance(this).isRunning()) {
            NetworkThread.getThreadInstance(this).start();
        }

        // FIXME: just for the test demo, no need in the client if there is a http server
        try {
            HttpTestServer.launch();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
