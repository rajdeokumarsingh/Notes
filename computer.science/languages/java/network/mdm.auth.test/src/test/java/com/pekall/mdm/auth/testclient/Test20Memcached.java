package com.pekall.mdm.auth.testclient;

import com.pekall.mdm.auth.testclient.util.TestConfig;
import com.pekall.mdm.auth.testclient.util.Debug;
import com.pekall.mdm.auth.testclient.util.HttpUtil;
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

import java.util.ArrayList;
import java.util.List;

public class Test20Memcached extends TestCase {

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

    public void testMemcached() throws Exception {
        memcachedClient.set("testKey", 6000, "testValue");
        assertEquals("testValue", memcachedClient.get("testKey"));
    }

    public void testClearMemcache() throws Exception {
        memcachedClient.delete(TestConfig.USERNAME_VALID);
        assertNull(memcachedClient.get(TestConfig.USERNAME_VALID));
    }

    public void testLoginOKAndMemCachedUpdate() throws Exception {
        memcachedClient.delete(TestConfig.USERNAME_VALID);
        assertNull(memcachedClient.get(TestConfig.USERNAME_VALID));

        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // after login, user info will be updated to memcached
        assertNotNull(memcachedClient.get(TestConfig.USERNAME_VALID));

        assertEquals(HttpUtil.getHeaderNumber(response, TestConfig.HTTP_HDR_SET_COOKIE), 1);
        assertTrue(HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE).startsWith("JSESSIONID="));
        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL,
                HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    public void testRenewUserOkAndMemcachedUpdated() throws Exception {

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

        assertNotNull(memcachedClient.get(TestConfig.USERNAME_VALID));

        // change password back to old
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

        // after change password, entry in memcached should be delete
        assertNotNull(memcachedClient.get(TestConfig.USERNAME_VALID));
    }

}
