package com.pekall.mdm.util;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class HttpUtil {

    public static HttpResponse doHttpGet(String url, List<Header> headers) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        if (headers != null && headers.size() > 0) {
            for (Header header : headers) {
                get.addHeader(header);
            }
        }

        Debug.logVerbose(dumpGetMethod(get));
        return httpclient.execute(get);
    }

    public static HttpResponse doHttpPost(String url, List<NameValuePair> postParams) throws IOException {
        return doHttpPost(url, null, postParams);
    }

    public static HttpResponse doHttpPost(
            String url, List<Header> headers, List<NameValuePair> postParams) throws IOException {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        if (headers != null && headers.size() > 0) {
            for (Header header : headers) {
                post.addHeader(header);
            }
        }
        if (postParams != null && postParams.size() > 0) {
            HttpEntity entity = new UrlEncodedFormEntity(postParams, HTTP.UTF_8);
            post.setEntity(entity);
        }
        Debug.logVerbose(dumpPostMethod(post));
        return httpclient.execute(post);
    }

    public static HttpResponse doLogin(String url, String username, String password) throws IOException {
        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair(Const.PARAM_USERNAME, username));
        postParams.add(new BasicNameValuePair(Const.PARAM_PASSWORD, password));

        return HttpUtil.doHttpPost(url, postParams);
    }

    public static HttpResponse doLogout(String url, String cookie) throws IOException {
        List<Header> headers = new ArrayList<Header>();
        if (cookie != null) {
            headers.add(new BasicHeader(Const.HTTP_HDR_COOKIE, cookie));
        }
        return doHttpPost(url, headers, null);
    }

    public static HttpResponse doAccessApi(String url, String cookie) throws IOException {
        HttpResponse response;
        List<Header> headers = new ArrayList<Header>();
        if (cookie != null) {
            headers.add(new BasicHeader(Const.HTTP_HDR_COOKIE, cookie));
        }
        response = HttpUtil.doHttpGet(url, headers);
        return response;
    }

    public static String getSingleHeader(HttpResponse response, String headerName) {
        Header[] headers = response.getHeaders(headerName);
        return headers[0].getValue();
    }

    public static int getHeaderNumber(HttpResponse response, String headerName) {
        Header[] headers = response.getHeaders(headerName);
        return headers.length;
    }

    public static String dumpPostMethod(HttpPost post) {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP Post: ")
                .append(post.getURI().toString()).append(", ")
                .append("[cooke: ").append(post.getFirstHeader("Cookie")).append("], ");
        try {
            HttpEntity entity = post.getEntity();
            if (entity != null) {
                sb.append("[form: ");
                BufferedInputStream bis = new BufferedInputStream(post.getEntity().getContent());
                byte[] bytes = new byte[bis.available()];
                while (bis.read(bytes) > 0) {
                    sb.append(new String(bytes));
                }
                sb.append("]");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String dumpGetMethod(HttpGet get) {
        StringBuilder sb = new StringBuilder();
        sb.append("HTTP Get: ")
                .append(get.getURI().toString()).append(", ")
                .append("[header: ").append(get.getFirstHeader("Cookie")).append("], ");

        return sb.toString();
    }
}
