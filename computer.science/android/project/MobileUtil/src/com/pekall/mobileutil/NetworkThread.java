
package com.pekall.mobileutil;

import android.content.Context;
import android.util.Log;
import com.pekall.demo.Utility;
import com.pekall.http.HttpClient;
import com.pekall.http.HttpException;
import com.pekall.http.Response;

/**
 * The NetworkThread handles HTTP requests from the transaction layer
 */
public class NetworkThread extends Thread {
    private static final String LOGTAG = "NetworkThread";
    private boolean mKeepRunning;

    private TransInfo mCurrentTransInfo;

    @SuppressWarnings("UnusedDeclaration")
    public TransInfo getCurrentTransInfo() {
        return mCurrentTransInfo;
    }

    private static NetworkThread mThreadInstance;
    private final Context mContext;

    private NetworkThread(Context ctx) {
        mContext = ctx;
    }

    public static NetworkThread getThreadInstance(Context ctx) {
        if (mThreadInstance == null) {
            mThreadInstance = new NetworkThread(ctx);
        }

        return mThreadInstance;
    }

    @Override
    public synchronized void start() {
        mKeepRunning = true;
        super.start();
    }

    @SuppressWarnings({"static-access", "UnusedDeclaration"})
    public synchronized void quit() {
        mKeepRunning = false;
        this.interrupted();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public synchronized boolean isRunning() {
        return mKeepRunning;
    }

    @Override
    public void run() {
        while (mKeepRunning) {
            synchronized (Transaction.mTransQueue) {
                mCurrentTransInfo = null;
                while (Transaction.mTransQueue.isEmpty()) {
                    try {
                        Log.i(LOGTAG, "queue is empty, thread wait...");
                        Transaction.mTransQueue.wait();
                        if (!isRunning()) {
                            return;
                        }
                        Log.i(LOGTAG, "thread wake up");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mCurrentTransInfo = Transaction.mTransQueue.poll();
            }
            if (!Utility.isHaveNetwork(mContext)) {
                Utility.log("-------------no net----------");
                mCurrentTransInfo.reportResult(Helper.RESULT_ERR_NET_DOWN);
                continue;
            }

            // Clear cache if the operation is refresh.
            if (mCurrentTransInfo.isRefresh()) {
                mCurrentTransInfo.clearCache();
            } else if (mCurrentTransInfo.hasCacheData()) {
                // if the operation is not refresh, and we have data
                // in the cache, just use the cache data.
                mCurrentTransInfo.reportResult(Helper.RESULT_DONE);
                continue;
            }

            Utility.log(LOGTAG, "If you can see the phrase, network interaction "
                            + mCurrentTransInfo.getClass());
            switch (mCurrentTransInfo.mHttpMethod) {
                case TransInfo.HTTP_GET: {
                    Response respGet;
                    try {
                        respGet = sendHttpGet(mCurrentTransInfo.genGetRequest());
                    } catch (HttpException e1) {
                        e1.printStackTrace();
                        mCurrentTransInfo.handleHttpException(e1);
                        break;
                    }
                    Utility.log("ypy", "currentInfo name : "
                            + mCurrentTransInfo.getClass().getSimpleName());
                    mCurrentTransInfo.handleResponse(respGet);
                    break;
                }
                case TransInfo.HTTP_POST: {
                    Response respPost;
                    try {
                        respPost = sendHttpPost(mCurrentTransInfo.genPostRequest());
                    } catch (HttpException e) {
                        e.printStackTrace();
                        mCurrentTransInfo.handleHttpException(e);
                        break;
                    }
                    Utility.log("ypy", "currentInfo name : "
                            + mCurrentTransInfo.getClass().getSimpleName());
                    mCurrentTransInfo.handleResponse(respPost);
                    break;
                }

                case TransInfo.HTTP_PUT: {
                    Response respPost;
                    try {
                        respPost = sendHttpPut(mCurrentTransInfo.genPutRequest());
                    } catch (HttpException e) {
                        e.printStackTrace();
                        mCurrentTransInfo.handleHttpException(e);
                        break;
                    }
                    Utility.log("ypy", "currentInfo name : "
                            + mCurrentTransInfo.getClass().getSimpleName());
                    mCurrentTransInfo.handleResponse(respPost);
                    break;
                }

                case TransInfo.HTTP_DELETE: {
                    Response respPost;
                    try {
                        respPost = sendHttpDel(mCurrentTransInfo.genDelRequest());
                    } catch (HttpException e) {
                        e.printStackTrace();
                        mCurrentTransInfo.handleHttpException(e);
                        break;
                    }
                    Utility.log("ypy", "currentInfo name : "
                            + mCurrentTransInfo.getClass().getSimpleName());
                    mCurrentTransInfo.handleResponse(respPost);
                    break;
                }

                default:
                    throw new IllegalArgumentException();
            }

            Log.i(LOGTAG, "clear mCurrentTransInfo");
            mCurrentTransInfo = null;
        }

    }

    @SuppressWarnings("RedundantThrows")
    private Response sendHttpPut(PutRequest put) throws
            HttpException {
        // TODO:
        Log.i(LOGTAG, "sendHttpPut: todo, " + put.toString());
        @SuppressWarnings("UnnecessaryLocalVariable")
        Response respPost = null;
        /*
        HttpClient client = new HttpClient();
        if (put.mHeaders != null) {
            client.setHeaders(put.mHeaders);
        }
        Log.i(LOGTAG, "sendHttpPut, post start!");
        respPost = client.put(put.getRequestUrl());

        Log.i(LOGTAG, "sendHttpPut, put done!");
        */

        return respPost;
    }

    @SuppressWarnings("RedundantThrows")
    private Response sendHttpDel(DeleteRequest del)
            throws HttpException {
        // TODO:
        Log.i(LOGTAG, "sendHttpDel: todo, " + del.toString());
        @SuppressWarnings("UnnecessaryLocalVariable")
        Response respPost = null;
        /*
        HttpClient client = new HttpClient();
        if (del.mHeaders != null) {
            client.setHeaders(del.mHeaders);
        }
        Log.i(LOGTAG, "sendHttpDel, post start!");
        respPost = client.del(del.getRequestUrl());

        Log.i(LOGTAG, "sendHttpDel, post done!");
        */

        return respPost;
    }

    private Response sendHttpPost(PostRequest post)
            throws HttpException {
        Log.i(LOGTAG, "sendHttpPost:" + post.toString());

        Response respPost;

        HttpClient client = new HttpClient();
        if (post.mHeaders != null) {
            client.setHeaders(post.mHeaders);
        }
        Log.i(LOGTAG, "sendHttpPost, post start!");
        respPost = client.post(post.mRequestUrl, post.mParams);

        Log.i(LOGTAG, "sendHttpPost, post done!");

        return respPost;
    }

    private Response sendHttpGet(GetRequest get) throws HttpException {
        Log.i(LOGTAG, "sendHttpGet: " + get.toString());

        Response respGet;

        HttpClient client = new HttpClient();
        if (get.mHeaders != null) {
            client.setHeaders(get.mHeaders);
        }
        Log.i(LOGTAG, "sendHttpGet, get start!");
        respGet = client.get(get.getRequestUrl());
        Log.i(LOGTAG, "sendHttpGet, get done!");
        return respGet;
    }
}
