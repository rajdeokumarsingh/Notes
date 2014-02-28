
package com.pekall.mobileutil;

import android.text.TextUtils;
import android.util.Pair;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;

class PostRequest {
    /**
     * Protocol method: 0 for HTTP, 1 for HTTPS
     */

    @SuppressWarnings("UnusedDeclaration")
    public PostRequest(String url) {
        mRequestUrl = url;
    }

    final String mRequestUrl;
    ArrayList<NameValuePair> mParams;

    @SuppressWarnings("UnusedDeclaration")
    public ArrayList<NameValuePair> getParams() {
        return mParams;
    }

    ArrayList<Pair<String, String>> mHeaders;

    // String mPostBody;

    @SuppressWarnings("UnusedDeclaration")
    public ArrayList<Pair<String, String>> getHeaders() {
        return mHeaders;
    }

    @Override
    public String toString() {
        StringBuilder params = new StringBuilder();
        StringBuilder headers = new StringBuilder();

        if (mParams != null) {
            params.append("Params: ");
            for (NameValuePair p : mParams) {
                if (p == null)
                    continue;

                if ("password".equals(p.getName()))
                    continue;

                params.append("[").append(p.getName()).append(":").append(p.getValue()).append("]");
            }
        }

        if (mHeaders != null) {
            headers.append("headers: ");
            for (Pair<String, String> p : mHeaders) {
                if (p == null)
                    continue;

                headers.append("[").append(p.first).append(":").append(p.second).append("]");
            }
        }
        return "PostRequest, url: " + mRequestUrl + ", " + params.toString()
                + ", " + headers.toString();
    }

    @SuppressWarnings({"unchecked", "UnusedDeclaration"})
    public void appendHeaders(String name, String value) {
        if (TextUtils.isEmpty(name))
            return;

        appendHeaders(new Pair<String, String>(name,
                StringUtil.toSafeString(value)));
    }

    void appendHeaders(Pair<String, String>... pairs) {
        if (pairs == null)
            return;

        for (Pair<String, String> p : pairs) {
            if (p == null)
                continue;

            if (mHeaders == null)
                mHeaders = new ArrayList<Pair<String, String>>();

            mHeaders.add(p);
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public void appendParams(BasicNameValuePair... pairs) {
        for (BasicNameValuePair p : pairs) {
            if (p == null)
                continue;

            if (mParams == null)
                mParams = new ArrayList<NameValuePair>();

            mParams.add(p);
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public void appendOptionParam(BasicNameValuePair... pairs) {
        for (BasicNameValuePair p : pairs) {
            if (p == null || TextUtils.isEmpty(p.getValue()))
                continue;

            if (mParams == null)
                mParams = new ArrayList<NameValuePair>();

            mParams.add(p);
        }
    }
}
