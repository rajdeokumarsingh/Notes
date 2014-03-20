package com.pekall.mdm.auth.testclient;

import com.pekall.mdm.auth.testclient.util.Debug;
import com.pekall.mdm.auth.testclient.util.HttpUtil;
import com.pekall.mdm.auth.testclient.util.TestConfig;
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

public class Test40CreateUser extends TestCase {

    private MemcachedClient memcachedClient;

    /** create connection with the memory cache cluster */
    private void initMemcache() {
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
        initMemcache();
    }

    @After
    public void tearDown() throws Exception {
        Debug.setVerboseDebugLog(false);
    }

    public void testCreateUser() throws Exception {
//        String email = "rui.jiang@pekall.com";
//
//        memcachedClient.delete(email);
//        assertNull(memcachedClient.get(email));
//
//        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
//        postParams.add(new BasicNameValuePair("username", "jiangrui"));
//        postParams.add(new BasicNameValuePair("email", email));
//        HttpResponse response = HttpUtil.doHttpPost(
//                TestConfig.MDM_SVR_ADDR + "/mdm/v1/tenants/register_person", postParams);
//        Debug.logVerbose(response.toString());
//        assertEquals(response.getStatusLine().getStatusCode(), 200);
//
//        // after login, user info will be updated to memcached
//        assertNotNull(memcachedClient.get(email));
    }

}
