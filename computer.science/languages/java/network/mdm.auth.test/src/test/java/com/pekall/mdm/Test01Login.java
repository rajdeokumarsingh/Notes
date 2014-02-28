package com.pekall.mdm;

import com.pekall.mdm.util.Const;
import com.pekall.mdm.util.Debug;
import com.pekall.mdm.util.HttpUtil;
import junit.framework.TestCase;

import org.apache.http.HttpResponse;

import org.junit.After;
import org.junit.Before;

public class Test01Login extends TestCase {

    @Before
    public void setUp() throws Exception {
        // Debug.setVerboseDebugLog(true);
    }

    @After
    public void tearDown() throws Exception {
        Debug.setVerboseDebugLog(false);
    }

    public void testLoginOK() throws Exception {

        HttpResponse response = HttpUtil.doLogin(Const.AUTH_LOGIN_URL,
                Const.USERNAME_OK, Const.PASSWORD_OK);

        Debug.logVerbose(response.toString());

        assertEquals(response.getStatusLine().getStatusCode(), 200);

        assertEquals(HttpUtil.getHeaderNumber(response, Const.HTTP_HDR_SET_COOKIE), 1);
        assertTrue(HttpUtil.getSingleHeader(response, Const.HTTP_HDR_SET_COOKIE).startsWith("JSESSIONID="));

        response = HttpUtil.doLogout(Const.AUTH_LOGOUT_URL,
                HttpUtil.getSingleHeader(response, Const.HTTP_HDR_SET_COOKIE));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

    public void testLoginFail() throws Exception {
        // error path
        HttpResponse response = HttpUtil.doLogin(Const.AUTH_LOGIN_URL + "/invalid",
                Const.USERNAME_OK, Const.PASSWORD_OK);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);

        // error username
        response = HttpUtil.doLogin(Const.AUTH_LOGIN_URL,
                Const.USERNAME_OK + "xxx", Const.PASSWORD_OK);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);

        // error password
        response = HttpUtil.doLogin(Const.AUTH_LOGIN_URL,
                Const.USERNAME_OK, Const.PASSWORD_OK + "xxx");
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }
}
