package com.pekall.mdm.auth.testclient;

import com.pekall.mdm.auth.testclient.util.TestConfig;
import com.pekall.mdm.auth.testclient.util.Debug;
import com.pekall.mdm.auth.testclient.util.HttpUtil;
import junit.framework.TestCase;

import org.apache.http.HttpResponse;

import org.junit.After;
import org.junit.Before;

public class Test01Login extends TestCase {

    @Before
    public void setUp() throws Exception {
        Debug.setVerboseDebugLog(true);
    }

    @After
    public void tearDown() throws Exception {
        Debug.setVerboseDebugLog(false);
    }

    public void testLoginOK() throws Exception {

        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);

        Debug.logVerbose(response.toString());

        assertEquals(response.getStatusLine().getStatusCode(), 200);

        assertEquals(HttpUtil.getHeaderNumber(response, TestConfig.HTTP_HDR_SET_COOKIE), 1);
        assertTrue(HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE).startsWith("JSESSIONID="));

        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL,
                HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

//    public void testLoginOK2() throws Exception {
//
//        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
//                TestConfig.USERNAME_NEW_ADDED, TestConfig.PASSWORD_NEW_ADDED);
//
//        Debug.logVerbose(response.toString());
//
//        assertEquals(response.getStatusLine().getStatusCode(), 200);
//
//        assertEquals(HttpUtil.getHeaderNumber(response, TestConfig.HTTP_HDR_SET_COOKIE), 1);
//        assertTrue(HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE).startsWith("JSESSIONID="));
//
//        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL,
//                HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE));
//        Debug.logVerbose(response.toString());
//        assertEquals(response.getStatusLine().getStatusCode(), 200);
//    }

    public void testLoginFail() throws Exception {
        // error path
        HttpResponse response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL + "/invalid",
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);

        // error username
        response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID + "xxx", TestConfig.PASSWORD_OLD);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);

        // error password
        response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_OLD + "xxx");
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }
}

