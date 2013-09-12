/*
 * Copyright (C) 2010 The Android Open Source Project
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

package com.android.browser;

import android.util.Log;
import android.util.Patterns;
import android.content.Context;
import android.content.Intent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import android.os.SystemProperties;


/**
 * Controller for browser
 */
public class ThemeCheck {


    //Extreme Booster Loading
    private static final int RES_EXTREME_NUM = 13;

    private static final String[] encResLoading = {
        //ie.microsoft.testdrive.performance | Graphics  (fishIEtank, fishBowl, SpeedReading,FlyingImage,GraphicsAccelebration)
        "FC6C29B8BBA8D1232E96BE52A6707B7DEB2E1076128EAE4C08A6BFB5C3FBA5E290A47389BCF4F5A17F7B33550CF09FD41A51442EB35F82C73621BFF4160B002E77A9CBBED145F3CAC56137376AD821FB",
        //ie.microsoft.testdrive.Mobile.Performance (fishIEtank, SpeedReading)
        "FC6C29B8BBA8D1232E96BE52A6707B7DEB2E1076128EAE4C08A6BFB5C3FBA5E22F7D2FBE27B3AB74B55C2C98F68251C00DE044E1063162DAFFC08E6CAA4E8E3EE84F940C7FC955E9F46CE3798023C381",
        //guimark3
        "FC6C29B8BBA8D1232E96BE52A6707B7DF87B100BB1CFE5999D3321F44D8CF3B9756EADC702335C764EC5ED66A3E5EBFDD9F4F6B862A57324834262B88E65714B",
        //Sunspider
        "FC6C29B8BBA8D1232E96BE52A6707B7DDFF926747E2ED30784944388894AE4D6F2CCD80D3F43C97C9854CAA5EC52073B3BC555CFA0F67D58B3FFF73C565C77E4C417BDAC9FCE7060435D7E8557A943C2AF3973241036785759E68AF3CCB44393C4631F771AB8A94AF8B01D4AFB387468",
        //Kraken 1.1
        "FC6C29B8BBA8D1232E96BE52A6707B7D32C2A5544047534B8107CE4D225A2BB5804982E3E03136A75717554864FA054D1F64B448CE51E30D9EA09BB3BC85F96D67F0B12CC1DD9F551A60B13C8FEEB58D",
        //v8 benchmark
        "FC6C29B8BBA8D1232E96BE52A6707B7DA1B8932D3E73B6A2E375367F7D7FA260A7521A6871015E0D4F08FAA399AE92873CFA63B5401EE7DD7E0AEEE5E21D33AE4FB55CF2F5D4E688C9A1BBA94D7A4530",
        //Octane v1
        "FC6C29B8BBA8D1232E96BE52A6707B7DB93B8A15A3DA7548C67602B0B53CC3DBDBF0184FF4786720795241064C7C271081CB060637E8A5D48260AF6FD60556F675E7E3898107F291411D92997D139F63",
        //PeaceKeeper
        "FC6C29B8BBA8D1232E96BE52A6707B7D6A12C467357C78BD1E275D45994BEEBAFD9EC8E09D6B4204BA6DDDF6A212ED31AAF890BB9CEED43EEE5863F477427E80",
        //BrowserMark 2.0
        "FC6C29B8BBA8D1232E96BE52A6707B7DB58DB85FE63CAEC57433A87FD85D90A5557D14EC9493D60B8E3965FFDCBB5A87DF354C5DFF8C48967A5067B06894E8BD",
        //impact html benchmark
        "FC6C29B8BBA8D1232E96BE52A6707B7DE001BA86C1C9A70DC3443618DDBCADA17063FA86775EA81357D49D5A2966810B",
        //webxprt
        "FC6C29B8BBA8D1232E96BE52A6707B7D7DD3B0AD4BC109DE342135FA8A97C1F3F8CC036D99F1BFA024922122CA795195270FA999BDE5EC73F74B8BAA1BAF15FFF7D0E2281AB0E73673B96324B45D3CB6CD213CC0FD822633646BDC0121CDC24E2757966FA1A26F0D9DBD51F184F71FDF",
        //scortt porter
        "FC6C29B8BBA8D1232E96BE52A6707B7D983D3CEA16F30CA3DEC423DCD14AF1DFFCA3702A1699C07ACD9B23FA81CC99AC8CD30D602C43749F2101FC13B42735FE",
        //fruit ninja
        "FC6C29B8BBA8D1232E96BE52A6707B7DF9A902E902B2B6E627A09A135D8F6A745E448C3BA63FA9C607BCDD5745FB4A887A641E65DEA68675AA7E0B1E42C4D23D"
    };

