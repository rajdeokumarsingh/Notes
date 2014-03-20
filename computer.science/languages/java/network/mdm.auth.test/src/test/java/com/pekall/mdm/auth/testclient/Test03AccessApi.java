package com.pekall.mdm.auth.testclient;

import com.pekall.mdm.auth.testclient.util.TestConfig;
import com.pekall.mdm.auth.testclient.util.Debug;
import com.pekall.mdm.auth.testclient.util.HttpUtil;
import junit.framework.TestCase;
import org.apache.http.HttpResponse;
import org.junit.After;
import org.junit.Before;

public class Test03AccessApi extends TestCase {

    @Before
    public void setUp() throws Exception {
        // Debug.setVerboseDebugLog(true);
    }

    @After
    public void tearDown() throws Exception {
        Debug.setVerboseDebugLog(false);
    }

    public void testAccessWithoutLogin() throws Exception {
        HttpResponse response = HttpUtil.doAccessApi(TestConfig.NGNIX_ADDR + TestConfig.MDM_TEST_PATH, null);
        Debug.logVerbose(response.toString());

        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }

    public void testAccessWithWrongToken() throws Exception {

        HttpResponse response = HttpUtil.doAccessApi(
                TestConfig.NGNIX_ADDR + TestConfig.MDM_TEST_PATH,
                "JSESSIONID=EF510684FF55EDDDCB1E53CBCBFE7F69");

        Debug.logVerbose(response.toString());

        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }

    public void testAccessOkAfterLogin() throws Exception {
        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);

        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        String token = HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE);

        response = HttpUtil.doAccessApi(TestConfig.NGNIX_ADDR + TestConfig.MDM_TEST_PATH, token);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL, token);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // should not access api after logout
        response = HttpUtil.doAccessApi(TestConfig.NGNIX_ADDR + TestConfig.MDM_TEST_PATH, token);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }


    public void testMultipleLoginWithSameUser() throws Exception {

        // login 1
        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);
        Debug.logVerbose(response.toString());
        String token1 = HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE);
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        assertNotNull(token1);

        // login 2
        response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL, TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);
        Debug.logVerbose(response.toString());
        String token2 = HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE);
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        assertNotNull(token2);

        // tokens of different login should not be same
        assertFalse(token1.equals(token2));

        // access api should be ok with token 1
        response = HttpUtil.doAccessApi(TestConfig.NGNIX_ADDR + TestConfig.MDM_TEST_PATH, token1);
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // access api should be ok with token 2
        response = HttpUtil.doAccessApi(TestConfig.NGNIX_ADDR + TestConfig.MDM_TEST_PATH, token2);
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // should not access api after logout
        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL, token1);
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        response = HttpUtil.doAccessApi(TestConfig.NGNIX_ADDR + TestConfig.MDM_TEST_PATH, token1);
        assertEquals(response.getStatusLine().getStatusCode(), 401);

        // toke2 should still be valid
        response = HttpUtil.doAccessApi(TestConfig.NGNIX_ADDR + TestConfig.MDM_TEST_PATH, token2);
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // should not access api after logout
        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL, token2);
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        response = HttpUtil.doAccessApi(TestConfig.NGNIX_ADDR + TestConfig.MDM_TEST_PATH, token2);
        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }
}
