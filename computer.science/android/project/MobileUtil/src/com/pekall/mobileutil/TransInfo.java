
package com.pekall.mobileutil;

import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.pekall.demo.Utility;
import com.pekall.http.HttpException;
import com.pekall.http.Response;
import com.pekall.http.ResponseException;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.lang.reflect.Field;
import java.util.ArrayList;

@SuppressWarnings("SameParameterValue")
public abstract class TransInfo {
    public static final int HTTP_GET = 0;
    public static final int HTTP_POST = 1;
    public static final int HTTP_PUT = 2;
    public static final int HTTP_DELETE = 3;
    private static final String LOGTAG = "TransInfo";
    /**
     * HTTP_GET or HTTP_POST
     */
    final int mHttpMethod;
    /**
     * The unique id for a transaction
     */
    private final long mTransId;
    /**
     * The callback handler for notifying UI
     */
    private final Handler mClientCallback;
    private String mKey;
    /**
     * The callback intent to notifying UI
     */
    private PendingIntent mCallbackIntent;
    /**
     * Flag for clearing cache and getting data from network
     */
    private boolean mRefresh = false;

    /**
     * Subclass need to check whether there is cache data for itself.
     *
     * @return
     */
    public abstract boolean hasCacheData();

    /**
     * Subclass need to clear its own cache here
     */
    public abstract void clearCache();

    @SuppressWarnings("UnusedDeclaration")
    public TransInfo(int httpMethod, PendingIntent intent, Handler handler) {
        mHttpMethod = httpMethod;
        mClientCallback = handler;
        mCallbackIntent = intent;
        mTransId = TransResult.generateNextId();
        Log.i(LOGTAG,
                "TransInfo created, id: " + mTransId + ", class: "
                        + this.getClass());
    }

    protected TransInfo(int httpMethod, Handler handler) {
        mHttpMethod = httpMethod;
        mClientCallback = handler;
        mTransId = TransResult.generateNextId();
        Utility.log("84");
        Log.i(LOGTAG,
                "TransInfo created, id: " + mTransId + ", class: "
                        + this.getClass());
    }

    @SuppressWarnings("UnusedDeclaration")
    public PendingIntent getCallbackIntent() {
        return mCallbackIntent;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setCallbackIntent(PendingIntent mCallbackIntent) {
        this.mCallbackIntent = mCallbackIntent;
    }

    public boolean isRefresh() {
        return this.mRefresh;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setRefresh(boolean refresh) {
        this.mRefresh = refresh;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getKey() {
        return mKey;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setKey(String key) {
        this.mKey = key;
    }


    @SuppressWarnings("UnusedDeclaration")
    public int getHttpMethod() {
        return mHttpMethod;
    }

    public long getTransId() {
        return mTransId;
    }

    PostRequest genPostRequest() {
        throw new IllegalAccessError();
    }

    PutRequest genPutRequest() {
        throw new IllegalAccessError();
    }

    DeleteRequest genDelRequest() {
        throw new IllegalAccessError();
    }

    GetRequest genGetRequest() {
        throw new IllegalAccessError();
    }

    public void reportResult(int result) {
        if (mClientCallback != null) {
            Message msg = mClientCallback.obtainMessage();
            msg.obj = new TransResult(mTransId, result);
            // XXX: Delay just for unit test
            mClientCallback.sendMessageDelayed(msg, 100);
        }

        if (mCallbackIntent != null) {
            try {
                Log.i(LOGTAG, "send pending event");
                mCallbackIntent.send(result);
            } catch (CanceledException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Parse network data and save it to the cache layer
     *
     * @param resp
     * @return
     */
    @SuppressWarnings("SameReturnValue")
    protected abstract int internalProcessResponse(String resp);

    final void handleResponse(Response resp) {
        String result;
        try {
            Log.i(LOGTAG, "handleResponse begin:");
            result = resp.asString();
            Log.i(LOGTAG, "handleResponse end");
        } catch (ResponseException e) {
            e.printStackTrace();

            // TODO: add error types
            reportResult(Helper.RESULT_ERR_NET_GENERAL);
            return;
        }

        if (TextUtils.isEmpty(result)) {
            reportResult(Helper.RESULT_ERR_NET_NULL_RESP);
            return;
        }

        reportResult(internalProcessResponse(result));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TransInfo))
            return false;

        if (!this.getClass().equals(o.getClass())) {
            return false;
        }

        // TODO: Add support for primitive types
        boolean ret = true;
        for (Field f : this.getClass().getDeclaredFields()) {
            try {
                f.setAccessible(true);
                if (Object.class.isAssignableFrom(f.getType())) {
                    if (!f.get(this).equals(f.get(o))) {
                        return false;
                    }
                }
                if (int.class.equals(f.getType())) {
                    if (f.getInt(this) != f.getInt(o)) {
                        return false;
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                ret = false;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                ret = false;
            }
        }
        return ret && (mHttpMethod == ((TransInfo) o).mHttpMethod)
                && (mRefresh == ((TransInfo) o).mRefresh);
    }

    @SuppressWarnings("UnusedDeclaration")
    public ArrayList<Pair<String, String>> genHeaders(
            Pair<String, String>... pairs) {
        ArrayList<Pair<String, String>> headers = new ArrayList<Pair<String, String>>();
        for (Pair<String, String> p : pairs) {
            if (p == null)
                continue;
            headers.add(p);
        }
        return headers;
    }

    @SuppressWarnings("UnusedDeclaration")
    public ArrayList<NameValuePair> genPostParams(BasicNameValuePair... pairs) {
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        for (BasicNameValuePair p : pairs) {
            if (p == null)
                continue;

            params.add(p);
        }
        return params;
    }

    @SuppressWarnings("UnusedParameters")
    public void handleHttpException(HttpException e) {
        Utility.log("Helper.RESULT_ERR_NET_DOWN  " + Helper.RESULT_ERR_NET_DOWN);
        reportResult(Helper.RESULT_ERR_NET_DOWN);
    }
}
