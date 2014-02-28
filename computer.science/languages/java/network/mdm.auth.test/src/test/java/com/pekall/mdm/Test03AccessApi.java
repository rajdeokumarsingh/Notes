package com.pekall.mdm;

import com.pekall.mdm.util.Const;
import com.pekall.mdm.util.Debug;
import com.pekall.mdm.util.HttpUtil;
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
        HttpResponse response = HttpUtil.doAccessApi(Const.AUTH_SVR_ADDR + "/rest/api/test", null);
        Debug.logVerbose(response.toString());

        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }

    public void testAccessWithWrongToken() throws Exception {

        HttpResponse response = HttpUtil.doAccessApi(
                Const.AUTH_SVR_ADDR + "/rest/api/test",
                "JSESSIONID=EF510684FF55EDDDCB1E53CBCBFE7F69");

        Debug.logVerbose(response.toString());

        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }

    public void testAccessOkAfterLogin() throws Exception {
        HttpResponse response = HttpUtil.doLogin(Const.AUTH_LOGIN_URL,
                Const.USERNAME_OK, Const.PASSWORD_OK);

        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        String token = HttpUtil.getSingleHeader(response, Const.HTTP_HDR_SET_COOKIE);

        response = HttpUtil.doAccessApi(Const.AUTH_SVR_ADDR + "/rest/api/test", token);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        response = HttpUtil.doLogout(Const.AUTH_LOGOUT_URL, token);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // should not access api after logout
        response = HttpUtil.doAccessApi(Const.AUTH_SVR_ADDR + "/rest/api/test", token);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }


    public void testMultipleLoginWithSameUser() throws Exception {

        // login 1
        HttpResponse response = HttpUtil.doLogin(Const.AUTH_LOGIN_URL,
                Const.USERNAME_OK, Const.PASSWORD_OK);
        Debug.logVerbose(response.toString());
        String token1 = HttpUtil.getSingleHeader(response, Const.HTTP_HDR_SET_COOKIE);
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        assertNotNull(token1);

        // login 2
        response = HttpUtil.doLogin(Const.AUTH_LOGIN_URL, Const.USERNAME_OK, Const.PASSWORD_OK);
        Debug.logVerbose(response.toString());
        String token2 = HttpUtil.getSingleHeader(response, Const.HTTP_HDR_SET_COOKIE);
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        assertNotNull(token2);

        // tokens of different login should not be same
        assertFalse(token1.equals(token2));

        // access api should be ok with token 1
        response = HttpUtil.doAccessApi(Const.AUTH_SVR_ADDR + "/rest/api/test", token1);
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // access api should be ok with token 2
        response = HttpUtil.doAccessApi(Const.AUTH_SVR_ADDR + "/rest/api/test", token2);
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // should not access api after logout
        response = HttpUtil.doLogout(Const.AUTH_LOGOUT_URL, token1);
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        response = HttpUtil.doAccessApi(Const.AUTH_SVR_ADDR + "/rest/api/test", token1);
        assertEquals(response.getStatusLine().getStatusCode(), 401);

        // toke2 should still be valid
        response = HttpUtil.doAccessApi(Const.AUTH_SVR_ADDR + "/rest/api/test", token2);
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        // should not access api after logout
        response = HttpUtil.doLogout(Const.AUTH_LOGOUT_URL, token2);
        assertEquals(response.getStatusLine().getStatusCode(), 200);
        response = HttpUtil.doAccessApi(Const.AUTH_SVR_ADDR + "/rest/api/test", token2);
        assertEquals(response.getStatusLine().getStatusCode(), 401);
    }
}