    private static Pattern[] pResLoading = new Pattern[RES_EXTREME_NUM];

    private static int[] timeoutSetting = {
        //ie.microsoft.testdrive
        3600000,
        //ie.microsoft.testdrive.mobile
        3600000,
        //guimark3
        3600000,
        //Sunspider
        140000,
        //Kraken 1.1
        480000,
        //v8 benchmark
        45000,
        //Octane v1
        120000,
        //PeaceKeeper
        480000,
        //BrowserMark 2.0
        660000,
        //impact html5 benchmark
        320000,
        //webxprt
        2100000,
        //scortt porter
        3600000,
        //fruit ninja
        3600000,
        //NORMAL
        8000
    };

    private static boolean isThemeCheckRun = true;

    public ThemeCheck() {
        if(pResLoading[0] == null) {
            try {
                int i = 0;
                while(i < RES_EXTREME_NUM) {
                    pResLoading[i] = Pattern.compile(SimpleCrypto.decrypt("mrvl", encResLoading[i]).toLowerCase());
                    i++;
                }
            } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
        }
        isThemeCheckRun = (SystemProperties.get("marvell.browser.themecheck", "1")).equals("1");
    }


    static int isThemeExtreme(String url) {
        if(isThemeCheckRun) {
            //Normal url ,return true
            //currently when Extreme Loading finish, send Extreme Booster
            int index = 0;
            while(index < RES_EXTREME_NUM) {
                if(pResLoading[index] != null) {
                    Matcher m = pResLoading[index].matcher(url.toLowerCase());
                    if(m.lookingAt()) {
                        return index;
                    }
                }
                index++;
            }
        }
        return -1;
    }

    static void SendBoosterIntent(boolean isNormal, Context context, int index) {
        if(isThemeCheckRun) {
            Intent dvfsLockIntent = new Intent();
            dvfsLockIntent.setAction("com.sec.android.intent.action.MRVL_DVFS_BOOSTER"); // Name for the intent

            if(isNormal) {
                dvfsLockIntent.putExtra("DURATION", String.valueOf(timeoutSetting[RES_EXTREME_NUM]));
                dvfsLockIntent.putExtra("BOOST_REASON", "BROWSER");
            } else {
                dvfsLockIntent.putExtra("DURATION", String.valueOf(timeoutSetting[index])); // put string extra for the intent
                dvfsLockIntent.putExtra("BOOST_REASON", "BROWSER_EXTREME"); // put string extra the boost reason, webkit stands for webkit scenario.
            }

            context.sendBroadcast(dvfsLockIntent);  // broadcast
        }
    }

    static void trySendFinishBoosterIntent(boolean isNormal, Context context) {
        if(isThemeCheckRun) {
            Intent dvfsLockIntent = new Intent();
            dvfsLockIntent.setAction("com.sec.android.intent.action.MRVL_DVFS_BOOSTER"); // Name for the intent

            dvfsLockIntent.putExtra("DURATION", "-1");

            if(isNormal) {
                dvfsLockIntent.putExtra("BOOST_REASON", "BROWSER");
            } else {
                dvfsLockIntent.putExtra("BOOST_REASON", "BROWSER_EXTREME"); // put string extra the boost reason, webkit stands for webkit scenario
            }

            context.sendBroadcast(dvfsLockIntent);  // broadcast
        }
    }


    static void checkTabCancelBooster(Tab tab, Context context, String LastUrl) {
        //for closeTab
        //if there is Extreme booster run, try cancel
        String url = tab.getUrl();
        if(isThemeExtreme(url) >= 0) {
            trySendFinishBoosterIntent(false, context);
        } else if(url.equals(LastUrl)) {
        //if it is normal booster, and close its tab, try to cancel this normal booster loading
            trySendFinishBoosterIntent(true, context);
        }
    }

}

