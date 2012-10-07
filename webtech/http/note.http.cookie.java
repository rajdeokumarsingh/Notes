

frameworks/base/core/java/android/webkit
    CookieManager.java  
    CookieSyncManager.java
    WebSyncManager.java

================================================================================
Cookie data:

sqlite> select * from cookies;
_id         name        value                                     domain             path        expires        secure    
----------  ----------  ----------------------------------------  -----------------  ----------  -------------  ----------
2           V5          AStfNg0nDCwmUCAkPSIjIz4XHAMAVlInHlI1wA__  .imrworldwide.com  /cgi-bin    1621132808000  0         
3           IMRID       TdSDCD2IPuMAAWcTlb4                       .imrworldwide.com  /cgi-bin    1621132808000  0         
14          WEBTRENDS_  211.136.28.135-129212784.30152146         sdc.10086.cn       /           1621134510000  0         
15          WT_FPC      id=20fd214314991c549bd1305773902657:lv=1  .10086.cn          /           1621134349000  0         
17          AlteonP     24254edcf426cdaef8d7baae                  www.monternet.com  /           1305835651000  0         
24          AlteonP     24254edcf426cdaef8d7baae                  www.monternet.com  /image/200  1305835831000  0         
25          AlteonP     24254edcf426cdaef8d7baae                  www.monternet.com  /image/200  1305835831000  0         
26          AlteonP     24254edcf426cdaef8d7baae                  www.monternet.com  /image/200  1305835831000  0         
27          AlteonP     24254edcf426cdaef8d7baae                  www.monternet.com  /image/200  1305835831000  0         
28          AlteonP     24254edcf426cdaef8d7baae                  www.monternet.com  /image/200  1305835831000  0         
29          AlteonP     24254edcf426cdaef8d7baae                  www.monternet.com  /image/200  1305835831000  0         
31          __utma      235265820.1127259870.1305772658.13057745  .live.monternet.c  /           1368848091000  0         
33          __utmz      235265820.1305776091.4.4.utmccn=(referra  .live.monternet.c  /           1321544091000  0         
36          counter     2                                         218.206.177.209    /waptest/c  1305827940000  0         
42          IAr7_2132_  1305794838                                bbs.gdou.edu.cn    /           1308390438000  0         
43          IAr7_2132_  57ZV0G                                    bbs.gdou.edu.cn    /           1305884838000  0         
44          IAr7_2132_  1305798438%09home.php%09misc              bbs.gdou.edu.cn    /           1305884838000  0         
45          cnzz_a2794  1                                         localhost          /           1305849600000  0         
46          sin2794383                                            localhost          /           1305849600000  0         
47          rtime       0                                         localhost          /           1321527964000  0         
48          ltime       1305803164613                             localhost          /           1321527964000  0         
49          cnzz_eid    90974057-1305798415-                      localhost          /           1321527964000  0         
================================================================================

CookieManager.java  
    // manager cookies in memory
    // CookieSyncManager.java do the disk/flash job

    /* provide interfaces to browser and sync manager:
    setAcceptCookie [CookieManager]
    acceptCookie [CookieManager]
    setCookie [CookieManager]
    setCookie [CookieManager]
    getCookie [CookieManager]
    getCookie [CookieManager]
    removeSessionCookie [CookieManager]
    removeAllCookie [CookieManager]
    hasCookies [CookieManager]
    removeExpiredCookie [CookieManager]
    getUpdatedCookiesSince [CookieManager]
    deleteACookie [CookieManager]
    syncedACookie [CookieManager]
    deleteLRUDomain [CookieManager]
    getHostAndPath [CookieManager]
    getBaseDomain [CookieManager]
    parseCookie [CookieManager]*/


static class Cookie
    static final byte MODE_NEW = 0;
    static final byte MODE_NORMAL = 1;
    static final byte MODE_DELETED = 2;
    static final byte MODE_REPLACED = 3;
    
    String domain;
    String path;
    String name;
    String value;
    long expires;
    long lastAcessTime;
    long lastUpdateTime;
    boolean secure;
    byte mode;

boolean exactMatch(Cookie in)
    // An exact match means that domain, path, and name are equal. If
    // both values are null, the cookies match. If both values are
    // non-null, the cookies match. If one value is null and the other
    // is non-null, the cookies do not match (i.e. "foo=;" and "foo;")

boolean domainMatch(String urlHost) 

boolean pathMatch(String urlPath)

/**
* Get the base domain for a give host. E.g. mail.google.com will return
* google.com, test.mail.google.com will return google.com
* @param host The give host
* @return the base domain
*/
private String getBaseDomain(String host)

/**
* parseCookie() parses the cookieString which is a comma-separated list of
* one or more cookies in the format of "NAME=VALUE; expires=DATE;
* path=PATH; domain=DOMAIN_NAME; secure httponly" to a list of Cookies.
* Here is a sample: IGDND=1, IGPC=ET=UB8TSNwtDmQ:AF=0; expires=Sun,
* 17-Jan-2038 19:14:07 GMT; path=/ig; domain=.google.com, =,
* PREF=ID=408909b1b304593d:TM=1156459854:LM=1156459854:S=V-vCAU6Sh-gobCfO;
* expires=Sun, 17-Jan-2038 19:14:07 GMT; path=/; domain=.google.com which
* contains 3 cookies IGDND, IGPC, PREF and an empty cookie
* @param host The default host
* @param path The default path
* @param cookieString The string coming from "Set-Cookie:"
* @return A list of Cookies
*/
private ArrayList<Cookie> parseCookie(String host, String path,
String cookieString)

================================================================================
/**    
 * The CookieSyncManager is used to synchronize the browser cookie store
 * between RAM and permanent storage. To get the best performance, browser cookies are
 * saved in RAM. A separate thread saves the cookies between, driven by a timer. 
 */
CookieSyncManager.java
    extends WebSyncManager
    // just a wrapper of WebViewDatabase
    /* provide interfaces 
        getCookiesForDomain [CookieSyncManager]
        clearAllCookies [CookieSyncManager]     
        hasCookies [CookieSyncManager]              
        clearSessionCookies [CookieSyncManager]             
        clearExpiredCookies [CookieSyncManager] 
        syncFromRamToFlash [CookieSyncManager]  */

================================================================================
// implement the thread mechanism
abstract class WebSyncManager implements Runnable 
    // thread for syncing
    private Thread mSyncThread;  

    // database for the persistent storage
    protected WebViewDatabase mDataBase;










