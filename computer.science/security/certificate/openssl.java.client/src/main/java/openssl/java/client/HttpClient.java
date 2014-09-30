package openssl.java.client;

/**
 * Created by jiangrui on 8/15/14.
 */

import javafx.util.Pair;
import org.apache.commons.logging.Log;
import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.TextUtils;

import javax.net.ssl.SSLHandshakeException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Wrap of org.apache.http.impl.client.DefaultHttpClient
 *
 * @author wjl
 */
public class HttpClient {

    private static final String TAG = "HttpClient";
    private final static boolean DEBUG = true;

    /** OK: Success! */
    public static final int OK = 200;

    public static final int REQUEST_OK = 201;

    /** Not Modified: There was no new data to return. */
    public static final int NOT_MODIFIED = 304;
    /**
     * Bad Request: The request was invalid. An accompanying error message will
     * explain why. This is the status code will be returned during rate
     * limiting.
     */
    public static final int BAD_REQUEST = 400;
    /** Not Authorized: Authentication credentials were missing or incorrect. */
    public static final int NOT_AUTHORIZED = 401;
    /**
     * Forbidden: The request is understood, but it has been refused. An
     * accompanying error message will explain why.
     */
    public static final int FORBIDDEN = 403;
    /**
     * Not Found: The URI requested is invalid or the resource requested, such
     * as a user, does not exists.
     */
    public static final int NOT_FOUND = 404;
    /**
     * Not Acceptable: Returned by the Search API when an invalid format is
     * specified in the request.
     */
    public static final int NOT_ACCEPTABLE = 406;
    /**
     * Internal Server Error: Something is broken. Please post to the group so
     * the Weibo team can investigate.
     */
    public static final int INTERNAL_SERVER_ERROR = 500;
    /** Bad Gateway: Weibo is down or being upgraded. */
    public static final int BAD_GATEWAY = 502;
    /**
     * Service Unavailable: The Weibo servers are up, but overloaded with
     * requests. Try again later. The search and trend methods use this to
     * indicate when you are being rate limited.
     */
    public static final int SERVICE_UNAVAILABLE = 503;

    private static final int CONNECTION_TIMEOUT_MS = 30 * 1000;
    private static final int SOCKET_TIMEOUT_MS = 30 * 1000;

    public static final int RETRIEVE_LIMIT = 20;
    public static final int RETRIED_TIME = 3;

    private static final String SERVER_HOST = "api.fanfou.com";

    private DefaultHttpClient mClient;
    private AuthScope mAuthScope;
    private BasicHttpContext localcontext;

    private String mUserId;
    private String mPassword;

    private String mToken;
    private static boolean isAuthenticationEnabled = false;

    /**
     * HTTP headers
     */
    ArrayList<Pair<String, String>> mHeaders;

    public HttpClient() {
        prepareHttpClient();
    }

    /**
     * @param user_id
     *            auth user
     * @param password
     *            auth password
     */
    public HttpClient(String user_id, String password) {
        prepareHttpClient();
        setCredentials(user_id, password);
    }

    /**
     * Empty the credentials
     */
    public void reset() {
        setCredentials("", "");
    }

    /**
     * @return authed user id
     */
    public String getUserId() {
        return mUserId;
    }

    /**
     * @return authed user password
     */
    public String getPassword() {
        return mPassword;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        this.mToken = token;
    }

    /**
     * @param host
     *            the hostname (IP or DNS name)
     * @param port
     *            the port number. -1 indicates the scheme default port.
     * @param scheme
     *            the name of the scheme. null indicates the default scheme
     */
    public void setProxy(String host, int port, String scheme) {
        HttpHost proxy = new HttpHost(host, port, scheme);
        mClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
    }

    public void removeProxy() {
        mClient.getParams().removeParameter(ConnRoutePNames.DEFAULT_PROXY);
    }

    private void enableDebug() {
        System.out.println("enable apache.http debug");

        java.util.logging.Logger.getLogger("org.apache.http").setLevel(
                java.util.logging.Level.FINEST);
        java.util.logging.Logger.getLogger("org.apache.http.wire").setLevel(
                java.util.logging.Level.FINER);
        java.util.logging.Logger.getLogger("org.apache.http.headers").setLevel(
                java.util.logging.Level.OFF);
    }

