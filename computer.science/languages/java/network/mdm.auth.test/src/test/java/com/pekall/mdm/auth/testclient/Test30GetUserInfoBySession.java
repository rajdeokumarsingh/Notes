package com.pekall.mdm.auth.testclient;

import com.pekall.mdm.auth.testclient.util.TestConfig;
import com.pekall.mdm.auth.testclient.util.Debug;
import com.pekall.mdm.auth.testclient.util.HttpUtil;
import junit.framework.TestCase;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import org.junit.After;
import org.junit.Before;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test30GetUserInfoBySession extends TestCase {

    @Before
    public void setUp() throws Exception {
        Debug.setVerboseDebugLog(true);
    }

    @After
    public void tearDown() throws Exception {
        Debug.setVerboseDebugLog(false);
    }

    public void testFail() throws Exception {
        // no session
        HttpResponse response = HttpUtil.doHttpGet(TestConfig.AUTH_GET_USER_INFO_URL, null);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 404);

        // wrong session id
        response = HttpUtil.doHttpGet(TestConfig.AUTH_GET_USER_INFO_URL + "?session=xxx", null);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 404);
    }

    public void testFailAfterLogout() throws Exception {

        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        assertEquals(HttpUtil.getHeaderNumber(response, TestConfig.HTTP_HDR_SET_COOKIE), 1);
        String cookie = HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE);

        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL, cookie);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        String[] tokes = cookie.split(";");
        String[] tokes1 = tokes[0].split("=");
        String sessionId = tokes1[1];

        // after logout the session should be gone, and we should not find it
        response = HttpUtil.doHttpGet(TestConfig.AUTH_GET_USER_INFO_URL + "?session=" + sessionId, null);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 404);
    }

    public void testOKWithSessionId() throws Exception {

        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);

        Debug.logVerbose(response.toString());

        assertEquals(response.getStatusLine().getStatusCode(), 200);

        assertEquals(HttpUtil.getHeaderNumber(response, TestConfig.HTTP_HDR_SET_COOKIE), 1);
        String cookie = HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE);

        String[] tokes = cookie.split(";");
        String[] tokes1 = tokes[0].split("=");
        String sessionId = tokes1[1];

        response = HttpUtil.doHttpGet(TestConfig.AUTH_GET_USER_INFO_URL + "?session=" + sessionId, null);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        assertTrue(response.getEntity().getContentType().getValue().startsWith("application/json"));

        InputStream in = response.getEntity().getContent();
        byte[] bytes = new byte[1024];
        int count = in.read(bytes);
        String json = new String(bytes, 0, count, "utf-8");
        assertTrue(json.contains(TestConfig.USERNAME_VALID));
        Debug.logVerbose(json);

        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL, cookie);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    public void testOKWithCookie() throws Exception {

        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        assertEquals(HttpUtil.getHeaderNumber(response, TestConfig.HTTP_HDR_SET_COOKIE), 1);

        String cookie = HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE);
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader(TestConfig.HTTP_HDR_COOKIE, cookie));

        response = HttpUtil.doHttpGet(TestConfig.AUTH_GET_USER_INFO_URL, headers);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        assertTrue(response.getEntity().getContentType().getValue().startsWith("application/json"));

        InputStream in = response.getEntity().getContent();
        byte[] bytes = new byte[1024];
        int count = in.read(bytes);
        String json = new String(bytes, 0, count, "utf-8");
        assertTrue(json.contains(TestConfig.USERNAME_VALID));
        Debug.logVerbose(json);

        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL, cookie);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
    }
}

