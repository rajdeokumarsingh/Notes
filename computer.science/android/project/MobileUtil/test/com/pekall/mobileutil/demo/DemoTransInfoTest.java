/*
 * Copyright (C) 2013 Capital Alliance Software LTD (Pekall)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pekall.mobileutil.demo;

import android.test.AndroidTestCase;
import com.pekall.demo.bean.DemoBean;
import com.pekall.demo.bean.DemoBeanCollection;
import com.pekall.demo.cache.CacheManager;
import com.pekall.demo.transinfo.DemoTransInfo;
import com.pekall.mobileutil.HttpTestServer;
import com.pekall.mobileutil.Transaction;

import java.util.List;

public class DemoTransInfoTest extends AndroidTestCase {
    public static final String LOGTAG = "DemoTransInfoTest";
    private HttpTestServer mServer;

    public void setUp() throws Exception {
        super.setUp();
        mServer = HttpTestServer.launch();
    }

    public void tearDown() throws Exception {
        mServer.quit();
    }

    public void testSendRequest() throws Exception {
        CacheManager.getInstance().clearAllCache();
        Transaction.enqueueTransInfo(new DemoTransInfo());

        Thread.sleep(2000);

        DemoBeanCollection collection = CacheManager.getInstance().getDemoBeanCollection();
        List<DemoBean> beans = collection.getBeans();
        assertNotNull(collection);
        assertEquals(beans.size(), 2);
    }
}
