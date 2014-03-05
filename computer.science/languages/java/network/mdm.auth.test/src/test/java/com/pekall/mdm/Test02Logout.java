package com.pekall.mdm;

import com.pekall.mdm.util.Const;
import com.pekall.mdm.util.Debug;
import com.pekall.mdm.util.HttpUtil;
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
        HttpResponse response = HttpUtil.doLogout(Const.AUTH_LOGOUT_URL + "/invalid",
                "JSESSIONID=667FF2FE6F3668DE4724E1588A975B11");
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);


        // empty cookie
        response = HttpUtil.doLogout(Const.AUTH_LOGOUT_URL, null);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);

        // error cookie
        response = HttpUtil.doLogout(Const.AUTH_LOGOUT_URL, "JSESSIONID=667FF2FE6F3668DE4724E1588A975B11");
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }

    public void testLogoutFail1() throws Exception {
        HttpResponse response = HttpUtil.doLogin(Const.AUTH_LOGIN_URL,
                Const.USERNAME_VALID, Const.PASSWORD_VALID);
        Debug.logVerbose(response.toString());

        // first logout should be OK
        response = HttpUtil.doLogout(Const.AUTH_LOGOUT_URL,
                HttpUtil.getSingleHeader(response, Const.HTTP_HDR_SET_COOKIE));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // logout second time should not be OK
        response = HttpUtil.doLogout(Const.AUTH_LOGOUT_URL,
                HttpUtil.getSingleHeader(response, Const.HTTP_HDR_SET_COOKIE));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }

    public void testLogoutOK() throws Exception {
        HttpResponse response = HttpUtil.doLogin(Const.AUTH_LOGIN_URL,
                Const.USERNAME_VALID, Const.PASSWORD_VALID);
        Debug.logVerbose(response.toString());

        response = HttpUtil.doLogout(Const.AUTH_LOGOUT_URL,
                HttpUtil.getSingleHeader(response, Const.HTTP_HDR_SET_COOKIE));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        assertEquals(HttpUtil.getHeaderNumber(response, Const.HTTP_HDR_SET_COOKIE), 1);
        assertTrue(HttpUtil.getSingleHeader(response,
                Const.HTTP_HDR_SET_COOKIE).startsWith("JSESSIONID="));
    }
}
