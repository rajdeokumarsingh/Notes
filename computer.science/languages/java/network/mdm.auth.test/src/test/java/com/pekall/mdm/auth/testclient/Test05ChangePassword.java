package com.pekall.mdm.auth.testclient;

import com.pekall.mdm.auth.testclient.util.TestConfig;
import com.pekall.mdm.auth.testclient.util.Debug;
import com.pekall.mdm.auth.testclient.util.HttpUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Before;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

public class Test05ChangePassword extends TestCase {

    private static MemcachedClient memcachedClient;

    static {
        initMemcache();
    }

    /** create connection with the memory cache cluster */
    private static void initMemcache() {
        try {
            memcachedClient = new MemcachedClient(
                    AddrUtil.getAddresses(TestConfig.MEMCACHED_SERVERS));
        } catch (Exception e) {
            memcachedClient = null;
        }
        assertNotNull(memcachedClient);
    }

    @Before
    public void setUp() throws Exception {
        Debug.setVerboseDebugLog(true);
    }

    @After
    public void tearDown() throws Exception {
        Debug.setVerboseDebugLog(false);
    }

    public void testFailWithoutLogin() throws Exception {

        // There no ngnix in test environment, could only test in the production environment
        if (TestConfig.PROFILE == TestConfig.Profile.PRODUCTION) {
            List<NameValuePair> postParams = new ArrayList<NameValuePair>();
            postParams.add(new BasicNameValuePair(TestConfig.PARAM_USERNAME, TestConfig.USERNAME_VALID));
            postParams.add(new BasicNameValuePair(TestConfig.PARAM_OLD_PASSWORD, TestConfig.PASSWORD_OLD));
            postParams.add(new BasicNameValuePair(TestConfig.PARAM_NEW_PASSWORD, TestConfig.PASSWORD_NEW));

            HttpResponse response = HttpUtil.doHttpPost(
                    TestConfig.MDM_PS_ADMIN_CHANGE_PWS_URL, null, postParams);
            Debug.logVerbose(response.toString());
            assertEquals(response.getStatusLine().getStatusCode(), 401);
        }
    }

    // use to change password manually, not for test
    public void testOK1() throws Exception {
//        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
//                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_NEW);
//
//        Debug.logVerbose(response.toString());
//        assertEquals(response.getStatusLine().getStatusCode(), 200);
//
//        String token = HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE);
//        List<Header> headers = new ArrayList<Header>();
//        headers.add(new BasicHeader(TestConfig.HTTP_HDR_COOKIE, token));
//
//        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
//        postParams.add(new BasicNameValuePair(
//                TestConfig.PARAM_USERNAME, TestConfig.USERNAME_VALID));
//        postParams.add(new BasicNameValuePair(
//                TestConfig.PARAM_OLD_PASSWORD, TestConfig.PASSWORD_NEW));
//        postParams.add(new BasicNameValuePair(
//                TestConfig.PARAM_NEW_PASSWORD, TestConfig.PASSWORD_OLD));
//        response = HttpUtil.doHttpPost(TestConfig.MDM_PS_ADMIN_CHANGE_PWS_URL,
//                headers, postParams);
//        Debug.logVerbose(response.toString());
//        assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    public void testOK() throws Exception {

        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);

        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        String token = HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE);
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader(TestConfig.HTTP_HDR_COOKIE, token));

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair(TestConfig.PARAM_USERNAME, TestConfig.USERNAME_VALID));
        postParams.add(new BasicNameValuePair(TestConfig.PARAM_OLD_PASSWORD, TestConfig.PASSWORD_OLD));
        postParams.add(new BasicNameValuePair(TestConfig.PARAM_NEW_PASSWORD, TestConfig.PASSWORD_NEW));
        response = HttpUtil.doHttpPost(TestConfig.MDM_PS_ADMIN_CHANGE_PWS_URL, headers, postParams);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // restore password to old
        response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_NEW);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        token = HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE);
        headers = new ArrayList<Header>();
        headers.add(new BasicHeader(TestConfig.HTTP_HDR_COOKIE, token));

        postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair(TestConfig.PARAM_USERNAME, TestConfig.USERNAME_VALID));
        postParams.add(new BasicNameValuePair(TestConfig.PARAM_OLD_PASSWORD, TestConfig.PASSWORD_NEW));
        postParams.add(new BasicNameValuePair(TestConfig.PARAM_NEW_PASSWORD, TestConfig.PASSWORD_OLD));
        response = HttpUtil.doHttpPost(TestConfig.MDM_PS_ADMIN_CHANGE_PWS_URL, headers, postParams);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
    }
}
