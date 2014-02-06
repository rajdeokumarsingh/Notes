/* ---------------------------------------------------------------------------------------------
 *                       Copyright (c) 2013 Capital Alliance Software(Pekall) 
 *                                    All Rights Reserved
 *    NOTICE: All information contained herein is, and remains the property of Pekall and
 *      its suppliers,if any. The intellectual and technical concepts contained herein are
 *      proprietary to Pekall and its suppliers and may be covered by P.R.C, U.S. and Foreign
 *      Patents, patents in process, and are protected by trade secret or copyright law.
 *      Dissemination of this information or reproduction of this material is strictly 
 *      forbidden unless prior written permission is obtained from Pekall.
 *                                     www.pekall.com
 *--------------------------------------------------------------------------------------------- 
 */

package com.pekall.demo.transinfo;

import android.os.Handler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.pekall.demo.ConstantInfo;
import com.pekall.demo.Utility;
import com.pekall.demo.bean.DemoBeanCollection;
import com.pekall.demo.cache.CacheManager;
import com.pekall.http.HttpException;
import com.pekall.mobileutil.GetRequest;
import com.pekall.mobileutil.Helper;
import com.pekall.mobileutil.TransInfo;

/**
 * 查询所有任务
 */
public class DemoTransInfo extends TransInfo {

    private static final String LOGTAG = "DemoTransInfo";

    @SuppressWarnings("UnusedDeclaration")
    public DemoTransInfo() {
        super(HTTP_GET, null);
    }

    public DemoTransInfo(Handler handler) {
        super(HTTP_GET, handler);
    }

    @Override
    public boolean hasCacheData() {
        boolean ret = (CacheManager.getInstance().getDemoBeanCollection() != null);
        Utility.log("DemoTransInfo, has cache: " + ret);
        return (CacheManager.getInstance().getDemoBeanCollection() != null);
    }

    @Override
    public void clearCache() {
        Utility.log("DemoTransInfo, clear cache");
        CacheManager.getInstance().setDemoBeanCollection(null);
    }

    @SuppressWarnings("UnusedDeclaration")
    GetRequest genGetRequest() {
        GetRequest get = new GetRequest(ConstantInfo.LOCAL_HOST_TEST_URL);
        get.appendHeaders("test", "demo");
        get.appendOptionalParam("test_format", "json");
        return get;
    }

    @Override
    public int internalProcessResponse(String resp) {
        Utility.log(LOGTAG, "internalProcessResponse: " + resp);
        Gson gson = new GsonBuilder().serializeNulls().create();
        DemoBeanCollection file = null;
        try {
            file = gson.fromJson(resp, DemoBeanCollection.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        CacheManager.getInstance().setDemoBeanCollection(file);

        return Helper.RESULT_DONE;
    }

    @Override
    public void handleHttpException(HttpException e) {
    }
}
