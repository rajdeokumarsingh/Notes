
package com.pekall.mobileutil;

import android.text.TextUtils;
import android.util.Pair;
import com.pekall.demo.Utility;

import java.util.ArrayList;

class DeleteRequest {
    @SuppressWarnings("UnusedDeclaration")
    public DeleteRequest(String url) {
        mRequestUrl = url;
    }

    private final String mRequestUrl;
    private ArrayList<String> mParams;
    private ArrayList<Pair<String, String>> mHeaders;

    @SuppressWarnings("UnusedDeclaration")
    public ArrayList<Pair<String, String>> getHeaders() {
        return mHeaders;
    }

    void appendParam(String name, String value) {
        if (name == null || value == null) {
            return;
        }

        if (mParams == null)
            mParams = new ArrayList<String>();

        mParams.add(name + "=" + value);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void appendOptionalParam(String name, String value) {
        // if (TextUtils.isEmpty(name) || TextUtils.isEmpty(value)) {
        // return;
        // }
        appendParam(name, value);
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getRequestUrl() {
        StringBuilder sb = new StringBuilder(mRequestUrl);
        if (mParams == null || mParams.isEmpty())
            return sb.toString();

        sb.append("?").append(mParams.get(0));

        for (int i = 1; i < mParams.size(); i++) {
            sb.append("&").append(mParams.get(i));
        }
        Utility.log(sb.toString());
        return sb.toString();
    }

    @SuppressWarnings({"unchecked", "UnusedDeclaration"})
    public void appendHeaders(String name, String value) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("header name should not be null");
        }

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

    @Override
    public String toString() {
        StringBuilder headers = new StringBuilder();

        if (mHeaders != null) {
            headers.append("headers: ");
            for (Pair<String, String> p : mHeaders) {
                if (p == null)
                    continue;

                headers.append("[").append(p.first).append(":").append(p.second).append("]");
            }
        }
        return "Get Request, url: " + mRequestUrl + ", " + headers.toString();
    }
}
