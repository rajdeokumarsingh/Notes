package com.pekall.mdm.auth.testclient;

import com.pekall.mdm.auth.testclient.util.TestConfig;
import com.pekall.mdm.auth.testclient.util.Debug;
import com.pekall.mdm.auth.testclient.util.HttpUtil;
import junit.framework.TestCase;
import org.apache.http.HttpResponse;
import org.junit.After;
import org.junit.Before;

public class Test02Logout extends TestCase {

    @Before
    public void setUp() throws Exception {
        // Debug.setVerboseDebugLog(true);
    }

    @After
    public void tearDown() throws Exception {
        Debug.setVerboseDebugLog(false);
    }

    public void testLogoutFail() throws Exception {

        // logout path error
        HttpResponse response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL + "/invalid",
                "JSESSIONID=667FF2FE6F3668DE4724E1588A975B11");
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);


        // empty cookie
        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL, null);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);

        // error cookie
        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL, "JSESSIONID=667FF2FE6F3668DE4724E1588A975B11");
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }

    public void testLogoutFail1() throws Exception {
        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);
        Debug.logVerbose(response.toString());

        // first logout should be OK
        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL,
                HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // logout second time should not be OK
        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL,
                HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }

    public void testLogoutOK() throws Exception {
        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);
        Debug.logVerbose(response.toString());

        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL,
                HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        assertEquals(HttpUtil.getHeaderNumber(response, TestConfig.HTTP_HDR_SET_COOKIE), 1);
        assertTrue(HttpUtil.getSingleHeader(response,
                TestConfig.HTTP_HDR_SET_COOKIE).startsWith("JSESSIONID="));
    }
}