    private SSLSocketFactory newSslSocketFactory() {
        try {

            KeyStore trustStore = KeyStore.getInstance(KeyStore
                    .getDefaultType());
            trustStore.load(null, null);

            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            return sf;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    /**
     * Setup DefaultHttpClient Use ThreadSafeClientConnManager.
     */
    private void prepareHttpClient() {
        if (DEBUG) {
            enableDebug();
        }

        // Create and initialize HTTP parameters
        HttpParams params = new BasicHttpParams();
        ConnManagerParams.setMaxTotalConnections(params, 10);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

        // Create and initialize scheme registry
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schemeRegistry
                .register(new Scheme("https", newSslSocketFactory(), 443));

        // Create an HttpClient with the ThreadSafeClientConnManager.
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
                params, schemeRegistry);
        mClient = new DefaultHttpClient(cm, params);
        // Setup BasicAuth
        BasicScheme basicScheme = new BasicScheme();
        mAuthScope = new AuthScope(SERVER_HOST, AuthScope.ANY_PORT);

        // mClient.setAuthSchemes(authRegistry);
        mClient.setCredentialsProvider(new BasicCredentialsProvider());

        // Generate BASIC scheme object and stick it to the local
        // execution context
        localcontext = new BasicHttpContext();
        localcontext.setAttribute("preemptive-auth", basicScheme);

        // first request interceptor
        mClient.addRequestInterceptor(preemptiveAuth, 0);
        // Support GZIP
        mClient.addResponseInterceptor(gzipResponseIntercepter);

        // TODO: need to release this connection in httpRequest()
        // cm.releaseConnection(conn, validDuration, timeUnit);
        // httpclient.getConnectionManager().shutdown();
    }

    /**
     * HttpRequestInterceptor for DefaultHttpClient
     */
    private static HttpRequestInterceptor preemptiveAuth = new HttpRequestInterceptor() {

        public void process(final HttpRequest request, final HttpContext context) {
            AuthState authState = (AuthState) context
                    .getAttribute(ClientContext.TARGET_AUTH_STATE);
            CredentialsProvider credsProvider = (CredentialsProvider) context
                    .getAttribute(ClientContext.CREDS_PROVIDER);
            HttpHost targetHost = (HttpHost) context
                    .getAttribute(ExecutionContext.HTTP_TARGET_HOST);

            if (authState.getAuthScheme() == null) {
                AuthScope authScope = new AuthScope(targetHost.getHostName(),
                        targetHost.getPort());
                Credentials creds = credsProvider.getCredentials(authScope);
                if (creds != null) {
                    authState.setAuthScheme(new BasicScheme());
                    authState.setCredentials(creds);
                }
            }
        }
    };

    private static HttpResponseInterceptor gzipResponseIntercepter = new HttpResponseInterceptor() {

        public void process(HttpResponse response, HttpContext context)
                throws org.apache.http.HttpException, IOException {
            HttpEntity entity = response.getEntity();
            Header ceheader = entity.getContentEncoding();
            if (ceheader != null) {
                HeaderElement[] codecs = ceheader.getElements();
                for (int i = 0; i < codecs.length; i++) {
                    if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                        response.setEntity(new GzipDecompressingEntity(response
                                .getEntity()));
                        return;
                    }
                }
            }

        }
    };

    static class GzipDecompressingEntity extends HttpEntityWrapper {

        public GzipDecompressingEntity(final HttpEntity entity) {
            super(entity);
        }

        @Override
        public InputStream getContent() throws IOException,
                IllegalStateException {

            // the wrapped entity's getContent() decides about repeatability
            InputStream wrappedin = wrappedEntity.getContent();
            return new GZIPInputStream(wrappedin);
        }

        @Override
        public long getContentLength() {
            // length of ungzipped content is not known
            return -1;
        }

    }

    /**
     * Setup Credentials for HTTP Basic Auth
     *
     * @param username
     * @param password
     */
    public void setCredentials(String username, String password) {
        mUserId = username;
        mPassword = password;
        mClient.getCredentialsProvider().setCredentials(mAuthScope,
                new UsernamePasswordCredentials(username, password));
        isAuthenticationEnabled = true;
    }

    public Response post(String url, List<NameValuePair> postParams,
                         boolean authenticated) throws HttpException {
        if (null == postParams) {
            postParams = new ArrayList<NameValuePair>();
        }
        return httpRequest(url, postParams, authenticated, HttpPost.METHOD_NAME);
    }

    public Response post(String url, List<NameValuePair> params)
            throws HttpException {
        return httpRequest(url, params, false, HttpPost.METHOD_NAME);
    }

    public Response post(String url, boolean authenticated)
            throws HttpException {
        return httpRequest(url, null, authenticated, HttpPost.METHOD_NAME);
    }

    public Response post(String url) throws HttpException {
        return httpRequest(url, null, false, HttpPost.METHOD_NAME);
    }

    public Response post(String url, File file) throws HttpException {
        return httpRequest(url, null, file, false, HttpPost.METHOD_NAME);
    }

