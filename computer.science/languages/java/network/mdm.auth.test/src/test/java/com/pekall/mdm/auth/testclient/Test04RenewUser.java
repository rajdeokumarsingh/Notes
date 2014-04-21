package com.pekall.mdm.auth.testclient;

import com.pekall.mdm.auth.testclient.util.security.MdmPasswordEncoder;
import com.pekall.mdm.auth.testclient.util.TestConfig;
import com.pekall.mdm.auth.testclient.util.Debug;
import com.pekall.mdm.auth.testclient.util.HttpUtil;
import junit.framework.TestCase;
import org.apache.http.HttpResponse;
import org.junit.After;
import org.junit.Before;

import java.net.URLEncoder;

public class Test04RenewUser extends TestCase {

    @Before
    public void setUp() throws Exception {
        // Debug.setVerboseDebugLog(true);
    }

    @After
    public void tearDown() throws Exception {
        Debug.setVerboseDebugLog(false);
    }

    public void testWrongParam() throws Exception {
        // not any params
        HttpResponse response = HttpUtil.doAccessApi(
                TestConfig.AUTH_SVR_ADDR + TestConfig.AUTH_RENEW_USER_PATH, null);
        assertEquals(response.getStatusLine().getStatusCode(), 404);

        // no email
        response = HttpUtil.doAccessApi(TestConfig.AUTH_SVR_ADDR + TestConfig.AUTH_RENEW_USER_PATH
                + "?id=xxx"
                + "&password=" + URLEncoder.encode("xxx", "utf-8"), null);
        assertEquals(response.getStatusLine().getStatusCode(), 404);

        // email is null
        response = HttpUtil.doAccessApi(TestConfig.AUTH_SVR_ADDR + TestConfig.AUTH_RENEW_USER_PATH
                + "?email=" + "&id=id"
                + "&password=" + URLEncoder.encode("xxx", "utf-8"), null);
        assertEquals(response.getStatusLine().getStatusCode(), 404);

        // no id
        HttpUtil.doAccessApi(TestConfig.AUTH_SVR_ADDR + TestConfig.AUTH_RENEW_USER_PATH
                + "?email=xxx"
                + "&password=" + URLEncoder.encode("xxx", "utf-8"), null);
        assertEquals(response.getStatusLine().getStatusCode(), 404);

        // no pwd
        HttpUtil.doAccessApi(TestConfig.AUTH_SVR_ADDR + TestConfig.AUTH_RENEW_USER_PATH
                + "?email=xxx@pekall.com" + "&id=xxx", null);
        assertEquals(response.getStatusLine().getStatusCode(), 404);

    }

    public void testNormal() throws Exception {
        // renew_user must be accessed after login
        HttpResponse response = HttpUtil.renewUser("1", TestConfig.USERNAME_VALID,
                new MdmPasswordEncoder().encode(TestConfig.PASSWORD_NEW));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        response = HttpUtil.doLogin(TestConfig.AUTH_LOGIN_URL,
                TestConfig.USERNAME_VALID, TestConfig.PASSWORD_NEW);
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        assertEquals(HttpUtil.getHeaderNumber(response, TestConfig.HTTP_HDR_SET_COOKIE), 1);
        assertTrue(HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE).startsWith("JSESSIONID="));

        response = HttpUtil.doLogout(TestConfig.AUTH_LOGOUT_URL,
                HttpUtil.getSingleHeader(response, TestConfig.HTTP_HDR_SET_COOKIE));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);

        response = HttpUtil.renewUser("1", TestConfig.USERNAME_VALID,
                new MdmPasswordEncoder().encode(TestConfig.PASSWORD_OLD));
        Debug.logVerbose(response.toString());
        assertEquals(response.getStatusLine().getStatusCode(), 200);
    }

}