    /**
     * @param url
     * @param file
     * @param authenticate
     * @return
     * @throws HttpException
     */
    public Response post(String url, File file, boolean authenticate)
            throws HttpException {
        return httpRequest(url, null, file, authenticate, HttpPost.METHOD_NAME);
    }

    public Response get(String url, ArrayList<NameValuePair> params,
                        boolean authenticated) throws HttpException {
        return httpRequest(url, params, authenticated, HttpGet.METHOD_NAME);
    }

    public Response get(String url, ArrayList<NameValuePair> params)
            throws HttpException {
        return httpRequest(url, params, false, HttpGet.METHOD_NAME);
    }

    public Response get(String url) throws HttpException {
        return httpRequest(url, null, false, HttpGet.METHOD_NAME);
    }

    public Response get(String url, boolean authenticated) throws HttpException {
        return httpRequest(url, null, authenticated, HttpGet.METHOD_NAME);
    }

    public Response httpRequest(String url, List<NameValuePair> postParams,
                                boolean authenticated, String httpMethod) throws HttpException {
        return httpRequest(url, postParams, null, authenticated, httpMethod);
    }

    public Response httpRequest(String url, List<NameValuePair> postParams,
                                File file, boolean authenticated, String httpMethod)
            throws HttpException {
        // System.out.println("Sending " + httpMethod + " request to " + url);
        // DebugTimer.betweenStart("HTTP");

        URI uri = createURI(url);

        HttpResponse response = null;
        Response res = null;
        HttpUriRequest method = null;

        // Create POST, GET or DELETE METHOD
        method = createMethod(httpMethod, uri, file, postParams);
        // Setup ConnectionParams, Request Headers
        SetupHTTPConnectionParams(method);

        // Execute Request
        try {
            response = mClient.execute(method);
            res = new Response(response);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw new HttpException(e.getMessage(), e);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new HttpException(ioe.getMessage(), ioe);
        } catch (Exception ioe) {
            ioe.printStackTrace();
            throw new HttpException(ioe.getMessage(), ioe);
        }

        if (response != null) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                System.out.println("statusCode : " + statusCode);
            }
            // It will throw a Exception while status code is not 200
            HandleResponseStatusCode(statusCode, res);
        } else {
           System.out.println("response is null");
        }

        // DebugTimer.betweenEnd("HTTP");
        return res;
    }

    public void close() {
        mClient.getConnectionManager().shutdown();
    }

    /**
     * CreateURI from URL string
     *
     * @param url
     * @return request URI
     * @throws HttpException
     *             Cause by URISyntaxException
     */
    private URI createURI(String url) throws HttpException {
        URI uri;

        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new HttpException("Invalid URL.");
        }

        return uri;
    }

    /**
     * Setup HTTPConncetionParams
     *  {}
     * @param method
     */
    private void SetupHTTPConnectionParams(HttpUriRequest method) {
        HttpConnectionParams.setConnectionTimeout(method.getParams(),
                CONNECTION_TIMEOUT_MS);
        HttpConnectionParams
                .setSoTimeout(method.getParams(), SOCKET_TIMEOUT_MS);
        mClient.setHttpRequestRetryHandler(requestRetryHandler);
        method.addHeader("Accept-Encoding", "gzip, deflate");
        method.addHeader("Accept-Charset", "UTF-8,*;q=0.5");
        method.addHeader("Pekall-Lang", "zh-CN");
        method.setHeader("Connection",  "Keep-Alive");
        if (!TextUtils.isEmpty(mToken))
            method.addHeader("Authorization", this.mToken);

        // Add headers for transactions
        if (mHeaders != null) {
            for (Pair<String, String> pair : mHeaders) {
                if (pair == null)
                    continue;
                method.addHeader(pair.getKey(), pair.getValue());
            }
        }
    }

    private HttpUriRequest createMethod(String httpMethod, URI uri, File file,
                                        List<NameValuePair> postParams) throws HttpException {

        HttpUriRequest method;

        if (httpMethod.equalsIgnoreCase(HttpPost.METHOD_NAME)) {
            // POST METHOD

            HttpPost post = new HttpPost(uri);
            // See this:
            // http://groups.google.com/group/twitter-development-talk/browse_thread/thread/e178b1d3d63d8e3b
            post.getParams().setBooleanParameter(
                    "http.protocol.expect-continue", false);

            try {
                HttpEntity entity = null;
                if (null != file) {
//					entity = createMultipartEntity("photo", file,
//					postParams);
//					post.setEntity(entity);
                } else if (null != postParams) {
                    entity = new UrlEncodedFormEntity(postParams, HTTP.UTF_8);
                }
                post.setEntity(entity);
            } catch (IOException ioe) {
                throw new HttpException(ioe.getMessage(), ioe);
            }

            method = post;
        } else if (httpMethod.equalsIgnoreCase(HttpDelete.METHOD_NAME)) {
            method = new HttpDelete(uri);
        } else {
            method = new HttpGet(uri);
        }

        return method;
    }

    private static String getCause(int statusCode) {
        String cause = null;
        switch (statusCode) {
            case NOT_MODIFIED:
                break;
            case BAD_REQUEST:
                cause = "The request was invalid.  An accompanying error message will explain why. This is the status code will be returned during rate limiting.";
                break;
            case NOT_AUTHORIZED:
                cause = "Authentication credentials were missing or incorrect.";
                break;
            case FORBIDDEN:
                cause = "The request is understood, but it has been refused.  An accompanying error message will explain why.";
                break;
            case NOT_FOUND:
                cause = "The URI requested is invalid or the resource requested, such as a user, does not exists.";
                break;
            case NOT_ACCEPTABLE:
                cause = "Returned by the Search API when an invalid format is specified in the request.";
                break;
            case INTERNAL_SERVER_ERROR:
                cause = "Something is broken.  Please post to the group so the Pekall team can investigate.";
                break;
            case BAD_GATEWAY:
                cause = "Pekall Weather is down or being upgraded.";
                break;
            case SERVICE_UNAVAILABLE:
                cause = "Service Unavailable: The Weibo servers are up, but overloaded with requests. Try again later. The search and trend methods use this to indicate when you are being rate limited.";
                break;
            default:
                cause = "";
        }
        return statusCode + ":" + cause;
    }

    public boolean isAuthenticationEnabled() {
        return isAuthenticationEnabled;
    }

    public static void log(String msg) {
        if (DEBUG) {
            System.out.println(msg);
        }
    }

    private void HandleResponseStatusCode(int statusCode, Response res)
            throws HttpException {
        String msg = getCause(statusCode) + "\n";
        // RefuseError error = null;

        switch (statusCode) {
            // It's OK, do nothing
            case OK:
            case REQUEST_OK:
            case BAD_REQUEST:
            case INTERNAL_SERVER_ERROR:
                break;

            // Mine mistake, Check the Log
            case NOT_MODIFIED:
//		case BAD_REQUEST:
            case NOT_FOUND:
            case NOT_ACCEPTABLE:
                throw new HttpException(msg + res.asString(), statusCode);

                // UserName/Password incorrect
            case NOT_AUTHORIZED:
                throw new HttpAuthException(msg + res.asString(), statusCode);

                // Server will return a error message, use
                // HttpRefusedException#getError() to see.
            case FORBIDDEN:
                throw new HttpRefusedException(msg, statusCode);

                // Something wrong with server
//		case INTERNAL_SERVER_ERROR:
            case BAD_GATEWAY:
            case SERVICE_UNAVAILABLE:
                throw new HttpServerException(msg, statusCode);

                // Others
            default:
                throw new HttpException(msg + res.asString(), statusCode);
        }
    }

    public static String encode(String value) throws HttpException {
        try {
            return URLEncoder.encode(value, HTTP.UTF_8);
        } catch (UnsupportedEncodingException e_e) {
            throw new HttpException(e_e.getMessage(), e_e);
        }
    }

    public static String encodeParameters(ArrayList<NameValuePair> params)
            throws HttpException {
        StringBuffer buf = new StringBuffer();
        for (int j = 0; j < params.size(); j++) {
            if (j != 0) {
                buf.append("&");
            }
            try {
                buf.append(URLEncoder.encode(params.get(j).getName(), "UTF-8"))
                        .append("=")
                        .append(URLEncoder.encode(params.get(j).getValue(),
                                "UTF-8"));
            } catch (UnsupportedEncodingException neverHappen) {
                throw new HttpException(neverHappen.getMessage(), neverHappen);
            }
        }
        return buf.toString();
    }

    private static HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {

        public boolean retryRequest(IOException exception, int executionCount,
                                    HttpContext context) {
            if (executionCount >= RETRIED_TIME) {
                // Do not retry if over max retry count
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                // Retry if the server dropped connection on us
                return true;
            }
            if (exception instanceof SSLHandshakeException) {
                // Do not retry on SSL handshake exception
                return false;
            }
            HttpRequest request = (HttpRequest) context
                    .getAttribute(ExecutionContext.HTTP_REQUEST);
            boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
            if (!idempotent) {
                // Retry if the request is considered idempotent
                return true;
            }
            return false;
        }
    };

    public ArrayList<Pair<String, String>> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(ArrayList<Pair<String, String>> headers) {
        this.mHeaders = headers;
    }
}
